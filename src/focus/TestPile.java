package focus;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestPile {

	private Pile pile;
	
	@Before
	public void setUp() throws Exception {
		pile = new Pile();
	}

	@Test
	public void testToString() {
		pile.addFront(0);//GREEN
		pile.addBack(1);//RED
		assertEquals("#O", pile.toString());
	}

}
