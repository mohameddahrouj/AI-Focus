package focus;

/** A linked list node, for use in a Deque. */
public class Node {

	/** The key of this node. */
	private int key;
	
	/** The next node. */
	private Node next;

	public Node(int key, Node next) {
		this.key = key;
		this.next = next;
	}

	/** Returns the key of this node. */
	public int getKey() {
		return key;
	}

	/** Returns the next node. */
	public Node getNext() {
		return next;
	}

	/** Sets the next node. */
	public void setNext(Node next) {
		this.next = next;
	}

}
