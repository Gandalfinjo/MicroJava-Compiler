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
    
    @Override
    public void visit(ClassDeclaration classDeclaration) {
    	String className = classDeclaration.getName();
    	
    	if (className == null) {
    		report_error("Class declaration without name", classDeclaration);
    		return;
    	}
    	
    	if (Tab.currentScope.findSymbol(className) != null) {
    		report_error("Class " + className + " already declared in this scope", classDeclaration);
    		return;
    	}
    	
    	Obj classObj = Tab.insert(Obj.Type, className, new Struct(Struct.Class));
    	
    	report_info("Declared class type " + className, classDeclaration);
    	reportSymbolFound(classObj, className, classDeclaration);
    	
    	Tab.openScope();
    	
    	// Tab.closeScope();
    }
    
    @Override
    public void visit(InterfaceDeclaration interfaceDeclaration) {
    	String interfaceName = interfaceDeclaration.getName();
    	
    	if (interfaceName == null) {
    		report_error("Interface declaration without name", interfaceDeclaration);
    		return;
    	}
    	
    	if (Tab.currentScope.findSymbol(interfaceName) != null) {
    		report_error("Interface " + interfaceName + " already declared in this scope", interfaceDeclaration);
    		return;
    	}
    	
    	Obj interfaceObj = Tab.insert(Obj.Type, interfaceName, new Struct(Struct.Interface));
    	
    	report_info("Declared interface " + interfaceName, interfaceDeclaration);
    	reportSymbolFound(interfaceObj, interfaceName, interfaceDeclaration);
    }
    
    @Override
    public void visit(TypeMethodSignature typeMethodSignature) {
    	String typeMethodName = typeMethodSignature.getName();
    	
    	if (typeMethodName == null) {
    		report_error("Method signature without name", typeMethodSignature);
    		return;
    	}
    	
    	
    	if (Tab.currentScope.findSymbol(typeMethodName) != null) {
    		report_error("Method " + typeMethodName + " already declared in this scope", typeMethodSignature);
    		return;
    	}
    	
    	Struct methodReturnType = typeMethodSignature.getType().struct;
    	Obj methodObj = Tab.insert(Obj.Meth, typeMethodName, methodReturnType);
    	
    	typeMethodSignature.obj = methodObj;
    	currentMethod = methodObj;
    	inMethod = true;
    	currentMethodFormalCount = 0;
    	
    	Tab.openScope();
    	
    	report_info("Entered method " + typeMethodName + " with return type " + methodReturnType, typeMethodSignature);
    	reportSymbolFound(methodObj, typeMethodName, typeMethodSignature);
    }
    
    @Override
    public void visit(VoidMethodSignature voidMethodSignature) {
    	String voidMethodName = voidMethodSignature.getName();
    	
    	if (voidMethodName == null) {
    		report_error("Method signature without name", voidMethodSignature);
    		return;
    	}
    	
    	if (Tab.currentScope.findSymbol(voidMethodName) != null) {
    		report_error("Method " + voidMethodName + " already declared in this scope", voidMethodSignature);
    		return;
    	}
    	
    	Struct methodReturnType = Tab.noType;
    	Obj methodObj = Tab.insert(Obj.Meth, voidMethodName, methodReturnType);
    	
    	voidMethodSignature.obj = methodObj;
    	currentMethod = methodObj;
    	inMethod = true;
    	currentMethodFormalCount = 0;
    	
    	Tab.openScope();
    	
    	report_info("Entered method " + voidMethodName + " with return type " + methodReturnType, voidMethodSignature);
    	reportSymbolFound(methodObj, voidMethodName, voidMethodSignature);
    }
    
    @Override
    public void visit(MethodDeclaration methodDeclaration) {
    	if (currentMethod != null) {
    		Tab.chainLocalSymbols(currentMethod);
    		Tab.closeScope();
    		report_info("Exited method " + currentMethod.getName() + " with " + currentMethodFormalCount + " formal params", methodDeclaration);
    	}
    	
    	currentMethod = null;
    	inMethod = false;
    }
    
}