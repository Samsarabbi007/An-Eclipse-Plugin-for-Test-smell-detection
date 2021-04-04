package eager_Test_Smell;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EagerMainClass {
	
	public static List<String> JavaFilesPath = new ArrayList<String>();

	
	 public void EagerMain(String filePath) {
		 
		 UserInterface gui = new UserInterface(filePath);
		 //gui.setVisible(true);
		 gui.setInstanceOfThisClass(gui);
		 

	 }

}


