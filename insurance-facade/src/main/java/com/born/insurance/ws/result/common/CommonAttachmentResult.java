package com.born.insurance.ws.result.common;

import com.born.insurance.ws.info.common.CommonAttachmentInfo;
import com.born.insurance.ws.result.base.InsuranceBaseResult;

public class CommonAttachmentResult extends InsuranceBaseResult {
	
	private static final long serialVersionUID = -8894712705496167696L;
	CommonAttachmentInfo attachmentInfo;
	
	public CommonAttachmentInfo getAttachmentInfo() {
		return this.attachmentInfo;
	}
	
	public void setAttachmentInfo(CommonAttachmentInfo attachmentInfo) {
		this.attachmentInfo = attachmentInfo;
	}
	
}
