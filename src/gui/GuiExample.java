package gui;

import java.awt.Color;

/** Example of a graphic program. */
public class GuiExample extends Gui {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/** The colored ellipse. */
	private Ellipse ellipse;

	/** The text field displaying a String representing the ellipse's color. */
	private TextField textField;

	public static void main(String[] args) {
		new GuiExample().display();
	}

	public GuiExample() {
		// Add a label
		add(new Label("Click on a rectangle"), 300, 100);
		// Add some ActiveRectangleExamples. When these are clicked on, they
		// call the handleClick() method from this class.
		add(new ActiveRectangleExample(Color.RED, this), 150, 200);
		add(new ActiveRectangleExample(Color.GREEN, this), 300, 200);
		add(new ActiveRectangleExample(Color.BLUE, this), 450, 200);
		// Add a colored ellipse
		ellipse = new Ellipse(200, 75, Color.LIGHT_GRAY, Color.BLACK,
				"An ellipse");
		add(ellipse, 300, 300);
		// Note that more recently added objects are drawn farther back.
		// This is a property of the Java graphics libraries, not ljing.
		// Add some lines
		add(new Line(150, 200, 300, 300, Color.BLACK));
		add(new Line(300, 200, 300, 300, Color.BLACK));
		add(new Line(450, 200, 300, 300, Color.BLACK));
		// Add a text field
		textField = new TextField("Some text", 20);
		add(textField, 150, 400);
		// Add a button. The button doesn't do anything.
		add(new Button("A button"), 150, 500);
		// Add a pentagon
		add(new RegularPolygon(5, 100, Math.PI / 2, null, Color.BLACK,
				"A regular polygon"), 450, 450);
	}

	/** Responds to a click from one of the colored ActiveRectangleExamples. */
	public void handleClick(Color color) {
		// Set the color of the ellipse
		ellipse.setFill(color);
		// Display the color in the text field
		textField.setText(color.toString());
		// Repaint everything
		repaint();
	}

}