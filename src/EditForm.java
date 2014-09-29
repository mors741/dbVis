import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.util.PSQLException;

public class EditForm extends FieldForm {

	private static final long serialVersionUID = 1L;

	public EditForm(Client par) {
		super(par);
		this.setTitle("Edit");
		int selectedRow = parent.table.getSelectedRow();
		txtLastname.setText((String) parent.table.getValueAt(selectedRow, 1));
		txtFirstname.setText((String) parent.table.getValueAt(selectedRow, 2));
		txtAddress.setText((String) parent.table.getValueAt(selectedRow, 3));
		txtCity.setText((String) parent.table.getValueAt(selectedRow, 4));
	}

	@Override
	void changeDB() {
		try {
			Statement statement = parent.connection.createStatement();
			int cityId = getCityId(txtCity.getText());
			statement.executeUpdate("UPDATE persons SET lastname = '"+txtLastname.getText()+
					"', firstname = '"+txtFirstname.getText()+"', address = '"+txtAddress.getText()+
					"', city_id = "+cityId+" where personid = "+parent.selectedRowId+";");
			parent.refresh();
			parent.editForm.setVisible(false);
		} catch (PSQLException e1) {
			addCity = new AddCity(parent);
			addCity.setVisible(true);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
