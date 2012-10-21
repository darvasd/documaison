package hu.documaison.data.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tags")
public class Tag extends DatabaseObject {
	public static final String NAME = "name";

	@DatabaseField(columnName = NAME, unique = true)
	private String name;

	@DatabaseField
	private String colorName;

	@DatabaseField
	private boolean hidden;

	public Tag() {
		// ORMLite needs a no-arg constructor
	}

	public Tag(String name) {
		this.name = name;
		this.colorName = "red";
		this.hidden = false;
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
	 * @return the colorName
	 */
	public String getColorName() {
		return colorName;
	}

	/**
	 * @param colorName
	 *            the colorName to set
	 */
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
