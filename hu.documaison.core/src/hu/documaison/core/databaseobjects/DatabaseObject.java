package hu.documaison.core.databaseobjects;

import com.j256.ormlite.field.DatabaseField;

public abstract class DatabaseObject {
	@DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
	private int id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
