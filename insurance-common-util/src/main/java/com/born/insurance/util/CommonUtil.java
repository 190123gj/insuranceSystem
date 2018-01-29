/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename CommonUtil.java
 * 
 * @Description 通用工具类
 * 
 * @Version 1.0
 * 
 * @Author chenrui
 * 
 * @Email chenrui@yiji.com
 * 
 * @History <li>Author: chenrui</li> <li>Date: 2013-5-8 下午5:50:55</li> <li>
 * Version: 1.0</li> <li>Content: create</li>
 */
public class CommonUtil {
	protected static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	//默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	/**
	 * 设置结果信息
	 * 
	 * @param result
	 * @param isSuccess
	 * @param message
	 */
	public static void setResultInfo(ResultBase result, boolean isSuccess, String message) {
		result.setSuccess(isSuccess);
		result.setMessage(message);
	}
	
	/**
	 * 手机号码验证
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		if (StringUtil.isBlank(mobile) || mobile.length() != 11) {
			return false;
		}
		if (!StringUtil.isNumeric(mobile)) {
			return false;
		}
		Pattern p = Pattern
			.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\\d{8})$");
		Matcher m = p.matcher(mobile);
		if (m.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 身份证号码验证
	 * 
	 * @param cardNum
	 * @return
	 */
	public static boolean checkIdentifyCardNum(String cardNum) {
		if (StringUtil.isBlank(cardNum)) {
			return false;
		}
		if (!(cardNum.length() == 15) && !(cardNum.length() == 18)) {
			return false;
		}
		String pre = StringUtil.substring(cardNum, 0, cardNum.length() - 1);
		String lastChar = StringUtil.substring(cardNum, cardNum.length() - 1, cardNum.length());
		if (StringUtil.isNumeric(pre) && (StringUtil.isNumeric(lastChar))
			|| StringUtil.equalsIgnoreCase("x", lastChar)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 校验Email格式是否合法
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if (StringUtil.isEmpty(email) || !StringUtil.contains(email, "@")) {
			return false;
		}
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 隐藏字符串的一部分
	 * @param str
	 * @param start 开始隐藏的位置
	 * @param hidePartLength 隐藏的长度
	 * @return 转换过的字符串
	 */
	public static String hidePartNum(String str, int start, int hidePartLength) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		int length = str.length();
		if (start > length || hidePartLength > length || start + hidePartLength > length) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(StringUtil.substring(str, 0, start));
		sb.append(StringUtil.repeat("*", hidePartLength));
		sb.append(StringUtil.substring(str, start + hidePartLength));
		
		return sb.toString();
	}
	
	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double addDD(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static long mulLong(double v1, int n) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Integer.toString(n));
		return b1.multiply(b2).longValue();
	}
	
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v1));
		BigDecimal one = new BigDecimal(Double.toString(v2));
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 浮点数加法精度运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float addF(float v1, float v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));
		BigDecimal b2 = new BigDecimal(Float.toString(v2));
		return b1.add(b2).floatValue();
	}
	
	/**
	 * 浮点数与整数的乘法运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float mulF(float v1, int v2) {
		BigDecimal b1 = new BigDecimal(Float.toString(v1));
		BigDecimal b2 = new BigDecimal(Integer.toString(v2));
		return b1.multiply(b2).floatValue();
	}
	
	/**
	 * 浮点数与整数的乘法运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mulDI(double v1, int v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Integer.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 将字符串用“*”替换选择的字符
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String mask(String str, int start, int end) {
		String returnStr = "";
		int length = str.length();
		if (start > end) {
			return "";
		} else if (start >= 0 && length > end) {
			String f = str.substring(0, start);
			String z = str.substring(start, end);
			String b = str.substring(end, length);
			StringBuffer sb = new StringBuffer();
			StringBuffer sb1 = new StringBuffer();
			for (int i = 0; i < f.length(); i++) {
				sb.append("*");
			}
			for (int n = 0; n < b.length(); n++) {
				sb1.append("*");
			}
			returnStr = sb.toString() + z + sb1.toString();
		} else {
			return "";
		}
		
		return returnStr;
	}
	
	public static int getGender(String certNo) {
		if (StringUtil.isNotBlank(certNo)) {
			if (certNo.length() == 15) {
				String c = certNo.substring(14, 15);
				int n = Integer.parseInt(c);
				if (n % 2 != 0) {
					return 1;
				} else {
					return 0;
				}
			} else if (certNo.length() == 18) {
				String c = certNo.substring(16, 17);
				int n = Integer.parseInt(c);
				if (n % 2 != 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 3;
			}
		}
		return 3;
	}
	
	/**
	 * 更新session
	 * @param request
	 */
	public static void removeCookie(HttpServletRequest request) {
		request.getSession().invalidate();//清空session
		if (request.getCookies() != null) {
			Cookie cookie = request.getCookies()[0];//获取cookie
			cookie.setMaxAge(0);//让cookie过期
		}
	}
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 下载或预览文件
	 * @param request
	 * @param response
	 * @param type(downLoad-下载)
	 * @param filePath
	 * @param fileName(包含后辍名)
	 */
	public static void downLoadOrPreview(HttpServletRequest request, HttpServletResponse response,
											String type, String filePath, String fileName)
																							throws Exception {
		if (filePath == null && "".equals(filePath)) {
			return;
		}
		FileInputStream fis = null;
		BufferedInputStream buff = null;
		OutputStream servletOS = null;
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			buff = new BufferedInputStream(fis);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		if ("downLoad".equals(type)) {
			response.setContentType("application/octet-stream");
			try {
				response.addHeader("Content-Disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage(), e1);
				e1.printStackTrace();
			}
		}
		byte[] b = new byte[1024];
		long k = 0;
		try {
			servletOS = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 1024);
				k += j;
				servletOS.write(b, 0, j);
			}
			servletOS.flush();
			servletOS.close();
			fis.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	/**
	 * 验证文件路径是否为图片
	 * @param image
	 * @return
	 */
	public static boolean validationImage(String image) {
		String[] imageFormat = { "bmp", "gif", "jpg", "jpeg", "png", "tiff" };
		boolean flag = false;
		if (image != null && !"".equals(image)) {
			String extName = image.substring(image.lastIndexOf(".") + 1);
			for (String ext : imageFormat) {
				if (ext.equalsIgnoreCase(extName)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	public static void clearMap(Map<?, ?> object) {
		if (object != null) {
			object = null;
		}
	}
	
	public static String viewMoblie(String moblie) {
		
		if (StringUtil.isEmpty(moblie)) {
			return "";
		}
		String reMoblie = StringUtil.substring(moblie, 0, 3) + "****"
							+ StringUtil.substring(moblie, 7);
		
		return reMoblie;
		
	}
	
	public static String numberFormat(double value, int scale) {
		return String.format("%." + scale + "f", value);
	}
	
	public static JSONObject newJson(String jsonStr) {
		return (JSONObject) JSONObject.parse(jsonStr);
	}
	
	public static List<JSONObject> newJsonList(String jsonStr) {
		JSONArray array = (JSONArray) JSONArray.parse(jsonStr);
		List<JSONObject> list = Lists.newArrayList();
		Iterator<Object> it = array.iterator();
		while (it.hasNext()) {
			list.add((JSONObject) it.next());
		}
		return list;
	}
	
	/**
	 * 获取pm通用跳转地址
	 * @param url 不带任何参数的地址
	 * @return
	 */
	public static String getRedirectUrl(String url) {
		if (StringUtil.isNotBlank(url)) {
			String module = "projectMg";
			String[] stringSplit = url.split("/");
			if (stringSplit.length > 0)
				module = stringSplit[1];
			url = "/" + module + "/index.htm?systemNameDefautUrl=" + url;
		}
		return url;
	}
}
