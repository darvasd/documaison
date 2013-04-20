package hu.documaison.test.data.search;

import static org.junit.Assert.*;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.search.ExpressionTwoOp;
import hu.documaison.data.search.Operator;

import org.junit.Test;

public class ExpressionTwoOpTest {

	@Test
	public void test() throws InvalidParameterException {
		// arrange
		String val1 = "2001";
		String val2 = "2009";
		String metadataName = "year";
		Operator operator = Operator.between;
		
		// act
		ExpressionTwoOp expression = 
				new ExpressionTwoOp(metadataName, operator, val1, val2);
		
		// assert
		assertEquals(val1, expression.getValue(0));
		assertEquals(val2, expression.getValue(1));
		assertEquals(metadataName, expression.getMetadataName());
		assertEquals(operator, expression.getOperator());
		assertEquals(2, expression.getValueCount());
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testWrongParam() throws InvalidParameterException {
		// arrange
		String val1 = "2001";
		String val2 = "2009";
		String metadataName = "year";
		Operator operator = Operator.contains;
		
		// act
		ExpressionTwoOp expression = 
				new ExpressionTwoOp(metadataName, operator, val1, val2);
		fail();
	}

}
