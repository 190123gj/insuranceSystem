package com.born.insurance.ws.info.bpm;

import java.util.List;
import java.util.Set;

import com.born.insurance.ws.info.common.BaseToStringInfo;
import com.google.common.collect.Lists;

/**
 * 用户详细信息
 *
 *
 * @author wuzj
 *
 */
public class UserDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 4026775861874192091L;
	
	/** 用户ID */
	private long id;
	
	/** 用户账号 */
	private String account;
	
	/** 用户名称 */
	private String name;
	
	/** 用户电话 */
	private String mobile;
	
	/** 用户邮箱 */
	private String email;
	
	/** 职位列表 */
	private List<Job> jobList = Lists.newArrayList();
	
	/** 岗位列表 */
	private List<Position> posList = Lists.newArrayList();
	
	/** 角色列表 */
	private List<Role> roleList = Lists.newArrayList();
	
	/** 所在组织列表 */
	private List<Org> orgList = Lists.newArrayList();
	
	/** 所在集团列表 */
	private List<Org> groupList = Lists.newArrayList();
	
	/** 所在公司列表 */
	private List<Org> firmList = Lists.newArrayList();
	
	/** 所在部门列表 */
	private List<Org> deptList = Lists.newArrayList();
	
	/** 所在小组列表 */
	private List<Org> teamList = Lists.newArrayList();
	
	/** 其他组织列表 */
	private List<Org> otherList = Lists.newArrayList();
	
	/** 所有部门ID */
	private List<Long> deptIds = Lists.newArrayList();
	
	/** 所有部门（包括父部门ID） */
	private Set<Long> belongDeptIds;
	
	/** 主组织（部门） */
	private Org primaryOrg;
	
	/** 主岗位 */
	private Position primaryPos;
	
	/** 是否属于某部门 */
	public boolean isBelong2Dept(long deptId) {
		if (belongDeptIds == null) {
			return false;
		} else {
			boolean belongs = false;
			for (Long id : belongDeptIds) {
				if (id == deptId) {
					belongs = true;
					break;
				}
			}
			return belongs;
		}
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAccount() {
		return this.account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Job> getJobList() {
		return this.jobList;
	}
	
	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}
	
	public List<Position> getPosList() {
		return this.posList;
	}
	
	public void setPosList(List<Position> posList) {
		this.posList = posList;
	}
	
	public List<Role> getRoleList() {
		return this.roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
	public List<Org> getOrgList() {
		return this.orgList;
	}
	
	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}
	
	public List<Org> getGroupList() {
		return this.groupList;
	}
	
	public void setGroupList(List<Org> groupList) {
		this.groupList = groupList;
	}
	
	public List<Org> getFirmList() {
		return this.firmList;
	}
	
	public void setFirmList(List<Org> firmList) {
		this.firmList = firmList;
	}
	
	public List<Org> getDeptList() {
		return this.deptList;
	}
	
	public void setDeptList(List<Org> deptList) {
		this.deptList = deptList;
	}
	
	public List<Org> getTeamList() {
		return this.teamList;
	}
	
	public void setTeamList(List<Org> teamList) {
		this.teamList = teamList;
	}
	
	public List<Org> getOtherList() {
		return this.otherList;
	}
	
	public void setOtherList(List<Org> otherList) {
		this.otherList = otherList;
	}
	
	public List<Long> getDeptIds() {
		return this.deptIds;
	}
	
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}
	
	public Set<Long> getBelongDeptIds() {
		return this.belongDeptIds;
	}
	
	public void setBelongDeptIds(Set<Long> belongDeptIds) {
		this.belongDeptIds = belongDeptIds;
	}
	
	/** 没有主部门就返回其中一个部门 */
	public Org getPrimaryOrg() {
		if (this.primaryOrg == null && this.deptList != null && this.deptList.size() > 0) {
			return this.deptList.get(0);
		}
		return this.primaryOrg;
	}
	
	public void setPrimaryOrg(Org primaryOrg) {
		this.primaryOrg = primaryOrg;
	}
	
	public Position getPrimaryPos() {
		return this.primaryPos;
	}
	
	public void setPrimaryPos(Position primaryPos) {
		this.primaryPos = primaryPos;
	}
}
