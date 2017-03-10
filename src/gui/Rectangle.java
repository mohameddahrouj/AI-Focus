package gui;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.*;
import static java.awt.Color.*;

/** A rectangle (which might be a square). */
public class Rectangle extends Shape {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a default rectangle (20x20 pixels, red background, black outline,
	 * no text).
	 */
	public Rectangle() {
		this(20, 20, RED, BLACK, null);
	}

	/**
	 * @param width
	 *            width of the rectangle, in pixels.
	 * @param height
	 *            height of the rectangle, in pixels.
	 * @param fill
	 *            fill color, or null for none.
	 * @param outline
	 *            outline color, or null for none.
	 * @param text
	 *            text on the rectangle, or null for none.
	 */
	public Rectangle(int width, int height, Color fill, Color outline, String text) {
		super(new Rectangle2D.Double(0, 0, width - 1, height - 1), fill, outline, text);
		setPreferredSize(new Dimension(width, height));
	}

}
