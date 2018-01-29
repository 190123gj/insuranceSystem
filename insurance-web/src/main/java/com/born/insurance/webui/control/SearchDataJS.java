/**
 * Created on 2003-10-23
 * 
 */
package com.born.insurance.webui.control;

/**
 * @author He Kun
 *
 */
public class SearchDataJS
{
	
	/**
	   * <br><B>SearchData�ͻ��˷���(javascript)</B><br><br>
	   * ע��SearchData�ͻ��˿ؼ�����Ҫ����ı�̿���
	   * 
	   * <br><B>��ȡ���������SearchText�ؼ���ID</B><br>
	   * function:SearchData_getSourceControlID(ControlID)<br>
	   * param ControlID �����Id<br>
	   * retrun ���������SearchText�ؼ���ID<br>
	 * @return
	 */
	public static String getSearchDataJS()
	{

		JSTool jstool=new JSTool("SearchDataJS.js");
		StringBuffer js = jstool.js;
		if(!jstool.util.isUpdateJsEveryTime())
		 {
			 return jstool.getComponentJS();
		 }
		js.append("/**\r\n");
	js.append("* SearchData Client JavaScript\r\n");
	js.append("* @author hekun bornsoft\r\n");
	js.append("* @date 2003-10-22\r\n");
	js.append("* @since 1.0\r\n");
	js.append("*/\r\n");
	js.append("    function SearchData_init(ControlID)\r\n");
	js.append("	{\r\n");
	js.append("		\r\n");
	js.append("	}\r\n");
	js.append("	 function SearchData_getListTitle(ControlID)\r\n");
	js.append("	{\r\n");
	js.append("		searchData=_SearchData_getSearchData(ControlID);\r\n");
	js.append("		return searchData.getAttribute('listTitle');    \r\n");
	js.append("	}\r\n");
	js.append("	\r\n");
	js.append("	//return SearchTextID to fill back\r\n");
	js.append("	 function SearchData_getSourceControlID(ControlID)\r\n");
	js.append("	{\r\n");
	js.append("		searchData=_SearchData_getSearchData(ControlID);\r\n");
	js.append("		return searchData.getAttribute('sourceControlID');\r\n");
	js.append("	}\r\n");
	js.append("	//fill SearchText using value and text that use select id SearchData\r\n");
	js.append("	function SearchData_fillBackToOpener(ControlID,value,text)\r\n");
	js.append("	{\r\n");
	js.append("	    if(window.opener!=null)\r\n");
	js.append("		{ \r\n");
	js.append("			 //alert('start : '+value+','+text); \r\n");
	js.append("				if(value==null) value='';\r\n");
	js.append("				if(text==null) text='';\r\n");
	js.append("				sourceID=SearchData_getSourceControlID(ControlID);\r\n");
	js.append("				searchText=window.opener.document.getElementById(sourceID);\r\n");
	js.append("				if(searchText==null) return;\r\n");
	js.append("			if(window.opener.SearchText_getFillBackFinished(sourceID)==true) return;  \r\n");
	js.append("				window.opener.SearchText_setValue(sourceID,value);\r\n");
	js.append("				window.opener.SearchText_setText(sourceID,text);\r\n");
	js.append("				window.opener.SearchText_setFillBackFinished(sourceID,true);   \r\n");
	js.append("				window.opener.SearchText_triggerOnChange(sourceID);\r\n");
	js.append("				searchText.setAttribute('hasPopup',false);           \r\n");
	js.append("		}		\r\n");
	js.append("       window.close(); 		\r\n");
	js.append("	}\r\n");
	js.append("	//fill SearchText using value and text that use select id SearchData\r\n");
	js.append("	function SearchData_fillBack(ControlID,value,text)\r\n");
	js.append("	{\r\n");
	js.append("	  try{");
	js.append("	    if(value==null) value='';\r\n");
	js.append("		if(text==null) text=''; \r\n");
	js.append(" 	var actionType=_SearchData_getSearchData(ControlID).getAttribute('actionType');\r\n");
	js.append(" 	//alert('action type: '+actionType); \r\n");
	js.append(" 	if(actionType=='iframe') \r\n");
	js.append(" 	{ \r\n");
	js.append(" 	    if(window.parent==null)\r\n");
	js.append(" 		return;\r\n");
	js.append("	    var searchTextID=SearchData_getSourceControlID(ControlID);	\r\n");
	js.append("	    var searchText=window.parent.SearchText_getSearchText(searchTextID);	\r\n");
	js.append("	    searchText.setAttribute('hasPopup',false);           \r\n");
	js.append("	    sourceID=SearchData_getSourceControlID(ControlID); \r\n");
	js.append("	     //alert('by iframe setValue:  '+window.parent.SearchText_setValue);\r\n");
	js.append("		window.parent.SearchText_setValue(sourceID,value);\r\n");
	js.append("		window.parent.SearchText_setText(sourceID,text);\r\n");
	js.append("		window.parent.SearchText_setFillBackFinished(sourceID,true);      \r\n");
	js.append("		window.parent.SearchText_triggerOnChange(sourceID);\r\n");
	js.append("	    }\r\n");
	js.append(" 	else if(actionType=='iframePopup')\r\n");
	js.append(" 	{ \r\n");
	js.append("	    if(window.opener==null||window.opener.parent==null)\r\n");
	js.append("	    {\r\n");
	js.append("	       return;\r\n");
	js.append("	    }\r\n");
	js.append("	    sourceID=SearchData_getSourceControlID(ControlID); \r\n");
	js.append("	    var searchTextID=SearchData_getSourceControlID(ControlID);	\r\n");
	js.append("	    var searchText=window.opener.parent.SearchText_getSearchText(searchTextID);	\r\n");
	js.append("	    searchText.setAttribute('hasPopup',false);           \r\n");
	js.append("		window.opener.parent.SearchText_setValue(sourceID,value);\r\n");
	js.append("		window.opener.parent.SearchText_setText(sourceID,text);\r\n");
	js.append("		window.opener.parent.SearchText_setFillBackFinished(sourceID,true);    \r\n");
	js.append("		window.opener.parent.SearchText_triggerOnChange(sourceID);\r\n");
	js.append(" 	} \r\n");
	js.append("	    else  if(actionType=='popup')\r\n");
	js.append(" 	{ \r\n");
	js.append("		  SearchData_fillBackToOpener(ControlID,value,text);\r\n");
	js.append("	     }\r\n");
	js.append("	     }catch(Ex){\r\n");
	js.append("	     }\r\n");
	js.append("	    window.close();\r\n");
	js.append("	}\r\n");
	js.append("	function SearchData_popupMeFromIframe(ControlID)\r\n");
	js.append("	{	\r\n");
	js.append("	    if(window.parent==null)\r\n");
	js.append("	        return;\r\n");
	js.append("	    var searchTextID=SearchData_getSourceControlID(ControlID);	\r\n");
	js.append("	    var searchText=window.parent.SearchText_getSearchText(searchTextID);	\r\n");
	js.append("	    var hasPopup=searchText.getAttribute('hasPopup');\r\n");
	js.append("	    if(hasPopup=='true' || hasPopup==true) return;      \r\n");
	js.append("	    else window.parent.SearchText_popupQueryList(searchTextID);	\r\n");
	js.append("	}\r\n");
	js.append("	\r\n");
	js.append("	function _SearchData_getSearchData(ControlID)\r\n");
	js.append("	{		\r\n");
	js.append("		return document.getElementById(ControlID);\r\n");
	js.append("	}\r\n");
	js.append("	\r\n");
	js.append("	function SearchData_cancel(ControlID)\r\n");
	js.append("	{		\r\n");
	js.append("	  try{	\r\n");
	js.append("	    if(window.opener==null)\r\n");
	js.append("	    {\r\n");
	js.append("	    	return;	\r\n");
	js.append("	    }\r\n");
	js.append("	    var searchTextID=SearchData_getSourceControlID(ControlID);	\r\n");
	js.append("	    searchText=window.opener.document.getElementById(searchTextID);\r\n");
	js.append("     var tempText=searchText.getAttribute('tempText');   \r\n");
	js.append("     var tempValue=searchText.getAttribute('tempValue');   \r\n");
	js.append("		//window.opener.SearchText_setValue(searchTextID,tempValue);      \r\n");
	js.append("		//window.opener.SearchText_setText(searchTextID,tempText);       \r\n");
	js.append("	    if(window.opener!=null) window.opener.SearchText_setFillBackFinished(searchTextID,true);		\r\n");
	js.append("	    searchText.setAttribute('hasPopup',false);           \r\n");
	js.append("      	\r\n");
	js.append("   }catch(Ex){\r\n");
	js.append("	  }\r\n");
	js.append("	}\r\n");
	js.append("");
		jstool.js=js;
		return jstool.getComponentJS() ;
	}


}
