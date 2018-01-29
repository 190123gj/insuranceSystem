package com.born.insurance.webui.table;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

public class ReportUtil {
	
	public static String AMOUNT_FORMAT_STRING = "###0.00";
	
	public static DecimalFormat floatFormat = new DecimalFormatBorn("###0.#########");
	
	public static DecimalFormat doubleFormat = floatFormat; //new DecimalFormatBorn(AMOUNT_FORMAT_STRING);
	
	public static DecimalFormat intFormat = new DecimalFormatBorn("###0");
	
	public static DecimalFormat floatListSeparatoFormat = new DecimalFormatBorn("###0.###");
	
	public static DecimalFormat floatSixFormat = new DecimalFormatBorn("###0.##########");
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static Map DecimalFormatMap = new HashMap();
	static {
		DecimalFormatMap.put(new Integer(0), intFormat);
		String tmp = "000000000000000";
		for (int i = 1; i < 15; i++) {
			DecimalFormatMap.put(new Integer(i),
				new DecimalFormatBorn("###0." + tmp.substring(0, i)));
		}
	}
	
	/**
	 * 编码转换函数
	 * @param src
	 * @param inCode
	 * @param outCode
	 * @return
	 */
	public static String convert(String src, String inCode, String outCode) {
		try {
			if (src == null) {
				return "";
			}
			return (new String(src.getBytes(inCode), outCode));
		} catch (Exception e) {
			return (null);
		}
	}
	
	public static String objectToString(Object obj) {
		if (obj == null)
			return "";
		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof Integer) {
			Integer integer = (Integer) obj;
			return intFormat.format(integer.intValue());
		} else if (obj instanceof Double) {
			Double d = (Double) obj;
			return doubleFormat.format(d.doubleValue());
		} else if (obj instanceof Float) {
			Float f = (Float) obj;
			return floatFormat.format(f.floatValue());
		} else if (obj instanceof Date) {
			return dateToString((Date) obj);
		} else if (obj instanceof Money) {
			return ((Money) obj).toStandardString();
		} else {
			return obj.toString();
		}
	}
	
	/**
	 * 把object对象转换为字符串
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj, String formatString) {
		if (obj == null)
			return "";
		if (StringUtil.isEmpty(formatString)) {
			return objectToString(obj);
		}
		if (obj instanceof Number) {
			Number d = (Number) obj;
			return (new DecimalFormatBorn(formatString)).format(d.doubleValue());
		}
		return objectToString(obj);
		
	}
	
	/**
	 * 日期型转化为字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		
		if (date == null) {
			return dateFormat.format(new Date());
		} else {
			
			return dateFormat.format(date);
		}
	}
	
	/**
	 * 字符串宽度转换函数
	 * @param strWidth
	 * @return
	 */
	public static int parseWidth(String strWidth) {
		if (StringUtil.isEmpty(strWidth))
			return 0;
		boolean isValid = false;
		int intWidth = 0;
		try {
			intWidth = Integer.parseInt(strWidth);
			isValid = true;
		} catch (Exception e) {
			isValid = false;
		}
		if (!isValid) {
			try {
				if (("").equals(strWidth)) {
					return 0;
				}
				intWidth = Integer.parseInt(strWidth.substring(0, strWidth.length() - 2));
			} catch (Exception e) {
				return -1;
			}
		}
		return intWidth;
	}
	
	public static List parseQueryString(String queryString) {
		List keyList = new LinkedList();
		if (queryString == null)
			return keyList;
		final String EMPTY = "";
		int index = queryString.indexOf("?");
		if (index > -1) {
			queryString = queryString.substring(index + 1);
		}
		StringTokenizer st = new StringTokenizer(queryString, "&");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int pos = token.indexOf("=");
			if (pos > -1) {
				token = token.substring(0, pos);
				keyList.add(token);
			}
		}
		return keyList;
	}
}
