package gui;

import javax.swing.JLabel;

/** Text to display graphically. */
public class Label extends JLabel {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	public Label(String text) {
		super(text);
	}

	@Override
	public void setText(String text) {
		// Most of this code is to keep the new text centered at the same place
		int x = getX() + getWidth() / 2;
		int y = getY() + getHeight() / 2;
		super.setText(text);
		int w = getPreferredSize().width;
		int h = getPreferredSize().height;
		setBounds(x - w / 2, y - h / 2, w, h);
	}

}
