package focus;

import java.awt.Color;

import gui.*;

/** GUI for the Focus game. */
public class FocusGui extends Gui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Mode choosing a destination. */
	public static final int CHOOSING_DESTINATION = 1;
	
	/** Mode choosing a source square or reserves. */
	public static final int CHOOSING_SOURCE = 0;
	
	/** Color of board squares. */
	public static final Color LIGHT_ORANGE = new Color(255,219,88);
	
	/**0 = Human vs Human 
	 * 1 = Human vs AI
	 * 2 = AI 	 vs AI*/
	public static final int gameMode = 2;
	
	public static void main(String[] args) {
		new FocusGui().display();
	}
	
	/** The game behind this GUI. */
	private Focus game;
	
	/** Instructions for player. */
	private Label instructions;
	
	/** Current mode, either CHOOSING_SOURCE or CHOOSING_DESTINATION. */
	private int mode;

	/** Boxes displaying how many pieces each player has in reserve. */
	private ReservesBox[] reservesBoxes;
	
	/** Column of the source square (or -1 for reserves). */
	private int sourceColumn;
	
	/** Row of the source square (or -1 for reserves). */
	private int sourceRow;

	/** Graphic squares. */
	private Square[][] squares;

	public FocusGui() {
		game = new Focus();
		squares = new Square[game.BOARD_WIDTH][game.BOARD_WIDTH];
		for (int r = 0; r < game.BOARD_WIDTH; r++) {
			for (int c = 0; c < game.BOARD_WIDTH; c++) {
				if (game.isOnBoard(r, c )) {
					squares[r][c] = new Square(r, c, this);
					add(squares[r][c], 125 + c * 50, 25 + r * 50);
				}
			}
		}
		reservesBoxes = new ReservesBox[3];
		reservesBoxes[game.GREEN] = new ReservesBox(game.GREEN, this);
		reservesBoxes[game.RED] = new ReservesBox(game.RED, this);
		add(reservesBoxes[game.GREEN], 150, 450);
		add(reservesBoxes[game.RED], 450, 450);
		mode = CHOOSING_SOURCE;
		instructions = new Label("Green: click on source square or reserves.");
		add(instructions, 300, 550);
	}
	
	/** Updates graphic elements after moving to row, column. */
	protected void chooseDestination(int row, int column) {
		squares[row][column].update();
		mode = CHOOSING_SOURCE;
		String color;
		if (game.getCurrentPlayer() == game.GREEN) {
			color = "Green";
		} else {
			color = "Red";
		}
		if (game.gameOver()) {
			instructions.setText("Game over.");
		} else {
			instructions.setText(color + ": click on source square or reserves.");
		}
	}
	
	/** Reacts to a player choosing a destination. */
	protected void chooseSource(int row, int column) {
		sourceRow = row;
		sourceColumn = column;
		mode = CHOOSING_DESTINATION;
		instructions.setText("Click on destination square.");
	}

	/** Returns the game behind this GUI. */
	public Focus getGame() {
		return game;
	}

	/** Reacts to a player clicking on a square. */
	public void squareClicked(int row, int column) {
		if (mode == CHOOSING_SOURCE) {
			if (row == -1) {
				if (game.reserves(game.getCurrentPlayer()) > 0) {
					reservesBoxes[game.getCurrentPlayer()].setMarked(true);
					reservesBoxes[game.getCurrentPlayer()].update();
					chooseSource(row, column);
				}
			} else if (game.isLegalSource(row, column)) {
				squares[row][column].setMarked(true);
				squares[row][column].update();
				chooseSource(row, column);
			}
		} else {
			if (sourceRow == -1) {
				reservesBoxes[game.getCurrentPlayer()].setMarked(false);
				reservesBoxes[game.getCurrentPlayer()].update();
				game.playFromReserves(row, column);
				chooseDestination(row, column);
			} else if (game.isLegal(new Move(sourceRow, sourceColumn, row, column))) {
				game.move(new Move(sourceRow, sourceColumn, row, column));
				squares[sourceRow][sourceColumn].setMarked(false);
				squares[sourceRow][sourceColumn].update();
				chooseDestination(row, column);
			}
		}
		repaint();
	}

}
