package eager_Test_Smell;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

import test_smell_detection_plugin.handlers.AllOutputs;
import test_smell_detection_plugin.handlers.OutputCollector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

public class TestMethods extends SwingWorker<Void, String> {
	
	public File projectDirectory = null;
	public int counter = 0;
	public File projectDir;
	public UserInterface gui;
	public boolean smellFound = false;
	ArrayList<OutputCollector> outputs = new ArrayList<OutputCollector>();
	
	public TestMethods(UserInterface gui) {
		this.gui = gui;
	}
	
//    public void statementsByLine(File projectDir) {
	
	@Override
	protected Void doInBackground() throws Exception {
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(new File("D:\\SPL-3\\TestSmellSystem-Outputs\\egerTest.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		StringBuilder sb = new StringBuilder();
		FileWriter csvWriter = new FileWriter("D:\\SPL-3\\TestSmellSystem-Outputs\\EgarTest.csv");
    	csvWriter.append("Smell_Name");
	    csvWriter.append(",");
	    csvWriter.append("Path");
	    csvWriter.append(",");
	    csvWriter.append("File_Name");
	    csvWriter.append(",");
	    csvWriter.append("LineNo");
	    csvWriter.append("\n");
        new FolderExplorer((level, path, file) -> path.endsWith(".java") && (path.contains("Test") || path.contains("test")),
        		(level, path, file) -> {
        			
            //System.out.println(path);
            //System.out.println(Strings.repeat("=", path.length()));
			String[] sections  = path.split("/");
  			String fileName = sections[sections.length-1];
            try {

                new NodeIterator(new NodeIterator.NodeHandler() {
                    @Override
                    public boolean handle(Node node) {
                        if (node instanceof Statement) {
                        	
                            MethodCallVisitor visitor = new MethodCallVisitor();
                            node.accept(visitor, null);
                            
                            if(visitor.isTestMethod() && visitor.getCounter() >=2 ) {
                            	smellFound = true;                            	
                            	
//                           	System.out.println(" [Lines " + node.getBegin().get().line +" - "+ node.getEnd().get().line + " ]\n " + node);
//                            	System.out.println();                       
                            	
                            	List<String> listOfEagerTests = visitor.getEagerTestSmellsInsideMethod();
                            	
                            	//System.out.println(Strings.repeat("-", 35));
                            	String str = " Lines: " + node.getBegin().get().line +" - "+ node.getEnd().get().line + "\n ";
                            	//str += Strings.repeat("-", 45) + "\n";
//                            	for(String s: listOfEagerTests) {
////                            		System.out.println(s);
//                            		str += s + "\n";
//                            	}
                            	try {
									csvWriter.append("Eager Test");
									csvWriter.append(",");
	                        	    csvWriter.append(path);
	                        	    csvWriter.append(",");
	                        	    csvWriter.append(fileName);
	                        	    csvWriter.append(",");
	                        	    csvWriter.append(str);
	                        	    csvWriter.append("\n");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                            	System.out.println(path);
                            	System.out.println(str);
                            	sb.append(path+" "+str+"\n");
                            	publish(Integer.toString( counter) );
            					publish(str);
            					
            					String lineNo = node.getBegin().get().line +" - "+ node.getEnd().get().line;
            					
            					OutputCollector output = new OutputCollector(path, fileName, lineNo, "Eager Test Smell");
            					outputs.add(output);
                            }
                            
//                            System.out.println("\n");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }).explore(JavaParser.parse(file));
                //System.out.println(); smellFound: " + smellFound + " index counter: " + counter// empty line
                
               // System.out.println("path: " + path + "\t smellFound Value: " + smellFound + " index counter: " + counter);
                if(!smellFound) {
                	//System.out.println("path: " + path + "\t" +counter);
                	//System.out.println("smell Found");
                	//System.out.println(" ");
                    
                	String str = "\tNo 'Eager Test Smell' found in this class.";
                	publish(Integer.toString( counter) );
                	publish(str);
                }
                
                counter++;
                smellFound = false;
                
                
                
                
                
            } catch (IOException e) {
            	//System.out.println("Error in class: " + path);
                new RuntimeException(e);
            }
            
        }).explore(projectDir);
        writer.write(sb.toString());
		writer.close(); 
		csvWriter.flush();
		csvWriter.close();
		AllOutputs ao = AllOutputs.getExistingInstance();
		ao.setEagers(outputs);
        return null;
    }
	

	@Override
	protected void process(final List<String> chunks) {
		// Updates the messages text area
		
	    for (final String info : chunks) {
	    	if(info.length() <= 2) {
	    		int index = Integer.parseInt(info);
	    		//System.out.println("Method Info ====="+info);
	    		//gui.setCounter(index);
	    	}
	    	else {
	    		//gui.displaySmells(info);
	    	}

	    }
	}
	
    
	public void setDirectory(File projectDir) {
		this.projectDir = projectDir;
	}


	
}