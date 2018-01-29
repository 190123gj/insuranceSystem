/**
 * Created on 2003-10-15
 *
 *
 */
package com.born.insurance.webui.control;


  
  /**
 * <p>Title: SearchText�ͻ��˷���(javascript)<br>
 * <p>Description: SearchText�ͻ��˷���<br>
 * <p>Copyright: Copyright (c) 2003<br>
 * <p>Company: bornsoft<br> *
 * @author: He Kun
 * @version 1.0
 * @since 1.0
   * @see com.bornsoft.core.gaui.component.SearchText
   */
  class SearchTextJS
  {
	/**	
	   * <br><B>SearchText�ͻ��˷���(javascript)</B><br><br>
	   * 
	   * <br><B>������ʵֵ</B><br>
	   * function:SearchText_setValue(searchTextID,value)<br>
	   * param: searchTextID �����Id<br>
	   * param: value �������ʵֵ<br>
	   * <br>
	   * <br><B>������ʾֵ</B><br>
	   * function:SearchText_setText(searchTextID,text)<br>
	   * param: searchTextID �����Id<br>
	   * param: text �������ʾֵ 	<br>  
	   * <br>
	   * <br><B>��ȡ��ʵֵ</B><br>
	   * function: SearchText_getValue(searchTextID)<br>
	   * param: searchTextID �����Id<br>
	   * return: ָ��SearchText�ؼ�����ʵֵ	<br>  
	   * <br>
	      * <br><B>��ȡ��ʾֵ 	</B><br>
	   * function: SearchText_getText(searchTextID)<br>
	   * param: searchTextID �����Id<br>
	   * return: ָ��SearchText�ؼ�����ʾֵ <br>	  
	   * <br>
	      * <br><B>���SearchText�����ָ��SearchText����ʾֵ����ʵֵ 	</B><br>
	   * function: SearchText_clear(searchTextID)<br>
	   * param: searchTextID �����Id<br>	 	  
	   * <br>
	   * 
	   * <br><B>��������ֵ,��������SearchText�Ѷ��������ֵ��Ҳ���������Զ�������ֵ 	</B><br>
	   * function: SearchText_setProperty(searchTextID,propertyName,value)<br>
	   * param: searchTextID �����Id<br>
	   * param: propertyName ������	 <br>	  
	   * param: value ����ֵ<br>
	   * ע���Ѷ������ԣ�width,readOnly,disabled,pageUrl,windowHeight,windowWidth
	   * <br>
	    * <br><B>��ȡ����ֵ </B><br>
	   * function:  SearchText_getProperty(searchTextID,propertyName)<br>
	   * param: searchTextID �����Id<br>	 	  
	   * param: propertyName ������<br>
	   * return: ָ��������ֵ<br>
	   * ע���Ѷ������ԣ�width,readOnly,disabled,pageUrl,windowHeight,windowWidth
	   * <br> 
	   * 
	   * <br><B>��ȡSearchText�ؼ� 	</B><br>
	   * function: SearchText_getSearchText(searchTextID)<br>
	   * param: searchTextID �����Id	<br> 	  
	   * return: ָ��ID��SearchText�ؼ�������<br>
	   * <br> 
	   *
	   * @return: �ű��ļ�������
	   */
	  public static String getSearchTextJS()
	  {

		JSTool jstool=new JSTool("SearchTextJS.js");
		StringBuffer js = jstool.js;
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS();
		 }

		js.append("/**\r\n");
		js.append(" *Component SearchText Method\r\n");
		js.append("  *@company bornsoft ,  @author He Kun\r\n");
		js.append("  *@date  since 2003-10-15\r\n");
		js.append("  *@version sindce 1.0\r\n");
		js.append(" */ \r\n");
		js.append("\r\n");
		js.append("    function SearchText_init(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("       SearchText_setFillBackFinished(searchTextID,true);\r\n");
		js.append("   }\r\n");
		js.append("   function SearchText_triggerOnChange(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("		var searchText=SearchText_getSearchText(searchTextID);  \r\n");
		js.append("		if(searchText.getAttribute('searchTextOnChange')!=null)	 \r\n");
		js.append("		   eval(searchText.getAttribute('searchTextOnChange'));\r\n");
		js.append("		 \r\n");
		js.append("   }\r\n");
		js.append("\r\n");
		js.append("   function SearchText_handleKeyDown(searchTextID,event)\r\n");
		js.append("   {\r\n");
		js.append("		var searchText=SearchText_getSearchText(searchTextID);\r\n");
		js.append("		if(searchText==null) return;\r\n");
		js.append("		var button=_SearchText_getButton(searchTextID);\r\n");
		js.append("		if(button==null) return;\r\n");
		js.append("		var autoSearch=searchText.getAttribute(\"autoSearch\");   \r\n");
		js.append("		var key=searchText.getAttribute(\"hotKey\");\r\n");
		js.append("		if(key==null || autoSearch===\"false\" || autoSearch===false) return;	\r\n");
		js.append("		key=key.toLowerCase();\r\n");
		js.append("		if(key===getKeyName(event))\r\n");
		js.append("	    {\r\n");
		js.append("			eval(_SearchText_getTextBox(searchTextID).onchange());	\r\n");		
		js.append("	    }\r\n");
		js.append("   }\r\n");
		js.append("\r\n");
		js.append("   function getKeyName(event)\r\n");
		js.append("   {\r\n");
		js.append("	   if(event==null) return;\r\n");
		js.append("	   if(event.keyCode>=112 && event.keyCode<=123) //from f1 to f12\r\n");
		js.append("	      return \"f\"+(event.keyCode-111);	\r\n");
		js.append("	   if(event.keyCode==13) return \"enter\";  \r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("    function SearchText_setValue(searchTextID,value)\r\n");
		js.append("   {     \r\n");
		js.append("	 	var hidden=_SearchText_getHidden(searchTextID);		\r\n");
		js.append("		hidden.value=value;	 \r\n");
		js.append("   }\r\n");
		js.append("    function SearchText_getValue(searchTextID)\r\n");
		js.append("   { \r\n");
		js.append("         if(SearchText_getFillBackFinished(searchTextID)==false) return \"\";  \r\n");
		js.append("         var hidden=_SearchText_getHidden(searchTextID);	  \r\n");
		js.append("	        return hidden.value;   \r\n");
		js.append("   }\r\n");
		js.append("    function SearchText_setText(searchTextID,text)\r\n");
		js.append("   {     \r\n");	
		js.append("			var textBox=_SearchText_getTextBox(searchTextID);\r\n");
		js.append("			var searchText=SearchText_getSearchText(searchTextID);     \r\n");		
		js.append("			searchText.setAttribute('oldValue',text);     \r\n");
		js.append("			textBox.value=text;   \r\n");
		js.append("   }\r\n");
		js.append("    function SearchText_getText(searchTextID)\r\n");
		js.append("   {     \r\n");
		js.append("     if(SearchText_getFillBackFinished(searchTextID)==false) return \"\";  \r\n");
		js.append("     var textBox=_SearchText_getTextBox(searchTextID);\r\n");
		js.append("	 var text=textBox.value;	   \r\n");
		js.append("	 while(text.indexOf('#')>0) text=text.replace('#',''); 	   \r\n");
		js.append("	 return text; 	   \r\n");
		js.append("   }  \r\n");
		js.append("   \r\n");
		js.append("   function SearchText_getValue2(searchTextID)\r\n");
		js.append("   {  \r\n");
		js.append("         var hidden=_SearchText_getHidden(searchTextID);	  \r\n");
		js.append("	     return hidden.value;   \r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("   function SearchText_getText2(searchTextID)\r\n");
		js.append("   {   \r\n");
		js.append("     var textBox=_SearchText_getTextBox(searchTextID);\r\n");
		js.append("	 var text=textBox.value;	   \r\n");
		js.append("	 text=text.replace('#',''); 	   \r\n");
		js.append("	 return text; 	   \r\n");
		js.append("   }  \r\n");
		js.append("   \r\n");
		js.append("    function SearchText_clear(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("      var textBox=_SearchText_getTextBox(searchTextID);\r\n");
		js.append("		 var hidden=_SearchText_getHidden(searchTextID);\r\n");
		js.append("		 textBox.value=\"\";\r\n");
		js.append("		 hidden.value=\"\";\r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("    function SearchText_isValid(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("   function SearchText_setProperty(searchTextID,propertyName,value)\r\n");
		js.append("   {\r\n");
		js.append("      var searchText=SearchText_getSearchText(searchTextID)	;\r\n");
		js.append("     var layoutTable=_SearchText_getLayoutTable(searchTextID); \r\n");
		js.append("	  var textBox=_SearchText_getTextBox(searchTextID);\r\n");
		js.append("      var button=_SearchText_getButton(searchTextID);\r\n");
		js.append("	  if(\"width\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     layoutTable.style.width=value;    \r\n");
		js.append("	     textBox.style.width=value-17;\r\n");
		js.append("	  }\r\n");
		js.append("	  else if(\"readonly\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     if(value)              \r\n");
		js.append("	     {                      \r\n");
		js.append("	     	button.onclick=null;\r\n");
		js.append("	     	var sr=button.src;\r\n");
		js.append("	     	button.src=sr.substring(0,sr.lastIndexOf(\"/\"))+\"/"+SearchText.disImage+"\";\r\n");
		js.append("	     }                      \r\n");
		js.append("	     else                    \r\n");
		js.append("	     {                       \r\n");
		js.append("	     	 button.onclick=function () { SearchText_setFillBackFinished(searchTextID,false); SearchText_popupQueryList2(searchTextID,false);};\r\n");
		js.append("	     	 var sr=button.src;\r\n");
		js.append("	     	button.src=sr.substring(0,sr.lastIndexOf(\"/\"))+\"/"+SearchText.enableImage+"\";\r\n");
		js.append("	     }                      \r\n");
		js.append("		  textBox.readOnly=value;\r\n");
		js.append("	   }\r\n");
		js.append("	   else if(\"disabled\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     textBox.disabled=value;\r\n");
		js.append("		 textBox.readOnly=false;\r\n");
		js.append("	  }\r\n");
		js.append("	  else if(\"pageurl\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     searchText.setAttribute('pageUrl',value);\r\n");
		js.append("	  }\r\n");
		js.append("	   else if(\"windowwidth\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     searchText.setAttribute('windowWidth',value);\r\n");
		js.append("	  }\r\n");
		js.append("	    else if(\"windowheight\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     searchText.setAttribute('windowHeight',value);\r\n");
		js.append("	   } 	  \r\n");
		js.append("	  else 	searchText.setAttribute(propertyName,value);  \r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("   \r\n");
		js.append("   function SearchText_getProperty(searchTextID,propertyName)\r\n");
		js.append("   {\r\n");
		js.append("   	  var searchText=SearchText_getSearchText(searchTextID)	;\r\n");
		js.append("       var layoutTable=_SearchText_getLayoutTable(searchTextID); \r\n");
		js.append("	      var textBox=_SearchText_getTextBox(searchTextID);\r\n");
		js.append("       var button=_SearchText_getButton(searchTextID);\r\n");
		js.append("	\r\n");
		js.append("	  if(\"width\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     if(layoutTable.style.width!=null && layoutTable.style.width!=\"\") \r\n");
		js.append("		    return layoutTable.style.width;\r\n");
		js.append("		 else \r\n");
		js.append("		    return layoutTable.width;	\r\n");
		js.append("	  }\r\n");
		js.append("	  else if(\"readonly\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     return textBox.readOnly;\r\n");
		js.append("	  }\r\n");
		js.append("	   else if(\"disabled\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     return textBox.disabled;		\r\n");
		js.append("	  }\r\n");
		js.append("	  else if(\"pageurl\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     return searchText.getAttribute('pageUrl');\r\n");
		js.append("	  }\r\n");
		js.append("	   else if(\"windowwidth\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     return searchText.getAttribute('windowWidth');\r\n");
		js.append("	  }\r\n");
		js.append("	  else if(\"windowheight\"==propertyName.toLowerCase())\r\n");
		js.append("	  {\r\n");
		js.append("	     return searchText.getAttribute('windowHeight');\r\n");
		js.append("	  } 	  \r\n");
		js.append("	  else return 	searchText.getAttribute(propertyName);  \r\n");
		js.append(" }\r\n");
		js.append("   \r\n");
		js.append("   //get entityName to search\r\n");
		js.append("  function SearchText_getSearchEntity(searchTextID)\r\n");
		js.append("  { \r\n");
		js.append("     var searchBox=SearchText_getSearchText(searchTextID);     \r\n");
		js.append("     return searchBox.getAttribute('searchEntity');           \r\n");
		js.append("   }\r\n");
		js.append("  function SearchText_setSearchEntity(searchTextID,entityName)\r\n");
		js.append("  { \r\n");
		js.append("     var  searchBox=SearchText_getSearchText(searchTextID);     \r\n");
		js.append("     searchBox.setAttribute('searchEntity',entityName);           \r\n");
		js.append("   }\r\n");
		js.append("   //check whether SearchData has finished filling back to its source SearhcText\r\n");
		js.append("  function SearchText_getFillBackFinished(searchTextID)\r\n");
		js.append("  { \r\n");
		js.append("     var searchBox=SearchText_getSearchText(searchTextID);     \r\n");
		js.append("     var fillBackFinished=searchBox.getAttribute('fillBackFinished');\r\n");
		js.append("     var result=searchBox.getAttribute('fillBackFinished');\r\n");
		js.append("     if(fillBackFinished=='false'|| fillBackFinished==false) result=false;     \r\n");
		js.append("     else  result=true;     \r\n");
		js.append("    // alert('get :' +result);\r\n");
		js.append("     return result;\r\n");
		js.append("   }\r\n");
		js.append("   //set whether SearchData has finished filling back to its source SearhcText\r\n");
		js.append("  function SearchText_setFillBackFinished(searchTextID,hasFinished)\r\n");
		js.append("  { \r\n");
		js.append("     var searchBox=SearchText_getSearchText(searchTextID);     \r\n");
		js.append("     searchBox.setAttribute('fillBackFinished',hasFinished);             \r\n");
		js.append("     //alert('set :' +hasFinished);\r\n");
		js.append("   }\r\n");
		js.append("   function SearchText_prepareUserData(componentId,eventName,searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("		var searchText=SearchText_getSearchText(searchTextID);     \r\n");		
		js.append("		SearchText_setValue(searchTextID,'');   \r\n");
		js.append("		var text=SearchText_getText2(searchTextID);   \r\n");
		js.append("		var value=SearchText_getValue2(searchTextID);\r\n");
		js.append("		if(text==\"\")\r\n");
		js.append("		{\r\n");
		js.append("			return false;\r\n");
		js.append("		}\r\n");		
		js.append("		var oldValue=searchText.getAttribute('oldValue');    \r\n");
		js.append("		if(oldValue==text)     \r\n");
		js.append("			return false;     \r\n");	
		js.append("		else     \r\n");	
		js.append("			searchText.setAttribute('oldValue',text);     \r\n");	
		js.append("		SearchText_setFillBackFinished(searchTextID,false);   \r\n");
		js.append("		SearchText_triggerOnChange(searchTextID);  \r\n");
		js.append("		var windowHeight=searchText.getAttribute('windowHeight');\r\n");
		js.append("		var windowWidth=searchText.getAttribute('windowWidth');	    	 \r\n");
		js.append("		var searchEntity=SearchText_getSearchEntity(searchTextID);   \r\n");
		js.append("		var listTitle=searchText.getAttribute('listTitle');\r\n");
		js.append("		var keyField=searchText.getAttribute('keyField');\r\n");
		js.append("		var codeField=searchText.getAttribute('codeField');\r\n");
		js.append("		var nameField=searchText.getAttribute('nameField');\r\n");
		js.append("		var dataFields=searchText.getAttribute('dataFields');	 \r\n");
		js.append("		var captions=_SearchText_getCaptions(searchTextID);\r\n");
		js.append("		var textFormula=searchText.getAttribute('textFormula');\r\n");
		js.append("		if(captions!=null)  while(captions.indexOf(\"`\")>0) captions=captions.replace('`',',');\r\n");
		js.append("\r\n");
		js.append("		var objData=getUserDataHidden(componentId,eventName);\r\n");
		js.append("		HashtableManager_Add(\"searchTextID\",searchTextID,objData);\r\n");
		js.append("		HashtableManager_Add(\"text\",text,objData);    \r\n");
		js.append("		HashtableManager_Add(\"value\",value,objData);     		\r\n");
		js.append("		if(searchEntity!=null) HashtableManager_Add(\"searchEntity\",searchEntity,objData);     \r\n");
		js.append("		if(listTitle!=null)HashtableManager_Add(\"listTitle\",listTitle,objData);     \r\n");
		js.append("		if(keyField!=null) HashtableManager_Add(\"keyField\",keyField,objData);     \r\n");
		js.append("		if(codeField!=null) HashtableManager_Add(\"codeField\",codeField,objData);     \r\n");
		js.append("		if(nameField!=null) HashtableManager_Add(\"nameField\",nameField,objData); \r\n");
		js.append("		if(dataFields!=null) HashtableManager_Add(\"dataFields\",dataFields,objData); \r\n");
		js.append("		if(captions!=null) HashtableManager_Add(\"captions\",captions,objData); \r\n");
		js.append("		if(textFormula!=null) HashtableManager_Add(\"textFormula\",textFormula,objData); \r\n");
		js.append("		HashtableManager_Add(\"actionType\",\"iframe\",objData);     \r\n");
		js.append("		HashtableManager_Add(\"windowHeight\",windowHeight,objData);\r\n");
		js.append("		HashtableManager_Add(\"windowWidth\",windowWidth,objData);\r\n");
		js.append("   }\r\n");
		js.append("   function SearchText_getOpennerName()\r\n");
		js.append("   {\r\n");
		js.append("		var i=0;\r\n");
		js.append("		var myopener=window.opener;;\r\n");
		js.append("		while(myopener!=null)\r\n");
		js.append("     {\r\n");
		js.append("		    myopener=myopener.opener;\r\n");
		js.append("         i++;\r\n");
		js.append("     }\r\n");
		js.append("     return \"window\" + i;\r\n");
		js.append("   }\r\n");
		js.append("   function SearchText_setFocus(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("	     var searchBox=_SearchText_getTextBox(searchTextID);	   \r\n");
		js.append("      searchBox.focus() ;\r\n");
		js.append("   }\r\n");
		js.append("   function SearchText_popupQueryList(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("	     var searchBox=SearchText_getSearchText(searchTextID);	   \r\n");
		js.append("      var queryString=_SearchText_buildQueryString(searchTextID);\r\n");
		js.append("      var feature=_SearchText_buildFeature(searchTextID) ;\r\n");
		js.append("      var openerName=SearchText_getOpennerName(searchTextID);\r\n");
		js.append("      queryList=window.open(searchBox.getAttribute('pageUrl')+queryString,openerName,feature);\r\n");
		js.append("  }\r\n");
		js.append("   function SearchText_popupQueryList2(searchTextID,hasPopup)\r\n");
		js.append("   {\r\n");
		js.append("	      var  searchBox=SearchText_getSearchText(searchTextID);	\r\n");
		js.append("       var openerName=SearchText_getOpennerName(searchTextID);\r\n");
		js.append("	      searchBox.setAttribute('hasPopup',true);	  \r\n");
		js.append("       var queryString=_SearchText_buildQueryString(searchTextID);\r\n");
		js.append("       var feature=_SearchText_buildFeature(searchTextID) ;\r\n");
		js.append("       queryList=window.open(searchBox.getAttribute('pageUrl')+queryString,openerName,feature);\r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("     //build queryString of pageUrl\r\n");
		js.append("   function _SearchText_buildQueryString(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("        var queryString=\"\";\r\n");
		js.append("        var searchText=SearchText_getSearchText(searchTextID);\r\n");
		js.append("		var text=SearchText_getText2(searchTextID);   \r\n");
		js.append("		var value=SearchText_getValue2(searchTextID);   \r\n");
		js.append("		var searchEntity=SearchText_getSearchEntity(searchTextID);   \r\n");
		js.append("		var listTitle=searchText.getAttribute('listTitle');\r\n");
		js.append("		var keyField=searchText.getAttribute('keyField');\r\n");
		js.append("		var codeField=searchText.getAttribute('codeField');\r\n");
		js.append("		var nameField=searchText.getAttribute('nameField');\r\n");
		js.append("		var dataFields=searchText.getAttribute('dataFields');\r\n");
		js.append("		var textFormula=searchText.getAttribute('textFormula');\r\n");
		js.append("		var windowHeight=searchText.getAttribute('windowHeight');\r\n");
		js.append("		var windowWidth=searchText.getAttribute('windowWidth');	   \r\n");
		js.append("		var captions=_SearchText_getCaptions(searchTextID);\r\n");
		js.append("		if(captions!=null)  while(captions.indexOf(\"`\")>0) captions=captions.replace('`',',');\r\n");
		js.append("        var queryString='';                            \r\n");
		js.append("		if(searchText.getAttribute('pageUrl').indexOf('?')<0)     \r\n");
		js.append("            queryString='?';                \r\n");
		js.append("        else \r\n");
		js.append("			queryString='&';       \r\n");
		js.append("		\r\n");
		js.append("        queryString+='searchTextID='+searchTextID;\r\n");
		js.append("        queryString+=\"&text=\"+text;\r\n");
		js.append("        queryString+=\"&value=\"+value;\r\n");
		js.append("        queryString+=\"&actionType=popup\";\r\n");
		js.append("        queryString+=searchEntity!=null?(\"&searchEntity=\"+searchEntity):'';  \r\n");
		js.append("        queryString+=listTitle!=null?(\"&listTitle=\"+listTitle):'';  \r\n");
		js.append("        queryString+=keyField!=null?(\"&keyField=\"+keyField):'';  \r\n");
		js.append("        queryString+=codeField!=null?(\"&codeField=\"+codeField):'';  \r\n");
		js.append("        queryString+=nameField!=null?(\"&nameField=\"+nameField):'';  \r\n");
		js.append("		queryString+=dataFields!=null?(\"&dataFields=\"+dataFields):'';\r\n");
		js.append("		queryString+=captions!=null?(\"&captions=\"+captions):'';\r\n");
		js.append("		queryString+=textFormula!=null?(\"&textFormula=\"+textFormula):'';\r\n");
		js.append("		queryString+=\"&windowHeight=\"+windowHeight;                 \r\n");
		js.append("		queryString+=\"&windowWidth=\"+windowWidth;\r\n");
		js.append("		return queryString;\r\n");
		js.append("   }\r\n");
		js.append("   \r\n");
		js.append("    function _SearchText_buildFeature(searchTextID) \r\n");
		js.append("	{ \r\n");
		js.append("	    var searchBox=SearchText_getSearchText(searchTextID);    \r\n");
		js.append("		var feature=\"width=\"+searchBox.getAttribute(\"windowWidth\");        \r\n");
		js.append("		feature+=\",height=\"+searchBox.getAttribute(\"windowHeight\") ;           \r\n");
		js.append("		feature+=\",left=200,top=100\" ;           \r\n");
		js.append("		return feature; \r\n");
		js.append("	} \r\n");
		js.append("		\r\n");
		js.append("		\r\n");
		js.append("   function SearchText_getSearchText(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("       return   document.getElementById(searchTextID);    \r\n");
		js.append("   }\r\n");
		js.append("   function _SearchText_getTextBox(searchTextID) \r\n");
		js.append("   {\r\n");
		js.append("       return   document.getElementById(searchTextID+'_textbox');    \r\n");
		js.append("   }\r\n");
		js.append("    function _SearchText_getHidden(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("      return   document.getElementById(searchTextID+\"_hidden\");     \r\n");
		js.append("   }\r\n");
		js.append("    function _SearchText_getButton(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("       return   document.getElementById(searchTextID+'_button');     \r\n");
		js.append("   }\r\n");
		js.append("    function  _SearchText_getLayoutTable(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("      return   document.getElementById(searchTextID);   \r\n");
		js.append("   }\r\n");
		js.append("\r\n");
		js.append("   function  _SearchText_getCaptions(searchTextID)\r\n");
		js.append("   {\r\n");
		js.append("      var captionsHidden=document.getElementById(searchTextID+\"_captions\");\r\n");
		js.append("	  if(captionsHidden!=null)\r\n");
		js.append("		return   captionsHidden.value;   \r\n");
		js.append("	  return null;\r\n");
		js.append("   }\r\n");
		js.append("   function SearchText_onkeyDown(controlId)\r\n");
		js.append("   {\r\n");
		js.append("      if(event.keyCode==8||event.keyCode==46)\r\n");
		js.append("      {\r\n");
		js.append("         var obj=event.srcElement;\r\n");
		js.append("      	SearchText_setValue(controlId,'');\r\n");
		js.append("      	SearchText_setText(controlId,'');\r\n");
		js.append("      	SearchText_triggerOnChange(controlId);\r\n");
		js.append("      	event.keyCode=0;\r\n");
		js.append("      	return false; \r\n");
		js.append("      } \r\n");
		js.append("    }\r\n");
		js.append(" \r\n");
		js.append("");
		//generate js file	 
        jstool.js=js;        
        return jstool.getComponentJS();
	}


}

