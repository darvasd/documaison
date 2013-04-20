package hu.documaison.test.data;

import static org.junit.Assert.*;
import hu.documaison.data.entities.Tag;

import org.junit.Before;
import org.junit.Test;

public class TagTest {
	private Tag tag;

	@Before
	public void setUp() throws Exception {
		this.tag = new Tag();
	}

	@Test
	public void testSetName() {
		// arrange
		String name = "asdfgh";

		// act
		this.tag.setName(name);

		// assert
		assertEquals(name, this.tag.getName());
	}

	@Test
	public void testSetColorName() {
		// arrange
		String name = "asdfgh";

		// act
		this.tag.setColorName(name);

		// assert
		assertEquals(name, this.tag.getColorName());
	}

	@Test
	public void testSetHidden() {

		// act
		this.tag.setHidden(true);

		// assert
		assertTrue(this.tag.isHidden());

		// act
		this.tag.setHidden(false);

		// assert
		assertFalse(this.tag.isHidden());
	}

}
