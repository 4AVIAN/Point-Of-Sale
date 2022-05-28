import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import database.Connect;

public class home extends JFrame implements ActionListener{
	Connect con = new Connect();
	String test = "hey";
	

	JPanel northPanel, centerPanel;
	JMenuBar menuBar;
	JMenu page;
	JMenuItem newSalesEntry, home, totalSalesTransaction;
	JLabel pointOfSaleLabel;
	
	public home() {
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
		home.setEnabled(false);
		
		page.add(newSalesEntry);
		page.add(totalSalesTransaction);
		page.add(home);
		
		menuBar.add(page);
		northPanel.add(menuBar);
		add(northPanel, BorderLayout.NORTH);
		//south panel
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20, 20, 20, 20);
		pointOfSaleLabel = new JLabel("Point of Sale");
		pointOfSaleLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		centerPanel.add(pointOfSaleLabel, gbc);
		add(centerPanel, BorderLayout.CENTER);
		
		setSize(800,400);
		setLocationRelativeTo(null);
		setTitle("Home");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
