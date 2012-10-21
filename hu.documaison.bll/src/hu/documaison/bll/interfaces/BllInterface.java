package hu.documaison.bll.interfaces;

import hu.documaison.data.entities.*;

import java.util.Collection;

public interface BllInterface {
	public Collection<DocumentType> getAllDocumentTypes();
	public Document createDocument(int documentTypeId);
}
