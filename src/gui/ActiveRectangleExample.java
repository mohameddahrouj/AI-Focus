package gui;

import java.awt.Color;

/**
 * An extension of rectangle that calls back to its GuiExample when clicked on.
 *
 * @see GuiExample
 */
public class ActiveRectangleExample extends Rectangle {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/** The gui associated with this ActiveRectangle. */
	private GuiExample gui;

	public ActiveRectangleExample(Color color, GuiExample gui) {
		// The rectangle is 100 pixels wide and 50 high.
		// It has the specified fill color, no outline, and no text.
		super(100, 50, color, null, null);
		// The gui must be stored so that this rectangle can inform
		// it of mouse clicks.
		this.gui = gui;
	}

	/**
	 * Tells the gui that this rectangle has been clicked on, passing the fill
	 * color as an argument.
	 */
	public void mousePressed() {
		gui.handleClick(getFill());
	}

}