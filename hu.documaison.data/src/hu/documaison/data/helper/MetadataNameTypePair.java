package hu.documaison.data.helper;

import hu.documaison.data.entities.MetadataType;

public class MetadataNameTypePair {
	private String name;
	private MetadataType type;

	public MetadataNameTypePair(String name, MetadataType type) {
		this.name = name;
		this.type = type;
		
		if (this.name == null){
			this.name = "";
		}
	}

	public String getName() {
		return name;
	}

	public MetadataType getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null || !(arg0 instanceof MetadataNameTypePair)){
			return false;
		}
		
		MetadataNameTypePair other = (MetadataNameTypePair)arg0;
		return other.getName().equals(this.name) && other.getType() == this.type;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() * 31 + this.type.hashCode();
	}
}
