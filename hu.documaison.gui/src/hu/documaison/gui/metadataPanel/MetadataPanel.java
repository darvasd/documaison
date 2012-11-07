package hu.documaison.gui.metadataPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MetadataPanel extends Composite {

	public MetadataPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		Table table = new Table(this, SWT.NO_SCROLL | SWT.SINGLE);
		TableColumn col1 = new TableColumn(table, SWT.NONE);
		TableColumn col2 = new TableColumn(table, SWT.NONE);
		col1.setText("Name");
		col2.setText("Value");

		TableItem typeItem = new TableItem(table, SWT.NONE);
		typeItem.setText(2, "Document type");
		TableEditor editor = new TableEditor(table);
		CCombo combo = new CCombo(table, SWT.NONE);
		editor.grabHorizontal = true;
		editor.setEditor(combo, typeItem, 1);
		combo.add("A");
		combo.add("B");

	}

}
