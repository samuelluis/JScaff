package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utils.QueryHelper;

import dbms.SQLConnection;
import dbms.Store;

@SuppressWarnings("serial")
public class Database extends JFrame {
	private JScrollPane scroll;
	private DefaultTableModel model;
	private JTable table;
	private JPanel actions;
	private JButton select;
	private JButton cancel;
	
	public Database(){
		super("Database Selection");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(getScroll(), BorderLayout.CENTER);
		add(getActions(), BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public JScrollPane getScroll() {
		if(scroll==null){
			scroll = new JScrollPane(getTable());
			scroll.setPreferredSize(new Dimension(300, 400));
		}
		return scroll;
	}

	public DefaultTableModel getModel() {
		if(model==null){
			model = new DefaultTableModel(null, new String[]{"","Database"}){
				public boolean isCellEditable(int row, int column) {
					return column==0;
				}
				public java.lang.Class<?> getColumnClass(int column) {
					return (column==0) ? Boolean.class : super.getColumnClass(column);
				}
			};
			for (String value : Store.databases) getModel().addRow(new Object[]{false,value});
		}
		return model;
	}

	public JTable getTable() {
		if(table==null){
			table = new JTable(getModel());
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(table.getSelectedRow()>=0){
						for (int i = 0; i < table.getRowCount(); i++) {
							model.setValueAt(false, i, 0);
						}
						model.setValueAt(true, table.getSelectedRow(), 0);
					}
				}
			});
		}
		return table;
	}

	public JPanel getActions() {
		if(actions==null){
			actions = new JPanel();
			actions.setLayout(new FlowLayout());
			actions.add(getSelect());
			actions.add(getCancel());
		}
		return actions;
	}

	public JButton getSelect() {
		if(select==null){
			select = new JButton("Select");
			select.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < table.getRowCount(); i++) {
						if((Boolean)getModel().getValueAt(i, 0)){
							try {
								String query = "select "+QueryHelper.enclose("table_name")+" from "+QueryHelper.enclose("tables")+" where "+QueryHelper.enclose("table_schema")+"='"+getModel().getValueAt(i, 1)+"'";
								ResultSet rs = SQLConnection.getConnection(Store.struct.getGeneral().getConnection(Store.connection), Store.struct.getGeneral().driverPath).executeQuery(query);
								Store.tables = new ArrayList<String>();
								while(rs.next()){
									Store.tables.add(rs.getString(1));
									System.out.println(rs.getString(1));
								}
								dispose();
								new Tables();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}
		return select;
	}

	public JButton getCancel() {
		if(cancel==null){
			cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					new Login();
				}
			});
		}
		return cancel;
	}
}
