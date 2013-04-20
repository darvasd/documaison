package hu.documaison.test.data;

import static org.junit.Assert.*;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.ParentRunner;
import static org.mockito.Mockito.mock;

public class MetadataTest {
	private Metadata metadata;
	
	@Before
	public void setUp() throws Exception {
		this.metadata = new Metadata();
	}

	@Test
	public void testSetParent() {
		// arrange
		Document parent = mock(Document.class);
		
		// act
		metadata.setParent(parent);
		
		// assert
		// none
	}

}
