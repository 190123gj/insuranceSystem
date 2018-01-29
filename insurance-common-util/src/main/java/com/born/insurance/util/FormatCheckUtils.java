package com.born.insurance.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式验证工具类
 * 
 * 
 * @author min
 * 
 */
public class FormatCheckUtils {
	
	/**
	 * 邮箱格式校验
	 * @param email
	 * @return
	 */
	public static boolean emailCheck(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}
	
	/**
	 * 身份证格式校验
	 * @param cardNo
	 * @return
	 */
	public static boolean cardNoCheck(String cardNo) {
		CheckIdCard checkIdCard = new CheckIdCard(cardNo);
		return checkIdCard.validate();
		
	}
	
	public static void main(String[] args) {
		System.out.println(emailCheck("12465468@345.cn"));
		System.out.println(emailCheck("huafei_83@345.cn"));
		System.out.println(emailCheck("12_1@345.cn"));
		System.out.println(emailCheck("12465468@a.cn"));
		System.out.println("_________________");
		
		System.out.println(cardNoCheck("12465468@a.cn"));
		System.out.println(cardNoCheck("500383198404015498"));
		System.out.println(cardNoCheck("500383198404015496"));
		System.out.println(cardNoCheck("600383198404015498"));
		
	}
	
}
