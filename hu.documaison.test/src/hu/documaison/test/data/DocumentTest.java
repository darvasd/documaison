package hu.documaison.test.data;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;

import hu.documaison.data.entities.Comment;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;

import org.junit.Before;
import org.junit.Test;

import com.j256.ormlite.dao.ForeignCollection;

public class DocumentTest {
	private Document document;

	@Before
	public void setUp() throws Exception {
		this.document = new Document();
	}

	@Test
	public void testSetLocation() {
		// arrange
		String location = "asdfg";

		// act
		this.document.setLocation(location);

		// assert
		assertEquals(location, this.document.getLocation());
	}

	@Test
	public void testSetCreatorComputerId() {
		// arrange
		String creatorComputerId = "asdfg";

		// act
		this.document.setCreatorComputerId(creatorComputerId);

		// assert
		assertEquals(creatorComputerId, this.document.getCreatorComputerId());
	}

	@Test
	public void testSetType() {
		// arrange
		DocumentType type = mock(DocumentType.class);

		// act
		this.document.setType(type);

		// assert
		assertEquals(type, this.document.getType());
	}

	@Test
	public void testSetDateAdded() {
		// arrange
		Date date = new Date();

		// act
		this.document.setDateAdded(date);

		// assert
		assertEquals(date, this.document.getDateAdded());
	}

	@Test
	public void testSetThumbnailBytes() {
		// arrange
		byte[] thumbnailBytes = new byte[1000];

		// act
		this.document.setThumbnailBytes(thumbnailBytes);

		// assert
		assertEquals(thumbnailBytes, this.document.getThumbnailBytes());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMetadata() {
		// arrange
		Metadata metadata1 = mock(Metadata.class);
		when(metadata1.getName()).thenReturn("metadata1");

		Metadata metadata2 = mock(Metadata.class);
		when(metadata2.getName()).thenReturn("metadata2");

		ForeignCollection<Metadata> collection = (ForeignCollection<Metadata>) mock(ForeignCollection.class);
		ArrayList<Metadata> list = new ArrayList<Metadata>();
		list.add(metadata1);
		list.add(metadata2);
		when(collection.iterator()).thenReturn(list.iterator());

		// act
		this.document.setMetadataCollection(collection);
		this.document.addMetadata(metadata1);
		this.document.addMetadata(metadata2);

		// assert

		// single getter works
		assertEquals(metadata1, this.document.getMetadata("metadata1"));
		assertEquals(metadata2, this.document.getMetadata("metadata2"));
		assertNull(this.document.getMetadata("metadata3"));

		// collection getter works
		assertEquals(collection, this.document.getMetadataCollection());

		// mock is OK
		verify(collection, times(1)).add(metadata1);
		verify(collection, times(1)).add(metadata2);
		verify(collection, times(3)).iterator();
		
		this.document.removeMetadata(metadata1);
		verify(collection, times(1)).remove(metadata1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddComment() {
		// arrange
		Comment comment1 = mock(Comment.class);
		Comment comment2 = mock(Comment.class);

		ForeignCollection<Comment> collection = (ForeignCollection<Comment>) mock(ForeignCollection.class);
		ArrayList<Comment> list = new ArrayList<Comment>();
		list.add(comment1);
		list.add(comment2);
		when(collection.iterator()).thenReturn(list.iterator());

		// act
		this.document.setCommentCollection(collection);
		this.document.addComment(comment1);
		this.document.addComment(comment2);

		// assert

		// collection getter works
		assertEquals(collection, this.document.getCommentCollection());

		// mock is OK
		verify(collection, times(1)).add(comment1);
		verify(collection, times(1)).add(comment2);
		
		this.document.removeComment(comment1);
		verify(collection, times(1)).remove(comment1);
	}

}
