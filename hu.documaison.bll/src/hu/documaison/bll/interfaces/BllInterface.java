package hu.documaison.bll.interfaces;

import hu.documaison.data.entities.Comment;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.entities.Tag;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.exceptions.UnknownDocumentTypeException;
import hu.documaison.data.exceptions.UnknownTagException;
import hu.documaison.data.helper.DocumentFilePointer;
import hu.documaison.data.helper.MetadataNameTypePair;
import hu.documaison.data.search.SearchExpression;

import java.io.IOException;
import java.util.Collection;

public interface BllInterface {
	public void addTagToDocument(Tag tag, Document document)
			throws InvalidParameterException;

	public void copyDocument(Document document, String newLocation)
			throws InvalidParameterException, UnknownDocumentException,
			IOException;

	public Comment createComment(Document parent)
			throws UnableToCreateException;

	public DefaultMetadata createDefaultMetadata(DocumentType parent)
			throws UnableToCreateException;

	public Document createDocument(int documentTypeId)
			throws UnknownDocumentTypeException, UnableToCreateException;

	public DocumentType createDocumentType() throws UnableToCreateException;

	public Metadata createMetadata(Document parent)
			throws UnableToCreateException;

	public Tag createTag(String name) throws UnableToCreateException;

	public boolean deleteAndRemoveDocument(Document document)
			throws InvalidParameterException, UnknownDocumentException;

	public Collection<DocumentType> getAllDocumentTypes();

	public Collection<MetadataNameTypePair> getAllMetadataKeys();

	public Document getDocument(int id) throws UnknownDocumentException;

	public Collection<DocumentFilePointer> getDocumentPointers(
			String locationFilter);

	public Collection<Document> getDocuments();

	public Collection<Document> getDocumentsByTag(Tag tag);

	public Collection<Document> getDocumentsByTags(java.util.List<Tag> tags);

	public DocumentType getDocumentType(int id)
			throws UnknownDocumentTypeException;

	public DocumentType getDocumentTypeForExtension(String extension);

	public Collection<DocumentType> getDocumentTypes();

	public Tag getTag(int id) throws UnknownTagException;

	public Tag getTag(String name) throws UnknownTagException;

	public Collection<Tag> getTags();

	public void moveDocument(Document document, String newLocation)
			throws InvalidParameterException, UnknownDocumentException,
			IOException;

	public void removeComment(int id);

	public void removeDefaultMetadata(int id);

	public void removeDocument(int id);

	public void removeDocumentType(int id);

	public void removeMetadata(int id);

	public void removeTag(int id);

	public void removeTagFromDocument(Tag tag, Document document)
			throws InvalidParameterException;

	public Collection<Document> searchDocuments(SearchExpression expr);

	public Collection<Document> searchDocumentsFreeText(String textFragment);

	public void updateComment(Comment comment);

	public void updateDefaultMetadata(DefaultMetadata metadata);

	public void updateDocument(Document document);

	public void updateDocumentType(DocumentType documentType);

	public void updateMetadata(Metadata metadata);

	public void updateTag(Tag tag);

	public Document changeDocumentType(Document doc, DocumentType type)
			throws UnknownDocumentException, UnknownDocumentTypeException,
			UnableToCreateException;
}
