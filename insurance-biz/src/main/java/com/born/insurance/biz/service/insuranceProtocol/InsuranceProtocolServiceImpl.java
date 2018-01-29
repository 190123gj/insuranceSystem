package com.born.insurance.biz.service.insuranceProtocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.insurance.biz.convert.UnBoxingConverter;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.dal.daointerface.*;
import com.born.insurance.dal.dataobject.*;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.util.BusinessNumberUtil;
import com.born.insurance.util.NumberUtil;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.SalesAreaTypeEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.CityInfo;
import com.born.insurance.ws.info.common.ProvinceInfo;
import com.born.insurance.ws.info.common.RegionInfo;
import com.born.insurance.ws.info.insuranceProduct.InsuranceProductInfo;
import com.born.insurance.ws.order.insuranceProtocol.ProtocolChargeQueryOrder;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoOrder;
import com.born.insurance.ws.order.insuranceProtocolInfo.InsuranceProtocolInfoQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.info.insuranceProtocol.InsuranceProtocolInfo;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolOrder;
import com.born.insurance.ws.order.insuranceProtocol.InsuranceProtocolQueryOrder;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.BasicDataCacheService;
import com.born.insurance.ws.service.insuranceProtocol.InsuranceProtocolService;
import com.born.insurance.ws.service.insuranceProtocolInfo.InsuranceProtocolInfoService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;
import org.springframework.stereotype.Service;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016-11-18.
 */
@Service("insuranceProtocolService")
public class InsuranceProtocolServiceImpl extends SalesAreaCommService implements
        InsuranceProtocolService {
    @Autowired
    protected InsuranceProtocolDAO insuranceProtocolDAO;

    @Autowired
    protected InsuranceProtocolChargeDAO insuranceProtocolChargeDAO;

    @Autowired
    protected InsuranceProtocolInfoDAO insuranceProtocolInfoDAO;

    @Autowired
    protected InsuranceProtocolInfoService insuranceProtocolInfoService;

    @Autowired
    protected InsuranceCatalogDAO insuranceCatalogDAO;

    @Autowired
    protected InsuranceProductDAO insuranceProductDAO;

    private void saveInsuranceProtocolCharge(InsuranceProtocolInfoOrder infoOrder) {
        insuranceProtocolChargeDAO.deleteByProtocolInfoId(infoOrder.getProtocolInfoId());
        if (StringUtil.isEmpty(infoOrder.getChargeInfo())) {
            return;
        }
        JSONObject jsonObject = JSON.parseObject(infoOrder.getChargeInfo());
        if (jsonObject == null) {
            return;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        if (jsonArray != null && jsonArray.size() > 0) {
            for (int i = 0, length = jsonArray.size(); i < length; i++) {
                JSONObject yearJsonObject = jsonArray.getJSONObject(i);
                String timeLimit = yearJsonObject.getString("timeLimit");
                InsuranceProtocolChargeDO yearChargeDO = new InsuranceProtocolChargeDO();
                yearChargeDO.setProtocolInfoId(infoOrder.getProtocolInfoId());
                yearChargeDO.setDepth(1);
                yearChargeDO.setVal(timeLimit);
                yearChargeDO.setChargeId(BusinessNumberUtil.gainNumber());
                yearChargeDO.setNodePath(yearChargeDO.getChargeId() + ".");
                yearChargeDO.setParentId("0");
                insuranceProtocolChargeDAO.insert(yearChargeDO);
                JSONArray periodsJsonArray = yearJsonObject.getJSONArray("periods");
                if (periodsJsonArray != null && periodsJsonArray.size() > 0) {
                    for (int j = 0, len = periodsJsonArray.size(); j < len; j++) {
                        InsuranceProtocolChargeDO periodsChargeDO = new InsuranceProtocolChargeDO();
                        periodsChargeDO.setProtocolInfoId(infoOrder.getProtocolInfoId());
                        periodsChargeDO.setDepth(2);
                        periodsChargeDO.setVal((j + 1) + "");
                        periodsChargeDO.setChargeId(BusinessNumberUtil.gainNumber());
                        periodsChargeDO.setNodePath(yearChargeDO.getNodePath()
                                + periodsChargeDO.getChargeId() + ".");
                        periodsChargeDO.setParentId(yearChargeDO.getChargeId());
                        insuranceProtocolChargeDAO.insert(periodsChargeDO);
                        InsuranceProtocolChargeDO valChargeDO = new InsuranceProtocolChargeDO();
                        valChargeDO.setProtocolInfoId(infoOrder.getProtocolInfoId());
                        valChargeDO.setDepth(3);
                        valChargeDO.setVal(periodsJsonArray.getString(j));
                        valChargeDO.setChargeId(BusinessNumberUtil.gainNumber());
                        valChargeDO.setNodePath(periodsChargeDO.getNodePath()
                                + periodsChargeDO.getChargeId() + ".");
                        valChargeDO.setParentId(periodsChargeDO.getChargeId());
                        insuranceProtocolChargeDAO.insert(valChargeDO);
                    }
                }

            }
        }

    }

    private void saveInsuranceProtocolInfo(InsuranceProtocolOrder protocolOrder) {
        if (ListUtil.isNotEmpty(protocolOrder.getInfoOrders())) {
            insuranceProtocolInfoDAO.deleteByProtocolId(protocolOrder.getProtocolId());
            for (InsuranceProtocolInfoOrder infoOrder : protocolOrder.getInfoOrders()) {
                InsuranceProtocolInfoDO protocolInfoDO = new InsuranceProtocolInfoDO();
                BeanCopier.staticCopy(infoOrder, protocolInfoDO);
                protocolInfoDO.setProtocolId(protocolOrder.getProtocolId());
                protocolInfoDO.setContractingAgencyName(protocolOrder.getContractingAgencyName());
                protocolInfoDO.setProtocolUserName(protocolOrder.getProtocolUserName());
                protocolInfoDO.setType(protocolOrder.getType());
                insuranceProtocolInfoDAO.insert(protocolInfoDO);
                infoOrder.setProtocolInfoId(protocolInfoDO.getProtocolInfoId());
                saveInsuranceProtocolCharge(infoOrder);
            }
        }

    }

    @Override
    public InsuranceBaseResult save(final InsuranceProtocolOrder order) {
        return commonProcess(order, "保险协议增加或修改信息", new BeforeProcessInvokeService() {

            @Override
            public Domain before() {
                final Date now = InsuranceDomainHolder.get().getSysDate();

                long id = order.getProtocolId();
                if (id <= 0) {
                    InsuranceProtocolDO doObj = new InsuranceProtocolDO();
                    BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());

                    insuranceProtocolDAO.insert(doObj);
                    id = doObj.getProtocolId();
                    order.setProtocolId(id);
                } else {
                    InsuranceProtocolDO doObj = insuranceProtocolDAO.findById(order.getProtocolId());
                    if (null == doObj) {
                        throw ExceptionFactory.newFcsException(InsuranceResultEnum.HAVE_NOT_DATA,
                                "风险预警信息不存在");
                    }
                    BeanCopier.staticCopy(order, doObj, UnBoxingConverter.getInstance());

                    insuranceProtocolDAO.update(doObj);
                }

                saveSalesAreas(order.getProtocolId(), order.getSalesAreasCollect(),
                        SalesAreaTypeEnum.INSURANCE_PROTOCOL);
                saveInsuranceProtocolInfo(order);
                InsuranceBaseResult baseResult = (InsuranceBaseResult) InsuranceDomainHolder.get()
                        .getAttribute("result");
                baseResult.setKeyId(id);
                return null;
            }
        }, null, null);
    }

    @Override
    public InsuranceProtocolInfo findById(long id) {
        InsuranceProtocolDO insuranceProtocolDo = insuranceProtocolDAO.findById(id);
        if (insuranceProtocolDo != null) {
            InsuranceProtocolInfo insuranceProtocolInfo = new InsuranceProtocolInfo();
            BeanCopier.staticCopy(insuranceProtocolDo, insuranceProtocolInfo);
            querySalesAreas(insuranceProtocolInfo, id, SalesAreaTypeEnum.INSURANCE_PROTOCOL);
            InsuranceProtocolInfoQueryOrder infoQueryOrder = new InsuranceProtocolInfoQueryOrder();
            infoQueryOrder.setProtocolId(insuranceProtocolInfo.getProtocolId());
            insuranceProtocolInfo.setProtocolInfoInfos(insuranceProtocolInfoService.queryList(
                    infoQueryOrder).getPageList());
            return insuranceProtocolInfo;
        }
        return null;
    }

    @Override
    public QueryBaseBatchResult<InsuranceProtocolInfo> queryList(InsuranceProtocolQueryOrder queryOrder) {
        QueryBaseBatchResult<InsuranceProtocolInfo> batchResult = new QueryBaseBatchResult<InsuranceProtocolInfo>();

        try {
            queryOrder.check();
            List<InsuranceProtocolInfo> pageList = new ArrayList<InsuranceProtocolInfo>(
                    (int) queryOrder.getPageSize());

            InsuranceProtocolDO insuranceProtocolDO = new InsuranceProtocolDO();
            BeanCopier.staticCopy(queryOrder, insuranceProtocolDO);
            insuranceProtocolDO.setProtocolUserId(queryOrder.getProtocolUserId()+"");
            long totalCount = insuranceProtocolDAO.findByConditionCount(insuranceProtocolDO, null);
            PageComponent component = new PageComponent(queryOrder, totalCount);
            List<InsuranceProtocolDO> recordList = insuranceProtocolDAO.findByCondition(
                    insuranceProtocolDO, queryOrder.getSortCol(), queryOrder.getSortOrder(),
                    component.getFirstRecord(), component.getPageSize(), null);
            for (InsuranceProtocolDO item : recordList) {
                InsuranceProtocolInfo info = new InsuranceProtocolInfo();
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
    public String queryInsuranceProtocolCharge(ProtocolChargeQueryOrder queryOrder) {
        String[] companyUserIds = queryOrder.getCompanyUserIds().split(",");
        String[] catalogIds = queryOrder.getCatalogIds().split(",");
        JSONArray companyArray = new JSONArray();
        for (String userId : companyUserIds) {
            InsuranceProtocolDO insuranceProtocolDO = new InsuranceProtocolDO();
            insuranceProtocolDO.setProtocolUserId(userId);
            List<InsuranceProtocolDO> recordList = insuranceProtocolDAO.findByCondition(
                    insuranceProtocolDO, queryOrder.getSortCol(), queryOrder.getSortOrder(), 0,
                    100, getSysdate());
            JSONObject jsonObject = new JSONObject();
            if (ListUtil.isNotEmpty(recordList)) {
                List<InsuranceProtocolInfoDO> protocolInfoDOList = new ArrayList<InsuranceProtocolInfoDO>();
                for (InsuranceProtocolDO protocolDO : recordList) {
                    InsuranceProtocolInfoDO protocolInfoDO = new InsuranceProtocolInfoDO();
                    protocolInfoDO.setProtocolId(protocolDO.getProtocolId());
                    List<InsuranceProtocolInfoDO> protocolInfoDOs = insuranceProtocolInfoDAO
                            .findByCondition(protocolInfoDO, null, null, 0, 100);
                    protocolInfoDOList.addAll(protocolInfoDOs);
                }
                JSONArray jsonArray = new JSONArray();
                for (String catalogId : catalogIds) {
                    InsuranceProtocolInfoDO protocolInfoDO = queryCharge(NumberUtil.parseLong(catalogId), protocolInfoDOList);
                    if (protocolInfoDO != null) {
                        InsuranceProtocolChargeDO chargeDo = new InsuranceProtocolChargeDO();
                        chargeDo.setProtocolInfoId(protocolInfoDO.getProtocolInfoId());
                        chargeDo.setDepth(3);
                        List<InsuranceProtocolChargeDO> chargeDos = insuranceProtocolChargeDAO.findByCondition(chargeDo, null, null, 0, 100);
                        if (ListUtil.isNotEmpty(chargeDos)) {
                            JSONObject json = new JSONObject();
                            json.put("catalogId", catalogId);
                            json.put("protocolFee", chargeDos.get(0).getVal());
                            jsonArray.add(json);
                        }
                    }
                }
                jsonObject.put("companyUserId",userId);
                jsonObject.put("data",jsonArray);
                companyArray.add(jsonObject);
            }
        }
        return companyArray.toJSONString();
    }

    @Override
    public String queryInsuranceProtocolCharge(InsuranceProductInfo productInfo) {
        InsuranceProtocolDO insuranceProtocolDO = new InsuranceProtocolDO();
        insuranceProtocolDO.setProtocolUserId(productInfo.getCompanyBaseUserId()+"");
        List<InsuranceProtocolDO> recordList = insuranceProtocolDAO.findByCondition(
                insuranceProtocolDO, null,null, 0,
                100, getSysdate());
        List<InsuranceProtocolInfoDO> protocolInfoDOList = new ArrayList<InsuranceProtocolInfoDO>();
       if (ListUtil.isNotEmpty(recordList)) {
            for (InsuranceProtocolDO protocolDO : recordList) {
                InsuranceProtocolInfoDO protocolInfoDO = new InsuranceProtocolInfoDO();
                protocolInfoDO.setProtocolId(protocolDO.getProtocolId());
                List<InsuranceProtocolInfoDO> protocolInfoDOs = insuranceProtocolInfoDAO
                        .findByCondition(protocolInfoDO, null, null, 0, 100);
                protocolInfoDOList.addAll(protocolInfoDOs);
            }
       }

        InsuranceProtocolInfoDO infoDO = queryFirstChargeByProductId(productInfo.getProductId(),protocolInfoDOList);
        if(infoDO != null){
            return NumberUtil.parseDouble(infoDO.getFirstPeriod())/100 +"";
        }else{
            return "0";
        }

    }

    private InsuranceProtocolInfoDO queryFirstChargeByProductId(long productId,
                                                                List<InsuranceProtocolInfoDO> protocolInfoDOList) {
        for (InsuranceProtocolInfoDO infoDO : protocolInfoDOList) {
            if (infoDO.getProtocolId() == productId) {
                return infoDO;
            }
        }
        InsuranceProductDO insuranceProductDO = insuranceProductDAO.findById(productId);
        InsuranceCatalogDO catalogDO = insuranceCatalogDAO.findById(insuranceProductDO.getCatalogId());
        if (catalogDO == null) {
            return null;
        }
        return queryCharge(catalogDO.getCatalogId(), protocolInfoDOList);
    }

    private InsuranceProtocolInfoDO queryCharge(long catalogId,
                                                List<InsuranceProtocolInfoDO> protocolInfoDOList) {
        for (InsuranceProtocolInfoDO infoDO : protocolInfoDOList) {
            if (infoDO.getCatalogId() == catalogId) {
                return infoDO;
            }
        }
        InsuranceCatalogDO catalogDO = insuranceCatalogDAO.findById(catalogId);
        if (catalogDO == null) {
            return null;
        }
        InsuranceCatalogDO parentCatalogDO = insuranceCatalogDAO.findById(catalogDO.getParentCatalogId());
        if (parentCatalogDO == null) {
            return null;
        }
        return queryCharge(parentCatalogDO.getCatalogId(), protocolInfoDOList);
    }
}