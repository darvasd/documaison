package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TagItem extends Composite implements MouseListener {
	
	private boolean selected = false;
	private Color white = new Color(null, 255, 255, 255);
	private Color black = new Color(null, 0, 0, 0);
	private Color selectionBackground = new Color(null, 43, 89, 190);
	private Image image = new Image(getDisplay(), "images/tagIcon.png");
	private Label textLabel;
	private TagPanel parentTagPanel;
	private int index;
	

	public TagItem(Composite parent, int style, int index, String tagName, TagPanel parentTagPanel) {
		super(parent, style);
		this.index = index;
		this.parentTagPanel = parentTagPanel;
		FormLayout layout = new FormLayout();
		setLayout(layout);
		Label imageLabel = new Label(this, SWT.None);
		imageLabel.setImage(image);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 15);
		imageLabel.setLayoutData(data);
		textLabel = new Label(this, SWT.None);;
		textLabel.setText(tagName);
		data = new FormData();
		data.left = new FormAttachment(imageLabel, 5);
		data.top = new FormAttachment(imageLabel, 0, SWT.CENTER);
		data.right = new FormAttachment(100, 0);
		textLabel.setLayoutData(data);

		imageLabel.addMouseListener(this);
		textLabel.addMouseListener(this);
		addMouseListener(this);
		
	}

	
	public void setSelected(boolean value) {
		selected = value;
		if (selected) {
			setBackground(selectionBackground);
			textLabel.setForeground(white);
			parentTagPanel.addToSelection(this);
		} else {
			setBackground(null);
			textLabel.setForeground(black);
			parentTagPanel.removeFromSelection(this);
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
	}

	@Override
	public void mouseDown(MouseEvent e) {
		if (((e.stateMask & SWT.CTRL) == SWT.CTRL) || ((e.stateMask & SWT.COMMAND) == SWT.COMMAND)) {
			setSelected(!selected);
		} else if ((e.stateMask & SWT.SHIFT) == SWT.SHIFT) {
			parentTagPanel.multipleSelection(this);
		} else {
			if (parentTagPanel.isSelectionEmpty()) {
				setSelected(!selected);
			} else {
				parentTagPanel.clearSelection();
				setSelected(!selected);
			}
		}
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
	}
	
	public int getIndex() {
		return index;
	}
	
	

}
