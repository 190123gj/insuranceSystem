package com.born.insurance.util;

import java.util.Random;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * @author wg
 * 
 * 日期：2005-8-16
 */
public class ValidateCode {
	static final Logger		logger		= LoggerFactory.getLogger(ValidateCode.class);
	
	/**
	 * 验证码字符数
	 */
	public static final int	CODE_LENGTH	= 4;
	/**
	 * 干扰点数
	 */
	public static final int	NOISE_COUNT	= 4;
	/**
	 *  字符类型，0-数字 1-字母，其他-混合
	 */
	public static final int	CODE_TYPE	= 0;
	
	/**
	 * 获得验证码，并显示
	 * 
	 * @param number
	 *            验证码字符数
	 * @param noise
	 *            干扰点数
	 * @param type
	 *            字符类型，0-数字 1-字母，其他-混合
	 * @param request
	 * @param response
	 * @return 返回验证码
	 */
	
	/**
	 * 获得验证码
	 * @param number 验证码字符数
	 * @param type 字符类型，0-数字 1-字母，其他-混合
	 * @return
	 */
	public static String getCode(int number, int type) {
		Random rand = new Random();
		String code = "";
		
		for (int i = 0; i < number; i++) {
			String temp;
			switch (type) {
				case 0:
					temp = getNumber(rand.nextInt(10));
					break;
				case 1:
					temp = getLetter(rand.nextInt(26));
					break;
				default:
					temp = String.valueOf(getRandom());
					break;
			}
			code += temp;
		}
		
		return code;
	}
	
	/**随机获得指定的验证码
	 * @return
	 */
	public static char getRandom() {
		String value = "2345679ACDEFHJKLMNPRSTUVWXYZ";
		int randomNumber = 0;
		randomNumber = (int) (Math.random() * value.length());
		return value.toCharArray()[randomNumber];
	}
	
	private static String getNumber(int n) {
		return "" + n;
	}
	
	private static String getLetter(int n) {
		return "" + (char) (n + 65);
	}
}
