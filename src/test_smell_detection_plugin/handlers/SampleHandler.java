package test_smell_detection_plugin.handlers;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;

import com.github.javaparser.ParseException;

import assert_Roulette_Test_Smell.AssertRouletteTestMain;
import eager_Test_Smell.EagerMainClass;
import general_Fixture_Test_Smell.GeneralFixtureMainClass;
import indirect_Test_Smell.IndirectTestMainClass;
import sensitive_Equality_Test_Smell.SensititiveEqualitySmell;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ArrayList<OutputCollector> indirectOutput;
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		AllOutputs allOutput = AllOutputs.getInstance();
		
		 JFileChooser jf = new JFileChooser();
	     jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	     jf.showSaveDialog(null);
	     System.out.println(jf.getSelectedFile().getAbsolutePath());
	     String filePath = jf.getSelectedFile().getAbsolutePath();
	     
	     
			AssertRouletteTestMain assertRouteMain = new AssertRouletteTestMain();
			try {
				assertRouteMain.assertRoulettemain(filePath);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			EagerMainClass eagerMain = new EagerMainClass();
			eagerMain.EagerMain(filePath);
			
			GeneralFixtureMainClass generalfixturemain = new GeneralFixtureMainClass();
			try {
				generalfixturemain.generalfixturemain(filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IndirectTestMainClass indirectTestsmell = new IndirectTestMainClass();
			try {
				indirectOutput = indirectTestsmell.indirecttestmain(filePath);
				allOutput.setIndirects(indirectOutput);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        
			SensititiveEqualitySmell sensititive = new SensititiveEqualitySmell();
			try {
				sensititive.sensitivityMain(filePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		String filePath = "";
//		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
//		try {
//			String project = getCurrentProject(event);
//			filePath = project;
//			
//		} catch (  NullPointerException ex) {
//			ex.printStackTrace();
//			MessageDialog.openError(activeShell, "Test Smell Detector", "Please select a project");
//			return null;
//		}
		
		

		MessageDialog.openInformation(
				window.getShell(),
				"Smell Detection",
				"Successfully detected the Test Smells");
		
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if(page.findView("TestSmellDetector.view") != null) {
			IViewPart viewPart = page.findView("TestSmellDetector.view");
			TSmellView tSmellView = (TSmellView)viewPart;
			page.hideView(tSmellView);
		}
		try {
			page.showView("TestSmellDetector.view");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;

	}
//	private String getCurrentProject(ExecutionEvent e) {
//
//		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		 if (window != null)
//		 {
//		   IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
//		   Object firstElement = selection.getFirstElement();
//		   if (firstElement instanceof IAdaptable)
//		   {
//			 System.out.println(firstElement.getClass());
//			 Project project = (Project)((IAdaptable)firstElement).getAdapter(Project.class);
//			 System.out.println(project);
//		     IPath path = project.getFullPath();
//		     System.out.println(path);
//		   }
//		 }
//		return null;
//	}
}
