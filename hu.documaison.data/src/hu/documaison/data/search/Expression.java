package hu.documaison.data.search;

import hu.documaison.data.exceptions.InvalidParameterException;

public class Expression {
	private String metadataName;
	protected Operator operator = Operator.eq;
	protected String value;
	
	public Expression(String metadataName, Operator operator, String value) throws InvalidParameterException {
		super();
		
		if (operator == Operator.between){
			throw new InvalidParameterException("operator");
		}
		
		this.metadataName = metadataName;
		this.operator = operator;
		this.value = value;
	}
	
	protected Expression(String metadataName, String value) {
		super();
		
		this.metadataName = metadataName;
		this.value = value;
	}
	
	public String getMetadataName() {
		return metadataName;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getValue(int idx) throws InvalidParameterException{
		if (idx == 0){
			return this.value;
		}else{
			throw new InvalidParameterException("idx");
		}
	}
	
	public int getValueCount(){
		return 1;
	}
}
