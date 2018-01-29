package com.born.insurance.webui.control;

import javax.servlet.http.HttpServletRequest;
/**
 * Title:TabCtrl Component<br>
 * Description:TabCtrl���<br>
 * Copyright: Copyright (c) 2003<br>
 * Company: bornsoft<br>
 * @author �ᴺ��
 * @version 1.0
 */
public class TabCtrl extends AbstractComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strTabCtrlWidth=null;
	private int intTabCtrlPages=1;
	private int defaultShowPage=0;
	private int TitlePosition=0;
	//private int TitleRows=1;
	private String[] TitlesOfPages=null;
	private boolean[] VisiblesOfPages=null;
	private IComponent[] componentsOfPages=null;
	private Table[] tables=null;
	private String EventParameter=null;
	private String[] imageSrc=new String[7];
	private Hidden hidden=null;
	private Hidden stylehidden=null; 
	private String ClickFunction=null;
	private String FunctionOnBlur[]=null;
	private String strBodyCss="";
	
	/**
	 * ���캯��<br>
	 * @param id ���ID<br>
	 */
	public TabCtrl(String id){
		super(id);
		this.name=id;		
		this.initInner();		
		this.EventParameter="'"+this.name+"'";		
		ComponentUtil util=ComponentUtil.getInstance();
		for (int index=0;index<imageSrc.length;index++){
			if (index==0)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-a.gif";
			if (index==1)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-b.gif";
			if (index==2)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-c.gif";
			if (index==3)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-d.gif";
			if (index==4)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-e.gif";
			if (index==5)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-f.gif";
			if (index==6)
				imageSrc[index]=util.getImagesRequestPath()+"/"+"page-g.gif";
		}		
		this.hidden=new Hidden(this.name+"_hidden");
		this.stylehidden=new Hidden(this.name+"_stylehidden");
	}
	/**
	 * ����TabCtrl����Ŀ��<br>
	 * @param width TabCtrl����Ŀ��<br>
	 * @return void<br>
	 */
	public void setTabCtrlWidth(String width){
		this.strTabCtrlWidth=String.valueOf(width);
	}
	/**
	 * ��ȡTabCtrl����Ŀ��<br>
	 * @return String TabCtrl����Ŀ��<br>
	 */
	public String getTabCtrlWidth(){
		return this.strTabCtrlWidth;
	}
	/**
	 * ����TabCtrl�����ҳ��ǩ��<br>
	 * @param pages TabCtrl�����ҳ��ǩ��<br>
	 * @return void<br>
	 */
	public void setTabCtrlPages(int pages){
		this.setTabCtrlPages_1(pages);
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩ��<br>
	 * @return int TabCtrl�����ҳ��ǩ��<br>
	 */
	public int getTabCtrlPages(){
		return this.intTabCtrlPages;
	}
	/**
	 * ����TabCtrl�����Ĭ����ʾҳ<br>
	 * @param indexOfPage TabCtrl�����Ĭ����ʾҳ���±�<br>
	 * @return void<br>
	 */
	public void setDefaultShowPage(int indexOfPage){
		if ((indexOfPage>=this.intTabCtrlPages)||(indexOfPage<0))
			return;
		this.defaultShowPage=indexOfPage;
	}
	/**
	 * ��ȡTabCtrl�����Ĭ����ʾҳ<br>
	 * @return int TabCtrl�����Ĭ����ʾҳ���±�<br>
	 */
	public int getDefaultShowPage(){
		return this.defaultShowPage;
	}
	/**
	 * ����TabCtrl�����ҳ��ǩ�ı���<br>
	 * @param indexOfPage TabCtrl�����ҳ��ǩ���±�<br>
	 * @param title TabCtrl�����ҳ��ǩ�ı���<br> 
	 * @return void<br>
	 */
	public void setTitleOfPage(int indexOfPage,String title){
		if ((indexOfPage>=this.TitlesOfPages.length)||(indexOfPage<0))
			return;
		this.TitlesOfPages[indexOfPage]=title;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩ�ı���<br>
	 * @param indexOfPage TabCtrl�����ҳ��ǩ���±�<br>
	 * @return String TabCtrl�����ҳ��ǩ�ı���<br>
	 */
	public String getTitleOfPage(int indexOfPage){
		if ((indexOfPage>=this.TitlesOfPages.length)||(indexOfPage<0))
			return null;
		return this.TitlesOfPages[indexOfPage];
	}
	/**
	 * ����TabCtrl�����ҳ��ǩ���CssClass<br>
	 * @param strCss TabCtrl�����ҳ��ǩ���CssClass<br>
	 * @return void<br>
	 */
	public void setTabCtrlBodyCss(String strCss){
		if ((strCss==null)||(strCss.trim().equals("")))
			return;
		this.strBodyCss=strCss;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩ���CssClass<br>
	 * @return String TabCtrl�����ҳ��ǩ���CssClass<br>
	 */
	public String getTabCtrlBodyCss(){
		return this.strBodyCss;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩͷ��ʾҳ��CssClass<br>
	 * @return String TabCtrl�����ҳ��ǩͷ��ʾҳ��CssClass<br>
	 */
	public String getTabCtrlHeadCss(){
		return "tabForeStyle";
	}
	/**
	 * ����TabCtrl�����ҳ��ǩ�����λ��<br>
	 * @param indexOfPosition TabCtrl�����ҳ��ǩ�����λ��<br>
	 * 	      ����:0-Left;1-Center;2-Right<br>
	 * @return void<br>
	 */
	public void setTitlePosition(int indexOfPosition){
		if ((indexOfPosition<0)||(indexOfPosition>2))
			return;
		this.TitlePosition=indexOfPosition;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩ�����λ��<br>
	 * @return int TabCtrl�����ҳ��ǩ�����λ��<br>
	 * 	      ����:0-Left;1-Center;2-Right<br>
	 */
	public int getTitlePosition(){
		return this.TitlePosition;
	}
	/**
	 * ��������,��ȡTabCtrl����ĵ�ǰ��ҳ��ǩ��<br>
	 * @param id ���Id<br>
	 * @param request ����<br>
	 * @return int TabCtrl����ĵ�ǰ��ҳ��ǩ��<br>
	 */
	public static int getCurrentPage(String id,HttpServletRequest request){
		String Values=request.getParameter(id+"_hidden");
		if ((Values==null)||(Values.length()<=0)||(Values.equals("")))
			return 0;
		return Integer.parseInt(Values.substring(0,1));
	}
	/**
	 * ����TabCtrl�����ĳ��ҳ��ǩ�Ƿ�ɼ�<br>
	 * @param indexOfPosition TabCtrl�����ҳ��ǩ��<br>
	 * @param blnIsVisible �Ƿ�ɼ�<br>
	 * @return void<br>
	 */
	public void setVisibleOfPage(int indexOfPage,boolean blnIsVisible){
		if((indexOfPage>=this.VisiblesOfPages.length)||(indexOfPage<0))
			return;
		this.VisiblesOfPages[indexOfPage]=blnIsVisible;
	}
	/**
	 * ȡTabCtrl�����ĳ��ҳ��ǩ�Ƿ�ɼ�<br>
	 * @param indexOfPosition TabCtrl�����ҳ��ǩ��<br>
	 * @return boolean �Ƿ�ɼ�<br>
	 */
	public boolean getVisibleOfPage(int indexOfPage){
		if ((indexOfPage>=this.VisiblesOfPages.length)||(indexOfPage<0))
			return false;
		return this.VisiblesOfPages[indexOfPage];
	}
	/**
	 * ����TabCtrl�����ҳ��ǩ�ڵ����<br>
	 * @param indexOfPage TabCtrl�����ҳ��ǩ���±�<br>
	 * @param components TabCtrl�����ҳ��ǩ�ڵ����<br> 
	 * @return void<br>
	 */
	public void setComponentsOfPage(int indexOfPage,IComponent components){
		if ((indexOfPage>=this.componentsOfPages.length)||(indexOfPage<0))
			return;
		this.componentsOfPages[indexOfPage]=components;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩ�ڵ����<br>
	 * @param indexOfPage TabCtrl�����ҳ��ǩ���±�<br> 
	 * @return IComponent abCtrl�����ҳ��ǩ�ڵ����<br>
	 */
	public IComponent getComponentsOfPage(int indexOfPage){
		if ((indexOfPage>=this.componentsOfPages.length)||(indexOfPage<0))
			return null;
		return this.componentsOfPages[indexOfPage];
	}
	/**
	 * ����TabCtrl�����ҳ��ǩOnBlurʱ���Զ���Ŀͻ��˷���<br>
	 * @param indexOfPage TabCtrl�����ҳ��ǩ���±�<br> 
	 * * @param function ҳ��ǩOnBlurʱ���Զ���Ŀͻ��˷���<br>
	 * @return void<br>
	 */
	public void setFunctionTabPageOnBlur(int indexOfPage,String function){
		if ((indexOfPage>=this.FunctionOnBlur.length)||(indexOfPage<0))
			return;
		this.FunctionOnBlur[indexOfPage]=function;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩOnBlurʱ���Զ���Ŀͻ��˷���<br>
	 * @param indexOfPage TabCtrl�����ҳ��ǩ���±�<br> 
	 * @return String ҳ��ǩOnBlurʱ���Զ���Ŀͻ��˷���<br>
	 */
	public String getFunctionTabPageOnBlur(int indexOfPage){
		if ((indexOfPage>=this.FunctionOnBlur.length)||(indexOfPage<0))
			return null;
		return this.FunctionOnBlur[indexOfPage];
	}
	/**
	 * ����TabCtrl�����ҳ��ǩ���ʱ���Զ���Ŀͻ��˷���<br>
	 * @param function ҳ��ǩ���ʱ���Զ���Ŀͻ��˷���<br>
	 * @return void<br>
	 */
	public void setFunctionOnTabCtrlClick(String function){
		this.ClickFunction=function;
	}
	/**
	 * ��ȡTabCtrl�����ҳ��ǩ���ʱ���Զ���Ŀͻ��˷���<br>
	 * @return String ҳ��ǩ���ʱ���Զ���Ŀͻ��˷���<br>
	 */
	public String getFunctionOnTabCtrlClick(){
		return this.ClickFunction;
	}
	protected String getElementHtml(){
		StringBuffer strInnerHtml=new StringBuffer();
		this.AdjustData();
		
		if (this.intTabCtrlPages<=0)
			return "";
			
		this.setContainerAttribute();
		strInnerHtml.append(this.tables[0].getInnerHtml());
		return strInnerHtml.toString();
	}
	protected String getScriptHtml(){
		return super.getScriptHtml() + TabCtrlJS.getTabCtrlJS();
	}
	private void initInner(){
		String ContainerTableId=this.name+"_tab_container";
		String TitleTableId=this.name+"_tab_title";
		String ComponentsTableID=this.name+"_tab_";
		int TitleTableCols=this.intTabCtrlPages*2+1;
		
		this.FunctionOnBlur=new String[this.intTabCtrlPages];
		this.TitlesOfPages=new String[this.intTabCtrlPages];
		this.VisiblesOfPages=new boolean[this.intTabCtrlPages];
		for (int index=0;index<this.VisiblesOfPages.length;index++)
			this.VisiblesOfPages[index]=true;
		this.componentsOfPages=new IComponent[this.intTabCtrlPages];
		this.tables=new Table[this.intTabCtrlPages+2];		
		for (int index=0;index<this.tables.length;index++)
			if (index==0)
				this.tables[index]=new Table(ContainerTableId,1,3);
			else if (index==1)
				this.tables[index]=new Table(TitleTableId,TitleTableCols,1);
			else
				this.tables[index]=new Table(ComponentsTableID+(index-2),1,1);
	}
	private void setContainerAttribute(){
		String strTemp=null;
		
		Table ContainerTable=this.tables[0];	
		ContainerTable.setBorder(0);
		ContainerTable.setAttribute("cellpadding","0");
		ContainerTable.setAttribute("cellspacing","0");	
		ContainerTable.setWidth(this.strTabCtrlWidth);
					
		this.setTitleAttribute();
		Table TitleTable=this.tables[1];
		ContainerTable.getRow(0).getTableCell(0).addComponent(TitleTable);
		
		if (this.TitlePosition==0)
			strTemp="left";
		if (this.TitlePosition==1)
			strTemp="center";
		if (this.TitlePosition==2)
			strTemp="right";
		ContainerTable.getRow(0).getTableCell(0).addAttribute("align",strTemp);
					
		this.setComponentsAttribute();
		if (!(this.strBodyCss.trim().equals("")))
			ContainerTable.getRow(1).getTableCell(0).addAttribute("class",this.strBodyCss);
		for (int index=2;index<this.tables.length;index++)
			ContainerTable.getRow(1).getTableCell(0).addComponent(this.tables[index]);
		
		this.setHiddenAttribute();
		ContainerTable.getRow(2).getTableCell(0).addComponent(this.hidden);
		ContainerTable.getRow(2).getTableCell(0).addComponent(this.stylehidden);
	}
	private void setTitleAttribute(){
		Table TitleTable=this.tables[1];
		int TitleTableCols=TitleTable.columnSize();
		String CurTitle=null;
		TableCell CurCell=null;
		int TitleIndex=0;
		int ImageIndex=0;
		Image CurImage=null;
		String OnClickFunction=null;
		String strTemp=null;
		boolean blnChangImage=false;
		int xx=0;
		
		TitleTable.setAttribute("cellpadding","0");
		TitleTable.setAttribute("cellspacing","0");		
		TitleTable.getRow(0).addAttribute("style","FONT-SIZE: 12px");
		
		for (int index=0;index<TitleTableCols;index++){
			CurCell=TitleTable.getRow(0).getTableCell(index);
					
			if (index!=(TitleTableCols-1)){		
				if (this.IsVisible(index/2))
					strTemp="display:";
				else{
					strTemp="display:none";
				}
				if ((index%2)!=0)
					strTemp="BORDER-TOP: #dcdcdc 1px solid; CURSOR: hand;"+strTemp;
				CurCell.addAttribute("style",strTemp);
			}
			
			CurImage=new Image("image_"+ImageIndex);
			if (index==0){
				ImageIndex=index/2;
				if (ImageIndex==this.defaultShowPage)						
					CurImage.addAttribute("src",this.imageSrc[1]);
				else
					CurImage.addAttribute("src",this.imageSrc[0]);
				CurCell.addComponent(CurImage);		
			}
			else if (index==(TitleTableCols-1)){
				blnChangImage=false;
				ImageIndex=index/2;
				for (xx=(index-1);xx>=0;xx-=2)
					if (this.IsVisible(xx/2))
						break;
					else
						blnChangImage=true;
				if (blnChangImage){					
					if (xx>=0){
						if ((xx/2)==this.defaultShowPage)
							CurImage.addAttribute("src",this.imageSrc[6]);
						else
							CurImage.addAttribute("src",this.imageSrc[3]);
						strTemp="display:";
					}
					if (xx==-1){
						if ((ImageIndex-1)==this.defaultShowPage)						
							CurImage.addAttribute("src",this.imageSrc[6]);
						else
							CurImage.addAttribute("src",this.imageSrc[3]);
						strTemp="display:none";
					}
				}
				else{
					if ((ImageIndex-1)==this.defaultShowPage)						
						CurImage.addAttribute("src",this.imageSrc[6]);
					else
						CurImage.addAttribute("src",this.imageSrc[3]);
					strTemp="display:";
				}
				CurCell.addAttribute("style",strTemp);
				CurCell.addComponent(CurImage);			
			}
			else
				if (index%2==0){
					blnChangImage=false;
					ImageIndex=index/2;
					for (xx=(index-1);xx>=0;xx-=2)
						if (this.IsVisible(xx/2))
							break;
						else
							blnChangImage=true;
					if (blnChangImage)	{					
						if (xx>=0)
							if ((xx/2)==this.defaultShowPage)
								CurImage.addAttribute("src",this.imageSrc[2]);
							else
								CurImage.addAttribute("src",this.imageSrc[4]);
						if (xx==-1)
							if (ImageIndex==this.defaultShowPage)
								CurImage.addAttribute("src",this.imageSrc[1]);
							else
								CurImage.addAttribute("src",this.imageSrc[0]);
					}
					else
						if ((ImageIndex!=(this.defaultShowPage+1))&&(ImageIndex!=this.defaultShowPage))
							CurImage.addAttribute("src",this.imageSrc[4]);
						else if (ImageIndex==(this.defaultShowPage+1))
							CurImage.addAttribute("src",this.imageSrc[2]);
						else
							CurImage.addAttribute("src",this.imageSrc[5]);
					CurCell.addComponent(CurImage);	
				}
				else{				
					TitleIndex=(index-1)/2;
					CurTitle=this.TitlesOfPages[TitleIndex];
					CurTitle=StringUtils.replace(CurTitle," ","&nbsp;");// CurTitle.replaceAll(" ","&nbsp;");
					if (TitleIndex==this.defaultShowPage)
						//CurCell.addAttribute("background",ComponentUtil.getInstance().getImagesRequestPath()+"/tab-dw1.gif");
						CurCell.addAttribute("class","tabForeStyle");
					else
						CurCell.addAttribute("class","tabBackStyle");
					CurCell.setText("<nobr>"+CurTitle+"</nobr>");
					if ((this.FunctionOnBlur[TitleIndex]!=null)&&(!this.FunctionOnBlur[TitleIndex].trim().equals("")))
						CurCell.addAttribute("pageonblur",this.FunctionOnBlur[TitleIndex]);
					if ((this.ClickFunction==null)||(this.ClickFunction.trim().equals("")))
						OnClickFunction="TabCtrl_onclickTabCtrl("+this.EventParameter+","+TitleIndex+",false)";
					else
						OnClickFunction="TabCtrl_onclickTabCtrl("+this.EventParameter+","+TitleIndex+",false)"
										+";"+this.ClickFunction;
					CurCell.addAttribute("onclick",OnClickFunction);
				}
		}
	}
	private boolean IsVisible(int index){
		if ((index<0)||(index>=this.VisiblesOfPages.length))
			return false;
		else
			return this.VisiblesOfPages[index];
	}
	private void AdjustShowPage(){
		for (int index=0;index<this.VisiblesOfPages.length;index++)
			if (this.IsVisible(index)){
				this.defaultShowPage=index;
				break;
			}
	}
	private void AdjustData(){
		int intInValidCount=0;
		boolean blnnotchangeshowpage=true;
		blnnotchangeshowpage=this.IsVisible(this.defaultShowPage);
		if (!blnnotchangeshowpage)
			this.AdjustShowPage();
			
		for (int index=0;index<this.TitlesOfPages.length;index++)
			if ((this.TitlesOfPages[index]==null)||(this.TitlesOfPages[index].trim().equals("")))
				intInValidCount++;
		if (intInValidCount==0)
			return;
		this.intTabCtrlPages=this.intTabCtrlPages-intInValidCount;
		this.setTabCtrlPages_1(this.intTabCtrlPages);
		if (this.defaultShowPage>=this.TitlesOfPages.length)
			this.defaultShowPage=0;
		
		blnnotchangeshowpage=this.IsVisible(this.defaultShowPage);
		if (!blnnotchangeshowpage)
			this.AdjustShowPage();
					
	}
	private void setTabCtrlPages_1(int pages){
		if (pages<=0)
			return;			
		
		int intCounter=0;
		for (int index=0;index<this.TitlesOfPages.length;index++)
			if ((this.TitlesOfPages[index]==null)||(this.TitlesOfPages[index].trim().equals("")))
				continue;
			else
				intCounter++;
			
		String[] oldtitles=new String[intCounter];
		boolean[] oldvisibles=new boolean[intCounter];
		IComponent[] oldcomponents=new IComponent[intCounter];
		String[] oldfunctiononblur=new String[intCounter];
		
		int index_1=0;
		for (int index=0;index<this.TitlesOfPages.length;index++)
			if ((this.TitlesOfPages[index]==null)||(this.TitlesOfPages[index].trim().equals("")))
				continue;
			else{
				oldtitles[index_1]=this.TitlesOfPages[index];
				oldvisibles[index_1]=this.VisiblesOfPages[index];
				oldcomponents[index_1]=this.componentsOfPages[index];
				oldfunctiononblur[index_1]=this.FunctionOnBlur[index];
				index_1++;
			}
				
		this.intTabCtrlPages=pages;
		this.initInner();
		int end=0;
		if (oldtitles.length<=this.TitlesOfPages.length)
			end=oldtitles.length;
		else
			end=this.TitlesOfPages.length;
			
		for (int index=0;index<end;index++){
			this.TitlesOfPages[index]=oldtitles[index];
			this.VisiblesOfPages[index]=oldvisibles[index];
			this.componentsOfPages[index]=oldcomponents[index];
			this.FunctionOnBlur[index]=oldfunctiononblur[index];
		}
		
	}
	private void setComponentsAttribute(){
		Table CurComponentTable=null;
		IComponent CurComponent=null;
		for (int index=0;index<this.componentsOfPages.length;index++){
			CurComponent=this.componentsOfPages[index];
			CurComponentTable=this.tables[index+2];
			
			if (index==this.defaultShowPage)
				CurComponentTable.addAttribute("style","display:block");
			else
				CurComponentTable.addAttribute("style","display:none");
			/*if (!(this.strBodyCss.trim().equals("")))
				CurComponentTable.addAttribute("class",this.strBodyCss);*/
			CurComponentTable.getRow(0).getTableCell(0).addComponent(CurComponent);
		}
	}
	private void setHiddenAttribute(){
		String hiddenvalue=String.valueOf(this.defaultShowPage);
		for (int index=0;index<this.imageSrc.length;index++)
			hiddenvalue=hiddenvalue+"*"+this.imageSrc[index];
		this.hidden.setText(hiddenvalue);
	}
}
