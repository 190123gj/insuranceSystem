package com.born.insurance.ws.info.bpm;

import java.util.List;


import com.born.insurance.ws.order.bpm.TaskNodeInfo;
import com.google.common.collect.Lists;

public class WebNodeInfo extends TaskNodeInfo {
	
	private static final long serialVersionUID = 2553319715416321480L;
	List<BpmButtonInfo> bpmButtonInfos = Lists.newArrayList();
	
	public List<BpmButtonInfo> getBpmButtonInfos() {
		return this.bpmButtonInfos;
	}
	
	public void setBpmButtonInfos(List<BpmButtonInfo> bpmButtonInfos) {
		this.bpmButtonInfos = bpmButtonInfos;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WebNodeInfo [bpmButtonInfos=");
		builder.append(bpmButtonInfos);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
