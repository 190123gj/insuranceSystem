package com.born.insurance.webui.control;
/**
 *
 * <p>Title:GAMenuGroup </p>
 * <p>Description:����Ϊ�˵�������࣬������ɲ˵���html</p>
 * <p>Copyright: Jsp 2003</p>
 * <p>Company: Jsp</p>
 * @author zhangr
 * @version 1.0
 */
public class GAMenuGroup {
  private MenuItemCollection menuItems = new MenuItemCollection();

  private int groupIndex;

  private GAMenuItem parentItem;

  private String menuId = "";

  private boolean isIE = true;
  /**
   * ���캯��
   * @param index �˵�������
   */
  public GAMenuGroup(int index) {
    groupIndex = index;
  }
  /**
   * ���ò˵�ID
   * @param mId �˵�ID
   */
  public void setMenuId(String mId){
    this.menuId = mId;
  }
  /**
   * ��òŲ˵���ID
   * @return �˵�ID
   */
  public String getMenuId(){
    return this.menuId;
  }
  /**
   * ����������Ƿ�ΪIE
   * @param ie boolean����
   */
  public void setIsIE(boolean ie){
    this.isIE = ie;
  }
  /**
   * �Ƿ�ΪIE�����
   * @return boolean
   */
  public boolean getIsIE(){
    return this.isIE;
  }
  /**
   * ��ò˵�������
   * @return �˵�������
   */
  public int getGroupIndex(){
    return this.groupIndex;
  }
  /**
   * ���ø��˵�
   * @param item �˵������
   */
  public void setParent(GAMenuItem item){
    this.parentItem = item;
  }
  /**
   * ��ø��˵�
   * @return �˵������
   */
  public GAMenuItem getParentItem(){
  	return this.parentItem;
  }
  /**
   * ���Ӳ˵���
   * @param menuItem �˵������
   */
  public void addMenuItem(GAMenuItem menuItem){
    this.menuItems.addMenuItem(menuItem);
  }
  /**
   * ��ò˵��б��в˵�����Ŀ
   * @return �б��в˵�����Ŀ
   */
  public int size(){
    return this.menuItems.size();
  }
  /**
   * ��ò˵���
   * @param index int
   * @return �˵������
   */
  public GAMenuItem get(int index){
    return this.menuItems.getMenuItem(index);
  }
  /**
   * ��ò˵����html����
   * @return Html����
   */
  public String getGroupHtml(){
    StringBuffer sb = new StringBuffer();
    if(groupIndex == 0){
    	sb.append(getTopGroupHtml());
    }
    else{
    	sb.append(getOtherGroupHtml());
    }
    for(int i = 0; i < this.menuItems.size(); i++){
      GAMenuItem menuItem = this.menuItems.getMenuItem(i);
      if(menuItem.isLeafItem()){
        continue;
      }
      else{
        sb.append(menuItem.getChildGroup().getGroupHtml());
      }
    }
    return sb.toString();
  }

  private String getTopGroupHtml(){
    StringBuffer sb = new StringBuffer();
    if(this.isIE){
    	sb.append(getIETopGroup());
    }
    else{
    	sb.append(getMozilaTopGroup());
    }
     return sb.toString();
  }

  private String getIETopGroup(){
    StringBuffer sb = new StringBuffer();
    sb.append("<span><table  class=\"vpsnmTopG\"  id=\"" + this.menuId + "_group_" + String.valueOf(groupIndex) + "\"");
    sb.append(" style=\"Z-INDEX: 999\" ");
    sb.append("onmouseout=\"if (document.readyState == 'complete') vpsnm_groupMsOut('" + this.menuId + "_group_0', null, null, 0);\"");
    sb.append(" cellSpacing=\"0\" cellPadding=\"0\" border=\"0\"><tr>");
    for(int i = 0; i < this.menuItems.size(); i++){
    	GAMenuItem menuItem = this.menuItems.getMenuItem(i);
	sb.append(menuItem.getItemHtml());
    }
     sb.append("</tr></table></span>");
     return sb.toString();
  }

  private String getMozilaTopGroup(){
    StringBuffer sb = new StringBuffer();
    sb.append("<span><table  class=\"vpsnmTopG\"  id=\"" + this.menuId + "_group_" + String.valueOf(groupIndex) + "\"");
    sb.append(" style=\"Z-INDEX: 999\" ");
    sb.append("onmouseout=\"vpsnm_groupMsOut('" + this.menuId + "_group_0', null, null, 0, event);\"");
    sb.append(" cellSpacing=\"0\" cellPadding=\"0\" border=\"0\"><tr>");
    for(int i = 0; i < this.menuItems.size(); i++){
    	GAMenuItem menuItem = this.menuItems.getMenuItem(i);
	sb.append(menuItem.getItemHtml());
    }
     sb.append("</tr></table></span>");
     return sb.toString();
  }

  private String getMozilaOGroupHtml(){
    StringBuffer sb = new StringBuffer();
    sb.append("<div  class=\"vpsnmG\"  id=\"" + this.menuId + "_group_" + String.valueOf(groupIndex) + "\"");
    sb.append("  style=\"position: absolute; z-index: 999; left: 80px; top: 62px; visibility: hidden;\"");
    sb.append(" onmouseover=\"vpsnm_groupMsOver('" + this.menuId + "_group_" +  String.valueOf(groupIndex) + "')\" ");
    sb.append("onmouseout=\"vpsnm_groupMsOut('" + this.menuId + "_group_" + String.valueOf(this.groupIndex) + "', '" + this.menuId + "_" + this.parentItem.getMenuItemId() + "', '" + this.menuId + "_group_" + String.valueOf(this.parentItem.getCurGroup().getGroupIndex()) + "', 0, event);;\"");
    sb.append("><table cellSpacing=\"0\" cellPadding=\"0\" border=\"0\">");
    for(int i = 0; i < this.menuItems.size(); i++){
      GAMenuItem menuItem = this.menuItems.getMenuItem(i);
      sb.append("<tr>" + menuItem.getItemHtml() + "</tr>");
    }
    sb.append("</table></div>");
    return sb.toString();
  }

  private String getIEOGroupHtml(){
    StringBuffer sb = new StringBuffer();
    sb.append("<table  class=\"vpsnmG\"  id=\"" + this.menuId + "_group_" + String.valueOf(groupIndex) + "\"");
    sb.append(" style=\"Z-INDEX: 999;  LEFT: 0px; VISIBILITY: hidden; POSITION: absolute; TOP: 0px;\" ");
    sb.append(" onmouseover=\"vpsnm_groupMsOver('" + this.menuId + "_group_" +  String.valueOf(groupIndex) + "')\" ");
    sb.append("onmouseout=\"if (document.readyState == 'complete') vpsnm_groupMsOut('" + this.menuId + "_group_" + String.valueOf(this.groupIndex) + "', '" + this.menuId + "_" + this.parentItem.getMenuItemId() + "', '" + this.menuId + "_group_" + String.valueOf(this.parentItem.getCurGroup().getGroupIndex()) + "', 0, null);;\"");
    sb.append(" cellSpacing=\"0\" cellPadding=\"0\" border=\"0\">");
    for(int i = 0; i < this.menuItems.size(); i++){
      GAMenuItem menuItem = this.menuItems.getMenuItem(i);
      sb.append("<tr>" + menuItem.getItemHtml() + "</tr>");
    }
    sb.append("</table>");
    return sb.toString();
  }

  private String getOtherGroupHtml(){
    StringBuffer sb = new StringBuffer();
    if(this.isIE){
      sb.append(getIEOGroupHtml());
    }
    else{
      sb.append(getMozilaOGroupHtml());
    }
    return sb.toString();
}
}