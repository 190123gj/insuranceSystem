package com.born.insurance.webui.control;

/**
 * Title:TabCtrlJS Class<br>
 * Description:TabCtrl��Ӧ��JavaScript��<br>
 * Copyright: Copyright (c) 2003<br>
 * Company: bornsoft<br>
 * <br>�ͻ��˷������£�<br>
 * function TabCtrl_TabCtrlNextPageOnClick(ControlName)<br>
 * ���ܣ������һ��ҳ��ǩ��<br>
 * ����ControlName ���ID<br>
 * <br>
 * function TabCtrl_TabCtrlPrevPageOnClick(ControlName)<br>
 * ���ܣ������һ��ҳ��ǩ��<br>
 * ����ControlName ���ID<br>
 * <br>
 * function TabCtrl_IsArriveLast(ControlName)<br>
 * ���ܣ��Ƿ������TabCtrl��������һ��ҳ��ǩ��<br>
 * ����ControlName ���ID<br>
 * Retrun:true ��;false ��<br>
 * <br>
 * function TabCtrl_IsArriveFirst(ControlName)<br>
 * ���ܣ��Ƿ������TabCtrl�������ǰһ��ҳ��ǩ��<br>
 * ����ControlName ���ID<br>
 * Retrun:true ��;false ��<br>
 * <br>
 * function TabCtrl_setShowPage(ControlName,Index)<br>
 * ���ܣ��������TabCtrl����ĵ�ǰҳ��<br>
 * ����ControlName ���ID<br>
 * ����Index ҳ��ǩ��<br>
 * <br>
 * function TabCtrl_getShowPage(ControlName)<br>
 * ���ܣ�ȡ���TabCtrl����ĵ�ǰҳ��ǩ�š�<br>
 * ����ControlName ���ID<br>
 * Retrun:��ǰҳ��ǩ��<br>
 * <br>
 * function TabCtrl_setVisibleOfPage(ControlName,index,visible)<br>
 * ���ܣ��������TabCtrl�����ĳ��ҳ��ǩ�Ƿ�ɼ�<br>
 * ����ControlName ���ID<br>
 * ����index ҳ��ǩ��<br>
 * ����visible �Ƿ�ɼ�(true ��;false ��)<br>
 * <br>
 * function TabCtrl_getVisibleOfPage(ControlName,Index)<br>
 * ���ܣ�ȡ���TabCtrl�����ĳ��ҳ��ǩ�Ƿ�ɼ�<br>
 * ����ControlName ���ID<br>
 * ����index ҳ��ǩ��<br>
 * Retrun:�Ƿ�ɼ�(true ��;false ��)<br>
 * @author �ᴺ��
 * @version 1.0
 */
public class TabCtrlJS{
	/**
	 * ȡTabCtrl��Ӧ��JavaScript<br>
	 * @return String TabCtrl��Ӧ��JavaScript<br>
	 */
	public static String getTabCtrlJS(){
		JSTool jstool=new JSTool("TabCtrl.js");
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS();
		 }
		StringBuffer js = new StringBuffer();
		js.append("function TabCtrl_IsVisible(ControlName,xx){\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var ColCount;\r\n");
		js.append("	\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	if ((parseInt(xx)<0)||(parseInt(xx)>=ColCount))\r\n");
		js.append("		return false;\r\n");
		js.append("	if (RowObject.cells[parseInt(xx)].style.display!=null)\r\n");
		js.append("		if (RowObject.cells[parseInt(xx)].style.display==\"none\")\r\n");
		js.append("			return false			\r\n");
		js.append("		else\r\n");
		js.append("			return true;\r\n");
		js.append("	else\r\n");
		js.append("		return true;\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_setTitles(ControlName,Index){\r\n");
		js.append("	var visibilityClass=\"\";\r\n");
		js.append("	var hideenClass=\"\";\r\n");
		js.append("	var HideStyle=\"\";\r\n");
		js.append("	var ShowStyle=\"\"; \r\n");
		js.append("	var blnChangeImage=false;\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;   \r\n");
		js.append("	var intCount;   \r\n");
		js.append("	var TitleIndex;   \r\n");
		js.append("	var StyleHidden;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("    MyArray=TabCtrl_getHidden(ControlName);    \r\n");
		js.append("    \r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	\r\n");
		js.append("	visibilityClass=RowObject.cells[MyArray[0]*2+1].className;\r\n");
		js.append("	StyleHidden=document.getElementById(ControlName+\"_stylehidden\");\r\n");
		js.append("	if ((StyleHidden.value==null)||(StyleHidden.value==\"\"))\r\n");
		js.append("		ShowStyle=RowObject.cells[MyArray[0]*2+1].getAttribute(\"style\");\r\n");
		js.append("	else{\r\n");
		js.append("		ShowStyle=StyleHidden.value;\r\n");
		js.append("		StyleHidden.value=\"\";\r\n");
		js.append("	}\r\n");
		js.append("	hideenClass=RowObject.cells[parseInt(Index)*2+1].className;\r\n");
		js.append("	HideStyle=RowObject.cells[parseInt(Index)*2+1].getAttribute(\"style\");\r\n");
		js.append("	       \r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	for (intCount=1;intCount<ColCount;intCount+=2){\r\n");
		js.append("		TitleIndex=(intCount-1)/2;\r\n");
		js.append("		if (parseInt(Index)==TitleIndex){\r\n");
		js.append("			RowObject.cells[intCount].setAttribute(\"style\",ShowStyle);\r\n");
		js.append("			RowObject.cells[intCount].className=visibilityClass;                  \r\n");
		js.append("		}\r\n");
		js.append("		else{	\r\n");
		js.append("			blnChangeImage=false;\r\n");
		js.append("			if (TabCtrl_IsVisible(ControlName,intCount)==false)\r\n");
		js.append("				blnChangeImage=true;\r\n");
		js.append("			RowObject.cells[intCount].setAttribute(\"style\",HideStyle);\r\n");
		js.append("			RowObject.cells[intCount].className=hideenClass;\r\n");
		js.append("			if (blnChangeImage==true)\r\n");
		js.append("				RowObject.cells[intCount].style.display=\"none\";\r\n");
		js.append("		}\r\n");
		js.append("	}\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_setImages(ControlName,Index){\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;   \r\n");
		js.append("	var intCount;   \r\n");
		js.append("	var ImageIndex;\r\n");
		js.append("	var xx=0; \r\n");
		js.append("	var blnChangeImage=false;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("    MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append(" \r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];       \r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	\r\n");
		js.append("	for (intCount=0;intCount<ColCount;intCount+=2)\r\n");
		js.append("	{\r\n");
		js.append("		if (intCount==0){\r\n");
		js.append("			ImageIndex=intCount/2;\r\n");
		js.append("			if (ImageIndex==parseInt(Index))\r\n");
		js.append("				RowObject.cells[intCount].childNodes[0].src=MyArray[2];\r\n");
		js.append("			else\r\n");
		js.append("				RowObject.cells[intCount].childNodes[0].src=MyArray[1];				\r\n");
		js.append("		}\r\n");
		js.append("		else if (intCount==(ColCount-1)){\r\n");
		js.append("			ImageIndex=intCount/2;\r\n");
		js.append("			blnChangeImage=false;\r\n");
		js.append("			for (xx=(intCount-1);xx>=0;xx-=2)\r\n");
		js.append("				if (TabCtrl_IsVisible(ControlName,xx)==true)\r\n");
		js.append("					break;\r\n");
		js.append("				else\r\n");
		js.append("					blnChangeImage=true;\r\n");
		js.append("			if (blnChangeImage==true){\r\n");
		js.append("				if (xx>=0)\r\n");
		js.append("					if ((xx-1)/2==parseInt(Index))	\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[7];\r\n");
		js.append("					else\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[4];\r\n");
		js.append("				if (xx==-1)\r\n");
		js.append("					if ((ImageIndex-1)==parseInt(Index))\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[7];\r\n");
		js.append("					else\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[4];\r\n");
		js.append("			}\r\n");
		js.append("			else\r\n");
		js.append("				if ((ImageIndex-1)==parseInt(Index))\r\n");
		js.append("					RowObject.cells[intCount].childNodes[0].src=MyArray[7];\r\n");
		js.append("				else\r\n");
		js.append("					RowObject.cells[intCount].childNodes[0].src=MyArray[4];\r\n");
		js.append("		}\r\n");
		js.append("		else{\r\n");
		js.append("			ImageIndex=intCount/2;\r\n");
		js.append("			blnChangeImage=false;\r\n");
		js.append("			for (xx=(intCount-1);xx>=0;xx-=2)\r\n");
		js.append("				if (TabCtrl_IsVisible(ControlName,xx)==true)\r\n");
		js.append("					break;\r\n");
		js.append("				else\r\n");
		js.append("					blnChangeImage=true;\r\n");
		js.append("			if (blnChangeImage==true){\r\n");
		js.append("				if (xx>=0)\r\n");
		js.append("					if (((xx-1)/2!=parseInt(Index))&&(ImageIndex!=parseInt(Index)))\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[5];\r\n");
		js.append("					else if ((xx-1)/2==parseInt(Index))\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[3];\r\n");
		js.append("					else\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[6];\r\n");
		js.append("				if (xx==-1)\r\n");
		js.append("					if (ImageIndex==parseInt(Index))\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[2];\r\n");
		js.append("					else\r\n");
		js.append("						RowObject.cells[intCount].childNodes[0].src=MyArray[1];\r\n");
		js.append("			}\r\n");
		js.append("			else\r\n");
		js.append("				if ((ImageIndex!=(parseInt(Index)+1))&&(ImageIndex!=parseInt(Index)))\r\n");
		js.append("					RowObject.cells[intCount].childNodes[0].src=MyArray[5];\r\n");
		js.append("				else if (ImageIndex==(parseInt(Index)+1))\r\n");
		js.append("					RowObject.cells[intCount].childNodes[0].src=MyArray[3];\r\n");
		js.append("				else\r\n");
		js.append("					RowObject.cells[intCount].childNodes[0].src=MyArray[6];	\r\n");
		js.append("		}\r\n");
		js.append("	}\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_onclickTabCtrl(ControlName,Index,blnIsInnerInvoke)\r\n");
		js.append("{\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;   \r\n");
		js.append("	var MyArray;\r\n");
		js.append("	\r\n");
		js.append("    MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("    \r\n");
		js.append("	if (parseInt(Index)!=parseInt(MyArray[0]))\r\n");
		js.append("	{\r\n");
		js.append("		TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("		RowObject=TitleTable.rows[0];\r\n");
		js.append("        \r\n");
		js.append("        if (blnIsInnerInvoke==false)\r\n");
		js.append("			if (RowObject.cells[parseInt(MyArray[0])*2+1].getAttribute(\"pageonblur\")!=null){\r\n");
		js.append("				var ret=eval(RowObject.cells[parseInt(MyArray[0])*2+1].getAttribute(\"pageonblur\"));\r\n");
		js.append("				if (ret==false)\r\n");
		js.append("					return;\r\n");
		js.append("			}\r\n");
		js.append("		\r\n");
		js.append("		TabCtrl_setImages(ControlName,Index);\r\n");
		js.append("		TabCtrl_setTitles(ControlName,Index);\r\n");
		js.append("		\r\n");
		js.append("		ShowObj=document.getElementById(ControlName+\"_tab_\"+Index);\r\n");
		js.append("		HideObj=document.getElementById(ControlName+\"_tab_\"+MyArray[0]);\r\n");
		js.append("		HideObj.style.display=\"none\";\r\n");
		js.append("		ShowObj.style.display=\"block\";\r\n");
		js.append("		MyArray[0]=Index;\r\n");
		js.append("		TabCtrl_setHidden(ControlName,MyArray);\r\n");
		js.append("	}\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_TabCtrlNextPageOnClick(ControlName)\r\n");
		js.append("{\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var xx=0;\r\n");
		js.append("	var intCount=0;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("	MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("	\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;	\r\n");
		js.append("	for (intCount=parseInt(MyArray[0])*2+1+2;intCount<(ColCount-1);intCount+=2)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,intCount)==false)\r\n");
		js.append("			xx++;\r\n");
		js.append("		else\r\n");
		js.append("			break;\r\n");
		js.append("	if ((parseInt(MyArray[0])+xx)>=((ColCount-1)/2-1))	\r\n");
		js.append("		return;\r\n");
		js.append("	TabCtrl_onclickTabCtrl(ControlName,(parseInt(MyArray[0])+xx+1),false);\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_TabCtrlPrevPageOnClick(ControlName)\r\n");
		js.append("{\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var xx=0;\r\n");
		js.append("	var intCount=0;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("	MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("	\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	for (intCount=parseInt(MyArray[0])*2+1-2;intCount>=0;intCount-=2)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,intCount)==false)\r\n");
		js.append("			xx++;\r\n");
		js.append("		else\r\n");
		js.append("			break;\r\n");
		js.append("	if (parseInt(MyArray[0]-xx)<=0)	\r\n");
		js.append("		return;\r\n");
		js.append("	TabCtrl_onclickTabCtrl(ControlName,(parseInt(MyArray[0])-xx-1),false);\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_IsArriveLast(ControlName){\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var xx=0;\r\n");
		js.append("	var intCount=0;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("	MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("	\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	for (intCount=parseInt(MyArray[0])*2+1+2;intCount<(ColCount-1);intCount+=2)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,intCount)==false)\r\n");
		js.append("			xx++;\r\n");
		js.append("		else\r\n");
		js.append("			break;\r\n");
		js.append("	if ((parseInt(MyArray[0])+xx)>=((ColCount-1)/2-1))	\r\n");
		js.append("		return true;\r\n");
		js.append("	else\r\n");
		js.append("		return false;\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_IsArriveFirst(ControlName)\r\n");
		js.append("{\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var xx=0;\r\n");
		js.append("	var intCount=0;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("	MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("	\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	for (intCount=parseInt(MyArray[0])*2+1-2;intCount>=0;intCount-=2)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,intCount)==false)\r\n");
		js.append("			xx++;\r\n");
		js.append("		else\r\n");
		js.append("			break;\r\n");
		js.append("	if (parseInt(MyArray[0]-xx)<=0)	\r\n");
		js.append("		return true;\r\n");
		js.append("	else\r\n");
		js.append("		return false;\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_setShowPage(ControlName,Index){\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var ColCount;\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	if (((parseInt(Index)*2+1)<1)||((parseInt(Index)*2+1)>=(ColCount-1)))\r\n");
		js.append("		return;\r\n");
		js.append("	if (TabCtrl_IsVisible(ControlName,Index)==false)\r\n");
		js.append("		return;\r\n");
		js.append("	TabCtrl_onclickTabCtrl(ControlName,parseInt(Index),false);\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_getShowPage(ControlName){\r\n");
		js.append("	var MyArray;\r\n");
		js.append("	MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("	return MyArray[0];\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_setHidden(ControlName,dataarray){\r\n");
		js.append("	var HiddenObj=document.getElementById(ControlName+\"_hidden\");\r\n");
		js.append("	for (xx=0;xx<dataarray.length;xx++)\r\n");
		js.append("			if (xx==0)\r\n");
		js.append("				HiddenObj.value=dataarray[xx];\r\n");
		js.append("			else\r\n");
		js.append("				HiddenObj.value=HiddenObj.value+\"*\"+dataarray[xx];\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_getHidden(ControlName){\r\n");
		js.append("	var HiddenObj=document.getElementById(ControlName+\"_hidden\");\r\n");
		js.append("	var HiddenValue=HiddenObj.value; \r\n");
		js.append("	var dataarray=new Array();\r\n");
		js.append("	dataarray=HiddenValue.split(\"*\");\r\n");
		js.append("	return dataarray; \r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_getVisibleOfPage(ControlName,Index){\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject;  \r\n");
		js.append("	var ColCount;\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	if (((parseInt(Index)*2+1)<0)||((parseInt(Index)*2+1)>=(ColCount-1)))\r\n");
		js.append("		return false;\r\n");
		js.append("	return TabCtrl_IsVisible(ControlName,parseInt(Index)*2+1);\r\n");
		js.append("}\r\n");
		js.append("function TabCtrl_setVisibleOfPage(ControlName,index,visible)\r\n");
		js.append("{\r\n");
		js.append("	var ColCount;\r\n");
		js.append("	var TitleTable;\r\n");
		js.append("	var RowObject; \r\n");
		js.append("	var intCount=0;\r\n");
		js.append("	var intFront=-1;\r\n");
		js.append("	var intBehind;\r\n");
		js.append("	var HideObj;\r\n");
		js.append("	var old;\r\n");
		js.append("	var StyleHidden;\r\n");
		js.append("	var MyArray;\r\n");
		js.append("	MyArray=TabCtrl_getHidden(ControlName);\r\n");
		js.append("	\r\n");
		js.append("	TitleTable=document.getElementById(ControlName+\"_tab_title\");\r\n");
		js.append("	RowObject=TitleTable.rows[0];\r\n");
		js.append("	ColCount=RowObject.cells.length;\r\n");
		js.append("	intBehind=ColCount;\r\n");
		js.append("	if ((parseInt(index)*2+1<0)||(parseInt(index)*2+1>=(ColCount-1)))\r\n");
		js.append("		return;\r\n");
		js.append("	if (visible==false)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,parseInt(index)*2+1)==false)\r\n");
		js.append("			return;	\r\n");
		js.append("	if (visible==true)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,parseInt(index)*2+1)==true)\r\n");
		js.append("			return;	\r\n");
		js.append("	for (intCount=(parseInt(index)*2+1-2);intCount>=0;intCount-=2)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,intCount)==true){\r\n");
		js.append("			intFront=intCount;\r\n");
		js.append("			break;\r\n");
		js.append("	}\r\n");
		js.append("	for (intCount=(parseInt(index)*2+1+2);intCount<(ColCount-1);intCount+=2)\r\n");
		js.append("		if (TabCtrl_IsVisible(ControlName,intCount)==true){\r\n");
		js.append("			intBehind=intCount;\r\n");
		js.append("			break;\r\n");
		js.append("	}\r\n");
		js.append("	if ((intFront==-1)&&(intBehind==ColCount))\r\n");
		js.append("		return;\r\n");
		js.append("		\r\n");		
		js.append("	StyleHidden=document.getElementById(ControlName+\"_stylehidden\");\r\n");
		js.append("	StyleHidden.value=\"\";\r\n");
		js.append("	if (visible==false){\r\n");
		js.append("		RowObject.cells[parseInt(index)*2].style.display=\"none\";\r\n");
		js.append("		old=RowObject.cells[parseInt(index)*2+1].getAttribute(\"style\");\r\n");
		js.append("		RowObject.cells[parseInt(index)*2+1].style.display=\"none\";\r\n");		
		js.append("	}\r\n");
		js.append("	if (visible==true){\r\n");
		js.append("		RowObject.cells[parseInt(index)*2].style.display=\"\";\r\n");
		js.append("		RowObject.cells[parseInt(index)*2+1].style.display=\"\";\r\n");
		js.append("	}\r\n");
		js.append("	if (intBehind==ColCount)\r\n");
		js.append("		if (parseInt(index)==parseInt(TabCtrl_getShowPage(ControlName))){\r\n");
		js.append("			StyleHidden.value=old;\r\n");
		js.append("			TabCtrl_onclickTabCtrl(ControlName,(parseInt(intFront)-1)/2,true);\r\n");
		js.append("		}\r\n");
		js.append("		else\r\n");
		js.append("			TabCtrl_setImages(ControlName,parseInt(TabCtrl_getShowPage(ControlName)));\r\n");
		js.append("	else\r\n");
		js.append("		if (parseInt(index)==parseInt(TabCtrl_getShowPage(ControlName))){\r\n");
		js.append("			StyleHidden.value=old;\r\n");
		js.append("			TabCtrl_onclickTabCtrl(ControlName,(parseInt(intBehind)-1)/2,true);\r\n");
		js.append("		}\r\n");
		js.append("		else\r\n");
		js.append("			TabCtrl_setImages(ControlName,parseInt(TabCtrl_getShowPage(ControlName)));\r\n");
		js.append("}");
		jstool.js=js;
		return jstool.getComponentJS();
	}
}