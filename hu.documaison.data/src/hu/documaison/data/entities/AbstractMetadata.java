package hu.documaison.data.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;

import org.apache.log4j.Logger;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public abstract class AbstractMetadata extends DatabaseObject {
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	public static final String NAME = "name";
	public static final String PARENT = "parent";
	public static final String VALUE = "value";
	
	private static final Logger logger = Logger.getLogger("DocuMaison.DAL");

	@DatabaseField(dataType = DataType.ENUM_INTEGER)
	// @Column
	protected MetadataType metadataType = MetadataType.Text;

	// @DatabaseField(canBeNull = true, columnName = NAME)
	@Column(name = NAME, nullable = true)
	protected String name; // TODO must not be null

	// @DatabaseField(canBeNull = true, columnName = VALUE)
	@Column(name = VALUE, nullable = true)
	protected String value = null;

	public AbstractMetadata() {
		super();
	}

	public Date getDateValue() {
		if (this.getMetadataType() == MetadataType.Date) {
			try {
				return DATEFORMAT.parse(this.getValueInternal());
			} catch (ParseException e) {
				logger.error("Unable to convert data: "
						+ this.getValueInternal());
			} catch (NullPointerException e) {
				logger.error("Unable to convert data: "
						+ this.getValueInternal());
			}

		} else {
			logger.error("Unable to convert data (as date): "
					+ this.getValueInternal());
		}
		return null;
	}

	public int getIntValue() {
		if (this.getMetadataType() == MetadataType.Integer) {
			try {
				return Integer.parseInt(this.getValueInternal());
			} catch (NumberFormatException e) {
				logger.error("Unable to convert data: "
						+ this.getValueInternal());
			}
		} else {
			logger.error("Unable to convert data (as integer): "
					+ this.getValueInternal());
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

	private static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}

		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static boolean isDate(String str) {
		if (str == null) {
			return false;
		}

		try {
			DATEFORMAT.parse(str);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public void setMetadataType(MetadataType metadataType) {
		MetadataType oldType = this.metadataType;
		this.metadataType = metadataType;

		if (oldType == this.metadataType || this.value == null) {
			// there is no need to drop the old value
			return;
		}

		switch (this.metadataType) {
		case Text:
			// there is no need to modify anything
			break;
		case Date:
			if (oldType == MetadataType.Text && isDate(this.value)) {
				// the value won't be changed
			} else {
				this.value = null;
			}
			break;
		case Integer:
			if (oldType == MetadataType.Text && isInteger(this.value)) {
				// the value won't be changed
			} else {
				this.value = null;
			}
			break;
		}
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
			logger.error("Unable to convert data @ setValue(Date).");
		}
	}

	public void setValue(int x) {
		if (this.getMetadataType() == MetadataType.Integer) {
			setValueInternal(Integer.toString(x));
		} else {
			logger.error("Unable to convert data @ setValue(int).");
			// TODO: rendes hibakezelés
		}
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		setValueInternal(value);
//		if (this.getMetadataType() == MetadataType.Text) {
//			setValueInternal(value);
//		} else {
//			logger.error("Unable to convert data @ setValue(String), because field type = " + this.getMetadataType().toString());
//		}
	}

	protected void setValueInternal(String value) {		
		this.value = value;
	}
}