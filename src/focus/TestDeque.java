package focus;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestDeque {

	private Deque deque;
	
	@Before
	public void setUp() {
		deque = new Deque();
	}

	@Test
	public void testAddFront() {
		deque.addFront(1);
		assertEquals(1, deque.size());
		assertEquals("(1)", deque.toString());
		deque.addFront(2);
		assertEquals(2, deque.size());
		assertEquals("(2, 1)", deque.toString());
	}

	@Test
	public void testAddBack() {
		deque.addBack(1);
		assertEquals(1, deque.size());
		assertEquals("(1)", deque.toString());
		deque.addBack(2);
		assertEquals(2, deque.size());
		assertEquals("(1, 2)", deque.toString());
	}

	@Test
	public void testRemoveFront() {
		deque.addBack(1);
		deque.addBack(2);
		deque.addBack(3);
		assertEquals(1, deque.removeFront());
		assertEquals("(2, 3)", deque.toString());
		assertEquals(2, deque.removeFront());
		assertEquals("(3)", deque.toString());
		assertEquals(3, deque.removeFront());
		assertEquals("()", deque.toString());
	}

	@Test
	public void testRemoveBack() {
		deque.addBack(1);
		deque.addBack(2);
		deque.addBack(3);
		assertEquals(3, deque.removeBack());
		assertEquals("(1, 2)", deque.toString());
		assertEquals(2, deque.removeBack());
		assertEquals("(1)", deque.toString());
		assertEquals(1, deque.removeBack());
		assertEquals("()", deque.toString());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(deque.isEmpty());
		deque.addFront(1);
		assertFalse(deque.isEmpty());
		assertEquals(1, deque.removeFront());
		assertTrue(deque.isEmpty());
	}

	@Test
	public void testGetFront() {
		deque.addFront(1);
		assertEquals(1, deque.getFront());
		deque.addFront(2);
		assertEquals(2, deque.getFront());
	}

	@Test
	public void testClear() {
		deque.addFront(1);
		deque.addFront(2);
		deque.clear();
		assertTrue(deque.isEmpty());
	}

	@Test
	public void getFrontNode() {
		deque.addFront(1);
		deque.addFront(2);
		Node n = deque.getFrontNode();
		assertEquals(2, n.getKey());
		assertEquals(2, deque.removeFront());
		assertEquals(deque.getFrontNode(), n.getNext());
		n = deque.getFrontNode();
		assertEquals(1, n.getKey());
		assertNull(n.getNext());
	}

	@Test
	public void testTrickyRemoval() {
		deque.addFront(1);
		assertEquals(1, deque.removeFront());
		deque.addFront(2);
		assertEquals(2, deque.removeBack());
	}

}
