package hu.documaison.data.exceptions;

public class InvalidPropertyException extends Exception {
	private static final long serialVersionUID = 4175338701821233190L;

	public InvalidPropertyException(String message){
		super(message);
	}
}
