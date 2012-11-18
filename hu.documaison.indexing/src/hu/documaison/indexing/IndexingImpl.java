package hu.documaison.indexing;

import java.io.File;

import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;

public class IndexingImpl implements IndexingInterface {
	private BllInterface bll = null;
	private String folder;

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
		// file visitor
		this.recursiveRefresh(this.folder);
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
	}
}
