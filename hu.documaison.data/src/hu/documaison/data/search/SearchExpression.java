package hu.documaison.data.search;

import java.util.ArrayList;
import java.util.Collection;

public class SearchExpression {
	private BoolOperator boolOperator = BoolOperator.and;
	private Collection<Expression> expressions = new ArrayList<Expression>();
	
	public void AddExpression(Expression expression)
	{
		this.expressions.add(expression);
	}
	
	public BoolOperator getBoolOperator() {
		return boolOperator;
	}
	
	public Collection<Expression> getExpressions() {
		return expressions;
	}
	
	public void setBoolOperator(BoolOperator boolOperator) {
		this.boolOperator = boolOperator;
	}
}
