package focus;

import static java.awt.Color.*;

import gui.*;

import static focus.FocusGui.*;

/** Graphic square in the Focus game. */
public class Square extends Rectangle {

	/** Column of this square. */
	private int column;
	
	/** Gui to which this square is attached. */
	private FocusGui gui;
	
	/** True if this square is marked (highlighted). */
	private boolean marked;
	
	/** Row of this square. */
	private int row;

	public Square(int row, int column, FocusGui gui) {
		super(45, 45, LIGHT_ORANGE, null, null);
		this.row = row;
		this.column = column;
		this.gui = gui;
		update();
	}
	
	@Override
	public void mousePressed() {
		gui.squareClicked(row, column);
	}
	
	/** Sets whether this square is highlighted. */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	@Override
	public void update() {
		removeAll();
		if (marked) {
			// Colour when marked
			setFill(LIGHT_GRAY);
		} else {
			// Colour of tile
			setFill(LIGHT_ORANGE);
		}
		Deque pile = gui.getGame().getPile(row, column);
		String s = pile.toString();
		for (int i = 0; i < s.length(); i++) {
			Ellipse circle;
			if (s.charAt(i) == '#') {
				circle = new PieceGui(this, GREEN, RED);
			} else {
				circle = new PieceGui(this, RED, GREEN);
			}
			add(circle, 30 - (4 * i), 9 + (6 * i));
		}
	}

}
