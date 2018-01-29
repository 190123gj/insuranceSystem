package com.born.insurance.web.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

public class ExcelExportUtil {
	static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);
	
	static final String HEADER_PARAM_BEGIN_TAG = "${";
	static final String HEADER_PARAM_END_TAG = "}";
	
	static final String LIST_PARAM_BEGIN_TAG = "$DETAIL{";
	static final String LIST_PARAM_END_TAG = "}";
	
	protected static Map<String, byte[]> fileInputCachMap = new HashMap<String, byte[]>();
	
	/**
	 * 简单列表导出
	 * @param request
	 * @param excelTemplateFile excel模板
	 * @param response
	 * @param dataList 列表数据
	 * @param title 导出文件名
	 */
	public static void exportExcelFile(HttpServletRequest request, String excelTemplateFile,
										HttpServletResponse response,
										List<Map<String, Object>> dataList, String title) {
		exportExcelFile(request, excelTemplateFile, response, null, dataList, title);
	}
	
	/**
	 * 带表头表尾列表导出
	 * @param request
	 * @param excelTemplateFile 模板
	 * @param response
	 * @param header 表头表尾数据
	 * @param dataList 列表数据
	 * @param title 导出文件名
	 */
	public static void exportExcelFile(HttpServletRequest request, String excelTemplateFile,
										HttpServletResponse response, Map<String, Object> header,
										List<Map<String, Object>> dataList, String title) {
		WebApplicationContext wac = (WebApplicationContext) request
			.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		ServletContext context = wac.getServletContext();
		
		InputStream is = getExcelTemplateStream(context, excelTemplateFile);
		
		try {
			String tempFile = context.getRealPath("/excelTemplate/account/temp/"
													+ System.currentTimeMillis());
			Workbook wb = Workbook.getWorkbook(is);
			WritableWorkbook writewb = Workbook.createWorkbook(new File(tempFile), wb);
			WritableSheet sheet = writewb.getSheet(0);
			int rows = sheet.getRows();
			int beginRows = 0;
			List<List<String>> colList = new ArrayList<List<String>>();
			Map<String, String> letterMap = new HashMap<String, String>();
			Map<String, String> lastLetterMap = new HashMap<String, String>();
			for (int i = 0; i < rows; i++) {
				Cell[] cells = sheet.getRow(i);
				for (int k = 0; k < cells.length; k++) {
					String contents = cells[k].getContents();
					if (StringUtil.isNotEmpty(contents)) {
						contents = contents.trim();
						//表头
						if (contents.indexOf(HEADER_PARAM_BEGIN_TAG) > -1
							&& contents.indexOf(HEADER_PARAM_END_TAG) > -1
							&& contents.indexOf(HEADER_PARAM_BEGIN_TAG) < contents
								.indexOf(HEADER_PARAM_END_TAG)) {
							String value = contents.substring(0,
								contents.indexOf(HEADER_PARAM_BEGIN_TAG));
							
							while (contents.indexOf(HEADER_PARAM_BEGIN_TAG) > -1
									&& contents.indexOf(HEADER_PARAM_END_TAG) > -1) {
								value += contents.substring(0,
									contents.indexOf(HEADER_PARAM_BEGIN_TAG));
								String fieldName = contents.substring(
									contents.indexOf(HEADER_PARAM_BEGIN_TAG)
											+ HEADER_PARAM_BEGIN_TAG.length(),
									contents.indexOf(HEADER_PARAM_END_TAG));
								if (header != null && header.get(fieldName) != null)
									value += String.valueOf(header.get(fieldName));
								
								if (contents.length() >= (contents.indexOf(HEADER_PARAM_END_TAG) + HEADER_PARAM_END_TAG
									.length()))
									contents = contents
										.substring(contents.indexOf(HEADER_PARAM_END_TAG)
													+ HEADER_PARAM_END_TAG.length());
								else
									contents = "";
							}
							
							if (StringUtil.isNotEmpty(contents))
								value += contents;
							
							sheet.addCell(new Label(k, i, value));
						}
						//列表
						if (contents.indexOf(LIST_PARAM_BEGIN_TAG) > -1
							&& contents.indexOf(LIST_PARAM_END_TAG) > -1
							&& contents.indexOf(LIST_PARAM_BEGIN_TAG) < contents
								.indexOf(LIST_PARAM_END_TAG)) {
							beginRows = i;
							List<String> subColList = new ArrayList<String>();
							String fieldName = null;
							while (contents.indexOf(LIST_PARAM_BEGIN_TAG) > -1
									&& contents.indexOf(LIST_PARAM_END_TAG) > -1
									&& contents.indexOf(LIST_PARAM_BEGIN_TAG) < contents
										.indexOf(LIST_PARAM_END_TAG)) {
								fieldName = contents.substring(
									contents.indexOf(LIST_PARAM_BEGIN_TAG)
											+ LIST_PARAM_BEGIN_TAG.length(),
									contents.indexOf(LIST_PARAM_END_TAG));
								subColList.add(fieldName);
								if (contents.indexOf(LIST_PARAM_BEGIN_TAG) > 0)
									letterMap.put(
										fieldName + colList.size(),
										contents.substring(0,
											contents.indexOf(LIST_PARAM_BEGIN_TAG)));
								
								if (contents.length() >= (contents.indexOf(LIST_PARAM_END_TAG) + LIST_PARAM_END_TAG
									.length()))
									contents = contents.substring(contents
										.indexOf(LIST_PARAM_END_TAG) + LIST_PARAM_END_TAG.length());
								else
									contents = "";
								
							}
							if (StringUtil.isNotEmpty(fieldName) && StringUtil.isNotEmpty(contents))
								lastLetterMap.put(fieldName + colList.size(), contents);
							
							colList.add(subColList);
							
							//contents = contents.substring(contents.indexOf(LIST_PARAM_END_TAG) + LIST_PARAM_END_TAG.length());
						}
					}
				}
			}
			
			//填入列表数据
			if (ListUtil.isNotEmpty(dataList)) {
				for (int i = 0; i < dataList.size(); i++) {
					if (i > 0)
						sheet.insertRow(beginRows + i);
					Map<String, Object> data = dataList.get(i);
					for (int k = 0; k < colList.size(); k++) {
						List<String> subColList = colList.get(k);
						String value = "";
						for (int f = 0; f < subColList.size(); f++) {
							if (letterMap.get(subColList.get(f) + k) != null)
								value += letterMap.get(subColList.get(f) + k);
							
							if (data.get(subColList.get(f)) != null)
								value += String.valueOf(data.get(subColList.get(f)));
							
							if (lastLetterMap.get(subColList.get(f) + k) != null)
								value += lastLetterMap.get(subColList.get(f) + k);
						}
						
						sheet.addCell(new Label(k, beginRows + i, value));
					}
				}
			} else {
				for (int k = 0; k < colList.size(); k++) {
					sheet.addCell(new Label(k, beginRows, ""));
				}
			}
			
			writewb.write();
			writewb.close();
			is.close();
			
			downExcel(context, response, title, tempFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				logger.error("down file error", e);
			}
		}
		
	}
	
	private static void downExcel(ServletContext context, HttpServletResponse response,
									String title, String tempFile) {
		try {
			title = URLEncoder.encode(title, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("", e1);
		}
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment;filename=" + title + ".xls");
		OutputStream os = null;
		BufferedInputStream bis = null;
		
		try {
			InputStream is = new FileInputStream(tempFile);
			bis = new BufferedInputStream(is);
			response.setContentLength(bis.available());
			byte[] buf = new byte[1024];
			int bytes = 0;
			os = response.getOutputStream();
			while ((bytes = bis.read(buf)) != -1) {
				os.write(buf, 0, bytes);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				logger.error("down file error", e);
			}
		}
		
		//		try
		//		{
		//			new File(tempFile).delete();
		//		}catch (Exception e)
		//		{
		//			log.error(e.getMessage(), e);
		//		}
	}
	
	public static InputStream getExcelTemplateStream(ServletContext context,
														String excelTemplateName) {
		ByteArrayInputStream bis = null;
		FileInputStream fis = null;
		byte[] byteArray = null;
		try {
			if (fileInputCachMap.get(excelTemplateName) != null) {
				byteArray = (byte[]) fileInputCachMap.get(excelTemplateName);
			} else {
				fis = new FileInputStream(
					context.getRealPath("/excelTemplate/" + excelTemplateName));
				byteArray = new byte[fis.available()];
				fis.read(byteArray);
				fis.close();
			}
			fileInputCachMap.put(excelTemplateName, byteArray);
			bis = new ByteArrayInputStream(byteArray);
		} catch (FileNotFoundException e) {
			logger.error(" file [" + "/print/excelTemplate/" + excelTemplateName + "]....", e);
		} catch (IOException e) {
			logger.error(" file [" + "/print/excelTemplate/" + excelTemplateName + "]....", e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
			}
		}
		return bis;
	}
	
}
