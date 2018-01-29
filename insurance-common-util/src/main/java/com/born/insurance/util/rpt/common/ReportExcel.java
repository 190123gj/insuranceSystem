package com.born.insurance.util.rpt.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.jdom.Document;
import org.jdom.Element;

public class ReportExcel extends Report {

	
	
	 private WritableWorkbook wb = null;

	  /**
	   * <p>Description: ReportExcel构造函数</p>
	   * @param out OutputStream
	   * @throws IOException
	   * <p>Create Time: 2014-08-27</p>
	   */
	  public ReportExcel(OutputStream out) throws IOException {
	    this.wb = Workbook.createWorkbook(out);
	  }

	  /**
	   * <p>Description: 输出Excel报表</p>
	   * @param reportData Document 数据xml
	   * @param reportStyle Document 数据格式xml
	   * @throws GenericException
	   * <p>Create Time: 2014-08-27</p>
	   */
	  @Override
	  public void print(Document reportData, Document reportStyle)
	             throws Exception {
	    try {
	      Element root = reportStyle.getRootElement();
	      Element rh = root.getChild("Report-Header");
	      IncomeExcel excel = new IncomeExcel(wb,
	                                          rh.getAttributeValue("Report-Name"));

	      List tables = root.getChild("Report-Body").getChildren("Table");
	      int cl_num = 0;
	      int rw_num_tmp = 0;

	      for (int i = 0; i < tables.size(); i++) {
	        Element th = ((Element) tables.get(i)).getChild("Table-Header");
	        List clRows = th.getChildren("Table-Row");
	        //set columns' width
	        for (int j = 0; j < clRows.size(); j++) {
	          List clNames = ((Element) clRows.get(j)).getChildren("Table-Cell");
	          cl_num = 0;

	          for (int k = 0; k < clNames.size(); k++) {
	            String w = ((Element) clNames.get(k)).getAttributeValue(
	                           "Column-Span");
	            w = (w != null) ? w : "1";

	            if ("1".equals(w)) {
	              String tmp = ((Element) clNames.get(k)).getAttributeValue("Width");

	              tmp = (tmp != null) ? tmp : "15";

	              String c_width = tmp.endsWith("cm")
	                               ? tmp.substring(0, tmp.length() - 2) : tmp;
	              excel.setColumnWidth(cl_num, Integer.parseInt(c_width));
	            }

	            cl_num += Integer.parseInt(w);
	          }
	        }


	        //writing title & columns
	        excel.writeTitle(rh.getAttributeValue("Report-Name"), 0, 0, cl_num, 1);

	        Element dataRoot = reportData.getRootElement();
	        ReportHead dh = new ReportHead();
	        dh.setHead(dataRoot.getChild("Head"));
	        String filters=dh.getFilters();
	        excel.writeTail(filters+"   查询日期："+dh.getDate(), 0, 1, cl_num, 1);

	        int rw_num = 0;
	        int rw_ext = 0;

	        for (int j = 0; j < clRows.size(); j++) {
	          List clNames = ((Element) clRows.get(j)).getChildren("Table-Cell");
	          cl_num = 0;
	          rw_num = 2 + j;

	          for (int k = 0; k < clNames.size(); k++) {
	            Element column = (Element) clNames.get(k);
	            String name = column.getAttributeValue("Title");
	            String w = column.getAttributeValue("Column-Span");
	            String h = column.getAttributeValue("Row-Span");
	            String cColor = column.getAttributeValue("BgColor");
	            cColor = (cColor != null) ? cColor : "#CCCCCC";

	            if (name == null) {
	              name = " ";
	            }

	            if (w == null) {
	              w = "1";
	            }

	            if (h == null) {
	              h = "1";
	            }

	            rw_ext = "1".equals(h) ? 1 : Integer.parseInt(h);

	            if (!"".equals(name)) {
	              excel.writeLabelCell(name,
	                                   excel.getCellFormat(excel.COLUMN, cColor,
	                                                       true, "center"), cl_num,
	                                   rw_num, Integer.parseInt(w),
	                                   Integer.parseInt(h));
	            }

	            cl_num += Integer.parseInt(w);
	          }
	        }

	        rw_num_tmp = rw_num + rw_ext;
	        rw_ext = 0;

	        //writing data
	        Element dataTable = dataRoot.getChild("Table"); //!!!!just a data table?
	        List data = dataTable.getChildren("Row");

	        Element tb = ((Element) tables.get(i)).getChild("Table-Body");
	        List tabRows = tb.getChildren("Table-Row");
	        boolean empty_row = true;
	        boolean isFindStyle = false;
	        for (int j = 0; j < data.size(); j++) { //data rows
	          cl_num = 0;
	          rw_num += (empty_row ? 0 : 1);
	          empty_row = true;
	          isFindStyle = false;

	          for (int k = 0; k < tabRows.size(); k++) { //style rows

	            List dataStyle = ((Element) tabRows.get(k)).getChildren(
	                                 "Table-Cell");
	            rw_num = rw_num_tmp;
	            for (int m = 0; m < dataStyle.size(); m++) {
	              Element cellStyle = (Element) dataStyle.get(m);
	              String df = cellStyle.getAttributeValue("Data-Field");
	              String type = cellStyle.getAttributeValue("Data-Type");
	              String align = cellStyle.getAttributeValue("Align");


	              String bold = cellStyle.getAttributeValue("isBold");
	              boolean isBold = ("true".equals(bold)) ? true : false;
	              String cw = cellStyle.getAttributeValue("Column-Span");
	              String ch = cellStyle.getAttributeValue("Row-Span");
	              String cellColor = cellStyle.getAttributeValue("BgColor");
	              cellColor = (cellColor != null) ? cellColor : "#FFFFFF";

	              if (type == null) {
	                type = "STRING";
	              }

	              if (cw == null) {
	                cw = "1";
	              }

	              if (ch == null) {
	                ch = "1";
	              }

	              rw_ext = "1".equals(ch) ? 1 : Integer.parseInt(ch);

	              String dataCell = ((Element) data.get(j)).getChildTextTrim(df);

	              if (dataCell != null) {
	                empty_row = false;
	                isFindStyle = true;

	                if ("INTEGER".equals(type)) {
	                  dataCell = "null".equals(dataCell) ? "0" : dataCell;
	                  align = (align != null) ? align : "right";
	                  excel.writeNumberCell(Double.valueOf(dataCell).intValue(),
	                                        excel.getCellFormat(excel.INT,
	                                                            cellColor, isBold,
	                                                            align), cl_num,
	                                        rw_num, Integer.parseInt(cw),
	                                        Integer.parseInt(ch));
	                } else if ("FLOAT".equals(type)) {
	                  dataCell = "null".equals(dataCell) ? "0.00" : dataCell;
	                  align = (align != null) ? align : "right";
	                  excel.writeNumberCell(Double.parseDouble(dataCell),
	                                        excel.getCellFormat(excel.NUMBER,
	                                                            cellColor, isBold,
	                                                            align), cl_num,
	                                        rw_num, Integer.parseInt(cw),
	                                        Integer.parseInt(ch));
	                } else {
	                  dataCell = "null".equals(dataCell) ? " " : dataCell;
	                  align = (align != null) ? align : "left";
	                  excel.writeLabelCell(dataCell,
	                                       excel.getCellFormat(excel.LABEL,
	                                                           cellColor, isBold,
	                                                           align), cl_num,
	                                       rw_num, Integer.parseInt(cw),
	                                       Integer.parseInt(ch));
	                }

	                cl_num += Integer.parseInt(cw);
	              } else {
	                break;
	              }
	            }

	            if (isFindStyle) {
	              break;
	            }
	          }

	          rw_num_tmp = rw_num + (empty_row ? 0 : rw_ext);
	          rw_ext = 0;
	        }
	      }


	      //printing excel
	      wb.write();
	      wb.close();
	    } catch (Exception ex) {
	      throw ex;
	    }
	  }

}
