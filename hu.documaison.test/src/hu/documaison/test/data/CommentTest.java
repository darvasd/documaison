package hu.documaison.test.data;

import static org.junit.Assert.*;

import java.util.Date;

import hu.documaison.data.entities.Comment;
import hu.documaison.data.entities.Document;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class CommentTest {
	private Comment comment;

	@Before
	public void setUp() {
		this.comment = new Comment();
	}

	@Test
	public void testSetParent() {
		// arrange
		Document parent = mock(Document.class);

		// act
		this.comment.setParent(parent);

		// assert
		assertEquals(parent, this.comment.getParent());
	}

	@Test
	public void testSetMessage() {
		// arrange
		String message = "abc123";

		// act
		this.comment.setMessage(message);

		// assert
		assertEquals(message, this.comment.getMessage());
	}

	@Test
	public void testSetCreatedDate() {
		// arrange
		Date date = new Date();

		// act
		this.comment.setCreatedDate(date);

		// assert
		assertEquals(date, this.comment.getCreatedDate());
	}

}
