package controller;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import models.Car;
import models.Customer;
import models.Stock;
import models.Transaction;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            Configuration config = new Configuration();            
    		config.addAnnotatedClass(Stock.class);
    		config.addAnnotatedClass(Car.class);
    		config.addAnnotatedClass(Customer.class);
    		config.addAnnotatedClass(Transaction.class);
    		config.configure();
            
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();

            sessionFactory = config.buildSessionFactory(serviceRegistry);           
        }         
        return sessionFactory;
    }

}
