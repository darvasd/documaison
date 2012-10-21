package hu.documaison.data.entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Metadata")
public class Metadata extends AbstractMetadata {
	@DatabaseField(canBeNull = true, foreign = true, columnName = PARENT)
	protected Document parent;
	public Metadata() {
		// ORMLite needs a no-arg constructor
	}

	public Metadata(MetadataType type, Document parent) {
		setMetadataType(type);
		setParent(parent);
	}

	public void setParent(Document parent) {
		this.parent = parent;
	}
}
