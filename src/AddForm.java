import java.sql.*;

import org.postgresql.util.PSQLException;

public class AddForm extends FieldForm {


	private static final long serialVersionUID = 1L;

	public AddForm(Client par) {
		super(par);
		this.setTitle("Add");
	}

	@Override
	void changeDB() {
		try {
			Statement statement = parent.connection.createStatement();
			int cityId = getCityId(txtCity.getText());
			statement.executeUpdate("insert INTO persons VALUES ("+parent.nextId +
					", '"+txtLastname.getText()+"','"+txtFirstname.getText()+"', '"+txtAddress.getText()+"', "+cityId+");");
			parent.refresh();
			parent.addForm.setVisible(false);
		} catch (PSQLException e1) {
			addCity = new AddCity(parent);
			addCity.setVisible(true);
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

}
