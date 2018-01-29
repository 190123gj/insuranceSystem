/*
 * �������� 2003-10-16
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

//import org.ofbiz.core.entity.GenericDelegator;
//import org.ofbiz.core.entity.jdbc.DatabaseUtil;
/**
 *
 * <p>Title:GAMenuBuilder </p>
 * <p>sample:GAMenuBuilder menu = new GAMenuBuilder(request);
 *           String menuHtml = menu.getInnerHtml("workerRoot");</p>
 * @see GAMenuBuilder(HttpServletRequest request)
 * <p>Description: �ṩ�˵������</p>
 * <p>Copyright: Jsp 2003</p>
 * <p>Company: Jsp</p>
 * @author zhangr
 * @version 1.0
 */
public class GAMenuBuilder {
//  private GenericDelegator delegator;
  private ArrayList menuGroups = new ArrayList();
  private GAMenuGroup topGroup;
  private boolean isIE = true;
  private String menuId;
  private String popImageSrc = ComponentUtil.getInstance().getImagesRequestPath() + "//Popup.gif";
  private String selectedImageSrc = ComponentUtil.getInstance().getImagesRequestPath() + "//SelectedPopup.gif";
  /**
   * ���캯��
   * @param request ����
   */
  public GAMenuBuilder(HttpServletRequest request) {
//    this.delegator = (GenericDelegator)request.getAttribute("delegator");
    if(!ComponentUtil.getInstance().isIEBrowser(request)){
      isIE = false;
    }
  }

  /**
   * ���õ����˵���ͼƬ·��
   * @param src ͼƬ·��
   */
  public void setPopImageSrc(String src){
    this.popImageSrc = src;
  }
  /**
   * ����ѡ�в˵���ͼƬ·��
   * @param src ͼƬ·��
   */
  public void setSelectedImageSrc(String src){
    this.selectedImageSrc = src;
  }
  /**
   * ��õ����˵���ͼƬ·��
   * @return ͼƬ·��
   */
  public String getPopImageSrc(){
    return this.popImageSrc;
  }
  /**
   * ���ѡ�в˵���ͼƬ·��
   * @return ͼƬ·��
   */
  public String  getSelectedImageSrc(){
    return this.selectedImageSrc;
  }
  /**
   * ��ò˵���Html����
   * @param menuAppTypeId �˵�����ID
   * @return �˵�Html����
   * @throws MenuException
   */
  public String getInnerHtml (String menuAppTypeId) throws MenuException {
    this.menuId = menuAppTypeId;
    String str = "";
    if(this.isIE){
    	str = MenuJsForIE.getMenuJs();
    }
    else{
    	str = MenuJsForMozila.getMenuJs();
    }
    str += buildTopMenu(menuAppTypeId);
    return str;
  }
  /**
   * ���춥���˵�
   * @param menuAppTypeId �˵�����ID
   * @return �˵����Html����
   * @throws MenuException
   */
  private String buildTopMenu (String menuAppTypeId) throws MenuException {
//    Connection conn = null;
//    PreparedStatement ps = null;
//    ResultSet rs = null;
//    DatabaseUtil dbUtil = null;
//    try {
//      dbUtil = new DatabaseUtil(delegator.getEntityHelperName("Menu"));
//      conn = dbUtil.getConnection();
//      ps =
//          conn.prepareStatement(
//          "select " +
//            " ma.menu_app_id , ma.menu_app_type_id, ma.menu_id , m.menu_name , " +
//            " ma.parent_menu_app_id , ma.menu_seq , m.url , m.description" +
//            " from " +
//            "   menu_app ma , " +
//            "   menu_system m," +
//            "   (select menu_app_id ,menu_app_type_id" +
//            "    from menu_app " +
//            "    start with " +
//            "       parent_menu_app_id is null " +
//            "       and menu_app_type_id in ( select menu_app_type_id from menu_app_type) " +
//            "    connect by " +
//            "       prior menu_app_id = parent_menu_app_id  " +
//            "        and prior menu_app_type_id = menu_app_type_id ) mai" +
//            " where 1=1" +
//            "   and ma.menu_app_id = mai.menu_app_id" +
//            "   and ma.menu_app_type_id = ?" +
//            "	and ma.parent_menu_app_id is null" +
//            "   and ma.menu_id = m.menu_id (+)" +
//            " order by menu_app_type_id ");
//            ps.setString(1, menuAppTypeId.trim());
//      rs = ps.executeQuery();
//      topGroup = new GAMenuGroup(0);
//      topGroup.setMenuId(this.menuId);
//      topGroup.setIsIE(this.isIE);
//      while (rs.next()) {
//        GAMenuItem menuItem =
//            new GAMenuItem(
//            rs.getString("menu_id"),
//            rs.getString("menu_name"),
//            rs.getString("url"),
//            rs.getString("description"));
//        menuItem.setTopItem(true);
//        menuItem.setMenuId(this.menuId);
//        menuItem.setIsIE(this.isIE);
// 	menuItem.setPopImageSrc(this.popImageSrc);
// 	menuItem.setSelectedImageSrc(this.selectedImageSrc);
//        menuItem.setCurGroup(topGroup);
//        topGroup.addMenuItem(menuItem);
//      }
//      this.menuGroups.add(topGroup);
//    }
//    catch (Exception ex) {
//      throw new MenuException("Unable to get root menu from the database", ex);
//    }
//    finally {
//      try {
//        if (rs != null) {
//          rs.close();
//          rs = null;
//        }
//        if (ps != null) {
//          ps.close();
//          ps = null;
//        }
//        if (conn != null) {
//          conn.close();
//          conn = null;
//        }
//      }
//      catch (Exception e) {
//      }
//    }
//    for (int i = 0; i < topGroup.size(); i++) {
//        GAMenuItem menuItem = topGroup.get(i);
//        if(isLeaf(menuItem.getMenuItemId())){
//       	  menuItem.setLeaf(true);
//          continue;
//        }
//        else{
//          menuItem.setLeaf(false);
//          buildMenu(menuItem.getMenuItemId(), menuItem);
//         }
//      }
//    return topGroup.getGroupHtml();
    return "";
}
  /**
   * ����Ƕ����˵�
   * @param mId �˵�ID
   * @param �˵������
   * @throws MenuException
   */
  private void buildMenu(String mId, GAMenuItem mi)
  throws MenuException {
//     GAMenuGroup mg = new GAMenuGroup(this.menuGroups.size());
//     mg.setMenuId(this.menuId);
//     mg.setIsIE(this.isIE);
//     ArrayList compositionItems = new ArrayList();
//     mg.setParent(mi);
//     mi.setChildGroup(mg);
//     Connection conn = null;
//     PreparedStatement ps = null;
//     ResultSet rs = null;
//     DatabaseUtil dbUtil = null;
//     try {
//       //Class.forName(driver);
//       dbUtil = new DatabaseUtil(delegator.getEntityHelperName("Menu"));
//       conn = dbUtil.getConnection();
//       ps =
//           conn.prepareStatement(
//           "select " +
//            " ma.menu_app_id , ma.menu_app_type_id, ma.menu_id , m.menu_name , " +
//            " ma.parent_menu_app_id , ma.menu_seq , m.url , m.description" +
//            " from " +
//            "   menu_app ma , " +
//            "   menu_system m," +
//            "   (select menu_app_id ,menu_app_type_id" +
//            "    from menu_app " +
//            "    start with " +
//            "       parent_menu_app_id is null " +
//            "       and menu_app_type_id in ( select menu_app_type_id from menu_app_type) " +
//            "    connect by " +
//            "       prior menu_app_id = parent_menu_app_id  " +
//            "        and prior menu_app_type_id = menu_app_type_id ) mai" +
//            " where 1=1" +
//            "   and ma.menu_app_id = mai.menu_app_id" +
//            "   and ma.menu_app_type_id = mai.menu_app_type_id" +
//            "	and ma.parent_menu_app_id = ?" +
//            "   and ma.menu_id = m.menu_id (+)" +
//            " order by menu_app_type_id ");
//       ps.setString(1, mId.trim());
//       rs = ps.executeQuery();
//       while (rs.next()) {
//         String childMenuId = rs.getString("menu_id");
//         String menuName = rs.getString("menu_Name");
//         String href = rs.getString("url");
//         String description = rs.getString("description");
//         GAMenuItem menuItem = new GAMenuItem(childMenuId, menuName, href,
//                                                  description);
//         menuItem.setPopImageSrc(this.popImageSrc);
//         menuItem.setMenuId(menuId);
//         menuItem.setIsIE(this.isIE);
//         menuItem.setSelectedImageSrc(this.selectedImageSrc);
//         if (!isLeaf(menuItem.getMenuItemId())) {
//            menuItem.setLeaf(false);
//            compositionItems.add(menuItem);
//         }
//         else{
//            menuItem.setLeaf(true);
//         }
//         menuItem.setParentGroup(mi.getCurGroup());
//         menuItem.setCurGroup(mg);
//         mg.addMenuItem(menuItem);
//
//       }
//       this.menuGroups.add(mg);
//       for(int index = 0; index<compositionItems.size(); index++){
//            GAMenuItem cMenuItem = (GAMenuItem)compositionItems.get(index);
//            buildMenu(cMenuItem.getMenuItemId(), cMenuItem);
//         }
//     } catch (Exception ex) {
//       throw new MenuException("Unable to get data from the database", ex);
//     } finally {
//       try {
//          if (rs != null) {
//            rs.close();
//            rs = null;
//          }
//          if (ps != null) {
//            ps.close();
//            ps = null;
//          }
//          if (conn != null) {
//            conn.close();
//            conn = null;
//          }
//        } catch (Exception e) {
//      }
//    }
  }
  /**
   * �Ƿ���֧�ڵ�
   * @param mId �˵�ID
   * @return boolean
   * @throws MenuException
   */
  private boolean isLeaf (String mId) throws MenuException {
//    Connection conn = null;
//    PreparedStatement ps = null;
//    ResultSet rs = null;
//    DatabaseUtil dbUtil = null;
//    boolean isLeaf = false;
//    try {
//      dbUtil = new DatabaseUtil(delegator.getEntityHelperName("Menu"));
//      conn = dbUtil.getConnection();
//      ps =
//          conn.prepareStatement(
//          "select count(menu_app_id) from menu_app where parent_menu_app_id =?");
//      ps.setString(1, mId.trim());
//      rs = ps.executeQuery();
//      if (rs.next()) {
//        //leaf node
//        int i = rs.getInt(1);
//        if (i < 1)
//          isLeaf = true;
//      }
//      return isLeaf;
//    } catch (Exception ex) {
//      throw new MenuException("Unable to get data from the database", ex);
//    } finally {
//      try {
//        if (rs != null) {
//          rs.close();
//          rs = null;
//        }
//        if (ps != null) {
//          ps.close();
//          ps = null;
//        }
//        if (conn != null) {
//          conn.close();
//          conn = null;
//        }
//      } catch (Exception e) {
//      }
//    }
    return false;
  }

}