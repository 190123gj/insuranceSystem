/*
 * 创建日期 2004-12-2
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.born.insurance.webui.table;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;


import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * @author qichh 导出exel 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ExcelPrint {
	protected static final Logger logger = LoggerFactory.getLogger(ExcelPrint.class);
	
	public static interface CellSetValueHandle {
		void setCellStyle(int rowIndex, int cellIndex, HSSFCell cell, HSSFWorkbook wb);
	}
	
	public final static int Excel_Type_List = 2;
	public final static int Excel_Type_Card = 1;
	private final static String PARAM_CHAR = "${";
	private final static String LIST_PARAM_CHAR = "$Detail{";
	

	

	
	public static String replaceFormatString(String sValue, Map paraMap) {
		return replaceFormatString(sValue, paraMap, PARAM_CHAR);
	}
	
	public static String replaceFormatString(String sValue, Map paraMap, String listseparator) {
		String separator = listseparator;
		String result = sValue;
		if (result == null)
			return "";
		if ((result.indexOf(PARAM_CHAR) == -1) && (result.indexOf(LIST_PARAM_CHAR) == -1))
			return result;
		int index = 0; //索引
		while (result.indexOf(separator) != -1) {
			index = result.indexOf(separator, index);
			int end = result.indexOf("}", index + separator.length());
			if (end == -1) {
				return result;
			}
			String param = result.substring(index + separator.length(), end); //取出参数
			String value = null;
			if (paraMap.get(param) instanceof String[])
				value = ((String[]) paraMap.get(param))[0];
			else if (paraMap.get(param) instanceof Double) {
				value = ReportUtil.objectToString(paraMap.get(param));
				//value =UtilFunction.doubleToFormat(paraMap.get(param));
			} else
				value = ReportUtil.objectToString(paraMap.get(param));
			
			if (value.startsWith(".")) {
				try {
					value = ReportUtil.objectToString(Double.valueOf(value));
				} catch (Exception e) {
				}
			}
			
			if (value == null) {
				value = "";
			}
			
			String tempstr = result.substring(index + separator.length());
			result = result.substring(0, index) + firstReplace(tempstr, param + "}", value);
		}
		return result;
	}
	
	static public void copyCellAttribute(HSSFCell orgcell, HSSFCell copeToCell) {
		copeToCell.setCellStyle(orgcell.getCellStyle());
		//copeToCell.setCellNum(orgcell.getCellNum());		
	}
	
	static public String firstReplace(String str, String pattern, String replace) {
		if (replace == null) {
			replace = "";
		}
		int s = 0, e = 0;
		StringBuffer result = new StringBuffer(str.length() * 2);
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
			break;
		}
		result.append(str.substring(s));
		return result.toString();
	}
	
	/**
	 * 替换全部设置了标签的明细数据
	 * @param str
	 * @param printDetailDataList
	 * @param curPageNumber
	 * @param firstPageSize
	 * @param pageSize
	 * @return
	 */
	private static String replaceAllFieldString(String str, List printDetailDataList,
												int curPageNumber, int firstPageSize, int pageSize) {
		if (StringUtil.isEmpty(str))
			return str;
		if (!(str.startsWith("$D") || str.indexOf("$PageMemo") > -1))
			return str;
		str = str.trim();
		//Debug.log("str:" + str);
		int start = 0;
		if (curPageNumber > 0) {
			start += firstPageSize;
			if (curPageNumber > 1) {
				start += (curPageNumber - 1) * pageSize;
			}
		}
		
		int end = start;
		if (pageSize < 0) {
			end += printDetailDataList.size();
		} else {
			end += pageSize;
		}
		
		//Debug.log("firstPageSize:" + firstPageSize + " pageSize:" + pageSize + " curPageNumber:" + curPageNumber + " start:" + start + " end:" + end);
		
		//Debug.log("---------------------");
		for (int i = start; i < end; i++) {
			if (i >= printDetailDataList.size())
				break;
			
			Map printData = (Map) printDetailDataList.get(i);
			if (i == start) {
				if (printData.containsKey("$D0remarks")) {
					//Debug.log("str:" + str + ":");
					if (str.indexOf("$PageMemo") > -1) {
						//Debug.log("equlas:" + printData.get("$D0remarks"));
						return str.substring(0, str.indexOf("$PageMemo"))
								+ ReportUtil.objectToString(printData.get("$D0remarks"))
								+ str.substring(str.indexOf("$PageMemo") + 9);
					}
				}
			}
			
			Iterator it = printData.keySet().iterator();
			while (it.hasNext()) {
				String fieldName = (String) it.next();
				if (fieldName.equalsIgnoreCase(str)) {
					//Debug.log("fieldName:" + fieldName);
					if (printData.get(fieldName) instanceof Double) {
						return ReportUtil.objectToString(printData.get(fieldName));
					} else {
						return ReportUtil.objectToString(printData.get(fieldName));
					}
				}
			}
		}
		if (str.startsWith("$D"))
			return "";
		return str;
	}
	
	/**
	 * 替换明细中合并的备注
	 * @param cell
	 * @param remarks
	 */
	private static void replacePageMemo(HSSFCell cell, String remarks) {
		String cellValue = cell.getStringCellValue();
		if (StringUtil.isEmpty(cellValue))
			return;
		String tag = "$PageMemo";
		if (cellValue.indexOf(tag) > -1) {
			cellValue = cellValue.substring(0, cellValue.indexOf(tag)) + remarks
						+ cellValue.substring(cellValue.indexOf(tag) + tag.length());
			cell.setCellValue(cellValue);
		}
	}
	
	/**
	 * 替换表头意见总页数PageHeader
	 * @param cell
	 * @param strPageHeader
	 * @param curPageNumber 当前页
	 */
	private static void replacePageHeader(HSSFCell cell, String strPageHeader, int curPageNumber) {
		String cellValue = cell.getStringCellValue();
		if (StringUtil.isEmpty(cellValue))
			return;
		String tag = "$PageHeader";
		if (cellValue.indexOf(tag) > -1) {
			cellValue = cellValue.substring(0, cellValue.indexOf(tag)) + strPageHeader + ",第 "
						+ curPageNumber + " 页"
						+ cellValue.substring(cellValue.indexOf(tag) + tag.length());
			cell.setCellValue(cellValue);
		}
	}
	
	private static int getListBeginIndex(HSSFSheet sheet) {
		int listBeginIndex = -1;
		//Debug.log("sheet.getLastRowNum():" + sheet.getLastRowNum());
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;
			//Debug.log("row " + i + ":" + row);
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);
				if (cell != null) {
					String str = "";
					try {
						str = cell.getStringCellValue();
					} catch (Exception e) {
						str = "";
					}
					
					if (listBeginIndex == -1) {
						if (str.indexOf(LIST_PARAM_CHAR) >= 0) {
							listBeginIndex = i;
							break;
						}
					}
				}
			}
		}
		return listBeginIndex;
	}
	
	/**
	 * 将列表导出为excel
	 * 
	 * @param dataList 数据列表
	 * 内部数据为List，List里为Map{data:Object[];col:int[];row:int[]}
	 * @param title 标题
	 * @param jspos 输出流
	 * @param width 列宽
	 * @param colNum 列数
	 * @return
	 */
	public static boolean exportExcel(List dataList, String title, OutputStream jspos,
										short[] width, int colNum) {
		
		HSSFWorkbook wb = null;
		
		try {
			Iterator it = null;
			
			wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("1");
			sheet.setGridsPrinted(true);
			sheet.setDisplayGridlines(true);
			sheet.setDisplayRowColHeadings(true);
			if (null != width) {
				for (int x = 0; x < width.length; x++) {
					sheet.setColumnWidth(x, width[x]);
				}
			}
			if (null != title) {//写入标题
				HSSFRow titleRow = sheet.createRow(sheet.getFirstRowNum());
				HSSFCell cell = titleRow.createCell(0);
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				HSSFFont font = wb.createFont();
				font.setFontName("宋体");
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setFontHeightInPoints((short) 20);
				cellStyle.setFont(font);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(title);
			}
			sheet.addMergedRegion(new Region(sheet.getFirstRowNum(),
				(short) sheet.getFirstRowNum(), sheet.getFirstRowNum(), (short) (colNum - 1))); //指定合并区域
			
			Iterator dataIt = dataList.iterator();
			int cou = 1;
			int[] defualtNum = new int[colNum];
			for (int i = 0; i < colNum; i++) {
				defualtNum[i] = 1;
			}
			while (dataIt.hasNext()) {
				List data = (List) dataIt.next();
				for (it = data.iterator(); it.hasNext(); cou++) {
					HSSFRow row = sheet.createRow(sheet.getFirstRowNum() + cou);
					Map map = (Map) it.next();
					Object[] dataInfo = (Object[]) map.get("data");
					//跨列设置
					int[] dataCol = null;
					if (null == map.get("col")) {
						dataCol = defualtNum;
					} else {
						dataCol = (int[]) map.get("col");
					}
					//跨行设置
					int[] dataRow = null;
					if (null == map.get("row")) {
						dataRow = defualtNum;
					} else {
						dataRow = (int[]) map.get("row");
					}
					//水平对齐
					String[] align = null;
					if (null != map.get("align")) {
						align = (String[]) map.get("align");
					}
					//是否换行
					String[] wrap = null;
					if (null != map.get("wrapText")) {
						wrap = (String[]) map.get("wrapText");
					}
					Map[] styleMap = null;
					if (null != map.get("style")) {
						styleMap = (Map[]) map.get("style");
					}
					int endCol = -1;
					for (short i = 0; i < dataInfo.length; i++) {
						HSSFCell cell = row.createCell((endCol + 1));
						cell.setCellValue(ReportUtil.objectToString(dataInfo[i]));
						//样式设置
						//						List style = UtilMisc.toList(
						//								null == align || align.length <= i ?"":align[i],
						//								null == wrap || wrap.length <= i ?"":wrap[i]);
						HSSFCellStyle cellStyle = wb.createCellStyle();
						if (null != wrap && "true".equals(wrap[i])) {
							cellStyle.setWrapText(true);
						}
						if (null != align && "center".equals(align[i])) {
							cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						}
						//						HSSFCellStyle cellStyle = ExcelPrintUtil.getStyle(wb,style);
						if (styleMap != null) {
							cellStyle = ExcelPrintUtil.getStyle(wb, cellStyle, styleMap[i]);
						}
						if (null != cellStyle) {
							cell.setCellStyle(cellStyle);
						}
						if ((dataCol.length > i && 1 < dataCol[i])
							|| (dataRow.length > i && dataRow[i] > 1)) {
							//							sheet.addMergedRegion(new Region(cou, (short) (endCol + 1),
							//								(cou + (dataRow[i] - 1)), (short) (endCol + dataCol[i]))); //指定合并区域
							sheet.addMergedRegion(new CellRangeAddress(cou, (endCol + 1),
								(cou + (dataRow[i] - 1)), (endCol + dataCol[i])));
						}
						endCol = (endCol + (dataCol.length > i ? dataCol[i] : 1));
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		} finally {
		}
		OutputStream fos = null;
		try {
			fos = jspos;
			wb.write(jspos);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return true;
	}
	
	//Excel模板文件流Map
	private static Map excelTemplateCacheMap = new HashMap<>();
	
	/**
	 * 取Excel模板文件流
	 * @param context
	 * @param excelTemplateName
	 * @return
	 */
	public static POIFSFileSystem getExcelTemplateStream(ServletContext context,
															String excelTemplateName) {
		Date beginDate = new Date();
		FileInputStream fis = null;
		byte[] byteArray = null;
		POIFSFileSystem fs = null;
		try {
			if (excelTemplateCacheMap.get(excelTemplateName) != null) {
				byteArray = (byte[]) excelTemplateCacheMap.get(excelTemplateName);
			} else {
				fis = new FileInputStream(context.getRealPath("/print/excelTemplate/"
																+ excelTemplateName));
				byteArray = new byte[fis.available()];
				fis.read(byteArray);
				fis.close();
				//excelTemplateCacheMap.put(excelTemplateName, byteArray);
			}
			ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
			fs = new POIFSFileSystem(bis);
		} catch (FileNotFoundException e) {
			logger.error("模板文件:" + excelTemplateName + " 没找到!", e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
			}
		}
		logger.info("read excel time:" + (new Date().getTime() - beginDate.getTime()));
		return fs;
	}
}
