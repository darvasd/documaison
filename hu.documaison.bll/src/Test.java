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
			Document doc = bll.createDocument(dt.getId());
			System.out.println("DocId=" + doc.getId());
			Tag t = bll.createTag("überfontos");
			
			bll.addTagToDocument(t, doc);
			
			System.out.println("Documents with tag:");
			for (Document d : bll.getDocuments(t))
			{
				System.out.println(d.getId());
			}
			
		} catch (UnknownDocumentTypeException e) {
			e.printStackTrace();
		}
	}
}
