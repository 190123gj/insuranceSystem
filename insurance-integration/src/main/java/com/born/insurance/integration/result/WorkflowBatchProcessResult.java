package com.born.insurance.integration.result;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

/**
 * 流程批量审核结果
 *
 * @author wuzj
 */
public class WorkflowBatchProcessResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = -6900128262764538135L;
	
	/** 成功 */
	List<Long> successFormIdList;
	List<String> successMessageList;
	/** 失败 */
	List<Long> failureFormIdList;
	/** 失败信息 */
	List<String> failureMessageList;
	/** 不支持批量审核 */
	List<Long> nonSupportFormIdList;
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("successFormIdList",
			successFormIdList == null ? new JSONObject() : JSONObject.toJSON(successFormIdList));
		json.put("successMessageList",
			successMessageList == null ? new JSONObject() : JSONObject.toJSON(successMessageList));
		json.put("failureFormIdList",
			failureFormIdList == null ? new JSONObject() : JSONObject.toJSON(failureFormIdList));
		json.put("failureMessageList",
			failureMessageList == null ? new JSONObject() : JSONObject.toJSON(failureMessageList));
		json.put("nonSupportFormIdList", nonSupportFormIdList == null ? new JSONObject()
			: JSONObject.toJSON(nonSupportFormIdList));
		return json;
	}
	
	public List<Long> getSuccessFormIdList() {
		return this.successFormIdList;
	}
	
	public void setSuccessFormIdList(List<Long> successFormIdList) {
		this.successFormIdList = successFormIdList;
	}
	
	public List<String> getSuccessMessageList() {
		return this.successMessageList;
	}
	
	public void setSuccessMessageList(List<String> successMessageList) {
		this.successMessageList = successMessageList;
	}
	
	public List<Long> getFailureFormIdList() {
		return this.failureFormIdList;
	}
	
	public void setFailureFormIdList(List<Long> failureFormIdList) {
		this.failureFormIdList = failureFormIdList;
	}
	
	public List<String> getFailureMessageList() {
		return this.failureMessageList;
	}
	
	public void setFailureMessageList(List<String> failureMessageList) {
		this.failureMessageList = failureMessageList;
	}
	
	public List<Long> getNonSupportFormIdList() {
		return this.nonSupportFormIdList;
	}
	
	public void setNonSupportFormIdList(List<Long> nonSupportFormIdList) {
		this.nonSupportFormIdList = nonSupportFormIdList;
	}
}
