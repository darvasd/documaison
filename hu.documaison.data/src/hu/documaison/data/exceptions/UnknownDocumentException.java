package hu.documaison.data.exceptions;

public class UnknownDocumentException extends UnknownEntityException {
	public UnknownDocumentException(int documentId)
	{
		super("Document", documentId);
	}
	
	public int getDocumentId(){
		return this.getEntityId();
	}
}
