package hu.documaison.data.exceptions;

public class UnableToCreateException extends Exception {
	private static final long serialVersionUID = -6739226367377974302L;
	
	private String entityType;

	public UnableToCreateException(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityType() {
		return this.entityType;
	}
}
