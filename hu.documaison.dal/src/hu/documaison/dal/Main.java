package hu.documaison.dal;

import hu.documaison.dal.database.DatabaseUtils;
import hu.documaison.data.entities.*;
import hu.documaison.dal.interfaceimpl.CoreImplementation;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception { //FIXME 
		// TEST METHOD!!!
//		
//		System.out.println("Hello world, this is DocuMaison.");
		DatabaseUtils.createTablesBestEffort();
//		
//		Tag t1 = new Tag("BME");
//		t1.setColorName("blue");
//		Tag t2 = new Tag("PetriDotNet");
//		
//		// create a connection source to our database
//		ConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
//		
//        // instantiate the dao
//        Dao<Tag, String> tagDao =
//            DaoManager.createDao(connectionSource, Tag.class);
//
//       
//        // insert testdata
//        Tag tag = new Tag("szilvafa");
//        tag.setColorName("green");
//		tagDao.create(tag);
//		
//		(new CoreImplementation()).addTag(t1);
//		
//		System.out.println("Tags now:");
//		for (Tag t : tagDao.queryForAll())
//		{
//			System.out.println("    -> " + t.getName());
//		}
//		
//		connectionSource.close();
		
		
		
		
		
		////
		CoreImplementation ci = new CoreImplementation();
		
		DocumentType dt = ci.createDocumentType(); //new DocumentType();
		dt.setTypeName("publikáció");
		
		dt.addMetadata(new DefaultMetadata("konferencia","SPLST"));
		ci.addDocumentType(dt);
	}

}
