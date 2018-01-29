package com.born.insurance.webui.control;
public class ListGridJS {
  public ListGridJS() {
  }
  /**
   *
   * <P><B>��ȡListGrid�ͻ��˽ű�</B></P>
   * <P><B>ListGrid����ѡ������÷���</B></P>
   * function:ListGrid_RowOnSelect(gridId)</p>
   * param gridId �����Id
   * @return
   */
  public static String getListGridJS()
  {
    JSTool jstool=new JSTool("ListGrid.js");
	if(!jstool.util.isUpdateJsEveryTime())
	 {
		 return jstool.getComponentJS();
	 }
    StringBuffer js = jstool.js;
        js.append("        function ListGrid_RowOnSelect(Row,tableId)\r\n");
        js.append("	{		\r\n");
        js.append("		var table=document.getElementById(tableId); \r\n");
        js.append(
            "		Row.className=table.getAttribute('selectedItemClass');\r\n");
        js.append("		if(table.getAttribute(\"oldItemID\"))\r\n");
        js.append("		{\r\n");
        js.append(
            "			var oldRow=document.getElementById(table.getAttribute(\"oldItemID\"));\r\n");
        js.append("			if(oldRow.rowIndex%2==0)\r\n");
        js.append("			{		\r\n");
        js.append("				oldRow.className=table.getAttribute(\"ItemClass\");\r\n");
        js.append("			}\r\n");
        js.append("			else\r\n");
        js.append("			{\r\n");
        js.append(
            "				if(table.getAttribute(\"alternatingItemCssClass\")!=null&&table.getAttribute(\"alternatingItemCssClass\")!=\"\")\r\n");
        js.append(
            "					oldRow.className=table.getAttribute(\"alternatingItemCssClass\");\r\n");
        js.append("				else\r\n");
        js.append("				{\r\n");
        js.append(
            "					oldRow.className=table.getAttribute(\"ItemClass\");\r\n");
        js.append("				}\r\n");
        js.append("			}\r\n");
        js.append("		}\r\n");
        js.append("		table.setAttribute('oldItemID',Row.id);	\r\n");
        js.append("		\r\n");
        js.append("	}\r\n");
		js.append("        function ListGrid_RowOutSelect(Row,tableId)\r\n");
		js.append("	{		\r\n");
		js.append("		var table=document.getElementById(tableId); \r\n");
		js.append("		if(table.getAttribute(\"oldItemID\"))\r\n");
		js.append("		{\r\n");
		js.append("			var oldRow=Row;\r\n");
		js.append("			if(oldRow.rowIndex%2==0)\r\n");
		js.append("			{		\r\n");
		js.append("				oldRow.className=table.getAttribute(\"ItemClass\");\r\n");
		js.append("			}\r\n");
		js.append("			else\r\n");
		js.append("			{\r\n");
		js.append(
		    "				if(table.getAttribute(\"alternatingItemCssClass\")!=null&&table.getAttribute(\"alternatingItemCssClass\")!=\"\")\r\n");
		js.append(
		    "					oldRow.className=table.getAttribute(\"alternatingItemCssClass\");\r\n");
		js.append("				else\r\n");
		js.append("				{\r\n");
		js.append(
		    "					oldRow.className=table.getAttribute(\"ItemClass\");\r\n");
		js.append("				}\r\n");
		js.append("			}\r\n");
		js.append("		}\r\n");
		js.append("		\r\n");
		js.append("	}\r\n");
        return jstool.getComponentJS() + HashMapJS.getHashMapJS();
  }

}