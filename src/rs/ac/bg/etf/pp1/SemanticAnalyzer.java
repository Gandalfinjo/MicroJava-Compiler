package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	private boolean errorDetected = false;
	private int nVars;
	
	private Struct currentType = Tab.noType;
	
	private Obj currentMethod = null;
	private int currentMethodFormalCount = 0;
	
	private boolean inMethod = false;
	
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

    public int getnVars() {
        return nVars;
    }
    
    /**
     * For every detected symbol print
     * - line
     * - symbol name
     * - string representation of Obj node from the table of symbols (obj.toString())
     * @param obj
     * @param symName
     * @param node
     */
    private void reportSymbolFound(Obj obj, String symName, SyntaxNode node) {
    	if (node != null && node.getLine() != 0) {
    		System.out.print("Symbol detected at line " + node.getLine() + " : name = " + symName + " ; obj = " + (obj != null ? obj.toString() : "null"));
    	}
    	else {
    		System.out.println("Symbol detected : name = " + symName + " ; obj = " + (obj != null ? obj.toString() : "null"));
    	}
    }
    
    @Override
    public void visit(ProgName progName) {
    	String name = progName.getProgName();
    	Obj progObj = Tab.insert(Obj.Prog, name, Tab.noType);
    	
    	progName.obj = progObj;
    	
    	Tab.openScope();
    	
    	report_info("Opened program scope for " + name, progName);
    	reportSymbolFound(progObj, name, progName);
    }
    
    @Override
    public void visit(Program program) {
    	if (program.getProgName() != null && program.getProgName().obj != null) {
    		Tab.chainLocalSymbols(program.getProgName().obj);
    	}
    	
    	nVars = Tab.currentScope.getnVars();
    	Tab.closeScope();
    	report_info("Closed program scope. Globals = " + nVars, program);
    }
    
    @Override
    public void visit(Type type) {
    	String typeName = type.getTypeName();
    	Obj typeObj = Tab.find(typeName);
    	
    	if (typeObj == Tab.noObj) {
    		report_error("Type " + typeName + " not found", type);
    		
    		type.struct = Tab.noType;
    		currentType = Tab.noType;
    		
    		return;
    	}
    	else if (typeObj.getKind() != Obj.Type) {
    		report_error("Name " + typeName + " is not a type", type);
    		
            type.struct = Tab.noType;
            currentType = Tab.noType;
            
            return;
    	}
    	
    	type.struct = typeObj.getType();
    	currentType = type.struct;
    }
    
    @Override
    public void visit(ConstDeclaration constDeclaration) {
    	String constName = constDeclaration.getName();
    	
    	if (constName == null) {
    		report_error("Const declaration without name", constDeclaration);
    		return;
    	}
    	
    	if (Tab.currentScope.findSymbol(constName) != null) {
    		report_error("Constant " + constName + " already declared in this scope", constDeclaration);
    		return;
    	}
    	
		Struct constType = currentType != null ? currentType : Tab.noType;
		
		if (constType == Tab.noType) {
			report_error("Invalid type for constant " + constName, constDeclaration);
			return;
		}
		
		Obj constObj = Tab.insert(Obj.Con, constName, constType);
		
		int value = 0;
		boolean ok = false;
		ConstDeclValue valNode = constDeclaration.getConstDeclValue();
		
		if (valNode == null) {
			report_error("Constant " + constName + " has no value", constDeclaration);
			return;
		}
		
		if (valNode instanceof NumberConst) {
			NumberConst numberConst = (NumberConst) valNode;
			value = numberConst.getN1();
			ok = true;
		}
		else if (valNode instanceof CharConst) {
			CharConst charConst = (CharConst) valNode;
			char ch = charConst.getC1();
			value = (int) ch;
			ok = true;
		}
		else if (valNode instanceof BoolConst) {
			BoolConst boolConst = (BoolConst) valNode;
			boolean b = boolConst.getB1();
			value = b ? 1 : 0;
			ok = true;
		}
		else {
			try {
				String token = valNode.toString();
				value = Integer.parseInt(token);
				ok = true;
			}
			catch (Exception e) {
				ok = false;
			}
		}
		
		if (ok) {
			constObj.setAdr(value);
			
			report_info("Declared constant" + constName + " = " + value, constDeclaration);
			reportSymbolFound(constObj, constName, constDeclaration);
		}
		else {
			report_error("Couldn't determine constant value for " + constName, constDeclaration);
		}
    }
    
    @Override
    public void visit(VarDeclaration varDeclaration) {
    	String varName = varDeclaration.getName();
    	
    	if (varName == null) {
    		report_error("Variable declaration without name", varDeclaration);
    		return;
    	}
    	
    	if (currentType == Tab.noType) {
    		report_error("Invalid type for variable " + varName, varDeclaration);
    		return;
    	}
    	
    	if (Tab.currentScope.findSymbol(varName) != null) {
    		report_error("Variable " + varName + " already declared in this scope", varDeclaration);
    		return;
    	}
    	
		Obj varObj = Tab.insert(Obj.Var, varName, currentType);
		report_info("Declared variable " + varName + " of type " + currentType, varDeclaration);
		reportSymbolFound(varObj, varName, varDeclaration);
    }
    
    @Override
    public void visit(VarDeclarationArray varDeclArray) {
    	String arrName = varDeclArray.getName();
    	
    	if (arrName == null) {
    		report_error("Array variable declaration without name", varDeclArray);
    		return;
    	}
    	
    	if (currentType == Tab.noType) {
    		report_error("Invalid base type of array " + arrName, varDeclArray);
    		return;
    	}
    	
    	if (Tab.currentScope.findSymbol(arrName) != null) {
    		report_error("Array " + arrName + " already declared in this scope", varDeclArray);
    		return;
    	}
    	
    	Struct arrType = new Struct(Struct.Array, currentType);
    	Obj arrObj = Tab.insert(Obj.Var, arrName, arrType);
    	
    	report_info("Declared array " + arrName + " of base type " + currentType, varDeclArray);
    	reportSymbolFound(arrObj, arrName, varDeclArray);
    }
    
}
