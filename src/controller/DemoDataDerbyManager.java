package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

public class DemoDataDerbyManager implements DemoDataManagerInterface {
	
	private Connection connection;
	
	public DemoDataDerbyManager() throws SQLException, URISyntaxException, IOException {
		connection = DerbyDBUtil.getConnection();
		initCars();
		initCustomers();
		initStock();
		initTransactions();
	}

	@Override
	public void initStock() throws SQLException, URISyntaxException, IOException {
		String createStockTable = "CREATE TABLE stock (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				 " car varchar(20)," + 
				 " quantity integer)";
		Statement statement = connection.createStatement();
		statement.executeUpdate(createStockTable);
		statement.close();
		
		fillStock();
	}

	private void fillStock() throws SQLException, URISyntaxException, IOException {
		String getCars = "select model from cars";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(getCars);
		
		String fillCars = "insert into stock(car, quantity) values(?,?)";
		PreparedStatement prepStmt = connection.prepareStatement(fillCars);

		while(resultSet.next()){
			prepStmt.setString(1, resultSet.getString("model"));
			prepStmt.setInt(2, 10);
			prepStmt.executeUpdate();
		}
		
		statement.close();
		prepStmt.close();
	}

	@Override
	public void initCars() throws SQLException, URISyntaxException, IOException {
		String createTransactionsTable = "CREATE TABLE cars (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				 " model varchar(20)," +
				 " picture BLOB," +
				 " price integer)";
		Statement statement = connection.createStatement();
		statement.executeUpdate(createTransactionsTable);
		statement.close();
		
		fillCars();
	}

	private void fillCars() throws SQLException, URISyntaxException, IOException {
		String insertCar = "insert into cars(model, picture, price) values(?, ?, ?)";
		PreparedStatement prepStmt = connection.prepareStatement(insertCar);

		for(int i = 1; i <= 10; i++){
			prepStmt.setString(1, "car ¹" + i);
			prepStmt.setBlob(2, getImageAsBlob("carModel" + i + ".png"));
			prepStmt.setInt(3, i * 500);
			prepStmt.executeUpdate();
		}			
		
	}
		
		private static java.sql.Blob getImageAsBlob(String imageFileName) throws URISyntaxException, IOException, SerialException, SQLException{
			InputStream fis = DemoDataDerbyManager.class.getResourceAsStream("/Resources/" + imageFileName);
			
			byte[] byteArr = IOUtils.toByteArray(fis);
			
			SerialBlob blob = new SerialBlob(byteArr);
	        return blob;
		}

	@Override
	public void initCustomers() throws SQLException {
		String createCustomersTable = "CREATE TABLE customers (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
					 " name VARCHAR(20))";
		Statement statement = connection.createStatement();
		statement.executeUpdate(createCustomersTable);
		statement.close();
	}

	@Override
	public void initTransactions() throws SQLException {
		String createTransactionsTable = "CREATE TABLE transactions (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
				 " customer varchar(20), " +
				 " car varchar(20)," + 
				 " price integer," +
				 " quantity integer," +
				 " summ integer)";
		Statement statement = connection.createStatement();
		statement.executeUpdate(createTransactionsTable);
		statement.close();
	}
}
