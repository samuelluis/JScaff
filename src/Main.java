import gui.Login;

import javax.swing.UIManager;


public class Main {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		new Login();
		
		
		//Object[] possibilities = {"Hola", "Adios", "Muerete"};
		//String s = (String)JOptionPane.showInputDialog(null,"Select a Author: ","Author Selection",JOptionPane.PLAIN_MESSAGE,null,possibilities,"Hola");
		//System.out.println(s);
		
		/*JTextField name = new JTextField(20);
		JTextField lastName = new JTextField(20);
		Object[] inputs = {new JLabel("Name: "), name, new JLabel("Last Name: "), lastName};
		boolean create = JOptionPane.showConfirmDialog(null,inputs,"Author Creation",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)==0;
		if(create)
			System.out.println(name.getText()+" "+lastName.getText());*/
	}
}
