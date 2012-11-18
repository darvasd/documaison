package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.gui.InnerPanel;

import java.util.ArrayList;

import org.eclipse.nebula.widgets.compositetable.CompositeTable;
import org.eclipse.nebula.widgets.compositetable.IRowContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class DocumentLister extends InnerPanel {

	private ArrayList<Document> documents;

	public DocumentLister(Composite parent, int style) {
		super(parent, style, "All documents");

	}

	@Override
	protected void createComposite() {

		documents = new ArrayList<Document>(Application.getBll().getDocuments());

		Composite panel = new Composite(this, SWT.NONE);
		panel.setLayout(new FillLayout());
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(100, -250);
		panel.setLayoutData(data);
		final MetadataEditors editor = new MetadataEditors(this, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(panel, 5);
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(100, -10);
		data.bottom = new FormAttachment(100, 0);
		editor.setLayoutData(data);
		if (documents.size() == 0) {
			new Label(panel, SWT.WRAP)
					.setText("  No document found in the database for the current selection.");
		} else {
			CompositeTable table = new CompositeTable(panel, SWT.NONE);
			DocumentItem it = new DocumentItem(table, SWT.NONE);
			table.setBackground(it.getBackground());
			table.setRunTime(true);
			table.setNumRowsInCollection(documents.size());

			table.addRowContentProvider(new IRowContentProvider() {
				@Override
				public void refresh(CompositeTable sender,
						int currentObjectOffset, Control rowControl) {
					DocumentItem row = (DocumentItem) rowControl;
					row.setEditor(editor);
					row.setDocument(documents.get(currentObjectOffset));
				}
			});
		}

	}

	@Override
	public void showed() {
		documents = new ArrayList<Document>(Application.getBll().getDocuments());
		super.showed();
	}

}
