package assert_Roulette_Test_Smell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AssertRouletteTestMain {

	public void assertRoulettemain(String filePath) throws IOException 
	{
		FileWriter csvWriter = new FileWriter("D:\\SPL-3\\TestSmellSystem-Outputs\\AssertRoulette.csv");
    	csvWriter.append("Smell_Name");
	    csvWriter.append(",");
	    csvWriter.append("Path");
	    csvWriter.append(",");
	    csvWriter.append("File_Name");
	    csvWriter.append(",");
	    csvWriter.append("LineNo");
	    csvWriter.append("\n");
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
        
		arf.searchMethods(writer,sb,source,csvWriter);
        
		writer.write(sb.toString());
		writer.close();
		csvWriter.flush();
		csvWriter.close();
        
       
	}

}
