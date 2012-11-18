package hu.documaison.gui.commentstags;

import hu.documaison.Application;
import hu.documaison.data.entities.Document;
import hu.documaison.data.entities.Tag;
import hu.documaison.data.exceptions.UnknownDocumentException;
import hu.documaison.data.exceptions.UnknownTagException;
import hu.documaison.gui.NotifactionWindow;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

public class TagViewer extends Composite implements SelectionListener {

	private final ArrayList<Control> controls = new ArrayList<Control>();

	public TagViewer(Composite parent, int style) {
		super(parent, style);
		RowLayout layout = new RowLayout();
		layout.wrap = true;
		setLayout(layout);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {

	}

	public void createControls(final Document doc) {

		Document updatedDoc = doc;
		try {
			updatedDoc = Application.getBll().getDocument(doc.getId());
		} catch (UnknownDocumentException e1) {
			NotifactionWindow.showError("Database error",
					"Failed to update the document from the database.");
		}

		for (Control c : controls) {
			c.dispose();
		}

		controls.clear();

		if (updatedDoc.getTags() == null || updatedDoc.getTags().size() == 0) {
			Label emptyLabel = new Label(this, SWT.WRAP);
			emptyLabel.setText("No tags...");
			controls.add(emptyLabel);
		} else {
			for (Tag tag : updatedDoc.getTags()) {
				try {
					tag = Application.getBll().getTag(tag.getId());
				} catch (UnknownTagException e1) {
					NotifactionWindow.showError("Database error",
							"Failed to update tag from database.");
				}
				Link link = new Link(this, SWT.None);
				controls.add(link);
				link.setText(tag.getName() + " (<a>X</a>)");
				int[] rgb = ColorMap.get().getRGB(tag.getColorName());
				if (rgb == null) {
					rgb = ColorMap.get().getRGB("Black");
				}
				link.setForeground(new Color(getDisplay(), rgb[0], rgb[1],
						rgb[2]));
				link.setData(tag);
				link.addSelectionListener(this);
			}
		}

		Link addLink = new Link(this, SWT.NONE);
		controls.add(addLink);
		addLink.setText("<a>Add new</a>");

		addLink.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				AddTagDialog ATD = new AddTagDialog();
				ATD.showAndHandle(getShell(), doc);
				createControls(doc);
			}
		});

		layout();
	}
}
