package hu.documaison.bll.interfaces;

import hu.documaison.dal.interfaces.DalInterface;
import hu.documaison.dal.interfaces.DalSingletonProvider;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;

import java.util.Collection;

public class BllImplementation implements BllInterface {

	@Override
	public Collection<DocumentType> getAllDocumentTypes() {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		return dal.getDocumentTypes();
	}

	@Override
	public Document createDocument(int documentTypeId) {
		DalInterface dal = DalSingletonProvider.getDalImplementation();
		DocumentType dtype = dal.getDocumentType(documentTypeId);
		Document document = dal.createDocument(documentTypeId);
		for (DefaultMetadata dmetadata : dtype.getDefaultMetadataCollection())
		{
			Metadata metadata = dal.createMetadata();
			metadata.setName(dmetadata.getName());
			metadata.setValue(dmetadata.getValue());
			metadata.setParent(document);
			dal.updateMetadata(metadata);
		}
		return document;
	}

}
