package com.born.insurance.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class XmlUtil {
	static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);
	
	public static Document parseXML(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return doc;
	}
	
	public static Element createDoc(Element parentElement, String tageName) {
		Element docRoot = null;
		if (parentElement == null) {
			Document document = DocumentHelper.createDocument();
			document.setXMLEncoding("utf-8");
			docRoot = document.addElement(tageName);
		} else {
			docRoot = parentElement.addElement(tageName);
		}
		return docRoot;
	}
}
