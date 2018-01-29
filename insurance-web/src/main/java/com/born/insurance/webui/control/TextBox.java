package com.born.insurance.webui.control;
import javax.servlet.http.HttpServletRequest;
/**
 * <p>
 * Title: the textbox control
 * </p>
 * <p>
 * Description: the textbox control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author: <a href="mailto:qingj@sina.com"> </a>
 * 
 * @version 1.0
 */
public class TextBox extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//���������ɽ��ܵ�����ַ��
	protected int MaxLength;
	//Set the TextBox is readonly
	protected boolean ReadOnly = false;
	protected String Title = "";
	protected String Size = "";
	protected String value = "";
	protected String type = "text";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * <p>
	 * Description: create a textbox control
	 * </p>
	 * 
	 * @param id:
	 *            the control id
	 * @param request:
	 *            the http servlet request
	 */
	public TextBox(String id) {
		super(id);
		this.setName(id);
		this.setCssClass("inputText");
	}
	/**
	 * Set the OfbizTextBox's MaxLength
	 * 
	 * @param OfbizTextBox's
	 *            MaxLength
	 */
	public void setMaxLength(int MaxLength) {
		this.MaxLength = MaxLength;
	}
	/**
	 * Get the OfbizTextBox's MaxLength
	 * 
	 * @return MaxLength
	 */
	public int getMaxLength() {
		return this.MaxLength;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param value
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Set the OfbizTextBox readonly
	 * 
	 * @param ReadOnly
	 */
	public void setReadOnly(boolean ReadOnly) {
		this.ReadOnly = ReadOnly;
	}
	/**
	 * Get the OfbizTextBox readonly
	 * 
	 * @return readonly
	 */
	public boolean getReadOnly() {
		return this.ReadOnly;
	}
	/**
	 * ����OfbizTextBox�ߴ�
	 * 
	 * @param Size
	 */
	public void setSize(String Size) {
		this.Size = Size;
	}
	/**
	 * ��ȡOfbizTextBox�ߴ�
	 * 
	 * @return OfbizTextBox�ߴ�
	 */
	public String getSize() {
		return this.Size;
	}
	/**
	 * set Title
	 * 
	 * @param Title
	 */
	public void setTitle(String Title) {
		this.Title = Title;
	}
	/**
	 * get Title
	 * 
	 * @return this.Title
	 */
	public String getTitle() {
		return this.Title;
	}
	/**
	 * Set the OfbizTextBox's value
	 * 
	 * @param OfbizTextBox's
	 *            value
	 */
	public void setValue(String value) {
		this.setText(value);
	}
	/**
	 * Get the OfbizTextBox's value
	 * 
	 * @return OfbizTextBox's value
	 */
	public String getValue() {
		return this.getText();
	}
	/**
	 * Set the OfbizTextBox's Width
	 * 
	 * @param Width
	 */
	public void setWidth(String Width) {
		this.width = Width;
		this.setStyle("width", Width);
	}
	/**
	 * Get the OfbizTextBox's Width
	 * 
	 * @return the OfbizTextBox's Width
	 */
	public String getWidth() {
		return this.width;
	}
	/**
	 *  
	 */
	protected String getElementHtml() {
		if (this.getMaxLength()!=0) {
			this.sourceAttributes.put("maxlength", String.valueOf(this
					.getMaxLength()));
		}
		if (this.getReadOnly() == true) {
			this.sourceAttributes.put("readonly", String.valueOf(this
					.getReadOnly()));
		}
		//		���size
		if (!(this.getSize() == null) && !this.getSize().equals("")) {
			this.sourceAttributes.put("size", this.getSize());
		}
		//		 
		if (!(this.getTitle() == null) && !this.getTitle().equals("")) {
			this.sourceAttributes.put("title", this.getTitle());
		} else {
			if(this.getValue()!=null)
			{
				this.sourceAttributes.put("title", this.getValue());
			}
			else
			{
				this.sourceAttributes.remove("title");
			}
			
		}
		if (this.getValue() != null) {
			this.sourceAttributes.put("value", this.getValue());
		}
		else
		{
			this.sourceAttributes.remove("value");
		}
		return "<input type=\""+this.type+"\"" + super.getElementHtml() + " >";
	}
	/**
	 * ��ȡ�ı��������(��������ʹ��)
	 * 
	 * @param request
	 *            ����
	 * @param name
	 *            �ı���name
	 * @return �ͻ���ָ���ı��������
	 */
	public static String getValue(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		return value;
	}
	public static String getValue(RequestUtils request, String name) {
			String value = request.getString(name);
			return value;
		}
	/**
	 * ��ȡ�ı��������(��������ʹ��)
	 * 
	 * @param name
	 *            �ı���name
	 * @param request
	 *            ����
	 * @deprecated As of this
	 * @return �ͻ���ָ���ı��������
	 */
	public static String getValue(String name, HttpServletRequest request) {
		String value = request.getParameter(name);
		return value;
	}
}
