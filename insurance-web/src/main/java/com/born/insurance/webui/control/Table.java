package com.born.insurance.webui.control;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: Table</p>
 * <p>Description: Table</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author qch
 * @version 1.0
 */

public class Table extends AbstractComponent {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static final String BORDER_ATTRIBUTE  = "border";
  static final String SPACING_ATTRIBUTE = "cellspacing";
  static final String PADDING_ATTRIBUTE = "cellpadding";
  protected List tableColumns=new ArrayList();
  protected List tableRows=new ArrayList();
  private boolean isToolTip=false;
  private boolean noWrap=false;
  public Table(String id)
  {
     super(id);
     this.setElementType("table");
  }
  public Table(String id,int columnSize)
  {
    this(id);
    for(int i=0;i<columnSize;i++)
    {
      TableColumn newColumn=this.getNewColumn();
      tableColumns.add(newColumn);
    }
  }
  public Table(String id,int columnSize,int rowSize)
  {
    this(id);
    for(int i=0;i<columnSize;i++)
    {
      TableColumn newColumn=this.getNewColumn();
      tableColumns.add(newColumn);
    }
    for(int i=0;i<rowSize;i++)
    {
      TableRow newRow=getNewRow(i);
      newRow.rowIndex=i;
      tableRows.add(newRow);
    }
  }
  protected String getElementHtml()
  {
    if(this.isVisible())
    {
      StringBuffer html = new StringBuffer(25500);
      if(this.isNoWrap())
      {
          this.sourceAttributes.put("noWrap","true");
      }
      html.append("<" + this.getElementType() + super.getElementHtml()+">");
      html.append(this.getText());
      for (int i = 0; i < this.rowSize(); i++){
       html.append("\r\n"+this.getRow(i).getInnerHtml());
      }
     html.append("\r\n"+this.assembleEndTag());
     return html.toString();
    }
    else
    {
      return "";
    }
  }
  /**
   * set CellPadding
   * @param newCellPadding
   */
  public void setCellPadding( int newCellPadding ) {
    this.sourceAttributes.put(PADDING_ATTRIBUTE, String.valueOf(newCellPadding));
  }
  /**
   * get CellPadding
   * @return
   */
  public int getCellPadding() {
    return Integer.parseInt((String)this.sourceAttributes.get(PADDING_ATTRIBUTE));
  }

  /**
  * set CellSpacing
  */
  public void setCellSpacing( int newCellSpacing ) {
    this.sourceAttributes.put(SPACING_ATTRIBUTE, String.valueOf(newCellSpacing));
  }

  /**
   * get CellSpacing
   * @return
   */
  public int getCellSpacing() {
    return Integer.parseInt((String)this.sourceAttributes.get(SPACING_ATTRIBUTE));
  }
  /**
   * get Border
   * @return
   */
  public int getBorder()
  {
    return Integer.parseInt((String)this.sourceAttributes.get(BORDER_ATTRIBUTE));
  }
  /**
  * set Border
  */
  public void setBorder(int newBorder)
  {
     this.sourceAttributes.put(BORDER_ATTRIBUTE, String.valueOf(newBorder));
  }
  /**
   * get Column
   * @return
   */
  public TableColumn getColumn(int index)
  {
    if(index<this.columnSize())
    {
      return (TableColumn) tableColumns.get(index);
    }
    else
    {
      return null;
    }
  }
  /**
  * get Row
  * @return
  */
  public TableRow getRow(int index)
  {
    if(index<this.rowSize())
    {
      return (TableRow) tableRows.get(index);
    }
    else
    {
      return null;
    }
  }
  public TableCell getCell(int rowIndex,int columnIndex)
  {
      try
      {
         return this.getRow(rowIndex).getTableCell(columnIndex);
      }
      catch(Exception e)
      {
         return null;
      }
  }


  /**
   * columnSize
   * @return
   */
  public int columnSize()
  {
    return tableColumns.size();
  }
  /**
   * rowSize
   * @return
   */
  public int rowSize()
  {
    return tableRows.size();
  }
  /**
   * add Table Row
   * @return
   */
  public TableRow addTableRow()
  {	 
	TableRow  newTableRow=this.getNewRow(this.rowSize());
    tableRows.add(newTableRow);
    return newTableRow;
  }
  /**
   * insert Table Row
   * @return
   */
  public TableRow insertRow(int index)
  {
    TableRow newTableRow=this.getNewRow(index);
    tableRows.add(index,newTableRow);
    refreshTableRow(this);
    return newTableRow;
  }
  /**
   * add Table Column
   * @return
   */
  public TableColumn addTableColumn()
  {
    TableColumn  newTableColumn=this.getNewColumn();
    this.tableColumns.add(newTableColumn);
    return newTableColumn;
  }
  /**
  * insert Table Column
  * @return
  */
  public TableColumn insertTableColumn(int index)
  {
    TableColumn  newTableColumn=this.getNewColumn();
    this.tableColumns.add(index,newTableColumn);
    return newTableColumn;
  }

  /**
    * remove Row
    */
  public void removeRow(int index)
  {
    this.tableRows.remove(index);
    refreshTableRow(this);
  }
  /**
    * remove Row
    * @return
    */
  public void removeColumn(int index)
  {
    this.tableColumns.remove(index);
  }
  /**
   * column Index
   * @param column
   * @return
   */
  public int columnIndexOf(TableColumn column)
  {
     return this.tableColumns.indexOf(column);
  }
  /**
  * row IndexOf
  * @param column
  * @return
  */
  public int rowIndexOf(TableRow row)
  {
     return this.tableRows.indexOf(row);
  }
  /**
   * set Default Column Width
   * @param width
   */
  public void setDefaultColumnWidth(String width)
  {
    for(int i=0;i<this.columnSize();i++)
    {
      this.getColumn(i).setWidth(width);
    }
  }
  /**
   * �����Ƿ񲻻���
   * @param wrap
   */
  public void setNoWrap(boolean noWrap)
  {
      this.noWrap=noWrap;
  }
  /**
   * ��ȡ�Ƿ񲻻���
   * @return
   */
  public boolean isNoWrap()
  {
      return this.noWrap;
  }
  /**
   * ��ȡ�Ƿ���ʹ��ToolTip
   * @return
   */
  public boolean getIsToolTip()
  {
     return this.isToolTip;
  }
  /**
   * �����Ƿ���ʹ��ToolTip
   * @param isToolTip
   */
  public void setIsToolTip(boolean isToolTip)
  {
    this.isToolTip=isToolTip;
  }
  protected TableRow getNewRow(int index)
 {
   TableRow row = new TableRow(this.getComponentId() + "_Row_");
   row.setOutID(this.isOutID);
   row.parentTable = this;   
   row.rowIndex = index;   
   if(this.columnSize()>0)
   {
       for(int i=0;i<this.columnSize();i++)
       {
           row.addTableCell();
       }
   }
   return row;
 }
 /**
  * get New Column
  * @return
  */
 protected TableColumn getNewColumn() {
   return new TableColumn(this.getComponentId() + "_Column");
 }

 private static void refreshTableRow(Table myTable) {
   for (int i = 0; i < myTable.rowSize(); i++) {
    myTable.getRow(i).rowIndex = i;
   }
 }

}
