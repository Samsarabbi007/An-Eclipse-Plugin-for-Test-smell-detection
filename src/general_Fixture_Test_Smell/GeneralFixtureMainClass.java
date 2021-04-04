package general_Fixture_Test_Smell;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import test_smell_detection_plugin.handlers.AllOutputs;
import test_smell_detection_plugin.handlers.OutputCollector;


public class GeneralFixtureMainClass {
	
	public static List<String> JavaFilesPath = new ArrayList<String>();
    static PrintWriter pw;
    static StringBuilder sb = new StringBuilder();
    ArrayList<OutputCollector> outputs = new ArrayList<OutputCollector>();

    public void generalfixturemain(String filePath) throws IOException {
    	FileWriter csvWriter = new FileWriter("D:\\SPL-3\\TestSmellSystem-Outputs\\generalfixture.csv");
    	csvWriter.append("Smell_Name");
	    csvWriter.append(",");
	    csvWriter.append("Path");
	    csvWriter.append(",");
	    csvWriter.append("File_Name");
	    csvWriter.append(",");
	    csvWriter.append("LineNo");
	    csvWriter.append("\n");
        pw = new PrintWriter(new File("D:\\SPL-3\\TestSmellSystem-Outputs\\generalFixture.txt"));

//        
        File projectDir = new File(filePath);
        ////////////// a extends b , b extends c so a extends b,c ////// here <a,<b,c>>
        HashMap<String, String> class_with_extends = new HashMap<String, String>();
        HashMap<String, List<String>> final_class_with_extends = new HashMap<String, List<String>>();
		 ListClassesExample javaFiles = new ListClassesExample();
		 javaFiles.listClasses(projectDir);
		 JavaFilesPath = javaFiles.getJavaFilesPath(); 
        // System.out.print(JavaFilesPath);

        StatementsLinesExample methodParser = new StatementsLinesExample();
        methodParser.statementsByLine(projectDir);

        List<String> setUpClass = methodParser.getUpClass();
        HashMap<String, Boolean> truth = methodParser.getTrueFalseCheck();
        List<String> generalSmell = new ArrayList<String>();
        

        // System.out.println(truth + "--------------------------------------------------------truth");
        // System.out.println(methodParser.getField());

        for (int i = 0; i < JavaFilesPath.size(); i++) {

            String path = JavaFilesPath.get(i).toString();
            String words[] = path.split("/");
            String javaFilePath = projectDir.toString();

            for (int j = 0; j < words.length - 1; j++) {
                javaFilePath += "\\" + words[j];
            }

            javaFilePath += "\\" + words[words.length - 1];

            String exploredClass = words[words.length - 1];
            exploredClass = exploredClass.replaceAll(".java", "");
            // System.out.println("now explore" + words[words.length - 1]);

            if((javaFilePath.contains("Test")||javaFilePath.contains("test"))&&javaFilePath.contains(".java")) {

                File file = new File(javaFilePath);
    
                BufferedReader br = new BufferedReader(new FileReader(file));

                String st;

                while ((st = br.readLine()) != null) {
    
                    if (st.contains("extends")) {
                        st = st.replaceAll("\\{", "");
                        st = st.replaceAll(",", " ");
                        st = st.replaceAll("\\<", "");
                        st = st.replaceAll("\\>", " ");
                        String classs[] = st.split(" ");

                        for (int j = 0; j < classs.length; j++) {

                            if (classs[j].equals("extends")) {
                                if ((j + 1) <= (classs.length - 1))
                                    class_with_extends.put(exploredClass, classs[j + 1]);
                                break;
                            }

                        }
                        break;
                    }
                }
            }

        }

        // System.out.println(class_with_extends);

        for (Map.Entry<String, String> entry : class_with_extends.entrySet()) {

            List<String> extend = new ArrayList<String>();
            String value = entry.getValue();
            String key = entry.getKey();
            extend.add(value);
            while (class_with_extends.containsKey(value)) {

                value = class_with_extends.get(value);
                extend.add(value);
            }

            final_class_with_extends.put(key, extend);

        }

        // System.out.println(final_class_with_extends + " finalllllllll");

        HashMap<String, List<String>> field = methodParser.getField();

        HashMap<String, String> field_Class = new HashMap<String, String>();

        List<String> willExplored = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        
        List<String> totalVariable = new ArrayList<String>();

        for (Map.Entry<String, List<String>> entry : field.entrySet()) {

            List<String> variable = entry.getValue();

            String class_Name = entry.getKey();

            for (int j = 0; j < variable.size(); j++) {
                field_Class.put(variable.get(j), class_Name);
                set.add(class_Name);
                totalVariable.add(variable.get(j));
            }

        }

        for (Map.Entry<String, List<String>> entry : final_class_with_extends.entrySet()) {
            String class_Name = entry.getKey();
            willExplored.add(class_Name);
        }

        // System.out.println("Field With Class" + field_Class);
        // System.out.println(willExplored);
        List<String> finalClass = new ArrayList<String>();

        for (int i = 0; i < JavaFilesPath.size(); i++) {

            String path = JavaFilesPath.get(i).toString();
            String words[] = path.split("/");
            String javaFilePath = projectDir.toString();

            for (int j = 0; j < words.length - 1; j++) {
                javaFilePath += "\\" + words[j];
            }

            // System.out.println(javaFilePath + " " + words[words.length - 1]);

            if (willExplored.contains(words[words.length - 1].replaceAll(".java", ""))) {

                javaFilePath += "\\" + words[words.length - 1];

                // System.out.println(javaFilePath);

                if ((javaFilePath.contains("Test") || javaFilePath.contains("test"))
                        && javaFilePath.contains(".java")) {

                    for (int j = 0; j < totalVariable.size(); j++) {

                        String operation = totalVariable.get(j);
                        // System.out.println(operation);

                        File file = new File(javaFilePath);

                        BufferedReader br = new BufferedReader(new FileReader(file));
                        
                        String st;

                        boolean tagClass = false;
                        boolean initialize = false;
                        Stack<Character> stack = new Stack<Character>();
                        List<String> primitiveData = new ArrayList<String>();
                        primitiveData.add("int");
                        primitiveData.add("byte");
                        primitiveData.add("float");
                        primitiveData.add("short");
                        primitiveData.add("double");
                        primitiveData.add("long");
                        primitiveData.add("boolean");
                        primitiveData.add("void");
                        primitiveData.add("char");
                        boolean foundVariable=false;
                        while ((st = br.readLine()) != null) {

                            if (st.contains("class")) tagClass = true;
                            if (tagClass) {

                                if (st.contains("{")) stack.push('{');

                                if (st.contains(operation)) {
                                	foundVariable = true;
                                    st = st.replaceAll("=", " ");
                                    st = st.replaceAll(";", "");
                                    st = st.replaceAll(":", " ");
                                    st = st.replaceAll(",", " ");
                                    st = st.replaceAll("\\(", " ");
                                    st = st.replaceAll("\\)", " ");
                                    st = st.replaceAll("\\.", " ");
                                    st = st.replaceAll("\\{", " ");
                                    st = st.replaceAll("\\]", " ");
                                    st = st.replaceAll("\\[", " ");
                                    st = st.replaceAll("\\+", " ");
                                    st = st.replaceAll("\\&", " ");
                                    st = st.replaceAll("\\^", " ");
                                    st = st.replaceAll("\\%", " ");
                                    st = st.replaceAll("\\|", " ");
                                    st = st.replaceAll("\\~", " ");
                                    st = st.replaceAll("/", " ");
                                    st = st.replaceAll("\\*", " ");
                                    st = st.replaceAll("\\>>>", " ");
                                    st = st.replaceAll("\\>>", " ");
                                    st = st.replaceAll("\\<<", " ");
                                    // System.out.println(st);
                                    st = st.trim().replaceAll("\\s{2,}", " ");
                                    // System.out.println(st);
                                    String word[] = st.split(" ");
                                    //System.out.println(word);


                                    for (int k = 0; k < word.length; k++) {
                                        if (word[k].equals(operation)) {

                                            if (k - 1 >= 0) {

                                                if (primitiveData.contains(word[k - 1]) ||
                                                        Character.isUpperCase(word[k - 1].charAt(0))) {
                                                	//System.out.println(word[k - 1]);
                                                    // System.out.println("It is initialization");
                                                    initialize = true;
                                                    stack.add('i');
                                                } else {
                                                    // System.out.println("It is usable");
                                                    stack.add('u');
                                                    if (initialize == false) {
                                                        // System.out.println("huurrrrrrrrayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                                                        String classname = field_Class.get(operation);
                                                        finalClass.add(classname);
                                                        truth.put(classname, true);
                                                    }
                                                }
                                                // System.out.println(word[k - 1].charAt(0));

                                            } else {
                                                stack.add('u');
                                                // System.out.println("It is usable");
                                                if (initialize == false) {
                                                    
                                                    // System.out.println("huurrrrrrrrayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");

                                                    String classname = field_Class.get(operation);
                                                    finalClass.add(classname);
                                                    truth.put(classname, true);
                                                }

                                            }
                                        }

                                    }

                                }


                                if (st.contains("}")) {

                                    while (true) {

                                        Character ch = stack.pop();

                                        if (ch.equals('i')) initialize = false;

                                        if (ch.equals('{')) break;
                                    }

                                }


                            }
                        }
                        if(foundVariable==false) {
                        	String classname = field_Class.get(operation);
                        	//finalClass.add(classname);
                            generalSmell.add(classname);
                        }

                    }


                }
            }

        }
        
        
        
//        System.out.println("final   " + finalClass);
//        System.out.println("Should explored " + set);
        
        Set<String> hash_Set = new HashSet<String>();
        for(int ii=0;ii<generalSmell.size();ii++) {
        	//System.out.println("final   " + generalSmell.get(ii));
        	hash_Set.add(generalSmell.get(ii));
        }
        //System.out.println("generalSmell " + hash_Set);
       // System.out.println(hash_Set.size());

        HashMap<String,String> falseFound = new HashMap<String,String>();
        HashMap<String,String> pathVsLine = new HashMap<String,String>();
        List<String> lineNO = new ArrayList<String>(); 
        
        
        
        for (Map.Entry<String, Boolean> entry : truth.entrySet()) {

            if (!entry.getValue()) {

                String class_Name = entry.getKey();
                //System.out.println(class_Name);
                class_Name+=".java";
                
                for (int i = 0; i < JavaFilesPath.size(); i++) {

                    String path = JavaFilesPath.get(i).toString();
                    String words[] = path.split("/");
                    String javaFilePath = projectDir.toString();

                    for (int j = 0; j < words.length - 1; j++) {
                        javaFilePath += "\\" + words[j];
                    }

                    //System.out.println(javaFilePath + " " + words[words.length - 1]);
                    if (class_Name.equals(words[words.length - 1])) {
                        javaFilePath += "\\" + words[words.length - 1];

                        //System.out.println(javaFilePath);
                        File file = new File(javaFilePath);

                        BufferedReader br = new BufferedReader(new FileReader(file));

                        String st;
                        int count = 0;

                        while ((st = br.readLine()) != null) {
                            if (st.contains("setUp()") || st.contains("SetUp()") || st.contains("setup()")) {
                                //System.out.println("found in path " + javaFilePath + "line no  " + count);
                                falseFound.put(class_Name, javaFilePath + ",line no" + count);
                                break;
                            }
                            count++;
                        }

                    }
                }
                
            }
        }
        Iterator<String> itr = hash_Set.iterator();
        while (itr.hasNext()) {

            String class_Name = itr.next();
            //System.out.println(class_Name);
            class_Name+=".java";
            
            for (int i = 0; i < JavaFilesPath.size(); i++) {

                String path = JavaFilesPath.get(i).toString();
                String words[] = path.split("/");
                String javaFilePath = projectDir.toString();

                for (int j = 0; j < words.length - 1; j++) {
                    javaFilePath += "\\" + words[j];
                }

                //System.out.println(javaFilePath + " " + words[words.length - 1]);
                if (class_Name.equals(words[words.length - 1])) {
                    javaFilePath += "\\" + words[words.length - 1];

                    //System.out.println(javaFilePath);
                    File file = new File(javaFilePath);

                    BufferedReader br = new BufferedReader(new FileReader(file));

                    String st;
                    int count = 0;

                    while ((st = br.readLine()) != null) {
                        if (st.contains("setUp()") || st.contains("SetUp()") || st.contains("setup()")) {
                        	
                        	String[] sections  = javaFilePath.split("/");
                 			String fileName = sections[sections.length-1];
                 			
                 			
                 			
                            System.out.println("path: " + javaFilePath);
                            System.out.println("line No  " + count);
                            fileName="";
                            
                            for(int index=javaFilePath.length()-1;index>=0;index--) {
                            	Character ch = new Character(javaFilePath.charAt(index));
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
                            fileName= ActualFileName;
                            
                            OutputCollector o = new OutputCollector(javaFilePath, fileName, count + "", "General Fixture");
                 			outputs.add(o);
                 			
                        	falseFound.put(class_Name, javaFilePath );
                            pathVsLine.put(javaFilePath,String.valueOf(count));
                            lineNO.add(String.valueOf(count));
                            csvWriter.append("General Fixture");
                    	    csvWriter.append(",");
                            csvWriter.append(javaFilePath);
                    	    csvWriter.append(",");
                    	    csvWriter.append(ActualFileName);
                    	    csvWriter.append(",");
                    	    csvWriter.append(Integer.toString(count));
                    	    csvWriter.append("\n");
                            break;
                        }
                        count++;
                    }

                }
            }
            
            
        }
        AllOutputs ao = AllOutputs.getExistingInstance();
        ao.setGfixtures(outputs);
        
        String str = "";

        if (falseFound.size() > 0) {
            sb.append("Class Name,");
            sb.append("File Path,");
            sb.append("Line Number\r\n");
            int index=0;
            for (Map.Entry<String, String> entry : falseFound.entrySet()) {
                str += "Class Name: " + entry.getKey() + "\n File Path: " + entry.getValue() + "\n";
                str+= "Line Number "+lineNO.get(index)+"\n\n";
                
                sb.append(entry.getKey() + ",");
                sb.append(entry.getValue() + "\n");
                sb.append(lineNO.get(index) + "\r\n\n\n");
                index++;
            }
//            for (Map.Entry<String, String> entry : pathVsLine.entrySet()) {
//                str += "Line No: " + entry.getValue() + "\n\n";
//
//                sb.append(entry.getValue() + "\r\n");
//
//            }
            
//            String className="";
//            String filePath="";
//            String lineNumber="";
//
//            for (Map.Entry<String, String> entry : falseFound.entrySet()) {
//                str += "Class Name: " + entry.getKey() + "\n File Path: " + entry.getValue() + "\n";
//                className = entry.getKey();
//                filePath = entry.getValue();
//
//            }
//            for (Map.Entry<String, String> entry : pathVsLine.entrySet()) {
//                str += "Line No: " + entry.getValue() + "\n\n";
//                lineNumber =entry.getValue(); 
//            }
//            sb.append(className + ",");
//            sb.append(filePath + "\n");
//            sb.append(lineNumber + "\r\n");
            
        } else {

            str += "SuccessFullll no Smell Found";
        }
        


//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(0, 0, 1300, 500);
//        JTextArea text = new JTextArea();
//        text.setBounds(20, 20, 500, 500);
//        text.setBackground(Color.BLACK);
//        text.setForeground(Color.RED);
//        text.setFont(new Font("serif", Font.BOLD, 15));
//        text.setText(str);
//        JScrollPane pane=new JScrollPane(text);
//        pane.setBounds(10, 10, 500, 500);
//        frame.add(pane);
//        frame.setVisible(true);

        pw.write(sb.toString());
        pw.close();
        csvWriter.flush();
        csvWriter.close();
	 }

}
