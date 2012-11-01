package hu.documaison.bll.interfaces;

import hu.documaison.data.entities.*;
import hu.documaison.data.exceptions.UnknownDocumentTypeException;
import hu.documaison.data.search.SearchExpression;

import java.util.Collection;

public interface BllInterface {
	public void addTagToDocument(Tag tag, Document document);

	public Comment createComment(Document parent);

	public DefaultMetadata createDefaultMetadata(DocumentType parent);

	public Document createDocument(int documentTypeId) throws UnknownDocumentTypeException;

	public DocumentType createDocumentType();

	public Metadata createMetadata(Document parent);

	public Tag createTag(String name);

	public Collection<DocumentType> getAllDocumentTypes();

	public Document getDocument(int id);

	public Collection<Document> getDocuments();

	public Collection<Document> getDocuments(Tag tag);

	public DocumentType getDocumentType(int id);

	public Collection<DocumentType> getDocumentTypes();

	public Tag getTag(int id);

	public Tag getTag(String name);

	public Collection<Tag> getTags();

	public void removeComment(int id);

	public void removeDefaultMetadata(int id);

	public void removeDocument(int id);

	public void removeDocumentType(int id);

	public void removeMetadata(int id);

	public void removeTag(int id);

	public void removeTagFromDocument(Tag tag, Document document);

	public Collection<Document> searchDocuments(SearchExpression expr);

	public void updateComment(Comment comment);

	public void updateDefaultMetadata(DefaultMetadata metadata);

	public void updateDocument(Document document);

	public void updateDocumentType(DocumentType documentType);

	public void updateMetadata(Metadata metadata);

	public void updateTag(Tag tag);
}
