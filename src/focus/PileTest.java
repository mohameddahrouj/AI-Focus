package focus;

import static focus.Focus.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PileTest {

	private Pile pile;
	
	@Before
	public void setUp() throws Exception {
		pile = new Pile();
	}

	@Test
	public void testToString() {
		pile.addFront(RED);
		pile.addBack(GREEN);
		assertEquals("O#", pile.toString());
	}

}
