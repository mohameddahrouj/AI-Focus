package focus;

import java.io.Serializable;

/**
 * A pile of pieces for the Focus game. The top of the pile corresponds to the
 * front of the deque. The sole purpose of this class is to provide a
 * Focus-specific toString() method, as opposed to the general-purpose one in
 * Deque.
 */
public class Pile extends Deque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		String result = "";
		for (Node n = getFrontNode(); n != null; n = n.getNext()) {
			if (n.getKey() == 0){//GREEN
				result += "#";
			} else {
				result += "O";
			}
		}
		return result;
	}

}
