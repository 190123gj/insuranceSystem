package com.born.insurance.util.certificate;

import org.mapu.themis.api.common.PersonalIdentifer;
import org.mapu.themis.api.common.PreservationType;

public class ThemisParam {
	
	private String sourcefile;
	
	private String text;
	
	private String preservationTitle;
	
	private PreservationType preservationType;
	
	private PersonalIdentifer personalIdentifer;
	
	private String sourceRegistryId;
	
	private String userEmail;
	
	private String mobilePhone;
	
	private double contractAmount;
	
	private String contractNumber;
	
	public String getMobilePhone() {
		return this.mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getSourcefile() {
		return this.sourcefile;
	}
	
	public void setSourcefile(String sourcefile) {
		this.sourcefile = sourcefile;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getPreservationTitle() {
		return this.preservationTitle;
	}
	
	public void setPreservationTitle(String preservationTitle) {
		this.preservationTitle = preservationTitle;
	}
	
	public PersonalIdentifer getPersonalIdentifer() {
		return this.personalIdentifer;
	}
	
	public void setPersonalIdentifer(PersonalIdentifer personalIdentifer) {
		this.personalIdentifer = personalIdentifer;
	}
	
	public String getSourceRegistryId() {
		return this.sourceRegistryId;
	}
	
	public void setSourceRegistryId(String sourceRegistryId) {
		this.sourceRegistryId = sourceRegistryId;
	}
	
	public PreservationType getPreservationType() {
		return this.preservationType;
	}
	
	public void setPreservationType(PreservationType preservationType) {
		this.preservationType = preservationType;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public double getContractAmount() {
		return contractAmount;
	}
	
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	public String getContractNumber() {
		return contractNumber;
	}
	
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThemisParam [sourcefile=");
		builder.append(sourcefile);
		builder.append(", text=");
		builder.append(text);
		builder.append(", preservationTitle=");
		builder.append(preservationTitle);
		builder.append(", preservationType=");
		builder.append(preservationType);
		builder.append(", personalIdentifer=");
		builder.append(personalIdentifer);
		builder.append(", sourceRegistryId=");
		builder.append(sourceRegistryId);
		builder.append(", userEmail=");
		builder.append(userEmail);
		builder.append(", mobilePhone=");
		builder.append(mobilePhone);
		builder.append(", contractAmount=");
		builder.append(contractAmount);
		builder.append(", contractNumber=");
		builder.append(contractNumber);
		builder.append("]");
		return builder.toString();
	}
	
}
