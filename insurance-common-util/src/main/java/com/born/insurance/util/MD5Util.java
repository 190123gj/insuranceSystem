/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package com.born.insurance.util;

import java.security.MessageDigest;

import rop.thirdparty.org.apache.commons.codec.digest.DigestUtils;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class MD5Util {
	protected static final Logger logger = LoggerFactory.getLogger(MD5Util.class);
	
	public static String getMD5_16(String plainText) {
		String md5_16bit = null;
		try {
			md5_16bit = getMD5_32(plainText).substring(8, 24);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return md5_16bit;
	}
	
	public static String getMD5_32(String plainText) {
		String md5_32bit = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			md5_32bit = buf.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return md5_32bit;
	}
	
	public static String getSha256Hex(String plainText) {
		String encryptedPWD = DigestUtils.sha256Hex(plainText);
		return encryptedPWD;
	}
	
	public static void main(String[] args) {
		
		System.out.println(MD5Util.getSha256Hex("a123456"));
		System.out.println(MD5Util.getMD5_16("1234567"));
		System.out.println(MD5Util.getMD5_32("123456"));
	}
	
}
