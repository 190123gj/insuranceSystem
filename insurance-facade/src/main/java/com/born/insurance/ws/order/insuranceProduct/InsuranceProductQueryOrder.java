package com.born.insurance.ws.order.insuranceProduct;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceProductQueryOrder extends QueryPermissionPageBase {
				
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1895035926303314227L;
	/**
	 * 所选择的产品的id
	 */
	private String productIds;
	
	/**
	 * 询价单中选择的产品
	 */
	private String priceProducts;
	
	/**
	 * 关联了超权限对应的险种信息
	 */
	private String projectSetUpCatalogIds;
	/**
	* 用户备注
	*/	
	private String remark;
 	/**
	* YES,NO 是否必选
	*/	
	private String choice;
 	/**
	* 停售时间
	*/	
	private Date stopSaleDate;
 	/**
	* 
	*/	
	private Date rawUpdateTime;
 	/**
	* 创建者
	*/	
	private long creatorId;
 	/**
	* id
	*/	
	private long productId;
 	/**
	* 销售状态
	*/	
	private String saleStatus;
	
	/**
	 * 是否为寿险
	 */
	private String isLifeInsurance;
	
	/**
	 * 是否定额
	 */
	private String isQuota;
	
	/**
	 * 险别
	 */
	private String riskStatus;
 	/**
	* 父险种
	*/	
	private long parentProductId;
 	/**
	* 
	*/	
	private Date rawAddTime;
 	/**
	* 保险公司
	*/	
	private long companyUserId;

	private long companyBaseUserId;
 	/**
	* 缴费类型
	*/	
	private long payType;
 	/**
	* 险种名称
	*/	
	private String productName;
 	/**
	* 缴费期限
	*/	
	private long payPeriod;
 	/**
	* 费率表id
	*/	
	private long rateId;
 	/**
	* 创建者名称
	*/	
	private String creatorName;
 	/**
	* 备案号
	*/	
	private String recordNumber;
 	/**
	* 所属保险分类
	*/	
	private long catalogId;
 
  	public String getRemark() {
        return remark;
	}

	public void setRemark(String remark) {
        this.remark = remark;
	}
	public String getChoice() {
        return choice;
	}

	public void setChoice(String choice) {
        this.choice = choice;
	}
	public Date getStopSaleDate() {
        return stopSaleDate;
	}

	public void setStopSaleDate(Date stopSaleDate) {
        this.stopSaleDate = stopSaleDate;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getCreatorId() {
        return creatorId;
	}

	public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
	}
	public String getSaleStatus() {
        return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
	}

	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public long getCompanyUserId() {
        return companyUserId;
	}

	public void setCompanyUserId(long companyUserId) {
        this.companyUserId = companyUserId;
	}
	public long getPayType() {
        return payType;
	}

	public void setPayType(long payType) {
        this.payType = payType;
	}

	public long getPayPeriod() {
        return payPeriod;
	}

	public void setPayPeriod(long payPeriod) {
        this.payPeriod = payPeriod;
	}
	public long getRateId() {
        return rateId;
	}

	public void setRateId(long rateId) {
        this.rateId = rateId;
	}
	public String getCreatorName() {
        return creatorName;
	}

	public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
	}
	public String getRecordNumber() {
        return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
	}
	public long getCatalogId() {
        return catalogId;
	}

	public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(long parentProductId) {
		this.parentProductId = parentProductId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}
	
	public String getIsLifeInsurance() {
		return isLifeInsurance;
	}

	public void setIsLifeInsurance(String isLifeInsurance) {
		this.isLifeInsurance = isLifeInsurance;
	}
	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}
	
	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	

	public String getPriceProducts() {
		return priceProducts;
	}

	public void setPriceProducts(String priceProducts) {
		this.priceProducts = priceProducts;
	}
	

	public String getProjectSetUpCatalogIds() {
		return projectSetUpCatalogIds;
	}

	public void setProjectSetUpCatalogIds(String projectSetUpCatalogIds) {
		this.projectSetUpCatalogIds = projectSetUpCatalogIds;
	}

	public long getCompanyBaseUserId() {
		return companyBaseUserId;
	}

	public void setCompanyBaseUserId(long companyBaseUserId) {
		this.companyBaseUserId = companyBaseUserId;
	}

	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	
}	