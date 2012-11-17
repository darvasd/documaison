package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.mihalis.opal.propertyTable.PTProperty;
import org.mihalis.opal.propertyTable.PropertyTable;
import org.mihalis.opal.propertyTable.editor.PTComboEditor;
import org.mihalis.opal.propertyTable.editor.PTFileEditor;

public class MetadataPanel extends Composite {

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

		PropertyTable pTable = new PropertyTable(this, SWT.None);
		pTable.viewAsFlatList();
		pTable.hideButtons();
		pTable.hideDescription();

		PTComboEditor combo = new PTComboEditor(true, Application.getBll()
				.getDocumentTypes().toArray());
		pTable.addProperty(new PTProperty("Document type", "", "")
				.setEditor(combo));

		pTable.addProperty(new PTProperty("Location", "", "")
				.setEditor(new PTFileEditor()));
	}

}
