package hu.documaison.dal;

import hu.documaison.dal.database.DatabaseUtils;
import hu.documaison.data.entities.*;
import hu.documaison.dal.interfaces.DalInterface;
import hu.documaison.dal.interfaces.DalSingletonProvider;

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
		DalInterface ci = DalSingletonProvider.getDalImplementation();
		
		DocumentType dt = ci.createDocumentType(); //new DocumentType();
		dt.setTypeName("publication");
		
		// version1
		DefaultMetadata dmd = ci.createDefaultMetadata();
		dmd.setName("conference");
		dmd.setValue("SPLST");
		dmd.setParent(dt);
		ci.updateDefaultMetadata(dmd);
		
		// version2
		DefaultMetadata dmd2 = new DefaultMetadata();
		dmd2.setName("year");
		dmd2.setValue("2011");
		dmd2.setParent(dt);
		dt.addMetadata(dmd2);

		// delete_test
		DefaultMetadata dmd3 = ci.createDefaultMetadata();
		dmd3.setName("TOBEDELETED");
		dmd3.setValue("xxx");
		dmd3.setParent(dt);
		ci.updateDefaultMetadata(dmd3);
		
		// and now delete the last md
		ci.removeDefaultMetadata(dmd3.getId());
		
		// list things
		System.out.println("Listing things");
		for (DocumentType dt1 : ci.getDocumentTypes())
		{
			System.out.println("Documenttype: " + dt1.getId() + "   " + dt1.getTypeName());
			for (DefaultMetadata m : dt1.getDefaultMetadataCollection())
			{
				System.out.println("  "+m.getName() + " = " + m.getValue());
			}
		}
		
	}

}
