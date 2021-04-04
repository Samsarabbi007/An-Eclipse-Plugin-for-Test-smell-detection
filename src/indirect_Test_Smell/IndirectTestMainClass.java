package indirect_Test_Smell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.github.javaparser.ParseException;

import test_smell_detection_plugin.handlers.OutputCollector;



public class IndirectTestMainClass {

	public ArrayList<OutputCollector> indirecttestmain(String filePath) throws ParseException {
		// TODO Auto-generated method stub
		
		File projectDir = new File(filePath);

	
				PrintWriter writer=null;
				try {
					writer = new PrintWriter(new File("D:\\SPL-3\\TestSmellSystem-Outputs\\IndirectTestSmell.txt"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				StringBuilder sb = new StringBuilder();
				String src = filePath;
				
				
				StatementsLinesExample javaFiles = new StatementsLinesExample();
				ArrayList<OutputCollector> ret = StatementsLinesExample.statementsByLine(projectDir, writer, sb, src);
				sb.append("File Path");
				sb.append("ClassName");
				sb.append("Method Name");
		        //sb.append("Line Number\r\n");
				sb.append('\n');
				sb.append('\n');
		        
				StatementsLinesExample.statementsByLine(projectDir, writer,sb,src);
		        
				writer.write(sb.toString());
				writer.close();
				
				return ret;
        
	}

}
