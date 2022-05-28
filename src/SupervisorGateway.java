import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.Connect;
import database.member;
import database.supervisor;

public class SupervisorGateway extends JFrame implements ActionListener{
	JFrame gateway;
	JPanel centerPanel;
	JLabel supervisorID, supervisorPassword;
	JTextField supervisorIDField, supervisorPasswordField;
	JButton login;
	Vector<supervisor> listSupervisor = new Vector<>();
	Connect con = new Connect();
	static int count = 0;
	private String supervisorIDSaved;

	public SupervisorGateway(String supervisorIDSaved) throws HeadlessException {
		super();
		this.supervisorIDSaved = supervisorIDSaved;
	}


	public SupervisorGateway() {
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		supervisorID = new JLabel("Input Supervisor ID");
		supervisorPassword = new JLabel("Input Supervisor Password");
		supervisorIDField = new JTextField(17);
		supervisorPasswordField = new JTextField(17);
		login = new JButton("Login as supervisor");
		login.addActionListener(this);

		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(supervisorID, gbc);
		
		gbc.gridx = 1;
		centerPanel.add(supervisorIDField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(supervisorPassword, gbc);
		
		gbc.gridx = 1;
		centerPanel.add(supervisorPasswordField, gbc);
		
		gbc.gridy = 2;
		centerPanel.add(login, gbc);
		
		
		gateway = new JFrame();
		
		gateway.add(centerPanel, BorderLayout.CENTER);
		gateway.setSize(400,200);
		gateway.setLocationRelativeTo(null);
		gateway.setTitle("Supervisor Assist");
		gateway.setVisible(true);
		gateway.setResizable(false);
		gateway.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ResultSet rs;
		String id = supervisorIDField.getText();
		String password = supervisorPasswordField.getText();
		
		if (arg0.getSource().equals(login)) {
			String searchQuery = "SELECT * FROM supervisor";
			rs = con.executeQuery(searchQuery);
			try {
				while (rs.next()) {
					listSupervisor.add(new supervisor(rs.getString("supervisorID"), rs.getString("name"), rs.getString("password")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			for (int i = 0; i < listSupervisor.size(); i++) {
				String logMember = String.format("SELECT * FROM supervisor "
						+ "WHERE supervisorID = '"+listSupervisor.get(i).getSupervisorID()+"'");
				con.executeQuery(logMember);
				if (listSupervisor.get(i).getSupervisorID().equals(supervisorIDField.getText()) && listSupervisor.get(i).getPassword().equals(supervisorPasswordField.getText())) {
					count++;
					break;
				} else {
					count+=0;
					continue;
				}
			}
			if (supervisorIDField.getText().isEmpty()|| supervisorPasswordField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Text Field cannot be empty!", "Warning", JOptionPane.ERROR_MESSAGE);
				
			} else if (count == 0) {
				JOptionPane.showMessageDialog(this, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
				count = 0;
				
				
			} else if (count == 1) {
				JOptionPane.showMessageDialog(this, "Welcome"+supervisorIDField.getText()  , "Sucess", JOptionPane.INFORMATION_MESSAGE);
				String supervisorID = "'"+supervisorIDField.getText()+"'";
				count = 0;
				gateway.dispose();
				ChangeSalesEntryDetail csed = new ChangeSalesEntryDetail();
				
			}
		} 
	}


	public String getSupervisorIDSaved() {
		return supervisorIDSaved;
	}


	public void setSupervisorIDSaved(String supervisorIDSaved) {
		this.supervisorIDSaved = supervisorIDSaved;
	}
	


}
