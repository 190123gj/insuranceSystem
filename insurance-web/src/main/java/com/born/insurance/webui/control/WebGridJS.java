package com.born.insurance.webui.control;
public class WebGridJS {
  public WebGridJS() {
  }
  /**
   * <P><B>��ȡWebGrid�ͻ��˽ű�</B></P>
   * <P><B>WebGridȷ����ť���÷���</B></P>
   * function:WebGrid_Confirm(gridId)</p>
   * param gridId �����Id
   * <br>
   * <P><B>WebGrid���밴ť���÷���</B></P>
   * <B>function:WebGrid_Insert(gridId)</B><P>
   * param gridId �����Id
   * <br>
   * <P><B>WebGridȡ��ť���÷�������ո��У�</B></P>
   * <B>function��WebGrid_Cancel(gridId)</B><P>
   * param gridId �����Id
   * <br>
   *  <P><B>WebGridɾ��ť���÷�����ɾ��ǰ�У�</B></P>
   * <B>function��WebGrid_Delete(gridId)</B><P>
   * param gridId �����Id
   *  <br>
   * <P><B>WebGrid����е��÷���</B></P>
   * <p><B> WebGrid_RowOnclick(gridId,rowObj)</B></p>
   * param gridId �����Id
   * param rowObj ����ж���
   *  <br>
   * <P><B>WebGrid��У�鷽��</B></P>
   * <p><B> WebGrid_RowCheckout(gridId,rowObj)</B></p>
   * param gridId �����Id
   * param rowObj ����ж���
   *  <br>
   * <P><B>WebGrid�õ��༭�ж���</B></P>
   * <p><B> WebGrid_getExistEditRow(table)</B></p>
   * param table Ϊtable����table=document.getElementById(grid)
   * return Table Row �ж���
   *  <br>
   * <P><B>WebGridˢ�ºϼ�</B></P>
   * <p><B> WebGrid_refleshSum��grid��</B></p>
   * param �ͻ���table��table=document.getElementById(grid)�� ����
   * <br>
  *<P><B>������У�����ݣ�</B><br>
   * <P><B>WebGrid_insertNewRow(gridId,rowIndex)</B></P>
   *gridIdΪgrid��id;
   * rowIndex ������
   *<P><B>ɾ���У�ɾ������ݣ�</B><br>
   * <P><B>WebGrid_deleteRowByIndex(gridId,rowIndex)</B></P>
   *gridIdΪgrid��id;
   * rowIndex ������
   * <P><B>WebGridˢ��������</B></P>
   * <p><B> WebGrid_refleshAutoAcsend(gridId��</B></p>
   * param gridId �����Id
   * <br>
   * <P><B>WebGrid�õ�grid������</B></P>
   * <p><B> WebGrid_RowSize(gridId)</B></p>
   * param gridId �����Id
   * <br>
   * <P><B>WebGridȡ��grid�ı༭��</B></P>
   * <p><B>WebGrid_cancelEditRow(gridId)</B></p>
   * param gridId �����Id
   * <P><B>WebGrid�жϸ��������Ƿ�Ϊ����</B></P>
   * <p><B>WebGrid_isAllNull(table,rowIndex)</B></p>
   * param table ����Ŀͻ���table����
   * param rowIndex ������
   * return true or false
   * <br>
   * <br>
   * <P><B>WebGrid��ʾ�У���ݰ��ֶΣ�</B></P>
   * <p><B> WebGrid_InsertColumn(gridId,dataField)</B></p>
   * param gridId �����Id
   * param dataField ����İ��ֶ�
   *  <br>
   * <P><B>WebGrid���õ�Ԫ�ϸ�״̬���Ƿ��д��</B></P>WebGrid_setEditRowCellDisabled(gridId,dataField,disabled)</B></p>
   * <p><B> WebGrid_setCellDisabled(gridId,rowIndex,dataField,disabled)</B></p>
   * param gridId �����Id
   * param rowIndex ����Ӱ
   * param dataField �����
   * param disabled ��Ԫ��״ֵ̬
   *  <br>
   * <P><B>WebGrid���õ�Ԫ��״̬���Ƿ��д��</B></P>WebGrid_setEditRowCellDisabled(gridId,dataField,disabled)</B></P>
   * <p><B> WebGrid_setCellDisabled(gridId,rowIndex,dataField,disabled)</B></p>
   * param gridId �����Id
   * param rowIndex ������
   * param dataField �����
   * param disabled ��Ԫ��״ֵ̬
   * <br>
   * <P><B>WebGrid���ñ༭�е�Ԫ��״̬���Ƿ��д��</B></P>
   * <p><B>WebGrid_setEditRowCellDisabled(gridId,dataField,disabled)</B></P>
   * param gridId �����Id
   * param dataField ����İ��ֶ�
   * param disabled ״ֵ̬
   * <br>
   * WebGrid_SetEditorValue(tableID,dataField,hiddenValue)
   * <P><B>WebGrid���õ�Ԫ�Ƿ���䣨��ݰ��ֶΣ�</B></P>
   * <p><B>WebGrid_setCellIsNecessary(gridId,rowIndex,dataField,necessary)</B></P>
   * param gridId �����Id
   * param rowIndex ������
   * param dataField ����İ��ֶ�
   * param necessary ״ֵ̬
   *<br>
   * WebGrid_setEditRowCellNecessary
   * <P><B>WebGrid���ñ༭�е�Ԫ�Ƿ���䣨��ݰ��ֶ�</B></P>
   * <p><B>WebGrid_setEditRowCellNecessary(gridId,dataField,necessary)</B></P>
   * param gridId �����Id
   * param dataField ����İ��ֶ�
   * param necessary ״ֵ̬
   * <P><B>WebGrid����Editor��Ԫֵ����ֵ����ݰ��ֶΣ�</B></P>
   * <p><B>WebGrid_SetEditorValue(gridId,dataField,hiddenValue)</B></P>
   * param gridId �����Id
   * param dataField ����İ��ֶ�
   * param hiddenValue ֵ
   *<br>
   * <P><B>WebGrid����Editor��Ԫֵ��ʾֵ����ݰ��ֶΣ�</B></P>
   * <p><B>WebGrid_SetEditorText(gridId,dataField,text)</B></P>
   * param gridId �����Id
   * param dataField ����İ��ֶ�(�а��ֶ�)
   * param text ֵ
   * //���õ�Ԫ����ʾ��ݣ���ͬ��model��ݣ�
   *<P><B>WebGrid_SetGridCellText(gridId,rowIndex,dataField,text)</B></P>
   *gridIdΪgrid��id;
   *rowIndex ������
   *dataField�еİ��ֶ�
   *text �е���ʾֵ
   *<P><B>�õ���Ԫ����ʾ���</B></P>
   *<P><B>WebGrid_GetGridCellText(gridId,rowIndex,dataField)</B></P>
   *gridIdΪgrid��id;
   *rowIndex ������
   *dataField�еİ��ֶ�
   *<br>
   *<P><B>���õ�Ԫ�����������ݣ���ͬ��model��ݣ�</B></P>
   * <P><B>WebGrid_SetGridCellValue(gridId,rowIndex,dataField,hiddenValue)</B></P>
   *gridIdΪgrid��id;
   * rowIndex ������
   *dataField�еİ��ֶ�
   *hiddenValue�е���ʾֵ
   *�õ���Ԫ������������
   *  <P><B>WebGrid_GetGridCellValue(gridId,rowIndex,dataField)</B></P>
   *gridIdΪgrid��id;
   *rowIndex ������
   *dataField�еİ��ֶ�
   *
   * <br><B>���Զ���ģʽ��</B>
   * �޸İ�ť��onclick�¼�
   * WebGrid_Modify(gridId)
    *gridIdΪgrid��id;
   *��Ӱ�ť��onclick�¼�
   * function WebGrid_AddNew(gridId)
   *gridIdΪgrid��id;
   * @return
   */
  public static String getWebGridJS()
  {
      JSTool jstool = new JSTool("WebGrid.js");
      java.lang.StringBuffer  js=jstool.js;
	  if(!jstool.util.isUpdateJsEveryTime())
	  {
		  return jstool.getComponentJS();
	  }
      js.append("    function WebGrid_DefaultConfirm(gridId)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("        var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(editRow!=null)\r\n");
      js.append("		{\r\n");
      js.append("\r\n");
      js.append("			if(!WebGrid_RowCheckout(table.id,editRow))\r\n");
      js.append("			{\r\n");
      js.append("					return;\r\n");
      js.append("			}\r\n");
      js.append("			if(editRow.rowIndex<table.rows.length-2)\r\n");
      js.append("			{\r\n");
      js.append("				WebGrid_setNextSelectedRow(table,editRow);\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				if(table.getAttribute(\"ClientAddNew\")==\"false\")\r\n");
      js.append("				{\r\n");
      js.append("					return;\r\n");
      js.append("				}\r\n");
      js.append(
          "				var objRow=WebGrid_addNewRow(table,editRow,editRow.rowIndex+1);\r\n");
      js.append("				WebGrid_deleteEditRow(table,editRow);\r\n");
      js.append("				WebGrid_setSeletedRow(table,objRow,true);\r\n");
      js.append("				var div=document.getElementById(gridId+\"_div\");\r\n");
      js.append("				div.scrollTop=table.offsetHeight;\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("		WebGrid_setFocus(table,null);\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_DefaultInsert(gridId)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(table.getAttribute(\"ClientAddNew\")==\"false\")\r\n");
      js.append("		{\r\n");
      js.append("			return;\r\n");
      js.append("		}		\r\n");
      js.append("		if(editRow!=null)\r\n");
      js.append("		{\r\n");
      js.append("			if(!WebGrid_RowCheckout(table.id,editRow))\r\n");
      js.append("			{\r\n");
      js.append("					return;\r\n");
      js.append("			}			\r\n");
      js.append(
          "			var rowObj=WebGrid_addNewRow(table,editRow,editRow.rowIndex);			\r\n");
      js.append("			WebGrid_deleteEditRow(table,editRow);\r\n");
      js.append("			WebGrid_setSeletedRow(table,rowObj,true);					\r\n");
      js.append("		}\r\n");
      js.append("	}	\r\n");
      js.append("	function WebGrid_DefaultDelete(gridId)\r\n");
      js.append("	{\r\n");
      js.append("        var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(editRow!=null)\r\n");
      js.append("		{\r\n");
      js.append("			if(editRow.rowIndex<table.rows.length-2)\r\n");
      js.append("			{\r\n");
      js.append("				var rowObj=table.rows[editRow.rowIndex+2];\r\n");
      js.append("				WebGrid_deleteRow(table,editRow.rowIndex);\r\n");
      js.append("				WebGrid_setSeletedRow(table,rowObj,false);\r\n");
      js.append("				WebGrid_refleshSum(table);\r\n");
      js.append("\r\n");
      js.append("\r\n");
      js.append("			}\r\n");
      js.append("			else if(table.rows.length-2>0)\r\n");
      js.append("			{\r\n");
      js.append("				var rowObj=table.rows[editRow.rowIndex-1];\r\n");
      js.append("				WebGrid_deleteRow(table,editRow.rowIndex);\r\n");
      js.append("				WebGrid_setSeletedRow(table,rowObj,false);\r\n");
      js.append("				WebGrid_refleshSum(table);\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				WebGrid_clearRowData(table,editRow);\r\n");
      js.append("				WebGrid_refleshSum(table);\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_DefaultCancel(gridId)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(editRow!=null)\r\n");
      js.append("		{\r\n");
      js.append("			WebGrid_clearRowData(table,editRow);\r\n");
      js.append("			WebGrid_refleshSum(table);\r\n");
      js.append("		}\r\n");
      js.append("	}	\r\n");
      js.append("	function WebGrid_DefaultRowOnclick(gridId,rowObj)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(editRow==null)\r\n");
      js.append("		{		\r\n");
      js.append("		\r\n");
      js.append("			WebGrid_setSeletedRow(table,rowObj,false)\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");
      js.append("			if(editRow!=rowObj)\r\n");
      js.append("			{\r\n");
      js.append("				if(editRow.rowIndex==table.rows.length-2)\r\n");
      js.append("				{\r\n");
      js.append("					if(WebGrid_isAllNull(table,editRow.rowIndex))\r\n");
      js.append("					{\r\n");
      js.append("						var index=editRow.rowIndex;						\r\n");
      js.append("						WebGrid_deleteRow(table,editRow.rowIndex);\r\n");
      js.append("						WebGrid_setSeletedRow(table,rowObj,false);\r\n");
      js.append("						return;\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("				if(!WebGrid_RowCheckout(table.id,editRow))\r\n");
      js.append("				{\r\n");
      js.append("					return;\r\n");
      js.append("				}\r\n");
      js.append("				WebGrid_deleteEditRow(table,editRow);\r\n");
      js.append("				WebGrid_setSeletedRow(table,rowObj,false);\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("	}		\r\n");
      js.append("	//table�����\r\n");
      js.append("	 function WebGrid_AddColumn(objTable,lngColumn,width,text,editor,isEditRow)\r\n");
      js.append("	 {		\r\n");
      js.append("		var editRowIndex=-2;		\r\n");
      js.append("		var rowIndex=0;\r\n");
      js.append("		if(isEditRow==true)\r\n");
      js.append("		{\r\n");
      js.append("		    var editRow=WebGrid_getExistEditRow(objTable)\r\n");
      js.append("			if(editRow!=null)\r\n");
      js.append("			{\r\n");
      js.append("				editRowIndex=editRow.rowIndex;\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("		for (var i = 0;i < objTable.rows.length;i++)\r\n");
      js.append("		{\r\n");
      js.append("			var cell=null;\r\n");
      js.append("			if(editRowIndex>=0)\r\n");
      js.append("			{			\r\n");
      js.append("				if(i<=editRowIndex)\r\n");
      js.append("				{\r\n");
      js.append("					rowIndex=i;\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					rowIndex=i-1;\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				rowIndex=i;\r\n");
      js.append("			}\r\n");
      js.append("			if(editRowIndex==i)\r\n");
      js.append("			{\r\n");
      js.append("				cell=objTable.rows[i].insertCell(lngColumn);								\r\n");
      js.append("				cell.innerHTML=editor.component;								\r\n");
      js.append("				var control=editor.getChild();				\r\n");
      js.append("				var setProperty=control.getAttribute(\"setProperty\");\r\n");
      js.append("				var setValue=control.getAttribute(\"setValue\");\r\n");
      js.append("				var setText=control.getAttribute(\"setText\");\r\n");
      js.append("				if(document.all)\r\n");
      js.append("				{\r\n");
      js.append("					cell.width=editor.colWidth;\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					if(cell.cellIndex==objTable.rows[i].cells.length-1)\r\n");
      js.append("					{\r\n");
      js.append("						cell.width=parseInt(editor.colWidth)-16;\r\n");
      js.append("						objTable.rows[i].cells[cell.cellIndex-1].width=parseInt(objTable.rows[i].cells[cell.cellIndex-1].width)+16;\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("						cell.width=editor.colWidth;	\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("				if(setProperty!=null)\r\n");
      js.append("				{					\r\n");
      js.append("					var width=0;\r\n");
      js.append("					if(cell.offsetWidth==0)\r\n");
      js.append("					{\r\n");
      js.append("						width=parseInt(cell.width)-2;\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("						width=cell.offsetWidth-2;\r\n");
      js.append("					}\r\n");
      js.append(
          "					eval(setProperty+\"('\"+control.id+\"','width','\"+width+\"')\");\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					control.width=editor.colWidth;\r\n");
      js.append("				}					\r\n");
      js.append("				if(setText!=null)\r\n");
      js.append("				{\r\n");
      js.append(
          "					eval(setText+\"('\"+control.id+\"','\"+text[rowIndex]+\"')\");\r\n");
      js.append("				}\r\n");
      js.append("				else if(setValue!=null)\r\n");
      js.append("				{\r\n");
      js.append(
          "					eval(setValue+\"('\"+control.id+\"','\"+text[rowIndex]+\"')\");\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{					\r\n");
      js.append("					control.innerHTML=text[rowIndex];\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			else if(editRowIndex+1==i)\r\n");
      js.append("			{\r\n");
      js.append("				continue;\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				cell=objTable.rows[i].insertCell(lngColumn);\r\n");
      js.append("				if(document.all)\r\n");
      js.append("				{\r\n");
      js.append("					cell.width=editor.colWidth;\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{				\r\n");
      js.append("					if(isEditRow)\r\n");
      js.append("					{\r\n");
      js.append("						if(cell.cellIndex==objTable.rows[i].cells.length-1)\r\n");
      js.append("						{\r\n");
      js.append("							cell.width=parseInt(editor.colWidth)-16;\r\n");
      js.append("							objTable.rows[i].cells[cell.cellIndex-1].width=parseInt(objTable.rows[i].cells[cell.cellIndex-1].width)+16;\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("						cell.width=editor.colWidth;\r\n");
      js.append("					}\r\n");
      js.append("				}	\r\n");
      js.append("			}\r\n");
      js.append("					\r\n");
      js.append("			if(editor.hAlign!=\"\")\r\n");
      js.append("				cell.align=editor.hAlign;			\r\n");
      js.append("			if(lngColumn-1>=0)\r\n");
      js.append("			{\r\n");
      js.append(
          "				if(objTable.rows[i].cells[lngColumn-1].className!=\"\");\r\n");
      js.append("				{\r\n");
      js.append(
          "					cell.className=objTable.rows[i].cells[lngColumn-1].className;\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append(
          "				if(objTable.rows[i].cells[lngColumn-1].className!=\"\");\r\n");
      js.append("				{\r\n");
      js.append(
          "					cell.className=objTable.rows[i].cells[lngColumn+1].className;\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			if(editRowIndex<0)\r\n");
      js.append("			{\r\n");
      js.append("				if(text[rowIndex]===\"\")\r\n");
      js.append("				{\r\n");
      js.append("					cell.innerHTML=\"&nbsp;\"\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{					\r\n");
      js.append("					WebGrid_setInnerText(cell,text[rowIndex]);	\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			else if(editRowIndex!=i)\r\n");
      js.append("			{				\r\n");
      js.append("				WebGrid_setInnerText(cell,text[rowIndex]);	\r\n");
      js.append("				cell.disabled=true;\r\n");
      js.append("				cell.className=\"celldisabled\";\r\n");
      js.append("			}	\r\n");
      js.append("								\r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_insertButtonRow(table,rowObj)\r\n");
      js.append("	{\r\n");
      js.append("		var row=table.insertRow(rowObj.rowIndex+1);\r\n");
      js.append("        row.className=rowObj.className;       \r\n");
      js.append("		var cell=row.insertCell(0);\r\n");
      js.append("		cell.colSpan=table.rows[0].cells.length;\r\n");
      js.append("		if(eval(\"window.\"+table.id+\"_buttonHtml==null\"))\r\n");
      js.append("		{\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");      
      js.append("			cell.height=\"30px\";\r\n");
      js.append("			var buttonsTable=document.createElement(\"table\");\r\n");
      js.append("			buttonsTable.width=\"100%\";		\r\n");
      js.append("			var tempRow=buttonsTable.insertRow(0);		\r\n");
      js.append("			var tempCell=tempRow.insertCell(0);\r\n");
      js.append("			tempCell.innerHTML=\"&nbsp;\";\r\n");
      js.append("			var buttonCell=tempRow.insertCell(1);\r\n");
      js.append("			buttonCell.align=\"left\";\r\n");
      js.append("			buttonsTable.id=table.id+\"_buttonsTable\";\r\n");
      js.append("			cell.appendChild(buttonsTable);\r\n");
      js.append("			\r\n");
      js.append("			var buttonHtml=eval(table.id+\"_buttonHtml\");\r\n");
      js.append("			for(var i=0;i<buttonHtml.length;i++)\r\n");
      js.append("			{\r\n");
      js.append("				var spanButton=document.createElement(\"span\");\r\n");
      js.append("				buttonCell.appendChild(spanButton);\r\n");
      js.append("				var spanNbsp=document.createElement(\"span\");\r\n");
      js.append("				spanNbsp.innerHTML=\"&nbsp;\";\r\n");
      js.append("				buttonCell.appendChild(spanNbsp);\r\n");
      js.append("				spanButton.innerHTML=buttonHtml[i];\r\n");
      js.append("			}\r\n");
      js.append("			WebGrid_MoveButton(table.id);\r\n");
      js.append("			var div=document.getElementById(table.id+\"_div\");\r\n");
      js.append("			//30��ʾ�༭�еĿ�ȣ�16��ʾ�������Ŀ��\r\n");
      js.append("			if(buttonsTable.parentElement.offsetTop-div.scrollTop+30+16>=div.offsetHeight);\r\n");
      js.append("			{\r\n");
      js.append("				div.scrollTop=buttonsTable.parentElement.offsetTop+30-div.offsetHeight+20;\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_MoveButton(gridId)\r\n");
      js.append("    {\r\n");
      js.append(
          "		var buttonsTable=document.getElementById(gridId+\"_buttonsTable\");\r\n");
      js.append("		var tableObj=document.getElementById(gridId);\r\n");
      js.append("		if(buttonsTable == null)\r\n");
      js.append("			return;\r\n");
      js.append("		var cell=buttonsTable.rows[0].cells[0];\r\n");
      js.append("		var tableDiv=document.getElementById(gridId+\"_div\");	\r\n");
      js.append("		var left=tableDiv.offsetLeft+tableDiv.scrollLeft+tableDiv.offsetWidth-350;\r\n");
      js.append("		if(left<0)\r\n");
      js.append("		{\r\n");
      js.append("			left=0;\r\n");
      js.append("		}\r\n");
      js.append("		if(tableDiv.offsetWidth!=0)\r\n");
      js.append("		{\r\n");
      js.append("		    cell.style.width=tableDiv.offsetLeft+tableDiv.scrollLeft+tableDiv.offsetWidth-350;\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");
      js.append(
          "		   cell.style.width=Math.abs(parseInt(tableDiv.style.width)-350);\r\n");
      js.append("		}			\r\n");
      js.append("		//cell.style.width=tableDiv.offsetLeft+tableDiv.scrollLeft+tableDiv.offsetWidth-350;		\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_insertEditRow(table,row,isAddNew)\r\n");
      js.append("	{\r\n");
      js.append("		var Editors =eval(table.id+\"_Editors\");\r\n");
      js.append("		var first=-1;\r\n");
      js.append("		for(var i=0;i<Editors.length;i++)\r\n");
      js.append("		{\r\n");
      js.append("			var editor=Editors[i];							\r\n");
      js.append("           var index=WebGrid_getColumnRealIndexByEditorIndex(table.id,i);        	\r\n");
      js.append("			var width=0;\r\n");
      js.append("			if(index!=-1)\r\n");
      js.append("			{\r\n");
      js.append("				 width=row.cells[index].offsetWidth-2;	\r\n");
      js.append("				 if(width<0)\r\n");
      js.append("				 {					\r\n");
      js.append("					 width=parseInt(row.cells[index].width)-2;\r\n");
      js.append("				 }				 \r\n");
      js.append("				if(editor.isAutoAscend==true)\r\n");
      js.append("				{\r\n");
      js.append("					continue;	\r\n");
      js.append("				}			\r\n");
      js.append("				if(isAddNew)\r\n");
      js.append("				{\r\n");
      js.append("					if(editor.isColInitStatus)\r\n");
      js.append("					{\r\n");
      js.append("						if(!editor.isColDisabled)\r\n");
      js.append("						{\r\n");
      js.append("							row.cells[index].innerHTML=editor.component;\r\n");
      js.append("							row.cells[index].title=\"\";\r\n");
      js.append("						}\r\n");
      js.append("						else\r\n");
      js.append("						{\r\n");
      js.append("							row.cells[index].disabled=true;\r\n");
      js.append("							row.cells[index].className=\"celldisabled\";\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("						row.cells[index].disabled=true;\r\n");
      js.append("						row.cells[index].className=\"celldisabled\";\r\n");
      js.append("					}\r\n");
      js.append("					\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					if(row.cells[index].disabled!=true)\r\n");
      js.append("					{					\r\n");
      js.append("						row.cells[index].innerHTML=editor.component;					\r\n");
      js.append("						row.cells[index].title=\"\";\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				continue;\r\n");
      js.append("			}	\r\n");
      js.append("			if(editor.dataField==\"\")\r\n");
      js.append("			{\r\n");
      js.append("				continue;\r\n");
      js.append("			}				\r\n");
      js.append("			var control=editor.getChild(true);\r\n");
      js.append("			if(control!=null)\r\n");
      js.append("			{		\r\n");
      js.append("				if(first==-1 && (control.getAttribute(\"setValue\")!=null || control.getAttribute(\"setText\")!=null))\r\n");
      js.append("				{\r\n");
      js.append("					first=editor.editorIndex;\r\n");
      js.append("				}		\r\n");
      js.append(
          "				var setProperty=control.getAttribute(\"setProperty\");				\r\n");
      js.append("				if(setProperty!=null)\r\n");
      js.append("				{					\r\n");
      js.append(
          "					eval(setProperty+\"('\"+control.id+\"','width',\"+width+\")\");\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					control.width=width;\r\n");
      js.append("				}\r\n");
      js.append("				if(!isAddNew)\r\n");
      js.append("				{\r\n");
      js.append("					WebGrid_setViewData(editor,table,row,i);\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("		}	\r\n");
      js.append("		if(first!=-1)\r\n");
      js.append("		{	\r\n");
      js.append("			var editor=Editors[first];\r\n");
      js.append("			if(table.getAttribute(\"firstControl\")==null||table.getAttribute(\"firstControl\")==\"\")\r\n");
      js.append("			{\r\n");
      js.append(
          "				table.setAttribute(\"firstControl\",editor.editorIndex);\r\n");
      js.append("			}\r\n");
      js.append("			//WebGrid_setFocus(table,editor);	qch 2006-5-25\r\n");
      js.append("		}		\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_cancelEditRow(gridId)\r\n");
      js.append("	{\r\n");
      js.append("	   var table=document.getElementById(gridId);\r\n");
      js.append("	   var eidtRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("	   if(eidtRow!=null)\r\n");
      js.append("	   {\r\n");
      js.append("	      WebGrid_deleteEditRow(table,eidtRow);\r\n");
      js.append("	      eidtRow.className=table.getAttribute(\"ItemClass\");\r\n");
      js.append("	      table.removeAttribute(\"oldItemID\");\r\n");
      js.append("	   }\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_deleteEditRow(table,row)\r\n");
      js.append("	{\r\n");
      js.append("		var Editors =eval(table.id+\"_Editors\");\r\n");
      js.append("		for(var i=0;i<Editors.length;i++)\r\n");
      js.append("		{\r\n");
      js.append("			var editor=Editors[i];\r\n");
      js.append(
          "			var index=WebGrid_getColumnRealIndexByEditorIndex(table.id,i);\r\n");
      js.append("             if(index!=-1)\r\n");
      js.append("			{				\r\n");
      js.append("				WebGrid_setCellText(row,index,editor)\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				continue;\r\n");
      js.append("			}\r\n");
      js.append("\r\n");
      js.append("		}\r\n");
      js.append("		table.deleteRow(row.rowIndex+1);\r\n");
      js.append("	}	\r\n");
      js.append("	//grid�ͻ�����У��\r\n");
      js.append("	function WebGrid_RowCheckout(gridId,rowObj)\r\n");
      js.append("	{\r\n");
      js.append("		var Editors =eval(gridId+\"_Editors\");\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		for(var i=0;i<Editors.length;i++)\r\n");
      js.append("		{	\r\n");
      js.append("			if(Editors[i].isNecessary==true)\r\n");
      js.append("			{\r\n");
      js.append("				if(Editors[i].isMutex==false)\r\n");
      js.append("				{\r\n");
      js.append("					var hidden=WebGrid_getColumnText(gridId,i);\r\n");
      js.append("					var value=ListManager_Get(rowObj.rowIndex,hidden);\r\n");
      js.append("					var control=Editors[i].getChild();						\r\n");
      js.append("					if(control.getAttribute(\"IsNumber\")==\"true\")\r\n");
      js.append("					{\r\n");
      js.append("						if(value==\"0\"||value==\"\")\r\n");
      js.append("						{\r\n");
      js.append("							alert(Editors[i].headerName+\" ����Ϊ��!\");		\r\n");
      js.append("							table.setAttribute(\"errorEditorIndex\",i);					\r\n");
      js.append("							return false;\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("						if(value==\"\")\r\n");
      js.append("						{\r\n");
      js.append("							alert(Editors[i].headerName+\" ����Ϊ��!\");	\r\n");
      js.append(
          "							table.setAttribute(\"errorEditorIndex\",i);																				 \r\n");
      js.append("							return false;\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					var isAlert=true;\r\n");
      js.append("					var strAlert=\"\";\r\n");
      js.append("					var hidden=WebGrid_getColumnText(gridId,i);\r\n");
      js.append("					var value=ListManager_Get(rowObj.rowIndex,hidden);\r\n");
      js.append("					var control=Editors[i].getChild();\r\n");
      js.append("					strAlert=Editors[i].headerName;//alert(control.getAttribute(\"IsConvertZero\"));\r\n");
      js.append("					if(control.getAttribute(\"IsNumber\")==\"true\")\r\n");
      js.append("					{\r\n");
      js.append("						if(value!=\"0\"&&value!=\"\")\r\n");
      js.append("						{\r\n");
      js.append("							isAlert=false;\r\n");
      js.append("							continue;\r\n");
      js.append("\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("						if(value!=\"\")\r\n");
      js.append("						{\r\n");
      js.append("							isAlert=false;\r\n");
      js.append("							continue;\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					for(var j=0;j<Editors[i].mutexColumn.length;j++)\r\n");
      js.append("					{\r\n");
      js.append("\r\n");
      js.append("						var mutexEditor=WebGrid_getEditorByColumnName(gridId,Editors[i].mutexColumn[j]);\r\n");
      js.append("						var mutexControl=mutexEditor.getChild();\r\n");
      js.append(
          "						var mutexGetValue=mutexControl.getAttribute(\"getValue\");\r\n");
      js.append(
          "						var mutexValue=eval(mutexGetValue+\"('\"+mutexControl.id+\"')\");\r\n");
      js.append("						strAlert+=\",\"+mutexEditor.headerName;\r\n");
      js.append("						if(mutexControl.getAttribute(\"IsNumber\")==\"true\")\r\n");
      js.append("						{\r\n");
      js.append("							if(mutexValue!=\"0\"&&mutexValue!=\"\")\r\n");
      js.append("							{\r\n");
      js.append("								isAlert=false;\r\n");
      js.append("								break;\r\n");
      js.append("							}\r\n");
      js.append("						}\r\n");
      js.append("						else\r\n");
      js.append("						{\r\n");
      js.append("							if(mutexValue!=\"\")\r\n");
      js.append("							{\r\n");
      js.append("								isAlert=false;\r\n");
      js.append("								break;\r\n");
      js.append("							}\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					if(isAlert)\r\n");
      js.append("					{\r\n");
      js.append("						alert(strAlert+\" ���ܶ�Ϊ��!\");	\r\n");
      js.append("						var table=document.getElementById(gridId);\r\n");
      js.append("						table.setAttribute(\"errorEditorIndex\",i);					\r\n");
      js.append("						return false;\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append(
          "				var cellIndex=WebGrid_getColumnRealIndex(gridId,Editors[i].dataField);\r\n");
      js.append("				if(cellIndex>=0)\r\n");
      js.append("				{\r\n");
      js.append(
          "					if(rowObj.cells[cellIndex].getAttribute(\"IsNecessary\")==\"true\")\r\n");
      js.append("					{\r\n");
      js.append("						\r\n");
      js.append("						var hidden=WebGrid_getColumnText(gridId,i);\r\n");
      js.append("						var value=ListManager_Get(rowObj.rowIndex,hidden);\r\n");
      js.append("						var control=Editors[i].getChild();\r\n");
      js.append("						if(control==null)\r\n");
      js.append("						{\r\n");
      js.append("							continue;\r\n");
      js.append("						}\r\n");
      js.append("						if(control.getAttribute(\"IsNumber\")==\"true\")\r\n");
      js.append("						{\r\n");
      js.append("							if(value==\"0\"||value==\"\")\r\n");
      js.append("							{\r\n");
      js.append("								alert(Editors[i].headerName+\" ����Ϊ��!\");	\r\n");
      js.append("								table.setAttribute(\"errorEditorIndex\",i);						\r\n");
      js.append("								return false;\r\n");
      js.append("							}\r\n");
      js.append("						}\r\n");
      js.append("						else\r\n");
      js.append("						{\r\n");
      js.append("							if(value==\"\")\r\n");
      js.append("							{\r\n");
      js.append("								alert(Editors[i].headerName+\" ����Ϊ��!\");	\r\n");
      js.append(
          "								table.setAttribute(\"errorEditorIndex\",i);																				 \r\n");
      js.append("								return false;\r\n");
      js.append("							}\r\n");
      js.append("						}																	\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("		}		\r\n");
      js.append("		return true;\r\n");
      js.append("	}\r\n");
      js.append(
          "	function WebGrid_SetEditorValue(gridId,dataField,hiddenValue)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(editRow==null)\r\n");
      js.append("		{\r\n");
      js.append("			return;\r\n");
      js.append("		}	\r\n");
      js.append(
          "		var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append("		var index=editor.editorIndex;\r\n");
      js.append("		var control=editor.getChild();\r\n");
      js.append("		if(control==null)\r\n");
      js.append("		{\r\n");
      js.append("			var hidden=WebGrid_getColumnValue(table.id,index);\r\n");
      js.append("			ListManager_Set(editRow.rowIndex,hiddenValue,hidden);  	\r\n");
      js.append("			return;\r\n");
      js.append("		}\r\n");
      js.append("		var setText=control.getAttribute(\"setText\");\r\n");
      js.append("		var setValue=control.getAttribute(\"setValue\");\r\n");
      js.append("        if(editor.dataField!=\"\"&&editor.dataValue!=\"\"&&setValue!=null&&setText!=null&&!editor.isDrop)\r\n");
      js.append("        {\r\n");
      js.append(
          "                var hidden=WebGrid_getColumnValue(table.id,index);\r\n");
      js.append("                eval(setValue+\"('\"+control.id+\"','\"+hiddenValue+\"')\");\r\n");
      js.append("                ListManager_Set(editRow.rowIndex,hiddenValue,hidden);                \r\n");
      js.append("        }\r\n");
      js.append("        else if(editor.isDrop)\r\n");
      js.append("        {\r\n");
      js.append("			    var hidden=WebGrid_getColumnValue(table.id,index);\r\n");
      js.append(
          "				eval(setValue+\"('\"+control.id+\"','\"+hiddenValue+\"')\");\r\n");
      js.append("                ListManager_Set(editRow.rowIndex,hiddenValue,hidden);                \r\n");
      js.append("        }\r\n");
      js.append("		\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_SetDefaultGridCellText(gridId,rowIndex,dataField,viewtext)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		if(editRow==null)\r\n");
      js.append("		{\r\n");
      js.append(
          "			var columnRealIndex=WebGrid_getColumnRealIndex(gridId,dataField);\r\n");
      js.append(
          "			var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append("			if(columnRealIndex!=-1)\r\n");
      js.append("			{											\r\n");
      js.append("				WebGrid_setInnerText(table.rows[rowIndex].cells[columnRealIndex],viewtext);\r\n");
      js.append("			}\r\n");
      js.append(
          "			var hidden=WebGrid_getColumnText(gridId,editor.editorIndex);\r\n");
      js.append("			 ListManager_Set(rowIndex,viewtext,hidden);      \r\n");
      js.append("			\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");
      js.append("			if(rowIndex<editRow.rowIndex)\r\n");
      js.append("			{\r\n");
      js.append(
          "				var columnRealIndex=WebGrid_getColumnRealIndex(gridId,dataField);\r\n");
      js.append(
          "				var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append("				if(columnRealIndex!=-1)	\r\n");
      js.append("					WebGrid_setInnerText(table.rows[rowIndex].cells[columnRealIndex],viewtext);							\r\n");
      js.append(
          "				var hidden=WebGrid_getColumnText(gridId,editor.editorIndex);\r\n");
      js.append("				ListManager_Set(rowIndex,viewtext,hidden);  \r\n");
      js.append("			}\r\n");
      js.append("			else if(rowIndex==editRow.rowIndex)\r\n");
      js.append("			{\r\n");
      js.append("				WebGrid_SetEditorText(gridId,dataField,viewtext);\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				if(rowIndex<table.rows.length)\r\n");
      js.append("				{\r\n");
      js.append(
          "					var columnRealIndex=WebGrid_getColumnRealIndex(gridId,dataField);\r\n");
      js.append(
          "					var editor=WebGrid_getEditorByColumnName(gridId,dataField);			\r\n");
      js.append("					if(columnRealIndex!=-1)	\r\n");
      js.append("						WebGrid_setInnerText(table.rows[rowIndex+1].cells[columnRealIndex],viewtext);						\r\n");
      js.append(
          "					var hidden=WebGrid_getColumnText(gridId,editor.editorIndex);\r\n");
      js.append("					ListManager_Set(rowIndex,viewtext,hidden); \r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append(
          "	function WebGrid_GetDefaultGridCellText(gridId,rowIndex,dataField)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append(
          "		var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);	\r\n");
      js.append(
          "		var index=WebGrid_getRealRowIndex(rowIndex,editRow,table);		\r\n");
      js.append("		if(editor.dataField!=\"\")\r\n");
      js.append("		{\r\n");
      js.append(
          "			var hidden=WebGrid_getColumnText(gridId,editor.editorIndex);						\r\n");
      js.append("			return ListManager_Get(index,hidden); \r\n");
      js.append("		}\r\n");
      js.append("		return \"\";\r\n");
      js.append("	}\r\n");
      js.append(
          "	function WebGrid_GetDefaultGridCellValue(gridId,rowIndex,dataField)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append(
          "		var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);	\r\n");
      js.append(
          "		var index=WebGrid_getRealRowIndex(rowIndex,editRow,table);\r\n");
      js.append("		if(editor.dataValue!=\"\")\r\n");
      js.append("		{\r\n");
      js.append(
          "			var hidden=WebGrid_getColumnValue(gridId,editor.editorIndex);\r\n");
      js.append("			return ListManager_Get(index,hidden); \r\n");
      js.append("		}\r\n");
      js.append("		return \"\";\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_SetDefaultGridCellValue(gridId,rowIndex,dataField,hiddenValue)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append(
          "		var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);	\r\n");
      js.append(
          "		var index=WebGrid_getRealRowIndex(rowIndex,editRow,table);		\r\n");
      js.append("		if(editor.dataValue!=\"\")\r\n");
      js.append("		{\r\n");
      js.append(
          "			var hidden=WebGrid_getColumnValue(gridId,editor.editorIndex);\r\n");
      js.append("			ListManager_Set(index,hiddenValue,hidden); \r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_deleteRow(table,deleteIndex)\r\n");
      js.append("	{\r\n");
      js.append("			WebGrid_deleteData(table,deleteIndex);\r\n");
      js.append("			table.deleteRow(deleteIndex);\r\n");
      js.append("			table.deleteRow(deleteIndex);\r\n");
      js.append("			table.removeAttribute(\"oldItemID\");\r\n");
      js.append("			for(var i=table.rows.length-1;i>=deleteIndex;i--)\r\n");
      js.append("			{\r\n");
      js.append("				table.rows[i].id=table.id+\"_Row_\"+i;\r\n");
      js.append("			}\r\n");
      js.append("	}	\r\n");
      js.append("	function WebGrid_setNextSelectedRow(table,editRow)\r\n");
      js.append("	{\r\n");
      js.append("		WebGrid_deleteEditRow(table,editRow);\r\n");
      js.append("		var rowObj=table.rows[editRow.rowIndex+1];\r\n");
      js.append("		WebGrid_setSeletedRow(table,rowObj,false);\r\n");
      js.append("	}\r\n");
      js.append("	function WebGrid_setSeletedRow(table,rowObj,isAddNew)\r\n");
      js.append("	{\r\n");
      js.append("		ListGrid_RowOnSelect(rowObj,table.id);\r\n");
      js.append("		if(isAddNew)\r\n");
      js.append("		{\r\n");
      js.append("			\r\n");
      js.append("			WebGrid_insertNewData(table,rowObj.rowIndex);\r\n");
      js.append("		}\r\n");
      js.append("		\r\n");
      js.append("		WebGrid_insertEditRow(table,rowObj,isAddNew);\r\n");
      js.append("		WebGrid_insertButtonRow(table,rowObj);\r\n");
      js.append(
          "		var onEditRowChange=table.getAttribute(\"oneditrowchange\");\r\n");
      js.append("		if(onEditRowChange!=null&&onEditRowChange!=\"\")\r\n");
      js.append("		{\r\n");
      js.append("			eval(onEditRowChange);\r\n");
      js.append("		}\r\n");
      js.append("	}	\r\n");
      js.append("	function WebGrid_getRealRowIndex(rowIndex,editRow,table)\r\n");
      js.append("	{\r\n");
      js.append("		var index=0;\r\n");
      js.append("		if(editRow==null)\r\n");
      js.append("		{\r\n");
      js.append("			index=rowIndex;\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");
      js.append("			if(rowIndex<=editRow.rowIndex)\r\n");
      js.append("			{\r\n");
      js.append("				index=rowIndex;\r\n");
      js.append("			}			\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				if(rowIndex<table.rows.length)\r\n");
      js.append("				{\r\n");
      js.append("					index=rowIndex-1;\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("		}		\r\n");
      js.append("		return rowIndex;\r\n");
      js.append("	}\r\n");
      js.append("	//----------------------------------------------------------------------------//\r\n");
      js.append("	//�û��ɵ��÷���\r\n");
      js.append("	//grid��ʾ�У���ݰ��ֶΣ�\r\n");
      js.append("	function WebGrid_InsertColumn(gridId,dataField)\r\n");
      js.append("	{\r\n");
      js.append("\r\n");
      js.append("		var _Store_ColumnInfo=document.getElementById(gridId+\"_Store_ColumnInfo_hidden\");\r\n");
      js.append("		var columnIndex=WebGrid_getColumnIndex(gridId,dataField);\r\n");
      js.append(
          "		var index=ListManager_IndexOf(columnIndex,_Store_ColumnInfo);\r\n");
      js.append("		if(index>=0)\r\n");
      js.append("		{\r\n");
      js.append("			\r\n");
      js.append("			var objTable=document.getElementById(gridId);\r\n");
      js.append("			var objheader=document.getElementById(gridId+\"_head\");\r\n");
      js.append(
          "			var objfooter=document.getElementById(gridId+\"_footer\");\r\n");
      js.append(
          "			var realIndex=WebGrid_getColumnRealIndex(gridId,dataField);\r\n");
      js.append(
          "			var columnIndex=WebGrid_getColumnIndex(gridId,dataField);\r\n");
      js.append("			var Editors =eval(gridId+\"_Editors\");\r\n");
      js.append("			var editor=Editors[columnIndex];\r\n");
      js.append("			var lngColumn=columnIndex-index;\r\n");
      js.append("			var hidden=WebGrid_getColumnText(gridId,columnIndex);\r\n");
      js.append("			var valueArray=ListManager_Analysis(hidden);\r\n");
      js.append("			var editRow=WebGrid_getExistEditRow(objTable);\r\n");
      js.append("			if(editRow!=null)\r\n");
      js.append("			{\r\n");
      js.append("				//valueArray[editRow.rowIndex]=editor.component;\r\n");
      js.append("			}\r\n");
      js.append("			if(editor.colWidth==\"\")\r\n");
      js.append("			{\r\n");
      js.append("				editor.colWidth=\"50px\";\r\n");
      js.append("			}\r\n");
      js.append("			var objTableWidth=objTable.offsetWidth;\r\n");
      js.append("			var objheaderWidth=objheader.offsetWidth;\r\n");
      js.append("			var objheaderWidth=objfooter.offsetWidth;			\r\n");
      js.append("			WebGrid_AddColumn(objTable,lngColumn,editor.colWidth,valueArray,editor,true);\r\n");
      js.append("			WebGrid_AddColumn(objheader,lngColumn,editor.colWidth,[editor.headerName],editor,false);\r\n");
      js.append("			WebGrid_AddColumn(objfooter,lngColumn,editor.colWidth,[\"\"],editor,false);\r\n");
      js.append("			ListManager_Remove(index,_Store_ColumnInfo);\r\n");
      js.append("			objheader.rows[0].cells[lngColumn].align=\"center\";\r\n");
      js.append("			if(document.all)\r\n");
      js.append("			{\r\n");
      js.append("				;	\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append(
          "				objTable.width=parseInt(objTable.width)+parseInt(editor.colWidth);\r\n");
      js.append(
          "				objheader.width=parseInt(objheader.width)+parseInt(editor.colWidth);\r\n");
      js.append("				objfooter.width=parseInt(objfooter.width)+parseInt(editor.colWidth);						\r\n");
      js.append("			}\r\n");
      js.append("			if(editor.isTotal)\r\n");
      js.append("			{\r\n");
      js.append("				Formula_runSum(objTable,mutexEditor.dataField);\r\n");
      js.append("			}\r\n");
      js.append(
          "			var buttonsTable=document.getElementById(gridId+\"_buttonsTable\");\r\n");
      js.append("			if(editRow!=null)\r\n");
      js.append("			{\r\n");
      js.append("				var cell=objTable.rows[editRow.rowIndex+1].cells[0];\r\n");
      js.append("				cell.colSpan=cell.colSpan+1;				\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append("	//���õ�Ԫ�����״̬\r\n");
      js.append(
          "	function WebGrid_setCellIsNecessary(gridId,rowIndex,dataField,necessary)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		var index=WebGrid_getColumnRealIndex(gridId,dataField);		\r\n");
      js.append("		if(index==-1)\r\n");
      js.append("		{\r\n");
      js.append("			return;\r\n");
      js.append("		}\r\n");
      js.append("		var rowRealIndex=rowIndex;\r\n");
      js.append("		if(editRow!=null)\r\n");
      js.append("		{\r\n");
      js.append("			if(rowIndex>editRow)\r\n");
      js.append("			{\r\n");
      js.append("				rowRealIndex=rowRealIndex+1;\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");
      js.append("			if(table.rows[rowRealIndex].cells[index])\r\n");
      js.append("			{\r\n");
      js.append("				return;\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("		var cell=table.rows[rowRealIndex].cells[index];\r\n");
      js.append("		cell.setAttribute(\"IsNecessary\",necessary.toString());\r\n");
      js.append("	}\r\n");
      js.append(
          "	function WebGrid_setEditRowCellNecessary(gridId,dataField,disabled)\r\n");
      js.append("	{		\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append(
          "		WebGrid_setCellIsNecessary(gridId,editRow.rowIndex,dataField,disabled);\r\n");
      js.append("	}\r\n");
      js.append(
          "	function WebGrid_setEditRowCellDisabled(gridId,dataField,disabled)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append(
          "		WebGrid_setCellDisabled(gridId,editRow.rowIndex,dataField,disabled);\r\n");
      js.append("	}\r\n");
      js.append("	//���õ�Ԫ��״̬\r\n");
      js.append("function WebGrid_setCellDisabled(gridId,rowIndex,dataField,disabled)\r\n");
      js.append("	{\r\n");
      js.append("		var table=document.getElementById(gridId);\r\n");
      js.append("		var editRow=WebGrid_getExistEditRow(table);\r\n");
      js.append("		var index=WebGrid_getColumnRealIndex(gridId,dataField);\r\n");
      js.append("		if(index==-1)\r\n");
      js.append("		{\r\n");
      js.append("			return;\r\n");
      js.append("		}\r\n");
      js.append("		var realRowIndex=rowIndex;\r\n");
      js.append("		if(editRow==null)\r\n");
      js.append("		{\r\n");
      js.append("\r\n");
      js.append("			if(index<0)\r\n");
      js.append("			{\r\n");
      js.append("				return;\r\n");
      js.append("			}\r\n");
      js.append("			else\r\n");
      js.append("			{\r\n");
      js.append("				if(table.rows[rowIndex].cells[index]!=null)\r\n");
      js.append("				{\r\n");
      js.append(
          "					if(table.rows[rowIndex].cells[index].disabled!=disabled)\r\n");
      js.append("					{\r\n");
      js.append("						table.rows[rowIndex].cells[index].disabled=disabled;\r\n");
      js.append("						if(disabled)\r\n");
      js.append("						{\r\n");
      js.append(
          "							table.rows[rowIndex].cells[index].className=\"celldisabled\";\r\n");
      js.append("						}\r\n");
      js.append("						else\r\n");
      js.append("						{\r\n");
      js.append(
          "							if(table.rows[rowIndex].cells[index].className==\"celldisabled\")\r\n");
      js.append("							{\r\n");
      js.append("								table.rows[rowIndex].cells[index].className=\"\";	\r\n");
      js.append("							}	\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("				}\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("		else\r\n");
      js.append("		{\r\n");
      js.append("			if(editRow.rowIndex<rowIndex)\r\n");
      js.append("			{\r\n");
      js.append("				realRowIndex=realRowIndex+1;\r\n");
      js.append("			}\r\n");
      js.append("			if(index<0)\r\n");
      js.append("			{\r\n");
      js.append("				return;\r\n");
      js.append("			}\r\n");
      js.append("			if(table.rows[rowIndex].cells[index]==null)\r\n");
      js.append("			{\r\n");
      js.append("				return;\r\n");
      js.append("			}\r\n");
      js.append(
          "			if(table.rows[realRowIndex].cells[index].disabled!=disabled)\r\n");
      js.append("			{\r\n");
      js.append("\r\n");
      js.append(
          "				var editor=WebGrid_getEditorByColumnName(gridId,dataField);\r\n");
      js.append(
          "				var columnIndex=WebGrid_getColumnIndex(gridId,dataField);\r\n");
      js.append("				var hidden=WebGrid_getColumnText(gridId,columnIndex);\r\n");
      js.append("				var valueText=ListManager_Get(rowIndex,hidden);		\r\n");
      js.append("				var width=editRow.cells[index].offsetWidth-2;		\r\n");
      js.append("				if(disabled)\r\n");
      js.append("				{										\r\n");
      js.append("					WebGrid_setInnerText(table.rows[realRowIndex].cells[index],valueText);\r\n");
      js.append("					table.rows[realRowIndex].cells[index].disabled=true;\r\n");
      js.append(
          "					table.rows[realRowIndex].cells[index].className=\"celldisabled\";\r\n");
      js.append("\r\n");
      js.append("				}\r\n");
      js.append("				else\r\n");
      js.append("				{\r\n");
      js.append("					if(realRowIndex==editRow.rowIndex)\r\n");
      js.append("					{\r\n");
      js.append(
          "					    table.rows[realRowIndex].cells[index].disabled=false;\r\n");
      js.append("					    	if(table.rows[rowIndex].cells[index].className==\"celldisabled\")\r\n");
      js.append("						{\r\n");
      js.append("							table.rows[rowIndex].cells[index].className=\"\";	\r\n");
      js.append("						}\r\n");
      js.append("						table.rows[realRowIndex].cells[index].innerHTML=editor.component;						\r\n");
      js.append("						var control=editor.getChild(true);\r\n");
      js.append("						var index=rowIndex;\r\n");
      js.append("						if(control!=null)\r\n");
      js.append("						{					\r\n");
      js.append(
          "							var setProperty=control.getAttribute(\"setProperty\");\r\n");
      js.append("							if(setProperty!=null)\r\n");
      js.append("							{					\r\n");
      js.append(
          "								eval(setProperty+\"('\"+control.id+\"','width',\"+width+\")\");\r\n");
      js.append("							}\r\n");
      js.append("							else\r\n");
      js.append("							{\r\n");
      js.append("								control.width=width;\r\n");
      js.append("							}\r\n");
      js.append("							var setValue=control.getAttribute(\"setValue\");\r\n");
      js.append("							var setText=control.getAttribute(\"setText\");\r\n");
      js.append("							if(editor.dataField!=\"\"&&editor.dataValue==\"\"&&setValue!=null&&!editor.isDrop)\r\n");
      js.append("							{\r\n");
      js.append("								eval(setValue+\"('\"+control.id+\"',valueText)\");\r\n");
      js.append("							}\r\n");
      js.append("							else if(editor.dataField!=\"\"&&editor.dataValue==\"\"&&setValue==null)\r\n");
      js.append("							{\r\n");
      js.append("									WebGrid_setInnerText(control,valueText);								\r\n");
      js.append("							} \r\n");
      js.append("							else\r\n");
      js.append("							{\r\n");
      js.append("								eval(setText+\"('\"+control.id+\"',valueText)\");\r\n");
      js.append("							}\r\n");
      js.append("						}\r\n");
      js.append("					}\r\n");
      js.append("					else\r\n");
      js.append("					{\r\n");
      js.append("					    	WebGrid_setInnerText(table.rows[realRowIndex].cells[index],valueText);\r\n");
      js.append("						table.rows[realRowIndex].cells[index].disabled=false;\r\n");
      js.append("						table.rows[realRowIndex].cells[index].className=\"\";\r\n");
      js.append(
          "						if(table.rows[rowIndex].cells[index].className==\"celldisabled\")\r\n");
      js.append("						{\r\n");
      js.append("							table.rows[rowIndex].cells[index].className=\"\";	\r\n");
      js.append("						}\r\n");
      js.append("					}					\r\n");
      js.append("				}				\r\n");
      js.append("			}\r\n");
      js.append("		}\r\n");
      js.append("	}");

      return jstool.getComponentJS() + HashMapJS.getHashMapJS();
  }
}