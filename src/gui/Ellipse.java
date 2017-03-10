package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import static java.awt.Color.*;

/** An ellipse (which might be a circle). */
public class Ellipse extends Shape {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a default ellipse (20x20 pixels, red background, green outline,
	 * no text).
	 */
	public Ellipse() {
		this(20, 20, RED, GREEN, null);
	}

	/**
	 * @param width
	 *            width of the ellipse, in pixels.
	 * @param height
	 *            height of the ellipse, in pixels.
	 * @param fill
	 *            fill color, or null for none.
	 * @param outline
	 *            outline color, or null for none.
	 * @param text
	 *            text on the ellipse, or null for none.
	 */
	public Ellipse(int width, int height, Color fill, Color outline, String text) {
		super(new Ellipse2D.Double(0, 0, width - 1, height - 1), fill, outline,
				text);
		setPreferredSize(new Dimension(width, height));
	}

}
