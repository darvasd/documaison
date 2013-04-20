package hu.documaison.test.data.search;

import static org.junit.Assert.*;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.search.Expression;
import hu.documaison.data.search.ExpressionTwoOp;
import hu.documaison.data.search.Operator;

import org.junit.Test;

public class ExpressionTest {

	@Test(expected = InvalidParameterException.class)
	public void testBetweenCtor() throws InvalidParameterException {
		// arrange
		String val1 = "2001";
		String metadataName = "year";
		Operator operator = Operator.between;

		// act
		@SuppressWarnings("unused")
		Expression expression = new Expression(metadataName, operator, val1);
	}

	@Test
	public void test() throws InvalidParameterException {
		// arrange
		String val1 = "2001";
		String metadataName = "year";
		Operator operator = Operator.le;

		// act
		Expression expression = new Expression(metadataName, operator, val1);

		// assert
		assertEquals(val1, expression.getValue());
		assertEquals(metadataName, expression.getMetadataName());
		assertEquals(operator, expression.getOperator());
		assertEquals(val1, expression.getValue(0));
		assertEquals(1, expression.getValueCount());

		try {
			expression.getValue(1);
			fail();
		} catch (InvalidParameterException ex) {
			// empty
		}
	}

}
