package hu.documaison.dal.interfaces;

import hu.documaison.data.entities.*;

import java.util.Collection;

public interface CoreInterface {
	// documents
	public Collection<Document> getDocuments();
	public Collection<Document> getDocuments(Tag tag);
	public Document getDocument(int id);
	public Document createDocument(int typeId);
	public void addDocument(Document document);
	public void removeDocument(int id);
	public void updateDocument(Document document);
	
	// tags
	public void addTag(Tag tag);
	public void updateTag(Tag tag);
	public Collection<Tag> getTags();
	public Tag getTag(int id);
	public Tag getTag(String name);
	public void removeTag(int id);
	
	// documentTypes
	public void addDocumentType(DocumentType documentType);
	public DocumentType createDocumentType();
	public void removeDocumentType(int id);
	public void updateDocumentType(DocumentType documentType);
	public Collection<DocumentType> getDocumentTypes();
	public DocumentType getDocumentType(int id);
	
	// comments are accessible from documents
	// metadata is accessible from documents or documenttypes
	// 
}
