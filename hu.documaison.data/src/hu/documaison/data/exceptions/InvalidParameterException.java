package hu.documaison.data.exceptions;

public class InvalidParameterException extends Exception {
	private String parameter = "";
	
	public InvalidParameterException(String parameter){
		this.parameter = parameter;
	}
	
	public String getParameter(){
		return this.parameter;
	}
}
