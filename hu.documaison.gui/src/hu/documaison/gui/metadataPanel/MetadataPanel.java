package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;

import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.mihalis.opal.propertyTable.PTProperty;
import org.mihalis.opal.propertyTable.PTPropertyChangeListener;
import org.mihalis.opal.propertyTable.PropertyTable;
import org.mihalis.opal.propertyTable.editor.PTDateEditor;
import org.mihalis.opal.propertyTable.editor.PTFileEditor;
import org.mihalis.opal.propertyTable.editor.PTIntegerEditor;
import org.mihalis.opal.propertyTable.editor.PTStringEditor;
import org.mihalis.opal.propertyTable.editor.PTURLEditor;

public class MetadataPanel extends Composite {

	private PropertyTable pTable;
	private Document doc;
	private HashMap<String, Metadata> metadataMap;

	public MetadataPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
	}

	public void setDocument(Document doc) {
		this.doc = doc;
		createNewPropTable();

		String loc = doc.getLocation();
		if (loc.startsWith("http://")) {
			pTable.addProperty(new PTProperty("Location (URL)", "loc", "", loc)
					.setEditor(new PTURLEditor()));
		} else {
			pTable.addProperty(new PTProperty("Location (file)", "loc", "", loc)
					.setEditor(new PTFileEditor()));
		}

		// tüptürüpp
		metadataMap = new HashMap<String, Metadata>();
		if (doc.getMetadataCollection() != null) {
			for (Metadata mtdt : doc.getMetadataCollection()) {
				metadataMap.put("" + mtdt.getId(), mtdt);
				switch (mtdt.getMetadataType()) {
				case Date:
					pTable.addProperty(new PTProperty(mtdt.getName(), ""
							+ mtdt.getId(), null, mtdt.getDateValue())
							.setEditor(new PTDateEditor()));
					break;
				case Integer:
					pTable.addProperty(new PTProperty(mtdt.getName(), ""
							+ mtdt.getId(), null, mtdt.getIntValue())
							.setEditor(new PTIntegerEditor()));
					break;
				case Text:
				default:
					pTable.addProperty(new PTProperty(mtdt.getName(), ""
							+ mtdt.getId(), null, mtdt.getValue())
							.setEditor(new PTStringEditor()));
					break;

				}
			}
		}
		layout();

	}

	private void createNewPropTable() {
		if (pTable != null) {
			pTable.dispose();
		}
		pTable = new PropertyTable(this, SWT.BORDER);
		pTable.viewAsFlatList();
		pTable.hideButtons();
		pTable.hideDescription();

		pTable.addChangeListener(new PTPropertyChangeListener() {

			@Override
			public void propertyHasChanged(PTProperty prop) {
				if (prop.getDisplayName().equalsIgnoreCase("loc")) {

				} else {
					Metadata mtdt = metadataMap.get(prop.getDisplayName());
					switch (mtdt.getMetadataType()) {
					case Date:
						mtdt.setValue((Date) prop.getValue());
						break;
					case Integer:
						mtdt.setValue((Integer) prop.getValue());
						break;
					case Text:
					default:
						mtdt.setValue((String) prop.getValue());
						break;

					}
				}

				Application.getBll().updateDocument(doc);
			}
		});
	}

}
