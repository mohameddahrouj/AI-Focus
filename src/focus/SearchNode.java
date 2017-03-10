package focus;

import java.io.*;

/**
 * 
 * Represents search nodes for different algorithms
 * Includes: State 
 * 			 Cost to get to that state
 * 			 State's parent node
 * 
 * @author Mohamed Dahrouj
 * 
 */
public class SearchNode
{
	private SearchNode parent;
	
	private Pile[][] board;
	private int player;
	private int[] reserves;
	
	private Move move;
	
	private int cost;    // cost to get to this state
	private int hCost; 	// heuristic cost
	private int fCost; 	// function cost
	
	//Copy Constructor
	public SearchNode(SearchNode sn)
	{
		if(sn.getParent()!=null){
			this.parent = new SearchNode(sn.getParent());
		}
		else{
			this.parent= null;
		}
		this.board = copyBoard(sn.getBoard());
		this.player = sn.getPlayer();
		this.reserves = copyReserves(sn.getReserves());
		if(sn.getMove()!=null){
			this.move = new Move(sn.getMove().getr1(), sn.getMove().getc1(), sn.getMove().getr2(), sn.getMove().getc2());
		}
		else{
			this.move= null;
		}
		this.cost = sn.getCost();
		this.hCost = sn.gethCost();
		this.fCost = sn.getCost() + sn.gethCost();
	}
	
	
	// Constructor for the root
	public SearchNode(Pile[][] board, int player, int[] reserves)
	{
		parent = null;
		this.board = copyBoard(board);
		this.player = player;
		this.reserves = copyReserves(reserves);
		this.move = null;
		this.cost = 0;
		this.hCost = 0;
		this.fCost = 0;
	}

	// Constructor for all other SearchNodes
	// prev = parent node
	// s = state
	// Used for A*:
    //             c = g(n) cost to get to this node
	//             h = h(n) cost to get to this node
	public SearchNode(SearchNode parent, Pile[][] board, int player, int[] reserves, Move move, int cost, int hCost)
	{
		if(parent!=null){
			this.parent = new SearchNode(parent);
		}
		else{
			this.parent= null;
		}
		this.board = copyBoard(board);
		this.player = player;
		this.reserves = copyReserves(reserves);		
		if(move!=null){
			this.move = new Move(move.getr1(), move.getc1(), move.getr2(), move.getc2());
		}
		else{
			this.move= null;
		}
		this.cost = cost;
		this.hCost = hCost;
		this.fCost = cost + hCost;
	}
	
	public SearchNode(SearchNode parent, Pile[][] board, int player, int[] reserves, Move move)
	{
		if(parent!=null){
			this.parent = new SearchNode(parent);
		}
		else{
			this.parent= null;
		}
		this.board = copyBoard(board);
		this.player = player;
		this.reserves = copyReserves(reserves);
		if(move!=null){
			this.move = new Move(move.getr1(), move.getc1(), move.getr2(), move.getc2());
		}
		else{
			this.move= null;
		}
		this.cost = 0;
		this.hCost = 0;
		this.fCost = 0;
	}

	public Pile[][] getBoard() {
		return board;
	}

	public int getPlayer() {
		return player;
	}

	public int[] getReserves() {
		return reserves;
	}

	public Move getMove() {
		return move;
	}

	public int gethCost() {
		return hCost;
	}

	public int getfCost() {
		return fCost;
	}

	public int getCost() {
		return cost;
	}

	public SearchNode getParent()
	{
		return parent;
	}

	public void setParent(SearchNode parent) {
		this.parent = parent;
	}

	public void setBoard(Pile[][] board) {
		this.board = board;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public void setReserves(int[] reserves) {
		this.reserves = reserves;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
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
	public int[] copyReserves(int[] src){
		int[] dest = new int[5];
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
}
