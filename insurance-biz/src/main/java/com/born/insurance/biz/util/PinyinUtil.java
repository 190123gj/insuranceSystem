package com.born.insurance.biz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * Created by Administrator on 2017-1-22.
 */
public class PinyinUtil {
	public PinyinUtil() {
	}
	
	public static String getPinYinHeadChar(String chinese) {
		StringBuffer pinyin = new StringBuffer();
		if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
			for (int j = 0; j < chinese.length(); ++j) {
				char word = chinese.charAt(j);
				String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
				if (pinyinArray != null) {
					pinyin.append(pinyinArray[0].charAt(0));
				} else {
					pinyin.append(word);
				}
			}
		}
		
		return pinyin.toString().toUpperCase();
	}
	
	public static String strFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}\':;\',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String getPinYinHeadCharFilter(String chinese) {
		return strFilter(getPinYinHeadChar(chinese));
	}
	
}
