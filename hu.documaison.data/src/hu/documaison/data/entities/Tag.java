package hu.documaison.data.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;


@Entity(name = "Tags")
public class Tag extends DatabaseObject {
	public static final String NAME = "name";
	public static final String DOCUMENTS = "documents";

	//@DatabaseField(columnName = NAME)
	@Column(name = NAME)
	private String name;

	//@DatabaseField
	@Column
	private String colorName;

	//@DatabaseField
	@Column
	private boolean hidden;
	
	@ForeignCollectionField(eager = false, columnName = DOCUMENTS)
	//@OneToMany(fetch = FetchType.EAGER)
	//@JoinColumn(name = DOCUMENTS)
	private ForeignCollection<DocumentTagConnection> dtcs;

	public Tag() {
		// ORMLite needs a no-arg constructor
	}

	public Tag(String name) {
		this.name = name;
		this.colorName = "red";
		this.hidden = false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the colorName
	 */
	public String getColorName() {
		return colorName;
	}

	/**
	 * @param colorName
	 *            the colorName to set
	 */
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
