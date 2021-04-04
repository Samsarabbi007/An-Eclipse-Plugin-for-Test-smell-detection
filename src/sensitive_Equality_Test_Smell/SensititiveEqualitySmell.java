package sensitive_Equality_Test_Smell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import test_smell_detection_plugin.handlers.AllOutputs;
import test_smell_detection_plugin.handlers.OutputCollector;

public class SensititiveEqualitySmell {

	  private static File folder;
	  static String temp = "";
	  private static int errorCount=0;
  	static ArrayList<OutputCollector> outputs = new ArrayList<OutputCollector>();

	  
	  private static String errorLog = "Error Log" + '\n' + '\n';

	  public void sensitivityMain(String filePath) throws IOException {
		    // TODO Auto-generated method stub
			  BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\SPL-3\\TestSmellSystem-Outputs\\SensititiveEqualitySmell.txt"));
			  folder = new File(filePath);
			  chooseFolder(writer,folder);
			  writer.close();

		  }

		  public static void chooseFolder(BufferedWriter writer, final File folder) {
	       
		    for (final File fileEntry : folder.listFiles()) {
		      if (fileEntry.isDirectory()) {
		        // System.out.println("Reading files under the folder "+folder.getAbsolutePath());
		        chooseFolder(writer,fileEntry);
		      } else {
		        if (fileEntry.isFile()) {
		          temp = fileEntry.getName();
		          if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals("java")){
		            
		        	  //System.out.println("File= " + folder.getAbsolutePath()+ "\\" + fileEntry.getName());
		        	  
		        	  String path = folder.getAbsolutePath()+ "\\" + fileEntry.getName();
		        	  try {
		      			chooseFile(writer,path);
		      		  } catch (IOException e) {
		      			// TODO Auto-generated catch block
		      			e.printStackTrace();
		      		  } 
		      			
		        	  
		          }
		            
		        
		        }

		      }
		    }
		  }
		  
		  
		  public static int getErrorCount() {
			return errorCount;
		  }

		public static String getErrorLog() {
			return errorLog;
		}

		public static void setErrorLog(String errorLog) {
			SensititiveEqualitySmell.errorLog = errorLog;
		}

		public static void setErrorCount(int errorCount) {
			SensititiveEqualitySmell.errorCount = errorCount;
		}

		

		 public static void printMessage( BufferedWriter writer, String path ,ArrayList<ErrorRecords> errors) throws IOException {
			  System.out.println("File Path = " + path);
			  writer.write("File Path = " + path+'\n');
			  errorLog = errorLog + "File Path = " + path +'\n' +'\n';
			  //System.out.println();
			  for(int i=0 ; i <errors.size() ; i++ ) {
				  System.out.println("Line Number = " + errors.get(i).getLineNumber() ); 
				  //System.out.println("Error = "+ errors.get(i).getError());
				  String[] sections  = path.split("/");
        			 String fileName = sections[sections.length-1];
        			 fileName="";
                     
                     for(int index=path.length()-1;index>=0;index--) {
                     	Character ch = new Character(path.charAt(index));
                     	if(ch.equals('\\')) {
                     		break;
                     	} 
                     	//System.out.println(ch);
                     	fileName+=ch.toString();
                     }
                     String ActualFileName="";
                     for(int index= fileName.length()-1;index>=0;index--) {
                     	Character ch = new Character(fileName.charAt(index));
                     	ActualFileName+=ch.toString();
                     }
                     System.out.println(ActualFileName);
				  OutputCollector o = new OutputCollector(path, ActualFileName, errors.get(i).getLineNumber() + "", "Sensititive Equality");
				 outputs.add(o);
				  errorCount++;
				  writer.write("Line Number = " + errors.get(i).getLineNumber()+'\n');
				  errorLog = errorLog + "Line Number = " + errors.get(i).getLineNumber()   +'\n';
				  errorLog = errorLog +"Error = "+ errors.get(i).getError()  +'\n';
			  }
			  System.out.println();
			  //System.out.println();
			  
			  errorLog = errorLog +'\n'+'\n'; 
		  }
		  
		  public static void chooseFile(BufferedWriter writer, String fileName) throws IOException {
			  
			  File file = new File(fileName); 
			  
			  BufferedReader br = new BufferedReader(new FileReader(file)); 
			  boolean fileShow = false;
			  ArrayList<ErrorRecords> errors = new ArrayList<ErrorRecords>();
			  
			  String st="";
			  int lineNumber=0;
			  
			  while ((st = br.readLine()) != null) {
				  lineNumber++;
				  
				  String currentLine = st;
				  currentLine = currentLine.trim();
				  String merger = "";
				  if(currentLine.contains("assert")) {
					  merger = currentLine;
					  String stTemp = st;
					  for ( ; st!=null ; ) {
					  //for ( ;(st = br.readLine()) != null; ) {
					  	 
					  	  st = st.trim();
					  	  stTemp = stTemp.trim();
					  	  
					  	  if(stTemp.contains("toString()")) { 
					  		
					  		  ErrorRecords newError = new ErrorRecords(lineNumber, stTemp);
					  		  errors.add(newError);
					  		  fileShow = true;
					  	  }
						  merger = merger + st;
						  
						  lineNumber++;
						  if(stTemp.contains(";")) {
							  stTemp = st;
							  lineNumber--;
							  break;
						  }
						  else {
							  st = br.readLine();
							  
						  }
						  stTemp = st;

					  		
					  }
				  }
				 
			  }
			  if(fileShow == true) {
				  printMessage(writer,fileName,errors);
			  }
			  
			  AllOutputs.getExistingInstance().setSensitivities(outputs);
			  
			  
		  }
		  
		  public static void deadFliedTestSmell(String fileName) throws IOException {
			  
			  File file = new File(fileName); 
			  
			  BufferedReader br = new BufferedReader(new FileReader(file)); 
			  
			 // System.out.println(file.getName());
			  String st="";
			  Integer secondBracketCount = 0;
			  int i=0;
			  ArrayList<String> allVariables = new ArrayList<String>();
			  
			  while ((st = br.readLine()) != null) {
				  i++;
				  
				 
				  if(st.contains("{")) {
					  secondBracketCount++;
					
				  }
				  
				  if(st.contains("}")) {
					  secondBracketCount--;
					
				  }
				  
				  if(secondBracketCount==1) {
					  if(st.endsWith(";")) {
						  
						  String currentLine = st;
						  currentLine = currentLine.trim();
					
						  currentLine = currentLine.replace(";", "");
						  currentLine = currentLine.replace("[", "");
						  currentLine = currentLine.replace("]", "");
						  currentLine = currentLine.trim();
						  String [] wordArray = currentLine.split(" "); 
						  
					      for (String word : wordArray) {
					      }
					      
					     
					      allVariables.add(wordArray[wordArray.length-1]);
					  }
				  }
				  
				  
				  if(st.contains("testUp") ) {
					
				  }
				 
			    
			  }
			  

			  for(String s: allVariables) {
				 
			  }
				  
			  BufferedReader br2 = new BufferedReader(new FileReader(file)); 
			  
			  int ct=0;
			  
			  while ((st = br2.readLine()) != null) {
				  ct++;
				  
				  String currentLine = st;
				  currentLine = currentLine.trim();
				  currentLine = currentLine.replace(";", "");
				  currentLine = currentLine.replace("[", "");
				  currentLine = currentLine.replace("]", "");
				  currentLine = currentLine.trim();
				
				  if(currentLine.contains("{")) {
					  secondBracketCount++;
				  }
				  
				  if(currentLine.contains("}")) {
					  secondBracketCount--;
				  }
				  
				  if(secondBracketCount!=1 && secondBracketCount!=0 ) {
					  
					  if(currentLine.contains("{") ) {
						  String firstBracket = currentLine.substring(currentLine.lastIndexOf("(")+1,
								  currentLine.lastIndexOf(")"));
						 // System.out.println(firstBracket);
						  firstBracket = firstBracket.replace(",", "");
						  firstBracket = firstBracket.replace("[", "");
						  firstBracket = firstBracket.replace("]", "");
						  String [] firstBracketWordArray = firstBracket.split(" "); 

						  ArrayList<String> checkVariable = new ArrayList<String>();
						  
						  for (String word : firstBracketWordArray) {
					    	  for(String s : allVariables) {
					    		  if(word.contains(s)) {
					    			
					    			  checkVariable.add(s);
									  System.out.println(word);
					    		  }
					    	  }
					        
					      }
						  
						  String afterSecondBracket = currentLine.substring(currentLine.lastIndexOf("{")+1); 
						  // System.out.println(afterSecondBracket);
						  String [] wordArray = afterSecondBracket.split(" "); 
						  
					      for (String word : wordArray) {
					    	  for(String s : allVariables) {
					    		  if(word.contains(s)) {
					    			//  System.out.println("Line Number " + ct ); 
									  System.out.println(word);
					    		  }
					    	  }
					          //System.out.println(word); 
					      }

					  
					  }
					  for(String s : allVariables) {
						  if(currentLine.contains(s)) {
							//  System.out.println("Line Number " + ct ); 
							//  System.out.println(currentLine);
						  }
					  }
				  }
				  
				  
			  }
			  
			  
		  }

		  
	}
