/*
 * Created on 2003-10-26
 *
 */
package com.born.insurance.webui.control;
/**
 * @author     qch
 * @created    2003-10-26
 * @version    $Revision: 1.16 $ November 20,2003
 * @since      1.0 
 * grid�༭�пͻ��˴���
 */
public class EditorJS
{
	/**
	   * <P><B>��ȡEditor�ͻ��˽ű�</B></P>
	   * 
	   * <P><B>�õ��ͻ������</B></P>
	   * <P><B>function:getChild(flag)</B></P>
	   * param flag �Ƿ��������Դ��ʽ��־
	   * return �ͻ����������
	   * 
	   * <P><B>�õ��ͻ������</B></P>
	   * <P><B>function:getTableCell(obj,gridID)</B></P>
	   * param obj ����
	   * param gridID webgrid����ID
	   * return �ͻ����������
	   * 
	   * <P><B>����Editor����</B></P>
	   * <P><B>function:setEditor(Property,Value)</B></P>
	   * param Property Editor����
	   * param Value Editor����ֵ
	   * 
	   * <P><B>�ı����Դ</B></P>
	   * <P><B>function:changeData(flag,editRowIndex,totalRows)</B></P>
	   * param flag -1ɾ��1����\\����0ȡ��
	   * param editRowIndex ��ǰ��������
	   * param totalRows ���Դ������
	   * 
	   * <P><B>�õ�editor(�ⲿ����)</B></P>
	   * <P><B>function:Editor_getEditor(gridID,columnName)</B></P>
	   * param gridID webgrid����ID
	   * param columnName ������
	   * 
	   * <P><B>�������Դ(�ⲿ����)</B></P>
	   * <P><B>function:setData(function setEditorData(editRowIndex,filterObj,textObj,valueObj)</B></P>
	   * param editRowIndex ��ǰ��������
	   * param totalRows ���filter����ݵĿͻ��˶���
	   * param totalRows ���text����ݵĿͻ��˶���
	   * param totalRows ���value����ݵĿͻ��˶���
	   * 
	   * <P><B>�������Դ(�ⲿ����)</B></P>
	   * <P><B>function:setDataByValue(editRowIndex,filterValue,textValue,valueValue)</B></P>
	   * param editRowIndex ��ǰ��������
	   * param totalRows ���filter����ݵĿͻ���value
	   * param totalRows ���text����ݵĿͻ���value
	   * param totalRows ���value����ݵĿͻ���value
	   * 
	   * <P><B>�ı��й̶���ʽ(�ⲿ����)</B></P>
	   * <P><B>function:Editor_changeFixFormula(gridID,columnName,fomula)</B></P>
	   * param gridID webgrid����ID
	   * param columnName ������
	   * param fomula ��ʽ
	   * 
	   * <P><B>�ı乫ʽ��־(�ⲿ����)</B></P>
	   * <P><B>function:Editor_changeFormulaFlag(gridID,columnName,key)</B></P>
	   * param gridID webgrid����ID
	   * param columnName ������
	   * param key ��ʽ��־����Ӧ��HashMapManager��key��
	   * 
	   * <P><B>�ı乫ʽ��־(�ⲿ����)</B></P>
	   * <P><B>function:Editor_addFormula(gridID,columnName,key,object)</B></P>
	   * param gridID webgrid����ID
	   * param columnName ������
	   * param key ��ʽ��־����Ӧ��HashMapManager��key��
	   * param object ��ʽ��־��Ӧ�Ĺ�ʽ����Ӧ��HashMapManager��value��
	   * 
	   */
	public static String getEditorJS()
	{
		JSTool jstool = new JSTool("EditorJS.js");
		StringBuffer js = new StringBuffer();
		if (!jstool.util.isUpdateJsEveryTime())
			return jstool.getComponentJS();
		js.append("function Editor()\r\n");
		js.append("{\r\n");
		js.append("   if(arguments.length==0)\r\n");
		js.append("   {\r\n");
		js.append("       this.name=\"\";\r\n");
		js.append("       this.columnName=\"\";\r\n");
		js.append("   }\r\n");
		js.append("   else\r\n");
		js.append("   {\r\n");
		js.append("       this.columnName=arguments[0];\r\n");
		js.append("       this.name=arguments[1];\r\n");
		js.append("   }\r\n");
		js.append("   this.gridID=\"\";\r\n");
		js.append("   this.dataField=\"\";\r\n");
		js.append("   this.dataValue=\"\";\r\n");
		js.append("   this.data=null;\r\n");
		js.append("   this.dataColumn=null;\r\n");
		js.append("   this.headerName=\"\";\r\n");
		js.append("   this.formula=null;\r\n");
		js.append("   this.colWidth=\"\";\r\n");
		js.append("   this.hAlign=\"\";\r\n");
		js.append("   this.componentID=\"\";\r\n");
		js.append("   this.component=\"\";\r\n");
		js.append("   this.mutexColumn=null;\r\n");
		js.append("   this.relationColumn=\"\";\r\n");
		js.append("   this.editorIndex=-1;\r\n");
		js.append("   this.isNoFixFormula=false;\r\n");
		js.append("   this.isTotal=false;\r\n");
		js.append("   this.isNecessary=false;\r\n");
		js.append("   this.isReadOnly=false;\r\n");
		js.append("   this.isChange=false;\r\n");
		js.append("   this.isMutex=false;\r\n");
		js.append("   this.isDrop=false;\r\n");
		js.append("   this.isRelation=false;\r\n");
		js.append("   this.isAutoSearch=false;\r\n");
		js.append("   this.isColInitStatus=false;\r\n");
		js.append("   this.isColDisabled=false;\r\n");
		js.append("   this.isAutoAscend=false;\r\n");
		js.append("   this.isClient=false;\r\n");
		js.append(" \r\n");
		js.append("   this.getChild=getEditorChild;\r\n");
		js.append("   this.getIsReady=getSearchStatus;\r\n");
		js.append("   this.setEditor=setEditorProperty;\r\n");
		js.append("   this.changeData=changeEditorData;\r\n");
		js.append("   this.setData=setEditorData;\r\n");
		js.append("   this.setDataByValue=setEditorDataByValue;\r\n");
		js.append("   this.changeFormula=changeEditorFormula;\r\n");
		js.append("   this.setDecimal=\"\";\r\n");
		js.append("}\r\n");
		js.append("function getEditorChild(flag)\r\n");
		js.append("{\r\n");
		js.append("   var child = window.document.getElementById(this.componentID);\r\n");
		js.append("   if(this.isChange&&flag==true&&child!=null)\r\n");
		js.append("       setDropDownData(this);\r\n");
		js.append("   if(this.isNoFixFormula)\r\n");
		js.append("       setEditorFormula(this);\r\n");
		js.append("   if(this.isClient&&flag==true&&child!=null){\r\n");
		js.append("       editor_setDecimal(this);\r\n");
		js.append("	  }\r\n");
		js.append("   return child;\r\n");
		js.append("}\r\n");
		js.append("function getSearchStatus()\r\n");
		js.append("{\r\n");
		js.append("   var isReady = false;\r\n");
		js.append("   var child = window.document.getElementById(this.componentID);\r\n");
		js.append("   if(child!=null&&child!=\"undefined\")\r\n");
		js.append("   {\r\n");
		js.append("   	var fillFinished=child.getAttribute(\"fillBackFinished\");\r\n");
		js.append("   	isReady=eval(fillFinished);\r\n");
		js.append("   }\r\n");
		js.append("   return isReady;\r\n");
		js.append("}\r\n");
		js.append("function setDropDownData(editor)\r\n");
		js.append("{\r\n");
		js.append("   var component = document.getElementById(editor.componentID);\r\n");
		js.append("   if(component==null) return;\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   var str=editor.gridID+\"_\"+editor.columnName+\"_\"+editRow.rowIndex+\"_\";\r\n");
		js.append("   //var array=editor.data[editRow];\r\n");
		js.append("   var filterHidden=null;\r\n");
		js.append("   if(editor.dataColumn[0].trim()!=\"\");\r\n");
		js.append("   	filterHidden = document.getElementById(str+editor.dataColumn[0]+\"_hidden\");\r\n");
		js.append("   var textHidden = document.getElementById(str+editor.dataColumn[1]+\"_hidden\");\r\n");
		js.append("   var valueHidden = document.getElementById(str+editor.dataColumn[2]+\"_hidden\");\r\n");
		js.append("   if(filterHidden==null)\r\n");
		js.append("   	DropDown_setDataSource(editor.componentID,null,ListManager_Analysis(textHidden),ListManager_Analysis(valueHidden),null,-1);\r\n");
		js.append("   else\r\n");
		js.append("   	DropDown_setDataSource(editor.componentID,ListManager_Analysis(filterHidden),ListManager_Analysis(textHidden),ListManager_Analysis(valueHidden),null,-1);\r\n");
		js.append("}\r\n");
		js.append("function editor_setDecimal(editor)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.setDecimal.trim()!='') eval(editor.setDecimal);\r\n");
		js.append("}\r\n");
		js.append("function setEditorFormula(editor)\r\n");
		js.append("{\r\n");
		js.append("   if(!editor.isNoFixFormula)return;\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var formulaHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\");\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   var key=ListManager_Get(editRow.rowIndex,ffHidden);\r\n");
		js.append("   var tmp=HashtableManager_Get(key,formulaHidden);\r\n");
		js.append("   if(tmp!=null && tmp.trim()!=\"\")\r\n");
		js.append("       editor.formula=tmp.split(\",\");\r\n");
		js.append("}\r\n");
		js.append("function changeEditorFormula(flag,editRowIndex,totalRows)\r\n");
		js.append("{\r\n");
		js.append("   var editor=this;\r\n");
		js.append("   var editRow=editRowIndex;\r\n");
		js.append("   var rows=totalRows;\r\n");
		js.append("   if(parseInt(flag)==-1)\r\n");
		js.append("   {\r\n");
		js.append("       removeFormulaFlag(editor);\r\n");
		js.append("   }\r\n");
		js.append("   else\r\n");
		js.append("   {\r\n");
		js.append("       if(parseInt(flag)==1)\r\n");
		js.append("       { \r\n");
		js.append("           if(parseInt(editRow+1)!=rows)\r\n");
		js.append("       		   insertFormulaFlag(editor);\r\n");
		js.append("       	  else\r\n");
		js.append("       		   addFormulaFlag(editor);\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function setFormulaFlag(editor,editRowIndex,reverse)\r\n");
		js.append("{\r\n");
		js.append("   if(!editor.isNoFixFormula)return;\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var cValue;\r\n");
		js.append("   var pValue;\r\n");
		js.append("   var pValue=ListManager_Get(parseInt(editRowIndex-1),ffHidden);\r\n");
		js.append("   var cValue=ListManager_Get(editRowIndex,ffHidden);\r\n");
		js.append("   if(reverse)\r\n");
		js.append("       ListManager_Set(parseInt(editRowIndex-1),cValue,ffHidden);\r\n");
		js.append("   else\r\n");
		js.append("       ListManager_Set(editRowIndex,pValue,ffHidden);\r\n");
		js.append("}\r\n");
		js.append("function Editor_changeFixFormula(gridID,columnName,formula)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.isNoFixFormula)return;\r\n");
		js.append("   var editor=getEditorByProperty(gridID,\"columnName\",columnName);\r\n");
		js.append("   if(formula==null||formula.trim()==\"\")return;\r\n");
		js.append("   formula = formula.replace(/;/g,\",\");\r\n");
		js.append("   formula = formula.replace(/\"/g,\"\");\r\n");
		js.append("   editor.formula=tmp.split(\",\");\r\n");
		js.append("}\r\n");
		js.append("function Editor_changeFormulaFlag(gridID,columnName,key)\r\n");
		js.append("{\r\n");
		js.append("   var editor=getEditorByProperty(gridID,\"columnName\",columnName);\r\n");
		js.append("   changeFormulaFlag(editor,key);\r\n");
		js.append("}\r\n");
		js.append("function changeFormulaFlag(editor,key)\r\n");
		js.append("{\r\n");
		js.append("   if(!editor.isNoFixFormula)return;\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   ListManager_Set(editRow.rowIndex,key,ffHidden);\r\n");
		js.append("   setEditorFormula(editor);\r\n");
		js.append("}\r\n");
		js.append("function addFormulaFlag(editor)\r\n");
		js.append("{\r\n");
		js.append("   if(!editor.isNoFixFormula)return;\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   var key=ListManager_Get(parseInt(editRow.rowIndex-1),ffHidden);\r\n");
		js.append("   ListManager_Add(key,true,ffHidden);\r\n");
		js.append("   setEditorFormula(editor);\r\n");
		js.append("}\r\n");
		js.append("function insertFormulaFlag(editor)\r\n");
		js.append("{\r\n");
		js.append("   if(!editor.isNoFixFormula)return;\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   var key=ListManager_Get(parseInt(editRow.rowIndex),ffHidden);\r\n");
		js.append("   ListManager_Insert(editRow.rowIndex,key,ffHidden);\r\n");
		js.append("   setEditorFormula(editor);\r\n");
		js.append("}\r\n");
		js.append("function removeFormulaFlag(editor)\r\n");
		js.append("{\r\n");
		js.append("   if(!editor.isNoFixFormula)return;\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   ListManager_Remove(editRow.rowIndex,ffHidden);\r\n");
		js.append("   setEditorFormula(editor);\r\n");
		js.append("}\r\n");
		js.append("function Editor_addFormula(gridID,columnName,key,object)\r\n");
		js.append("{\r\n");
		js.append("   if(object==null||object.trim()==\"\")return;\r\n");
		js.append("   object = object.replace(/;/g,\",\");\r\n");
		js.append("   object = object.replace(/\"/g,\"\");\r\n");
		js.append("   var editor=getEditorByProperty(gridID,\"columnName\",columnName);\r\n");
		js.append("   var ffHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\"+\"_hidden\");\r\n");
		js.append("   var formulaHidden=window.document.getElementById(editor.gridID+\"_\"+editor.columnName+\"_formula\");\r\n");
		js.append("   HashtableManager_Add(key,object,formulaHidden);\r\n");
		js.append("   var grid=window.document.getElementById(editor.gridID);\r\n");
		js.append("   var editRow=WebGrid_getExistEditRow(grid);\r\n");
		js.append("   ListManager_Set(editRow.rowIndex,key,ffHidden);\r\n");
		js.append("   setEditorFormula(editor);\r\n");
		js.append("}\r\n");
		js.append("function Editor_getArrayListManager(id)\r\n");
		js.append("{\r\n");
		js.append("   return document.getElementById(id);\r\n");
		js.append("}\r\n");
		js.append("function Editor_getHashMapManager(id)\r\n");
		js.append("{\r\n");
		js.append("   return document.getElementById(id);\r\n");
		js.append("}\r\n");
		js.append("var nextEditorIndex=-1;\r\n");		
		js.append("function setEditorProperty(Property,Value)\r\n");
		js.append("{\r\n");
		js.append("   if(this)\r\n");
		js.append("   {\r\n");
		js.append("       if(this.name.trim()!=\"\")\r\n");
		js.append("       {\r\n");
		js.append("           var str=this.name+\".\"+Property+\"=\"+Value;\r\n");
		js.append("           eval(str);\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function editor_keydown(grid,event) \r\n");
		js.append("{\r\n");
		js.append("   var srcObj;\r\n");
		js.append("   if(document.all)\r\n");
		js.append("       srcObj=event.srcElement;\r\n");
		js.append("   else\r\n");
		js.append("       srcObj=event.target;\r\n");
		js.append("   if(event.keyCode!=38&&event.keyCode!=40&&event.keyCode!=13)return;\r\n");
		js.append("   var cell=getTableCell(srcObj,grid.id);\r\n");
		js.append("   if(cell==null)return;     \r\n");
		js.append("   var currCell=cell.cellIndex;\r\n");
		js.append("   var currEditorIndex=WebGrid_getEditorIndexByColumnRealIndex(grid.id,currCell);\r\n");
		js.append("   var Editors =eval(grid.id+\"_Editors\");  \r\n");
		js.append("   var editor=Editors[currEditorIndex];\r\n");
		js.append("   var editorIndex=currEditorIndex;\r\n");
		js.append("   if(editor!=null)\r\n");
		js.append("   	editorIndex= editor.editorIndex;\r\n");
		js.append("   if(event.keyCode==38)\r\n");
		js.append("   {\r\n");
		js.append("       EditorOnChange(editorIndex,grid.id);\r\n");
		js.append("   	  WebGrid_Up(grid.id);\r\n");
		js.append("       WebGrid_setFocus(grid,null);\r\n");
		js.append("       return false;\r\n");
		js.append("   }\r\n");
		js.append("   if(event.keyCode==40)\r\n");
		js.append("   {\r\n");
		js.append("       EditorOnChange(editorIndex,grid.id);\r\n");
		js.append("   	   WebGrid_Down(grid.id);\r\n");
		js.append("       WebGrid_setFocus(grid,null);\r\n");
		js.append("       return false;\r\n");
		js.append("   }\r\n");
		js.append("   if(event.keyCode==13)\r\n");
		js.append("   {\r\n");
		js.append("       if(srcObj.type!=\"submit\"&&srcObj.type!=\"button\")\r\n");
		js.append("       {\r\n");
		js.append("       	  var control = null;\r\n");
		js.append("           if(editor)\r\n");
		js.append("           	  control=editor.getChild();\r\n");
		js.append("           var cells = grid.rows[0].cells.length;\r\n");
		js.append("           if(currCell==cells-1)\r\n");
		js.append("           {\r\n");
		js.append("              EditorOnChange(editorIndex,grid.id);\r\n");
		js.append("              WebGrid_Confirm(grid.id);\r\n");
		js.append("              WebGrid_setFocus(grid,null);             \r\n");
		js.append("       		  return false;\r\n");
		js.append("           }\r\n");
		js.append("           else\r\n");
		js.append("               nextEditorIndex=WebGrid_getEditorIndexByColumnRealIndex(grid.id,currCell+1);//event.keyCode=9;\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function getTableCell(obj,gridID)\r\n");
		js.append("{\r\n");
		js.append("   var tmpObj=obj;\r\n");
		js.append("   var retObj;\r\n");
		js.append("   while(tmpObj&&tmpObj.tagName.toUpperCase()!=\"TABLE\")\r\n");
		js.append("   {\r\n");
		js.append("       if(tmpObj.tagName.toUpperCase()==\"TD\")\r\n");
		js.append("           retObj=tmpObj;\r\n");
		js.append("       tmpObj=tmpObj.parentElement;\r\n");
		js.append("   }\r\n");
		js.append("   if(tmpObj!=null)\r\n");
		js.append("   {\r\n");
		js.append("       if(tmpObj.id==gridID)\r\n");
		js.append("       {\r\n");
		js.append("           return retObj;\r\n");
		js.append("       }\r\n");
		js.append("       else\r\n");
		js.append("       {\r\n");
		js.append("           return getTableCell(tmpObj.parentElement,gridID);\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("   else\r\n");
		js.append("   {\r\n");
		js.append("       return null;\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function getEditorByProperty(gridID,pName,pValue)\r\n");
		js.append("{\r\n");
		js.append("   var editors = eval(gridID+\"_Editors\");\r\n");
		js.append("   if(editors==null||editors==\"undefined\")\r\n");
		js.append("       return null;   var editor = null;\r\n");
		js.append("   for(var i=0;i<editors.length;i++)\r\n");
		js.append("   {\r\n");
		js.append("       if(eval(\"editors[\"+i+\"].\"+pName)==pValue)\r\n");
		js.append("       {\r\n");
		js.append("           editor=editors[i];\r\n");
		js.append("           break;\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("   return editor;\r\n");
		js.append("}\r\n");
		js.append("function Editor_getEditor(gridID,columnName)\r\n");
		js.append("{\r\n");
		js.append("   return getEditorByProperty(gridID,\"columnName\",columnName);\r\n");
		js.append("}\r\n");
		js.append("function changeEditorData(flag,editRowIndex,totalRows)\r\n");
		js.append("{\r\n");
		js.append("   var editor=this;\r\n");
		js.append("   var editRow=editRowIndex;\r\n");
		js.append("   var rows=totalRows;\r\n");
		js.append("   if(parseInt(flag)==-1)\r\n");
		js.append("   {\r\n");
		js.append("       for(var i=editRow;i<rows;i++)\r\n");
		js.append("       {\r\n");
		js.append("            swapEditorData(editor,i+1,true);\r\n");
		js.append("       }\r\n");
		js.append("       removeEditorData(editor,rows);\r\n");
		js.append("   }\r\n");
		js.append("   else\r\n");
		js.append("   {\r\n");
		js.append("       if(parseInt(flag)==1)\r\n");
		js.append("       { \r\n");
		js.append("           createEditorData(editor,parseInt(rows-1),null);\r\n");
		js.append("           if(parseInt(editRow+1)!=rows)\r\n");
		js.append("           {\r\n");
		js.append("                for(var i=rows-1;i>editRow;i--)\r\n");
		js.append("                {\r\n");
		js.append("                    swapEditorData(editor,i,false);\r\n");
		js.append("                }\r\n");
		js.append("                clearEditorData(editor,editRow,false);\r\n");
		js.append("           }\r\n");
		js.append("       }\r\n");
		js.append("       else\r\n");
		js.append("       {\r\n");
		js.append("            clearEditorData(editor,editRow,true);\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function clearEditorData(editor,editRow,flag)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.dataColumn!==null && editor.dataColumn!=\"undefined\")\r\n");
		js.append("   {\r\n");
		js.append("      var str=editor.gridID+\"_\"+editor.columnName+\"_\";\r\n");
		js.append("      var arrayLength=editor.dataColumn.length;\r\n");
		js.append("      var obj;\r\n");
		js.append("      for(var i=0;i<arrayLength;i++)\r\n");
		js.append("      {\r\n");
		js.append("          obj=document.getElementById(str+editRow+\"_\"+editor.dataColumn[i]+\"_hidden\");\r\n");
		js.append("          if(obj)\r\n");
		js.append("              ListManager_RemoveAll(obj);\r\n");
		js.append("      }\r\n");
		js.append("      if(flag)\r\n");
		js.append("      	setDropDownData(editor);\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function swapEditorData(editor,editRow,reverse)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.dataColumn!==null && editor.dataColumn!=\"undefined\")\r\n");
		js.append("   {\r\n");
		js.append("       var str=editor.gridID+\"_\"+editor.columnName+\"_\";\r\n");
		js.append("       var arrayLength=editor.dataColumn.length;\r\n");
		js.append("       var pObjs=new Array(arrayLength);\r\n");
		js.append("       var cObjs=new Array(arrayLength);\r\n");
		js.append("       for(var i=0;i<arrayLength;i++)\r\n");
		js.append("       {\r\n");
		js.append("           pObjs[i]=document.getElementById(str+parseInt(editRow-1)+\"_\"+editor.dataColumn[i]+\"_hidden\");\r\n");
		js.append("           cObjs[i]=document.getElementById(str+editRow+\"_\"+editor.dataColumn[i]+\"_hidden\");\r\n");
		js.append("           if( pObjs[i] && cObjs[i])\r\n");
		js.append("           {\r\n");
		js.append("               if(reverse)\r\n");
		js.append("                   pObjs[i].value=cObjs[i].value;\r\n");
		js.append("               else\r\n");
		js.append("                   cObjs[i].value=pObjs[i].value;\r\n");
		js.append("           }\r\n");
		js.append("       }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function createEditorData(editor,editRow,values)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.dataColumn!==null && editor.dataColumn!=\"undefined\")\r\n");
		js.append("   {\r\n");
		js.append("       var str=editor.gridID+\"_\"+editor.columnName+\"_\";\r\n");
		js.append("       var arrayLength=editor.dataColumn.length;\r\n");
		js.append("       var obj;\r\n");
		js.append("       for(var i=0;i<arrayLength;i++)\r\n");
		js.append("       {\r\n");
		js.append("           if(editor.dataColumn!=null&&editor.dataColumn[i]!=\"\")\r\n");
		js.append("           {\r\n");
		js.append("               obj=document.createElement(\"<input type=\\\"hidden\\\" id=\"+str+editRow+\"_\"+editor.dataColumn[i]+\"_hidden value=\\\"\\\">\");\r\n");
		js.append("               ListManager_RemoveAll(obj);\r\n");
		js.append("               document.getElementsByTagName(\"body\")[0].appendChild(obj);\r\n");
		js.append("               if(values!=null && values[i]!=null && document.getElementById(values[i]))\r\n");
		js.append("                   obj.value=document.getElementById(values[i]).value;\r\n");
		js.append("           }\r\n");
		js.append("       }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function removeEditorData(editor,row)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.dataColumn!=null && editor.dataColumn!=\"undefined\")\r\n");
		js.append("   {\r\n");
		js.append("       var str=editor.gridID+\"_\"+editor.columnName+\"_\";\r\n");
		js.append("       var arrayLength=editor.dataColumn.length;\r\n");
		js.append("       var obj;\r\n");
		js.append("       for(var i=0;i<arrayLength;i++)\r\n");
		js.append("       {\r\n");
		js.append("           if(editor.dataColumn!=null&&editor.dataColumn[i]!=\"\")\r\n");
		js.append("           {\r\n");
		js.append("               obj=document.getElementById(str+row+\"_\"+editor.dataColumn[i]+\"_hidden\");\r\n");
		js.append("               if(obj)\r\n");
		js.append("               	  obj.parentNode.removeChild(obj);\r\n");
		js.append("           }\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function setEditorData(editRowIndex,filterObj,textObj,valueObj)\r\n");
		js.append("{\r\n");
		js.append("   var values=new Array(3);\r\n");
		js.append("   if(filterObj==null)\r\n");
		js.append("   	values[0]=\"\";\r\n");
		js.append("   else\r\n");
		js.append("   	values[0]=filterObj.value;\r\n");
		js.append("   if(textObj==null)\r\n");
		js.append("   	values[1]=\"\";\r\n");
		js.append("   else\r\n");
		js.append("   	values[1]=textObj.value;\r\n");
		js.append("   if(valueObj==null)\r\n");
		js.append("   	values[2]=\"\";\r\n");
		js.append("   else\r\n");
		js.append("   	values[2]=valueObj.value;\r\n");
		js.append("   setEditorDataByValues(this,editRowIndex,values);\r\n");
		js.append("}\r\n");
		js.append("function setEditorDataByValue(editRowIndex,filterValue,textValue,valueValue)\r\n");
		js.append("{\r\n");
		js.append("   var values=[filterValue,textValue,valueValue];\r\n");
		js.append("   setEditorDataByValues(this,editRowIndex,values)  ;\r\n");
		js.append("}\r\n");
		js.append("function setEditorDataByValues(editor,editRowIndex,values)\r\n");
		js.append("{\r\n");
		js.append("   if(editor.dataColumn!==null && editor.dataColumn!=\"undefined\")\r\n");
		js.append("   {\r\n");
		js.append("       var str=editor.gridID+\"_\"+editor.columnName+\"_\";\r\n");
		js.append("       var arrayLength=editor.dataColumn.length;\r\n");
		js.append("       var obj;\r\n");
		js.append("       var valueObj;\r\n");
		js.append("       for(var i=0;i<arrayLength;i++)\r\n");
		js.append("       {\r\n");
		js.append("           if(editor.dataColumn!=null&&editor.dataColumn[i]!=\"\")\r\n");
		js.append("           {\r\n");
		js.append("               obj=document.getElementById(str+editRowIndex+\"_\"+editor.dataColumn[i]+\"_hidden\");\r\n");
		js.append("               if(obj)\r\n");
		js.append("               {\r\n");
		js.append("                   ListManager_RemoveAll(obj);\r\n");
		js.append("                   if(values[i]!=null)\r\n");
		js.append("                   {\r\n");
		js.append("                   	obj.value=values[i];\r\n");
		js.append("                   }\r\n");
		js.append("               }\r\n");
		js.append("           }\r\n");
		js.append("       }\r\n");
		js.append("       setDropDownData(editor);\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("String.prototype.trim = function()\r\n");
		js.append("{\r\n");
		js.append("   return this.replace(/(^\\s*)|(\\s*$)/g, \"\");\r\n");
		js.append("}\r\n");
		js.append("String.prototype.leftTrim = function()\r\n");
		js.append("{\r\n");
		js.append("   return this.replace(/(^\\s*)/g, \"\");\r\n");
		js.append("}\r\n");
		js.append("String.prototype.rightTrim = function()\r\n");
		js.append("{\r\n");
		js.append("   return this.replace(/(\\s*$)/g, \"\");\r\n");
		js.append("}");
		jstool.js = js;
		return jstool.getComponentJS();
	}
}
