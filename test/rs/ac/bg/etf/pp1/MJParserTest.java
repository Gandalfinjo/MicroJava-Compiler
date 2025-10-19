package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	public static void main(String[] args) throws Exception {
		Reader br = null;
		try {
			File sourceCode = new File("test/testCopy.mj");
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
			v.initPredeclaredSymbols();
			prog.traverseBottomUp(v);
			
			Tab.dump();
			
			if(!p.errorDetected && v.passed()){
				File objFile = new File("test/program.obj");
				
				if (objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				codeGenerator.initPredefinedMethods();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.mainPc;
				
				Code.write(new FileOutputStream(objFile));
				
				System.out.println("Parsing done successfully!");
			}
			else {
				System.err.println("Parsing not done successfully");
			}
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { System.err.println((e1.getMessage())); }
		}
	}

}
