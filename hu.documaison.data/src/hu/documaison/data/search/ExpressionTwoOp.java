package hu.documaison.data.search;

import hu.documaison.data.exceptions.InvalidParameterException;

public class ExpressionTwoOp extends Expression {
	private String value2;

	protected ExpressionTwoOp(String metadataName, Operator operator,
			String value1, String value2) throws InvalidParameterException {
		super(metadataName, value1);

		if (operator != Operator.between) {
			throw new InvalidParameterException("operator");
		}

		this.value2 = value2;
		this.operator = operator;
	}

	@Override
	public String getValue(int idx) throws InvalidParameterException {
		if (idx == 1) {
			return this.value2;
		} else {
			return super.getValue(idx);
		}
	}

	@Override
	public int getValueCount() {
		return 2;
	}

}
