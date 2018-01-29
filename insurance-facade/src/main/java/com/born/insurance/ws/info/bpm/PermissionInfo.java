package com.born.insurance.ws.info.bpm;

import java.io.Serializable;

public class PermissionInfo implements Serializable {
	private static final long serialVersionUID = -2822458872288249767L;
	String permissionOperate;
	
	public String getPermissionOperate() {
		return this.permissionOperate;
	}
	
	public void setPermissionOperate(String permissionOperate) {
		this.permissionOperate = permissionOperate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PermissionInfo [permissionOperate=");
		builder.append(permissionOperate);
		builder.append("]");
		return builder.toString();
	}
	
}
