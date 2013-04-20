package hu.documaison.test.data;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import hu.documaison.data.entities.Comment;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;

import org.junit.Before;
import org.junit.Test;

import com.j256.ormlite.dao.ForeignCollection;

public class DocumentTypeTest {
	private DocumentType type;

	@Before
	public void setUp() throws Exception {
		this.type = new DocumentType();
	}

	@Test
	public void testSetTypeName() {
		// arrange
		String name = "asdfgh";

		// act
		this.type.setTypeName(name);

		// assert
		assertEquals(name, this.type.getTypeName());
	}

	@Test
	public void testSetDefaultExt() {
		// arrange
		String name = "asdfgh";

		// act
		this.type.setDefaultExt(name);

		// assert
		assertEquals(name, this.type.getDefaultExt());
	}

	@Test
	public void testSetDefaultThumbnailBytes() {
		// arrange
		byte[] name = new byte[1000];

		// act
		this.type.setDefaultThumbnailBytes(name);

		// assert
		assertEquals(name, this.type.getDefaultThumbnailBytes());
	}

	@Test
	public void testAddMetadata() {
		// arrange
		DefaultMetadata metadata1 = mock(DefaultMetadata.class);
		DefaultMetadata metadata2 = mock(DefaultMetadata.class);

		ForeignCollection<DefaultMetadata> collection = 
				(ForeignCollection<DefaultMetadata>) mock(ForeignCollection.class);

		// act
		this.type.setDefaultMetadataCollection(collection);
		this.type.addMetadata(metadata1);
		this.type.addMetadata(metadata2);

		// assert
		// collection getter works
		assertEquals(collection, this.type.getDefaultMetadataCollection());
		// mock is OK
		verify(collection, times(1)).add(metadata1);
		verify(collection, times(1)).add(metadata2);
		
		// act2
		this.type.removeMetadata(metadata1);
		verify(collection, times(1)).remove(metadata1);
	}

}
