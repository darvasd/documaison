package hu.documaison.gui.search;

import hu.documaison.Application;
import hu.documaison.data.search.BoolOperator;
import hu.documaison.data.search.SearchExpression;
import hu.documaison.gui.InnerPanel;
import hu.documaison.gui.NotifactionWindow;
import hu.documaison.gui.ViewManager;
import hu.documaison.gui.document.DocumentLister;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class AdvancedSearchPanel extends InnerPanel {

	private AdvancedSearchField firstSearch;
	private ArrayList<AdvancedSearchField> searchFields;
	private Label operator1;
	private Combo operatorCombo;
	private Button searchButton;

	public AdvancedSearchPanel(Composite parent, int style) {
		super(parent, style, "Advanced search");
	}

	@Override
	protected void createComposite() {
		searchFields = new ArrayList<AdvancedSearchField>();
		operator1 = new Label(this, SWT.None);
		operator1.setText("The following filters will be used, with an");
		FormData data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(0, 10);
		operator1.setLayoutData(data);

		operatorCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		data = new FormData();
		data.top = new FormAttachment(operator1, 0, SWT.CENTER);
		data.left = new FormAttachment(operator1, 5);
		operatorCombo.setLayoutData(data);
		operatorCombo.add("AND");
		operatorCombo.add("OR");
		operatorCombo.select(0);

		Label operator2 = new Label(this, SWT.None);
		operator2.setText("operator:");
		data = new FormData();
		data.top = new FormAttachment(titleLabel, 25);
		data.left = new FormAttachment(operatorCombo, 5);
		operator2.setLayoutData(data);

		firstSearch = new AdvancedSearchField(this, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(operator1, 25);
		data.left = new FormAttachment(0, 5);
		data.right = new FormAttachment(100, -5);
		firstSearch.setLayoutData(data);
		searchFields.add(firstSearch);

		searchButton = new Button(this, SWT.PUSH);
		searchButton.setText("Search");
		data = new FormData();
		data.bottom = new FormAttachment(100, -30);
		data.right = new FormAttachment(100, -50);
		searchButton.setLayoutData(data);
		addEventListeners();
	}

	private void clearAll() {

	}

	private void addEventListeners() {
		item.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				clearAll();
			}

		});

		firstSearch.addPlusListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final AdvancedSearchField asf = new AdvancedSearchField(
						AdvancedSearchPanel.this, SWT.NONE);
				FormData data = new FormData();
				AdvancedSearchField last = searchFields.get(searchFields.size() - 1);
				data.top = new FormAttachment(last, 0);
				data.left = new FormAttachment(0, 5);
				data.right = new FormAttachment(100, -5);
				asf.setLayoutData(data);
				searchFields.add(asf);
				layout();
				asf.addPlusListener(this);
				asf.addRemoveListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						removeListener(asf);
					}

				});
				checkRemoveBtns();
			}

		});

		firstSearch.addRemoveListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				removeListener(firstSearch);
			}

		});

		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SearchExpression expr = SearchExpressionBuilder
						.createExpression(searchFields, getBoolOperator());
				DocumentLister lister = (DocumentLister) ViewManager
						.getDefault().getView("documents");
				lister.advancedSearch(expr);
				ViewManager.getDefault().showView(lister);
			}
		});

	}

	private void checkRemoveBtns() {
		if (searchFields.size() > 1) {
			for (AdvancedSearchField field : searchFields) {
				field.setRemoveVisibility(true);
			}
		} else {
			for (AdvancedSearchField field : searchFields) {
				field.setRemoveVisibility(false);
			}
		}
	}

	private void removeListener(AdvancedSearchField field) {
		AdvancedSearchField next = null;
		AdvancedSearchField prev = null;
		for (int i = 0; i < searchFields.size() - 1; i++) {
			if (searchFields.get(i) == field) {
				next = searchFields.get(i + 1);
				if (i > 0) {
					prev = searchFields.get(i - 1);
				}
			}
		}
		if (next != null) {
			FormData data = (FormData) next.getLayoutData();
			if (prev != null) {
				data.top = new FormAttachment(prev, 0);
			} else {
				data.top = new FormAttachment(operator1, 25);
			}
		}
		searchFields.remove(field);
		field.dispose();
		checkRemoveBtns();
		layout();
	}

	private BoolOperator getBoolOperator() {
		if (operatorCombo.getItem(operatorCombo.getSelectionIndex())
				.equalsIgnoreCase("and")) {
			return BoolOperator.and;
		} else {
			return BoolOperator.or;
		}
	}

	@Override
	public void showed() {
		if (Application.getBll().getAllMetadataKeys().size() == 0) {
			ViewManager.getDefault().showView("documents");
		} else {
			for (int i = searchFields.size() - 1; i > 0; i--) {
				removeListener(searchFields.get(i));
			}
			searchFields.get(0).reset();
		}
	}

}
