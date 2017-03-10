package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import static java.lang.Math.*;
import static java.awt.Color.*;

/** A regular polygon, such as a hexagon. */
public class RegularPolygon extends Shape {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/** Returns the x coordinates of the vertices. Used by constructor. */
	protected static int[] xCoordinates(int sides, int radius, double rotation) {
		int[] x = new int[sides];
		double angle = 2 * PI / sides;
		for (int i = 0; i < sides; i++) {
			rotation += angle;
			x[i] = radius + (int) (radius * cos(rotation));
		}
		return x;
	}

	/** Returns the y coordinates of the vertices. Used by constructor. */
	protected static int[] yCoordinates(int sides, int radius, double rotation) {
		int[] y = new int[sides];
		double angle = 2 * PI / sides;
		for (int i = 0; i < sides; i++) {
			rotation += angle;
			y[i] = radius - (int) (radius * sin(rotation));
		}
		return y;
	}

	/**
	 * Creates a default regular polygon (a hexagon, radius 10, unrotated, red
	 * background, black outline, no text).
	 */
	public RegularPolygon() {
		this(6, 10, 0, RED, BLACK, null);
	}

	/**
	 * @param sides
	 *            number of sides.
	 * @param radius
	 *            distance in pixels from center to a point.
	 * @param rotation
	 *            angle of one point, in radians counterclockwise from right.
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
	public RegularPolygon(int sides, int radius, double rotation, Color fill, Color outline, String text) {
		super(new Polygon(xCoordinates(sides, radius - 1, rotation), yCoordinates(sides, radius - 1, rotation), sides),
				fill, outline, text);
		setPreferredSize(new Dimension(radius * 2, radius * 2));
	}

}
