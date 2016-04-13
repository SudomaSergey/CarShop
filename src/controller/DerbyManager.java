package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import org.hibernate.Query;
import org.hibernate.Session;

import models.Car;
import models.Customer;
import models.Transaction;

public class DerbyManager implements DBManagerInterface{

	private static Connection connection;

	public DerbyManager() throws SQLException, URISyntaxException, IOException{
		initConnection();
		new DemoDataDerbyManager();
	}
	
	private void initConnection() throws SQLException{
		connection = DerbyDBUtil.getConnection();
	}

	@Override
	public Car[] getAvailableCars() throws SQLException {
		String getCars = "select  cars.model, cars.price, stock.quantity"
						+ " from cars"
						+ " join stock"
						+ " on cars.model = stock.car";
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(getCars);
		ArrayList<Car> tempCarNameList = new ArrayList<>();
		while(resultSet.next()){
			Car car = new Car();
			car.setName(resultSet.getString("model"));
			car.setPrice(resultSet.getInt("price"));
			car.setQuantity(resultSet.getInt("quantity"));
			tempCarNameList.add(car);
		}
		Car[] cars = new Car[tempCarNameList.size()];
		tempCarNameList.toArray(cars);
		return cars;
	}

	@Override
	public List getTransactionReport() throws SQLException {
		String transactionsReport = "select * from transactions where quantity > 0";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(transactionsReport);
		
		List<Transaction> transactionsList = new ArrayList<>();
		
		while(resultSet.next()){
			Transaction transaction = new Transaction();
			
			Car car = new Car();
			String carName = resultSet.getString("car"); 
			car.setName(carName);
			car.setPrice(getPriceByName(carName));
			
			Customer customer = new Customer();
			customer.setName(resultSet.getString("customer"));
			
			transaction.setCar(car);
			transaction.setCustomer(customer);
			
			int price = getPriceByName(car.getName());
			int quantity = resultSet.getInt("quantity");
			transaction.setQuantity(quantity);			

			transaction.setSumm(price * quantity);
			
			transactionsList.add(transaction);			
		}
		
		return transactionsList;
	}

	@Override
	public void addNewTransaction(String customerName, int quanitySelected, String carModel) throws SQLException {
		String addNewTransaction = "insert into transactions("
				+ "customer, "
				+ "car, "
				+ "price, "
				+ "quantity, "
				+ "summ)"
				+ " values(?, ?, ?, ?, ?)";
		PreparedStatement prepStmt = connection.prepareStatement(addNewTransaction);
		prepStmt.setString(1, customerName);
		prepStmt.setString(2, carModel);
		int price = getPriceByName(carModel);
		prepStmt.setInt(3, price);
		prepStmt.setInt(4, quanitySelected);
		prepStmt.setInt(5, quanitySelected * price);
		prepStmt.executeUpdate();
		prepStmt.close();	
		
		removeBoughtCarsFromStock(quanitySelected, carModel);
	}
	
	private void removeBoughtCarsFromStock(int quanitySelected, String carModel) throws SQLException {
		int quantityLeft = getAvailableQuantity(carModel) - quanitySelected;
		String updateQuantity = "update stock set quantity = ? where car = ?";
		PreparedStatement prepStmt = connection.prepareStatement(updateQuantity);
		prepStmt.setInt(1, quantityLeft);
		prepStmt.setString(2, carModel);
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	private int getPriceByName(String carName) throws SQLException{
		String getPicture = "select price from cars where ? = model";
		PreparedStatement stmt = connection.prepareStatement(getPicture);
		stmt.setString(1, carName);
		ResultSet resultSet = stmt.executeQuery();		
		resultSet.next();		
		int price = resultSet.getInt("price");
		return price;
	}

	@Override
	public ImageIcon getCarPicture(String carName) throws SQLException {
		String getPicture = "select picture from cars where ? = model";
		PreparedStatement stmt = connection.prepareStatement(getPicture);
		stmt.setString(1, carName);
		ResultSet resultSet = stmt.executeQuery();		
		resultSet.next();		
		Blob blob = resultSet.getBlob("picture");
		ImageIcon icon = new ImageIcon(blob.getBytes(1, (int) blob.length()));
		return icon;
	}

	@Override
	public int getAvailableQuantity(String carName) throws SQLException {
		String getPicture = "select quantity from stock where ? = car";
		PreparedStatement stmt = connection.prepareStatement(getPicture);
		stmt.setString(1, carName);
		ResultSet resultSet = stmt.executeQuery();		
		resultSet.next();		
		int quantity = resultSet.getInt("quantity");
		return quantity;
	}

	@Override
	public List getStockReport() throws SQLException {
		List list = Arrays.asList(getAvailableCars());
		return list;
	}

	
}
