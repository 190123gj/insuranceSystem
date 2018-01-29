package com.born.insurance.webui.control;

import java.util.Map;

/**
 * <p>Title: GridColumn</p>
 * <p>Description: GridColumn</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author qch
 * @version 1.0
 */

public class GridColumn extends TableColumn {
  
  private String formatString="";
  
  private int colValueLength=-1;
  
  //�м�����ʽ
  private String expression="";

  //�а��ֶ�
  private String dataField="";

  //�Ƿ�����Ϊ��
  private boolean allowNull=true;

  //�ͻ�������
  private boolean clientVisible=false;
  //����������ֵ���ֶ�
  private String hiddenValueDataField="";
  //�Ƿ��кϼ�
  private boolean isColumnSum=false;

  //���Ƿ����ת��Ϊ""
  private boolean  convertZero=false;
  /**
   * ���������Ƿ���ո������
   */
  private boolean inceptData=true;
 // ListGrid parentGrid=null;
 /**
  * �ͻ�������
  */
 private boolean autoAscend=false;
 /**
  * �Ƿ�̶����
  */
 private boolean isFixationWidth=false;
 
 /**
  * ö��ֵ�趨
  *
  */
 private  Map enumValueMap=null;
 
 /**
  * �Ǹ�����
  *
  */
 private  boolean non_negative=false;
 
	public boolean isNon_negative()
	{
		return non_negative;
	}
	public void setNon_negative(boolean non_negative)
	{
		this.non_negative = non_negative;
	}
	  public GridColumn() {
	    super("");
	  }
	  public GridColumn(String id) {
	    super(id);
	  }
  /**
   * �����б���ʽ
   * ��֧�ֱ��ʽ��������[]�����������������÷ֺŸ�����
   *����������ǰ[3]=[2]*[1]������Ϊ�еİ��ֶΡ�
   *���ʽ���ڣ���ǰ�������ϣ���ʾ��ǰ��ֵ�ı�ʱ������ʽ����
   *setColumnExpression("[3]=[2]*[1];[4]=[2]*[1];[5]=[3]/[4]");

  */
  public void setColumnExpression(String expression)
  {
    this.expression=expression;
  }
  /**
   * �õ��б��ʽ
   */
  public String getColumnExpression()
  {
    return this.expression;
  }

  /**
 * ���ð��ֶ�
 */
  public void setDataField(String field)
  {
    this.dataField=field;
  }
  /**
   * �õ����ֶ�
   */
  public String getDataField()
  {
    return this.dataField;
  }

  /**
 * �õ�������ֵ�ֶ�
 */
  public String getHiddenValueDataField()
  {
    return this.hiddenValueDataField;
  }
  /**
   * ���ð�����ֵ�ֶ�
   */
  public void setHiddenValueDataField(String dataField)
  {
    this.hiddenValueDataField=dataField;
  }

  /**
   * �õ����Ƿ�����Ϊ��
   */
  public boolean getAllowNull()
  {
    return this.allowNull;
  }
  /**
   * �������Ƿ�����Ϊ��
   */
  public void setAllowNull(boolean isNull)
  {
     this.allowNull=isNull;
  }
  /**
   * �������Ƿ�ϼ�
   */
  public void setIsColumnSum(boolean isSum)
  {
    this.isColumnSum=isSum;
  }
  /**
   * ��ȡ���Ƿ�ϼ����Ƿ�ϼ�
   */
  public boolean getIsColumnSum()
  {
    return this.isColumnSum;
  }
  /**
   * ��ȡ���Ƿ���ʾ��
   */
  public boolean isConvertZero()
  {
    return this.convertZero;
  }
  /**
   * �������Ƿ���ʾ��
   */
  public void setConvertZero(boolean convertZero)
  {
    this.convertZero=convertZero;
  }
  /**
   * �ͻ����Ƿ�����
   * @return
   */
  public boolean isAutoAscend()
  {
    return this.autoAscend;
  }
  /**
  * ���ÿͻ�������
  */
  public void setAutoAscend(boolean newAutoAscend)
  {
     this.autoAscend=newAutoAscend;
  }
  /**
   * ���ø����Ƿ���������������
   * @param newIsSendData
   */
  public void setIsInceptData(boolean newIsInceptData)
{
   this.inceptData=newIsInceptData;
}
 public boolean IsInceptData()
 {
   return this.inceptData;
 }



  /*
 * �����пͻ�������
 */
public void setVisible(boolean visible)
 {
  super.setVisible(visible);
  // if(parentGrid.isAfterBind==true)
 // {
//      for(int i=0;i<parentGrid.rowSize();i++)
//      {
//          parentGrid.getRow(i).visible=visible;
//          parentGrid
//      }
 // }
 }
  /*
   * �õ��пͻ����Ƿ����ص�����
   */
//  public boolean getClientVisible()
 // {
//    return this.clientVisible;
 // }
public boolean isFixationWidth() {
	return isFixationWidth;
}
public void setFixationWidth(boolean isFixationWidth) {
	this.isFixationWidth = isFixationWidth;
}
/**
 * ö��ֵ����Map,���ʵ�����"1",��ʾ��ͬ�⣬"2"��ʾͬ����ʾΪ ��ͬ�⣬ͬ��
 * ���� Map item=new HashMap();
 * item.put("1","��ͬ��")��
 * item.put("2","ͬ��")��
 * setEnumValueMap��item����
 * @return
 */
public Map getEnumValueMap() {
	return enumValueMap;
}
public void setEnumValueMap(Map enumValueMap) {
	this.enumValueMap = enumValueMap;
}
public String getFormatString() {
	return formatString;
}
public void setFormatString(String formatString) {
	this.formatString = formatString;
}
public int getColValueLength() {
	return colValueLength;
}
public void setColValueLength(int colValueLength) {
	this.colValueLength = colValueLength;
}
}