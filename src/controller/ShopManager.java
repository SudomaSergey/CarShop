package controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import models.Car;

public class ShopManager {
	
	private DBManagerInterface dbController;

	public ShopManager(DBManagerInterface dbController) {
		this.dbController = dbController;
	}
	
	
	public List getTransactions() throws SQLException{
		return dbController.getTransactionReport();
	}
	
	public Car[] getAvailaleCars() throws SQLException{
		Car[] carsOnStock = dbController.getAvailableCars();
		return carsOnStock;
	}

	public ImageIcon getCarPicture(String selectedCarName) throws SQLException {
		ImageIcon icon = dbController.getCarPicture(selectedCarName);		
		return icon;
	}
	
	public List getStock() throws SQLException{
		return dbController.getStockReport();
	}
	
	public void sellCar(String customerName, int quanitySelected, String carModel) throws SQLException{
		
		if(customerName.length() == 0){
			JOptionPane.showMessageDialog(null, "Select car model and input customer's name", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int availableQuantity = dbController.getAvailableQuantity(carModel);
		
		if(availableQuantity >= quanitySelected){
			dbController.addNewTransaction(customerName, quanitySelected, carModel);
		}
		else{
			JOptionPane.showMessageDialog(null, "Not enough on stock to process request. Reduce quantity or select another model.", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

}
