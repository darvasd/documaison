package hu.documaison.data.entities;

import javax.persistence.Column;
import javax.persistence.Id;

public abstract class DatabaseObject {
	public static final String ID = "id";
	
	//@DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName=ID)
	@Id
	@Column(name = ID)
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
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}
}
