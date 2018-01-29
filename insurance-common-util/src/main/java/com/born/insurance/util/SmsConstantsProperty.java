package com.born.insurance.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.born.insurance.page.PageParamUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class SmsConstantsProperty extends  PropertyPlaceholderConfigurer {
	
	protected static final Logger logger = LoggerFactory.getLogger(PageParamUtil.class);
	
	private static Map<String, Object> ctxPropertiesMap;  
	
	 @Override  
	    protected void processProperties(  
	            ConfigurableListableBeanFactory beanFactoryToProcess,  
	            Properties props) throws BeansException {  
	        super.processProperties(beanFactoryToProcess, props);  
	        ctxPropertiesMap = new HashMap<String, Object>();  
	        for (Object key : props.keySet()) {  
	            String keyStr = key.toString();  
	            String value = props.getProperty(keyStr);  
	            ctxPropertiesMap.put(keyStr, value);  
	        }  
	    }  
	  
	    public static String getContent(String key) {  
	    	
	    	try{
	    		if(ctxPropertiesMap.get(key)==null){
		    		return "";
		    	}
		    	String content =  (String)ctxPropertiesMap.get(key);
		    	String productName = AppConstantsUtil.getProductName();
		        return content.replaceAll("#ProductName#", productName) ;
	    	}catch (Exception ex){
	    		logger.error(ex.getMessage(), ex);
	    	}
	    	return "";
	    }  

}
