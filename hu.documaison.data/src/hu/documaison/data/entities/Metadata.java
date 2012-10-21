package hu.documaison.data.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Metadata")
public class Metadata extends DatabaseObject {
	public static final String NAME = "name";
	// ugly duplication but OrmLite doesn't really support inheritance
	public static final String PARENT = "parent";
	public static final String VALUE = "value";
	public static final SimpleDateFormat DATEFORMAT =
            new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@DatabaseField(dataType = DataType.ENUM_INTEGER)
	protected MetadataType metadataType = MetadataType.Text;

	@DatabaseField(canBeNull = false, columnName = NAME)
	protected String name;

	@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	protected Document parent;
	
	@DatabaseField(canBeNull = true, columnName = VALUE)
	protected String value;

	public Metadata() {
		// ORMLite needs a no-arg constructor
	}

	public Metadata(MetadataType type, Document parent) {
		setMetadataType(type);
		setParent(parent);
	}

	public MetadataType getMetadataType() {
		return metadataType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public void setMetadataType(MetadataType metadataType) {
		this.metadataType = metadataType;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Document parent) {
		this.parent = parent;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		if (this.metadataType == MetadataType.Text)
		{
			this.value = value;
		}
		else
		{
			System.err.println("Unable to convert data.");
			//TODO: rendes hibakezelés
		}
	}
	
	public void setValue(int x)
	{
		if (this.metadataType == MetadataType.Integer)
		{
		this.value = Integer.toString(x);
		}
		else
		{
			System.err.println("Unable to convert data.");
			//TODO: rendes hibakezelés
		}
	}
	
	public void setValue(Date date)
	{
		if (this.metadataType == MetadataType.Date)
		{
			this.value = DATEFORMAT.format(date);
		}
		else
		{
			System.err.println("Unable to convert data.");
			//TODO: rendes hibakezelés
		}
	}
	
	public Date getDateValue()
	{
		if (this.metadataType == MetadataType.Date)
		{
			try {
				return DATEFORMAT.parse(this.value);
			} catch (ParseException e) {
				System.err.println("Unable to convert data: " + this.value);
			}
		}
		else
		{
			System.err.println("Unable to convert data.");
			//TODO: rendes hibakezelés
		}	
		return null;
	}
	
	public int getIntValue()
	{
		if (this.metadataType == MetadataType.Integer)
		{
			try {
				return Integer.parseInt(this.value);
			} catch (NumberFormatException e) {
				System.err.println("Unable to convert data: " + this.value);
			}
		}
		else
		{
			System.err.println("Unable to convert data.");
			//TODO: rendes hibakezelés
		}	
		return -1;
	}
}
