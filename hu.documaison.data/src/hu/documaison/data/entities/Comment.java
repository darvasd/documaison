package hu.documaison.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name="Comments")
public class Comment extends DatabaseObject {
	public static final String PARENT = "parent";
	
	//@DatabaseField
	@Column
	private String message;
	
	//@DatabaseField
	@Column
	private Date createdDate;
	
	//@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	@ManyToOne
	@JoinColumn(name = PARENT, nullable = true)
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
