/*
 * �������� 2003-10-13
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
/**
 * @author �ᴺ��
 * <P>Description:��ʽ��</P>
 */
public class Style implements Serializable
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Style()
	{
	}
	protected Map style=new HashMap();
	/**
	 * <P>��ȡָ����ʽ�����ʽֵ</P>
	 * @param key ��ʽ��(��:Height)
	 */
	public String getStyle(String key)
	{
		key=key.toLowerCase();
		if(this.style.size()>0 && this.style.containsKey(key))
		{
			return this.style.get(key).toString();		
		}
		return "";
	}
	/**
	 * <P>����ָ����ʽ�����ʽֵ</P>
	 * @param key ��ʽ��(��:Height)
	 */
	public void setStyle(String key,String value)
	{
		key=key.toLowerCase();
		this.style.put(key,value);
	}
	/**
	 * <P>����ָ����ʽֵ</P>
	 * @param key ��ʽ��(��:Height)
	 */
	public void setStyle(String style)
	{
		style=style.toLowerCase();
		this.style.clear();
		this.style=this.stringtoMap(style);		
	}
	public String getInnerHtml()
	{
		Iterator keys=this.style.keySet().iterator();
		String result="";
		String tmp="";
		while(keys.hasNext())
		{
			tmp=keys.next().toString();
			result+=";" + tmp + ":" + this.style.get(tmp).toString();
		}
		if("" != result)
		{
			result=" Style=\"" + result.substring(1) + "\"";
		}
		return result;
	}
	/**
	 * ���ִ��Ž�Map��
	 * @param strValue
	 */
	protected Map stringtoMap(String strValue)
	{
		Map result=new HashMap();
		String[] array= StringUtils.split(strValue,";") ;//strValue.split(";");
		for(int i=0;i<array.length;i++)
		{
			String[] temp=StringUtils.split(array[i],":"); //  array[i].split(":");
			result.put(temp[0].toString(),temp[1].toString());
		}
		array=null;
		return result; 
	}
	public static void main(String[] args)
	{
		Style clss=new Style();
		clss.setStyle("height:100px;widtH:100px;Css:abcd");
		clss.setStyle("Add","add");
		System.out.println(clss.getInnerHtml()); 
	}
}
