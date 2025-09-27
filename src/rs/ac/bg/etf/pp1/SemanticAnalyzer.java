package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	public int nVars;
	
	private boolean errorDetected = false;
	private Struct currentType = Tab.noType;
	
	// flag za proveru da li smo u globalnom scope-u
	private boolean inProgramScope = false;
	
	private static final Struct setType = new Struct(Struct.Array, Tab.intType);
	
	private Obj currentMethod = null;
	
	public static Obj addMeth;
	public static Obj addAllMeth;
	public static Obj addParamA;
	public static Obj addParamB;
	public static Obj addAllParamA;
	public static Obj addAllParamB;
	
	public void initPredeclaredSymbols() {
        // Bool type
        Struct boolType = new Struct(Struct.Bool);
        Tab.insert(Obj.Type, "bool", boolType);

        // Set type
        Tab.insert(Obj.Type, "set", setType);

        // null constant
        Tab.insert(Obj.Con, "null", Tab.nullType);

        // eol constant (char '\n')
        Obj eolObj = Tab.insert(Obj.Con, "eol", Tab.charType);
        eolObj.setAdr('\n');

        // chr(i:int) : char
        Obj chrMeth = Tab.insert(Obj.Meth, "chr", Tab.charType);
        Tab.openScope();
        Tab.insert(Obj.Var, "i", Tab.intType);
        Tab.closeScope();
        chrMeth.setLevel(1);

        // ord(ch:char) : int
        Obj ordMeth = Tab.insert(Obj.Meth, "ord", Tab.intType);
        Tab.openScope();
        Tab.insert(Obj.Var, "ch", Tab.charType);
        Tab.closeScope();
        ordMeth.setLevel(1);
        
        // len(obj:array) : int
        Obj lenMeth = Tab.insert(Obj.Meth, "len", Tab.intType);
        Tab.openScope();
        Tab.insert(Obj.Var, "obj", new Struct(Struct.Array));
        Tab.closeScope();
        lenMeth.setLevel(1);

        // add(a:set, b:int) : void
        addMeth = Tab.insert(Obj.Meth, "add", Tab.noType);
        Tab.openScope();
        addParamA = Tab.insert(Obj.Var, "a", setType);
        addParamB = Tab.insert(Obj.Var, "b", Tab.intType);
        Tab.closeScope();
        addMeth.setLevel(2);

        // addAll(a:set, b:int[]) : void
        addAllMeth = Tab.insert(Obj.Meth, "addAll", Tab.noType);
        Tab.openScope();
        addAllParamA = Tab.insert(Obj.Var, "a", setType);
        addAllParamB = Tab.insert(Obj.Var, "b", new Struct(Struct.Array, Tab.intType));
        Tab.closeScope();
        addAllMeth.setLevel(2);
    }

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		
		StringBuilder msg = new StringBuilder("Semantic error: ").append(message);
		
		if (info != null && info.getLine() != 0)
			msg.append(" at line ").append(info.getLine());
		
		System.err.println(msg.toString());
	}
	
    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder("Info: ").append(message);
        
        if (info != null && info.getLine() != 0)
            msg.append(" at line ").append(info.getLine());
        
        System.out.println(msg.toString());
    }

    public boolean passed() {
        return !errorDetected;
    }
    
    /**
     * Štampanje detektovanog simbola:
     * - linija izvornog koda
     * - naziv simbola
     * - objekat iz tabele simbola (obj.toString())
     */
    private void reportSymbolFound(Obj obj, String symName, SyntaxNode node) {
    	if (node != null && node.getLine() != 0) {
    		System.out.println("Symbol detected at line " + node.getLine() +
    			" : name = " + symName +
    			" ; obj = " + (obj != null ? obj.toString() : "null"));
    	}
    	else {
    		System.out.println("Symbol detected : name = " + symName +
    			" ; obj = " + (obj != null ? obj.toString() : "null"));
    	}
    }

    @Override
    public void visit(ProgName progName) {
    	String name = progName.getProgName();
    	Obj progObj = Tab.insert(Obj.Prog, name, Tab.noType);
    	progName.obj = progObj;
    	
    	Tab.openScope();
    	inProgramScope = true;
    }
    
    @Override
    public void visit(Program program) {    	
    	if (program.getProgName() != null && program.getProgName().obj != null) {
    		Tab.chainLocalSymbols(program.getProgName().obj);
    	}
    	
    	nVars = Tab.currentScope.getnVars();
    	
    	Tab.closeScope();
    	inProgramScope = false;
    }

    @Override
    public void visit(Type type) {
    	String typeName = type.getTypeName();
    	Obj typeObj = Tab.find(typeName);
    	
    	if (typeObj == Tab.noObj) {
    		report_error("Type " + typeName + " not found", type);
    		type.struct = Tab.noType;
    		currentType = Tab.noType;
    	}
    	else if (typeObj.getKind() != Obj.Type) {
    		report_error("Name " + typeName + " is not a type", type);
    		type.struct = Tab.noType;
    		currentType = Tab.noType;
    	}
    	else {
    		type.struct = typeObj.getType();
    		currentType = type.struct;
    	}
    }

    @Override
    public void visit(ConstDeclaration constDecl) {
    	String constName = constDecl.getName();
    	
    	if (Tab.currentScope.findSymbol(constName) != null) {
    		report_error("Constant " + constName + " already declared", constDecl);
    		return;
    	}
    	
    	Obj con = Tab.insert(Obj.Con, constName, currentType);
    	
    	reportSymbolFound(con, constName, constDecl);
    }
    
    @Override
    public void visit(ConstDeclarationExtended constDeclExt) {
        String constName = constDeclExt.getName();

        if (Tab.currentScope.findSymbol(constName) != null) {
            report_error("Constant " + constName + " already declared", constDeclExt);
            return;
        }

        Obj con = Tab.insert(Obj.Con, constName, currentType);
        reportSymbolFound(con, constName, constDeclExt);
    }
    
    @Override
    public void visit(NumberConst numConst) {
        if (!currentType.equals(Tab.intType)) {
            report_error("Type mismatch: expected " + currentType + " but got int", numConst);
        }
    }

    @Override
    public void visit(CharConst charConst) {
        if (!currentType.equals(Tab.charType)) {
            report_error("Type mismatch: expected " + currentType + " but got char", charConst);
        }
    }

    @Override
    public void visit(BoolConst boolConst) {
        Struct boolType = Tab.find("bool").getType();
        if (!currentType.equals(boolType)) {
            report_error("Type mismatch: expected " + currentType + " but got bool", boolConst);
        }
    }

    
    @Override
    public void visit(VarDeclaration varDecl) {
    	String varName = varDecl.getName();
    	
    	if (Tab.currentScope.findSymbol(varName) != null) {
    		report_error("Variable " + varName + " already declared", varDecl);
    		return;
    	}
    	
    	Obj var = Tab.insert(Obj.Var, varName, currentType);
    	reportSymbolFound(var, varName, varDecl);
    }
    
    @Override
    public void visit(VarDeclarationArray varDeclArray) {
        String name = varDeclArray.getName();
        Struct type = varDeclArray.getType().struct;

        Struct arrayType = new Struct(Struct.Array, type);

        if (Tab.currentScope.findSymbol(name) != null) {
            report_error("Variable " + name + " already declared in this scope", varDeclArray);
        }
        else {
            Obj obj = Tab.insert(Obj.Var, name, arrayType);
            varDeclArray.obj = obj;
            report_info("Declared array " + name + " of type " + type, varDeclArray);
        }
    }
    
    @Override
    public void visit(VarDeclarationExtended varDeclExt) {
        String name = varDeclExt.getName();

        if (Tab.currentScope.findSymbol(name) != null) {
            report_error("Variable " + name + " already declared in this scope", varDeclExt);
        }
        else {
            Obj obj = Tab.insert(Obj.Var, name, currentType);
            varDeclExt.obj = obj;
            reportSymbolFound(obj, name, varDeclExt);
        }
    }
    
    @Override
    public void visit(VarDeclarationExtendedArray varDeclExtArr) {
        String name = varDeclExtArr.getName();
        Struct arrayType = new Struct(Struct.Array, currentType);

        if (Tab.currentScope.findSymbol(name) != null) {
            report_error("Variable " + name + " already declared in this scope", varDeclExtArr);
        }
        else {
            Obj obj = Tab.insert(Obj.Var, name, arrayType);
            varDeclExtArr.obj = obj;
            reportSymbolFound(obj, name, varDeclExtArr);
        }
    }
    
    @Override
    public void visit(TypeMethodSignature methodSig) {
        String name = methodSig.getName();
        Obj methodObj = Tab.insert(Obj.Meth, name, methodSig.getType().struct);
        methodSig.obj = methodObj;
        currentMethod = methodObj;

        Tab.openScope();
    }

    @Override
    public void visit(VoidMethodSignature methodSig) {
        String name = methodSig.getName();
        Obj methodObj = Tab.insert(Obj.Meth, name, Tab.noType);
        methodSig.obj = methodObj;
        currentMethod = methodObj;

        Tab.openScope();
    }
    
    @Override
    public void visit(MethodDeclaration methodDecl) {
        Tab.closeScope();
        currentMethod = null;
    }
    
    @Override
    public void visit(FormParamExtendedNormal param) {
        String name = param.getName();
        if (Tab.currentScope.findSymbol(name) != null) {
            report_error("Formal parameter " + name + " already declared in this scope", param);
        }
        else {
            Obj obj = Tab.insert(Obj.Var, name, param.getType().struct);
            param.obj = obj;
            reportSymbolFound(obj, name, param);
        }
    }

    @Override
    public void visit(FormParamExtendedArary param) {
        String name = param.getName();
        if (Tab.currentScope.findSymbol(name) != null) {
            report_error("Formal parameter " + name + " already declared in this scope", param);
        }
        else {
            Struct arrayType = new Struct(Struct.Array, param.getType().struct);
            Obj obj = Tab.insert(Obj.Var, name, arrayType);
            param.obj = obj;
            reportSymbolFound(obj, name, param);
        }
    }
    
    @Override
    public void visit(FormParamList paramList) {
        Type type = paramList.getType();
        String name = paramList.getName();
        
        if (Tab.currentScope.findSymbol(name) != null) {
            report_error("Formal parameter " + name + " already declared in this scope", paramList);
        }
        else {
            Struct paramType = type.struct;
            if (paramList.getFormParamArray() instanceof FormArrayBrackets) {
                paramType = new Struct(Struct.Array, type.struct);
            }
            Obj obj = Tab.insert(Obj.Var, name, paramType);
            paramList.obj = obj;
            reportSymbolFound(obj, name, paramList);
        }
    }

    @Override
    public void visit(Designator designator) {
        String name = designator.getName();
        Obj obj = Tab.find(name);

        if (obj == Tab.noObj) {
            report_error("Undeclared identifier " + name, designator);
            designator.obj = Tab.noObj;
            return;
        }
        
        if (designator.getDesignatorTail() instanceof ExprDesignatorTail) {
            Expr index = ((ExprDesignatorTail) designator.getDesignatorTail()).getExpr();
            if (obj.getType().getKind() != Struct.Array) {
                report_error("Cannot index non-array variable " + name, designator);
            }
            else if (index.struct != Tab.intType) {
                report_error("Array index must be of type int", designator);
            }
            else {
                obj = new Obj(Obj.Elem, name + "[]", obj.getType().getElemType());
            }
        }
        else if (designator.getDesignatorTail() instanceof DotDesignatorTail) {
            
        }

        designator.obj = obj;

        if (obj.getKind() != Obj.Con &&
            obj.getKind() != Obj.Var &&
            obj.getKind() != Obj.Meth &&
            obj.getKind() != Obj.Type) 
        {
            report_error("Identifier " + name + " is not a variable, constant, method or type", designator);
        }

        reportSymbolFound(obj, name, designator);
    }
    
    @Override
    public void visit(NumFactor factor) {
        factor.struct = Tab.intType;
    }

    @Override
    public void visit(CharFactor factor) {
        factor.struct = Tab.charType;
    }

    @Override
    public void visit(BoolFactor factor) {
        factor.struct = Tab.find("bool").getType();
    }

    @Override
    public void visit(NewFactor factor) {
        if (factor.getNewFactorTail() instanceof ExprFactorTail) {
            Expr e = ((ExprFactorTail) factor.getNewFactorTail()).getExpr();
            if (e.struct != Tab.intType) {
                report_error("Array size must be of type int", factor);
            }
            factor.struct = new Struct(Struct.Array, factor.getType().struct);
        }
        else {
            factor.struct = Tab.noType;
        }
    }

    @Override
    public void visit(ExprFactor factor) {
        factor.struct = factor.getExpr().struct;
    }
    
    @Override
    public void visit(DesignatorFactor df) {
    	df.struct = df.getDesignator().obj.getType();
        Obj obj = df.getDesignator().obj;
        if (obj.getKind() == Obj.Meth) {
            reportSymbolFound(obj, obj.getName(), df);
        }
    }
        
    @Override
    public void visit(IfStatement ifStmt) {
        if (ifStmt.getCondition().struct != Tab.find("bool").getType()) {
            report_error("Condition in IF must be of type bool", ifStmt);
        }
    }

    @Override
    public void visit(DoWhileStatement doWhileStmt) {
        if (doWhileStmt.getDoWhileOption() instanceof YesDoWhileOption) {
            YesDoWhileOption opt = (YesDoWhileOption) doWhileStmt.getDoWhileOption();
            if (opt.getCondition().struct != Tab.find("bool").getType()) {
                report_error("Condition in DO-WHILE must be of type bool", doWhileStmt);
            }
        }
    }
    
    @Override
    public void visit(PrintStatement printStmt) {
        if (printStmt.getExpr() != null) {
            printStmt.getExpr().struct = printStmt.getExpr().struct;
        }
    }
    
//    public void visit(PrintStatement print) {
//    	if (print.getExpr().struct != Tab.intType && print.getExpr().struct != Tab.charType)
//    		report_error("Semantic error on the line " + print.getLine() + ": PRINT instruction operand must be int or char", null);
//	}
  
}
