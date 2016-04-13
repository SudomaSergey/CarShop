package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.swing.ImageIcon;
import org.hibernate.Query;
import org.hibernate.Session;

import models.Car;
import models.Customer;
import models.Transaction;

public class HibernateManager implements DBManagerInterface{
	
	public HibernateManager() throws URISyntaxException, SerialException, IOException, SQLException {
		new DemoDataHibernateManager(); 
	}
	
	@Override
	public Car[] getAvailableCars(){
		
		String hql = "from Car as car where car.quantity > 0";
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(hql);		
		List<Car> resultsList = query.list();
		
		Car[] resultsArray = new Car[resultsList.size()];

		for(int i = 0; i < resultsList.size(); i++){
			resultsArray[i] = resultsList.get(i);
		}
		session.close();
		return resultsArray;		
	}
	

	@Override
	public List getTransactionReport() {
		String hql = "from Transaction as transaction where transaction.quantity > 0";
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(hql);		
		List<Transaction> resultsList = query.list();
		session.close();
		return resultsList;
	}

	@Override
	public void addNewTransaction(String customerName, int quanitySelected, String carModel) {
		
		Car car = getCarByName(carModel);
		
		Customer customer = new Customer();
		customer.setName(customerName);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Transaction transaction = new Transaction();
		transaction.setQuantity(quanitySelected);
		transaction.setCar(car);
		transaction.setCustomer(customer);
		transaction.setSumm(quanitySelected * car.getPrice());
		
		session.save(transaction);
		session.getTransaction().commit();
		
		session.close();
		
		removeBoughtCarsFromStock(carModel, quanitySelected);
			
		
	}
	
	public int getAvailableQuantity(String carName){
		String hqlGetAvailableQuantity = "from Car as car where :carName like car.name";
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(hqlGetAvailableQuantity);		
		query.setString("carName", carName);
		List<Car> resultsList = query.list();
		int quantity = resultsList.get(0).getQuantity();
		session.close();
		return quantity;
	}
	
	private void removeBoughtCarsFromStock(String carName, int qty){
		String hql = "UPDATE Car set quantity = :qty WHERE name like :carName";
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(hql);
		int quanityLeft = getAvailableQuantity(carName) - qty;
		query.setInteger("qty", quanityLeft);
		query.setString("carName", carName);
		query.executeUpdate();
		session.close();
	}
	
	private Car getCarByName(String carName){
		String hql = "from Car as car where :carName like car.name";		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery(hql);		
		query.setString("carName", carName);		
		List<Car> resultsList = query.list();
		Car car = resultsList.get(0);
		session.close();
		return car;
	}

	@Override
	public ImageIcon getCarPicture(String carName){
		
		Car car = getCarByName(carName);
		java.sql.Blob blob = car.getPicture();
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(blob.getBytes(1, (int) blob.length()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return icon;		
	}

	@Override
	public List getStockReport() {
		List list = Arrays.asList(getAvailableCars());
		return list;
	}


}

