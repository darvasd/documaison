package hu.documaison.data.exceptions;

public class UnknownDocumentException extends UnknownEntityException {
	private static final long serialVersionUID = 63159006339462200L;

	public UnknownDocumentException(int documentId)
	{
		super("Document", documentId);
	}
	
	public int getDocumentId(){
		return this.getEntityId();
	}
}
