package focus;

/** A linked, double-ended queue of ints. */
public class Deque {

	/** The back node in this deque; undefined if this deque is empty. */
	private Node back;

	/** The front node in this deque, or null if this deque is empty. */
	private Node front;

	/** Adds key to the back of this deque. */
	public void addBack(int key) {
		if (isEmpty()) {
			front = new Node(key, front);
			back = front;
		} else {
			back.setNext(new Node(key, null));
			back = back.getNext();
		}
	}

	/** Adds key to the front of this deque. */
	public void addFront(int key) {
		if (isEmpty()) {
			front = new Node(key, front);
			back = front;
		} else {
			front = new Node(key, front);
		}
	}

	/** Removes all keys from this deque. */
	public void clear() {
		front = null;
	}

	/** Returns the front key in this deque. */
	public int getFront() {
		return front.getKey();
	}

	/** Returns the front node in this deque. */
	protected Node getFrontNode() {
		return front;
	}

	/** Returns true if this deque is empty. */
	public boolean isEmpty() {
		return front == null;
	}

	/**
	 * Removes and returns the back key from this deque. Assumes that this deque
	 * is not empty.
	 */
	public int removeBack() {
		int result = back.getKey();
		Node prev = null;
		for (Node n = front; n != back; n = n.getNext()) {
			prev = n;
		}
		if (prev == null) {
			front = null;
		} else {
			prev.setNext(null);
		}
		back = prev;
		return result;
	}

	/**
	 * Removes and returns the front key from this deque. Assumes that this
	 * deque is not empty.
	 */
	public int removeFront() {
		int result = front.getKey();
		front = front.getNext();
		return result;
	}

	/** Returns the number of keys in this deque. */
	public int size() {
		int result = 0;
		for (Node n = front; n != null; n = n.getNext()) {
			result++;
		}
		return result;
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "()";
		}
		String result = "(" + front.getKey();
		for (Node n = front.getNext(); n != null; n = n.getNext()) {
			result += ", " + n.getKey();
		}
		return result + ")";
	}

}
