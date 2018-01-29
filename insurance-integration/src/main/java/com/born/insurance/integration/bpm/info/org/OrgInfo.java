package com.born.insurance.integration.bpm.info.org;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 组织架构
 *
 *
 * @author Fei
 *
 */
public class OrgInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 组织代码 */
	String orgCode;
	/** 组织名称 */
	String orgName;
	/** 上级组织代码 */
	String parentOrgCode;
	/** 下级组织 */
	List<OrgInfo> subOrg = Lists.newArrayList();

	long rank;

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<OrgInfo> getSubOrg() {
		return this.subOrg;
	}

	public void setSubOrg(List<OrgInfo> subOrg) {
		this.subOrg = subOrg;
	}

	public String getParentOrgCode() {
		return this.parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public long getRank() {
		return this.rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "OrgInfo [orgCode=" + orgCode + ", orgName=" + orgName
				+ ", parentOrgCode=" + parentOrgCode + ",  rank=" + rank + "]";
	}

}
