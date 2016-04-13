package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public interface DemoDataManagerInterface {
	public void initStock() throws SQLException, URISyntaxException, IOException;
	public void initCars() throws URISyntaxException, FileNotFoundException, SQLException, IOException;
	public void initCustomers() throws SQLException;
	public void initTransactions() throws SQLException;
}
