package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

public class Configurator {
	
	private String config;
	
	public Configurator() throws FileNotFoundException, IOException{
		getConfig();
	}

	public DBManagerInterface initDbController() throws SQLException, URISyntaxException, IOException {
		
		if(config.equalsIgnoreCase(Configs.MYSQL.toString())){
			return new HibernateManager();
		}
		else {
			return new DerbyManager();
		}		
	}

	private void getConfig() throws FileNotFoundException, IOException {
			File file = new File("launcher.properties");
			if (file.exists() && !file.isDirectory()){
				Properties properties = new Properties();
				properties.load(new FileReader(new File("launcher.properties")));
				config = properties.getProperty("db.mode");	
				System.out.println(config);
			}
			else{
				config = Configs.DERBY.name();
				System.out.println(config);
			}	
	}
	
	private enum Configs {
		MYSQL, DERBY;
	}
}
