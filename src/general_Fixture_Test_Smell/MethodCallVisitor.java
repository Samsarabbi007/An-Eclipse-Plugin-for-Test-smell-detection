package general_Fixture_Test_Smell;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodCallVisitor extends VoidVisitorAdapter<Void> {
		
		public int counter = 0;
		public List<String> listGeneralFixtureTestMethods = new ArrayList<String>();
		
		
        @Override
        public void visit(MethodCallExpr n, Void arg) {
        	// Don't forget to call super, it may find more method calls inside the arguments of this method call, for example.

        super.visit(n, arg);

        // System.out.println(" [Line " + n.getBegin().get().line + "] " + n);

        String methodName = n.getNameAsString();
        // System.out.println("ekhane method---\n" + methodName);

        if (!methodName.contains("assert") && !methodName.contains("set")) {
            counter++;
            String str = " [Line " + n.getBegin().get().line + "] " + methodName;
            // listEagerTestMethods.add(str);
            //System.out.println(str);
        }
        }
        

        public int getCounter() {
        	return counter;
        }
        
        public List<String> getGeneralFixtureTestMethods(){
        	return listGeneralFixtureTestMethods;
        }
        
    }