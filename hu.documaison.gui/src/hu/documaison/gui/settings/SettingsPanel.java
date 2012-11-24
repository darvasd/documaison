package hu.documaison.gui.settings;

import hu.documaison.Application;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.NotificationWindow;
import hu.documaison.indexing.AutomaticIndexing;
import hu.documaison.indexing.IndexerFactory;
import hu.documaison.settings.SettingsData;
import hu.documaison.settings.SettingsManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SettingsPanel extends InnerPanel{

	private Button addFolderBtn;
	private Button removeFolderBtn;
	private List indexingFolders;
	private Button browseBtn;
	private Text dbText;
	private Button indexingCheck;
	private Label indexingLabel;

	public SettingsPanel(Composite parent, int style) {
		super(parent, style, "Settings");
	}

	@Override
	protected void createComposite() {
		SettingsData settings = new SettingsData();
		try {
			settings = SettingsManager.getCurrentSettings();
		} catch (Exception e) {
			NotificationWindow.showError("File error", "Failed to load the application's settings.");
		}
		
		indexingCheck = new Button(this, SWT.CHECK);
		indexingCheck.setText("Enable automatic indexing of documents. Manual refresh can be done from the File menu anytime.");
		indexingCheck.setSelection(settings.isIndexingEnabled());
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 20);
		data.left = new FormAttachment(0, 10);
		indexingCheck.setLayoutData(data);
		
		indexingLabel = new Label(this, SWT.NONE);
		indexingLabel.setText("The application will monitor the following folders:");
		data = new FormData();
		data.top = new FormAttachment(indexingCheck, 25);
		data.left = new FormAttachment(0, 10);
		indexingLabel.setLayoutData(data);
		
		addFolderBtn = new Button(this, SWT.PUSH);
		addFolderBtn.setText("Add");
		data = new FormData();
		data.top = new FormAttachment(indexingLabel, 0, SWT.CENTER);
		data.right = new FormAttachment(100, -10);
		addFolderBtn.setLayoutData(data);
		
		removeFolderBtn = new Button(this, SWT.PUSH);
		removeFolderBtn.setText("Remove");
		removeFolderBtn.setEnabled(false);
		data = new FormData();
		data.top = new FormAttachment(indexingLabel, 0, SWT.CENTER);
		data.right = new FormAttachment(addFolderBtn, 0);
		removeFolderBtn.setLayoutData(data);
		
		indexingFolders = new List(this, SWT.BORDER | SWT.V_SCROLL);
		
		for (String s : settings.getIndexedFolders()) {
			indexingFolders.add(s);
		}
		
		data = new FormData();
		data.top = new FormAttachment(indexingLabel, 10);
		data.left = new FormAttachment(0, 10);
		data.height = 150;
		data.right = new FormAttachment(100, -10);
		indexingFolders.setLayoutData(data);
		
		Label dbLabel = new Label(this, SWT.None);
		dbLabel.setText("Document database file location:");
		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.top = new FormAttachment(indexingFolders, 50);
		dbLabel.setLayoutData(data);
		
		browseBtn = new Button(this, SWT.None);
		browseBtn.setText("Browse");
		data = new FormData();
		data.right = new FormAttachment(100, -10);
		data.top = new FormAttachment(dbLabel, 5);
		browseBtn.setLayoutData(data);
		
		dbText = new Text(this, SWT.BORDER);
		dbText.setEditable(false);
		dbText.setText(settings.getDatabaseFileLocation());
		data = new FormData();
		data.left = new FormAttachment(0, 10);
		data.top = new FormAttachment(browseBtn, 0, SWT.CENTER);
		data.right = new FormAttachment(browseBtn, -5);
		dbText.setLayoutData(data);
		
		createEventHandlers();
	}
	
	private void createEventHandlers() {
		indexingCheck.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				try {
					SettingsData settings = SettingsManager.getCurrentSettings();
					settings.setIndexingEnabled(indexingCheck.getSelection());
					SettingsManager.storeSettings(settings);
				} catch (Exception ex) {
					indexingCheck.setSelection(false);
					NotificationWindow.showError("Error", "Failed to modify application settings.");
				}
				if (indexingCheck.getSelection()) {
					setIndexing(true);
				} else {
					setIndexing(false);
				}
			}
		});
		browseBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				DirectoryDialog dd = new DirectoryDialog(getShell());
				dd.setText("Select database file location...");
				dd.setFilterPath(dbText.getText());
				String loc = dd.open();
				if (loc != null) {
					String oldLoc = dbText.getText();
					dbText.setText(loc);
					try {
						SettingsData settings = SettingsManager.getCurrentSettings();
						settings.setDatabaseFileLocation(loc);
						SettingsManager.storeSettings(settings);
					} catch (Exception e1) {
						dbText.setText(oldLoc);
						NotificationWindow.showError("Error", "Failed to modify application settings.");
					}
				}
			}
		});
		indexingFolders.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (indexingFolders.getSelectionCount() == 0) {
					removeFolderBtn.setEnabled(false);
				} else {
					removeFolderBtn.setEnabled(true);
				}
			}
		});
		addFolderBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				DirectoryDialog dd = new DirectoryDialog(getShell());
				dd.setText("Select a folder to monitor");
				String loc = dd.open();
				if (loc != null) {
					addFolder(loc);
				}
			}
		});
		removeFolderBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String[] selection = indexingFolders.getSelection();
				for (int i = 0; i < selection.length; i++) {
					removeFolder(selection[i]);
				}
				removeFolderBtn.setEnabled(false);
				indexingFolders.setSelection(-1);
			}
		});

	}
	
	private void setIndexing(boolean enabled) {		
		if (enabled) {
			AutomaticIndexing.enableAutomaticIndexing();
		} else {
			AutomaticIndexing.disableAutomaticIndexing();
		}
	}
	
	private void addFolder(String path) {
		try {
			SettingsData data = SettingsManager.getCurrentSettings();
			if (!data.getIndexedFolders().contains(path)) {
				indexingFolders.add(path);
				data.getIndexedFolders().add(path);
			}
			SettingsManager.storeSettings(data);
			AutomaticIndexing.getIndexers().addIndexer(IndexerFactory.getIndexing(path, data.getComputerId(), Application.getBll()));
		} catch (Exception e) {
			NotificationWindow.showError("Error", "Failed to modify application settings.");
		}
	}
	
	private void removeFolder(String path) {
		try {
			SettingsData data = SettingsManager.getCurrentSettings();
			if (data.getIndexedFolders().contains(path)) {
				data.getIndexedFolders().remove(path);
			}
			SettingsManager.storeSettings(data);
			indexingFolders.remove(path);
			AutomaticIndexing.getIndexers().removeIndexer(path, data.getComputerId());
		} catch (Exception e) {
			NotificationWindow.showError("Error", "Failed to modify application settings.");
		}
	}

}
