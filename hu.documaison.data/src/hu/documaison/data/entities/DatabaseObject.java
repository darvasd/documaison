package hu.documaison.data.entities;

import com.j256.ormlite.field.DatabaseField;

public abstract class DatabaseObject {
	public static final String ID = "id";
	
	@DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName=ID)
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
	private void setId(int id) {
		this.id = id;
	}
}
