package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;

public class MJParserTest {

	public static void main(String[] args) throws Exception {
		Reader br = null;
		try {
			File sourceCode = new File("test/programClass.mj");
			System.out.println("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
			
			Program prog = (Program)(s.value);
			
			System.out.println(prog.toString(""));
			System.out.println("===================================");
			
			RuleVisitor v = new RuleVisitor();
			prog.traverseBottomUp(v);
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { System.err.println((e1.getMessage())); }
		}
	}

}
