package com.born.insurance.webui.control;
/**
 * <p>
 * Title: the javascript code of the OfbizTextBox control
 * </p>
 * <p>
 * Description: the javascript code of the TextBoxJS control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author: �ᴺ��(qch)
 * 
 * @version 1.0
 */
public class OfbizTextBoxJS {
	public OfbizTextBoxJS() {
	}
	/**
	 * <p>
	 * Description: get the javascript code of the OfbizTextBox control
	 * </p>
	 * 
	 * @param path:
	 *            the path of the javasrcipt code file
	 * @return the referece of the javascript code file <br>
	 *         <p>
	 *         1����ȡ���ÿؼ�ֵ�ĺ������������Ϊ�ؼ�ID��ֵ
	 *         </p>
	 *         <p>
	 *         OfbizTextBox_SetValue
	 *         </p>
	 *         <b>@param: </b> �ؼ�ID <b>@paramType: </b> String <b>@param: </b>
	 *         �ؼ�ֵ <b>@paramType: </b> String <br>
	 *         <p>
	 *         2����ȡ�õ��ؼ�ֵ�ĺ������������Ϊ�ؼ�ID
	 *         </p>
	 *         <p>
	 *         OfbizTextBox_GetValue
	 *         </p>
	 *         <b>@param: </b> �ؼ�ID <b>@paramType: </b> String <b>@return: </b>
	 *         �ؼ�ֵ <br>
	 *         <p>
	 *         3����ȡ���ÿؼ����Եĺ������������Ϊ�ؼ�ID��������ֵ
	 *         </p>
	 *         <p>
	 *         OfbizTextBox_setProperty
	 *         </p>
	 *         <b>@param: </b> �ؼ�ID <b>@paramType: </b> String <b>@param: </b>
	 *         ������ <b>@paramType: </b> String <b>@param: </b> ����ֵ <b>
	 *         @paramType: </b> String <br>
	 *         <p>
	 *         4.��ȡ�õ��ؼ����Եĺ������������Ϊ�ؼ�ID��������
	 *         </p>
	 *         <p>
	 *         OfbizTextBox_getProperty
	 *         </p>
	 *         <b>@param: </b> �ؼ�ID,������ <b>@paramType: </b> String <b>@param:
	 *         </b> ������ <b>@paramType: </b> String <b>@return: </b> ����ֵ
	 *  
	 */
	public static String getOfbizTextBoxJS() {
		JSTool jstool = new JSTool("OfbizTextBox.js");
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS();
		 }
		StringBuffer js = new StringBuffer();
		js.append("/**\r\n");
		js.append(" *JS/OfbizTextBox.js\r\n");
		js.append(" */\r\n");
		js.append("/**\r\n");
		js.append(" */\r\n");
		js.append("\r\n");
		js.append("function Integer(objText,ObjTextEvent) {\r\n");
		js.append("    var k = ObjTextEvent.keyCode;\r\n");
		//js.append("    if ( k==107||k==17||k==20||k==9||(k <= 45 && k >= 33) ||(k <= 57 && k >= 48) ||(k <= 105 && k >= 96)|| k == 46 || k == 8||k==13)\r\n");
		js.append("    if (true)\r\n");
		js.append("    {\r\n");
		js.append("        return true;\r\n");
		js.append("    }\r\n");
		js.append("    else {\r\n");
		js.append("        return false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("/**\r\n");
		js.append(" */\r\n");
		js.append("\r\n");
		js.append("function Positive(objText,ObjTextEvent) {\r\n");
		js.append("    var k = ObjTextEvent.keyCode;\r\n");
		js.append("    if((k==190||k==110)&&objText.value.indexOf(\".\")>0){\r\n");
		js.append("    	return false;\r\n");
		js.append("    }\r\n");
		//js.append("    if ( k==107||k==17||k==20||k==9||(k <= 45 && k >= 33) ||(k <= 57 && k >= 48) ||(k <= 105 && k >= 96)|| k == 46 || k == 110 || k == 190||k == 8||k==13)\r\n");
		js.append("    if (true)\r\n");
		js.append("    {\r\n");
		js.append("        return true;\r\n");
		js.append("    }\r\n");
		js.append("    else {\r\n");
		js.append("        return false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("/**\r\n");
		js.append("*/\r\n");
		js.append("function NumberOnly(objText,ObjTextEvent) {\r\n");
		js.append("    var k = ObjTextEvent.keyCode;\r\n");
		js
				.append("    if((k==190||k==110)&&objText.value.indexOf(\".\")>0){\r\n");
		js.append("        return false;\r\n");
		js.append("    }\r\n");
		js.append("    if((k==189||k==109)&&objText.value.indexOf(\"-\")>=0){\r\n");
		js.append("        return false;\r\n");
		js.append("    }\r\n");
		//js.append("    if ( k==107||k==17||k==20||k==9||(k <= 45 && k >= 33) ||(k <= 57 && k >= 48)||(k <= 105 && k >= 96) || k == 46 || k == 45 || k == 110 ||k == 190 || k == 189 || k == 109||k == 8||k==13) {\r\n");
		js.append("    if (true) {\r\n");
		js.append("        return true;\r\n");
		js.append("    }\r\n");
		js.append("    else {\r\n");
		js.append("        return false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("/**\r\n");
		js.append("    */\r\n");
		js.append("function CodeOnly(ObjTextEvent) {\r\n");
		js.append("    var k = ObjTextEvent.keyCode;\r\n");
		js.append("    if ( k==16||k==17||k==20||k==9||(k <= 45 && k >= 33) ||(k <= 57 && k >= 48) ||(k <= 105 && k >= 96)|| k <= 90 && k >= 65 ||(k <= 122 && k >= 97) || k == 45 || k == 95 || k == 189 ||k == 109||k == 8||k==13) {\r\n");
		js.append("        return true;\r\n");
		js.append("    }\r\n");
		js.append("    else {\r\n");
		js.append("        return false;\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("/**\r\n");
		js.append("*/\r\n");
		js.append("function ConvertData(objText) {\r\n");
		js.append("  var value = objText.value.replace(/,/g,\"\");\r\n");
		js.append("  value = value.replace(/ /g,\"\");\r\n");
		js.append("  if(value==\"\")objText.value=\"\";\r\n");
		js.append("  if(true){\r\n");
		js
				.append("    DecimalDigits=objText.getAttribute(\"DecimalDigits\");\r\n");
		js.append("    SignNum=objText.getAttribute(\"SignNum\");\r\n");
		js.append("    if (value!=\"\") {\r\n");
		js.append("        if (isNaN(value)) {\r\n");
		js.append("                objText.value = \"\";\r\n");
		js.append("        }\r\n");
		js.append("        else {\r\n");
		js.append("            var prefix = \"\";\r\n");
		js.append("            var s = value;\r\n");
		js.append("            if (s.indexOf(\"-\") == 0) {\r\n");
		js.append("                prefix = \"-\";\r\n");
		js.append("                s = s.substring(1);\r\n");
		js.append("            }\r\n");
		js.append("            if (s.indexOf(\"0\") == 0) {\r\n");
		js.append("                s = TrimNum(s)\r\n");
		js.append("            }\r\n");
		js.append("            var f = \"\";\r\n");
		js.append("            var l = \"\";\r\n");
		js.append("            if (s.indexOf(\".\") != -1) {\r\n");
		js.append("                f = s.substring(0, s.indexOf(\".\"));\r\n");
		js.append("                l = s.substring(s.indexOf(\".\") + 1);\r\n");
		js.append("            }\r\n");
		js.append("            else {\r\n");
		js.append("                f = s;\r\n");
		js.append("            }\r\n");
		js.append("            var result;\r\n");
		js.append("            var theNum = 0;\r\n");
		js.append("            if (DecimalDigits == -1) {\r\n");
		js.append("                if (l.length > 0) {\r\n");
		js.append("                    result = f + \".\" + l;\r\n");
		js.append("                }\r\n");
		js.append("                else {\r\n");
		js.append("                    result = f;\r\n");
		js.append("                }\r\n");
		js.append("            }\r\n");
		js.append("            else if (DecimalDigits == 0) {\r\n");
		js.append("                var lastNum = \"0\";\r\n");
		js.append("                if (l.length > 0) {\r\n");
		js
				.append("                    lastNum = l.substring(DecimalDigits, DecimalDigits*1 + 1);\r\n");
		js.append("                }\r\n");
		js
				.append("                if (parseInt(lastNum, 0) >= SignNum && SignNum != -1) {\r\n");
		js.append("                    theNum++;\r\n");
		js.append("                }\r\n");
		js.append("                if (theNum > 0) {\r\n");
		js
				.append("                    result = (parseInt(f, 0) + 1) + \"\";\r\n");
		js.append("                }\r\n");
		js.append("                else {\r\n");
		js.append("                    result = f;\r\n");
		js.append("                }\r\n");
		js.append("            }\r\n");
		js.append("            else {\r\n");
		js.append("                if (l.length > 0) {\r\n");
		js.append("                    var lastNum = \"0\";\r\n");
		js.append("                    if (l.length > DecimalDigits) {\r\n");
		js
				.append("                        lastNum = l.substring(DecimalDigits, DecimalDigits*1 + 1);\r\n");
		js
				.append("                        l = l.substring(0, DecimalDigits);\r\n");
		js.append("                    }\r\n");
		js.append("                    else{\r\n");
		js
				.append("                        for (var i = l.length; i < DecimalDigits; i++) {\r\n");
		js.append("                            l = l + \"0\";\r\n");
		js.append("                        }\r\n");
		js.append("                    }\r\n");
		js
				.append("                    if (parseInt(lastNum, 0) >= SignNum) {\r\n");
		js.append("                        theNum++;\r\n");
		js.append("                    }\r\n");
		js
				.append("                    increscent = Math.pow(10, DecimalDigits);\r\n");
		js
				.append("                    increscentL = parseInt(1 + \"\" + l) + theNum;\r\n");
		js
				.append("                    var toInt = parseInt( (increscentL - increscent) +\"\")\r\n");
		js.append("                    if (toInt < increscent) {\r\n");
		js.append("                        toInt = 0\r\n");
		js.append("                    }\r\n");
		js.append("                    else {\r\n");
		js.append("                        toInt = 1\r\n");
		js.append("                    }\r\n");
		js.append("                    if(f==\"\"){f=\"0\";}\r\n");
		js
				.append("                    result = parseInt(f, 0) + toInt + \".\" +(increscentL + \"\").substring(1);\r\n");
		js.append("                }\r\n");
		js.append("                else {\r\n");
		js
				.append("                    for (var i = 0; i < DecimalDigits; i++) {\r\n");
		js.append("                        l = l + \"0\";\r\n");
		js.append("                    }\r\n");
		js.append("                    if(f==\"\"){f=\"0\";}\r\n");
		js.append("                    result = f + \".\" + l;\r\n");
		js.append("                }\r\n");
		js.append("            }\r\n");
		js.append("            objText.value = prefix + result;\r\n");
		js.append("        }\r\n");
		js.append("      }\r\n");
		js.append("  }\r\n");
		js.append("}\r\n");
		js.append("/**\r\n");
		js.append("*/\r\n");
		js.append("function convertMoneyFormat(objText) {\r\n");
		js.append("  value = objText.value.replace(/,/g,\"\");\r\n");
		js.append("  if(true){\r\n");
		js
				.append("    listSeparator=objText.getAttribute(\"listSeparator\");\r\n");
		js.append("    if (value!=\"\") {\r\n");
		js.append("        if (isNaN(value)) {\r\n");
		js.append("            objText.value = \"\";\r\n");
		js.append("        }\r\n");
		js.append("        else {\r\n");
		js.append("            var m = value;\r\n");
		js.append("            var prefix = \"\";\r\n");
		js.append("            if (m.indexOf(\"-\") == 0) {\r\n");
		js.append("                prefix = \"-\";\r\n");
		js.append("                m = m.substring(1);\r\n");
		js.append("            }\r\n");
		js.append("            var result = \"\";\r\n");
		js.append("            result = m;\r\n");
		js.append("            var f = \"\";\r\n");
		js.append("            var l = \"\";\r\n");
		js.append("            if (result.indexOf(\".\", 0) != -1) {\r\n");
		js
				.append("                f = result.substring(0, result.indexOf(\".\"));\r\n");
		js
				.append("                l = result.substring(result.indexOf(\".\") + 1);\r\n");
		js.append("          if(l.length<2){l=l+\"0\";}   }\r\n");
		js.append("            else {\r\n");
		js.append("                f = result;\r\n");
		js.append("            }\r\n");
		js.append("            var length = f.length - 1;\r\n");
		js
				.append("            for (var i = length - 3; i >= 0; i = i - 3) {\r\n");
		js
				.append("                f = f.substring(0, i + 1) + listSeparator + f.substring(i + 1);\r\n");
		js.append("            }\r\n");
		js.append("            if (l == \"\") {\r\n");
		js.append("                objText.value = prefix + f;\r\n");
		js.append("                //return prefix+f;\r\n");
		js.append("            }\r\n");
		js.append("            else {\r\n");
		js
				.append("                objText.value = prefix + f + \".\" + l;\r\n");
		js.append("                //return prefix+f+\".\"+l;\r\n");
		js.append("            }\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("  }\r\n");
		js.append("}\r\n");
		js.append("/**\r\n");
		js.append("*\r\n");
		js.append("*/\r\n");
		js.append("function NumberValidator(objText) {\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue != \"\") {\r\n");
		js
				.append("        if (isNaN(strValue)||!(parseFloat(strValue)>=0)) {\r\n");
		js.append("            strValue = \"\";\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js
				.append("    if(strValue!=\"\"){objText.value=parseFloat(strValue);}else{objText.value=\"\";}\r\n");
		js.append("}\r\n");
		js.append("function SerialNumberValidator(objText) {\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue != \"\") {\r\n");
		js
				.append("        var validationexpression =\"[~!@#\\$\\^&\\*=+'\\\";:,./?\\\\|\\\\\\\\<>]{1,}\";\r\n");
		js.append("        var rx = new RegExp(validationexpression);\r\n");
		js.append("        var matches = rx.exec(strValue);\r\n");
		js.append("        if (matches != null) {\r\n");
		js.append("            objText.value = \"\";\r\n");
		js.append("            if(objText.onchange)objText.onchange();\r\n");
		js.append("            objText.focus();\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function EmailValidator(objText) {\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue != \"\") {\r\n");
		js.append("        var validationexpression =\"\\\\w+([-+.]\\\\w+)*@\\\\w+([-.]\\\\w+)*\\\\.\\\\w+([-.]\\\\w+)*\";\r\n");
		js.append("        var rx = new RegExp(validationexpression);\r\n");
		js.append("        var matches = rx.exec(strValue);\r\n");
		js.append("        if (matches == null) {\r\n");
		js.append("            objText.value = \"\";\r\n");
		js.append("            if(objText.onchange)objText.onchange();\r\n");		
		js.append("            objText.focus();\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function PostalValidator(objText) {\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue != \"\") {\r\n");
		js.append("        var validationexpression = \"\\\\d{6}\";\r\n");
		js.append("        var rx = new RegExp(validationexpression);\r\n");
		js.append("\r\n");
		js.append("        var matches = rx.exec(strValue);\r\n");
		js.append("            if (matches == null) {\r\n");
		js.append("            objText.value = \"\";\r\n");
		js.append("            if(objText.onchange)objText.onchange();\r\n");
		js.append("            objText.focus();\r\n");
		js.append("            }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function PhoneNumValidator(objText) {\r\n");
		js.append("    CodeValidator(objText);\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue != \"\") {\r\n");
		js
				.append("        var validationexpression = \"(\\\\(\\\\d{3,6}\\\\)|\\\\d{3,6}-)?\\\\d{0,8}\";\r\n");
		js.append("        var rx = new RegExp(validationexpression);\r\n");
		js.append("        var matches = rx.exec(strValue);\r\n");
		js.append("        if (matches == null) {\r\n");
		js.append("            objText.value = \"\";\r\n");
		js.append("            if(objText.onchange)objText.onchange();\r\n");
		js.append("            objText.focus();\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function RequiredFieldValidator(objText) {\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue == \"\") {\r\n");
		js.append("        if(document.all){\r\n");
		js.append("            objText.focus();\r\n");
		js.append("            alert(\"���������Ϊ��!\");\r\n");
		js.append("    	   }\r\n");
		js.append("    	   else{\r\n");
		js.append("            alert(\"���������Ϊ��!\");\r\n");
		js.append("            objText.focus();\r\n");
		js.append("    	   }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function CodeValidator(objText) {\r\n");
		js.append("    var strValue = trim(objText.value);\r\n");
		js.append("    if (strValue != \"\") {\r\n");
		js.append("        var validationexpression = \"[`~\\$\\^&'\\\";|<>]{1,}\";\r\n");
		js.append("        var rx = new RegExp(validationexpression);\r\n");
		js.append("        var matches = rx.exec(strValue);\r\n");
		js.append("        if (matches != null) {\r\n");
		js.append("            objText.value = \"\";\r\n");
		js.append("            if(objText.onchange)objText.onchange();\r\n");
		js.append("            objText.focus();\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function trim(str) {\r\n");
		js.append("    var tmpstr = new String();\r\n");
		js.append("    tmpstr = str;\r\n");
		js.append("    tmpstr = tmpstr.replace(/\\s/g,\"\");\r\n");
		js.append("    return tmpstr;\r\n");
		js.append("}\r\n");
		js.append("function TrimSpace(objText) {\r\n");
		js.append("    var tmpstr = objText.value;\r\n");
		js.append("    tmpstr = tmpstr.replace(/\\s/g,\"\");\r\n");
		js.append("    objText.value = tmpstr;\r\n");
		js.append("}\r\n");
		js.append("function RegexValid(objText, Expression) {\r\n");
		js.append("   var strValue = trim(objText.value);\r\n");
		js.append("   if (strValue != \"\") {\r\n");
		js.append("       var validationexpression = Expression;\r\n");
		js.append("       var rx = new RegExp(validationexpression);\r\n");
		js.append("       var matches = rx.exec(strValue);\r\n");
		js.append("       if (matches == null) {\r\n");
		js.append("           objText.value = \"\";\r\n");
		js.append("            if(objText.onchange)objText.onchange();\r\n");
		js.append("           objText.focus();\r\n");
		js.append("       }\r\n");
		js.append("   }\r\n");
		js.append("}\r\n");
		js.append("function OfbizTextBox_SetValue(ID,Value){\r\n");
		js.append("    OfbizTextBox=document.getElementById(ID);\r\n");
		js.append("    OfbizTextBox.value=Value;\r\n");
		js
				.append("    if(OfbizTextBox.getAttribute(\"DecimalDigits\")!=null&&OfbizTextBox.getAttribute(\"SignNum\")!=null)\r\n");
		js.append("    {\r\n");
		js.append("    	ConvertData(OfbizTextBox);\r\n");
		js.append("    }\r\n");
		js
				.append("    if(OfbizTextBox.getAttribute(\"listSeparator\")!=null)\r\n");
		js.append("    {\r\n");
		js.append("    convertMoneyFormat(OfbizTextBox);\r\n");
		js.append("    }\r\n");
		js.append("    if(OfbizTextBox.getAttribute(\"IsConvertZero\")==\"true\")\r\n");
		js.append("    {\r\n");
		js.append("    	ZeroToNull(OfbizTextBox);\r\n");
		js.append("    }\r\n");
		js.append("    OfbizTextBox_CutByMaxLength(OfbizTextBox)\r\n");
		js.append("    OfbizTextBox_SetTitle(OfbizTextBox)\r\n");
		js.append("}\r\n");
		js.append("function OfbizTextBox_GetValue(ID){\r\n");
		js.append("    OfbizTextBox=document.getElementById(ID);\r\n");
		js.append("        if(OfbizTextBox.getAttribute(\"IsNumber\")==\"true\"&&(OfbizTextBox.value==null||OfbizTextBox.value==\"\")){\r\n");
		js.append("        		if(OfbizTextBox.getAttribute(\"sustainNull\")!=\"true\")");
		js.append("        		{\r\n");
		js.append("        			return 0; \r\n");
		js.append("        		} \r\n");		
		js.append("        }\r\n");
		js.append("    OfbizTextBox_SetValue(ID,OfbizTextBox.value);\r\n");
		js.append("    return OfbizTextBox.value;\r\n");
		js.append("}\r\n");
		js.append("function RevertData(objText)\r\n");
		js.append("{\r\n");
		js.append("    if(true){\r\n");
		js.append("        var tmpstr = objText.value;\r\n");
		js.append("        tmpstr = tmpstr.replace(/,/g,\"\");\r\n");
		js.append("        objText.value = tmpstr;\r\n");
		js.append("        if(document.all){\r\n");
		js.append("            var r =objText.createTextRange();\r\n");
		js.append("            r.moveStart('character',objText.value.length);\r\n");
		js.append("            r.select();\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function TrimNum(num){\r\n");
		js.append("    var i;\r\n");
		js.append("    if(num=='0')return num;\r\n");
		js.append("    for(i=0;i<num.length;){\r\n");
		js.append("        if(num.charAt(i)=='0'){\r\n");
		js.append("            i++;\r\n");
		js.append("        }\r\n");
		js.append("        else if(num.charAt(i)=='.'){\r\n");
		js.append("            i--;\r\n");
		js.append("            break;\r\n");
		js.append("        }\r\n");
		js.append("        else{\r\n");
		js.append("            break;\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("    if(i==num.length)\r\n");
		js.append("    {\r\n");
		js.append("    		i=num.length-1;\r\n");
		js.append("    	}\r\n");
		js.append("    return num.substring(i);\r\n");
		js.append("}\r\n");
		js.append("function OfbizTextBox_setProperty (ID,Attribute,Value){\r\n");
		js.append("    TextBox=document.getElementById(ID);\r\n");
		js.append("    if(Attribute==\"width\"){\r\n");
		js.append("    setValue=\"TextBox.style.\"+Attribute+\"=\"+Value;}\r\n");
		js.append("    else{\r\n");
		js.append("    setValue=\"TextBox.\"+Attribute+\"=\"+Value;}\r\n");
		js.append("    eval(setValue);\r\n");
		js.append("}\r\n");
		js.append("function OfbizTextBox_getProperty (ID,Attribute,Value){\r\n");
		js.append("    TextBox=document.getElementById(ID);\r\n");
		js.append("    getValue=\"TextBox.\"+Attribute;\r\n");
		js.append("    return eval(getValue);\r\n");
		js.append("}\r\n");
		js.append("function ZeroToNull(objText){\r\n");
		js.append("    if((trim(objText.value)==\"0\"||parseFloat(objText.value)==0)&&objText.getAttribute(\"IsConvertZero\")==\"true\"){\r\n");
		js.append("        objText.value=\"\";\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("function OfbizTextBox_SetTitle(objText){\r\n");
		js.append("    objText.title=objText.value;\r\n");
		js.append("}\r\n");
		js.append("function OfbizTextBox_CutByMaxLength(objText){\r\n");
		js.append("    if(objText.value.replace(/[^\\x00-\\xff]/g,'**').length>objText.maxLength){\r\n");
		js.append("        for(i=1;i<objText.maxLength;i++){\r\n");
		js.append("            temp=objText.value.substring(0,i);\r\n");
		js.append("            length1=objText.value.substring(0,i+1).replace(/[^\\x00-\\xff]/g,'**').length;\r\n");
		js.append("            if(length1>objText.maxLength){\r\n");
		js.append("                objText.value=temp;\r\n");
		js.append("                break;\r\n");
		js.append("            }\r\n");
		js.append("        }\r\n");
		js.append("    }\r\n");
		js.append("}\r\n");
		js.append("");
		jstool.js = js;
		return jstool.getComponentJS();
	}
}
