package hu.documaison.test;

import hu.documaison.data.search.ExpressionTwoOp;
import hu.documaison.test.data.CommentTest;
import hu.documaison.test.data.DefaultMetadataTest;
import hu.documaison.test.data.DocumentTagConnectionTest;
import hu.documaison.test.data.DocumentTest;
import hu.documaison.test.data.DocumentTypeTest;
import hu.documaison.test.data.MetadataTest;
import hu.documaison.test.data.ParameteredCtorTests;
import hu.documaison.test.data.TagTest;
import hu.documaison.test.data.helper.DataHelperTest;
import hu.documaison.test.data.search.ExpressionTest;
import hu.documaison.test.data.search.ExpressionTwoOpTest;
import hu.documaison.test.data.search.SearchExpressionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CommentTest.class, DefaultMetadataTest.class,
	DocumentTagConnectionTest.class, DocumentTest.class,
	DocumentTypeTest.class, MetadataTest.class, TagTest.class,
	ParameteredCtorTests.class,
	DataHelperTest.class, ExpressionTwoOpTest.class,
	ExpressionTest.class, SearchExpressionTest.class})
public class AllTests {

}
