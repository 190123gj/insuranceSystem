package com.born.insurance.webui.control;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Title:ListBox Component<br>
 * Description:ListBox���<br>
 * Copyright: Copyright (c) 2003<br>
 * Company: bornsoft<br>
 * 
 * @author �ᴺ��
 * @version 1.0
 */
public class ListBox extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���캯��<br>
	 * 
	 * @param id
	 *            ���ID<br>
	 */
	private String valueField;

	private String textField;

	private int size = 1;

	public ListBox(String id) {
		super(id);
		this.name = id;
	}

	protected String getElementHtml() {
		StringBuffer innerHTML = new StringBuffer();
		innerHTML.append("<select ");
		innerHTML.append(super.getElementHtml() + " >");
		for (int index = 0; index < this.components.size(); index++)
		{
			innerHTML.append(((AbstractComponent) this.components.get(index)).getInnerHtml() + "\r\n");
		}
		innerHTML.append("</select>");
		return innerHTML.toString();
	}

	/**
	 * ѡ��������
	 * 
	 * @param index
	 */
	public void setSelectedIndex(int index) {
		for (int i = 0; i < this.components.size(); i++)
		{
			if (i == index)
			{
				((Option) this.components.get(i)).Selected = true;
			} else
			{
				((Option) this.components.get(i)).Selected = false;
			}
		}
	}

	public void setSelectedOption(String value) {
		for (int i = 0; i < this.components.size(); i++)
		{
			Option option = ((Option) this.components.get(i));
			if (option.value.equals(value))
			{
				option.Selected = true;
			} else
			{
				option.Selected = false;
			}
		}
	}

	public Option getSelectedOption() {
		for (int i = 0; i < this.components.size(); i++)
		{
			Option option = ((Option) this.components.get(i));
			if (option.Selected)
			{
				return option;
			}
		}
		return null;
	}

	public int getOptionSize() {
		return this.components.size();
	}

	public void dataBind() {
		if (this.dataSource instanceof List)
		{
			List listDataSource = (List) this.dataSource;
			Iterator it = listDataSource.iterator();
			for (; it.hasNext();)
			{
				Map map = (Map) it.next();
				Object oValue = map.get(this.valueField);
				Object oText = map.get(this.textField);
				String value = "";
				String text = "";
				if (oValue == null)
				{
					value = "";
				} else
				{
					value = oValue.toString();
				}
				if (oText == null)
				{
					text = "";
				} else
				{
					text = oText.toString();
				}
				Option option = new Option();
				option.setValue(value);
				option.setText(text);
				this.components.add(option);
			}
		}
	}

	/**
	 * ����������Ҫ�ﶨ�ĵ��ı�
	 * 
	 * @param textId
	 *            String
	 */
	public void setTextField(String textId) {
		this.textField = textId;
	}

	/**
	 * ���option�ڵ�
	 * 
	 * @param option
	 */
	public void addOptions(Option option) {
		this.components.add(option);
	}

	public Option getOption(int index) {
		return (Option) this.components.get(index);
	}

	/**
	 * ����option�ڵ�
	 * 
	 * @param index
	 * @param option
	 */
	public void addOptions(int index, Option option) {
		this.components.add(index, option);
	}

	public void addComponent() {
	}

	/**
	 * ��ÿؼ��󶨵��ı�
	 * 
	 * @return String
	 */
	public String getTextField() {
		return this.textField;
	}

	/**
	 * ����Ҫ�ﶨ��ֵ
	 * 
	 * @param valueId
	 *            String
	 */
	public void setValueField(String valueId) {
		this.valueField = valueId;
	}

	/**
	 * ��ÿؼ��󶨵�ֵ
	 * 
	 * @return String
	 */
	public String getValueField() {
		return this.valueField;
	}

	/**
	 * ���ø߶�
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
		if (this.size > 1)
		{
			this.sourceAttributes.put("size", String.valueOf(size));
		} else
		{
			this.sourceAttributes.remove("size");
		}
	}

	/**
	 * ��ȡ�߶�
	 * 
	 * @return
	 */
	public int getSize() {
		return this.size;
	}

	public void setWidth(String width) {
		this.style.setStyle("width", width);
	}
}