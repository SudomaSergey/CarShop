package view;

import javax.swing.SpringLayout;

import controller.ShopManager;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MainPanel extends AbstractPanel{
	
	private PanelManagerInterface panelManagerInterface;
	private ShopManager shopManager;
	
	public MainPanel(PanelManagerInterface panelManagerInterface, ShopManager shopManager) throws IOException {
		super();
		this.shopManager = shopManager;
		this.panelManagerInterface = panelManagerInterface;
		initContent();
	}

	private void initContent(){
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
	
		JButton btnSales = new JButton("Sales");
		springLayout.putConstraint(SpringLayout.NORTH, btnSales, 258, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnSales, 123, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSales, -241, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSales, -527, SpringLayout.EAST, this);
		
		URL imageSalesUrl = this.getClass().getResource("/Resources/sales_button.png");
		btnSales.setIcon(new ImageIcon(imageSalesUrl));
		
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					panelManagerInterface.setSalesPanel();
				} catch (IOException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnSales);
		
		JButton btnReports = new JButton("Reports");
		springLayout.putConstraint(SpringLayout.NORTH, btnReports, 0, SpringLayout.NORTH, btnSales);
		springLayout.putConstraint(SpringLayout.WEST, btnReports, -296, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnReports, -241, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnReports, -136, SpringLayout.EAST, this);

		URL imageReportUrl = this.getClass().getResource("/Resources/reports_button.png");
		btnReports.setIcon(new ImageIcon(imageReportUrl));
		
		btnReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					panelManagerInterface.setReportsPanel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnReports);
	}
}
