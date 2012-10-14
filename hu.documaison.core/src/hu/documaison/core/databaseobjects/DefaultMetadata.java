package hu.documaison.core.databaseobjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DefaultMetadata")
public class DefaultMetadata extends DatabaseObject {
	public static final String PARENT = "parent";
	// ugly duplication but OrmLite doesn't really support inheritance

	@DatabaseField(canBeNull = false)
	protected String name;

	@DatabaseField(canBeNull = true)
	protected String value;

	@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	protected DocumentType parent;

	public DefaultMetadata() {
		// ORMLite needs a no-arg constructor
	}

	public DefaultMetadata(String name, String value) {
		setName(name);
		setValue(value);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public void setParent(DocumentType parent) {
		this.parent = parent;
	}

	public Metadata createMetadata() {
		Metadata ret = new Metadata();
		ret.name = this.name;
		ret.value = this.value;
		return ret;
	}
}
