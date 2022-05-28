import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.Connect;

public class TotalSalesReport extends JFrame implements ActionListener, MouseListener{
	Connect con = new Connect();
	
	JPanel northPanel, centerPanel;
	JMenuBar menuBar;
	JMenu page;
	JMenuItem newSalesEntry, home, totalSalesTransaction;
	JLabel pointOfSaleLabel, totalTrans;
	
	DefaultTableModel dtm;
	JTable totalSalesReportTable;
	JScrollPane totalSalesReportContainer;
	ResultSet rs;
	Vector<String> totalSalesReportHeader = new Vector<>();
	String searchQuery = String.format(
			"SELECT * FROM salesentry WHERE status = 'UNVERIFIED' and shift = 'Sunday Evening'");
	public TotalSalesReport() {
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
		totalSalesTransaction.setEnabled(false);
		
		page.add(newSalesEntry);
		page.add(totalSalesTransaction);
		page.add(home);
		
		menuBar.add(page);
		northPanel.add(menuBar);
		add(northPanel, BorderLayout.NORTH);
		//center panel
		totalSalesReportHeader.add("Transaction ID");
		totalSalesReportHeader.add("Total Price");
		totalSalesReportHeader.add("Status");
		totalSalesReportHeader.add("Action");
		
		centerPanel = new JPanel();
		dtm = new DefaultTableModel(totalSalesReportHeader,0) {
			@Override 
			public boolean isCellEditable(int row, int column) {
			return false;}
		};
		
		totalSalesReportTable = new JTable(dtm);
		totalSalesReportTable.setFillsViewportHeight(true);
		totalSalesReportTable.setModel(dtm);
		totalSalesReportTable.addMouseListener(this);
		
		totalSalesReportContainer = new JScrollPane(totalSalesReportTable);
		totalSalesReportContainer.setPreferredSize(new Dimension(400,300));
		
		rs = con.executeQuery(searchQuery);
		try {
			while (rs.next()) {
				Vector<String> salesData = new Vector<>();
				String transID = rs.getString("transactionID");
				String totalPrice = rs.getString("totalPrice");
				String status = rs.getString("status");
				
				salesData.add(transID);
				salesData.add(totalPrice);
				salesData.add(status);
				salesData.add("APPROVE");
				dtm.addRow(salesData);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		centerPanel.add(totalSalesReportContainer);
		
		add(centerPanel, BorderLayout.CENTER);
		
		totalTrans = new JLabel("Total Sales :"+ dtm.getRowCount());
		add(totalTrans, BorderLayout.SOUTH);
		
		
		setSize(500,400);
		setLocationRelativeTo(null);
		setTitle("Total Sales Report");
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
		if (arg0.getSource().equals(newSalesEntry)) {
			MemberGateway mg = new MemberGateway();
			this.dispose();
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == (totalSalesReportTable)) {
			int row = totalSalesReportTable.rowAtPoint(arg0.getPoint());
			int column = totalSalesReportTable.columnAtPoint(arg0.getPoint());
			String transID = dtm.getValueAt(row, 0).toString();
			if (column==3) {
				String approveTrans = String.format(
						"UPDATE salesentry SET status = 'APPROVED' WHERE transactionID = '"+transID +"';");
				rs = con.executeUpdate(approveTrans);
				rs = con.executeQuery(searchQuery);
				dtm.setRowCount(0);
				try {
					while (rs.next()) {
						Vector<String> updatedSalesData = new Vector<>();
						String transIDUp = rs.getString("transactionID");
						String totalPrice = rs.getString("totalPrice");
						String status = rs.getString("status");
						
						updatedSalesData.add(transID);
						updatedSalesData.add(totalPrice);
						updatedSalesData.add(status);
						updatedSalesData.add("APPROVE");
						dtm.addRow(updatedSalesData);
						
					
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, "Transaction succesfull approved", "Approve", JOptionPane.INFORMATION_MESSAGE);
				totalTrans.setText("Total Sales :" +String.valueOf(dtm.getRowCount()));
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
