package com.born.insurance.util.certificate;

import org.mapu.themis.api.request.cer.CertificateLinkGetRequest;
import org.mapu.themis.api.request.contract.ContractFilePreservationCreateRequest;
import org.mapu.themis.api.request.preservation.FileBasedPreservationCreateRequest;
import org.mapu.themis.api.request.preservation.HashBasedPreservationCreateRequest;
import org.mapu.themis.api.request.preservation.PlainTextBasedPreservationCreateRequest;
import org.mapu.themis.api.request.preservation.PreservationFileDownloadRequest;
import org.mapu.themis.api.request.preservation.PreservationGetRequest;
import org.mapu.themis.api.response.cer.CertificateLinkGetResponse;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;
import org.mapu.themis.api.response.preservation.PreservationDownloadFileResponse;
import org.mapu.themis.api.response.preservation.PreservationGetResponse;

import com.yjf.common.lang.util.StringUtil;

public class ThemisClient extends ThemisClientInit {
	
	/**
	 * <pre>基于文件上传的保全调用</pre>
	 * 
	 * <pre>不推荐此种方法，因此种方法会占用较大网络带宽</pre>
	 * @author luopeng Created on 2014/5/5.
	 */
	public YjfPreservationCreateResponse fileUploadPreservation(ThemisParam param) {
		//创建请求
		FileBasedPreservationCreateRequest.Builder requestBuilder = new FileBasedPreservationCreateRequest.Builder();
		requestBuilder.withFile(param.getSourcefile());
		requestBuilder.withPreservationTitle(param.getPreservationTitle());
		requestBuilder.withPreservationType(param.getPreservationType());
		requestBuilder.withIdentifer(param.getPersonalIdentifer());
		requestBuilder.withSourceRegistryId(param.getSourceRegistryId());
		if (StringUtil.isNotBlank(param.getUserEmail())) {
			requestBuilder.withUserEmail(param.getUserEmail());
		}
		if (StringUtil.isNotBlank(param.getMobilePhone())) {
			requestBuilder.withMobilePhone(param.getMobilePhone());
		}
		
		PreservationCreateResponse response = getClient()
			.createPreservation(requestBuilder.build());
		
		LogUtils.logCreation(response);
		
		return new YjfPreservationCreateResponse(response);
		
	}
	
	public YjfPreservationCreateResponse contractFileUploadPreservation(ThemisParam param) {
		//创建请求
		ContractFilePreservationCreateRequest.Builder requestBuilder = new ContractFilePreservationCreateRequest.Builder();
		requestBuilder.withFile(param.getSourcefile());
		requestBuilder.withPreservationTitle(param.getPreservationTitle());
		requestBuilder.withPreservationType(param.getPreservationType());
		requestBuilder.withIdentifer(param.getPersonalIdentifer());
		requestBuilder.withSourceRegistryId(param.getSourceRegistryId());
		requestBuilder.withIsNeedSign(true);//使用电子签章
		if (StringUtil.isNotBlank(param.getUserEmail())) {
			requestBuilder.withUserEmail(param.getUserEmail());
		}
		
		if (StringUtil.isNotBlank(param.getMobilePhone())) {
			requestBuilder.withMobilePhone(param.getMobilePhone());
		}
		requestBuilder.withContractAmount(param.getContractAmount());
		requestBuilder.withContractNumber(param.getContractNumber());
		PreservationCreateResponse response = getClient()
			.createPreservation(requestBuilder.build());
		
		LogUtils.logCreation(response);
		
		return new YjfPreservationCreateResponse(response);
		
	}
	
	/**
	 * <pre>基于文件/文本特征的保全（不上传文件/文本）</pre>
	 * 
	 * <pre style="color:red">推荐使用！</pre>
	 * @author luopeng Created on 2014/5/5.
	 */
	public YjfPreservationCreateResponse hashBasedPreservation(ThemisParam param) {
		HashBasedPreservationCreateRequest.Builder requestBuilder = new HashBasedPreservationCreateRequest.Builder();
		//注意这里选择从文本计算或者从文件计算，二选一，推荐从文件计算
		//		requestBuilder.fromString("这里是待保全的文本内容");
		//		requestBuilder.fromFile("/home/files/Xd8FDFdosYl.JPG");
		requestBuilder.fromFile(param.getSourcefile());
		requestBuilder.withPreservationTitle(param.getPreservationTitle());
		requestBuilder.withPreservationType(param.getPreservationType());
		requestBuilder.withIdentifer(param.getPersonalIdentifer());
		requestBuilder.withSourceRegistryId(param.getSourceRegistryId());
		if (StringUtil.isNotBlank(param.getUserEmail())) {
			requestBuilder.withUserEmail(param.getUserEmail());
		}
		
		if (StringUtil.isNotBlank(param.getMobilePhone())) {
			requestBuilder.withMobilePhone(param.getMobilePhone());
		}
		
		//		requestBuilder.withGroupId(1L);
		
		PreservationCreateResponse response = getClient()
			.createPreservation(requestBuilder.build());
		
		LogUtils.logCreation(response);
		
		return new YjfPreservationCreateResponse(response);
	}
	
	/**
	 * 基于纯文本上传保全
	 * @author luopeng Created on 2014/5/5.
	 */
	public YjfPreservationCreateResponse plaintextPreservation(ThemisParam param) {
		PlainTextBasedPreservationCreateRequest.Builder requestBuilder = new PlainTextBasedPreservationCreateRequest.Builder();
		
		requestBuilder.withText(param.getSourcefile());
		requestBuilder.withPreservationTitle(param.getPreservationTitle());
		requestBuilder.withPreservationType(param.getPreservationType());
		requestBuilder.withIdentifer(param.getPersonalIdentifer());
		requestBuilder.withSourceRegistryId(param.getSourceRegistryId());
		//		requestBuilder.withGroupId(1L);
		
		PreservationCreateResponse response = getClient()
			.createPreservation(requestBuilder.build());
		
		LogUtils.logCreation(response);
		return new YjfPreservationCreateResponse(response);
	}
	
	/**
	 * @param preservationId 证书下载
	 */
	public PreservationDownloadFileResponse preservationFileDownload(Long preservationId) {
		PreservationFileDownloadRequest request = new PreservationFileDownloadRequest();
		request.setPreservationId(preservationId); //53L
		PreservationDownloadFileResponse response = getClient().downloadPreservationFile(request);
		LogUtils.logResponse(response);
		return response;
	}
	
	/**
	 * @param preservationId <pre>证书下载
	 * 
	 * <pre>
	 * 
	 * <pre>保全ID
	 * 
	 * <pre>
	 * 
	 * <pre>保全特征码
	 * 
	 * <pre>
	 * 
	 * <pre>保全时间
	 * 
	 * <pre>
	 */
	public PreservationGetResponse preservationGet(Long preservationId) {
		PreservationGetRequest request = new PreservationGetRequest();
		request.setPreservationId(preservationId); //53L
		PreservationGetResponse response = getClient().getPreservation(request);
		LogUtils.logPreservation(response);
		return response;
	}
	
	/**
	 * 获取数字证书链接
	 * @param preservationId
	 */
	public CertificateLinkGetResponse certificateLinkGet(Long preservationId) {
		CertificateLinkGetRequest request = new CertificateLinkGetRequest();
		request.setPreservationId(preservationId); //53L
		CertificateLinkGetResponse response = getClient().getCertificateLink(request);
		LogUtils.logResponse(response);
		return response;
	}
	
}
