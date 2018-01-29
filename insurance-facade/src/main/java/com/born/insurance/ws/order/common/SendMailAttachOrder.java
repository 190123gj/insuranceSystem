package com.born.insurance.ws.order.common;

import com.born.insurance.ws.info.common.BaseToStringInfo;

/**
 * 邮件附件
 *
 * @author wuzj
 *
 */
public class SendMailAttachOrder extends BaseToStringInfo {
	
	private static final long serialVersionUID = -4983478517875686703L;
	
	/**
	 * 附件名
	 */
	private String name;
	
	/**
	 * 邮件附件地址(物理地址或者在线地址)
	 */
	private String path;
	
	/**
	 * 附件描述
	 */
	private String desc;
	
	/**
	 * 是否在线地址
	 */
	private boolean isOnlinePath;
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean isOnlinePath() {
		return this.isOnlinePath;
	}
	
	public void setOnlinePath(boolean isOnlinePath) {
		this.isOnlinePath = isOnlinePath;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
