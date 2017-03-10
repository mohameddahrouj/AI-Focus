package focus;

import java.awt.event.*;

import gui.*;

import static java.awt.Color.*;
import static focus.FocusGui.*;

/** Graphic box listing reserves for one player in the Focus game. */
public class ReservesBox extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Gui to which this box is attached. */
	private FocusGui gui;
	
	/** Color of the player whose reserves this box displays. */
	private int color;
		
	/** True if the box is currently marked (highlighted). */
	private boolean marked;
		
	/** Sets whether this box is highlighted. */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public ReservesBox(int color, FocusGui gui) {
		super(200, 50, LIGHT_GRAY, null, "Reserves");
		this.gui = gui;
		this.color = color;
		update();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		gui.squareClicked(-1, -1);
	}

	@Override
	public void update() {
		if (marked) {
			setFill(LIGHT_GRAY);
		} else {
			setFill(LIGHT_ORANGE);
		}
		if (color == 0) {//green
			setText("Green reserves: " + gui.getGame().reserves(color));
		} else {
			setText("Red reserves: " + gui.getGame().reserves(color));			
		}
	}

}
