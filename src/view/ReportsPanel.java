package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import controller.ShopManager;
import models.Car;
import models.Transaction;

import javax.swing.SpringLayout;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReportsPanel extends AbstractPanel{

	private PanelManagerInterface panelManagerInterface;
	private ShopManager shopManager;
	
	public ReportsPanel(PanelManagerInterface panelManagerInterface, ShopManager shopManager) throws IOException {
		super();
		this.shopManager = shopManager;
		this.panelManagerInterface = panelManagerInterface;
		initContent();
	}
	
	private void initContent(){
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnStockReport = new JButton("Stock Report");
		springLayout.putConstraint(SpringLayout.NORTH, btnStockReport, -306, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnStockReport, 172, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnStockReport, -230, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnStockReport, 352, SpringLayout.WEST, this);
		
		URL ReportImageUrl = this.getClass().getResource("/Resources/report_stock.png");
		btnStockReport.setIcon(new ImageIcon(ReportImageUrl));

		add(btnStockReport);
		
		btnStockReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List stock = null;
				try {
					stock = shopManager.getStock();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				StockReport stockReport = new StockReport(stock);
				createReportFrame("Stock report", stockReport);				
			}
		});
		
		JButton btnSalesReport = new JButton("Sales Report");
		springLayout.putConstraint(SpringLayout.NORTH, btnSalesReport, -306, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnSalesReport, 77, SpringLayout.EAST, btnStockReport);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSalesReport, -230, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSalesReport, 267, SpringLayout.EAST, btnStockReport);
		
		URL imageUrlSalesReport = this.getClass().getResource("/Resources/report_sales.png");
		btnSalesReport.setIcon(new ImageIcon(imageUrlSalesReport));

		add(btnSalesReport);
		
		btnSalesReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List transactions = null;
				try {
					transactions = shopManager.getTransactions();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				SalesReport salesReport = new SalesReport(transactions);
				createReportFrame("Sales report", salesReport);				
			}
		});
		
		
		JButton btnBack = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnBack, 17, SpringLayout.SOUTH, btnStockReport);
		springLayout.putConstraint(SpringLayout.WEST, btnBack, 329, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnBack, -159, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnBack, -363, SpringLayout.EAST, this);
		
		URL imageUrlBack = this.getClass().getResource("/Resources/back_button.png");
		btnBack.setIcon(new ImageIcon(imageUrlBack));

		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					panelManagerInterface.setMainPanel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		add(btnBack);		
	}
	
	
	public class StockReport implements TableModel{ 
		
		private List stock;
		
		public StockReport(List stock) {
			this.stock = stock;
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Car car = (Car) stock.get(rowIndex);
			switch(columnIndex){
			case 0:
				return rowIndex + 1;
			case 1:
				return car.getName();
			case 2:
				return car.getPrice();
			case 3:
				return car.getQuantity();
			}			
			return null;
		}
		
		@Override
		public int getRowCount() {
			return stock.size();
		}
		
		@Override
		public String getColumnName(int columnIndex) {
		    switch (columnIndex) {
	        case 0:
	            return "No.";
	        case 1:
	            return "Model";
	        case 2:
	            return "Price, USD";
	        case 3:
	            return "Quantity";
		    }
	    return "";
		}
		
		@Override
		public int getColumnCount() {
			return 4;
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}
		
		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	public class SalesReport implements TableModel{ 
		
		private List transactions;
		
		public SalesReport(List transactions) {
			this.transactions = transactions;
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Transaction transaction = (Transaction) transactions.get(rowIndex);
			switch(columnIndex){
			case 0:
				return rowIndex + 1;
			case 1:
				return transaction.getCustomer().getName();
			case 2:
				return transaction.getCar().getName();
			case 3:
				return transaction.getCar().getPrice();
			case 4:
				return transaction.getQuantity();
			case 5:
				return transaction.getSumm();
			}			
			return null;
		}
		
		@Override
		public int getRowCount() {
			return transactions.size();
		}
		
		@Override
		public String getColumnName(int columnIndex) {
		    switch (columnIndex) {
	        case 0:
	            return "No.";
	        case 1:
	            return "Customer";
	        case 2:
	            return "Car";
	        case 3:
	            return "Price, USD";
	        case 4:
	            return "Quantity, pcs";
		    case 5:
		    	return "Total, USD";
		    }
	    return "";
		}
		
		@Override
		public int getColumnCount() {
			return 6;
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}
		
		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void createReportFrame(String frameName, TableModel tableModel){
		
		JTable table = new JTable(tableModel);

		JFrame reportFrame = new JFrame(frameName);		
		reportFrame.setBounds(300, 300, 600, 300);
		reportFrame.add(new JScrollPane(table));
		reportFrame.setVisible(true);
		reportFrame.setResizable(false);	
	}
}
