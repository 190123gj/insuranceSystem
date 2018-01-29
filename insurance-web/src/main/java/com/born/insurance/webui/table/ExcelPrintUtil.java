package com.born.insurance.webui.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.born.insurance.ws.enums.BooleanEnum;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class ExcelPrintUtil {
	protected static final Logger logger = LoggerFactory.getLogger(ExcelPrintUtil.class);
	public static final int Chinese_characters_Width = 14;
	
	public static final int Alphabetic_Width = 7;
	
	public static final int Excel_font_Size = 10;
	
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
		return width * 365 / 10 + 37;
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
	
	public static void setCellValue(HSSFCell cell, String temp) {
		
		cell.setCellValue(temp);
	}
	
	public static void setCellValueByCellType(HSSFCell cell, String temp) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			cell.setCellValue(Double.parseDouble(temp));
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			setCellValue(cell, temp);
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			setCellValue(cell, temp);
		} else {
			logger.error("未找到单元格类型" + cell.getCellType() + "-位置");
		}
	}
	
	public static boolean isNumber(String inStr) {
		if (StringUtil.isEmpty(inStr))
			return false;
		inStr = inStr.trim();
		if (inStr.startsWith("-"))
			inStr = inStr.substring(1, inStr.length());
		Pattern p = Pattern.compile("\\d{1,}\\.\\d{1,}");
		Matcher m = p.matcher(inStr);
		boolean b = m.matches();
		if (!b) {
			Pattern pattern = Pattern.compile("[0-9]*");
			b = pattern.matcher(inStr).matches();
		}
		return b;
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
	
	public static void MergHeadTableExcel(HSSFSheet sheet, short cellNum, int begin) {
		for (short i = 0; i < cellNum; i++)
			mergHeadRowTableOneExcel(sheet, i, begin);
	}
	
	private static void mergHeadRowTableOneExcel(HSSFSheet sheet, int cellNum, int begin) {
		int i = begin, rowSpanNum = 1;
		while (i < sheet.getLastRowNum()) {
			HSSFRow gvr = sheet.getRow(i);
			for (++i; i <= sheet.getLastRowNum(); i++) {
				HSSFRow gvrNext = sheet.getRow(i);
				HSSFCell cell1 = gvr.getCell(cellNum);
				HSSFCell cell2 = gvrNext.getCell(cellNum);
				if (cell1 == null || cell2 == null) {
					break;
				}
				if (cell1.getCellType() == cell2.getCellType()) {
					if (cell1.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
							rowSpanNum++;
						}
					} else if (cell1.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						
						if (cell1.getNumericCellValue() == cell2.getNumericCellValue()) {
							rowSpanNum++;
						}
					}
				} else {
					cell1.getCellStyle().setVerticalAlignment((HSSFCellStyle.VERTICAL_CENTER));
					//					sheet.addMergedRegion(new Region(gvr.getRowNum(), cellNum, gvr.getRowNum()
					//																				+ rowSpanNum - 1,
					//						cellNum));
					sheet.addMergedRegion(new CellRangeAddress(gvr.getRowNum(), cellNum, gvr
						.getRowNum() + rowSpanNum - 1, cellNum));
					rowSpanNum = 1;
					break;
				}
				if (i == sheet.getLastRowNum()) {
					//					sheet.addMergedRegion(new Region(gvr.getRowNum(), cellNum, gvr.getRowNum()
					//																				+ rowSpanNum - 1,
					//						cellNum));
					sheet.addMergedRegion(new CellRangeAddress(gvr.getRowNum(), cellNum, gvr
						.getRowNum() + rowSpanNum - 1, cellNum));
					cell1.getCellStyle().setVerticalAlignment((HSSFCellStyle.VERTICAL_CENTER));
				}
			}
		}
	}
	
	public static void MergTableExcel(HSSFSheet sheet, int cellNum, int begin) {
		Map<TableCellBean, BooleanEnum> map = new HashMap<TableCellBean, BooleanEnum>();
		for (int i = 0; i < cellNum; i++)
			mergRowTableOneExcel(sheet, i, begin, map);
	}
	
	private static void mergRowTableOneExcel(HSSFSheet sheet, int cellNum, int begin,
												Map<TableCellBean, BooleanEnum> map) {
		int i = begin, rowSpanNum = 1;
		while (i < sheet.getLastRowNum()) {
			HSSFRow gvr = sheet.getRow(i);
			for (++i; i <= sheet.getLastRowNum(); i++) {
				HSSFRow gvrNext = sheet.getRow(i);
				HSSFCell cell1 = gvr.getCell(cellNum);
				HSSFCell cell2 = gvrNext.getCell(cellNum);
				if (cell1 == null || cell2 == null) {
					break;
				}
				boolean isEqual = false;
				if (cell1.getCellType() == cell2.getCellType()) {
					if (cell1.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
							isEqual = true;
						}
					} else if (cell1.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						
						if (cell1.getNumericCellValue() == cell2.getNumericCellValue()) {
							isEqual = true;
						}
					}
				}
				
				TableCellBean bean1 = new TableCellBean(gvrNext.getCell((cellNum)));
				if (isEqual) {
					if (cellNum > 0) {
						TableCellBean bean = new TableCellBean(gvrNext.getCell((cellNum - 1)));
						BooleanEnum merg = map.get(bean);
						if (merg == BooleanEnum.YES) {
							map.put(bean1, merg);
							rowSpanNum++;
						} else {
							sheet.addMergedRegion(new CellRangeAddress(gvr.getRowNum(), cellNum,
								gvr.getRowNum() + rowSpanNum - 1, cellNum));
							//							sheet.addMergedRegion(new Region(gvr.getRowNum(), cellNum, gvr
							//								.getRowNum() + rowSpanNum - 1, cellNum));
							cell1.getCellStyle().setVerticalAlignment(
								(HSSFCellStyle.VERTICAL_CENTER));
							rowSpanNum = 1;
							break;
						}
					} else {
						map.put(bean1, BooleanEnum.YES);
						rowSpanNum++;
					}
					
				} else {
					cell1.getCellStyle().setVerticalAlignment((HSSFCellStyle.VERTICAL_CENTER));
					sheet.addMergedRegion(new CellRangeAddress(gvr.getRowNum(), cellNum, gvr
						.getRowNum() + rowSpanNum - 1, cellNum));
					rowSpanNum = 1;
					break;
				}
				if (i == sheet.getLastRowNum()) {
					sheet.addMergedRegion(new CellRangeAddress(gvr.getRowNum(), cellNum, gvr
						.getRowNum() + rowSpanNum - 1, cellNum));
					cell1.getCellStyle().setVerticalAlignment((HSSFCellStyle.VERTICAL_CENTER));
				}
			}
		}
	}
	
	public static void MergColTableExcel(HSSFSheet sheet, int beginRum, int rowNum, int begin,
											int colCount) {
		Map<TableCellBean, BooleanEnum> map = new HashMap<TableCellBean, BooleanEnum>();
		for (int i = beginRum; i < beginRum + rowNum; i++)
			mergColTableOneExcel(sheet, i, begin, colCount, beginRum, map);
	}
	
	public static void mergColTableOneExcel(HSSFSheet sheet, int rowNum, int begin, int colCount,
											int beginRum, Map<TableCellBean, BooleanEnum> map) {
		int i = begin, colSpanNum = 1;
		HSSFRow row = sheet.getRow(rowNum);
		while (i < colCount - 1) {
			HSSFCell gvr = row.getCell(i);
			for (++i; i < colCount; i++) {
				HSSFCell nextCell = row.getCell(i);
				if (nextCell == null || gvr == null) {
					break;
				}
				if (gvr.getStringCellValue().equals(nextCell.getStringCellValue())) {
					if (rowNum - 1 > beginRum) {
						TableCellBean bean = new TableCellBean(gvr);
						BooleanEnum merg = map.get(bean);
						if (merg == BooleanEnum.YES) {
							TableCellBean bean1 = new TableCellBean(nextCell);
							map.put(bean1, BooleanEnum.YES);
							colSpanNum++;
						} else {
							//							sheet.addMergedRegion(new Region(rowNum, gvr.getColumnIndex(), rowNum,
							//								 (gvr.getColumnIndex() + colSpanNum - 1)));
							sheet
								.addMergedRegion(new CellRangeAddress(rowNum, gvr.getColumnIndex(),
									rowNum, gvr.getColumnIndex() + colSpanNum - 1));
							colSpanNum = 1;
							break;
						}
					} else {
						TableCellBean bean1 = new TableCellBean(nextCell);
						map.put(bean1, BooleanEnum.YES);
						colSpanNum++;
					}
					
				} else {
					//					sheet.addMergedRegion(new Region(rowNum, gvr.getCellNum(), rowNum, (short) (gvr
					//						.getCellNum() + colSpanNum - 1)));
					sheet.addMergedRegion(new CellRangeAddress(rowNum, gvr.getColumnIndex(),
						rowNum, gvr.getColumnIndex() + colSpanNum - 1));
					colSpanNum = 1;
					break;
				}
				if (i == row.getLastCellNum()) {
					//					sheet.addMergedRegion(new Region(rowNum, gvr.getCellNum(), rowNum, (short) (gvr
					//						.getCellNum() + colSpanNum - 1)));
					sheet.addMergedRegion(new CellRangeAddress(rowNum, gvr.getColumnIndex(),
						rowNum, gvr.getColumnIndex() + colSpanNum - 1));
				}
			}
		}
	}
}
