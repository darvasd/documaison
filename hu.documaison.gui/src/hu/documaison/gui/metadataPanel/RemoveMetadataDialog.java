package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RemoveMetadataDialog {

	private Document parentDoc;
	private Shell dialog;
	private Button removeBtn;
	private Combo metadataCombo;
	private ArrayList<Metadata> metadataList;

	public void showAndHandle(Shell parent, Document doc) {
		parentDoc = doc;
		metadataList = new ArrayList<Metadata>(doc.getMetadataCollection());
		dialog = new Shell(parent, SWT.APPLICATION_MODAL | SWT.BORDER
				| SWT.CLOSE | SWT.TITLE);
		dialog.setText("Select metadata to remove");
		dialog.setLayout(new FillLayout());

		Composite composite = new Composite(dialog, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 15;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;
		composite.setLayout(gridLayout);

		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Metadata name:");
		metadataCombo = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
		loadMetadataCombo(doc);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		metadataCombo.setLayoutData(gridData);

		removeBtn = new Button(composite, SWT.PUSH);
		removeBtn.setText("Remove");
		removeBtn.setEnabled(false);
		gridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		gridData.horizontalSpan = 2;
		removeBtn.setLayoutData(gridData);

		addEventHandlers();

		dialog.pack();
		dialog.open();

		// Create and check the event loop
		while (!dialog.isDisposed()) {
			if (!dialog.getDisplay().readAndDispatch())
				dialog.getDisplay().sleep();
		}
	}

	private void loadMetadataCombo(Document doc) {
		for (Metadata m : metadataList) {
			metadataCombo.add(m.getName());
		}
	}

	private void addEventHandlers() {
		metadataCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (metadataCombo.getSelectionIndex() > -1) {
					removeBtn.setEnabled(true);
				} else {
					removeBtn.setEnabled(false);
				}
			}
		});

		removeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Metadata mtdt = metadataList.get(metadataCombo
						.getSelectionIndex());
				parentDoc.removeMetadata(mtdt);
				Application.getBll().updateDocument(parentDoc);
				dialog.dispose();
			}
		});

	}

}
