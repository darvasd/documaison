package hu.documaison.data.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DocumentTagConnection extends DatabaseObject {
	@DatabaseField(canBeNull = false, foreign = true, columnName = Document.TAGS)
	private Document document;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = Tag.DOCUMENTS)
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
	
	public Tag getTag()
	{
		return this.tag;
	}
}
