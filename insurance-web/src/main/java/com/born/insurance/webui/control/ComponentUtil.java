/**
 * ´´½¨ɕǚ 2003-10-16
 *
 * ¸��¼�\uFFFD
 * ´°¿ؠ> ˗ѡЮ > Java > ´�³ƜuFFFD> ´�עʜuFFFD
 */
package com.born.insurance.webui.control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author qch
 * @created 2003-10-26
 * @edit 2003-11-30
 * @edit 2003-12-07
 * <P>
 * Description:͡¹©һЩة¼�½µĳ�¯ʨ׃хϢ
 * </P>
 * »򈢇숳·¾\uFFFD »򈡾�¼� Ɛ¶Ф¯τ¼�F�\uFFFD ½«·�¼�ȳԃxmlƤ׃ »򈠃omponent.xmlƤ׃µņ匼՜uFFFD
 * component.xml}ؓ <?xml version="1.0" encoding="utf-8" ?> <root> <jsDir
 * value="js"/> <imagesDir value="images"/> <cssDir value="css"/> <resFilePath
 * value="component/webapp/res"/> <resRequestPath value="component/res"/>
 * <!--first:resfilepath= the path of the application + "/" + resFilePath + "/"
 * + jsDir --> <!--second:jsfilepath= the filepath + "/" + resFilePath + "/" +
 * jsDir --> <rootFilePath value=""/> <rootRequestPath
 * value="http://192.168.1.117:8080"/> <serverEventRequest
 * value="component/control/GenericRequest"/> <UpdateJsEveryTime value="false"/>
 * </root>
 */
public class ComponentUtil {
	
	public static final String module = ComponentUtil.class.getName();
	
	/**
	 * <P>
	 * ��ԴĿ¼·��
	 * </P>
	 */
	protected String RESFILEPATH = "resFilePath";
	/**
	 * <P>
	 * ��Դ����·��
	 * </P>
	 */
	protected String RESREQUESTPATH = "resRequestPath";
	/**
	 * <P>
	 * ��Ŀ¼
	 * </P>
	 */
	protected String ROOTFILEPATH = "rootFilePath";
	/**
	 * <P>
	 * �����·��
	 * </P>
	 */
	protected String ROOTREQUESTPATH = "rootRequestPath";
	/**
	 * �������¼�����·��
	 */
	protected String SERVEREVENTREQUEST = "serverEventRequest";
	/**
	 * ��������·��
	 */
	protected String SEARCHDATAREQUEST = "searchDataRequest";
	
	/**
	 * <P>
	 * javaScriptĿ¼��
	 * </P>
	 */
	protected String JSDIR = "jsDir";
	/**
	 * <P>
	 * ͼƬ�ڵ���
	 * </P>
	 */
	protected String IMAGESDIR = "imagesDir";
	/**
	 * <P>
	 * ��ʽ�ڵ���
	 * </P>
	 */
	protected String CSSDIR = "cssDir";
	/**
	 * �Ƿ�ÿ�θ���
	 */
	protected String ISUPDATEJS = "UpdateJsEveryTime";
	/**
	 * <P>
	 * �����ļ���
	 * </P>
	 */
	private final String COMPONENTUTIL_CONFIG_FILE = "component.xml";
	/**
	 * <P>
	 * ���Catch
	 * </P>
	 */
	protected Map dataMap = new HashMap();
	/**
	 * <P>
	 * ʵ��
	 * </P>
	 */
	private static ComponentUtil _instance = null;
	
	/**
	 * <P>
	 * ���캯��
	 * </P>
	 */
	private ComponentUtil() {
	}
	
	/**
	 * <P>
	 * ��ȡʵ��
	 * </P>
	 * @return
	 */
	public static ComponentUtil getInstance() {
		if (null != _instance)
			return _instance;
		_instance = new ComponentUtil();
		_instance.getComponentSetting();
		if (_instance.dataMap.isEmpty())//û��Ĭ�Ϸ���
		{
			_instance.dataMap.put(_instance.JSDIR, "js");
			_instance.dataMap.put(_instance.IMAGESDIR, "images");
			_instance.dataMap.put(_instance.CSSDIR, "css");
			
			_instance.dataMap.put(_instance.JSDIR, "js");
			_instance.dataMap.put(_instance.IMAGESDIR, "images");
			_instance.dataMap.put(_instance.CSSDIR, "css");
			_instance.dataMap.put(_instance.RESFILEPATH,
				"G:\\myOldwork\\WebControl\\defaultroot\\res");
			_instance.dataMap.put(_instance.RESREQUESTPATH, "res");
			_instance.dataMap.put(_instance.ROOTFILEPATH, "");
			_instance.dataMap.put(_instance.ROOTREQUESTPATH, "http://127.0.0.1:8083/res");
			_instance.dataMap.put(_instance.ROOTFILEPATH, "");
			_instance.dataMap.put(_instance.ROOTREQUESTPATH, "");
			_instance.dataMap.put(_instance.SERVEREVENTREQUEST, "component/control/GenericRequest");
			_instance.dataMap.put(_instance.SEARCHDATAREQUEST, "component/control/searchdatatest");
		}
		return _instance;
	}
	
	/**
	 * <P>
	 * ��ȡJavaScript��Դ�ļ�Ŀ¼
	 * </P>
	 */
	public String getJSFilePath() {
		return this.getFilePath(JSDIR);
	}
	
	/**
	 * <P>
	 * ��ȡJavaScript��Դ����Ŀ¼
	 * </P>
	 */
	public String getJSRequestPath() {
		return this.getRequestPath(JSDIR);
	}
	
	/**
	 * <P>
	 * ��ȡͼƬ��Դ����Ŀ¼
	 * </P>
	 */
	public String getImagesRequestPath() {
		return this.getRequestPath(IMAGESDIR);
	}
	
	/**
	 * <P>
	 * ��ȡ��ʽ��Դ����Ŀ¼
	 * </P>
	 */
	public String getCssRequestPath() {
		return this.getRequestPath(CSSDIR);
	}
	
	/**
	 * <P>
	 * »򈡷�¼�·¾¶
	 * </P>
	 * @return ·�¼�·¾¶
	 */
	public String getServerEventRequestPath() {
		return this.getRootRequestPath() + this.getSetting(SERVEREVENTREQUEST);
	}
	
	/**
	 * ��ȡ��������·��
	 * @return
	 */
	public String getSearchDataRequestPath() {
		return this.getRootRequestPath() + this.getSetting(SEARCHDATAREQUEST);
	}
	
	/**
	 * »򈢇숳»�¼
	 * @return ȫȳ»�\uFFFD
	 */
	public String getRootRequestPath() {
		return this.getSetting(this.ROOTREQUESTPATH);
	}
	
	/**
	 * ¸�ȳ¶Տ􊩖û�ȳĿ¼
	 * @param request ȫȳ
	 */
	public void setRootRequestPath(HttpServletRequest request) {
		//this.dataMap.put(ROOTREQUESTPATH,UtilHttp.getServerRootUrl(request));
	}
	
	/**
	 * getRequestPath(String key)
	 * <P>
	 * »򈢇숳·¾\uFFFD
	 * <P>
	 * ՚ѨҪµł·¾¶ǰ¼ԉβootrequestpath + "/" + resrequestpath + "/" +РӦkeyµäir
	 * ɧ:getPilePath(JSDIR); filepath= rootrequestpath + "/" + resrequestpath +
	 * "/" + jsDir
	 * @param key ¼ﾜuFFFD * @return РӦ¼�숳·¾¶
	 */
	private String getRequestPath(String key) {
		String result = "";
		String temp = "";
		temp = this.getSetting(RESREQUESTPATH);
		result = this.getSetting(this.ROOTREQUESTPATH);
		if (!temp.equals("")) {
			result += "/" + temp;
		}
		temp = this.getSetting(key);
		if (!temp.equals("")) {
			result += "/" + temp;
		}
		return result;
	}
	
	/**
	 * getFilePath(String key)
	 * <P>
	 * »򈢎ļ�P> ՚ѨҪµł·¾¶ǰ¼ԉβootfilepath + / + resfilepath + / +РӦkeyµäir
	 * ɧ:getPilePath(JSDIR); filepath= the filepath + "/" + resFilePath + "/" +
	 * jsDir
	 * @param key ¼ﾜuFFFD * @return РӦ¼�·¾\uFFFD
	 */
	private String getFilePath(String key) {
		String result = "";
		String temp = "";
		temp = this.getSetting(RESFILEPATH);
		result = this.getSetting(this.ROOTFILEPATH);
		if (!temp.equals("")) {
			result += "/" + temp;
		}
		temp = this.getSetting(key);
		if (!temp.equals("")) {
			result += "/" + temp;
		}
		return result;
	}
	
	/**
	 * <P>
	 * »򈠓ettingMapאРӦ¼ﾜuFFFD/P>
	 * @param key ¼ﾜuFFFD * @return РӦµŖµ
	 */
	public String getSetting(String key) {
		String result = "";
		try {
			result = this.dataMap.get(key).toString();
		} catch (Exception ex) {
			return "";
		}
		return result;
	}
	
	/**
	 * ´ҘMLא»򈢂·¾¶ʨ׃
	 * 
	 */
	private void getComponentSetting() {
		String temp = "";
		Document document = null;
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put(JSDIR, "js");
			dataMap.put(IMAGESDIR, "images");
			dataMap.put(CSSDIR, "css");
			dataMap.put(RESFILEPATH, "res/res");
			dataMap.put(RESREQUESTPATH, "res/res");
			dataMap.put(ROOTFILEPATH, "");
			dataMap.put(ROOTREQUESTPATH, "");
			dataMap.put(SERVEREVENTREQUEST, "hrm/control/GenericRequest");
			dataMap.put(SEARCHDATAREQUEST, "hrm/control/searchdatatest");
			try {
				File file = new File("build.xml");//֒µ½Ӧԃ¸ﾜuFFFD
				URL url = file.toURL();
				temp = file.getAbsolutePath();
				temp = StringUtils.replace(temp, "build.xml", ""); //temp.replaceFirst("build.xml","");
			} catch (IOException ioex) {
				ioex.printStackTrace();
				return;
			}
			this.dataMap.put(ROOTFILEPATH, temp);
			return;
		}
		NodeList nodes = document.getElementsByTagName("component-config");//document.getChildNodes();
		if (null == nodes.item(0)) {
			return;
		}
		nodes = nodes.item(0).getChildNodes();
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			temp = "";
			try {
				temp = ((Element) node).getAttribute("value");
			} catch (Exception ex) {
				temp = "";
			}
			if (!temp.equals("")) {
				
				dataMap.put(node.getNodeName(), temp);
			}
		}
		temp = this.getSetting(ROOTFILEPATH);
		if (temp.equals("")) {
			try {
				File file = new File("build.xml");//֒µ½Ӧԃ¸ﾜuFFFD
				URL url = file.toURL();
				temp = file.getAbsolutePath();
				temp = StringUtils.replace(temp, "build.xml", "");// temp.replaceFirst("build.xml","");
			} catch (IOException ioex) {
				return;
			}
			this.dataMap.put(ROOTFILEPATH, temp);
		}
	}
	
	public void test(Document document) {
		Element root = document.getDocumentElement();
		
		printChildrennodes(root);
	}
	
	public void printChildrennodes(Node node) {
		if (node.hasChildNodes()) {
			
			NodeList nodelist = node.getChildNodes();
			for (int i = 0; i < nodelist.getLength(); i++) {
				printChildrennodes(nodelist.item(i));
			}
		} else {
			
		}
		
	}
	
	/**
	 * �Ƿ�ÿ�θ��½ű�
	 * @return
	 */
	public boolean isUpdateJsEveryTime() {
		return this.getSetting(this.ISUPDATEJS).equals("true");
	}
	
	/**
	 * ����ÿ���Ƿ���½ű� true��ÿ�θ���;false��ֻ����һ��
	 * @param bool
	 */
	public void setUpdateJsEvetyTime(boolean bool) {
		this.dataMap.put(this.ISUPDATEJS, bool ? "true" : "false");
	}
	
	/**
	 * ��������� TypeĬ��ΪMSIE
	 * @param request
	 * @return true ΪIE����� falseΪ���������
	 */
	public static boolean isIEBrowser(HttpServletRequest request) {
		if (request == null)
			return true;
		Enumeration headerNames = request.getHeaderNames();
		String browserType = "";
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			if (headerName.toLowerCase().equals("user-agent")) {
				browserType = request.getHeader(headerName);
				break;
			}
		}
		if (browserType.indexOf("MSIE") <= 0) {
			return false;
		}
		return true;
	}
	
	public static String getComponentScript(AbstractComponent c) {
		boolean oldValue = c.isMakeScript;
		c.isMakeScript = true;
		String rel = c.getScriptHtml();
		c.isMakeScript = oldValue;
		return rel;
	}
}
