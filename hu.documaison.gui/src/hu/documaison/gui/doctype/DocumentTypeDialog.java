package hu.documaison.gui.doctype;

import hu.documaison.Application;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.exceptions.UnableToCreateException;
import hu.documaison.gui.ImageHelper;
import hu.documaison.gui.NotificationWindow;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DocumentTypeDialog {

	private Composite composite;
	private Button saveBtn;
	private Text nameText;
	private Shell dialog;
	private Text extText;
	private Button browseThumbnail;
	private Canvas thumbnailImage;
	private DocumentType previousType;
	private byte[] thumbBytes;

	public DocumentType showAndHandle(Shell parent, DocumentType previousType) {
		this.previousType = previousType;
		dialog = new Shell(parent, SWT.APPLICATION_MODAL | SWT.BORDER
				| SWT.CLOSE | SWT.TITLE);
		if (previousType == null) {
			dialog.setText("Add new document type");
		} else {
			dialog.setText("Edit document type");
		}
		dialog.setLayout(new FillLayout());

		composite = new Composite(dialog, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 15;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;
		composite.setLayout(gridLayout);

		// Document type name
		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Document type name:");
		nameText = new Text(composite, SWT.BORDER);
		if (previousType != null) {
			nameText.setText(previousType.getTypeName());
		}
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		nameText.setLayoutData(gridData);

		// Extensions
		Label typeLabel = new Label(composite, SWT.None);
		typeLabel.setText("Default type for extensions:");
		extText = new Text(composite, SWT.BORDER);
		if (previousType != null) {
			extText.setText(previousType.getDefaultExt());
		}
		extText.setMessage("Format: doc, docx, ...");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 300;
		extText.setLayoutData(gridData);

		// Thumbnail
		Label thumbnailLabel = new Label(composite, SWT.NONE);
		thumbnailLabel.setText("Default thumbnail:");
		thumbnailImage = new Canvas(composite, SWT.NONE);
		if (previousType != null && previousType.getDefaultThumbnailBytes() != null) {
			thumbBytes = previousType.getDefaultThumbnailBytes();
			BufferedInputStream inputStreamReader = new BufferedInputStream(
					new ByteArrayInputStream(
							previousType.getDefaultThumbnailBytes()));
			ImageData imageData = new ImageData(inputStreamReader);
			Image byteImage = new Image(parent.getDisplay(), imageData);
			ImageHelper.setResizedBackground(thumbnailImage, byteImage);
		} else {
			try {
				storeNewThumbnail("images/unknown.png");
			} catch (IOException e) {
				NotificationWindow.showError("Error",
						"Can't load default thumbnail");
			}
		}
		gridData = new GridData();
		gridData.verticalSpan = 2;
		thumbnailImage.setLayoutData(gridData);
		browseThumbnail = new Button(composite, SWT.PUSH);
		browseThumbnail.setText("Browse");

		// Save button
		saveBtn = new Button(composite, SWT.PUSH);
		saveBtn.setText("Save");
		saveBtn.setEnabled(false);
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
		return this.previousType;

	}

	private void addEventHandlers() {

		saveBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				DocumentType dt = null;
				if (previousType != null) {
					dt = previousType;
				} else {
					try {
						dt = Application.getBll().createDocumentType();
						dt.setDefaultExt(extText.getText());
						dt.setTypeName(nameText.getText());
						dt.setDefaultThumbnailBytes(thumbBytes);
						Application.getBll().updateDocumentType(dt);
						dialog.dispose();
					} catch (UnableToCreateException e1) {
						NotificationWindow.showError(
								"Database error",
								"Unable to create new document type. ("
										+ e1.getMessage() + ")");
					}
					previousType = dt;
				}
			}
		});

		browseThumbnail.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				FileDialog dialog = new FileDialog(Display.getCurrent()
						.getActiveShell());
				dialog.setText("Select thumbnail");
				dialog.setFilterExtensions(new String[] { "*.png", "*.jpg",
						"*.bmp", "*.gif" });
				String path = dialog.open();
				if (path != null) {
					try {
						storeNewThumbnail(path);
					} catch (IOException e1) {
						NotificationWindow.showError("I/O error",
								e1.getMessage());
					}
				}
				validateInputs();
			}
		});

		nameText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validateInputs();
			}
		});

		extText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				validateInputs();
			}
		});
	}

	private byte[] storeNewThumbnail(String path) throws IOException {

		thumbBytes = ImageHelper.getImageBytes(path);
		BufferedInputStream inputStreamReader = new BufferedInputStream(
				new ByteArrayInputStream(thumbBytes));
		ImageData imageData = new ImageData(inputStreamReader);
		Image byteImage = new Image(null, imageData);
		ImageHelper.setResizedBackground(thumbnailImage, byteImage);
		return thumbBytes;

	}

	private void validateInputs() {
		if (nameText.getText().isEmpty()) {
			saveBtn.setEnabled(false);
		} else if (extText.getText().isEmpty() == false) {
			if (!extText.getText().matches("(\\w*(, *)?)+")) {
				saveBtn.setEnabled(false);
			} else {
				saveBtn.setEnabled(true);
			}
		} else {
			saveBtn.setEnabled(true);
		}
	}

}
