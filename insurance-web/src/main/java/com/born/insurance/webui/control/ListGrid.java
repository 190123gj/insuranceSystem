package com.born.insurance.webui.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * <p>
 * Title: ListGrid
 * </p>
 * <p>
 * Description: ֻ��grid
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * <p>
 * Ĭ����ʽ:��ͷĬ����ʽ .listhead
 * </p>
 * <p>
 * Ĭ����ʽ:����Ĭ����ʽ .listGrid
 * </p>
 * <p>
 * Ĭ����ʽ: ��ͷĬ������ʽ .listGridHeadRow
 * </p>
 * <p>
 * Ĭ����ʽ:����Ĭ�ϵ�������ʽ .trdual
 * </p>
 * <p>
 * Ĭ����ʽ:����Ĭ�� ˫������ʽ trodd
 * </p>
 * <p>
 * Ĭ����ʽ:��������ƶ��Ϸ�Ĭ������ʽ����ѡ����Ĭ����ʽ.trmouseover
 * </p>
 * <p>
 * Ĭ����ʽ:��Ĭ����ʽ. webgridfooter
 * </p>
 * <p>
 * Ĭ����ʽ:��Ԫ���ûҵ�Ĭ����ʽ celldisabled
 * </p>
 * @author qch
 * @version 1.0
 */
public class ListGrid extends GridTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Table gridHead = null;
	protected GridTable gridFooter = null;
	protected HtmlDiv gridHeadDiv = null;
	protected HtmlDiv gridFooterDiv = null;
	protected HtmlDiv gridDiv = null;
	static final String BORDER_ATTRIBUTE = "border";
	static final String SPACING_ATTRIBUTE = "cellspacing";
	static final String PADDING_ATTRIBUTE = "cellpadding";
	protected static final String SELECTED_ITEM_CLASS_ATTRIBUTE = "selectedItemClass";
	protected static final String ITEM_CLASS_ATTRIBUTE = "ItemClass";
	protected static final String ALTERNATING_ITEM_CLASS_ATTRIBUTE = "alternatingItemCssClass";
	private boolean autoGenerateColumns = false;
	private Object headDataSource = null;
	//���������ʽ
	private String alternatingItemCssClass = "";
	//��ͨ�����ʽ
	private String itemCssClass = "";
	//����ѡ������ʽ
	private String selectedItemCssClass = "";
	private String headCssItem = "";
	private String footerCssClassItem = "";
	//����mouserOver
	private boolean allowMouseOver = true;
	protected boolean isIE = true;
	boolean isAfterBind = false;
	protected int allGridWidth = 0;
	protected int allGridHeadWidth = 0;
	boolean addBlank = false;
	private String defaulFormatString = "";
	
	public String getDefaulFormatString() {
		return defaulFormatString;
	}
	
	public void setDefaulFormatString(String defaulFormatString) {
		this.defaulFormatString = defaulFormatString;
	}
	
	/**
	 * ListGrid ���캯��
	 * @param id
	 * @deprecated ���鲻ʹ�����ж�������汾
	 */
	@Deprecated
	public ListGrid(String id) {
		super(id);
		init();
	}
	
	public ListGrid(String id, HttpServletRequest request) {
		super(id);
		isIE = ComponentUtil.isIEBrowser(request);
		init();
		setDefaultStyle();
	}
	
	public ListGrid(String id, HttpServletRequest request, int columnSize) {
		super(id);
		isIE = ComponentUtil.isIEBrowser(request);
		init();
		for (int i = 0; i < columnSize; i++) {
			this.addTableColumn();
		}
		setDefaultStyle();
	}
	
	/**
	 * ��ʼ������
	 */
	private void init() {
		gridHead = new Table(this.componentID + "_head");
		gridFooter = new GridTable(this.componentID + "_footer");
		gridFooter.setVisible(false);
		this.gridFooterDiv = new HtmlDiv(this.gridFooter.getComponentId() + "_div");
		this.gridHeadDiv = new HtmlDiv(this.gridHead.getComponentId() + "_div");
		this.gridDiv = new HtmlDiv(this.componentID + "_div");
		this.gridFooterDiv.addComponent(this.gridFooter);
		this.gridHeadDiv.addComponent(this.gridHead);
		if (this.isIE) {
			this.gridDiv.setStyle("overflow:scroll");
			this.gridFooterDiv.setStyle("OVERFLOWX: auto; OVERFLOWY: hidden;OVERFLOW: hidden");
			this.gridHeadDiv.setStyle("OVERFLOWX: auto; OVERFLOWY: hidden;OVERFLOW: hidden");
			//this.gridDiv.sourceAttributes.put("onscroll","javascript:document.getElementById('"+this.gridHeadDiv.getComponentId()+"').scrollLeft = document.getElementById('"+this.gridDiv.getComponentId()+"').scrollLeft;if(document.getElementById('"+this.gridFooterDiv.getComponentId()+"')");
			this.gridDiv.sourceAttributes.put(
				"onscroll",
				"javascript:document.getElementById('" + this.gridHeadDiv.getComponentId()
						+ "').scrollLeft = document.getElementById('"
						+ this.gridDiv.getComponentId()
						+ "').scrollLeft;if(document.getElementById('"
						+ this.gridFooterDiv.getComponentId() + "'))document.getElementById('"
						+ this.gridFooterDiv.getComponentId()
						+ "').scrollLeft = document.getElementById('"
						+ this.gridDiv.getComponentId() + "').scrollLeft;");
		} else {
			this.gridDiv.setStyle("OVERFLOW:scroll");
		}
		this.setIsToolTip(true);
		this.setBorder(1);
		this.addStyle("TABLE-LAYOUT", "fixed");
		this.gridFooter.addStyle("TABLE-LAYOUT", "fixed");
		this.gridHead.addStyle("TABLE-LAYOUT", "fixed");
	}
	
	/**
	 * ��ȡgrid head
	 * @return Table
	 */
	public Table getGridHead() {
		return this.gridHead;
	}
	
	/**
	 * ��ȡGrid Footer
	 * @return
	 */
	public GridTable getGridFooter() {
		return this.gridFooter;
	}
	
	/**
	 * ��ȡGrid Head Div
	 * @return
	 */
	public HtmlDiv getGridHeadDiv() {
		return this.gridHeadDiv;
	}
	
	/**
	 * ��ȡGrid Footer Div
	 * @return
	 */
	public HtmlDiv getGridFooterDiv() {
		return this.gridFooterDiv;
	}
	
	/**
	 * ��ȡGrid Div
	 * @return
	 */
	public HtmlDiv getBodyDiv() {
		return this.gridDiv;
	}
	
	/**
	 * ��ȡ�Զ���չ�У���Ϊʵ�֣�
	 * @return
	 */
	public boolean isAutoGenerateColumns() {
		return this.autoGenerateColumns;
	}
	
	/**
	 * �õ����������ʽ
	 * @return
	 */
	public String getAlternatingItemCssClass() {
		return this.alternatingItemCssClass;
	}
	
	/**
	 * ���ý��������ʽ
	 * @param cssClass
	 */
	public void setAlternatingItemCssClass(String cssClass) {
		this.alternatingItemCssClass = cssClass;
	}
	
	/**
	 * �õ���ͨ�����ʽ
	 * @return
	 */
	public String getItemCssClass() {
		return this.itemCssClass;
	}
	
	/**
	 * ������ͨ�����ʽ
	 * @param itemCssClass
	 */
	public void setItemCssClass(String itemCssClass) {
		this.itemCssClass = itemCssClass;
	}
	
	/**
	 * ����ѡ�������ʽ
	 */
	public void setSelectedItemCssClass(String itemCssClass) {
		this.selectedItemCssClass = itemCssClass;
	}
	
	/**
	 * ���õõ�ѡ�������ʽ
	 */
	public String getSelectedItemCssClass() {
		return this.selectedItemCssClass;
	}
	
	/**
	 * �Ƿ����mouse over�¼�
	 */
	public boolean isAllowMouseOver() {
		return this.allowMouseOver;
	}
	
	/**
	 * ����ʹ�� Mouser Over�¼�
	 */
	public void setAllowMouseOver(boolean allowMouseOver) {
		this.allowMouseOver = allowMouseOver;
	}
	
	/**
	 * ���� �Զ� �����
	 */
	public void setAutoGenerateColumns(boolean autoGenerateColumns) {
		this.autoGenerateColumns = autoGenerateColumns;
	}
	
	/**
	 * ���ñ߿���
	 * @param newBorder
	 */
	@Override
	public void setBorder(int newBorder) {
		super.setBorder(newBorder);
		this.gridHead.setBorder(newBorder);
		this.gridFooter.setBorder(newBorder);
	}
	
	/**
	 * ��ݰ�
	 */
	@Override
	public void dataBind() {
		isAfterBind = true;
		int allWidth = 0;
		int visibleColumnCount = 0;
		int fixColumnCount = 0;
		for (int i = 0; i < this.columnSize(); i++) {
			GridColumn column = this.getGridColumn(i);
			if (column.isVisible()) {
				if (column.isFixationWidth()) {
					fixColumnCount++;
				}
				visibleColumnCount++;
				int intWidth = ListGrid.parseWidth(this.getColumn(i).getWidth());
				if (intWidth != -1) {
					allWidth += intWidth;
				}
			}
		}
		int inttableWidth = ListGrid.parseWidth(this.getWidth());
		int lastColumnIndex = 0;
		int lastColumnWidth = 0;
		//������б߿�ʱ�����ʵ��Ȼ�Ӧ�ü�ȥ�߿���߿�Ϊ1(visibleColumnCount*1)
		int borderWidth = visibleColumnCount * this.getCellSpacing();
		int scrollWidth = 17;
		
		if (allWidth <= (inttableWidth - borderWidth)) {
			
			int intaddWidth = (inttableWidth - scrollWidth - allWidth - borderWidth)
								/ (visibleColumnCount - fixColumnCount);
			int intSpareWidth = (inttableWidth - scrollWidth - allWidth - borderWidth)
								% (visibleColumnCount - fixColumnCount);
			//�����ٱ߿�
			//int intaddWidth = (inttableWidth - scrollWidth - allWidth) / (visibleColumnCount-fixColumnCount);
			//int intSpareWidth = (inttableWidth - scrollWidth - allWidth) % visibleColumnCount;
			int addTotalWidth = 0;
			for (int i = 0; i < this.columnSize(); i++) {
				GridColumn column = this.getGridColumn(i);
				if (column.isVisible() && !column.isFixationWidth()) {
					int intWidth = ListGrid.parseWidth(column.getWidth());
					if (intWidth != -1) {
						intWidth = intWidth + intaddWidth;
						addTotalWidth += intaddWidth;
						column.setWidth(String.valueOf(intWidth) + "px");
						lastColumnIndex = i;
						lastColumnWidth = intWidth;
					}
				}
			}
			addTotalWidth += intSpareWidth - 1 + visibleColumnCount * this.getCellSpacing();
			this.getColumn(lastColumnIndex).setWidth(
				String.valueOf(lastColumnWidth + intSpareWidth - 1) + "px");
			if (!this.isIE) {
				this.gridHead.width = String.valueOf(inttableWidth + scrollWidth);
				this.gridFooter.width = String.valueOf(inttableWidth + scrollWidth);
			}
			this.allGridWidth = inttableWidth;
			this.allGridHeadWidth = allWidth + addTotalWidth;
		} else {
			if (!this.isIE) {
				inttableWidth = allWidth;
				this.width = String.valueOf(allWidth);
				this.gridHead.width = String.valueOf(allWidth + scrollWidth);
				this.gridFooter.width = String.valueOf(allWidth + scrollWidth);
				for (int i = this.columnSize() - 1; i >= 0; i--) {
					TableColumn column = this.getColumn(i);
					if (column.isVisible()) {
						lastColumnWidth = ListGrid.parseWidth(column.getWidth());
						lastColumnIndex = i;
						break;
					}
				}
			}
			this.allGridWidth = allWidth;
			this.allGridHeadWidth = this.allGridWidth + visibleColumnCount * this.getCellSpacing()
									+ 2 * this.getCellSpacing();
		}
		TableRow headRow = this.gridHead.addTableRow();
		TableRow footerRow = this.gridFooter.addTableRow();
		footerRow.setCssClass(this.getFooterCssClass());
		for (int i = 0; i < columnSize(); i++) {
			TableCell cell = headRow.getTableCell(i);
			if (!("").equals(this.getColumn(i).cssClass))
				;
			{
				cell.setCssClass(this.getColumn(i).cssClass);
			}
			// cell.enabled = this.getColumn(i).enabled;
			if (!("").equals(this.getColumn(i).width))
				;
			{
				cell.setWidth(this.getColumn(i).width);
				if (lastColumnIndex == i && !this.isIE) {
					cell.setWidth(String.valueOf(parseWidth(this.getColumn(i).width) + 16));
				}
			}
			cell.visible = this.getColumn(i).visible;
			if (!("").equals(this.getColumn(i).horizontalAlign)) {
				cell.setHorizontalAlign(this.getColumn(i).horizontalAlign);
			}
			cell.wrap = this.getColumn(i).wrap;
			cell = footerRow.addTableCell();
			cell.setText("&nbsp;");
			if (!("").equals(this.getColumn(i).cssClass))
				;
			{
				cell.setCssClass(this.getColumn(i).cssClass);
			}
			//cell.enabled = this.getColumn(i).enabled;
			if (!("").equals(this.getColumn(i).width))
				;
			{
				cell.setWidth(this.getColumn(i).width);
				if (lastColumnIndex == i && !this.isIE) {
					cell.setWidth(String.valueOf(parseWidth(this.getColumn(i).width) + 16));
				}
			}
			cell.visible = this.getColumn(i).visible;
			if (!("").equals(this.getColumn(i).horizontalAlign)) {
				cell.setHorizontalAlign(this.getColumn(i).horizontalAlign);
			}
			cell.wrap = this.getColumn(i).wrap;
		}
		if (this.getDataSource() instanceof List) {
			this.bindListDataSource();
		} else {
		}
	}
	
	@Override
	protected String getScriptHtml() {
		return super.getScriptHtml() + ListGridJS.getListGridJS() + "\r\n";
	}
	
	protected void beforeGetElementHtmlEvent() {
		StringBuffer html = new StringBuffer(5000);
		if (this.isIE) {
			this.gridHeadDiv.setWidth(this.width);
			this.gridDiv.setWidth(this.width);
		}
		//this.gridFooter.setWidth(this.width);
		//this.gridFooterDiv.setWidth(this.width);
		//this.gridHead.setWidth(this.width);
		//�����ͷ�Ŀ�ȣ�grid ���-16px��
		//����� �Ŀ�ȣ�grid ���-16px��
		String gridHeadFooterWidth = "";
		if (!("").equals(this.width)) {
			int tableWidth = parseWidth(this.width);
			if (tableWidth != -1) {
				gridHeadFooterWidth = String.valueOf(tableWidth - 18) + "px";
			}
		}
		if (!this.getWidth().equals("") && this.isIE) {
			this.gridDiv.setStyle("width", this.getWidth());
			this.gridHeadDiv.setStyle("width", gridHeadFooterWidth);
			this.gridFooterDiv.setStyle("width", gridHeadFooterWidth);
		}
		if (StringUtil.isNotEmpty(this.height)) {
			this.gridDiv.setStyle("height", this.getHeight());
		}
		//ȡ��grid��Ŀ��
		this.setHeight("");
		if (this.isIE) {
			this.setWidth("");
			/*if(((List)this.dataSource).size()==0&&this.getClass().equals(ListGrid.class))
			{
			    this.setWidth(String.valueOf(this.allGridWidth));
			}
			else
			{
			
			}*/
		}
		//�����Զ�������
		this.setAttribute(ListGrid.SELECTED_ITEM_CLASS_ATTRIBUTE, this.getSelectedItemCssClass());
		this.setAttribute(ListGrid.ITEM_CLASS_ATTRIBUTE, this.itemCssClass);
		this.setAttribute(ListGrid.ALTERNATING_ITEM_CLASS_ATTRIBUTE, this.alternatingItemCssClass);
		//�����е�onmouseOver �¼�
		if (this.isAllowMouseOver()) {
			for (int i = 0; i < this.rowSize(); i++) {
				TableRow selectedRow = this.getRow(i);
				selectedRow.addAttribute("onmouseover",
					"ListGrid_RowOnSelect(this,'" + this.getComponentId() + "');");
				selectedRow.addAttribute("onmouseout",
					"ListGrid_RowOutSelect(this,'" + this.getComponentId() + "');");
			}
		}
		for (int i = 0; i < this.columnSize(); i++) {
			Map columnSourceAttributes = new HashMap();
			TableColumn column = this.getColumn(i);
			TableColumn footerColumn = this.gridFooter.getColumn(i);
			TableColumn headColumn = this.gridHead.getColumn(i);
			columnSourceAttributes.putAll(column.sourceAttributes);
			columnSourceAttributes.putAll(headColumn.sourceAttributes);
			headColumn.sourceAttributes.clear();
			headColumn.sourceAttributes.putAll(columnSourceAttributes);
			columnSourceAttributes.clear();
			columnSourceAttributes.putAll(column.sourceAttributes);
			columnSourceAttributes.putAll(footerColumn.sourceAttributes);
			footerColumn.sourceAttributes.clear();
			footerColumn.sourceAttributes.putAll(columnSourceAttributes);
		}
		
		html.append(this.getGridHeadDiv().getInnerHtml() + "\r\n");
		this.gridDiv.setText(super.getElementHtml());
		html.append(this.gridDiv.getInnerHtml() + "\r\n");
		html.append(this.gridFooterDiv.getInnerHtml() + "\r\n");
		
	}
	
	@Override
	protected String getElementHtml() {
		beforeGetElementHtmlEvent();
		StringBuffer html = new StringBuffer(5000);
		html.append(this.getGridHeadDiv().getInnerHtml() + "\r\n");
		this.gridDiv.setText(super.getElementHtml());
		html.append(this.gridDiv.getInnerHtml() + "\r\n");
		html.append(this.gridFooterDiv.getInnerHtml() + "\r\n");
		HtmlDiv div = new HtmlDiv("");
		div.setText(html.toString());
		return div.getInnerHtml();
	}
	
	/**
	 * ����grid head ��ʽ
	 * @param cssClass
	 */
	public void setHeadCssClass(String cssClass) {
		this.headCssItem = cssClass;
	}
	
	/**
	 * ��ȡͷ��ʽ
	 * 
	 * @return
	 */
	public String getHeadCssClass() {
		return this.headCssItem;
	}
	
	/**
	 * ���ý���ʽ
	 * @param cssClass
	 */
	public void setFooterCssClass(String cssClass) {
		this.footerCssClassItem = cssClass;
	}
	
	/**
	 * ���ý���ʽ
	 * @return
	 */
	public String getFooterCssClass() {
		return this.footerCssClassItem;
	}
	
	@Override
	public void setWidth(String newWidth) {
		super.setWidth(newWidth);
		if (!this.isIE) {
			int width = parseWidth(newWidth);
			this.gridHead.setWidth(String.valueOf(width + 16));
			this.gridFooter.setWidth(String.valueOf(width + 16));
		}
	}
	
	/**
	 * add Table Column
	 * @return
	 */
	@Override
	public TableColumn addTableColumn() {
		this.gridHead.addTableColumn();
		this.gridFooter.addTableColumn();
		TableColumn newTableColumn = this.getNewColumn();
		this.tableColumns.add(newTableColumn);
		return newTableColumn;
	}
	
	/**
	 * insert Table Column
	 * @return
	 */
	@Override
	public TableColumn insertTableColumn(int index) {
		this.gridHead.insertTableColumn(index);
		this.gridFooter.insertTableColumn(index);
		TableColumn newTableColumn = this.getNewColumn();
		this.tableColumns.add(index, newTableColumn);
		return newTableColumn;
	}
	
	/**
	 * ����ͷ���Դ
	 */
	public void setHeadDataSource(Object dataSource) {
		this.headDataSource = dataSource;
	}
	
	/*
	*  �õ�ͷ���Դ
	*/
	public Object getHeadDataSource() {
		return this.headDataSource;
	}
	
	/**
	 * ������Ĭ����ʽ
	 */
	protected void setDefaultStyle() {
		this.setCssClass("listGrid");
		this.setHeadCssClass("listHead");
		this.setCellPadding(0);
		this.gridHead.setCellPadding(0);
		this.gridHead.setCellSpacing(0);
		this.setCellSpacing(0);
		this.gridFooter.setCellPadding(0);
		this.gridFooter.setCellSpacing(0);
		this.setBorder(1);
		this.gridFooter.customAttributes.put("borderColor", "#e9eaec");
		this.customAttributes.put("borderColor", "#e9eaec");
		this.gridHead.customAttributes.put("borderColor", "#e9eaec");
		this.setFooterCssClass("");
		//this.setCssClass("listGridHead");
		this.setAlternatingItemCssClass("trodd");
		this.setItemCssClass("trdual");
		this.setSelectedItemCssClass("trmouseover");
		if (this.gridHead.getRow(0) != null) {
			this.gridHead.getRow(0).setCssClass("listGridHeadRow");
		}
		//this.addStyle("BORDER-RIGHT: #e9eaec 1px solid; BORDER-TOP: #e9eaec 1px solid; BORDER-LEFT: #e9eaec 1px solid; BORDER-BOTTOM: #e9eaec 1px solid; BORDER-COLLAPSE: collapse");
		//this.gridHead.addStyle();
	}
	
	/**
	 * ������Ĭ����ʽ
	 * @param dr
	 * @param rowIndex
	 */
	protected void setRowStyleClass(TableRow dr, int rowIndex) {
		if (!this.getItemCssClass().equals("") && rowIndex % 2 == 0) {
			dr.setCssClass(this.getItemCssClass());
		} else if (this.getAlternatingItemCssClass() != null
					&& this.getAlternatingItemCssClass().length() != 0 && rowIndex % 1 == 0) {
			dr.setCssClass(this.getAlternatingItemCssClass());
		} else {
			dr.setCssClass(this.getItemCssClass());
		}
	}
	
	/**
	 * ͷ��ݰ�
	 */
	protected void bindHead() {
		Map dataHeadMap = (Map) ((List) this.headDataSource).get(0);
		if (!this.isIE) {
			this.gridHead.getRow(0).setCssClass(this.headCssItem);
		}
		for (int j = 0; j < this.columnSize(); j++) {
			String key = this.getGridColumn(j).getDataField();
			TableCell cell = this.gridHead.getRow(0).getTableCell(j);
			if (!("").equals(key)) {
				String str = (String) dataHeadMap.get(key);
				if (str == null) {
					cell.setText("");
				} else {
					cell.setText(str);
				}
			}
			if (this.isIE) {
				cell.setCssClass(this.headCssItem);
			}
			if (("").equals(cell.getText()) && !cell.hasComponent()) {
				cell.setText("&nbsp;");
			}
			TableHeadCellCreateEvent(cell, 0);
		}
	}
	
	protected static int parseWidth(String strWidth) {
		boolean isValid = false;
		int intWidth = 0;
		try {
			intWidth = Integer.parseInt(strWidth);
			isValid = true;
		} catch (Exception e) {
			isValid = false;
		}
		if (!isValid) {
			try {
				if (("").equals(strWidth)) {
					return 0;
				}
				intWidth = Integer.parseInt(strWidth.substring(0, strWidth.length() - 2));
			} catch (Exception e) {
				return -1;
			}
		}
		return intWidth;
	}
	
	/**
	 * ��list���Դ
	 */
	protected void bindListDataSource() {
		List dataSource = (List) this.getDataSource();
		boolean isHeadBind = this.headDataSource instanceof List;
		if (isHeadBind) {
			isHeadBind = ((List) this.headDataSource).get(0) instanceof Map;
		}
		if (isAutoGenerateColumns()) {
			/*for(int i=0;i<dataSource.size();i++)
			         {
			  GridColumn column=(GridColumn)this.addTableColumn();
			  column.setDataField();
			         }*/
		} else {
			if (isHeadBind) {
				bindHead();
			}
			
			if (dataSource.size() == 0) {
				TableRow dr = this.addTableRow();
				for (int j = 0; j < this.columnSize(); j++) {
					TableCell cell = dr.addTableCell();
					cell.components.clear();
					cell.setVisible(true);
					cell.setText("&nbsp;");
					cell.setWidth(String.valueOf(this.allGridWidth - 17));
					break;
					
				}
				super.setBorder(0);
				this.setAllowMouseOver(false);
				return;
			}
			int i = 0;
			for (Iterator it = dataSource.iterator(); it.hasNext(); i++) {
				Object oTemp = it.next();
				if (oTemp instanceof Map) {
					Map dataMap = (Map) oTemp;
					TableRow dr = this.addTableRow();
					this.setRowStyleClass(dr, i);
					for (int j = 0; j < this.columnSize(); j++) {
						TableCell cell = dr.addTableCell();
						GridColumn column = this.getGridColumn(j);
						String key = column.getDataField();
						if (!("").equals(key)) {
							Object o = dataMap.get(key);
							if (o == null) {
								cell.text = null;
							} else {
								if (column.getEnumValueMap() != null) {
									int colLength = column.getColValueLength();
									if (colLength > -1) {
										if (o != null) {
											if (o.toString().length() > colLength) {
												o = o.toString().substring(0, colLength);
											}
										}
									}
									o = column.getEnumValueMap().get(o != null ? o.toString() : o);
								}
								String format = this.defaulFormatString;
								if (StringUtil.isNotEmpty(column.getFormatString())) {
									format = column.getFormatString();
								}
								String tempValue = objectToString(o, format);
								//qch 20060619 Ϊ�����ʾ��ʽ����
								if (this.addBlank) {
									cell.text = "&nbsp;" + tempValue;
								} else {
									cell.text = tempValue;
								}
								
							}
							if (cell.text == null) {
								//								if (!dataMap.containsKey(key))
								//								{
								//									throw new ComponentException("����[" + key + "]���ֶο��ܲ����ڣ�");
								//								}
								cell.text = "";
							}
							if (column.isConvertZero()) {
								if (("0").equals(cell.text))
									cell.text = "";
							}
						}
						if (column.isAutoAscend()) {
							cell.setText(String.valueOf(i));
						}
						if (column.isNon_negative()) {
							cell.setText(cell.getText().replace('-', '<'));
						}
						TableCellCreateEvent(cell, i);
					}
				}
			}
		}
	}
	
	protected String objectToString(Object object, String format) {
		if (StringUtil.isBlank(format)) {
			if (object == null)
				return "";
			return String.valueOf(object);
		} else {
			return DateUtil.getFormat(format).format(object);
		}
		
	}
	
	/**
	 * tablecell�������ʱ�����¼�
	 */
	protected void TableCellCreateEvent(TableCell cell, int rowIndex) {
		if (this.getIsToolTip()) {
			if (cell.getText() != null && cell.getText().length() != 0)
				cell.setToolTip(cell.getText());
		}
	}
	
	/**
	 * tableHeadcell�������ʱ�����¼�
	 */
	protected void TableHeadCellCreateEvent(TableCell cell, int rowIndex) {
	}
	
	public boolean isAddBlank() {
		return addBlank;
	}
	
	public void setAddBlank(boolean addBlank) {
		this.addBlank = addBlank;
	}
	
	public int getAllGridWidth() {
		return allGridHeadWidth;
	}
}
