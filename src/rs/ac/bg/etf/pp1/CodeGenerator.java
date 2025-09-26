package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;

public class CodeGenerator extends VisitorAdaptor {
	public int mainPc;
	
	@Override
	public void visit(NumFactor num) {
		Code.loadConst(num.getNum());
	}
	
	@Override
	public void visit(CharFactor ch) {
		Code.loadConst(ch.getCh());
	}
	
	@Override
	public void visit(BoolFactor b) {
		Code.loadConst(b.getBool() ? 1 : 0);
	}
	
	@Override
	public void visit(Designator designator) {
	    boolean isLeftSideOfAssign = false;

	    if (designator.getParent() instanceof OptionalDesignatorStatement) {
	        OptionalDesignatorStatement ods = (OptionalDesignatorStatement) designator.getParent();
	        if (ods.getDesignator() == designator) {
	            isLeftSideOfAssign = ods.getDesignatorStatementTail() instanceof AssignopExprDSTail;
	        }
	    }

	    if (!isLeftSideOfAssign) {
	        Code.load(designator.obj);
	    }
	}
	
	@Override
	public void visit(AssignopExprDSTail assign) {
		Obj des = ((OptionalDesignatorStatement) assign.getParent()).getDesignator().obj;
		Code.store(des);
	}
	
	@Override
	public void visit(IncrementDSTail inc) {
		Obj des = ((OptionalDesignatorStatement) inc.getParent()).getDesignator().obj;
		Code.load(des);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(des);
	}
	
    @Override
    public void visit(DecrementDSTail dec) {
        Obj des = ((OptionalDesignatorStatement) dec.getParent()).getDesignator().obj;
        Code.load(des);
        Code.loadConst(1);
        Code.put(Code.sub);
        Code.store(des);
    }
    
    @Override
    public void visit(Plus plus) { Code.put(Code.add); }

    @Override
    public void visit(Minus minus) { Code.put(Code.sub); }

    @Override
    public void visit(Multiplication mul) {Code.put(Code.mul); }

    @Override
    public void visit(Division div) { Code.put(Code.div); }

    @Override
    public void visit(Modulo mod) { Code.put(Code.rem); }
    
    public void visit(NegativeSignExpr expr) { 
    	expr.getTerm().accept(this);
    	Code.put(Code.neg);
    }
    
    @Override
    public void visit(PrintStatement stmt) {
        Struct exprType = stmt.getExpr().struct;
        
        if (exprType == Tab.intType) {
            Code.loadConst(5);
            Code.put(Code.print);
        }
        else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
    }

    @Override
    public void visit(ReadStatement stmt) {
        Obj des = stmt.getDesignator().obj;
        
        if (des.getType() == Tab.intType) {
            Code.put(Code.read);
            Code.loadConst(5);
        }
        else {
            Code.put(Code.bread);
            Code.loadConst(1);
        }
        
        Code.store(des);
    }
    
    @Override
    public void visit(NewFactor newArr) {
    	newArr.getNewFactorTail().accept(this); 
    	
        Code.put(Code.newarray);
        Code.put(newArr.getType().struct == Tab.charType ? 0 : 1);
    }
    
    @Override
    public void visit(TypeMethodSignature method) {
        if ("main".equalsIgnoreCase(method.getName())) {
            Code.mainPc = Code.pc;
        }
        
        method.obj.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(method.obj.getLevel());
        Code.put(method.obj.getLocalSymbols().size());
    }

    @Override
    public void visit(VoidMethodSignature method) {
        if ("main".equalsIgnoreCase(method.getName())) {
            Code.mainPc = Code.pc;
        }
        
        method.obj.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(method.obj.getLevel());
        Code.put(method.obj.getLocalSymbols().size());
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        Code.put(Code.exit);
        Code.put(Code.return_);
    }
        
}
