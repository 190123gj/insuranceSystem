package com.born.insurance.util.fop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.InputHandler;
import org.apache.fop.apps.Options;
import org.apache.fop.apps.XSLTInputHandler;
import org.apache.fop.messaging.MessageHandler;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class FopReport {
	
	private static ConsoleLogger log = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
	
	private static Logger logger = LoggerFactory.getLogger(FopReport.class);
	
	////	private final static String sConfigFile = "";
	
	////	private static String REPORT_ROOT = "D:\\date_fop";
	
	public static ReportData createReport(String sXslTemplateFile, byte[] bXmlData,
											String configFile) throws Exception {
		
		ReportData reportData = new ReportData();
		
		reportData.setContentType("application/pdf");
		//设置日志
		try {
			
			MessageHandler.setScreenLogger(log);
			logger.debug("sXslTemplateFile:" + sXslTemplateFile);
			logger.debug("configFile:" + configFile);
			
			logTime("step 1");
			//将xml文件内容写到临时文件
			File xmlFile = File.createTempFile("FOP", ".tmp");
			FileOutputStream fos = new FileOutputStream(xmlFile);
			fos.write(bXmlData);
			fos.close();
			logTime("step 2");
			//获取文件路径
			String xslFileName = sXslTemplateFile;
			File xslFile = new File(xslFileName);
			logTime("step 3");
			//将xml,xsl合并成fo格式文件
			//	        InputSource foInputSource=generateFO(xmlFile,xslFile);
			//fop输出流
			logTime("step 4");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			logTime("step 5");
			//设成pdf文件
			Driver driver = new Driver();
			logTime("step 6");
			driver.setLogger(log);
			logTime("step 7");
			driver.setRenderer(Driver.RENDER_PDF);
			
			logTime("step 8");
			InputHandler inputHandler = new XSLTInputHandler(xmlFile, xslFile);
			logTime("step 9");
			org.xml.sax.XMLReader parser = inputHandler.getParser();
			logTime("step 10");
			driver.setOutputStream(out);
			
			Options options = new Options(new File(configFile));
			
			logTime("step 11");
			driver.render(parser, inputHandler.getInputSource());
			logTime("step 12");
			//删除临时文件
			xmlFile.delete();
			reportData.setData(out.toByteArray());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			org.apache.fop.image.FopImageFactory.resetCache();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//no need to throw the exception
		}
		logTime("step 12");
		return reportData;
		
	}
	
	public static ReportData createReport(byte[] sXslData, byte[] bXmlData, String configFile)
																								throws Exception {
		
		ReportData reportData = new ReportData();
		
		reportData.setContentType("application/pdf");
		//设置日志
		try {
			MessageHandler.setScreenLogger(log);
			
			logTime("step 1");
			//将xml文件内容写到临时文件
			File xmlFile = File.createTempFile("FOP", ".tmp");
			FileOutputStream fos = new FileOutputStream(xmlFile);
			fos.write(bXmlData);
			fos.close();
			
			logTime("step 2");
			//将xsl文件内容写到临时文件
			File xslFile = File.createTempFile("FOP", ".tmp");
			FileOutputStream fos2 = new FileOutputStream(xslFile);
			fos2.write(sXslData);
			fos2.close();
			
			logTime("step 3");
			//将xml,xsl合并成fo格式文件
			//InputSource foInputSource=generateFO(xmlFile,xslFile);
			//fop输出流
			logTime("step 4");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			logTime("step 5");
			//设成pdf文件
			Driver driver = new Driver();
			logTime("step 6");
			driver.setLogger(log);
			logTime("step 7");
			driver.setRenderer(Driver.RENDER_PDF);
			
			logTime("step 8");
			InputHandler inputHandler = new XSLTInputHandler(xmlFile, xslFile);
			logTime("step 9");
			org.xml.sax.XMLReader parser = inputHandler.getParser();
			logTime("step 10");
			driver.setOutputStream(out);
			Options options = new Options(new File(configFile));
			
			logTime("step 11");
			driver.render(parser, inputHandler.getInputSource());
			logTime("step 12");
			//删除临时文件
			xmlFile.delete();
			reportData.setData(out.toByteArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			org.apache.fop.image.FopImageFactory.resetCache();
		} catch (Exception e) {
			e.printStackTrace();
			//no need to throw the exception
		}
		logTime("step 12");
		return reportData;
		
	}
	
	public static void logTime(String pStep) {
		
		logger.info(pStep + ":" + String.valueOf(System.currentTimeMillis()));
		
	}
	/*
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		try {
			String sFile = "D:\\date_fop\\guarantee_contract.xml";
			String configFile = "D:\\opt\\apache-tomcat-7.0.39-yrd\\webapps\\fop\\userconfig.xml";
			String xslFile = "D:\\opt\\apache-tomcat-7.0.39-yrd\\webapps\\fop\\xsl\\guarantee_contract.xsl";
			FileInputStream fis = new FileInputStream(sFile);
			File f = new File(sFile);
			byte[] buf = new byte[(int) f.length()];
			fis.read(buf, 0, (int) f.length());
			fis.close();
			ReportData data = null;
			long t = System.currentTimeMillis();
			data = FopReport.createReport(xslFile, buf, configFile);
			long t1 = System.currentTimeMillis();
			System.out.println(t1 - t);
			FileOutputStream fos = new FileOutputStream("D:\\date_fop\\guarantee_contract.pdf");
			fos.write(data.getData());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	*/
}
