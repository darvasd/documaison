package hu.documaison.gui.metadataPanel;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.gui.ImageHelper;
import hu.documaison.gui.NotificationWindow;
import hu.documaison.gui.commentstags.CommentDialog;
import hu.documaison.gui.commentstags.TagViewer;
import hu.documaison.gui.document.DocumentObserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

public class MainDetailsPanel extends Composite {

	private final Link commentLink;
	private final Document document;
	private final Label tagLabel;
	private TagViewer tags;
	private final Canvas thumbnailImage;
	private final Button browseThumbnail;
	private final Combo typeCombo;
	private final HashMap<Integer, DocumentType> doctypemap = new HashMap<Integer, DocumentType>();

	public MainDetailsPanel(Composite parent, int style, Document doc) {
		super(parent, style);
		document = doc;
		setLayout(new FormLayout());

		Label typeLabel = new Label(this, SWT.None);
		typeLabel.setText("Document type:");
		FormData data = new FormData();
		data.top = new FormAttachment(0, 3);
		data.left = new FormAttachment(0, 0);
		typeLabel.setLayoutData(data);

		typeCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.SINGLE);
		int id = 0, i = 0;
		for (DocumentType dt : Application.getBll().getDocumentTypes()) {
			typeCombo.add(dt.getTypeName());
			if (dt.getTypeName().equalsIgnoreCase(doc.getType().getTypeName())) {
				id = i;
			}
			doctypemap.put(i, dt);
			i++;
		}
		typeCombo.select(id);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(typeLabel, 5);
		typeCombo.setLayoutData(data);

		commentLink = new Link(this, SWT.NONE);
		commentLink.setText("<a>Comments...</a>");
		data = new FormData();
		data.top = new FormAttachment(0, 3);
		data.left = new FormAttachment(typeCombo, 10);
		commentLink.setLayoutData(data);

		thumbnailImage = new Canvas(this, SWT.NONE);
		thumbnailImage.setBackground(getBackground());
		if (doc.getThumbnailBytes() != null) {
		BufferedInputStream inputStreamReader = new BufferedInputStream(
				new ByteArrayInputStream(doc.getThumbnailBytes()));
		ImageData imageData = new ImageData(inputStreamReader);
		Image byteImage = new Image(parent.getDisplay(), imageData);
		ImageHelper.setResizedBackground(thumbnailImage, byteImage);
		} else {
			ImageHelper.setResizedBackground(thumbnailImage, new Image(Display.getDefault(), "images/unknown.png"));
		}
		data = new FormData();
		data.left = new FormAttachment(typeLabel, 5);
		data.top = new FormAttachment(typeCombo, 5);
		data.width = 50;
		data.height = 60;
		thumbnailImage.setLayoutData(data);

		Label thumbnailLabel = new Label(this, SWT.NONE);
		thumbnailLabel.setText("Thumbnail:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(typeCombo, 5);
		thumbnailLabel.setLayoutData(data);

		browseThumbnail = new Button(this, SWT.PUSH);
		browseThumbnail.setText("Change");
		data = new FormData();
		data.left = new FormAttachment(thumbnailLabel, 0, SWT.CENTER);
		data.top = new FormAttachment(thumbnailLabel, 5);
		browseThumbnail.setLayoutData(data);

		tagLabel = new Label(this, SWT.NONE);
		tagLabel.setText("Tags:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(thumbnailImage, -15);
		tagLabel.setLayoutData(data);

		showTags(doc);

		addEventHandlers();
	}

	private void addEventHandlers() {
		commentLink.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				CommentDialog dialog = new CommentDialog();
				dialog.showAndHandle(getShell(), document);
				Application.getBll().updateDocument(document);

			}
		});

		browseThumbnail.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog(Display.getCurrent()
						.getActiveShell());
				dialog.setText("Select thumbnail");
				dialog.setFilterExtensions(new String[] { "*.png", "*.jpg",
						"*.bmp", "*.gif" });
				String path = dialog.open();
				if (path != null) {
					try {
						byte[] thumb = storeNewThumbnail(path);
						document.setThumbnailBytes(thumb);
						Application.getBll().updateDocument(document);
						DocumentObserver.notify(document.getId(), null);
					} catch (IOException e) {
						NotificationWindow.showError("Error",
								"Failed to load resource:" + e.getMessage());
					}
				}
			}
		});

		typeCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Application.getBll().changeDocumentType(document,
							doctypemap.get(typeCombo.getSelectionIndex()));
				} catch (Exception e1) {
					NotificationWindow.showError(
							"DB error",
							"Failed to change document type ("
									+ e1.getMessage() + ")");
				}
				DocumentObserver.notify(document.getId(), null);
			}
		});
	}

	private void showTags(Document doc) {
		if (tags != null) {
			tags.dispose();
		}
		tags = new TagViewer(this, SWT.NONE);
		tags.createControls(doc);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(tagLabel, 0);
		data.right = new FormAttachment(100, -10);
		tags.setLayoutData(data);
		layout();
	}

	@Override
	public void dispose() {
		if (tags != null) {
			tags.dispose();
		}
		super.dispose();
	}

	private byte[] storeNewThumbnail(String path) throws IOException {

		byte[] thumbBytes = ImageHelper.getImageBytes(path);
		BufferedInputStream inputStreamReader = new BufferedInputStream(
				new ByteArrayInputStream(thumbBytes));
		ImageData imageData = new ImageData(inputStreamReader);
		Image byteImage = new Image(null, imageData);
		ImageHelper.setResizedBackground(thumbnailImage, byteImage);
		return thumbBytes;

	}
}
