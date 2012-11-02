import gui.Login;

import javax.swing.UIManager;


public class Main {
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		new Login();
	}
}
