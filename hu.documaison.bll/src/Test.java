import java.util.ArrayList;
import java.util.List;

import hu.documaison.bll.interfaces.BllImplementation;
import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.entities.*;
import hu.documaison.data.exceptions.UnknownDocumentTypeException;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BllInterface bll = new BllImplementation();
		
		try {
			DocumentType dt = bll.createDocumentType();
			System.out.println(dt.getId());
			Document doc1 = bll.createDocument(dt.getId());
			Document doc2 = bll.createDocument(dt.getId());
			Document doc3 = bll.createDocument(dt.getId());
			Document doc4 = bll.createDocument(dt.getId());
			
			Tag t1 = bll.createTag("überfontos");
			Tag t2 = bll.createTag("unalmas");
			
			bll.addTagToDocument(t1, doc1);
			bll.addTagToDocument(t1, doc2);
			bll.addTagToDocument(t2, doc2);
			bll.addTagToDocument(t2, doc3);
			
			System.out.println("Documents with tag:");
			List<Tag> tags = new ArrayList<Tag>();
			tags.add(t1);
			tags.add(t2);
			for (Document d : bll.getDocumentsByTags(tags))
			{
				System.out.println(d.getId());
			}
			
		} catch (UnknownDocumentTypeException e) {
			e.printStackTrace();
		}
	}
}
