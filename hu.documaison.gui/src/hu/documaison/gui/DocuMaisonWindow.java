package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

public class DocuMaisonWindow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DocuMaisonWindow w = new DocuMaisonWindow();
		w.showWindow();
	}

	private void showWindow() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		prepareIcons(shell);
		shell.setLayout(new FormLayout());
		final Sash sash = new Sash(shell, SWT.VERTICAL | SWT.SMOOTH);

		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 230);
		sash.setLayoutData(data);

		LeftPanel tagPanel = new LeftPanel(shell, SWT.BORDER | SWT.WRAP);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(sash, 0);
		tagPanel.setLayoutData(data);

		RightPanel rightPanel = new RightPanel(shell, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(sash, 0);
		data.right = new FormAttachment(100, 0);
		rightPanel.setLayoutData(data);

		sash.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				sash.setBounds(e.x, e.y, e.width, e.height);

				FormData formData = new FormData();
				formData.top = new FormAttachment(0, 0);
				formData.left = new FormAttachment(0, e.x);
				formData.bottom = new FormAttachment(100, 0);
				formData.height = 3;
				sash.setLayoutData(formData);
				shell.layout(true);
			}
		});

		shell.open();

		// Create and check the event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void prepareIcons(Shell window) {
		Display display = window.getDisplay();
		Image icon16 = new Image(display, "images/icon_16x16.png");
		Image icon32 = new Image(display, "images/icon_32x32.png");
		Image icon128 = new Image(display, "images/icon_128x128.png");
		window.setImages(new Image[] { icon16, icon32, icon128 });
	}

}
