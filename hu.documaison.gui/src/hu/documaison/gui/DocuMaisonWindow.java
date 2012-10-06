package hu.documaison.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
		shell.setLayout(new FormLayout());
		final Sash sash = new Sash(shell, SWT.VERTICAL);
		
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
		
		sash.addListener(SWT.Selection, new Listener () {
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

}
