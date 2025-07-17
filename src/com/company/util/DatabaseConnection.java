package com.company.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	public static Connection getConnection() {
		Connection connection = null;
		String url = "jdbc:mysql://localhost:3306/bankwise?useSSL=false";
		String username = "root";
		String password = "admin@123";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Failed to connect to the database...");
			e.printStackTrace();
		}
		return connection;
	}
}