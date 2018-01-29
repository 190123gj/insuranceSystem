package com.born.insurance.webui.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yjf.common.lang.util.StringUtil;

/**
 * @author qichh 分页处理对象 To change the template for this generated type comment
 * go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PaginationObject {
	public static final long DefaultPageSize = 15;
	public static final long MaxPageSize = 50;
	private HttpServletRequest request = null;
	private String view = "";
	private long currentPage = 1;
	private long totalPage = 1;
	private long pageSize = DefaultPageSize;
	private long size = 0;
	private long firstRecord = 0;
	private long lastRecord = 0;
	private QueryString query = null;
	private long navigatorSize = 10;
	private String queryStr = "";
	private String linkPageCss = "StormyWeatherNavigatorLink";
	private String linkPageFooterCss = "StormyWeatherNavigatorFooterLink";
	/**
	 * 页面参数的键值
	 */
	public final static String PAGEPARAM = "Pagination";
	public final static String ListFlag = "ListFlag";
	
	private boolean isViewLinkClass;
	
	public PaginationObject(HttpServletRequest request, long size, String url) {
		this.request = request;
		this.size = size;
		this.view = url;
		init();
	}
	
	public PaginationObject(HttpServletRequest request, long size, String url, boolean isResetPage) {
		this.request = request;
		this.size = size;
		this.view = url;
		init(isResetPage);
	}
	
	public PaginationObject(HttpServletRequest request, long size, String url, boolean isResetPage,
							long pageSize) {
		this.request = request;
		this.size = size;
		this.view = url;
		this.pageSize = pageSize;
		
		init(isResetPage);
	}
	
	public PaginationObject(HttpServletRequest request, long size, String url, long pageSize,
							long pageNumber) {
		this.request = request;
		this.size = size;
		this.view = url;
		this.pageSize = pageSize;
		this.currentPage = pageNumber;
		init();
	}
	
	public PaginationObject(HttpServletRequest request, long size) {
		this.request = request;
		this.size = size;
		init();
	}
	
	public PaginationObject(HttpServletRequest request, String url) {
		this.request = request;
		query = new QueryString(request);
		query.getParams().remove(PAGEPARAM);
		List removeParaList = ReportUtil.parseQueryString(url);
		for (int i = 0; i < removeParaList.size(); i++) {
			
			query.getParams().remove(removeParaList.get(i));
		}
		if (request.getParameter(PAGEPARAM) != null) {
			this.currentPage = Integer.parseInt(request.getParameter(PAGEPARAM));
		}
		this.firstRecord = (this.currentPage - 1) * pageSize;
		this.lastRecord = (this.currentPage) * pageSize;
		//this.firstRecord=0;
		//this.lastRecord=pageSize;
	}
	
	private void init() {
		init(false);
	}
	
	private void init(boolean isResetPage) {
		if (request == null) {
			if (pageSize <= 0)
				return;
			this.totalPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
			if (this.totalPage == 0) {
				this.totalPage = 1;
			}
		} else {
			query = new QueryString(request);
			String listFlag = request.getParameter(ListFlag);
			query.getParams().remove(PAGEPARAM);
			List removeParaList = ReportUtil.parseQueryString(this.view);
			for (int i = 0; i < removeParaList.size(); i++) {
				query.getParams().remove(removeParaList.get(i));
			}
			this.totalPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
			if (this.totalPage == 0) {
				this.totalPage = 1;
			}
			if (!isResetPage) {
				if (request.getParameter(PAGEPARAM) != null) {
					//解决一个页面多个列表的翻页问题	
					if (StringUtil.isNotEmpty(listFlag)) {
						String pathInfo = "";
						if (this.view != null) {
							int index = this.view.indexOf("?");
							if (index > -1) {
								pathInfo = this.view.substring(index + 1);
							}
						}
						String[] str = pathInfo.split("&");
						for (int i = 0; i < str.length; i++) {
							String[] temp = str[i].split("=");
							if (temp[0].equals(ListFlag)) {
								if (temp.length > 0) {
									if (temp[1].equals(listFlag)) {
										this.currentPage = Integer.parseInt(request
											.getParameter(PAGEPARAM));
										break;
									}
								}
							}
							
						}
					} else {
						this.currentPage = Integer.parseInt(request.getParameter(PAGEPARAM));
					}
				}
			} else {
				this.currentPage = 1;
			}
		}
		
		if (this.currentPage > this.totalPage)
			this.currentPage = 1;
		if (this.currentPage < this.totalPage) {
			this.firstRecord = (this.currentPage - 1) * pageSize;
			this.lastRecord = (this.currentPage) * pageSize;
		} else {
			this.firstRecord = (this.currentPage - 1) * pageSize;
			this.lastRecord = size;
		}
	}
	
	public long getFirstRecord() {
		return this.firstRecord;
	}
	
	public long getLastRecord() {
		return this.lastRecord;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
		init();
	}
	
	public void setView(String view) {
		this.view = view;
	}
	
	public long getCurrentPage() {
		return currentPage;
	}
	
	public long getTotalPage() {
		return this.totalPage;
	}
	
	public String getQueryStr() {
		if (queryStr.length() == 0) {
			queryStr = query.toString();
		}
		return queryStr;
	}
	
	/**
	 * 生成分页html代码
	 * @return
	 */
	public String getHtmlCode() {
		StringBuffer str = new StringBuffer(500);
		String queryStr = this.getQueryStr();
		String linkUrl = "?";
		if (this.view != null && this.view.indexOf("?") > -1) {
			linkUrl = "&";
		}
		if (queryStr.length() > 0) {
			queryStr = "&" + queryStr;
		}
		str.append(" &nbsp;<a href='" + this.view + linkUrl + "Pagination=1" + queryStr
					+ "' class='" + this.linkPageFooterCss + "'>首页</a>&nbsp;");
		if (currentPage != 1) {
			str.append("<a href='" + this.view + linkUrl + "Pagination=" + (currentPage - 1)
						+ queryStr + "' class='" + this.linkPageFooterCss + "'>上一页</a>&nbsp;");
		}
		if (totalPage <= navigatorSize) {
			for (long i = 0; i < navigatorSize && i < totalPage; i++) {
				if ((i + 1) != currentPage)
					str.append("<a href='" + this.view + linkUrl + "Pagination=" + (i + 1)
								+ queryStr + "' class='" + this.linkPageFooterCss + "'>" + (i + 1)
								+ "</a>&nbsp;");
				else {
					str.append("<b>" + (i + 1) + "</b>" + "&nbsp;");
				}
			}
		} else {
			for (long i = 0; i < navigatorSize; i++) {
				long k = 0;
				if (currentPage - navigatorSize / 2 <= 0) {
					k = i + 1;
				} else if (currentPage + navigatorSize / 2 >= totalPage) {
					k = totalPage - navigatorSize + (i + 1);
				} else {
					k = currentPage - navigatorSize / 2 + i;
				}
				if (k > totalPage) {
					break;
				}
				if (k != currentPage)
					str.append("<a href='" + this.view + linkUrl + "Pagination=" + (k) + queryStr
								+ "' class='" + this.linkPageFooterCss + "'>" + k + "</a>&nbsp;");
				else {
					str.append("<b>" + k + "</b>" + "&nbsp;");
				}
			}
		}
		str.append("&nbsp; 共 " + totalPage + "&nbsp;页 &nbsp;");
		if (currentPage < totalPage) {
			str.append("<a href='" + this.view + linkUrl + "Pagination=" + (currentPage + 1)
						+ queryStr + "' class='" + this.linkPageFooterCss + "'>下一页</a>&nbsp;");
		}
		str.append("<a href='" + this.view + linkUrl + "Pagination=" + totalPage + queryStr
					+ "' class='" + this.linkPageFooterCss + "'>尾页</a>");
		str.append("&nbsp;");
		str.append("(共 " + this.size + " 条记录)");
		return str.toString();
	}
	
	/**
	 * 获取显示页码数量
	 * @return
	 */
	public long getNavigatorSize() {
		return navigatorSize;
	}
	
	/**
	 * 设置显示页码数量
	 * @param i
	 */
	public void setNavigatorSize(long i) {
		navigatorSize = i;
	}
	
	/**
	 * 获取超链接样式
	 * @return
	 */
	public String getLinkPageCss() {
		return linkPageCss;
	}
	
	/**
	 * 设置超链接样式名称
	 * @param string
	 */
	public void setLinkPageCss(String string) {
		linkPageCss = string;
	}
	
	/**
	 * 获取所有记录数
	 * @return
	 */
	public long getSize() {
		return size;
	}
	
	/**
	 * 设置所有记录数
	 * @param i
	 */
	public void setSize(long i) {
		size = i;
	}
	
	/**
	 * 获取当前页面显示记录数
	 * @return
	 */
	public long getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置翻页的页面链接路径
	 * @return
	 */
	public String getView() {
		return view;
	}
	
	/**
	 * 设置当前页值
	 * @param i
	 */
	public void setCurrentPage(long i) {
		currentPage = i;
	}
	
	/**
	 * 
	 * @return
	 */
	public QueryString getQuery() {
		return query;
	}
	
	/**
	 * @param string
	 */
	public void setQuery(QueryString string) {
		query = string;
	}
	
	public boolean isViewLinkClass() {
		return isViewLinkClass;
	}
	
	public void setViewLinkClass(boolean isViewLinkClass) {
		if (!isViewLinkClass) {
			this.linkPageFooterCss = "";
			this.linkPageCss = "";
		}
		this.isViewLinkClass = isViewLinkClass;
	}
}
