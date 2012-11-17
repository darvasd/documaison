package hu.documaison.gui.doctype;

import hu.documaison.Application;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.NotifactionWindow;
import hu.documaison.gui.ViewManager;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class DocTypeEditor extends InnerPanel {

	private Tree tree;
	private Button removeDocTypeBtn;
	private Button addMetadataBtn;
	private Button editMetadataBtn;
	private Button removeMetadataBtn;
	private Button addNewDocTypeBtn;
	private Button editDocTypeBtn;
	private Button doneBtn;

	public DocTypeEditor(Composite parent, int style) {
		super(parent, style, "Document type manager");
	}

	@Override
	protected void createComposite() {

		Label label = new Label(this, SWT.None);
		label.setText("Here you can modify the document types of the application.");
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(100, -10);
		label.setLayoutData(data);

		addNewDocTypeBtn = new Button(this, SWT.PUSH);
		addNewDocTypeBtn.setText("Add new type");
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.width = 200;
		data.top = new FormAttachment(label, 25);
		addNewDocTypeBtn.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(addNewDocTypeBtn, -10);
		data.top = new FormAttachment(label, 25);
		data.bottom = new FormAttachment(100, -20);
		tree = new Tree(this, SWT.VIRTUAL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLayoutData(data);
		loadDocTypes();

		editDocTypeBtn = new Button(this, SWT.PUSH);
		editDocTypeBtn.setText("Edit type");
		editDocTypeBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(addNewDocTypeBtn, 0);
		data.left = new FormAttachment(tree, 10);
		editDocTypeBtn.setLayoutData(data);

		removeDocTypeBtn = new Button(this, SWT.PUSH);
		removeDocTypeBtn.setText("Remove type");
		removeDocTypeBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(editDocTypeBtn, 0);
		data.left = new FormAttachment(tree, 10);
		removeDocTypeBtn.setLayoutData(data);

		addMetadataBtn = new Button(this, SWT.PUSH);
		addMetadataBtn.setText("Add new metadata");
		addMetadataBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(removeDocTypeBtn, 0);
		data.left = new FormAttachment(tree, 10);
		addMetadataBtn.setLayoutData(data);

		editMetadataBtn = new Button(this, SWT.PUSH);
		editMetadataBtn.setText("Edit metadata");
		editMetadataBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(addMetadataBtn, 0);
		data.left = new FormAttachment(tree, 10);
		editMetadataBtn.setLayoutData(data);

		removeMetadataBtn = new Button(this, SWT.PUSH);
		removeMetadataBtn.setText("Remove metadata");
		removeMetadataBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(editMetadataBtn, 0);
		data.left = new FormAttachment(tree, 10);
		removeMetadataBtn.setLayoutData(data);

		doneBtn = new Button(this, SWT.PUSH);
		doneBtn.setText("Done");
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.bottom = new FormAttachment(tree, 0, SWT.BOTTOM);
		data.left = new FormAttachment(tree, 10);
		doneBtn.setLayoutData(data);

		addEventListeners();
	}

	private void addEventListeners() {
		tree.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				TreeItem parent = item.getParentItem();
				DocumentType parentDT = (DocumentType) parent
						.getData("docType");
				parent.setText(parentDT.getTypeName());
				DefaultMetadata[] metadata = (DefaultMetadata[]) parent
						.getData("metadata");
				if (metadata == null
						&& parentDT.getDefaultMetadataCollection().size() > 0) {
					metadata = parentDT.getDefaultMetadataCollection().toArray(
							new DefaultMetadata[0]);
					parent.setData("metadata", metadata);
				}
				if (metadata != null) {
					DefaultMetadata currentMeta = metadata[e.index];
					item.setText(getMetadataItemLabel(currentMeta));
					item.setData("metadataObject", currentMeta);
					item.setImage(new Image(null, "images/info.png"));
				}

			}
		});

		tree.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem) e.item;
				if (item.getParentItem() == null) {
					removeDocTypeBtn.setEnabled(true);
					editDocTypeBtn.setEnabled(true);
					removeMetadataBtn.setEnabled(false);
					addMetadataBtn.setEnabled(true);
					editMetadataBtn.setEnabled(false);
				} else {
					removeDocTypeBtn.setEnabled(true);
					editDocTypeBtn.setEnabled(true);
					removeMetadataBtn.setEnabled(true);
					addMetadataBtn.setEnabled(true);
					editMetadataBtn.setEnabled(true);
				}
			}
		});

		addMetadataBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = getDTParentOfSelection();
				DocumentType dt = (DocumentType) item.getData("docType");
				MetadataDialog dialog = new MetadataDialog();
				dialog.showAndHandle(getShell(), dt, null);
				DocumentType type = Application.getBll().getDocumentType(
						dt.getId());
				item.setItemCount(type.getDefaultMetadataCollection().size());
				item.setData("metadata", type.getDefaultMetadataCollection()
						.toArray(new DefaultMetadata[0]));
				item.setData("docType", type);
				tree.redraw();
				item.setExpanded(true);
			}
		});

		editMetadataBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem metadataItem = tree.getSelection()[0];
				DefaultMetadata metadataObject = (DefaultMetadata) metadataItem
						.getData("metadataObject");
				if (metadataObject != null) {
					DefaultMetadata currentMeta = metadataObject;
					TreeItem item = getDTParentOfSelection();
					TreeItem selectedItem = tree.getSelection()[0];
					DocumentType dt = (DocumentType) item.getData("docType");
					MetadataDialog dialog = new MetadataDialog();
					currentMeta = dialog.showAndHandle(getShell(), dt,
							currentMeta);
					selectedItem.setText(getMetadataItemLabel(currentMeta));
					selectedItem.setData("metadataObject", currentMeta);
					DocumentType type = Application.getBll().getDocumentType(
							dt.getId());
					item.setItemCount(type.getDefaultMetadataCollection()
							.size());
					item.setData(
							"metadata",
							type.getDefaultMetadataCollection().toArray(
									new DefaultMetadata[0]));
					item.setData("docType", type);
					tree.redraw();
					item.setExpanded(true);
				} else {
					NotifactionWindow
							.showError("Error",
									"Failed to load the metadata object from database.");
				}
			}
		});

		addNewDocTypeBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				DocumentTypeDialog dialog = new DocumentTypeDialog();
				DocumentType newType = dialog.showAndHandle(getShell(), null);
				if (newType != null) {
					addDocTypeToTree(newType);
				}
			}
		});

		editDocTypeBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				DocumentTypeDialog dialog = new DocumentTypeDialog();
				TreeItem item = getDTParentOfSelection();
				DocumentType dt = (DocumentType) item.getData("docType");
				dialog.showAndHandle(getShell(), dt);
				item.setText(dt.getTypeName());
			}
		});

		removeDocTypeBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = getDTParentOfSelection();
				DocumentType dt = (DocumentType) item.getData("docType");
				Application.getBll().removeDocumentType(dt.getId());
				item.dispose();
			}
		});

		removeMetadataBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = tree.getSelection()[0];
				DefaultMetadata metadataObject = (DefaultMetadata) item
						.getData("metadataObject");
				Application.getBll().removeDefaultMetadata(
						metadataObject.getId());
				item.dispose();
			}
		});

		doneBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (previous != null) {
					ViewManager.getDefault().showView(previous);
				}
			}
		});

	}

	private void loadDocTypes() {
		Collection<DocumentType> docTypes = Application.getBll()
				.getAllDocumentTypes();
		for (DocumentType d : docTypes) {
			addDocTypeToTree(d);
		}
	}

	private void addDocTypeToTree(DocumentType d) {
		TreeItem item = new TreeItem(tree, SWT.None);
		item.setText(d.getTypeName());
		int metadataCount = d.getDefaultMetadataCollection().size();
		item.setImage(new Image(null, "images/document.png"));
		item.setData(d);
		item.setData("docType", d);
		item.setItemCount(metadataCount);
	}

	private TreeItem getDTParentOfSelection() {
		TreeItem item = tree.getSelection()[0];
		while (item.getParentItem() != null) {
			item = item.getParentItem();
		}
		return item;
	}

	private String getMetadataItemLabel(DefaultMetadata currentMeta) {
		String dv = "";
		if (currentMeta.getValue() != null
				&& currentMeta.getValue().isEmpty() == false) {
			dv = " (" + currentMeta.getValue() + ")";
		}
		return currentMeta.getName() + " : " + currentMeta.getMetadataType()
				+ dv;
	}

	@Override
	public void showed() {
		if (previous != null) {
			doneBtn.setVisible(true);
		} else {
			doneBtn.setVisible(false);
		}
		super.showed();
	}

}
