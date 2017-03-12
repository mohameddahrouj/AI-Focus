package focus;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestFocus {

	private Focus game;

	@Before
	public void setUp() throws Exception {
		game = new Focus();
	}

	@Test
	public void testDistance() {
		assertEquals(3, game.distance(new Move(2, 5, 2, 8)));
		assertEquals(1, game.distance(new Move(6, 4, 5, 4)));
		assertEquals(-1, game.distance(new Move(3, 4, 3, 4)));
		assertEquals(-1, game.distance(new Move(1, 2, 3, 4)));
	}

	@Test
	public void testIsLegalSource() {
		assertFalse(game.isLegalSource(0, 2)); // Empty pile
		assertTrue(game.isLegalSource(1, 1));
		assertFalse(game.isLegalSource(6, 6)); // Wrong color
	}
	
	@Test
	public void testIsLegal() {
		Pile p = new Pile();
		p.addFront(game.GREEN);
		// The piece below is played from imaginary reserves
		p.addFront(game.GREEN);
		game.setPile(1, 3, p);
		assertTrue(game.isLegal(new Move(1, 3, 1, 5)));
		assertTrue(game.isLegal(new Move(1, 3, 0, 3)));
		assertFalse(game.isLegal(new Move(1, 3, 4, 3))); // Too far
		assertFalse(game.isLegal(new Move(6, 7, 5, 7))); // Source off board
		assertFalse(game.isLegal(new Move(1, 3, 2, 4))); // Not orthogonal
		assertFalse(game.isLegal(new Move(1, 3, 1, 3))); // No movement
		assertFalse(game.isLegal(new Move(2, 1, 2, 2))); // Wrong color
	}
	
	@Test
	public void testIsCorrectColour() {
		assertTrue(game.getPile(1,1).getFront() == game.GREEN);
		assertTrue(game.getPile(1,2).getFront() == game.GREEN);
		assertTrue(game.getPile(2,1).getFront() == game.RED);
		assertTrue(game.getPile(2,2).getFront() == game.RED);
	}

	@Test
	public void testToString() {
		String correct = " 01234567\n0  ....  0\n1 ##OO## 1\n2.OO##OO.2\n3.##OO##.3\n4.OO##OO.4\n5.##OO##.5\n6 OO##OO 6\n7  ....  7\n 01234567\nReserves: # 0, O 0\n";
		assertEquals(correct, game.toString());
	}

	@Test
	public void testMove() {
		game.move(new Move(5, 2, 5, 3));
		String correct = " 01234567\n0  ....  0\n1 ##OO## 1\n2.OO##OO.2\n3.##OO##.3\n4.OO##OO.4\n5.#.#O##.5\n6 OO##OO 6\n7  ....  7\n 01234567\n5, 3: #O\nReserves: # 0, O 0\n";
		assertEquals(correct, game.toString());
		assertEquals("#O", game.getPile(5, 3).toString());
	}

	@Test
	public void testPlayFromReserves() {
		// Not actually a legal move because game.GREEN has no reserves, but we can
		// test the effects anyway
		game.playFromReserves(3, 7);
		String correct = " 01234567\n0  ....  0\n1 ##OO## 1\n2.OO##OO.2\n3.##OO###3\n4.OO##OO.4\n5.##OO##.5\n6 OO##OO 6\n7  ....  7\n 01234567\nReserves: # -1, O 0\n";
		assertEquals(correct, game.toString());
		assertEquals("#", game.getPile(3, 7).toString());
		assertEquals(-1, game.reserves(game.GREEN));
	}

	@Test
	public void testOpposite() {
		assertEquals(1, game.opposite(0));
		assertEquals(0, game.opposite(1));
	}

	@Test
	public void testColorToPlay() {
		assertEquals(game.GREEN, game.getCurrentPlayer());
		game.move(new Move(4, 5, 5, 5));
		assertEquals(game.RED, game.getCurrentPlayer());
		game.move(new Move(5, 1, 6, 1));
		assertEquals(game.GREEN, game.getCurrentPlayer());
	}

	@Test
	public void testReserves() {
		assertEquals(0, game.reserves(game.GREEN));
		assertEquals(0, game.reserves(game.RED));
		// Some of these moves are not legal (wrong color to play)
		game.move(new Move(2, 3, 3, 3));
		game.move(new Move(3, 3, 5, 3));
		game.move(new Move(5, 3, 5, 6));
		game.move(new Move(5, 6, 1, 6));
		game.move(new Move(1, 6, 1, 1));
		assertEquals(1, game.reserves(game.GREEN));
		assertEquals(0, game.reserves(game.RED));
		game.move(new Move(2, 1, 1, 1));
		assertEquals(1, game.reserves(game.GREEN));
		assertEquals(0, game.reserves(game.RED));		
	}

	@Test
	public void testGameOver() {
		for (int r = 0; r < game.BOARD_WIDTH; r++) {
			for (int c = 0; c < game.BOARD_WIDTH; c++) {
				Deque p = game.getPile(r, c);
				if (p != null) {
					p.clear();
				}
			}
		}
		Pile q = new Pile();
		q.addFront(game.RED);
		game.setPile(4, 4, q);
		game.setReserves(game.GREEN, 1);
		assertFalse(game.gameOver());
		game.playFromReserves(3, 4);
		assertFalse(game.gameOver());
		game.move(new Move(4, 4, 3, 4));
		assertTrue(game.gameOver());
	}

	@Test
	public void testIsOnBoard() {
		assertTrue(game.isOnBoard(1, 1));
		assertFalse(game.isOnBoard(6, 7));
		assertFalse(game.isOnBoard(3, 12));
	}

}
