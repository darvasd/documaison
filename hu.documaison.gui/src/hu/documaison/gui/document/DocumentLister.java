package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.gui.InnerPanel;

import java.util.ArrayList;

import org.eclipse.nebula.widgets.compositetable.CompositeTable;
import org.eclipse.nebula.widgets.compositetable.IRowContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class DocumentLister extends InnerPanel {

	private ArrayList<Document> documents;
	private Sash sash;
	private boolean sashVisible = false;
	private CompositeTable table;
	private Composite panel;
	private Label label;
	private MetadataEditors editor;

	public DocumentLister(Composite parent, int style) {
		super(parent, style, "All documents");

	}

	@Override
	protected void createComposite() {

		sash = new Sash(this, SWT.HORIZONTAL | SWT.SMOOTH);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(100, 0);
		sash.setLayoutData(data);
		sash.setBackground(new Color(null, 84, 84, 84));
		sash.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				int limit = 200;
				if (sashVisible == false) {
					limit = 0;
				}
				if (getBounds().height - e.y < limit) {
					e.y = getBounds().height - limit;
				}
				sash.setBounds(e.x, e.y, e.width, e.height);
				FormData formData = new FormData();
				formData.top = new FormAttachment(100, -1
						* (getBounds().height - e.y));
				formData.left = new FormAttachment(0, 0);
				formData.right = new FormAttachment(100, 0);
				sash.setLayoutData(formData);
				layout(true);

			}

		});
		sash.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				setDetailsVisible(false);
			}
		});
		panel = new Composite(this, SWT.NONE);
		panel.setLayout(new FillLayout());
		data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(sash, 0);
		panel.setLayoutData(data);
		editor = new MetadataEditors(this, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(sash, 10);
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(100, -10);
		data.bottom = new FormAttachment(100, 0);
		editor.setLayoutData(data);

	}

	@Override
	public void showed() {
		if (documents == null) {
			documents = new ArrayList<Document>(Application.getBll()
					.getDocuments());
		}

		if (documents.size() == 0) {
			if (table != null) {
				table.dispose();
				table = null;
			}
			if (label == null) {
				label = new Label(panel, SWT.WRAP);
				label.setText("  No document found in the database for the current selection.");
			}
		} else {
			if (label != null) {
				label.dispose();
				label = null;
			}
			if (table == null) {
				table = new CompositeTable(panel, SWT.NONE);
			}
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
					row.setLister(DocumentLister.this);
					row.setDocument(documents.get(currentObjectOffset));
				}
			});
		}
		panel.layout();

		super.showed();
	}

	public void setDetailsVisible(boolean visible) {
		if ((visible && sashVisible) || (!visible && !sashVisible)) {
			return;
		}
		if (visible) {
			sashVisible = true;
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(100, -200);
			sash.setLayoutData(data);
		} else {
			sashVisible = false;
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(100, 0);
			sash.setLayoutData(data);
		}
		layout();
	}

	public void freetextSearch(String freetext) {
		documents = new ArrayList<Document>(Application.getBll()
				.searchDocumentsFreeText(freetext));

	}

}
