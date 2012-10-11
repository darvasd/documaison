package hu.documaison.core.databaseobjects;

import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Documents")
public class Document extends DatabaseObject {
	public static final String TAGS = "tags";

	@DatabaseField(canBeNull = false)
	private String location;
	
	@DatabaseField(foreign = true)
	private DocumentType type;
	
	@DatabaseField
	private Date dateAdded;
	
	//@DatabaseField(dataType = DataType.SERIALIZABLE)
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] thumbnailBytes;

	@ForeignCollectionField(eager = true, columnName = TAGS)
	ForeignCollection<Tag> tags;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<Metadata> metadataCollection; 
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<Comment> commentCollection; 
	
	public Document()
	{
		// ORMLite needs a no-arg constructor 
	}

	public Document(DocumentType type)
	{
		this.type = type;
		// TODO copy default metadata
	}
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the type
	 */
	public DocumentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DocumentType type) {
		this.type = type;
	}

	/**
	 * @return the dateAdded
	 */
	public Date getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded the dateAdded to set
	 */
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * @return the thumbnailBytes
	 */
	public byte[] getThumbnailBytes() {
		return thumbnailBytes;
	}

	/**
	 * @param thumbnailBytes the thumbnailBytes to set
	 */
	public void setThumbnailBytes(byte[] thumbnailBytes) {
		this.thumbnailBytes = thumbnailBytes;
	}

	/**
	 * @return the tags
	 */
	public ForeignCollection<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tag the tag to add 
	 */
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}
	
	/**
	 * @param tag the tag to remove 
	 */
	public void removeTag(Tag tag) {
		this.tags.remove(tag);
	}

	/**
	 * @return the metadataCollection
	 */
	public ForeignCollection<Metadata> getMetadataCollection() {
		return metadataCollection;
	}

	/**
	 * @param metadata the metadata to add
	 */
	public void addMetadata(Metadata metadata) {
		this.metadataCollection.add(metadata);
	}
	
	/**
	 * @param metadata the metadata to remove
	 */
	public void removeMetadata(Metadata metadata) {
		this.metadataCollection.remove(metadata);
	}
	
	/**
	 * @return the commentCollection
	 */
	public ForeignCollection<Comment> getCommentCollection() {
		return commentCollection;
	}

	/**
	 * @param comment the comment to add
	 */
	public void addComment(Comment comment) {
		this.commentCollection.add(comment);
	}
	
	/**
	 * @param comment the comment to remove
	 */
	public void removeComment(Comment comment) {
		this.commentCollection.remove(comment);
	}
}
