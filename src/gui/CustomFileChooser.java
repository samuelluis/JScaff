package gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CustomFileChooser extends JTextField {
	public CustomFileChooser(){
		this("", 20);
	}

	public CustomFileChooser(int columns) {
		this("",columns);
	}

	public CustomFileChooser(String text) {
		this(text,20);
	}

	public CustomFileChooser(String text, int columns) {
		super(text, columns);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				JFileChooser chooser = new JFileChooser(new File("."));
				if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					setText(chooser.getSelectedFile().getAbsolutePath());
					setFocusable(false);
					setFocusable(true);
				}
			}
		});
	}
}
