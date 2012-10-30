package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.QueryHelper;
import utils.ReflectionHelper;
import utils.XMLReader;
import dbms.SQLConnection;
import dbms.Store;

@SuppressWarnings("serial")
public class Login extends JFrame {
	private JComboBox engines;
	private JPanel connectionContainer;
	private JButton connect;
	private JButton cancel;
	private HashMap<String, JTextField> connectionFields;
	
	public Login(){
		super("Login");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(getField(getLabel("Engine: "),getEngines()), BorderLayout.NORTH);
		add(getConnectionContainer(), BorderLayout.CENTER);
		add(getField(getConnect(), getCancel()), BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	public JComboBox getEngines(){
		if(engines==null){
			engines = new JComboBox(ReflectionHelper.getFiles("dbms.xml"));
			engines.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectEngine();
				}
			});
			selectEngine();
		}
		return engines;
	}
	
	public void selectEngine(){
		int count = 1;
		getConnectionContainer().removeAll();
		Store.struct = XMLReader.getStruct(engines.getSelectedItem().toString());
		connectionFields = new HashMap<String, JTextField>();
		for (String field : Store.struct.connection.keySet()) {
			getConnectionContainer().setLayout(new GridLayout(count,1));
			JTextField jfield = new JTextField(20);
			jfield.setText(Store.struct.connection.get(field));
			connectionFields.put(field, jfield);
			getConnectionContainer().add(getField(getLabel(field+": "),jfield));
			count++;
		}
		pack();
		setLocationRelativeTo(null);
	}

	public JPanel getConnectionContainer() {
		if(connectionContainer==null){
			connectionContainer = new JPanel();
		}
		return connectionContainer;
	}

	public JButton getConnect() {
		if(connect==null){
			connect = new JButton("Connect");
			connect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Store.connection = new HashMap<String, String>();
					for (String key : connectionFields.keySet()) {
						Store.connection.put(key, connectionFields.get(key).getText());
					}
					try {
						String query = "select "+QueryHelper.enclose("schema_name")+" from "+QueryHelper.enclose("schemata");
						ResultSet rs = SQLConnection.getConnection(Store.struct.getGeneral().getConnection(Store.connection), Store.struct.getGeneral().driverPath).executeQuery(query);
						Store.databases = new ArrayList<String>();
						while(rs.next()){
							Store.databases.add(rs.getString(1));
						}
						dispose();
						new Database();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			});
		}
		return connect;
	}

	public JButton getCancel() {
		if(cancel==null){
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return cancel;
	}

	public JPanel getField(JComponent ...fields){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		for (JComponent component : fields) {
			panel.add(component);
		}
		
		return panel;
	}
	
	public JLabel getLabel(String text){
		return new JLabel(text);
	}
}
