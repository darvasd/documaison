package hu.documaison.core;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import hu.documaison.core.database.DatabaseUtils;
import hu.documaison.core.databaseobjects.Tag;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception { //FIXME 
		// TEST METHOD!!!
		
		System.out.println("Hello world, this is DocuMaison.");
		DatabaseUtils.createTablesBestEffort();
		
		Tag t1 = new Tag("BME");
		t1.setColorName("blue");
		Tag t2 = new Tag("PetriDotNet");
		
		// create a connection source to our database
		ConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
		
        // instantiate the dao
        Dao<Tag, String> tagDao =
            DaoManager.createDao(connectionSource, Tag.class);

       
        // insert testdata
        Tag tag = new Tag("szilvafa");
        tag.setColorName("green");
		tagDao.create(tag);
		
		System.out.println("Tags now:");
		for (Tag t : tagDao.queryForAll())
		{
			System.out.println("    -> " + t.getName());
		}
		
		connectionSource.close();
	}

}
