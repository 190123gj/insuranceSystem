package com.born.insurance.biz.service.projectSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.dal.daointerface.ProjectSetupCatalogDAO;
import com.born.insurance.dal.daointerface.ProjectSetupCustomerDAO;
import com.born.insurance.dal.daointerface.ProjectSetupDAO;
import com.born.insurance.dal.dataobject.InsuranceLiabilityDO;
import com.born.insurance.dal.dataobject.ProjectSetupCatalogDO;
import com.born.insurance.dal.dataobject.ProjectSetupCustomerDO;
import com.born.insurance.dal.dataobject.ProjectSetupDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dataobject.ProjectSetupExtraDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupFormInfo;
import com.born.insurance.ws.info.projectSetup.ProjectSetupInfo;
import com.born.insurance.ws.order.insuranceCatalog.InsuranceCatalogOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupOrder;
import com.born.insurance.ws.order.projectSetup.ProjectSetupQueryOrder;
import com.born.insurance.ws.result.base.FormBaseResult;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.FormService;
import com.born.insurance.ws.service.projectSetup.ProjectSetupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("projectSetupService")
public class ProjectSetupServiceImpl extends BaseFormAutowiredDomainService implements
																		ProjectSetupService {
	@Autowired
	protected ProjectSetupDAO projectSetupDAO;
	
	@Autowired
	protected ProjectSetupCustomerDAO projectSetupCustomerDAO;
	
	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected ProjectSetupCatalogDAO  projectSetupCatalogDAO;
	
	@Autowired
	protected FormService formService;
	
	@Override
	public FormBaseResult save(final ProjectSetupOrder order) {
		return commonFormSaveProcess(order, "超权限增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
				
				//取得表单信息
				FormInfo form = (FormInfo) InsuranceDomainHolder.get().getAttribute("form");
				if (form == null) {
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"获取表单信息出错");
				}
				String id = order.getProjectSetupId();
				ProjectSetupDO doObj = new ProjectSetupDO();
				if (StringUtils.isEmpty(id) || id.equals("0")) {
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());
					doObj.setProportion(!StringUtils.isBlank(order.getProportion()) ? Double.valueOf(order.getProportion()) : 0);
					doObj.setBeginDate(DateUtil.parse(order.getBeginDate()));
					doObj.setEndDate(DateUtil.parse(order.getEndDate()));
					doObj.setRawAddTime(now);
					projectSetupDAO.insert(doObj);
					id = String.valueOf(doObj.getProjectSetupId());
				} else {
					doObj = projectSetupDAO.findById(Integer.parseInt(order.getProjectSetupId()));
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setProjectSetupId(Integer.parseInt(order.getProjectSetupId()));
					doObj.setProportion(Double.valueOf(order.getProportion()));
					doObj.setBeginDate(DateUtil.parse(order.getBeginDate()));
					doObj.setEndDate(DateUtil.parse(order.getEndDate()));
					projectSetupDAO.update(doObj);
					projectSetupCatalogDAO.deleteByProjectSetupId(doObj.getProjectSetupId());
					projectSetupCustomerDAO.deleteByProjectSetupId(doObj.getProjectSetupId());
				}
				
				//该特殊权限 与 客户的使用关系
				String customerUserNames = doObj.getCustomerUserName();
				if (!StringUtils.isBlank(customerUserNames)) {
					List<ProjectSetupCustomerDO> projectSetupCustomerDOs = JSONObject.parseArray(customerUserNames, ProjectSetupCustomerDO.class);
					if (null != projectSetupCustomerDOs && projectSetupCustomerDOs.size() > 0) {
						for (ProjectSetupCustomerDO projectSetupCustomerDO : projectSetupCustomerDOs) {
							projectSetupCustomerDO.setProjectSetupId(doObj.getProjectSetupId());
							projectSetupCustomerDAO.insert(projectSetupCustomerDO);
						}
					}
				}
				
				//该特殊权限与险种的关系
				List<InsuranceCatalogOrder> bankOrders = order.getBankOrders();
				if (null != bankOrders && bankOrders.size() > 0 ) {
					for (InsuranceCatalogOrder CcatalogDO : bankOrders) {
						//添加特与保险对应的关系
						ProjectSetupCatalogDO projectSetupCatalogDO = new ProjectSetupCatalogDO();
						projectSetupCatalogDO.setProjectSetupId(doObj.getProjectSetupId());
						projectSetupCatalogDO.setCatalogId(CcatalogDO.getCatalogId());
						projectSetupCatalogDO.setCatalogName(CcatalogDO.getCatalogName());
						projectSetupCatalogDAO.insert(projectSetupCatalogDO);
					}
				}
				
				
				InsuranceBaseResult result = (InsuranceBaseResult) InsuranceDomainHolder.get().getAttribute("result");
				result.setKeyId(Integer.parseInt(id));
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ProjectSetupInfo findById(long id) {
		ProjectSetupDO projectSetupDo = projectSetupDAO.findById(id);
		if (projectSetupDo != null) {
			ProjectSetupInfo projectSetupInfo = new ProjectSetupInfo();
			BeanCopier.staticCopy(projectSetupDo, projectSetupInfo);
			return projectSetupInfo;
		}
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectSetupFormInfo> queryList(ProjectSetupQueryOrder queryOrder) {
		QueryBaseBatchResult<ProjectSetupFormInfo> batchResult = new QueryBaseBatchResult<ProjectSetupFormInfo>();
		
		try {
			queryOrder.check();
			List<ProjectSetupFormInfo> pageList = new ArrayList<ProjectSetupFormInfo>(
				(int) queryOrder.getPageSize());
			
			 ProjectSetupExtraDO  projectSetupExtraDO= new ProjectSetupExtraDO();
			BeanCopier.staticCopy(queryOrder, projectSetupExtraDO);
			long totalCount = extraDAO.findProjectSetupByConditionCount(projectSetupExtraDO,queryOrder.getCurrentSystemTime());
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<ProjectSetupExtraDO> recordList = extraDAO.findProjectSetupByCondition(projectSetupExtraDO,queryOrder.getCurrentSystemTime(),
				queryOrder.getSortCol(), queryOrder.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			for (ProjectSetupExtraDO item : recordList) {
				ProjectSetupFormInfo info = new ProjectSetupFormInfo();
				BeanCopier.staticCopy(item, info);
				info.setFormCode(FormCodeEnum.getByCode(item.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				pageList.add(info);
			}
			batchResult.initPageParam(component);
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
		} catch (IllegalArgumentException e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.INCOMPLETE_REQ_PARAM);
			batchResult.setMessage(e.getMessage());
		} catch (Exception e) {
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return batchResult;
	}

	@Override
	public ProjectSetupInfo findByFormId(long formId) {
		ProjectSetupDO projectSetupDO = projectSetupDAO.findByFormId(formId);
		if (null != projectSetupDO) {
			ProjectSetupInfo setUpInfo = new ProjectSetupInfo();
			BeanCopier.staticCopy(projectSetupDO, setUpInfo);
			return setUpInfo;
		}
		return null;
	}

	@Override
	public InsuranceBaseResult deleteProjectSetup(final ProjectSetupOrder order) {
		if (null == order || StringUtils.isBlank(order.getProjectSetupId())) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.WRONG_REQ_PARAM, "参数不能为空");
		}
		InsuranceBaseResult result = new InsuranceBaseResult();
		Integer projectSetUpId = Integer.parseInt(order.getProjectSetupId());
		ProjectSetupDO projectSetupDO = projectSetupDAO.findById(Integer.parseInt(order.getProjectSetupId()));
		if (null == projectSetupDO) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA, "该超权限申请不存在");
		}
		try {
			projectSetupDAO.deleteById(projectSetUpId);
			projectSetupCustomerDAO.deleteByProjectSetupId(projectSetUpId);
			projectSetupCatalogDAO.deleteByProjectSetupId(projectSetUpId);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			result.setMessage("删除超权限审批失败");
			logger.error("错误信息:",e.getMessage());
		} catch (NumberFormatException e) {
			result.setMessage("删除超权限审批失败");
			logger.error("错误信息:",e.getMessage());
		}
		return result;
	}

	@Override
	public InsuranceBaseResult revoke(ProjectSetupOrder order) {
		InsuranceBaseResult result = new InsuranceBaseResult();
		if (null == order || StringUtils.isBlank(order.getProjectSetupId())) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.WRONG_REQ_PARAM, "参数不能为空");
		}
		Integer projectSetUpId = Integer.parseInt(order.getProjectSetupId());
		ProjectSetupDO projectSetupDO = projectSetupDAO.findById(projectSetUpId);
		if (null == projectSetupDO) {
			throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA, "该超权限申请不存在");
		}
		long formId = projectSetupDO.getFormId();
		FormInfo formInfo = formService.findByFormId(formId);
		formInfo.setSubmitTime(null);
		formInfo.setRunId(0);
		formInfo.setDefId(0);
		formInfo.setActDefId("0");
		formInfo.setActDefId("0");
		formInfo.setStatus(FormStatusEnum.DRAFT);
		try {
			formService.updateForm(formInfo);
			result.setSuccess(true);
			result.setMessage("撤销成功");
		} catch (Exception e) {
			
		}
		return result;
	}
	
}