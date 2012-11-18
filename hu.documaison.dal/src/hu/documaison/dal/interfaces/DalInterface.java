package hu.documaison.dal.interfaces;

import hu.documaison.data.entities.*;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.exceptions.UnknownDocumentTypeException;
import hu.documaison.data.exceptions.UnknownTagException;
import hu.documaison.data.helper.DocumentFilePointer;
import hu.documaison.data.search.SearchExpression;

import java.util.Collection;

public interface DalInterface {
	// document
	public Collection<Document> getDocuments();
	public Collection<Document> getDocumentsByTag(Tag tag);
	public Collection<Document> getDocumentsByTags(java.util.List<Tag> tags);
	public Document getDocument(int id) throws UnknownDocumentException;
	public Document createDocument(int typeId) throws UnknownDocumentTypeException, UnableToCreateException;
	public void removeDocument(int id);
	public void updateDocument(Document document);
	
	// tag
	public Tag createTag(String name) throws UnableToCreateException;
	public void updateTag(Tag tag);
	public Collection<Tag> getTags();
	public Tag getTag(int id) throws UnknownTagException;
	public Tag getTag(String name) throws UnknownTagException;
	public void removeTag(int id);
	
	public void addTagToDocument(Tag tag, Document document) throws InvalidParameterException;
	public void removeTagFromDocument(Tag tag, Document document) throws InvalidParameterException;
	
	// documentType
	public DocumentType createDocumentType() throws UnableToCreateException;
	public void removeDocumentType(int id);
	public void updateDocumentType(DocumentType documentType);
	public Collection<DocumentType> getDocumentTypes();
	public DocumentType getDocumentType(int id) throws UnknownDocumentTypeException;
	
	
	// metadata
	public Metadata createMetadata() throws UnableToCreateException;
	public void updateMetadata(Metadata metadata);
	public void removeMetadata(int id);
	
	// default metadata
	public DefaultMetadata createDefaultMetadata() throws UnableToCreateException;
	public void updateDefaultMetadata(DefaultMetadata metadata);
	public void removeDefaultMetadata(int id);
	
	// comment
	public Comment createComment() throws UnableToCreateException;
	public void updateComment(Comment comment);
	public void removeComment(int id);
	
	// search
	public Collection<Document> searchDocuments(SearchExpression expr);
	
	// for indexing
	public Collection<DocumentFilePointer> getDocumentPointers(String locationFilter);
}
