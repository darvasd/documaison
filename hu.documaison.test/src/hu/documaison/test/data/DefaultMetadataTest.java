package hu.documaison.test.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import hu.documaison.data.entities.AbstractMetadata;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.entities.MetadataType;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DefaultMetadataTest {
	private DefaultMetadata defaultMetadata;
	private String name = "abc1234";
	private DocumentType parent = mock(DocumentType.class);

	@Before
	public void setUp() {
		this.defaultMetadata = new DefaultMetadata();
		this.defaultMetadata.setName(name);
		this.defaultMetadata.setParent(parent);
	}

	@Test
	public void testParams() {
		// arrange

		// act

		// assert
		assertEquals(name, this.defaultMetadata.getName());
	}

	@Test
	public void testIntValue() {
		// arrange
		int x = 1234;
		this.defaultMetadata.setMetadataType(MetadataType.Integer);

		// act
		this.defaultMetadata.setValue(x);

		// assert
		assertEquals(Integer.toString(x), this.defaultMetadata.getValue());
		assertEquals(x, this.defaultMetadata.getIntValue());
		assertNull(this.defaultMetadata.getDateValue());
	}

	@Test
	public void testStringValue() {
		// arrange
		String value = "asdfggh";
		this.defaultMetadata.setMetadataType(MetadataType.Text);

		// act
		this.defaultMetadata.setValue(value);

		// assert
		assertEquals(value, this.defaultMetadata.getValue());
		assertNull(this.defaultMetadata.getDateValue());
		assertEquals(-1, this.defaultMetadata.getIntValue());
	}

	@Test
	public void testDateValue() {
		// arrange
		Date date = new Date();
		this.defaultMetadata.setMetadataType(MetadataType.Date);

		// act
		this.defaultMetadata.setValue(date);

		// assert
		assertEquals(AbstractMetadata.DATEFORMAT.format(date),
				this.defaultMetadata.getValue());
		assertEquals(-1, this.defaultMetadata.getIntValue());
		assertEquals(date, this.defaultMetadata.getDateValue());
	}

	@Test
	public void testSwitchType() {
		int x = 10;
		Date date = new Date();
		String str = "asdfgh";

		// Int -> Date
		this.defaultMetadata.setMetadataType(MetadataType.Integer);
		this.defaultMetadata.setValue(x);
		this.defaultMetadata.setMetadataType(MetadataType.Date);
		assertNull(this.defaultMetadata.getValue());

		// Int -> String
		this.defaultMetadata.setMetadataType(MetadataType.Integer);
		this.defaultMetadata.setValue(x);
		this.defaultMetadata.setMetadataType(MetadataType.Text);
		assertEquals(Integer.toString(x), this.defaultMetadata.getValue());

		// Date -> Int
		this.defaultMetadata.setMetadataType(MetadataType.Date);
		this.defaultMetadata.setValue(date);
		this.defaultMetadata.setMetadataType(MetadataType.Integer);
		assertNull(this.defaultMetadata.getValue());

		// Date -> String -> Date
		this.defaultMetadata.setMetadataType(MetadataType.Date);
		this.defaultMetadata.setValue(date);
		this.defaultMetadata.setMetadataType(MetadataType.Text);
		assertEquals(AbstractMetadata.DATEFORMAT.format(date),
				this.defaultMetadata.getValue());
		this.defaultMetadata.setMetadataType(MetadataType.Date);
		assertEquals(AbstractMetadata.DATEFORMAT.format(date),
				this.defaultMetadata.getValue());
		assertEquals(date, this.defaultMetadata.getDateValue());

		// String -> Int
		this.defaultMetadata.setMetadataType(MetadataType.Text);
		this.defaultMetadata.setValue(str);
		this.defaultMetadata.setMetadataType(MetadataType.Integer);
		assertNull(this.defaultMetadata.getValue());
		assertEquals(-1, this.defaultMetadata.getIntValue());

		// String -> Date
		this.defaultMetadata.setMetadataType(MetadataType.Text);
		this.defaultMetadata.setValue(str);
		this.defaultMetadata.setMetadataType(MetadataType.Date);
		assertNull(this.defaultMetadata.getValue());

	}
	
	@Test
	public void testCreateMetadata()
	{
		// arrange
		this.defaultMetadata.setName("asdfg1");
		this.defaultMetadata.setMetadataType(MetadataType.Text);
		this.defaultMetadata.setValue("asdfg2");
		
		// act
		Metadata md = this.defaultMetadata.createMetadata();
		
		// assert
		assertEquals(this.defaultMetadata.getName(), md.getName());
		assertEquals(this.defaultMetadata.getValue(), md.getValue());
		assertEquals(this.defaultMetadata.getMetadataType(), md.getMetadataType());
	}
}
