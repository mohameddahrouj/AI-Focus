package gui;

import java.awt.*;
import javax.swing.*;

/**
 * Graphic user interface. Graphic programs should extend this class. After
 * creating an instance of a subclass, call display() to show it on the screen.
 * <p>
 * Components (such as Shapes) are added with the add method.
 * <p>
 * This class hides many of the messy details of Java GUIs. The 600x600 pixel
 * gui is automatically placed in a JFrame (window) and various properties (such
 * as the title and the default close operation) are set to reasonable defaults.
 */
public abstract class Gui extends JPanel {

	/**
	 * This has to do with serialization. It is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/** The gui has a null layout manager and a size of 600x600. */
	public Gui() {
		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
	}

	/** Adds component <strong>centered</strong> at x, y. */
	public void add(JComponent component, int x, int y) {
		int w = component.getPreferredSize().width;
		int h = component.getPreferredSize().height;
		component.setBounds(x - w / 2, y - h / 2, w, h);
		add(component);
	}

	/** Displays this gui in a window. */
	public void display() {
		JFrame frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setTitle(this.getClass().getSimpleName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
