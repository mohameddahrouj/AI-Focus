package focus;

import java.io.*;
import java.lang.Math;

import java.util.*;

import gui.Program;

/**
 * The game of Focus, also known as Domination. The game was invented by Sid
 * Sackson.
 */
public class Focus extends Program {

	/** The green player. */
	public final int GREEN = 0;

	/** The width of the board. */
	public final int BOARD_WIDTH = 8;

	/**
	 * Coordinates of squares that, while within the larger square bounding the
	 * board, are not part of the board.
	 */
	public final int[][] REMOVED_CORNERS = { { 0, 0 }, { 0, 1 },
			{ 1, 0 }, { 6, 7 }, { 7, 7 }, { 7, 6 }, { 0, 6 }, { 0, 7 },
			{ 1, 7 }, { 6, 0 }, { 7, 0 }, { 7, 1 } };

	/** The red player. */
	public final int RED = 1;
	
	public int uptoDepth = 2;
	
	public boolean randomBoard = true;

	/** The current player (GREEN or RED). */
	private int currentPlayer;

	/** A pile for each square. */
	private Pile[][] piles;

	/** reserves[p] is the number of pieces player p has in reserve. */
	private int[] reserves;
	
	/** capturedPieces[p] is the number of pieces player p has captured. */
	private int[] capturedPieces;
	
	private SearchNode bestMove;
	
	public static void main(String[] args) {
		Focus focus = new Focus();
		
		//Root
		focus.generateChildren(new SearchNode(focus.getPiles(), focus.getCurrentPlayer(), focus.getReserves(), focus.getCapturedPieces()));
		
		//SearchNode node = new SearchNode(focus.getPiles(), focus.getCurrentPlayer(), focus.getReserves(), focus.getCapturedPieces());
		//System.out.println(focus.alphaBetaMiniMax(node, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, focus.getCurrentPlayer()));
		//System.out.println(focus.getBestMove().getMove());
		System.out.println("done");
		
		
		//new Focus().run();
	}

	public Focus() {
		int redPieces = 0;
		int greenPieces = 0;
		// Initialize piles
		piles = new Pile[BOARD_WIDTH][BOARD_WIDTH];
		for (int r = 0; r < BOARD_WIDTH; r++) {
			for (int c = 0; c < BOARD_WIDTH; c++) {
				piles[r][c] = new Pile();
			}
		}
		for (int r = 1; r < 7; r++) {
			for (int c = 1; c < 7; c++) {
				
				if(!randomBoard){
					if ((r % 2 == 0) == ((c % 4 == 0) || (c % 4 == 3))) {
						piles[r][c].addFront(GREEN);
					} else {
						piles[r][c].addFront(RED);
					}
				}
				else{
					int rando = new Random().nextInt(2);
					if(rando == GREEN){
						if(greenPieces<18){
							piles[r][c].addFront(GREEN);
							greenPieces++;
						}
						else{
							if(redPieces<18){
								piles[r][c].addFront(RED);
								redPieces++;
							}
						}
					}
					if(rando == RED){
						if(redPieces<18){
							piles[r][c].addFront(RED);
							redPieces++;
						}
						else{
							if(greenPieces<18){
								piles[r][c].addFront(GREEN);
								greenPieces++;
							}
						}
					}
				}
			}
		}
		// Remove corners from board
		for (int i = 0; i < REMOVED_CORNERS.length; i++) {
			piles[REMOVED_CORNERS[i][0]][REMOVED_CORNERS[i][1]] = null;
		}
		// Other setup
		reserves = new int[2];
		capturedPieces = new int[2];
		currentPlayer = GREEN;
		bestMove = null;
		
		//System.out.println(redPieces);
		//System.out.println(greenPieces);
		
	}
	
	public Pile[][] getPiles(){
		return piles;
	}
	
	public int[] getReserves() {
		return reserves;
	}

	public int[] getCapturedPieces() {
		return capturedPieces;
	}

	public void setCapturedPieces(int[] capturedPieces) {
		this.capturedPieces = copyArray(capturedPieces);
	}

	public void setReserves(int[] reserves) {
		this.reserves = copyArray(reserves);
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setPiles(Pile[][] piles) {
		this.piles = copyBoard(piles);
	}

	public SearchNode getBestMove() {
		return bestMove;
	}

	public void setBestMove(SearchNode bestMove) {
		this.bestMove = bestMove;
	}
	
	public ArrayList<SearchNode> generateChildren(SearchNode node)
	{
		ArrayList<SearchNode> children = new ArrayList<>();
		children.addAll(generateAllUpDownRightLeftMoves(node));
		children.addAll(generateAllReserveMoves(node));
		return children;
		
	}
	
	private ArrayList<SearchNode> generateAllUpDownRightLeftMoves(SearchNode node) {

		ArrayList<SearchNode> children = new ArrayList<>();
		ArrayList<Move> positions = new ArrayList<>();

		for(int i=0; i<BOARD_WIDTH; i++){
			for(int j=0; j<node.getBoard()[i].length; j++){
				if(node.getBoard()[i][j]!=null && isLegalSource(node.getBoard(), i,j, node.getPlayer()) && node.getBoard()[i][j].size()>=1 && node.getBoard()[i][j].getFront()== node.getPlayer()){
					for(int numInPile=1; numInPile<=node.getBoard()[i][j].size(); numInPile++){
						Move move1 = new Move(i, j, i-numInPile, j);
						if (isValidPosition(move1, node.getBoard()) && isLegal(node.getBoard(),move1, node.getPlayer())) {
							positions.add(move1); // Left
						}

						Move move2 = new Move(i, j, i+numInPile, j);
						if (isValidPosition(move2, node.getBoard()) && isLegal(node.getBoard(), move2, node.getPlayer())) {
							positions.add(move2); // Up
						}

						Move move3 = new Move(i, j, i, j-numInPile);
						if (isValidPosition(move3, node.getBoard()) && isLegal(node.getBoard(), move3, node.getPlayer())) {
							positions.add(move3); // Left
						}

						Move move4 = new Move(i, j, i, j+numInPile);
						if (isValidPosition(move4, node.getBoard()) && isLegal(node.getBoard(), move4, node.getPlayer())) {
							positions.add(move4); // Right
						}

						for (Move position: positions) {
							swapAndStoreRegularMove(node, position, children);
						}
						positions.clear();
					}
				}
			}
		}
		//System.out.println("Moves based on state: " + children.size());
		return children;
	}
	
	private ArrayList<SearchNode> generateAllReserveMoves(SearchNode node) {

		ArrayList<SearchNode> children = new ArrayList<>();

		if(node.getReserves()[node.getPlayer()]>=1){
			for(int i=0; i<BOARD_WIDTH; i++){
				for(int j=0; j<node.getBoard()[i].length; j++){
					if(node.getBoard()[i][j]!=null){
							swapAndStoreReserveMove(node, i , j, children);
					}
				}
			}
		}
		//System.out.println("Moves from reserve  : " + children.size());
		return children;
	}
		
	private void swapAndStoreRegularMove(SearchNode node, Move position, ArrayList<SearchNode> children)
	{	
		SearchNode newNode = new SearchNode(node, copyBoard(node.getBoard()), node.getPlayer(), copyArray(node.getReserves()), copyArray(node.getCapturedPieces()), position);
		//Apply the move
		move(newNode);
		children.add(newNode);
	}
	
	private void swapAndStoreReserveMove(SearchNode node, int row, int column, ArrayList<SearchNode> children)
	{	
		SearchNode newNode = new SearchNode(node, copyBoard(node.getBoard()), node.getPlayer(), copyArray(node.getReserves()), copyArray(node.getCapturedPieces()), new Move(-1, -1, row, column), true);
		//Apply the move
		playFromReserves(newNode, row, column);
		children.add(newNode);
	}
	
	// Deep Copy method
	public Pile[][] copyBoard(Pile[][] fboard){
		Pile[][] result = new Pile[fboard.length][fboard.length];
		for(int r = 0; r < fboard.length; r++){
			for(int s = 0; s < fboard.length; s++){
				result[r][s] = (Pile)deepClone(fboard[r][s]);
			}
		}
		
		return result;
	}
	
	// Deep Copy method
	public int[] copyArray(int[] src){
		int[] dest = new int[src.length];
		System.arraycopy( src, 0, dest, 0, src.length );
		return dest;
	}
	
	/**
	 * This method makes a "deep clone" of any Java object it is given.
	 */
	 public static Object deepClone(Object object) {
	   try {
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(baos);
	     oos.writeObject(object);
	     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	     ObjectInputStream ois = new ObjectInputStream(bais);
	     return ois.readObject();
	   }
	   catch (Exception e) {
	     e.printStackTrace();
	     return null;
	   }
	 }
	
    private  boolean isValidPosition(Move move, Pile[][] board)
    {
        boolean status = move.getr1() < BOARD_WIDTH && move.getr1() >=0
        		&& move.getc1() < BOARD_WIDTH && move.getc1() >=0 
        		&& move.getr2() < BOARD_WIDTH && move.getr2() >=0
        		&& move.getc2() < BOARD_WIDTH && move.getc2() >=0
        		&& board[move.getr1()][move.getc1()] != null
        		&& board[move.getr2()][move.getc2()] != null;
        		
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
			return Math.abs(move.getc1() - move.getc2());
		} else {
			return Math.abs(move.getr1() - move.getr2());
		}
	}

	/**
	 * Returns true if the game is over, i.e., the current player has no legal
	 * move.
	 */
	protected boolean gameOver() {
		
		//More than 8 pieces need to be captured to win
		if (capturedPieces[currentPlayer] >=8) {
			return true;
		}
		if (capturedPieces[1-currentPlayer] >=8) {
			return true;
		}
		
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
	protected boolean gameOver(SearchNode node) {
		
		//More than 8 pieces need to be captured to win
		if (node.getCapturedPieces()[node.getPlayer()] >=8) {
			return true;
		}
		
		// Check the current player's reserves
		if (node.getReserves()[node.getPlayer()] > 0) {
			return false;
		}
		// Check for friendly piles
		for (int r1 = 0; r1 < BOARD_WIDTH; r1++) {
			for (int c1 = 0; c1 < BOARD_WIDTH; c1++) {
				if (node.getBoard()[r1][c1] != null && isLegalSource(node.getBoard(), r1, c1, node.getPlayer())) {
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
	 * Moves pieces from r1, c1 to r2, c2. Does not check legality -- the move
	 * is made even if it is illegal.
	 */
	protected void move(SearchNode node) {
		int d = distance(node.getMove());
		Pile hand = new Pile();
		while (hand.size() < d) {
			hand.addBack(node.getBoard()[node.getMove().getr1()][node.getMove().getc1()].removeFront());
		}
		place(node, hand, node.getMove().getr2(), node.getMove().getc2());
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
			if (removed != currentPlayer) {
				capturedPieces[currentPlayer]++;
			}
		}
		currentPlayer = opposite(currentPlayer);
	}
	
	protected void place(SearchNode node, Pile hand, int r, int c) {
		while (!hand.isEmpty()) {
			node.getBoard()[r][c].addFront(hand.removeBack());
		}
		while (node.getBoard()[r][c].size() > 5) {
			int removed = node.getBoard()[r][c].removeBack();
			if (removed == node.getPlayer()) {
				node.getReserves()[node.getPlayer()]++;
			}
			if (removed != node.getPlayer()) {
				node.getCapturedPieces()[node.getPlayer()]++;
			}
		}
		//node.getPlayer()) = opposite(node.getPlayer()));
	}

	/** Plays a piece from reserves to square r, c. */
	protected void playFromReserves(int r, int c) {
		reserves[currentPlayer]--;
		Pile hand = new Pile();
		hand.addFront(currentPlayer);
		place(hand, r, c);
	}
	
	/** Plays a piece from reserves to square r, c. */
	protected void playFromReserves(SearchNode node, int r, int c) {
		node.getReserves()[node.getPlayer()]--;
		Pile hand = new Pile();
		hand.addFront(node.getPlayer());
		place(node, hand, r, c);
	}

	/** Returns the number of pieces color has in reserve. */
	protected int reserves(int color) {
		return reserves[color];
	}
	
	/** Returns the number of pieces color has captured. */
	protected int capturedPieces(int color) {
		return capturedPieces[color];
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
	
	//Current players loss is other players gain
	public int alphaBetaMiniMax(SearchNode node, int alpha, int beta, int depth, int player){
		
		if(depth == uptoDepth || gameOver(node)){
			//System.out.println("Alpha= " + alpha);
			//System.out.println("Beta= " + beta);
			//System.out.println("Heuristic for " + player +" = " + evaluateHeuristic(node, player) + "");
			return evaluateHeuristic(node, player);
		}
		
		//Production System
		ArrayList<SearchNode> expandedNodes = generateChildren(node);
		
		if (expandedNodes.isEmpty()){
			//System.out.println("Heuristic for " + player +" = " + evaluateHeuristic(node, player) + "");
			return evaluateHeuristic(node, player);
		}
		
		int currentScore;
		
		if(player == GREEN){
			for (SearchNode newNode: expandedNodes){
				currentScore = alphaBetaMiniMax(newNode, alpha, beta, depth+1, RED);
				
				if(currentScore > alpha){
					alpha = currentScore;
					if(depth == 1){
						//Sets the best possible next move
						bestMove = newNode;
						//System.out.println("CurrentBestMove--> " + bestMove.getMove());
						//System.out.println("CurrentScore--> " + currentScore);
					}
				}
				
				if(alpha >= beta){
					//System.out.println("CurrentBestMove= " + bestMove.getMove());
					//System.out.println("Alpha= " + alpha);
					//System.out.println("Beta= " + beta);
					return alpha;
				}
			}
			//System.out.println("CurrentBestMove= " + bestMove.getMove());
			//System.out.println("Alpha= " + alpha);
			//System.out.println("Beta= " + beta);
			return alpha;
		} else{
			for (SearchNode newNode: expandedNodes){
				currentScore = alphaBetaMiniMax(newNode, alpha, beta, depth+1, GREEN);
				
				if(currentScore < beta){
					beta = currentScore;
					if(depth == 1){
						//Sets the best possible next move
						bestMove = newNode;
						//System.out.println("CurrentBestMove--> " + bestMove.getMove());
						//System.out.println("CurrentScore--> " + currentScore);
					}
				}
				
				if(beta <= alpha){
					//System.out.println("CurrentBestMove= " + bestMove.getMove());
					//System.out.println("Alpha= " + alpha);
					//System.out.println("Beta= " + beta);
					return beta;
				}
			}
			//System.out.println("CurrentBestMove= " + bestMove.getMove());
			//System.out.println("Alpha= " + alpha);
			//System.out.println("Beta= " + beta);
			return beta;
		}
	}
	
	public int evaluateHeuristic(SearchNode node, int player){
		if(player==GREEN){
			return redPlayerHeuristic(node);
		}
		
		else{
			return greenPlayerHeuristic(node);
		}
		
	}
	
	//Return the number of opponent pieces I can potentially catch
	//Maximum score is best
	private int greenPlayerHeuristic(SearchNode node) {
		
		int numCapturedPlusReserve = (node.getCapturedPieces()[node.getPlayer()] + node.getReserves()[node.getPlayer()]);
		int pileSizeOfDestination = node.getBoard()[node.getMove().getr2()][node.getMove().getc2()].size();
		if(numCapturedPlusReserve==0){
			return pileSizeOfDestination;
		}
		if(node.getMove().getr1() == -1){
			return pileSizeOfDestination + numCapturedPlusReserve + 100; //Reserve play factor
		}
		else{
			return pileSizeOfDestination + numCapturedPlusReserve;
		}
	}
	
	//minumum score is best
	private int redPlayerHeuristic(SearchNode node) {
		int pileSizeOfDestination = node.getBoard()[node.getMove().getr2()][node.getMove().getc2()].size();
		
		if(pileSizeOfDestination==0){
			return 0 - new Random().nextInt(53); //Random to change things up
		}
		else{
			return 0- pileSizeOfDestination - (pileSizeOfDestination*pileSizeOfDestination) - new Random().nextInt(53);
		}
	
	}
}
