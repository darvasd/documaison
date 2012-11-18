package hu.documaison.settings;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SettingsData {

	private String databaseFileLocation;
	private ArrayList<String> indexedFolders = new ArrayList<String>();
	private boolean indexingEnabled;
	private boolean evernoteIndexingEnabled;
	private String computerId;
	
	public String getDatabaseFileLocation() {
		return databaseFileLocation;
	}
	
	@XmlElement
	public void setDatabaseFileLocation(String databaseFileLocation) {
		this.databaseFileLocation = databaseFileLocation;
	}
	
	public ArrayList<String> getIndexedFolders() {
		return indexedFolders;
	}
	
	@XmlElement
	public void setIndexedFolders(ArrayList<String> indexedFolders) {
		this.indexedFolders = indexedFolders;
	}

	public boolean isIndexingEnabled() {
		return indexingEnabled;
	}

	@XmlElement
	public void setIndexingEnabled(boolean indexingEnabled) {
		this.indexingEnabled = indexingEnabled;
	}

	public boolean isEvernoteIndexingEnabled() {
		return evernoteIndexingEnabled;
	}

	@XmlElement
	public void setEvernoteIndexingEnabled(boolean evernoteIndexingEnabled) {
		this.evernoteIndexingEnabled = evernoteIndexingEnabled;
	}
	
	public String getComputerId() {
		return computerId;
	}
	
	@XmlElement
	public void setComputerId(String computerId) {
		this.computerId = computerId;
	}

	@Override
	public String toString() {
		return "SettingsData [databaseFileLocation=" + databaseFileLocation
				+ ", indexedFolders=" + indexedFolders + ", indexingEnabled="
				+ indexingEnabled + ", evernoteIndexingEnabled="
				+ evernoteIndexingEnabled + "]";
	}
	
	
	
	
	
}
