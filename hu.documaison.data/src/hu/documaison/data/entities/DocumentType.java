package hu.documaison.data.entities;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DocumentTypes")
public class DocumentType extends DatabaseObject {
	@DatabaseField
	private String typeName;

	@DatabaseField
	private String defaultExt;

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] defaultThumbnailBytes;

	@ForeignCollectionField(eager = true, foreignFieldName = DefaultMetadata.PARENT)
	private ForeignCollection<DefaultMetadata> defaultMetadataCollection; 

	public DocumentType()
	{
		// ORMLite needs a no-arg constructor 
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
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
	 * @param defaultExt the defaultExt to set
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
	 * @param defaultThumbnailBytes the defaultThumbnailBytes to set
	 */
	public void setDefaultThumbnailBytes(byte[] defaultThumbnailBytes) {
		this.defaultThumbnailBytes = defaultThumbnailBytes;
	}

	/**
	 * @return the defaultMetadataCollection
	 */
	public ForeignCollection<DefaultMetadata> getDefaultMetadataCollection() {
		return defaultMetadataCollection;
	}

	/**
	 * @param metadata the metadata to add to defaults
	 */
	public void addMetadata(DefaultMetadata metadata) {
		this.defaultMetadataCollection.add(metadata);
	}

	/**
	 * @param metadata the metadata to remove from defaults
	 */
	public void removeMetadata(DefaultMetadata metadata) {
		this.defaultMetadataCollection.remove(metadata);
	}
}
