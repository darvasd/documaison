package hu.documaison.core.viewmodel;

import hu.documaison.core.databaseobjects.DocumentType;
import hu.documaison.core.databaseobjects.Metadata;

import java.util.ArrayList;
import java.util.HashMap;


public class DocumentTypeViewModel {
	private int id;
	private String typeName;
	private String defaultExt;
	private byte[] defaultThumbnailBytes;
	private HashMap<String, String> defaultMetadataCollection;
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
	public HashMap<String, String> getDefaultMetadataCollection() {
		return defaultMetadataCollection;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void addDefaultMetadata(String name, String value) {
		this.defaultMetadataCollection.put(name, value);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	} 
	
	public static DocumentTypeViewModel createFromDatabaseEntity(DocumentType documentType)
	{
		DocumentTypeViewModel ret = new DocumentTypeViewModel();
		ret.id = documentType.getId();
		ret.typeName = documentType.getTypeName();
		ret.defaultExt = documentType.getDefaultExt();
		ret.defaultThumbnailBytes = documentType.getDefaultThumbnailBytes();
		ret.defaultMetadataCollection = new HashMap<String, String>();
		for (Metadata md : documentType.getDefaultMetadataCollection())
		{
			ret.defaultMetadataCollection.put(md.getName(), md.getValue());
		}
		return ret;
	}
}
