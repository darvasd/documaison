package hu.documaison.test.data.search;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import hu.documaison.data.search.BoolOperator;
import hu.documaison.data.search.Expression;
import hu.documaison.data.search.SearchExpression;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class SearchExpressionTest {
	SearchExpression expression;
	

	@Before
	public void setUp() throws Exception {
		expression = new SearchExpression();
	}

	@Test
	public void testAddExpression() {
		// arrange
		Expression expr1 = mock(Expression.class);
		Expression expr2 = mock(Expression.class);
	
		// act
		this.expression.AddExpression(expr1);
		this.expression.AddExpression(expr2);
		
		// assert
		assertTrue(this.expression.getExpressions().contains(expr1));
		assertTrue(this.expression.getExpressions().contains(expr2));
		assertEquals(2, this.expression.getExpressions().size());
	}
	

	@Test
	public void testSetBoolOperator() {
		this.expression.setBoolOperator(BoolOperator.and);
		assertEquals(BoolOperator.and, this.expression.getBoolOperator());
		
		this.expression.setBoolOperator(BoolOperator.or);
		assertEquals(BoolOperator.or, this.expression.getBoolOperator());
	}

}
