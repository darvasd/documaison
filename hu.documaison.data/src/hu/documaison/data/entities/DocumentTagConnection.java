package hu.documaison.data.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class DocumentTagConnection extends DatabaseObject {
	public static final String DOCUMENTID = "document";
	public static final String TAGID = "tag";
	
	//@DatabaseField(canBeNull = false, foreign = true, columnName = DOCUMENTID)
	@ManyToOne
	@JoinColumn(nullable = false, name = DOCUMENTID)
	private Document document;
	
	//@DatabaseField(canBeNull = false, foreign = true, columnName = TAGID)
	@ManyToOne
	@JoinColumn(nullable = false, name = TAGID)
	private Tag tag;

	public DocumentTagConnection()
	{
		// ORMLite needs a no-arg constructor
	}

	public DocumentTagConnection(Document document, Tag tag)
	{
		this.document = document;
		this.tag = tag;
	}

	public Document getDocument() {
		return document;
	}
	
	public Tag getTag()
	{
		return this.tag;
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
