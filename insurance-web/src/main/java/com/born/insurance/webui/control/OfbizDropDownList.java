package com.born.insurance.webui.control;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Title: the dropdownlist control.
 * </p>
 * <p>
 * Description: �����б��ؼ�
 * </p>
 * <p>
 * sample:OfbizDropDownList d5=new OfbizDropDownList("d5"); d5.setDropDownType(DropDownType.ONLYDROPDOWN); List list=new ArrayList(); Map map; int n=6; //���� for(int i=0;i<n;i++) { map=new HashMap();
 * map.put("Depart", "Depart"+i); map.put("����","����"+i); list.add(map); } d5.setTextField("����"); d5.setValueField("Depart"); d5.setDataSource(list); d5.dataBind(); String
 * dropDownListHtml=d5.getInnerHtml()
 * <p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * 
 * @author: �ᴺ��
 * @version 1.0
 */
public class OfbizDropDownList extends AbstractComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ListItemCollection items = new ListItemCollection();

	private ArrayList curItems = new ArrayList();

	private List signArray = new ArrayList();

	private List fieldArray = new ArrayList();

	private int selectedIndex = -1;

	private ListItem selectedItem = null;

	private String filter = null;

	private TextBox dropDownText = null;

	private Image dropDownBtn = null;

	private HashMapManager containerHidden = null;

	private ArrayListManager relatingHidden = null;

	private ArrayListManager filterList = null;

	private ArrayList filterCollection = null;

	private int dropDownType = 2;

	private Map dataMap = null;

	private List dataList = null;

	private Hidden textHidden = null;

	private Hidden valueHidden = null;

	private HtmlDiv popDiv = null;

	private String textId = null;

	private int buttonWidth = 17;

	private int buttonHeight = 17;

	private String btnId = null;

	private String filterListId = null;

	private String popDivId = null;

	private String textHiddenId = null;

	private String valueHiddenId = null;

	private String containerId = null;

	private String relatingId = null;

	private String filterField = null;

	private String textBoxClass = "inputText";

	private String buttonClass = "inputText";

	private boolean isIEBrowser = true;

	private String textField = null;

	private String valueField = null;

	private ArrayList relations = new ArrayList();

	private String onselectedChange = null;

	private String expressionStr = null;

	private String imageSrc = ComponentUtil.getInstance().getImagesRequestPath() + "/downenabled.gif";

	private String disImageSrc = ComponentUtil.getInstance().getImagesRequestPath() + "/downdisabled.gif";

	private boolean necessary = false;

	private boolean readonly = false;

	/**
	 * ���캯��
	 * 
	 * @param id
	 *            String
	 * @deprecated ����ʹ��OfbizDropDownList(id,request)����,�����������Ϊֻ��IE��������
	 */
	public OfbizDropDownList(String id) {
		super(id);
		this.OnChangeEventName = "onselectedchange";
		this.style.setStyle("width", "150px");
		this.name = id;
		this.setContolId(id);
		this.addControl();
		this.setAttribute("popDivWidth", "17");
		this.setAttribute("popDivHeight", "6");
	}

	/**
	 * ���캯��
	 * 
	 * @param id
	 *            String
	 * @param request
	 *            HttpServletRequest
	 */
	public OfbizDropDownList(String id, HttpServletRequest request) {
		super(id);
		if (ComponentUtil.isIEBrowser(request))
		{
			this.isIEBrowser = true;
		} else
		{
			this.isIEBrowser = false;
		}
		this.style.setStyle("width", "150px");
		this.OnChangeEventName = "onselectedchange";
		this.name = id;
		this.setContolId(id);
		this.addControl();
		this.setAttribute("popDivWidth", "17");
		this.setAttribute("popDivHeight", "6");

	}

	/**
	 * <p>
	 * Description: set unique id of sub control
	 * </p>
	 * 
	 * @param id:
	 *            unique sign of the control
	 */
	private void setContolId(String id) {
		this.textId = id + "_textBox";
		this.popDivId = id + "_popDiv";
		this.btnId = id + "_divButton";
		this.valueHiddenId = id + "_selectedValue";
		this.textHiddenId = id + "_selectedText";
		this.containerId = id + "_containerValueId";
		this.relatingId = id + "_Filter";
		this.filterListId = id + "_filters";
	}

	/**
	 * <p>
	 * Description: add child controls to components
	 * </p>
	 */
	private void addControl() {
		this.dropDownText = new TextBox(this.textId);
		dropDownText.setCssClass(this.textBoxClass);
		this.dropDownBtn = new Image(this.btnId);
		this.popDiv = new HtmlDiv(this.popDivId);
		this.textHidden = new Hidden(this.textHiddenId);
		this.valueHidden = new Hidden(this.valueHiddenId);
		this.containerHidden = new HashMapManager(this.containerId);
		this.filterList = new ArrayListManager(this.filterListId);
		this.components.add(this.dropDownText);
		this.components.add(this.dropDownBtn);
		this.components.add(this.popDiv);
		this.components.add(this.textHidden);
		this.components.add(this.valueHidden);
		this.components.add(this.containerHidden);
		this.components.add(this.filterList);
	}

	/**
	 * <p>
	 * Description: set all child control attribute
	 * </p>
	 */
	private void setControlAttribute() {
		this.setCurrentItems();
		this.setButtonAttribute();
		this.setTextBoxAttribute();
		this.setPopDivAttribute();
		this.addFilter();
		this.addContainer();
		this.setRelatingHidden();
		this.setGolbeAttribute();
	}

	/**
	 * <p>
	 * Description: set attribute of dropdownlist control
	 * </p>
	 */
	private void setGolbeAttribute() {
		if (this.necessary)
		{
			this.dropDownText.style.setStyle("background-Color", "yellow");
			this.setAttribute("necessary", "true");
		} else
		{
			this.setAttribute("necessary", "false");
		}
		if (this.readonly || !this.enabled)
		{
			this.dropDownText.setAttribute("readonly", "true");
			this.dropDownBtn.setAttribute("disabled", "true");
			this.setAttribute("readonly", "true");
		} else
		{
			this.setAttribute("readonly", "false");
		}
	}

	/**
	 * ���ù����ֶ�
	 * 
	 * @param filterId
	 *            String
	 */
	public void setFilterField(String filterId) {
		this.filterField = filterId;
	}

	/**
	 * ��ù����ֶ�
	 * 
	 * @return String
	 */
	public String getFilterField() {
		return this.filterField;
	}

	/**
	 * �����������
	 * 
	 * @param width
	 * @param height
	 */
	public void setPopDivSize(String width, String height) {
		this.setAttribute("popDivWidth", width);
		this.setAttribute("popDivHeight", height);
	}

	/**
	 * ����������Ҫ�ﶨ�ĵ��ı�
	 * 
	 * @param textId
	 *            String
	 */
	public void setTextField(String textId) {
		this.textField = textId;
	}

	/**
	 * ��ÿؼ��󶨵��ı�
	 * 
	 * @return String
	 */
	public String getTextField() {
		return this.textField;
	}

	/**
	 * ����Ҫ�ﶨ��ֵ
	 * 
	 * @param valueId
	 *            String
	 */
	public void setValueField(String valueId) {
		this.valueField = valueId;
	}

	public int getListItemSize() {
		return this.items.getAllItems().size();
	}

	/**
	 * ��ÿؼ��󶨵�ֵ
	 * 
	 * @return String
	 */
	public String getValueField() {
		return this.valueField;
	}

	/*
	 * <p>Description:set a expression, the component will be merger field according to the expression. the expression format is "[fieldName]" + all letter or number + "[fieldName]", for example:
	 * "[����]--[dpartId]",or "[����]**[dpartId]" <p>�÷����������ñ��ʽ�����ʽ�ĸ�ʽΪ[fieldName]���Ϸָ���ţ��ָ���ſ����ǳ�"[","~","`"��������[fieldName] @param expStr: the expression.
	 */
	public void setExpression(String expStr) {
		this.expressionStr = expStr;
		parseStrToArrays();
	}

	private void parseStrToArrays() {
		if (this.expressionStr.charAt(0) != '[')
		{
			return;
		}
		boolean isField = false;
		boolean isSplitSign = false;

		int startIndex = 0;
		int nIndex = 0;
		int mIndex = 0;
		for (int index = 0; index < expressionStr.length(); index++)
		{
			if (expressionStr.charAt(index) == '[')
			{
				if (index > startIndex)
				{
					String splitSign = expressionStr.substring(startIndex + 1, index);
					if (splitSign != null && splitSign.length() > 0 && index > 0)
					{
						signArray.add(nIndex, splitSign);
						nIndex++;
					} else
					{
						signArray.add(nIndex, "");
						nIndex++;
					}
				}
				isField = true;
				isSplitSign = false;
				startIndex = index;
			} else if (expressionStr.charAt(index) == ']')
			{
				if (index > startIndex)
				{
					String fieldName = expressionStr.substring(startIndex + 1, index);
					if (fieldName != null && fieldName.length() > 0 && index > 0)
					{
						fieldArray.add(mIndex, fieldName);
						mIndex++;
					}
				}
				isField = false;
				isSplitSign = true;
				startIndex = index;
			}
		}
	}

	/**
	 * �����Դ���ؼ�
	 */
	public void dataBind() {
		if (this.getDataSource() instanceof Map)
		{
			this.dataMap = (Map) this.getDataSource();
			this.setMapData(dataMap);
		} else if (this.getDataSource() instanceof List)
		{
			this.dataList = (List) this.getDataSource();
			this.setListData(dataList);
		}
	}

	/**
	 * add list item according as map data ,when data source is a instance of map.
	 * 
	 * @param ds:
	 *            data source.
	 */
	private void setMapData(Map ds) {
		for (Iterator i = ds.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry e = (Map.Entry) i.next();
			if (!("").equals(e.getValue()))
			{
				ListItem li = new ListItem((String) e.getValue(), (String) e.getKey());
				this.addItem(li);
			}
		}
	}

	/**
	 * add list item according as list data, when data source is a instance of list.
	 * 
	 * @param ds:
	 *            data source.
	 */
	private void setListData(List ds) {
		try
		{
			for (int index = 0; index < ds.size(); index++)
			{
				Map itemGv = (Map) ds.get(index);
				String itemText = "";
				String itemValue = "";
				if (this.fieldArray != null && this.fieldArray.size() > 0)
				{
					for (int nIndex = 0; nIndex < this.fieldArray.size(); nIndex++)
					{
						String fieldName = this.fieldArray.get(nIndex).toString();
						if (itemGv.get(fieldName) != null)
						{
							itemText += itemGv.get(fieldName).toString();
						} else
						{
							itemText += "";
						}
						if (this.signArray.size() > nIndex && this.signArray.get(nIndex) != null && !this.signArray.get(nIndex).equals(""))
						{
							itemText += this.signArray.get(nIndex).toString();
						}
					}
				} else
				{
					if (itemGv.get(textField) != null)
					{
						itemText = itemGv.get(this.textField).toString();
					} else
					{
						itemText = "";
					}
				}
				if (itemGv.get(this.valueField) != null)
				{
					itemValue = itemGv.get(this.valueField).toString();
				}
				if (this.filterField != null && !this.filterField.equals(""))
				{
					if (itemGv.get(this.filterField) == null)
					{

						break;
					}
					ListItem li = new ListItem(itemGv.get(this.filterField).toString(), itemText, itemValue);
					this.addItem(li);
				} else
				{
					ListItem li = new ListItem(itemText, itemValue);
					this.addItem(li);
				}
			}
		} catch (Exception e)
		{
			System.out.print("DATA BIND ERROR:" + e.getMessage() + "\r\n");
		}
	}

	/**
	 * <p>
	 * Description: get html code of the dropdownlist control.
	 * </p>
	 * 
	 * @return html code of the dropdownlist control.
	 */
	protected String getElementHtml() {
		this.setControlAttribute();
		StringBuffer innerHtml = new StringBuffer();
		if (this.isIEBrowser)
		{
			innerHtml.append("<label ");
		} else
		{
			innerHtml.append("<div ");
		}
		innerHtml.append(super.getElementHtml());
		innerHtml.append("><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" ");
		innerHtml.append(" style=\"");
		innerHtml.append("WIDTH: 100%; \">");
		innerHtml.append("<tr><td>");
		innerHtml.append(this.dropDownText.getElementHtml());
		innerHtml.append("</td><td style=\"WIDTH: 18px\">");
		innerHtml.append(this.dropDownBtn.getElementHtml());
		innerHtml.append("</td></tr></table>");
		innerHtml.append(this.containerHidden.getElementHtml());
		innerHtml.append(this.filterList.getElementHtml());
		innerHtml.append(this.textHidden.getElementHtml());
		innerHtml.append(this.valueHidden.getElementHtml());
		if (this.relatingHidden != null)
		{
			innerHtml.append(this.relatingHidden.getElementHtml());
		}
		if (this.filterCollection != null)
		{
			for (int index = 0; index < this.filterCollection.size(); index++)
			{
				HashMapManager filterHash = (HashMapManager) this.filterCollection.get(index);
				innerHtml.append(filterHash.getElementHtml());
			}
		}
		if (this.isIEBrowser)
		{
			innerHtml.append("</label>");
		} else
		{
			innerHtml.append("</div>");
		}
		innerHtml.append(this.popDiv.getElementHtml());
		setRelationFilter();
		return innerHtml.toString();
	}

	protected String getScriptHtml() {
		if (!this.isMakeScript)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(super.getScriptHtml());
		if (this.isIEBrowser)
		{
			sb.append(DropDownListJs.getDropDownJS());
		} else
		{
			sb.append(DropDownJsForMozila.getDropDownJS());
		}
		sb.append(HashMapJS.getHashMapJS());
		sb.append(ArrayListJS.getArrayListJS());
		return sb.toString();
	}

	/**
	 * <p>
	 * Description: set control attribute of text box
	 * </p>
	 */
	private void setTextBoxAttribute() {
		this.dropDownText.setAttribute("selectedIndex", String.valueOf(selectedIndex));
		this.dropDownText.setAttribute("onblur", "DropDown_changeValueByTextBox('" + this.componentID + "',true)");
		if (!this.isIEBrowser)
		{
			this.dropDownText.setAttribute("onclick", "DropDown_hideDivPop('" + this.componentID + "')");
		}

		this.setAttribute("setValue", "DropDown_setValue");
		this.setAttribute("setText", "DropDown_setText");
		this.setAttribute("setFocus", "DropDown_setFocus");
		this.setAttribute("setSelectedIndex", "DropDown_setDropDownIndex");
		this.setAttribute("getSelectedIndex", "DropDown_getSelectedIndex");
		this.setAttribute("setDataSource", "DropDown_setDataSource");
		this.setAttribute("getValue", "DropDown_getValue");
		this.setAttribute("src", this.imageSrc);
		this.setAttribute("dissrc", this.disImageSrc);
		this.setAttribute("getText", "DropDown_getText");
		this.setAttribute("setProperty", "DropDown_setProperty");
		this.dropDownText.setAttribute("onkeydown", "DropDown_TextBoxKeyDown('" + this.componentID + "')");
		this.dropDownText.setAttribute("style", "WIDTH: 100%;  PADDING-TOP: 0px");
		this.dropDownText.setAttribute("DropDownType", String.valueOf(this.dropDownType));
		if (this.dropDownType == 1)
		{
			this.dropDownText.setAttribute("readOnly", "true");
		}
		if (this.filter != null)
		{
			this.dropDownText.setAttribute("filterId", this.filter);
		}
		if (this.onselectedChange != null)
		{
			this.dropDownText.setAttribute("onselectedChange", this.onselectedChange);
		}		
		if (this.selectedItem != null || selectedIndex != -1)
		{		
			this.dropDownText.setValue(this.selectedItem.getText());
			this.textHidden.setAttribute("value", this.selectedItem.getText());
			this.valueHidden.setAttribute("value", this.selectedItem.getValue());
		} else
		{
			this.dropDownText.setValue("");
			this.textHidden.setAttribute("value", "");
			this.valueHidden.setAttribute("value", "");
		}
	}

	/**
	 * <p>
	 * Description: set attribute of button control
	 * </p>
	 */
	private void setButtonAttribute() {
		if (this.imageSrc != null)
		{
			this.dropDownBtn.setAttribute("src", this.imageSrc);
		}
		StringBuffer style = new StringBuffer();
		style.append("BORDER-RIGHT: #999999 0px solid;");
		style.append("BORDER-TOP: #999999 0px solid;");
		style.append("BORDER-LEFT: #999999 0px solid;");
		style.append("BORDER-BOTTOM: #999999 0px solid;");
		style.append("width: " + String.valueOf(this.buttonWidth) + ";");
		style.append("height:  " + String.valueOf(this.buttonHeight) + ";");
		style.append("CURSOR:hand;");
		this.dropDownBtn.setAttribute("style", style.toString());
		this.dropDownBtn.setAttribute("onclick", "DropDown_dropDown('" + this.componentID + "')");
		this.dropDownBtn.setAttribute("class", this.buttonClass);
	}

	/**
	 * set attribute of pop div
	 */
	private void setPopDivAttribute() {
		this.popDiv.setAttribute("onblur", "DropDown_hidePopDiv('" + this.componentID + "',this)");
		this.popDiv.setAttribute("onkeydown", "return DropDown_optionKeyDown('" + this.componentID + "')");
		this.popDiv.setAttribute("popshow", "false");
		if (!this.isIEBrowser)
		{
			this.popDiv.setAttribute("onmouseout", "DropDown_hidePopDiv('" + this.componentID + "',this,event)");
		}
		this.popDiv.style.setStyle("BORDER-RIGHT", "black 1px solid");
		this.popDiv.style.setStyle("BORDER-TOP", "black 1px solid");
		this.popDiv.style.setStyle("VISIBILITY", "hidden");
		this.popDiv.style.setStyle("OVERFLOW", "auto");
		this.popDiv.style.setStyle("BORDER-LEFT", "black 1px solid");
		this.popDiv.style.setStyle("BORDER-BOTTOM", "black 1px solid");
		this.popDiv.style.setStyle("POSITION", "absolute");
		this.popDiv.style.setStyle("top", "0px");
		this.popDiv.style.setStyle("left", "0px");
		this.popDiv.style.setStyle("BACKGROUND-COLOR", "white");
		this.popDiv.style.setStyle("WIDTH", "0px");
		this.popDiv.style.setStyle("Z-INDEX", "10000");
		this.popDiv.style.setStyle("Height", "0px");
	}

	/**
	 * <p>
	 * Description: set hiddens to store relation control
	 * </p>
	 */
	private void setRelatingHidden() {
		if (this.relations.size() <= 0)
		{
			return;
		}
		this.relatingHidden = new ArrayListManager(this.relatingId);
		for (int index = 0; index < this.relations.size(); index++)
		{
			OfbizDropDownList ddl = (OfbizDropDownList) this.relations.get(index);
			this.relatingHidden.add(ddl.getComponentId());
		}
	}

	/**
	 * <p>
	 * Description: set the relation control filter
	 * </p>
	 */
	private void setRelationFilter() {
		if (curItems != null && curItems.size() > 0)
		{
			if (this.selectedIndex != -1)
			{
				selectedItem = ((ListItem) curItems.get(this.selectedIndex));
			}
		}
		if (this.relations.size() > 0)
		{
			for (int index = 0; index < relations.size(); index++)
			{
				if (selectedItem == null)
				{
					((OfbizDropDownList) relations.get(index)).setFilter("");
				} else
				{
					((OfbizDropDownList) relations.get(index)).setFilter(selectedItem.value);
				}
				// ((OfbizDropDownList)relations.get(index)).setSelectedIndex(-1);
			}
		}
	}

	public void effectRelation() {
		this.setCurrentItems();
		this.setRelationFilter();
	}

	/**
	 * <p>
	 * Description: the list item collection after filted
	 * </p>
	 */
	private void setCurrentItems() {
		if (filter == null || filter.equals(""))
		{
			this.curItems = this.items.getAllItems();
		} else
		{
			this.curItems = this.items.getByFilterId(this.filter);
		}
		if (this.curItems != null && this.curItems.size() > 0)
		{
			if (this.selectedIndex != -1)
			{
				this.selectedItem = (ListItem) this.curItems.get(this.selectedIndex);
			}
		}
	}

	/**
	 * ���һ��������dropdownlist
	 * 
	 * @param ddl
	 *            OfbizDropDownList
	 */
	public void addRelationDropDown(OfbizDropDownList ddl) {
		this.relations.add(ddl);
	}

	/**
	 * <p>
	 * Description: add hidden to store the filted list item control accroding as filter id.
	 * </p>
	 */
	private void addFilter() {
		if (items.getFilterIdList().size() <= 0)
		{
			return;
		}
		this.filterCollection = new ArrayList();
		for (int index = 0; index < items.getFilterIdList().size(); index++)
		{
			String filterId = items.getFilterIdList().get(index).toString();
			this.filterList.add(filterId);
			String filterHiddenId = this.componentID + "_" + filterId + "Hidden";
			HashMapManager filterHash = new HashMapManager(filterHiddenId);
			for (int nIndex = 0; nIndex < items.getByFilterId(filterId).size(); nIndex++)
			{
				ListItem curItem = (ListItem) items.getByFilterId(filterId).get(nIndex);
				filterHash.put(curItem.value, curItem.text);
			}
			this.components.add(filterHash);
			this.filterCollection.add(filterHash);
		}
	}

	/**
	 * <p>
	 * Description: set hidden to store current list item collection
	 * </p>
	 */
	private void addContainer() {
		if (curItems == null)
		{
			return;
		}
		for (int index = 0; index < this.curItems.size(); index++)
		{
			ListItem curItem = (ListItem) curItems.get(index);
			this.containerHidden.put(curItem.getValue(), curItem.getText());
		}
	}

	/**
	 * ����б���
	 * 
	 * @param item
	 *            ListItem
	 */
	public void addItem(ListItem item) {
		this.items.addItem(item);
	}

	/**
	 * <p>
	 * Description: remove one list item according as value
	 * </p>
	 * 
	 * @param item:
	 *            the item want to remove
	 */
	/**
	 * ���ֵɾ���б������ĳһ��
	 * 
	 * @param item
	 *            ListItem
	 */
	public void removeItem(ListItem item) {
		this.items.removeItem(item);
	}

	/**
	 * �������ɾ���б������ĳһ��
	 * 
	 * @param index
	 *            int
	 */
	public void removeItem(int index) {
		this.items.removeItem(index);
	}

	/**
	 * ɾ���б������������
	 */
	public void removeAll() {
		this.items.removeAll();
	}

	/**
	 * ����������б�����в�������
	 * 
	 * @param index
	 *            int
	 * @param item
	 *            ListItem
	 */
	public void insertItem(int index, ListItem item) {
		this.items.insertItem(index, item);
	}

	/**
	 * ��ݹ����ֶλ�������б���
	 * 
	 * @param id
	 *            String
	 * @return ArrayList
	 */
	public ArrayList getItemsByFilterId(String id) {
		return this.items.getByFilterId(id);
	}

	/**
	 * ���ù�������
	 * 
	 * @param filterType
	 *            String
	 */
	public void setFilter(String filterType) {
		this.filter = filterType;
	}

	/**
	 * ������������ѡ���������
	 * 
	 * @param index
	 *            int
	 */
	public void setSelectedIndex(int index) {
		this.selectedIndex = index;
	}

	/**
	 * �����������ѡ����
	 * 
	 * @return ListItem
	 */
	public ListItem getSelectedItem() {
		return this.selectedItem;
	}

	public ListItem getSelectedItem(int index) {
		return (ListItem) this.items.getAllItems().get(index);
	}

	/**
	 * �����������ѡ���������
	 * 
	 * @return int
	 */
	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	/**
	 * ���item�������
	 * 
	 * @return int index
	 */
	public int getIndexByItem(ListItem li) {
		setCurrentItems();
		if (curItems == null)
		{
			return -1;
		}
		for (int index = 0; index < this.curItems.size(); index++)
		{
			ListItem curLI = (ListItem) curItems.get(index);
			if (curLI.getText().equals(li.getText()) && curLI.getValue().equals(li.getValue()))
			{
				return index;
			}
		}
		return -1;
	}

	/*
	 * ���value���ListItem @return ListItem
	 */
	public ListItem findItemByValue(String value) {
		setCurrentItems();
		if (curItems == null)
		{
			return null;
		}
		
		for (int index = 0; index < this.curItems.size(); index++)
		{
			ListItem curLI = (ListItem) curItems.get(index);			
			if (curLI.getValue()!=null&&curLI.getValue().equals(value))
			{				
				return curLI;
			}
		}
		return null;
	}

	/*
	 * ���text���ListItem @return ListItem
	 */
	public ListItem findItemByText(String ddlText) {
		setCurrentItems();
		if (curItems == null)
		{
			return null;
		}
		for (int index = 0; index < this.curItems.size(); index++)
		{
			ListItem curLI = (ListItem) curItems.get(index);
			if (curLI.getText().equals(ddlText))
			{
				return curLI;
			}
		}
		return null;
	}

	/*
	 * ���value����ѡ����
	 */
	public void setSelectedValue(String value) {
		ListItem li = findItemByValue(value);
		if (li != null)
		{
			setSelectedItem(li);
		}
	}

	/*
	 * ���ListItem����ѡ�е���
	 */
	public void setSelectedItem(ListItem li) {
		int index = getIndexByItem(li);		
		this.setSelectedIndex(index);
	}

	/**
	 * ��������������
	 * 
	 * @param type
	 *            DropDownType
	 */
	public void setDropDownType(DropDownType type) {
		this.dropDownType = type.getDropDownType();
	}

	/**
	 * ���ð�ť���
	 * 
	 * @param bWidth
	 *            int
	 */
	public void setButtonWidth(int bWidth) {
		this.buttonWidth = bWidth;
	}

	/**
	 * ���ð�ť�߶�
	 * 
	 * @param bHeight
	 *            int
	 */
	public void setButtonheight(int bHeight) {
		this.buttonHeight = bHeight;
	}

	/**
	 * ���button���
	 * 
	 * @return int
	 */
	public int getButtonWidth() {
		return this.buttonWidth;
	}

	/**
	 * ���button�߶�
	 * 
	 * @return int
	 */
	public int getButtonHeight() {
		return this.buttonHeight;
	}

	/**
	 * ���ÿͻ����¼�onselectedchange
	 * 
	 * @param functionStr
	 *            String
	 */
	/**
	 * public void setOnChangeEvent(String functionStr){ IEvent event = new Event("onselectedchange"); event.setFuncName(functionStr); this.addEvent(event); }
	 */

	/**
	 * ������beanshell���õķ��������¼�
	 * 
	 * @param functionStr
	 *            String
	 * @param fileName
	 *            String
	 * 
	 * public void setOnChangeEvent(String functionStr, String fileName){ IServerEvent event = new ServerEvent("onselectedchange"); event.setFuncName(functionStr); event.setBshFile(fileName);
	 * this.addEvent(event); }
	 */

	/**
	 * ���÷������¼�onselectedchange
	 * 
	 * @param functionStr
	 *            String
	 * @param className
	 *            String
	 * @param methodName
	 *            String
	 * 
	 * public void setOnChangeEvent(String functionStr, String className, String methodName){ IServerEvent event = new ServerEvent("onselectedchange"); event.setClassName(className);
	 * event.setMethodName(methodName); event.setFuncName(functionStr); this.addEvent(event); }
	 */
	/**
	 * ���onchange�¼�
	 * 
	 * @return IEvent
	 * 
	 * public IEvent getOnChangeEvent(){ return this.getEvent("onselectedchange"); }
	 */

	/**
	 * ���ͼƬ��ť
	 * 
	 * @return Image
	 */
	public Image getImageButton() {
		return this.dropDownBtn;
	}

	/**
	 * ����������TextBox���
	 * 
	 * @return TextBox
	 */
	public TextBox getTextBox() {
		return this.dropDownText;
	}

	/**
	 * ���ð�ť��ͼƬ·��
	 * 
	 * @param filePath
	 *            String
	 */
	public void setImageSrc(String filePath) {
		this.imageSrc = filePath;
	}

	public String getImageSrc() {
		return this.imageSrc;
	}

	/**
	 * ��ý���ʱ��ť��ͼƬ·��
	 * 
	 * @return String
	 */
	public String getDisImageSrc() {
		return this.disImageSrc;
	}

	/**
	 * ���ý���ʱ������ť��ͼƬ·��
	 * 
	 * @param filePath
	 *            String
	 */
	public void setDisImageSrc(String filePath) {
		this.disImageSrc = filePath;
	}

	/**
	 * ����ֻ��
	 * 
	 * @param onlyRead
	 *            boolean
	 */
	public void setReadOnly(boolean onlyRead) {
		this.readonly = onlyRead;
	}

	/**
	 * 
	 * @param necessary
	 */
	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	/**
	 * �ж��Ƿ�ֻ��
	 * 
	 * @return boolean
	 */
	public boolean getReadOnly() {
		return this.readonly;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean getNecessary() {
		return this.necessary;
	}

	/**
	 * ��������е��б���
	 * 
	 * @param controlId
	 *            String
	 * @param request
	 *            HttpServletRequest
	 * @return ListItem
	 * @deprecated As of this version
	 */
	public static ListItem getResult(String controlId, HttpServletRequest request) {
		String selectedText = request.getParameter(controlId + "_selectedText");
		String selectedValue = request.getParameter(controlId + "_selectedValue");
		ListItem li = new ListItem(selectedText, selectedValue);
		return li;
	}

	public static ListItem getResult(String controlId, RequestUtils request) {
		String selectedText = request.getString(controlId + "_selectedText");
		String selectedValue = request.getString(controlId + "_selectedValue");
		ListItem li = new ListItem(selectedText, selectedValue);
		return li;
	}

	public static String getText(HttpServletRequest request, String controlId) {
		return OfbizDropDownList.getResult(controlId, request).getText();
	}

	public static String getText(RequestUtils request, String controlId) {
		return OfbizDropDownList.getResult(controlId, request).getText();
	}

	public static String getValue(HttpServletRequest request, String controlId) {
		return OfbizDropDownList.getResult(controlId, request).getValue();
	}

	public static String getValue(RequestUtils request, String controlId) {
		return OfbizDropDownList.getResult(controlId, request).getValue();
	}

	private static String getChinese(String in) {
		String result = null;
		byte temp[];
		if (in == null)
		{
			return null;
		}
		try
		{
			temp = in.getBytes("iso-8859-1");
			result = new String(temp);
		} catch (Exception e)
		{
			// System.out.println(e.toString());
		}
		return in;
	}

	public void setText(String text) {
		this.dropDownText.setText(text);
		this.textHidden.setText(text);
	}

	/**
	 * ����������TextBox�����������
	 * 
	 * @param styleClass
	 *            String
	 */
	public void setTextBoxClass(String styleClass) {
		this.textBoxClass = styleClass;
		dropDownText.setCssClass(this.textBoxClass);
	}

	/**
	 * ���TextBox�����������
	 * 
	 * @return String
	 */
	public String getTextBoxClass() {
		return this.textBoxClass;
	}

	/**
	 * ���ð�ť��������
	 * 
	 * @param styleClass
	 *            String
	 */
	public void setButtonClass(String styleClass) {
		this.buttonClass = styleClass;
	}

	/**
	 * ���button��������
	 * 
	 * @return String
	 */
	public String getButtonClass() {
		return this.buttonClass;
	}

	public void setWidth(String width) {
		this.style.setStyle("width", width);
	}

}
