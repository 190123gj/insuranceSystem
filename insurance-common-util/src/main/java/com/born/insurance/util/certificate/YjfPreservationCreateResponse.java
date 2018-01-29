package com.born.insurance.util.certificate;

import java.util.List;

import org.mapu.themis.api.response.preservation.PreservationCreateResponse;

import rop.security.MainError;
import rop.security.SubError;

public class YjfPreservationCreateResponse  {
	
	private PreservationCreateResponse response;
	
	public YjfPreservationCreateResponse(PreservationCreateResponse response){
		this.response= response;
	}
	

	public boolean isSuccess(){
		return this.response.isSuccess();
	}
	
	public Long getPreservationId(){
		return this.response.getPreservationId();
	}
	
	public String getDocHash(){
		return this.response.getDocHash();
	}
	
	public Long getPreservationTime(){
		return this.response.getPreservationTime();
	}
	
	
	public String getErrorMsg(){
		MainError error = this.response.getError();
		List<SubError> subErrors = error.getSubErrors();
		if(subErrors != null){
			for(SubError subError : subErrors){
				
				String code = ""+subError.getCode();
				if(code.indexOf("DUPLICATE_PRESERVATION")!=-1){
					return "该文件已做过数字认证";
				}else{
					return subError.getMessage();
				}
			}
		}
		return "认证异常";
		
	}
	
	
	public String getErrorCode(){
		MainError error = this.response.getError();
		List<SubError> subErrors = error.getSubErrors();
		if(subErrors != null){
			for(SubError subError : subErrors){
				
				String code = ""+subError.getCode();
				if(code.indexOf("DUPLICATE_PRESERVATION")!=-1){
					return "DUPLICATE_PRESERVATION";
				}else{
					return code;
				}
			}
		}
		return "认证异常";
		
	}
	
	
}
