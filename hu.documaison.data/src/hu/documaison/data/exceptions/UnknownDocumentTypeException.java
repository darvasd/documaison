package hu.documaison.data.exceptions;

import hu.documaison.data.entities.DocumentType;

public class UnknownDocumentTypeException extends UnknownEntityException {
	public UnknownDocumentTypeException(int documentTypeId)
	{
		super("DocumentType", documentTypeId);
	}
	
	public int getDocumentTypeId(){
		return this.getEntityId();
	}
}
