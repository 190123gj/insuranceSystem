package com.born.insurance.webui.control;
/**
 * <p>
 * Title: The TextArea Control
 * </p>
 * <p>
 * Description:The TextArea Control
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: bornsoft
 * </p>
 * @author: QingJian
 * 
 * @version 1.0
 */
public class TextArea extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Set the TextBox is readonly
	protected boolean ReadOnly = false;
	protected String Value = "";
	protected int Rows;
	protected int Cols;
	/**
	 * ���캯��
	 * 
	 * @param id
	 */
	public TextArea(String id) {
		super(id);
		this.name = id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Set the TextBox readonly
	 * 
	 * @param ReadOnly
	 */
	public void setReadOnly(boolean ReadOnly) {
		this.ReadOnly = ReadOnly;
	}
	/**
	 * Get the TextBox readonly
	 * 
	 * @return readonly
	 */
	public boolean getReadOnly() {
		return this.ReadOnly;
	}
	/**
	 * set value
	 * 
	 * @param Value
	 */
	public void setValue(String Value) {
		this.setText(Value);
	}
	/**
	 * get value
	 * 
	 * @return value
	 */
	public String getValue() {
		if(this.getText()==null)
		{
			this.setText("");
		}		
		return this.getText();
	}
	/**
	 * ��������
	 * 
	 * @param Rows
	 */
	public void setRows(int Rows) {
		this.Rows = Rows;
	}
	/**
	 * ��ȡ����
	 * 
	 * @return rows
	 */
	public int getRows() {
		return this.Rows;
	}
	/**
	 * ��������
	 * 
	 * @param Cols
	 */
	public void setCols(int Cols) {
		this.Cols = Cols;
	}
	/**
	 * ��ȡ����
	 * 
	 * @return cols
	 */
	public int getCols() {
		return this.Cols;
	}
	/**
	 * set width
	 * 
	 * @param Width
	 */
	public void setWidth(String Width) {
		this.setStyle("width", Width);
		this.width = Width;
	}
	/**
	 * get width
	 * 
	 * @return width
	 */
	public String getWidth() {
		return this.width;
	}
	/**
	 * set height
	 * 
	 * @param Height
	 */
	public void setHeight(String Height) {
		this.setStyle("height", Height);
		this.height = Height;
	}
	/**
	 * get height
	 * 
	 * @return height
	 */
	public String getHeight() {
		return this.height;
	}
	/**
	 * Get html code of the control
	 * 
	 * @return html code
	 */
	protected String getElementHtml() {
		this.sourceAttributes.put("Cols", String.valueOf(this.getCols()));
		this.sourceAttributes.put("rows", String.valueOf(this.getRows()));
		if (this.getReadOnly()) {
			this.sourceAttributes.put("readonly", String.valueOf(this
					.getReadOnly()));
		}
		return "<textarea " + super.getElementHtml() + ">" + this.getValue()
				+ "</textarea>";
	}
}
