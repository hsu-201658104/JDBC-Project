package com.hanshin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class addressbook_createtable {

	public static void main(String[] args) {
		String jdbc_driver = "com.mysql.cj.jdbc.Driver";
		String jdbc_url = "jdbc:mysql://127.0.0.1:3306/databasetest?serverTimezone=UTC";
		try {
			Class.forName(jdbc_driver).newInstance();
			Connection con = DriverManager.getConnection(jdbc_url, "root", "123456");
			Statement st = con.createStatement();
			String sql = "CREATE TABLE `databasetest`.`addressbook` (\r\n" + 
					"  `id` INT NOT NULL,\r\n" + 
					"  `name` VARCHAR(45) NULL,\r\n" + 
					"  `tel` VARCHAR(45) NULL,\r\n" + 
					"  `email` VARCHAR(45) NULL,\r\n" + 
					"  `address` VARCHAR(60) NULL,\r\n" + 
					"  PRIMARY KEY (`id`));\r\n" + 
					"";
			st.executeUpdate(sql);
		
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
