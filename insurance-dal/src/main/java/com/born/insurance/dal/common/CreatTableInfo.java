package com.born.insurance.dal.common;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.filechooser.FileSystemView;

/**
 * 生成表的列名加注释
 * 
 * @author abing
 * */
public class CreatTableInfo {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String pwd = "123456";
	private static final String user = "root";
	private static final String sql_name = "born_fcs_fbpm";//库名
	private static final String url = "jdbc:mysql://192.169.2.245:3306/" + sql_name + "?user="
										+ user + "&password=" + pwd
										+ "&useUnicode=true&characterEncoding=UTF-8";
	private static Connection getConnection = null;
	
	public static void main(String[] args) {
		//设置表信息
		String table_Name = "charge_repay_plan_detail";//目标表名
		String[] money_clumn = { "" };//金额字段列名
		
		create(table_Name, money_clumn);
	}
	
	//开始生成数据
	public static void create(String table_Name, String[] moneyClumns) {
		
		getConnection = getConnections();
		try {
			DatabaseMetaData dbmd = getConnection.getMetaData();
			ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				if (tableName.equalsIgnoreCase(table_Name)) {
					ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
					System.out.println("生成表：" + tableName + "的字段和注释信息:" + clumnName(tableName)
										+ "Info");
					while (rs.next()) {
						System.out.println("/**" + rs.getString("REMARKS") + "*/");
						System.out.println("private "
											+ type(moneyClumns, rs.getString("COLUMN_NAME"),
												rs.getString("TYPE_NAME")) + " "
											+ clumnName(rs.getString("COLUMN_NAME")) + ";");
					}
				}
			}
			FileSystemView fsv = FileSystemView.getFileSystemView();
			String path = fsv.getHomeDirectory().toString();//获取当前用户桌面路径
			File directory = new File(path);
			if (directory.exists()) {
			} else {
				directory.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 处理列名 */
	public static String clumnName(String name) {
		if (name.indexOf("_") > -1) {
			String[] names = name.split("_");
			name = "";
			int i = 0;
			for (String s : names) {
				if (i == 0) {
					name += s;
					i++;
				} else {
					name += s.substring(0, 1).toUpperCase() + s.substring(1);
				}
				
			}
		}
		return name;
	}
	
	/** 处理类型 */
	public static String type(String[] money_cloumn, String clumnName, String type) {
		for (String s : money_cloumn) {
			if (clumnName.equals(s)) {
				return "Money";
			}
		}
		switch (type) {
			case "BIGINT":
				type = "long";
				break;
			case "VARCHAR":
				type = "String";
				break;
			case "TIMESTAMP":
				type = "Date";
				break;
			case "TIME":
				type = "Time";
				break;
			case "ENUM":
				type = "String";
				break;
			case "DOUBLE":
				type = "double";
				break;
			case "INT":
				type = "int";
				break;
		}
		return type;
		
	}
	
	public static Connection getConnections() {
		try {
			Class.forName(driver);
			getConnection = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getConnection;
	}
	
}