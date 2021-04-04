package general_Fixture_Test_Smell;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;

public class ListClassesExample {

	List<String> JavaFilesPath = new ArrayList<String>();
	
    public void listClasses(File projectDir) {
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java") && (path.contains("Test") || path.contains("test")),
        		(level, path, file) -> {
        			
            //System.out.println(path);
           // System.out.println(Strings.repeat("=", path.length()));
            JavaFilesPath.add(path);
            
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                               // System.out.println(" * " + n.getExtendedTypes());
                    }
                }.visit(JavaParser.parse(file), null);
                //System.out.println(); // empty line

            } catch ( IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);
    }
    
    
    public List<String> getJavaFilesPath(){
     	return JavaFilesPath;
     }
    

//    public static void main(String[] args) {
//        File projectDir = new File("F:\\06\\Testing\\Gradebook-master");
//        listClasses(projectDir);
//    }
    
    
    
}