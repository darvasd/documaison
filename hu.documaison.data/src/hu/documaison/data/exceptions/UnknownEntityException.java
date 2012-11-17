package hu.documaison.data.exceptions;

import hu.documaison.data.entities.DatabaseObject;

public class UnknownEntityException extends Exception {
	private int entityId;
	private String entityType;
	
	public UnknownEntityException(String entityType, int entityId)
	{
		this.entityType = entityType;
		this.entityId = entityId;
	}
	
	public int getEntityId(){
		return this.entityId;
	}
	
	public String getEntityType(){
		return this.entityType;
	}
}
