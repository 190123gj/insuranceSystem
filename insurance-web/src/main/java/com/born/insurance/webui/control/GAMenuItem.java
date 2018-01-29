package com.born.insurance.webui.control;
/**
 *
 * <p>Title: GAMenuItem</p>
 * <p>Description:����Ϊ�˵�������࣬������ɲ˵���html </p>
 * <p>Copyright: Jsp 2003</p>
 * <p>Company:Jsp </p>
 * @author zhangr
 * @version 1.0
 */
public class GAMenuItem {
  private GAMenuGroup childGroup;
  private GAMenuGroup curGroup;
  private GAMenuGroup parentGroup;
  private String url;
  private String description;
  private String id;
  private String name;
  private String menuId = "";
  private boolean isLeaf = false;
  private boolean isTop = false;
  private boolean isIE = true;
  private String popImageSrc = ComponentUtil.getInstance().getImagesRequestPath() + "//Popup.gif";
  private String selectedImageSrc = ComponentUtil.getInstance().getImagesRequestPath() + "//SelectedPopup.gif";
  /**
   * ���캯��
   * @param id GAMenuItem����ID
   * @param name �˵���ʾ���
   * @param href �˵�������ַ
   * @param caption �˵�����
   */
  public GAMenuItem(String id, String name, String href, String caption){
    this.id = id;
    this.name = name;
    this.url = href;
    this.description = caption;
  }
  /**
   * �����Ƿ�IE�����
   * @param ie boolean��
   */
  public void setIsIE(boolean ie){
    this.isIE = ie;
  }
  /**
   * ������Ƿ�ΪIE
   * @return boolean
   */
  public boolean getIsIE(){
    return this.isIE;
  }
  /**
   * ���õ����˵�ͼƬ·��
   * @param src �˵�ͼƬ·��
   */
  public void setPopImageSrc(String src){
    this.popImageSrc = src;
  }
  /**
   * ����ѡ�в˵�ͼƬ·��
   * @param src �˵�ͼƬ·��
   */
  public void setSelectedImageSrc(String src){
    this.selectedImageSrc = src;
  }
  /**
   * ��õ����˵���ͼƬ·��
   * @return �˵���ͼƬ·��
   */
  public String getPopImageSrc(){
    return this.popImageSrc;
  }
  /**
   * ���ѡ�в˵���ͼƬ·��
   * @return �˵���ͼƬ·��
   */
  public String  getSelectedImageSrc(){
    return this.selectedImageSrc;
  }
  /**
   * �����Ӳ˵���
   * @param menuGroup �˵������
   */
  public void setChildGroup(GAMenuGroup menuGroup){
    this.childGroup = menuGroup;
  }
  /**
   * ���õ�ǰ�˵���
   * @param menuGroup �˵������
   */
  public void setCurGroup(GAMenuGroup menuGroup){
    this.curGroup = menuGroup;
  }
  /**
   * ���ø��˵���
   * @param menuGroup �˵������
   */
  public void setParentGroup(GAMenuGroup menuGroup){
    this.parentGroup = menuGroup;
  }

  /**
   * ��õ�ǰ�˵���
   * @return �˵������
   */
  public GAMenuGroup getCurGroup(){
    return this.curGroup;
  }
  /**
   * ���ø��˵���
   * @return �˵������
   */
  public GAMenuGroup setParentGroup(){
    return this.parentGroup;
  }
  /**
   * ��ö����˵����html
   * @return Html����
   */
  public String getTopItemHtml(){
    StringBuffer sb = new StringBuffer();
    if(isIE){
      sb.append(getIETopItemHtml());
    }
    else{
      sb.append(getMozilaTopItemHtml());
    }
    return sb.toString();
  }
  /**
   * �����IE������¶����˵����html
   * @return Html����
   */
   public String getIETopItemHtml(){
    StringBuffer sb = new StringBuffer();
    sb.append("<TD class=\"vpsnmTopI\" onmousemove=\"return false;\" ");
    sb.append(" id=\"" + this.menuId + "_" +this.id+"\" ondblclick=\"return false;\"");
    sb.append(" onmouseover=\"this.className='vpsnmTopIO';if (document.readyState == 'complete')");
    sb.append(" vpsnm_itemMsOver('" + this.menuId +"_" + this.id + "', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 'belowleft', 0, 0, 0, null);document.body.focus();\"");
    sb.append(" onclick=\""+this.url+"\" ");
    sb.append("onmouseout=\"this.className='vpsnmTopI';");
    sb.append("if (document.readyState == 'complete') ");
    sb.append("vpsnm_itemMsOut('" + this.menuId + "_" + this.id + "', '" + this.menuId +"_group_0', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 0, null);\"");
    sb.append(" align=\"middle\">"+this.name+"</td>");
    return sb.toString();
  }
  /**
   * ���Mozila������¶����˵����html
   * @return Html����
   */
   public String getMozilaTopItemHtml(){
    StringBuffer sb = new StringBuffer();
    sb.append("<TD class=\"vpsnmTopI\" onmousemove=\"return false;\" ");
    sb.append(" id=\"" + this.menuId + "_" +this.id+"\" ondblclick=\"return false;\"");
    sb.append(" onmouseover=\"this.className='vpsnmTopIO';");
    sb.append(" vpsnm_itemMsOver('" + this.menuId +"_" + this.id + "', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 'belowleft', 0, 0, 0);\"");
    sb.append(" onclick=\""+this.url+"\" ");
    sb.append("onmouseout=\"this.className='vpsnmTopI';");
    sb.append("vpsnm_itemMsOut('" + this.menuId + "_" + this.id + "', '" + this.menuId +"_group_0', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 0, event);\"");
    sb.append(" align=\"middle\">"+this.name+"</td>");
    return sb.toString();
  }
  /**
   * ��ò˵����html
   * @return Html����
   */
  public String getItemHtml(){
    StringBuffer sb = new StringBuffer();
    if(isIE){
      sb.append(getIEItemHtml());
    }
    else{
      sb.append(getMozilaItemHtml());
    }
    return sb.toString();
  }
  /**
   * �����IE������²˵����html����
   * @return Html����
   */
  public String getIEItemHtml(){
    StringBuffer sb = new StringBuffer();
    if(this.isTop){
    	sb.append(getTopItemHtml());
    	return sb.toString();
    }
    if(this.childGroup == null){
      sb.append("<td class=\"vpsnmI\"  id=\"" + this.menuId +"_" + this.id + "\" onmousemove=\"return false;\" ondblclick=\"return false;\"");
      sb.append("  onmouseover=\"this.className='vpsnmIO';\"  onmouseout=\"this.className='vpsnmI';\"");
      sb.append("onclick=\"document.location.href='"  + this.url + "';\">"+ this.name + "</td>");
    }
    else{
      sb.append("<td><TABLE id=\"" + this.menuId +"_"+this.id+"\" onmouseup=\"\" class=\"vpsnmI\" onmousemove=\"return false;\"");
      sb.append(" onmousedown=\"\" ondblclick=\"return false;\"");
      sb.append("  onmouseover=\"if (document.readyState == 'complete')");
      sb.append("  vpsnm_updateCell('" + this.menuId +"_" + this.id + "', 'vpsnmIO', null, '', '" + this.menuId +"_" + this.id + "_coin', '" + this.selectedImageSrc + "', 'over');");
      sb.append(" if (document.readyState == 'complete')");
      sb.append(" vpsnm_itemMsOver('" + this.menuId +"_" + this.id + "', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 'rightdown', -1, 1, 0, null);\"");
      sb.append(" onclick=\"document.location.href='" + this.url + "';\"");
      sb.append("onmouseout=\"if (document.readyState == 'complete')");
      sb.append(" vpsnm_updateCell('" + this.menuId +"_" + this.id + "', 'vpsnmI', null, '', '" + this.menuId +"_" + this.id + "_coin', '" + this.popImageSrc + "', 'out');");
      sb.append("if (document.readyState == 'complete') ");
      sb.append("vpsnm_itemMsOut('" + this.menuId +"_" + this.id + "', '" + this.menuId +"_group_" + this.curGroup.getGroupIndex() + "', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 0, null);\"");
      sb.append(" height=\"100%\" cellSpacing=\"0\" cellPadding=\"0\" width=\"100%\" border=\"0\">");
      sb.append("<TBODY><TR><TD>"+this.name+"</TD>");
      sb.append("<TD style=\"PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px\" align=\"right\" width=\"15\">");
      sb.append("<IMG id=\"" + this.menuId +"_" + this.id + "_coin\" src=\"" + this.popImageSrc + "\" border=\"0\"></TD>");
      sb.append("</TR></TBODY></TABLE></td>");
    }
    return sb.toString();
  }
  /**
   * ���Mozila������²˵����html
   * @return Html����
   */
  public String getMozilaItemHtml(){
    StringBuffer sb = new StringBuffer();
    if(this.isTop){
    	sb.append(getTopItemHtml());
    	return sb.toString();
    }
    if(this.childGroup == null){
        sb.append("<td><TABLE");
      sb.append(" onmousedown=\"\" id=\"" + this.menuId +"_" + this.id + "\"");
      sb.append(" onclick=\"document.location.href='" + this.url + "';\"");
      sb.append("cellSpacing=\"0\" cellPadding=\"0\" width=\"100%\" border=\"0\">");
      sb.append("<TBODY><TR onmouseout=\"this.className='vpsnmI';\" onmouseover=\"this.className='vpsnmIO';\" class=\"vpsnmI\"><TD onmouseout=\"this.className='vpsnmI';\" onmouseover=\"this.className='vpsnmIO';\" class=\"vpsnmI\" style=\"border-style: none;\">"+this.name+"</TD>");
      sb.append("<TD width=\"15\" style=\"padding: 0pt;\" align=\"right\">");
      sb.append("<span style=\"font-size: 1px;\">&nbsp;</span></TD>");
      sb.append("</TR></TBODY></TABLE></td>");
    }
    else{
      sb.append("<td><TABLE id=\"" + this.menuId +"_" + this.id + "\"");
      sb.append(" onmousedown=\"\"");
      sb.append("  onmouseover=\"");
      sb.append(" vpsnm_itemMsOver('" + this.menuId +"_" + this.id + "', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 'rightdown', 0, 0, 0);\"");
      sb.append(" onclick=\"document.location.href='" + this.url + "';\"");
      sb.append("onmouseout=\"");
      sb.append("vpsnm_itemMsOut('" + this.menuId +"_" + this.id + "', '" + this.menuId +"_group_" + this.curGroup.getGroupIndex() + "', '" + this.menuId +"_group_" + this.childGroup.getGroupIndex() + "', 0, event);\"");
      sb.append("cellSpacing=\"0\" cellPadding=\"0\" width=\"100%\" border=\"0\">");
      sb.append("<TBODY><TR onmouseout=\"this.className='vpsnmI';\" onmouseover=\"this.className='vpsnmIO';\" class=\"vpsnmI\"><TD onmouseout=\"this.className='vpsnmI';\" onmouseover=\"this.className='vpsnmIO';\" class=\"vpsnmI\" style=\"border-style: none;\">"+this.name+"</TD>");
      sb.append("<TD width=\"15\" style=\"padding: 0pt;\" align=\"right\">");
      sb.append("<IMG id=\"" + this.menuId +"_" + this.id + "_coin\" src=\"" + this.popImageSrc + "\" border=\"0\"></TD>");
      sb.append("</TR></TBODY></TABLE></td>");
    }
    return sb.toString();
  }
  /**
   * ����Ӳ˵������
   * @return �˵������
   */
  public GAMenuGroup getChildGroup(){
    return this.childGroup;
  }
  /**
   * �����Ƿ���֦����
   * @param isLeaf boolean
   */
  public void setLeaf(boolean isLeaf){
    this.isLeaf = isLeaf;
  }
  /**
   * �Ƿ���֦����
   * @return boolean
   */
  public boolean isLeafItem(){
    return this.isLeaf;
  }
  /**
   * ��ò˵���url
   * @return �˵�����·��
   */
  public String getUrl(){
    return this.url;
  }
  /**
   * ��ò˵�����ʾ���
   * @return �˵���ʾ���
   */
  public String getText(){
    return this.name;
  }
  /**
   * ���ò˵�url
   * @param href ����·��
   */
  public void setUrl(String href){
    this.url = href;
  }
  /**
   * ���ò˵���ʾ���
   * @param caption �˵���ʾ���
   */
  public void setText(String caption){
    this.name = caption;
  }
  /**
   * ��ò˵���ID
   * @return �˵���ID
   */
  public String getMenuItemId(){
    return this.id;
  }
  /**
   * ���ò˵�ID
   * @param mId �˵�ID
   */
  public void setMenuId(String mId){
    this.menuId = mId;
  }
  /**
   * ��ò˵�ID
   * @return �˵�ID
   */
  public String getMenuId(){
    return this.menuId;
  }
  /**
   * ���ö����˵���
   * @param isTopItem �Ƿ񶥼��˵�
   */
  public void setTopItem(boolean isTopItem){
  	this.isTop = isTopItem;
  }
}