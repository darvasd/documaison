package hu.documaison.data.exceptions;

public class UnknownTagException extends UnknownEntityException {
	private static final long serialVersionUID = 8544285277911860917L;

	public UnknownTagException(int tagId)
	{
		super("Tag", tagId);
	}
	
	public int getDocumentId(){
		return this.getEntityId();
	}
}
