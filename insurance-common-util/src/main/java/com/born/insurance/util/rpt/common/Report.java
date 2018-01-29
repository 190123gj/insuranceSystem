package com.born.insurance.util.rpt.common;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jdom.*;

import org.jdom.input.SAXBuilder;

public abstract class Report {
	protected final String XML_DIR = "WEB-INF"+ File.separator +"rpt"+File.separator+"xml"+File.separator;
	
	protected final String defaultXmlFileName = "default.xml";
	
	 /**
	  * 
	  * @param serverRealPath
	  * @param xmlFileName  xml模板文件的存放URL
	  * @return
	  */
	  public Document toDocument(String serverRealPath,String xmlFileName) {
	      Document document = null;

	      try {
	          SAXBuilder saxBuilder = new SAXBuilder();
	          document = saxBuilder.build(serverRealPath + File.separator +XML_DIR + xmlFileName);
	      } catch (Exception ex) {
	          ex.printStackTrace();
	      }

	      return document;
	  }
	  
	  /**
		  * 
		  * @param serverRealPath
		  * @param xmlFileName  xml模板文件的存放URL
		  * @return
		  */
		  public Document getDefaultDocument(String serverRealPath,String reportName,Map<String,String> firstRow) {
		      Document document = null;

		      try {
		          SAXBuilder saxBuilder = new SAXBuilder();
		          document = saxBuilder.build(serverRealPath + File.separator +XML_DIR + defaultXmlFileName);
		          
		          Element root = document.getRootElement();
			      Element table = root.getChild("Report-Body").getChild("Table");
			      
			      if(reportName!=null){
			    	  Element rHeader = root.getChild("Report-Header");
				      rHeader.setAttribute("Report-Name",reportName);
			      }
			      
			      Set<String> keys = firstRow.keySet();

			      for (String key : keys) {
			    	   Element th =  table.getChild("Table-Header");
			    	   Element headerRow = th.getChild("Table-Row");
			    	   Element newHCell = new Element("Table-Cell");
			    	   newHCell.setAttribute("BgColor", "#FFFFFF");
			    	   newHCell.setAttribute("Title",key);
			    	   newHCell.setAttribute("Width", "20cm");
			    	   headerRow.addContent(newHCell);
			    	   
			    	   Element tb =  table.getChild("Table-Body");
			    	   Element bodyRow = tb.getChild("Table-Row");
			    	   Element newBCell = new Element("Table-Cell");
			    	   newBCell.setAttribute("Data-Type", "STRING");
			    	   newBCell.setAttribute("Data-Field",key);
			    	   newBCell.setAttribute("Align", "left");
			    	   bodyRow.addContent(newBCell);
			      }
		          
		      } catch (Exception ex) {
		          ex.printStackTrace();
		      }

		      return document;
		  }

	  /**
	   * <p>Description:输出报表的抽象函数</p>
	   * @param reportData Document 数据xml
	   * @param reportStyle Document 数据格式xml
	   * @throws Exception
	   * <p>Create Time: 2014-08-25</p>
	   */
	  public abstract void print(Document reportData, Document reportStyle)
	                           throws Exception;

}
