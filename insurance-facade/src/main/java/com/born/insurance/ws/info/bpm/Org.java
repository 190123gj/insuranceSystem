package com.born.insurance.ws.info.bpm;

import com.born.insurance.ws.enums.bpm.OrgTypeEnum;
import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * 用户组织
 *
 *
 * @author wuzj
 *
 */
public class Org extends BaseToStringInfo {
	
	private static final long serialVersionUID = -8578241855180835149L;
	
	/** 组织ID */
	private long id;
	/** 组织编码 */
	private String code;
	/** 组织名称 */
	private String name;
	/** 组织ID路径 */
	private String path;
	/** 组织路径名称 */
	private String pathName;
	/** 组织类型 */
	private OrgTypeEnum type;
	/** 是否主部门 */
	private boolean isPrimary;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPathName() {
		return this.pathName;
	}
	
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
	public OrgTypeEnum getType() {
		return this.type;
	}
	
	public void setType(OrgTypeEnum type) {
		this.type = type;
	}
	
	public boolean isPrimary() {
		return this.isPrimary;
	}
	
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	
	/**
	 * 父节点ID
	 * @return
	 */
	public long getParentId() {
		if (path == null || "".equals(path))
			return 0;
		//去掉最后一个点
		String noDotPath = path.substring(0, path.length() - 1);
		String[] pathArr = noDotPath.split("\\.");
		if (pathArr.length > 1) {
			String parentId = pathArr[pathArr.length - 2];
			return Long.valueOf(parentId);
		} else {
			return 0;
		}
	}
	
	/**
	 * 父节点名称
	 * @return
	 */
	public String getParentName() {
		if (pathName == null || "".equals(pathName))
			return null;
		String[] pathNameArr = pathName.split("/");
		if (pathNameArr.length > 1) {
			return pathNameArr[pathNameArr.length - 2];
		} else {
			return null;
		}
	}
}
