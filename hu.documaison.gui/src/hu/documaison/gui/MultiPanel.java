package hu.documaison.gui;

import java.util.HashMap;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

public class MultiPanel extends Composite {

	private final HashMap<String, InnerPanel> sheetMap = new HashMap<String, InnerPanel>();
	private Composite currentSheet = null;
	private final FormLayout layout = new FormLayout();

	public MultiPanel(Composite parent, int style) {
		super(parent, style);
		setLayout(layout);
	}

	public void addSheet(InnerPanel sheet, String id) {
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
		sheet.showed();
		layout();
	}

	public InnerPanel selectSheet(String id) {
		InnerPanel sheet = sheetMap.get(id);
		return selectSheet(sheet);
	}

	public InnerPanel selectSheet(InnerPanel sheet) {
		if (currentSheet != null) {
			currentSheet.setVisible(false);
		}
		currentSheet = sheet;
		sheet.setPreviousPanel(null);
		sheet.setVisible(true);
		sheet.showed();
		layout();
		return sheet;
	}

	public InnerPanel selectSheet(String id, InnerPanel previous) {
		InnerPanel sheet = selectSheet(id);
		sheet.setPreviousPanel(previous);
		sheet.showed();
		return sheet;
	}

	public InnerPanel getSheet(String id) {
		return sheetMap.get(id);
	}
}
