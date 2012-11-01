package hu.documaison.data.exceptions;

public class UnknownDocumentTypeException extends Exception {
	private int documentTypeId;
	
	public UnknownDocumentTypeException(int documentTypeId)
	{
		this.documentTypeId = documentTypeId;
	}
	
	public int getDocumentTypeId(){
		return this.documentTypeId;
	}
}
