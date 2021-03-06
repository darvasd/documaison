package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class NotificationWindow {

	public static void showError(String title, String errorMessage) {
		MessageBox dialog = new MessageBox(Display.getDefault()
				.getActiveShell(), SWT.ICON_ERROR | SWT.OK);
		dialog.setText(title);
		dialog.setMessage(errorMessage);
		dialog.open();
	}
}
