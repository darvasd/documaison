package hu.documaison.data.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

@Entity(name = "DocumentTypes")
public class DocumentType extends DatabaseObject {
	public static final String METADATA = "metadata";
	public static final String DEFAULTEXTS = "defaultExt";

	// @DatabaseField
	@Column
	private String typeName;

	// @DatabaseField(columnName = DEFAULTEXTS)
	@Column(name = DEFAULTEXTS)
	private String defaultExt;

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	// @Column
	private byte[] defaultThumbnailBytes;

	@ForeignCollectionField(eager = true, foreignFieldName = DefaultMetadata.PARENT, columnName = METADATA)
	// @OneToMany(fetch = FetchType.EAGER, mappedBy = DefaultMetadata.PARENT)
	// @JoinColumn(name = METADATA)
	private ForeignCollection<DefaultMetadata> defaultMetadataCollection;

	public DocumentType() {
		// ORMLite needs a no-arg constructor
	}

	public DocumentType(Dao<DocumentType, Integer> dao) throws SQLException {
		this.defaultMetadataCollection = dao
				.getEmptyForeignCollection(METADATA);
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the defaultExt
	 */
	public String getDefaultExt() {
		return defaultExt;
	}

	/**
	 * @param defaultExt
	 *            the defaultExt to set
	 */
	public void setDefaultExt(String defaultExt) {
		this.defaultExt = defaultExt;
	}

	/**
	 * @return the defaultThumbnailBytes
	 */
	public byte[] getDefaultThumbnailBytes() {
		return defaultThumbnailBytes;
	}

	/**
	 * @param defaultThumbnailBytes
	 *            the defaultThumbnailBytes to set
	 */
	public void setDefaultThumbnailBytes(byte[] defaultThumbnailBytes) {
		this.defaultThumbnailBytes = defaultThumbnailBytes;
	}

	/**
	 * @return the defaultMetadataCollection
	 */
	public Collection<DefaultMetadata> getDefaultMetadataCollection() {
		return defaultMetadataCollection;
	}

	/**
	 * @param metadata
	 *            the metadata to add to defaults
	 */
	public void addMetadata(DefaultMetadata metadata) {
		this.defaultMetadataCollection.add(metadata);
		// metadata.setParent(this);
	}

	/**
	 * @param metadata
	 *            the metadata to remove from defaults
	 */
	public void removeMetadata(DefaultMetadata metadata) {
		this.defaultMetadataCollection.remove(metadata);
	}

	@Override
	public String toString() {
		return typeName;
	}

}
