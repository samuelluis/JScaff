package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dbms.Store;

@SuppressWarnings("serial")
public class Tables extends JFrame {
	private JScrollPane scroll;
	private DefaultTableModel model;
	private JTable table;
	private JPanel actions;
	private JButton select;
	private JButton cancel;
	
	public Tables(){
		super("Tables Selection");
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
		}
		return scroll;
	}

	public DefaultTableModel getModel() {
		if(model==null){
			model = new DefaultTableModel(null, new String[]{"","Table"}){
				public boolean isCellEditable(int row, int column) {
					return column==0;
				}
				public java.lang.Class<?> getColumnClass(int column) {
					return (column==0) ? Boolean.class : super.getColumnClass(column);
				}
			};
			for (String value : Store.tables) getModel().addRow(new Object[]{false,value});
		}
		return model;
	}

	public JTable getTable() {
		if(table==null){
			table = new JTable(getModel());
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
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
						Store.tables = new ArrayList<String>();
						if((Boolean)getModel().getValueAt(i, 0)){
							Store.tables.add(getModel().getValueAt(i, 1).toString());
							System.out.println(getModel().getValueAt(i, 1).toString());
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
					new Database();
				}
			});
		}
		return cancel;
	}
}
