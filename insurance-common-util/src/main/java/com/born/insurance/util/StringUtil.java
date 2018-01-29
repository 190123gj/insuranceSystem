package com.born.insurance.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.born.insurance.util.token.TokenProccessor;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;
import com.yjf.common.net.HttpRequest;

public class StringUtil extends com.yjf.common.lang.util.StringUtil {
	
	static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	static final Pattern IP_PATTERN = Pattern
		.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
	
	/**
	 * subString("acd中央人民广播电台", 5, "..") 返回： “acd中央人民..”
	 * @param text 原始字符串
	 * @param length 截取长度
	 * @param endWith 超过截取长度时，用来什么字符串省略代替，
	 * @return subString("acd中央人民广播电台", 5, "..") 返回： “acd中央人民..”
	 */
	public static String subString(String text, int length, String endWith) {
		if (isBlank(text))
			return "";
		int textLength = text.length();
		int byteLength = 0;
		StringBuffer returnStr = new StringBuffer();
		for (int i = 0; i < textLength && byteLength < length * 2; i++) {
			String str_i = text.substring(i, i + 1);
			if (str_i.getBytes().length == 1) {//英文   
				byteLength++;
			} else {//中文   
				byteLength += 2;
			}
			returnStr.append(str_i);
		}
		try {
			if (byteLength < text.getBytes("GBK").length) {//getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3   
				returnStr.append(endWith);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return returnStr.toString();
	}
	
	/**
	 * 
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签
	 * <p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		if (isBlank(str)) {
			return "";
		}
		Pattern pattern = Pattern.compile("<([^>]*)>");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 
	 * 基本功能：过滤引号
	 * <p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterXSS(String str) {
		if (isBlank(str)) {
			return "";
		}
		Pattern pattern = Pattern.compile("\"|\'");
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	public static String nullToEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
	 * <p/>
	 * 
	 * <pre>
     * StringUtil.isNotEmpty(null)      = false
     * StringUtil.isNotEmpty("")        = false
     * StringUtil.isNotEmpty(" ")       = true
     * StringUtil.isNotEmpty("bob")     = true
     * StringUtil.isNotEmpty("  bob  ") = true
     * </pre>
	 * 
	 * @param str 要检查的字符串
	 * @return 如果不为空, 则返回<code>true</code>
	 */
	public static boolean isNotEmpty(String str) {
		str = replaceBlank(str);
		return ((str != null) && (str.length() > 0));
	}
	
	/**
	 * 生成maken
	 * @param request
	 * @return
	 */
	public String getToken(HttpServletRequest request){
		
		return (String) request.getSession().getAttribute("token");
	}
	/**
	 * 前面start位字符， 后面end位字符， 中间用endWith
	 * */
	public static String subString2(String text, int start, int end, String endWith) {
		if (isBlank(text))
			return "";
		int textLength = text.length();
		String returnStr;
		if (textLength > start + end) {
			returnStr = text.substring(0, start) + endWith
						+ text.substring(textLength - end, textLength);
		} else if (textLength > start && (textLength < start + end || textLength == start + end)) {
			returnStr = text.substring(0, start) + endWith;
		} else {
			returnStr = text.substring(0, 1) + endWith;
		}
		
		return returnStr.toString();
	}
	
	/**
	 * 前面start位字符， 后面end位字符， 中间用endWith替换每个字符
	 * */
	public static String subString3(String text, int start, int end, String endWith) {
		if (isBlank(text))
			return "";
		int textLength = text.length();
		String returnStr;
		String endWith2 = "";
		if (textLength > start + end) {
			for (int i = 0; i < textLength - (start + end); i++) {
				endWith2 = endWith2 + endWith;
			}
			returnStr = text.substring(0, start) + endWith2
						+ text.substring(textLength - end, textLength);
		} else if (textLength > start && (textLength < start + end || textLength == start + end)) {
			for (int i = 0; i < textLength - start; i++) {
				endWith2 = endWith2 + endWith;
			}
			returnStr = text.substring(0, start) + endWith2;
		} else {
			for (int i = 0; i < textLength - 1; i++) {
				endWith2 = endWith2 + endWith;
			}
			returnStr = text.substring(0, 1) + endWith2;
		}
		
		return returnStr.toString();
	}
	
	/**
	 * 截取text中某两个字符间的字符串
	 * @Param text 原字段
	 * @param startStr 起始字符
	 * @param endString 结束字符
	 * @param includeStr 是否包含目标字符
	 * */
	public static String subByStr(String text, String startStr, String endString, Boolean includeStr) {
		String returnStr = "";
		if (isBlank(text))
			return returnStr;
		int strIndex = text.indexOf(startStr);
		int endIndex = text.lastIndexOf(endString);
		if (strIndex > -1 && endIndex > -1) {
			if (includeStr) {
				returnStr = text.substring(strIndex, endIndex + 1);
			} else {
				returnStr = text.substring(strIndex + 1, endIndex);
			}
		} else {
			returnStr = text;
		}
		
		return returnStr.toString();
	}
	
	/** 中文字符转码 */
	public static String base64Encoder(String str) {
		if (StringUtil.isNotEmpty(str)) {
			try {
				return new BASE64Encoder().encode(str.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				logger.info("中文编码：UnsupportedEncodingException", e);
			}
		}
		
		return str;
	}
	
	/** 中文字符解码码 */
	public static String base64Decoder(String str) {
		if (StringUtil.isNotEmpty(str)) {
			try {
				return new String(new BASE64Decoder().decodeBuffer(str), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.info("中文解码：UnsupportedEncodingException", e);
			} catch (IOException e) {
				logger.info("中文解码：IOException", e);
			}
		}
		
		return str;
	}
	
	public static String replaceStrToStr(String source, String str0, String replace) {
		
		if (StringUtil.isNotEmpty(source)) {
			if (source.indexOf(str0) > -1) {
				source = source.replace(str0, replace);
			}
		}
		return source;
	}
	
	/**
	 * 左填充
	 * @param source
	 * @param len
	 * @param pad
	 * @return
	 */
	public static String leftPad(String source, int len, String pad) {
		if (source.length() >= len) {
			return source;
		} else {
			int padlen = len - source.length();
			for (int i = 0; i < padlen; i++) {
				source = pad + source;
			}
			return source;
		}
	}
	
	/**
	 * 右填充
	 * @param source
	 * @param len
	 * @param pad
	 * @return
	 */
	public static String rightPad(String source, int len, String pad) {
		if (source.length() >= len) {
			return source;
		} else {
			int padlen = len - source.length();
			for (int i = 0; i < padlen; i++) {
				source += pad;
			}
			return source;
		}
	}
	
	
	  public static Random r = new Random();
	     
	    public static String getRandom(){
	        long num = Math.abs(r.nextLong() % 10000000000L);
	        String s = String.valueOf(num);
	        for(int i = 0; i < 10-s.length(); i++){
	            s = "0" + s;
	        }
	         
	        return s;
	    }
	
	/**
	 * escape
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		
		if (isBlank(src)) {
			return "";
		}
		
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	/**
	 * unescape
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		
		if (isBlank(src)) {
			return "";
		}
		
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 是否IP地址
	 * @param addr
	 * @return
	 */
	public static boolean isIp(String addr) {
		if (addr == null || addr.length() < 7 || addr.length() > 15) {
			return false;
		}
		Matcher mat = IP_PATTERN.matcher(addr);
		return mat.matches();
	}
	
	/**
	 * encodeURI utf-8
	 * @param str
	 * @return
	 */
	public static String encodeURI(String str) {
		return encodeURI(str, "utf-8");
	}
	
	/**
	 * encodeURI
	 * @param str
	 * @param charSet
	 * @return
	 */
	public static String encodeURI(String str, String charSet) {
		if (str == null)
			return str;
		try {
			if (isBlank(charSet))
				charSet = "utf-8";
			str = URLEncoder.encode(str, charSet);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return str;
	}
	
	/**
	 * decodeURI utf-8
	 * @param str
	 * @return
	 */
	public static String decodeURI(String str) {
		return decodeURI(str, "utf-8");
	}
	
	/**
	 * decodeURI
	 * @param str
	 * @param charSet
	 * @return
	 */
	public static String decodeURI(String str, String charSet) {
		if (str == null)
			return str;
		try {
			if (isBlank(charSet))
				charSet = "utf-8";
			str = URLDecoder.decode(str, charSet);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return str;
	}
	
	/**
	 * 页面展示时候还原texarea格式
	 * @param str
	 * @return
	 */
	public static String textareaString(String str) {
		if (isBlank(str))
			return "";
		return str.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;");
	}
	
}
