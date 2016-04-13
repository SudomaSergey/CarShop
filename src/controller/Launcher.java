package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import view.ApplicationFrame;

public class Launcher {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, SQLException {

			DBManagerInterface dbController = new Configurator().initDbController();
			ShopManager shopManager = new ShopManager(dbController);
			new ApplicationFrame(shopManager);
	}
}
