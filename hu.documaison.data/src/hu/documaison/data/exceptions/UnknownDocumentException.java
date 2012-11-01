package hu.documaison.data.exceptions;

public class UnknownDocumentException extends Exception {
	private int documentId;
	
	public UnknownDocumentException(int documentId)
	{
		this.documentId = documentId;
	}
	
	public int getDocumentId(){
		return this.documentId;
	}
}
