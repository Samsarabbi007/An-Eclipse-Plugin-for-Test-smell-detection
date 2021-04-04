package mainConnector;
import java.io.File;
import java.io.IOException;

import com.github.javaparser.ParseException;

import eager_Test_Smell.EagerMainClass;
import general_Fixture_Test_Smell.GeneralFixtureMainClass;
import indirect_Test_Smell.IndirectTestMainClass;
import assert_Roulette_Test_Smell.AssertRouletteTestMain;
import sensitive_Equality_Test_Smell.SensititiveEqualitySmell;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class mainConnectorClass {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		 JFileChooser jf = new JFileChooser();
	     jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	     jf.showSaveDialog(null);
	     System.out.println(jf.getSelectedFile().getAbsolutePath());
	     String filePath = jf.getSelectedFile().getAbsolutePath();
	        
		
		IndirectTestMainClass indirectTestsmell = new IndirectTestMainClass();
		indirectTestsmell.indirecttestmain(filePath);
		
		SensititiveEqualitySmell sensititive = new SensititiveEqualitySmell();
		sensititive.sensitivityMain(filePath);

		EagerMainClass eagerMain = new EagerMainClass();
		eagerMain.EagerMain(filePath);
		
		GeneralFixtureMainClass generalfixturemain = new GeneralFixtureMainClass();
		generalfixturemain.generalfixturemain(filePath);

		AssertRouletteTestMain assertRouteMain = new AssertRouletteTestMain();
		assertRouteMain.assertRoulettemain(filePath);
			
		
		
	}

}
