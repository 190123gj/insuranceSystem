package com.born.insurance.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseUtil {

	public String fieldToProperty(String b) {
		StringBuffer sb = new StringBuffer();
		String[] bs = b.split("_");
		for(int i =0;i<bs.length;i++){
			if(i == 0){
				sb.append(bs[i]);
			}else{
				sb.append(StringUtil.capitalize(bs[i]));
			}
		}
		return sb.toString();
	}


	public String fieldToPropertyAll(String b) {
		StringBuffer sb = new StringBuffer();
		String[] bs = b.split("_");
		for(int i =0;i<bs.length;i++){
			if(i == 0){
				sb.append(StringUtil.capitalize(bs[i]));
			}else{
				sb.append(StringUtil.capitalize(bs[i]));
			}
		}
		return sb.toString();
	}

	static final Logger			log	= LoggerFactory.getLogger(DataBaseUtil.class);
	protected String			lookupSchemaName;
	DatabaseMetaData			dbData;
	private final Connection	connection;
	
	// protected boolean checkPk=false;
	public DataBaseUtil(Connection connection, String lookupSchemaName) {
		this.connection = connection;
		this.lookupSchemaName = lookupSchemaName;
	}
	
	/* ====================================================================== */
	
	/* ====================================================================== */
	
	public void makeSqlXml() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
			messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			ColumnCheckInfo[] colInfoArray = new ColumnCheckInfo[colMap.size()];
			Iterator<Map.Entry<String, ColumnCheckInfo>> colMapIt = colMap.entrySet().iterator();
			List<ColumnCheckInfo> pkList = new ArrayList<ColumnCheckInfo>();
			while (colMapIt.hasNext()) {
				Map.Entry<String, ColumnCheckInfo> entry = colMapIt.next();
				colInfoArray[entry.getValue().seq - 1] = entry.getValue();
			}
			for (int i = 0; i < colInfoArray.length; i++) {
				if (colInfoArray[i].isPk) {
					pkList.add(colInfoArray[i]);
				}
			}
			template.setInsertSql(makeInsertSql(tableName, colInfoArray, pkList));
			template.setUpdateSql(makeUpdateSql(tableName, colInfoArray, pkList));
			template.setDeleteByIdSql(makeDeleteSql(tableName, colInfoArray, pkList));
			template.setPk(fieldToProperty(pkList.get(0).columnName));
			template.setFindByIdSql(makeLoadIdSql(tableName, colInfoArray, pkList));
			template.setSelectCountSql(makeLoadCountSql(tableName, colInfoArray, pkList));
			template.setSelectSql(makeSelectSql(tableName,colInfoArray,pkList));
			template.setInsertSqlMap(makeInsertSqlMap(tableName,colInfoArray,pkList));
			generateDoc(template);

		}

		
	}



	public void makePath() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			String tableName = iterator.next();
			System.out.println(tableName);
			String fileName = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-web\\src\\main\\java\\com\\born\\insurance\\web\\controller\\"+fieldToProperty(tableName);
			File file = new File(fileName);
			file.mkdirs();
			/*fileName = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\order\\"+fieldToProperty(tableName);
			file = new File(fileName);
			file.mkdirs();
			fileName = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\info\\"+fieldToProperty(tableName);
			file = new File(fileName);
			file.mkdirs();
			fileName = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\service\\"+fieldToProperty(tableName);
			file = new File(fileName);
			file.mkdirs();
			fileName = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-biz\\src\\main\\java\\com\\born\\insurance\\biz\\service\\"+fieldToProperty(tableName);
			file = new File(fileName);
			file.mkdirs();*/
			fileName = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-assemble\\src\\main\\webapp\\WEB-INF\\velocity\\insurance\\"+fieldToProperty(tableName);
			file = new File(fileName);
			file.mkdirs();
		}
	}


	public void makeSqlServiceImpl() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			ColumnCheckInfo[] colInfoArray = new ColumnCheckInfo[colMap.size()];
			List<ColumnCheckInfo> pkList = new ArrayList<ColumnCheckInfo>();
			Iterator<Map.Entry<String, ColumnCheckInfo>> colMapIt = colMap.entrySet().iterator();
			while (colMapIt.hasNext()) {
				Map.Entry<String, ColumnCheckInfo> entry = colMapIt.next();
				colInfoArray[entry.getValue().seq - 1] = entry.getValue();
			}

			for (int i = 0; i < colInfoArray.length; i++) {
				if (colInfoArray[i].isPk) {
					pkList.add(colInfoArray[i]);
				}
			}
			for(String key : colMap.keySet()){
				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setName2(fieldToPropertyAll(key));
				if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"bigint")){
					field.setType("long");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"datetime") || StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"timestamp")){
					field.setType("Date");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"decimal")){
					field.setType("double");
				}else{
					field.setType("String");
				}
				template2.setPk(fieldToPropertyAll(pkList.get(0).columnName));
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc6(template2);

		}
	}



	public void makeSqlService() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			for(String key : colMap.keySet()){
				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setName2(fieldToPropertyAll(key));
				if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"bigint")){
					field.setType("long");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"datetime") || StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"timestamp")){
					field.setType("Date");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"decimal")){
					field.setType("double");
				}else{
					field.setType("String");
				}
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc5(template2);

		}
	}



	public void makeSqlController() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			ColumnCheckInfo[] colInfoArray = new ColumnCheckInfo[colMap.size()];
			List<ColumnCheckInfo> pkList = new ArrayList<ColumnCheckInfo>();
			Iterator<Map.Entry<String, ColumnCheckInfo>> colMapIt = colMap.entrySet().iterator();
			while (colMapIt.hasNext()) {
				Map.Entry<String, ColumnCheckInfo> entry = colMapIt.next();
				colInfoArray[entry.getValue().seq - 1] = entry.getValue();
			}

			for (int i = 0; i < colInfoArray.length; i++) {
				if (colInfoArray[i].isPk) {
					pkList.add(colInfoArray[i]);
				}
			}
			for(String key : colMap.keySet()){
				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setName2(fieldToPropertyAll(key));
				if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"bigint")){
					field.setType("long");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"datetime") || StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"timestamp")){
					field.setType("Date");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"decimal")){
					field.setType("double");
				}else{
					field.setType("String");
				}
				template2.setPk("id");
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateController(template2);
		}
	}



	public void makeSqlQueryOrder() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			String tableName = iterator.next();
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			for(String key : colMap.keySet()){
				if(StringUtil.equalsIgnoreCase("user_id",key)){
					continue;
				}
				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setName2(fieldToPropertyAll(key));
				if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"bigint")){
					field.setType("long");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"datetime") || StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"timestamp")){
					field.setType("Date");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"decimal")){
					field.setType("double");
				}else{
					field.setType("String");
				}
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc4(template2);

		}
	}


	public void makeSqlList() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			for(String key : colMap.keySet()){

				Field field = new Field();
				field.setName("$!{item."+fieldToProperty(key)+"}");
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc7(template2);

		}


	}


	public void makeSqlAdd() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			for(String key : colMap.keySet()){

				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc8(template2);

		}


	}



	public void makeSqlOrder() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			for(String key : colMap.keySet()){
				if(StringUtil.equalsIgnoreCase("user_id",key)){
					continue;
				}
				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setName2(fieldToPropertyAll(key));
				if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"bigint")){
					field.setType("long");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"datetime") || StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"timestamp")){
					field.setType("Date");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"decimal")){
					field.setType("double");
				}else{
					field.setType("String");
				}
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc3(template2);

		}


	}


	public void makeSqlInfo() {
		List<String> messages = new ArrayList<String>();
		TreeSet<String> tableNames = this.getTableNames(messages);
		Map<String, Map<String, ColumnCheckInfo>> colInfo = this.getColumnInfo(tableNames, true,
				messages);
		Iterator<String> iterator = tableNames.iterator();
		while (iterator.hasNext()) {
			Template template = new Template();
			String tableName = iterator.next();
			template.setTableName(tableName);
			Template2  template2= new Template2();
			template2.setClassName(fieldToPropertyAll(tableName));
			Map<String, ColumnCheckInfo> colMap = colInfo.get(tableName);
			for(String key : colMap.keySet()){
				Field field = new Field();
				field.setName(fieldToProperty(key));
				field.setName2(fieldToPropertyAll(key));
				if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"bigint")){
					field.setType("long");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"datetime") || StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"timestamp")){
					field.setType("Date");
				}else if(StringUtil.equalsIgnoreCase(colMap.get(key).typeName,"decimal")){
					field.setType("double");
				}else{
					field.setType("String");
				}
				field.setRemarks(colMap.get(key).remarks);
				template2.getFields().add(field);
			}
			generateDoc2(template2);

		}


	}


	public void generateDoc(Template t) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("xml.xml", "UTF-8");

			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			context.put("template", t);
			//确定静态文档在共享文件目录的完整存储路径
			String filePath = "F:\\workspace3\\insuranceSystem\\DOC\\项目管理\\" +t.getTableName()+ ".xml";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}


	public void generateDoc8(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("addForm.vm", "UTF-8");

			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-assemble\\src\\main\\webapp\\WEB-INF\\velocity\\insurance\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\add"+t2.getClassName() + ".vm";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}




	public void generateDoc7(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("list.vm", "UTF-8");

			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-assemble\\src\\main\\webapp\\WEB-INF\\velocity\\insurance\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\list"+t2.getClassName() + ".vm";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}



	public void generateDoc3(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("order.xml", "UTF-8");

			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\order\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\"+t2.getClassName() + "Order.java";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}



	public void generateController(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("controller.vm", "UTF-8");

			VelocityContext context = new VelocityContext();
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-web\\src\\main\\java\\com\\born\\insurance\\web\\controller\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\"+t2.getClassName() + "Controller.java";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}


	public void generateDoc4(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("queryOrder.xml", "UTF-8");

			VelocityContext context = new VelocityContext();
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\order\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\"+t2.getClassName() + "QueryOrder.java";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}



	public void generateDoc6(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("serviceImpl.xml", "UTF-8");
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-biz\\src\\main\\java\\com\\born\\insurance\\biz\\service\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\"+t2.getClassName() + "ServiceImpl.java";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}



	public void generateDoc5(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("service.xml", "UTF-8");

			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\service\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\"+t2.getClassName() + "Service.java";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}


	public void generateDoc2(Template2 t2) {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			Properties properties = new Properties();
			//指定生成静态文档需要的模板文件所在的目录
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,
					"F:\\workspace3\\code\\template");
			VelocityEngine engine = new VelocityEngine();
			//初始化模板引擎
			engine.init(properties);
			//根据API_TEMPLATE_FILE读取生成静态文档需要的模板文件
			org.apache.velocity.Template template = engine.getTemplate("info.xml", "UTF-8");

			VelocityContext context = new VelocityContext();
			//将生成文档需要的数据apiModel放入模板引擎的上下文中
			t2.setPage(StringUtil.uncapitalize(t2.getClassName()));
			context.put("template", t2);
			//确定静态文档在共享文件目录的完整存储路径

			String filePath = "F:\\workspace3\\insuranceSystem\\trunk\\insurance-facade\\src\\main\\java\\com\\born\\insurance\\ws\\info\\"+StringUtil.uncapitalize(t2.getClassName()) +"\\"+t2.getClassName() + "Info.java";
			File file = new File(filePath);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));// 设置写入的文件编码,解决中文问题
			//将数据与模板merge，并写入到静态文档
			template.merge(context, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					//打印日志
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//打印日志
				}
			}
		}
	}

	private String makeDeleteSql(String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		StringBuffer sb = new StringBuffer();
		// TODO Auto-generated method stub
		sb.append("DELETE FROM " + tableName + " WHERE ");
		for (int i = 0; i < pkList.size(); i++) {
			if (i == 0)
				sb.append(pkList.get(i).columnName + "=?");
			else
				sb.append(" AND " + pkList.get(i).columnName + "=?");
		}

		return sb.toString();
	}


	private String makeSelectSql(String tableName, ColumnCheckInfo[] colInfoArray,
								 List<ColumnCheckInfo> pkList) {
		StringBuffer sb = new StringBuffer();
		// TODO Auto-generated method stub
		sb.append("SELECT  ");
		//		int realIndex = 0;
		for (int i = 0; i < colInfoArray.length; i++) {

			if (i == 0)
				sb.append(colInfoArray[i].columnName + " ");
			else
				sb.append("," + colInfoArray[i].columnName + " ");
			//			realIndex++;
		}
		sb.append(" FROM " + tableName);
	   return sb.toString();
	}
	
	private String makeLoadIdSql(String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		StringBuffer sb = new StringBuffer();
		// TODO Auto-generated method stub
		sb.append("SELECT  ");
		//		int realIndex = 0;
		for (int i = 0; i < colInfoArray.length; i++) {
			
			if (i == 0)
				sb.append(colInfoArray[i].columnName + " ");
			else
				sb.append("," + colInfoArray[i].columnName + " ");
			//			realIndex++;
		}
		sb.append(" FROM " + tableName);
		sb.append(" WHERE ");
		for (int i = 0; i < pkList.size(); i++) {
			if (i == 0)
				sb.append(pkList.get(i).columnName + "=?");
			else
				sb.append(" AND " + pkList.get(i).columnName + "=?");
		}

		return sb.toString();
	}
	
	private String makeLoadCountSql( String tableName,
									ColumnCheckInfo[] colInfoArray, List<ColumnCheckInfo> pkList) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  count(*) FROM " + tableName);
		return sb.toString();
	}
	
	private String makeUpdateSql( String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE " + tableName + " SET ");
		int realIndex = 0;
		for (int i = 0; i < colInfoArray.length; i++) {
			if (colInfoArray[i].isPk)
				continue;
			
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			
			if ("raw_add_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			
			if (realIndex == 0)
				sb.append(colInfoArray[i].columnName + "=?");
			else
				sb.append("," + colInfoArray[i].columnName + "=?");
			realIndex++;
		}
		sb.append(" WHERE ");
		for (int i = 0; i < pkList.size(); i++) {
			if (i == 0)
				sb.append(pkList.get(i).columnName + "=?");
			else
				sb.append(" AND " + pkList.get(i).columnName + "=?");
		}

		return sb.toString();
	}



	private String makeInsertSqlMap(String tableName, ColumnCheckInfo[] colInfoArray,
								 List<ColumnCheckInfo> pkList) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO " + tableName);
		sb.append("(");
		for (int i = 0; i < colInfoArray.length; i++) {
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			if (i == 0)
				sb.append(colInfoArray[i].columnName);
			else
				sb.append("," + colInfoArray[i].columnName);
		}
		sb.append(")");
		sb.append(" VALUES (");
		for (int i = 0; i < colInfoArray.length; i++) {
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			if (i == 0)
				sb.append("#").append(fieldToProperty(colInfoArray[i].columnName)).append("#");
			else
				sb.append(",#").append(fieldToProperty(colInfoArray[i].columnName)).append("#");
		}
		sb.append(")");
		return sb.toString();
	}
	
	private String makeInsertSql(String tableName, ColumnCheckInfo[] colInfoArray,
								List<ColumnCheckInfo> pkList) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO " + tableName);
		sb.append("(");
		for (int i = 0; i < colInfoArray.length; i++) {
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			if (i == 0)
				sb.append(colInfoArray[i].columnName);
			else
				sb.append("," + colInfoArray[i].columnName);
		}
		sb.append(")");
		sb.append(" VALUES (");
		for (int i = 0; i < colInfoArray.length; i++) {
			if ("raw_update_time".equalsIgnoreCase(colInfoArray[i].columnName))
				continue;
			if (i == 0)
				sb.append("?");
			else
				sb.append(",?");
		}
		sb.append(")");
		return sb.toString();
	}
	
	private void writerFile(String sb) throws IOException {
		BufferedWriter bw = null;
		File file = new File("D:\\dataSql.txt");
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
			bw.write(sb);
			bw.newLine();
		} catch (IOException e) {
			log.error("writerTemp error:", e);
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
		}
	}
	
	public void printDbMiscData(DatabaseMetaData dbData, Connection con) {
		if (dbData == null) {
			return;
		}
		// Database Info
		if (log.isInfoEnabled()) {
			try {
				log.info("Database Product Name is " + dbData.getDatabaseProductName());
				log.info("Database Product Version is " + dbData.getDatabaseProductVersion());
			} catch (SQLException sqle) {
				log.warn("Unable to get Database name & version information");
			}
		}
		// JDBC Driver Info
		if (log.isInfoEnabled()) {
			try {
				log.info("Database Driver Name is " + dbData.getDriverName());
				log.info("Database Driver Version is " + dbData.getDriverVersion());
				log.info("Database Driver JDBC Version is " + dbData.getJDBCMajorVersion() + "."
							+ dbData.getJDBCMinorVersion());
			} catch (SQLException sqle) {
				log.warn("Unable to get Driver name & version information", sqle);
			} catch (AbstractMethodError ame) {
				log.warn("Unable to get Driver JDBC Version", ame);
			} catch (Exception e) {
				log.warn("Unable to get Driver JDBC Version", e);
			}
		}
		// Db/Driver support settings
		if (log.isInfoEnabled()) {
			try {
				log.info("Database Setting/Support Information (those with a * should be true):");
				log.info("- supports transactions    [" + dbData.supportsTransactions() + "]*");
				log.info("- isolation None           ["
							+ dbData.supportsTransactionIsolationLevel(Connection.TRANSACTION_NONE)
							+ "]");
				log.info("- isolation ReadCommitted  ["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED)
							+ "]");
				log.info("- isolation ReadUncommitted["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED)
							+ "]");
				log.info("- isolation RepeatableRead ["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ)
							+ "]");
				log.info("- isolation Serializable   ["
							+ dbData
								.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)
							+ "]");
				log.info("- default fetchsize        [" + con.createStatement().getFetchSize()
							+ "]");
				log.info("- forward only type        ["
							+ dbData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY) + "]");
				log.info("- scroll sensitive type    ["
							+ dbData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE) + "]");
				log.info("- scroll insensitive type  ["
							+ dbData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE) + "]");
				log.info("- is case sensitive        [" + dbData.supportsMixedCaseIdentifiers()
							+ "]");
				log.info("- stores LowerCase         [" + dbData.storesLowerCaseIdentifiers() + "]");
				log.info("- stores MixedCase         [" + dbData.storesMixedCaseIdentifiers() + "]");
				log.info("- stores UpperCase         [" + dbData.storesUpperCaseIdentifiers() + "]");
				log.info("- max table name length    [" + dbData.getMaxTableNameLength() + "]");
				log.info("- max column name length   [" + dbData.getMaxColumnNameLength() + "]");
				log.info("- max schema name length   [" + dbData.getMaxSchemaNameLength() + "]");
				log.info("- concurrent connections   [" + dbData.getMaxConnections() + "]");
				log.info("- concurrent statements    [" + dbData.getMaxStatements() + "]");
				log.info("- ANSI SQL92 Entry         [" + dbData.supportsANSI92EntryLevelSQL()
							+ "]");
				log.info("- ANSI SQL92 Itermediate   [" + dbData.supportsANSI92IntermediateSQL()
							+ "]");
				log.info("- ANSI SQL92 Full          [" + dbData.supportsANSI92FullSQL() + "]");
				log.info("- ODBC SQL Grammar Core    [" + dbData.supportsCoreSQLGrammar() + "]");
				log.info("- ODBC SQL Grammar Extended[" + dbData.supportsExtendedSQLGrammar() + "]");
				log.info("- ODBC SQL Grammar Minimum [" + dbData.supportsMinimumSQLGrammar() + "]");
				log.info("- outer joins              [" + dbData.supportsOuterJoins() + "]*");
				log.info("- limited outer joins      [" + dbData.supportsLimitedOuterJoins() + "]");
				log.info("- full outer joins         [" + dbData.supportsFullOuterJoins() + "]");
				log.info("- group by                 [" + dbData.supportsGroupBy() + "]*");
				log.info("- group by not in select   [" + dbData.supportsGroupByUnrelated() + "]");
				log.info("- column aliasing          [" + dbData.supportsColumnAliasing() + "]");
				log.info("- order by not in select   [" + dbData.supportsOrderByUnrelated() + "]");
				// this doesn't work in HSQLDB, other databases?
				// log.info("- named parameters [" +
				// dbData.supportsNamedParameters() + "]");
				log.info("- alter table add column   [" + dbData.supportsAlterTableWithAddColumn()
							+ "]*");
				log.info("- non-nullable column      [" + dbData.supportsNonNullableColumns()
							+ "]*");
			} catch (Exception e) {
				log.warn("Unable to get misc. support/setting information", e);
			}
		}
	}
	
	public DatabaseMetaData getDatabaseMetaData(Connection connection, Collection<String> messages) {
		if (connection == null) {
			connection = getConnection();
		}
		DatabaseMetaData dbData = null;
		try {
			dbData = connection.getMetaData();
		} catch (SQLException sqle) {
			String message = "Unable to get database meta data... Error was:" + sqle.toString();
			log.error(message, sqle);
			if (messages != null) {
				messages.add(message);
			}
			return null;
		}
		
		if (dbData == null) {
			log.warn("Unable to get database meta data; method returned null");
		}
		
		return dbData;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public TreeSet<String> getTableNames(Collection<String> messages) {
		Connection connection = null;
		connection = getConnection();
		
		dbData = this.getDatabaseMetaData(connection, messages);
		if (dbData == null) {
			return null;
		}
		
		printDbMiscData(dbData, connection);
		if (log.isInfoEnabled())
			log.info("Getting Table Info From Database");
		
		// get ALL tables from this database
		TreeSet<String> tableNames = new TreeSet<String>();
		ResultSet tableSet = null;
		
		try {
			String[] types = { "TABLE", "VIEW", "ALIAS", "SYNONYM" };
			lookupSchemaName = getSchemaName(dbData);
			tableSet = dbData.getTables(null, lookupSchemaName, null, types);
			if (tableSet == null) {
				log.warn("getTables returned null set");
			}
		} catch (SQLException sqle) {
			String message = "Unable to get list of table information, let's try the create anyway... Error was:"
								+ sqle.toString();
			log.error(message, sqle);
			if (messages != null)
				messages.add(message);
			
			try {
				connection.close();
			} catch (SQLException sqle2) {
				String message2 = "Unable to close database connection, continuing anyway... Error was:"
									+ sqle2.toString();
				log.error(message2);
				if (messages != null)
					messages.add(message2);
			}
			// we are returning an empty set here because databases like SapDB
			// throw an exception when there are no tables in the database
			return tableNames;
		}
		
		try {
			//			boolean needsUpperCase = false;
			//			try {
			//				needsUpperCase = dbData.storesLowerCaseIdentifiers()
			//									|| dbData.storesMixedCaseIdentifiers();
			//			} catch (SQLException sqle) {
			//				String message = "Error getting identifier case information... Error was:"
			//									+ sqle.toString();
			//				log.error(message, sqle);
			//				if (messages != null)
			//					messages.add(message);
			//			}
			while (tableSet.next()) {
				try {
					String tableName = tableSet.getString("TABLE_NAME");
					// for those databases which do not return the schema name
					// with the table name (pgsql 7.3)
					boolean appendSchemaName = false;
					if (tableName != null && lookupSchemaName != null
						&& !tableName.startsWith(lookupSchemaName)) {
						appendSchemaName = true;
					}
					//					if (needsUpperCase && tableName != null) {
					//						tableName = tableName.toUpperCase();
					//					}
					if (appendSchemaName) {
						tableName = lookupSchemaName + "." + tableName;
					}
					// NOTE: this may need a toUpperCase in some cases, keep an
					// eye on it, okay for now just do a compare with
					// equalsIgnoreCase
					String tableType = tableSet.getString("TABLE_TYPE");
					// only allow certain table types
					if (tableType != null && !"TABLE".equalsIgnoreCase(tableType)
						&& !"VIEW".equalsIgnoreCase(tableType)
						&& !"ALIAS".equalsIgnoreCase(tableType) &&
						
						!"SYNONYM".equalsIgnoreCase(tableType)) {
						continue;
					}
					
					// String remarks = tableSet.getString("REMARKS");
					tableNames.add(tableName);
					// if (log.isInfoEnabled()) log.info("Found table named [" +
					// tableName + "] of type [" + tableType +
					// "] with remarks: " + remarks);
				} catch (SQLException sqle) {
					String message = "Error getting table information... Error was:"
										+ sqle.toString();
					log.error(message, sqle);
					if (messages != null)
						messages.add(message);
					continue;
				}
			}
		} catch (SQLException sqle) {
			String message = "Error getting next table information... Error was:" + sqle.toString();
			log.error(message, sqle);
			if (messages != null)
				messages.add(message);
		} finally {
			try {
				tableSet.close();
			} catch (SQLException sqle) {
				String message = "Unable to close ResultSet for table list, continuing anyway... Error was:"
									+ sqle.toString();
				log.error(message, sqle);
				if (messages != null)
					messages.add(message);
			}
			
			//			try {
			//				connection.close();
			//			} catch (SQLException sqle) {
			//				String message = "Unable to close database connection, continuing anyway... Error was:" + sqle.toString();
			//				log.error(message, sqle);
			//				if (messages != null)
			//					messages.add(message);
			//			}
		}
		return tableNames;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Map<String, ColumnCheckInfo>> getColumnInfo(Set<String> tableNames,
																	boolean getPks,
																	Collection<String> messages) {
		// if there are no tableNames, don't even try to get the columns
		if (tableNames.size() == 0) {
			return new HashMap<String, Map<String, ColumnCheckInfo>>();
		}
		
		Connection connection = null;
		connection = getConnection();
		try {
			
			if (log.isInfoEnabled())
				log.info("Getting Column Info From Database");
			
			Map<String, Map<String, ColumnCheckInfo>> colInfo = new HashMap<String, Map<String, ColumnCheckInfo>>();
			String lookupSchemaName = this.getSchemaName(null);
			boolean isUserName = false;
			try {
				
				boolean needsUpperCase = false;
				try {
					needsUpperCase = dbData.storesLowerCaseIdentifiers()
										|| dbData.storesMixedCaseIdentifiers();
				} catch (SQLException sqle) {
					String message = "Error getting identifier case information... Error was:"
										+ sqle.toString();
					log.error(message, sqle);
					if (messages != null)
						messages.add(message);
				}
				
				boolean foundCols = false;
				Map<String, Map<String, ColumnCheckInfo>> kpMap = new HashMap<String, Map<String, ColumnCheckInfo>>();
				ResultSet rsCols = dbData.getColumns(null, lookupSchemaName, null, null);
				if (!rsCols.next()) {
					try {
						rsCols.close();
					} catch (SQLException sqle) {
						String message = "Unable to close ResultSet for column list, continuing anyway... Error was:"
											+ sqle.toString();
						log.error(message, sqle);
						if (messages != null)
							messages.add(message);
					}
					rsCols = dbData.getColumns(null, lookupSchemaName, "%", "%");
					if (!rsCols.next()) {
						// TODO: now what to do? I guess try one table name at a
						// time...
					} else {
						foundCols = true;
					}
				} else {
					foundCols = true;
				}
				if (foundCols) {
					do {
						try {
							ColumnCheckInfo ccInfo = new ColumnCheckInfo();
							
							ccInfo.tableName = ColumnCheckInfo.fixupTableName(
								rsCols.getString("TABLE_NAME"), lookupSchemaName, needsUpperCase);
							
							// ignore the column info if the table name is not
							// in the list we are concerned with
							if (!tableNames.contains(ccInfo.tableName)) {
								continue;
							}
							ccInfo.remarks =rsCols.getString("REMARKS");
							ccInfo.columnName = rsCols.getString("COLUMN_NAME");
							//							if (needsUpperCase && ccInfo.columnName != null) {
							//								ccInfo.columnName = ccInfo.columnName.toUpperCase();
							//							}
							// NOTE: this may need a toUpperCase in some cases,
							// keep an eye on it
							ccInfo.typeName = rsCols.getString("TYPE_NAME");
							ccInfo.columnSize = rsCols.getInt("COLUMN_SIZE");
							ccInfo.decimalDigits = rsCols.getInt("DECIMAL_DIGITS");
							// NOTE: this may need a toUpperCase in some cases,
							// keep an eye on it
							ccInfo.isNullable = rsCols.getString("IS_NULLABLE");
							if (getPks) {
								if (!kpMap.containsKey(ccInfo.tableName)) {
									ResultSet rsPks = null;
									Map<String, ColumnCheckInfo> pkcolMap = new HashMap<String, ColumnCheckInfo>();
									try {
										String pkTabbleName = ccInfo.tableName;
										if (isUserName) {
											int dotIndex = ccInfo.tableName.indexOf(".");
											if (dotIndex >= 0) {
												pkTabbleName = ccInfo.tableName
													.substring(dotIndex + 1);
											}
										}
										rsPks = dbData.getPrimaryKeys(null, lookupSchemaName,
											pkTabbleName);
										while (rsPks.next()) {
											ColumnCheckInfo ccInfoTemp = new ColumnCheckInfo();
											ccInfoTemp.columnName = rsPks.getString("COLUMN_NAME");
											ccInfoTemp.pkSeq = rsPks.getShort("KEY_SEQ");
											ccInfoTemp.pkName = rsPks.getString("PK_NAME");
											pkcolMap.put(ccInfoTemp.columnName, ccInfoTemp);
										}
									} catch (Exception e) {
										String message = "Unable to close ResultSet for primary key list, continuing anyway... Error was:"
															+ e.toString();
										log.error(message, e);
									}
									kpMap.put(ccInfo.tableName, pkcolMap);
								}
								Map pkcolMap = kpMap.get(ccInfo.tableName);
								if (pkcolMap.containsKey(ccInfo.columnName)) {
									ColumnCheckInfo ccInfoTemp = (ColumnCheckInfo) pkcolMap
										.get(ccInfo.columnName);
									ccInfo.isPk = true;
									ccInfo.pkSeq = ccInfoTemp.pkSeq;
									ccInfo.pkName = ccInfoTemp.pkName;
								}
								
							}
							Map<String, ColumnCheckInfo> tableColInfo = colInfo
								.get(ccInfo.tableName);
							if (tableColInfo == null) {
								tableColInfo = new HashMap<String, ColumnCheckInfo>();
								colInfo.put(ccInfo.tableName, tableColInfo);
							}
							ccInfo.seq = tableColInfo.size() + 1;
							tableColInfo.put(ccInfo.columnName, ccInfo);
							//							List<String> fieldList = new ArrayList<String>();
							
						} catch (SQLException sqle) {
							String message = "Error getting column info for column. Error was:"
												+ sqle.toString();
							log.error(message, sqle);
							if (messages != null)
								messages.add(message);
							continue;
						}
					} while (rsCols.next());
				}
				
				try {
					rsCols.close();
				} catch (SQLException sqle) {
					String message = "Unable to close ResultSet for column list, continuing anyway... Error was:"
										+ sqle.toString();
					log.error(message, sqle);
					if (messages != null)
						messages.add(message);
				}
			} catch (SQLException sqle) {
				String message = "Error getting column meta data for Error was:" + sqle.toString()
									+ ". Not checking columns.";
				log.error(message, sqle);
				if (messages != null)
					messages.add(message);
				// we are returning an empty set in this case because databases
				// like SapDB throw an exception when there are no tables in the
				// database
				// colInfo = null;
			}
			return colInfo;
		} finally {
			if (connection != null) {
				//				try {
				//					//connection.close();
				//				} catch (SQLException sqle) {
				//					String message = "Unable to close database connection, continuing anyway... Error was:" + sqle.toString();
				//					log.error(message, sqle);
				//					if (messages != null)
				//						messages.add(message);
				//				}
			}
		}
	}
	
	public String getSchemaName(DatabaseMetaData dbData) {
		// if (this.datasourceInfo.useSchemas &&
		// dbData.supportsSchemasInTableDefinitions()) {
		// if (this.datasourceInfo.schemaName != null &&
		// this.datasourceInfo.schemaName.length() > 0) {
		// return this.datasourceInfo.schemaName;
		// } else {
		// return dbData.getUserName();
		// }
		// }
		return this.lookupSchemaName;
	}

	public static class Field{
		private String remarks;
		private String name;
		private String name2;
		private String type;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName2() {
			return name2;
		}

		public void setName2(String name2) {
			this.name2 = name2;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	}


	public static class Template2{
		String page;
		String className;
		String pk;
		List<Field>  fields = new ArrayList<Field>();

		public List<Field> getFields() {
			return fields;
		}

		public void setFields(List<Field> fields) {
			this.fields = fields;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getPage() {
			return page;
		}

		public void setPage(String page) {
			this.page = page;
		}

		public String getPk() {
			return pk;
		}

		public void setPk(String pk) {
			this.pk = pk;
		}
	}


	public static class Template{
		private String tableName;
		private String insertSql;
		private String updateSql;
		private String findByIdSql;
		private String deleteByIdSql;
		private String selectSql;
		private String selectCountSql;
		private String insertSqlMap;
		private String pk;

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getUpdateSql() {
			return updateSql;
		}

		public void setUpdateSql(String updateSql) {
			this.updateSql = updateSql;
		}

		public String getFindByIdSql() {
			return findByIdSql;
		}

		public void setFindByIdSql(String findByIdSql) {
			this.findByIdSql = findByIdSql;
		}

		public String getDeleteByIdSql() {
			return deleteByIdSql;
		}

		public void setDeleteByIdSql(String deleteByIdSql) {
			this.deleteByIdSql = deleteByIdSql;
		}

		public String getSelectSql() {
			return selectSql;
		}

		public void setSelectSql(String selectSql) {
			this.selectSql = selectSql;
		}

		public String getSelectCountSql() {
			return selectCountSql;
		}

		public void setSelectCountSql(String selectCountSql) {
			this.selectCountSql = selectCountSql;
		}

		public String getInsertSql() {
			return insertSql;
		}

		public void setInsertSql(String insertSql) {
			this.insertSql = insertSql;
		}

		public String getInsertSqlMap() {
			return insertSqlMap;
		}

		public void setInsertSqlMap(String insertSqlMap) {
			this.insertSqlMap = insertSqlMap;
		}

		public String getPk() {
			return pk;
		}

		public void setPk(String pk) {
			this.pk = pk;
		}
	}
	
	/* ====================================================================== */
	
	/* ====================================================================== */
	public static class ColumnCheckInfo {
		public String	tableName;
		public String	columnName;
		public String	typeName;
		public int		columnSize;
		public int		decimalDigits;
		public boolean	isPk	= false;	// YES/NO or "" = ie nobody knows
		public String	isNullable;		// YES/NO or "" = ie nobody knows
		public int		pkSeq;
		public String	pkName;
		public int		seq;
		public String remarks;
		
		public static String fixupTableName(String rawTableName, String lookupSchemaName,
											boolean needsUpperCase) {
			String tableName = rawTableName;
			// for those databases which do not return the schema name with the
			// table name (pgsql 7.3)
			boolean appendSchemaName = false;
			if (tableName != null && lookupSchemaName != null
				&& !tableName.startsWith(lookupSchemaName)) {
				appendSchemaName = true;
			}
			//			if (needsUpperCase && tableName != null) {
			//				tableName = tableName.toUpperCase();
			//			}
			if (appendSchemaName) {
				tableName = lookupSchemaName + "." + tableName;
			}
			return tableName;
		}
	}
	
	public static class ReferenceCheckInfo {
		public String	pkTableName;
		
		/**
		 * Comma separated list of column names in the related tables primary
		 * key
		 */
		public String	pkColumnName;
		public String	fkName;
		public String	fkTableName;
		
		/**
		 * Comma separated list of column names in the primary tables foreign
		 * keys
		 */
		public String	fkColumnName;
		
		@Override
		public String toString() {
			return "FK Reference from table " + fkTableName + " called " + fkName
					+ " to PK in table " + pkTableName;
		}
	}
}
