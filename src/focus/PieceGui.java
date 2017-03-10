package focus;

import java.awt.Color;

import gui.Ellipse;

/** Graphic representation of one piece in the Focus game. */
public class PieceGui extends Ellipse {

	/** The (graphic) square on which this piece appears. */
	private Square square;

	public PieceGui(Square square, Color fill, Color outline) {
		super(25, 12, fill, outline, null);
		this.square = square;
	}

	/** Tells the square that the mouse has been pressed over this piece. */
	public void mousePressed() {
		square.mousePressed();
	}

}
