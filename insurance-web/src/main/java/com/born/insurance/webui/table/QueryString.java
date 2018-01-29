//QueryString class @0-3C073414
package com.born.insurance.webui.table;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理url参数
 * @author qichh
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryString extends Query {
	private final String queryString;
	
	public QueryString(HttpServletRequest req) {
		super(req);
		this.queryString = req.getQueryString();
		parseQueryString();
	}
	
	public String getQueryString() {
		return queryString;
	}
	
	private String paramToString(String name) {
		if (name == null)
			return "";
		String[] qvals = getParameterValues(name);
		if (qvals == null)
			return "";
		StringBuffer result = new StringBuffer();
		if (qvals.length == 1) {
			result.append(name + "=" + qvals[0]);
		} else {
			for (int i = 0; i < qvals.length; i++) {
				result.append(name + "[" + i + "]=" + qvals[i]);
				if (i < qvals.length - 1)
					result.append("&");
			}
		}
		return result.toString();
	}
	
	private void parseQueryString() {
		if (queryString == null)
			return;
		final String EMPTY = "";
		StringTokenizer st = new StringTokenizer(queryString, "&");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			int pos = token.indexOf("=");
			String value = EMPTY;
			if (pos > -1) {
				//        try {
				//            value = new String( java.net.URLDecoder.decode( 
				//                    token.substring( pos + 1 )).getBytes( defaultEncoding ), encoding );
				//        } catch ( java.io.UnsupportedEncodingException e ) {
				//            value = java.net.URLDecoder.decode( token.substring( pos + 1 ) );
				//        }
				value = token.substring(pos + 1);
				//Debug.log(value+"==value1");
				token = token.substring(0, pos);
				value = this.req.getParameter(token);
				//Debug.log(value+"==value2");
			}
			Vector items = null;
			if ((items = (Vector) params.get(token)) == null) {
				items = new Vector();
			}
			
			items.addElement(value);
			params.put(token, items);
			
		} // End of while st.hasMoreTokens()
	}
	
}
//End QueryString class

