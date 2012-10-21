package hu.documaison.gui.doctype;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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

import hu.documaison.Application;
import hu.documaison.data.entities.DefaultMetadata;
import hu.documaison.data.entities.DocumentType;
import hu.documaison.data.entities.Metadata;
import hu.documaison.gui.InnerPanel;

public class DocTypeEditor extends InnerPanel {

	private Tree tree;
	private Button removeDocTypeBtn;
	private Button addMetadataBtn;
	private Button editMetadataBtn;
	private Button removeMetadataBtn;

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
		
		Button addNewDocTypeBtn = new Button(this, SWT.PUSH);
		addNewDocTypeBtn.setText("Add new type");
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.width = 200;
		data.top = new FormAttachment(label, 25);
		addNewDocTypeBtn.setLayoutData(data);
		
		//ScrolledComposite treeComposite = new ScrolledComposite(this, SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.right = new FormAttachment(addNewDocTypeBtn, -10);
		data.top = new FormAttachment(label, 25);
		data.bottom = new FormAttachment(100, -20);
		//treeComposite.setLayoutData(data);
		//treeComposite.setExpandHorizontal(true);
		//treeComposite.setExpandVertical(true);
		tree = new Tree(this, SWT.VIRTUAL | SWT.V_SCROLL | SWT.BORDER);
		tree.setLayoutData(data);
		loadDocTypes();
		//treeComposite.setContent(tree);
		
		removeDocTypeBtn = new Button(this, SWT.PUSH);
		removeDocTypeBtn.setText("Remove type");
		removeDocTypeBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(addNewDocTypeBtn, 0);
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
		
		addEventListeners();
	}
	
	
	private void addEventListeners() {
		tree.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem)e.item;
				TreeItem parent = item.getParentItem();
				DocumentType parentDT = (DocumentType)parent.getData("docType");
				DefaultMetadata[] metadata = (DefaultMetadata[])parent.getData("metadata");
				if (metadata == null) {
					metadata = parentDT.getDefaultMetadataCollection().toArray(new DefaultMetadata[0]);
					parent.setData("metadata", metadata);
				}
				DefaultMetadata currentMeta = metadata[e.index];
				String dv = "";
				if (currentMeta.getValue() != null && currentMeta.getValue().isEmpty() == false) {
					dv = " (" + currentMeta.getValue() + ")";
				}
				
				item.setText(currentMeta.getName() + " : " + currentMeta.getMetadataType() + dv);
				item.setImage(new Image(null, "images/info.png"));
				
			}
		});
		
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem)e.item;
				if (item.getParentItem() == null) {
					removeDocTypeBtn.setEnabled(true);
					removeMetadataBtn.setEnabled(false);
					addMetadataBtn.setEnabled(true);
					editMetadataBtn.setEnabled(false);
				} else {
					removeDocTypeBtn.setEnabled(true);
					removeMetadataBtn.setEnabled(true);
					addMetadataBtn.setEnabled(true);
					editMetadataBtn.setEnabled(true);
				}
			}
		});
		
		addMetadataBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = tree.getSelection()[0];
				while (item.getParentItem() != null) {
					item = item.getParentItem();
				}
				DocumentType dt = (DocumentType)item.getData("docType");
				NewMetadataDialog dialog = new NewMetadataDialog();
				dialog.showAndHandle(getShell(), dt);
				DocumentType type = Application.getBll().getDocumentType(dt.getId());
				item.setItemCount(type.getDefaultMetadataCollection().size());
				item.setData("metadata", type.getDefaultMetadataCollection().toArray(new DefaultMetadata[0]));
				item.setData("docType", type);
				tree.redraw();
				item.setExpanded(true);
			}
		});
	}
	
	
	private void loadDocTypes() {
		Collection<DocumentType> docTypes = Application.getBll().getAllDocumentTypes();
		for (DocumentType d : docTypes) {
			TreeItem item = new TreeItem(tree, SWT.None);
			item.setText(d.getTypeName());
			int metadataCount = d.getDefaultMetadataCollection().size();
			item.setImage(new Image(null, "images/document.png"));
			item.setData(d);
			item.setData("docType", d);
			if (metadataCount == 0) {
				item.setItemCount(1);
				TreeItem childItem = new TreeItem(item, SWT.NONE);
				childItem.setText("No metadata provided for this type");
			} else {
				item.setItemCount(metadataCount);
			}
		}
	}
}
