package com.born.insurance.webui.control;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
//import org.ofbiz.core.entity.GenericDelegator;

/**
 * Title:FilterSet Component<br>
 * Description:ɸѡ�����������<br>
 * Copyright: Copyright (c) 2004<br>
 * Company: bornsoft<br>
 * @author �ᴺ��
 * @version 1.0
 */
public class FilterSet extends AbstractComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button btnSaveAs;
	//////////////////�������////////////////////////
	public static final String TEXT = "text";
	public static final String VALUE = "value";
	/**
	 * �������Ϣ
	 */
	private HttpServletRequest request=null;
	/**
	 * ��ǰ������Ƿ���IE
	 */
	private boolean isIE=true;
	/**
	 * ��Ŀ�������
	 */
	private Map fieldType=null;
	/**
	 * ��Ŀ����ʵ��
	 */
	private Map relEntity=null;
	/**
	 * �������
	 */
	private String ListCondition ="";
	private List lstFilterCond=null;
	//////////////// ���Դ  /////////////////
	private List lstFilterFields=new ArrayList();

	///////////////////����Ԫ��///////////////////////
	/**
	 *	��ɸѡ��Ŀչʾ�б�
	 */
	private Table tblOutPut=null;
	private ListBox lstFilterField = null;
	//*** �����༭��*/
	private OfbizTextBox txtFrom = null;
	private OfbizTextBox txtTo = null;
	private OfbizTextBox txtNumberFrom = null;
	private OfbizTextBox txtNumberTo = null;
	private OFBIZCalendar calDataFrom = null;
	private OFBIZCalendar calDataTo = null;
	private HtmlDiv hmmSearchTextDiv = null;
	private HashMapManager hmmFieldType=null;
	private HashMapManager hmmRelEntity=null;
	///////////��ť//////////
	private Button btnSave = null;
	private Button btnCancel = null;
	private Button btnClear = null;
	private Button btnDel=null;
	/**
	 * �������������б��
	 */
	private OfbizDropDownList ddlSelect =  null;
	////////��ѡ������ʾ//////////
	private WebGrid condGrid = null;

	///////////////////////��������
	private HashMapManager hmmCondOp = null;//���������� fieldName,operatorId��ӳ��
	private HashMapManager hmmCondVal1 = null;//����ֵ1 fieldName,fieldParameter1��ӳ��
	private HashMapManager hmmCondVal2 = null;//����ֵ2 fieldName, fieldParameter2��ӳ��
	private HashMapManager hmmOpMap = null;//operatorId,operator��ӳ��

	/**
	 * ���캯��
	 * @param id  ���ID
	 * @param request �������(���Ϊ����Ĭ��ΪIE)
	 */
	public FilterSet(String id,HttpServletRequest request)
	{
		super(id);
		this.request=request;
		this.isIE=ComponentUtil.isIEBrowser(request);
		this.initComponent();
	}
	/**
	 * ���캯��
	 * @param id  ���ID
	 * @deprecated ����ʹ��FilterSet(id,request)����,�����������Ϊֻ��IE��������
	 */
	public FilterSet(String id)
	{
		super(id);
		this.isIE=ComponentUtil.isIEBrowser(request);
		this.initComponent();
	}
	/**
	 * ��ȡɸѡ��Ŀ�б��
	 * @return ��ȡɸѡ��Ŀ�б��
	 */
	public ListBox getFilterFieldList()
	{
		return  this.lstFilterField;
	}
	/**
	 * ����ɸѡ��Ŀ�б��
	 * @param lstbox ����ɸѡ��Ŀ�б��
	 */
	public void setFilterFieldList(ListBox lstbox)
	{
		this.lstFilterField=lstbox;
	}
	/**
	 * ��ȡɸѡ��Ŀ
	 * @return
	 */
	public List getFilterFields()
	{
		return this.lstFilterFields;
	}
	/**
	 * ����ɸѡ��Ŀ
	 * @param lst
	 */
	public void setFilterFields(List lst)
	{
		this.lstFilterField.setDataSource(lst);
		this.lstFilterFields=lst;
	}
	/**
	 * ��ȡ��������
	 * @return
	 */
	public List getlstFilterCondList()
	{
		return this.lstFilterCond;
	}
	/**
	 * ������������
	 *
	 */
	public void setlstFilterCondList(List lst)
	{
		this.lstFilterCond=lst;
	}
	/**
	 * ��ȡ��Ŀ�������
	 * @return
	 */
	public Map getFieldType()
	{
		return this.fieldType;
	}
	/**
	 * ������Ŀ�������
	 * @param map
	 */
	public void setFieldType(Map map)
	{
		this.fieldType=map;
	}
	/**
	 * ��ȡ����ʵ��
	 * @return
	 */
	public Map getRelEntity()
	{
		return this.relEntity;
	}
	/**
	 * ���ù���ʵ��
	 * @param map
	 */
	public void setRelEntity(Map map)
	{
		this.relEntity=map;
	}
	/**
	 * ��ȡ���水ť
	 * @return
	 */
	public Button getButtonSave()
	{
		return this.btnSave;
	}
	/**
	 * ���ñ��水ť
	 * @param btn
	 */
	public void setButtonSave(Button btn)
	{
		this.btnSave=btn;
	}
	/**
	 * ��ȡȡ��ť
	 * @return
	 */
	public Button getButtonCancel()
	{
		return this.btnCancel;
	}
	/**
	 * ����ȡ��ť
	 * @param btn
	 */
	public void setButtonCancel(Button btn)
	{
		this.btnCancel=btn;
	}
	/**
	 * ��ȡ���ť
	 * @return
	 */
	public Button getButtonClear()
	{
		return this.btnClear;
	}
	/**
	 * �������ť
	 * @param btn
	 */
	public void setButtonClear(Button btn)
	{
		this.btnClear=btn;
	}
	/**
	 * ��ȡ����Grid
	 * @return
	 */
	public WebGrid getCondGrid()
	{
		return this.condGrid;
	}
	/**
	 * ��������Grid
	 * @param webgrid
	 */
	public void setConGrid(WebGrid webgrid)
	{
		this.condGrid=webgrid;
	}
	/**
	 * ��ʼ�������
	 *
	 */
	private void initComponent()
	{
		//ɸѡ��ѡ��Ŀ�б��
		this.initFilterField();
		//��ʼ�������༭��
		this.initConditionEditor();
		//��ʼ������grid
		this.initConditionGrid();
		//��ʼ��������ť
		this.initOperatButton();
	}
	/**
	 * ��ʼ�������
	 *
	 */
	private void initOutTable()
	{
		//Table���溯���У�����ǰ,���ں�
		this.tblOutPut=new Table("",2,3);
		this.tblOutPut.addAttribute("cellSpacing","0");
		this.tblOutPut.addAttribute("cellPadding","0");
		this.tblOutPut.addAttribute("width","100%");
		this.tblOutPut.addAttribute("border","0");
		////////////////��0��
		this.tblOutPut.getCell(0,0).addAttribute("vAlign","top");
		this.tblOutPut.getCell(0,0).addAttribute("align","right");
		this.tblOutPut.getCell(0,0).addAttribute("width","40%");
		////////////�ӿؼ�
		Table tblList = new Table("",1,2);
		tblList.addAttribute("cellSpacing","0");
		tblList.addAttribute("cellPadding","0");
		tblList.addAttribute("width","100%");
		tblList.addAttribute("border","0");
		tblList.getCell(0,0).addAttribute("vAlign","top");
		tblList.getCell(0,0).addAttribute("align","left");
		tblList.getCell(0,0).addAttribute("width","100%");
		tblList.getCell(0,0).addAttribute("class","label");
		tblList.getCell(0,0).setText("��ѡ��Ŀ");
		tblList.getCell(1,0).addAttribute("vAlign","top");
		tblList.getCell(1,0).addAttribute("width","100%");
		tblList.getCell(1,0).setWrap(false);
		tblList.getCell(1,0).addComponent(this.lstFilterField);
		/////////////////�ӿؼ����
		this.tblOutPut.getCell(0,0).addComponent(tblList);

		////////////////��һ��
		this.tblOutPut.getCell(0,1).addAttribute("vAlign","top");
		this.tblOutPut.getCell(0,1).addAttribute("align","left");
		this.tblOutPut.getCell(0,1).addAttribute("width","60%");
		/////////////�ڶ����ӿؼ�
		Table tblCondition = new Table("Table2",1,1);
		tblCondition.addAttribute("cellSpacing","1");
		tblCondition.addAttribute("cellPadding","1");
		tblCondition.addAttribute("border","0");
		tblCondition.getCell(0,0).addAttribute("align","left");
		tblCondition.getCell(0,0).setWrap(false);
		Table tblT = new Table("T",1,1);
		tblT.addAttribute("cellSpacing","0");
		tblT.addAttribute("cellPadding","0");
		tblT.addAttribute("width","100%");
		tblT.getCell(0,0).addAttribute("height","7");
		tblCondition.getCell(0,0).addComponent(tblT);

		FieldSet fs=new FieldSet("");
		fs.setStyle("border:1px solid; border-color:808080;PADDING-RIGHT: 5px; PADDING-LEFT: 5px; PADDING-BOTTOM: 5px; MARGIN: 0px; WIDTH: 264px; CURSOR: hand; PADDING-TOP: 5px;");
		Legend leg=new Legend("");
		leg.setStyle("FONT-SIZE: 12px");
		leg.setText("��������");
		fs.setLegend(leg);

		Table tblConditionSet=new Table("",1,4);
		tblConditionSet.addAttribute("cellSpacing","0");
		tblConditionSet.addAttribute("cellPadding","0");
		tblConditionSet.addAttribute("border","0");
		//////////��һ���Ա��⴦
		tblConditionSet.getCell(0,0).setStyle("WIDTH: 250px;height:18px");
		tblConditionSet.getCell(0,0).addAttribute("vAlign","bottom");
		tblConditionSet.getCell(0,0).addAttribute("align","left");
		tblConditionSet.getCell(0,0).setWrap(false);
		Label lbFN=new Label("lblFieldName");
		lbFN.setCssClass("label");
		tblConditionSet.getCell(0,0).addComponent(lbFN);
		//////////�ڶ���������
		tblConditionSet.getCell(1,0).setStyle("HEIGHT: 20px;WIDTH: 250px");
		tblConditionSet.getCell(1,0).addAttribute("align","left");
		tblConditionSet.getCell(1,0).setWrap(false);
		tblConditionSet.getCell(1,0).addComponent(this.ddlSelect);
		//////////�����д����
		tblConditionSet.getCell(2,0).setStyle("WIDTH: 250px;HEIGHT: 20px;");
		//tblConditionSet.getCell(2,0).componentID="tdFrom";
		//tblConditionSet.getCell(2,0).addAttribute("id","tdFrom");
		tblConditionSet.getCell(2,0).addAttribute("vAlign","middle");
		tblConditionSet.getCell(2,0).addAttribute("align","left");
		tblConditionSet.getCell(2,0).setWrap(false);
		tblConditionSet.getCell(2,0).setCssClass("label");
        Nobr nb3=new Nobr();
		HtmlDiv divLabelFrom=new HtmlDiv("divLabelFrom");
		divLabelFrom.setCssClass("DISPLAY: inline;HEIGHT: 13px;");
		divLabelFrom.setText("��");
		nb3.addComponent(divLabelFrom);
		Label divCalendar1=new Label("divCalendar1");
		divCalendar1.setStyle("DISPLAY:inline;HEIGHT:15px;width:148px;z-index:101;");
		divCalendar1.addComponent(this.calDataFrom);
		nb3.addComponent(divCalendar1);

		HtmlDiv divSearchText=new HtmlDiv("divSearchText");
		divSearchText.setStyle("DISPLAY:inline;HEIGHT:15px;");
		nb3.addComponent(divSearchText);

		HtmlDiv divTextBoxFrom=new HtmlDiv("divTextBoxFrom");
		divTextBoxFrom.setStyle("DISPLAY:inline;HEIGHT:15px;");
		divTextBoxFrom.addComponent(this.txtFrom);
		nb3.addComponent(divTextBoxFrom);

		HtmlDiv divTextBoxNumberFrom=new HtmlDiv("divTextBoxNumberFrom");
		divTextBoxNumberFrom.setStyle("DISPLAY:inline;HEIGHT:15px;");
		divTextBoxNumberFrom.addComponent(this.txtNumberFrom);
		nb3.addComponent(divTextBoxNumberFrom);

		Label lblExplain=new Label("lblExplain");
		lblExplain.setCssClass("label");
		nb3.addComponent(lblExplain);

		tblConditionSet.getCell(2,0).addComponent(nb3);
		//////////�����е����
		tblConditionSet.getCell(3,0).setStyle("HEIGHT: 20px;WIDTH: 250px;");
		tblConditionSet.getCell(3,0).addAttribute("vAlign","middle");
		tblConditionSet.getCell(3,0).addAttribute("align","left");
		tblConditionSet.getCell(3,0).setWrap(false);
		tblConditionSet.getCell(3,0).setCssClass("label");

		Nobr nb4=new Nobr();
		HtmlDiv lblTo=new HtmlDiv("divLabelTo");
		lblTo.setStyle("DISPLAY: inline; HEIGHT: 13px");
		lblTo.setText("��");
		nb4.addComponent(lblTo);

		Label divCalendar2=new Label("divCalendar2");
		divCalendar2.setStyle("DISPLAY:inline;HEIGHT:15px;width:148px;z-index:100;");
		divCalendar2.addComponent(this.calDataTo);
		nb4.addComponent(divCalendar2);

		HtmlDiv divTextBoxTo=new HtmlDiv("divTextBoxTo");
		divTextBoxTo.setStyle("DISPLAY: inline; HEIGHT: 15px;");
		divTextBoxTo.addComponent(this.txtTo);
		nb4.addComponent(divTextBoxTo);

		HtmlDiv divTextBoxNumberTo=new HtmlDiv("divTextBoxNumberTo");
		divTextBoxNumberTo.setStyle("DISPLAY: inline; HEIGHT: 15px;");
		divTextBoxNumberTo.addComponent(this.txtNumberTo);
		nb4.addComponent(divTextBoxNumberTo);
		tblConditionSet.getCell(3,0).addComponent(nb4);

		//���ӵ������
		fs.addComponent(tblConditionSet);
		tblCondition.getCell(0,0).addComponent(fs);

		this.tblOutPut.getCell(0,1).addComponent(tblCondition);

		Table Table1=new Table("Table1",1,1);
		Table1.setStyle("table-layout:fixed");
		Table1.addAttribute("cellSpacing","1");
		Table1.addAttribute("cellPadding","1");
		Table1.addAttribute("border","0");

		FieldSet fs1=new FieldSet("");
		fs1.setStyle("border:1px solid; border-color:808080;PADDING-RIGHT: 5px; PADDING-LEFT: 5px; PADDING-BOTTOM: 5px; MARGIN: 0px; WIDTH: 264px; CURSOR: hand; PADDING-TOP: 5px; HEIGHT: 119px");
		Legend leg1=new Legend("");
		leg1.setStyle("FONT-SIZE: 12px");
		leg1.setText("��ѡ����");
		fs1.setLegend(leg1);
		fs1.addComponent(this.condGrid);
		Table1.getCell(0,0).addComponent(fs1);
		this.tblOutPut.getCell(0,1).addComponent(Table1);
		/////////////��ť��
		this.tblOutPut.getCell(1,0).setColumnSpan(2);
		this.tblOutPut.getCell(1,1).setVisible(false);
		Label lblSpace=new Label("");
		lblSpace.setText("&nbsp;");
		this.tblOutPut.getCell(1,0).addComponent(this.btnCancel);
		this.tblOutPut.getCell(1,0).addComponent(lblSpace);
		this.tblOutPut.getCell(1,0).addComponent(this.btnSave);
		this.tblOutPut.getCell(1,0).addComponent(lblSpace);
		if(!"0".equals(request.getParameter("ID")))
		{
			
			this.tblOutPut.getCell(1,0).addComponent(this.btnSaveAs);
			this.tblOutPut.getCell(1,0).addComponent(lblSpace);
			this.tblOutPut.getCell(1,0).addComponent(this.btnDel);
			this.tblOutPut.getCell(1,0).addComponent(lblSpace);
			
		}		
		this.tblOutPut.getCell(1,0).addComponent(this.btnClear);
		this.intFieldSet();
		this.tblOutPut.getCell(2,0).addComponent(this.hmmFieldType);
		this.tblOutPut.getCell(2,0).addComponent(this.hmmRelEntity);
		this.getSearchText();
		this.tblOutPut.getCell(2,0).addComponent(this.hmmSearchTextDiv);
		this.initFiterCondition();
		this.tblOutPut.getCell(2,0).addComponent(this.hmmCondOp);
		this.tblOutPut.getCell(2,0).addComponent(this.hmmCondVal1);
		this.tblOutPut.getCell(2,0).addComponent(this.hmmCondVal2);
		this.tblOutPut.getCell(2,0).addComponent(this.hmmOpMap);
	}
	/**
	 * ��ʼ����Ŀ����
	 *
	 */
	private void intFieldSet()
	{
		this.hmmFieldType=new HashMapManager("hmmFieldType");
		if(this.fieldType!=null)
		{
			this.hmmFieldType.setHashMap(this.fieldType);
		}
		
		this.hmmRelEntity=new HashMapManager("hmmRelEntity");
		if(this.relEntity!=null)
		{
			this.hmmRelEntity.setHashMap(this.relEntity);
		}
		
	}
	/**
	 * ��ʼ����������
	 *
	 */
	private void initFiterCondition()
	{
		this.hmmCondOp = new HashMapManager("hmmCondOp");     //���������� fieldName,operatorId��ӳ��
		this.hmmCondVal1 = new HashMapManager("hmmCondVal1"); //����ֵ1 fieldName,fieldParameter1��ӳ��
		this.hmmCondVal2 = new HashMapManager("hmmCondVal2"); //����ֵ2 fieldName, fieldParameter2��ӳ��
		this.hmmOpMap = new HashMapManager("hmmOpMap");       //operatorId,operator��ӳ��
		if (this.lstFilterCond != null){
			for (int i = 0; i < this.lstFilterCond.size(); i++){
				Map gv = (Map)this.lstFilterCond.get(i);
				String fieldName = (String)gv.get("fieldName");

				String operator = String.valueOf(gv.get("operationSign"));
				String opId = operator;
				hmmCondOp.put(fieldName, opId);

				String param1 = gv.get("conditionValue1") == null ? null : (String)gv.get("conditionValue1");
				String param2 = gv.get("conditionValue2") == null ? null : (String)gv.get("conditionValue2");

				if (param1 != null){
					hmmCondVal1.put(fieldName, param1);
				}

				if (param2 != null){
					hmmCondVal2.put(fieldName, param2);
				}
			}
		}
	}
	/**
	 * ��ʼ��ɸѡ��ѡ��Ŀ�б��
	 *
	 */
	private void initFilterField()
	{
		this.lstFilterField = new ListBox("lstFilterField");
		lstFilterField.addAttribute("size", "4");	
		lstFilterField.addStyle("height", "265px");
		lstFilterField.addStyle("width", "180px");
		lstFilterField.addStyle("background-color", "white");
		lstFilterField.addStyle("border-color", "White");
		lstFilterField.addStyle("BORDER-RIGHT", "#dcdcdc 3px double");
		lstFilterField.addStyle("BORDER-TOP", "#696969 2px inset");
		lstFilterField.addStyle("BORDER-LEFT", "#696969 2px solid");
		lstFilterField.addStyle("BORDER-BOTTOM", "#dcdcdc 3px double");
		lstFilterField.addEvent("onchange", "lstFilterField_onchange();");
		lstFilterField.setTextField(FilterSet.TEXT);
		lstFilterField.setValueField(FilterSet.VALUE);
	}
	/**
	 * ��ʼ�������༭��
	 */
	private void initConditionEditor()
	{
		txtFrom = new OfbizTextBox("txtFrom");
		txtTo = new OfbizTextBox("txtTo");
		txtNumberFrom = new OfbizTextBox("txtNumberFrom");
		txtNumberTo = new OfbizTextBox("txtNumberTo");
		txtFrom.addStyle("width", "148px");
		txtTo.addStyle("width", "138px");
		txtNumberFrom.addStyle("width", "148px");
		txtNumberFrom.setIsNumber(true);
		txtNumberTo.addStyle("width", "148px");
		txtNumberTo.setIsNumber(true);
		hmmSearchTextDiv = new HtmlDiv("hmmSearchText");
		hmmSearchTextDiv.setStyle("DISPLAY","none");
		calDataFrom = new OFBIZCalendar("calDataFrom",this.request);
		//calDataFrom.setWidth("150");
		
		calDataTo = new OFBIZCalendar("calDataTo",this.request);
		//calDataTo.setWidth("150");
		calDataTo.setOnChangeEvent("calendar_onchange();");
		calDataFrom.setOnChangeEvent("calendar_onchange();");

		ddlSelect = new OfbizDropDownList("ddlSelect", this.request);
		ddlSelect.setDropDownType(DropDownType.ONLYDROPDOWN);
		ddlSelect.addStyle("width", "150px");
		ddlSelect.setOnChangeEvent("ddlSelect_onchange();");
	}
	/**
	 * ��ʼ������grid
	 *
	 */
	private void initConditionGrid()
	{
		condGrid = new WebGrid("condGrid", this.request, 4);
		condGrid.setDefaultColumnWidth("117px");
		condGrid.setHeadCssClass("listhead");
		HtmlDiv div = condGrid.getBodyDiv();
		div.addStyle("OVERFLOW-Y", "auto");
		div.addStyle("HEIGHT", "92px");

		ArrayList listHead = new ArrayList();
		Map headMap=new HashMap();
		headMap.put("filterItem","������Ŀ");
		headMap.put("filterCond","��������");
		listHead.add(headMap);
		condGrid.setHeadDataSource(listHead);
		condGrid.setDataSource(new ArrayList());
		condGrid.getGridColumn(0).setDataField("filterItem");
		condGrid.getGridColumn(1).setDataField("filterItemId");
		condGrid.getGridColumn(1).setVisible(false);
		condGrid.getGridColumn(2).setDataField("filterCond");
		condGrid.getGridColumn(3).setDataField("filterCondId");
		condGrid.getGridColumn(3).setVisible(false);
		condGrid.setNoWrap(true);
		condGrid.setBorder(1);
		condGrid.setCellPadding(0);
		condGrid.setCellSpacing(0);
		condGrid.setGridViewModel(WebGrid.CUSTOM_MODEL);
		condGrid.setEditRowChangeEvent("condGridEditRow_onchange();");

	}
	/**
	 * ��ʼ��������ť
	 *
	 */
	private void initOperatButton()
	{
		String imgPath = ComponentUtil.getInstance().getImagesRequestPath();
		//		ȷ�ϰ�ť
		this.btnSave = new Button("btnSave");
		this.btnSave.setText("�� ��");
		this.btnSave.setCssClass("bgbutton");
		this.btnSave.setEvent("onclick","lstFilterField_onchange();validate(this,false);");
		//this.btnSave.setStyle("background-image", "url(" + imgPath + "/bgbutton.gif)");
		
		this.btnSaveAs = new Button("btnSave");
		this.btnSaveAs.setText("�� ��");
		this.btnSaveAs.setCssClass("bgbutton");
		this.btnSaveAs.setEvent("onclick","lstFilterField_onchange();validate(this,true);");
		//ɾ��ť
		this.btnDel = new Button("btnDel");
		this.btnDel.setText("ɾ ��");
		this.btnDel.setCssClass("bgbutton");
		this.btnDel.setEvent("onclick","btnDelete_onclick(this.form);");
		
		//ȡ��ť
		this.btnCancel = new Button("btnCancel");
		this.btnCancel.setText("ȷ ��");
		this.btnCancel.setCssClass("bgbutton");
		//this.btnCancel.setStyle("background-image", "url(" + imgPath + "/bgbutton.gif)");
		this.btnCancel.addEvent("onclick","lstFilterField_onchange();validateComfirm(this);");
		
		//���ť
		this.btnClear = new Button("bntClear");
		this.btnClear.setText("�� ��");
		this.btnClear.setCssClass("bgbutton");
		//this.btnClear.setStyle("background-image", "url(" + imgPath + "/bgbutton.gif)");
		this.btnClear.addEvent("onclick", "btnClear_onclick();");

	}
	/**
	 * ��ʼ���������
	 *
	 */
	private void getSearchText()
	{
		if(this.fieldType==null)
		{
			return;
		}
		for (Iterator i = this.fieldType.entrySet().iterator(); i.hasNext(); )
		{
			Map.Entry e = (Map.Entry) i.next();
			if(("SearchText").equals(e.getValue()))
			{
				SearchText searchText = new SearchText("common_st");
				searchText.setReadOnly(true);
				Label temDiv = new Label("common_st_lab");
				
				//Label temDiv = new Label((String)e.getKey());
				searchText.setWidth("149px");
				searchText.setPageUrl("anySelectpicker.jsp");
				searchText.setOnChangeEvent("searchTextOnChanged();");
				searchText.setAutoSearch(false);
				searchText.setHotKey("Enter");
				searchText.setWidth("200");
				searchText.setWindowHeight(480);
				searchText.setWindowWidth(400);
				searchText.getButton().setEvent("onClick","searchTextOnclick()");
				//searchText.setSearchEntity((String)this.relEntity.get((String)e.getKey()));
				temDiv.setText(searchText.getInnerHtml());
				hmmSearchTextDiv.addComponent(temDiv);
				break;
			}
		}
	}
	/**
	 * ��ݰ�
	 */
	public void dataBind()
	{
		this.lstFilterField.dataBind();
		this.condGrid.dataBind();
	}
	/**
	 * ���Ԫ��
	 */
	protected String getElementHtml()
	{
		StringBuffer elementHtml=new StringBuffer();
		elementHtml.append(this.tblOutPut.getElementHtml());
		return elementHtml.toString();
	}
	/**
	 * ��ɽű�
	 */
	protected String getScriptHtml()
	{
		StringBuffer scriptHtml=new StringBuffer();
		//��ʼ�������
	    this.initOutTable();
		scriptHtml.append(super.getScriptHtml());
		scriptHtml.append(FilterSetJS.getFilterSetJS());
		scriptHtml.append(this.tblOutPut.getScriptHtml());
		scriptHtml.append(this.hmmFieldType.getScriptHtml());
		return scriptHtml.toString();
	}
	/**
	 * ��ȡ��������
	 * @param filterSetid ���ID
	 * @param request ����
	 * @return ����List �ڷ�һ��Map,Map�ṹΪ:entityFieldName,operator,fieldParameter1,fieldParameter2
	 */
	public static List getReturnCondition(String filterSetid,HttpServletRequest request)
	{
		List result=new ArrayList();
		Map condOp = HashMapManager.parseToMap("hmmCondOp", request);
		Map condOpMap = HashMapManager.parseToMap("hmmOpMap", request);
		Map condVal1 = HashMapManager.parseToMap("hmmCondVal1", request);
		Map condVal2 = HashMapManager.parseToMap("hmmCondVal2", request);

		if (condOp == null || condOp.isEmpty())
		{
			return result;
		}
		for (Iterator i = condOp.entrySet().iterator(); i.hasNext();)
		{
			Map fieldCon=new HashMap();
			Map.Entry e = (Map.Entry) i.next();
			String key=(String)e.getKey();
			if(key ==null || ("").equals(key) || ("null").equals(key))
			{
				continue;
			}
			String id=(condOp.get(key))==null ? null : condOp.get(key).toString();
			String op=id;//FilterOperator.getName(id);
			String p1=(condVal1.get(key))==null ? null : condVal1.get(key).toString();
			String p2=(condVal2.get(key))==null ? null : condVal2.get(key).toString();
			fieldCon.put("listCondInstanceId",null);
			fieldCon.put("fieldName",key);
			fieldCon.put("operationSign",op);
			fieldCon.put("listFilterId",null);
			fieldCon.put("conditionValue1",p1);
			fieldCon.put("conditionValue2",p2);
			result.add(fieldCon);
		}
		return result;
	}
	/**
	 * ���listCondigionField�ṹ��װΪ����List
	 * @param listConditionField ��Ϊ��listConditionField�Ľṹ
	 * @param entityName ʵ����
	 * @return ����List
	 */
//	public static List getParseCondition(GenericDelegator delegator,String entityName,List listConditionField)
//	{
//		return FilterOperator.getCondition(delegator,listConditionField,entityName);
//	}
}