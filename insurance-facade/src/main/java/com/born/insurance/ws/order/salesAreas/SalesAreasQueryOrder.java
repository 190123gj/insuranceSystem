package com.born.insurance.ws.order.salesAreas;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class SalesAreasQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* 
	*/	
	private long id;
 	/**
	* 险种
	*/	
	private long productId;
 	/**
	* 所属区域 - 省名称
	*/	
	private String provinceName;
 	/**
	* 所属区域 - 省编码
	*/	
	private String provinceCode;
 	/**
	* 类型：险种，协议
	*/	
	private String type;
 
  	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProvinceName() {
        return provinceName;
	}

	public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
	}
	public String getProvinceCode() {
        return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
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