package hu.documaison.gui;

public class ViewManager {

	private static ViewManager instance = null;
	private MultiPanel viewer = null;

	private ViewManager() {
		// singleton
	}

	public static ViewManager getDefault() {
		if (instance == null) {
			instance = new ViewManager();
		}
		return instance;
	}

	public void setViewer(MultiPanel viewer) {
		this.viewer = viewer;
	}

	public void showView(String id) {
		if (viewer != null) {
			viewer.selectSheet(id);
		}
	}

	public InnerPanel showView(InnerPanel view) {
		if (viewer != null) {
			return viewer.selectSheet(view);
		}
		return null;
	}

	public InnerPanel showView(String id, InnerPanel previous) {
		if (viewer != null) {
			return viewer.selectSheet(id, previous);
		}
		return null;
	}

}
