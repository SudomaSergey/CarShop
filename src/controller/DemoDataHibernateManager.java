package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import models.Car;
import models.Customer;
import models.Stock;
import models.Transaction;

public class DemoDataHibernateManager implements DemoDataManagerInterface{
	
	private static Stock stock;

	public DemoDataHibernateManager() throws URISyntaxException, SerialException, IOException, SQLException{		
		initSession();
		initStock();
		initCars();
		initCustomers();
		initTransactions();
	}

	@Override
	public void initTransactions() {
		Transaction transaction = new Transaction();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.save(transaction);
		closeSession(session);		
	}

	public void initCustomers() {
		Customer customer = new Customer();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.save(customer);
		closeSession(session);		
	}

	public void initStock(){
		stock = new Stock();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.save(stock);
		closeSession(session);
	}
	
	private static void closeSession(Session session) {
		session.flush();
		session.close();		
	}

	private static java.sql.Blob getImageAsBlob(Object object, String imageFileName) throws URISyntaxException, IOException, SerialException, SQLException{
		
		System.out.println(imageFileName);
		
		InputStream fis = DemoDataHibernateManager.class.getResourceAsStream("/Resources" + imageFileName);
		
		byte[] byteArr = IOUtils.toByteArray(fis);
		
		SerialBlob blob = new SerialBlob(byteArr);
        return blob;

	}
		
	public void initCars() throws URISyntaxException, SerialException, IOException, SQLException{
		
		for(int i = 1; i <= 10; i++){
			Car car = new Car();
			car.setName("car ¹"+i);		
			car.setPicture(getImageAsBlob(car, "/carModel" + i + ".png"));
			car.setPrice(i * 500);
			car.setQuantity(10);
			car.setStock(stock);
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.save(car);
			closeSession(session);
		}
	}
	
	private static void endSession() {
		HibernateUtil.getSessionFactory().close();		
		System.out.println("success");		
	}
	
	private static void initSession(){		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
	}
}

