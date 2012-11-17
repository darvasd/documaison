package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.mihalis.opal.propertyTable.PTProperty;
import org.mihalis.opal.propertyTable.PropertyTable;
import org.mihalis.opal.propertyTable.editor.PTComboEditor;
import org.mihalis.opal.propertyTable.editor.PTDateEditor;
import org.mihalis.opal.propertyTable.editor.PTFileEditor;
import org.mihalis.opal.propertyTable.editor.PTIntegerEditor;
import org.mihalis.opal.propertyTable.editor.PTStringEditor;
import org.mihalis.opal.propertyTable.editor.PTURLEditor;

public class MetadataPanel extends Composite {

	private PropertyTable pTable;

	public MetadataPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());

		// Table table = new Table(this, SWT.NO_SCROLL | SWT.SINGLE);
		// // table.setBackground(new Color(null, 188, 0, 10));
		// table.setLinesVisible(true);
		// table.setHeaderVisible(true);
		// TableColumn col1 = new TableColumn(table, SWT.NONE);
		// TableColumn col2 = new TableColumn(table, SWT.NONE);
		// col1.setText("Name");
		// col1.setWidth(150);
		// col1.setResizable(true);
		// col2.setText("Value");
		// col2.pack();
		// col2.setWidth(300);
		// col2.setResizable(true);
		//
		// TableItem typeItem = new TableItem(table, SWT.NONE);
		// typeItem.setText(0, "Document type");
		// TableEditor editor = new TableEditor(table);
		// CCombo combo = new CCombo(table, SWT.NONE);
		// editor.grabHorizontal = true;
		// editor.setEditor(combo, typeItem, 1);
		// for (DocumentType dt : Application.getBll().getDocumentTypes()) {
		// combo.add(dt.getTypeName());
		// }

		pTable = new PropertyTable(this, SWT.None);
		pTable.viewAsFlatList();
		pTable.hideButtons();
		pTable.hideDescription();

		pTable.addProperty(new PTProperty("Location", "", "")
				.setEditor(new PTFileEditor()));
	}

	public void setDocument(Document doc) {
		if (pTable != null) {
			pTable.dispose();
		}
		pTable = new PropertyTable(this, SWT.BORDER);
		pTable.viewAsFlatList();
		pTable.hideButtons();
		pTable.hideDescription();

		PTComboEditor combo = new PTComboEditor(true, Application.getBll()
				.getDocumentTypes().toArray());
		pTable.addProperty(new PTProperty("doctype", "Document Type", "", doc
				.getType().getTypeName()).setEditor(combo));

		String loc = doc.getLocation();
		if (loc.startsWith("http://")) {
			pTable.addProperty(new PTProperty("loc", "Location (URL)", "", loc)
					.setEditor(new PTURLEditor()));
		} else {
			pTable.addProperty(new PTProperty("loc", "Location (file)", "", loc)
					.setEditor(new PTFileEditor()));
		}

		// tüptürüpp
		if (doc.getMetadataCollection() != null) {
			for (Metadata mtdt : doc.getMetadataCollection()) {
				switch (mtdt.getMetadataType()) {
				case Date:
					pTable.addProperty(new PTProperty("mtdt_" + mtdt.getId(),
							mtdt.getName(), null, mtdt.getDateValue())
							.setEditor(new PTDateEditor()));
					break;
				case Integer:
					pTable.addProperty(new PTProperty("mtdt_" + mtdt.getId(),
							mtdt.getName(), null, mtdt.getIntValue())
							.setEditor(new PTIntegerEditor()));
					break;
				case Text:
				default:
					pTable.addProperty(new PTProperty("mtdt_" + mtdt.getId(),
							mtdt.getName(), null, mtdt.getValue())
							.setEditor(new PTStringEditor()));
					break;

				}
			}
		}

	}
}
