package hu.documaison.gui.commentstags;

import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Tag;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

public class TagViewer extends Composite implements SelectionListener {

	public TagViewer(Composite parent, int style, final Document doc) {
		super(parent, style);
		setLayout(new RowLayout(SWT.WRAP | SWT.HORIZONTAL));

		if (doc.getTags() == null || doc.getTags().size() == 0) {
			Label emptyLabel = new Label(this, SWT.WRAP);
			emptyLabel.setText("No tag...");
		} else {
			for (Tag tag : doc.getTags()) {
				Link link = new Link(this, SWT.None);
				link.setText(tag.getName() + "(<a>X</a>)");
				int[] rgb = ColorMap.get().getRGB(tag.getColorName());
				link.setBackground(new Color(getDisplay(), rgb[0], rgb[1],
						rgb[2]));
				link.setData(tag);
				link.addSelectionListener(this);
			}
		}

		Link addLink = new Link(this, SWT.NONE);
		addLink.setText("<a>Add new</a>");

		addLink.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				AddTagDialog ATD = new AddTagDialog();
				ATD.showAndHandle(getShell(), doc);
			}
		});

	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {

	}
}
