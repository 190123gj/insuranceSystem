package com.born.insurance.biz.service.base;

import java.util.Calendar;
import java.util.Date;

import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.exception.InsuranceBizException;
import com.born.insurance.biz.service.common.DateSeqService;
import com.born.insurance.biz.service.common.OperationJournalService;
import com.born.insurance.biz.service.common.SysParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.born.insurance.domain.context.InsuranceDomainContext;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.domain.exception.InsuranceDomainException;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.enums.SysParamEnum;
import com.born.insurance.ws.enums.TimeUnitEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.order.base.ProcessOrder;
import com.born.insurance.ws.order.common.OperationJournalAddOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.service.base.CheckBeforeProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.Order;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import com.yjf.common.service.base.ProcessInvokeService;

/**
 * @Filename BaseAutowiredService.java
 * @Description
 * @Version 1.0
 * @Author qichunhai
 * @Email qchunhai@yiji.com
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-2</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public class BaseAutowiredDomainService extends BaseAutowiredDAOService {
	
	/** 事务模板 */
	@Autowired
	protected TransactionTemplate transactionTemplate;
	
	@Autowired
	protected OperationJournalService operationJournalService;
	
	@Autowired
	protected DateSeqService dateSeqService;
	
	@Autowired
	protected SysParameterService sysParameterService;
	
	@Autowired
	protected ThreadPoolTaskExecutor taskExecutor;
	
	public static final String YRD_HOLDER_RESULT_KEY = "result";
	
	public static Money ZERO_MONEY = Money.zero();
	
	protected void checkOrder(Order order) {
		logger.info("[order={}]", order);
		
		if (null == order) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.EXECUTE_FAILURE,
				"order must not be null");
		}
		
		try {
			order.check();
		} catch (IllegalArgumentException ex) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.INCOMPLETE_REQ_PARAM,
				"请求参数异常--" + ex.getLocalizedMessage());
			
		}
	}
	
	protected void setInsuranceException(TransactionStatus status, InsuranceBaseResult result,
											InsuranceBizException eex, String errorMessage) {
		if (status != null)
			status.setRollbackOnly();
		result.setSuccess(false);
		result.setInsuranceResultEnum(eex.getResultCode());
		result.setMessage(errorMessage);
		logger.error(eex.getLocalizedMessage() + " ==errMesaage=" + eex.getErrorMsg()
						+ " == result  =" + result);
	}
	
	protected void setInsuranceDomainException(TransactionStatus status,
												InsuranceBaseResult result,
												InsuranceDomainException eex, String errorMessage) {
		status.setRollbackOnly();
		result.setSuccess(false);
		result.setInsuranceResultEnum(InsuranceResultEnum
			.getByCode(eex.getDomainResult().getCode()));
		result.setMessage(errorMessage);
		logger.error(eex.getLocalizedMessage() + " ==errMesaage=" + eex.getErrorMsg(), eex);
	}
	
	protected void setUnknownException(InsuranceBaseResult result, Throwable ex) {
		logger.error(ex.getLocalizedMessage(), ex);
		result.setSuccess(false);
		result.setInsuranceResultEnum(InsuranceResultEnum.UN_KNOWN_EXCEPTION);
	}
	
	protected void setDbException(TransactionStatus status, InsuranceBaseResult result, Throwable e) {
		logger.error(e.getLocalizedMessage(), e);
		status.setRollbackOnly();
		result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		result.setSuccess(false);
	}
	
	protected InsuranceBaseResult commonProcess(final Order order,
												final String processBizName,
												final CheckBeforeProcessService checkBeforeProcessService,
												final BeforeProcessInvokeService beforeProcessInvokeService,
												final ProcessInvokeService processInvokeService,
												final AfterProcessInvokeService successProcessInvokeService) {
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} order={} ", processBizName, order);
		
		final Date nowDate = getSysdate();
		boolean isClear = false;
		if (InsuranceDomainHolder.get() == null) {
			InsuranceDomainHolder.set(new InsuranceDomainContext<Order>(nowDate, order, null));
			isClear = true;
		}
		
		InsuranceBaseResult result = null;
		if (InsuranceDomainHolder.get().getAttribute("result") == null) {
			result = createResult();
			InsuranceDomainHolder.get().addAttribute("result", result);
		} else {
			result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
		}
		
		try {
			
			checkOrder(order);
			if (checkBeforeProcessService != null)
				checkBeforeProcessService.check();
			result = transactionTemplate.execute(new TransactionCallback<InsuranceBaseResult>() {
				
				@Override
				public InsuranceBaseResult doInTransaction(TransactionStatus status) {
					InsuranceBaseResult result = null;
					if (InsuranceDomainHolder.get().getAttribute("result") == null) {
						result = createResult();
					} else {
						result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute(
							"result");
					}
					try {
						// 激活领域模型
						Domain domain = null;
						InsuranceDomainHolder.get().addAttribute("result", result);
						if (beforeProcessInvokeService != null) {
							domain = beforeProcessInvokeService.before();
							logger.info("beforeProcessInvokeService.before():" + domain);
						}
						
						if (domain != null) {
							InsuranceDomainHolder.get().setDomain(domain);
						}
						if (processInvokeService != null) {
							processInvokeService.process(domain);
							logger.info("processInvokeService.process():" + domain);
						}
						
						if (result.getInsuranceResultEnum() == InsuranceResultEnum.UN_KNOWN_EXCEPTION) {
							result.setSuccess(true);
						}
						
						if (order instanceof ProcessOrder) {
							addOperationJournalInfo((ProcessOrder) order, processBizName,
								processBizName, order.toString());
						}
					} catch (InsuranceBizException eex) {
						setInsuranceException(status, result, eex, eex.getErrorMsg());
						
					} catch (InsuranceDomainException e) {
						setInsuranceDomainException(status, result, e, e.getErrorMsg());
					} catch (Exception e) {
						setDbException(status, result, e);
					} catch (Throwable e) {
						setDbException(status, result, e);
					}
					
					return result;
				}
			});
		} catch (InsuranceBizException eex) {
			logger.error(eex.getErrorMsg(), eex);
			result.setSuccess(false);
			result.setInsuranceResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
			
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		if (result.isSuccess()) {
			try {
				if (successProcessInvokeService != null) {
					successProcessInvokeService.after(InsuranceDomainHolder.get().getDomain());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (isClear) {
			InsuranceDomainHolder.clear();
		}
		logger.info("-处理结束{}  commonProcess processBizName={} result={} ", this.getClass()
			.getName(), processBizName, result);
		
		return result;
	}
	
	protected InsuranceBaseResult commonProcess(final Order order,
												final String processBizName,
												final BeforeProcessInvokeService beforeProcessInvokeService,
												final ProcessInvokeService processInvokeService,
												final AfterProcessInvokeService successProcessInvokeService) {
		return commonProcess(order, processBizName, null, beforeProcessInvokeService,
			processInvokeService, successProcessInvokeService);
	}
	
	protected InsuranceBaseResult checkProcess(final Order order, final String processBizName,
												final ProcessInvokeService processInvokeService) {
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} order={} ", processBizName, order);
		InsuranceBaseResult result = null;
		final Date nowDate = getSysdate();
		InsuranceDomainHolder.set(new InsuranceDomainContext<Order>(nowDate, order, null));
		try {
			checkOrder(order);
			result = createResult();
			processInvokeService.process(null);
			result.setSuccess(true);
			return result;
		} catch (InsuranceBizException eex) {
			logger.error(eex.getLocalizedMessage(), eex);
			result.setSuccess(false);
			result.setInsuranceResultEnum(eex.getResultCode());
			result.setMessage(processBizName + "异常[" + eex.getErrorMsg() + "]");
		} catch (Exception ex) {
			setUnknownException(result, ex);
		} catch (Throwable e) {
			setUnknownException(result, e);
		}
		InsuranceDomainHolder.clear();
		logger.info("-进入{} " + this.getClass().getName()
					+ "  commonProcess processBizName={} result={} ", processBizName, result);
		return result;
	}
	
	protected void addOperationJournalInfo(ProcessOrder opOrder, String permissionName,
											String operationContent, String memo) {
		try {
			OperationJournalAddOrder order = new OperationJournalAddOrder();
			order.setMemo(memo);
			
			if (opOrder == null || opOrder.getUserId() == null || opOrder.getUserId() <= 0) {
				order.setOperatorId(-1);
				order.setOperatorName("系统自动");
				order.setOperatorIp("127.0.0.1");
			} else {
				order.setOperatorId(opOrder.getUserId() == null ? 0 : opOrder.getUserId());
				String userName = opOrder.getUserName();
				if (StringUtil.isNotBlank(opOrder.getUserAccount())) {
					userName = userName + "[" + opOrder.getUserAccount() + "]";
				}
				order.setOperatorName(userName);
				order.setOperatorIp(opOrder.getUserIp());
			}
			order.setBaseModuleName("项目管理");
			order.setPermissionName(permissionName);
			order.setOperationContent(operationContent);
			
			operationJournalService.addOperationJournalInfo(order);
		} catch (Exception e) {
			logger.error("添加操作日志失败,失败原因：{}", e.getMessage(), e);
		}
	}
	
	protected InsuranceBaseResult simpleSaveTemplate(String processBizName, String paramNames,
														ProcessInvokeService processInvokeService) {
		InsuranceBaseResult baseResult = createResult();
		try {
			processInvokeService.process(null);
			baseResult.setSuccess(true);
			addOperationJournalInfo(null, processBizName, processBizName, paramNames);
		} catch (InsuranceBizException eex) {
			setInsuranceException(null, baseResult, eex, eex.getErrorMsg());
			
		} catch (Exception e) {
			setUnknownException(baseResult, e);
		} catch (Throwable e) {
			setUnknownException(baseResult, e);
		}
		return baseResult;
	}
	
	/**
	 * 默认返回结果类型
	 * 
	 * @return
	 */
	protected InsuranceBaseResult createResult() {
		return new InsuranceBaseResult();
	}
	
	/**
	 * 获取授信结束日期
	 * 
	 * @param startDate 授信开始日期
	 * @param timeUnit 单位
	 * @param timeLimit 时长
	 * @return
	 */
	protected Date calculateExpireDate(Date startDate, TimeUnitEnum timeUnit, int timeLimit) {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		if (timeUnit == TimeUnitEnum.YEAR) {
			c.add(Calendar.YEAR, timeLimit);
		} else if (timeUnit == TimeUnitEnum.MONTH) {
			c.add(Calendar.MONTH, timeLimit);
		} else if (timeUnit == TimeUnitEnum.DAY) {
			c.add(Calendar.DAY_OF_MONTH, timeLimit);
		}
		return c.getTime();
	}
	
	/**
	 * 获取web访问地址
	 * @return
	 */
	protected String getFaceWebUrl() {
		String faceUrl = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code());
		if (faceUrl != null && faceUrl.endsWith("/")) {
			faceUrl = faceUrl.substring(0, faceUrl.length() - 1);
		}
		return faceUrl;
	}
	
}
