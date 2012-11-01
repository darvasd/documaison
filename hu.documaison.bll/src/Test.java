import hu.documaison.bll.interfaces.BllImplementation;
import hu.documaison.bll.interfaces.BllInterface;
import hu.documaison.data.entities.DocumentType;
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
			bll.createDocument(dt.getId());
		} catch (UnknownDocumentTypeException e) {
			e.printStackTrace();
		}
	}
}
