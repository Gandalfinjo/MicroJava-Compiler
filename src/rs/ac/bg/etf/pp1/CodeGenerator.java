package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;

public class CodeGenerator extends VisitorAdaptor {
	public int mainPc;
	
	private void loadConst(int value) {
		Code.loadConst(value);
	}
	
	@Override
	public void visit(ProgName progName) {
		
	}
	
	@Override
	public void visit(MethodDeclaration methodDeclaration) {
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	@Override
	public void visit(TypeMethodSignature typeMethodSig) {      
		if ("main".equalsIgnoreCase(typeMethodSig.getName())) {
			mainPc = Code.pc;
		}
		
        int nFormPars = 0;
        int nLocalVals = 0;

        Code.put(Code.enter);
        Code.put(nFormPars);
        Code.put(nLocalVals);
	}
	
	@Override
	public void visit(VoidMethodSignature voidMethodSig) {      
		if ("main".equalsIgnoreCase(voidMethodSig.getName())) {
			mainPc = Code.pc;
		}
		
        int nFormPars = 0;
        int nLocalVals = 0;

        Code.put(Code.enter);
        Code.put(nFormPars);
        Code.put(nLocalVals);
	}
	
	@Override
    public void visit(OptionalDesignatorStatement stmt) { }
	@Override
    public void visit(FixedDesignatorStatement stmt) { }
	@Override
    public void visit(AssignopExprDSTail stmt) { }
	@Override
    public void visit(ActParsDSTail stmt) { }
	@Override
    public void visit(IncrementDSTail stmt) { }
	@Override
    public void visit(DecrementDSTail stmt) { }
    
	@Override
    public void visit(ReadStatement stmt) { }
    
	@Override
    public void visit(PrintStatement stmt) {
        if (stmt.getExpr().struct == Tab.charType) {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
        else {
            Code.loadConst(5);
            Code.put(Code.print);
        }
    }
    
	@Override
    public void visit(NoSignExpr expr) { }
	@Override
    public void visit(NegativeSignExpr expr) { }
	@Override
    public void visit(AddopTerm expr) { }
	@Override
    public void visit(Term term) { }
	@Override
    public void visit(MulopFactor mulopFactor) { }
    
	@Override
    public void visit(NumFactor factor) {
        loadConst(factor.getNum());
    }

	@Override
    public void visit(CharFactor factor) {
        loadConst(factor.getCh());
    }

	@Override
    public void visit(BoolFactor factor) {
        loadConst(factor.getBool() ? 1 : 0);
    }
        
	
	@Override
    public void visit(DesignatorFactor factor) { }
	@Override
    public void visit(NewFactor factor) { }
	@Override
    public void visit(ExprFactor factor) { }
    
	@Override
    public void visit(Designator designator) { }
	@Override
    public void visit(DotDesignatorTail tail) { }
	@Override
    public void visit(ExprDesignatorTail tail) { }
}
