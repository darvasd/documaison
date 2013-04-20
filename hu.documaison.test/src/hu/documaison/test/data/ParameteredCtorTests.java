package hu.documaison.test.data;

import static org.junit.Assert.*;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentTagConnection;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.entities.MetadataType;
import hu.documaison.data.entities.Tag;

import org.junit.Test;
import static org.mockito.Mockito.*;


public class ParameteredCtorTests {

	@Test
	public void testDefaultMetadata() {
		//arrange
		MetadataType metadataType = MetadataType.Date;
		DocumentType documentType = mock(DocumentType.class);
		
		// act
		DefaultMetadata defaultMetadata = new DefaultMetadata(metadataType, documentType);
		
		// assert
		assertEquals(metadataType, defaultMetadata.getMetadataType());
	}
	
	@Test
	public void testTag()
	{
		// arrange
		String name = "abcdef";
		
		// act
		Tag tag = new Tag(name);
		
		// assert
		assertEquals(name, tag.getName());
	}
	
	@Test
	public void testMetadata(){
		// arrange
		MetadataType metadataType = MetadataType.Date;
		Document document = mock(Document.class);
		
		// act
		Metadata metadata = new Metadata(metadataType, document);
		
		// assert
		assertEquals(metadataType, metadata.getMetadataType());
	}
	
	@Test
	public void testDTC(){
		// arrange
		Document document = mock(Document.class);
		Tag tag = mock(Tag.class);
		
		// act
		DocumentTagConnection dtc = new DocumentTagConnection(document, tag);
		
		// assert
		assertEquals(document, dtc.getDocument());
		assertEquals(tag, dtc.getTag());
	}
	

}
