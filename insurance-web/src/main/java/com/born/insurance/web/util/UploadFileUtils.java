/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.web.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.born.insurance.biz.service.common.SysParameterService;
import com.born.insurance.util.ApplicationConstant;
import com.born.insurance.ws.enums.SysParamEnum;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename UploadFileUtils.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-11</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public class UploadFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	private static Random newRandom = new Random(1000);
	
	private static SysParameterService sysParameterService;
	
	static {
		sysParameterService = SpringUtil.getBean("sysParameterService");
	}
	
	/**
	 * 获取文件路径，string[0]物理路径，string[1]http路径
	 * @param req
	 * @param fileOrgName
	 * @return
	 */
	public static String[] getStaticFilesImgPath(HttpServletRequest req, String fileOrgName) {
		
		return getFilePath(req, "uploadfile", "images", fileOrgName);
	}
	
	public static String[] getStaticFilesPdfPath(HttpServletRequest req, String fileOrgName) {
		return getFilePath(req, "uploadfile", "pdf", fileOrgName);
	}
	
	public static String getStaticFilesPdfDir() {
		return sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_UPLOAD_FOLDER
			.code()) + File.separator + "uploadfile" + File.separator + "pdf";
	}
	
	public static String getStaticFilesImgDir() {
		return sysParameterService.getSysParameterValue(SysParamEnum.SYS_PARAM_UPLOAD_FOLDER
			.code()) + File.separator + "uploadfile" + File.separator + "images";
	}
	
	public static String[] getFilePath(HttpServletRequest req, String fileRoot, String dir,
										String fileOrgName) {
		String[] pathArray = new String[2];
		
		String uploadFolder = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_UPLOAD_FOLDER.code());
		
		String serverUrl = sysParameterService
			.getSysParameterValue(SysParamEnum.SYS_PARAM_IMAGE_SERVER_URL.code());
		
		if (StringUtil.isNotEmpty(uploadFolder)) {
			pathArray[0] = uploadFolder + File.separator;
			pathArray[1] = serverUrl + "/";
			pathArray = getFilePaths(uploadFolder, serverUrl, fileRoot, dir, fileOrgName, "110");
		} else {
			WebApplicationContext wac = (WebApplicationContext) req
				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			ServletContext context = wac.getServletContext();
			pathArray[0] = context.getRealPath("") + File.separator;
			pathArray[1] = "";
			pathArray = getFilePaths(pathArray[0], pathArray[1], fileRoot, dir, fileOrgName, "110");
		}
		return pathArray;
	}
	
	private static long getFileNameRandom(Calendar cal) {
		long temp = cal.get(Calendar.MINUTE) * 60l * 1000l;
		long temp1 = cal.get(Calendar.SECOND) * 1000l;
		long temp2 = cal.get(Calendar.MILLISECOND);
		
		long time = temp + temp1 + temp2;
		
		time = time * 1000l + newRandom.nextInt(1000);
		
		return time;
	}
	
	public static String[] getFilePaths(String realRootPath, String httpRootPath, String moudleDir,
										String dirName, String fileOrgName, String filePrefix) {
		if (StringUtil.isEmpty(realRootPath) && StringUtil.isEmpty(httpRootPath)) {
			realRootPath = ApplicationConstant.FILE_PATH_ROOT;
			httpRootPath = ApplicationConstant.HTTP_DOMAIN_URL + "/"
							+ ApplicationConstant.HTTP_PATH_ROOT;
		}
		if (StringUtil.isEmpty(filePrefix))
			filePrefix = "";
		else
			filePrefix = filePrefix + "_";
		String[] pathArray = new String[2];
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String strMonth = month < 10 ? "0" + month : String.valueOf(month);
		String strDay = day < 10 ? "0" + day : String.valueOf(day);
		String realPath = realRootPath + File.separator + moudleDir + File.separator + dirName
							+ File.separator + year + "-" + strMonth + File.separator + strDay;
		String httpUrl = httpRootPath + "/" + moudleDir + "/" + dirName + "/" + year + "-"
							+ strMonth + "/" + strDay;
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String filePath = "";
		String newFileName = "";
		int maxCount = 1000;
		int count = 0;
		
		while (count < maxCount) {
			long time = getFileNameRandom(cal);
			String fileName = fileOrgName;
			String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
			newFileName = filePrefix + time + "." + extName;
			filePath = realPath + File.separator + newFileName;
			File file = new File(filePath);
			if (!file.exists()) {
				break;
			} else
				logger.warn("filePath repeat " + filePath);
			count++;
			if (count >= maxCount) {
				logger.error("save file error 1000 time " + filePath);
				return pathArray;
			}
		}
		pathArray[0] = filePath;
		pathArray[1] = httpUrl + "/" + newFileName;
		return pathArray;
	}
}
