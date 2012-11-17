package hu.documaison.data.exceptions;

public class UnableToCreateException extends Exception {
	private String entityType;

	public UnableToCreateException(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityType() {
		return this.entityType;
	}
}
