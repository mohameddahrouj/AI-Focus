package gui;

import static java.lang.Math.*;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.Color.*;

/**
 * A graphic line.
 * <p>
 * <strong>IMPORTANT:</strong> a line should be added to the gui with the
 * zero-argument add method, not the version that specifies a center. (Because
 * the line's endpoints have already been specified, specifying the center would
 * be unnecessarily tedious.)
 */
public class Line extends JComponent {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/** Color of this line. */
	private Color color;

	/** X coordinate of endpoint 1. */
	private int x1;

	/** X coordinate of endpoint 2. */
	private int x2;

	/** Y coordinate of endpoint 1. */
	private int y1;

	/** Y coordinate of endpoint 2. */
	private int y2;

	/**
	 * Creates a black line from x1, y1 to x2, y2.
	 */
	public Line(int x1, int y1, int x2, int y2) {
		this(x1, y1, x2, y2, BLACK);
	}

	/**
	 * Creates a line from x1, y1 to x2, y2, of the specified color.
	 */
	public Line(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		setBounds(min(x1, x2) - 1, min(y1, y2) - 1, abs(x2 - x1) + 2, abs(y2
				- y1) + 2);
		this.color = color;
	}

	@Override
	public void paintComponent(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		if (color != null) {
			g2.setColor(color);
			g2.drawLine(x1 - getX(), y1 - getY(), x2 - getX(), y2 - getY());
		}
	}

	/** This is called whenever the line is redrawn. */
	public void update() {
		// Does nothing by default
	}

}
