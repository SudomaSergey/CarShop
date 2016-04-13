package controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;

import models.Car;

public interface DBManagerInterface {	
	public Car[] getAvailableCars() throws SQLException;
	public List getTransactionReport() throws SQLException;
	public void addNewTransaction(String customerName, int quanitySelected, String carModel) throws SQLException;
	public ImageIcon getCarPicture(String carName) throws SQLException;
	public int getAvailableQuantity(String carName) throws SQLException;
	public List getStockReport() throws SQLException;
}
