/*
 * Created on 2003-10-26
 *
 */
package com.born.insurance.webui.control;

import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.lang.reflect.*;
/**
 * @author     qch
 * @created    2003-10-26
 * @version    $Revision: 1.12 $ November 20,2003
 * @since      1.0 
 * grid��Ԫ�༭����
 */
public class Editor extends AbstractComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String setDecimal = "setDecimal";
	
    //editor �ͻ��˶����ṩ����
    private String name = "";
    private String dataField = "";//��ӦcolumnName
    private String dataValue = "";//��id����
    protected String columnName = "";//������
    private String headerName = "";//��ʾ����
    private String formula = "";//��ʽ(�ͻ��˱���Ϊ����)
	private boolean isNoFixFormula = false;//�Ƿ�̶���ʽ
    protected IComponent component = new Label("label");
    //String componentID = "";
    private boolean isTotal = false;//�ϼƱ�־
    private boolean isNecessary = false;//��¼��־
    private boolean isReadOnly = false;//ֻ����־
    private boolean isChange = false;//��̬���Դ��־
    private boolean isMutex = false;//�����־
    private boolean isDrop = false;//������־
    private String mutexColumn = "";//��������(�ͻ��˱���Ϊ����)
    private boolean isRelation = false;//������־
    private String relationColumn = "";//��������
    private boolean isAutoSearch = false;
    private boolean isColInitStatus = false;//�ؼ���ʼ״̬
	private boolean isColDisabled = false;//�ؼ�����״̬
	private boolean isAutoAscend = false;//����״̬
	private boolean isClient = false;//���ÿͻ���ί��״̬
    private List dataSource = new LinkedList();//���Դ
	private Map dsMap = new HashMap();//���Դ
    protected StringBuffer dataSB = new StringBuffer();
	protected ArrayListManager formulaALM = null;
	protected HashMapManager formulaHMM = null;
    protected String gridID = "";
    protected int editorIndex = -1;
    protected String colWidth = "";//�п�
    protected String horizontalAlign = "";//���뷽ʽ
    protected GridTable gridTable = null;
    private int tabIndex = 999;
    private StringBuffer dataColumn = new StringBuffer();
    private Map clientMethodMap = new HashMap();
    IEvent event = null;
    
    protected Editor(String columnName)
    {
		this.setColumnName(columnName);
    }
    
    protected Editor(String columnName,IComponent component)
    {
		this.setColumnName(columnName);
        this.addChild(component);
    }
    
    /**
     * ��������
     * @param columnName ��ݰ�����
     */
    public void setColumnName(String columnName)
    {
        if(columnName!=null && this.columnName.trim().equals(""))
            this.columnName = columnName.trim();
    }
    
    /**
     * ��������ʾ��
     * @param headerName ����ʾ��
     */
    public void setheaderName(String headerName)
    {
        if(this.headerName.equals(""))
            this.headerName = headerName.trim();
    }
        
    public void setName(String name)
    {
        if(this.name.equals(""))
            this.name = name.trim();
    }
    
    /**
     * ��������
     * @param dataField ����
     */    
    public void setDataField(String dataField)
    {
        this.dataField = dataField;
    }
    
    /**
     * ���ð�id����
     * @param dataValue ��id����
     */
    public void setDataValue(String dataValue)
    {
        this.dataValue = dataValue;
    }
    
    /**
     * �����Ƿ�ϼ�
     * @param isTotal �ϼƱ�־
     */
    public void setTotal(boolean isTotal)
    {
        this.isTotal = isTotal;
    }
    
    public void setNecessary(boolean isNecessary)
    {
        this.isNecessary = isNecessary;
    }
    
    public void setReadOnly(boolean isReadOnly)
    {
        this.isReadOnly = isReadOnly;
    }
    
    /**
     * �����Ƿ�����Դ
     * @param isChange �����Դ��־
     */
    public void setChangeData(boolean isChange)
    {
        this.isChange = isChange;    
    }
    
    /**
     * �����Ƿ񻥳�
     * @param isMutex �����־
     */
    public void setMutex(boolean isMutex)
    {
        this.isMutex = isMutex;   
    }
    
    /**
     * ���û�����
     * @param columnName ������
     */
    public void setMutexColumnName(String columnName)
    {
        this.mutexColumn = columnName.trim();
    }
    
	/**
	 * �����г�ʼ״̬
	 * @param isColInitStatus �г�ʼ״̬��־
	 */
	protected void setColInitStatus(boolean isColInitStatus)
	{
		this.isColInitStatus = isColInitStatus;
	}
		
	/**
	 * �����г�ʼ״̬
	 * @param isColDisabled �п���״̬��־
	 */
	protected void setColDisabled(boolean isColDisabled)
	{
		this.isColDisabled = isColDisabled;
	}
		
	/**
	 * ��������״̬
	 * @param isAutoAscend ����״̬��־
	 */
	protected void setAutoAscend(boolean isAutoAscend)
	{
		this.isAutoAscend = isAutoAscend;
	}
	
	/**
	 * �������ÿͻ���ί��״̬
	 * @param isSetDecimal ���ÿͻ���ί��
	 */
	public void setClientDelegator(boolean isClient)
	{
		this.isClient = isClient;
	}
	
	/**
	 * ��������С��λ��ͻ��˷���
	 * @param method ����С��λ��ͻ��˷���
	 */
	public void setSetDecimalClientMethod(String method)
	{
		clientMethodMap.put(setDecimal,method);
	}
			
	/**
	 * ���ÿͻ��˷���
	 * @param method ���ÿͻ��˷���
	 */
	protected void setClientMethod(Map methodMap)
	{
		clientMethodMap.putAll(methodMap);
	}
			
    public void setRelation(boolean isRelation)
    {
        this.isMutex = isRelation;   
    }

    public void setRelationColumnName(String columnName)
    {
        this.relationColumn = columnName.trim();
    }
    
    /**
     * ���ù�ʽ
     * @param formula ��ʽ
     */    
    public void setFormula(String formula)
    {
    	String tmp = getFormatFormula(formula,true);
    	if(tmp!=null && !tmp.trim().equals(""))
        	this.formula = "[" + tmp + "]";
    }
    
    private String getFormatFormula(String formula,boolean isAddQuotation)
    {
    	String retStr = new String();
		if(formula!=null && !formula.trim().equals(""))
		{
			String[] sperate =StringUtils.split(formula,";");/////formula.split(";");
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<sperate.length;i++)
			{
				sperate[i]=StringUtils.replace(sperate[i],"\"","");// sperate[i].replaceAll("\"","");
				//sperate[i]=sperate[i].replaceAll("'","");
				if(isAddQuotation)
					sb.append("\""+sperate[i]+"\",");
				else
					sb.append(sperate[i]+",");
			}
			retStr= sb.toString().substring(0,sb.toString().length()-1);
		}
		return retStr;
    }
     
	/**
	* ���÷ǹ̶�����־ʽ
	* @param isNoFixFormula �Ƿ�ǹ̶���ʽ
	*/   
	public void setNoFixFormula(boolean isNoFixFormula)
	{
		this.isNoFixFormula = isNoFixFormula;
	}
	
	/**
	 * ���ÿ��ù�ʽ��
	 * @param key ��ʽ��Ӧ�ľ���ֵ
	 * @param formula ��ʽ
	 */
	public void setFormulas(String key,String formula)
	{
		this.setNoFixFormula(true);
		if(this.formulaHMM==null)
			this.formulaHMM = new HashMapManager(this.gridID+"_"+this.columnName+"_formula");
		if(this.formulaALM==null)
			this.formulaALM = new ArrayListManager(this.gridID+"_"+this.columnName+"_formula");
		if(key==null || key.trim().equals("") || formula==null)
			return;
		if(!this.formulaHMM.contains(key))
			this.formulaHMM.put(key,this.getFormatFormula(formula,false));
	}
		
	/**
	 * ���ÿ��ù�ʽ��
	 * @param keys ��ʽ��Ӧ�ľ���ֵ����
	 * @param formula ��ʽ����
	 */
	public void setFormulas(String[] keys,String[] formula)
	{
		this.setNoFixFormula(true);
		if(keys==null || formula==null)
			return;
		for(int i=0;i<keys.length;i++)
		{
			if(i<=formula.length-1)
				this.setFormulas(keys[i],formula[i]);
		}
	}
	
	/**
	 * ���ù�ʽ��־(rowIndex��С����)
	 * @param rowIndex �к�
	 * @param Value ��ʽ��Ӧ�ľ���ֵ
	 */
	public void setFormulaFlag(int rowIndex,String Value)
	{
		this.setNoFixFormula(true);
		if(this.formulaHMM==null)
			this.formulaHMM = new HashMapManager(this.gridID+"_"+this.columnName+"_formula");
		if(this.formulaALM==null)
			this.formulaALM = new ArrayListManager(this.gridID+"_"+this.columnName+"_formula");
		if(this.formulaALM.size() == rowIndex)
			this.formulaALM.insert(rowIndex,Value);
		else
		{
			if(this.formulaALM.size() > rowIndex && rowIndex >= 0)
				this.formulaALM.set(rowIndex,Value);
			else
				throw new ComponentException("���ù�ʽ��־����!�����к�Ϊ"+String.valueOf(rowIndex)+".");
		}
	}
	
	/**
	 * ���ù�ʽ��־
	 * @param Values ÿ�й�ʽ��Ӧ�ľ���ֵ����
	 */
	public void setFormulaFlag(String[] Values)
	{
		this.setNoFixFormula(true);
		int rows = Values.length;
		for(int rowIndex=0;rowIndex<rows;rowIndex++)
		{
			if(rowIndex<=Values.length-1)
				setFormulaFlag(rowIndex,Values[rowIndex]);
		}
	}
		
	/**
	 * ���ù�ʽ��־
	 * @param rows ������
	 * @param Values ÿ�й�ʽ��Ӧ�ľ���ֵ����
	 */
	public void setFormulaFlag(int rows,String[] Values)
	{
		this.setNoFixFormula(true);
		for(int rowIndex=0;rowIndex<rows;rowIndex++)
		{
			if(rowIndex<=Values.length-1)
				setFormulaFlag(rowIndex,Values[rowIndex]);
		}
	}
	
    /**
     * �������Դ(rowIndex��С����)
     * @param rowIndex ��
     * @param dataSource ���Դ
     */
    public void setDataSource(int rowIndex,List dataSource)
    {
        this.isChange = true;
        if(this.dataSource.size() == rowIndex)
			this.dataSource.add(rowIndex,dataSource);
		else
		{
			if(this.dataSource.size() > rowIndex && rowIndex >= 0)
	        	this.dataSource.set(rowIndex,dataSource);
	        else
	        	throw new ComponentException("�������Դ����!�����к�Ϊ"+String.valueOf(rowIndex)+".");
		}
//		this.dsMap.put(String.valueOf(rowIndex),dataSource);
    }
    
	/**
	 * �������Դ
	 * @param dataSourceList ���Դ����
	 */
	public void setDataSource(List[] dataSourceList)
	{
		this.isChange = true;
		int rows = dataSourceList.length;
		for(int rowIndex=0;rowIndex<rows;rowIndex++)
		{
			if(rowIndex<=dataSourceList.length-1)
				setDataSource(rowIndex,dataSourceList[rowIndex]);
		}
	}
		
    /**
     * �������Դ
     * @param rows ����
     * @param dataSourceList ���Դ����
     */
	public void setDataSource(int rows,List[] dataSourceList)
	{
		this.isChange = true;
		for(int rowIndex=0;rowIndex<rows;rowIndex++)
		{
			if(rowIndex<=dataSourceList.length-1)
				setDataSource(rowIndex,dataSourceList[rowIndex]);
		}
	}
	
    /**
     * �Ƴ����Դ
     * @param rowIndex ��
     */
    public void removeDataSource(int rowIndex)
    {
        this.dataSource.remove(rowIndex);
    }
    
//	private void convertDataSource()
//	{
//		if(this.isChange)
//		{
//			int key = 0;
//	        for (Iterator iter = this.dsMap.entrySet().iterator(); iter.hasNext(); )
//			{
//				List Value = new LinkedList();
//				Map.Entry e = (Map.Entry) iter.next();
//				key = Integer.parseInt(String.valueOf(e.getKey()));
//				Value = (List)e.getValue();
//				this.dataSource.add(key,Value);
//			} 
//		}
//	} 
	
    private String getData()
    {
        StringBuffer sb = new StringBuffer();
        if(this.isChange)
        {
			Map alm = null; 
            int rowIndex = 0;
            String dataName = this.gridID+"_"+this.columnName+"_data";
            if(this.dataSource.size()==0)
            {
				this.processNoData(rowIndex);
            }
            else
            	sb.append("var "+dataName+" = new Array("+String.valueOf(this.dataSource.size())+");");
            for(Iterator iterator = this.dataSource.iterator();iterator.hasNext();)
            {
            	Object obj = iterator.next();
            	if(obj==null)
            	{
					this.processNoData(rowIndex);
					continue;
            	}
                List DS = (List)obj;
                alm = new HashMap();
                String keyID="";
                String Value="";
                String arrayID="";
                for(int i=0;i<DS.size();i++)
                {
                    Map dataMap = (Map)DS.get(i);
                    for(int field=0;field<dataMap.keySet().toArray().length;field++)
                    {
                        String fieldName = String.valueOf(dataMap.keySet().toArray()[field]);
                        if(isBangField(this.dataColumn.toString(),fieldName))
                        {                 
	                        if(!alm.containsKey(fieldName))
	                        {
	                            arrayID="\""+this.gridID+"_"+this.columnName+"_"+fieldName+"_"+String.valueOf(rowIndex)+"\",";
	                            alm.put(fieldName,new ArrayListManager(this.gridID+"_"+this.columnName+"_"+String.valueOf(rowIndex)+"_"+fieldName));
	                        }
							Value = (String)dataMap.get(fieldName);
							((ArrayListManager)alm.get(fieldName)).add(Value); 
                        }
                        if(field+1==dataMap.keySet().toArray().length && !arrayID.equals(""))
                            arrayID=arrayID.substring(0,arrayID.length()-1);
                        
                    }   
                }
                if(arrayID.equals(""))
                    sb.append(dataName+"["+String.valueOf(rowIndex)+"]=null;");
                else
                    sb.append(dataName+"["+String.valueOf(rowIndex)+"]=["+arrayID+"];");
                for (Iterator iter = alm.values().iterator(); iter.hasNext(); )
                {
					dataSB.append(((ArrayListManager)iter.next()).getElementHtml());
                }
                rowIndex++;
            }
        }
        return sb.toString();
    }
    
    private void processNoData(int rowIndex)
    { 
		Map alm = new HashMap();
		String field[] = StringUtils.split(removeQuotation(this.dataColumn.toString()),",");// removeQuotation(this.dataColumn.toString()).split(",");
		for(int i=0;i<field.length;i++)
		{
			if(!field[i].trim().equals(""))
				alm.put(field[i],new ArrayListManager(this.gridID+"_"+this.columnName+"_"+String.valueOf(rowIndex)+"_"+field[i]));
		}
		for (Iterator iter = alm.values().iterator(); iter.hasNext(); )
		{
			dataSB.append(((ArrayListManager)iter.next()).getElementHtml());
		}
    }
    
    /**
     * ����ͻ������Դ
     * @param DS ���Դ
     * @param dataColumn ����DropDownList��filter��test��value����","�ָ
     * @param columnName ����
     * @return
     */
    public static String getALMString(List DS,String dataColumn,String columnName)
    {
    	StringBuffer sb = new StringBuffer();
		Map alm = new HashMap(); 
		String Value = "";
		for(int i=0;i<DS.size();i++)
		{
			Map dataMap = (Map)DS.get(i);
			for(int field=0;field<dataMap.keySet().toArray().length;field++)
			{
				String fieldName = String.valueOf(dataMap.keySet().toArray()[field]);
				if(isBangField(dataColumn,fieldName))
				{                 
					if(alm.get(fieldName)==null)
					{
						alm.put(fieldName,new ArrayListManager(columnName+"_"+fieldName));
					}
					Value = (String)dataMap.get(fieldName);
					((ArrayListManager)alm.get(fieldName)).add(Value); 
				}     
			}                   
		}

		for (Iterator iter = alm.values().iterator(); iter.hasNext(); )
		{
			sb.append(((ArrayListManager)iter.next()).getElementHtml());
		}
    	return sb.toString();
    }
    
    private static boolean isBangField(String dataColumn,String fieldName)
    {  
    	boolean retBol = false;
    	if(dataColumn!=null && dataColumn.indexOf(fieldName)!=-1)
			retBol = true;
    	return retBol;
    }
    
    protected String getNoFixFormula()
    {
    	if(!this.isNoFixFormula)
    		return "";
    	StringBuffer sb = new StringBuffer();
		if(this.formulaHMM!=null)
			sb.append(this.formulaHMM.getInnerHtml());
		if(this.formulaALM!=null)
			sb.append(this.formulaALM.getInnerHtml());
		return sb.toString();
    }
    
    protected void addChild(IComponent component)
    {
        this.component = component;
        //((AbstractComponent)this.component).componentID = this.gridID + "_" + this.component.getComponentId();
        //((AbstractComponent)this.component).setTabIndex(this.tabIndex);
        if(component instanceof OfbizDropDownList)
        {
            this.isDrop = true;
            this.setDataColumn();
        }
        if(component instanceof SearchText)
        	this.isAutoSearch = ((SearchText)component).getAutoSearch();
    }
    
    /**
     * 
     * �õ�Editor�������Ĭ��ΪLabel�����
     */
    public IComponent getComponent()
    {
        return this.component;    
    }
    
    private void setDataColumn()
    {
        //this.dataColumn.append("[");
        if( ((OfbizDropDownList)this.component).getFilterField()==null)
            this.dataColumn.append(this.getQuotation(""));
        else
            this.dataColumn.append(this.getQuotation(((OfbizDropDownList)this.component).getFilterField()));
        this.dataColumn.append(",");
        if( ((OfbizDropDownList)this.component).getTextField()==null)
            this.dataColumn.append(this.getQuotation(""));
        else
            this.dataColumn.append(this.getQuotation(((OfbizDropDownList)this.component).getTextField()));
        this.dataColumn.append(",");
        if( ((OfbizDropDownList)this.component).getValueField()==null)
            this.dataColumn.append(this.getQuotation(""));
        else
            this.dataColumn.append(this.getQuotation(((OfbizDropDownList)this.component).getValueField()));
        //this.dataColumn.append("]");    
    }
    
    protected void setGridID(String gridTableID)
    {
        if(this.gridID.trim().equals(""))
            this.gridID = gridTableID;
    }
    
    protected void setLabelID(String componentID)
    {
        if(this.component instanceof Label)
            ((AbstractComponent)this.component).componentID = componentID;
    }
        
    protected String getScriptHtml()
    {
        return EditorJS.getEditorJS();
    }
    
    protected String getElementHtml()
    {
        StringBuffer html = new StringBuffer();
        String editor = this.gridID+"_"+this.columnName;
        //html.append(this.getData());
		this.getData();
        html.append("\r\n");
        html.append("var "+editor+" = new Editor("+getQuotation(this.columnName)+");");
        html.append(editor+".name="+getQuotation(this.name)+";");
        html.append(editor+".gridID="+getQuotation(this.gridID)+";");
        html.append(editor+".dataField="+getQuotation(this.dataField)+";");
        html.append(editor+".dataValue="+getQuotation(this.dataValue)+";");
//        if(this.isChange)
//            html.append(editor+".data="+editor+"_data;");
        html.append(editor+".headerName="+getQuotation(this.headerName)+";");
        if(!this.formula.equals(""))
            html.append(editor+".formula="+this.formula+";");
        html.append(editor+".colWidth="+getQuotation(this.colWidth)+";");
        html.append(editor+".hAlign="+getQuotation(this.horizontalAlign)+";");
        if(this.gridTable!=null && ((WebGrid)this.gridTable).getGridViewModel()!=WebGrid.CUSTOM_MODEL)
        {
        	processEvent();
			html.append(editor+".component="+getQuotation(StringUtils.replace(StringUtils.replace(((AbstractComponent)this.component).getElementHtml(),"\"","\\\""),"\r\n",""))+";"); 
			// getQuotation(((AbstractComponent)(this.component)).getElementHtml().replaceAll("\"","\\\\\"").replaceAll("\r\n",""))+";");
        }
        html.append(editor+".componentID="+getQuotation(((AbstractComponent)(this.component)).getComponentId())+";");
        
        html.append(editor+".relationColumn="+getQuotation(this.relationColumn)+";");

        html.append(this.getFlagString(editor));
		html.append(this.getClientMethod(editor));
        if(this.isMutex && !this.mutexColumn.equals(""))
            html.append(editor+".mutexColumn=["+this.mutexColumn+"];");
        if(this.isDrop)
            html.append(editor+".dataColumn=["+this.dataColumn.toString()+"];");
        html.append(editor+".editorIndex="+String.valueOf(this.editorIndex)+";");

        return html.toString();
    }
    
    private String getQuotation(String str)
    {
        StringBuffer tmpStr = new StringBuffer();
        tmpStr.append("\"");
        tmpStr.append(str);
        tmpStr.append("\"");
        return tmpStr.toString();
    }
    
	private String removeQuotation(String str)
	{
		return StringUtils.replace(str,"\"","");// str.replaceAll("\"","");
	}
		
    private String getFlagString(String editor,String flag)
    {
        StringBuffer html = new StringBuffer();
        boolean output = true;
        Field field;
        try{
            field = this.getClass().getDeclaredField(flag);
            output = field.getBoolean(this);
        }catch(NoSuchFieldException e){
            output = false;
        }
        catch(IllegalAccessException e1){
            output = false;
        }
        if(output)
            html.append(editor+"."+flag+"=true;");
        return html.toString();
    }
    
    private String getFlagString(String editor)
    {
        StringBuffer html = new StringBuffer();
        boolean output = false;
        Field[] fields = this.getClass().getDeclaredFields();
        Field field;
        String flag="";
        for(int i=0;i<fields.length;i++)
        {
            output = false;
            try{
                field = fields[i];
                flag = field.getName();
                if(flag.toUpperCase().startsWith("IS"))
                    output = field.getBoolean(this);         
            }catch(IllegalAccessException e1){
                output = false;
            }
            if(output)
                html.append(editor+"."+flag+"=true;");
        }
        return html.toString();
    }
    
    private String getClientMethod(String editor)
    {
    	StringBuffer sb = new StringBuffer();
    	if(this.isClient)
    	{
			if(this.clientMethodMap==null)
				return sb.toString();
			String key = "";
			String Value = "";
			for(Iterator iterator = this.clientMethodMap.entrySet().iterator();iterator.hasNext();)
			{
				Map.Entry e = (Map.Entry) iterator.next();
				key = String.valueOf(e.getKey());
				Value = String.valueOf(e.getValue()==null?"":e.getValue());
				sb.append(editor+"."+key+"='"+Value+"';");
			}
    	}
    	return sb.toString();
    }
    
    private void processEvent()
    {
        IEvent event = this.component.getOnChangeEvent();
		this.event=this.getOnChangeEvent();
        String funcName="";
        if(this.event!=null)
        {
            if(null!=event)
            {
                funcName = event.getFuncName();
                funcName = funcName + ";EditorOnChange("+String.valueOf(this.editorIndex)+",'"+this.gridID+"');";
            }
            else
                funcName = "EditorOnChange("+String.valueOf(this.editorIndex)+",'"+this.gridID+"');";
            if(this.event instanceof IServerEvent)
                this.component.setOnChangeEvent(funcName+((IServerEvent)this.event).getFuncName(),((IServerEvent)this.event).getClassName(),((IServerEvent)this.event).getMethodName());
            else
                this.component.setOnChangeEvent(funcName+this.event.getFuncName());
        }
        else
        {
            if(null!=event)
            {
                funcName = event.getFuncName();
                funcName = funcName + ";EditorOnChange("+String.valueOf(this.editorIndex)+",'"+this.gridID+"');";
                event.setFuncName(funcName);
            }
            else
            {
                this.component.setOnChangeEvent("EditorOnChange("+String.valueOf(this.editorIndex)+",'"+this.gridID+"');");
            }
        }
    }
   
}
