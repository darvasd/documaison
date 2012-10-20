package hu.documaison.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class MultiPanel extends Composite {

	private HashMap<String, Composite> sheetMap = new HashMap<String, Composite>();
	private Composite currentSheet = null;
	private FormLayout layout = new FormLayout();
	
	public MultiPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(layout);
	}
	
	public void addSheet(Composite sheet, String id) {
		sheetMap.put(id, sheet);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		sheet.setLayoutData(data);
		if (currentSheet != null) {
			currentSheet.setVisible(false);
		}
		currentSheet = sheet;
		sheet.setVisible(true);
		layout();
	}
	
	public void selectSheet(String id) {
		Composite sheet = sheetMap.get(id);
		if (currentSheet != null) {
			currentSheet.setVisible(false);
		}
		currentSheet = sheet;
		sheet.setVisible(true);
		layout();
	}
	
	

}
