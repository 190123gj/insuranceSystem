package com.born.insurance.util.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.io.IOUtils;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class FcsRSACoder extends Coder {
	protected static final Logger logger = LoggerFactory.getLogger(FcsRSACoder.class);
	
	public static final String KEY_ALGORITHM = "RSA";
	//"-----BEGIN PRIVATE KEY-----"
	private static String yijiuPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPPNt40uxmDcB3BS"
											+ "KlsFtfkGTG0Be3Z5moY6V2/ceI4Bg+UpN80BXl1LIK70HvfWsvgO3fU52+AmGK2g"
											+ "2QzgMCrqQOYldE4FWRP9J+hRYg6Neb+Oame4hLRWPSpeB4qr+XD6NWoUXmF5yItE"
											+ "A2fmvZWdpFoiSdZkH6Lhga1sl1BPAgMBAAECgYBhzg4rii81Hi+xxJSPTVwyGW/B"
											+ "bw3Len8eB/uZuXV3am4yGXX0PO9RN9lVtkNSI4RKdRRf0yU856uQw+0u+CT52eI5"
											+ "fbJZba1g4oBDLBovMyZI0dUJZMAV2zJZNDxXAX2j/u3ZVSzkeeNTKbQ/NyXrKcaq"
											+ "xc7+yA7uFQfoy4qFQQJBAP6pYOzp+WSzr2Sr1U1mtPmYX1nCN0Teu/omahbDT+7w"
											+ "R5UfAWrH6CkfMmmo0Utgbh/Y+ouX6deXJ7Ml5Be/xCECQQD1FbrfmkJmNxW0zIam"
											+ "kdXAqCuTPEyYIPvSyvF7/2yaqEMp4xTnLtW2l5iBKseSP6NK8tUzBw9jVoS0VRbG"
											+ "yIZvAkEAhjnio50DXYe0B7zmVcCv3OrqPxY2KW+45rW+rzbM9+Tf5gKMraqmfJ47"
											+ "5SMdBbzS7qhgDpnIoGDEhRGQss/Z4QJAF+hTv2Yz3fa3plhhNjR5rn55KbazHg/x"
											+ "oMFtRxRGitupGZfuPRMDg/lLxiXfK/QLQM9pXr3skVsqPNEkFKYQ/wJAEtixUzoO"
											+ "1iQzRSj6s95XYAtHwEPkrwe9reOjDNZ3X3Wsx9ryXgNuXakxfVR9eOENnjp9nLuH"
											+ "WtiPrpbz/jL4gw==";
	//		+ "-----END PRIVATE KEY-----"
	//"-----BEGIN PUBLIC KEY-----"
	private static String yijiuPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDzzbeNLsZg3AdwUipbBbX5Bkxt"
											+ "AXt2eZqGOldv3HiOAYPlKTfNAV5dSyCu9B731rL4Dt31OdvgJhitoNkM4DAq6kDm"
											+ "JXROBVkT/SfoUWIOjXm/jmpnuIS0Vj0qXgeKq/lw+jVqFF5heciLRANn5r2VnaRa"
											+ "IknWZB+i4YGtbJdQTwIDAQAB";
	//		+ "-----END PUBLIC KEY-----";
	private static final String RSA_PUBKEY_FILENAME = "/security/rsa_public_key.pem";
	private static final String RSA_PRIKEY_FILENAME = "/security/private_key.pem";
	private static RSAPrivateKey privateKey = null;
	private static RSAPublicKey publicKey = null;
	private static File yijiuPublicKeyFile = null;
	private static File yijiuPrivateKeyFile = null;
	static final int KEY_SIZE = 1024;
	
	static {
		yijiuPublicKeyFile = new File(getRSAPairFilePath(RSA_PUBKEY_FILENAME));
		yijiuPrivateKeyFile = new File(getRSAPairFilePath(RSA_PRIKEY_FILENAME));
		try {
			readKeyPair();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 同步读出保存的密钥对
	private static Object readKeyPair() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(yijiuPrivateKeyFile));
			String s = br.readLine();
			StringBuffer privatekey = new StringBuffer();
			s = br.readLine();
			while (s.charAt(0) != '-') {
				privatekey.append(s + "\r");
				s = br.readLine();
			}
			yijiuPrivateKey = privatekey.toString();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(fis);
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(yijiuPublicKeyFile));
			String s = br.readLine();
			StringBuffer publicKey = new StringBuffer();
			s = br.readLine();
			while (s.charAt(0) != '-') {
				publicKey.append(s + "\r");
				s = br.readLine();
			}
			yijiuPublicKey = publicKey.toString();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(fis);
		}
		return null;
	}
	
	/**
	 * 返回生成/读取的密钥对文件的路径。
	 */
	private static String getRSAPairFilePath(String fileName) {
		String urlPath = RSACoder.class.getResource("/").getPath();
		return (new File(urlPath).getParent() + fileName);
	}
	
	public static String getDefaultPrivateKey() throws Exception {
		return yijiuPrivateKey;
	}
	
	public static String getDefaultPublicKey() throws Exception {
		return yijiuPublicKey;
	}
	
	public static String getPrivateKey() throws Exception {
		byte[] keybyte = decryptBASE64(yijiuPrivateKey);
		
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);
		
		Key privateKey = kf.generatePrivate(keySpec);
		return encryptBASE64(privateKey.getEncoded());
	}
	
	public static String getPublicKey() throws Exception {
		byte[] keybyte = decryptBASE64(yijiuPublicKey);
		
		KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keybyte);
		
		Key publicKey = kf.generatePublic(keySpec);
		return encryptBASE64(publicKey.getEncoded());
	}
	
	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);
		
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		return cipher.doFinal(data);
	}
	
	/**
	 * 加密<br>
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		return cipher.doFinal(data);
	}
	
	public static void main(String[] args) {
		System.out.println(RSACoder.class.getResource("/").getPath());
	}
}
