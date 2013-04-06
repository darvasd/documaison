package hu.documaison.data.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;


@Entity(name = "Documents")
public class Document extends DatabaseObject {
	public static final String TAGS = "tags";
	public static final String METADATA = "metadata";
	public static final String COMMENTS = "comments";
	public static final String LOCATION = "location";
	protected static final String CREATOR_COMPUTERID = "creator_computerid";

	//@DatabaseField(columnName = LOCATION)
	@Column(name = LOCATION)
	private String location;

	//@DatabaseField(columnName = CREATOR_COMPUTERID)
	@Column(name = CREATOR_COMPUTERID)
	private String creatorComputerId;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	//@ManyToOne
	private DocumentType type;

	//@DatabaseField
	@Column
	private Date dateAdded;

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	//@Column
	private byte[] thumbnailBytes;

	@ForeignCollectionField(eager = true, columnName = TAGS)
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = DocumentTagConnection.DOCUMENTID)
	//@JoinColumn(name = TAGS)
	private ForeignCollection<DocumentTagConnection> tags;

	@ForeignCollectionField(eager = true, columnName = METADATA)
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = Metadata.PARENT)
	//@JoinColumn(name = METADATA)
	private ForeignCollection<Metadata> metadataCollection;

	@ForeignCollectionField(eager = true, columnName = COMMENTS)
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = Comment.PARENT)
	//@JoinColumn(name = COMMENTS)
	private ForeignCollection<Comment> commentCollection;

	public Document() {
		// ORMLite needs a no-arg constructor
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

	public String getCreatorComputerId() {
		return creatorComputerId;
	}

	public void setCreatorComputerId(String creatorComputerId) {
		this.creatorComputerId = creatorComputerId;
	}

	
	/**
	 * @return the type
	 */
	public DocumentType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
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
	public Collection<Tag> getTags() {
		List<Tag> ret = new ArrayList<Tag>();
		if (this.tags != null) {
			for (DocumentTagConnection dtc : this.tags) {
				ret.add(dtc.getTag());
			}
		}
		return ret;
	}

	/**
	 * @return the metadataCollection
	 */
	public Collection<Metadata> getMetadataCollection() {
		return metadataCollection;
	}

	public Metadata getMetadata(String metadataName) {
		if (this.metadataCollection == null) {
			return null;
		} else {
			for (Metadata md : this.metadataCollection) {
				if (md.getName().equals(metadataName)) {
					return md;
				}
			}
		}
		return null;
	}

	/**
	 * @param metadata
	 *            the metadata to add
	 */
	public void addMetadata(Metadata metadata) {
		this.metadataCollection.add(metadata);
	}

	/**
	 * @param metadata
	 *            the metadata to remove
	 */
	public void removeMetadata(AbstractMetadata metadata) {
		this.metadataCollection.remove(metadata);
	}

	/**
	 * @return the commentCollection
	 */
	public Collection<Comment> getCommentCollection() {
		return commentCollection;
	}

	/**
	 * @param comment
	 *            the comment to add
	 */
	public void addComment(Comment comment) {
		this.commentCollection.add(comment);
	}

	/**
	 * @param comment
	 *            the comment to remove
	 */
	public void removeComment(Comment comment) {
		this.commentCollection.remove(comment);
	}
}