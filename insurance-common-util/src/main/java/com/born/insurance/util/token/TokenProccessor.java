package com.born.insurance.util.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * token生成
 * @author jenny
 *
 */

public class TokenProccessor {
	 /* 
     *单例设计模式（保证类的对象在内存中只有一个） 
     *1、把类的构造函数私有 
     *2、自己创建一个类的对象 
     *3、对外提供一个公共的方法，返回类的对象 
     */  
    private TokenProccessor(){}  
      
    private static final TokenProccessor instance = new TokenProccessor();  
    
    /** 
     * 返回类的对象 
     * @return 
     */  
    public static TokenProccessor getInstance(){  
        return instance;  
    }  
    
    /** 
     * 生成Token 
     * Token：Nv6RRuGEVvmGjB+jimI/gw== 
     * @return 
     */  
    public String makeToken(){  //checkException  
        //  7346734837483  834u938493493849384  43434384  
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";  
        //数据指纹   128位长   16个字节  md5  
        try {  
            MessageDigest md = MessageDigest.getInstance("md5");  
            byte md5[] =  md.digest(token.getBytes());  
            //base64编码--任意二进制编码明文字符   adfsdfsdfsf  
            BASE64Encoder encoder = new BASE64Encoder();  
            return encoder.encode(md5);  
        } catch (NoSuchAlgorithmException e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
	 /** 
     * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致 
     *  
     * @param request 
     * @return true 用户重复提交了表单 false 用户没有重复提交表单 
     */  
    public static boolean isRepeatSubmit(HttpServletRequest request) {  
        String client_token = request.getParameter("token");  
        // 1、如果用户提交的表单数据中没有token，则用户是重复提交了表单  
        if (client_token == null) {  
            return true;  
        }  
        // 取出存储在Session中的token  
        String server_token = (String) request.getSession().getAttribute("token");  
        // 2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单  
        if (server_token == null) {  
            return true;  
        }  
        // 3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单  
        if (!client_token.equals(server_token)) {  
            return true;  
        }  
  
        return false;  
    } 
}
