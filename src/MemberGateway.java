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

public class MemberGateway extends JFrame implements ActionListener {
	JFrame gateway;
	JPanel centerPanel;
	JLabel memberID;
	JTextField memberIDField;
	JButton memberButton, nonMemberButton;
	Vector<member> listMember = new Vector<>();
	Connect con = new Connect();

	public MemberGateway() {
		centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		memberID = new JLabel("Input Member ID");
		memberIDField = new JTextField(17);
		memberButton = new JButton("Continue as Member");
		memberButton.addActionListener(this);
		nonMemberButton = new JButton("Continue as Non Member");
		nonMemberButton.addActionListener(this);
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(memberID, gbc);
		
		gbc.gridx = 1;
		centerPanel.add(memberIDField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		centerPanel.add(memberButton, gbc);
		
		gbc.gridx = 1;
		centerPanel.add(nonMemberButton, gbc);
		
		gateway = new JFrame();
		gateway.add(centerPanel, BorderLayout.CENTER);
		gateway.setSize(400,200);
		gateway.setLocationRelativeTo(null);
		gateway.setTitle("Member ?");
		gateway.setVisible(true);
		gateway.setResizable(false);
		gateway.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ResultSet rs;
		String id = memberIDField.getText();
		int count = 0;
		if (arg0.getSource().equals(memberButton)) {
			String searchQuery = "SELECT * FROM member";
			rs = con.executeQuery(searchQuery);
			try {
				while (rs.next()) {
					listMember.add(new member(rs.getString("memberID"), rs.getString("name"), rs.getString("gender")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			for (int i = 0; i < listMember.size(); i++) {
				String logMember = String.format("SELECT * FROM member "
						+ "WHERE memberID = '"+listMember.get(i).getMemberID()+"'");
				con.executeQuery(logMember);
				if (listMember.get(i).getMemberID().equals(memberIDField.getText())) {
					count++;
					break;
				} else {
					count+=0;
					continue;
				}
			}
			if (memberIDField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Text Field cannot be empty for member!", "Warning", JOptionPane.ERROR_MESSAGE);
				
			} else if (count == 0) {
				JOptionPane.showMessageDialog(this, "Member does not exist", "Error", JOptionPane.ERROR_MESSAGE);
				count = 0;
			} else if (count == 1) {
				JOptionPane.showMessageDialog(this, memberIDField.getText()+" set as member", "Sucess", JOptionPane.INFORMATION_MESSAGE);
				String memberID = "'"+memberIDField.getText()+"'";
				count = 0;
				salesEntry se = new salesEntry(memberID);
				se.memberID1 = memberID;
				gateway.dispose();
				
			}
		} else if (arg0.getSource().equals(nonMemberButton)) {
			JOptionPane.showMessageDialog(this, "Continuing as guest", "Sucess", JOptionPane.INFORMATION_MESSAGE);
			String memberID = "NULL";
			salesEntry se = new salesEntry(memberID);
			se.memberID1 = memberID;
			gateway.dispose();
			
		}
		
	}
}
