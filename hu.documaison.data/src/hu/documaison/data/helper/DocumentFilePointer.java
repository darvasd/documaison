package hu.documaison.data.helper;

import java.io.File;

import hu.documaison.data.entities.Document;

public class DocumentFilePointer {
	private int documentId;
	private String location;
	private File file = null;

	public int getDocumentId() {
		return documentId;
	}

	public String getLocation() {
		return location;
	}
	
	public File getFile(){
		if (this.file == null){
			this.file = new File(this.location);
		}
		return this.file;
	}

	public DocumentFilePointer(String path, int documentId) {
		this.location = path;
		this.documentId = documentId;
	}

	public static DocumentFilePointer createInstance(Document document) {
		return new DocumentFilePointer(document.getLocation(), document.getId());
	}
}
