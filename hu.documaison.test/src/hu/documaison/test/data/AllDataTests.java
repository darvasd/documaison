package hu.documaison.test.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommentTest.class, DefaultMetadataTest.class,
		DocumentTagConnectionTest.class, DocumentTest.class,
		DocumentTypeTest.class, MetadataTest.class, TagTest.class,
		ParameteredCtorTests.class})
public class AllDataTests {

}
