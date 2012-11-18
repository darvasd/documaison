package hu.documaison.indexing;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.data.exceptions.UnknownDocumentTypeException;
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
		for (DocumentFilePointer dfp : lastPointers) {
			System.out.println(dfp.getDocumentId() + " = " + dfp.getLocation());

			File f = new File(dfp.getLocation());
			if (f.exists() == false) {
				onDeleted(dfp.getDocumentId());
			}
		}

		// file visitor
		this.recursiveRefresh(this.folder);
	}

	private void onDeleted(int documentId) {
		System.err.println("DELETED: id = " + documentId);
	}

	private String fileExtension(Path path) {
		int lastDot = path.toString().lastIndexOf('.');
		return path.toString().substring(lastDot + 1);
	}

	private void onAdded(Path path) {
		System.err.println("ADD: path = " + path);
		String extension = fileExtension(path);

		// find corresponding document type
		DocumentType dt = bll.getDocumentTypeForExtension(extension);
		if (dt == null) {
			onAddingError(path, "DocumentType not found for extension: " + extension);
			return;
		}
		System.err.println("Found type for " + extension + " ext: "
				+ dt.getTypeName());

		// create new document
		Document newDoc;
		try {
			newDoc = bll.createDocument(dt.getId());
			newDoc.setLocation(path.toString());
			newDoc.setDateAdded(new Date());
			bll.updateDocument(newDoc);

			Metadata md1 = bll.createMetadata(newDoc);
			md1.setName("autocreated");
			md1.setValue("true");
			bll.updateMetadata(md1);
		} catch (Exception e) {
			onAddingError(path, null);
			return;
		}
	}

	private void onAddingError(Path path, String message) {
		if (message == null) {
			message = "N/A";
		}
		System.err
				.println("Adding error: " + message + " @ " + path.toString());
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

	// Necessary operations on f file.
	private void fileRefresh(File f) {
		System.out.println("File found: " + f.getAbsolutePath());
		if (!inPointerList(f.getAbsolutePath())) {
			onAdded(f.toPath());
		}

//		try {
//			System.out.println("   last modified: " + f.lastModified());
//			Map<String, Object> map = Files.readAttributes(f.toPath(), "*",
//					LinkOption.NOFOLLOW_LINKS);
//
//			for (Map.Entry<String, Object> entry : map.entrySet()) {
//				System.out.println("   " + entry.getKey() + " = "
//						+ entry.getValue());
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private boolean inPointerList(String absolutePath) {
		for (DocumentFilePointer dfp : this.lastPointers) {
			if (dfp.getLocation() != null
					&& dfp.getFile().getAbsolutePath().equals(absolutePath)) {
				return true;
			}
		}
		return false;
	}

}
