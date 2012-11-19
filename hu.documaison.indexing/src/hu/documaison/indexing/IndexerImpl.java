package hu.documaison.indexing;

import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.helper.DocumentFilePointer;
import hu.documaison.data.helper.FileHelper;
import hu.documaison.indexing.interceptor.IndexerInterceptor;
import hu.documaison.indexing.interceptor.IndexerInterceptorDispatcher;

import java.io.File;
import java.util.Collection;
import java.util.Date;

class IndexerImpl implements IndexerInterface {
	private BllInterface bll = null;
	private String folder;
	private String currentComputerId;
	private Collection<DocumentFilePointer> lastPointers = null;
	private IndexerInterceptorDispatcher dispatcher = null;

	public IndexerImpl(String folder, String currentComputerId, BllInterface bll)
			throws InvalidParameterException {
		setFolder(folder);
		setCurrentComputerId(currentComputerId);
		setBll(bll);
	}
	
	private void setCurrentComputerId(String currentComputerId)
			throws InvalidParameterException {
		if (currentComputerId != null) {
			this.currentComputerId = currentComputerId;
		} else {
			throw new InvalidParameterException("currentComputerId");
		}
	}

	private void setFolder(String folder) throws InvalidFolderException {
		File folderFile = new File(folder);
		if (!folderFile.exists() || !folderFile.isDirectory()) {
			throw new InvalidFolderException(folder);
		}
		this.folder = folder;
	}

	private void setBll(BllInterface bll) throws InvalidParameterException {
		if (bll == null) {
			throw new InvalidParameterException("bll");
		}

		this.bll = bll;
	}

	@Override
	public void refresh() {
		// check for deletions
		lastPointers = bll.getDocumentPointers(this.folder + "%");
		for (DocumentFilePointer dfp : lastPointers) {
			//System.out.println(dfp.getDocumentId() + " = " + dfp.getLocation());

			File f = new File(dfp.getLocation());
			if (f.exists() == false) {
				onDeleted(dfp.getDocumentId());
			}
		}

		// check for new files
		// (file "visitor")
		this.recursiveRefresh(this.folder);
		
		// check for modifications
		// not necessary at the moment
	}
	
	private void recursiveRefresh(String rootDirPath) {
		System.out.println("Indexing directory: " + rootDirPath);

		File rootDir = new File(rootDirPath);
		File[] fileList = rootDir.listFiles();

		for (File f : fileList) {
			if (f.isDirectory()) {
				recursiveRefresh(f.getAbsolutePath());
			} else if (f.isFile()) {
				fileRefresh(f);
			} else {
				System.err.println("Hát te ki vagy és mit eszel?");
			}
		}
	}

	// Necessary operations on file file.
	private void fileRefresh(File file) {
		System.out.println("File found: " + file.getAbsolutePath());
		if (!inPointerList(file.getAbsolutePath())) {
			onAdded(file);
		} else {
			System.out.println(file.getAbsolutePath() + " is already in database.");
		}

		// try {
		// System.out.println("   last modified: " + f.lastModified());
		// Map<String, Object> map = Files.readAttributes(f.toPath(), "*",
		// LinkOption.NOFOLLOW_LINKS);
		//
		// for (Map.Entry<String, Object> entry : map.entrySet()) {
		// System.out.println("   " + entry.getKey() + " = "
		// + entry.getValue());
		// }
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void onDeleted(int documentId) {
		// best effort (fail silent)
		System.err.println("REMOVE: id = " + documentId);

		Document document;
		try {
			document = bll.getDocument(documentId);
		} catch (UnknownDocumentException e) {
			return;
		}
		if (document == null) {
			return;
		}

		if (!document.getCreatorComputerId()
				.equalsIgnoreCase(currentComputerId)) {
			// This document was created on another computer.
			// This instance doesn't have the right to delete it.
			return;
		} else {
			if (dispatcher != null && dispatcher.getInterceptors() != null){
				for (IndexerInterceptor interceptor : dispatcher.getInterceptors()){
					interceptor.beforeDocumentDeleted(document);
				}
			}
			
			bll.removeDocument(documentId);
			// TODO cascade delete check!
			
			if (dispatcher != null && dispatcher.getInterceptors() != null){
				for (IndexerInterceptor interceptor : dispatcher.getInterceptors()){
					interceptor.onDocumentDeleted(documentId);
				}
			}
		}

	}

	private void onAdded(File file) {
		if (dispatcher != null && dispatcher.getInterceptors() != null){
			for (IndexerInterceptor interceptor : dispatcher.getInterceptors()){
				interceptor.beforeDocumentAdded(file.toString());
			}
		}
		
		System.err.println("ADD: path = " + file);
		String extension = FileHelper.fileExtension(file.getAbsolutePath());

		// find corresponding document type
		DocumentType dt = bll.getDocumentTypeForExtension(extension);
		if (dt == null) {
			onAddingError(file, "DocumentType not found for extension: "
					+ extension);
			return;
		}
		System.err.println("Found type for " + extension + " ext: "
				+ dt.getTypeName());

		// create new document
		Document newDoc;
		try {
			newDoc = bll.createDocument(dt.getId());
			newDoc.setLocation(file.toString());
			newDoc.setCreatorComputerId(this.currentComputerId);
			newDoc.setDateAdded(new Date());
			bll.updateDocument(newDoc);

			Metadata md1 = bll.createMetadata(newDoc);
			md1.setName("autocreated");
			md1.setValue("true");
			bll.updateMetadata(md1);
		} catch (Exception e) {
			onAddingError(file, null);
			return;
		}
		
		if (dispatcher != null && dispatcher.getInterceptors() != null){
			for (IndexerInterceptor interceptor : dispatcher.getInterceptors()){
				interceptor.onDocumentAdded(newDoc);
			}
		}
	}

	private void onAddingError(File file, String message) {
		if (message == null) {
			message = "N/A";
		}
		System.err
				.println("Adding error: " + message + " @ " + file.toString());
		
		if (dispatcher != null && dispatcher.getInterceptors() != null){
			for (IndexerInterceptor interceptor : dispatcher.getInterceptors()){
				interceptor.onDocumentAddingError(file.toString());
			}
		}
	}

	private boolean inPointerList(String absolutePath) {
		for (DocumentFilePointer dfp : this.lastPointers) {
			if (dfp.getLocation() != null
					&& dfp.getFile().getAbsolutePath().equals(absolutePath)) {
				return true;
			}
			//System.out.println(dfp.getFile().getAbsolutePath()+ "!=" + absolutePath);
		}
		return false;
	}

	@Override
	public void setDispatcher(IndexerInterceptorDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
