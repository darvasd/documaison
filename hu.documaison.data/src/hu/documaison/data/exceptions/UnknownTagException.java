package hu.documaison.data.exceptions;

public class UnknownTagException extends UnknownEntityException {
	public UnknownTagException(int tagId)
	{
		super("Tag", tagId);
	}
	
	public int getDocumentId(){
		return this.getEntityId();
	}
}
