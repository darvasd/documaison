package hu.documaison.data.entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "Metadata")
public class Metadata extends AbstractMetadata {
	//@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	@ManyToOne
	@JoinColumn(name = PARENT, nullable = true)
	protected Document parent;
	
	public Metadata() {
		// ORMLite needs a no-arg constructor
	}

	public Metadata(MetadataType type, Document parent) {
		setMetadataType(type);
		setParent(parent);
	}
	
	public Metadata(Document parent, String name, String value) {
		setMetadataType(MetadataType.Text);
		setParent(parent);
		setName(name);
		setValue(value);
	}
	
	public Metadata(Document parent, String name, int value) {
		setMetadataType(MetadataType.Text);
		setParent(parent);
		setName(name);
		setValue(value);
	}

	public void setParent(Document parent) {
		this.parent = parent;
	}
}
