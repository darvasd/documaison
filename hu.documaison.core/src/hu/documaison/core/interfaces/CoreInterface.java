package hu.documaison.core.interfaces;

import hu.documaison.core.databaseobjects.*;

import java.util.Collection;

public interface CoreInterface {
	// documents
	public Collection<Document> getDocuments();
	public Document getDocument(int id);
	public void addDocument(Document document);
	public void removeDocument(Document document);
	public void updateDocument(Document document);
	
	// tags
	public void addTag(Tag tag);
	public void updateTag(Tag tag);
	public Collection<Tag> getTags();
	public Tag getTag(int id);
	public Tag getTag(String name);
	public void removeTag(Tag tag);
	
	// documentTypes
	public void addDocumentType(DocumentType documentType);
	public void removeDocumentType(DocumentType documentType);
	public void updateDocumentType(DocumentType documentType);
	public Collection<DocumentType> getDocumentTypes();
	
	// comments are accessible from documents
	// metadata is accessible from documents or documenttypes
	// 
}
