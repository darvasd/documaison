package hu.documaison.core.viewmodel;

import hu.documaison.core.databaseobjects.Comment;
import hu.documaison.core.databaseobjects.Document;
import hu.documaison.core.databaseobjects.DocumentType;
import hu.documaison.core.databaseobjects.Metadata;
import hu.documaison.core.databaseobjects.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DocumentViewModel {
	private int id;
	private String location;
	private DocumentTypeViewModel type;
	private Date dateAdded;
	private byte[] thumbnailBytes;
	private List<String> tags;
	private HashMap<String, String> metadataCollection;
	private List<String> comments;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the comments
	 */
	public List<String> getComments() {
		return comments;
	}

	/**
	 * @param comment the comment to add
	 */
	public void addComment(String comment) {
		this.comments.add(comment);
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the type
	 */
	public DocumentTypeViewModel getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(DocumentTypeViewModel type) {
		this.type = type;
	}

	/**
	 * @return the dateAdded
	 */
	public Date getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded
	 *            the dateAdded to set
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
	 * @param thumbnailBytes
	 *            the thumbnailBytes to set
	 */
	public void setThumbnailBytes(byte[] thumbnailBytes) {
		this.thumbnailBytes = thumbnailBytes;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @return the metadataCollection
	 */
	public HashMap<String, String> getMetadataCollection() {
		//TODO make readonly
		return metadataCollection;
	}

	public void addTag(String tag) {
		this.tags.add(tag);
	}

	public void removeTag(String tag) {
		this.tags.remove(tag);
	}

	public void addMetadata(String name, String value) {
		this.metadataCollection.put(name, value);
	}

	public String getMetadataValue(String name) {
		return this.metadataCollection.get(name);
	}
	
	public static DocumentViewModel createFromDatabaseEntity(Document document)
	{
		DocumentViewModel ret = new DocumentViewModel();
		ret.id = document.getId();
		ret.location = document.getLocation();
		ret.type = DocumentTypeViewModel.createFromDatabaseEntity(document.getType());
		ret.dateAdded = document.getDateAdded();
		ret.thumbnailBytes = document.getThumbnailBytes();
		ret.tags = new ArrayList<String>();
		for (Tag t : document.getTags())
		{
			ret.tags.add(t.getName());
		}
		ret.metadataCollection = new HashMap<String, String>();
		for (Metadata md : document.getMetadataCollection())
		{
			ret.metadataCollection.put(md.getName(), md.getValue());
		}
		ret.comments = new ArrayList<String>();
		for (Comment c : document.getCommentCollection())
		{
			ret.tags.add(c.getMessage());
		}
		
		return ret;
	}
}
