package com.born.insurance.biz.service.personCommissionDetail;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.dataobject.PersonCommissionDetailDO;
import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.dal.daointerface.PersonCommissionDetailDAO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.personCommissionDetail.PersonCommissionDetailInfo;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailOrder;
import com.born.insurance.ws.order.personCommissionDetail.PersonCommissionDetailQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.personCommissionDetail.PersonCommissionDetailService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("personCommissionDetailService")
public class PersonCommissionDetailServiceImpl extends BaseAutowiredDomainService implements PersonCommissionDetailService {
	@Autowired
	protected PersonCommissionDetailDAO personCommissionDetailDAO;
	
	@Autowired
	protected ExtraDAO extraDAO;

	@Override
	public InsuranceBaseResult save(final PersonCommissionDetailOrder order) {
		return commonProcess(order, "增加或修改信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				final Date now = InsuranceDomainHolder.get().getSysDate();
		
				long id = order.getSettlementPersonId();
				if (id <= 0) {
					PersonCommissionDetailDO doObj = new PersonCommissionDetailDO();
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
						
					personCommissionDetailDAO.insert(doObj);
				} else {
					PersonCommissionDetailDO doObj = personCommissionDetailDAO.findById(order.getSettlementPersonId());
					if (null == doObj) {
						throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
							"风险预警信息不存在");
					}
					BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());
					
					personCommissionDetailDAO.update(doObj);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public PersonCommissionDetailInfo findById(long id) {
		PersonCommissionDetailDO personCommissionDetailDo= personCommissionDetailDAO.findById(id);
        if(personCommissionDetailDo != null){
            PersonCommissionDetailInfo personCommissionDetailInfo = new PersonCommissionDetailInfo();
            BeanCopier.staticCopy(personCommissionDetailDo,personCommissionDetailInfo);
            return personCommissionDetailInfo;
        }
		return null;
	}
	
	@Override
	public QueryBaseBatchResult<PersonCommissionDetailInfo> queryList(PersonCommissionDetailQueryOrder queryOrder) {
		QueryBaseBatchResult<PersonCommissionDetailInfo> batchResult = new QueryBaseBatchResult<PersonCommissionDetailInfo>();
		
		try {
			queryOrder.check();
			List<PersonCommissionDetailInfo> pageList = new ArrayList<PersonCommissionDetailInfo>((int) queryOrder.getPageSize());
			
			PersonCommissionDetailDO personCommissionDetailDO = new PersonCommissionDetailDO();
			BeanCopier.staticCopy(queryOrder, personCommissionDetailDO);
			queryOrder.setSortCol("raw_add_time");
			queryOrder.setSortOrder("DESC");
			long totalCount = personCommissionDetailDAO.findByConditionCount(personCommissionDetailDO,queryOrder.getBusinessUserId(),DateUtil.string2DateTimeByAutoZero(queryOrder.getBeginDate()),DateUtil.string2DateTimeBy23(queryOrder.getEndDate()));
			PageComponent component = new PageComponent(queryOrder, totalCount);
			List<PersonCommissionDetailDO> recordList = personCommissionDetailDAO.findByCondition(personCommissionDetailDO, queryOrder.getSortCol(),
				queryOrder.getSortOrder(), component.getFirstRecord(), component.getPageSize(),queryOrder.getBusinessUserId(),DateUtil.string2DateTimeByAutoZero(queryOrder.getBeginDate()),DateUtil.string2DateTimeBy23(queryOrder.getEndDate()));
			for (PersonCommissionDetailDO item : recordList) {
				PersonCommissionDetailInfo info = new PersonCommissionDetailInfo();
				BeanCopier.staticCopy(item, info);
				info.setCommissionTime(DateUtil.simpleFormatYmdhms(item.getCommissionTime()));
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
	public Money getInComeTotalSum(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) throws Exception {
		Date beginDate = DateUtil.string2DateTimeByAutoZero(personCommissionDetailQueryOrder.getBeginDate());
		Date endDate = DateUtil.string2DateTimeBy23(personCommissionDetailQueryOrder.getEndDate());
		long businessUserId = personCommissionDetailQueryOrder.getBusinessUserId();
		try {
			String inComeTotalSum = extraDAO.getInComeTotalSum(businessUserId, beginDate,endDate);
			return Money.amout(inComeTotalSum).divide(BigDecimal.valueOf(100));
		} catch (DataAccessException e) {
			logger.error("业务员佣金收入统计查询错误：", e.getMessage());
		}
		return new Money(0,0);
	}

	@Override
	public Money getExtractTotalSum(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) throws Exception {
		Date beginDate = DateUtil.string2DateTimeByAutoZero(personCommissionDetailQueryOrder.getBeginDate());
		Date endDate = DateUtil.string2DateTimeBy23(personCommissionDetailQueryOrder.getEndDate());
		long businessUserId = personCommissionDetailQueryOrder.getBusinessUserId();
		 
		try {
			String extractTotalSum = extraDAO.getExtractTotalSum(businessUserId, beginDate,endDate);
			return Money.amout(extractTotalSum).divide(BigDecimal.valueOf(100));
		} catch (DataAccessException e) {
			logger.error("业务员佣金收入统计查询错误：", e.getMessage());
		}
		return new Money(0,0);
	}

	@Override
	public Money getLastTotal(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) throws Exception {
		Date beginDate = DateUtil.string2DateTimeByAutoZero(personCommissionDetailQueryOrder.getBeginDate());
		long businessUserId = personCommissionDetailQueryOrder.getBusinessUserId();
		 
		try {
			String lastTotal = extraDAO.getLastTotal(businessUserId, beginDate);
			return Money.amout(lastTotal).divide(BigDecimal.valueOf(100));
		} catch (DataAccessException e) {
			logger.error("业务员佣金收入统计查询错误：", e.getMessage());
		}
		return new Money(0,0);
	}

	@Override
	public List<String> getXAxisData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder) {
		List<String> list = null ;
		try {
			list = extraDAO.getXAxisData(personCommissionDetailQueryOrder);
		} catch (Exception e) {
			logger.error("佣金明细日期查询错误：", e.getMessage());
		}
		return list;
	}

	@Override
	public List<BigDecimal> getPositiveData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder, List<String> xAxisData) {
		List<BigDecimal> list = null ;
		 try {
			list = extraDAO.getPositiveData(personCommissionDetailQueryOrder,xAxisData);
		} catch (Exception e) {
			logger.error("佣金收入明细查询错误：", e.getMessage());
		}
		return list;
	}

	@Override
	public List<BigDecimal>  getNegativexData(PersonCommissionDetailQueryOrder personCommissionDetailQueryOrder, List<String> xAxisData) {
		List<BigDecimal> list  = null ;
		try {
			list = extraDAO.getNegativexData(personCommissionDetailQueryOrder,xAxisData);
		} catch (Exception e) {
			logger.error("佣金提取查询明细错误:", e.getMessage());
		}
		return list;
	}

	@Override
	public List<PersonCommissionDetailInfo> getPersonCommissionDetail(
			PersonCommissionDetailOrder personCommissionDetailOrder) {
		
		List<PersonCommissionDetailInfo>  pageList = new ArrayList<PersonCommissionDetailInfo>();
		List<PersonCommissionDetailDO> list = personCommissionDetailDAO.getPersonCommissionDetail(personCommissionDetailOrder.getBusinessUserId(),personCommissionDetailOrder.getCommissionType(),personCommissionDetailOrder.getCommissionTime());
		for (PersonCommissionDetailDO item : list) {
			PersonCommissionDetailInfo info = new PersonCommissionDetailInfo();
			BeanCopier.staticCopy(item, info);
			info.setCommissionTime(DateUtil.format(item.getCommissionTime(), DateUtil.dtSimpleYmdhms));
			pageList.add(info);
		}
		return pageList;
	}
}