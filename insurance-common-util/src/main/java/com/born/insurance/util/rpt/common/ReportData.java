package com.born.insurance.util.rpt.common;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

public class ReportData {
	 private ReportHead head;
	 private List<Map<String,String>> table;

	  /**
	   * <p>Description: ReportData构造函数</p>
	   * @param head ReportHead 报表头
	   * @param table List 数据列表
	   * <p>Create Time: 2003-03-27</p>
	   */
	  public ReportData(ReportHead head, List<Map<String,String>> table) {
	      this.head = head;
	      this.table = table;
	  }

	  /**
	   * <p>Description: 获取数据XML的Document</p>
	   * @return Document
	   * <p>Create Time: 2003-03-27</p>
	   */
	  public Document getXMLDocument() {
	      Document document = null;

	      try {
	          SAXBuilder saxBuilder = new SAXBuilder();
	          //System.out.print("\n data_xml="+this.getXMLString()+"\n");
	          StringReader stringIn = new StringReader(this.getXMLString());
	          document = saxBuilder.build(stringIn);
	      } catch (Exception ex) {
	          ex.printStackTrace();
	      }

	      return document;
	  }

	  /**
	   * <p>Description: 获取数据XML的String</p>
	   * @return String
	   * <p>Create Time: 2003-03-27</p>
	   */
	  public String getXMLString() {
	      StringBuffer xmlStr = new StringBuffer();
	      Iterator<Map<String, String>> data = this.table.iterator();
	      Map<String, String> hData = null;
	      Iterator<String> iter = null;
	      xmlStr.append("<ReportData>");
	      xmlStr.append(head.toXmlReportHead());
	      xmlStr.append("<Table>");

	      while (data.hasNext()) {
	          hData =  data.next();
	          iter = hData.keySet().iterator();
	          xmlStr.append("<Row>");

	          while (iter.hasNext()) {
	              String key = (String) iter.next();
	              xmlStr.append("<" + key + ">");
	              xmlStr.append("<![CDATA[" + hData.get(key) + "]]>");
	              xmlStr.append("</" + key + ">");
	          }

	          xmlStr.append("</Row>");
	      }

	      xmlStr.append("</Table>");
	      xmlStr.append("</ReportData>");

	      return xmlStr.toString();
	  }
}
