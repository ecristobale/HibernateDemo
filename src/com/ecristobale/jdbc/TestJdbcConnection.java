package com.ecristobale.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbcConnection {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:mysql://localhost:3306/hb_demo_schema?useSSL=false";
		String user = "hibernatedemo";
		String pw = "hibernatedemo";
		// String pw = "fakepw";
		try {
			System.out.println("Conecting to Database: " + jdbcUrl);
			
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, pw);
			
			System.out.println("Connection successful!!!");
			
			myConn.close();
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
	}

}
