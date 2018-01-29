package com.born.insurance.biz.service.billSettlementApply;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseFormAutowiredDomainService;
import com.born.insurance.dal.daointerface.BillPayFeeDetailDAO;
import com.born.insurance.dal.daointerface.BillSettlementApplyDAO;
import com.born.insurance.dal.daointerface.BillSettlementApplyDetailDAO;
import com.born.insurance.dal.dataobject.BillPayFeeDetailDO;
import com.born.insurance.dal.dataobject.BillSettlementApplyDO;
import com.born.insurance.dal.dataobject.BillSettlementApplyDetailDO;
import com.born.insurance.dataobject.BillSettlementApplyFormDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.FormCodeEnum;
import com.born.insurance.ws.enums.FormStatusEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.billSettlementApply.BillSettlementApplyFormInfo;
import com.born.insurance.ws.info.billSettlementApply.BillSettlementApplyInfo;
import com.born.insurance.ws.info.common.FormInfo;
import com.born.insurance.ws.order.billPayFeeDetail.BillPayFeeDetailOrder;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyOrder;
import com.born.insurance.ws.order.billSettlementApply.BillSettlementApplyQueryOrder;
import com.born.insurance.ws.order.billSettlementApplyDetail.BillSettlementApplyDetailOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.billSettlementApply.BillSettlementApplyService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("billSettlementApplyService")
public class BillSettlementApplyServiceImpl extends BaseFormAutowiredDomainService implements BillSettlementApplyService {
	@Autowired
	protected BillSettlementApplyDAO billSettlementApplyDAO;
	@Autowired
	protected BillSettlementApplyDetailDAO billSettlementApplyDetailDAO;
	@Autowired
	protected BillPayFeeDetailDAO billPayFeeDetailDAO;

	@Override
	public InsuranceBaseResult save(final BillSettlementApplyOrder order) {
		return commonFormSaveProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
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
		
				long id = order.getId();
				if (id <= 0) {
					BillSettlementApplyDO doObj = new BillSettlementApplyDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setFormId(form.getFormId());	
					doObj.setRowAddTime(now);
					billSettlementApplyDAO.insert(doObj);
					order.setFormId(form.getFormId());
					order.setId(doObj.getId());
				} else {
					BillSettlementApplyDO doObj = billSettlementApplyDAO.findById(order.getId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					doObj.setBrokerRank(order.getBrokerRank());
					billSettlementApplyDAO.update(doObj);
					BillSettlementApplyDetailDO billSettlementApplyDetailDO = billSettlementApplyDetailDAO.findBySettlementApplyId(order.getId());
					if (null != billSettlementApplyDetailDO) {
						BillSettlementApplyDetailOrder billSettlementApplyDetailOrder = order.getBillSettlementApplyDetailOrder();
						billSettlementApplyDetailDO.setActualPayFee(billSettlementApplyDetailOrder.getActualPayFee());
						billSettlementApplyDetailDO.setGenerationFee(billSettlementApplyDetailOrder.getGenerationFee());
						billSettlementApplyDetailDO.setIndividualTax(billSettlementApplyDetailOrder.getIndividualTax());
						billSettlementApplyDetailDO.setPayFee(billSettlementApplyDetailOrder.getPayFee());
						billSettlementApplyDetailDO.setRecievedFee(billSettlementApplyDetailOrder.getRecievedFee());
						billSettlementApplyDetailDO.setRecievableFee(billSettlementApplyDetailOrder.getRecievableFee());
						billSettlementApplyDetailDO.setShouldPayFee(billSettlementApplyDetailOrder.getShouldPayFee());
						billSettlementApplyDetailDO.setServiceFee(billSettlementApplyDetailOrder.getServiceFee());
						billSettlementApplyDetailDAO.update(billSettlementApplyDetailDO);
					}
					if (ListUtil.isNotEmpty(order.getBillPayFeeDetails())) {
						billPayFeeDetailDAO.deleteBysettlementApplyId(order.getId());
						for (BillPayFeeDetailOrder details : order.getBillPayFeeDetails()) {
							BillPayFeeDetailDO 	billPayFeeDetailDO = new BillPayFeeDetailDO();
							billPayFeeDetailDO.setBusinessBillId(Integer.parseInt(order.getBusinessBillId()));
							billPayFeeDetailDO.setSettlementApplyId(order.getId());
							billPayFeeDetailDO.setActualPayFee(details.getActualPayFee());
							billPayFeeDetailDO.setGenerationFee(details.getGenerationFee());
							billPayFeeDetailDO.setServiceFee(details.getServiceFee());
							billPayFeeDetailDO.setReceiverName(details.getReceiverName());
							billPayFeeDetailDO.setInsuranceBrokerNo(details.getInsuranceBrokerNo());
							billPayFeeDetailDO.setWithholdTaxes(details.getWithholdTaxes());
							billPayFeeDetailDO.setPayFee(details.getPayFee());
							billPayFeeDetailDO.setRowAddTime(new Date());
							billPayFeeDetailDAO.insert(billPayFeeDetailDO);
						}
						}
					}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public BillSettlementApplyInfo findById(long id) {
		BillSettlementApplyDO billSettlementApplyDo= billSettlementApplyDAO.findById(id);
        if(billSettlementApplyDo != null){
            BillSettlementApplyInfo billSettlementApplyInfo = new BillSettlementApplyInfo();
            BeanCopier.staticCopy(billSettlementApplyDo,billSettlementApplyInfo);
            return billSettlementApplyInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<BillSettlementApplyFormInfo> queryList(BillSettlementApplyQueryOrder queryOrder) {
		QueryBaseBatchResult<BillSettlementApplyFormInfo> batchResult = new QueryBaseBatchResult<BillSettlementApplyFormInfo>();
		
		try {
			queryOrder.check();
			List<BillSettlementApplyFormInfo> pageList = new ArrayList<BillSettlementApplyFormInfo>((int) queryOrder.getPageSize());
			
			BillSettlementApplyFormDO billSettlementApplyDO = new BillSettlementApplyFormDO();
			BeanCopier.staticCopy(queryOrder, billSettlementApplyDO);
			long totalCount = extraDAO.findByConditionCount(billSettlementApplyDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<BillSettlementApplyFormDO> recordList = extraDAO.findByCondition(billSettlementApplyDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (BillSettlementApplyFormDO item : recordList) {
				BillSettlementApplyFormInfo info = new BillSettlementApplyFormInfo();
				info.setFormCode(FormCodeEnum.getByCode(item.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				BeanCopier.staticCopy(item, info);
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
	public BillSettlementApplyInfo findByFormId(long formId) {
		BillSettlementApplyInfo info = new BillSettlementApplyInfo();
		BillSettlementApplyDO billSettlementApplyDO = billSettlementApplyDAO.findByFormId(formId);
		if (null != billSettlementApplyDO) {
			BeanCopier.staticCopy(billSettlementApplyDO, info);
			return info ;
		}
		return null;
	}
}