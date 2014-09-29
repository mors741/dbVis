import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Calendar;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Client{

	Client me = this;
	private JFrame frame;
	protected JTable table;
	private JPopupMenu popup=new JPopupMenu();
	AddForm addForm;
	DeleteForm deleteForm;
	EditForm editForm;
	DefaultTableModel m;
	Connection connection = null;
	int nextId;
	int selectedRowId;
	TableStatus status = TableStatus.PASSIV;
    String url = "jdbc:postgresql://127.0.0.1:5432/mydb";
    String name = "evgeny";
    String password = "Ub40sAnti7bhgLsd";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Persons");
		frame.setBounds(100, 100, 449, 357);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, name, password);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JLabel lblHello = new JLabel("");
		lblHello.setHorizontalAlignment(SwingConstants.CENTER);
		lblHello.setFont(new Font("Dialog", Font.BOLD, 12));
		lblHello.setBounds(12, 311, 425, 15);
		frame.getContentPane().add(lblHello);
		
		JButton Refresh = new JButton("Refresh");
		Refresh.setBounds(243, 274, 117, 25);
		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
				lblHello.setText("Last update: "+Calendar.getInstance().getTime().toString());
			}
		});
		frame.getContentPane().add(Refresh);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addForm = new AddForm(me);
				status = TableStatus.ADDING;
				addForm.setVisible(true);
			}
		});
		add.setBounds(79, 274, 117, 25);
		frame.getContentPane().add(add);
		m = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Last Name", "First Name", "Address", "City"
				}
			);
		refresh();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 425, 250);
		frame.getContentPane().add(scrollPane);
		
		JMenuItem edit = new JMenuItem("Edit");
		JMenuItem delete = new JMenuItem("Delete");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editForm = new EditForm(me);
				status = TableStatus.EDITING;
				editForm.setVisible(true);
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteForm = new DeleteForm(me);
				deleteForm.setVisible(true);
			}
		});
		popup.add(edit);
		popup.add(delete);
		
		table = new JTable();
		table.setEnabled(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
			          int row = table.rowAtPoint(e.getPoint());
			          table.setRowSelectionInterval(row, row); 
			    }
				else if(e.getButton() == MouseEvent.BUTTON3) {
			          int row = table.rowAtPoint(e.getPoint());
			          table.setRowSelectionInterval(row, row); 
			          selectedRowId = (int) m.getValueAt(row, 0);
			          popup.show(table, e.getX(), e.getY());
			    }
			}
		});
		
		
		scrollPane.setViewportView(table);
		table.setModel(m);
		
		
	}
	
	void refresh(){
		try {
			Object[] d = new Object[5];
			m.setRowCount(0);
			
			Statement statement = connection.createStatement();
			ResultSet result1 = statement.executeQuery(
                    "select personid, lastname, firstname, address, cities.name from persons, cities where city_id = id order by personid");
			while (result1.next()) {
				d[0]=result1.getInt("personid");
				d[1]=result1.getString("lastname");
				d[2]=result1.getString("firstname");
				d[3]=result1.getString("address");
				d[4]=result1.getString("name");
				m.addRow(d);
            }
			nextId =(int) d[0]+1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
