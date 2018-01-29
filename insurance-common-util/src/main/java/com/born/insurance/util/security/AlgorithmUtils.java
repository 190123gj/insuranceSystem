package com.born.insurance.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 算法
 * 
 * @author Administrator
 * 
 */
public final class AlgorithmUtils {
	private static Logger logger = LoggerFactory.getLogger(AlgorithmUtils.class);
	
	/**
	 * 16进制字符串转化为字节数组
	 * 
	 * @param strhex
	 * @return
	 */
	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}
	
	/**
	 * 字节数组转化为16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1) {
				hs = hs.append("0").append(stmp);
			} else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}
	
	/**
	 * 加密
	 * 
	 * @param src
	 * @param key
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key, String type) throws Exception {
		byte[] reuslt = null;
		SecretKeySpec sKeySpec = new SecretKeySpec(key, type);
		Cipher cipher = Cipher.getInstance(type);
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
		reuslt = cipher.doFinal(src);
		return reuslt;
	}
	
	/**
	 * 解密
	 * 
	 * @param src
	 * @param key
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key, String type) throws Exception {
		byte[] reuslt = null;
		SecretKeySpec sKeySpec = new SecretKeySpec(key, type);
		Cipher cipher = Cipher.getInstance(type);
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
		reuslt = cipher.doFinal(src);
		return reuslt;
	}
	
	/**
	 * 获得单向加密
	 * 
	 * @param encryptType
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getOneWayEncrypt(String encryptType, String data) {
		String result = null;
		if (encryptType == null || data == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
			messageDigest.update(data.getBytes("UTF-8"));
			result = byte2hex(messageDigest.digest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.out.println("获得单向加密异常-------------->" + e.toString());
		}
		return result;
	}
}
