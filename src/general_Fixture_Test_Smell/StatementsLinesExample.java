package general_Fixture_Test_Smell;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.Statement;

import test_smell_detection_plugin.handlers.OutputCollector;

public class StatementsLinesExample {
	
	public File projectDirectory = null;

    HashMap<String, List<String>> remainFieldClass = new HashMap<String, List<String>>();
    final List<String> setUpClass = new ArrayList<String>();
    HashMap<String, Boolean> TrueFalseCheck = new HashMap<String, Boolean>();
   

    public void statementsByLine(File projectDir) {
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java") && (path.contains("Test") || path.contains("test")),
        		(level, path, file) -> {
        			
        			
        			
                    // System.out.println(projectDir.toString() + path);
                    // System.out.println(Strings.repeat("=", path.length()));
                    List<String> setupVariable = new ArrayList<String>();
                    String search[] = path.split("/");
                    // System.out.println(search.length);
                    String ClassName = search[search.length - 1].toString();
                    // System.out.println("ClassName------" + ClassName);



            try {
                new NodeIterator(new NodeIterator.NodeHandler() {
                    @Override
                    public boolean handle(Node node) {
                        if (node instanceof Statement) {

                                    // System.out.println("ParentNodes word ");
                                    String ss = node.getParentNode().toString();
                                    // System.out.println(ss.contains("setUp()"));

                                    ///////////// setup method //////////////////
                                    if (ss.contains("setUp()") || ss.contains("SetUp()") ||
                                            ss.contains("setup()")) {
                                        String str = ClassName;
                                        str = ClassName.replaceAll(".java", "");
                                        // System.out.println("ClassName------" + str);
                                        setUpClass.add(str);
                                        List<Node> getChildNodes = node.getChildNodes();

                                        // prints public fields of ConcreteClass, it's superclass and super interfaces

                                        for (int i = 0; i < getChildNodes.size(); i++) {

                                            // System.out.println(getChildNodes.get(i).toString());
                                            String line = getChildNodes.get(i).toString();
                                            if (line.contains("=")) {
                                                String words[] = line.split("=");

                                                for (int j = 0; j < words.length; j++) {
                                                    // System.out.println(words[j]);
                                                }

                                                String variableLine = words[0];
                                                String variableList[] = variableLine.split(" ");
                                                String finalVariable = variableList[variableList.length - 1];
                                                // System.out.println("Variable----------------" + finalVariable);
                                                setupVariable.add(finalVariable);
                                            }

                                        }

                                        // System.out.println(setup_field.size());

                                    }
                                    ///////////////////// other method ////////////////
                                    else {

                                        List<Node> getChildNodes = node.getChildNodes();
                                        for (int i = 0; i < getChildNodes.size(); i++) {

                                            // System.out.println(getChildNodes.get(i).toString());
                                            String line = getChildNodes.get(i).toString();

                                            line = line.replaceAll("=", " ");
                                            line = line.replaceAll(";", "");
                                            line = line.replaceAll(":", " ");
                                            line = line.replaceAll(",", " ");
                                            line = line.replaceAll("\\(", " ");
                                            line = line.replaceAll("\\)", " ");
                                            line = line.replaceAll("\\.", " ");
                                            line = line.replaceAll("\\}", " ");
                                            line = line.replaceAll("\\{", " ");
                                            line = line.replaceAll("\\]", " ");
                                            line = line.replaceAll("\\[", " ");
                                            line = line.replaceAll("\\+", " ");
                                            line = line.replaceAll("\\&", " ");
                                            line = line.replaceAll("\\^", " ");
                                            line = line.replaceAll("\\%", " ");
                                            line = line.replaceAll("\\|", " ");
                                            line = line.replaceAll("\\~", " ");
                                            line = line.replaceAll("/", " ");
                                            line = line.replaceAll("\\*", " ");
                                            line = line.replaceAll("\\>>>", " ");
                                            line = line.replaceAll("\\>>", " ");
                                            line = line.replaceAll("\\<<", " ");


                                            String word[] = line.split(" ");

                                            for (int j = 0; j < word.length; j++) {
                                                if (word[j].equals("new"))
                                                    word[j + 1] = "new" + word[j + 1];
                                                // System.out.println(word[j]);

                                                if (setupVariable.contains(word[j])) {
                                                   
                                                    TrueFalseCheck.put(ClassName.replaceAll(".java", ""), true);
                                                    setupVariable.remove(word[j]);
                                                }

                                            }


                                        }

                                    }


                                    String nodeString = node.toString();
                                    // System.out.println("I think ekhane method" + nodeString);
                                    MethodCallVisitor visitor = new MethodCallVisitor();

                            node.accept(visitor, null);

                            //System.out.println("\n");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }).explore(JavaParser.parse(file));
               // System.out.println(); // empty line
            } catch (IOException e) {
                new RuntimeException(e);
            }

                    //////// remainder of variable of setUp Methods //////////////
                    for (int i = 0; i < setupVariable.size(); i++)
                        // System.out.println(setupVariable.get(i) + " iiiiiiiiissssss remainder ");

                    if (setupVariable.size() > 0) {
                        remainFieldClass.put(ClassName.replaceAll(".java", ""), setupVariable);
                        if (!TrueFalseCheck.containsKey(ClassName.replaceAll(".java", "")))
                            TrueFalseCheck.put(ClassName.replaceAll(".java", ""), false);
                    } else {
                        TrueFalseCheck.put(ClassName.replaceAll(".java", ""), true);
                    }

        }).explore(projectDir);
                       

    }
    
    public List<String> getUpClass() {
        return setUpClass;
    }

    public void setProjectDirectory(File directory) {
    	projectDirectory = directory;
    }
	
    public HashMap<String, List<String>> getField() {
        return remainFieldClass;
    }

    public HashMap<String, Boolean> getTrueFalseCheck() {
        return TrueFalseCheck;
    }
    
}