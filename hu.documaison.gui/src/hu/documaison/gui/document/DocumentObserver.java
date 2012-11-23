package hu.documaison.gui.document;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentObserver {

	private static HashMap<Integer, ArrayList<IDocumentChangeListener>> observers = new HashMap<Integer, ArrayList<IDocumentChangeListener>>();
	private static DocumentLister lister;

	public static void attach(int id, IDocumentChangeListener observer) {
		ArrayList<IDocumentChangeListener> list = observers.get(id);
		if (list == null) {
			list = new ArrayList<IDocumentChangeListener>();
			observers.put(id, list);
		}
		if (!list.contains(observer)) {
			list.add(observer);
		}
	}

	public static void detach(int id, IDocumentChangeListener observer) {
		ArrayList<IDocumentChangeListener> list = observers.get(id);
		if (list != null) {
			list.remove(observer);
		}
	}

	public static void notify(int id, IDocumentChangeListener caller) {
		ArrayList<IDocumentChangeListener> list = observers.get(id);
		if (list != null) {
			System.out.println("Notify " + list.size());
			for (IDocumentChangeListener l : list) {
				if (l != caller) {
					l.documentChanged();
				}
			}
		}
	}

	public static void setLister(DocumentLister lister) {
		DocumentObserver.lister = lister;
	}

	public static void detachAllDocumentItem() {
		ArrayList<IDocumentChangeListener> toRemove = new ArrayList<IDocumentChangeListener>();

		for (ArrayList<IDocumentChangeListener> lists : observers.values()) {
			for (IDocumentChangeListener l : lists) {
				if (l instanceof DocumentItem) {
					toRemove.add(l);
				}
			}
			lists.removeAll(toRemove);
			toRemove.clear();
		}

	}

	public static void notifyLister() {
		if (lister != null) {
			lister.notifyLister();
		}
	}

}
