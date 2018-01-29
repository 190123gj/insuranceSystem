package com.born.insurance.webui.control;

public class MenuJsForMozila {
 /**
  * <p>Description: js����д��MenuJS.js�ļ�����ø��ļ�·��������</p>
  * <P><B>����ƹ�˵�ʱ�˵��仯</B><P>
  * <B>function:vpsnm_itemMsOver(item, subGroup, expandDirection, horAdj, verAdj, expandDelay, effect) </B></p>
  *    param item �˵���
  *    param subGroup �Ӳ˵���
  *    param expandDirection �Ӳ˵���Ը��˵�����չ����
  *    param horAdj ˮƽλ��
  *    param verAdj ��ֱλ��
  *    param expandDelay �Ӳ˵���չ��ʱ
  *    param effect �˾���ֵ
  * <P><B>����Ƴ��˵�ʱ�˵��仯</B></P>
  * <B>function:vpsnm_itemMsOut(item, group, subGroup, expandDelay, effect)</B><P>
  *    param item �˵���
  *    param group ��ǰ�˵���
  *    param subGroup �Ӳ˵���
  *    param expandDelay �Ӳ˵���չ��ʱ
  *    param effect �˾���ֵ
  * <P><B>����ƹ�˵���ʱ�˵��仯</B></P>
  * <B>function:vpsnm_groupMsOver(group)</B><P>
  *    param group ��ǰ�˵���
  * <P><B>����ƹ�˵���ʱ�˵��仯</B></P>
  * <B>function:vpsnm_groupMsOut(group, parentItem, parentGroup, expandDelay, effect)</B><P>
  *    param group ��ǰ�˵���
  *    param parentItem ���˵���
  *    param parentGroup ���˵���
  *    param expandDelay �Ӳ˵���չ��ʱ
  *    param effect �˾���ֵ
  */
  public static String getMenuJs(){
    StringBuffer js = new StringBuffer();
    JSTool jsTools = new JSTool("MozilaMenuJS.js");
	js.append("var vpsnm_expandedObjects = new Array(); \r\n");
	js.append("var vpsnm_expandCount = 0;               \r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_itemMsOver(item, subGroup, expandDirection, horAdj, verAdj, expandDelay) \r\n");
	js.append("{\r\n");
	js.append("  var newLeft = 0; \r\n");
	js.append("  var newTop = 0; \r\n");
	js.append("  var oItem = document.getElementById(item); \r\n");
	js.append("  var oSubGroup = document.getElementById(subGroup); \r\n");
	js.append("\r\n");
	js.append("  switch (expandDirection)\r\n");
	js.append("  {\r\n");
	js.append("    case 'belowleft': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem); \r\n");
	js.append("      newTop = vpsnm_pageY(oItem) + oItem.offsetHeight; \r\n");
	js.append("      break; \r\n");
	js.append("    case 'belowright': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) + oItem.offsetWidth - oSubGroup.offsetWidth; \r\n");
	js.append("      newTop =  vpsnm_pageY(oItem) + oItem.offsetHeight; \r\n");
	js.append("      break; \r\n");
	js.append("    case 'aboveleft': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem); \r\n");
	js.append("      newTop =  vpsnm_pageY(oItem) - oSubGroup.offsetHeight; \r\n");
	js.append("      break; \r\n");
	js.append("    case 'aboveright': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) + oItem.offsetWidth - oSubGroup.offsetWidth; \r\n");
	js.append("      newTop =  vpsnm_pageY(oItem) - oSubGroup.offsetHeight; \r\n");
	js.append("      break; \r\n");
	js.append("    case 'rightdown': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) + oItem.offsetWidth; \r\n");
	js.append("      newTop = vpsnm_pageY(oItem); \r\n");
	js.append("      break; \r\n");
	js.append("    case 'rightup': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) + oItem.offsetWidth; \r\n");
	js.append("      newTop = vpsnm_pageY(oItem) - oSubGroup.offsetHeight + oItem.offsetHeight; \r\n");
	js.append("      break; \r\n");
	js.append("    case 'leftdown': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) - oSubGroup.offsetWidth; \r\n");
	js.append("      newTop = vpsnm_pageY(oItem); \r\n");
	js.append("      break; \r\n");
	js.append("    case 'leftup': \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) - oSubGroup.offsetWidth; \r\n");
	js.append("      newTop = vpsnm_pageY(oItem) - oSubGroup.offsetHeight + oItem.offsetHeight; \r\n");
	js.append("      break; \r\n");
	js.append("    default: \r\n");
	js.append("      newLeft = vpsnm_pageX(oItem) + oItem.offsetWidth; \r\n");
	js.append("      newTop = vpsnm_pageY(oItem); \r\n");
	js.append("      break; \r\n");
	js.append("  }  \r\n");
	js.append("\r\n");
	js.append("  newLeft += horAdj; \r\n");
	js.append("  if (verAdj < 0) newTop += verAdj; \r\n");
	js.append("  if (!(navigator.userAgent.indexOf('Netscape6') > 0)) \r\n");
	js.append("  {\r\n");
	js.append("    var cs = window.getComputedStyle(oSubGroup, ''); \r\n");
	js.append("    var topCorrection = parseInt(cs.getPropertyValue('border-top-width').replace('px', ''));  \r\n");
	js.append("    var leftCorrection = parseInt(cs.getPropertyValue('border-left-width').replace('px', ''));  \r\n");
	js.append("\r\n");
	js.append("    newLeft += topCorrection; \r\n");
	js.append("    newTop += topCorrection; \r\n");
	js.append("  }\r\n");
	js.append("\r\n");
	js.append("  oSubGroup.style.left = newLeft + 'px'; \r\n");
	js.append("  oSubGroup.style.top = newTop + 'px'; \r\n");
	js.append("  \r\n");
	js.append("  vpsnm_expand(subGroup); \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_itemMsOut(item, group, subGroup, expandDelay, evt)\r\n");
	js.append("{\r\n");
	js.append("  if (!(vpsnm_isMouseOnObject(subGroup, evt)))\r\n");
	js.append("    vpsnm_collapse(subGroup);\r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_groupMsOver(group)\r\n");
	js.append("{\r\n");
	js.append("\r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("function vpsnm_groupMsOut(group, parentItem, parentGroup, expandDelay, evt)\r\n");
	js.append("{ \r\n");
	js.append("  var subGroup = vpsnm_expandedObjects[vpsnm_expandCount]; \r\n");
	js.append("  if (subGroup == group) subGroup = null; \r\n");
	js.append("\r\n");
	js.append("  \r\n");
	js.append("  if (vpsnm_isMouseOnObject(group, evt) || vpsnm_isMouseOnObject(subGroup, evt) || vpsnm_isMouseOnObject(parentItem, evt))\r\n");
	js.append("    ;\r\n");
	js.append("  else if (vpsnm_isMouseOnObject(parentGroup, evt))\r\n");
	js.append("  {\r\n");
	js.append("    vpsnm_collapse(group); \r\n");
	js.append("    vpsnm_collapse(subGroup); \r\n");
	js.append("  }\r\n");
	js.append("  else\r\n");
	js.append("  {\r\n");
	js.append("    vpsnm_collapseAll(); \r\n");
	js.append("  }\r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_expand(group)\r\n");
	js.append("{\r\n");
	js.append("  var oGroup = document.getElementById(group); \r\n");
	js.append("  if (oGroup.style.visibility != 'visible')\r\n");
	js.append("  {\r\n");
	js.append("    vpsnm_hideSelectElements(group); \r\n");
	js.append("    oGroup.style.visibility = 'visible'; \r\n");
	js.append("    vpsnm_expandCount++; \r\n");
	js.append("    vpsnm_expandedObjects[vpsnm_expandCount] = group; \r\n");
	js.append("  }  \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_collapse(group)\r\n");
	js.append("{\r\n");
	js.append("  var oGroup = document.getElementById(group); \r\n");
	js.append("  if (group != null && group) \r\n");
	js.append("    if (oGroup.style.visibility != 'hidden')\r\n");
	js.append("    {\r\n");
	js.append("      oGroup.style.visibility = 'hidden';     \r\n");
	js.append("      vpsnm_expandCount--; \r\n");
	js.append("    }  \r\n");
	js.append("  if (!(vpsnm_contextUp) && vpsnm_expandCount == 0) \r\n");
	js.append("    vpsnm_restoreSelectElements(); \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_collapseAll()\r\n");
	js.append("{\r\n");
	js.append("  for (var i = vpsnm_expandCount; i >= 1; i--)\r\n");
	js.append("  {\r\n");
	js.append("    var oGroup = document.getElementById(vpsnm_expandedObjects[i]); \r\n");
	js.append("    oGroup.style.visibility = 'hidden';\r\n");
	js.append("  }\r\n");
	js.append("  vpsnm_expandCount = 0;   \r\n");
	js.append("  if (!(vpsnm_contextUp) && vpsnm_expandCount == 0) \r\n");
	js.append("    vpsnm_restoreSelectElements(); \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_hideAllGroups()\r\n");
	js.append("{\r\n");
	js.append("  vpsnm_collapseAll(); \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_isMouseOnObject(objName, evt)\r\n");
	js.append("{\r\n");
	js.append("  if (objName != null)\r\n");
	js.append("  {\r\n");
	js.append("    var obj = document.getElementById(objName); \r\n");
	js.append("    var objLeft = vpsnm_pageX(obj) - 1; \r\n");
	js.append("    var objTop = vpsnm_pageY(obj) - 1; \r\n");
	js.append("    var objRight = objLeft + obj.offsetWidth + 1; \r\n");
	js.append("    var objBottom = objTop + obj.offsetHeight + 1;\r\n");
	js.append("    \r\n");
	js.append("    if ((evt.pageX > objLeft) && (evt.pageX < objRight) && \r\n");
	js.append("        (evt.pageY > objTop) && (evt.pageY < objBottom))\r\n");
	js.append("      return true; \r\n");
	js.append("    else  \r\n");
	js.append("      return false; \r\n");
	js.append("  }\r\n");
	js.append("  else\r\n");
	js.append("    return false; \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_pageX(element)\r\n");
	js.append("{\r\n");
	js.append("  var x = 0;\r\n");
	js.append("  do \r\n");
	js.append("  {\r\n");
	js.append("    if (element.style.position == 'absolute') \r\n");
	js.append("    {\r\n");
	js.append("      return x + element.offsetLeft; \r\n");
	js.append("    }\r\n");
	js.append("    else\r\n");
	js.append("    {\r\n");
	js.append("      x += element.offsetLeft;\r\n");
	js.append("      if (element.offsetParent) \r\n");
	js.append("        if (element.offsetParent.tagName == 'TABLE') \r\n");
	js.append("          if (parseInt(element.offsetParent.border) > 0)\r\n");
	js.append("          {\r\n");
	js.append("            x += 1; \r\n");
	js.append("          }\r\n");
	js.append("    }\r\n");
	js.append("  }\r\n");
	js.append("  while ((element = element.offsetParent));\r\n");
	js.append("  return x; \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_pageY(element)\r\n");
	js.append("{\r\n");
	js.append("  var y = 0;\r\n");
	js.append("  do \r\n");
	js.append("  {\r\n");
	js.append("    if (element.style.position == 'absolute') \r\n");
	js.append("    {\r\n");
	js.append("      return y + element.offsetTop; \r\n");
	js.append("    }\r\n");
	js.append("    else\r\n");
	js.append("    {\r\n");
	js.append("      y += element.offsetTop;\r\n");
	js.append("      if (element.offsetParent) \r\n");
	js.append("        if (element.offsetParent.tagName == 'TABLE') \r\n");
	js.append("          if (parseInt(element.offsetParent.border) > 0)\r\n");
	js.append("          {\r\n");
	js.append("            y += 1; \r\n");
	js.append("          }\r\n");
	js.append("    }\r\n");
	js.append("  }\r\n");
	js.append("  while ((element = element.offsetParent));\r\n");
	js.append("  return y; \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_hideSelectElements(group)\r\n");
	js.append("{\r\n");
	js.append("  if (document.getElementsByTagName) \r\n");
	js.append("  {\r\n");
	js.append("    var arrElements = document.getElementsByTagName('select'); \r\n");
	js.append("    if (vpsnm_hideSelectElems) \r\n");
	js.append("      for (var i = 0; i < arrElements.length; i++) \r\n");
	js.append("        if (vpsnm_objectsOverlapping(document.getElementById(group), arrElements[i]))\r\n");
	js.append("          arrElements[i].style.visibility = 'hidden'; \r\n");
	js.append("  }\r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_objectsOverlapping(obj1, obj2)\r\n");
	js.append("{\r\n");
	js.append("  var result = true; \r\n");
	js.append("  var obj1Left = vpsnm_pageX(obj1); \r\n");
	js.append("  var obj1Top = vpsnm_pageY(obj1); \r\n");
	js.append("  var obj1Right = obj1Left + obj1.offsetWidth; \r\n");
	js.append("  var obj1Bottom = obj1Top + obj1.offsetHeight;\r\n");
	js.append("  var obj2Left = vpsnm_pageX(obj2); \r\n");
	js.append("  var obj2Top = vpsnm_pageY(obj2); \r\n");
	js.append("  var obj2Right = obj2Left + obj2.offsetWidth; \r\n");
	js.append("  var obj2Bottom = obj2Top + obj2.offsetHeight;\r\n");
	js.append("  \r\n");
	js.append("  if (obj1Right <= obj2Left || obj1Bottom <= obj2Top || \r\n");
	js.append("      obj1Left >= obj2Right || obj1Top >= obj2Bottom) \r\n");
	js.append("    result = false; \r\n");
	js.append("  return result; \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_restoreSelectElements()\r\n");
	js.append("{\r\n");
	js.append("  if (document.getElementsByTagName) \r\n");
	js.append("  {\r\n");
	js.append("    var arrElements = document.getElementsByTagName('select'); \r\n");
	js.append("    if (vpsnm_hideSelectElems) \r\n");
	js.append("      for (var i = 0; i < arrElements.length; i++) \r\n");
	js.append("        arrElements[i].style.visibility = 'visible'; \r\n");
	js.append("  }\r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("\r\n");
	js.append("function vpsnm_positionMenu(menu, alignment, offsetX, offsetY)\r\n");
	js.append("{\r\n");
	js.append("  if (vpsnm_dragging) return false; \r\n");
	js.append("  var scrlLeft = window.pageXOffset; \r\n");
	js.append("  var scrlTop = window.pageYOffset;\r\n");
	js.append("  var clientW = window.innerWidth; \r\n");
	js.append("  var clientH = window.innerHeight; \r\n");
	js.append("  var menuWidth = menu.offsetWidth; \r\n");
	js.append("  var menuHeight = menu.offsetHeight; \r\n");
	js.append("  var newLeft = 0; \r\n");
	js.append("  var newTop = 0; \r\n");
	js.append("\r\n");
	js.append("  switch (alignment)\r\n");
	js.append("  {\r\n");
	js.append("    case 'topleft': \r\n");
	js.append("      newLeft = scrlLeft;\r\n");
	js.append("      newTop = scrlTop;\r\n");
	js.append("      break; \r\n");
	js.append("    case 'topmiddle': \r\n");
	js.append("      newLeft = (clientW - menuWidth) / 2 + scrlLeft;\r\n");
	js.append("      newTop = scrlTop;\r\n");
	js.append("      break; \r\n");
	js.append("    case 'topright': \r\n");
	js.append("      newLeft = clientW + scrlLeft - menuWidth;\r\n");
	js.append("      newTop = scrlTop;\r\n");
	js.append("      break; \r\n");
	js.append("    case 'bottomleft': \r\n");
	js.append("      newLeft = scrlLeft;\r\n");
	js.append("      newTop = clientH + scrlTop - menuHeight;\r\n");
	js.append("      break; \r\n");
	js.append("    case 'bottommiddle': \r\n");
	js.append("      newLeft = (clientW - menuWidth) / 2 + scrlLeft;\r\n");
	js.append("      newTop = clientH + scrlTop - menuHeight;\r\n");
	js.append("      break; \r\n");
	js.append("    case 'bottomright': \r\n");
	js.append("      newLeft = clientW + scrlLeft - menuWidth;\r\n");
	js.append("      newTop = clientH + scrlTop - menuHeight;\r\n");
	js.append("      break; \r\n");
	js.append("    default: \r\n");
	js.append("      newLeft = clientW + scrlLeft;\r\n");
	js.append("      newTop = clientH + scrlTop;\r\n");
	js.append("      break; \r\n");
	js.append("  }    \r\n");
	js.append("  \r\n");
	js.append("  newLeft += offsetX; \r\n");
	js.append("  newTop += offsetY; \r\n");
	js.append("  menu.style.left = newLeft; \r\n");
	js.append("  menu.style.top = newTop; \r\n");
	js.append("}\r\n");
	js.append("\r\n");
	js.append("var vpsnm_hideSelectElems = true;     \r\n");
	js.append("var vpsnm_dragging = false;           \r\n");
	js.append("var vpsnm_contextUp = false;          ");


    jsTools.js = js;
    return jsTools.getComponentJS();
  }

}