package focus;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Arrays;

import gui.Program;

/**
 * The game of Focus, also known as Domination. The game was invented by Sid
 * Sackson.
 */
public class Focus extends Program {

	/** The green player. */
	public static final int GREEN = 0;

	/** The width of the board. */
	public static final int BOARD_WIDTH = 8;

	/**
	 * Coordinates of squares that, while within the larger square bounding the
	 * board, are not part of the board.
	 */
	public static final int[][] REMOVED_CORNERS = { { 0, 0 }, { 0, 1 },
			{ 1, 0 }, { 6, 7 }, { 7, 7 }, { 7, 6 }, { 0, 6 }, { 0, 7 },
			{ 1, 7 }, { 6, 0 }, { 7, 0 }, { 7, 1 } };

	/** The red player. */
	public static final int RED = 1;
	
	public static int counter = 0;
	
	public int uptoDepth = 4;
	
	public Move bestMove;

	public static void main(String[] args) {
		Focus focus = new Focus();
		//focus.generateAllUpDownRightLeftMoves(focus.getPiles(), focus.getCurrentPlayer());
		
		SearchNode node = new SearchNode(new Focus());
		
		System.out.println(focus.alphaBetaMiniMax(node, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, focus.currentPlayer));
		System.out.println(focus.bestMove);
		System.out.println("done");
		
		
		//new Focus().run();
	}

	/** The current player (GREEN or RED). */
	private int currentPlayer;

	/** A pile for each square. */
	private Pile[][] piles;

	/** reserves[p] is the number of pieces player p has in reserve. */
	private int[] reserves;

	public Focus() {
		// Initialize piles
		piles = new Pile[BOARD_WIDTH][BOARD_WIDTH];
		for (int r = 0; r < BOARD_WIDTH; r++) {
			for (int c = 0; c < BOARD_WIDTH; c++) {
				piles[r][c] = new Pile();
			}
		}
		for (int r = 1; r < 7; r++) {
			for (int c = 1; c < 7; c++) {
				if ((r % 2 == 0) == ((c % 4 == 0) || (c % 4 == 3))) {
					piles[r][c].addFront(GREEN);
				} else {
					piles[r][c].addFront(RED);
				}
			}
		}
		// Remove corners from board
		for (int i = 0; i < REMOVED_CORNERS.length; i++) {
			piles[REMOVED_CORNERS[i][0]][REMOVED_CORNERS[i][1]] = null;
		}
		// Other setup
		reserves = new int[2];
		currentPlayer = GREEN;
	}
	
	public Pile[][] getPiles(){
		return piles;
	}
	
	public Focus(Pile[][] piles, int[] reserves, int currentPlayer) {
		// Initialize piles
		this.piles = piles;
		// Other setup
		this.reserves = reserves;
		this.currentPlayer = currentPlayer;
	}
	
	public ArrayList<Focus> generateChildren(Pile[][] board, int player)
	{
		ArrayList<Focus> children = new ArrayList<>();
		children.addAll(generateAllUpDownRightLeftMoves(board, player));
		return children;
		
	}
	
	//Generate horse moves for non-blank tiles
		private ArrayList<Focus> generateAllUpDownRightLeftMoves(Pile[][] board, int player) {

	        ArrayList<Focus> children = new ArrayList<>();
	        ArrayList<Move> positions = new ArrayList<>();
 
			for(int i=0; i<BOARD_WIDTH; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j]!=null && isLegalSource(board, i,j, player) && board[i][j].getFront()== player){
			        	for(int numInPile=1; numInPile<=board[i][j].size(); numInPile++){
		        			Move move1 = new Move(i, j, i-numInPile, j);
			            	if (isValidPosition(move1, board) && isLegal(board,move1, player)) {
			            		positions.add(move1); // Left
			            	}
				            
			            	Move move2 = new Move(i, j, i+numInPile, j);
				            if (isValidPosition(move2, board) && isLegal(board, move2, player)) {
				                positions.add(move2); // Up
				            }
				            
				            Move move3 = new Move(i, j, i, j-numInPile);
				            if (isValidPosition(move3, board) && isLegal(board, move3, player)) {
				                positions.add(move3); // Left
				            }
				            
				            Move move4 = new Move(i, j, i, j+numInPile);
				            if (isValidPosition(move4, board) && isLegal(board, move4, player)) {
				                positions.add(move4); // Right
				            }
				            for (Move position: positions) {
				                swapAndStore(board, reserves, position, children, player);
				            }
				            positions.clear();
			        	}
					}
				}
	        }
			//System.out.println(children.size());
	        return children;
    }
		
	private void swapAndStore(Pile[][] board, int[] reserves, Move move, ArrayList<Focus> s, int player)
	{
		Pile[][] copy = copyBoard(board);
		Pile temp = copy[move.getr1()][move.getc1()];
		copy[move.getr1()][move.getc1()] = board[move.getr2()][move.getc2()];
		copy[move.getr2()][move.getc2()] = temp;
		s.add(new Focus(copy, reserves, player));
	}
	
	// Deep Copy method
	public static Pile[][] copyBoard(Pile[][] original) {
	    if (original == null) {
	        return null;
	    }

	    final Pile[][] copy = new Pile[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        copy[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return copy;
	}
	
    private  boolean isValidPosition(Move move, Pile[][] board)
    {
        boolean status = move.getr1() < BOARD_WIDTH && move.getr1() >=0
        		&& move.getc1() < BOARD_WIDTH && move.getc1() >=0 
        		&& move.getr2() < BOARD_WIDTH && move.getr2() >=0
        		&& move.getc2() < BOARD_WIDTH && move.getc2() >=0
        		&& board[move.getr1()][move.getc1()] != null
        		&& board[move.getr2()][move.getc2()] != null;
        		
        		//if(status){
        		//	counter++;
        		//	System.out.println(counter + ":   " + move.getr1() + "," + move.getc1() + " to " + move.getr2() + "," + move.getc2());
        		//}
        		
        		return status;
        		
    }

	/**
	 * Returns the distance from r1, c1 to r2, c2. If these points are not on
	 * the same line, or are the same point, returns -1.
	 */
	protected int distance(Move move) {
		if ((move.getr1() == move.getr2()) == (move.getc1() == move.getc2())) {
			return -1;
		}
		if (move.getr1() == move.getr2()) {
			return abs(move.getc1() - move.getc2());
		} else {
			return abs(move.getr1() - move.getr2());
		}
	}

	/**
	 * Returns true if the game is over, i.e., the current player has no legal
	 * move.
	 */
	protected boolean gameOver() {
		// Check the current player's reserves
		if (reserves[currentPlayer] > 0) {
			return false;
		}
		// Check for friendly piles
		for (int r1 = 0; r1 < BOARD_WIDTH; r1++) {
			for (int c1 = 0; c1 < BOARD_WIDTH; c1++) {
				if (piles[r1][c1] != null && isLegalSource(r1, c1)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns true if the game is over, i.e., the current player has no legal
	 * move.
	 */
	protected boolean gameOver(Pile[][] board, int player) {
		// Check the current player's reserves
		if (reserves[currentPlayer] > 0) {
			return false;
		}
		// Check for friendly piles
		for (int r1 = 0; r1 < BOARD_WIDTH; r1++) {
			for (int c1 = 0; c1 < BOARD_WIDTH; c1++) {
				if (board[r1][c1] != null && isLegalSource(board, r1, c1, player)) {
					return false;
				}
			}
		}
		return true;
	}

	/** Returns the current player (GREEN or RED). */
	protected int getCurrentPlayer() {
		return currentPlayer;
	}

	/** Returns the (possibly empty) pile on square r, c. */
	protected Pile getPile(int r, int c) {
		return piles[r][c];
	}

	/** Returns true if it is legal to move from r1, c1 to r2, c2. */
	protected boolean isLegal(Move move) {
		if (piles[move.getr1()][move.getc1()] == null || piles[move.getr2()][move.getc2()] == null) {
			return false; // Not on board
		}
		int d = distance(move);
		if (d == -1) {
			return false; // Same point or not orthogonal
		}
		if (d > piles[move.getr1()][move.getc1()].size()) {
			return false; // Too far
		}
		if (piles[move.getr1()][move.getc1()].getFront() != currentPlayer) {
			return false; // Wrong color
		}
		return true;
	}
	
	/** Returns true if it is legal to move from r1, c1 to r2, c2. */
	protected boolean isLegal(Pile[][] board, Move move, int player) {
		if (board[move.getr1()][move.getc1()] == null || board[move.getr2()][move.getc2()] == null) {
			return false; // Not on board
		}
		int d = distance(move);
		if (d == -1) {
			return false; // Same point or not orthogonal
		}
		if (d > board[move.getr1()][move.getc1()].size()) {
			return false; // Too far
		}
		if (board[move.getr1()][move.getc1()].getFront() != player) {
			return false; // Wrong color
		}
		return true;
	}

	/**
	 * Returns true if square r, c is a legal source for a move by the current
	 * player.
	 */
	protected boolean isLegalSource(int r, int c) {
		Pile p = piles[r][c];
		if (p.size() == 0) {
			return false;
		}
		return p.getFront() == currentPlayer;
	}
	
	protected boolean isLegalSource(Pile[][] board, int r, int c, int player) {
		Pile p = board[r][c];
		if (p.size() == 0) {
			return false;
		}
		return p.getFront() == player;
	}

	/** Returns true if r and c are valid coordinates for a square on the board. */
	protected boolean isOnBoard(int r, int c) {
		return (r >= 0) && (r < BOARD_WIDTH) && (c >= 0) && (c < BOARD_WIDTH)
				&& piles[r][c] != null;
	}

	/**
	 * Moves pieces from r1, c1 to r2, c2. Does not check legality -- the move
	 * is made even if it is illegal.
	 */
	protected void move(Move move) {
		int d = distance(move);
		Pile hand = new Pile();
		while (hand.size() < d) {
			hand.addBack(piles[move.getr1()][move.getc1()].removeFront());
		}
		place(hand, move.getr2(), move.getc2());
	}

	/**
	 * Returns the index of the other player. Thus, opposite(0) returns 1 and
	 * opposite(1) returns 0.
	 */
	protected int opposite(int color) {
		return 1 - color;
	}

	/**
	 * Places the pile hand onto square r, c, dealing with any pieces that are
	 * removed or become reserves as a result. Also toggles the current player.
	 */
	protected void place(Pile hand, int r, int c) {
		while (!hand.isEmpty()) {
			piles[r][c].addFront(hand.removeBack());
		}
		while (piles[r][c].size() > 5) {
			int removed = piles[r][c].removeBack();
			if (removed == currentPlayer) {
				reserves[currentPlayer]++;
			}
		}
		currentPlayer = opposite(currentPlayer);
	}

	/** Plays a piece from reserves to square r, c. */
	protected void playFromReserves(int r, int c) {
		reserves[currentPlayer]--;
		Pile hand = new Pile();
		hand.addFront(currentPlayer);
		place(hand, r, c);
	}

	/** Returns the number of pieces color has in reserve. */
	protected int reserves(int color) {
		return reserves[color];
	}

	/** Plays the game. */
	public void run() {
		while (!gameOver()) {
			printLine(this);
			printLine("#O".charAt(currentPlayer) + " to play.");
			printLine("Enter source and destination coordinates.");
			printLine("Use -1, -1 for source coordinates to play from reserves.");
			int r1 = readInt("Source row:");
			int c1 = readInt("Source column: ");
			int r2 = readInt("Destination row:");
			int c2 = readInt("Destination column:");
			if (r1 < 0) {
				if (reserves[currentPlayer] > 0) {
					playFromReserves(r2, c2);
				} else {
					printLine("Sorry, you have no reserves.");
				}
			} else {
				if (isLegal(new Move(r1, c1, r2, c2))) {
					move(new Move (r1, c1, r2, c2));
				} else {
					printLine("Sorry, that is not a legal move.");
				}
			}
		}
		printLine(this);
		printLine("Game over.");
	}

	/** Sets the pile on square r, c. */
	protected void setPile(int r, int c, Pile p) {
		piles[r][c] = p;
	}

	/** Sets the number of reserves for color. */
	protected void setReserves(int color, int n) {
		reserves[color] = n;
	}

	@Override
	public String toString() {
		String result = " 01234567\n";
		for (int r = 0; r < BOARD_WIDTH; r++) {
			result += r;
			for (int c = 0; c < BOARD_WIDTH; c++) {
				if (!isOnBoard(r, c)) {
					result += " ";
				} else {
					Pile p = piles[r][c];
					if (p.size() == 0) {
						result += ".";
					} else if (p.getFront() == GREEN) {
						result += "#";
					} else {
						result += "O";
					}
				}
			}
			result += r + "\n";
		}
		result += " 01234567\n";
		for (int r = 0; r < BOARD_WIDTH; r++) {
			for (int c = 0; c < BOARD_WIDTH; c++) {
				if (isOnBoard(r, c) && piles[r][c].size() > 1) {
					result += r + ", " + c + ": " + piles[r][c] + "\n";
				}
			}
		}
		result += "Reserves: # " + reserves[GREEN] + ", O " + reserves[RED]
				+ "\n";
		return result;
	}
	
	public int alphaBetaMiniMax(SearchNode node, int alpha, int beta, int depth, int player){
		
		if(depth == uptoDepth || gameOver(node.getCurrentState().getPiles(), player)){
			return evaluateHeuristic(node, player);
		}
		
		//Production System
		ArrayList<Focus> expandedNodes = generateChildren(node.getCurrentState().getPiles(), player);
		
		if (expandedNodes.isEmpty()){
			return evaluateHeuristic(node, player);
		}
		
		int currentScore;
		
		if(player == GREEN){
			for (Focus newNode: expandedNodes){
				currentScore = alphaBetaMiniMax(new SearchNode(newNode), alpha, beta, depth+1, RED);
				
				if(currentScore > alpha){
					alpha = currentScore;
					if(depth == 1){
						bestMove = newNode.bestMove;
					}
				}
				
				if(alpha >= beta){
					return alpha;
				}
			}
			return alpha;
		} else{
			for (Focus newNode: expandedNodes){
				currentScore = alphaBetaMiniMax(new SearchNode(newNode), alpha, beta, depth+1, GREEN);
				
				if(currentScore < beta){
					beta = currentScore;
					if(depth == 1){
						bestMove = newNode.bestMove;
					}
				}
				
				if(beta <= alpha){
					return beta;
				}
			}
			return beta;
		}
	}
	
	public int evaluateHeuristic(SearchNode node, int player){
		// TODO
		if(player==GREEN){
			return playerOneHeuristic(node);
		}
		
		else{
			return playerTwoHeuristic(node);
		}
		
	}
	
	private int playerOneHeuristic(SearchNode node) {
		// TODO Auto-generated method stub
		return 1;
	}

	private int playerTwoHeuristic(SearchNode node) {
		// TODO Auto-generated method stub
		return 2;
	}

}
