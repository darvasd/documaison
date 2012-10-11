package hu.documaison.core.databaseobjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Metadata")
public class Metadata extends DatabaseObject {
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = true)
	private String value;
	
	public Metadata()
	{
		// ORMLite needs a no-arg constructor 
	}
	
	public Metadata(String name, String value)
	{
		setName(name);
		setValue(value);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public Metadata createCopy()
	{
		Metadata ret = new Metadata();
		ret.name = this.name;
		ret.value = this.value;
		return ret;
	}
}
