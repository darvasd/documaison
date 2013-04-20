package hu.documaison.test.data;

import static org.junit.Assert.*;

import javax.print.Doc;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentTagConnection;
import hu.documaison.data.entities.Tag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;

public class DocumentTagConnectionTest {
	private DocumentTagConnection dtc;

	@Before
	public void setUp() throws Exception {
		this.dtc = new DocumentTagConnection();
	}

	@Test
	public void testSetDocument() {
		// arrange
		Document document = mock(Document.class);

		// act
		this.dtc.setDocument(document);

		// assert
		assertSame(document, this.dtc.getDocument());
	}

	@Test
	public void testSetTag() {
		// arrange
		Tag tag = mock(Tag.class);

		// act
		this.dtc.setTag(tag);

		// assert
		assertSame(tag, this.dtc.getTag());
	}
}
