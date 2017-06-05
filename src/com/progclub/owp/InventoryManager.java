package com.progclub.owp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class InventoryManager {
	public static void main(String[] args)
	{
		// Create a data source to connect to our MySQL database
		MysqlDataSource mysqlSource = new MysqlDataSource();
		mysqlSource.setDatabaseName("inventorydb");
		mysqlSource.setUser("inventoryuser");
		mysqlSource.setPassword("inventorypass");
		mysqlSource.setServerName("localhost");
		try {
			// Try to connect to the database, it will throw exceptions on error
			// and hence we need to do this in a try-catch block
			Connection conn = mysqlSource.getConnection();
			Statement stmt = conn.createStatement();
			
			System.out.println("Connection successful! :)");	// Successfully connected
			
			// Close the connection properly
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! :(");		// Failed connection
			e.printStackTrace();
		}		
	}

}
