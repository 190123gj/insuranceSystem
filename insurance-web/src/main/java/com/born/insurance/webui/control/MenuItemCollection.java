package com.born.insurance.webui.control;
import java.util.*;
/**
 *
 * <p>Title:MenuItemCollection </p>
 * <p>Description:�����ṩ�����в˵�������ɾ���޸�</p>
 * <p>Copyright:Jsp 2003</p>
 * <p>Company:Jsp </p>
 * @author zhangr
 * @version 1.0
 */
public class MenuItemCollection {
  private ArrayList menuItems = new ArrayList();
  /**
   * ���캯��
   */
  public MenuItemCollection() {
  }
  /**
   * ���б������Ӳ˵���
   * @param mItem �˵������
   */
  public void addMenuItem(GAMenuItem mItem){
    this.menuItems.add(mItem);
  }
  /**
   * �������ɾ��˵���
   * @param index ����ֵ
   */
  public void removeMenuItem(int index){
    this.menuItems.remove(index);
  }
  /**
   * ��������ò˵���
   * @param index ����ֵ
   * @return �˵������
   */
  public GAMenuItem getMenuItem(int index){
    return (GAMenuItem)this.menuItems.get(index);
  }
  /**
   * ����б��в˵�����
   * @return �б��в˵�����
   */
  public int size(){
    return this.menuItems.size();
  }
}