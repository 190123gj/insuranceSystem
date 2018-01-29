package com.born.insurance.webui.control;

import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.*;

/**
 * <p>
 * Title: WebGrid
 * </p>
 * <p>
 * Description: ��дWebGrid
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * <p>
 * Ĭ����ʽ:��ťĬ����ʽ:nlbutton
 * </p>
 * <p>
 * Ĭ����ʽ:��Ĭ����ʽ:webgriditem
 * </p>
 * <p>
 * Ĭ����ʽ:ͷ��ʽĬ����ʽ:webgridhead
 * </p>
 * <p>
 * Ĭ����ʽ:��Ĭ����ʽ: webgridfooter
 * </p>
 * 
 * @author qch
 * @version 1.0
 */

public class WebGrid extends ListGrid {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// �����м���
	private List exclusiveColumns = new ArrayList();

	// ��ť����
	private List buttons;

	// �洢�����
	private List dataColumns;

	// �༭������
	private int intEditRowIndex = -1;

	// grid Ψһ��ʶ�ֶ�
	private String keyField = "";

	// grid ֻ����ʶ
	private boolean readOnly = false;

	private GridEditRow gridEditRow = null;

	/**
	 * �ͻ��Ƿ���������
	 */
	private boolean clientAddNew = true;

	private int gridViewModel = 0;

	boolean isAfterBind = false;

	boolean isThrowException = true;

	/**
	 * ��ť���ڸ���״̬��ΪĬ�ϣ�
	 */
	public static final int DEFAULT_MODEL = 0;

	/**
	 * ��ť������grid�����Զ�����ӣ��ṩ��ť�ͻ��˷������ṩ����ؼ����У� ����������������Ϸ����·�Ϊgrid
	 */
	public static final int CUSTOM_MODEL = 1;

	/**
	 * ֻ��grid����ʾ���ṩ��ֵ�������Լ����ֵ�����ڿؼ���
	 */
	// public static final int CUSTOM_MODEL=2;
	// ��ť����
	/**
	 * ��ť���ͣ�ȷ��
	 */
	public static final int CONFIRM_BUTTON = 0;

	/**
	 * 
	 * ��ť���ͣ�����
	 */
	public static final int INSERT_BUTTON = 1;

	/**
	 * 
	 * ��ť���ͣ�ɾ��
	 */
	public static final int DELETE_BUTTON = 2;

	/**
	 * 
	 * ��ť���ͣ�ȡ��/���
	 */
	public static final int CANCEL_BUTTON = 3;

	/**
	 * WebGrid ���캯��
	 * 
	 * @param id
	 * @@deprecated ����ʹ��WebGrid(id,request)����,�����������Ϊֻ��IE��������
	 */
	public WebGrid(String id) {
		super(id, null);
	}

	/**
	 * WebGrid ���캯��
	 * 
	 * @param id
	 *            ���ID
	 * @param request
	 *            ���������ж�����������ͣ�
	 */
	public WebGrid(String id, HttpServletRequest request) {
		super(id, request);
		this.setDefaultAttribute();
	}

	/**
	 * WebGrid ���캯��
	 * 
	 * @param id
	 *            ���ID
	 * @param request
	 *            ���������ж�����������ͣ�
	 * @param columnSize
	 *            ����
	 */
	public WebGrid(String id, HttpServletRequest request, int columnSize) {
		super(id, request, columnSize);
		this.initEditRow(true);
		this.setDefaultAttribute();
	}

	/**
	 * ���캯��
	 * 
	 * @param id
	 *            ���id
	 * @param request
	 *            HttpServletRequest
	 * @param columnSize
	 *            ������
	 * @param isInitEditor
	 *            �Ƿ��ʼEditor(true,����������ʼ����false����������ʱ��ʼ��)
	 */
	public WebGrid(String id, HttpServletRequest request, int columnSize, boolean isInitEditor) {
		super(id, request, columnSize);
		this.initEditRow(isInitEditor);
		this.setDefaultAttribute();

	}

	/**
	 * ��ӻ�����
	 * 
	 * @param columns
	 *            List
	 */
	public void addExclusiveColumn(List columns) {
		this.exclusiveColumns.add(columns);
	}

	/**
	 * �õ�������
	 * 
	 * @param index
	 *            int
	 * @return List
	 */
	public List getExclusiveColumn(int index) {
		if (this.exclusiveColumns.size() > index)
		{
			return (List) this.exclusiveColumns.get(index);
		}
		return null;
	}

	/**
	 * �Ƴ����
	 * 
	 * @param index
	 *            int
	 */
	public void removeExclusiveColumn(int index) {
		this.exclusiveColumns.remove(index);
	}

	/**
	 * ������г����
	 */
	public void clearExclusiveColumn() {
		this.exclusiveColumns.clear();
	}

	/**
	 * �������������
	 * 
	 * @return int
	 */
	public int exclusiveColumnSize() {
		return this.exclusiveColumns.size();
	}

	/**
	 * �õ��༭�ж���
	 * 
	 * @return GridEditRow
	 */
	public GridEditRow getGridEditRow() {
		return this.gridEditRow;
	}

	/**
	 * ���ñ༭��
	 * 
	 * @param index
	 *            
	 */
	public void setGridEidtRow(int index) {
		this.intEditRowIndex = index;
	}

	/**
	 * ���ñ༭��
	 * 
	 * @param index
	 *            int
	 */
	public void setGridEditRow(int index) {
		this.intEditRowIndex = index;
	}

	/**
	 * �õ��༭������
	 * 
	 * @return int
	 */
	public int getGridEidtRowIndex() {
		return this.intEditRowIndex;
	}

	/**
	 * ��Ӱ�ť
	 * 
	 * @param button
	 *            Button
	 */
	public void addButton(Button button) {
		this.getButtons().add(button);
	}

	/**
	 * �Ƴ�Button������
	 * 
	 * @param buttonType
	 *            ��ť����
	 */
	public void removeButton(int buttonType) {
		for (int i = 0; i < this.getButtons().size(); i++)
		{
			Button button = (Button) this.getButtons().get(i);
			if (String.valueOf(buttonType).equals(button.getAttribute("buttonType")))
			{
				this.getButtons().remove(i);
				break;
			}
		}
	}

	/**
	 * �õ���ť
	 * 
	 * @param buttonId
	 *            button Id
	 * @return Button
	 */
	public Button getButton(String buttonId) {
		Button returnButton = null;
		for (int i = 0; i < this.getButtons().size(); i++)
		{
			Button button = (Button) this.getButtons().get(i);
			if (button.getComponentId().equals(buttonId))
			{
				returnButton = button;
			}
		}
		return returnButton;
	}

	/**
	 * ��ť������Ӱ�ť
	 * 
	 * @param buttonType
	 *            ��ť����
	 */
	public void addButton(int buttonType) {

		Button button = new Button(this.getComponentId() + "_button_" + String.valueOf(buttonType));
		button.setAttribute("buttonType", String.valueOf(buttonType));
		button.setCssClass("nlbutton");
		switch (buttonType)
		{
		case 0:
			button.setText("ȷ��");
			button.addEvent("onclick", "WebGrid_Confirm('" + this.getComponentId() + "');");
			break;
		case 1:
			button.setText("����");
			button.addEvent("onclick", "WebGrid_Insert('" + this.getComponentId() + "');");
			break;
		case 2:
			button.setText("ɾ��");
			button.addEvent("onclick", "WebGrid_Delete('" + this.getComponentId() + "');");
			break;
		case 3:
			button.setText("���");
			button.addEvent("onclick", "WebGrid_Cancel('" + this.getComponentId() + "');");
			break;
		}
		this.addButton(button);
	}

	/**
	 * ��ť���ͻ�ȡ��ť
	 * 
	 * @param buttonType
	 *            ��ť����
	 * @return Button
	 */
	public Button getButton(int buttonType) {
		Button returnButton = null;
		for (int i = 0; i < this.getButtons().size(); i++)
		{
			Button button = (Button) this.getButtons().get(i);
			if (String.valueOf(buttonType).equals(button.getAttribute("buttonType")))
			{
				returnButton = button;
				break;
			}
		}
		return returnButton;
	}

	/**
	 * ������
	 * 
	 * @return TableColumn
	 */
	public TableColumn addTableColumn() {
		TableColumn newTableColumn = super.addTableColumn();
		this.getDataColumns().add(this.getNewListDataManager(this.columnSize() - 1));
		// this.tableColumns.add(newTableColumn);
		return newTableColumn;
	}

	/**
	 * ɾ����
	 * 
	 * @param index
	 *            ����
	 */
	public void removeColumn(int index) {
		super.removeColumn(index);
		refreshGridDataColumn(this);
	}

	/**
	 * ����grid������ֶ�
	 * 
	 * @param keyField
	 *            �����ֶ�
	 */
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	/**
	 * ��ȡgrid������ֶ�
	 * 
	 * @return KeyField
	 */
	public String getKeyField() {
		return this.keyField;
	}

	/**
	 * ��ȡgrid��д״̬
	 * 
	 * @return boolean
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	/**
	 * ���ÿͻ����¼�
	 * 
	 * @param functionStr
	 *            �ͻ��˺���
	 */
	public void setEditRowChangeEvent(String functionStr) {
		IEvent event = new Event("oneditrowchange");
		event.setFuncName(functionStr);
		this.addEvent(event);
	}

	/**
	 * �õ��¼�����
	 * 
	 * @return IEvent
	 */
	public IEvent getEditRowChangeEvent() {
		return this.getEvent("oneditrowchange");
	}

	/**
	 * ���÷������˱༭�б仯�¼�
	 * 
	 * @param functionStr
	 *            �ͻ�����
	 * @param className
	 *            ���������¼���
	 * @param methodName
	 *            ���������¼�����
	 */
	public void setEditRowChangeEvent(String functionStr, String className, String methodName) {
		IServerEvent event = new ServerEvent("oneditrowchange");
		event.setFuncName(functionStr);
		event.setClassName(className);
		event.setMethodName(methodName);
		this.addEvent(event);
	}

	/**
	 * ����grid��д״̬
	 * 
	 * @param readOnly
	 *            boolean
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * ���ÿͻ����Ƿ��������
	 * 
	 * @param clientAddNew
	 */
	public void setClientAddNew(boolean clientAddNew) {
		this.clientAddNew = clientAddNew;
	}

	/**
	 * ��ȡ�ͻ��˷��������
	 * 
	 * @return
	 */
	public boolean getClientAddNew() {
		return this.clientAddNew;
	}

	/**
	 * ������ʾģʽ
	 * 
	 * @param newGridViewModel
	 */
	public void setGridViewModel(int newGridViewModel) {
		this.gridViewModel = newGridViewModel;
		if (this.gridViewModel == WebGrid.CUSTOM_MODEL)
		{
			this.gridFooterDiv.setVisible(false);
			this.gridDiv.setStyle("OVERFLOW", "");
			this.gridDiv.sourceAttributes.put("onscroll", "");
		}
	}

	/**
	 * ��ȡgrid����ʾģʽ
	 * 
	 * @return
	 */
	public int getGridViewModel() {
		return this.gridViewModel;
	}

	protected String getScriptHtml() {
		String strReturnValue = "";
		if (this.gridViewModel == 0)
		{
			strReturnValue = super.getScriptHtml() + FormulaJS.getFormulaJS() + WebGridCommonJS.getWebGridCommonJS() + WebGridJS.getWebGridJS();
		} else
		{
			strReturnValue = super.getScriptHtml() + WebGridCommonJS.getWebGridCommonJS() + WebGridCustomJS.getWebGridCustomJS();
		}
		return strReturnValue;// super.getScriptHtml();//
	}
	protected void beforeGetElementHtmlEvent()
	{
		super.beforeGetElementHtmlEvent();
		String gridHeadFooterWidth = "";
		if (!("").equals(this.width))
		{
			int tableWidth = parseWidth(this.width);
			if (tableWidth != -1)
			{
				gridHeadFooterWidth = String.valueOf(tableWidth - 16) + "px";
			}
		}
		if (!this.getWidth().equals("") && this.isIE)
		{
			this.gridDiv.setStyle("width", this.getWidth());
			this.gridHeadDiv.setStyle("width", gridHeadFooterWidth);
			this.gridFooterDiv.setStyle("width", gridHeadFooterWidth);
		}		
		if (this.isReadOnly())
		{
			for (int i = 0; i < this.rowSize(); i++)
			{
				IEvent event = new Event("onclick");
				event.setFuncName("ListGrid_RowOnSelect(this,'" + this.componentID + "')");
				this.getRow(i).setEvent(event);
			}
			this.customAttributes.put("WebGridReadOnly", "true");

		} else
		{
			this.addEvent("onkeydown", "return editor_keydown(this,event);");
		}
		this.customAttributes.put("GridViewModel", String.valueOf(this.gridViewModel));
		if (this.clientAddNew == false)
		{
			this.customAttributes.put("ClientAddNew", "false");
		}
	}
	protected String getElementHtml() {
		StringBuffer html = new StringBuffer(20000);
		
		html.append(super.getElementHtml() + "\r\n");
		
		if (this.gridViewModel == 0)
		{
			StringBuffer buttonHtml = new StringBuffer(200);
			StringBuffer buttonId = new StringBuffer(50);
			int spaceCount=4-this.getButtons().size();
			String spaceString="";
			for(int k=0;k<spaceCount;k++)
			{
				spaceString+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			for (int i = 0; i < this.getButtons().size(); i++)
			{
				IComponent button = (IComponent) this.getButtons().get(i);
				if (i == 0)
				{

					buttonHtml.append("<script>\r\n");
					buttonHtml.append("var " + this.getComponentId() + "_buttonHtml=[");
					String temp = spaceString+button.getInnerHtml();
					temp = StringUtils.replace(temp, "\"", "\\\"");// temp.replaceAll("\"", "\\\\\"");
					buttonHtml.append("\"" + temp + "\"");
					buttonId.append("var " + this.getComponentId() + "_buttonId=[\"" + button.getComponentId() + "\"");
				} else
				{
					String temp = button.getInnerHtml();
					temp = StringUtils.replace(temp, "\"", "\\\"");// temp.replaceAll("\"", "\\\\\"");
					buttonHtml.append(",\"" + temp + "\"");
					buttonId.append(",\"" + button.getComponentId() + "\"");
				}

			}
			if (buttonHtml.length() > 0)
			{
				buttonHtml.append("];\r\n</script>");
				html.append(buttonHtml.toString());
			}
		}
		this.sendOutClicentInfo(html);
		if (!this.isReadOnly())
		{
			if (this.gridEditRow != null) html.append(this.gridEditRow.getInnerHtml());
			if (this.getGridEidtRowIndex() >= 0)
			{
//				 html.append("<body onload=\"WebGrid_RowOnclick('" +
//				 this.getComponentId() +
//				 "',document.getElementById('" + this.getComponentId() +
//				 "').rows[" + this.getGridEidtRowIndex() + "]);\"" +
//				 "></body>");
				 html.append("<script>\r\n");
				 html.append("var ").append(this.getComponentId()).append("_fun = function(){WebGrid_RowOnclick('");
				 html.append(this.getComponentId());
				 html.append("',document.getElementById('" + this.getComponentId() + "').rows[" + this.getGridEidtRowIndex() + "]);}\r\n");
				 html.append("window.attachEvent(\"onload\","+this.getComponentId()+"_fun);");
				 html.append("</script>");

			} else
			{
				// html.append("<body onload=\"document.forms[0].reset();\"></body>");
			}

		} else
		{
			// System.out.println("-----dddddddddddddddd----");
			// this.setEvent("onkeydown","");
		} 
		// html.append("<script>");
		// html.append(" window.document.title='��ӭ�����ã��������Ϊ��ʾ�汾';\r\n");
		// html.append("</script>");
		return html.toString();
	}

	/**
	 * ��List DataSource
	 */
	protected void bindListDataSource() {
		List dataSource = (List) this.getDataSource();
		boolean isHeadBind = this.getHeadDataSource() instanceof List;
		if (isHeadBind)
		{
			isHeadBind = ((List) this.getHeadDataSource()).get(0) instanceof Map;
		}
		if (isAutoGenerateColumns())
		{
			/*
			 * for(int i=0;i<dataSource.size();i++) { GridColumn column=(GridColumn)this.addTableColumn(); column.setDataField(); }
			 */
		} else
		{
			if (isHeadBind)
			{
				bindHead();
			}
			if (dataSource.size() == 0)
			{
				if (this.gridViewModel == 0)
				{
					TableRow dr = this.addTableRow();
					this.setRowStyleClass(dr, 0);
					for (int j = 0; j < this.columnSize(); j++)
					{
						TableCell cell = dr.addTableCell();
						if (this.getGridColumn(j).isAutoAscend())
						{
							cell.setText(String.valueOf(1));
						} else
						{
							cell.setText("&nbsp;");
						}
						List listManagers = (List) this.getDataColumns().get(j);
						ArrayListManager list = (ArrayListManager) listManagers.get(0);
						list.add("");
						if (!("").equals(this.getGridColumn(j).getHiddenValueDataField()))
						{
							((ArrayListManager) listManagers.get(1)).add("");
						}
					}
					this.setGridEidtRow(0);
				}
				return;
			}
			int rowIndex = 0;
			for (Iterator it = dataSource.iterator(); it.hasNext(); rowIndex++)
			{
				Object oTemp = it.next();
				if (oTemp instanceof Map)
				{
					Map dataMap = (Map) oTemp;
					TableRow dr = this.addTableRow();
					this.setRowStyleClass(dr, rowIndex);
					for (int j = 0; j < this.columnSize(); j++)
					{
						TableCell cell = dr.addTableCell();
						List listManagers = (List) this.getDataColumns().get(j);
						ArrayListManager list = (ArrayListManager) listManagers.get(0);
						String key = (String) this.getGridColumn(j).getDataField();
						if (!("").equals(key))
						{
							Object o = dataMap.get(key);
							String temp = "";
							if (o == null)
							{
								cell.text = "";
							} else
							{
								if (o instanceof String)
								{
									temp = (String) o;
								} else if (o instanceof Double)
								{
									String formatString = "###########0";
									double tempdouble = ((Double) o).doubleValue();
									if (tempdouble == 0)
									{
										temp = "0";
									} else
									{
										Editor contorl = (Editor) this.gridEditRow.editors.get(j);
										IComponent textbox = contorl.component;
										String strFormat = "";
										if (textbox instanceof OfbizTextBox)
										{
											OfbizTextBox textBox = (OfbizTextBox) textbox;
											if (textBox.DecimalDigits > 0)
											{
												formatString += ".";
											}
											else
											{
												formatString="#####0.########";
											}
											for (int n = 0; n < textBox.DecimalDigits; n++)
											{
												formatString += "0";
											}

										}
										DecimalFormat decimalFormat = new DecimalFormat(formatString);
										temp = decimalFormat.format(tempdouble);
									}

								} else
								{
									temp = o.toString();
								}
								cell.text = temp;
							}
							if (o == null)
							{
								if (!dataMap.containsKey(key))
								{
									if (this.isThrowException)
									{
										System.out.print("����[" + key + "]���ֶο��ܲ����ڣ�");
										throw new ComponentException("����[" + key + "]���ֶο��ܲ����ڣ�");
									}
								}
							}
							list.add(cell.text);
						}
						if (this.getGridColumn(j).isAutoAscend())
						{
							cell.setText(String.valueOf(rowIndex + 1));
						}
						TableCellCreateEvent(cell, rowIndex);

						String keyValue = (String) this.getGridColumn(j).getHiddenValueDataField();
						if (!("").equals(keyValue))
						{
							String strValue = (String) dataMap.get(keyValue);
							if (strValue != null)
							{
								((ArrayListManager) listManagers.get(1)).add((String) dataMap.get(keyValue));
							} else
							{
								((ArrayListManager) listManagers.get(1)).add("");
							}
						}
					}
				}
			}
			for (int i = 0; i < this.columnSize(); i++)
			{
				GridColumn column = this.getGridColumn(i);
				if (column.getIsColumnSum())
				{
					double sum = 0;
					for (int j = 0; j < rowSize(); j++)
					{
						String text = this.getRow(j).getTableCell(i).getText();
						text = StringUtils.replace(text, ",", "");// text.replaceAll(",","");
						if (text.equals("&nbsp;"))
						{
							text = "";
						}
						if (!text.equals(""))
						{
							try
							{
								double tempNum = Double.parseDouble(text);
								sum += tempNum;
							} catch (Exception e)
							{
								System.out.print("�ϼ��г��ַǷ��ַ�" + text);
								throw new ComponentException("�ϼ��г��ַǷ��ַ�");
							}
						}
					}
					Editor contorl = (Editor) this.gridEditRow.editors.get(i);
					IComponent textbox = contorl.component;
					String viewText = "0";
					String strFormat = "";
					if (textbox instanceof OfbizTextBox)
					{
						OfbizTextBox sumTextBox = (OfbizTextBox) textbox;
						if (sumTextBox.getFormatChangeEnable())
						{
							strFormat = "###,###,###,###";
						}
						if (sumTextBox.DecimalDigits > 0)
						{
							strFormat += ".";
						}
						for (int j = 0; j < sumTextBox.DecimalDigits; j++)
						{
							strFormat += "0";
						}

						if (sumTextBox.IsConvertZero == true || column.isConvertZero() == true)
						{
							viewText = "";
						}
					}

					java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);

					if (sum != 0)
					{
						viewText = df.format(sum);
					}
					this.getGridFooter().getRow(0).getTableCell(i).setText(viewText);
				}
			}
		}
	}

	/**
	 * ���� ����ʽ
	 * 
	 * @param dr
	 *            TableRow
	 * @param rowIndex
	 *            int
	 */
	protected void setRowStyleClass(TableRow dr, int rowIndex) {
		super.setRowStyleClass(dr, rowIndex);
		dr.addEvent("onclick", "WebGrid_RowOnclick('" + this.componentID + "',this)");
	}

	/**
	 * grid cell�������ʱ�����¼�
	 * 
	 * @param cell
	 *            TableCell
	 * @param rowIndex
	 *            int
	 */
	protected void TableCellCreateEvent(TableCell cell, int rowIndex) {
		if (("0").equals(cell.getText()))
		{
			Editor contorl = (Editor) this.gridEditRow.editors.get(cell.cellIndex);
			IComponent textbox = contorl.component;
			if (textbox instanceof OfbizTextBox)
			{
				if (((OfbizTextBox) textbox).IsConvertZero == true || this.getGridColumn(cell.cellIndex).isConvertZero())
				{
					cell.setText("&nbsp;");
				}
			}
		} else if (("").equals(cell.getText()))
		{
			cell.setText("&nbsp;");
		} else
		{
			super.TableCellCreateEvent(cell, rowIndex);
		}
	}

	/**
	 * grid ͷ��Ԫ�񴴽��󼤷��¼�
	 * 
	 * @param cell
	 *            TableCell
	 * @param rowIndex
	 *            int
	 */
	protected void TableHeadCellCreateEvent(TableCell cell, int rowIndex) {
		if (this.gridViewModel == WebGrid.DEFAULT_MODEL)
		{
			cell.setHorizontalAlign(ComponentConstant.ALIGN_CENTER);
		}
	}

	/**
	 * Init Edit Row
	 * 
	 * @param isInitEditor
	 *            boolean
	 */
	private void initEditRow(boolean isInitEditor) {
		this.gridEditRow = new GridEditRow(this, isInitEditor);
	}

	/**
	 * ����Ĭ������
	 */
	private void setDefaultAttribute() {
		this.setAllowMouseOver(false);
		this.setItemCssClass("webgriditem");
		this.setHeadCssClass("webgridhead");
		this.setAlternatingItemCssClass("");
		this.setFooterCssClass("webgridfooter");
		for (int i = 0; i < 4; i++)
		{
			this.addButton(i);
		}
		this.gridDiv.sourceAttributes.put("onscroll", (String) this.gridDiv.sourceAttributes.get("onscroll") + "WebGrid_MoveButton('" + this.componentID + "');");
		this.getGridFooter().setVisible(true);
		// this.grid .sourceAttributes.put("onscroll","javascript:document.getElementById('"+this.gridHeadDiv.getComponentId()+"').scrollLeft =
		// document.getElementById('"+this.gridDiv.getComponentId()+"').scrollLeft;if(document.getElementById('"+this.gridFooterDiv.getComponentId()+"'))document.getElementById('"+this.gridFooterDiv.getComponentId()+"').scrollLeft
		// = document.getElementById('"+this.gridDiv.getComponentId()+"').scrollLeft;");
	}

	private List getButtons() {
		if (this.buttons == null)
		{
			this.buttons = new ArrayList();
		}
		return this.buttons;
	}

	private List getDataColumns() {
		if (this.dataColumns == null)
		{
			this.dataColumns = new ArrayList();
		}
		return this.dataColumns;
	}

	private List getNewListDataManager(int index) {
		ArrayListManager listValueManager = new ArrayListManager(this.getComponentId() + "_Text_" + String.valueOf(index));
		listValueManager.setAttribute("ColumnIndex", String.valueOf(index));
		ArrayListManager listTextManager = new ArrayListManager(this.getComponentId() + "_Value_" + String.valueOf(index));
		listTextManager.setAttribute("ColumnIndex", String.valueOf(index));
		List listManagers = new ArrayList();
		listManagers.add(listValueManager);
		listManagers.add(listTextManager);
		return listManagers;
	}

	private static void refreshGridDataColumn(WebGrid grid) {
		for (int i = 0; i < grid.columnSize(); i++)
		{
			List listManagers = ((List) grid.getDataColumns().get(i));
			ArrayListManager listManagerText = (ArrayListManager) listManagers.get(0);
			listManagerText.setComponentId(grid.getComponentId() + "_Text_" + String.valueOf(i));
			ArrayListManager listManagerValue = (ArrayListManager) listManagers.get(0);
			listManagerValue.setComponentId(grid.getComponentId() + "_Value_" + String.valueOf(i));
		}
	}

	// ���Ϳͻ�����Ϣ
	private StringBuffer sendOutClicentInfo(StringBuffer wirteHtml) {
		// ���а��ֶκʹ洢��������ϵ������
		HashMapManager returnData = new HashMapManager(this.getComponentId() + "_Column_Hidden_Name");
		wirteHtml.append(returnData.getScriptHtml() + "\r\n");
		ArrayListManager hiddenColumnInfo = new ArrayListManager(this.getComponentId() + "_Store_ColumnInfo");
		wirteHtml.append(hiddenColumnInfo.getScriptHtml() + "\r\n");
		for (int i = 0; i < this.columnSize(); i++)
		{
			GridColumn column = this.getGridColumn(i);
			if (!column.isVisible())
			{
				hiddenColumnInfo.add(String.valueOf(i));
			}
			List listManagers = ((List) this.getDataColumns().get(i));
			ArrayListManager listManagerText = (ArrayListManager) listManagers.get(0);
			if (column.IsInceptData())
			{
				returnData.put(listManagerText.getComponentId(), column.getDataField());
			}
			wirteHtml.append(listManagerText.getElementHtml() + "\r\n");
			if (!("").equals(this.getGridColumn(i).getHiddenValueDataField()))
			{
				ArrayListManager listManagerValue = (ArrayListManager) listManagers.get(1);
				wirteHtml.append(listManagerValue.getElementHtml() + "\r\n");
				if (column.IsInceptData())
				{
					returnData.put(listManagerValue.getComponentId(), column.getHiddenValueDataField());
				}
			}
		}
		wirteHtml.append(returnData.getElementHtml() + "\r\n");
		wirteHtml.append(hiddenColumnInfo.getElementHtml() + "\r\n");
		return wirteHtml;
	}

	/**
	 * �õ�grid�ط������
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param gridId
	 *            grid Id
	 * @return
	 */
	public static Object getGridReturnData(HttpServletRequest request, String gridId) {
		return getGridReturnData(gridId, request);
	}

	/**
	 * �õ�grid�ط������
	 * 
	 * @param gridId
	 *            grid Id
	 * @param request
	 *            HttpServletRequest
	 * @return �������
	 * @deprecated As of getGridReturnData��HttpServletRequest request��String gridId��;
	 */
	public static Object getGridReturnData(String gridId, HttpServletRequest request) {
		Map column_Hidden_Name = null;
		try
		{
			column_Hidden_Name = HashMapManager.parseToMap(gridId + "_Column_Hidden_Name", request);
		} catch (Exception e)
		{

			return null;
		}
		if (null == column_Hidden_Name)
		{

			return null;
		}

		List ListAllData = new ArrayList();
		// �����ͻ��˴洢��ݵ��齨������ȡ��ݡ�
		for (Iterator i = column_Hidden_Name.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry e = (Map.Entry) i.next();
			String keyID = (String) e.getKey();
			List listData = null;
			try
			{
				listData = ArrayListManager.parseHidden(keyID, request);

			} catch (Exception e1)
			{
				e1.printStackTrace();
				return null;
			}
			int intSize = ListAllData.size();
			if (listData == null)
			{
				continue;
			}
			for (int j = 0; j < listData.size(); j++)
			{
				if (intSize == 0)
				{
					Map mapData = new HashMap();
					if (e.getValue().equals(""))
					{
						continue;
					}
					mapData.put(e.getValue(), listData.get(j));
					ListAllData.add(mapData);
				} else
				{
					if (e.getValue().equals(""))
					{
						continue;
					}
					Map mapData = (Map) ListAllData.get(j);
					mapData.put(e.getValue(), listData.get(j));
				}
			}
		}

		ListAllData = getClearedDiryData(ListAllData);
		return ListAllData;
	}

	public static Object getGridReturnData(RequestUtils request, String gridId) {
		Map column_Hidden_Name = null;
		try
		{
			column_Hidden_Name = HashMapManager.parseToMap(gridId + "_Column_Hidden_Name", request);
		} catch (Exception e)
		{

			return null;
		}
		if (null == column_Hidden_Name)
		{

			return null;
		}

		List ListAllData = new ArrayList();
		// �����ͻ��˴洢��ݵ��齨������ȡ��ݡ�
		for (Iterator i = column_Hidden_Name.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry e = (Map.Entry) i.next();
			String keyID = (String) e.getKey();
			List listData = null;
			try
			{
				listData = ArrayListManager.parseHidden(keyID, request);

			} catch (Exception e1)
			{
				e1.printStackTrace();
				return null;
			}
			int intSize = ListAllData.size();
			if (listData == null)
			{
				continue;
			}
			for (int j = 0; j < listData.size(); j++)
			{
				if (intSize == 0)
				{
					Map mapData = new HashMap();
					if (e.getValue().equals(""))
					{
						continue;
					}
					mapData.put(e.getValue(), listData.get(j));
					ListAllData.add(mapData);
				} else
				{
					if (e.getValue().equals(""))
					{
						continue;
					}
					Map mapData = (Map) ListAllData.get(j);
					mapData.put(e.getValue(), listData.get(j));
				}
			}
		}

		ListAllData = getClearedDiryData(ListAllData);
		return ListAllData;
	}

	/**
	 * <P>
	 * ������
	 * </P>
	 * 
	 * @param dataList
	 *            ����Map��ArrayList
	 * @return �����к��ArrayList
	 */
	protected static List getClearedDiryData(List dataList) {
		int count = dataList.size();
		for (int i = count - 1; i >= 0; i--)
		{
			boolean isHaveData = false;
			Map map = (Map) dataList.get(i);
			for (Iterator j = map.entrySet().iterator(); j.hasNext();)
			{
				Map.Entry e = (Map.Entry) j.next();
				String keyID = (String) e.getKey();
				String fieldValue = (String) map.get(keyID);
				if (fieldValue.equals(null) || fieldValue.toLowerCase().equals("null") || fieldValue.trim().equals("") || fieldValue.trim().equals("&nbsp;") || fieldValue.equals("0"))
				{
				} else
				{
					isHaveData = true;
					break;
				}
			}
			if (!isHaveData)
			{
				dataList.remove(i);
			}
		}
		return dataList;
	}

	/**
	 * @return
	 */
	public boolean isThrowException() {
		return isThrowException;
	}

	/**
	 * @param b
	 */
	public void setThrowException(boolean b) {
		isThrowException = b;
	}

}
