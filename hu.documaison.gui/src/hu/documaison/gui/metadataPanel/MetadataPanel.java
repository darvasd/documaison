package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.helper.DataHelper;
import hu.documaison.gui.NotifactionWindow;
import hu.documaison.gui.document.DocumentObserver;
import hu.documaison.gui.document.IDocumentChangeListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.mihalis.opal.propertyTable.PTProperty;
import org.mihalis.opal.propertyTable.PTPropertyChangeListener;
import org.mihalis.opal.propertyTable.PropertyTable;
import org.mihalis.opal.propertyTable.editor.PTDateEditor;
import org.mihalis.opal.propertyTable.editor.PTFileEditor;
import org.mihalis.opal.propertyTable.editor.PTIntegerEditor;
import org.mihalis.opal.propertyTable.editor.PTStringEditor;
import org.mihalis.opal.propertyTable.editor.PTURLEditor;

public class MetadataPanel extends Composite implements IDocumentChangeListener {

	private PropertyTable pTable;
	private final Link addProp;
	private Document doc;
	private HashMap<String, Metadata> metadataMap;
	private final Link remProp;
	private Timer timer;
	private TimerTask timerTask;
	private final Composite parent;

	public MetadataPanel(Composite parent, int style) {
		super(parent, style);
		this.parent = parent;
		setLayout(new FormLayout());
		addProp = new Link(this, SWT.NONE);
		addProp.setText("<a>Add more metadata...</a>");
		FormData data = new FormData();
		data.bottom = new FormAttachment(100, -5);
		data.right = new FormAttachment(100, -10);
		addProp.setLayoutData(data);
		addProp.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				AddMoreMetadataDialog dialog = new AddMoreMetadataDialog();
				Metadata metadata = dialog.showAndHandle(getShell(), doc);
				try {
					doc = Application.getBll().getDocument(doc.getId());
				} catch (UnknownDocumentException e1) {
					NotifactionWindow.showError("Database error",
							"Failed to update document from database.");
				}
				if (metadata != null) {
					DocumentObserver.notify(doc.getId(), null);
				}
			}
		});
		remProp = new Link(this, SWT.NONE);
		remProp.setText("<a>Remove metadata</a>");
		data = new FormData();
		data.bottom = new FormAttachment(100, -5);
		data.right = new FormAttachment(addProp, -5);
		remProp.setLayoutData(data);
		remProp.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				RemoveMetadataDialog dialog = new RemoveMetadataDialog();
				dialog.showAndHandle(getShell(), doc);
				DocumentObserver.notify(doc.getId(), null);
			}
		});
	}

	public void setDocument(Document doc) {
		if (this.doc != null && this.doc != doc) {
			DocumentObserver.detach(this.doc.getId(), this);
		}
		if (this.doc != doc) {
			DocumentObserver.attach(doc.getId(), this);
		}
		this.doc = doc;
		createNewPropTable();
		addProperties();
		pack();
		layout(true);

	}

	private void createNewPropTable() {

		if (pTable != null) {
			pTable.dispose();
		}

		pTable = new PropertyTable(this, SWT.BORDER);
		FormData data = new FormData();
		data.bottom = new FormAttachment(addProp, -5);
		data.right = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, 0);
		pTable.setLayoutData(data);
		pTable.viewAsFlatList();
		pTable.hideButtons();
		pTable.hideDescription();

		pTable.addChangeListener(new PTPropertyChangeListener() {

			@Override
			public void propertyHasChanged(PTProperty prop) {
				if (prop.getDisplayName().equalsIgnoreCase("loc") == false) {
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

					lazyUpdate(doc, mtdt);
				}
			}

		});

	}

	private void addProp(Metadata mtdt) {
		metadataMap.put("" + mtdt.getId(), mtdt);
		switch (mtdt.getMetadataType()) {
		case Date:
			if (!pTable.getProperties().containsKey(mtdt.getName())) {
				pTable.addProperty(new PTProperty(mtdt.getName(), ""
						+ mtdt.getId(), null, mtdt.getDateValue())
						.setEditor(new PTDateEditor()));
			}
			break;
		case Integer:
			if (!pTable.getProperties().containsKey(mtdt.getName())) {
				pTable.addProperty(new PTProperty(mtdt.getName(), ""
						+ mtdt.getId(), null, mtdt.getIntValue())
						.setEditor(new PTIntegerEditor()));
			}
			break;
		case Text:
		default:
			if (!pTable.getProperties().containsKey(mtdt.getName())) {
				pTable.addProperty(new PTProperty(mtdt.getName(), ""
						+ mtdt.getId(), null, mtdt.getValue())
						.setEditor(new PTStringEditor()));
			}
			break;

		}
	}

	@Override
	public void documentChanged() {
		createNewPropTable();
		addProperties();
		pack();
		parent.layout();
	}

	private void addProperties() {
		try {
			doc = Application.getBll().getDocument(doc.getId());
		} catch (UnknownDocumentException e) {
			NotifactionWindow.showError("DB error",
					"Failed to update the document from db.");
		}
		String loc = doc.getLocation();
		if (loc != null) {
			if (DataHelper.isURL(loc)) {
				pTable.addProperty(new PTProperty("Location (URL)", "loc", "",
						loc).setEditor(new PTURLEditor()));
			} else {
				pTable.addProperty(new PTProperty("Location (file)", "loc", "",
						loc).setEditor(new PTFileEditor()));
			}
		}

		metadataMap = new HashMap<String, Metadata>();
		if (doc.getMetadataCollection() != null) {
			for (Metadata mtdt : doc.getMetadataCollection()) {
				addProp(mtdt);
			}
		}
	}

	private void lazyUpdate(final Document doc, final Metadata mtdt) {

		if (timer == null) {
			timer = new Timer();
		}
		if (timerTask == null
				|| timerTask.scheduledExecutionTime() < (new Date()).getTime()) {
			timerTask = new TimerTask() {

				@Override
				public void run() {

					Application.getBll().updateMetadata(mtdt);
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							System.out.println("Runned");
							DocumentObserver.notify(doc.getId(),
									MetadataPanel.this);
						}
					});

				}
			};
			timer.schedule(timerTask, 300);
			System.out.println("Scheduled");
		}
	}

	@Override
	public void dispose() {
		DocumentObserver.detach(doc.getId(), this);
		super.dispose();
	}

}
