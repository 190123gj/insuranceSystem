package com.born.insurance.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * httpClient工具类
 *
 * @author wuzj
 */
public class HttpClientUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static PoolingHttpClientConnectionManager connManager = null;
	
	private static CloseableHttpClient httpclient = null;
	
	/**
	 * 默认超时时间
	 */
	public final static int connectTimeout = 30000;
	
	static {
		try {
			SSLContext sslContext = SSLContexts.custom().useTLS().build();
			
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} }, null);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslContext)).build();
			
			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			httpclient = HttpClients.custom().setConnectionManager(connManager).build();
			// Create socket configuration
			SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
			connManager.setDefaultSocketConfig(socketConfig);
			// Create message constraints
			MessageConstraints messageConstraints = MessageConstraints.custom()
				.setMaxHeaderCount(200).setMaxLineLength(2000).build();
			// Create connection configuration
			ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(200);
			connManager.setDefaultMaxPerRoute(20);
		} catch (KeyManagementException e) {
			logger.error("KeyManagementException", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException", e);
		}
	}
	
	/**
	 * GET请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return
	 */
	public static String get(String url, Map<String, String> params) {
		return get(url, params, "UTF-8", connectTimeout);
	}
	
	/**
	 * POST请求
	 * @param reqURL 请求地址
	 * @param params 请求参数
	 * @return
	 */
	public static String post(String reqURL, Map<String, String> params) {
		return post(reqURL, params, "UTF-8", connectTimeout);
	}
	
	/**
	 * get请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param encode 编码
	 * @param timeOut 超时时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String get(String url, Map<String, String> params, String encode, int timeOut) {
		String responseString = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut)
			.setConnectTimeout(timeOut).setConnectionRequestTimeout(timeOut).build();
		
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		int i = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			if (i == 0 && !url.contains("?")) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(entry.getKey());
			sb.append("=");
			String value = entry.getValue();
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.warn("encode http get params error, value is " + value, e);
				sb.append(URLEncoder.encode(value));
			}
			i++;
		}
		logger.info("[HttpUtils Get] begin invoke:" + sb.toString());
		HttpGet get = new HttpGet(sb.toString());
		get.setConfig(requestConfig);
		
		try {
			CloseableHttpResponse response = httpclient.execute(get);
			try {
				HttpEntity entity = response.getEntity();
				try {
					if (entity != null) {
						responseString = EntityUtils.toString(entity, encode);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} catch (Exception e) {
				logger.error(
					String.format("[HttpUtils Get]get response error, url:%s", sb.toString()), e);
				return responseString;
			} finally {
				if (response != null) {
					response.close();
				}
			}
			logger.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:",
				sb.toString(), responseString));
		} catch (SocketTimeoutException e) {
			logger.error(
				String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()), e);
			return responseString;
		} catch (Exception e) {
			logger
				.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()), e);
		} finally {
			get.releaseConnection();
		}
		return responseString;
	}
	
	/**
	 * POST请求
	 * @param url 请求地址
	 * @param timeout 超时时间 毫秒
	 * @param map 参数
	 * @param encoding 编码
	 * @return
	 */
	public static String post(String reqURL, Map<String, String> params, String encoding,
								int timeout) {
		
		String responseContent = null;
		
		HttpPost httpPost = new HttpPost(reqURL);
		
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
				.setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();
			
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encoding));
			httpPost.setConfig(requestConfig);
			// 绑定到请求 Entry
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				// 执行POST请求
				HttpEntity entity = response.getEntity(); // 获取响应实体
				try {
					if (null != entity) {
						responseContent = EntityUtils.toString(entity, encoding);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
			logger.info("requestURL : " + reqURL + ", responseContent: " + responseContent);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} finally {
			httpPost.releaseConnection();
		}
		return responseContent;
	}
	
	/**
	 * POST请求
	 * @param serverUrl 请求地址
	 * @param data key=value&key=value
	 * @return
	 */
	public static String post(String serverUrl, String data) {
		return post(serverUrl, data, connectTimeout);
	}
	
	/**
	 * POST请求
	 * @param serverUrl 请求地址
	 * @param data key=value&key=value
	 * @param timeout
	 * @return
	 */
	public static String post(String serverUrl, String data, long timeout) {
		
		StringBuilder responseBuilder = null;
		BufferedReader reader = null;
		OutputStreamWriter wr = null;
		
		URL url;
		try {
			url = new URL(serverUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(1000 * 5);
			wr = new OutputStreamWriter(conn.getOutputStream());
			
			wr.write(data);
			wr.flush();
			
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			responseBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				responseBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			responseBuilder = new StringBuilder();
			responseBuilder.append("{'success':false,'message':'请求失败'}");
			logger.error("", e);
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					logger.error("close OutputStreamWriter error", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("close BufferedReader error", e);
				}
			}
			
		}
		return responseBuilder.toString();
	}
}
