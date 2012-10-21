package hu.documaison.data.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DocumentTagConnection extends DatabaseObject {
	public static final String DOCUMENTID = "document";
	public static final String TAGID = "tag";
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = DOCUMENTID)
	private Document document;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = TAGID)
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
