/*
 * Created on 2005-7-7
 *
 */
package com.born.insurance.webui.control;

/**
 * TimeControlJS
 * 
 * @author liwei
 */
public class TimeControlJS
{
	public static String getTimeControlJS(){
		JSTool jstool=new JSTool("TimeControlJS.js");
		StringBuffer js = new StringBuffer();
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS();// + HashMapJS.getHashMapJS();
		 }
		//��֤����
	   js.append("function validateTime(id)\r\n");
	   js.append(" {\r\n");
	   js.append("    var textbox = document.getElementById(id+'_TextBox');\r\n");
	   js.append("	 var minute = textbox.value;\r\n");
	   js.append("	 var size = minute.length;\r\n");
	   js.append("	 var minuteFront;\r\n");
	   js.append("	 var minuteLast;\r\n");	  	   	   
	   js.append("	 if(size == 1)\r\n");
	   js.append("	 {\r\n");
	   js.append("       if(minute<=9 && minute>=0)\r\n");
	   js.append("        {\r\n");
	   js.append(" 		   textbox.value = '0'+ minute;\r\n");
	   js.append(" 		 }\r\n");
	   js.append("        else\r\n");
	   js.append("       {\r\n");
	   js.append("         textbox.value = '00';\r\n");
	   js.append("         alert('����������!');");
	   js.append("       }\r\n");
	   js.append("     }\r\n");
	   js.append("     else\r\n");
	   js.append("     {\r\n");
	   js.append("       minuteFront = minute.substring(0,1);\r\n");
	   js.append("       minuteLast = minute.substring(1);\r\n");
	   js.append("       if(minuteFront<=9&&minuteFront>=0&&minuteLast<=9&&minuteLast>=0)\r\n");
	   js.append("       {\r\n");
	   js.append("         if(minuteFront>6)\r\n");
	   js.append("         {\r\n");
	   js.append("           textbox.value = '00';\r\n");
	   js.append("           alert('�������뷶Χ��0 - 60֮��!');\r\n");
	   js.append("         }\r\n");
	   js.append("         if(minuteFront==6 && minuteLast>=0)\r\n");
	   js.append("        {\r\n");
	   js.append("          textbox.value = '00';\r\n");
	   js.append("          alert('�������뷶Χ��0 - 60֮��!');\r\n");
	   js.append("        }\r\n");
	   js.append("      }\r\n");
	   js.append("      else\r\n");
	   js.append("      {\r\n");
	   js.append("      textbox.value = '00';\r\n");
	   js.append("      alert('����������!');\r\n");	   
	   js.append("      }\r\n"); 
	   js.append("	   }\r\n");
	   js.append("	   }\r\n");
	   
	  //getValue����
	   js.append("function TimeControl_getValue(id)\r\n");
	   js.append("{\r\n");
	   js.append("      var value;\r\n");
	   js.append("      var textBox = document.getElementById(id+'_TextBox');\r\n");
	   js.append("      if(textBox.readOnly)\r\n");
	   js.append(" 	  {\r\n");
	   js.append(" 	  value = document.getElementById(id+'_ReadOnlyTextBox').value+':'+textBox.value;\r\n");
	   js.append(" 	  }\r\n");
	   js.append(" 	  else\r\n");
	   js.append("	  {\r\n");
	   js.append(" 	  value = document.getElementById(id+'_ListBox').value+':'+textBox.value;");
	   js.append("     }\r\n");
	   js.append(" 	  return value;\r\n");
	   js.append("     }\r\n");
	   
	   //setValue����
	   js.append("function TimeControl_setValue(id,value)\r\n");
	   js.append(" {\r\n");
	   js.append("	 var textBox = document.getElementById(id+'_TextBox');\r\n");
	   js.append("    var listBox;\r\n");
	   js.append("    var array;\r\n");
	   js.append("    if(value != null && value.indexOf(':')>-1)\r\n");
	   js.append("   {\r\n");
	   js.append(" 	array = value.split(':');\r\n");
	   js.append(" 	if(textBox.readOnly)\r\n");
	   js.append(" 	{\r\n");
	   js.append(" 	listBox = document.getElementById(id+'_ReadOnlyTextBox')\r\n");
	   js.append(" 	listBox.value = array[0];\r\n");
	   js.append(" 	textBox.value = array[1];\r\n");
	   js.append(" 	}\r\n");
	   js.append(" 	else\r\n");
	   js.append("   {\r\n");
	   js.append(" 	listBox = document.getElementById(id+'_ListBox')\r\n");
	   js.append("   if(array[0]>=0 && array[0]<=9)\r\n");
	   js.append("  {\r\n");
	   js.append(" 	listBox.value = '0'+array[0];\r\n");
	   js.append(" 	}\r\n");
	   js.append(" 	textBox.value = array[1];\r\n");
	   js.append(" 	}\r\n");
	   js.append(" 	validateTime(id);\r\n");
	   js.append("   }\r\n");
       js.append("   else\r\n");
	   js.append("   {\r\n");
	   js.append(" 	if(textBox.readOnly)\r\n");
	   js.append(" 	{\r\n");
	   js.append(" 	listBox = document.getElementById(id+'_ReadOnlyTextBox');\r\n");
	   js.append(" 	}\r\n");
	   js.append(" 	else\r\n");
	   js.append(" 	{\r\n");
	   js.append(" 	listBox = document.getElementById(id+'_ListBox');\r\n");
	   js.append(" 	}\r\n");
       js.append("   textBox.value='00';\r\n");
       js.append("   alert('Сʱ�ͷ�����󳤶�ֻ��Ϊ2!');\r\n");   
	   js.append(" 	}\r\n");
	   js.append("	}\r\n");
       
       jstool.js=js;
	    return jstool.getComponentJS();
	}
}