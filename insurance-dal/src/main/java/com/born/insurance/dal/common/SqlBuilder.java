/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.dal.common;

import java.sql.Connection;
import java.sql.SQLException;

import com.born.insurance.util.DataBaseUtil;

/**
 * 
 * @Filename SqlBuilder.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-3-20</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public class SqlBuilder {
	public static void main(String[] args) {
		String dataBase = "born_insurance";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = java.sql.DriverManager.getConnection(
				"jdbc:mysql://192.169.2.245:5600/" + dataBase, "root", "efHK1dKh3sd");
			DataBaseUtil dbu = new DataBaseUtil(con, null);
			dbu.makeSqlQueryOrder();
			con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
