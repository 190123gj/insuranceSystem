package com.born.insurance.webui.control;

import java.io.Serializable;

/**
 *
 * <p>Title: the type of the dropdownlist control</p>
 * <p>Description: ����������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: bornsoft</p>
 * @author: �ᴺ��
 * @version 1.0
 */
public class DropDownType implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * <p>Description: the sign of type.</p>
   */
  private int dropDownType = 2;

  /**
   * ���캯��
   * @param type int
   */
  public DropDownType(int type) {
    this.dropDownType = type;
  }

  public static DropDownType ONLYDROPDOWN = new DropDownType(1);

  public static DropDownType INPUT_OR_DROPDOWN = new DropDownType(0);

  public static DropDownType INPUT_AND_DROPDOWN = new DropDownType(2);

  /**
   * ��������������
   * @return int
   */
  public int getDropDownType(){
    return this.dropDownType;
  }
}