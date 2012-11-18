package hu.documaison.data.exceptions;

public class UnknownDocumentTypeException extends UnknownEntityException {
	private static final long serialVersionUID = -8680298761895918903L;

	public UnknownDocumentTypeException(int documentTypeId)
	{
		super("DocumentType", documentTypeId);
	}
	
	public int getDocumentTypeId(){
		return this.getEntityId();
	}
}
