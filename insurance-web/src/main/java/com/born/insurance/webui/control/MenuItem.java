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

public class MenuItem {
  private String label;
  private String url;
  private String textAlign;
  private String cssClassOver;

  public MenuItem() {
  }

  //���ö�������
  public void setLabel(String s)
  {
    label=s;
  }
  public void setURL(String s)
  {
    url=s;
  }
  public void setTextAlign(String s)
  {
    textAlign=s;
  }
  public void setCssClassOver(String s)
  {
    cssClassOver=s;
  }


  //get��������
  public String getLabel()
  {
    return label;
  }
  public String getURL()
  {
    return url;
  }
  public String getTextAlign()
  {
    return textAlign;
  }
  public String getCssClassOver()
  {
    return cssClassOver;
  }




}