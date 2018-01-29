package com.born.insurance.webui.control;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class WebGridCustomJS {
    public WebGridCustomJS() {
    }
    public static String getWebGridCustomJS()
    {
        JSTool jstool = new JSTool("WebGridCustomJS.js");
        java.lang.StringBuffer js = jstool.js;
		if(!jstool.util.isUpdateJsEveryTime())
		{
			return jstool.getComponentJS();
		}
        js.append("// JScript source code\r\n");
        js.append("function WebGrid_CustomConfirm(gridId)\r\n");
        js.append("{\r\n");
        js.append("	if(!WebGrid_RowCheckoutControl(gridId))\r\n");
        js.append("	{\r\n");
        js.append("		return;\r\n");
        js.append("	}\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("    var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("	if(editRow!=null)\r\n");
        js.append("	{\r\n");
        js.append("		var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("		for(var i=0;i<Editors.length;i++)\r\n");
        js.append("		{\r\n");
        js.append(
            "		   if(WebGrid_getColumnRealIndexByEditorIndex(gridId,i)>=0)\r\n");
        js.append("			EditorOnChange(i,gridId);\r\n");
        js.append("		}\r\n");
        js.append("	}\r\n");
        js.append("	else\r\n");
        js.append("	{\r\n");
        js.append("		if(table.getAttribute(\"ClientAddNew\")==\"false\")\r\n");
        js.append("		{\r\n");
        js.append("			return;\r\n");
        js.append("		}\r\n");
        js.append("		var objheader=document.getElementById(gridId+\"_head\");\r\n");
        js.append(
            "		var objRow=WebGrid_addNewRow(table,null,table.rows.length);\r\n");
        js.append("		WebGrid_insertNewData(table,table.rows.length);\r\n");
        js.append("		table.setAttribute(\"oldItemID\",objRow.id);\r\n");
        js.append("		var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("		for(var i=0;i<Editors.length;i++)\r\n");
        js.append("		{\r\n");
        js.append("			if(WebGrid_getColumnRealIndexByEditorIndex(gridId,i)>=0)\r\n");
        js.append("			   EditorOnChange(i,gridId);\r\n");
        js.append("\r\n");
        js.append("		}\r\n");
        js.append("		table.removeAttribute(\"oldItemID\");\r\n");
        js.append("		var div=document.getElementById(gridId+\"_div\");\r\n");
        js.append("		div.scrollTop=table.offsetHeight;\r\n");
        js.append("	}\r\n");
        js.append("	WebGrid_setFocus(table,null);\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_Modify(gridId)\r\n");
        js.append("{\r\n");
        js.append("	if(!WebGrid_RowCheckoutControl(gridId))\r\n");
        js.append("	{\r\n");
        js.append("	    WebGrid_setErrorEditorFocus(table);\r\n");
        js.append("		return;\r\n");
        js.append("	}\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("    var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("	if(editRow!=null)\r\n");
        js.append("	{\r\n");
        js.append("		var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("		for(var i=0;i<Editors.length;i++)\r\n");
        js.append("		{\r\n");
        js.append("			if(WebGrid_getColumnRealIndexByEditorIndex(gridId,i)>=0)\r\n");
        js.append("			    EditorOnChange(i,gridId);\r\n");
        js.append("		}\r\n");
        js.append("	}\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_AddNew(gridId)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("	if(!WebGrid_RowCheckoutControl(gridId))\r\n");
        js.append("	{\r\n");
        js.append("		WebGrid_setErrorEditorFocus(table);\r\n");
        js.append("		return;\r\n");
        js.append("	}\r\n");
        js.append("	var objheader=document.getElementById(gridId+\"_head\");\r\n");
        js.append("	WebGrid_insertNewData(table,table.rows.length);\r\n");
        js.append(
            "	var objRow=WebGrid_addNewRow(table,null,table.rows.length);\r\n");
        js.append("	var oldItemID=table.getAttribute(\"oldItemID\");\r\n");
        js.append("	table.setAttribute(\"oldItemID\",objRow.id);\r\n");
        js.append("	var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("	for(var i=0;i<Editors.length;i++)\r\n");
        js.append("	{\r\n");
        js.append("		if(WebGrid_getColumnRealIndexByEditorIndex(gridId,i)>=0)\r\n");
        js.append("			EditorOnChange(i,gridId);\r\n");
        js.append("\r\n");
        js.append("	}\r\n");
        js.append("	table.removeAttribute(\"oldItemID\");\r\n");
        js.append("	if(oldItemID!=null)\r\n");
        js.append("	{\r\n");
        js.append("		table.setAttribute(\"oldItemID\",oldItemID);\r\n");
        js.append("	}\r\n");
        js.append("	var div=document.getElementById(gridId+\"_div\");\r\n");
        js.append("	div.scrollTop=table.offsetHeight;\r\n");
        js.append("	WebGrid_CustomCancel(gridId);\r\n");
        js.append("	WebGrid_setFocus(table,null);\r\n");
        js.append("\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_CustomDelete(gridId)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("    var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("    if(editRow!=null)\r\n");
        js.append("    {\r\n");
        js.append("		var deleteIndex=editRow.rowIndex\r\n");
        js.append("		WebGrid_deleteData(table,deleteIndex);\r\n");
        js.append("		table.deleteRow(deleteIndex);\r\n");
        js.append("		for(var i=table.rows.length-1;i>=deleteIndex;i--)\r\n");
        js.append("		{\r\n");
        js.append("			table.rows[i].id=table.id+\"_Row_\"+i;\r\n");
        js.append("		}\r\n");
        js.append("		table.removeAttribute(\"oldItemID\");\r\n");
        js.append("\r\n");
        js.append("		if(deleteIndex<table.rows.length)\r\n");
        js.append("		{\r\n");
        js.append(
            "	        WebGrid_CustomRowOnclick(gridId,table.rows[deleteIndex]);\r\n");
        js.append("	    }\r\n");
        js.append("	    else if(deleteIndex>0)\r\n");
        js.append("	    {\r\n");
        js.append(
            "	        WebGrid_CustomRowOnclick(gridId,table.rows[deleteIndex-1]);\r\n");
        js.append("	    }\r\n");
        js.append("	    else\r\n");
        js.append("	    {\r\n");
        js.append("	       	WebGrid_CustomCancel(gridId);\r\n");
        js.append("	    }\r\n");
        js.append("	    WebGrid_setFocus(table,null);\r\n");
        js.append("\r\n");
        js.append("    }\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_CustomInsert(gridId)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("    var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("    if(!WebGrid_RowCheckoutControl(gridId))\r\n");
        js.append("	{\r\n");
        js.append("		return;\r\n");
        js.append("	}\r\n");
        js.append("	var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("    if(editRow!=null)\r\n");
        js.append("    {\r\n");
        js.append("		WebGrid_insertNewData(table,editRow.rowIndex);\r\n");
        js.append(
            "		var objRow=WebGrid_addNewRow(table,editRow,editRow.rowIndex);\r\n");
        js.append("		table.setAttribute(\"oldItemID\",objRow.id);\r\n");
        js.append("		for(var i=0;i<Editors.length;i++)\r\n");
        js.append("		{\r\n");
        js.append("			EditorOnChange(i,gridId);\r\n");
        js.append("		}\r\n");
        js.append("		table.removeAttribute(\"oldItemID\");\r\n");
        js.append("    }\r\n");
        js.append("    else\r\n");
        js.append("    {\r\n");
        js.append("		if(table.getAttribute(\"ClientAddNew\")==\"false\")\r\n");
        js.append("		{\r\n");
        js.append("			return;\r\n");
        js.append("		}\r\n");
        js.append("		var objheader=document.getElementById(gridId+\"_head\");\r\n");
        js.append("		WebGrid_insertNewData(table,table.rows.length);\r\n");
        js.append(
            "		var objRow=WebGrid_addNewRow(table,null,table.rows.length);\r\n");
        js.append("		table.setAttribute(\"oldItemID\",objRow.id);\r\n");
        js.append("		for(var i=0;i<Editors.length;i++)\r\n");
        js.append("		{\r\n");
        js.append("			if(WebGrid_getColumnRealIndexByEditorIndex(gridId,i)>=0)\r\n");
        js.append("			   EditorOnChange(i,gridId);\r\n");
        js.append("		}\r\n");
        js.append("		table.removeAttribute(\"oldItemID\");\r\n");
        js.append("		var div=document.getElementById(gridId+\"_div\");\r\n");
        js.append("		div.scrollTop=table.offsetHeight;\r\n");
        js.append("	}\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_CustomCancel(gridId)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("	var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("	for(var i=0;i<Editors.length;i++)\r\n");
        js.append("	{\r\n");
        js.append("		var control=Editors[i].getChild();\r\n");
        js.append("		var editor=Editors[i];\r\n");
        js.append("		if(control!=null)\r\n");
        js.append("		{\r\n");
        js.append("			var setValue=control.getAttribute(\"setValue\");\r\n");
        js.append("			var setText=control.getAttribute(\"setText\");\r\n");
        js.append("			if(editor.dataField!=\"\"&&editor.dataValue==\"\"&&setValue!=null&&!editor.isDrop)\r\n");
        js.append("			{\r\n");
        js.append("				eval(setValue+\"('\"+control.id+\"','')\");\r\n");
        js.append("			}\r\n");
        js.append(
            "			else if(editor.dataField!=\"\"&&editor.dataValue==\"\"&&setValue==null)\r\n");
        js.append("			{\r\n");
        js.append("					WebGrid_setInnerText(control,\"\");\r\n");
        js.append("			}\r\n");
        js.append("			else if(editor.dataField!=\"\"&&editor.dataValue!=\"\"&&setValue!=null&&setText!=null&&!editor.isDrop)\r\n");
        js.append("			{\r\n");
        js.append("					eval(setText+\"('\"+control.id+\"','')\");\r\n");
        js.append("					var hidden=WebGrid_getColumnValue(table.id,i);\r\n");
        js.append("					eval(setValue+\"('\"+control.id+\"','')\");\r\n");
        js.append("			}\r\n");
        js.append("			else if(editor.isDrop)\r\n");
        js.append("			{\r\n");
        js.append("					eval(setText+\"('\"+control.id+\"','')\");\r\n");
        js.append("\r\n");
        js.append("			}\r\n");
        js.append("		}\r\n");
        js.append("	}\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_RowCheckoutControl(gridId)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("	var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("	for(var i=0;i<Editors.length;i++)\r\n");
        js.append("	{\r\n");
        js.append("		var editor=Editors[i];\r\n");
        js.append("		var control=editor.getChild();\r\n");
        js.append("		if(control==null)\r\n");
        js.append("		{\r\n");
        js.append("			continue;\r\n");
        js.append("		}\r\n");
        js.append("		var getValue=control.getAttribute(\"getValue\");\r\n");
        js.append("		var getText=control.getAttribute(\"getText\");\r\n");
        js.append("		var viewText=\"\";\r\n");
        js.append("		if(editor.dataField!=\"\"&&editor.dataValue==\"\"&&getValue!=null&&!editor.isDrop)\r\n");
        js.append("		{\r\n");
        js.append("			var hidden=WebGrid_getColumnText(gridId,i);\r\n");
        js.append("			viewText=eval(getValue+\"('\"+control.id+\"')\");\r\n");
        js.append("		}\r\n");
        js.append(
            "		else if(editor.dataField!=\"\"&&editor.dataValue==\"\"&&getValue==null)\r\n");
        js.append("		{\r\n");
        js.append("				continue;\r\n");
        js.append("		}\r\n");
        js.append("		else if(editor.dataField!=\"\"&&editor.dataValue!=\"\"&&getValue!=null&&getText!=null&&!editor.isDrop)\r\n");
        js.append("		{\r\n");
        js.append("				var hidden=WebGrid_getColumnText(gridId,i);\r\n");
        js.append("				viewText=eval(getText+\"('\"+control.id+\"')\");\r\n");
        js.append("		}\r\n");
        js.append("		else if(editor.isDrop)\r\n");
        js.append("		{\r\n");
        js.append("			var hidden=WebGrid_getColumnText(gridId,i);\r\n");
        js.append("			viewText=eval(getText+\"('\"+control.id+\"')\");\r\n");
        js.append("		}\r\n");
        js.append("		if(editor.isNecessary==true)\r\n");
        js.append("		{\r\n");
        js.append("			if(viewText==\"0\"||viewText==\"\")\r\n");
        js.append("			{\r\n");
        js.append("				alert(editor.headerName+\" ����Ϊ�գ�\");\r\n");
        js.append("				table.setAttribute(\"errorEditorIndex\",i)\r\n");
        js.append("				return false;\r\n");
        js.append("			}\r\n");
        js.append("		}\r\n");
        js.append("	}\r\n");
        js.append("	return true;\r\n");
        js.append("}\r\n");
        js.append("\r\n");
        js.append("function WebGrid_SetCustomGridCellValue(gridId,rowIndex,dataField,hiddenValue)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append(
            "	var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
        js.append("	var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("	if(editor.dataValue!=\"\")\r\n");
        js.append("	{\r\n");
        js.append(
            "		var hidden=WebGrid_getColumnValue(gridId,editor.editorIndex);\r\n");
        js.append("		ListManager_Set(rowIndex,hiddenValue,hidden);\r\n");
        js.append("	}\r\n");
        js.append("}\r\n");
        js.append(
            "function WebGrid_GetCustomGridCellValue(gridId,rowIndex,dataField)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append(
            "	var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
        js.append("	var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("	if(editor.dataValue!=\"\")\r\n");
        js.append("	{\r\n");
        js.append(
            "		var hidden=WebGrid_getColumnValue(gridId,editor.editorIndex);\r\n");
        js.append("		return ListManager_Get(rowIndex,hidden);\r\n");
        js.append("	}\r\n");
        js.append("}\r\n");
        js.append(
            "function WebGrid_GetCustomGridCellText(gridId,rowIndex,dataField)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append(
            "	var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
        js.append("	var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("	if(editor.dataField!=\"\")\r\n");
        js.append("	{\r\n");
        js.append(
            "		var hidden=WebGrid_getColumnText(gridId,editor.editorIndex);\r\n");
        js.append("		return ListManager_Get(rowIndex,hidden);\r\n");
        js.append("	}\r\n");
        js.append("}\r\n");
        js.append(
            "function WebGrid_SetCustomGridCellText(gridId,rowIndex,dataField,viewtext)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("	var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append(
            "	var columnRealIndex=WebGrid_getColumnRealIndex(gridId,dataField);\r\n");
        js.append(
            "	var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
        js.append("	if(columnRealIndex!=-1)\r\n");
        js.append("	{\r\n");
        js.append("		WebGrid_setInnerText(table.rows[rowIndex].cells[columnRealIndex],viewtext);\r\n");
        js.append("	}\r\n");
        js.append(
            "	var hidden=WebGrid_getColumnText(gridId,editor.editorIndex);\r\n");
        js.append("	ListManager_Set(rowIndex,viewtext,hidden);\r\n");
        js.append("}\r\n");
        js.append("function WebGrid_CustomRowOnclick(gridId,rowObj)\r\n");
        js.append("{\r\n");
        js.append("	var table=document.getElementById(gridId);\r\n");
        js.append("	var editRow=WebGrid_getExistEditRow(table);\r\n");
        js.append("	if(editRow!=rowObj)\r\n");
        js.append("	{\r\n");
        js.append("	    ListGrid_RowOnSelect(rowObj,table.id);\r\n");
        js.append("	}\r\n");
        js.append("	var Editors =eval(gridId+\"_Editors\");\r\n");
        js.append("	for(var i=0;i<Editors.length;i++)\r\n");
        js.append("	{\r\n");
        js.append("		if(WebGrid_getColumnRealIndexByEditorIndex(gridId,i)>=0)\r\n");
        js.append("		{			\r\n");
        js.append("			if(null!=Editors[i].getChild())\r\n");
        js.append("			{\r\n");
        js.append("				//var columnRealIndex=WebGrid_getColumnRealIndex(gridId,Editors[i].dataField);\r\n");
        js.append("				WebGrid_setViewData(Editors[i],table,rowObj,i);\r\n");
        js.append("			}\r\n");
        js.append("		}\r\n");
        js.append("	}\r\n");
        js.append(
            "	var onEditRowChange=table.getAttribute(\"oneditrowchange\");\r\n");
        js.append("	if(onEditRowChange!=null&&onEditRowChange!=\"\")\r\n");
        js.append("	{\r\n");
        js.append("		eval(onEditRowChange);\r\n");
        js.append("	}\r\n");
        js.append("}");

        return jstool.getComponentJS() + HashMapJS.getHashMapJS();
    }
}