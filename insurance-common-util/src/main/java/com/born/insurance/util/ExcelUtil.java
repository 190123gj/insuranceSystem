package com.born.insurance.util;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.born.insurance.ws.info.customer.CustomerProtocolInfo;
import com.yjf.common.lang.exception.AppException;

import net.sf.jxls.transformer.XLSTransformer;

public class ExcelUtil {
	
	
	/**
	 * @param <T>
	 * @param workbook
	 * @param fileName
	 * @param headTitle
	 * @param content
	 * @return
	 */
	public static HSSFWorkbook exportExcel(String fileName,String[] headTitle, List<CustomerProtocolInfo> content) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = workbook.createSheet("客户协议");
		 // 设置列宽   
	    sheet.setColumnWidth(0, 3500);   
	    sheet.setColumnWidth(1, 3500);   
	    sheet.setColumnWidth(2, 3500);   
	    sheet.setColumnWidth(3, 3000);   
	    sheet.setColumnWidth(4, 3000);   
	    sheet.setColumnWidth(5, 6500);   
	    sheet.setColumnWidth(6, 6500);   
	    sheet.setColumnWidth(7, 5500);  
	    sheet.setColumnWidth(8, 4500);  
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font = workbook.createFont();   
	    font.setFontName("宋体");   
	    font.setFontHeightInPoints((short)13);   
	    style.setFont(font);
		
		
		HSSFCell[] cell = new HSSFCell[headTitle.length]; // 表头的长度即excel有多少列
		for (int i = 0; i < cell.length; i++) {
			String val = headTitle[i];
			HSSFCell c = row.createCell(i);
			c.setCellValue(val);
			c.setCellStyle(style);
		}

		for (int i = 0; i < content.size(); i++) {
			row = sheet.createRow((int) i + 1); // 除去表头的一行
			CustomerProtocolInfo customerProtocolInfo = content.get(i);
			// 第四步，创建单元格，并设置值
			for (int j = 0; j < cell.length; j++) {
				HSSFCell createCell = row.createCell(j);
				switch (j) {
				case 0:
					createCell.setCellValue(customerProtocolInfo.getNo());
					break;
				case 1:
					createCell.setCellValue(customerProtocolInfo.getName());
					break;
				case 2:
					createCell.setCellValue(customerProtocolInfo.getCertType());
					break;
				case 3:
					createCell
							.setCellValue(customerProtocolInfo.getCertType());
					break;
				case 4:
					createCell.setCellValue(customerProtocolInfo.getCertType());
					break;
				case 5:
					createCell.setCellValue(customerProtocolInfo.getCertType());
					break;
				case 6:
					createCell.setCellValue(customerProtocolInfo.getCertType());
					break;
				case 7:
					createCell.setCellValue(customerProtocolInfo.getCertType());
					break;
				case 8:
					createCell.setCellValue(customerProtocolInfo.getCertType());
					break;
				default:
					break;
				}
			}

		}
		return workbook;
	}
	
	
	public static byte[] createExcelFileByte(String templateFileName, Map<String, Object> beanParams,
			String resultFileName) {
		// 创建XLSTransformer对象
		XLSTransformer transformer = new XLSTransformer();

		try {
			// 生成Excel文件
			transformer.transformXLS(templateFileName, beanParams, resultFileName);

			FileInputStream fileInput = new FileInputStream(resultFileName);
			int i = fileInput.available();
			byte[] content = new byte[i];
			fileInput.read(content);
			fileInput.close();
			return content;
		} catch (Exception e) {
			throw new AppException("生成excel错误：", e);
		}
	}

}
