/*
 * @(#)Pattern.java	1.87 02/07/10
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.born.insurance.webui.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;





public final class StringUtils    
{

	//public String[]    
	public static List splitList(String str, String delim)
	{
		 List splitList = new ArrayList();
		 StringTokenizer st = null;
		 if(str == null)
		     return splitList;
		 if(delim != null)
		     st = new StringTokenizer(str, delim);
		 else
		     st = new StringTokenizer(str);
		 if(st != null && st.hasMoreTokens())
		 {
		     splitList = new ArrayList();
		     for(; st.hasMoreTokens(); splitList.add(st.nextToken()));
		 }		
		 return splitList;
	  }
	  public static String[] split(String str, String delim)
	  {
		 return (String[])splitList(str, delim).toArray(new String[]{} );
	  }
	public static String[] splitArray(String str, String delim)
	{						
		List splitList = new ArrayList();	
		if(str == null)
		    return (String[])splitList.toArray(new String[]{});
		while(str.indexOf(delim)>=0)
		{
			
			splitList.add(str.substring(0,str.indexOf(delim)));		
			if(str.indexOf(delim)<str.length())
			{				
				str=str.substring(str.indexOf(delim)+1);
			}
		}
		splitList.add(str);
		return (String[])splitList.toArray(new String[]{});
	}
	public static String listToString(List list)
	{						
		if(list==null)
		{
			return "";
		}
		String str="";
		int count=0;
		for(Iterator it=list.iterator();it.hasNext();count++)
		{
			String temp=(String)it.next();
			if(count==0)
			{
				str=temp;
			}
			else
			{
				str+=","+temp;
			}
		}
		return str;
	}
	public static String arrryToString(String[] strArray)
	{						
		if(strArray==null)
		{
			return "";
		}
		String str="";		
		for(int count=0;count<strArray.length;count++)
		{
			String temp=strArray[count];
			if(count==0)
			{
				str=temp;
			}
			else
			{
				str+=","+temp;
			}
		}
		return str;
	}
	public static List arrryToList(String[] strArray)
	{						
		if(strArray==null)
		{
			return new ArrayList();
		}
		List list=new ArrayList(strArray.length);		
		for(int count=0;count<strArray.length;count++)
		{
			list.add(strArray[count]);
		}
		return list;
	}
	static public String replace( String str, String pattern, 
			String replace) {
		  if (replace == null) {
			replace = "";
		  }
		  int s = 0, e = 0;
		  StringBuffer result = new StringBuffer((int) str.length()*2);
		  while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		  }
		  result.append(str.substring(s));
		  return result.toString();
	    }
	static public String firstReplace( String str, String pattern, 
				String replace) {
			  if (replace == null) {
				replace = "";
			  }
			  int s = 0, e = 0;
			  StringBuffer result = new StringBuffer((int) str.length()*2);
			  while ((e = str.indexOf(pattern, s)) >= 0) {
				result.append(str.substring(s, e));
				result.append(replace);
				s = e + pattern.length();
				break;
			  }
			  result.append(str.substring(s));
			  return result.toString();
		    }    

}
