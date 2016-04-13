package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.DBManagerInterface;
import controller.ShopManager;

import java.awt.BorderLayout;
import java.awt.CardLayout;

public class ApplicationFrame extends JFrame implements PanelManagerInterface{

	private JPanel currentPanel;
	private JPanel previousPanel;
	private ShopManager shopManager;
	
	public ApplicationFrame(ShopManager shopManager) throws IOException, InterruptedException{
		super(" \"Hot Wheels\" car shop");
		this.shopManager = shopManager;
		initFrame();
		setVisible(true);
	}

	private void initFrame() throws IOException, InterruptedException {		
		setMinimumSize(new Dimension(800, 620));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setMainPanel();
	}
	
	public void removeCurrentPanel(){
		if(currentPanel != null){
			remove(currentPanel);
			revalidate();
		}
	}

	@Override
	public void setSalesPanel() throws IOException, SQLException {
		setPreviousPanel(currentPanel);		
		SalesPanel salesPanel = new SalesPanel(this, shopManager);
		getContentPane().remove(currentPanel);
		getContentPane().add(salesPanel);
		setCurrentPanel(salesPanel);
		revalidate();
	}

	@Override
	public void setReportsPanel() throws IOException {
		setPreviousPanel(currentPanel);	
		ReportsPanel reportsPanel = new ReportsPanel(this, shopManager);
		getContentPane().remove(currentPanel);
		getContentPane().add(reportsPanel);
		setCurrentPanel(reportsPanel);
		revalidate();	
	}

	@Override
	public void setMainPanel() throws IOException {
		setPreviousPanel(currentPanel);
		removeCurrentPanel();
		MainPanel mainPanel = new MainPanel(this, shopManager);
		getContentPane().add(mainPanel);
		setCurrentPanel(mainPanel);
		revalidate();		
	}	

	
	public JPanel getCurrentPanel() {
		return currentPanel;
	}

	public void setCurrentPanel(JPanel currentPanel) {
		this.currentPanel = currentPanel;
	}

	public JPanel getPreviousPanel() {
		return previousPanel;
	}

	public void setPreviousPanel(JPanel previousPanel) {
		this.previousPanel = previousPanel;
	}	
}
