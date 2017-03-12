package focus;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import gui.*;

/** GUI for the Focus game. */
public class FocusGui extends JPanel implements ActionListener{

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
	
	public boolean gameOver;
	
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
	
	private JButton moveButton;
	private JFrame frame;

	/** Adds component <strong>centered</strong> at x, y. */
	public void add(JComponent component, int x, int y) {
		int w = component.getPreferredSize().width;
		int h = component.getPreferredSize().height;
		component.setBounds(x - w / 2, y - h / 2, w, h);
		add(component);
	}

	/** Displays this gui in a window. */
	public void display() {
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setTitle("Focus");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public FocusGui() {
		game = new Focus();
		gameOver = false;
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
		setLayout(null);
		setPreferredSize(new Dimension(600, 600));
		add(reservesBoxes[game.GREEN], 150, 450);
		add(reservesBoxes[game.RED], 450, 450);
		
		moveButton = new JButton ("Generate green move!");
		
		moveButton.setActionCommand("move");
		
		moveButton.addActionListener(this);
		
		add(moveButton, 300 , 600);
		
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
		instructions.setText(color + ": click on source square or reserves.");

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
		
		String color = "Red  ";
		if (game.getCurrentPlayer() == game.GREEN) {
			color = "Green";
		}
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
				System.out.println(color + ": " + new Move(-1 , -1, row, column));
				game.playFromReserves(row, column);
				if (game.getCurrentPlayer() == game.GREEN) {
					moveButton.setText("Generate green move!");
				} else {
					moveButton.setText("Generate red move!");
				}
				chooseDestination(row, column);
			} else if (game.isLegal(new Move(sourceRow, sourceColumn, row, column))) {
				System.out.println(color + ": " + new Move(sourceRow, sourceColumn, row, column));
				game.move(new Move(sourceRow, sourceColumn, row, column));
				if (game.getCurrentPlayer() == game.GREEN) {
					moveButton.setText("Generate green move!");
				} else {
					moveButton.setText("Generate red move!");
				}
				squares[sourceRow][sourceColumn].setMarked(false);
				squares[sourceRow][sourceColumn].update();
				chooseDestination(row, column);
			}
		}
		repaint();
	}
	
	/** Reacts to a player clicking on a square. */
	public void AIChoseMove(Move m, boolean playFromReserves) {
		aiSource(m, playFromReserves);
		aiDestination(m, playFromReserves);
		repaint();
	}

	public void aiSource(Move m, boolean playFromReserves) {
		if (playFromReserves && m.getr1() == -1) {
			if (game.reserves(game.getCurrentPlayer()) > 0) {
				reservesBoxes[game.getCurrentPlayer()].setMarked(true);
				reservesBoxes[game.getCurrentPlayer()].update();
				chooseSource(m.getr1(), m.getc1());
			}
		} else if (game.isLegalSource(m.getr1(), m.getc1())) {
			squares[m.getr1()][m.getc1()].setMarked(true);
			squares[m.getr1()][m.getc1()].update();
			chooseSource(m.getr1(), m.getc1());
		}
	}
	
	public void aiDestination(Move m, boolean playFromReserves) {
		if (playFromReserves && sourceRow == -1) {
			reservesBoxes[game.getCurrentPlayer()].setMarked(false);
			reservesBoxes[game.getCurrentPlayer()].update();
			game.playFromReserves(m.getr2(), m.getc2());
			chooseDestination(m.getr2(), m.getc2());
		} else if (game.isLegal(new Move(sourceRow, sourceColumn, m.getr2(), m.getc2()))) {
			game.move(new Move(sourceRow, sourceColumn, m.getr2(), m.getc2()));
			squares[sourceRow][sourceColumn].setMarked(false);
			squares[sourceRow][sourceColumn].update();
			chooseDestination(m.getr2(), m.getc2());
		}
	}
	
	public void isFocusOver(){
		if (game.gameOver()) {
			gameOver = true;
			instructions.setText("Game over.");
			moveButton.setText("Game Over");
			moveButton.setEnabled(false);
			
			if (game.getCurrentPlayer() == game.GREEN) {
				JOptionPane.showMessageDialog(this," Green, you won!", "Winner Detected", JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this," Red, you won!", "Winner Detected", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	
	private class AIWorker extends SwingWorker <String, String> {

	    @Override
	    protected String doInBackground() throws Exception {
	    	SearchNode node = new SearchNode(game.getPiles(), game.getCurrentPlayer(), game.getReserves(), game.getCapturedPieces());
	    	publish("");
			game.alphaBetaMiniMax(node, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, game.getCurrentPlayer());
			//Set game components
			//game.setPiles(game.getBestMove().getBoard());
			game.setReserves(game.getBestMove().getReserves());
			game.setCapturedPieces(game.getBestMove().getCapturedPieces());
			
			String color = "Red  ";
			if (game.getCurrentPlayer() == game.GREEN) {
				color = "Green";
			}
			System.out.println(color + ": " + game.getBestMove().getMove());
			
			//Play move
			AIChoseMove(game.getBestMove().getMove(), node.isPlayFromReserves());
			return null;
	    }
	    
	    @Override
	    protected void process(List<String> text){
			moveButton.setText("Calculating...");
			moveButton.setEnabled(false);	
	    }

	    @Override
	    protected void done() {
			isFocusOver();
			if(!gameOver){
				if (game.getCurrentPlayer() == game.GREEN) {
					moveButton.setText("Generate green move!");
				} else {
					moveButton.setText("Generate red move!");
				}
				moveButton.setEnabled(true);
			}
	    }

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("move")){
			AIWorker ai = new AIWorker();
			ai.execute();
		}
	}
}
