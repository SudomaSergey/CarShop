package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.ShopManager;

public interface PanelManagerInterface {
	
	public void setMainPanel() throws IOException;
	public void setSalesPanel() throws IOException, SQLException;
	public void setReportsPanel() throws IOException;
}
