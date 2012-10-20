package hu.documaison.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SettingsManager {
	
	private static final String SETTINGS_XML = "settings.xml";

	public static SettingsData getCurrentSettings() throws Exception {
		try {
			JAXBContext context = JAXBContext.newInstance(SettingsData.class);
			Unmarshaller um = context.createUnmarshaller();
			return (SettingsData) um.unmarshal(new FileReader(SETTINGS_XML));
		} catch (Exception e) {
			// Create default settings data
			return createDefaultSettingsData();
		}
	}

	private static SettingsData createDefaultSettingsData() throws Exception {
		SettingsData data = new SettingsData();
		data.setEvernoteIndexingEnabled(false);
		data.setIndexingEnabled(false);
		String currentDir = new File(".").getCanonicalPath();
		data.setDatabaseFileLocation(currentDir + System.getProperty("file.separator") + "documaison.db");
		storeSettings(data);
		return data;
		
	}

	public static void storeSettings(SettingsData data) throws Exception {
		JAXBContext context = JAXBContext.newInstance(SettingsData.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    Writer w = null;
	    try {
	      w = new FileWriter(SETTINGS_XML);
	      m.marshal(data, w);
	    } finally {
	        w.close();   
	    }
	}
	
}
