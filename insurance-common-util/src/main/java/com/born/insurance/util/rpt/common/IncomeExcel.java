package com.born.insurance.util.rpt.common;

import jxl.write.*;


public class IncomeExcel {
	 public static final int COLUMN = 0;
	  public static final int LABEL = 1;
	  public static final int NUMBER = 2;
	  public static final int INT = 3;
	  private WritableSheet wk = null;

	  //other fmt
	  private WritableCellFormat fmtTitle = null;
	  private WritableCellFormat fmtTail = null;

	  //column fmt
	  private WritableCellFormat fmtColumn = null;
	  private WritableCellFormat fmtColumn_w = null;

	  //label fmt
	  private WritableCellFormat fmtLabel = null;
	  private WritableCellFormat fmtLabel_w_c = null;
	  private WritableCellFormat fmtLabel_w_r = null;
	  private WritableCellFormat fmtLabel_w_l_b = null;
	  private WritableCellFormat fmtLabel_w_c_b = null;
	  private WritableCellFormat fmtLabel_w_r_b = null;
	  private WritableCellFormat fmtLabel_g_l = null;
	  private WritableCellFormat fmtLabel_g_c = null;
	  private WritableCellFormat fmtLabel_g_r = null;

	  //number fmt
	  private WritableCellFormat fmtNumber = null;
	  private WritableCellFormat fmtNumber_w_c = null;
	  private WritableCellFormat fmtNumber_w_r = null;
	  private WritableCellFormat fmtNumber_w_l_b = null;
	  private WritableCellFormat fmtNumber_w_c_b = null;
	  private WritableCellFormat fmtNumber_w_r_b = null;
	  private WritableCellFormat fmtNumber_g_l = null;
	  private WritableCellFormat fmtNumber_g_c = null;
	  private WritableCellFormat fmtNumber_g_r = null;

	  //int fmt
	  private WritableCellFormat fmtInt = null;
	  private WritableCellFormat fmtInt_w_c = null;
	  private WritableCellFormat fmtInt_w_r = null;
	  private WritableCellFormat fmtInt_w_l_b = null;
	  private WritableCellFormat fmtInt_w_c_b = null;
	  private WritableCellFormat fmtInt_w_r_b = null;
	  private WritableCellFormat fmtInt_g_l = null;
	  private WritableCellFormat fmtInt_g_c = null;
	  private WritableCellFormat fmtInt_g_r = null;

	  /**
	   * <p>Description:Construtor</p>
	   * @param wb Excel工作簿
	   * @param wsName 要在Excel工作簿添加的工作页名称
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	  */
	  public IncomeExcel(WritableWorkbook wb, String wsName)
	              throws WriteException {
	    this.wk = wb.createSheet(wsName, 0);
	    this.defaultFormat();
	  }

	  /**
	   * <p>Description:Construtor</p>
	   * @param wb Excel工作簿
	   * @param wsName 要在Excel工作簿添加的工作页名称
	   * @param num 要指明的工作页编号
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public IncomeExcel(WritableWorkbook wb, String wsName, int num)
	              throws WriteException {
	    this.wk = wb.createSheet(wsName, num);
	    this.defaultFormat();
	  }

	  /**
	   * <p>Description:私有方法：设置缺省格式</p>
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  private void defaultFormat() throws WriteException {
	    WritableFont fmtTitleFont = null;
	    WritableFont fmtThinFont = null;
	    WritableFont fmtBoldFont = null;


	    // Font : Arial, Bold, 14size
	    fmtTitleFont = new WritableFont(WritableFont.ARIAL);
	    fmtTitleFont.setBoldStyle(WritableFont.BOLD);
	    fmtTitleFont.setPointSize(14);


	    // Font: Arial, Bold, 10size
	    fmtBoldFont = new WritableFont(WritableFont.ARIAL);
	    fmtBoldFont.setBoldStyle(WritableFont.BOLD);
	    fmtBoldFont.setPointSize(10);


	    // Font: Arial, 10size
	    fmtThinFont = new WritableFont(WritableFont.ARIAL);
	    fmtThinFont.setPointSize(10);


	    // CellFormat: for Title
	    fmtTitle = new WritableCellFormat(fmtTitleFont);
	    fmtTitle.setAlignment(Alignment.LEFT);
	    fmtTitle.setBackground(Colour.WHITE);
	    fmtTitle.setWrap(true);
	    fmtTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtTitle.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Column
	    fmtColumn = new WritableCellFormat(fmtBoldFont);
	    fmtColumn.setAlignment(Alignment.LEFT);
	    fmtColumn.setBackground(Colour.GRAY_25);
	    fmtColumn.setWrap(true);
	    fmtColumn.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtColumn.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Column
	    fmtColumn_w = new WritableCellFormat(fmtBoldFont);
	    fmtColumn_w.setAlignment(Alignment.LEFT);
	    fmtColumn_w.setBackground(Colour.WHITE);
	    fmtColumn_w.setWrap(true);
	    fmtColumn_w.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtColumn_w.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel = new WritableCellFormat(fmtThinFont);
	    fmtLabel.setAlignment(Alignment.LEFT);
	    fmtLabel.setBackground(Colour.WHITE);
	    fmtLabel.setWrap(true);
	    fmtLabel.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_w_c = new WritableCellFormat(fmtThinFont);
	    fmtLabel_w_c.setAlignment(Alignment.CENTRE);
	    fmtLabel_w_c.setBackground(Colour.WHITE);
	    fmtLabel_w_c.setWrap(true);
	    fmtLabel_w_c.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_w_c.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_w_r = new WritableCellFormat(fmtThinFont);
	    fmtLabel_w_r.setAlignment(Alignment.RIGHT);
	    fmtLabel_w_r.setBackground(Colour.WHITE);
	    fmtLabel_w_r.setWrap(true);
	    fmtLabel_w_r.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_w_r.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_w_l_b = new WritableCellFormat(fmtBoldFont);
	    fmtLabel_w_l_b.setAlignment(Alignment.LEFT);
	    fmtLabel_w_l_b.setBackground(Colour.WHITE);
	    fmtLabel_w_l_b.setWrap(true);
	    fmtLabel_w_l_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_w_l_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_w_c_b = new WritableCellFormat(fmtBoldFont);
	    fmtLabel_w_c_b.setAlignment(Alignment.CENTRE);
	    fmtLabel_w_c_b.setBackground(Colour.WHITE);
	    fmtLabel_w_c_b.setWrap(true);
	    fmtLabel_w_c_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_w_c_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_w_r_b = new WritableCellFormat(fmtBoldFont);
	    fmtLabel_w_r_b.setAlignment(Alignment.RIGHT);
	    fmtLabel_w_r_b.setBackground(Colour.WHITE);
	    fmtLabel_w_r_b.setWrap(true);
	    fmtLabel_w_r_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_w_r_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_g_l = new WritableCellFormat(fmtThinFont);
	    fmtLabel_g_l.setAlignment(Alignment.LEFT);
	    fmtLabel_g_l.setBackground(Colour.GRAY_25);
	    fmtLabel_g_l.setWrap(true);
	    fmtLabel_g_l.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_g_l.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_g_c = new WritableCellFormat(fmtThinFont);
	    fmtLabel_g_c.setAlignment(Alignment.CENTRE);
	    fmtLabel_g_c.setBackground(Colour.GRAY_25);
	    fmtLabel_g_c.setWrap(true);
	    fmtLabel_g_c.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_g_c.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Label
	    fmtLabel_g_r = new WritableCellFormat(fmtThinFont);
	    fmtLabel_g_r.setAlignment(Alignment.RIGHT);
	    fmtLabel_g_r.setBackground(Colour.GRAY_25);
	    fmtLabel_g_r.setWrap(true);
	    fmtLabel_g_r.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtLabel_g_r.setBorder(Border.ALL, BorderLineStyle.THIN);

	    // CellFormat: for Number
	    NumberFormat nft = new NumberFormat("#,##0.00");
	    fmtNumber = new WritableCellFormat(fmtThinFont, nft);
	    fmtNumber.setAlignment(Alignment.RIGHT);
	    fmtNumber.setBackground(Colour.WHITE);
	    fmtNumber.setWrap(true);
	    fmtNumber.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_w_c = new WritableCellFormat(fmtThinFont, nft);
	    fmtNumber_w_c.setAlignment(Alignment.CENTRE);
	    fmtNumber_w_c.setBackground(Colour.WHITE);
	    fmtNumber_w_c.setWrap(true);
	    fmtNumber_w_c.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_w_c.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_w_r = new WritableCellFormat(fmtThinFont, nft);
	    fmtNumber_w_r.setAlignment(Alignment.RIGHT);
	    fmtNumber_w_r.setBackground(Colour.WHITE);
	    fmtNumber_w_r.setWrap(true);
	    fmtNumber_w_r.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_w_r.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_w_l_b = new WritableCellFormat(fmtBoldFont, nft);
	    fmtNumber_w_l_b.setAlignment(Alignment.LEFT);
	    fmtNumber_w_l_b.setBackground(Colour.WHITE);
	    fmtNumber_w_l_b.setWrap(true);
	    fmtNumber_w_l_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_w_l_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_w_c_b = new WritableCellFormat(fmtBoldFont, nft);
	    fmtNumber_w_c_b.setAlignment(Alignment.CENTRE);
	    fmtNumber_w_c_b.setBackground(Colour.WHITE);
	    fmtNumber_w_c_b.setWrap(true);
	    fmtNumber_w_c_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_w_c_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_w_r_b = new WritableCellFormat(fmtBoldFont, nft);
	    fmtNumber_w_r_b.setAlignment(Alignment.RIGHT);
	    fmtNumber_w_r_b.setBackground(Colour.WHITE);
	    fmtNumber_w_r_b.setWrap(true);
	    fmtNumber_w_r_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_w_r_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_g_l = new WritableCellFormat(fmtBoldFont, nft);
	    fmtNumber_g_l.setAlignment(Alignment.LEFT);
	    fmtNumber_g_l.setBackground(Colour.GRAY_25);
	    fmtNumber_g_l.setWrap(true);
	    fmtNumber_g_l.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_g_l.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_g_c = new WritableCellFormat(fmtBoldFont, nft);
	    fmtNumber_g_c.setAlignment(Alignment.CENTRE);
	    fmtNumber_g_c.setBackground(Colour.GRAY_25);
	    fmtNumber_g_c.setWrap(true);
	    fmtNumber_g_c.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_g_c.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Number
	    fmtNumber_g_r = new WritableCellFormat(fmtBoldFont, nft);
	    fmtNumber_g_r.setAlignment(Alignment.RIGHT);
	    fmtNumber_g_r.setBackground(Colour.GRAY_25);
	    fmtNumber_g_r.setWrap(true);
	    fmtNumber_g_r.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtNumber_g_r.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for INT
	    nft = new NumberFormat("#,##0");
	    fmtInt = new WritableCellFormat(fmtThinFont, nft);
	    fmtInt.setAlignment(Alignment.RIGHT);
	    fmtInt.setBackground(Colour.WHITE);
	    fmtInt.setWrap(true);
	    fmtInt.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_w_c = new WritableCellFormat(fmtThinFont, nft);
	    fmtInt_w_c.setAlignment(Alignment.CENTRE);
	    fmtInt_w_c.setBackground(Colour.WHITE);
	    fmtInt_w_c.setWrap(true);
	    fmtInt_w_c.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_w_c.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_w_r = new WritableCellFormat(fmtThinFont, nft);
	    fmtInt_w_r.setAlignment(Alignment.RIGHT);
	    fmtInt_w_r.setBackground(Colour.WHITE);
	    fmtInt_w_r.setWrap(true);
	    fmtInt_w_r.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_w_r.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_w_l_b = new WritableCellFormat(fmtBoldFont, nft);
	    fmtInt_w_l_b.setAlignment(Alignment.LEFT);
	    fmtInt_w_l_b.setBackground(Colour.WHITE);
	    fmtInt_w_l_b.setWrap(true);
	    fmtInt_w_l_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_w_l_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_w_c_b = new WritableCellFormat(fmtBoldFont, nft);
	    fmtInt_w_c_b.setAlignment(Alignment.CENTRE);
	    fmtInt_w_c_b.setBackground(Colour.WHITE);
	    fmtInt_w_c_b.setWrap(true);
	    fmtInt_w_c_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_w_c_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_w_r_b = new WritableCellFormat(fmtBoldFont, nft);
	    fmtInt_w_r_b.setAlignment(Alignment.RIGHT);
	    fmtInt_w_r_b.setBackground(Colour.WHITE);
	    fmtInt_w_r_b.setWrap(true);
	    fmtInt_w_r_b.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_w_r_b.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_g_l = new WritableCellFormat(fmtBoldFont, nft);
	    fmtInt_g_l.setAlignment(Alignment.LEFT);
	    fmtInt_g_l.setBackground(Colour.GRAY_25);
	    fmtInt_g_l.setWrap(true);
	    fmtInt_g_l.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_g_l.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_g_c = new WritableCellFormat(fmtBoldFont, nft);
	    fmtInt_g_c.setAlignment(Alignment.CENTRE);
	    fmtInt_g_c.setBackground(Colour.GRAY_25);
	    fmtInt_g_c.setWrap(true);
	    fmtInt_g_c.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_g_c.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Int
	    fmtInt_g_r = new WritableCellFormat(fmtBoldFont, nft);
	    fmtInt_g_r.setAlignment(Alignment.RIGHT);
	    fmtInt_g_r.setBackground(Colour.GRAY_25);
	    fmtInt_g_r.setWrap(true);
	    fmtInt_g_r.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtInt_g_r.setBorder(Border.ALL, BorderLineStyle.THIN);


	    // CellFormat: for Tail
	    fmtTail = new WritableCellFormat(fmtThinFont);
	    fmtTail.setAlignment(Alignment.RIGHT);
	    fmtTail.setBackground(Colour.WHITE);
	    fmtTail.setWrap(true);
	    fmtTail.setVerticalAlignment(VerticalAlignment.CENTRE);
	    fmtTail.setBorder(Border.ALL, BorderLineStyle.THIN);
	  }

	  /**
	   * <p>Description:写Excel表的标题</p>
	   * @param titleName 标题名
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeTitle(String titleName, int x, int y, int width, int height)
	                  throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      Label lblColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      lblColumn = new Label(x, y, titleName, fmtTitle);
	      wk.addCell(lblColumn);
	    }
	  }

	  /**
	   * <p>Description:写Excel表的列名</p>
	   * @param columnName 列名
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeColumn(String columnName, int x, int y, int width,
	                          int height) throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      Label lblColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      lblColumn = new Label(x, y, columnName, fmtColumn);
	      wk.addCell(lblColumn);
	    }
	  }

	  /**
	   * <p>Description:写Label类型数据</p>
	   * @param labelName 数据内容
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeLabel(String labelName, int x, int y, int width, int height)
	                  throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      Label lblColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      lblColumn = new Label(x, y, labelName, fmtLabel);
	      wk.addCell(lblColumn);
	    }
	  }

	  /**
	   * <p>Description:写Number类型数据</p>
	   * @param numberData 数据内容
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeNumber(double numberData, int x, int y, int width,
	                          int height) throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      jxl.write.Number numColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      numColumn = new jxl.write.Number(x, y, numberData, fmtNumber);
	      wk.addCell(numColumn);
	    }
	  }

	  /**
	   * <p>Description:写Int类型数据</p>
	   * @param intData 数据内容
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeInt(int intData, int x, int y, int width, int height)
	                throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      jxl.write.Number numColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      numColumn = new jxl.write.Number(x, y, intData, fmtInt);
	      wk.addCell(numColumn);
	    }
	  }

	  /**
	   * <p>写Excel表的表尾</p>
	   * @param titleName：表尾名
	   * @param x,y：表尾的起始位置（x,y>=0）
	   * @param width,height：表尾的长和宽（width,height>=1）
	   * @throws WriteException if has a error.
	   */
	  public void writeTail(String tailName, int x, int y, int width, int height)
	                 throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      Label lblColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      lblColumn = new Label(x, y, tailName, fmtTail);
	      wk.addCell(lblColumn);
	    }
	  }

	  /**
	   * <p>Description:设置列宽度</p>
	   * @param num 列位置
	   * @param width 宽度
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void setColumnWidth(int num, int width) throws WriteException {
	    if ((num >= 0) && (width > 0)) {
	      wk.setColumnView(num, width);
	    }
	  }

	  /**
	   * <p>Description:用自定义的格式写Excel Label Cell</p>
	   * @param str Cell的内容
	   * @param wct Cell的自定义格式
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeLabelCell(String str, WritableCellFormat wct, int x, int y,
	                             int width, int height) throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      Label lblColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      lblColumn = new Label(x, y, str, wct);
	      wk.addCell(lblColumn);
	    }
	  }

	  /**
	   * <p>Description:用自定义的格式写Excel Number Cell</p>
	   * @param val Cell的内容
	   * @param wct Cell的自定义格式
	   * @param x 标题的起始位置（x>=0）
	   * @param y 标题的起始位置（y>=0）
	   * @param width  标题的宽度（width>=1）
	   * @param height 标题的高度（height>=1）
	   * @throws WriteException if has a error.
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public void writeNumberCell(double val, WritableCellFormat wct, int x, int y,
	                              int width, int height) throws WriteException {
	    if ((x >= 0) && (y >= 0) && (width > 0) && (height > 0)) {
	      jxl.write.Number numColumn = null;

	      if ((width != 1) || (height != 1)) {
	        wk.mergeCells(x, y, (x + width) - 1, (y + height) - 1);
	      }

	      numColumn = new jxl.write.Number(x, y, val, wct);
	      wk.addCell(numColumn);
	    }
	  }

	  public WritableCellFormat getCellFormat(int kind, String bgColour,
	                                          boolean isBold, String align) {
	    WritableCellFormat fmtCell = null;

	    switch (kind) {
	    case COLUMN:

	      if ("#FFFFFF".equals(bgColour)) {
	        fmtCell = fmtColumn_w;
	      } else if ("#CCCCCC".equals(bgColour)) {
	        fmtCell = fmtColumn;
	      } else {
	        fmtCell = createCellFormat(COLUMN, bgColour);
	      }

	      break;

	    case LABEL:

	      if ("#FFFFFF".equals(bgColour)) {
	        if ("center".equals(align)) {
	          fmtCell = isBold ? fmtLabel_w_c_b : fmtLabel_w_c;
	        } else if ("right".equals(align)) {
	          fmtCell = isBold ? fmtLabel_w_r_b : fmtLabel_w_r;
	        } else {
	          fmtCell = isBold ? this.fmtLabel_w_l_b : fmtLabel;
	        }
	      } else if ("#CCCCCC".equals(bgColour)) {
	        if ("center".equals(align)) {
	          fmtCell = fmtLabel_g_c;
	        } else if ("right".equals(align)) {
	          fmtCell = fmtLabel_g_r;
	        } else {
	          fmtCell = fmtLabel_g_l;
	        }
	      } else {
	        fmtCell = createCellFormat(LABEL, bgColour);
	      }

	      break;

	    case NUMBER:

	      if ("#FFFFFF".equals(bgColour)) {
	        if ("center".equals(align)) {
	          fmtCell = isBold ? fmtNumber_w_c_b : fmtNumber_w_c;
	        } else if ("right".equals(align)) {
	          fmtCell = isBold ? fmtNumber_w_r_b : fmtNumber_w_r;
	        } else {
	          fmtCell = isBold ? fmtNumber_w_l_b : fmtNumber;
	        }
	      } else if ("#CCCCCC".equals(bgColour)) {
	        if ("center".equals(align)) {
	          fmtCell = fmtNumber_g_c;
	        } else if ("right".equals(align)) {
	          fmtCell = fmtNumber_g_r;
	        } else {
	          fmtCell = fmtNumber_g_l;
	        }
	      } else {
	        fmtCell = createCellFormat(NUMBER, bgColour);
	      }

	      break;

	    case INT:

	      if ("#FFFFFF".equals(bgColour)) {
	        if ("center".equals(align)) {
	          fmtCell = isBold ? fmtInt_w_c_b : fmtInt_w_c;
	        } else if ("right".equals(align)) {
	          fmtCell = isBold ? fmtInt_w_r_b : fmtInt_w_r;
	        } else {
	          fmtCell = isBold ? this.fmtInt_w_l_b : fmtInt;
	        }
	      } else if ("#CCCCCC".equals(bgColour)) {
	        if ("center".equals(align)) {
	          fmtCell = fmtInt_g_c;
	        } else if ("right".equals(align)) {
	          fmtCell = fmtInt_g_r;
	        } else {
	          fmtCell = fmtInt_g_l;
	        }
	      } else {
	        fmtCell = createCellFormat(INT, bgColour);
	      }

	      break;

	    default:
	      break;
	    }

	    return fmtCell;
	  }

	  /**
	   * <p>Description:创建Cell格式</p>
	   * @param kind Cell的类别
	   * @param bgColour Cell的背景色
	   * @throws WriteException if has a error.
	   * @return WritableCellFormat
	   * <p>Create Time: 2014-08-19</p>
	   */
	  private WritableCellFormat createCellFormat(int kind, String bgColour) {
	    WritableCellFormat fmtCell = null;
	    WritableFont fmtCellFont = null;
	    NumberFormat nft = null;

	    try {
	      switch (kind) {
	      case COLUMN:

	        // Font: Arial, Bold, 10size; CellFormat: for Column
	        fmtCellFont = new WritableFont(WritableFont.ARIAL);
	        fmtCellFont.setBoldStyle(WritableFont.BOLD);
	        fmtCellFont.setPointSize(10);
	        fmtCell = new WritableCellFormat(fmtCellFont);
	        fmtCell.setAlignment(Alignment.LEFT);
	        fmtCell.setBackground(toExcelColor(bgColour));
	        fmtCell.setWrap(true);
	        fmtCell.setVerticalAlignment(VerticalAlignment.CENTRE);
	        fmtCell.setBorder(Border.ALL, BorderLineStyle.THIN);

	        break;

	      case LABEL:

	        // Font: Arial, 10size; CellFormat: for Label
	        fmtCellFont = new WritableFont(WritableFont.ARIAL);
	        fmtCellFont.setPointSize(10);
	        fmtCell = new WritableCellFormat(fmtCellFont);
	        fmtCell.setAlignment(Alignment.LEFT);
	        fmtCell.setBackground(toExcelColor(bgColour));
	        fmtCell.setWrap(true);
	        fmtCell.setVerticalAlignment(VerticalAlignment.CENTRE);
	        fmtCell.setBorder(Border.ALL, BorderLineStyle.THIN);

	        break;

	      case NUMBER:

	        // Font: Arial, 10size; CellFormat: for Number
	        fmtCellFont = new WritableFont(WritableFont.ARIAL);
	        fmtCellFont.setPointSize(10);
	        nft = new NumberFormat("#,##0.00");
	        fmtCell = new WritableCellFormat(fmtCellFont, nft);
	        fmtCell.setAlignment(Alignment.RIGHT);
	        fmtCell.setBackground(toExcelColor(bgColour));
	        fmtCell.setWrap(true);
	        fmtCell.setVerticalAlignment(VerticalAlignment.CENTRE);
	        fmtCell.setBorder(Border.ALL, BorderLineStyle.THIN);

	        break;

	      case INT:

	        // Font: Arial, 10size; CellFormat: for INT
	        fmtCellFont = new WritableFont(WritableFont.ARIAL);
	        fmtCellFont.setPointSize(10);
	        nft = new NumberFormat("#");
	        fmtCell = new WritableCellFormat(fmtCellFont, nft);
	        fmtCell.setAlignment(Alignment.RIGHT);
	        fmtCell.setBackground(toExcelColor(bgColour));
	        fmtCell.setWrap(true);
	        fmtCell.setVerticalAlignment(VerticalAlignment.CENTRE);
	        fmtCell.setBorder(Border.ALL, BorderLineStyle.THIN);

	        break;

	      default:

	        // Font: Arial, 10size;  CellFormat: for other
	        fmtCellFont = new WritableFont(WritableFont.ARIAL);
	        fmtCellFont.setPointSize(10);
	        fmtCell = new WritableCellFormat(fmtCellFont);
	        fmtCell.setAlignment(Alignment.LEFT);
	        fmtCell.setBackground(toExcelColor(bgColour));
	        fmtCell.setWrap(true);
	        fmtCell.setVerticalAlignment(VerticalAlignment.CENTRE);
	        fmtCell.setBorder(Border.ALL, BorderLineStyle.THIN);

	        break;
	      }
	    } catch (WriteException ex) {
	      ex.printStackTrace();
	    }

	    return fmtCell;
	  }

	  /**
	   * <p>Description:获得WorkSheet</p>
	   * @return WritableSheet: WorkSheet
	   * <p>Create Time: 2014-08-19</p>
	   */
	  public WritableSheet getWorkSheet() {
	    return this.wk;
	  }

	  /**
	   * <p>Description:进行颜色转换</p>
	   * @param color "#XXXXXX"
	   * @return Colour "#XXXXXX"对应的EXCEL背景色
	   * <p>Create Time: 2014-08-19</p>
	   */
	  private jxl.format.Colour toExcelColor(String color) {
	    jxl.format.Colour excelColor = Colour.GRAY_25;

	    if ("#FFFFFF".equals(color)) {
	      excelColor = Colour.WHITE;
	    } else if ("#CCCCCC".equals(color)) {
	      excelColor = Colour.GRAY_25;
	    } else if ("#999999".equals(color)) {
	      excelColor = Colour.GRAY_50;
	    } else if ("#666666".equals(color)) {
	      excelColor = Colour.GRAY_80;
	    } else if ("#000000".equals(color)) {
	      excelColor = Colour.BLACK;
	    } else if ("#FF0000".equals(color)) {
	      excelColor = Colour.RED;
	    } else if ("#00FF00".equals(color)) {
	      excelColor = Colour.GREEN;
	    } else if ("#0000FF".equals(color)) {
	      excelColor = Colour.BLUE;
	    } else if ("#FFFF00".equals(color)) {
	      excelColor = Colour.YELLOW;
	    } else if ("#00FFFF".equals(color)) {
	      excelColor = Colour.LIME;
	    } else if ("#FF00FF".equals(color)) {
	      excelColor = Colour.PINK;
	    }

	    return excelColor;
	  }
}
