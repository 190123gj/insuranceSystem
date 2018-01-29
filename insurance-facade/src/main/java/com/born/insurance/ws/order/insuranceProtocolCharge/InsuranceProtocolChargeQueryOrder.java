package com.born.insurance.ws.order.insuranceProtocolCharge;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class InsuranceProtocolChargeQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 值
	*/	
	private String val;
 	/**
	* 协议id
	*/	
	private String nodePath;
 	/**
	* 保险信息id
	*/	
	private long protocolInfoId;
 	/**
	* id
	*/	
	private String chargeId;
 	/**
	* 类型：期限，期数，比例
	*/	
	private String type;
 	/**
	* 协议id
	*/	
	private long depth;
 	/**
	* 父id
	*/	
	private String parentId;
 
  	public String getVal() {
        return val;
	}

	public void setVal(String val) {
        this.val = val;
	}
	public String getNodePath() {
        return nodePath;
	}

	public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
	}
	public long getProtocolInfoId() {
        return protocolInfoId;
	}

	public void setProtocolInfoId(long protocolInfoId) {
        this.protocolInfoId = protocolInfoId;
	}
	public String getChargeId() {
        return chargeId;
	}

	public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
	}
	public String getType() {
        return type;
	}

	public void setType(String type) {
        this.type = type;
	}
	public long getDepth() {
        return depth;
	}

	public void setDepth(long depth) {
        this.depth = depth;
	}
	public String getParentId() {
        return parentId;
	}

	public void setParentId(String parentId) {
        this.parentId = parentId;
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