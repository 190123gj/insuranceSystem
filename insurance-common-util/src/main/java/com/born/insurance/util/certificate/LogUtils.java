package com.born.insurance.util.certificate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mapu.themis.api.domain.DigestDTO;
import org.mapu.themis.api.response.digest.GetLatestHashDigestResponse;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;
import org.mapu.themis.api.response.preservation.PreservationGetResponse;

import rop.response.RopResponse;
import rop.security.MainError;
import rop.security.SubError;

import com.alibaba.fastjson.JSON;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 日志工具
 * @author luopeng
 *         Created on 2014/5/5.
 */
public class LogUtils {
	
	protected final static Logger logger = LoggerFactory.getLogger(LogUtils.class);

	public static void logCreation(PreservationCreateResponse response){
		
		
		logger.info("-----------------------------------------------");
		if(response.isSuccess()){
			logger.info("创建保全成功!");
			logger.info("保全ID:"+response.getPreservationId());
			logger.info("保全特征码:"+response.getDocHash());
			logger.info("保全时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(response.getPreservationTime())));

		}else{
			logger.info("创建保全失败");
			logError(response);

		}
		logger.info("-----------------------------------------------");
	}

	private static void logError(RopResponse response) {
		MainError error = response.getError();
		if(error != null){
			logger.info("Main Error Code:"+error.getCode());
			logger.info("Main Error Message:"+error.getMessage());
			logger.info("Main Error Solution:"+error.getSolution());
			List<SubError> subErrors = error.getSubErrors();
			if(subErrors != null){
				for(SubError subError : subErrors){
					logger.info("Sub Error Code:"+subError.getCode());
					logger.info("Sub Error Message:"+subError.getMessage());
				}
			}
		}else{
			logger.info("Error is null");
		}
	}

	public static void logPreservation(PreservationGetResponse response) {

		logger.info("-----------------------------------------------");
		if(response.isSuccess()){
			logger.info("获取保全成功!");
			logger.info("保全ID:"+response.getPreservationId());
			logger.info("保全特征码:"+response.getDocHash());
			logger.info("保全时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(response.getPreservationTime())));

		}else{
			logger.info("获取保全失败");
			logError(response);

		}
		logger.info("-----------------------------------------------");
	}

	public static void logDigest(GetLatestHashDigestResponse response) {

		logger.info("-----------------------------------------------");
		if(response.isSuccess()){
			logger.info("获取最新哈希摘要成功!");
			for(DigestDTO digestDTO:response.getDigestDTOList()){
				logger.info(digestDTO.toString());
			}
		}else{
			logger.info("获取最新哈希摘要失败");
			logError(response);

		}
		logger.info("-----------------------------------------------");
	}

	public static void logResponse(RopResponse response) {

		logger.info("-----------------------------------------------");
		if(response.isSuccess()){
			logger.info("操作成功!");
			logger.info(JSON.toJSONString(response));
		}else{
			logger.info("操作失败");
			logError(response);

		}
		logger.info("-----------------------------------------------");
	}

}
