package com.progclub.owp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/*
 *	This is a static helper class to do all the database stuff
 *  It helps us avoid having database code all over other files 
 */
public class DatabaseHelper
{
	// Some constants that will be used throughout
	public final static String TABLE_NAME = "InventoryTable";
	public final static String COLUMN_ID = "ID";
	public final static String COLUMN_NAME = "Name";
	public final static String COLUMN_PRICE = "PricePerPiece";
	public final static String COLUMN_QUANTITY = "QuantityAvailable";
	public final static String[] COLUMN_NAMES = {COLUMN_ID, COLUMN_NAME, COLUMN_PRICE, COLUMN_QUANTITY};
	
	// A standard command to add rows into the table
	private static final String ADD_QUERY = "INSERT INTO " + TABLE_NAME + " ( " + COLUMN_NAME + ", " +
            								COLUMN_PRICE + ", " + COLUMN_QUANTITY + ") VALUES (?, ?, ?);";

	private static Connection conn;		// Database connection

	public static boolean getConnection(String username, String password)
	{
		// Create a data source to connect to our MySQL database
		MysqlDataSource mysqlSource = new MysqlDataSource();
		mysqlSource.setDatabaseName("inventorydb");
		mysqlSource.setUser(username);
		mysqlSource.setPassword(password);
		mysqlSource.setServerName("localhost");
		try
		{
			// Try to connect to the database, it will throw exceptions on error
			// and hence we need to do this in a try-catch block
			conn = mysqlSource.getConnection();
			
			createTableIfRequired();	// Create the necessary table if required
			
			return true;
		}
		catch (SQLException e)
		{
			// Failed connection
			return false;
		}
	}
	
	public static ResultSet getResult(String query)
	{
		try
		{
			// Execute the query and get the result. We need a Statement from the connection to do so.
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			stmt.close();
			return rs;
		}
		catch (SQLException e)
		{
			// Error
			return null;
		}
	}
	
	public static void executeQuery(String query)
	{
		try
		{
			// Execute a query and don't expect a result
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException e)
		{
			// Error
		}
	}
	
	public static void createTableIfRequired()
	{
		try
		{
			DatabaseMetaData dbMD = conn.getMetaData();		// Get the metadata/info about connection
			// Search a table with the name TABLE_NAME
			ResultSet rs = dbMD.getTables(null,  null, TABLE_NAME, null);
			if(rs.next())
				return;		// Table exists if ResultSet has any element
			// Create table with appropriate columns
			String query = "CREATE TABLE " + TABLE_NAME + " ( " +
						  	COLUMN_ID + " INTEGER NOT NULL AUTO_INCREMENT, " +
							COLUMN_NAME + " VARCHAR(255) NOT NULL, " +
						  	COLUMN_PRICE + " DOUBLE, " +
							COLUMN_QUANTITY + " INTEGER, " +
							"PRIMARY KEY(ID) );";
			executeQuery(query);
					
		}
		catch (SQLException e) {}
	}
	
	public static void addEntry(String name, double price, int quantity)
	{
		String query = ADD_QUERY;
		try
		{
			// A PreparedStatement helps us replace the '?' marks in the query with proper values
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, name);			// replace 1st question mark with name
			prepStmt.setDouble(2, price);			// replace 2nd question mark with price
			prepStmt.setInt(3, quantity);			// replace 3rd question mark with quantity
			prepStmt.executeUpdate();
		}
		catch (SQLException e) {}
	}

	public static void closeConnection()
	{
		try
		{
			conn.close();	// Close the connection
		}
		catch (SQLException e){}
	}

}
