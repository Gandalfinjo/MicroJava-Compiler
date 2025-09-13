package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor {
	int printCallCount = 0;
	int varDeclCount = 0;
	
	public void visit(VarDeclaration varDeclaration) {
		varDeclCount++;
	}
	
	public void visit(PrintStatement printStatement) {
		printCallCount++;
	}
}
