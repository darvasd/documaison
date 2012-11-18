package hu.documaison.data.exceptions;

public class InvalidFolderException extends InvalidParameterException {
	private static final long serialVersionUID = -5122214748358627957L;
	
	private String folderPath;

	public InvalidFolderException(String folderPath) {
		super("folder");
		this.folderPath = folderPath;
	}

	public String getFolderPath() {
		return this.folderPath;
	}
}
