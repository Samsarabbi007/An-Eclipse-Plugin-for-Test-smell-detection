package assert_Roulette_Test_Smell;


import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import test_smell_detection_plugin.handlers.AllOutputs;
import test_smell_detection_plugin.handlers.OutputCollector;



public class AssertionRouletteFinder
{
	
	ArrayList<OutputCollector> outputs = new ArrayList<OutputCollector>();
	public void searchMethods(PrintWriter writer,StringBuilder sb,File srcFile, FileWriter csvWriter) 
	{
	   
		
    	new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
    		ASTParser parser = ASTParser.newParser(AST.JLS3);
    		
    		String str = readUsingBufferedReaderCharArray(file);
    		parser.setSource(str.toCharArray());
    		parser.setKind(ASTParser.K_COMPILATION_UNIT);
    		
    		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
    		
    		cu.accept(new ASTVisitor() {
    			@Override
    			public boolean visit(MethodDeclaration node) 
    			{	
    				
					node.accept(new ASTVisitor() 
					{
						int  count = 0;
						@Override
						public boolean visit(MethodInvocation node1) 
						{
							if(count<2)
							{
								if(node1.getName().toString().contains("assert"))
								{
									count++;
									if(count>1)
									{
										//System.out.println("Smell found at......");
										System.out.println("File path: " +file.getAbsolutePath()+"......"+"File Name: "+file.getName().toString()+"......"+"Method Name: "+node.getName());
										int lineNo = cu.getLineNumber(node1.getStartPosition())-1;
										OutputCollector o = new OutputCollector(file.getAbsolutePath(), file.getName(), lineNo + "", "Assertion Roulette");
									    outputs.add(o);
									    try {
											csvWriter.append("Assert Roulette");
											csvWriter.append(",");
										    csvWriter.append(file.getAbsolutePath());
										    csvWriter.append(",");
										    csvWriter.append(file.getName().toString());
										    csvWriter.append(",");
										    csvWriter.append(Integer.toString(lineNo));
										    csvWriter.append("\n");
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									    
										sb.append(file.getAbsolutePath()+","+file.getName().toString()+","+node.getName());
									     sb.append('\n');
									     sb.append('\n');
									}
								}
							}	
							return super.visit(node1);
							
						}
					});
    			
					
    				return super.visit(node);
    			}
    		});
    		
        }).explore(srcFile);
    	
    	AllOutputs.getExistingInstance().setAssertions(outputs);
    }
	

	
    private static String readUsingBufferedReaderCharArray(File file) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		char[] buffer = new char[10];
		try {
			reader = new BufferedReader(new FileReader(file));
			while (reader.read(buffer) != -1) {
				stringBuilder.append(new String(buffer));
				buffer = new char[10];
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return stringBuilder.toString();
	}
}