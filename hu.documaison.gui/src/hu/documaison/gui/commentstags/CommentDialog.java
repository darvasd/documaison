package hu.documaison.gui.commentstags;

import hu.documaison.Application;
import hu.documaison.data.entities.Comment;
import hu.documaison.data.entities.Document;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CommentDialog {

	public void showAndHandle(Shell parent, final Document document) {

		Shell dialog = new Shell(parent, SWT.APPLICATION_MODAL | SWT.BORDER
				| SWT.CLOSE | SWT.TITLE);
		dialog.setText("Add new metadata");
		dialog.setSize(300, 300);
		dialog.setLayout(new FillLayout());

		Composite composite = new Composite(dialog, SWT.NONE);
		composite.setLayout(new FormLayout());

		Button storeCommentBtn = new Button(composite, SWT.PUSH);
		storeCommentBtn.setText("Save");
		FormData data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.right = new FormAttachment(100, 0);
		storeCommentBtn.setLayoutData(data);

		final Text newComment = new Text(composite, SWT.BORDER | SWT.SINGLE);
		newComment.setToolTipText("New comment");
		data = new FormData();
		data.top = new FormAttachment(storeCommentBtn, 0, SWT.CENTER);
		data.right = new FormAttachment(storeCommentBtn, 0);
		data.left = new FormAttachment(5, 0);
		newComment.setLayoutData(data);

		final Text viewer = new Text(composite, SWT.MULTI | SWT.READ_ONLY
				| SWT.V_SCROLL | SWT.WRAP | SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(newComment, -10);
		viewer.setLayoutData(data);

		loadComments(viewer, document);

		storeCommentBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				storeComment(newComment.getText(), document);
				loadComments(viewer, document);
				newComment.setText("");
			}

		});
		dialog.open();

		// Create and check the event loop
		while (!dialog.isDisposed()) {
			if (!dialog.getDisplay().readAndDispatch())
				dialog.getDisplay().sleep();
		}

	}

	private void loadComments(Text viewer, Document doc) {
		viewer.setText("");
		doc = Application.getBll().getDocument(doc.getId()); // get new document
																// data from DB
		for (Comment c : doc.getCommentCollection()) {
			String commentDate = DateFormat
					.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,
							new Locale("hu", "HU")).format(c.getCreatedDate());

			viewer.setText(viewer.getText() + c.getMessage() + " ("
					+ commentDate + ")\n\n");
		}
	}

	private void storeComment(String comment, Document doc) {
		Comment com = Application.getBll().createComment(doc);
		com.setCreatedDate(new Date());
		com.setMessage(comment);
		Application.getBll().updateComment(com);
	}
}
