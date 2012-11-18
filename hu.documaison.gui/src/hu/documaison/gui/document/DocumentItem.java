package hu.documaison.gui.document;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.gui.commentstags.TagViewer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DocumentItem extends Composite {

	private final Composite parent;
	private final Label thumbnailImage;
	private final Label titleLabel;
	private final TagViewer tagViewer;
	private final Label authorLabel;
	private final Color selectionBackground = new Color(null, 189, 210, 238);
	private boolean isSelected = false;
	private MetadataEditors editor;
	private Document doc;

	public DocumentItem(Composite parent, int style) {
		super(parent, style);

		setLayout(new FormLayout());
		this.parent = parent;

		thumbnailImage = new Label(this, SWT.NONE);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 10);
		data.left = new FormAttachment(0, 30);
		data.width = 80;
		data.height = 93;
		thumbnailImage.setLayoutData(data);

		Button openButton = new Button(this, SWT.PUSH);
		openButton.setText("Open");
		Image openImage = new Image(null, "images/open.png");
		openButton.setImage(openImage);
		data = new FormData();
		data.top = new FormAttachment(thumbnailImage, 0, SWT.CENTER);
		data.right = new FormAttachment(100, -70);
		data.height = 45;
		openButton.setLayoutData(data);

		Button otherActions = new Button(this, SWT.PUSH);
		data = new FormData();
		data.top = new FormAttachment(thumbnailImage, 0, SWT.CENTER);
		data.left = new FormAttachment(openButton, 2);
		data.height = 45;
		data.width = 30;
		otherActions.setLayoutData(data);
		Image downArrowImage = new Image(null, "images/down_arrow.png");
		otherActions.setImage(downArrowImage);

		titleLabel = new Label(this, SWT.NONE);
		FontData[] fontData = titleLabel.getFont().getFontData();
		fontData[0].setHeight(14);
		fontData[0].setStyle(SWT.BOLD);
		data = new FormData();
		data.top = new FormAttachment(0, 20);
		data.left = new FormAttachment(thumbnailImage, 10);
		data.right = new FormAttachment(openButton, -10);
		titleLabel.setFont(new Font(null, fontData[0]));
		titleLabel.setLayoutData(data);

		authorLabel = new Label(this, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(titleLabel, 10);
		data.left = new FormAttachment(thumbnailImage, 10);
		data.right = new FormAttachment(openButton, -10);
		authorLabel.setLayoutData(data);
		fontData = authorLabel.getFont().getFontData();
		fontData[0].setStyle(SWT.ITALIC);
		authorLabel.setFont(new Font(null, fontData[0]));

		tagViewer = new TagViewer(this, SWT.NONE);
		data = new FormData();
		data.bottom = new FormAttachment(thumbnailImage, 88);
		data.left = new FormAttachment(thumbnailImage, 10);
		data.right = new FormAttachment(openButton, -10);
		tagViewer.setLayoutData(data);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				setSelection(true);
			}

		});

	}

	public void setDocument(Document document) {
		doc = document;
		setSelection(editor.isShowed(doc));
		if (document.getThumbnailBytes() != null) {
			BufferedInputStream inputStreamReader = new BufferedInputStream(
					new ByteArrayInputStream(document.getThumbnailBytes()));
			ImageData imageData = new ImageData(inputStreamReader);
			Image byteImage = new Image(parent.getDisplay(), imageData);
			thumbnailImage.setImage(byteImage);
		} else {
			thumbnailImage.setImage(new Image(getDisplay(),
					"images/unknown.png"));
		}

		Metadata title = document.getMetadata("title");
		if (title == null) {
			if (document.getLocation() != null) {
				String[] loc = document.getLocation().split(File.separator);
				titleLabel.setText(loc[loc.length - 1]);
			} else {
				titleLabel.setText("Untitled document");
			}
		} else {
			titleLabel.setText(title.getValue());
		}

		Metadata author = document.getMetadata("author");
		if (author == null) {
			authorLabel.setText("Unknown author");
		} else {
			authorLabel.setText(author.getValue());
		}

		tagViewer.createControls(document);

	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return super.computeSize(150, 123, changed);
	}

	public void setSelection(boolean selection) {
		if (selection) {
			isSelected = true;
			titleLabel.setBackground(selectionBackground);
			if (doc != null) {
				editor.showDoc(this);
			}
		} else {
			isSelected = false;
			titleLabel.setBackground(null);
		}
	}

	public void setEditor(MetadataEditors editor) {
		this.editor = editor;
	}

	public Document getDoc() {
		return doc;
	}

}
