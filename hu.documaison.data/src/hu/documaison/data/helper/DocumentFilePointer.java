package hu.documaison.data.helper;

import hu.documaison.data.entities.Document;

public class DocumentFilePointer {
	private int documentId;
	private String location;

	public int getDocumentId() {
		return documentId;
	}

	public String getLocation() {
		return location;
	}

	public DocumentFilePointer(String path, int documentId) {
		this.location = path;
		this.documentId = documentId;
	}

	public static DocumentFilePointer createInstance(Document document) {
		return new DocumentFilePointer(document.getLocation(), document.getId());
	}
}
