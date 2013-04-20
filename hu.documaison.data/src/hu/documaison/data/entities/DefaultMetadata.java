package hu.documaison.data.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "DefaultMetadata")
public class DefaultMetadata extends AbstractMetadata {

	//@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	@ManyToOne
	@JoinColumn(name = PARENT)
	protected DocumentType parent;

	public DefaultMetadata() {
		// ORMLite needs a no-arg constructor
	}
	
	public DefaultMetadata(MetadataType type, DocumentType parent) {
		setMetadataType(type);
		setParent(parent);
	}

	public Metadata createMetadata() {
		Metadata ret = new Metadata();
		ret.name = this.name;
		ret.value = this.value;
		ret.metadataType = this.metadataType;
		return ret;
	}

	public void setParent(DocumentType parent) {
		this.parent = parent;
	}

//	public MetadataType getMetadataType() {
//		return metadataType;
//	}

	/**
	 * @return the name
	 */
//	public String getName() {
//		return name;
//	}

//	/**
//	 * @return the value
//	 */
//	public String getValue() {
//		return value;
//	}

//	public void setMetadataType(MetadataType metadataType) {
//		this.metadataType = metadataType;
//	}

	/**
	 * @param name
	 *            the name to set
	 */
//	public void setName(String name) {
//		this.name = name;
//	}

//	@Override
//	protected void setValueInternal(String value) {
//		this.value = value;
//	}

//	@Override
//	protected String getValueInternal() {
//		return this.value;
//	}
}
