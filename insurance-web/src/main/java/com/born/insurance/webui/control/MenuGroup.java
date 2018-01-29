package com.born.insurance.webui.control;
//package menuproject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.SAXException;

public class MenuGroup {
  private Map menuitem;
  private int expandOffsetY;
  private int expandOffsetX;

  public static void main(String args[]) throws SAXException, IOException,
      ParserConfigurationException {
//     MenuData menudata=new MenuData(new File("menu1.xml"));
//     menudata.TestGetMainMenu();
  }

//  public MenuGroup() throws ParserConfigurationException, IOException,
//      SAXException {
//    MenuData menudata=new MenuData(new File("menu.xml"));
//    menugroup=menudata.GetMenuGroup();
//  }

//  public MenuItem GetMenuItem()
//  {
//    Element element=(Element)menugroup;
//    NodeList nodelist=element.getElementsByTagName("MenuItem");
//    return (MenuItem)nodelist;
//  }

  //���ö�������
  public void setMenuItem(Map s)
  {
    menuitem=s;
  }
  public void setExpandOffsetY(int s)
  {
    expandOffsetY=s;
  }
  public void setExpandOffsetX(int s)
  {
    expandOffsetX=s;
  }

  //get��������
  public Map getMenuItem()
  {
    return menuitem;
  }
  public int getExpandOffsetY()
  {
    return expandOffsetY;
  }
  public int getExpandOffsetX()
  {
    return expandOffsetX;
  }
}