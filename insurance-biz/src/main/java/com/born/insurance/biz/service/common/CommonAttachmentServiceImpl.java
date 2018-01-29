package com.born.insurance.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.born.insurance.dal.daointerface.CommonAttachmentDAO;
import com.born.insurance.dal.dataobject.CommonAttachmentDO;
import com.born.insurance.util.MiscUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CheckStatusEnum;
import com.born.insurance.ws.enums.CommonAttachmentTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.born.insurance.ws.order.common.CommonAttachmentBatchOrder;
import com.born.insurance.ws.order.common.CommonAttachmentDeleteOrder;
import com.born.insurance.ws.order.common.CommonAttachmentOrder;
import com.born.insurance.ws.order.common.CommonAttachmentQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.result.common.CommonAttachmentResult;
import com.born.insurance.ws.service.common.CommonAttachmentService;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.StringUtil;

@Service("commonAttachmentService")
public class CommonAttachmentServiceImpl extends BaseAutowiredDomainService implements
																			CommonAttachmentService {
	@Autowired
	CommonAttachmentDAO commonAttachmentDAO;
	
	@Override
	public QueryBaseBatchResult<CommonAttachmentInfo> queryCommonAttachment(CommonAttachmentQueryOrder order) {
		QueryBaseBatchResult<CommonAttachmentInfo> result = new QueryBaseBatchResult<CommonAttachmentInfo>();
		logger.info("进入列表查询图片信息");
		try {
			
			List<String> moduleTypeCodeList = new ArrayList<String>();
			if (order.getModuleTypeList() != null) {
				for (CommonAttachmentTypeEnum typeEnum : order.getModuleTypeList()) {
					moduleTypeCodeList.add(typeEnum.code());
				}
			}
			if (order.getIsort() != 0) {
				List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO.findByIsort(
					order.getIsort(), order.getBizNo(), order.getChildId());
				List<CommonAttachmentInfo> attachmentInfos = new ArrayList<CommonAttachmentInfo>();
				for (CommonAttachmentDO tradeAttachmentDO : attachmentDOs) {
					CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
					MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
					attachmentInfos.add(attachmentInfo);
				}
				result.setPageList(attachmentInfos);
				result.setSuccess(true);
				result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
				result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
			} else {
				List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO.findByManyModuleType(
					moduleTypeCodeList, order.getBizNo(), order.getChildId());
				List<CommonAttachmentInfo> attachmentInfos = new ArrayList<CommonAttachmentInfo>();
				for (CommonAttachmentDO tradeAttachmentDO : attachmentDOs) {
					CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
					MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
					if (StringUtil.isNotBlank(tradeAttachmentDO.getModuleType())) {
						attachmentInfo.setModuleType(CommonAttachmentTypeEnum
							.getByCode(tradeAttachmentDO.getModuleType()));
					}
					
					if (StringUtil.isNotBlank(tradeAttachmentDO.getCheckStatus())) {
						attachmentInfo.setCheckstatus(CheckStatusEnum.getByCode(tradeAttachmentDO
							.getCheckStatus()));
					}
					attachmentInfos.add(attachmentInfo);
				}
				result.setPageList(attachmentInfos);
				result.setSuccess(true);
				result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
				result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
			}
			
		} catch (DataAccessException e) {
			logger.error("列表查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("列表查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<CommonAttachmentInfo> queryPage(CommonAttachmentQueryOrder order) {
		QueryBaseBatchResult<CommonAttachmentInfo> result = new QueryBaseBatchResult<CommonAttachmentInfo>();
		try {
			CommonAttachmentDO queryDO = new CommonAttachmentDO();
			BeanCopier.staticCopy(order, queryDO);
			if (order.getCheckStatus() != null) {
				queryDO.setCheckStatus(order.getCheckStatus().code());
			}
			if (order.getModuleType() != null) {
				queryDO.setModuleType(order.getModuleType().code());
			}
			if (order.getCheckStatus() != null) {
				queryDO.setCheckStatus(order.getCheckStatus().code());
			}
			List<String> moduleTypeList = null;
			if (order.getModuleTypeList() != null) {
				moduleTypeList = new ArrayList<String>();
				for (CommonAttachmentTypeEnum typeEnum : order.getModuleTypeList()) {
					moduleTypeList.add(typeEnum.code());
				}
			}
			long totalSize = commonAttachmentDAO.findConditionCount(queryDO, moduleTypeList);
			PageComponent component = new PageComponent(order, totalSize);
			
			List<CommonAttachmentDO> dataList = commonAttachmentDAO.findCondition(queryDO,
				moduleTypeList, order.getSortCol(), order.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			
			List<CommonAttachmentInfo> list = result.getPageList();
			if (totalSize > 0) {
				for (CommonAttachmentDO DO : dataList) {
					CommonAttachmentInfo info = new CommonAttachmentInfo();
					BeanCopier.staticCopy(DO, info);
					info.setCheckstatus(CheckStatusEnum.getByCode(DO.getCheckStatus()));
					info.setModuleType(CommonAttachmentTypeEnum.getByCode(DO.getModuleType()));
					list.add(info);
				}
			}
			result.initPageParam(component);
			result.setPageList(list);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询附件出错：{}", e);
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult insert(CommonAttachmentOrder order) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		logger.info("进入插入单条图片信息");
		try {
			int count = 1;
			CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
			MiscUtil.copyPoObject(attachmentDO, order);
			//attachmentDO.setModuleType(order.getModuleType().getCode());
			attachmentDO.setRawAddTime(new Date());
			commonAttachmentDAO.insert(attachmentDO);
			StringBuilder sb = new StringBuilder();
			sb.append("插入完成,总计插入[");
			sb.append(count);
			sb.append("]行");
			logger.info(sb.toString());
			result.setSuccess(true);
			result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
			result.setMessage(sb.toString());
		} catch (DataAccessException e) {
			logger.error("插入图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("插入图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult insertAll(List<CommonAttachmentOrder> CommonAttachments) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		logger.info("进入插入多条图片信息");
		try {
			int count = 0;
			for (CommonAttachmentOrder order : CommonAttachments) {
				CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
				MiscUtil.copyPoObject(attachmentDO, order);
				attachmentDO.setModuleType(order.getModuleType().getCode());
				attachmentDO.setRawAddTime(new Date());
				commonAttachmentDAO.insert(attachmentDO);
				count++;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("插入完成,总计插入[");
			sb.append(count);
			sb.append("]行");
			logger.info(sb.toString());
			result.setSuccess(true);
			result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
			result.setMessage(sb.toString());
		} catch (DataAccessException e) {
			logger.error("插入多条图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("插入多条图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("插入多条图片信息失败");
		}
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deleteById(long)
	 */
	@Override
	public CommonAttachmentResult deleteById(long id) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				commonAttachmentDAO.deleteById(id);
				
				result.setSuccess(true);
				result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
				result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deleteByIdAllSame(long)
	 */
	@Override
	public CommonAttachmentResult deleteByIdAllSame(long id) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息(同时删除未审核的其他同地址图片信息)");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				long findCount = 0;
				long delCount = 0;
				commonAttachmentDAO.deleteById(id);
				delCount++;
				List<CommonAttachmentDO> pics = commonAttachmentDAO.findByPicpath(attachmentDO
					.getFilePhysicalPath());
				for (CommonAttachmentDO pic : pics) {
					if (StringUtil.isNotBlank(pic.getCheckStatus())
						&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
						findCount++;
					} else {
						commonAttachmentDAO.deleteById(pic.getAttachmentId());
						delCount++;
					}
				}
				
				StringBuilder sb = new StringBuilder();
				sb.append("删除完成,总计删除[");
				sb.append(delCount);
				sb.append("]行,找到[");
				sb.append(findCount);
				sb.append("]行,已审核过的数据!");
				logger.info(sb.toString());
				result.setSuccess(true);
				result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
				result.setMessage(sb.toString());
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param Id
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#findJoinApplyById(long)
	 */
	@Override
	public CommonAttachmentResult findById(long id) {
		
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入查询单条图片信息{}", id);
		try {
			CommonAttachmentDO tradeAttachmentDO = commonAttachmentDAO.findById(id);
			if (tradeAttachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				CommonAttachmentInfo attachmentInfo = new CommonAttachmentInfo();
				MiscUtil.copyPoObject(attachmentInfo, tradeAttachmentDO);
				if (StringUtil.isNotBlank(tradeAttachmentDO.getModuleType())) {
					attachmentInfo.setModuleType(CommonAttachmentTypeEnum
						.getByCode(tradeAttachmentDO.getModuleType()));
				}
				
				result.setAttachmentInfo(attachmentInfo);
				result.setSuccess(true);
				result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
				result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
			}
		} catch (DataAccessException e) {
			logger.error("查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("查询图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.yjf.estate.service.TradeAttachmentService#deletePicture(com.yjf.estate.service.order.CommonAttachmentDeleteOrder)
	 */
	@Override
	public CommonAttachmentResult deletePicture(CommonAttachmentDeleteOrder order) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除单条图片信息");
		try {
			order.check();
			if (order.getAttachmentId() > 0) {
				CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(order
					.getAttachmentId());
				if (attachmentDO == null || !attachmentDO.getBizNo().equals(order.getBizNo())) {
					result.setSuccess(false);
					result.setMessage("无效的图片id");
				} else {
					long findCount = 0;
					long delCount = 0;
					commonAttachmentDAO.deleteById(order.getAttachmentId());
					delCount++;
					List<CommonAttachmentDO> pics = commonAttachmentDAO.findByPicpath(attachmentDO
						.getFilePhysicalPath());
					for (CommonAttachmentDO pic : pics) {
						if (StringUtil.isNotBlank(pic.getCheckStatus())
							&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
							findCount++;
						} else {
							commonAttachmentDAO.deleteById(pic.getAttachmentId());
							delCount++;
						}
					}
					
					StringBuilder sb = new StringBuilder();
					sb.append("删除完成,总计删除[");
					sb.append(delCount);
					sb.append("]行,找到[");
					sb.append(findCount);
					sb.append("]行,已审核过的数据!");
					logger.info(sb.toString());
					result.setSuccess(true);
					result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
					result.setMessage(sb.toString());
				}
			} else {
				if (order.getFilePhysicalPath() != null) {
					long findCount = 0;
					long delCount = 0;
					List<CommonAttachmentDO> attachmentDOs = commonAttachmentDAO
						.findByPicpath(order.getFilePhysicalPath());
					for (CommonAttachmentDO pic : attachmentDOs) {
						if (StringUtil.isNotBlank(pic.getCheckStatus())
							&& CheckStatusEnum.CHECK_PASS.code().equals(pic.getCheckStatus())) {
							findCount++;
						} else {
							commonAttachmentDAO.deleteById(pic.getAttachmentId());
							delCount++;
						}
					}
					
					StringBuilder sb = new StringBuilder();
					sb.append("删除完成,总计删除[");
					sb.append(delCount);
					sb.append("]行,找到[");
					sb.append(findCount);
					sb.append("]行,已审核过的数据!");
					logger.info(sb.toString());
					result.setSuccess(true);
					result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
					result.setMessage(sb.toString());
				}
			}
		} catch (DataAccessException e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("删除图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public CommonAttachmentResult deleteByBizNoModuleType(String bizNo,
															CommonAttachmentTypeEnum... types) {
		CommonAttachmentResult result = new CommonAttachmentResult();
		logger.info("进入删除图片信息[{}], [{}]", bizNo, types);
		if (null != types && types.length > 0) {
			for (CommonAttachmentTypeEnum type : types) {
				if (null != type) {
					commonAttachmentDAO.deleteByBizNoModuleType(bizNo, type.code());
				}
			}
		}
		result.setSuccess(true);
		result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
		return result;
	}
	
	@Override
	public InsuranceBaseResult updateStatusById(long id, CheckStatusEnum status) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		logger.info("进入更新图片信息");
		try {
			CommonAttachmentDO attachmentDO = commonAttachmentDAO.findById(id);
			if (attachmentDO == null) {
				result.setSuccess(false);
				result.setMessage("无效的图片id");
			} else {
				attachmentDO.setCheckStatus(status.code());
				commonAttachmentDAO.update(attachmentDO);
				StringBuilder sb = new StringBuilder();
				sb.append("更新完成,将id为[");
				sb.append(id);
				sb.append("]更新为[");
				sb.append(status);
				sb.append("]状态");
				logger.info(sb.toString());
				result.setSuccess(true);
				result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
				result.setMessage(sb.toString());
			}
		} catch (DataAccessException e) {
			logger.error("更新图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult updateStatus(String bizNo, CommonAttachmentTypeEnum moduleType,
											CheckStatusEnum status) {
		
		InsuranceBaseResult result = new InsuranceBaseResult();
		StringBuilder sb1 = new StringBuilder();
		sb1.append("入参:bizNo=[");
		sb1.append(bizNo);
		sb1.append("],moduleType=[");
		sb1.append(moduleType);
		sb1.append("],status=[");
		sb1.append(status);
		sb1.append("].");
		if (StringUtil.isBlank(bizNo) || moduleType == null || status == null) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("请求参数不完整,参数全部必传");
			sb1.append("进入更新图片状态信息,请求参数不完整");
			logger.error(sb1.toString());
			return result;
		}
		logger.info(sb1.toString());
		try {
			commonAttachmentDAO.updateStatusAllSame(status.code(), bizNo, moduleType.code());
			result.setSuccess(true);
			result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
			result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (DataAccessException e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult deleteByBizNoAndChildIdModuleType(	String bizNo,
																	String childId,
																	CommonAttachmentTypeEnum... moduleType) {
		logger
			.error(
				"进入CommonAttachmentServiceImpl的deleteByBizNoAndChildIdModuleType【3参数必传】，入参：bizNo={},childId={},moduleType={}",
				bizNo, childId, moduleType);
		InsuranceBaseResult result = new InsuranceBaseResult();
		try {
			
			if (StringUtil.isBlank(bizNo) || StringUtil.isBlank(childId) || moduleType == null) {
				result.setSuccess(false);
				result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("入参不能为空！");
			}
			for (CommonAttachmentTypeEnum type : moduleType) {
				commonAttachmentDAO.deleteByBizNoAndChildIdModuleType(bizNo, type.code(), childId);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除附件异常");
		}
		
		return result;
	}
	
	@Override
	public InsuranceBaseResult addNewDelOldByMoudleAndBizNo(final CommonAttachmentBatchOrder order) {
		
		logger.info("开始添加附件：{}", order);
		
		return transactionTemplate.execute(new TransactionCallback<InsuranceBaseResult>() {
			@Override
			public InsuranceBaseResult doInTransaction(TransactionStatus status) {
				InsuranceBaseResult result = new InsuranceBaseResult();
				try {
					try {
						order.check();
					} catch (IllegalArgumentException e) {
						result.setSuccess(false);
						result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
						result.setMessage(e.getMessage());
					}
					
					//先删除，再保存
					if (StringUtil.isNotBlank(order.getChildId())) {
						commonAttachmentDAO.deleteByBizNoAndChildIdModuleType(order.getBizNo(),
							order.getModuleType().code(), order.getChildId());
					} else {
						commonAttachmentDAO.deleteByBizNoModuleType(order.getBizNo(), order
							.getModuleType().code());
					}
					
					String[] attachPaths = order.getPath().split(";");
					int j = 1;
					for (String path : attachPaths) {
						String paths[] = path.split(",");
						if (null != paths && paths.length >= 3) {
							CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
							MiscUtil.copyPoObject(attachmentDO, order);
							attachmentDO.setModuleType(order.getModuleType().code());
							if (StringUtil.isBlank(order.getRemark())) {
								attachmentDO.setRemark(order.getModuleType().message());
							}
							attachmentDO.setRawAddTime(new Date());
							attachmentDO.setIsort(j++);
							attachmentDO.setFileName(paths[0]);
							attachmentDO.setFilePhysicalPath(paths[1]);
							attachmentDO.setRequestPath(paths[2]);
							commonAttachmentDAO.insert(attachmentDO);
						}
					}
					result.setSuccess(true);
				} catch (Exception e) {
					result.setSuccess(false);
					result.setMessage("添加附件失败");
					logger.error("添加附件失败,{}", e.getMessage(), e);
				}
				return result;
			}
		});
	}
	
	@Override
	public InsuranceBaseResult updateStatusCondition(CheckStatusEnum checkStatus,
														CommonAttachmentOrder order) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		
		if (order == null || order.isConditionNull()) {
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("更新条件不能全为空");
			logger.error("更新条件不能全为空");
			return result;
		}
		
		logger.info("开始更新附件状态：{}", order);
		try {
			CommonAttachmentDO condition = new CommonAttachmentDO();
			BeanCopier.staticCopy(order, condition);
			if (order.getModuleType() != null)
				condition.setModuleType(order.getModuleType().code());
			commonAttachmentDAO.updateStatusCondition(condition);
			result.setSuccess(true);
			result.setInsuranceResultEnum(InsuranceResultEnum.EXECUTE_SUCCESS);
			result.setMessage(InsuranceResultEnum.EXECUTE_SUCCESS.getMessage());
		} catch (DataAccessException e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
			result.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			result.setMessage(InsuranceResultEnum.DATABASE_EXCEPTION.getMessage());
		} catch (Exception e) {
			logger.error("更新图片状态信息失败,{}", e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
}