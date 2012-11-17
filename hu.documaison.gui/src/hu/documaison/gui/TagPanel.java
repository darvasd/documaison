package hu.documaison.gui;

import hu.documaison.Application;
import hu.documaison.data.entities.Tag;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TagPanel extends Composite implements ITagSelectionChangeListener {

	private final ArrayList<TagItem> selectedItems = new ArrayList<TagItem>();
	private final ArrayList<TagItem> items = new ArrayList<TagItem>();
	private final Label tagsLabel;
	private final ScrolledComposite scrollComposite;
	private final Composite tagListComposite;
	private final ArrayList<ITagSelectionChangeListener> changeListeners = new ArrayList<ITagSelectionChangeListener>();
	private final Button clearSelectionButton;

	public TagPanel(Composite parent, int style) {
		super(parent, style);
		FormLayout layout = new FormLayout();
		setLayout(layout);
		tagsLabel = new Label(this, SWT.SHADOW_OUT);
		tagsLabel.setText("TAGS");
		FormData data = new FormData();
		data.left = new FormAttachment(0, 5);
		tagsLabel.setLayoutData(data);
		Color tagColor = new Color(null, 112, 126, 139);
		tagsLabel.setForeground(tagColor);
		scrollComposite = new ScrolledComposite(this, SWT.V_SCROLL);
		data = new FormData();
		data.top = new FormAttachment(tagsLabel, 5);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		scrollComposite.setLayoutData(data);
		tagListComposite = new Composite(scrollComposite, SWT.NONE);
		TagItem preItem = null;
		tagListComposite.setLayout(new FormLayout());
		Collection<Tag> tags = Application.getBll().getTags();
		int i = 0;
		for (Tag tag : tags) {
			if (tag.getName() != null && tag.getColorName() != null) {
				TagItem item = new TagItem(tagListComposite, SWT.NONE, i++,
						tag, this);
				items.add(item);
				data = new FormData();
				data.left = new FormAttachment(0, 0);
				data.right = new FormAttachment(100, 0);
				if (preItem != null) {
					data.top = new FormAttachment(preItem, 0);
				} else {
					data.top = new FormAttachment(0, 0);
				}
				item.setLayoutData(data);
				preItem = item;
			}
		}
		tagListComposite.pack();
		scrollComposite.setContent(tagListComposite);
		scrollComposite.setExpandHorizontal(true);
		clearSelectionButton = new Button(this, SWT.NONE);
		clearSelectionButton.setVisible(false);
		clearSelectionButton.setText("Clear selection");
		clearSelectionButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				clearSelection();
			}

		});
		addChangeListener(this);
	}

	public void addToSelection(TagItem item) {
		selectedItems.add(item);
		notifyListeners();
	}

	public void removeFromSelection(TagItem item) {
		selectedItems.remove(item);
		notifyListeners();
	}

	public void clearSelection() {
		ArrayList<TagItem> selectedItems_temp = new ArrayList<TagItem>(
				selectedItems);
		for (TagItem i : selectedItems_temp) {
			i.setSelected(false);
		}
		notifyListeners();
	}

	public void multipleSelection(TagItem last) {
		int a = last.getIndex();
		int b;
		if (isSelectionEmpty()) {
			b = 0;
		} else {
			b = selectedItems.get(selectedItems.size() - 1).getIndex();
		}
		int start, end;
		if (a < b) {
			start = a;
			end = b;
		} else {
			start = b;
			end = a;
		}
		for (int i = start; i <= end; i++) {
			items.get(i).setSelected(true);
		}
	}

	public boolean isSelectionEmpty() {
		return (selectedItems.size() == 0);
	}

	private void notifyListeners() {
		for (ITagSelectionChangeListener listener : changeListeners) {
			listener.selectionChanged();
		}
	}

	public void addChangeListener(ITagSelectionChangeListener listener) {
		changeListeners.add(listener);
	}

	public void removeChangeListener(ITagSelectionChangeListener listenr) {
		changeListeners.remove(listenr);
	}

	@Override
	public void selectionChanged() {
		if (clearSelectionButton.isVisible() == false && !isSelectionEmpty()) {
			((FormLayout) this.getLayout()).marginBottom = 0;
			FormData data = new FormData();
			data.bottom = new FormAttachment(100, 0);
			data.left = new FormAttachment(0, 10);
			data.right = new FormAttachment(100, -10);
			data.height = 25;
			clearSelectionButton.setLayoutData(data);
			clearSelectionButton.setVisible(true);
			data = new FormData();
			data.top = new FormAttachment(tagsLabel, 5);
			data.bottom = new FormAttachment(clearSelectionButton, -5);
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			scrollComposite.setLayoutData(data);
			this.layout();
		} else if (isSelectionEmpty()) {
			clearSelectionButton.setVisible(false);
			FormData data = new FormData();
			data.top = new FormAttachment(tagsLabel, 5);
			data.bottom = new FormAttachment(100, 0);
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			scrollComposite.setLayoutData(data);
			this.layout();
		}
	}

}
