import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AddCity extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	Client client;
	FieldForm parent;
	String cityName;

	public AddCity(Client cli) {
		client = cli;
		if (client.status == TableStatus.ADDING)	parent = client.addForm;
		else parent = client.editForm;
		cityName = parent.getCityName();
		setTitle("Add city");
		setBounds(100, 100, 349, 107);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel label = new JLabel("Do you want to add new city "+cityName+"?");
			label.setBounds(0, 12, 337, 15);
			contentPanel.add(label);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Yes");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Statement statement = client.connection.createStatement();
							statement.executeUpdate("insert INTO cities VALUES ("+getNextId()+", '"+cityName+"');");
							parent.changeDB();
							parent.addCity.setVisible(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("Yes");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						parent.addCity.setVisible(false);
						parent.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private int getNextId() throws SQLException {
		Statement statement = client.connection.createStatement();
		ResultSet resultCity = statement.executeQuery("select id from cities order by id desc;");
		resultCity.next(); 
		return resultCity.getInt("id")+1;
	}

}
