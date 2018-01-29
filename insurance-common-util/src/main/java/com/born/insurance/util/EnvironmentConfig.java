package com.born.insurance.util;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class EnvironmentConfig {
	protected final Logger	logger			= LoggerFactory.getLogger(this.getClass());
	//	private static String		ipAddress;
	
	//	private static final String	ipLocaTestAdderss1		= "192.168.45.10";
	//	private static final String	ipLocaTestAdderss2		= "192.168.45.242";
	//	private static final String	ipLocaTestAdderss3		= "192.168.45.11";
	//	private static final String	ipTestNetAdderss		= "113.31.20.99";
	//	private static final String	ipTestAdderss			= "192.168.0.99";
	//	
	//	private static final String	ipProductionNetAdderss1	= "113.31.20.111";
	//	private static final String	ipProductionNetAdderss2	= "113.31.20.108";
	//	private static final String	ipProductionNetAdderss3	= "113.31.20.107";
	//	
	//	private static final String	ipProductionAdderss1	= "192.168.0.197";
	//	private static final String	ipProductionAdderss2	= "192.168.0.198";
	//	private static final String	ipProductionAdderss3	= "192.168.0.201";
	
	private String			environmentType	= "";
	
	public String getEnvironmentType() {
		return environmentType;
	}
	
	public void setEnvironmentType(String environmentType) {
		this.environmentType = environmentType;
	}
	
	public EnvironmentConfig() {
		/*ApplicationConstant.CURRENT_STORE_FILE_TYPE = ApplicationConstant.STORE_FILE_TYPE_ABSOLUTE_PATH;
		ApplicationConstant.HTTP_PATH_ROOT = "";
		ApplicationConstant.FILE_PATH_ROOT = "/var/www/upload/";
		ApplicationConstant.HTTP_DOMAIN_URL = "https://www.yiji.com/assets/upload/";
		environmentType = ApplicationConstant.ENVIRONMENT_TYPE_PRODUCTION;
		if (StringUtil.isNotBlank(PropertiesUtil.getProperty("upload.filepath.root"))) {
			ApplicationConstant.FILE_PATH_ROOT = PropertiesUtil.getProperty("upload.filepath.root");
		}
		if (StringUtil.isNotBlank(PropertiesUtil.getProperty("upload.http.domain"))) {
			ApplicationConstant.HTTP_DOMAIN_URL = PropertiesUtil.getProperty("upload.http.domain");
		}
		if (StringUtil.isNotBlank(PropertiesUtil.getProperty("upload.env"))) {
			environmentType = PropertiesUtil.getProperty("upload.env");
		}
		logger.info("ApplicationConstant.FILE_PATH_ROOT=" + ApplicationConstant.FILE_PATH_ROOT
					+ ",ApplicationConstant.CURRENT_STORE_FILE_TYPE="
					+ ApplicationConstant.CURRENT_STORE_FILE_TYPE
					+ ",ApplicationConstant.HTTP_DOMAIN_URL=" + ApplicationConstant.HTTP_DOMAIN_URL
					+ ",environmentType =" + environmentType);*/
		
	}
	
	//	private static void getIpAddress() {
	//		try {
	//			InetAddress addr = null;
	//			addr = InetAddress.getLocalHost();
	//			ipAddress = addr.getHostAddress();
	//			logger.info(" java Get locahost ipAddress :" + ipAddress
	//						+ " ================================================");
	//		} catch (UnknownHostException e) {
	//			logger.error("get ip address error", e);
	//		}
	//		
	//	}
	
}
