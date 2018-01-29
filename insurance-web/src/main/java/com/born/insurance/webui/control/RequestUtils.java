package com.born.insurance.webui.control;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class RequestUtils {
	protected static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);
	//~ Static fields/initializers /////////////////////////////////////////////
	
	//~ Instance fields ////////////////////////////////////////////////////////
	
	private final Hashtable map = new Hashtable();
	private final HttpServletRequest request;
	private List fileItems = new ArrayList();
	private final boolean isMultipart;
	private boolean isCovert = false;
	
	//~ Constructors ///////////////////////////////////////////////////////////
	
	public RequestUtils(HttpServletRequest request) {
		this(request, -1, -1, null, true);
	}
	
	public RequestUtils(HttpServletRequest request, boolean isThrowException) {
		
		this(request, -1, -1, null, isThrowException);
	}
	
	boolean isCovert(HttpServletRequest request) {
		ServletContext context = (ServletContext) request.getAttribute("servletContext");
		String serverInfo = context == null ? "" : context.getServerInfo();
		//        if(serverInfo.indexOf(UtilJ2eeCompat.TOMCAT) >-1)
		//        {
		//        	return false;
		//        }  
		return true;
	}
	
	public RequestUtils(HttpServletRequest request, int maxMemorySize, int maxFileSize,
						String tempDirectory, boolean isThrowException) {
		this.request = request;
		if (isThrowException && request.getContentLength() > 1024 * 1024 * 32) {
			throw new ComponentException("������󳬹�32M!");
		}
		RequestContext rc = new ServletRequestContext(request);
		
		isMultipart = ServletFileUpload.isMultipartContent(rc);
		
		if (isMultipart) {
			isCovert = isCovert(request);
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			
			try {
				fileItems = upload.parseRequest(request);
				//                if ((maxMemorySize == -1) || (maxFileSize == -1)) {
				//                    fileItems = upload.parseRequest(request);
				//                } else if (tempDirectory == null) {
				//                    String tmpDir = System.getProperty("user.dir");
				//                    fileItems = upload.parseRequest(request, maxMemorySize, maxFileSize, tmpDir);
				//                } else {
				//                    fileItems = upload.parseRequest(request, maxMemorySize, maxFileSize, tempDirectory);
				//                }
				
				for (int i = 0; i < fileItems.size(); i++) {
					FileItem item = (FileItem) fileItems.get(i);
					map.put(item.getFieldName(), item);
				}
			} catch (FileUploadException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public RequestUtils(HttpServletRequest request, int maxMemorySize, int maxFileSize,
						boolean isThrowException) {
		this(request, maxMemorySize, maxFileSize, null, isThrowException);
	}
	
	public HttpServletRequest getRequest() {
		return this.request;
	}
	
	//~ Methods ////////////////////////////////////////////////////////////////
	public boolean contains(String name) {
		String value = getString(name);
		
		return value != null;
	}
	
	public boolean equals(String name, String compareValue) {
		String value = getString(name);
		
		return (value != null) && value.equals(compareValue);
	}
	
	public boolean equalsIgnoreCase(String name, String compareValue) {
		String value = getString(name);
		
		return (value != null) && value.equalsIgnoreCase(compareValue);
	}
	
	/**
	 * @param name
	 * @return
	 * @deprecated contains(String)
	 */
	@Deprecated
	public boolean getBoolean(String name) {
		/*String value = getString(name);

		return value != null;*/
		return contains(name);
	}
	
	/**
	 * @param name
	 * @return
	 * @deprecated equals(String)
	 */
	@Deprecated
	public boolean getBoolean(String name, String compareValue) {
		return equals(name, compareValue);
	}
	
	public Date getDate(String name, String dateFormat) {
		String value = null;
		
		if (!isMultipart()) {
			value = request.getParameter(name);
		} else {
			value = getFieldValueFromMultiypartRequest(name);
		}
		
		Date result = null;
		try {
			result = DateUtil.parseDateNoTime(value, dateFormat);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	public double getDouble(String name) {
		return getDouble(name, -1);
	}
	
	public double getDouble(String name, double defaultValue) {
		String value = null;
		
		if (!isMultipart()) {
			value = request.getParameter(name);
		} else {
			value = getFieldValueFromMultiypartRequest(name);
		}
		
		double result = parseDouble(value);
		result = (result == -1) ? defaultValue : result;
		
		return result;
	}
	
	public double[] getDoubleArray(String name) {
		String[] value = getStringArray(name);
		double[] result = new double[value.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = parseDouble(value[i]);
		}
		
		return result;
	}
	
	public InputStream getFielInputStream(String fieldName) throws Exception {
		FileItem item = getFile(fieldName);
		
		return ((item != null) & !item.isFormField()) ? item.getInputStream() : null;
	}
	
	public FileItem getFile(String fieldName) {
		//Iterator it = fileItems.keySet().iterator();
		//while ( it.hasNext())
		
		/*
		for ( int i=0;i<fileItems.size();i++)
		{
		    //String name = (String)it.next();
		    FileItem item = (FileItem)fileItems.get(i);
		    if ( !item.isFormField() && item.getFieldName().equals(fieldName))
		        return item;
		}*/
		return (FileItem) map.get(fieldName);
	}
	
	/*
	    public String getHeader(String name)
	    {
	        return null;
	    }

	    public String getHeaderNames()
	    {
	        return null;
	    }

	    public javax.servlet.http.Cookie getCookie(String name)
	    {
	        return null;
	    }

	    public javax.servlet.http.Cookie[] getCookies()
	    {
	        return null;
	    }

	    public String getReferer()
	    {
	        return null;
	    }

	    public String getRemoteAddr()
	    {
	        return null;
	    }

	    public String getServerName()
	    {
	        return null;
	    }

	    public String getScheme()
	    {
	        return null;
	    }

	    public String getPort()
	    {
	        return null;
	    }

	    public String getQueryString()
	    {
	        return null;
	    }
	*/
	
	/** method for fileupload **/
	public String getFileName(String fieldName) {
		FileItem item = getFile(fieldName);
		String fname = (item != null) ? item.getName() : null;
		
		if ((fname != null) && (fname.length() > 0)) {
			fname = fname.substring(fname.lastIndexOf(File.separatorChar) + 1);
		}
		
		return fname;
	}
	
	public String[] getFileNames() {
		FileItem[] items = getFiles();
		String[] result = new String[items.length];
		
		for (int i = 0; i < items.length; i++) {
			result[i] = items[i].getName();
			
			if ((result[i] != null) && (result[i].length() > 0)) {
				result[i] = result[i].substring(result[i].lastIndexOf(File.separatorChar) + 1);
			}
		}
		
		return result;
	}
	
	public long getFileSize(String fieldName) {
		FileItem item = getFile(fieldName);
		
		return (item != null) ? item.getSize() : 0;
	}
	
	public FileItem[] getFiles() {
		ArrayList arr = new ArrayList();
		
		//Iterator it = fileItems.keySet().iterator();
		//while ( it.hasNext())
		for (int i = 0; i < fileItems.size(); i++) {
			//String name = (String)it.next();
			FileItem item = (FileItem) fileItems.get(i);
			
			if (!item.isFormField()) {
				arr.add(item);
			}
		}
		
		FileItem[] result = new FileItem[arr.size()];
		
		return (FileItem[]) arr.toArray(result);
	}
	
	public float getFloat(String name) {
		return getFloat(name, -1);
	}
	
	public float getFloat(String name, float defaultValue) {
		String value = null;
		
		if (!isMultipart()) {
			value = request.getParameter(name);
		} else {
			value = getFieldValueFromMultiypartRequest(name);
		}
		
		float result = parseFloat(value);
		result = (result == -1) ? defaultValue : result;
		
		return result;
	}
	
	public float[] getFloatArray(String name) {
		String[] value = getStringArray(name);
		float[] result = new float[value.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = parseFloat(value[i]);
		}
		
		return result;
	}
	
	public int getInt(String name) {
		return getInt(name, -1);
	}
	
	public int getInt(String name, int defaultValue) {
		String value = null;
		
		if (!isMultipart()) {
			value = request.getParameter(name);
		} else {
			value = getFieldValueFromMultiypartRequest(name);
		}
		
		int result = parseInt(value);
		result = result == -1 ? defaultValue : result;
		
		return result;
	}
	
	public int[] getIntArray(String name) {
		String[] value = getStringArray(name);
		int[] result = new int[value.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = parseInt(value[i]);
		}
		
		return result;
	}
	
	public long getLong(String name) {
		return getLong(name, -1);
	}
	
	public long getLong(String name, long defaultValue) {
		String value = null;
		
		if (!isMultipart()) {
			value = request.getParameter(name);
		} else {
			value = getFieldValueFromMultiypartRequest(name);
		}
		
		long result = parseLong(value);
		result = (result == -1) ? defaultValue : result;
		
		return result;
	}
	
	public long[] getLongArray(String name) {
		String[] value = getStringArray(name);
		long[] result = new long[value.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = parseLong(value[i]);
		}
		
		return result;
	}
	
	public boolean isMultipart() {
		return isMultipart;
	}
	
	public String getString(String name) {
		return getString(name, null);
	}
	
	public String getParameter(String name) {
		return getString(name, null);
	}
	
	public String getString(String name, String defaultValue) {
		String value = null;
		if (!isMultipart())
			value = request.getParameter(name);
		else {
			value = getFieldValueFromMultiypartRequest(name);
			if (this.isCovert && StringUtil.isNotEmpty(value)) {
				value = getChinese(value);
			}
		}
		
		value = value == null ? defaultValue : value;
		return value;
	}
	
	private static String getChinese(String value) {
		if (value == null)
			return value;
		try {
			byte temp[] = value.getBytes("iso-8859-1");
			value = new String(temp, "gb2312");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return value;
	}
	
	public String[] getParameterValues(String name) {
		return this.getStringArray(name);
	}
	
	public String[] getStringArray(String name) {
		String[] result = null;
		
		if (!isMultipart()) {
			result = request.getParameterValues(name);
		} else {
			result = getFieldValuesFromMultiypartRequest(name);
		}
		
		if (result == null) {
			result = new String[0];
		}
		
		return result;
	}
	
	public String[] getStringArray(String name, String splitChars) {
		String value = getString(name);
		
		//return value.split(splitChars);
		return StringUtils.split(value, splitChars);
	}
	
	public void writeFileToDisk(String fieldName, File file) throws Exception {
		FileItem item = getFile(fieldName);
		
		if (item != null) {
			item.write(file);
		}
	}
	
	public void writeFileToDisk(String fieldName, String filepath) throws Exception {
		File file = new File(filepath);
		
		if (!file.exists()) {
			throw new Exception("file not exist");
		}
		
		if (!file.canWrite()) {
			throw new Exception("file write delay");
		}
		
		writeFileToDisk(fieldName, file);
	}
	
	public Map getParameterMap() {
		if (!isMultipart())
			return request.getParameterMap();
		else {
			Map returnMap = new HashMap();
			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				FileItem fi = (FileItem) e.getValue();
				if (fi != null) {
					returnMap.put(e.getKey(), fi.getString());
				}
			}
			return returnMap;
		}
	}
	
	private String getFieldValueFromMultiypartRequest(String fieldName) {
		FileItem item = (FileItem) map.get(fieldName);
		
		return (item != null) ? item.getString() : null;
	}
	
	private String[] getFieldValuesFromMultiypartRequest(String fieldName) {
		ArrayList arr = new ArrayList();
		
		//Iterator it = fileItems.keySet().iterator();
		//while ( it.hasNext())
		for (int i = 0; i < fileItems.size(); i++) {
			//String name = (String)it.next();
			FileItem item = (FileItem) fileItems.get(i);
			
			if (item.getFieldName().equals(fieldName)) {
				if (this.isCovert) {
					arr.add(getChinese(item.getString()));
				} else {
					arr.add(item.getString());
				}
				
			}
		}
		
		String[] result = new String[arr.size()];
		
		return (String[]) arr.toArray(result);
	}
	
	private double parseDouble(String name) {
		double result = -1;
		
		if (name != null) {
			try {
				result = Double.parseDouble(name);
			} catch (Exception e) {
			}
		}
		
		;
		
		return result;
	}
	
	private float parseFloat(String name) {
		float result = -1;
		
		if (name != null) {
			try {
				result = Float.parseFloat(name);
			} catch (Exception e) {
			}
		}
		
		;
		
		return result;
	}
	
	/** private method for public **/
	private int parseInt(String name) {
		int result = -1;
		
		if (name != null) {
			try {
				result = Integer.parseInt(name);
			} catch (Exception e) {
			}
		}
		
		;
		
		return result;
	}
	
	private long parseLong(String name) {
		long result = -1;
		
		if (name != null) {
			try {
				result = Long.parseLong(name);
			} catch (Exception e) {
			}
		}
		
		;
		
		return result;
	}
}
