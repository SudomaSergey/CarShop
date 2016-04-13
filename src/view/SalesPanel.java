package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import controller.ShopManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class SalesPanel extends AbstractPanel{
	
	private PanelManagerInterface panelManagerInterface;
	private ShopManager shopManager;
	private int quantity = 1;
	private JTextField quantityPane;
	private JLabel picture;
	
	public SalesPanel(PanelManagerInterface panelManagerInterface, ShopManager shopManager) throws IOException, SQLException {
		super();
		this.shopManager = shopManager;
		this.panelManagerInterface = panelManagerInterface;
		initContent();
	}
	
	private void initContent() throws SQLException{
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JComboBox carsListBox = new JComboBox(shopManager.getAvailaleCars());
		springLayout.putConstraint(SpringLayout.WEST, carsListBox, 25, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, carsListBox, -580, SpringLayout.EAST, this);
		add(carsListBox);
		
		carsListBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 String selectedCarName = carsListBox.getSelectedItem().toString();
				try {
					ImageIcon icon = shopManager.getCarPicture(selectedCarName);
					picture.setIcon(icon);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}
		});
		
		
		JTextPane customerNameTextField = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, customerNameTextField, 0, SpringLayout.NORTH, carsListBox);
		springLayout.putConstraint(SpringLayout.WEST, customerNameTextField, 529, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, customerNameTextField, -253, SpringLayout.SOUTH, this);
		customerNameTextField.setBackground(SystemColor.control);
		add(customerNameTextField);
		
		picture = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, picture, 6, SpringLayout.EAST, carsListBox);
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		add(picture);
		
		JButton sellButton = new JButton("");
		springLayout.putConstraint(SpringLayout.SOUTH, picture, 0, SpringLayout.SOUTH, sellButton);
		springLayout.putConstraint(SpringLayout.EAST, customerNameTextField, -28, SpringLayout.WEST, sellButton);
		springLayout.putConstraint(SpringLayout.WEST, sellButton, 675, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, sellButton, -24, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, sellButton, 304, SpringLayout.NORTH, this);
		
		URL imageUrl = this.getClass().getResource("/Resources/sell_button.png");
		sellButton.setIcon(new ImageIcon(imageUrl));

		add(sellButton);
		
		sellButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String customerName = customerNameTextField.getText();
				int quanitySelected = Integer.parseInt(quantityPane.getText());
				try {
					shopManager.sellCar(customerName, quanitySelected, carsListBox.getSelectedItem().toString());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}								
			}
		});
		
		
		JLabel lblSelectCar = new JLabel("Select car:");
		springLayout.putConstraint(SpringLayout.NORTH, picture, -60, SpringLayout.NORTH, lblSelectCar);
		springLayout.putConstraint(SpringLayout.NORTH, carsListBox, 19, SpringLayout.SOUTH, lblSelectCar);
		springLayout.putConstraint(SpringLayout.WEST, lblSelectCar, 64, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblSelectCar, -292, SpringLayout.SOUTH, this);
		lblSelectCar.setFont(new Font("Calibri", Font.BOLD, 17));
		lblSelectCar.setForeground(Color.WHITE);
		add(lblSelectCar);
		
		JLabel customerName = new JLabel("Customer name:");
		springLayout.putConstraint(SpringLayout.NORTH, customerName, 0, SpringLayout.NORTH, lblSelectCar);
		springLayout.putConstraint(SpringLayout.WEST, customerName, 0, SpringLayout.WEST, customerNameTextField);
		customerName.setForeground(Color.WHITE);
		customerName.setFont(new Font("Calibri", Font.BOLD, 17));
		add(customerName);
		
	
		JButton qtyPlusButton = new JButton("+");
		springLayout.putConstraint(SpringLayout.EAST, picture, -17, SpringLayout.WEST, qtyPlusButton);
		springLayout.putConstraint(SpringLayout.WEST, qtyPlusButton, 456, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, qtyPlusButton, -31, SpringLayout.WEST, customerName);
		qtyPlusButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				quantityPane.setText(String.valueOf(++quantity));				
			}
		});
		add(qtyPlusButton);
		
		JButton qtyMinusButton = new JButton("-");
		springLayout.putConstraint(SpringLayout.WEST, qtyMinusButton, 457, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, qtyMinusButton, 0, SpringLayout.EAST, qtyPlusButton);
		qtyMinusButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(quantity >= 2){
					quantityPane.setText(String.valueOf(--quantity));
				}
			}
		});
		add(qtyMinusButton);
		
		quantityPane = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, quantityPane, 27, SpringLayout.EAST, picture);
		springLayout.putConstraint(SpringLayout.EAST, quantityPane, -41, SpringLayout.WEST, customerNameTextField);
		springLayout.putConstraint(SpringLayout.NORTH, qtyMinusButton, 6, SpringLayout.SOUTH, quantityPane);
		springLayout.putConstraint(SpringLayout.SOUTH, qtyPlusButton, -6, SpringLayout.NORTH, quantityPane);
		springLayout.putConstraint(SpringLayout.NORTH, quantityPane, 0, SpringLayout.NORTH, carsListBox);
		quantityPane.setHorizontalAlignment(SwingConstants.CENTER);
		quantityPane.setEditable(false);
		quantityPane.setText(String.valueOf(quantity));
		add(quantityPane);	
		
		JButton backButton = new JButton("");
		springLayout.putConstraint(SpringLayout.WEST, backButton, 177, SpringLayout.EAST, qtyMinusButton);
		springLayout.putConstraint(SpringLayout.EAST, backButton, -24, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, sellButton, -6, SpringLayout.NORTH, backButton);
		springLayout.putConstraint(SpringLayout.NORTH, backButton, 374, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, backButton, -168, SpringLayout.SOUTH, this);
		
		URL imageUrlBack = this.getClass().getResource("/Resources/back_button.png");
		backButton.setIcon(new ImageIcon(imageUrlBack));

		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					panelManagerInterface.setMainPanel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		add(backButton);
	}
}
