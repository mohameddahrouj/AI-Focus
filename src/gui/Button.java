package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/** A button. When it is pressed, mousePressed() is called. */
public class Button extends JButton implements ActionListener {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	public Button(String text) {
		super(text);
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		mousePressed();
	}

	/** This is called when this button is pressed. */
	public void mousePressed() {
		// Does nothing by default
	}

}
