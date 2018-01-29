package com.born.insurance.util.fop;
////
import java.io.Serializable;

public class ReportData implements Serializable {
	private static final long serialVersionUID = 5581305386894092700L;
	private byte[] mbData = null;
	private String msContentType = null;
	
	public byte[] getData() {
		return mbData;
	}
	
	public void setData(byte[] pData) {
		mbData = pData;
	}
	
	public String getContentType() {
		return msContentType;
	}
	
	public void setContentType(String pContentType) {
		msContentType = pContentType;
	}
	
	public ReportData() {
	}
	
}