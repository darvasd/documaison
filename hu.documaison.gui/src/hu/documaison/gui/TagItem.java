package hu.documaison.gui;

import hu.documaison.data.entities.Tag;
import hu.documaison.gui.commentstags.ColorMap;

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
	private final Color white = new Color(null, 255, 255, 255);
	private final Color selectionBackground = new Color(null, 43, 89, 190);
	private final Image image = new Image(getDisplay(), "images/tagIcon.png");
	private final Label textLabel;
	private final TagPanel parentTagPanel;
	private final int index;
	private final Tag tag;

	public TagItem(Composite parent, int style, int index, Tag tag,
			TagPanel parentTagPanel) {
		super(parent, style);
		this.tag = tag;
		this.index = index;
		this.parentTagPanel = parentTagPanel;
		FormLayout layout = new FormLayout();
		setLayout(layout);
		Label imageLabel = new Label(this, SWT.None);
		imageLabel.setImage(image);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 15);
		imageLabel.setLayoutData(data);
		textLabel = new Label(this, SWT.None);
		textLabel.setText(tag.getName());
		int rgb[] = ColorMap.get().getRGB(tag.getColorName());
		if (rgb == null) {
			rgb = ColorMap.get().getRGB("Black");
		}
		textLabel
				.setForeground(new Color(getDisplay(), rgb[0], rgb[1], rgb[2]));
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
			int rgb[] = ColorMap.get().getRGB(tag.getColorName());
			if (rgb == null) {
				rgb = ColorMap.get().getRGB("Black");
			}
			textLabel.setForeground(new Color(getDisplay(), rgb[0], rgb[1],
					rgb[2]));
			parentTagPanel.removeFromSelection(this);
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
	}

	@Override
	public void mouseDown(MouseEvent e) {
		if (((e.stateMask & SWT.CTRL) == SWT.CTRL)
				|| ((e.stateMask & SWT.COMMAND) == SWT.COMMAND)) {
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
