package hu.documaison.dal.interfaces;

import hu.documaison.data.entities.*;
import hu.documaison.data.search.SearchExpression;

import java.util.Collection;

public interface DalInterface {
	// document
	public Collection<Document> getDocuments();
	public Collection<Document> getDocuments(Tag tag);
	public Document getDocument(int id);
	public Document createDocument(int typeId);
	public void removeDocument(int id);
	public void updateDocument(Document document);
	
	// tag
	public Tag createTag();
	public void updateTag(Tag tag);
	public Collection<Tag> getTags();
	public Tag getTag(int id);
	public Tag getTag(String name);
	public void removeTag(int id);
	
	// documentType
	public DocumentType createDocumentType();
	public void removeDocumentType(int id);
	public void updateDocumentType(DocumentType documentType);
	public Collection<DocumentType> getDocumentTypes();
	public DocumentType getDocumentType(int id);
	
	
	// metadata
	public Metadata createMetadata();
	public void updateMetadata(Metadata metadata);
	public void removeMetadata(int id);
	
	// default metadata
	public DefaultMetadata createDefaultMetadata();
	public void updateDefaultMetadata(DefaultMetadata metadata);
	public void removeDefaultMetadata(int id);
	
	// comment
	public Comment createComment();
	public void updateComment(Comment comment);
	public void removeComment(int id);
	
	// search
	public Collection<Document> searchDocuments(SearchExpression expr);
}
