package com.born.insurance.integration.bpm.org.impl;

import java.util.List;

import com.born.insurance.integration.bpm.info.org.OrgInfo;
import com.born.insurance.integration.bpm.org.OrgService;
import com.born.insurance.integration.bpm.user.UserInfo;
import com.born.insurance.util.StringUtil;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;


@Service("orgService")
public class OrgServiceImpl implements OrgService {
	
	@Override
	public OrgInfo findOrgInSystemByCode(String orgCode) {
		OrgInfo result = new OrgInfo();
		OrgInfo info = loadSystemOrg();
		if (StringUtil.isNotEmpty(orgCode)) {
			findOrgInfo(info, orgCode, result);
			if (StringUtil.isNotEmpty(result.getOrgCode())) {
				return result;
			} else {
				return null;
			}
		} else {
			return info;
		}
		
	}
	
	/**
	 * 从树形结构的组织机构中递归查找某个机构
	 * 
	 * @param result
	 * @param orgCode
	 * @return
	 */
	private void findOrgInfo(OrgInfo target, String orgCode, final OrgInfo result) {
		if (orgCode.equals(target.getOrgCode())) {// 找到
			result.setOrgCode(target.getOrgCode());
			result.setOrgName(target.getOrgName());
			result.setParentOrgCode(target.getParentOrgCode());
			result.setSubOrg(target.getSubOrg());
		}
		for (OrgInfo subOrg : target.getSubOrg()) {
			if (StringUtil.isNotEmpty(result.getOrgCode())) {
				break;// 找到后停止继续查找
			} else {
				findOrgInfo(subOrg, orgCode, result);
			}
		}
	}
	
	// 系统中组织机构的顶级机构（ 测试数据）
	private OrgInfo loadSystemOrg() {
		String code = "CODE";
		String name = "部门";
		
		OrgInfo rs = getOrgInfo(code, name, 0, null);
		List<OrgInfo> list1 = Lists.newArrayList();
		for (int i = 1; i <= 2; i++) {
			OrgInfo i1 = getOrgInfo(code + i, name + i, i, rs.getOrgCode());
			List<OrgInfo> list2 = Lists.newArrayList();
			for (int j = 1; j <= 5; j++) {
				OrgInfo i2 = getOrgInfo(code + i + j, name + i + j, j, i1.getOrgCode());
				List<OrgInfo> list3 = Lists.newArrayList();
				for (int n = 1; n <= 10; n++) {
					OrgInfo i3 = getOrgInfo(code + i + j + n, name + i + j + n, j, i2.getOrgCode());
					list3.add(i3);
				}
				i2.setSubOrg(list3);
				list2.add(i2);
			}
			i1.setSubOrg(list2);
			list1.add(i1);
			
		}
		rs.setSubOrg(list1);
		return rs;
	}
	
	private OrgInfo getOrgInfo(String orgCode, String orgName, int r, String parentOrgCode) {
		OrgInfo o = new OrgInfo();
		o.setOrgCode(orgCode);
		o.setOrgName(orgName);
		o.setRank(r);
		o.setParentOrgCode(parentOrgCode);
		return o;
	}
	
	@Override
	public List<UserInfo> findOrgMenbersByCode(String orgCode) {
		if (StringUtil.isEmail(orgCode)) {
			return null;
		}
		// TODO 假数据
		List<UserInfo> rt = Lists.newArrayList();
		for (int i = 1; i <= 5; i++) {
			UserInfo user = new UserInfo();
			user.setUserId(i);
			user.setUserName(orgCode + "_user_" + i);
			user.setRealName("李四" + i);
			rt.add(user);
		}
		return rt;
	}
}
