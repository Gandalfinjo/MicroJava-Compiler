package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	public static void main(String[] args) throws Exception {
		Reader br = null;
		try {
			File sourceCode = new File("test/test303.mj");
			System.out.println("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
			
			Program prog = (Program)(s.value);		
			
			Tab.init();
			
			System.out.println(prog.toString(""));
			System.out.println("===================================");
			
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v);
			
			Tab.dump();
			
//			RuleVisitor v = new RuleVisitor();
//			prog.traverseBottomUp(v);
			
//			System.out.println("Print count calls = " + v.printCallCount);
//			System.out.println("Variable declarations = " + v.varDeclCount);
			
			if(!p.errorDetected && v.passed()){
				System.out.println("Parsing done successfully!");
			}
			else{
				System.err.println("Parsing not done successfully");
			}
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { System.err.println((e1.getMessage())); }
		}
	}

}
