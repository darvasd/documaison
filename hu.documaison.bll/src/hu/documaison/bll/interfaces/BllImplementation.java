package hu.documaison.bll.interfaces;

import hu.documaison.dal.interfaces.DalInterface;
import hu.documaison.dal.interfaces.DalSingletonProvider;
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
import hu.documaison.data.helper.FileHelper;
import hu.documaison.data.search.SearchExpression;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class BllImplementation implements BllInterface {

	@Override
	public void addTagToDocument(Tag tag, Document document)
			throws InvalidParameterException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.addTagToDocument(tag, document);
	}

	@Override
	public Comment createComment(Document parent)
			throws UnableToCreateException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		Comment ret = dal.createComment();
		ret.setParent(parent);
		return ret;

	}

	@Override
	public DefaultMetadata createDefaultMetadata(DocumentType parent)
			throws UnableToCreateException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		DefaultMetadata ret = dal.createDefaultMetadata();
		ret.setParent(parent);
		return ret;
	}

	@Override
	public Document createDocument(int documentTypeId)
			throws UnknownDocumentTypeException, UnableToCreateException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		DocumentType dtype = dal.getDocumentType(documentTypeId);
		Document document = dal.createDocument(documentTypeId);
		if (dtype.getDefaultMetadataCollection() != null) {
			for (DefaultMetadata dmetadata : dtype
					.getDefaultMetadataCollection()) {
				Metadata metadata = dal.createMetadata();
				metadata.setName(dmetadata.getName());
				metadata.setValue(dmetadata.getValue());
				metadata.setMetadataType(dmetadata.getMetadataType());
				metadata.setParent(document);
				dal.updateMetadata(metadata);
			}
		}

		if (dtype.getDefaultThumbnailBytes() != null) {
			document.setThumbnailBytes(dtype.getDefaultThumbnailBytes().clone());
		}

		dal.updateDocument(document);
		Document savedDoc;
		try {
			savedDoc = dal.getDocument(document.getId());
		} catch (UnknownDocumentException e) {
			// nothing to do here
			// this must not happen
			savedDoc = null;
		}
		return savedDoc;
	}

	@Override
	public DocumentType createDocumentType() throws UnableToCreateException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.createDocumentType();
	}

	@Override
	public Metadata createMetadata(Document parent)
			throws UnableToCreateException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		Metadata ret = dal.createMetadata();
		ret.setParent(parent);
		return ret;
	}

	@Override
	public Tag createTag(String name) throws UnableToCreateException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.createTag(name);
	}

	@Override
	public Collection<DocumentType> getAllDocumentTypes() {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentTypes();
	}

	@Override
	public Document getDocument(int id) throws UnknownDocumentException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocument(id);
	}

	@Override
	public Collection<Document> getDocuments() {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocuments();
	}

	@Override
	public Collection<Document> getDocumentsByTag(Tag tag) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentsByTag(tag);
	}

	@Override
	public DocumentType getDocumentType(int id)
			throws UnknownDocumentTypeException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentType(id);
	}

	@Override
	public Collection<DocumentType> getDocumentTypes() {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentTypes();
	}

	@Override
	public Tag getTag(int id) throws UnknownTagException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getTag(id);
	}

	@Override
	public Tag getTag(String name) throws UnknownTagException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getTag(name);
	}

	@Override
	public Collection<Tag> getTags() {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getTags();
	}

	@Override
	public void removeComment(int id) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeComment(id);
	}

	@Override
	public void removeDefaultMetadata(int id) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeDefaultMetadata(id);
	}

	@Override
	public void removeDocument(int id) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeDocument(id);
	}

	@Override
	public void removeDocumentType(int id) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeDocumentType(id);
	}

	@Override
	public void removeMetadata(int id) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeMetadata(id);
	}

	@Override
	public void removeTag(int id) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeTag(id);
	}

	@Override
	public void removeTagFromDocument(Tag tag, Document document)
			throws InvalidParameterException {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.removeTagFromDocument(tag, document);
	}

	@Override
	public Collection<Document> searchDocuments(SearchExpression expr) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.searchDocuments(expr);
	}

	@Override
	public void updateComment(Comment comment) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateComment(comment);
	}

	@Override
	public void updateDefaultMetadata(DefaultMetadata metadata) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateDefaultMetadata(metadata);
	}

	@Override
	public void updateDocument(Document document) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateDocument(document);
	}

	@Override
	public void updateDocumentType(DocumentType documentType) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateDocumentType(documentType);
	}

	@Override
	public void updateMetadata(Metadata metadata) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateMetadata(metadata);
	}

	@Override
	public void updateTag(Tag tag) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateTag(tag);
	}

	@Override
	public Collection<Document> getDocumentsByTags(java.util.List<Tag> tags) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentsByTags(tags);
	}

	@Override
	public Collection<DocumentFilePointer> getDocumentPointers(
			String locationFilter) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentPointers(locationFilter);
	}

	@Override
	public void moveDocument(Document document, String newLocation)
			throws InvalidParameterException, UnknownDocumentException,
			IOException {
		File target = new File(newLocation);
		if (target == null || target.exists()) {
			throw new InvalidParameterException("newLocation");
		}
		if (document == null) {
			throw new UnknownDocumentException(0);
		}

		File oldFile;
		try {
			oldFile = new File(document.getLocation());
		} catch (NullPointerException npe) {
			throw new InvalidParameterException("document");
		}

		// the simplest solution:
		if (!oldFile.renameTo(target)) {
			// if the renameTo was unsuccessful due to platform limitations ...
			FileHelper.copy(oldFile, target);
			oldFile.delete();
		}

		document.setLocation(target.getAbsolutePath());

		DalInterface dal = DalSingletonProvider.getDalImplementation();
		dal.updateDocument(document);

	}

	@Override
	public boolean deleteAndRemoveDocument(Document document)
			throws InvalidParameterException, UnknownDocumentException {
		if (document == null) {
			throw new UnknownDocumentException(0);
		}

		File docFile = FileHelper.createFileObject(document.getLocation());
		if (docFile == null || !docFile.exists()) {
			throw new InvalidParameterException("document.location");
		}

		if (docFile.delete()) {
			DalInterface dal = DalSingletonProvider.getDalImplementation();
			dal.removeDocument(document.getId());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public DocumentType getDocumentTypeForExtension(String extension) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentTypeForExtension(extension);
	}

	@Override
	public Collection<Document> searchDocumentsFreeText(String textFragment) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.searchDocumentsFreeText(textFragment);
	}

	@Override
	public Collection<Metadata> getAllMetadata() {
		// TODO: Only dummy implementation, shoud be replaced

		return getDocuments().iterator().next().getMetadataCollection();

	}
}
