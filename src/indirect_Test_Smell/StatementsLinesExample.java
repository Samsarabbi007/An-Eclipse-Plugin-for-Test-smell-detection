package indirect_Test_Smell;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ParseException;
import com.google.common.base.Strings;

import test_smell_detection_plugin.handlers.OutputCollector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StatementsLinesExample {
    public static ArrayList<OutputCollector> statementsByLine(File projectDir,PrintWriter writer, StringBuilder sb, String src) throws ParseException {
    	ArrayList<OutputCollector> outputs = new ArrayList<OutputCollector>();
    	 new DirExplorer((level, path, file) -> path.endsWith(".java") && ((path.contains("Test") || path.contains("test"))), (level, path, file) -> {
//            System.out.println(path);
            //System.out.println(Strings.repeat("=", path.length()));
            try {
                new NodeIterator(new NodeIterator.NodeHandler() {
                    @Override
                    public boolean handle(Node node) {
                        if (node instanceof Statement) {
                                 List<Node> getChildNodes = node.getChildNodes();
                                 HashMap<String, String> hm = new HashMap<String, String>();
                              

                                 for (int i = 0; i < getChildNodes.size(); i++) {

                                     //System.out.println(getChildNodes.get(i).toString());
                                     String line = getChildNodes.get(i).toString();
                                     //System.out.println(line);
                                     int count=0;
                                   
                                     if(line.contains("=")&& line.endsWith(";")&& !line.contains("==")) {
                                    	 
                                    	 String sundorLine = line.trim();
                                    	
                                    	 if(sundorLine.contains(" new ")){
                                    		
                                    		String word[]=sundorLine.split(" ");
                                  
                                    		//one case
                                    		for(int j=0;j<word.length;j++) {
                                    			//System.out.println(word[j]);
                                    		
                                    			if(word[j].equals("new")) count++;
                                    				
                                    		}
                                    		//
                                    		 hm.put(word[1], word[2]);
                                     		//System.out.println(hm);
                                     		Iterator<Entry<String, String>> it = hm.entrySet().iterator();
                                     		String[] sections  = path.split("/");
                               			 String fileName = sections[sections.length-1];
                                     		while(it.hasNext())
                                         	{
                                         		Map.Entry element = (Map.Entry)it.next();
                                         		
                                         		String hobe = ((String)element.getKey());
                                         		 
                                         		
                                         		 if(sundorLine.contains(" "+hobe+")")) {
                                         			
                                         			OutputCollector output = new OutputCollector(path, fileName, node.getBegin().get().toString(), "Indirect Test Smell");
                                         			outputs.add(output);
                                         			 System.out.println("Indirect smell found. Method starts at Line "+node.getBegin().get().line );
                                         			 sb.append(path+" Method starts at Line "+node.getBegin().get().line+"\n" );
                                         		 }
                                         		else if(sundorLine.contains("("+hobe+")")) {
                                         			OutputCollector output = new OutputCollector(path, fileName, node.getBegin().get().toString(), "Indirect Test Smell");
                                         			outputs.add(output);
                                         			System.out.println("Indirect smell found. Method starts at Line "+node.getBegin().get().line+"\n" );
                                        			 sb.append(path+" Method starts at Line "+node.getBegin().get().line+"\n" );


                                         		}
                                         		else if(sundorLine.contains("("+hobe+",")) {
                                         			OutputCollector output = new OutputCollector(path, fileName, node.getBegin().get().toString(), "Indirect Test Smell");
                                         			outputs.add(output);
                                         			System.out.println("Indirect smell found. Method starts at Line "+node.getBegin().get().line+"\n" );
                                        			 sb.append(path+" Method starts at Line "+node.getBegin().get().line+"\n" );

                                         		}
                                         		else if(sundorLine.contains("("+hobe+" ")) {
                                         			OutputCollector output = new OutputCollector(path, fileName, node.getBegin().get().toString(), "Indirect Test Smell");
                                         			outputs.add(output);
                                         			System.out.println("Indirect smell found. Method starts at Line "+node.getBegin().get().line+"\n" );
                                        			 sb.append(path+" Method starts at Line "+node.getBegin().get().line+"\n" );

                                         		}
                                         		else if(sundorLine.contains("("+hobe+".")) {
                                         			OutputCollector output = new OutputCollector(path, fileName, node.getBegin().get().toString(), "Indirect Test Smell");
                                         			outputs.add(output);
                                         			System.out.println("Indirect smell found. Method starts at Line "+node.getBegin().get().line+"\n" );
                                        			 sb.append(path+" Method starts at Line "+node.getBegin().get().line+"\n" );

                                         		}
                                         	
                                         	}
                                             
                                             
                                    		//another case
                                    		
                                    		
                                    	 }
                                    	 if (count>=2) {
                                    		 String[] sections  = path.split("/");
                                   			 String fileName = sections[sections.length-1];
                                    		 OutputCollector output = new OutputCollector(path, fileName, node.getBegin().get().toString(), "Indirect Test Smell");
                                  			outputs.add(output);
                                   			System.out.println("Indirect smell found. Method starts at Line "+node.getBegin().get().line );
                                			 sb.append(path+" Method starts at Line "+node.getBegin().get().line+"\n" );

                                   			//System.out.println(sundorLine);
                                   	
                                   		}
                                    	 	 
                                     }
                                	
                                 }
                                 //String word[]=line.split(" ");
                            	 //System.out.println(word);
//                                 for (int j = 0; j < word.length; j++) {
                                     //System.out.println(word);
//                                     if (word[j].equals("=")) {
//                                         //System.out.println(word[j]);
//                                     }
//                                 }
                                 
                                 //System.out.println(setup_field.size());

                          
                            return false;
                        } else {
                            return true;
                        }
                    }
                }).explore(JavaParser.parse(file));
                System.out.println(); // empty line
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);
    	 return outputs;
    }

//	public void searchMethods(PrintWriter writer, StringBuilder sb, String src) {
//		// TODO Auto-generated method stub
//		
//	}
   
}