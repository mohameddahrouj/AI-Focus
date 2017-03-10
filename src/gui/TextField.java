package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * An area to enter text. When the user hits return after entering text, the
 * actionPerformed method is called.
 */
public class TextField extends JTextField implements ActionListener {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param text
	 *            initial text (may be "").
	 * @param width
	 *            approximate width of the field, in characters.
	 */
	public TextField(String text, int width) {
		super(text, width);
		addActionListener(this);
	}

	/** Called when the user hits return after entering text in this text field. */
	public void actionPerformed() {
		// Does nothing by default
	}

	public void actionPerformed(ActionEvent e) {
		actionPerformed();
	}

}
