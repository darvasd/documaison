package hu.documaison.data.exceptions;

public class UnknownEntityException extends Exception {
	private static final long serialVersionUID = 2679697024745823208L;

	private int entityId;
	private String entityType;

	public UnknownEntityException(String entityType, int entityId) {
		this.entityType = entityType;
		this.entityId = entityId;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public String getEntityType() {
		return this.entityType;
	}
}
