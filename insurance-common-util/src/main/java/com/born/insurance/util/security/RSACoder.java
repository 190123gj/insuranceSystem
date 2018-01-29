package com.born.insurance.util.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * RSA安全编码组件
 * 
 * @author network
 * @version 1.0
 * @since 1.0
 */
public abstract class RSACoder extends Coder {
	protected static final Logger logger = LoggerFactory.getLogger(RSACoder.class);
	
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	/** 默认的安全服务提供者 */
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	private static final String RSA_PUBKEY_FILENAME = "/__YIJIU_RSA_PUBKEY.txt";
	private static final String RSA_PRIKEY_FILENAME = "/__YIJIU_RSA_PRIKEY.txt";
	/** 缓存的密钥对。 */
	//private static KeyPair oneKeyPair = null;
	/** 缓存的密钥对。 */
	private static RSAPublicKey yijiuPublicKey = null;
	private static RSAPrivateKey yijiuPrivateKey = null;
	private static File yijiuPublicKeyFile = null;
	private static File yijiuPrivateKeyFile = null;
	static final int KEY_SIZE = 1024;
	
	static {
		yijiuPublicKeyFile = new File(getRSAPairFilePath(RSA_PUBKEY_FILENAME));
		yijiuPrivateKeyFile = new File(getRSAPairFilePath(RSA_PRIKEY_FILENAME));
	}
	
	/**
	 * 返回生成/读取的密钥对文件的路径。
	 */
	private static String getRSAPairFilePath(String fileName) {
		String urlPath = RSACoder.class.getResource("/").getPath();
		return (new File(urlPath).getParent() + fileName);
	}
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data 加密数据
	 * @param privateKey 私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);
		
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		
		return encryptBASE64(signature.sign());
	}
	
	/**
	 * 校验数字签名
	 * 
	 * @param data 加密数据
	 * @param publicKey 公钥
	 * @param sign 数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		
		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);
		
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		
		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
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
	
	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		
		return encryptBASE64(key.getEncoded());
	}
	
	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		
		return encryptBASE64(key.getEncoded());
	}
	
	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM,
			new org.bouncycastle.jce.provider.BouncyCastleProvider());
		keyPairGen.initialize(KEY_SIZE,
			new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes()));
		
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * 初始化密钥Map
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Object initKeyMap() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
		keyPairGen.initialize(KEY_SIZE,
			new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes()));
		
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		saveKeyPair(publicKey, "public");
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		saveKeyPair(privateKey, "private");
		Map<String, Object> keyPairMap = new HashMap<String, Object>(2);
		keyPairMap.put(PUBLIC_KEY, publicKey);
		keyPairMap.put(PRIVATE_KEY, privateKey);
		
		return keyPairMap;
	}
	
	/**
	 * 将指定的RSA密钥对以文件形式保存。
	 * 
	 * @param keyPair 要保存的密钥对。
	 */
	private static void saveKeyPair(Object rsakey, String type) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			if ("public".equals(type)) {
				fos = FileUtils.openOutputStream(yijiuPublicKeyFile);
			} else {
				fos = FileUtils.openOutputStream(yijiuPrivateKeyFile);
			}
			oos = new ObjectOutputStream(fos);
			oos.writeObject(rsakey);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(fos);
		}
	}
	
	/**
	 * 返回RSA密钥对。
	 */
	public static Object getKeyPair(String type) {
		// 首先判断是否需要重新生成新的密钥对文件
		if (isCreateKeyPairFile(type)) {
			// 直接强制生成密钥对文件，并存入缓存。
			
		}
		if ("public".equals(type)) {
			if (yijiuPublicKey != null) {
				return yijiuPublicKey;
			}
		} else {
			if (yijiuPrivateKey != null) {
				return yijiuPrivateKey;
			}
		}
		
		return readKeyPair(type);
	}
	
	// 同步读出保存的密钥对
	private static Object readKeyPair(String type) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			if ("public".equals(type)) {
				fis = FileUtils.openInputStream(yijiuPublicKeyFile);
				ois = new ObjectInputStream(fis);
				yijiuPublicKey = (RSAPublicKey) ois.readObject();
				return yijiuPublicKey;
			} else {
				fis = FileUtils.openInputStream(yijiuPrivateKeyFile);
				ois = new ObjectInputStream(fis);
				yijiuPrivateKey = (RSAPrivateKey) ois.readObject();
				return yijiuPrivateKey;
			}
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
	 * 若需要创建新的密钥对文件，则返回 {@code true}，否则 {@code false}。
	 */
	private static boolean isCreateKeyPairFile(String type) {
		// 是否创建新的密钥对文件
		boolean createNewKeyPair = false;
		if ("public".equals(type)) {
			if (!yijiuPublicKeyFile.exists() || yijiuPublicKeyFile.isDirectory()) {
				createNewKeyPair = true;
			}
		} else {
			if (!yijiuPrivateKeyFile.exists() || yijiuPrivateKeyFile.isDirectory()) {
				createNewKeyPair = true;
			}
		}
		return createNewKeyPair;
	}
	
	public static String getDefaultPublicKey() throws Exception {
		Key key = (Key) getKeyPair("public");
		return encryptBASE64(key.getEncoded());
	}
	
	public static String getDefaultPrivateKey() throws Exception {
		Key key = (Key) getKeyPair("private");
		return encryptBASE64(key.getEncoded());
	}
	
	/**
	 * 初始化密钥Map
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> generateKeyMap() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
		keyPairGen.initialize(KEY_SIZE,
			new SecureRandom(DateFormatUtils.format(new Date(), "yyyyMMdd").getBytes()));
		
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyPairMap = new HashMap<String, Object>(2);
		keyPairMap.put(PUBLIC_KEY, encryptBASE64(publicKey.getEncoded()));
		keyPairMap.put(PRIVATE_KEY, encryptBASE64(privateKey.getEncoded()));
		return keyPairMap;
	}
}
