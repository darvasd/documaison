package hu.documaison.gui.search;

import hu.documaison.data.search.BoolOperator;
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

		}

		return expression;
	}

}
