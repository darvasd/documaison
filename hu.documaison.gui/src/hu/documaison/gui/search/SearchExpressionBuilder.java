package hu.documaison.gui.search;

import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.search.BoolOperator;
import hu.documaison.data.search.Expression;
import hu.documaison.data.search.ExpressionTwoOp;
import hu.documaison.data.search.Operator;
import hu.documaison.data.search.SearchExpression;

import java.util.Collection;

public class SearchExpressionBuilder {

	public static SearchExpression createExpression(
			Collection<AdvancedSearchField> fields, BoolOperator operator) {
		if (fields.isEmpty()) {
			throw new IllegalArgumentException("Empty list of searchfields.");
		}
		SearchExpression expression = new SearchExpression();

		for (AdvancedSearchField field : fields) {

			Operator op = field.getOperator();
			if (op != Operator.between) {
				try {
					expression.AddExpression(new Expression(field
							.getSelectedMetadataName(), op, field.getValue1()));
				} catch (InvalidParameterException e) {
					new IllegalArgumentException(
							"Failed to build search expression");
				}
			} else {
				try {
					expression.AddExpression(new ExpressionTwoOp(field
							.getSelectedMetadataName(), op, field.getValue1(),
							field.getValue2()));
				} catch (InvalidParameterException e) {
					new IllegalArgumentException(
							"Failed to build search expression");
				}
			}

		}
		expression.setBoolOperator(operator);

		return expression;
	}
}
