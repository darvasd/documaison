package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.mihalis.opal.starRating.StarRating;
import org.mihalis.opal.starRating.StarRating.SIZE;

public class DocumentItem extends Composite {

	private Color background = new Color(null, 240, 240, 240);
	private Color tagColor = new Color(null, 102, 102, 102);
	
	public DocumentItem(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		setBackground(background);
		Canvas icon = new Canvas(this, SWT.BORDER);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 10);
		data.left = new FormAttachment(0, 30);		
		data.width = 80;
		data.height = 93;
		Image unknown = new Image(null, "images/unknown.png");
		icon.setBackgroundImage(unknown);
		icon.setLayoutData(data);
		
		
		Canvas star = new Canvas(this, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(0, 10);
		data.right = new FormAttachment(icon, -5);
		data.width = 20;
		data.height = 18;
		Image unstarred = new Image(null, "images/unstarred.png");
		star.setBackgroundImage(unstarred);
		star.setLayoutData(data);

		Button checkBox = new Button(this, SWT.CHECK);
		data = new FormData();
		data.top = new FormAttachment(star, 5);
		data.right = new FormAttachment(icon, -2);		
		checkBox.setLayoutData(data);
		
		Label titleLabel = new Label(this, SWT.NONE);
		titleLabel.setText("Title of the document");
		FontData[] fontData = titleLabel.getFont().getFontData();
		fontData[0].setHeight(14);
		fontData[0].setStyle(SWT.BOLD);
		data = new FormData();
		data.top = new FormAttachment(0, 20);
		data.left = new FormAttachment(icon, 10);
		titleLabel.setFont(new Font(null, fontData[0]));
		titleLabel.setLayoutData(data);
		
		Label authorLabel = new Label(this, SWT.NONE);
		authorLabel.setText("Author");
		data = new FormData();
		data.top = new FormAttachment(titleLabel, 10);
		data.left = new FormAttachment(icon, 10);
		authorLabel.setLayoutData(data);
		fontData = authorLabel.getFont().getFontData();
		fontData[0].setStyle(SWT.ITALIC);
		authorLabel.setFont(new Font(null, fontData[0]));
		
		Label tagLabel = new Label(this, SWT.NONE);
		tagLabel.setText("Test document, random tag, documaison, ...");
		tagLabel.setForeground(tagColor);
		data = new FormData();
		data.bottom = new FormAttachment(icon, 88);
		data.left = new FormAttachment(icon, 10);
		tagLabel.setLayoutData(data);
		
		Button openButton = new Button(this, SWT.PUSH);
		openButton.setText("Open");
		Image openImage = new Image(null, "images/open.png");
		openButton.setImage(openImage);
		data = new FormData();
		data.top = new FormAttachment(icon, 0, SWT.CENTER);
		data.right = new FormAttachment(100, -70);
		data.height = 45;
		openButton.setLayoutData(data);
		
		Button otherActions = new Button(this, SWT.PUSH);
		data = new FormData();
		data.top = new FormAttachment(icon, 0, SWT.CENTER);
		data.left = new FormAttachment(openButton, 2);
		data.height = 45;
		data.width = 30;
		otherActions.setLayoutData(data);
		Image downArrowImage = new Image(null, "images/down_arrow.png");
		otherActions.setImage(downArrowImage);
	}



}
