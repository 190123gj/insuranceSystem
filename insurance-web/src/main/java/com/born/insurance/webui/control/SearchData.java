/**
 * Created on 2003-10-22
 *
 */
package com.born.insurance.webui.control;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: SearchData component</p>
 * <p>Description: ��SearchText���ʹ�ã�������ʾ��ѯ����SearchText���ٻ���</p>
 * <p>Copyright: Copyright (c) 2003 11</p>
 * <p>Company: bornsoft</p>
 * @author He Kun
 * @since 1.0
 *
 */
public class SearchData extends ListGrid
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isViewTitle = true;
	public Button okButton;
	public Button cancelButton;
	public String listTitle = "";
	public String searchEntity = "";
	public String searchTextId = "";
	public String actionType = "iframe"; //������֡�д�SearchData(default)
	public String keyField = "";
	public String codeField = "";
	public String nameField = "";
	public List dataFields;
	public Map captions;
	public int windowHeight = 400;
	public int windowWidth = 290;
	public String queryString = "";
	public String queryValue = "";
	public String textFormula = "";
	private HttpServletRequest request;
	boolean debug = false;
	/**
	 * ���캯���һ��SearchData
	 * @param id
	 */
	public SearchData(String id)
	{
		super(id, null, 2); //����
		initComposite();
	}
	/**
	 * ���캯���һ��SearchData
	 * @param id SearchData���id
	 * @param request ��SearchText������http����
	 */
	public SearchData(String id, HttpServletRequest request)
	{
		super(id, request, 2); //����		
		this.request = request;
		String searchTextID = request.getParameter("searchTextID");
		String sWidth = "";
		String sHeight = "";
		this.queryString = request.getParameter("text");
		this.queryValue = request.getParameter("value");
		this.actionType = request.getParameter("actionType");
		this.searchEntity = request.getParameter("searchEntity");
		this.listTitle = request.getParameter("listTitle");
		this.keyField = SearchText.getKeyField(request);
		this.codeField = SearchText.getCodeField(request);
		this.nameField = SearchText.getNameField(request);
		this.dataFields = SearchText.getDataFields(request);
		this.captions = SearchText.getCaptions(request);
		this.textFormula = SearchText.getTextFormula(request);
		if (dataFields == null || dataFields.isEmpty())
		{
			dataFields = new ArrayList();
			dataFields.add(this.codeField);
			dataFields.add(this.nameField);
		}
		else if (this.codeField == null || this.nameField == null)
		{
			this.codeField = (String) dataFields.get(0);
			if (dataFields.size() > 1)
				this.nameField = (String) dataFields.get(1);
		}
		this.setDataField(keyField, dataFields);
		this.setCaption(captions);
		sWidth = request.getParameter("windowWidth");
		sHeight = request.getParameter("windowHeight");
		Map userData = ServerEvent.getServerEventUserData(request);
		if (userData != null)
		{
			searchTextID = (String) userData.get("searchTextID");
			this.actionType = (String) userData.get("actionType");
			this.searchEntity = (String) userData.get("searchEntity");
			this.listTitle = (String) userData.get("listTitle");
			sWidth = (String) userData.get("windowWidth");
			sHeight = (String) userData.get("windowHeight");
			this.queryString = (String) userData.get("text");
			this.queryValue = (String) userData.get("value");
		}
		try
		{
			this.windowWidth = Integer.parseInt(sWidth);
			this.windowHeight = Integer.parseInt(sHeight);
		}
		catch (Exception ex)
		{
			this.windowWidth = 290;
			this.windowHeight = 400;
		}
		this.setWidth(String.valueOf(this.windowWidth));
		this.setHeight(String.valueOf(this.windowHeight - 100));
		this.setSearchTextID(searchTextID);
		if (listTitle != null)
			this.setListTitle(listTitle);
		initComposite();
		this.setSelectedItemCssClass("listtrmouseover");
		this.setBorder(0);
		this.getGridHead().setAttribute("bordercolorlight", "#9EB5E7");
		this.setCssClass("listheadtable");
		this.getGridHead().setBorder(1);
	}
	/**
	 * (�ڲ�����)��ʼ��SearchData����Ĺ���
	 */
	private void initComposite()
	{
		okButton = new Button(this.getComponentId() + "_okButton");
		okButton.setText("ȷ��");
		cancelButton = new Button(this.getComponentId() + "_cancelButton");
		cancelButton.setText("ȡ��");
		cancelButton.setCssClass("bgbutton");
	}
	/**
	 * (�ڲ�����)�����
	 *
	 */
	private void adjustLayout()
	{
		//�б���
		TableRow row = this.getGridHead().getRow(0);
		for (int i = 0; i < this.columnSize(); i++)
		{
			row.getTableCell(i).setStyle("text-align", "center");
		}
		//���headDataSource���ñ���
		Map headDataSource = (Map) this.getHeadDataSource();
		if (headDataSource != null)
			for (int i = 0; i < dataFields.size(); i++)
			{
				String title = (String) headDataSource.get(dataFields.get(i));
				this.getGridHead().getRow(0).getTableCell(i).setText(title);
			}
		else //headDataSource==null
			{
			if (this.columnSize() == 2 && "CN".equals(System.getProperty("user.country")))
			{
				this.getGridHead().getRow(0).getTableCell(0).setText("���");
				this.getGridHead().getRow(0).getTableCell(1).setText("���");
			}
			else
				for (int i = 0; i < this.dataFields.size(); i++)
				{
					this.getGridHead().getRow(0).getTableCell(i).setText((String) dataFields.get(i));
				}
		}
		this.getGridHead().setCssClass("listGridHeadRow");
		for (int i = 0; i < this.columnSize(); i++)
		{
			this.getGridHead().getRow(0).getTableCell(i).setCssClass("listhead");
		}
		//��С����
		//		int width=this.windowWidth;
		//		int height=this.windowHeight-100;			
		//		this.setHeight(String.valueOf(height));	
		//		this.setWidth(String.valueOf(width));
		//		this.getBodyDiv().setStyle("width","");
		//		int w=this.windowWidth-16;
		//		int colWidth=w/this.columnSize();				
		//		if(this.columnSize()==2)	//Ĭ�Ϸ���������
		//		{
		//			String firstColWidth=String.valueOf(100);
		//			String secondColWidth=String.valueOf(width-100-16);		
		//			this.getGridHead().getRow(0).getTableCell(0).setWidth(firstColWidth);
		//			this.getGridHead().getRow(0).getTableCell(1).setWidth(secondColWidth);
		//			for(int i=0;i<this.getDataRowCount();i++)
		//			{
		//				this.getRow(i).getTableCell(0).setWidth(firstColWidth);	
		//				this.getRow(i).getTableCell(1).setWidth(secondColWidth);				 
		//			}
		//		}				
		//		else  if(((List)getDataSource()).size()>0)//������1�л���У��Զ������п�
		//		{		
		//			for(int i=0;i<this.columnSize();i++)
		//				this.getGridHead().getRow(0).getTableCell(i).setWidth(String.valueOf(colWidth));
		//			for(int j=0;j<this.rowSize();j++)
		//			{					
		//				for(int i=0;i<this.columnSize();i++)
		//					this.getRow(j).getTableCell(i).setWidth(String.valueOf(colWidth));
		//			}		
		//		}		
		Table footerLayoutTable = this.getGridFooter();
		footerLayoutTable.setVisible(true);
		footerLayoutTable.setBorder(0);
		//layout for footer calcel button
		int width2 = this.windowWidth - 16;
		footerLayoutTable.getRow(0).setHeight("30");
		footerLayoutTable.getRow(0).setAttribute("valign", "bottom");
		footerLayoutTable.getRow(0).getTableCell(0).setWidth(String.valueOf(width2 - 100));
		footerLayoutTable.getRow(0).getTableCell(1).setWidth(String.valueOf(100));
		footerLayoutTable.getRow(0).getTableCell(1).setStyle("text-align", "center");
		footerLayoutTable.getRow(0).getTableCell(1).addComponent(cancelButton);
		//		this.getGridColumn(0).setDataField(codeField);
		//		this.getGridColumn(1).setDataField(nameField);		
	}
	/**
	 *����SearchData��javascript�ļ��������ؽű��ļ���html����
	 * @return ���ؽű��ļ���html����
	 */
	protected String getScriptHtml()
	{
		String scriptReference = super.getScriptHtml() + "\r\n" + SearchDataJS.getSearchDataJS();
		//		ODO : �ύʱɾ������Ĵ���
		//		scriptReference="<script src='c:/myweb/HashMapJS.js'></script>"
		//						+"<script src='c:/myweb/ListGrid.js'></script>"
		//						+"<script src='c:/myweb/SearchDataJS.js'></script>";
		return scriptReference;
	}
	/**
	 *�������SearchData�ͻ��˿ؼ���html����
	 * @return SearchData�ͻ��˿ؼ���html����
	 */
	protected String getElementHtml()
	{
		adjustLayout();
		this.setAttribute("actionType", actionType);
		cancelButton.addEvent("onClick", "SearchData_cancel('" + this.getComponentId() + "');   window.close();	");
		String html = "";
		html += "<script language='javascript'> document.title='" + this.listTitle + "';   </script>    \r\n";
		html += "<body onUnLoad=\"SearchData_cancel('" + this.componentID + "');\">";
		if (isViewTitle)
		{
			if (this.listTitle != null && !("".equals(this.listTitle)))
				html += "<br><div align=center style='font:12pt bold;width:" + this.windowWidth + "'>" + this.listTitle + "</div>";
			else if (this.searchEntity != null && !("".equals(this.searchEntity)))
				html += "<br><div align=center style='font:12pt bold;width:" + this.windowWidth + "'>" + this.searchEntity + "</div>";
		}
		html += getAdditionHtml() + super.getElementHtml();
		if (getDataRowCount() == 1 && actionType.equals("iframe")) //������֡�д򿪣�����ֻ��һ��ʱ���Զ�����
		{
			HyperLink link = (HyperLink) this.getRow(0).getTableCell(0).getComponent(0);
			String script = link.getHref() + ";";
			html += "<script language='JavaScript'> \r\n" + script + "\r\n</script>";
		}
		else if (actionType.equals("iframe") && getDataRowCount() != 1)
		{
			// ������֡�е���SEarchData
			html += "<script language='JavaScript'> SearchData_popupMeFromIframe('" + this.componentID + "');\r\n</script>";
		}
		else if (actionType.equals("popup")) //�ж���ʱ���򿪵������ڣ���һ���ý���
		{
			if (getDataRowCount() == 0)
				return html;
			List components = this.getRow(0).getTableCell(0).components;
			HyperLink hyperLink = (HyperLink) components.get(0);
			String firstRowLinkId = hyperLink.getComponentId();
			html += "<script language='JavaScript'>\r\n	document.getElementById('" + firstRowLinkId + "').focus();\r\n</script>";
		}
		html += "</body>";
		return html;
	}
	private String getAdditionHtml()
	{
		if (ComponentUtil.isIEBrowser(request))
			return "";
		else //Mozilla requires these codes to use server event 
			{
			String additionHtml = "";
			String iformName = request.getParameter("iformName");
			additionHtml += "<form method='post' action='' name='" + iformName + "' id='" + iformName + "'>";
			additionHtml += "<input type='hidden' id='COMPONENT_DATA_SYSTEM' name='COMPONENT_DATA_SYSTEM'>";
			additionHtml += "<input type='hidden' id='COMPONENT_DATA_USER' name='COMPONENT_DATA_USER'>";
			return additionHtml;
		}
	}
	/**
	 *(�ڲ�����)�������Դ�еļ�¼��
	 * @return
	 */
	private int getDataRowCount()
	{
		List list = (List) super.getDataSource();
		return list.size();
	}
	/**
	 * ��ȡSearchData��"ȡ��"��ť������
	 * @return ��ȡSearchData��"ȡ��"��ť������
	 */
	public Button getCancelButton()
	{
		return cancelButton;
	}
	/**
	 * ��ȡSearchData�ı���
	 * @return SearchData�ı���
	 */
	public String getListTitle()
	{
		return listTitle;
	}
	/**
	 * ��ȡ���������SearchText�ؼ���ID
	 * @return  ���������SearchText�ؼ���ID
	 */
	public String getSearchTextID()
	{
		return searchTextId;
	}
	/**
	 * ����SearchData ����к�����а��ֶ�
	 * @param codeDataField ����а󶨵����Դ�ֶ�
	 * @param nameDataField ����а󶨵����Դ�ֶ�
	 * @deprecated ʹ�� setDataField(String keyField,List dataFields)
	 */
	public void setDataField(String codeDataField, String nameDataField)
	{
		setDataField(codeDataField, codeDataField, nameDataField);
	}
	/**
	 * ����SearchData �����У�����к�����а��ֶ�
	 * @param keyField  �����а󶨵����Դ�ֶ�
	 * @param codeDataField  ����а󶨵����Դ�ֶ�
	 * @param nameDataField ����а󶨵����Դ�ֶ�
	 * @deprecated ʹ�� setDataField(String keyField,List dataFields)
	 */
	public void setDataField(String keyField, String codeDataField, String nameDataField)
	{
		this.codeField = codeDataField;
		this.nameField = nameDataField;
		String[] fields = new String[2];
		fields[0] = codeDataField;
		fields[1] = nameDataField;
		setDataField(keyField, fields);
	}
	/**
	 * /**
	 * ����SearchData���Դ�İ��ֶ�
	 * <br>�����Ҫ��ʾ����ʱʹ��	
	 * @param keyField �����У������в���ʾ������
	 * @param dataFields Ҫ��ʾ������List(��Ҫ��ʾ�����У����List�б���������е�����)
	 */
	public void setDataField(String keyField, List dataFields)
	{
		this.keyField = keyField;
		this.dataFields = dataFields;
		int colSize = this.columnSize();
		if (dataFields.size() > colSize) //�����
		{
			this.codeField = (String) dataFields.get(0);
			this.nameField = (String) dataFields.get(1);
			int t = 0;
			for (int i = 0; i < (dataFields.size() - colSize); i++)
				this.addTableColumn();
		}
		for (int i = 0; i < dataFields.size(); i++)
			this.getGridColumn(i).setDataField((String) dataFields.get(i));
	}
	/**
	 * /**
	 * ����SearchData���Դ�İ��ֶ�
	 * <br>�����Ҫ��ʾ����ʱʹ��	
	 * @param keyField �����У������в���ʾ������
	 * @param dataFields Ҫ��ʾ����������(��Ҫ��ʾ�����У���������б���������е�����)
	 * @deprecated �Ƽ�ʹ��setDataField(String keyField,List dataFields)
	 */
	public void setDataField(String keyField, String[] dataFields)
	{
		List ds = new ArrayList();
		for (int i = 0; i < dataFields.length; i++)
		{
			ds.add(dataFields[i]);
		}
		setDataField(keyField, ds);
	}
	/**
	 * �����б���б���.
	 * @param caption
	 * @see  com.bornsoft.core.gaui.component.ListGrid#setHeadDataSource
	 */
	public void setCaption(Map headDataSource)
	{
		if (headDataSource != null)
			setHeadDataSource(headDataSource);
	}
	/**
	 * �����б���б���,<br>
	 * ע�⣺�÷��������� setDataField()֮�����
	 * @param codeCaption ���������һ�б��⣬Ĭ��Ϊ"���"
	 * @param nameCaption	��Ʊ����ڶ��б��⣬Ĭ��Ϊ"���"
	 */
	public void setCaption(String codeCaption, String nameCaption)
	{
		Map headMap = new HashMap();
		String codeField = this.getGridColumn(0).getDataField();
		String nameField = this.getGridColumn(1).getDataField();
		if (codeField == null || nameField == null)
			return;
		headMap.put(codeField, codeCaption);
		headMap.put(nameField, nameCaption);
		setCaption(headMap);
	}
	/**
	*�б�ĵ�Ԫ������¼����ڴ�Ϊ��ǰ��Ԫ������ض�����ɿؼ�
	*/
	protected void TableCellCreateEvent(TableCell cell, int row)
	{
		//����ID�����ӵ�����	  
		if (cell.cellIndex == this.columnSize() - 1) //�����һ��ʱ����
		{
			TableRow curRow = this.getRow(row);
			TableCell idCell = curRow.getTableCell(0);
			List list = (List) this.dataSource;
			String value = "";
			if (this.keyField != null && !("").equals(this.keyField))
			{
				Map rowMap = (Map) list.get(row);
				value = (String) rowMap.get(this.keyField);
			}
			else
			{
				value = idCell.text;
			}
			String fbText = "";
			fbText = this.getFillBackText(row);
			this.getRow(row).addEvent("onDblClick", "SearchData_fillBack('" + this.componentID + "','" + value + "','" + fbText + "');");
			this.getRow(row).setAttribute("pkValue", value);
			HyperLink link = new HyperLink(idCell.componentID + "A");
			link.setHref("javascript:SearchData_fillBack('" + this.componentID + "','" + value + "','" + fbText + "');");
			link.setText(idCell.text);
			idCell.text = ""; //ȡ��ԭ��������
			idCell.components.add(link);
		}
	}
	/**
	*����SearchData���б���⣬ͨ����Ҫ��ѯʵ��(searchEntity)��������
	 * @param string
	 */
	public void setListTitle(String string)
	{
		//ʵ����������ͷ
		listTitle = string;
		this.setAttribute("listTitle", string);
	}
	/**
	* ����Ҫ��ѯ��ʵ�����
	   * @return  Ҫ��ѯ��ʵ�����
	   */
	public String getSearchEntity()
	{
		return searchEntity;
	}
	/**
	 * ���ð󶨵�SearchText�ؼ�
	 * @param string
	 */
	public void setSearchTextID(String string)
	{
		//���ÿͻ��˿��õ�����ֵ
		this.setAttribute("sourceControlID", string);
		searchTextId = string;
	}
	/**
	 * ����SearchData�򿪵ķ�ʽ��iframe �� popup
	 * @return SearchData�򿪵ķ�ʽ
	 */
	private String getActionType()
	{
		return actionType;
	}
	/**
	 * ���ط��������SearchText����ʾֵ������Ϊ��ѯ���ַ�
	 * @return ��ѯ���ַ�
	 */
	public String getQueryString()
	{
		return queryString;
	}
	/**
	 * ���ط��������SearchText����ʵֵ
	 * @return ��ʵֵ
	 */
	public String getQueryValue()
	{
		return queryValue;
	}
	/**
	 * ��ȡSearchData�����Դ�󶨵Ĵ����ֶ�
	 * @return
	 * @deprecated
	 */
	public String getCodeField()
	{
		return codeField;
	}
	/**
	 * ��ȡSearchData�����Դ�󶨵�key�ֶ�
	 * @return
	 * @deprecated
	 */
	public String getKeyField()
	{
		return keyField;
	}
	/**
	 * ��ȡSearchData�����Դ�󶨵�����ֶ�
	 * @return
	 * @deprecated
	 */
	public String getNameField()
	{
		return nameField;
	}
	/**
	 * ����SearchData�����Դ�󶨵Ĵ����ֶ�
	 * @param string
	 * @deprecated �Ƽ�ʹ��setDataField(String keyField,List dataFields)
	 */
	public void setCodeField(String string)
	{
		codeField = string;
	}
	/**
	 * ����SearchData�����Դ�󶨵�key�ֶ�
	 * @param string
	 * @deprecated �Ƽ�ʹ��setDataField(String keyField,List dataFields)
	 */
	public void setKeyField(String string)
	{
		keyField = string;
	}
	/**
	 *  ����SearchData�����Դ�󶨵�����ֶ�
	 * @param string
	 * @deprecated �Ƽ�ʹ��setDataField(String keyField,List dataFields)
	 */
	public void setNameField(String string)
	{
		nameField = string;
	}
	/**
	 * @return
	 */
	public Map getCaption()
	{
		return captions;
	}
	/**
	 * @return
	 */
	public List getDataFields()
	{
		return dataFields;
	}
	/**
	 * ��ȡ���SearchText���ı��Ĺ�ʽ
	 * @return
	 */
	public String getTextFormula()
	{
		return textFormula;
	}
	/**
	 * ���û��SearchText���ı��Ĺ�ʽ
	 * ��ʽΪ��[�ֶ���1][�ֶ���2]
	 * ��    [index1][index2]
	 * @param string
	 */
	public void setTextFormula(String string)
	{
		textFormula = string;
	}
	/**
	 * ������õĹ�ʽ��ȡ���SearchText���ı�
	 * @return
	 */
	public String getFillBackText(int row)
	{
		if (this.textFormula == null)
			textFormula = "[0] [1]";
		String fbText ="";
		if(textFormula.indexOf("[")==-1)
		{
			fbText=textFormula;
		}				
		int index = -1;
		int currentIndex = 0;
		String relaceString=textFormula;
		while ((index = relaceString.indexOf("[")) >= 0)
		{			
			currentIndex=relaceString.indexOf("]");
			String temp = relaceString.substring(index + 1, currentIndex);			
			String value = this.getRow(row).getTableCell(Integer.parseInt(temp)).getText();
						
			fbText+=relaceString.substring(0,index);
			fbText+=value;		
			if(currentIndex<relaceString.length())
			{
				relaceString=relaceString.substring(currentIndex+1);
			}
			else
			{
				break;
			}		
						
		}		
		return fbText;
	}
	/**
	 * @return
	 */
	public boolean isViewTitle()
	{
		return isViewTitle;
	}
	/**
	 * @param b
	 */
	public void setViewTitle(boolean b)
	{
		isViewTitle = b;
	}
}
