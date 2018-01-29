package com.born.insurance.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.born.insurance.ws.enums.InsuranceOfTypeEnum;
import com.born.insurance.ws.info.businessBill.BusinessBillInfo;
import com.yjf.common.lang.exception.AppException;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * excel导入与处理
 * 
 * @author lirz
 * 
 * 2016-3-24 下午4:20:31
 */
public class ExcelUtil {
	
	protected static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static ExcelData parseExcel(String excelPath) throws BiffException, IOException {
		Workbook model = Workbook.getWorkbook(new File(excelPath));
		Sheet sheet = model.getSheet(0);
		int columns = sheet.getColumns();//得到列数
		int rows = sheet.getRows();//得到行数
		logger.info(excelPath + "：共有" + columns + "列，" + rows + "行");
		if (columns > 0 && rows > 0) {
			String[][] datas = new String[rows][columns];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					datas[i][j] = sheet.getCell(j, i).getContents();
				}
			}
			
			return new ExcelData(columns, rows, datas);
		}
		return null;
	}
	
	public static ExcelData parseExcelReplaceYear(String excelPath) throws BiffException,
																	IOException {
		Workbook model = Workbook.getWorkbook(new File(excelPath));
		Sheet sheet = model.getSheet(0);
		int columns = sheet.getColumns();//得到列数
		int rows = sheet.getRows();//得到行数
		logger.info(excelPath + "：共有" + columns + "列，" + rows + "行");
		if (columns > 0 && rows > 0) {
			String[][] datas = new String[rows][columns];
			
			Date now = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int year = c.get(Calendar.YEAR);
			int i = 0;
			for (int j = 0; j < columns; j++) {
				String cellContent = sheet.getCell(j, i).getContents();
				if (StringUtil.isNotBlank(cellContent)) {
					datas[i][j] = cellContent.replace("yyyy", year + "");
					year--;
				}
			}
			for (i = 1; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					datas[i][j] = sheet.getCell(j, i).getContents();
				}
			}
			
			return new ExcelData(columns, rows, datas);
		}
		return null;
	}
	
	public static ExcelData parseExcelReplaceYearAndMonth(String excelPath) throws BiffException,
																			IOException {
		return null;
	}
	
	public static void test1(String modelFilePath) throws BiffException, IOException {
		Workbook model = Workbook.getWorkbook(new File(modelFilePath));
		Sheet sheet = model.getSheet(0);
		int clos = sheet.getColumns();//得到所有的列
		int rows = sheet.getRows();//得到所有的行
		System.out.println("共有" + clos + "列，" + rows + "行");
		for (int i = 0; i < rows; i++) {
			int id = 1;
			StringBuilder sb = new StringBuilder();
			sb.append("第" + i + "行：");
			for (int j = 0; j < clos; j++) {
				sb.append(sheet.getCell(j, i).getContents() + "(" + (id++) + ")");
			}
			System.out.println(sb.toString());
		}
	}
	
	public static void main1(String[] args) {
		try {
			test1("F:\\works\\进出口担保项目\\报表\\保证人主要财务指标.xls");
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ExcelData uploadExcel(HttpServletRequest request) throws FileUploadException,
																	IOException, BiffException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null != is) {
			Workbook model = Workbook.getWorkbook(is);
			Sheet sheet = model.getSheet(0);
			int columns = sheet.getColumns();//得到列数
			int rows = sheet.getRows();//得到行数
			logger.info("共有" + columns + "列，" + rows + "行");
			if (columns > 0 && rows > 0) {
				String[][] datas = new String[rows][columns];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						datas[i][j] = sheet.getCell(j, i).getContents();
					}
				}
				
				return new ExcelData(columns, rows, datas);
			}
		}
		return null;
	}
	
	public static ExcelData uploadExcel2(HttpServletRequest request) throws FileUploadException,
																	IOException, BiffException {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		fileUpload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
		} catch (FileUploadException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		Iterator<FileItem> it = fileList.iterator();
		InputStream is = null;
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				is = item.getInputStream();
				break;
			}
		}
		
		if (null != is) {
			Workbook model = Workbook.getWorkbook(is);
			Sheet sheet = model.getSheet("客户主要高管人员表");
			int columns = sheet.getColumns();//得到列数
			int rows = sheet.getRows();//得到行数
			logger.info("共有" + columns + "列，" + rows + "行");
			if (columns > 0 && rows > 0) {
				String[][] datas = new String[rows][columns];
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						datas[i][j] = sheet.getCell(j, i).getContents();
					}
				}
				
				return new ExcelData(columns, rows, datas);
			}
		}
		return null;
	}
	 
	 
	 public static List<BusinessBillInfo> readExcel(HttpServletRequest request,Map<String, Object> map) throws IOException, BiffException, ParseException{  
		 List<String[]> dataList = new ArrayList<>();
			List<BusinessBillInfo> pageList = new ArrayList<>();
			//错误数据集合返回
			List<BusinessBillInfo> businessBillBad=new ArrayList<BusinessBillInfo>();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
			ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			fileUpload.setHeaderEncoding("utf-8");
			List<FileItem> fileList = null;
			try {
				fileList = fileUpload.parseRequest(request);
			} catch (FileUploadException ex) {
				logger.error(ex.getMessage(), ex);
			}
			Iterator<FileItem> it = fileList.iterator();
			InputStream is = null;
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					is = item.getInputStream();
					break;
				}
			}
			
			if (null != is) {
				org.apache.poi.ss.usermodel.Workbook model = null ;
				if (fileList.get(0).getName().endsWith(".xls")) {
					model = new HSSFWorkbook(is);
				} else {
					model = new XSSFWorkbook(is);
				}
				org.apache.poi.ss.usermodel.Sheet sheet = model.getSheetAt(0);
			      //行数(表头的目录不需要，从1开始)  
		        for(int i=0; i<sheet.getLastRowNum(); i++){  
		        	Row row = sheet.getRow(i);
		        	if (null == row) {
		        		break ;
		        	}
		        	
		        	if(i >= 2 && CheckRowNull(row) > 4){
		        		continue ;
		        	}
	        		//总的列
	        		int columns =row.getLastCellNum();
	        		String[] str = new String[columns];  	
	        		for(int j=0; j< columns; j++){  
	        			//获取第i行，第j列的值  
	        			org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
	        			str[j] = getValue(cell,j);
	        		}  
	        		dataList.add(str);
		        }  
			}
			if (ListUtil.isNotEmpty(dataList)) {
				int num = 0 ;
				for (String[] s : dataList) {
					num ++ ;
					if (num < 3 ) {
						continue;
					}
					BusinessBillInfo info = new BusinessBillInfo();
					
					for (int i = 0; i < s.length; i++) {
						String data = s[i];
						switch (i) {
						case 0:
							info.setInsuranceDepart(data);
							break;
						case 1:
							info.setInsuranceTeam(data);
							break;
						case 2:
							info.setInsuranceBrokerNo(data);
							break;
						case 3:
							info.setInsuranceBrokerName(data);
							break;
						case 4:
							info.setBillNo("");
							break;
						case 5:
							//查询该保险公司是否存在
							info.setInsuranceCompanyName(data);
							break;
						case 6:
							//查询该保险公司是否存在
							info.setBillCustomerName(data);
							break;
						case 7:
							info.setBillInsuredName(data);
							break;
						case 8:
							info.setInsuranceTypeName(data);
							break;
						case 9:
							info.setInsuranceCatalogName(data);
							break;	
						case 10:
							info.setInsuranceNo(data);
							break;	
						case 11:
							info.setInsuranceAmount(new Money(Double.valueOf(data)));
							break;	
						case 12:
							info.setInsuranceDate(sf.parse(data));
							break;	
						case 13:
							info.setBeginDate(sdf.parse(data));
							break;	
						case 14:
							info.setEndDate(sf.parse(data));
							break;
						case 15:
							info.setPremiumAmount(new Money(Double.valueOf(data)));
							break;
						default:
							info.setInsuranceOfType(InsuranceOfTypeEnum.OFFLINE.getCode());
							break;
						}
					}
					pageList.add(info);
				}
			}
			map.put("businessBillBad", businessBillBad);
			return pageList;
	    }
	 
		/**
		 * 返回excel数据
		 * @param cell
		 * @return
		 */
		private static String getValue(org.apache.poi.ss.usermodel.Cell cell,int j) {
			 String cellValue = "";
			if(null==cell)return "";
			if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
				// 返回布尔类型的值
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) { //数值型
				if (HSSFDateUtil.isCellDateFormatted(cell) ) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					if (j==13 || j==14) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					}
	                if(cell.getDateCellValue()!=null){
	                    try {
							cellValue = sdf.format(cell.getDateCellValue());
						} catch (Exception e) {
							logger.error("转换日期格式异常", cell.getStringCellValue());
						}
	                }
				} else { // 纯数字     
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				return cellValue;
			}else {
				return cell.getRichStringCellValue().getString();
			}
		}
		
		
		// 判断行为空
		public static int CheckRowNull(Row hssfRow) {
			int num = 0;
			Iterator<Cell> cellItr = hssfRow.iterator();
			while (cellItr.hasNext()) {
				Cell c = cellItr.next();
				if (c.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					num++;
				}
			}
			return num;
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
