package com.born.insurance.biz.service.personCommissionProcess;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PersonCommissionDO;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.dal.dataobject.PersonCommissionProcessDO;
import com.born.insurance.dal.daointerface.PersonCommissionDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDetailDAO;
import com.born.insurance.dal.daointerface.PersonCommissionProcessDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.util.StringUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.CommissionTypeEnum;
import com.born.insurance.ws.enums.SettlementStatusEnum;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.personCommissionProcess.PersonCommissionProcessInfo;
import com.born.insurance.ws.order.insuranceProduct.InsuranceProductOrder;
import com.born.insurance.ws.order.personCommissionProcess.PersonCommissionProcessOrder;
import com.born.insurance.ws.order.personCommissionProcess.PersonCommissionProcessQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.personCommissionProcess.PersonCommissionProcessService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

import rop.thirdparty.org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.biz.util.PinyinUtil;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("personCommissionProcessService")
public class PersonCommissionProcessServiceImpl extends BaseAutowiredDomainService implements PersonCommissionProcessService {
	@Autowired
	protected PersonCommissionProcessDAO personCommissionProcessDAO;
	
	@Autowired
	protected PersonCommissionDAO personCommissionDAO;
	
	@Autowired
	protected PersonCommissionDetailDAO personCommissionDetailDAO;

	@Override
	public InsuranceBaseResult save(final PersonCommissionProcessOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				String ids = order.getCommissionProcessIds();
				if (StringUtils.isBlank(ids)) {
					PersonCommissionProcessDO doObj = new PersonCommissionProcessDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					doObj.setRawAddTime(now);
					personCommissionProcessDAO.insert(doObj);
				} else {
					//生成结算单号
					//处理佣金结算申请
					personCommissionProcessDAO.updateStatus(order.getCommissionProcessIds(),genSettleNo(),order.getStatus(),order.getReason());
						//变更业务员结算信息 和 申请
						//查询该业务员的佣金结算信息
						String businessUserId = !StringUtils.isBlank(order.getBusinessUserId()) ?order.getBusinessUserId():"0";
						PersonCommissionDO personCommissionDO = personCommissionDAO.findByUserId(Long.valueOf(businessUserId));
						Money applyAmount = order.getApplyDrawAmount();
						personCommissionDO.setApplyingAmount(personCommissionDO.getApplyingAmount().subtract(applyAmount));
						if (order.getStatus().equals(SettlementStatusEnum.SUCCESS.getCode())) {
							personCommissionDO.setDrawAmount(personCommissionDO.getDrawAmount().add(applyAmount));
						}
						personCommissionDAO.update(personCommissionDO);
						
						//新增业务员佣金明细
						PersonCommissionDetailDO  personCommissionDetailDO = new PersonCommissionDetailDO();
						personCommissionDetailDO.setCommissionTime(now);
						personCommissionDetailDO.setBalance(personCommissionDO.getTotalAmount().subtract(personCommissionDO.getDrawAmount()));
						personCommissionDetailDO.setBusinessUserId(personCommissionDO.getBusinessUserId());
						personCommissionDetailDO.setBusinessUserName(personCommissionDO.getBusinessUserName());
						personCommissionDetailDO.setBusinessUserType(personCommissionDO.getBusinessUserType());
						personCommissionDetailDO.setCommissionType(CommissionTypeEnum.EXPENDITURE.getCode());
						personCommissionDetailDO.setCommissionAmount(applyAmount);
						personCommissionDetailDO.setSerialNumber("");
						personCommissionDetailDO.setRawAddTime(now);
						personCommissionDetailDO.setRemark("佣金提取");
						personCommissionDetailDAO.insert(personCommissionDetailDO);
					
				}
				return null;
			}
		}, null, null);
	}
	

	/**
	 * 业务单号
	 * @return
	 */
	private synchronized String genSettleNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String m = String.valueOf(month);
		String d = String.valueOf(day);
		String billNo = "JJFJS";
		Pattern pattern = Pattern.compile("^[\\d]$");
		Matcher match = pattern.matcher(String.valueOf(month));
		Matcher match2 = pattern.matcher(String.valueOf(day));
		if (match.find()) {
			m = "0"+month;
		}
		if (match2.find()) {
			d = "0"+day;
		}
		String str= billNo+year+ m + d;
		String seqName = SysDateSeqNameEnum.INSURANCE_CONTACT_LETTER.code() + "-" + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		str += StringUtil.leftPad(String.valueOf(seq), 4, "0");
		return str;
	}
	
	@Override
	public PersonCommissionProcessInfo findById(long id) {
		PersonCommissionProcessDO personCommissionProcessDo= personCommissionProcessDAO.findById(id);
        if(personCommissionProcessDo != null){
            PersonCommissionProcessInfo personCommissionProcessInfo = new PersonCommissionProcessInfo();
            BeanCopier.staticCopy(personCommissionProcessDo,personCommissionProcessInfo);
            return personCommissionProcessInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PersonCommissionProcessInfo> queryList(PersonCommissionProcessQueryOrder queryOrder) {
		QueryBaseBatchResult<PersonCommissionProcessInfo> batchResult = new QueryBaseBatchResult<PersonCommissionProcessInfo>();
		
		try {
			queryOrder.check();
			List<PersonCommissionProcessInfo> pageList = new ArrayList<PersonCommissionProcessInfo>((int) queryOrder.getPageSize());
			
			PersonCommissionProcessDO personCommissionProcessDO = new PersonCommissionProcessDO();
			BeanCopier.staticCopy(queryOrder, personCommissionProcessDO);
			long totalCount = personCommissionProcessDAO.findByConditionCount(personCommissionProcessDO);
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PersonCommissionProcessDO> recordList = personCommissionProcessDAO.findByCondition(personCommissionProcessDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			for (PersonCommissionProcessDO item : recordList) {
				PersonCommissionProcessInfo info = new PersonCommissionProcessInfo();
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
}