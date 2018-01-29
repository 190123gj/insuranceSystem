package com.born.insurance.util.certificate;

import org.mapu.themis.ThemisClient;

import com.born.insurance.util.AppConstantsUtil;

public class ThemisClientInit {
	
	private static ThemisClient themisClient;
	
	private static Object lock="lock";
	
	public ThemisClient getClient() {
		if (themisClient == null) {
			synchronized (lock) {
			themisClient = new ThemisClient(AppConstantsUtil.getCertificateServiceUrl(),
				 AppConstantsUtil.getCertificateAppKey(),AppConstantsUtil.getCertificateAppSecret());
			}
		}
		
		return themisClient;
	}
	
	/**
	 * 更新
	 */
	public static void refresh(){
		synchronized (lock) {
			themisClient = new ThemisClient(AppConstantsUtil.getCertificateServiceUrl(),
				 AppConstantsUtil.getCertificateAppKey(),AppConstantsUtil.getCertificateAppSecret());
		}
	}
	
}
