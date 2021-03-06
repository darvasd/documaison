package hu.documaison.data.entities;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Comments")
public class Comment extends DatabaseObject {
	public static final String PARENT = "parent";
	
	
	@DatabaseField
	private String message;
	
	@DatabaseField
	private Date createdDate;
	
	@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	protected Document parent;
	
	public Document getParent() {
		return parent;
	}

	public void setParent(Document parent) {
		this.parent = parent;
	}

	public Comment()
	{
		// ORMLite needs a no-arg constructor 
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
