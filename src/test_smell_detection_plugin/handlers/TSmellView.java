package test_smell_detection_plugin.handlers;

import java.io.FileNotFoundException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

public class TSmellView extends ViewPart {
	private TSmellTableViewer viewer;
	public TSmellView() {}

	/**
	 * Creates the viewer and initializes it.
	 */
	public void createPartControl(Composite parent) {
		try {
			viewer = new TSmellTableViewer(parent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Passes the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * Handles a 'close' event by disposing of the view.
	 */
	public void handleDispose() {
		this.getSite().getPage().hideView(this);
	}


}