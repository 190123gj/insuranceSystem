package com.born.insurance.ws.info.bpm;

import java.io.Serializable;
import java.util.Date;



import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



/**
 * 已办任务
 * @author wuzj
 */
public class BpmFinishTaskInfo implements Serializable {
	
	private static final long serialVersionUID = -6558066365256746416L;
	
	long defId;
	String actDefId;
	String actInstId;
	long creatorId;
	String creator;
	Date createtime;
	Date endTime;
	String processName;
	String businessUrl;
	String subject;
	long runId;
	long status;
	
	public String getSubjectView() {
		if (subject != null && subject.indexOf("|") > 0) {
			String[] s = subject.split("\\|");
			String subjectView = "";
			for (int i = 0; i < s.length - 1; i++) {
				subjectView += s[i];
			}
			return subjectView;
		}
		return this.subject;
	}
	

	
	//状态描述
	public String getStatusView() {
		String desc = "-";
		if (status == 1) {
			desc = "正在运行";
		} else if (status == 2) {
			desc = "完成";
		} else if (status == 3) {
			desc = "终止";
		} else if (status == 5 || status == 7) {
			desc = "已撤销";
		} else if (status == 6) {
			desc = "已驳回";
		}
		return desc;
	}
	
	public long getDefId() {
		return defId;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public String getActDefId() {
		return actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public String getActInstId() {
		return actInstId;
	}
	
	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}
	
	public long getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getBusinessUrl() {
		return businessUrl;
	}
	
	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public long getRunId() {
		return runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getStatus() {
		return status;
	}
	
	public void setStatus(long status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
