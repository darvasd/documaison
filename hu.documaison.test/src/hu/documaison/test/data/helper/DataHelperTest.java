package hu.documaison.test.data.helper;

import static org.junit.Assert.*;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.helper.DataHelper;
import hu.documaison.data.helper.FileHelper;

import org.junit.Test;
import static org.mockito.Mockito.*;
public class DataHelperTest {

	@Test
	public void testIsValidId() {
		assertTrue(DataHelper.isValidId(1));
		assertTrue(DataHelper.isValidId(100));
		
		assertFalse(DataHelper.isValidId(0));
		assertFalse(DataHelper.isValidId(-1));
	}

	@Test
	public void testIsURL() {
		assertTrue(DataHelper.isURL("http://test"));
		assertTrue(DataHelper.isURL("https://test"));
		
		assertFalse(DataHelper.isURL("ftp://test"));
		assertFalse(DataHelper.isURL("test"));
		assertFalse(DataHelper.isURL(null));
	}

	@Test
	public void testCreateBibTex() {
		// arrange
		Document document = mock(Document.class);
		Metadata titleMetadata = mock(Metadata.class);
		when(titleMetadata.getValue()).thenReturn("ttttt");
		Metadata authorMetadata = mock(Metadata.class);
		when(authorMetadata.getValue()).thenReturn("aaaaa");
		Metadata locationMetadata = mock(Metadata.class);
		when(locationMetadata.getValue()).thenReturn("http://lllll");
		
		when(document.getId()).thenReturn(123);
		when(document.getMetadata("Title")).thenReturn(titleMetadata);
		when(document.getMetadata("Author")).thenReturn(authorMetadata);
		when(document.getMetadata("Location")).thenReturn(locationMetadata);
		
		String ret = "@Article{article123,\n" +
				"\ttitle = \"ttttt\",\n" + 
				"\tauthor = \"aaaaa\",\n" +
				"\turl = \"http://lllll\",\n" +
				"}";
		
		assertEquals(ret, DataHelper.createBibTex(document));
	}

	@Test
	public void testExtension() {
		assertEquals("pdf", FileHelper.fileExtension("c:\\test\\test.pdf"));
	}
}

