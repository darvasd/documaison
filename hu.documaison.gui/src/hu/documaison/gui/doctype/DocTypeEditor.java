package hu.documaison.gui.doctype;

import java.util.Collection;

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
import hu.documaison.data.entities.DocumentType;
import hu.documaison.gui.InnerPanel;

public class DocTypeEditor extends InnerPanel {

	private Tree tree;

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
		
		Button removeDocTypeBtn = new Button(this, SWT.PUSH);
		removeDocTypeBtn.setText("Remove type");
		removeDocTypeBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(addNewDocTypeBtn, 0);
		data.left = new FormAttachment(tree, 10);
		removeDocTypeBtn.setLayoutData(data);
		
		Button addMetadataBtn = new Button(this, SWT.PUSH);
		addMetadataBtn.setText("Add new metadata");
		addMetadataBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(removeDocTypeBtn, 0);
		data.left = new FormAttachment(tree, 10);
		addMetadataBtn.setLayoutData(data);
		
		Button editMetadataBtn = new Button(this, SWT.PUSH);
		editMetadataBtn.setText("Edit metadata");
		editMetadataBtn.setEnabled(false);
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(addMetadataBtn, 0);
		data.left = new FormAttachment(tree, 10);
		editMetadataBtn.setLayoutData(data);
		
		Button removeMetadataBtn = new Button(this, SWT.PUSH);
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
				System.out.println(item);
			}
		});
	}
	
	
	private void loadDocTypes() {
		Collection<DocumentType> docTypes = Application.getBll().getAllDocumentTypes();
		for (DocumentType d : docTypes) {
			TreeItem item = new TreeItem(tree, SWT.None);
			item.setText(d.getTypeName());
			item.setImage(new Image(null, "images/document.png"));
			item.setItemCount(5);
		}
	}

}
