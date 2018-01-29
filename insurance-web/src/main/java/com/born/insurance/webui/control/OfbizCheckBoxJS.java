package com.born.insurance.webui.control;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author QingJian
 * @version 1.0
 */

public class OfbizCheckBoxJS {
	public OfbizCheckBoxJS() {
	}

	/**
	 * 
	 * ����ofbizCheckBox��Value
	 * </p>
	 * <p>
	 * OfbizCheckBox_setValue
	 * </p>
	 * <b>@param:</b> �ؼ�ID <b>@paramType:</b> String <b>@param:</b> �ؼ�ֵ
	 * <b>@paramType:</b> String <br>
	 * <p>
	 * ��ȡOfbizCheckBox��Value
	 * </p>
	 * <p>
	 * OfbizCheckBox_getValue
	 * </p>
	 * <b>@param:</b> �ؼ�ID <b>@paramType:</b> String <b>@return:</b> �ؼ�ֵ
	 * <br>
	 * 
	 * <p>
	 * ����OfbizCheckBox Checked
	 * </p>
	 * <p>
	 * OfbizCheckBox_setChecked
	 * </p>
	 * <b>@param:</b> �ؼ�ID <b>@paramType:</b> String <b>@param:</b>
	 * Checked <b>@paramType:</b> boolean <br>
	 * <p>
	 * ��ȡofbizCheckBox Checked
	 * </p>
	 * <p>
	 * OfbizCheckBox_getChecked
	 * </p>
	 * <b>@param:</b> �ؼ�ID <b>@paramType:</b> String <b>@return:</b>
	 * Checked <br>
	 * <p>
	 * 
	 * @return
	 */
	public static String getOfbizCheckBoxJS() {
		JSTool jstool = new JSTool("OfbizCheckBox.js");
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS() ;
		 }
		StringBuffer js = new StringBuffer();
		js.append("function OfbizCheckBox_setChecked (ID,Check){ \r\n");
		js.append("    ofbizCheckBox=document.getElementById(ID);\r\n");
		js.append("    if(Check==\"true\"||Check==\"1\"||Check==ofbizCheckBox.getAttribute(\"ValueChecked\")){\r\n");
		js.append("    	ofbizCheckBox.checked=true;\r\n");
		js.append("    }\r\n");
		js.append("    else{\r\n");
		js.append("    	ofbizCheckBox.checked=false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("\r\n");
		js.append("function OfbizCheckBox_getChecked (ID){\r\n");
		js.append("    ofbizCheckBox=document.getElementById(ID);\r\n");
		js.append("    if((ofbizCheckBox.getAttribute(\"ValueChecked\")!=null)&&(ofbizCheckBox.getAttribute(\"ValueChecked\")!=\"\")){\r\n");
		js.append("    		ValueChecked=ofbizCheckBox.getAttribute(\"ValueChecked\");}\r\n");
		js.append("    else{ValueChecked=\"1\";}\r\n");
		js.append("    if((ofbizCheckBox.getAttribute(\"ValueUnChecked\")!=null)&&(ofbizCheckBox.getAttribute(\"ValueUnChecked\")!=\"\")){\r\n");
		js.append("    		ValueUnChecked=ofbizCheckBox.getAttribute(\"ValueUnChecked\");}\r\n");
		js.append("    else{ValueUnChecked=\"0\";}\r\n");
		js.append("    return ofbizCheckBox.checked?ValueChecked:ValueUnChecked;\r\n");
		js.append("}\r\n");
		js.append("\r\n");
		js.append("function OfbizCheckBox_setValue (ID,Value){\r\n");
		js.append("    ofbizCheckBox=document.getElementById(ID);\r\n");
		js.append("    if(Value==ofbizCheckBox.getAttribute(\"ValueChecked\")){\r\n");
		js.append("        ofbizCheckBox.checked=true;}\r\n");
		js.append("    else{\r\n");
		js.append("    	ofbizCheckBox.checked=false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("\r\n");
		js.append("function OfbizCheckBox_getValue (ID){\r\n");
		js.append("    ofbizCheckBox=document.getElementById(ID);\r\n");
		js.append("   if(ofbizCheckBox.checked==true){\r\n");
		js.append(
			"        return ofbizCheckBox.getAttribute(\"ValueChecked\");\r\n");
		js.append("   }\r\n");
		js.append("   else{\r\n");
		js.append(
			"   	return ofbizCheckBox.getAttribute(\"ValueUnChecked\");\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("\r\n");
		js.append("function OfbizCheckBox_setText (ID,TextCheckBox){\r\n");
		js.append("    ofbizCheckBox=document.getElementById(ID);\r\n");
		js.append(
			"    if(TextCheckBox==ofbizCheckBox.getAttribute(\"TextChecked\")){\r\n");
		js.append("        ofbizCheckBox.checked=true;}\r\n");
		js.append("    else{\r\n");
		js.append("    	ofbizCheckBox.checked=false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("\r\n");
		js.append("function OfbizCheckBox_getText(ID){\r\n");
		js.append("   ofbizCheckBox=document.getElementById(ID);\r\n");
		js.append("   if(ofbizCheckBox.checked==true){\r\n");
		js.append(
			"        return ofbizCheckBox.getAttribute(\"TextChecked\");\r\n");
		js.append("   }\r\n");
		js.append("   else{\r\n");
		js.append(
			"   	return ofbizCheckBox.getAttribute(\"TextUnChecked\");\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		jstool.js = js;
		return jstool.getComponentJS();
	}

}