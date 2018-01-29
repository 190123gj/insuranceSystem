package com.born.insurance.webui.table;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yjf.common.lang.util.StringUtil;

public class HtmlBuilderUtil {
	
	public static final int Chinese_characters_Width = 13;
	
	public static final int Alphabetic_Width = 7;
	
	public static final int Excel_font_Size = 10;
	
	public static final int HTML_Cell_MAX_WIDTH = 150;
	
	public static int getTextWidth(String text, int defaultWidth) {
		int width = 0;
		if (StringUtil.isNotEmpty(text)) {
			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) < 255) {
					width += Alphabetic_Width;
				} else {
					width += Chinese_characters_Width;
				}
			}
		}
		if (width < defaultWidth) {
			width = defaultWidth;
		}
		if (width > HTML_Cell_MAX_WIDTH)
			width = HTML_Cell_MAX_WIDTH;
		//System.out.println(text+"  "+width);
		return width;
	}
	
	public static int getTextCharLength(String text) {
		int count = 0;
		if (StringUtil.isNotEmpty(text)) {
			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) < 255) {
					count += 1;
				} else {
					count += 2;
				}
			}
		}
		
		return count;
	}
	
	public static void initDefaultWidth(int[] colWidth, int defaultWidth) {
		for (int i = 0; i < colWidth.length; i++) {
			colWidth[i] = defaultWidth;
		}
	}
	
	public static int getTextWidthDef150(String text) {
		return getTextWidth(text, 100);
	}
	
	public static int getTextWidthDef80(String text) {
		return getTextWidth(text, 80);
	}
	
	public static int getTextWidthDef60(String text) {
		return getTextWidth(text, 60);
	}
	
	public static String getInnerText(String tempValue) {
		int indexHtml = tempValue.indexOf("<");
		if (indexHtml > -1) {
			int index1Html = tempValue.indexOf(">");
			while (index1Html > indexHtml) {
				tempValue = tempValue.substring(0, indexHtml)
							+ tempValue.substring(index1Html + 1, tempValue.length());
				indexHtml = tempValue.indexOf("<");
				index1Html = tempValue.indexOf(">");
			}
		}
		return tempValue;
	}
	
	public static HSSFCellStyle getStyle(HSSFWorkbook wb, List style) {
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		
		// 设置水平对齐
		if ("center".equals(style.get(0))) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		} else if ("right".equals(style.get(0))) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		} else if ("left".equals(style.get(0))) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}
		
		// 是否换行
		if ("true".equals(style.get(1))) {
			cellStyle.setWrapText(true);
		}
		return cellStyle;
	}
	
	public static HSSFCellStyle getStyle(HSSFWorkbook wb, HSSFCellStyle cellStyle, Map styleMap) {
		if (styleMap == null)
			return cellStyle;
		// 设置水平对齐
		if ("center".equals(styleMap.get("align"))) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}
		if ("Bold".equals(styleMap.get("font"))) {
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);
		}
		return cellStyle;
	}
	
	public static void main(String[] args) {
		System.out.println(getTextWidth("API空气污染指数", 80));
	}
}
