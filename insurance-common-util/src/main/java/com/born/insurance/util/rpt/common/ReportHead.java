package com.born.insurance.util.rpt.common;

import org.jdom.Element;

public class ReportHead {
	  private String date;
	  
	  private String filters;

	  public ReportHead() {
	  }

	  /**
	   * <p>Description: 获取Date</p>
	   * @return String
	   * <p>Create Time: 2014-08-26</p>
	   */
	  public String getDate() {
	      return date;
	  }

	  /**
	   * <p>Description: 设置Date</p>
	   * @param date String
	   * <p>Create Time: 2014-08-26</p>
	   */
	  public void setDate(String date) {
	      this.date = date;
	  }

	  
	 
		public String getFilters() {
			return filters;
		}
		
		public void setFilters(String filters) {
			this.filters = filters;
		}

	/**
	   * <p>Description: 获取报表头XML的String</p>
	   * @return String
	   * <p>Create Time: 2014-08-26</p>
	   */
	  public String toXmlReportHead() {
	      date = (date == null) ? " " : date;
	      filters = (filters == null) ? " " : filters;
	      return "<Head><Date><![CDATA[" + this.date + "]]></Date><Filters><![CDATA[" + this.filters + "]]></Filters></Head>";
	  }

	  /**
	   * <p>Description: 使用XML Element设置Head报表头</p>
	   * @param head Element
	   * <p>Create Time: 2014-08-26</p>
	   */
	  public void setHead(Element head) {
	      this.date = head.getChildTextTrim("Date");
	      this.filters = head.getChildTextTrim("Filters");
	  }

}
