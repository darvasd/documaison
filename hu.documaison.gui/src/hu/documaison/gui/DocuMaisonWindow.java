package hu.documaison.gui;

import javax.management.NotificationEmitter;

import hu.documaison.Application;
import hu.documaison.data.exceptions.InvalidFolderException;
import hu.documaison.data.exceptions.InvalidParameterException;
import hu.documaison.data.exceptions.SettingsAccessException;
import hu.documaison.gui.commentstags.TagPanel;
import hu.documaison.gui.document.DocumentLister;
import hu.documaison.indexing.AutomaticIndexing;
import hu.documaison.indexing.ConcreteIndexerInterceptor;
import hu.documaison.indexing.IndexerCollection;
import hu.documaison.indexing.IndexerFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

public class DocuMaisonWindow {

	private MenuItem refreshMenu;
	private MenuItem settingsMenu;
	private MenuItem closeMenu;
	private MenuItem newMenu;
	private MenuItem allDocs;
	private MenuItem search;
	private MenuItem manageDocTypes;
	private IndexerCollection collection;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DocuMaisonWindow w = new DocuMaisonWindow();
		w.showWindow();
	}

	private void showWindow() {
		Display.setAppName("DocuMaison");
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

		createMenus(shell);

		//Indexing
		try {
			collection = IndexerFactory.getAllIndexers(Application.getBll());
			collection.getDispatcher().register(new ConcreteIndexerInterceptor());
			AutomaticIndexing.setIndexers(collection);
			AutomaticIndexing.initialize();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					collection.refreshAll();
				}
			}).start();
		} catch (InvalidFolderException e1) {
			NotificationWindow.showError("Indexing error", "Faild to create indexer for one of the directories (+ "+e1+")");
		} catch (SettingsAccessException e1) {
			NotificationWindow.showError("Error", "Can't acces the settings file.");
		} catch (InvalidParameterException e1) {
			NotificationWindow.showError("Error", "Invalid parameter exception: " + e1.getMessage());
		}
		
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

	private void createMenus(Shell shell) {

		Menu menubar = new Menu(shell, SWT.BAR);
		MenuItem fileMenuHeader = new MenuItem(menubar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		refreshMenu = new MenuItem(fileMenu, SWT.PUSH);
		refreshMenu.setText("&Refresh indexers");

		settingsMenu = new MenuItem(fileMenu, SWT.PUSH);
		settingsMenu.setText("&Preferences");

		closeMenu = new MenuItem(fileMenu, SWT.PUSH);
		closeMenu.setText("&Close");

		Menu docMenu = new Menu(shell, SWT.DROP_DOWN);
		MenuItem docMenuHeader = new MenuItem(menubar, SWT.CASCADE);
		docMenuHeader.setText("&Documents");
		docMenuHeader.setMenu(docMenu);

		newMenu = new MenuItem(docMenu, SWT.PUSH);
		newMenu.setText("&Add new document");

		allDocs = new MenuItem(docMenu, SWT.PUSH);
		allDocs.setText("Show all documents");

		search = new MenuItem(docMenu, SWT.PUSH);
		search.setText("Advanced search");

		manageDocTypes = new MenuItem(docMenu, SWT.PUSH);
		manageDocTypes.setText("Manage document &types");

		shell.setMenuBar(menubar);
		addMenuListeners();
	}

	private void addMenuListeners() {
		settingsMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("settings");
			}
		});

		closeMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Display.getDefault().getActiveShell().dispose();
			}
		});

		newMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("newDocument");
			}
		});

		allDocs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DocumentLister lister = (DocumentLister) ViewManager
						.getDefault().getView("documents");
				TagPanel.clearSelection(false);
				lister.showAll();
				ViewManager.getDefault().showView(lister);
			}
		});

		search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("advancedSearch");
			}
		});

		manageDocTypes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ViewManager.getDefault().showView("doctypeeditor");
			}
		});
		
		refreshMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (collection != null) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							collection.refreshAll();
						}
					}).start();
				}
			}
		});

	}

}
