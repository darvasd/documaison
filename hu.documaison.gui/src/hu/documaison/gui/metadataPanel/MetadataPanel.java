package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.gui.NotifactionWindow;

import java.util.Date;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
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

public class MetadataPanel extends Composite {

	private PropertyTable pTable;
	private final Link addProp;
	private Document doc;
	private HashMap<String, Metadata> metadataMap;

	public MetadataPanel(Composite parent, int style) {
		super(parent, style);
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
				MoreMetadataDialog dialog = new MoreMetadataDialog();
				Metadata metadata = dialog.showAndHandle(getShell(), doc);
				try {
					doc = Application.getBll().getDocument(doc.getId());
				} catch (UnknownDocumentException e1) {
					NotifactionWindow.showError("Database error",
							"Failed to update document from database.");
				}
				addProp(metadata);
				pTable.layout();
			}
		});
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
				addProp(mtdt);
			}
		}
		layout();

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
				}

				Application.getBll().updateDocument(doc);
			}
		});

	}

	private void addProp(Metadata mtdt) {
		metadataMap.put("" + mtdt.getId(), mtdt);
		switch (mtdt.getMetadataType()) {
		case Date:
			pTable.addProperty(new PTProperty(mtdt.getName(),
					"" + mtdt.getId(), null, mtdt.getDateValue())
					.setEditor(new PTDateEditor()));
			break;
		case Integer:
			pTable.addProperty(new PTProperty(mtdt.getName(),
					"" + mtdt.getId(), null, mtdt.getIntValue())
					.setEditor(new PTIntegerEditor()));
			break;
		case Text:
		default:
			pTable.addProperty(new PTProperty(mtdt.getName(),
					"" + mtdt.getId(), null, mtdt.getValue())
					.setEditor(new PTStringEditor()));
			break;

		}
	}

}
