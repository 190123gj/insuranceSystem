/*
 * �������� 2003-10-16
 *
 * ���������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > ������� > �����ע��
 */
package com.born.insurance.webui.control;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * JavaScript��ɹ�����
 * @author �ᴺ��
 */
public class JSTool
{
	public static ThreadLocal local = new ThreadLocal();
	private JSTool()
	{
	}
	/**
	 * ���캯��
	 * @param fileName �ļ���
	 */
	public JSTool(String fileName)
	{
		this.fileName=fileName;

	}
	protected ComponentUtil util=ComponentUtil.getInstance();
	/**
	 * JavaScript�ļ���
	 */
	protected String fileName="";
	protected File jsFile;
	protected FileWriter fw ;
    protected boolean fileIsExist=false;
    /**
     * �ļ��Ƿ����
     * @return �ļ��Ƿ����
     */
    public boolean FileIsExist()
    {
    	File jsFile = new File(util.getJSFilePath() + "/" + this.fileName);
        this.fileIsExist=jsFile.exists();
        return this.fileIsExist;
    }
    /**
     * Ҫд���ļ���JavaScript�ű���
     */
	public StringBuffer js = new StringBuffer();
	/**
	 * ��ȡ�����JavaScript����
	 * ͬʱ����û���ļ�������ļ�
	 * @return JavaScript���õĴ�
	 */
	public String getComponentJS()
	{
          
          if(util.isUpdateJsEveryTime())
          {
            
            try {
              File jsFile = new File(util.getJSFilePath() + "/" + this.fileName);
              FileWriter fw = new FileWriter(jsFile);
			  System.out.println("��qch����д�ű��ļ�"+jsFile.getAbsolutePath());
              fw.write(this.js.toString());
              fw.flush();
              fw.close();
            }
            catch (IOException ioex) {
            	throw new ComponentException("д��ű��ļ����?" + ioex.getMessage());
            }
          }
          else
          {
			//System.out.println("��qch������д�ű��ļ�");
          }
          Map fileMap=getCurrentTreadMap();
          if(!fileMap.containsKey(this.fileName))
          {
        	  fileMap.put(this.fileName,null);
        	  return "<script src=\"" + util.getJSRequestPath() + "/" + this.fileName+ "\" language=\"javascript\" charset=\"gb2312\"></script>";
          }
          else
          {
        	  return "";
          }
          
	}
	private static Map getCurrentTreadMap()
	{
		Map treadMap=null;
		Object o=local.get();
		if(o==null)
        {
			//System.out.println("===qch=====��ʼ��==");
			treadMap=new HashMap();
      	  	local.set(treadMap);
        }
		else
		{
			//System.out.println("===qch=====����==");
			treadMap=(Map)o;
		}
		return treadMap;
	}
	public  static  void clearJsFileNameMap()
	{
		local.set(null);
	}

}
