import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class DeleteForm extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client parent;

	/**
	 * Create the dialog.
	 */
	public DeleteForm(Client par) {
		this.parent=par;
		setTitle("Delete");
		setBounds(120, 120, 151, 97);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Yes");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Statement statement = parent.connection.createStatement();
							statement.executeUpdate("delete from persons where personid = "+parent.selectedRowId+";");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						parent.refresh();
						parent.deleteForm.setVisible(false);
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
						parent.deleteForm.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JLabel lblAreYouSure = new JLabel("Are you sure?");
			lblAreYouSure.setHorizontalAlignment(SwingConstants.CENTER);
			getContentPane().add(lblAreYouSure, BorderLayout.CENTER);
		}
	}
}
