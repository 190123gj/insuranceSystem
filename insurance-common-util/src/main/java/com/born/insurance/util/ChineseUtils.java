package com.born.insurance.util;

/**
 * 中文字符工具
 * @author luopeng
 *         Created on 2014/7/29.
 */
public class ChineseUtils {

	public static final String CHINESE_UNICODE_REGEX = "^[\\u4e00-\\u9fa5]+$";

	/**
	 * 是否对中文语系可读，包括中文字符，符号
	 * @param c
	 * @return
	 */
	public static boolean isReadableForChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.BASIC_LATIN
			|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
			|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
			|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
			|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
			|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是中文字符，对应Unicode编码表
	 * @param testSource
	 * @return
	 */
	public static boolean isChineseChar(String testSource){
		if(testSource.matches(CHINESE_UNICODE_REGEX)){
			return true;
		}
		return false;
	}

	/**
	 * 给定的字符串是否中文可读
	 * @param testSource
	 * @return
	 */
	public static boolean isReadableForChinese(String testSource) {
		char[] ch = testSource.toCharArray();
//		float chLength = ch.length;
		//		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {

				if (!isReadableForChinese(c)) {
					//					count = count + 1;
					return false;
				}

			}
		}

		return true;

		//		float result = count / chLength;
		//		if (result > 0.4) {
		//			return true;
		//		} else {
		//			return false;
		//		}

	}

	public static void main(String[] args) {
//		String str = "*��JTP.jar�ļ����JTP�ļ���ȡ��ͼƬ��Դ";
		String str = "JTP.jar!@#$%^&*()_,";
		System.out.println(isReadableForChinese(str));
	}
}


