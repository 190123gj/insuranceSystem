/*
 * Created on 2003-10-26
 *
 */
package com.born.insurance.webui.control;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
 * @author     qch
 * @created    2003-10-26
 * @version    $Revision: 1.7 $ November 20,2003
 * @since      1.0 
 * grid�༭�ж���
 */
public class GridEditRow extends AbstractComponent
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List editors = new LinkedList();
    GridTable gridTable = null;
    Map scriptMap = new HashMap();
    Map mutexMap = new HashMap();
   
    protected GridEditRow(GridTable gridTable)
    {       
        this(gridTable,true);
    }
    
    protected GridEditRow(GridTable gridTable,boolean isInitEditor)
    {
        this.gridTable = gridTable;
        if(isInitEditor)
            this.addEditors(this.gridTable.columnSize());
    }
    
	/**
	   * Editor�м������
	   * @param index Editor����
	   * @param component ���
	   */    
    public void addEditorChild(int index,IComponent component)
    {
        if(index>=0 && this.editors.size()>=index+1 && this.editors.get(index)!=null)
        {
            ((Editor)(this.editors.get(index))).addChild(component);
        }
        else
			throw new ComponentException("����Ϊ["+index+"]��Editorδ��ʼ��!");
    }
    
	/**
	   * Editor�м������
	   * @param columnName ����
	   * @param component ���
	   */
    public void addEditorChild(String columnName,IComponent component)
    {
        for(Iterator iterator = this.editors.iterator();iterator.hasNext();)
        {
            Editor editor = (Editor)iterator.next();
            if(editor.editorIndex>=0)
            	editor.setColumnName(this.gridTable.getGridColumn(editor.editorIndex).getDataField());
            if(editor.columnName.equals(columnName))
            {
                editor.addChild(component);
                break;
            }
        }
    }
        
    private void addEditors(int columns)
    {
        String columnName = "";
        for(int i=0;i<columns;i++)
        {
            GridColumn gColumn = this.gridTable.getGridColumn(i);
            columnName = gColumn.getDataField();
            Editor editor = new Editor(columnName);
            editor.editorIndex=i;
            putEditors(editor);
        }
    }
    
	/**
	   * Editor�м������
	   * @param columns ����
	   * @param components ���List
	   */
    public void addEditors(int columns,List components)
    {
        String columnName = "";
        for(int i=0;i<columns;i++)
        {
            GridColumn gColumn = this.gridTable.getGridColumn(i);
            columnName = gColumn.getDataField();
            Editor editor = new Editor(columnName,(IComponent)components.get(i));
            editor.editorIndex=i;
            putEditors(editor);
        }
    }
    
	/**
	   * Editor�м������
	   * @param columns ����
	   * @param components ���List
	   */
	public void addEditors(List components)
	{
		String columnName = "";
		int columns=components.size();
		for(int i=0;i<columns;i++)
		{
			GridColumn gColumn = this.gridTable.getGridColumn(i);
			columnName = gColumn.getDataField();
			Editor editor = new Editor(columnName,(IComponent)components.get(i));
			editor.editorIndex=i;
			putEditors(editor);
		}
	}
		
    private void putEditors(Editor editor)
    {
        this.editors.add(editor);
        editor.setGridID(this.gridTable.componentID);
        editor.gridTable=this.gridTable;
    }

	/**
     * �õ�Editor����
	 * @param index Editor����
	 * @return
	 */
    public Editor getEditor(int index)
    {
        Editor editor = null;
        if(index>=0 && index < this.editors.size())
        {
            if(((Editor)this.editors.get(index)).editorIndex==index)
                editor = (Editor)this.editors.get(index);
            else
            {
                for(int i=0;i<this.editors.size();i++)
                {
                    if(((Editor)this.editors.get(i)).editorIndex==index)
                    {
                        editor = (Editor)this.editors.get(i);
                        break;
                    }
                }
            }
        }
        return editor;
    }
    
	/**
	 * �õ�Editor����
	 * @param columnName ����
	 * @return
	 */
    public Editor getEditor(String columnName)
    {
        Editor editor = null;
        for(int i=0;i<this.editors.size();i++)
        {
			if(((Editor)this.editors.get(i)).editorIndex>=0)
				((Editor)this.editors.get(i)).setColumnName(this.gridTable.getGridColumn(((Editor)this.editors.get(i)).editorIndex).getDataField());
            if(((Editor)this.editors.get(i)).columnName.equals(columnName))
            {
                editor = (Editor)this.editors.get(i);
                break;
            }
        }
        return editor;
    }
    
    protected String getElementHtml()
    {
        StringBuffer html = new StringBuffer();
        StringBuffer script = new StringBuffer();
        StringBuffer clientArrayList = new StringBuffer(); 
        setEidtorMutex();
        html.append("<script language=\"javascript\">\r\n");
        int arrLength = editors.size();
        String clientArrayName = gridTable.componentID+"_Editors";
        String clientEditor = "";
        html.append("var "+clientArrayName+" = new Array("+arrLength+");");     
        for(int i=0;i<arrLength;i++)
        {
            Editor editor = (Editor)editors.get(i);
            setEditorProperty(editor);
            html.append(editor.getElementHtml());
            clientEditor = editor.gridID + "_" + editor.columnName;
            html.append(clientArrayName+"["+i+"]="+clientEditor+";");
            clientArrayList.append(editor.dataSB.toString());
            clientArrayList.append(editor.getNoFixFormula());
        }
        html.append("\r\n</script>");
        html.append(clientArrayList.toString());
        return this.getScript() + html.toString();
    }
    
    private void setEditorProperty(Editor editor)
    {
        this.setScriptMap(editor);
        this.setScriptMap(editor.component);
        editor.setColumnName(this.gridTable.getGridColumn(editor.editorIndex).getDataField());
        if(((ListGrid)this.gridTable).getGridHead().getRow(0)!=null)
        	editor.setheaderName(((ListGrid)this.gridTable).getGridHead().getRow(0).getTableCell(editor.editorIndex).getText());
        editor.setName(gridTable.componentID+"_Editors["+String.valueOf(editor.editorIndex)+"]");
        GridColumn gColumn = this.gridTable.getGridColumn(editor.editorIndex);
        editor.setDataField(gColumn.getDataField());
        editor.setDataValue(gColumn.getHiddenValueDataField());
        editor.setFormula(gColumn.getColumnExpression());
        if(!gColumn.getAllowNull())
            editor.setNecessary(true);
        if(gColumn.getIsColumnSum())
            editor.setTotal(true);
        //editor.isReadOnly = 
        //editor.isRelation = 
        if(this.mutexMap.get(editor.columnName)!=null)
        {
            editor.setMutex(true);
            editor.setMutexColumnName(String.valueOf(this.mutexMap.get(editor.columnName)).trim());
        }
        editor.setColInitStatus(gColumn.isVisible());
		editor.setColDisabled(!gColumn.isEnabled());
		editor.setAutoAscend(gColumn.isAutoAscend());
        editor.colWidth=gColumn.getWidth().trim();
        editor.horizontalAlign=gColumn.getHorizontalAlign();
        editor.setLabelID(editor.gridID + "_" + editor.columnName);
    }
    
    //�����ǵ�һ������
    private void setEidtorMutex()
    {
        int mutexNum = ((WebGrid)this.gridTable).exclusiveColumnSize();
        for(int i=mutexNum-1;i>=0;i--)
        {
            List list = ((WebGrid)this.gridTable).getExclusiveColumn(i);
            StringBuffer sb = new StringBuffer();
            for(int j=0;j<list.size();j++)
            {
                sb.append("\"");
                sb.append(String.valueOf(list.get(j)));
                if(j==list.size()-1)
                    sb.append("\"");
                else
                    sb.append("\",");               
            }
            setMutexMap(list,sb.toString());
        }
    }
    
    private void setMutexMap(List list,String mutexStr)
    {   
        String tmpStr = new String();
        String realMutexStr = new String();
        for(int j=0;j<list.size();j++)
        {   
            tmpStr = String.valueOf(list.get(j)).trim();
            realMutexStr = mutexStr.replaceAll("\""+tmpStr+"\",","");
            realMutexStr = realMutexStr.replaceAll(",\""+tmpStr+"\"","");
            if(mutexMap.get(tmpStr)==null)
                mutexMap.put(tmpStr,realMutexStr);
        }
    }
    
    private void setScriptMap(IComponent icomponent)
    {
        AbstractComponent component = (AbstractComponent)icomponent;
        String tmp = String.valueOf(component.getClass());
        if(this.scriptMap.get(tmp)==null)
            this.scriptMap.put(tmp,component.getScriptHtml());
    }
    
    private String getScript()
    {
        StringBuffer script = new StringBuffer();
        for(Iterator iterator = this.scriptMap.values().iterator();iterator.hasNext();)
        {
            script.append((String)iterator.next()+"\r\n");
        }
        return script.toString();
    }
    
}
