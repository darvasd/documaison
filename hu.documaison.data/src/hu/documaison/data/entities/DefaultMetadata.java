package hu.documaison.data.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DefaultMetadata")
public class DefaultMetadata extends DatabaseObject {
	public static final String PARENT = "parent";
	// ugly duplication but OrmLite doesn't really support inheritance

	@DatabaseField(dataType = DataType.ENUM_INTEGER)
	protected MetadataType metadataType = MetadataType.Text;

	@DatabaseField(canBeNull = true)
	protected String name;

	@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	protected DocumentType parent;

	@DatabaseField(canBeNull = true)
	protected String value;

	public DefaultMetadata() {
		// ORMLite needs a no-arg constructor
	}

	public Metadata createMetadata() {
		Metadata ret = new Metadata();
		ret.name = this.name;
		ret.value = this.value;
		return ret;
	}
	
	public MetadataType getMetadataType() {
		return metadataType;
	}

//	public DefaultMetadata(String name, String value) {
//		setName(name);
//		setValue(value);
//	}

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

	public void setParent(DocumentType parent) {
		this.parent = parent;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
