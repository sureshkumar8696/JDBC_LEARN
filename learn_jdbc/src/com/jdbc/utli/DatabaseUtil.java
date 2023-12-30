package com.jdbc.utli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/jdbcdb";
	
	private static final String USERNAME = "root";
	private static final String PASSWARD = "Suresht@k8696";
	
	public DatabaseUtil() {
		try {
			Class.forName(DRIVER_PATH);
		}catch(Exception e) {
			System.out.println("Something went wrong! " + e);
		}
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWARD);
	}
	
}
