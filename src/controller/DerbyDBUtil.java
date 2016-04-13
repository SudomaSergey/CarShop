package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDBUtil {
	
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException{
		if(connection == null){
			connection = DriverManager.getConnection("jdbc:derby:memory:carshopdb;create=true");
		}
		return connection;
	}

}
