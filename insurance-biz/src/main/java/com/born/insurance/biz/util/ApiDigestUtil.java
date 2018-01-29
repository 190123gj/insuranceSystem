package com.born.insurance.biz.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yjf.common.lang.security.DigestUtil.DigestALGEnum;
import com.yjf.common.lang.util.StringUtil;

/**
 * @Description: 签名帮助类
 * @author xiaohui@yiji.com
 * @date 2015-12-15 上午10:06:25
 * @version V1.0
 */
public final class ApiDigestUtil {
	private static String OPENAPIURL = "http://assembleapi.yijifu.net/gateway.html";
	private static Logger logger = LoggerFactory.getLogger(ApiDigestUtil.class);
	private static String SECURITYKEY = "be7c713d08194fac983036924586be09";
	
	/**
	 * 校验签名、如果校验失败throws Exception
	 * 
	 * @param dataMap 待签名kv
	 * @param securityCheckKey 安全密钥
	 * @param de
	 * @param encoding
	 * @return
	 */
	public static void check(Map<String, String> dataMap, String securityCheckKey,
								DigestALGEnum de, String encoding) {
		if (de == null) {
			de = DigestALGEnum.MD5;
		} else if (encoding == null || "".equals(encoding)) {
			encoding = "UTF-8";
		}
		
		String sign = execute(dataMap, securityCheckKey, de, encoding);
		String returnSign = dataMap.get("sign");
		if (!sign.equals(returnSign)) {
			throw new RuntimeException("校验签名失败");
		}
	}
	
	/**
	 * 校验签名、如果校验失败throws Exception
	 * 
	 * @param dataMap 待签名kv
	 * @param securityCheckKey 安全密钥
	 * @param de
	 * @param encoding
	 * @return
	 */
	public static void check(Map<String, String> dataMap, String securityCheckKey) {
		DigestALGEnum de = DigestALGEnum.MD5;
		String encoding = "UTF-8";
		String sign = execute(dataMap, StringUtil.defaultIfBlank(securityCheckKey, SECURITYKEY),
			de, encoding);
		String returnSign = dataMap.get("sign");
		if (!sign.equals(returnSign)) {
			throw new RuntimeException("校验签名失败");
		}
	}
	
	/**
	 * 对 dataMap 的内容进行签名，并将签名结果放入 dataMap 中
	 * 
	 * @param dataMap 待签名kv
	 * @param securityCheckKey 安全密钥
	 * @param de
	 * @param encoding
	 * @return
	 */
	public static void check(HttpServletRequest request, String securityCheckKey, DigestALGEnum de,
								String encoding) {
		Map<String, String[]> paramMap = request.getParameterMap();
		check(toMap(paramMap), securityCheckKey, de, encoding);
	}
	
	/**
	 * 对 dataMap 的内容进行签名，并返回签名值
	 * 
	 * @param dataMap 待签名kv
	 * @param securityCheckKey 安全密钥
	 * @param de 签名算法
	 * @param encoding 签名编码名称
	 */
	public static String execute(Map<String, String> dataMap, String securityCheckKey,
									DigestALGEnum de, String encoding) {
		if (dataMap == null) {
			throw new IllegalArgumentException("数据不能为空");
		}
		if (securityCheckKey == null || "".equals(securityCheckKey)) {
			throw new IllegalArgumentException("安全校验码数据不能为空");
		}
		if (de == null) {
			throw new IllegalArgumentException("摘要算法不能为空");
		}
		if (encoding == null || "".equals(encoding)) {
			throw new IllegalArgumentException("字符集不能为空");
		}
		
		String message = sort(dataMap) + securityCheckKey;
		
		/** 执行签名 **/
		byte[] toDigest;
		try {
			logger.info("待签名字符串==>{}", message);
			toDigest = message.getBytes(encoding);
			MessageDigest md = MessageDigest.getInstance(de.getName());
			md.update(toDigest);
			return new String(Hex.encodeHex(md.digest()));
		} catch (Exception e) {
			throw new RuntimeException("签名失败", e);
		}
	}
	
	/**
	 * 排序
	 * @param paramMap
	 * @return
	 */
	public static String sort(Map<String, String> paramMap) {
		/** 按a~z、A~Z排序 **/
		TreeMap<String, String> treeMap = new TreeMap<String, String>(paramMap);
		
		/** 拼接签名字符串 **/
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			if ("sign".equals(entry.getKey())) {
				continue;
			}
			sb.append(entry.getKey()).append("=").append(defaultIfBlank(entry.getValue(), ""))
				.append("&");
		}
		
		/** 整理字符串 **/
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**
	 * 为空默认
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	private static String defaultIfBlank(String value, String defaultValue) {
		if (value == null || "".equals(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}
	
	/**
	 * 将 Map<String, String[]> -> Map<String, String>
	 * 
	 * @param paramMap
	 * @return 转换完成的集合
	 */
	public static Map<String, String> toMap(Map<String, String[]> paramMap) {
		Map<String, String> returnMap = new HashMap<String, String>();
		if (paramMap != null) {
			for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
				returnMap.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return returnMap;
	}
	
	public static void digest(Map<String, String> paramMap, String securityKey) {
		
		String sign = execute(paramMap, securityKey, DigestALGEnum.MD5, "UTF-8");
		paramMap.put("sign", sign);
		
	}
	
	public static String makeUrl(Map<String, String> paramMap, String httpUrl, String securityKey)
																									throws UnsupportedEncodingException {
		String url = "";
		digest(paramMap, securityKey);
		if (paramMap != null && !paramMap.isEmpty()) {
			int i = 0;
			for (String s : paramMap.keySet()) {
				if (i == 0) {
					url += "?" + s + "=" + URLEncoder.encode(paramMap.get(s), "UTF-8");
					i++;
				}
				
				else
					url += "&" + s + "=" + URLEncoder.encode(paramMap.get(s), "UTF-8");
			}
			return StringUtil.defaultIfBlank(httpUrl, OPENAPIURL) + url;
		} else {
			return "";
		}
		
	}
}
