package com.born.insurance.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class AESCoder extends Coder {
	protected static final Logger logger = LoggerFactory.getLogger(AESCoder.class);
	
	/**
	 * 加密
	 * 
	 * @param content 需要加密的内容
	 * @param password 加密密码
	 * @return
	 */
	public static String encrypt(String content, String securityKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(securityKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return new String(Hex.encodeHex(result));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	public static String decrypt(byte[] content, String securityKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(securityKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return new String(result); // 解密
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
}
