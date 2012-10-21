package hu.documaison.data.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public abstract class AbstractMetadata extends DatabaseObject {
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	public static final String NAME = "name";
	public static final String PARENT = "parent";
	public static final String VALUE = "value";

	@DatabaseField(dataType = DataType.ENUM_INTEGER)
	protected MetadataType metadataType = MetadataType.Text;
	
	@DatabaseField(canBeNull = true, columnName = NAME)
	protected String name;
	
	@DatabaseField(canBeNull = true, columnName = VALUE)
	protected String value;

	public AbstractMetadata() {
		super();
	}

	public Date getDateValue() {
		if (this.getMetadataType() == MetadataType.Date) {
			try {
				return DATEFORMAT.parse(this.getValueInternal());
			} catch (ParseException e) {
				System.err.println("Unable to convert data: "
						+ this.getValueInternal());
			}
		} else {
			System.err.println("Unable to convert data.");
			// TODO: rendes hibakezelés
		}
		return null;
	}

	public int getIntValue() {
		if (this.getMetadataType() == MetadataType.Integer) {
			try {
				return Integer.parseInt(this.getValueInternal());
			} catch (NumberFormatException e) {
				System.err.println("Unable to convert data: "
						+ this.getValueInternal());
			}
		} else {
			System.err.println("Unable to convert data.");
			// TODO: rendes hibakezelés
		}
		return -1;
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

	protected String getValueInternal() {
		return this.value;
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

	public void setValue(Date date) {
		if (this.getMetadataType() == MetadataType.Date) {
			setValueInternal(DATEFORMAT.format(date));
		} else {
			System.err.println("Unable to convert data.");
			// TODO: rendes hibakezelés
		}
	}

	public void setValue(int x) {
		if (this.getMetadataType() == MetadataType.Integer) {
			setValueInternal(Integer.toString(x));
		} else {
			System.err.println("Unable to convert data.");
			// TODO: rendes hibakezelés
		}
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		if (this.getMetadataType() == MetadataType.Text) {
			setValueInternal(value);
		} else {
			System.err.println("Unable to convert data.");
			// TODO: rendes hibakezelés
		}
	}

	protected void setValueInternal(String value) {
		this.value = value;
	}
}