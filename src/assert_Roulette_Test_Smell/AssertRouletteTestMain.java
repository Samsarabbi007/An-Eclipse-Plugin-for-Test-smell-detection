package assert_Roulette_Test_Smell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class AssertRouletteTestMain {

	public void assertRoulettemain(String filePath) 
	{
		
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(new File("D:\\SPL-3\\TestSmellSystem-Outputs\\AssertRouletteSmell.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		String src = filePath;
		
		
		AssertionRouletteFinder arf = new AssertionRouletteFinder();
		File source = new File(src);
		sb.append("File Path");
		sb.append("ClassName");
		sb.append("Method Name");
        //sb.append("Line Number\r\n");
		sb.append('\n');
		sb.append('\n');
        
		arf.searchMethods(writer,sb,source);
        
		writer.write(sb.toString());
		writer.close();
        
       
	}

}
