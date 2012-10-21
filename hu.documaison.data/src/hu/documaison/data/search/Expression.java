package hu.documaison.data.search;

public class Expression {
	private String metadataName;
	private Operator operator = Operator.eq;
	private String value;
	
	public Expression(String metadataName, Operator operator, String value) {
		super();
		this.metadataName = metadataName;
		this.operator = operator;
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
}
