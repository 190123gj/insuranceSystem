package com.born.insurance.ws.order.baseDataInfo;
import com.born.insurance.ws.enums.BaseDataInfoTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.util.Date;
import com.born.insurance.ws.base.QueryPermissionPageBase;

public class BaseDataInfoQueryOrder extends QueryPermissionPageBase {
				
 	/**
	* id
	*/	
	private long id;
 	/**
	* 创建时间
	*/	
	private Date rawAddTime;
 	/**
	* 名称
	*/	
	private String name;
 	/**
	* 顺序
	*/	
	private long seq;
 	/**
	* 编码
	*/	
	private String code;
 	/**
	* 类型
	*/	
	private BaseDataInfoTypeEnum type;
 	/**
	* 更新时间
	*/	
	private Date rawUpdateTime;
 	/**
	* 父id
	*/	
	private long parentId;
 
  	public long getId() {
        return id;
	}

	public void setId(long id) {
        this.id = id;
	}
	public Date getRawAddTime() {
        return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
	}
	public String getName() {
        return name;
	}

	public void setName(String name) {
        this.name = name;
	}
	public long getSeq() {
        return seq;
	}

	public void setSeq(long seq) {
        this.seq = seq;
	}
	public String getCode() {
        return code;
	}

	public void setCode(String code) {
        this.code = code;
	}
	public BaseDataInfoTypeEnum getType() {
        return type;
	}

	public void setType(BaseDataInfoTypeEnum type) {
        this.type = type;
	}
	public Date getRawUpdateTime() {
        return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
	}
	public long getParentId() {
        return parentId;
	}

	public void setParentId(long parentId) {
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