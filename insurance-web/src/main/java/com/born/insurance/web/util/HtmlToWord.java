package com.born.insurance.web.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 将html文档转化为word文档。目前用于制式合同的word导出
 * 
 * 
 * @author hjiajie
 * 
 */
public class HtmlToWord {
	
	/**
	 * 
	 * @param str 要写的content
	 * @param filePath 写出位置
	 * @throws FileNotFoundException
	 */
	public static void writeWordFile(String str, String filePath) throws FileNotFoundException {
		FileOutputStream ostream = new FileOutputStream(filePath);
		writeWordFile(str, ostream);
	}
	
	/**
	 * 
	 * @param str 要写的content
	 * @param stream 写出流 ，一般使用response.getoutputstream
	 */
	public static void writeWordFile(String str, OutputStream stream) {
		// 生成临时文件名称
		try {
			String content = str;
			byte[] b = content.getBytes("gb2312");
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			POIFSFileSystem poifs = new POIFSFileSystem();
			DirectoryEntry directory = poifs.getRoot();
			DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
			poifs.writeFilesystem(stream);
			bais.close();
			//stream.flush();
			stream.close();
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
	}
	
	public static boolean writeWordFile(String[] args) {
		
		boolean w = false;
		
		String path = null;
		if (args == null || args.length == 0) {
			path = "d:\\index.htm";
		} else
			path = args[0];
		try {
			if (!"".equals(path)) {
				
				// 检查目录是否存在
				File fileDir = new File(path);
				if (fileDir.exists()) {
					
					// 生成临时文件名称
					String fileName = "d:\\atest.doc";
					String content = "<html>" + "<head>你好</head>" + "<body>" + "<table>" + "<tr>"
										+ "<td>信息1</td>" + "<td>信息2</td>" + "<td>t3</td>" + "<tr>"
										+ "</table>" + "</body>" + "</html>";
					FileInputStream inputStream = new FileInputStream(path);
					byte[] b = new byte[inputStream.available()];
					inputStream.read(b);
					ByteArrayInputStream bais = new ByteArrayInputStream(b);
					POIFSFileSystem poifs = new POIFSFileSystem();
					DirectoryEntry directory = poifs.getRoot();
					DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
					FileOutputStream ostream = new FileOutputStream(fileName);
					poifs.writeFilesystem(ostream);
					bais.close();
					ostream.close();
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return w;
	}
	
	public static void main(String[] args) {
		try {
			String content = "<html>" + "<head>你好</head>" + "<body>" + "<table>" + "<tr>"
								+ "<td>信息1</td>" + "<td>信息2</td>" + "<td>t3</td>" + "<tr>"
								+ "</table>" + "</body>" + "</html>";
			String str = content;
			FileOutputStream ostream = new FileOutputStream("d:\\2.doc");
			writeWordFile(str, ostream);
			//ostream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//		writeWordFile(args);
	}
	
}