package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;

public class CodeGenerator extends VisitorAdaptor {
	public int mainPc;
	
    private static final int RESERVED_LOCALS = 20;
	
	private void loadConst(int value) {
		Code.loadConst(value);
	}
	
	private int getPredefinedMethodAddress(String methodName) {
	    Obj meth = Tab.find(methodName);
	    if (meth == null) Code.error("Predefined method " + methodName + " doesn't exist");
	    return meth.getAdr();
	}
	
	public void initPredefinedMethods() {
        // chr(i:int) : char
        Obj chrMeth = Tab.find("chr");
        chrMeth.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);

        // ord(ch:char) : int
        Obj ordMeth = Tab.find("ord");
        ordMeth.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
        
        // len(arr:array) : int
        Obj lenMeth = Tab.find("len");
        lenMeth.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
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
		
		Obj meth = typeMethodSig.obj;
		meth.setAdr(Code.pc);
		
        int nFormPars = meth.getLevel();
        int nLocalVals = meth.getLocalSymbols().size();

        Code.put(Code.enter);
        Code.put(nFormPars);
        Code.put(nLocalVals);
	}
	
	@Override
	public void visit(VoidMethodSignature voidMethodSig) {      
		if ("main".equalsIgnoreCase(voidMethodSig.getName())) {
			mainPc = Code.pc;
		}
		
		Obj meth = voidMethodSig.obj;
		meth.setAdr(Code.pc);
		
        int nFormPars = meth.getLevel();
        int nLocalVals = meth.getLocalSymbols().size();

        Code.put(Code.enter);
        Code.put(nFormPars);
        Code.put(nLocalVals);
	}
	
	@Override
	public void visit(OptionalDesignatorStatement stmt) { 
	    Obj obj = stmt.getDesignator().obj;
	    stmt.getDesignator().accept(this);

	    if (stmt.getDesignatorStatementTail() instanceof AssignopExprDSTail) {
	    	((AssignopExprDSTail) stmt.getDesignatorStatementTail()).getExpr().accept(this);
	        Code.store(obj);
	    }
	    else if (stmt.getDesignatorStatementTail() instanceof ActParsDSTail) {
	    	stmt.getDesignatorStatementTail().accept(this);
	        Code.put(Code.call);
	        Code.put2(obj.getAdr());
	    }
	    else if (stmt.getDesignatorStatementTail() instanceof IncrementDSTail) {
	        Code.load(obj);
	        Code.loadConst(1);
	        Code.put(Code.add);
	        Code.store(obj);
	    }
	    else if (stmt.getDesignatorStatementTail() instanceof DecrementDSTail) {
	        Code.load(obj);
	        Code.loadConst(1);
	        Code.put(Code.sub);
	        Code.store(obj);
	    }
	}
	
	@Override
	public void visit(FixedDesignatorStatement stmt) { 
//	    Obj dest = stmt.getDesignator().obj;
//	    Obj left = stmt.getDesignator1().obj;
//	    Obj right = stmt.getDesignator2().obj;
//
//	    Code.load(left);
//	    Code.put(Code.arraylength);
//	    Code.load(right);
//	    Code.put(Code.arraylength);
//	    Code.put(Code.add);
//	    Code.put(Code.newarray);
//	    Code.put(1);
//	    Code.store(dest);
//
//	    Code.load(dest);
//	    Code.load(left);
//	    Code.put(Code.invokevirtual);
//	    Code.put2(getPredefinedMethodAddress("addAll"));
//
//	    Code.load(dest);
//	    Code.load(right);
//	    Code.put(Code.invokevirtual);
//	    Code.put2(getPredefinedMethodAddress("addAll"));
	}
	
	@Override
    public void visit(AssignopExprDSTail stmt) { }
	
	@Override
    public void visit(ActParsDSTail stmt) { }
	
	@Override
    public void visit(IncrementDSTail stmt) { }
	
	@Override
    public void visit(DecrementDSTail stmt) { }
    
	@Override
    public void visit(ReadStatement stmt) {
		Obj obj = stmt.getDesignator().obj;
		stmt.getDesignator().accept(this);
		
	    if (obj.getType() == Tab.charType) {
	        Code.put(Code.bread);
	    }
	    else {
	        Code.put(Code.read);
	    }
	    
	    Code.store(obj);
	}
    
	@Override
    public void visit(PrintStatement stmt) {
		stmt.getExpr().accept(this);
		
		int width = (stmt.getExpr().struct == Tab.charType) ? 1 : 5;
		NumConstList list = stmt.getNumConstList();
		while (list instanceof NumConsts) {
			NumConsts nc = (NumConsts) list;
			width = nc.getN1();
			list = nc.getNumConstList();
		}
		
		Code.loadConst(width);
		
        if (stmt.getExpr().struct == Tab.charType) {
            Code.put(Code.bprint);
        }
        else {
            Code.put(Code.print);
        }
    }
    
	@Override
    public void visit(NoSignExpr expr) { 
		expr.getTerm().accept(this);
		expr.getAddopTermList().accept(this);
	}
	
	@Override
    public void visit(NegativeSignExpr expr) {
		expr.getTerm().accept(this);
		expr.getAddopTermList().accept(this);
		Code.put(Code.neg);
	}
	
	@Override
    public void visit(AddopTerms list) {
		list.getAddopTermList().accept(this);
		list.getAddopTerm().accept(this);
	}
	
	@Override
    public void visit(AddopTerm addopTerm) { 
		addopTerm.getTerm().accept(this);
	    if (addopTerm.getAddop() instanceof Plus) {
	        Code.put(Code.add);
	    }
	    else if (addopTerm.getAddop() instanceof Minus) {
	        Code.put(Code.sub);
	    }
	}
	
	@Override
    public void visit(MulopFactors list) {
		list.getMulopFactorList().accept(this);
		list.getMulopFactor().accept(this);
	}
	
	@Override
    public void visit(MulopFactor mulopFactor) {
		mulopFactor.getFactor().accept(this);
	    if (mulopFactor.getMulop() instanceof Multiplication) {
	        Code.put(Code.mul);
	    }
	    else if (mulopFactor.getMulop() instanceof Division) {
	        Code.put(Code.div);
	    }
	    else if (mulopFactor.getMulop() instanceof Modulo) {
	        Code.put(Code.rem);
	    }
	}
	
	@Override
	public void visit(RelopCondFactTail tail) {
	    if (tail.getRelop() instanceof Equals) {
	        Code.put(Code.eq);
	    }
	    else if (tail.getRelop() instanceof NotEquals) {
	        Code.put(Code.ne);
	    }
	    else if (tail.getRelop() instanceof Higher) {
	        Code.put(Code.gt);
	    }
	    else if (tail.getRelop() instanceof HiglerEqual) {
	        Code.put(Code.ge);
	    }
	    else if (tail.getRelop() instanceof Lower) {
	        Code.put(Code.lt);
	    }
	    else if (tail.getRelop() instanceof LowerEqual) {
	        Code.put(Code.le);
	    }
	}
	
	@Override
    public void visit(Term term) { 
		term.getFactor().accept(this);
		term.getMulopFactorList().accept(this);
	}
	
    
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
    public void visit(DesignatorFactor factor) { 
		factor.getDesignator().accept(this);
		factor.getActParsOption().accept(this);
	    Obj obj = factor.getDesignator().obj;
	    
	    if (obj.getKind() == Obj.Meth) {
	        Code.put(Code.call);
	        Code.put2(obj.getAdr());
	    }
	    else if (obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem || obj.getKind() == Obj.Con) {
	        Code.load(obj);
	    }
	}
	
	@Override
	public void visit(ActParamsOption option) {
		option.getActParsInner().accept(this);
	}
	
	@Override
	public void visit(NoActParamsOption option) { }
	
	@Override
	public void visit(ActParamsInner inner) {
		inner.getActPars().accept(this);
	}
	
	@Override
	public void visit(NoActParamsInner inner) { }
	
	@Override
	public void visit(ActParams pars) {
		pars.getExpr().accept(this);
		pars.getExprsExtended().accept(this);
	}
	
	@Override
	public void visit(ExprsExtendedList extended) {
		extended.getExpr().accept(this);
		extended.getExprsExtended().accept(this);
	}
	
	@Override
	public void visit(NoExprsExtendedList extended) { }
	
	@Override
    public void visit(NewFactor factor) {
		if (factor.getNewFactorTail() instanceof ExprFactorTail) {
			((ExprFactorTail) factor.getNewFactorTail()).getExpr().accept(this);
		}
		
		Struct type = factor.getType().struct;
		Code.put(Code.newarray);
		
		if (type == Tab.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	@Override
    public void visit(ExprFactor factor) {
		factor.getExpr().accept(this);
	}
    
	@Override
    public void visit(Designator designator) { 
		Obj obj = designator.obj;
		
		if (obj.getKind() != Obj.Elem && obj.getType().getKind() == Struct.Array) {
			Code.load(Tab.find(designator.getName()));
		}
		
		designator.getDesignatorTail().accept(this);
	}
	
	@Override
    public void visit(DotDesignatorTail tail) { }
	
	@Override
    public void visit(ExprDesignatorTail tail) {
		tail.getExpr().accept(this);
	}
}
