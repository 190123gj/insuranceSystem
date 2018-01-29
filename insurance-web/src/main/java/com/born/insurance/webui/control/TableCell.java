package com.born.insurance.webui.control;

/**
 * <p>
 * Title: TableCell
 * </p>
 * <p>
 * Description: Table Cell
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author qch
 * @version 1.0
 */

public class TableCell extends AbstractComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int cellIndex = 0;

	protected TableRow parentRow = null;

	private int columnSpan = 0;

	private int rowSpan = 0;

	static final String ROWSPAN_ATTRIBUTE = "rowspan";

	static final String COLSPAN_ATTRIBUTE = "colspan";

	protected String horizontalAlign = "";

	protected String verticalAlign = "";

	protected boolean wrap = true;

	private boolean necessary = false;

	public TableCell(String id) {
		super(id);
		this.setElementType("td");
	}

	public String getComponentId() {
		return super.getComponentId() + String.valueOf(cellIndex);
	}

	/**
	 * ��ȡ���ж���
	 * 
	 * @return
	 */
	public TableRow getParentRow() {
		return parentRow;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public int getColumnIndex() {
		return this.cellIndex;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public int getRowIndex() {
		return this.parentRow.rowIndex;
	}

	protected String getElementHtml() {
		Table table = this.parentRow.parentTable;
		if (this.isVisible())
		{
			StringBuffer html = new StringBuffer(255);
			if (rowSpan > 1)
			{
				this.sourceAttributes.put(ROWSPAN_ATTRIBUTE, String.valueOf(rowSpan));
			}
			if (columnSpan > 1)
			{
				this.sourceAttributes.put(COLSPAN_ATTRIBUTE, String.valueOf(columnSpan));
			}
			if (this.necessary == true)
			{
				this.setAttribute("IsNecessary", "true");
			}
			if (!this.wrap)
			{
				html.append("<" + this.getElementType() + super.getElementHtml() + " noWrap>");
			} else
			{
				html.append("<" + this.getElementType() + super.getElementHtml() + ">");
			}

			if (this.getText() != null && this.getText().length() != 0)
			{
				html.append(this.getText());
			}
			for (int i = 0; i < this.components.size(); i++)
			{
				try
				{
					html.append(this.getComponent(i).getInnerHtml());
				} catch (Exception e)
				{
					e.printStackTrace();
					String strWrong = "�ӿؼ����?��" + this.getParentRow().getParentTable().getComponentId() + "��" + this.getRowIndex() + "��,��" + this.getColumnIndex() + "��";
					if (this.getComponent(i) != null)
					{
						strWrong += "�ӿؼ�idΪ" + this.getComponent(i).getComponentId();
					} else
					{
						strWrong += "�ӿؼ�Ϊnull";
					}
					strWrong += " �����쳣��Ϣ��" + e.getMessage();
					throw new ComponentException(strWrong);
				}
			}
			html.append(this.assembleEndTag());
			return html.toString();
		} else
		{
			return "";
		}
	}

	/**
	 * �õ�ˮƽ����ʽ
	 */
	public String getHorizontalAlign() {
		return this.horizontalAlign;
	}

	/**
	 * ����ˮƽ����ʽ
	 */
	public void setHorizontalAlign(String horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
		this.sourceAttributes.put("align", horizontalAlign);
	}

	/**
	 * �õ���ֱ����ʽ
	 */
	public void setVerticalAlign(String verticalAlign) {
		this.verticalAlign = verticalAlign;
		this.sourceAttributes.put("valign", verticalAlign);
	}

	/**
	 * ���ô�ֱ����ʽ
	 */
	public String getVerticalAlign() {
		return this.verticalAlign;
	}

	/**
	 * �õ��кϲ�
	 */
	public int getColumnSpan() {
		return this.columnSpan;
	}

	/**
	 * �����кϲ�
	 */
	public void setColumnSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}

	/**
	 * �õ��кϲ�
	 */
	public int getRowSpan() {
		return this.rowSpan;
	}

	/**
	 * �����кϲ�
	 */
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	/**
	 * ���õ�Ԫ���������Ƿ���
	 */
	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	/*
	 * �õ���Ԫ���������Ƿ���
	 */
	public boolean getWrap() {
		return this.wrap == wrap;
	}

	/**
	 * �Ƿ������ֵ�ڿ�дgrid��Ӧ��
	 */
	public boolean isNecessary() {

		return this.necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public void setEnabled(boolean enabled) {
		if (enabled)
		{
			super.setEnabled(enabled);
			if (("celldisabled").equals(this.getCssClass()))
			{
				this.setCssClass(null);
			}
		} else
		{
			super.setEnabled(enabled);
			this.setCssClass("celldisabled");
		}
	}

	public int getCellIndex() {
		return cellIndex;
	}
}