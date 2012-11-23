package hu.documaison.gui.document;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Metadata;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.helper.DataHelper;
import hu.documaison.data.helper.FileHelper;
import hu.documaison.gui.ImageHelper;
import hu.documaison.gui.NotifactionWindow;
import hu.documaison.gui.metadataPanel.MetadataEditors;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class DocumentItem extends Composite implements IDocumentChangeListener,
		MouseListener {

	private final Composite parent;
	private Canvas thumbnailImage;
	private Label titleLabel;
	private Label authorLabel;
	private final Color selectionBackground = new Color(null, 189, 210, 238);
	private MetadataEditors editor;
	private Document doc;
	private DocumentLister lister;
	private Button openButton;
	private Button otherActions;
	private MenuItem openFolder;
	private MenuItem delItem;
	private MenuItem copyItem;
	private MenuItem moveItem;
	private MenuItem copyBibTex;

	public DocumentItem(Composite parent, int style) {
		super(parent, style);

		setLayout(new FormLayout());
		this.parent = parent;
		createComposites();

	}

	private void createComposites() {
		thumbnailImage = new Canvas(this, SWT.NO_BACKGROUND);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 10);
		data.left = new FormAttachment(0, 30);
		data.width = 50;
		data.height = 60;
		thumbnailImage.setLayoutData(data);

		openButton = new Button(this, SWT.PUSH);
		openButton.setText("Open");
		Image openImage = new Image(null, "images/open.png");
		openButton.setImage(openImage);
		data = new FormData();
		data.top = new FormAttachment(thumbnailImage, 0, SWT.CENTER);
		data.right = new FormAttachment(100, -70);
		data.height = 45;
		openButton.setLayoutData(data);

		otherActions = new Button(this, SWT.PUSH);

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

		addEventListeners();
	}

	public void setDocument(Document document) {
		if (doc != document) {
			if (doc != null) {
				DocumentObserver.detach(doc.getId(), this);
			}
			DocumentObserver.attach(document.getId(), this);
		}
		try {
			doc = Application.getBll().getDocument(document.getId());
		} catch (UnknownDocumentException e) {
			NotifactionWindow.showError("DB error",
					"Can't update document data");
		}

		if (editor.isShowed(doc)) {
			setSelection(true, false);
		} else {
			setSelection(false, false);
		}

		if (document.getThumbnailBytes() != null) {
			BufferedInputStream inputStreamReader = new BufferedInputStream(
					new ByteArrayInputStream(document.getThumbnailBytes()));
			ImageData imageData = new ImageData(inputStreamReader);
			Image byteImage = new Image(parent.getDisplay(), imageData);
			ImageHelper.setResizedBackground(thumbnailImage, byteImage);
		} else {
			ImageHelper.setResizedBackground(thumbnailImage, new Image(
					getDisplay(), "images/unknwon.png"));
		}
		loadOtherCommands();
		Metadata title = doc.getMetadata("title");
		if (title == null) {
			title = doc.getMetadata("Title");
		}
		if (title == null || title.getValue() == null) {
			titleLabel.setText("Untitled document");
		} else {
			titleLabel.setText(title.getValue());
		}

		Metadata author = doc.getMetadata("author");
		if (author == null) {
			author = doc.getMetadata("Author");
		}
		if (author == null || author.getValue() == null) {
			authorLabel.setText("Unknown author");
		} else {
			authorLabel.setText(author.getValue());
		}

	}

	private void addEventListeners() {
		addMouseListener(this);
		if (thumbnailImage != null) {
			thumbnailImage.addMouseListener(this);
		}
		titleLabel.addMouseListener(this);
		authorLabel.addMouseListener(this);
		openButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (getDoc().getLocation() != null) {
					Program.launch(getDoc().getLocation());
				} else {
					NotifactionWindow.showError("Database error",
							"The location is a null value in the database.");
				}
			}
		});

	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return super.computeSize(150, 80, changed);

	}

	public void setSelection(boolean selection, boolean nomore) {

		if (selection) {
			setBackground(selectionBackground);
			if (doc != null) {
				if (!editor.isShowed(getDoc())) {
					editor.showDoc(this);
				}
			}
			lister.setDetailsVisible(true);
		} else {
			if (nomore == true) {
				editor.setHidden();
				lister.setDetailsVisible(false);
			}
			setBackground(null);
		}
	}

	public void setEditor(MetadataEditors editor) {
		this.editor = editor;
	}

	public Document getDoc() {
		return doc;
	}

	public void setLister(DocumentLister lister) {
		this.lister = lister;
	}

	@Override
	public void setBackground(Color c) {
		super.setBackground(c);
	}

	@Override
	public void documentChanged() {
		setDocument(doc);
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		setSelection(!editor.isShowed(doc), true);
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
	}

	public void copyBibTex(Document doc) {
		Clipboard cb = new Clipboard(getDisplay());
		TextTransfer textTransfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { DataHelper.createBibTex(doc) },
				new Transfer[] { textTransfer });

	}

	private void loadOtherCommands() {
		final Menu dropMenu = new Menu(getShell(), SWT.POP_UP);
		getShell().setMenu(dropMenu);

		openFolder = new MenuItem(dropMenu, SWT.PUSH);
		openFolder.setText("Open containing folder");

		delItem = new MenuItem(dropMenu, SWT.PUSH);
		delItem.setText("Remove document");
		moveItem = new MenuItem(dropMenu, SWT.PUSH);
		moveItem.setText("Relocate document");

		copyItem = new MenuItem(dropMenu, SWT.PUSH);
		copyItem.setText("Export document");

		new MenuItem(dropMenu, SWT.SEPARATOR);
		copyBibTex = new MenuItem(dropMenu, SWT.PUSH);
		copyBibTex.setText("Copy BibTeX");
		String location = getDoc().getLocation();
		if (location == null || DataHelper.isURL(location)) {
			openFolder.setEnabled(false);
			moveItem.setEnabled(false);
			copyItem.setEnabled(false);
		}
		addOtherEventListeners();
		otherActions.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				if ((dropMenu != null) && (!dropMenu.isVisible())) {
					Rectangle bounds = button.getBounds();
					Point menuLoc = button.getParent().toDisplay(bounds.x,
							bounds.y + bounds.height);
					dropMenu.setLocation(menuLoc.x, menuLoc.y);
					dropMenu.setVisible(true);
				}

			}
		});
	}

	private void addOtherEventListeners() {
		copyBibTex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				copyBibTex(getDoc());
			}
		});
		copyItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setText("Export directory");
				String path = dialog.open();
				if (path != null) {
					try {
						String fileName = FileHelper.fileName(doc.getLocation());

						Application.getBll().copyDocument(doc,
								path + File.separator + fileName);
						Program.launch(path);
					} catch (Exception e1) {
						NotifactionWindow.showError("Copy error",
								"Failed to export the selected document. ("
										+ e1.getMessage() + ")");
					}
				}
			}
		});
		moveItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setText("New directory");
				String path = dialog.open();
				if (path != null) {
					try {
						String fileName = FileHelper.fileName(doc.getLocation());

						Application.getBll().moveDocument(doc,
								path + File.separator + fileName);
					} catch (Exception e1) {
						NotifactionWindow.showError("Copy error",
								"Failed to export the selected document. ("
										+ e1.getMessage() + ")");
					}
				}

			}
		});

		delItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (doc.getLocation() == null
							|| doc.getLocation().isEmpty()) {
						Application.getBll().removeDocument(doc.getId());
					} else {
						Application.getBll().deleteAndRemoveDocument(doc);
					}
				} catch (UnknownDocumentException e1) {
					NotifactionWindow.showError("Error",
							"Can't locate the document. (" + e1.getMessage()
									+ ")");
				} catch (InvalidParameterException e1) {
					NotifactionWindow.showError("Error",
							"Can't delete the document. (" + e1.getMessage()
									+ ")");
				}
				DocumentObserver.notifyLister();
			}
		});
		openFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openContainingFolder(getDoc());
			}
		});

	}

	private void openContainingFolder(Document doc) {
		if (doc.getLocation() != null
				|| DataHelper.isURL(doc.getLocation()) == false) {
			File f = new File(doc.getLocation());
			if (f.exists() == false) {
				NotifactionWindow
						.showError("Error", "The file does not exist.");
			} else {
				Program.launch(f.getParent());
			}
		}
	}

	@Override
	public void dispose() {
		System.out.println("Disposing item");
		if (editor.isShowed(doc)) {
			lister.setDetailsVisible(false);
		}
		DocumentObserver.detach(doc.getId(), this);
		super.dispose();
	}

}
