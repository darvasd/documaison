package hu.documaison.data.exceptions;

public class InvalidParameterException extends Exception {
	private static final long serialVersionUID = -3314686054308955987L;
	
	private String parameter = "";
	
	public InvalidParameterException(String parameter){
		this.parameter = parameter;
	}
	
	public String getParameter(){
		return this.parameter;
	}
}
