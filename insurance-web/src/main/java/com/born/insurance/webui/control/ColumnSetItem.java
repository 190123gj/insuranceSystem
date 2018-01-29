package com.born.insurance.webui.control;

/**
 * Title:ColumnSetItem Class<br>
 * Description:��Ŀ���������<br>
 * Copyright: Copyright (c) 2003<br>
 * Company: bornsoft<br>
 * @author �ᴺ��
 * @version 1.0
 */
public class ColumnSetItem{
	private String strValue=null;
	private String strText=null;
	
	/**
	 * ���캯��<br>
	 * @param Value ��Ŀ����������Value<br>
	 * @param Text ��Ŀ����������Text<br>
	 */
	public ColumnSetItem(String Value,String Text){
		this.strValue=Value;
		this.strText=Text;
	}
	/**
	 * ������Ŀ������Value<br>
	 * @param Value ��Ŀ������Value<br>
	 * @return void<br>
	 */
	public void setValue(String Value){
		this.strValue=Value;
	}
	/**
	 * ������Ŀ������Text<br>
	 * @param Text ��Ŀ������Text<br>
	 * @return void<br>
	 */
	public void setText(String Text){
		this.strValue=Text;
	}
	/**
	 * ȡ����Ŀ������Value<br>
	 * @return String ��Ŀ������Value<br>
	 */
	public String getValue(){
		return this.strValue;
	}
	/**
	 * ȡ����Ŀ������Text<br>
	 * @return String ��Ŀ������Text<br>
	 */
	public String getText(){
		return this.strText;
	}
}