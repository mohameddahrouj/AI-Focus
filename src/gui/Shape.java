package gui;

import java.awt.Color;
import static java.awt.Color.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import javax.swing.JComponent;

/**
 * A geometric shape to add to a gui. Among other features, the shape can detect
 * mouse presses; to react to them, override mousePressed.
 */
public abstract class Shape extends JComponent implements MouseListener {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/** The color, if any, with which the shape should be filled. */
	private Color fill;

	/** The color, if any, of the shape's outline. */
	private Color outline;

	/** The text, if any, drawn at the center of the shape. */
	private String text;

	/** The underlying shape object. */
	private java.awt.Shape underlyingShape;

	/**
	 * @param shape
	 *            the underlying instance of java.awt.shape.
	 * @param fill
	 *            fill color, or null for none.
	 * @param outline
	 *            outline color, or null for none.
	 * @param text
	 *            text on the shape, or null for none.
	 */
	public Shape(java.awt.Shape shape, Color fill, Color outline, String text) {
		this.underlyingShape = shape;
		this.fill = fill;
		this.outline = outline;
		this.text = text;
		addMouseListener(this);
	}

	/** Adds component <strong>centered</strong> at x, y. */
	public void add(JComponent component, int x, int y) {
		int w = component.getPreferredSize().width;
		int h = component.getPreferredSize().height;
		component.setBounds(x - w / 2, y - h / 2, w, h);
		add(component);
	}

	/** Draws text at the center of this shape. */
	protected void drawCenteredText(Graphics2D g2) {
		Rectangle2D bounds = getBounds();
		Rectangle2D stringBounds = g2.getFontMetrics()
				.getStringBounds(text, g2);
		g2.drawString(text, (int) (bounds.getCenterX() - (stringBounds
				.getWidth() / 2)), (int) (bounds.getCenterY() + (stringBounds
				.getHeight() / 2)));
	}

	@Override
	public java.awt.Rectangle getBounds() {
		return underlyingShape.getBounds();
	}

	/** Returns the fill color (if any) of this shape. */
	public Color getFill() {
		return fill;
	}

	/** Returns the outline color (if any) of this shape. */
	public Color getOutline() {
		return outline;
	}

	/** Returns the text (if any) to be drawn at the center of this shape. */
	public String getText() {
		return text;
	}

	/** Returns the java.awt.Shape object underlying this shape. */
	protected java.awt.Shape getUnderlyingShape() {
		return underlyingShape;
	}

	public void mouseClicked(MouseEvent e) {
		// Does nothing
	}

	public void mouseEntered(MouseEvent e) {
		// Does nothing
	}

	public void mouseExited(MouseEvent e) {
		// Does nothing
	}

	/** Called when the mouse is pressed over this shape. */
	public void mousePressed() {
		// Does nothing by default
	}

	public void mousePressed(MouseEvent e) {
		mousePressed();
	}

	public void mouseReleased(MouseEvent e) {
		// Does nothing
	}

	@Override
	public void paintComponent(Graphics g) {
		update();
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (fill != null) {
			g2.setColor(fill);
			g2.fill(underlyingShape);
		}
		if (outline != null) {
			g2.setColor(outline);
			g2.draw(underlyingShape);
		} else if (fill != null) {
			// If there is no outline, make the outline the same as the fill
			// color
			g2.draw(underlyingShape);
		}
		if (text != null) {
			g2.setColor(BLACK);
			drawCenteredText(g2);
		}
	}

	/** Sets the fill color (if any) of this shape. */
	public void setFill(Color fill) {
		this.fill = fill;
	}

	/** Sets the fill color (if any) of this shape. */
	public void setOutline(Color outline) {
		this.outline = outline;
	}

	/** Sets the text (if any) to be drawn at the center of this shape. */
	public void setText(String text) {
		this.text = text;
	}

	/** This is called whenever this shape is redrawn. */
	public void update() {
		// Does nothing by default
	}

}
