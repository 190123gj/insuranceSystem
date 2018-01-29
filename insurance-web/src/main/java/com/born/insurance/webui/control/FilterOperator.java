/*
 * �������� 2003-12-4
 * Copyright (c) 2003 Gold Abacus software co. - www.bornsoft.com.cn
 *
 */
package com.born.insurance.webui.control;

import java.util.*;
//import org.ofbiz.core.entity.*;
//import org.ofbiz.core.entity.model.*;
//import org.ofbiz.core.util.*;
//import com.bornsoft.core.virtualEntity.VirtualEntityHelper;
/**
 * @author liujp
 * �����б�ɸѡ����������
 */

public class FilterOperator {

    private static final String ID_NULL = "0";                     //��ֵ
    private static final String ID_ZERO_OR_NULL = "1";             //����ֵ

    private static final String ID_EQUALS = "2";                   //����
    private static final String ID_GREATER_THAN = "3";             //����
    private static final String ID_LESS_THAN = "4";                //С��
    private static final String ID_NOT_EQUAL = "5";                //������
    private static final String ID_GREATER_THAN_EQUAL_TO = "6";    //���ڵ���
    private static final String ID_LESS_THAN_EQUAL_TO = "7";       //С�ڵ���

    private static final String ID_BETWEEN = "8";                  //����
    private static final String ID_LEFT_LIKE = "9";                //��ͷ�ַ�Ϊ
    private static final String ID_LIKE_CONTAIN = "10";            //���ַ�Ϊ
    private static final String ID_LIKE = "11";                    //������

    private static final String ID_YES = "12";                     //��
    private static final String ID_NO = "13";                      //��

    private static final String ID_TODAY = "14";                   //����
    private static final String ID_CURRENT_WEEK = "15";            //����
    private static final String ID_CURRENT_WEEK_TO_TODAY = "16";   //����������
    private static final String ID_CURRENT_PERIOD = "17";          //����
    private static final String ID_CURRENT_PERIOD_TO_TODAY = "18"; //����������

    private static final String ID_CURRENT_MONTH = "19";           //����
    private static final String ID_CURRENT_MONTH_FIRST = "20";     //������Ѯ
    private static final String ID_CURRENT_MONTH_MIDDLE = "21";    //������Ѯ
    private static final String ID_CURRENT_MONTH_LAST = "22";      //������Ѯ
    private static final String ID_CURRENT_MONTH_TO_TODAY = "23";  //����������

    private static final String ID_CURRENT_QUARTER = "24";         //������
    private static final String ID_CURRENT_QUARTER_TO_TODAY = "25";//����������

    private static final String ID_CURRENT_YEAR = "26";            //����
    private static final String ID_CURRENT_YEAR_TO_TODAY = "27";   //����������

    private static final String ID_PREVIOUS_WEEK = "28";           //����
    private static final String ID_PREVIOUS_PERIOD = "29";         //����
    private static final String ID_PREVIOUS_MONTH = "30";          //����
    private static final String ID_PREVIOUS_QUARTER = "31";        //�ϼ���
    private static final String ID_LAST_YEAR = "32";               //ȥ��

    private static final String ID_LAST_YEAR_BEGIN_TO_LAST_YEAR_TODAY = "33";//ȥ�������ȥ�����
	private static final String ID_CUSTOM = "34";                  //�Զ���

    private static final String ID_RELATION = "35";                //ѡ����Ŀ

    private Map opMap = new HashMap();
    private static FilterOperator operator = null;

    protected FilterOperator() {
        opMap.put(ID_NULL, "null");
        opMap.put(ID_ZERO_OR_NULL, "zeroOrNull");

        opMap.put(ID_EQUALS, "=");
        opMap.put(ID_GREATER_THAN, ">");
        opMap.put(ID_LESS_THAN, "<");
        opMap.put(ID_NOT_EQUAL, "<>");
        opMap.put(ID_GREATER_THAN_EQUAL_TO, ">=");
        opMap.put(ID_LESS_THAN_EQUAL_TO, "<=");

        opMap.put(ID_BETWEEN, "between");
        opMap.put(ID_LEFT_LIKE, "leftLike");
        opMap.put(ID_LIKE_CONTAIN, "likeContain");
        opMap.put(ID_LIKE, "like");
        opMap.put(ID_YES, "Y");
        opMap.put(ID_NO, "N");
        opMap.put(ID_TODAY, "today");

        opMap.put(ID_CURRENT_WEEK, "currentWeek");
        opMap.put(ID_CURRENT_WEEK_TO_TODAY, "currentWeekToToday");
        opMap.put(ID_CURRENT_PERIOD, "currentPeriod");
        opMap.put(ID_CURRENT_PERIOD_TO_TODAY, "currentPeriodToToday");

        opMap.put(ID_CURRENT_MONTH, "currentMonth");
        opMap.put(ID_CURRENT_MONTH_FIRST, "currentMonthFirst");
        opMap.put(ID_CURRENT_MONTH_MIDDLE, "currentMonthMiddle");
        opMap.put(ID_CURRENT_MONTH_LAST, "currentMonthLast");
        opMap.put(ID_CURRENT_MONTH_TO_TODAY, "currentMonthToToday");

        opMap.put(ID_CURRENT_QUARTER, "currentQuarter");
        opMap.put(ID_CURRENT_QUARTER_TO_TODAY, "currentQuarterToToday");
        opMap.put(ID_CURRENT_YEAR, "currentYear");
        opMap.put(ID_CURRENT_YEAR_TO_TODAY, "currentYearToToday");

        opMap.put(ID_PREVIOUS_WEEK, "previousWeek");
        opMap.put(ID_PREVIOUS_PERIOD, "previousPeriod");
        opMap.put(ID_PREVIOUS_MONTH, "previousMonth");
        opMap.put(ID_PREVIOUS_QUARTER, "previousQuarter");

        opMap.put(ID_LAST_YEAR, "lastYear");
        opMap.put(ID_CUSTOM, "custom");
        opMap.put(ID_LAST_YEAR_BEGIN_TO_LAST_YEAR_TODAY, "lastYearBeginToLastYearToday");
        opMap.put(ID_RELATION, "relation");
    }

    /**
     * ��� id ��ȡ�������������
     * @param id
     * @return
     */
    public static String getName(String id){
        if (id == null || id.trim().length() == 0) return null;

        if (operator == null){
            operator = new FilterOperator();
        }
        String result=null;
        try{
			result=operator.opMap.get(id).toString();
        }
        catch(Exception e)
        {
        	
        }
        return result;
    }

    /**
    * ��ݲ�������ƻ�ȡ id
    * @param name
    * @return
    */
    public static String getId(String name) {
        if (name == null) return null;

        if (operator == null){
            operator = new FilterOperator();
        }

        Set set = operator.opMap.keySet();
        Iterator it = set.iterator();
        Object obj;
        while (it.hasNext()){
            obj = it.next();
            if (operator.opMap.get(obj).equals(name)){
                return obj.toString();
            }
        }
        return null;
    }
	/**
	 * ���listCondigionField�ṹ��װΪ����List
	 * @param listConditionField ��Ϊ��listConditionField�Ľṹ
	 * @param entityName ʵ����
	 * @return ����List �û�ֻ��ҪresultCondition = new EntityConditionList(condList, EntityOperator.AND),�Ϳ���ֱ���õ�vps������ȡ���
	 */
//	public static List getCondition(GenericDelegator delegator,List listConditionField,String entityName)
//	{
//		if(listConditionField==null || listConditionField.size()<0)
//			return null;
//		List condList = new ArrayList();
//		java.util.Date currDate=new java.util.Date();
//		Calendar tempCr=GregorianCalendar.getInstance();
//		currDate.setTime(currDate.getTime());
//		for(int i=0;i<listConditionField.size();i++)
//		{
//			EntityExpr cond=null;
//			EntityExpr condTwo=null;
//			GenericValue viewCond=(GenericValue) listConditionField.get(i);
//			if(viewCond.get("entityFieldName")==null || viewCond.get("operator")==null)
//			{
//				continue;
//			}
//			//ȡ���������
//			ModelEntity myEntity = FilterOperator.getModelEntity(delegator,entityName);
//			if(myEntity!=null)
//			{
//				ModelField mf=myEntity.getField(String.valueOf(viewCond.get("entityFieldName")));
//				if(mf==null || mf.getType()==null)
//				{
//					continue;
//				}
//				else if(mf.getType().equals("date")||mf.getType().equals("time"))
//				{
//					continue;
//				}
//
//			}
//			String operatorStr=((String) viewCond.get("operator")).trim();
//			String fieldParameter1=(String) viewCond.get("fieldParameter1");
//			String fieldParameter2=(String) viewCond.get("fieldParameter2");
//			if(operatorStr!=null)
//			{
//				Debug.log("�����淽ʽƴװ����--��|"+operatorStr+"|");
//
//				if(operatorStr.equals("=")&&fieldParameter1!=null)
//				{
//					Debug.log("�����淽ʽƴװ����=");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.EQUALS,fieldParameter1);
//					condList.add(cond);
//					continue;
//				}
//
//				if(operatorStr.equals(">")&&fieldParameter1!=null)
//				{
//					Debug.log("�����淽ʽƴװ����>");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,fieldParameter1);
//					condList.add(cond);
//					continue;
//				}
//
//				if(operatorStr.equals("<")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LESS_THAN,fieldParameter1);
//					condList.add(cond);
//					continue;
//				}
//
//				if(operatorStr.equals(">=")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.GREATER_THAN_EQUAL_TO,fieldParameter1);
//					condList.add(cond);
//					continue;
//				}
//
//				if(operatorStr.equals("<=")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LESS_THAN_EQUAL_TO,fieldParameter1);
//					condList.add(cond);
//					continue;
//				}
//
//				if(operatorStr.equals("null"))
//				{
//					Debug.log("�����淽ʽƴװ���� Null");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.EQUALS,null);
//					condList.add(cond);
//					continue;
//				}
//
//				if(operatorStr.equals("zeroOrNull"))
//				{
//					Debug.log("�����淽ʽƴװ����---- zeroOrNull");
//					EntityExpr zeroExpr=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.EQUALS,"0");
//
//					EntityExpr nullExpr=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.EQUALS,null);
//
//					List tempList=new ArrayList();
//					tempList.add(nullExpr);
//					tempList.add(zeroExpr);
//
//					EntityExprList exprList = new EntityExprList(tempList, EntityOperator.OR);
//
//					condList.add(exprList);
//									Debug.log("�����淽ʽƴװ���� zeroOrNull"+condList);
//					continue;
//
//				}
//				if(operatorStr.equals("<>")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.NOT_EQUAL,fieldParameter1);
//					condList.add(cond);
//					continue;
//
//				}
//
//				if(operatorStr.equals("between")&&fieldParameter1!=null&&fieldParameter2!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.GREATER_THAN_EQUAL_TO,fieldParameter1);
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LESS_THAN_EQUAL_TO,fieldParameter2);
//					condList.add(cond);
//					condList.add(condTwo);
//					continue;
//
//				}
//
//				if(operatorStr.equals("leftLike")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LIKE,fieldParameter1+"%");
//					condList.add(cond);
//					continue;
//
//				}
//
//				if(operatorStr.equals("likeContain")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LIKE,"%"+fieldParameter1+"%");
//					condList.add(cond);
//					continue;
//
//				}
//
//				if(operatorStr.equals("like")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LIKE,fieldParameter1);
//					condList.add(cond);
//					continue;
//
//				}
//				if(operatorStr.equals("Y"))
//				{
//					Debug.log("��ʼƴװ���� Y   entityfield��|"+String.valueOf(viewCond.get("entityFieldName"))+"|");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.EQUALS,"Y");
//					condList.add(cond);
//					Debug.log("ƴװ���� Y  over!!!");
//					continue;
//
//				}
//
//				if(operatorStr.equals("N"))
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.EQUALS,"N");
//					condList.add(cond);
//					continue;
//
//				}
//
//				if(operatorStr.equals("relation")&&fieldParameter1!=null)
//				{
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.EQUALS,fieldParameter1);
//					condList.add(cond);
//					continue;
//
//				}
//
//				if(operatorStr.equals("today"))
//				{
//					Debug.log("����");
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//					condList.add(cond);
//
//
//					cr.setTime(currDate);
//					Debug.log(cr.toString());
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentWeek"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_WEEK,-cr.get(Calendar.DAY_OF_WEEK)+1);
//
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofWeek="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_WEEK,8-cr.get(Calendar.DAY_OF_WEEK));
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//					Debug.log("lastDayofWeek="+cr.getTime().toString());
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentWeekToToday"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_WEEK,-cr.get(Calendar.DAY_OF_WEEK)+1);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofWeek="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//					Debug.log("thisDayofWeek="+currDate);
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentPeriod"))
//				{
//									cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LIKE,fieldParameter1);
//									condList.add(cond);
//					Calendar cr=GregorianCalendar.getInstance();
//
//					Debug.log("currDayofMonth="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_MONTH,-cr.get(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofMonth="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_MONTH,cr.getActualMaximum(Calendar.DAY_OF_MONTH)-cr.get(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN,endTime);
//					condList.add(condTwo);
//					Debug.log("lastDayofMonth="+cr.getTime().toString());
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentPeriodToToday"))
//				{
//									cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.LIKE,fieldParameter1);
//									condList.add(cond);
//
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,1);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//					condList.add(cond);
//					Debug.log("currentMonthToToday");
//
//					cr.setTime(currDate);
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentMonth"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					Debug.log("currDayofMonth="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_MONTH,-cr.get(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofMonth="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_MONTH,cr.getActualMaximum(Calendar.DAY_OF_MONTH)-cr.get(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN,endTime);
//					condList.add(condTwo);
//					Debug.log("lastDayofMonth="+cr.getTime().toString());
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("previousMonth"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					Debug.log("currDayofMonth="+cr.getTime().toString());
//
//
//
//					cr.add(Calendar.MONTH,-1);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-1 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofMonth="+cr.getTime().toString());
//
//									cr.add(Calendar.DAY_OF_MONTH,cr.getActualMaximum(Calendar.DAY_OF_MONTH)-cr.get(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.getActualMaximum(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN,endTime);
//					condList.add(condTwo);
//					Debug.log("lastDayofMonth="+cr.getTime().toString());
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentMonthFirst"))
//				{
//
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,1);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//					condList.add(cond);
//					Debug.log("��Ѯ");
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,10);
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//
//					continue;
//
//				}
//				if(operatorStr.equals("currentMonthMiddle"))
//				{
//									java.sql.Timestamp.valueOf()
//
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//
//					cr.set(Calendar.DAY_OF_MONTH,11);
//
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//					condList.add(cond);
//					Debug.log("��Ѯ");
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,20);
//					Debug.log(cr.toString());
//
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//
//					continue;
//
//				}
//				if(operatorStr.equals("currentMonthLast"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,21);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//					condList.add(cond);
//					Debug.log("��Ѯ");
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//
//					continue;
//
//				}
//				if(operatorStr.equals("currentMonthToToday"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_MONTH,1);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//					condList.add(cond);
//					Debug.log("currentMonthToToday");
//
//					cr.setTime(currDate);
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("currentQuarter"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					int thisMonth=cr.get(Calendar.MONTH)+1;
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//
//					if(thisMonth>=1&&thisMonth<=3)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),1,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//
//						cr.set(cr.get(Calendar.YEAR),3,10);
//						tempCr.set(cr.get(Calendar.YEAR),3,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH))+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=4&&thisMonth<=6)
//					{
//
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),4,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						cr.set(cr.get(Calendar.YEAR),6,10);
//						tempCr.set(cr.get(Calendar.YEAR),6,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH))+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					}
//
//					if(thisMonth>=7&&thisMonth<=9)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),7,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						cr.set(cr.get(Calendar.YEAR),9,10);
//						tempCr.set(cr.get(Calendar.YEAR),9,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH))+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					}
//
//					if(thisMonth>=10&&thisMonth<=12)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),10,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						cr.set(cr.get(Calendar.YEAR),12,10);
//						tempCr.set(cr.get(Calendar.YEAR),12,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH))+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					}
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("currentQuarter");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//					continue;
//				}
//				if(operatorStr.equals("currentQuarterToToday"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					int thisMonth=cr.get(Calendar.MONTH)+1;
//
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//
//					if(thisMonth>=1&&thisMonth<=3)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),1,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						tempCr.setTime(currDate);
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=4&&thisMonth<=6)
//					{
//
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),4,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						tempCr.setTime(currDate);
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=7&&thisMonth<=9)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),7,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						tempCr.setTime(currDate);
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=10&&thisMonth<=12)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),9,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						tempCr.setTime(currDate);
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("currentQuarter");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//
//					continue;
//				}
//
//
//				if(operatorStr.equals("currentYear"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//					cr.setTime(currDate);
//
//					tempCr.set(cr.get(Calendar.YEAR),1,1);
//					startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//
//					tempCr.set(cr.get(Calendar.YEAR),12,31);
//					endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH))+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("currentYear");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//
//					continue;
//
//				}
//
//
//				if(operatorStr.equals("currentYearToToday"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//					cr.setTime(currDate);
//
//					tempCr.set(cr.get(Calendar.YEAR),1,1);
//
//					startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//					endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("currentYearToToday");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//
//					continue;
//				}
//
//				if(operatorStr.equals("previousWeek"))
//				{
//
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					cr.add(Calendar.DAY_OF_WEEK,-cr.get(Calendar.DAY_OF_WEEK)-7);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofWeek="+cr.getTime().toString());
//
//					cr.setTime(currDate);
//					cr.set(Calendar.DAY_OF_WEEK,7);
//									cr.add(Calendar.DAY_OF_WEEK,7-cr.get(Calendar.DAY_OF_WEEK)-7);
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//					condList.add(condTwo);
//					Debug.log("lastDayofWeek="+cr.getTime().toString());
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("previousPeriod"))
//				{//�ڼ����ڱ����뿪��ʱ��δʵ�֣���ʱʹ���´���
//									cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")), EntityOperator.GREATER_THAN_EQUAL_TO,currDate);
//									condList.add(cond);
//
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//					Debug.log("currDayofMonth="+cr.getTime().toString());
//
//
//
//					cr.add(Calendar.MONTH,-1);
//					java.sql.Timestamp startTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-1 00:00:00");
//					cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN,startTime);
//					condList.add(cond);
//					Debug.log("firstDayofMonth="+cr.getTime().toString());
//
//									cr.add(Calendar.DAY_OF_MONTH,cr.getActualMaximum(Calendar.DAY_OF_MONTH)-cr.get(Calendar.DAY_OF_MONTH));
//					java.sql.Timestamp endTime=java.sql.Timestamp.valueOf(String.valueOf(cr.get(Calendar.YEAR))+"-"+String.valueOf(cr.get(Calendar.MONTH)+1)+"-"+String.valueOf(cr.getActualMaximum(Calendar.DAY_OF_MONTH))+" 23:59:59");
//					condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN,endTime);
//					condList.add(condTwo);
//					Debug.log("lastDayofMonth="+cr.getTime().toString());
//
//					continue;
//
//				}
//				if(operatorStr.equals("previousQuarter"))
//				{
//
//					Calendar cr=GregorianCalendar.getInstance();
//
//					cr.setTime(currDate);
//
//					int thisMonth=cr.get(Calendar.MONTH)+1;
//
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//
//					if(thisMonth>=1&&thisMonth<=3)
//					{
//						cr.setTime(currDate);
//				//		cr.set(Calendar.YEAR,cr.get(Calendar.YEAR)-1);
//						tempCr.set(cr.get(Calendar.YEAR)-1,10,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						cr.set(cr.get(Calendar.YEAR)-1,12,10);
//
//						tempCr.set(cr.get(Calendar.YEAR)-1,12,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=4&&thisMonth<=6)
//					{
//
//						cr.setTime(currDate);
//						tempCr.set(cr.get(Calendar.YEAR),1,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						cr.set(cr.get(Calendar.YEAR),3,10);
//						tempCr.set(cr.get(Calendar.YEAR),3,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=7&&thisMonth<=9)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),4,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//						cr.set(cr.get(Calendar.YEAR),6,10);
//						tempCr.set(cr.get(Calendar.YEAR),6,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//
//					if(thisMonth>=10&&thisMonth<=12)
//					{
//						cr.setTime(currDate);
//
//						tempCr.set(cr.get(Calendar.YEAR),7,1);
//						startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//
//						cr.set(cr.get(Calendar.YEAR),12,10);
//
//						tempCr.set(cr.get(Calendar.YEAR),9,cr.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//					}
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("currentQuarter");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("lastYear"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//					cr.setTime(currDate);
//
//					cr.add(Calendar.YEAR,-1);
//
//					tempCr.set(cr.get(Calendar.YEAR),1,1);
//					startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//					tempCr.set(cr.get(Calendar.YEAR),12,31);
//					endTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("lastYear");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//
//					continue;
//				}
//
//				if(operatorStr.equals("custom")&&fieldParameter1!=null)
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//					cr.setTime(currDate);
//
//									tempCr.set(cr.get(Calendar.YEAR),1,1);
//									startDate=tempCr.getTime();
//
//									tempCr.set(cr.get(Calendar.YEAR),12,31);
//									endDate=tempCr.getTime();
//
//					startTime=java.sql.Timestamp.valueOf(fieldParameter1+" 00:00:00");
//					endTime=java.sql.Timestamp.valueOf(fieldParameter2+" 23:59:59");
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("custom");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//
//					continue;
//
//				}
//
//				if(operatorStr.equals("lastYearBeginToLastYearToday"))
//				{
//					Calendar cr=GregorianCalendar.getInstance();
//
//					java.sql.Timestamp startTime=null;
//					java.sql.Timestamp endTime=null;
//
//					cr.setTime(currDate);
//					cr.add(Calendar.YEAR,-1);
//
//					tempCr.set(cr.get(Calendar.YEAR),1,1);
//					startTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH))+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 00:00:00");
//
//					tempCr.set(cr.get(Calendar.YEAR),cr.get(Calendar.MONTH),cr.get(Calendar.DAY_OF_MONTH));
//					endTime=java.sql.Timestamp.valueOf(String.valueOf(tempCr.get(Calendar.YEAR))+"-"+String.valueOf(tempCr.get(Calendar.MONTH)+1)+"-"+String.valueOf(tempCr.get(Calendar.DAY_OF_MONTH))+" 23:59:59");
//
//
//					if(startTime!=null&&endTime!=null)
//					{
//						cond=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.GREATER_THAN_EQUAL_TO,startTime);
//						condList.add(cond);
//						Debug.log("lastYearBeginToLastYearToday");
//
//						condTwo=new EntityExpr(String.valueOf(viewCond.get("entityFieldName")),EntityOperator.LESS_THAN_EQUAL_TO,endTime);
//						condList.add(condTwo);
//					}
//					continue;
//				}
//			}
//		}
//		return condList;
//	}
//	private static ModelEntity getModelEntity(GenericDelegator delegator,String entityName)
//	{
//		ModelEntity mode=null;
//		try
//		{
//			mode =VirtualEntityHelper.getModelEntity(delegator,entityName);
//		}
//		catch (GenericEntityException e)
//		{
//			e.printStackTrace();
//		}
//		return mode;
//	}
}
