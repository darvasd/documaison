package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TagPanel extends Composite {

	private ITagProvider provider = null;
	
	public TagPanel(Composite parent, int style, ITagProvider provider) {
		super(parent, style);
		this.provider = provider;
		Color background = new Color(null, 228, 232, 237);
		setBackground(background);
		FormLayout layout = new FormLayout();
		layout.marginHeight = 10;
		setLayout(layout);
		Label tagsLabel = new Label(this, SWT.SHADOW_OUT);
		tagsLabel.setText("TAGS");
		FormData data = new FormData();
		data.left = new FormAttachment(0, 5);
		tagsLabel.setLayoutData(data);
		Color tagColor = new Color(null, 112, 126, 139);
		tagsLabel.setForeground(tagColor);
		final ScrolledComposite scrollComposite = new ScrolledComposite(this, SWT.V_SCROLL);
		data = new FormData();
		data.top = new FormAttachment(tagsLabel, 5);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		scrollComposite.setLayoutData(data);
		provider = new TestTagProvider();
		final Composite tagListComposite = new Composite(scrollComposite, SWT.NONE);
		TagItem preItem = null;
		tagListComposite.setLayout(new FormLayout());
		for(int i = 0; i <= provider.getTagCount(); i++) {
			TagItem item = new TagItem(tagListComposite, SWT.NONE, provider.getTag(i));
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
		tagListComposite.pack();
		scrollComposite.setContent(tagListComposite);
		scrollComposite.setExpandHorizontal(true);		
	}
	

}
