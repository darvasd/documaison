package hu.documaison.gui.doctype;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import hu.documaison.Application;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.MetadataType;
import hu.documaison.gui.MultiPanel;
import hu.documaison.gui.NotifactionWindow;
import hu.documaison.gui.search.SearchFieldType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewMetadataDialog {

	private Control c;
	private Combo typeCombo;
	private Composite composite;
	private Button saveBtn;
	private DocumentType parentType;
	private Text nameText;
	private Shell dialog;

	public void showAndHandle(Shell parent, DocumentType docType) {
		parentType = docType;
		dialog = new Shell(parent, SWT.APPLICATION_MODAL | SWT.BORDER | SWT.CLOSE | SWT.TITLE);
		dialog.setText("Add new metadata");
		dialog.setLayout(new FillLayout());
		
		
		composite = new Composite(dialog, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 15;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;
		composite.setLayout(gridLayout);
		
		Label lab1 = new Label(composite, SWT.NONE);
		lab1.setText("Adding new metadata for:");
		
		Label lab2 = new Label(composite, SWT.NONE);
		lab2.setText(docType.getTypeName());
		
		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Metadata name:");
		nameText = new Text(composite, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		nameText.setLayoutData(gridData);
		
		
		Label typeLabel = new Label(composite, SWT.None);
		typeLabel.setText("Metadata type:");
		typeCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		MetadataType[] values = MetadataType.values();
		for (int i = 0; i < values.length; i++) {
			MetadataType metadataType = values[i];
			typeCombo.add(metadataType + "");
		}
		typeCombo.select(0);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		typeCombo.setLayoutData(gridData);
		
		Label dvLabel = new Label(composite, SWT.None);
		dvLabel.setText("Default value:");
		c = createOneField(composite, typeCombo.getItem(0));
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		c.setLayoutData(gridData);
		
		saveBtn = new Button(composite, SWT.PUSH);
		saveBtn.setText("Save");
		gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		saveBtn.setLayoutData(gridData);
		
		addEventHandlers();
		
		dialog.pack();
		dialog.open();
		
		// Create and check the event loop
		while (!dialog.isDisposed()) {
		  if (!dialog.getDisplay().readAndDispatch())
			  dialog.getDisplay().sleep();
		}
		
	}
	
	private Control createOneField(Composite parent, String type) {
		if (type.equalsIgnoreCase("Text")) {
			Text textField = new Text(parent, SWT.BORDER);
			return textField;
		} else if (type.equalsIgnoreCase("Integer")) {
			Text textField = new Text(parent, SWT.BORDER);
			return textField;
		} else if (type.equalsIgnoreCase("Date")) {
			DateTime dateField = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN | SWT.MEDIUM);
			return dateField;
		} else {
			NotifactionWindow.showError("Internal error", "Unknown metadata type, can't create field for " + type + ".");
			Text textField = new Text(parent, SWT.BORDER);
			return textField;
		}
	}
	
	private void addEventHandlers() {
		typeCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Control old = c;
				c = createOneField(composite, typeCombo.getItem(typeCombo.getSelectionIndex()));
				GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
				gridData.widthHint = 300;
				c.setLayoutData(gridData);
				c.moveAbove(old);
				old.dispose();
				composite.layout();
			}
		});
		
		saveBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				DefaultMetadata metadata = Application.getBll().createDefaultMetadata(parentType);
				metadata.setName(nameText.getText());
				String typeName = typeCombo.getItem(typeCombo.getSelectionIndex());
				MetadataType[] values = MetadataType.values();
				for (int i = 0; i < values.length; i++) {
					MetadataType metadataType = values[i];
					if (typeName.equalsIgnoreCase(metadataType + "")) {
						metadata.setMetadataType(metadataType);
					}
				}
				if (c instanceof Text) {
					Text text = (Text) c;
					metadata.setValue(text.getText());
				} else if (c instanceof DateTime) {
					DateTime dt  = (DateTime)c;
					Calendar cal = new GregorianCalendar();
					cal.set(dt.getYear(), dt.getMonth(), dt.getDay());
					metadata.setValue(cal.getTime());
				} else {
					metadata.setValue("");
				}
				Application.getBll().updateDefaultMetadata(metadata);
				dialog.dispose();
			}
		});
	}
	
	
}
