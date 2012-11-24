package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.search.SearchExpression;
import hu.documaison.gui.ITagSelectionChangeListener;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.NotificationWindow;
import hu.documaison.gui.commentstags.TagPanel;
import hu.documaison.gui.metadataPanel.MetadataEditors;

import java.util.ArrayList;
import java.util.HashMap;

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

public class DocumentLister extends InnerPanel implements
		ITagSelectionChangeListener, IRowContentProvider {

	private ArrayList<Document> documents;
	private Sash sash;
	private boolean sashVisible = false;
	private CompositeTable table;
	private Composite panel;
	private Label label;
	private MetadataEditors editor;
	private boolean showAll = true;
	private SearchExpression searchExpression;
	private String searchString;
	private final HashMap<Integer, DocumentItem> itemMap = new HashMap<Integer, DocumentItem>();

	public DocumentLister(Composite parent, int style) {
		super(parent, style, "Documents");
	}

	@Override
	protected void createComposite() {
		DocumentObserver.setLister(this);
		sash = new Sash(this, SWT.HORIZONTAL | SWT.SMOOTH);
		TagPanel.addChangeListener(this);
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
		editor = new MetadataEditors(this, SWT.NONE, this);
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
			if (table != null && !table.isDisposed()) {
				table.dispose();
			}
			setDetailsVisible(false);
			DocumentObserver.detachAllDocumentItem();
			table = new CompositeTable(panel, SWT.NONE);
			itemMap.clear();
			DocumentItem it = new DocumentItem(table, SWT.NONE);
			table.setBackground(it.getBackground());
			table.setRunTime(true);
			table.setNumRowsInCollection(documents.size());

			table.removeRowContentProvider(this);
			table.addRowContentProvider(this);
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
			editor.setHidden();
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(100, 0);
			sash.setLayoutData(data);
		}
		layout();
	}

	public void freetextSearch(String freetext) {
		showAll = false;
		searchString = freetext;
		searchExpression = null;
		documents = new ArrayList<Document>(Application.getBll()
				.searchDocumentsFreeText(freetext));

		if (freetext.equalsIgnoreCase("tüptürüpp")) {
			NotificationWindow.showError("Easter egg", "Cipciripp");
		}

	}

	public void showAll() {
		documents = new ArrayList<Document>(Application.getBll().getDocuments());
		showAll = true;
	}

	public void advancedSearch(SearchExpression expression) {

		showAll = false;
		searchString = null;
		searchExpression = expression;
		documents = new ArrayList<Document>(Application.getBll()
				.searchDocuments(expression));
	}

	@Override
	public void selectionChanged() {
		if (TagPanel.isSelectionEmpty()) {
			showAll();
		} else {
			System.out.println("Search started");
			documents = new ArrayList<Document>(Application.getBll()
					.getDocumentsByTags(TagPanel.getSelection()));
			System.out.println("And it's done");
		}
		showed();
	}

	public void notifyLister() {
		setDetailsVisible(false);
		if (showAll) {
			showAll();
		} else if (searchString != null) {
			freetextSearch(searchString);
		} else if (searchExpression != null) {
			advancedSearch(searchExpression);
		} else {
			showAll = true;
			searchExpression = null;
			searchString = null;
			showAll();
		}
		showed();
	}

	@Override
	public void refresh(CompositeTable sender, int currentObjectOffset,
			Control rowControl) {
		DocumentItem row = (DocumentItem) rowControl;
		row.setEditor(editor);
		row.setLister(DocumentLister.this);
		Document documentToShow = documents.get(currentObjectOffset);
		row.setDocument(documentToShow);
		itemMap.put(documentToShow.getId(), row);
	}

	public DocumentItem getItemForDocument(int ID) {
		return itemMap.get(ID);
	}
}
