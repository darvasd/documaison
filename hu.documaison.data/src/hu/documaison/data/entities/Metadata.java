package hu.documaison.data.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Metadata")
public class Metadata extends DatabaseObject {
	public static final String PARENT = "parent";
	// ugly duplication but OrmLite doesn't really support inheritance

	@DatabaseField(canBeNull = false)
	protected String name;

	@DatabaseField(canBeNull = true)
	protected String value;

	@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	protected Document parent;

	public Metadata() {
		// ORMLite needs a no-arg constructor
	}

	public Metadata(String name, String value) {
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

	public void setParent(Document parent) {
		this.parent = parent;
	}
}
