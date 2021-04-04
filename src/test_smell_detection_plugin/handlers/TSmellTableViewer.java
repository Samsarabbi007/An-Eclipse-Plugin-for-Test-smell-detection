package test_smell_detection_plugin.handlers;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;



public class TSmellTableViewer {

	/**
	 * Constructor for the table viewer.
	 * 
	 * @param parent
	 * @throws FileNotFoundException
	 */
	public TSmellTableViewer(Composite parent) throws FileNotFoundException {

		this.addChildControls(parent);
	}

	private static Table table = null;
	private TableViewer tableViewer;
	private Button closeButton;

	// Set the table column property names
	private final String FILES_COLUMN = "files";
	private final String TEST_SMELLS_COLUMN = "test smells";
	private final String DETECTED_COLUMN = "detected";
	private final String DESCRIPTION_COLUMN = "description";

	// Set column names
	private String[] columnNames = new String[] { FILES_COLUMN, TEST_SMELLS_COLUMN, DETECTED_COLUMN,
			DESCRIPTION_COLUMN };

	/**
	 * Main method to launch the window.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		Shell shell = new Shell();
		shell.setText("Test Smell Detector");

		// Set layout for shell
		GridLayout layout = new GridLayout();
		shell.setLayout(layout);

		// Create a composite to hold the children
		Composite composite = new Composite(shell, SWT.NONE);

		final TSmellTableViewer tableViewer = new TSmellTableViewer(composite);

		tableViewer.getControl().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				tableViewer.dispose();
			}
		});

		shell.open();
		tableViewer.run(shell);
	}

	/**
	 * Runs and waits for a close event.
	 * 
	 * @param shell
	 */
	private void run(Shell shell) {

		// Add a listener for the close button
		closeButton.addSelectionListener(new SelectionAdapter() {

			// Close the view i.e. dispose of the composite's parent
			public void widgetSelected(SelectionEvent e) {
				table.getParent().getParent().dispose();
			}
		});

		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Creates the layout, table, and table viewer.
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	private void addChildControls(Composite composite) throws FileNotFoundException {

		// Create a composite to hold the children
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		composite.setLayoutData(gridData);

		// Create the table
		createTable(composite);

		// Create and setup the TableViewer
		createTableViewer();

	}

	/**
	 * Creates the table with a grid of columns.
	 * Displays all the detection results into the columns.
	 * 
	 * @param parent
	 */
	private void createTable(Composite parent) throws FileNotFoundException {

		AllOutputs allOutput = AllOutputs.getExistingInstance();

		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

		table = new Table(parent, style);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.CENTER, 0);
		column.setText("Test Smell");
		column.setWidth(200);

		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("File Path");
		column.setWidth(500);

		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText("File Name");
		column.setWidth(500);
		
		column = new TableColumn(table, SWT.LEFT, 3);
		column.setText("Line Number");
		column.setWidth(500);
		
		ArrayList<OutputCollector> indirect = allOutput.getIndirects();
		ArrayList<OutputCollector> eager = allOutput.getEagers();
		ArrayList<OutputCollector> gf = allOutput.getGfixtures();
		ArrayList<OutputCollector> asr = allOutput.getAssertions();
		ArrayList<OutputCollector> sens = allOutput.getSensitivities();
		
		for (OutputCollector el : asr) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(
					new String[] { el.smellType, el.path, el.fileName, el.lineNo });
		}
		
		for (OutputCollector el : sens) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(
					new String[] { el.smellType, el.path, el.fileName, el.lineNo });
		}
		
		for (OutputCollector el : indirect) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(
					new String[] { el.smellType, el.path, el.fileName, el.lineNo });
		}
		for (OutputCollector el : eager) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(
					new String[] { el.smellType, el.path, el.fileName, el.lineNo });
		}
		
		for (OutputCollector el : gf) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(
					new String[] { el.smellType, el.path, el.fileName, el.lineNo });
		}

//		if (collectionObj != null) {
			
			
			
//			while (detection != null) {
//				try {
//					String[] tempSmellNames = detection.getSmellNames();
//					String[] tempProdFilePaths = (String[]) detection.getProdFilePath();
//					String[] tempTestFilePaths = (String[]) detection.getTestFilePath();
//					if (!tempProdFilePaths.equals(null))
//						if (!tempTestFilePaths.equals(null))
//							for (int i = 0; i < tempSmellNames.length; i++) {
//								TableItem item = new TableItem(table, SWT.NONE);
//								item.setText(
//										new String[] { tempSmellNames[i], tempProdFilePaths[i], tempTestFilePaths[i] });
//							}
//				} catch (NullPointerException e) {
//					//pass
//				}
//				detection = collectionObj.getNextDetection();
			//}
//		}
	}

	/**
	 * Creates the TableViewer.
	 */
	private void createTableViewer() {

		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(columnNames);
	}

	/**
	 * Closes the window and disposes of resources.
	 */
	public void close() {
		Shell shell = table.getShell();

		if (shell != null && !shell.isDisposed())
			shell.dispose();
	}
	
	/**
	 * Releases resources.
	 */
	public void dispose() {
		tableViewer.getLabelProvider().dispose();
	}

	/**
	 * Returns the column names in a collection.
	 * 
	 * @return List containing column names
	 */
	public java.util.List<String> getColumnNames() {
		return Arrays.asList(columnNames);
	}

	/**
	 * Gets currently selected item in the table viewer.
	 * 
	 * @return currently selected item
	 */
	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	/**
	 * Returns the parent composite.
	 */
	public Control getControl() {
		return table.getParent();
	}
	
	/**
	 * Get an instance of the test smell table.
	 * @return
	 */
	public static Table getTSmellTable() {
		return table;
	}
}
