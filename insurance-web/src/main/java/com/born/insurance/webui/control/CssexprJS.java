//package org.gabiz.test;
//package vp3menu;
package com.born.insurance.webui.control;

//import com.bornsoft.core.gaui.component.JSTool;

/**
 *
 * <p>Title: javascript function of the calendar control</p>
 * <p>Description: javascript function of the calendar control</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: born</p>
 * @author: qch
 * @version 1.0
 */
public class CssexprJS {

  /**
   * <p>Description: get javascript functions of the calendar control</p>
   * @param path: path of the javascript file
   * @return: the reference of the javascript file
   */
  public static String CssexprJS(){

      JSTool jstool=new JSTool("cssexpr.js");
      StringBuffer js = new StringBuffer();
      js.append("function constExpression(x) {\r\n");
      js.append("	return x;\r\n");
      js.append("}\r\n");
      js.append("\r\n");
      js.append("function simplifyCSSExpression() {\r\n");
      js.append("	try {\r\n");
      js.append("		var ss,sl, rs, rl;\r\n");
      js.append("		ss = document.styleSheets;\r\n");
      js.append("		sl = ss.length\r\n");
      js.append("	\r\n");
      js.append("		for (var i = 0; i < sl; i++) {\r\n");
      js.append("			simplifyCSSBlock(ss[i]);\r\n");
      js.append("		}\r\n");
      js.append("	}\r\n");
      js.append("	catch (exc) {\r\n");
      js.append("		alert(\"Got an error while processing css. The page should still work but might be a bit slower\");\r\n");
      js.append("		throw exc;\r\n");
      js.append("	}\r\n");
      js.append("}\r\n");
      js.append("\r\n");
      js.append("function simplifyCSSBlock(ss) {\r\n");
      js.append("	var rs, rl;\r\n");
      js.append("	\r\n");
      js.append("	for (var i = 0; i < ss.imports.length; i++)\r\n");
      js.append("		simplifyCSSBlock(ss.imports[i]);\r\n");
      js.append("	\r\n");
      js.append("	if (ss.cssText.indexOf(\"expression(constExpression(\") == -1)\r\n");
      js.append("		return;\r\n");
      js.append("\r\n");
      js.append("	rs = ss.rules;\r\n");
      js.append("	rl = rs.length;\r\n");
      js.append("	for (var j = 0; j < rl; j++)\r\n");
      js.append("		simplifyCSSRule(rs[j]);\r\n");
      js.append("	\r\n");
      js.append("}\r\n");
      js.append("\r\n");
      js.append("function simplifyCSSRule(r) {\r\n");
      js.append("	var str = r.style.cssText;\r\n");
      js.append("	var str2 = str;\r\n");
      js.append("	var lastStr;\r\n");
      js.append("	do {\r\n");
      js.append("		lastStr = str2;\r\n");
      js.append("		str2 = simplifyCSSRuleHelper(lastStr);\r\n");
      js.append("	} while (str2 != lastStr)\r\n");
      js.append("\r\n");
      js.append("	if (str2 != str)\r\n");
      js.append("		r.style.cssText = str2;\r\n");
      js.append("}\r\n");
      js.append("\r\n");
      js.append("function simplifyCSSRuleHelper(str) {\r\n");
      js.append("	var i, i2;\r\n");
      js.append("	i = str.indexOf(\"expression(constExpression(\");\r\n");
      js.append("	if (i == -1) return str;\r\n");
      js.append("	i2 = str.indexOf(\"))\", i);\r\n");
      js.append("	var hd = str.substring(0, i);\r\n");
      js.append("	var tl = str.substring(i2 + 2);\r\n");
      js.append("	var exp = str.substring(i + 27, i2);\r\n");
      js.append("	var val = eval(exp)\r\n");
      js.append("	return hd + val + tl;\r\n");
      js.append("}\r\n");
      js.append("\r\n");
      js.append("if (/msie/i.test(navigator.userAgent) && window.attachEvent != null) {\r\n");
      js.append("	window.attachEvent(\"onload\", function () {\r\n");
      js.append("		simplifyCSSExpression();\r\n");
      js.append("	});\r\n");
      js.append("}");


      jstool.js=js;
    return jstool.getComponentJS();
  }
}
