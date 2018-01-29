package com.born.insurance.ws.order.personCommissionDetail;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.insurance.ws.base.QueryPermissionPageBase;

public class PersonCommissionDetailQueryOrder extends QueryPermissionPageBase {
	private static final long serialVersionUID = -1757017911114105329L;
	/**
	 * 佣金的id
	 */
    private long commissionId;
    
    /**
     * 业务员id
     */
    private long businessUserId;
    
    /**
     * 显示的类型(presonCommissionCanvas 图表 viewList 列表)
     */
    private String type;
    
    /**
     * 业务员名称
     */
    private String businessUserName;
    
    /**
     * 起始时间
     */
    private String beginDate;
    
    /**
     * 结束时间
     */
    private String endDate;
    
    /**
     * 快速查询（当月 thisMonth 上月lastMonth 最近三个月 recentlyMonth）
     */
    private String faseMark;

	

	public long getCommissionId() {
		return commissionId;
	}



	public void setCommissionId(long commissionId) {
		this.commissionId = commissionId;
	}



	public long getBusinessUserId() {
		return businessUserId;
	}



	public void setBusinessUserId(long businessUserId) {
		this.businessUserId = businessUserId;
	}



	public String getBusinessUserName() {
		return businessUserName;
	}



	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}



	public String getBeginDate() {
		return beginDate;
	}



	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getFaseMark() {
		return faseMark;
	}



	public void setFaseMark(String faseMark) {
		this.faseMark = faseMark;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
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