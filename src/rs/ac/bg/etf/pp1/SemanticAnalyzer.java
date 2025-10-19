package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	public int nVars;
	
	private boolean errorDetected = false;
	private Struct currentType = Tab.noType;
	
	private int lastIntConstValue;
	private int lastCharConstValue;
	private int lastBoolConstValue;
	private int lastConstKind = -1; // 0 = int, 1 = char, 2 = bool
	
	static final Struct setType = new Struct(Struct.Array, Tab.intType);
	static final Struct boolType = new Struct(Struct.Bool);
	
	private Obj currentMethod = null;
	
	public static Obj addMeth;
	public static Obj addAllMeth;
	public static Obj printSetMeth;
	public static Obj unionMeth;
	public static Obj overlapsMeth;
	public static Obj maxElementMeth;
	public static Obj isEmptyMeth;
	public static Obj containsMeth;
	public static Obj equalsMeth;
	public static Obj copyMeth;
	public static Obj addParamA;
	public static Obj addParamB;
	public static Obj addAllParamA;
	public static Obj addAllParamB;
	
	public void initPredeclaredSymbols() {
        // Bool type
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
        addMeth = new Obj(Obj.Meth, "add", Tab.noType, 0, 2);
        Tab.currentScope.addToLocals(addMeth);
        Tab.openScope();

        Obj formalA = new Obj(Obj.Var, "a", setType, 0, 1);
        Obj formalB = new Obj(Obj.Var, "b", Tab.intType, 1, 1);

        Obj setSize = new Obj(Obj.Var, "setSize", Tab.intType, 2, 1);
        Obj currentIndex = new Obj(Obj.Var, "currentSetIndex", Tab.intType, 3, 1);

        Tab.currentScope.addToLocals(formalA);
        Tab.currentScope.addToLocals(formalB);
        Tab.currentScope.addToLocals(setSize);
        Tab.currentScope.addToLocals(currentIndex);

        addMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();

        // addAll(a:set, b:int[]) : void
        addAllMeth = new Obj(Obj.Meth, "addAll", Tab.noType, 0, 2);
        Tab.currentScope.addToLocals(addAllMeth);
        Tab.openScope();

        Obj formalAA = new Obj(Obj.Var, "a", setType, 0, 1);
        Obj formalBB = new Obj(Obj.Var, "b", new Struct(Struct.Array, Tab.intType), 1, 1);
        Obj currentArrayIndex = new Obj(Obj.Var, "currentArrayIndex", Tab.intType, 2, 1);

        Tab.currentScope.addToLocals(formalAA);
        Tab.currentScope.addToLocals(formalBB);
        Tab.currentScope.addToLocals(currentArrayIndex);

        addAllMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // printSet
        printSetMeth = new Obj(Obj.Meth, "printSet", Tab.noType, 0, 2);
        Tab.currentScope.addToLocals(printSetMeth);
        Tab.openScope();

        Obj formalSet = new Obj(Obj.Var, "setToPrint", setType, 0, 1);
        Obj formalWidth = new Obj(Obj.Var, "elemPrintWidth", Tab.intType, 1, 1);

        Obj printSetLength = new Obj(Obj.Var, "setLength", Tab.intType, 2, 1);
        Obj printSetElemIdx = new Obj(Obj.Var, "setElemIdx", Tab.intType, 3, 1);

        Tab.currentScope.addToLocals(formalSet);
        Tab.currentScope.addToLocals(formalWidth);
        Tab.currentScope.addToLocals(printSetLength);
        Tab.currentScope.addToLocals(printSetElemIdx);

        printSetMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // union
        unionMeth = new Obj(Obj.Meth, "union", Tab.noType, 0, 3);
        Tab.currentScope.addToLocals(unionMeth);
        Tab.openScope();
        
        Obj formalSymbol1 = new Obj(Obj.Var, "destinationSet", setType, 0, 1);
        Obj formalSymbol2 = new Obj(Obj.Var, "leftSet", setType, 1, 1);
        Obj formalSymbol3 = new Obj(Obj.Var, "rightSet", setType, 2, 1);
        
        Obj localSymbol1 = new Obj(Obj.Var, "sourceSetSizeVar", Tab.intType, 3, 1);
        Obj localSymbol2 = new Obj(Obj.Var, "currentSrcSetIdx", Tab.intType, 4, 1);
          
        Tab.currentScope.addToLocals(formalSymbol1);
        Tab.currentScope.addToLocals(formalSymbol2);
        Tab.currentScope.addToLocals(formalSymbol3);
        Tab.currentScope.addToLocals(localSymbol1);
        Tab.currentScope.addToLocals(localSymbol2);
        
        unionMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // overlaps(s1: set, s2: set): bool
        overlapsMeth = new Obj(Obj.Meth, "overlaps", boolType, 0, 2);
        Tab.currentScope.addToLocals(overlapsMeth);
        Tab.openScope();
        
        Obj formalSetA = new Obj(Obj.Var, "s1", setType, 0, 1);
        Obj formalSetB = new Obj(Obj.Var, "s2", setType, 1, 1);
        
        Obj overlapsIndexA = new Obj(Obj.Var, "indexA", Tab.intType, 2, 1);
        Obj overlapsIndexB = new Obj(Obj.Var, "indexB", Tab.intType, 3, 1);
        Obj overlapsFound = new Obj(Obj.Var, "foundFlag", Tab.intType, 4, 1);
        
        Tab.currentScope.addToLocals(formalSetA);
        Tab.currentScope.addToLocals(formalSetB);
        Tab.currentScope.addToLocals(overlapsIndexA);
        Tab.currentScope.addToLocals(overlapsIndexB);
        Tab.currentScope.addToLocals(overlapsFound);
        
        overlapsMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // maxElement(s: set): int
        maxElementMeth = new Obj(Obj.Meth, "maxElement", Tab.intType, 0, 1);
        Tab.currentScope.addToLocals(maxElementMeth);
        Tab.openScope();
        
        Obj maxS = new Obj(Obj.Var, "s", setType, 0, 1);
        
        Obj maxIndex = new Obj(Obj.Var, "index", Tab.intType, 1, 1);
        Obj max = new Obj(Obj.Var, "max", Tab.intType, 2, 1);
        
        Tab.currentScope.addToLocals(maxS);
        Tab.currentScope.addToLocals(maxIndex);
        Tab.currentScope.addToLocals(max);
        
        maxElementMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // isEmpty(s: set): bool
        isEmptyMeth = new Obj(Obj.Meth, "isEmpty", boolType, 0, 1);
        Tab.currentScope.addToLocals(isEmptyMeth);
        Tab.openScope();
        
        Obj emptyS = new Obj(Obj.Var, "s", setType, 0, 1);
        
        Obj emptyFlag = new Obj(Obj.Var, "empty", Tab.intType, 2, 1);
        
        Tab.currentScope.addToLocals(emptyS);
        Tab.currentScope.addToLocals(emptyFlag);
        
        isEmptyMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // contains(s: set, x: int): bool
        containsMeth = new Obj(Obj.Meth, "contains", boolType, 0, 2);
        Tab.currentScope.addToLocals(containsMeth);
        Tab.openScope();
        
        Obj containsS = new Obj(Obj.Var, "s", setType, 0, 1);
        Obj containsX = new Obj(Obj.Var, "x", Tab.intType, 1, 1);
        
        Obj containsIndex = new Obj(Obj.Var, "index", Tab.intType, 2, 1);
        Obj containsFlag = new Obj(Obj.Var, "containsFlag", Tab.intType, 3, 1);
        
        Tab.currentScope.addToLocals(containsS);
        Tab.currentScope.addToLocals(containsX);
        Tab.currentScope.addToLocals(containsIndex);
        Tab.currentScope.addToLocals(containsFlag);
        
        containsMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // equals(s1: set, s2: set): bool
        equalsMeth = new Obj(Obj.Meth, "equals", boolType, 0, 2);
        Tab.currentScope.addToLocals(equalsMeth);
        Tab.openScope();
        
        Obj equalsSetA = new Obj(Obj.Var, "setA", setType, 0, 1);
        Obj equalsSetB = new Obj(Obj.Var, "setB", setType, 1, 1);
        
        Obj equalsIndexA = new Obj(Obj.Var, "indexA", Tab.intType, 2, 1);
        Obj equalsIndexB = new Obj(Obj.Var, "indexB", Tab.intType, 3, 1);
        Obj equalsFlag = new Obj(Obj.Var, "equalsFlag", Tab.intType, 4, 1);
        
        Tab.currentScope.addToLocals(equalsSetA);
        Tab.currentScope.addToLocals(equalsSetB);
        Tab.currentScope.addToLocals(equalsIndexA);
        Tab.currentScope.addToLocals(equalsIndexB);
        Tab.currentScope.addToLocals(equalsFlag);
        
        equalsMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
        
        // copy(dest: set, src: set): void
        copyMeth = new Obj(Obj.Var, "copy", Tab.noType, 0, 2);
        Tab.currentScope.addToLocals(copyMeth);
        Tab.openScope();
        
        Obj destSet = new Obj(Obj.Var, "dest", setType, 0, 1);
        Obj srcSet = new Obj(Obj.Var, "src", setType, 1, 1);
        
        Obj copyIndex = new Obj(Obj.Var, "index", Tab.intType, 2, 1);
        
        Tab.currentScope.addToLocals(destSet);
        Tab.currentScope.addToLocals(srcSet);
        Tab.currentScope.addToLocals(copyIndex);
        
        copyMeth.setLocals(Tab.currentScope.getLocals());
        Tab.closeScope();
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
    }
    
    @Override
    public void visit(Program program) {    	
    	if (program.getProgName() != null && program.getProgName().obj != null) {
    		Tab.chainLocalSymbols(program.getProgName().obj);
    	}
    	
    	nVars = Tab.currentScope.getnVars();
    	
    	Tab.closeScope();
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
    	
        if (lastConstKind == 0 && currentType.equals(Tab.intType)) {
            con.setAdr(lastIntConstValue);
        }
        else if (lastConstKind == 1 && currentType.equals(Tab.charType)) {
            con.setAdr(lastCharConstValue);
        }
        else if (lastConstKind == 2 && currentType.equals(Tab.find("bool").getType())) {
            con.setAdr(lastBoolConstValue);
        }
        
        lastConstKind = -1;
    	
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
        
        if (lastConstKind == 0 && currentType.equals(Tab.intType)) {
            con.setAdr(lastIntConstValue);
        }
        else if (lastConstKind == 1 && currentType.equals(Tab.charType)) {
            con.setAdr(lastCharConstValue);
        }
        else if (lastConstKind == 2 && currentType.equals(Tab.find("bool").getType())) {
            con.setAdr(lastBoolConstValue);
        }
        
        lastConstKind = -1;
        
        reportSymbolFound(con, constName, constDeclExt);
    }
    
    @Override
    public void visit(NumberConst numConst) {
        if (!currentType.equals(Tab.intType)) {
            report_error("Type mismatch: expected " + currentType + " but got int", numConst);
        }
        
        lastIntConstValue = numConst.getN1();
        lastConstKind = 0;
    }

    @Override
    public void visit(CharConst charConst) {
        if (!currentType.equals(Tab.charType)) {
            report_error("Type mismatch: expected " + currentType + " but got char", charConst);
        }
        
        lastCharConstValue = (int) charConst.getC1();
        lastConstKind = 1;
    }

    @Override
    public void visit(BoolConst boolConst) {
        Struct boolType = Tab.find("bool").getType();
        if (!currentType.equals(boolType)) {
            report_error("Type mismatch: expected " + currentType + " but got bool", boolConst);
        }
        
        lastBoolConstValue = boolConst.getB1() ? 1 : 0;
        lastConstKind = 2;
    }

    
    @Override
    public void visit(VarDeclaration varDecl) {
    	String varName = varDecl.getName();
    	
    	if (Tab.currentScope.findSymbol(varName) != null) {
    		report_error("Variable " + varName + " already declared", varDecl);
    		return;
    	}
    	
    	Obj var = Tab.insert(Obj.Var, varName, currentType);
    	
        if (currentMethod != null) var.setLevel(1);
        else var.setLevel(0);
        
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
            System.out.println("VarDeclarationArray: " + name + " inserted with adr=" + obj.getAdr());
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
        
        System.out.println("=== Opened scope for method: " + name + " ===");
    }
    
    @Override
    public void visit(MethodDeclaration methodDecl) {
    	Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();
        System.out.println("=== Closed scope for method, locals: " + currentMethod.getLocalSymbols().size() + " ===");
        for (Obj local : currentMethod.getLocalSymbols()) {
            System.out.println("  Local: " + local.getName() + " adr=" + local.getAdr());
        }
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
            	Obj arrayObj = obj;
            	obj = new Obj(Obj.Elem, arrayObj.getName() + "[]", arrayObj.getType().getElemType());
            	obj.setAdr(arrayObj.getAdr());
            	obj.setLevel(arrayObj.getLevel());
            }
        }
        else if (designator.getDesignatorTail() instanceof DotDesignatorTail) {
            
        }

        designator.obj = obj;

        if (obj.getKind() != Obj.Con &&
            obj.getKind() != Obj.Var &&
            obj.getKind() != Obj.Meth &&
            obj.getKind() != Obj.Type &&
            obj.getKind() != Obj.Elem) 
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
    	if (factor.getType().struct == setType) {
            if (((ExprFactorTail) factor.getNewFactorTail()).getExpr().struct != Tab.intType) {
                report_error("Set size must be int", factor);
            }
            factor.struct = setType;
    	}
    	else if (factor.getNewFactorTail() instanceof ExprFactorTail) {
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
    public void visit(Term term) {
        if (term.getMulopFactorList() instanceof NoMulopFactors) {
            term.struct = term.getFactor().struct;
        }
        else {
            term.struct = Tab.intType;
        }
    }

    @Override
    public void visit(ExprFactor factor) {
        factor.struct = factor.getExpr().struct;
    }
    
    @Override
    public void visit(NoSignExpr expr) {
        if (expr.getAddopTermList() instanceof NoAddopTerms) {
            expr.struct = expr.getTerm().struct;
        }
        else {
            expr.struct = Tab.intType;
        }
    }

    @Override
    public void visit(NegativeSignExpr expr) {
        expr.struct = Tab.intType;
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
        
        Struct exprType = printStmt.getExpr().struct;
        if (exprType != Tab.intType && exprType != Tab.charType && !exprType.equals(boolType) && !exprType.equals(setType)) {
            report_error("Semantic error on the line " + printStmt.getLine() + ": PRINT instruction operand must be int, char or set", null);
        }
    }
    
    @Override
    public void visit(FixedDesignatorStatement stmt) {
        Obj dest = stmt.getDesignator().obj;   // s3
        Obj left = stmt.getDesignator1().obj;  // s1
        Obj right = stmt.getDesignator2().obj; // s2

        if (dest == Tab.noObj || left == Tab.noObj || right == Tab.noObj) {
            report_error("Undeclared set in union operation", stmt);
            return;
        }

        if (!dest.getType().equals(setType) || !left.getType().equals(setType) || !right.getType().equals(setType)) {
            report_error("All operands in set union must be of type set", stmt);
        }
    }
    
    public static void semanticError(String message, SyntaxNode node) {
        System.err.println("Semantic Error: " + message + 
            (node != null && node.getLine() > 0 ? " (line " + node.getLine() + ")" : ""));
    }
  
}
