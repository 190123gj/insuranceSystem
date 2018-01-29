package com.born.insurance.webui.control;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Title:ColumnSet Component<br>
 * Description:��Ŀ����<br>
 * Copyright: Copyright (c) 2003<br>
 * Company: bornsoft<br>
 * @author qch
 * @version 1.0
 */
public class ColumnSet extends AbstractComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList SourceItems= new ArrayList();				//��ѡ��Ŀ��ݼ�
	private ArrayList SelectedItems=new ArrayList();			//��ѡ��Ŀ��ݼ�
	private ArrayList NecessaryItems = new ArrayList();			//��ѡ��Ŀ��ݼ�
	private ArrayList FixItems = new ArrayList();				//�̶���Ŀ��ݼ�
	private boolean blnFixItemReadOnly =true;					//�̶���Ŀֻ����־
	private boolean blnNecessaryReadOnly=false;					//��ѡ��Ŀֻ����־

	//���±���Ϊ�ƶ���ť�ı���(��ť��Image)
	private ArrayList ArrayListBtns=new ArrayList();
	private ArrayList ArrayListBtnsSrc=new ArrayList();
	private int intMoveBtnWidth=22;
	private int intMoveBtnHeight=20;
	private int intMoveBtnListBoxSpace=10;
	private int intMoveBtnsSpaceHeight=10;
	private String strMoveBtnClass=null;
	private int intOrderBtnListBoxSpace=15;
	private int intOrderBtnsSpace=15;
	private int intOrderBtnWidth=22;
	private int intOrderBtnHeight=24;
	private String strOrderBtnClass=null;

	private ArrayList ArrayListTable=new ArrayList();

	//���±���ΪLabel�ı���
	private int intLabelHeight=15;
	private String strListLabClass=null;
	private String strTextBoxLabClass=null;
	private ArrayList ArrayListLabels=new ArrayList();
	private int intTextLabSelListSpace_H=12;						//TextBox��Label���ұߵ�ListBoxˮƽ����ľ���
	private int intTextLabSelListSpace_V=0;							//TextBox��Label(��TextBox)���ұߵ�ListBox��ֱ����ľ���

	//���±���ΪListBox�ı���
	private String strListBoxClass=null;
	private int intListBoxWidth=150;
	private int intListBoxHeight=184;
	private ArrayList ArrayListListBoxs=new ArrayList();
	private String strsetItemSelected=null;
	private String strsetItemChanged=null;

	//���±���ΪTextBox�ı���
	private Table TextBoxTable=null;
	private int intTextBoxWidth=150;
	private TextBox TxtBox=null;
	private String strTxtBoxID=null;
	private String strTextBoxClass=null;
	private boolean blntxtisvisiblue=true;

	private ArrayList ArrayListHidden=new ArrayList();
	private HashMapManager widthHidden=null;
	private String EventParameters=null;
	private final int ControlsNums=12;
	private int StartTabIndex=1;

	private Hidden FieldsHidden=null;
	private boolean blnIsSetHidden=false;
	private String[][] FieldsString={{"ID","Name"},{"ID","Name"},{"ID","Name"},{"ID","Name"}};
	private boolean blnComIsEnable=true;

	/**
	 * ���캯��<br>
	 * @param id ���ID<br>
	 */
	public ColumnSet(String id){
		super(id);
		this.name =id;

		String ID=null;
		Object CurObject=null;
		Table CurTable=null;

		for (int index=0;index<3;index++){
			ID=id+"_div_"+index;
			CurObject=new HtmlDiv(ID);
			this.ArrayListLabels.add(CurObject);
		}

		for (int index=0;index<3;index++){
			ID=id+"_tab_"+index;
			if (index==0)
				CurObject=new Table(ID,4,2);
			if (index==1)
				CurObject=new Table(ID,3,9);
			if (index==2)
				CurObject=new Table(ID,2,5);
			this.ArrayListTable.add(CurObject);
		}

		ID=id+"_tab_3";
		this.TextBoxTable=new Table(ID,2,2);

		for (int index=0;index<2;index++){
			ID=id+"_select_"+index;
			CurObject=new ListBox(ID);
			this.ArrayListListBoxs.add(CurObject);
		}

		ComponentUtil util=ComponentUtil.getInstance();
		String imageSrc=null;
		String imageSrc_1=null;
		for (int index=0;index<6;index++){
			if (index<4)
				ID=id+"_movebtn_"+index;
			else
				ID=id+"_orderbtn_"+(index-4);
			CurObject=new Image(ID);
			this.ArrayListBtns.add(CurObject);
			if (index==0){
				imageSrc=util.getImagesRequestPath()+"/"+"adddisabled.gif";
				imageSrc_1=util.getImagesRequestPath()+"/"+"addenabled.gif";
			}
			if (index==1){
				imageSrc=util.getImagesRequestPath()+"/"+"addallenabled.gif";
				imageSrc_1=util.getImagesRequestPath()+"/"+"addalldisabled.gif";
			}
			if (index==2){
				imageSrc=util.getImagesRequestPath()+"/"+"removedisabled.gif";
				imageSrc_1=util.getImagesRequestPath()+"/"+"removeenabled.gif";
			}
			if (index==3){
				imageSrc=util.getImagesRequestPath()+"/"+"removeallenabled.gif";
				imageSrc_1=util.getImagesRequestPath()+"/"+"removealldisabled.gif";
			}
			if (index==4){
				imageSrc=util.getImagesRequestPath()+"/"+"updisabled.gif";
				imageSrc_1=util.getImagesRequestPath()+"/"+"upenabled.gif";
			}
			if (index==5){
				imageSrc=util.getImagesRequestPath()+"/"+"downdisabled.gif";
				imageSrc_1=util.getImagesRequestPath()+"/"+"downenabled.gif";
			}
			this.ArrayListBtnsSrc.add(imageSrc);
			this.ArrayListBtnsSrc.add(imageSrc_1);
		}

		ID=id+"_txtbox";
		OfbizTextBox ofbiztxt=new OfbizTextBox(ID);
		//���ÿ��
		ofbiztxt.setMaxLength(6);
		//ofbiztxt.setIsConvertZero(true);
		ofbiztxt.setSignNum(5);
		ofbiztxt.setDecimalDigits(0);
		ofbiztxt.setIsPositive(true);	
		ofbiztxt.setOnChangeEvent("changeWidthSave(this,'"+this.componentID+"');");
		this.TxtBox=ofbiztxt;
		this.widthHidden=new HashMapManager(id+"_widthset");
		
		for (int index=0;index<8;index++){
			ID=id+"_hidden_" +index;
			if (index<4){
				CurObject=new HashMapManager(ID);
			}
			else{
				CurObject=new Hidden(ID);
			}
			this.ArrayListHidden.add(CurObject);
		}

		ID=id+"_hidden_8";
		this.FieldsHidden=new Hidden(ID);

		this.EventParameters="document.getElementById('" +id+"_select_0"+"')," +
							"document.getElementById('" +id+"_select_1"+"')," +
							"document.getElementById('" +id+"_movebtn_0"+"')," +
							"document.getElementById('" +id+"_movebtn_1"+"')," +
							"document.getElementById('" +id+"_movebtn_2"+"')," +
							"document.getElementById('" +id+"_movebtn_3"+"')," +
							"document.getElementById('" +id+"_orderbtn_0"+"')," +
							"document.getElementById('" +id+"_orderbtn_1"+"')," +
							"document.getElementById('" +id+"_div_2"+"')," +
							"document.getElementById('" +id+"_txtbox"+"')," +
							"document.getElementById('" +id+"_hidden_5"+"')," +
							"document.getElementById('" +id+"_hidden_0"+"')," +
							"document.getElementById('" +id+"_hidden_1"+"')," +
							"document.getElementById('" +id+"_hidden_2"+"')," +
							"document.getElementById('" +id+"_hidden_3"+"')," +
							"document.getElementById('" +id+"_hidden_4"+"')," +
							"document.getElementById('" +id+"_hidden_6"+"')," +
							"document.getElementById('" +id+"_hidden_7"+"')";
	}
	/**
	 * ����ListBox��CssClass<br>
	 * @param strListBoxClass ListBox��CssClass<br>
	 * @return void<br>
	 */
	public void setListBoxClass(String strListBoxClass){
		this.strListBoxClass = strListBoxClass;
	}
	/**
	 * ����MoveButton��CssClass<br>
	 * @param strMoveBtnClass MoveButton��CssClass<br>
	 * @return void<br>
	 */
	public void setMoveBtnClass(String strMoveBtnClass){
		this.strMoveBtnClass =strMoveBtnClass;
	}
	/**
	 * ����OrderButton��CssClass<br>
	 * @param strOrderBtnClass OrderButton��CssClass<br>
	 * @return void<br>
	 */
	public void setOrderBtnClass(String strOrderBtnClass){
		this.strOrderBtnClass=strOrderBtnClass;
	}
	/**
	 * ����TextBox��CssClass<br>
	 * @param strTextBoxClass TextBox��CssClass<br>
	 * @return void<br>
	 */
	public void setTextBoxClass(String strTextBoxClass){
		this.strTextBoxClass=strTextBoxClass;
	}
	/**
	 * ����ListBox��Label��CssClass<br>
	 * @param strListLabClass ListBox��Label��CssClass<br>
	 * @return void<br>
	 */
	public void setListLabClass(String strListLabClass){
		this.strListLabClass=strListLabClass;
	}
	/**
	 * ����TextBox��Label��CssClass<br>
	 * @param strTextBoxLabClass TextBox��Label��CssClass<br>
	 * @return void<br>
	 */
	public void setTextBoxLabClass(String strTextBoxLabClass){
		this.strTextBoxLabClass=strTextBoxLabClass;
	}
	/**
	 * ����TextBox��Label����ѡ��ListBox֮���ˮƽ����<br>
	 * @param intspace TextBox��Label����ѡ��ListBox֮���ˮƽ����<br>
	 * @return void<br>
	 */
	public void setTextLabSelListSpace_H(int intsapce){
		this.intTextLabSelListSpace_H=intsapce;
	}
	/**
	 * ȡTextBox��Label����ѡ��ListBox֮���ˮƽ����<br>
	 * @return int TextBox��Label����ѡ��ListBox֮���ˮƽ����<br>
	 */
	public int getTextLabSelListSpace_H(){
		return this.intTextLabSelListSpace_H;
	}
	/**
	 * ����TextBox��Label����ѡ��ListBox֮��Ĵ�ֱ����<br>
	 * @param intspace TextBox��Label����ѡ��ListBox֮��Ĵ�ֱ����<br>
	 * @return void<br>
	 */
	public void setTextLabSelListSpace_V(int intspace){
		this.intTextLabSelListSpace_V=intspace;
	}
	/**
	 * ȡTextBox��Label����ѡ��ListBox֮��Ĵ�ֱ����<br>
	 * @return int TextBox��Label����ѡ��ListBox֮��Ĵ�ֱ����<br>
	 */
	public int getTextLabSelListSpace_V(){
		return this.intTextLabSelListSpace_V;
	}
	/**
	 * ����δѡ��Ŀ����ݼ�<br>
	 * @param SourceItems ��Value+"!"+Text��ɵ�String����
	 * ��ɵ�ArrayList<br>
	 * @return void <br>
	 */
	public void setSourceItems(List SourceItems){
		this.SourceItems=(ArrayList)SourceItems;
	}
	/**
	 * ȡδѡ��Ŀ����ݼ�<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public ArrayList getSourceItems(){
		return this.SourceItems;
	}
	/**
	 * ������ѡ��Ŀ����ݼ�<br>
	 * @param SelectedItems ��Value+"!"+Text��ɵ�String����
	 * ��ɵ�ArrayList<br>
	 * @return void <br>
	 */
	public void setSelectedItems(List SelectedItems){
		this.SelectedItems=(ArrayList)SelectedItems;
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public ArrayList getSelectedItems(){
		return this.SelectedItems;
	}
	/**
	 * ���ñ�ѡ��Ŀ����ݼ�<br>
	 * @param NecessaryItems ��Value+"!"+Text��ɵ�String����
	 * ��ɵ�ArrayList<br>
	 * @return void <br>
	 */
	public void setNecessaryItems(List NecessaryItems){
		this.NecessaryItems=(ArrayList)NecessaryItems;
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public ArrayList getNecessaryItems(){
		return this.NecessaryItems;
	}
	/**
	 * ���ù̶���Ŀ����ݼ�<br>
	 * @param FixItems ��Value+"!"+Text��ɵ�String����
	 * ��ɵ�ArrayList<br>
	 * @return void <br>
	 */
	public void setFixItems(List FixItems){
		this.FixItems=(ArrayList)FixItems;
	}
	/**
	 * ȡ�̶���Ŀ����ݼ�<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public ArrayList getFixItem(){
		return this.FixItems;
	}
	/**
	 * ���ù̶���Ŀ�Ƿ�ֻ��<br>
	 * @param blnFixItemReadOnly true-ֻ����false-��ֻ��<br>
	 * @return void <br>
	 */
	public void setFixItemReadOnly(boolean blnFixItemReadOnly){
		this.blnFixItemReadOnly=blnFixItemReadOnly;
	}
	/**
	 * ���ñ�ѡ��Ŀ�Ƿ�ֻ��<br>
	 * @param blnNecessaryReadOnly true-ֻ����false-��ֻ��<br>
	 * @return void <br>
	 */
	public void setNecessaryReadOnly(boolean blnNecessaryReadOnly){
		this.blnNecessaryReadOnly=blnNecessaryReadOnly;
	}
	/**
	 * ���������ListBox�Ŀ��<br>
	 * @param intListBoxWidth �����ListBox�Ŀ��<br>
	 * @return void<br>
	 */
	public void setListBoxWidth(int intListBoxWidth){
		this.intListBoxWidth=intListBoxWidth;
	}
	/**
	 * ���������ListBox�ĸ߶�<br>
	 * @param intListBoxHeight �����ListBox�ĸ߶�<br>
	 * @return void<br>
	 */
	public void setListBoxHeight(int intListBoxHeight){
		this.intListBoxHeight=intListBoxHeight;
	}
	/**
	 * ���������TextBox�Ŀ��<br>
	 * @param intTextBoxWidth �����TextBox�Ŀ��<br>
	 * @return void<br>
	 */
	public void setTextBoxWidth(int intTextBoxWidth){
		this.intTextBoxWidth=intTextBoxWidth;
	}
	/**
	 * ���������TextBox�Ƿ�ɼ�<br>
	 * @param visible true-�ɼ�false-���ɼ�<br>
	 * @return void<br>
	 */
	public void setTextBoxVisible(boolean visible){
		this.blntxtisvisiblue=visible;
	}
	/**
	 * ���������MoveButton�Ŀ��<br>
	 * @param intMoveBtnWidth �����MoveButton�Ŀ��<br>
	 * @return void<br>
	 */
	public void setMoveBtnWidth(int intMoveBtnWidth){
		this.intMoveBtnWidth=intMoveBtnWidth;
	}
	/**
	 * ���������MoveButton�ĸ߶�<br>
	 * @param intMoveBtnHeight �����MoveButton�ĸ߶�<br>
	 * @return void<br>
	 */
	public void setMoveBtnHeight(int intMoveBtnHeight){
		this.intMoveBtnHeight =intMoveBtnHeight;
	}
	/**
	 * ���������MoveButton��ListBox֮��ľ���<br>
	 * @param intMoveBtnListBoxSpace �����MoveButton��ListBox֮��ľ���<br>
	 * @return void<br>
	 */
	public void setMoveBtnListBoxSpace(int intMoveBtnListBoxSpace){
		this.intMoveBtnListBoxSpace=intMoveBtnListBoxSpace;
	}
	/**
	 * ���������MoveButtons֮��ľ���<br>
	 * @param intMoveBtnsSpaceHeight �����MoveButtons֮��ľ���<br>
	 * @return void<br>
	 */
	public void setMoveBtnsSpace(int intMoveBtnsSpaceHeight){
		this.intMoveBtnsSpaceHeight=intMoveBtnsSpaceHeight;
	}
	/**
	 * �������������Button��ListBox֮��ľ���<br>
	 * @param intOrderBtnListBoxSpace ���������Button��ListBox֮��ľ���<br>
	 * @return void<br>
	 */
	public void setOrderBtnListBoxSpace(int intOrderBtnListBoxSpace){
		this.intOrderBtnListBoxSpace=intOrderBtnListBoxSpace;
	}
	/**
	 * �������������Button֮��ľ���<br>
	 * @param intOrderBtnsSpace ���������Button֮��ľ���<br>
	 * @return void<br>
	 */
	public void setOrderBtnsSpace(int intOrderBtnsSpace){
		this.intOrderBtnsSpace=intOrderBtnsSpace;
	}
	/**
	 * �������������Button�Ŀ��<br>
	 * @param intOrderBtnWidth ���������Button�Ŀ��<br>
	 * @return void<br>
	 */
	public void setOrderBtnWidth(int intOrderBtnWidth){
		this.intOrderBtnWidth=intOrderBtnWidth;
	}
	/**
	 * �������������Button�ĸ߶�<br>
	 * @param intOrderBtnHeight ���������Button�ĸ߶�<br>
	 * @return void<br>
	 */
	public void setOrderBtnHeight(int intOrderBtnHeight){
		this.intOrderBtnHeight=intOrderBtnHeight;
	}
	/**
	 * ���������Label�ĸ߶�<br>
	 * @param intLabelHeight ���������Label�ĸ߶�<br>
	 * @return void<br>
	 */
	public void setLabelHeight(int intLabelHeight){
		this.intLabelHeight=intLabelHeight;
	}
	/**
	 * ������Ŀ�����е�����ѡ��ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 * @param strItemSelected ��Ŀ�����е�����ѡ��ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 * @return void<br>
	 */
	public void setItemSelected(String strItemSelected){
		this.strsetItemSelected=strItemSelected;
	}
	/**
	 * ȡ��Ŀ�����е�����ѡ��ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 * @return String ��Ŀ�����е�����ѡ��ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 */
	public String getItemSelected(){
		return this.strsetItemSelected;
	}
	/**
	 * ���õ���Ŀ�ı�ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 * @param strItemChanged ����Ŀ�ı�ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 * @return void<br>
	 */
	public void setItemChanged(String strItemChanged){
		this.strsetItemChanged=strItemChanged;
	}
	/**
	 * ȡ����Ŀ�ı�ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 * @return String ����Ŀ�ı�ʱ��
	 * �����Ŀͻ��˵�JavaScript�ĺ�����<br>
	 */
	public String getItemChanged(){
		return this.strsetItemChanged;
	}
	/**
	 * ������в��������<br>
	 * @param id ���ҵ������ID<br>
	 * @return IComponent IComponent����<br>
	 * @see IComponent<br>
	 */
	public IComponent findComponent(String id){
		return super.findComponent(id);
	}
	/**
	 * ȡ������Ƿ��������<br>
	 * @return boolean true-��;false-û��<br>
	 */
	public boolean hasComponent(){
		return super.hasComponent();
	}
	/**
	 * ��������Button��Src<br>
	 * @param BtnsSrc BtnsSrcָ��ArrayList��<br>
	 * BtnsSrc�д�ţ�ÿ��Button��Src��
	 * BtnsSrc���±���Button�Ķ�Ӧ��ϵ��
	 * 0-��ѡһ����1-��ѡ���У�2-��ѡһ����
	 * 3-��ѡ���У�4-���ƣ�5-����<br>
	 * @return void
	 */
	public void setBtnsSrc(List BtnsSrc){
		this.ArrayListBtnsSrc=(ArrayList)BtnsSrc;
	}
	/**
	 * ȡ����Button��Src<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�ÿ��Button��Src��
	 * ArrayList���±���Button�Ķ�Ӧ��ϵ��
	 * 0-��ѡһ����1-��ѡ���У�2-��ѡһ����
	 * 3-��ѡ���У�4-���ƣ�5-����<br>
	 */
	public ArrayList getBtnsSrc(){
		return this.ArrayListBtnsSrc;
	}
	/**
	 * ȡ���������HtmlԪ�صĸ���<br>
	 * @return int ���������HtmlԪ�صĸ���<br>
	 */
	public int getControlsNums(){
		return this.ControlsNums;
	}
	/**
	 * ������������TabIndex<br>
	 * @param StartTabIndex StartTabIndex��ֵ��Ҫ���
	 * ����getControlsNums�����á�
	 * �����������а���HtmlԪ�أ�
	 * �ڲ�����HtmlԪ�ص�TabIndex�����StartTabIndex�Զ���������<br>
	 * @see getControlsNums
	 * @return void<br>
	 */
	public void setStartTabIndex(int StartTabIndex){
		this.StartTabIndex=StartTabIndex;
	}
	/**
	 * �����������Ƿ����<br>
	 * @param IsEnable true-���ã�false-������<br>
	 * @return void<br>
	 */
	public void setComIsEnable(boolean IsEnable){
		this.blnComIsEnable=IsEnable;
	}
	/**
	 * ȡ�������Ƿ����<br>
	 * @return boolean true-���ã�false-������<br>
	 */
	public boolean getComIsEnable(){
		return this.blnComIsEnable;
	}
	protected String getElementHtml(){
		Table CurTable=null;
		CurTable=(Table)this.ArrayListTable.get(0);
		StringBuffer strTempInnerHtml=new StringBuffer();
		this.setTableAttribute();
		strTempInnerHtml.append(CurTable.getInnerHtml());

		if (this.blntxtisvisiblue){
			this.SetTextBoxTable();
			CurTable=this.TextBoxTable;
			strTempInnerHtml.append(CurTable.getInnerHtml());
		}
		return strTempInnerHtml.toString();
	}
	protected String getScriptHtml(){
		return super.getScriptHtml() + ColumnSetJS.getColumnSetJS();
	}
	private void setDivInnerAttribute(){
		HtmlDiv CurLabel=null;
		for (int index=0;index<this.ArrayListLabels.size();index++){
			CurLabel=(HtmlDiv)this.ArrayListLabels.get(index);
			if (index!=2){
				if((this.strListLabClass!=null)&&(!this.strListLabClass.equals("")))
					CurLabel.addAttribute("class",this.strListLabClass);
			}
			else{
				if((this.strTextBoxLabClass!=null)&&(!this.strTextBoxLabClass.equals("")))
					CurLabel.addAttribute("class",this.strTextBoxLabClass);
			}
			CurLabel.addAttribute("style",
								"height:"+String.valueOf(this.intLabelHeight));
			if (index==0){
				CurLabel.setText("��ѡ��Ŀ");
				CurLabel.addAttribute("tabindex",Integer.toString(this.StartTabIndex+0));
			}
			if (index==1){
				CurLabel.setText("��ѡ��Ŀ");
				CurLabel.addAttribute("tabindex",Integer.toString(this.StartTabIndex+4));
			}
			if (index==2){
				CurLabel.setText("���:");
				CurLabel.addAttribute("disabled","true");
				CurLabel.addAttribute("tabindex",Integer.toString(this.StartTabIndex+10));
			}
		}
	}
	private void SetTextBoxTable(){
		HtmlDiv CurDiv=null;
		int intWidth=0;
		int intHeight=0;
		CurDiv=(HtmlDiv)this.ArrayListLabels.get(2);

		this.setTxtBoxAttribute();
		this.TextBoxTable.setBorder(0);
		intHeight=this.intTextLabSelListSpace_V;
		this.TextBoxTable.getRow(0).setHeight(Integer.toString(intHeight));
		intWidth=this.intListBoxWidth+this.intMoveBtnWidth
				+this.intMoveBtnListBoxSpace*2
				-this.intTextLabSelListSpace_H;
		this.TextBoxTable.getRow(1).getTableCell(0).setWidth(Integer.toString(intWidth));
		intWidth=this.intListBoxWidth
						+this.intOrderBtnWidth
						+this.intOrderBtnListBoxSpace;
		this.TextBoxTable.getRow(1).getTableCell(1).setWidth(Integer.toString(intWidth));
		this.TextBoxTable.getRow(1).getTableCell(0).addAttribute("align","right");
		if (!this.blnComIsEnable)
			CurDiv.addAttribute("disabled","true");
		this.TextBoxTable.getRow(1).getTableCell(0).addComponent(CurDiv);
		if (!this.blnComIsEnable)
			this.TxtBox.addAttribute("disabled","true");
		this.TextBoxTable.getRow(1).getTableCell(1).addComponent(this.TxtBox);
		this.TextBoxTable.getRow(1).getTableCell(1).addComponent(this.widthHidden);
	}
	private void setListBoxAttribute(){
		Option CurOption=null;
		ListBox CurListBox=null;
		String Value=null;
		String EventFunction=null;
		int intStart=0;
		int intEnd=-1;
		String strValue=null;
		String strText=null;

		for (int indexoflistbox=0;indexoflistbox<this.ArrayListListBoxs.size();indexoflistbox++){
			CurListBox=(ListBox)this.ArrayListListBoxs.get(indexoflistbox);
			if ((this.strListBoxClass!=null)&&(!this.strListBoxClass.equals("")))
				CurListBox.addAttribute("class",this.strListBoxClass);
			CurListBox.addAttribute("size","4");
			CurListBox.addAttribute("style","height:"
						+this.intListBoxHeight
						+"; "
						+"width:"
						+this.intListBoxWidth);
			if (indexoflistbox==0){
				CurListBox.addAttribute("tabindex",Integer.toString(this.StartTabIndex+1));
				EventFunction="optiondblClick";
				CurListBox.addAttribute("ondblclick",
										EventFunction+"(" +this.EventParameters+")");
				EventFunction="optionclick";
				CurListBox.addAttribute("onclick",
										EventFunction+"(" +this.EventParameters+")");
				EventFunction="ListBoxKeyPress";
				CurListBox.addAttribute("onkeypress",
										EventFunction+"(" +this.EventParameters+")");
				for (int index=0;index<this.SourceItems.size();index++){
					Value=this.SourceItems.get(index).toString();
					if ((Value==null)||(Value.trim().equals("")))
						continue;
					intEnd=Value.indexOf("!");
					if (intEnd==-1)
						continue;
					strValue=Value.substring(intStart,intEnd);
					strText=Value.substring(intEnd+1);
					//�����"��ѡ��Ŀ"�������"��ѡ��Ŀ"
					if (this.isSelectedItem(strText))
						continue;
					CurOption=new Option();
					CurOption.setValue(strValue);
					CurOption.setText(strText);

					//"δѡ��Ŀ"�����ǹ̶���ͱ�ѡ������
					/*if (this.isFixItem(strText)){
						if (this.blnFixItemReadOnly)
							CurOption.addAttribute("onlyread","true");
						else
							CurOption.addAttribute("onlyread","false");
						CurOption.addAttribute("flag","111");
					}else{
						if (this.isNecessary(strText)){
							if (this.blnNecessaryReadOnly)
								CurOption.addAttribute("onlyread","true");
							else
								CurOption.addAttribute("onlyread","false");
							CurOption.addAttribute("flag","222");
						}
						else{*/
							CurOption.addAttribute("flag","999");
							CurOption.addAttribute("onlyread","false");
						/*}
					}*/
					CurListBox.addComponent(CurOption);
					((HashMapManager)this.ArrayListHidden.get(0)).put(strValue,strText);
				}
			}
			if (indexoflistbox==1){
				CurListBox.addAttribute("tabindex",Integer.toString(this.StartTabIndex+5));
				EventFunction="selectedoptiondbclick";
				CurListBox.addAttribute("ondblclick",
										EventFunction+"(" +this.EventParameters+")");
				EventFunction="selectedoptionclick";
				CurListBox.addAttribute("onclick",
										EventFunction+"(" +this.EventParameters+")");
				EventFunction="ListBoxKeyPress";
				CurListBox.addAttribute("onkeypress",
										EventFunction+"(" +this.EventParameters+")");
				//�ȼ��ع̶���Ŀ
				for (int index=0;index<this.SelectedItems.size();index++){
					Value=this.SelectedItems.get(index).toString();
					if ((Value==null)||(Value.trim().equals("")))
						continue;
					intEnd=Value.indexOf("!");
					if (intEnd==-1)
						continue;
					strValue=Value.substring(intStart,intEnd);
					strText=Value.substring(intEnd+1);
					if (this.isFixItem(strText)){
						CurOption=new Option();
						CurOption.setValue(strValue);
						CurOption.setText(strText);
						if (this.blnFixItemReadOnly)
							CurOption.addAttribute("onlyread","true");
						else
							CurOption.addAttribute("onlyread","false");
						CurOption.addAttribute("flag","111");
						CurListBox.addComponent(CurOption);
						((HashMapManager)this.ArrayListHidden.get(3)).put(strValue,strText);
						((HashMapManager)this.ArrayListHidden.get(1)).put(strValue,strText);
					}
				}
				//����طǹ̶���Ŀ
				for (int index=0;index<this.SelectedItems.size();index++){
					Value=this.SelectedItems.get(index).toString();
					if ((Value==null)||(Value.trim().equals("")))
						continue;
					intEnd=Value.indexOf("!");
					if (intEnd==-1)
						continue;
					strValue=Value.substring(intStart,intEnd);
					strText=Value.substring(intEnd+1);
					if (!this.isFixItem(strText)){
						CurOption=new Option();
						CurOption.setValue(strValue);
						CurOption.setText(strText);
						if (this.isNecessary(strText)){
							if (this.blnNecessaryReadOnly)
								CurOption.addAttribute("onlyread","true");
							else
								CurOption.addAttribute("onlyread","false");
							CurOption.addAttribute("flag","222");
							((HashMapManager)this.ArrayListHidden.get(2)).put(strValue,strText);
						}
						else{
							CurOption.addAttribute("flag","999");
							CurOption.addAttribute("onlyread","false");
						}
					CurListBox.addComponent(CurOption);
					((HashMapManager)this.ArrayListHidden.get(1)).put(strValue,strText);
					}
				}
			}
		}
	}

	private void setBtnAttribute(){
		Image CurImage=null;
		String EventFunction=null;
		String CurSrc=null;
		int srcindex=0;
		for (int index=0;index<this.ArrayListBtns.size();index++){
			CurImage=(Image)this.ArrayListBtns.get(index);
			srcindex=index*2;
			if (((index==1)&&(this.SourceItems.size()<=0))||((index==3)&&(this.SelectedItems.size()<=0)))
				srcindex+=1;
			CurSrc=(String)this.ArrayListBtnsSrc.get(srcindex);
			if ((CurSrc!=null)&&(!CurSrc.equals("")))
				CurImage.addAttribute("src",CurSrc);
			if (index<4){
				if ((this.strMoveBtnClass!=null)&&(!this.strMoveBtnClass.equals("")))
					CurImage.addAttribute("class",this.strMoveBtnClass);
				CurImage.addAttribute("style","height:"
											+Integer.toString(this.intMoveBtnHeight)
											+";"
											+"width:"
											+Integer.toString(this.intMoveBtnWidth));
				if (index==0){
					CurImage.addAttribute("disabled","true");
					CurImage.addAttribute("tabindex",Integer.toString(this.StartTabIndex+2));
					EventFunction="AddOneBtnClick";
					CurImage.addAttribute("onclick",
											EventFunction+"(" +this.EventParameters+")");
					EventFunction="AddOneBtnKeyPress";
					CurImage.addAttribute("onkeypress",
											EventFunction+"(" +this.EventParameters+")");
				}
				if (index==1){
					if (this.SourceItems.size()<=0)
						CurImage.addAttribute("disabled","true");
					CurImage.addAttribute("tabindex",Integer.toString(this.StartTabIndex+3));
					EventFunction="AddAllBtnClicked";
					CurImage.addAttribute("onclick",
											EventFunction+"(" +this.EventParameters+")");
					EventFunction="AddAllBtnKeyPress";
					CurImage.addAttribute("onkeypress",
											EventFunction+"(" +this.EventParameters+")");
				}
				if (index==2){
					CurImage.addAttribute("disabled","true");
					CurImage.addAttribute("tabindex",Integer.toString(this.StartTabIndex+6));
					EventFunction="RemoveOneBtnClick";
					CurImage.addAttribute("onclick",
											EventFunction+"(" +this.EventParameters+")");
					EventFunction="RemoveOneBtnKeyPress";
					CurImage.addAttribute("onkeypress",
											EventFunction+"(" +this.EventParameters+")");
				}
				if (index==3){
					if (this.SelectedItems.size()<=0)
						CurImage.addAttribute("disabled","true");
					CurImage.addAttribute("tabindex",Integer.toString(this.StartTabIndex+7));
					EventFunction="RemoveAllBtnClick";
					CurImage.addAttribute("onclick",
											EventFunction+"(" +this.EventParameters+")");
					EventFunction="RemoveAllBtnKeyPress";
					CurImage.addAttribute("onkeypress",
											EventFunction+"(" +this.EventParameters+")");
				}
			}
			else{
				if ((this.strOrderBtnClass!=null)&&(!this.strOrderBtnClass.equals("")))
					CurImage.addAttribute("class",this.strOrderBtnClass);
				CurImage.addAttribute("style","height:"
												+Integer.toString(this.intOrderBtnHeight)
												+";"
												+"width:"
												+Integer.toString(this.intOrderBtnWidth));
				CurImage.addAttribute("disabled","true");
				if (index==4){
					CurImage.addAttribute("tabindex",Integer.toString(this.StartTabIndex+8));
					EventFunction="PreviousBtnClick";
					CurImage.addAttribute("onclick",
											EventFunction+"(" +this.EventParameters+")");
					EventFunction="PreviousBtnKeyPress";
					CurImage.addAttribute("onkeypress",
											EventFunction+"(" +this.EventParameters+")");
				}
				if (index==5){
					CurImage.addAttribute("tabindex",Integer.toString(this.StartTabIndex+9));
					EventFunction="NextBtnClick";
					CurImage.addAttribute("onclick",
											EventFunction+"(" +this.EventParameters+")");
					EventFunction="NextBtnKeyPress";
					CurImage.addAttribute("onkeypress",
											EventFunction+"(" +this.EventParameters+")");
				}
			}
			if (!this.blnComIsEnable)
				CurImage.addAttribute("disabled","true");
		}
	}
	private void setBtnTab(){
		this.setBtnAttribute();
		Image CurImage=null;
		int indexBtn=0;
		int intWidth=0;
		Table CurTable=null;
		Table OutTable=(Table)this.ArrayListTable.get(0);
		for (int index=1;index<this.ArrayListTable.size();index++){
			CurTable=(Table)this.ArrayListTable.get(index);
			CurTable.setBorder(0);
			if (index==1){
				CurTable.getColumn(0).setWidth(Integer.toString(this.intMoveBtnListBoxSpace));
				CurTable.getColumn(2).setWidth(Integer.toString(this.intMoveBtnListBoxSpace));
				for (int index_1=1;index_1<8;index_1++){
					if (index_1%2==0)
						CurTable.getRow(index_1).setHeight(Integer.toString(this.intMoveBtnsSpaceHeight));
					else{
						CurImage=(Image)this.ArrayListBtns.get(indexBtn);
						CurTable.getRow(index_1).getTableCell(1).addComponent(CurImage);
						indexBtn++;
					}
				}
			}
			if (index==2){
				CurTable.getColumn(0).setWidth(Integer.toString(this.intOrderBtnListBoxSpace));
				for (int index_1=1;index_1<4;index_1++){
					if (index_1%2==0)
						CurTable.getRow(index_1).setHeight(Integer.toString(this.intOrderBtnsSpace));
					else{
						CurImage=(Image)this.ArrayListBtns.get(indexBtn);
						CurTable.getRow(index_1).getTableCell(1).addComponent(CurImage);
						indexBtn++;
					}
				}
			}
		}
	}
	private void setTxtBoxAttribute(){
		String EventFunction=null;
		TextBox CurTxtBox=null;
		CurTxtBox=this.TxtBox;
		if ((this.strTextBoxClass!=null)&&(!this.strTextBoxClass.equals("")))
			CurTxtBox.addAttribute("class",this.strTextBoxClass);
		CurTxtBox.addAttribute("disabled","true");
		CurTxtBox.addAttribute("tabindex",Integer.toString(this.StartTabIndex+11));
		CurTxtBox.addAttribute("style","width:"+this.intTextBoxWidth);
		//EventFunction="SetListBoxContent";
		//CurTxtBox.addAttribute("onchange",EventFunction+"(" +this.EventParameters+",'true')");
		//EventFunction="SetResult";
		//CurTxtBox.addAttribute("onblur",EventFunction+"(" +this.EventParameters+")");
		//EventFunction="TextBoxInput";
		//CurTxtBox.addAttribute("onkeydown",EventFunction+"(" +this.EventParameters+ ")");
	}
	private void setHiddenAttribute(){
		Hidden CurHidden=null;
		String strValue=null;
		String strReturn=null;
		for(int index=0;index<this.ArrayListHidden.size();index++){
			if (index>=4){
				CurHidden=(Hidden)this.ArrayListHidden.get(index);
			}
			else{
				CurHidden=(HashMapManager)this.ArrayListHidden.get(index);
			}
			//System.out.println(CurHidden.getInnerHtml());
			if (index==4){
				CurHidden.addAttribute("value0","�̶�����ƶ���");
				CurHidden.addAttribute("value1","�̶�����ƶ���");
				CurHidden.addAttribute("value2","��ѡ����ƶ���");
				CurHidden.addAttribute("value3","��ʾ��Ʋ���Ϊ�գ�");
				CurHidden.addAttribute("value4","��ʾ����ظ���");
				CurHidden.addAttribute("value5","��ʾ��Ʋ���Ϊ�����ַ�:");
			}
			if (index==6){
				if ((this.strsetItemSelected!=null)&&(!this.strsetItemSelected.equals("")))
					CurHidden.addAttribute("ItemSelected",this.strsetItemSelected);
			}
			if (index==7){
				if ((this.strsetItemChanged!=null)&&(!this.strsetItemChanged.equals("")))
					CurHidden.addAttribute("ItemChanged",this.strsetItemChanged);
			}
		}

		//�ֶ���Ƶ�Hidden
		if (this.blnIsSetHidden){
			this.FieldsHidden.setText("");
			for (int index_1=0;index_1<4;index_1++){
				strValue=this.FieldsString[index_1][0]+"`"+this.FieldsString[index_1][1];
				strReturn=this.FieldsHidden.getText();
				if ((strReturn==null)||(strReturn.equals("")))
					this.FieldsHidden.setText(strValue);
				else{
					this.FieldsHidden.setText(strReturn+"~"+strValue);
				}
			}
		}
	}
	private void setTableAttribute(){
		HtmlDiv CurLabel=null;
		ListBox CurListBox=null;
		Table CurTable=null;
		Table SubTable=null;
		Hidden CurHidden=null;
		int intWidth=0;

		CurTable=(Table)this.ArrayListTable.get(0);
		CurTable.setBorder(0);
		CurTable.setAttribute("cellpadding","0");
		CurTable.setAttribute("cellspacing","0");

		this.setDivInnerAttribute();
		CurLabel=(HtmlDiv)this.ArrayListLabels.get(0);
		if (!this.blnComIsEnable)
			CurLabel.addAttribute("disabled","true");
		CurTable.getRow(0).getTableCell(0).addComponent(CurLabel);
		CurLabel=(HtmlDiv)this.ArrayListLabels.get(1);
		if (!this.blnComIsEnable)
			CurLabel.addAttribute("disabled","true");
		CurTable.getRow(0).getTableCell(2).addComponent(CurLabel);

		this.setListBoxAttribute();
		CurListBox=(ListBox)this.ArrayListListBoxs.get(0);
		if (!this.blnComIsEnable)
			CurListBox.addAttribute("disabled","true");
		CurTable.getRow(1).getTableCell(0).addComponent(CurListBox);
		CurListBox=(ListBox)this.ArrayListListBoxs.get(1);
		if (!this.blnComIsEnable)
			CurListBox.addAttribute("disabled","true");
		CurTable.getRow(1).getTableCell(2).addComponent(CurListBox);

		this.setBtnTab();
		SubTable=(Table)this.ArrayListTable.get(1);
		CurTable.getRow(1).getTableCell(1).addComponent(SubTable);
		SubTable=(Table)this.ArrayListTable.get(2);
		CurTable.getRow(1).getTableCell(3).addComponent(SubTable);

		this.setHiddenAttribute();
		CurHidden=(Hidden)this.FieldsHidden;
		CurTable.getRow(0).getTableCell(1).addComponent(CurHidden);
		for (int index=0;index<this.ArrayListHidden.size();index++){
			if (index<4){
				CurHidden=(HashMapManager)this.ArrayListHidden.get(index);
			}
			else{
				CurHidden=(Hidden)this.ArrayListHidden.get(index);
			}
			CurTable.getRow(0).getTableCell(1).addComponent(CurHidden);
		}

	}
	private boolean isSelectedItem(String Item){
		int index_1=-1;
		String strCurItem=null;
		String strCurText=null;
		for (int index=0;index<this.SelectedItems.size();index++){
			strCurItem=this.SelectedItems.get(index).toString();
			index_1=strCurItem.indexOf("!");
			if (index_1==-1)
				continue;
			strCurText=strCurItem.substring(index_1+1);
			if (Item.equals(strCurText))
				return true;
		}
		return false;
	}
	private boolean isFixItem(String Item){
		int index_1=-1;
		String strCurItem=null;
		String strCurText=null;
		for (int index=0;index<this.FixItems.size();index++){
			strCurItem=this.FixItems.get(index).toString();
			index_1=strCurItem.indexOf("!");
			if (index_1==-1)
				continue;
			strCurText=strCurItem.substring(index_1+1);
			if (Item.equals(strCurText))
				return true;
		}
		return false;
	}
	private boolean isNecessary(String Item){
		int index_1=-1;
		String strCurItem=null;
		String strCurText=null;
		for (int index=0;index<this.NecessaryItems.size();index++){
			strCurItem=this.NecessaryItems.get(index).toString();
			index_1=strCurItem.indexOf("!");
			if (index_1==-1)
				continue;
			strCurText=strCurItem.substring(index_1+1);
			if (Item.equals(strCurText))
				return true;
		}
		return false;
	}
	private static ArrayList parseHidden_1(String value){
		ArrayList result = new ArrayList();
		String[] values = StringUtils.split(value,"~");//value.split("~");
		for(int index = 0; index < values.length; index++){
	  		String keyAndValue = values[index];
	  		result.add(keyAndValue);
		}
		return result;
  	}
	private static ArrayList parsehiddens(String id,HttpServletRequest request,int intType){
		ArrayList Result=new ArrayList();
		ArrayList keys=new ArrayList();
		String curKey=null;
		HashMap hashmap=null;
		String ID=null;
		String Values=null;
		boolean blnT=false;

		if (intType==4){
			intType=8;
			blnT=true;
		}
		ID=id+"_hidden_"+intType;
		Values=request.getParameter(ID);
		if ((Values==null)||(Values.length()<=0)||(Values.equals("")))
			return Result;
		if (blnT){
			Result=parseHidden_1(Values);
			return Result;
		}
		else{
			hashmap=HashMapManager.parseHidden(Values);
		}
		keys=HashMapManager.getKeyList(ID,request);
		for (int index=0;index<keys.size();index++){
			curKey=keys.get(index).toString();
			for (Iterator i=hashmap.entrySet().iterator();i.hasNext();){
				Map.Entry e = (Map.Entry)i.next();
				if (e.getKey().toString().equals(curKey))
					Result.add(e.getKey().toString()+"!"+e.getValue().toString());
			}
		}
		return Result;
	}
	/**
	 * ȡδѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public static ArrayList getSourceResult(String id,HttpServletRequest request){
		return parsehiddens(id,request,0);
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public static ArrayList getSelectedResult(String id,HttpServletRequest request){
		return parsehiddens(id,request,1);
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public static ArrayList getNecessaryResult(String id,HttpServletRequest request){
		return parsehiddens(id,request,2);
	}
	/**
	 * ȡ�̶���Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�Value+"!"+Text��ɵ�String����<br>
	 */
	public static ArrayList getFixedResult(String id,HttpServletRequest request){
		return parsehiddens(id,request,3);
	}

	//����2(ͨ��List(�������Map)��Map��Key�ֶ�,���úͷ������)
	/**
	 * ����δѡ��Ŀ����ݼ�<br>
	 * @param mapList ��HashMap��ɵ�List��
	 * һ��HashMap����һ������
	 * һ��HashMap��Ӧһ����ݿ��еļ�¼<br>
	 * @param valueField  ���HashMap��Key�ֶ�<br>
	 * @param textField  ���HashMap��Key�ֶ�<br>
	 * @return void <br>
	 */
	public void setDataUnSelected(List mapList,String valueField,String textField){
		Map map=null;
		String value=null;
		String text=null;

		this.blnIsSetHidden=true;
		this.FieldsString[0][0]=valueField;
		this.FieldsString[0][1]=textField;

		for (int index=0;index<mapList.size();index++){
			map=(Map)mapList.get(index);
			value=map.get(valueField).toString();
			text=map.get(textField).toString();
			this.SourceItems.add(value+"!"+text);
		}
	}
	/**
	 * ȡδѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ���Value ��Text��ɵ�HashMap����<br>
	 */
	public static ArrayList getDataUnselected(String id,HttpServletRequest request){
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=null;

		ArrayList tempResults_1=null;
		String valueField=null;
		String textField=null;

		Map curMap=null;
		String curValue=null;
		String curText=null;
		String curContent=null;

		int index_1=-1;
		tempResults=parsehiddens(id,request,0);
		tempResults_1=parsehiddens(id,request,4);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);

			curContent=(String)tempResults_1.get(0);
			index_1=curContent.indexOf("`");
			if (index_1==-1)
				continue;
			valueField=curContent.substring(0,index_1);
			textField=curContent.substring(index_1+1);

			curMap=new HashMap();
			curMap.put(valueField,curValue);
			curMap.put(textField,curText);
			reSults.add(curMap);
		}
		return reSults;
	}
	/**
	 * ������ѡ��Ŀ����ݼ�<br>
	 * @param mapList ��HashMap��ɵ�List��
	 * һ��HashMap����һ������
	 * һ��HashMap��Ӧһ����ݿ��еļ�¼<br>
	 * @param valueField  ���HashMap��Key�ֶ�<br>
	 * @param textField  ���HashMap��Key�ֶ�<br>
	 * @return void <br>
	 */
	public void setDataSelected(List mapList,String valueField,String textField){
		Map map=null;
		String value=null;
		String text=null;

		this.blnIsSetHidden=true;
		this.FieldsString[1][0]=valueField;
		this.FieldsString[1][1]=textField;

		for (int index=0;index<mapList.size();index++){
			map=(Map)mapList.get(index);
			value=map.get(valueField).toString();
			text=map.get(textField).toString();
			this.SelectedItems.add(value+"!"+text);
		}
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ���Value ��Text��ɵ�HashMap����<br>
	 */
	public static ArrayList getDataSelected(String id,HttpServletRequest request){
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=null;

		ArrayList tempResults_1=null;
		String valueField=null;
		String textField=null;

		Map curMap=null;
		String curValue=null;
		String curText=null;
		String curContent=null;

		int index_1=-1;
		tempResults=parsehiddens(id,request,1);
		tempResults_1=parsehiddens(id,request,4);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);

			curContent=(String)tempResults_1.get(1);
			index_1=curContent.indexOf("`");
			if (index_1==-1)
				continue;
			valueField=curContent.substring(0,index_1);
			textField=curContent.substring(index_1+1);

			curMap=new HashMap();
			curMap.put(valueField,curValue);
			curMap.put(textField,curText);
			reSults.add(curMap);
		}
		return reSults;
	}
	/**
	 * ���ñ�ѡ��Ŀ����ݼ�<br>
	 * @param mapList ��HashMap��ɵ�List��
	 * һ��HashMap����һ������
	 * һ��HashMap��Ӧһ����ݿ��еļ�¼<br>
	 * @param valueField  ���HashMap��Key�ֶ�<br>
	 * @param textField  ���HashMap��Key�ֶ�<br>
	 * @return void <br>
	 */
	public void setDataNecessary(List mapList,String valueField,String textField){
		Map map=null;
		String value=null;
		String text=null;

		this.blnIsSetHidden=true;
		this.FieldsString[2][0]=valueField;
		this.FieldsString[2][1]=textField;

		for (int index=0;index<mapList.size();index++){
			map=(Map)mapList.get(index);
			value=map.get(valueField).toString();
			text=map.get(textField).toString();
			this.NecessaryItems.add(value+"!"+text);
		}
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ���Value ��Text��ɵ�HashMap����<br>
	 */
	public static ArrayList getDataNecessary(String id,HttpServletRequest request){
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=null;

		ArrayList tempResults_1=null;
		String valueField=null;
		String textField=null;

		Map curMap=null;
		String curValue=null;
		String curText=null;
		String curContent=null;

		int index_1=-1;
		tempResults=parsehiddens(id,request,2);
		tempResults_1=parsehiddens(id,request,4);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);

			curContent=(String)tempResults_1.get(2);
			index_1=curContent.indexOf("`");
			if (index_1==-1)
				continue;
			valueField=curContent.substring(0,index_1);
			textField=curContent.substring(index_1+1);

			curMap=new HashMap();
			curMap.put(valueField,curValue);
			curMap.put(textField,curText);
			reSults.add(curMap);
		}
		return reSults;
	}
	/**
	 * ���ù̶���Ŀ����ݼ�<br>
	 * @param mapList ��HashMap��ɵ�List��
	 * һ��HashMap����һ������
	 * һ��HashMap��Ӧһ����ݿ��еļ�¼<br>
	 * @param valueField  ���HashMap��Key�ֶ�<br>
	 * @param textField  ���HashMap��Key�ֶ�<br>
	 * @return void <br>
	 */
	public void setDataFixed(List mapList,String valueField,String textField){
		Map map=null;
		String value=null;
		String text=null;

		this.blnIsSetHidden=true;
		this.FieldsString[3][0]=valueField;
		this.FieldsString[3][1]=textField;

		for (int index=0;index<mapList.size();index++){
			map=(Map)mapList.get(index);
			value=map.get(valueField).toString();
			text=map.get(textField).toString();
			this.FixItems.add(value+"!"+text);
		}
	}
	/**
	 * ȡ�̶���Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ���Value ��Text��ɵ�HashMap����<br>
	 */
	public static ArrayList getDataFixed(String id,HttpServletRequest request){
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=null;

		ArrayList tempResults_1=null;
		String valueField=null;
		String textField=null;

		Map curMap=null;
		String curValue=null;
		String curText=null;
		String curContent=null;

		int index_1=-1;
		tempResults=parsehiddens(id,request,3);
		tempResults_1=parsehiddens(id,request,4);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);

			curContent=(String)tempResults_1.get(3);
			index_1=curContent.indexOf("`");
			if (index_1==-1)
				continue;
			valueField=curContent.substring(0,index_1);
			textField=curContent.substring(index_1+1);

			curMap=new HashMap();
			curMap.put(valueField,curValue);
			curMap.put(textField,curText);
			reSults.add(curMap);
		}
		return reSults;
	}

	//	����3:ͨ��ColumnSetItem�ļ�������/ȡ�����
	/**
	 * ����δѡ��Ŀ����ݼ�<br>
	 * @param columnSetItems ��ColumnSetItem������ɵ�List<br>
	 * @see ColumnSetItem
	 * @return void <br>
	 */
	public void setDataUnSelectedCol(List columnItemsUnSel){
		ColumnSetItem curItem=null;
		String strValue=null;
		String strText=null;
		for (int index=0;index<columnItemsUnSel.size();index++){
			curItem=(ColumnSetItem)columnItemsUnSel.get(index);
			if (curItem==null)
				continue;
			strValue=curItem.getValue();
			strText=curItem.getText();
			if ((strValue.equals(""))||(strValue==null))
				strValue="unselected_"+index;
			if ((strText.equals(""))||(strText==null))
				continue;
			this.SourceItems.add(strValue+"!"+strText);
		}
	}
	/**
	 * ȡδѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�ColumnSetItem����<br>
	 * @see ColumnSetItem<br>
	 */
	public static ArrayList getDataUnSelectedCol(String id,HttpServletRequest request){
		ColumnSetItem curItem=null;
		String curValue=null;
		String curText=null;
		String curContent=null;
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=new ArrayList();

		int index_1=-1;
		tempResults=parsehiddens(id,request,0);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);
			curItem=new ColumnSetItem(curValue,curText);
			reSults.add(curItem);
		}
		return reSults;
	}
	/**
	 * ������ѡ��Ŀ����ݼ�<br>
	 * @param columnSetItems ��ColumnSetItem������ɵ�List<br>
	 * @see ColumnSetItem
	 * @return void <br>
	 */
	public void setDataSelectedCol(List columnSetItems){
		ColumnSetItem curItem=null;
		String strValue=null;
		String strText=null;
		for (int index=0;index<columnSetItems.size();index++){
			curItem=(ColumnSetItem)columnSetItems.get(index);
			if (curItem==null)
				continue;
			strValue=curItem.getValue();
			strText=curItem.getText();
			if ((strValue.equals(""))||(strValue==null))
				strValue="selected_"+index;
			if ((strText.equals(""))||(strText==null))
				continue;
			this.SelectedItems.add(strValue+"!"+strText);
		}
	}
	public void setWidthDataSource(Map widthColletion )
	{
		this.widthHidden.setHashMap(widthColletion);
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�ColumnSetItem����<br>
	 * @see ColumnSetItem<br>
	 */
	public static ArrayList getDataSelectedCol(String id,HttpServletRequest request){
		ColumnSetItem curItem=null;
		String curValue=null;
		String curText=null;
		String curContent=null;
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=new ArrayList();

		int index_1=-1;
		tempResults=parsehiddens(id,request,1);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);
			curItem=new ColumnSetItem(curValue,curText);
			reSults.add(curItem);
		}
		return reSults;
	}
	/**
	 * ���ñ�ѡ��Ŀ����ݼ�<br>
	 * @param columnSetItems ��ColumnSetItem������ɵ�List<br>
	 * @see ColumnSetItem
	 * @return void <br>
	 */
	public void setDataNecessaryCol(List columnSetItems){
		ColumnSetItem curItem=null;
		String strValue=null;
		String strText=null;
		for (int index=0;index<columnSetItems.size();index++){
			curItem=(ColumnSetItem)columnSetItems.get(index);
			if (curItem==null)
				continue;
			strValue=curItem.getValue();
			strText=curItem.getText();
			if ((strValue.equals(""))||(strValue==null))
				strValue="necessary_"+index;
			if ((strText.equals(""))||(strText==null))
				continue;
			this.NecessaryItems.add(strValue+"!"+strText);
		}
	}
	/**
	 * ȡ��ѡ��Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�ColumnSetItem����<br>
	 * @see ColumnSetItem<br>
	 */
	public static ArrayList getDataNecessaryCol(String id,HttpServletRequest request){
		ColumnSetItem curItem=null;
		String curValue=null;
		String curText=null;
		String curContent=null;
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=new ArrayList();

		int index_1=-1;
		tempResults=parsehiddens(id,request,2);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);
			curItem=new ColumnSetItem(curValue,curText);
			reSults.add(curItem);
		}
		return reSults;
	}
	public static Map getItemWidth(String id,HttpServletRequest request){			
			return HashMapManager.parseToMap(id+"_widthset",request);
	}
	/**
	 * ���ù̶���Ŀ����ݼ�<br>
	 * @param columnSetItems ��ColumnSetItem������ɵ�List<br>
	 * @see ColumnSetItem
	 * @return void <br>
	 */
	public void setDataFixedCol(List columnSetItems){
		ColumnSetItem curItem=null;
		String strValue=null;
		String strText=null;
		for (int index=0;index<columnSetItems.size();index++){
			curItem=(ColumnSetItem)columnSetItems.get(index);
			if (curItem==null)
				continue;
			strValue=curItem.getValue();
			strText=curItem.getText();
			if ((strValue.equals(""))||(strValue==null))
				strValue="fixed_"+index;
			if ((strText.equals(""))||(strText==null))
				continue;
			this.FixItems.add(strValue+"!"+strText);
		}
	}
	/**
	 * ȡ�̶���Ŀ����ݼ�<br>
	 * @param id ���ID<br>
	 * @param request ����<br>
	 * @return ArrayList ��ArrayList��ʽ���أ�
	 * ArrayList�д�ţ�ColumnSetItem����<br>
	 * @see ColumnSetItem<br>
	 */
	public static ArrayList getDataFixedCol(String id,HttpServletRequest request){
		ColumnSetItem curItem=null;
		String curValue=null;
		String curText=null;
		String curContent=null;
		ArrayList reSults=new ArrayList();
		ArrayList tempResults=new ArrayList();

		int index_1=-1;
		tempResults=parsehiddens(id,request,3);
		for (int index=0;index<tempResults.size();index++){
			curContent=(String)tempResults.get(index);
			index_1=curContent.indexOf("!");
			if (index_1==-1)
				continue;
			curValue=curContent.substring(0,index_1);
			curText=curContent.substring(index_1+1);
			curItem=new ColumnSetItem(curValue,curText);
			reSults.add(curItem);
		}
		return reSults;
	}
}