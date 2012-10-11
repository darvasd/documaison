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

public class TagItem extends GradientComposite implements MouseListener {
	
	private boolean selected = false;
	private Color gradientStart = new Color(null, 122, 186, 225);
	private Color gradientEnd = new Color(null, 56, 125, 197);
	private Color white = new Color(null, 255, 255, 255);
	private Color black = new Color(null, 0, 0, 0);
	private Color background = new Color(null, 228, 232, 237);
	private Image image = new Image(getDisplay(), "images/tagIcon.png");
	private Label textLabel;

	public TagItem(Composite parent, int style, String tagName) {
		super(parent, style);
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
		setDefaultBackground(background);
		
		imageLabel.addMouseListener(this);
		textLabel.addMouseListener(this);
		addMouseListener(this);
		
	}

	
	public void setSelected(boolean value) {
		if (selected) {
			setGradientEnabled(false);
			textLabel.setForeground(black);
		} else {
			setGradientEnabled(true);
			setGradientStart(gradientStart);
			setGradientEnd(gradientEnd);
			textLabel.setForeground(white);
		}
		selected = value;
		redrawComposite();
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		if (selected == true) {
			setSelected(false);
		} else {
			setSelected(true);
		}		
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
	}
	
	

}
