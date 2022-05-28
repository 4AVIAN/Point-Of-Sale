import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import database.Connect;

public class ChangeSalesEntryDetail extends JFrame implements ActionListener {
	Connect con = new Connect();
	String test = "hey";
	

	JPanel northPanel;
	JMenuBar menuBar;
	JMenu page;
	JMenuItem newSalesEntry, home, totalSalesTransaction;
	JLabel pointOfSaleLabel;
	
	public ChangeSalesEntryDetail() {
		// north panel
		northPanel = new JPanel(new GridLayout(1,1));
		menuBar = new JMenuBar();
		page = new JMenu("Page");
		newSalesEntry = new JMenuItem("New Sales Entry");
		newSalesEntry.addActionListener(this);
		home = new JMenuItem("Home");
		home.addActionListener(this);
		totalSalesTransaction = new JMenuItem("Total Sales Report");
		totalSalesTransaction.addActionListener(this);
		
		page.add(newSalesEntry);
		page.add(totalSalesTransaction);
		page.add(home);
		
		menuBar.add(page);
		northPanel.add(menuBar);
		add(northPanel, BorderLayout.NORTH);
		
		setSize(500,400);
		setLocationRelativeTo(null);
		setTitle("Change Sales Entry");
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
		if (arg0.getSource().equals(totalSalesTransaction)) {
			 TotalSalesReport tse = new TotalSalesReport();
			 this.dispose();
		}
	
		
	}

}
