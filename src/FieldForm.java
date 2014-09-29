import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;


public abstract class FieldForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client parent;
	FieldForm me =this;
	private final JPanel contentPanel = new JPanel();
	protected AddCity addCity;
	protected JTextField txtLastname;
	protected JTextField txtFirstname;
	protected JTextField txtAddress;
	protected JTextField txtCity;
	private JLabel lblLastName;
	private JLabel lblFirstName;
	private JLabel lblAddress;
	private JLabel lblCity;

	public FieldForm(Client par) {
		this.parent = par;
		setBounds(110, 110, 233, 198);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtLastname = new JTextField();
			txtLastname.setBounds(105, 12, 114, 19);
			contentPanel.add(txtLastname);
			txtLastname.setColumns(10);
		}
		{
			txtFirstname = new JTextField();
			txtFirstname.setBounds(105, 43, 114, 19);
			contentPanel.add(txtFirstname);
			txtFirstname.setColumns(10);
		}
		{
			txtAddress = new JTextField();
			txtAddress.setBounds(105, 74, 114, 19);
			contentPanel.add(txtAddress);
			txtAddress.setColumns(10);
		}
		
		txtCity = new JTextField();
		txtCity.setBounds(105, 105, 114, 19);
		contentPanel.add(txtCity);
		txtCity.setColumns(10);
		{
			lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(12, 12, 89, 19);
			contentPanel.add(lblLastName);
		}
		{
			lblFirstName = new JLabel("First Name");
			lblFirstName.setBounds(12, 43, 83, 19);
			contentPanel.add(lblFirstName);
		}
		{
			lblAddress = new JLabel("Address");
			lblAddress.setBounds(12, 74, 91, 19);
			contentPanel.add(lblAddress);
		}
		{
			lblCity = new JLabel("City");
			lblCity.setBounds(12, 105, 60, 19);
			contentPanel.add(lblCity);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						changeDB();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						me.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	int getCityId (String city) throws SQLException{
		Statement statement = parent.connection.createStatement();
		ResultSet resultCity = statement.executeQuery("select id from cities where name = '"+ txtCity.getText() +"';");
		resultCity.next(); 
		return resultCity.getInt("id");
	}
	
	String getCityName() {
		return this.txtCity.getText();
	}
	
	abstract void changeDB();
}
