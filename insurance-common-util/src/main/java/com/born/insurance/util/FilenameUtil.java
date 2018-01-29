package com.born.insurance.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.web.context.WebApplicationContext;

import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
/**
 * 
 *                       
 * @Filename FilenameUtil.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author chenxu
 *
 * @Email chenxu@yiji.com
 *       
 * @History
 *<li>Author: chenxu</li>
 *<li>Date: 2012-7-26</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class FilenameUtil {
	public static String getClassfold() {
		return FilenameUtil.class.getResource("/").getPath();
	}
	
	public static File getClassfoldFile() {
		return new File(getClassfold());
	}
	
	public static String getWebfold() {
		return getWebfoldFile().getPath();
	}
	
	public static File getWebfoldFile() {
		return getClassfoldFile().getParentFile().getParentFile();
	}
	
	public static String getFolderFileName(String fileName){
		String fileDirs ="personal"+File.separator
				+DateUtil.dtSimpleYmFormat(new Date())+File.separator+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		if(StringUtil.isNotBlank(fileName))
			fileDirs +=File.separator+fileName;
		return fileDirs;
	}
	
	public static String getWebAppPath(WebApplicationContext wac){
		
		return wac.getServletContext().getRealPath("");
	}
	
	public static String getRealNameCertFileName(String userName,String fileName) throws UnsupportedEncodingException{			
		 return userName+"_"+fileName;		
	}
}
