package hu.documaison.indexing;

import java.io.File;
import java.util.Collection;

import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.helper.DocumentFilePointer;

public class IndexingImpl implements IndexingInterface {
	private BllInterface bll = null;
	private String folder;
	private Collection<DocumentFilePointer> lastPointers = null;

	public IndexingImpl(String folder, BllInterface bll)
			throws InvalidParameterException {
		setFolder(folder);
		setBll(bll);
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
		lastPointers = bll.getDocumentPointers("/Users%");
		for (DocumentFilePointer dfp : lastPointers){
			System.out.println(dfp.getDocumentId() + " = " + dfp.getLocation());
			
			File f = new File(dfp.getLocation());
			if (f.exists() == false){
				onDeleted(dfp.getDocumentId());
			}
		}
		
		// file visitor
		this.recursiveRefresh(this.folder);
	}

	private void onDeleted(int documentId){
		System.err.println("DELETED: id = " + documentId);
	}
	
	private void onAdded(String path){
		System.err.println("ADDED: path = " + path);
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

	private void fileRefresh(File f) {
		System.out.println("File found: " + f.getAbsolutePath());
		System.out.println("   last modified: " + f.lastModified());
		
		if (!inPointerList(f.getAbsolutePath())){
			onAdded(f.getAbsolutePath());
		}
	}

	private boolean inPointerList(String absolutePath) {
		for (DocumentFilePointer dfp : this.lastPointers){
			if (dfp.getLocation() != null && dfp.getFile().getAbsolutePath().equals(absolutePath)){
				return true;
			}
		}
		return false;
	}

}
