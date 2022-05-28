import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.Connect;
import database.TempTrans;

public class salesEntry extends JFrame implements ActionListener, MouseListener{
	Connect con = Connect.getInstance();
	
	JPanel northPanel, westPanel, eastPanel,eastTablePanel, eastSearchPanel, westTablePanel, westMoneyPanel, westButtonPanel ;
	JMenuBar menuBar;
	JMenu page;
	JMenuItem newSalesEntry, home, totalSalesTransaction;
	JLabel pointOfSaleLabel;
	
	DefaultTableModel dtmItem, dtmTrans;
	JTable itemTable, salesEntryTable;
	JScrollPane itemTableContainer, salesEntryContainer;
	Vector<String> itemHeader = new Vector<>();
	Vector<String> transactionHeader = new Vector<>();
	JLabel searchLabel, totalPriceLabel, totalPrice, amountPaid, changeMoneyLabel, changeMoney;
	JTextField searchField, amountPaidField;
	JButton searchButton, payButton, endSession, helpButton;
	String itemQuery = "SELECT * FROM item";
	String transID = "";
	ResultSet rs;
	public String memberID1 = "";
	String memberIDFin;
	
	public salesEntry(String memberID) {
		memberIDFin = memberID;
		String getTransIDQuery = String.format(
				"SELECT transactionID  \r\n"
				+ "FROM salesentry \r\n"
				+ "ORDER BY transactionID DESC \r\n"
				+ "LIMIT 1");
		rs = con.executeQuery(getTransIDQuery);
			try { 
				rs.next();
				transID = rs.getString("transactionID");
				if (transID.charAt(4) ==  '0') {
					int ones = Character.getNumericValue(transID.charAt(4));
					ones++;
					transID = "SE00" + ones;
					
				} else if (transID.charAt(3) == '0') {
					if (transID.charAt(4) == '9') {
						String tens = String.valueOf(transID.charAt(3));
						String ones = String.valueOf(transID.charAt(4));
						String tenner = new StringBuilder().append(tens).append(ones).toString();
						int tennerInt = Integer.valueOf(tenner);
						tennerInt++;
						transID = "SE0" + tennerInt;
						
					} else if (transID.charAt(4) != '9') {
						int ones = Character.getNumericValue(transID.charAt(4));
						ones++;
						transID = "SE00" + ones;
					}
				} 	else if (transID.charAt(2) == '0') {
					if (transID.charAt(3) == '9') {
						String hunds = String.valueOf(transID.charAt(2));
						String tens  = String.valueOf(transID.charAt(3));
						String ones  = String.valueOf(transID.charAt(4));
						String hunder = new StringBuilder().append(hunds).append(tens).append(ones).toString();
						int hunderInt = Integer.valueOf(hunder);
						hunderInt++;
						
						transID = "SE" + hunderInt;
						
					} else  {
					String tens = String.valueOf(transID.charAt(3));
					String ones = String.valueOf(transID.charAt(4));
					String tenner = new StringBuilder().append(tens).append(ones).toString();
					int tennerInt = Integer.valueOf(tenner);
					tennerInt++;
					
					transID = "SE0" + tennerInt;
					}
				} 	else if (transID.charAt(2) != '0' ) {
					String hunds = String.valueOf(transID.charAt(2));
					String tens  = String.valueOf(transID.charAt(3));
					String ones  = String.valueOf(transID.charAt(4));
					String hunder = new StringBuilder().append(hunds).append(tens).append(ones).toString();
					int hunderInt = Integer.valueOf(hunder);
					hunderInt++;
					
					transID = "SE" + hunderInt;
					
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int totalPlaceHolder = 0;
			String insertSalesEntryQuery = String.format(
					"INSERT INTO salesentry VALUES "
					+ "('" + transID + "','OP002',NULL, "+memberIDFin+ "," + totalPlaceHolder +",'Sunday Evening','UNVERIFIED')");
			rs = con.executeUpdate(insertSalesEntryQuery);
		//north panel
			northPanel = new JPanel(new GridLayout(1,1));
			menuBar = new JMenuBar();
			page = new JMenu("Page");
			newSalesEntry = new JMenuItem("New Sales Entry");
			newSalesEntry.addActionListener(this);
			home = new JMenuItem("Home");
			home.addActionListener(this);
			totalSalesTransaction = new JMenuItem("Total Sales Report");
			totalSalesTransaction.addActionListener(this);
			newSalesEntry.setEnabled(false);
			
			page.add(newSalesEntry);
			page.add(totalSalesTransaction);
			page.add(home);
			
			menuBar.add(page);
			northPanel.add(menuBar);
			add(northPanel, BorderLayout.NORTH);
		//east panel
			itemHeader.add("ID");
			itemHeader.add("Name");
			itemHeader.add("Price");
			itemHeader.add("Quantity");
			itemHeader.add("Action");
			
			eastPanel = new JPanel(new BorderLayout());
			
			// east table panel
			eastTablePanel = new JPanel();
			dtmItem = new DefaultTableModel(itemHeader,0) {
				@Override 
				public boolean isCellEditable(int row, int column) {
				return false;}
			};
			
			itemTable = new JTable(dtmItem);
			itemTable.setFillsViewportHeight(true);
			itemTable.setModel(dtmItem);
			itemTable.addMouseListener(this);
			
			itemTableContainer = new JScrollPane(itemTable);
			itemTableContainer.setPreferredSize(new Dimension(700,300));
			
			rs = con.executeQuery(itemQuery);
			
			try {
				while (rs.next()) {
					Vector<String> itemData = new Vector<>();
					String id = rs.getString("itemID");
					String name = rs.getString("name");
					String price = rs.getString("price");
					String qty = rs.getString("qty");
					String action = "Add to Entry";
					
					itemData.add(id);
					itemData.add(name);
					itemData.add(price);
					itemData.add(qty);
					itemData.add(action);
					dtmItem.addRow(itemData);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			eastTablePanel.add(itemTableContainer, BorderLayout.NORTH);
			// east search panel
			eastSearchPanel = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			searchLabel = new JLabel("Search by name");
			searchButton = new JButton("Search");
			searchButton.addActionListener(this);
			searchField = new JTextField(20);
			
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.gridx = 0;
			gbc.gridy = 0;
			eastSearchPanel.add(searchLabel, gbc);
			
			gbc.gridx = 1;
			eastSearchPanel.add(searchField, gbc);
			
			gbc.gridx = 2;
			eastSearchPanel.add(searchButton, gbc);
			
			eastPanel.add(eastSearchPanel, BorderLayout.SOUTH);
			eastPanel.add(eastTablePanel, BorderLayout.NORTH);
			
			// west panel
			//west table panel
			transactionHeader.add("ID");
			transactionHeader.add("Name");
			transactionHeader.add("Price");
			transactionHeader.add("Quantity");
			transactionHeader.add("Quantity Price");
			
			westPanel = new JPanel(new BorderLayout());
			westTablePanel = new JPanel();
			
			dtmTrans = new DefaultTableModel(transactionHeader,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			
				}
			
			};
			salesEntryTable = new JTable(dtmTrans);
			salesEntryTable.setFillsViewportHeight(true);
			salesEntryTable.setModel(dtmTrans);
			
			salesEntryContainer = new JScrollPane(salesEntryTable);
			salesEntryContainer.setPreferredSize(new Dimension(700, 200));
			
			westTablePanel.add(salesEntryContainer, BorderLayout.NORTH);
			westPanel.add(westTablePanel, BorderLayout.NORTH);
			//west money panel
			westMoneyPanel = new JPanel(new GridBagLayout());
			gbc.insets = new Insets(10, 10, 10, 10);
			gbc.anchor = GridBagConstraints.WEST;
			totalPriceLabel = new JLabel("Total Price :");
			totalPrice = new JLabel("-");
			amountPaid = new JLabel("Amount Paid :");
			amountPaidField = new JTextField(12);
			changeMoneyLabel = new JLabel("Change Money :");
			changeMoney = new JLabel("-");
			
			payButton = new JButton("Pay");
			payButton.setPreferredSize(new Dimension(200,25));
			payButton.addActionListener(this);
			endSession = new JButton("End Session");
			endSession.setPreferredSize(new Dimension(200,25));
			endSession.addActionListener(this);
			helpButton = new JButton("Help");
			helpButton.setPreferredSize(new Dimension(200,25));
			helpButton.addActionListener(this);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			westMoneyPanel.add(totalPriceLabel,gbc );
			
			gbc.gridx = 1;
			westMoneyPanel.add(totalPrice, gbc);
			
			gbc.gridx = 2;
			westMoneyPanel.add(payButton, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			westMoneyPanel.add(amountPaid, gbc);
			
			gbc.gridx = 1;
			westMoneyPanel.add(amountPaidField, gbc);
			
			gbc.gridx = 2;
			westMoneyPanel.add(endSession, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			westMoneyPanel.add(changeMoneyLabel,gbc );
			
			gbc.gridx = 1;
			westMoneyPanel.add(changeMoney, gbc);
			
			gbc.gridx = 2;
			westMoneyPanel.add(helpButton, gbc);
			westPanel.add(westMoneyPanel, BorderLayout.SOUTH);
			
			add(westPanel, BorderLayout.WEST);
			add(eastPanel, BorderLayout.EAST);
			
			
		setSize(1430,400);
		setLocationRelativeTo(null);
		setTitle("Sales Entry");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(home)) {
			home h = new home();
			this.dispose();
		} 
		if (arg0.getSource().equals(totalSalesTransaction)) {
			 TotalSalesReport tse = new TotalSalesReport();
			 this.dispose();
		}
		if (arg0.getSource().equals(payButton)) {
			Integer totalPriceCombi = 0;
			Integer changeMoneyint = 0;
			String paid = amountPaidField.getText();
			
			for (int i = 0; i < dtmTrans.getRowCount(); i++) {
				String totalPriceBotSt = dtmTrans.getValueAt(i, 4).toString();
				Integer totalPriceBot = Integer.valueOf(totalPriceBotSt);
				totalPriceCombi = totalPriceCombi + totalPriceBot;
				
			}
			
			changeMoneyint = Integer.valueOf(paid) - totalPriceCombi;
			String changeMoneySt = String.valueOf(changeMoneyint);
			changeMoney.setText(changeMoneySt);
		}
		if (arg0.getSource().equals(helpButton)) {
			SupervisorGateway sg = new SupervisorGateway();
			
			this.dispose();
		}	
		if (arg0.getSource().equals(endSession)) {
			home h = new home();
			this.dispose();
		}
		if (arg0.getSource().equals(searchButton)) {
				String nameQuery = String.format(
						"SELECT * FROM item "
					  + "WHERE name = '" + searchField.getText() + "';" );
				rs = con.executeQuery(nameQuery);
				
				try {
					rs.next();
					Vector<Vector<String>> searchVectorOut = new Vector<>();
					String id = rs.getString("itemID");
					String name = rs.getString("name");
					String price = rs.getString("price");
					String qty = rs.getString("qty");
					String action = "Add to Entry";
					Vector<String> searchVector = new Vector<>();
					searchVector.add(id);
					searchVector.add(name);
					searchVector.add(price);
					searchVector.add(qty);
					searchVector.add(action);
					
					searchVectorOut.add(searchVector);
					dtmItem.setDataVector(searchVectorOut, itemHeader);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					dtmItem.setRowCount(0);
					e.printStackTrace();
				}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		ResultSet rs;
		if (arg0.getSource() == itemTable) {
			int row = itemTable.rowAtPoint(arg0.getPoint());
			int column = itemTable.columnAtPoint(arg0.getPoint());
			
			if (column==4) {
				String itemID = dtmItem.getValueAt(row, 0).toString();
				String quantityTableString = dtmItem.getValueAt(row, 3).toString();
				Integer quantityTableInteger = Integer.valueOf(quantityTableString);
				String searchQuery = String.format(
						"SELECT * FROM item "
					+"WHERE itemID = '" + itemID + "';"
						);
				rs = con.executeQuery(searchQuery);
				String inputQty = JOptionPane.showInputDialog(this, "Enter Quantity");
				Integer inputQtyInt = Integer.valueOf(inputQty);
				if (inputQtyInt > quantityTableInteger) {
					JOptionPane.showMessageDialog(this, "Quantity exceeded available stock", "Message", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String id = "";
					Integer qty = 0, total = 0 ;
					try {
						rs.next();
						Vector<String> addToTrans = new Vector<>();
						 id = rs.getString("itemID");
						String name = rs.getString("name");
						Integer price = rs.getInt("price");
						 qty = inputQtyInt;
						 total = qty * price;
						String qtyPrice = String.valueOf(total);
						String priceString = String.valueOf(price);
						String qtyString = String.valueOf(qty);
						
						addToTrans.add(id);
						addToTrans.add(name);
						addToTrans.add(priceString);
						addToTrans.add(qtyString);
						addToTrans.add(qtyPrice);
						dtmTrans.addRow(addToTrans);
						
						
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String salesEntryQuery = String.format(
								"SELECT * FROM salesEntry");
					
					
					Integer totalPriceCombi = 0;
					for (int i = 0; i < dtmTrans.getRowCount(); i++) {
						String totalPriceBotSt = dtmTrans.getValueAt(i, 4).toString();
						Integer totalPriceBot = Integer.valueOf(totalPriceBotSt);
						totalPriceCombi = totalPriceCombi + totalPriceBot;
						
					}
					String totalPriceCombiSt = String.valueOf(totalPriceCombi);
					totalPrice.setText(totalPriceCombiSt);
					
					String updateTotalPrice = String.format(
							"UPDATE salesentry SET totalPrice = "+totalPriceCombi+" WHERE transactionID = '"+transID+"';"
							);
					rs = con.executeUpdate(updateTotalPrice);
					
					String getSalesQuery = String.format(
							"SELECT salesentrydetailID  \r\n"
							+ "FROM salesentrydetail \r\n"
							+ "ORDER BY salesentrydetailID DESC \r\n"
							+ "LIMIT 1");
					rs = con.executeQuery(getSalesQuery);
					String sdID = "";
						try { 
							rs.next();
							sdID = rs.getString("salesentrydetailID");
							if (sdID.charAt(4) ==  '0') {
								int ones = Character.getNumericValue(sdID.charAt(4));
								ones++;
								sdID = "SD00" + ones;
								
							} else if (sdID.charAt(3) == '0') {
								if (sdID.charAt(4) == '9') {
									String tens = String.valueOf(sdID.charAt(3));
									String ones = String.valueOf(sdID.charAt(4));
									String tenner = new StringBuilder().append(tens).append(ones).toString();
									int tennerInt = Integer.valueOf(tenner);
									tennerInt++;
									sdID = "SD0" + tennerInt;
									
								} else if (sdID.charAt(4) != '9') {
									int ones = Character.getNumericValue(sdID.charAt(4));
									ones++;
									sdID = "SD00" + ones;
								}
							} 	else if (sdID.charAt(2) == '0') {
								if (sdID.charAt(3) == '9') {
									String hunds = String.valueOf(sdID.charAt(2));
									String tens  = String.valueOf(sdID.charAt(3));
									String ones  = String.valueOf(sdID.charAt(4));
									String hunder = new StringBuilder().append(hunds).append(tens).append(ones).toString();
									int hunderInt = Integer.valueOf(hunder);
									hunderInt++;
									
									sdID = "SD" + hunderInt;
									
								} else {
								String tens = String.valueOf(sdID.charAt(3));
								String ones = String.valueOf(sdID.charAt(4));
								String tenner = new StringBuilder().append(tens).append(ones).toString();
								int tennerInt = Integer.valueOf(tenner);
								tennerInt++;
								
								sdID = "SD0" + tennerInt;
								}
							} 	else if (sdID.charAt(2) != '0' ) {
								String hunds = String.valueOf(sdID.charAt(2));
								String tens  = String.valueOf(sdID.charAt(3));
								String ones  = String.valueOf(sdID.charAt(4));
								String hunder = new StringBuilder().append(hunds).append(tens).append(ones).toString();
								int hunderInt = Integer.valueOf(hunder);
								hunderInt++;
								
								sdID = "SD" + hunderInt;
								
							}
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					String insertSalesEntryDetailsQuery = String.format(
							"INSERT INTO salesentrydetail VALUES "
							+ "('" + sdID + "','"+ transID+"','"+ id+"', "+qty+ "," + total +")");
					rs = con.executeUpdate(insertSalesEntryDetailsQuery);
					String updateItem = String.format(
							"UPDATE item SET qty= qty-"+inputQtyInt+" WHERE itemID = '"+itemID+"';"
							);
					rs = con.executeUpdate(updateItem);
					rs = con.executeQuery(itemQuery);
					dtmItem.setRowCount(0);
					try {
						while (rs.next()) {
							String idup = rs.getString("itemID");
							String name = rs.getString("name");
							String price = rs.getString("price");
							String qtyup = rs.getString("qty");
							String action = "Add to Entry";
							Vector<String> updatedItemRow = new Vector<>();
							updatedItemRow.add(idup);
							updatedItemRow.add(name);
							updatedItemRow.add(price);
							updatedItemRow.add(qtyup);
							updatedItemRow.add(action);
							
							dtmItem.addRow(updatedItemRow);
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
					
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
