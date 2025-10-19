package rs.ac.bg.etf.pp1;

import java.util.Iterator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;

public class CodeGenerator extends VisitorAdaptor {
	public int mainPc;
	
	private void loadConst(int value) {
		Code.loadConst(value);
	}
	
	private int getPredefinedMethodAddress(String methodName) {
	    Obj meth = Tab.find(methodName);
	    
	    if (meth == null) {
	    	Code.error("Predefined method " + methodName + " doesn't exist");
	    	return 0;
	    }
	    
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
        
        // add(a:set, b:int) : void
        Obj addMeth = Tab.find("add");
        addMeth.setAdr(Code.pc);
        
        Code.put(Code.enter);
        Code.put(addMeth.getLevel());
        Code.put(addMeth.getLocalSymbols().size());
        
        Iterator<Obj> localSymbolsIterator = addMeth.getLocalSymbols().iterator();
        
        Obj destinationSet = localSymbolsIterator.next();
        Obj elementToAdd = localSymbolsIterator.next();
        
        Obj setSize = localSymbolsIterator.next();
        Obj currentSetIndex = localSymbolsIterator.next();
        
        Code.load(destinationSet);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(setSize);
        
        Code.loadConst(0);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(currentSetIndex);
        
        int addSearchLoopStartAddr = Code.pc;
        
        Code.load(currentSetIndex);
        Code.load(setSize);
        Code.putFalseJump(Code.le, 0);
        
        int insertAfterLoopAddr = Code.pc - 2;
        
        Code.load(destinationSet);
        Code.load(currentSetIndex);
        Code.put(Code.aload);
        Code.load(elementToAdd);
        Code.putFalseJump(Code.ne, 0);
        
        int skipInsertAddr = Code.pc - 2;
        
        Code.load(currentSetIndex);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(currentSetIndex);
        
        Code.putJump(addSearchLoopStartAddr);
        Code.fixup(insertAfterLoopAddr);
        
        Code.load(setSize);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(setSize);
        
        Code.load(destinationSet);
        Code.load(setSize);
        Code.load(elementToAdd);
        Code.put(Code.astore);
        
        Code.load(destinationSet);
        Code.loadConst(0);
        Code.load(setSize);
        Code.put(Code.astore);
        
        Code.fixup(skipInsertAddr);
        
        Code.put(Code.exit);
        Code.put(Code.return_);
        
        // addAll(a:set, b:int[]) : void
        Obj addAllMeth = Tab.find("addAll");
        addAllMeth.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(addAllMeth.getLevel());
        Code.put(addAllMeth.getLocalSymbols().size());

        Iterator<Obj> allLocalSymbolsIterator = addAllMeth.getLocalSymbols().iterator();

        Obj allDestinationSet = allLocalSymbolsIterator.next();
        Obj intArrayToAdd = allLocalSymbolsIterator.next();

        Obj currentArrayIndex = allLocalSymbolsIterator.next();

        Code.loadConst(0);
        Code.store(currentArrayIndex);

        int allInsertLoopStartAddr = Code.pc;

        Code.load(currentArrayIndex);
        Code.load(intArrayToAdd);
        
        int methodOffsetFromPc = lenMeth.getAdr() - Code.pc;
        
        Code.put(Code.call);
        Code.put2(methodOffsetFromPc);
        
        Code.putFalseJump(Code.lt, 0);
        
        int allInsertExitAddr = Code.pc - 2;

        Code.load(allDestinationSet);
        Code.load(intArrayToAdd);
        Code.load(currentArrayIndex);
        Code.put(Code.aload);
        
        methodOffsetFromPc = addMeth.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(methodOffsetFromPc);

        Code.load(currentArrayIndex);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(currentArrayIndex);

        Code.putJump(allInsertLoopStartAddr);
        Code.fixup(allInsertExitAddr);

        Code.put(Code.exit);
        Code.put(Code.return_);
        
        initPrintSetMethod();
        
        initUnionMethod();
        
        initOverlapsMethod();
        
        initMaxElementMethod();
        
        initIsEmptyMethod();
        
        initContainsMethod();
        
        initEqualsMethod();
        
        initCopyMethod();
	}
	
	private void initPrintSetMethod() {
        SemanticAnalyzer.printSetMeth.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(SemanticAnalyzer.printSetMeth.getLevel());
        Code.put(SemanticAnalyzer.printSetMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.printSetMeth.getLocalSymbols().iterator();

        Obj setToPrint = localSymbolsIterator.next();
        Obj elemPrintWidth = localSymbolsIterator.next();

        Obj setLength = localSymbolsIterator.next();
        Obj setElemIdx = localSymbolsIterator.next();

        Code.load(setToPrint);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(setLength);

        Code.load(setLength);
        Code.loadConst(0);
        Code.putFalseJump(Code.gt, 0);
        
        int emptySetSkipLoopAddr = Code.pc - 2;

        Code.loadConst(0);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(setElemIdx);

        int printLoopStartAddr = Code.pc;

        Code.load(setElemIdx);
        Code.load(setLength);
        Code.putFalseJump(Code.le, 0);
        
        int exitPrintLoopAddr = Code.pc - 2;

        Code.load(setToPrint);
        Code.load(setElemIdx);
        Code.put(Code.aload);
        Code.load(elemPrintWidth);
        Code.put(Code.print);

        Code.load(setElemIdx);
        Code.load(setLength);
        Code.putFalseJump(Code.ne, 0);
        
        int skipSpacePrintAddr = Code.pc - 2;

        Code.loadConst(' ');
        Code.loadConst(1);
        Code.put(Code.bprint);

        Code.fixup(skipSpacePrintAddr);

        Code.load(setElemIdx);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(setElemIdx);

        Code.putJump(printLoopStartAddr);
        Code.fixup(exitPrintLoopAddr);

        Code.fixup(emptySetSkipLoopAddr);

        Code.put(Code.exit);
        Code.put(Code.return_);
    }
	
	private void initUnionMethod() {
		SemanticAnalyzer.unionMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.unionMeth.getLevel());
        Code.put(SemanticAnalyzer.unionMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.unionMeth.getLocalSymbols().iterator();

        Obj destinationSet = localSymbolsIterator.next();
        Obj leftSet = localSymbolsIterator.next();
        Obj rightSet = localSymbolsIterator.next();

        Obj sourceSetSizeVar = localSymbolsIterator.next();
        Obj currentSrcSetIdx = localSymbolsIterator.next();

        ensureDestinationSetMatchesLeftAndRight(destinationSet, leftSet, rightSet);

        Code.load(destinationSet);
        Code.load(leftSet);
        Code.putFalseJump(Code.ne, 0);
        
        int leftSetMatchAddr = Code.pc - 2;

        mergeSourceIntoDestinationSet(destinationSet, leftSet, sourceSetSizeVar, currentSrcSetIdx);
        Code.fixup(leftSetMatchAddr);

        Code.load(destinationSet);
        Code.load(rightSet);
        Code.putFalseJump(Code.ne, 0);
        
        int rightSetMatchAddr = Code.pc - 2;

        mergeSourceIntoDestinationSet(destinationSet, rightSet, sourceSetSizeVar, currentSrcSetIdx);
        Code.fixup(rightSetMatchAddr);

        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	private void ensureDestinationSetMatchesLeftAndRight(Obj destinationSet, Obj leftSet, Obj rightSet) {
	    Code.load(destinationSet);
	    Code.load(leftSet);
	    Code.putFalseJump(Code.ne, 0);
	    
	    int leftSetMatchAddr = Code.pc - 2;

	    Code.load(destinationSet);
	    Code.load(rightSet);
	    Code.putFalseJump(Code.ne, 0);
	    
	    int rightSetMatchAddr = Code.pc - 2;

	    Code.load(destinationSet);
	    Code.loadConst(0);
	    Code.loadConst(0);
	    Code.put(Code.astore);

	    Code.fixup(leftSetMatchAddr);
	    Code.fixup(rightSetMatchAddr);
	}

	private void mergeSourceIntoDestinationSet(Obj destinationSet, Obj sourceSet, Obj sourceSetSize, Obj currentSourceSetIndex) {
	    Code.load(sourceSet);
	    Code.loadConst(0);
	    Code.put(Code.aload);
	    Code.store(sourceSetSize);

	    Code.loadConst(0);
	    Code.loadConst(1);
	    Code.put(Code.add);
	    Code.store(currentSourceSetIndex);

	    int sourceMergeLoopStartAddr = Code.pc;

	    Code.load(currentSourceSetIndex);
	    Code.load(sourceSetSize);
	    Code.putFalseJump(Code.le, 0);
	    
	    int exitMergeLoopAddr  = Code.pc - 2;

	    Code.load(destinationSet);
	    Code.load(sourceSet);
	    Code.load(currentSourceSetIndex);
	    Code.put(Code.aload);
	    Code.put(Code.call);
	    Code.put2(getPredefinedMethodAddress("add") - Code.pc + 1);

	    Code.load(currentSourceSetIndex);
	    Code.loadConst(1);
	    Code.put(Code.add);
	    Code.store(currentSourceSetIndex);

	    Code.putJump(sourceMergeLoopStartAddr);
	    Code.fixup(exitMergeLoopAddr);
	}
	
	private void initOverlapsMethod() {
		SemanticAnalyzer.overlapsMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.overlapsMeth.getLevel());
        Code.put(SemanticAnalyzer.overlapsMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.overlapsMeth.getLocalSymbols().iterator();

        Obj setA = localSymbolsIterator.next();
        Obj setB = localSymbolsIterator.next();
        
        Obj indexA = localSymbolsIterator.next();
        Obj indexB = localSymbolsIterator.next();
        Obj foundFlag = localSymbolsIterator.next();
        
        // foundFlag = 0 - false
        Code.loadConst(0);
        Code.store(foundFlag);
        
        // sizeA = a[0]
        Obj tempSizeA = new Obj(Obj.Var, "sizeA", Tab.intType, 10, 1);
        Code.load(setA);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSizeA);
        
        // sizeB = b[0]
        Obj tempSizeB = new Obj(Obj.Var, "sizeB", Tab.intType, 11, 1);
        Code.load(setB);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSizeB);
        
        // indexA = 1
        Code.loadConst(1);
        Code.store(indexA);
        
        int outerLoopStart = Code.pc;
        
        // if (indexA > sizeA) -> exit outer loop
        Code.load(indexA);
        Code.load(tempSizeA);
        Code.putFalseJump(Code.le, 0);
        int outerLoopExit = Code.pc - 2;
        
        // indexB = 1
        Code.loadConst(1);
        Code.store(indexB);
        
        int innerLoopStart = Code.pc;
        
        // if (indexB > sizeB) -> exit inner loop
        Code.load(indexB);
        Code.load(tempSizeB);
        Code.putFalseJump(Code.le, 0);
        int innerLoopExit = Code.pc - 2;
        
        // load a[indexA]
        Code.load(setA);
        Code.load(indexA);
        Code.put(Code.aload);
        
        // load b[indexB]
        Code.load(setB);
        Code.load(indexB);
        Code.put(Code.aload);
        
        // compare
        Code.putFalseJump(Code.eq, 0);
        int noMatchJump = Code.pc - 2;
        
        // if equal -> foundFlag = 1 -> return immediately
        Code.loadConst(1);
        Code.store(foundFlag);
        
        // jump to the end of the method
        Code.putJump(0);
        int foundReturnJump = Code.pc - 2;
        
        // fix skip over match
        Code.fixup(noMatchJump);
        
        // indexB++
        Code.load(indexB);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(indexB);
        
        // repeat inner loop
        Code.putJump(innerLoopStart);
        Code.fixup(innerLoopExit);
        
        // indexA++
        Code.load(indexA);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(indexA);
        
        // repeat outer loop
        Code.putJump(outerLoopStart);
        Code.fixup(outerLoopExit);
        
        // finish: return foundFlag
        Code.fixup(foundReturnJump);
        Code.load(foundFlag);
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	private void initMaxElementMethod() {
		SemanticAnalyzer.maxElementMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.maxElementMeth.getLevel());
        Code.put(SemanticAnalyzer.maxElementMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.maxElementMeth.getLocalSymbols().iterator();

        Obj s = localSymbolsIterator.next();
        
        Obj index = localSymbolsIterator.next();
        Obj max = localSymbolsIterator.next();
        
        // size = s[0]
        Obj tempSize = new Obj(Obj.Var, "tempSize", Tab.intType, 10, 1);
        Code.load(s);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSize);
        
        // index = 1
        Code.loadConst(1);
        Code.store(index);
        
        // max = s[1]
        Code.load(s);
        Code.load(index);
        Code.put(Code.aload);
        Code.store(max);
        
        // index = 2
        Code.loadConst(2);
        Code.store(index);
        
        int startLoop = Code.pc;
        
        // if (index > size) -> exit loop
        Code.load(index);
        Code.load(tempSize);
        Code.putFalseJump(Code.le, 0);
        int exitLoop = Code.pc - 2;
        
        // load s[index]
        Code.load(s);
        Code.load(index);
        Code.put(Code.aload);
        
        // load max
        Code.load(max);
        
        // compare
        Code.putFalseJump(Code.gt, 0);
        int lessEqualJump = Code.pc - 2;
        
        // max = s[index]
        Code.load(s);
        Code.load(index);
        Code.put(Code.aload);
        Code.store(max);
        
        // fixup lessEqualJump
        Code.fixup(lessEqualJump);
        
        // index++
        Code.load(index);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(index);
        
        // repeat loop
        Code.putJump(startLoop);
        Code.fixup(exitLoop);
        
        // return max
        Code.load(max);
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	private void initIsEmptyMethod() {
		SemanticAnalyzer.isEmptyMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.isEmptyMeth.getLevel());
        Code.put(SemanticAnalyzer.isEmptyMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.isEmptyMeth.getLocalSymbols().iterator();

        Obj s = localSymbolsIterator.next();
        
        Obj empty = localSymbolsIterator.next();
        
        // size = s[0]
        Obj tempSize = new Obj(Obj.Var, "tempSize", Tab.intType, 10, 1);
        Code.load(s);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSize);
        
        // size == 0
        Code.load(tempSize);
        Code.loadConst(0);
        Code.putFalseJump(Code.eq, 0);
        int notEmptyJump = Code.pc - 2;
        
        // empty = 1 - true;
        Code.loadConst(1);
        Code.store(empty);
        
        
        // jump to return
        Code.putJump(0);
        int jumpTrue = Code.pc - 2;
        
        // empty = 0 - false;
        Code.fixup(notEmptyJump);
        Code.loadConst(0);
        Code.store(empty);
        
        // return empty
        Code.fixup(jumpTrue);
        Code.load(empty);
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	private void initContainsMethod() {
		SemanticAnalyzer.containsMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.containsMeth.getLevel());
        Code.put(SemanticAnalyzer.containsMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.containsMeth.getLocalSymbols().iterator();

        Obj s = localSymbolsIterator.next();
        Obj x = localSymbolsIterator.next();
        
        Obj index = localSymbolsIterator.next();
        Obj containsFlag = localSymbolsIterator.next();
        
        // size = s[0]
        Obj tempSize = new Obj(Obj.Var, "tempSize", Tab.intType, 10, 1);
        Code.load(s);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSize);
        
        // index = 1
        Code.loadConst(1);
        Code.store(index);
        
        int startLoop = Code.pc;
        
        // if (index > size) -> exit loop
        Code.load(index);
        Code.load(tempSize);
        Code.putFalseJump(Code.le, 0);
        int exitLoop = Code.pc - 2;
        
        // load s[index]
        Code.load(s);
        Code.load(index);
        Code.put(Code.aload);
        
        // load x
        Code.load(x);
        
        // compare
        Code.putFalseJump(Code.eq, 0);
        int notEqualJump = Code.pc - 2;
        
        // containsFlag = 1 -> return immediately
        Code.loadConst(1);
        Code.store(containsFlag);
        
        // jump to the end of the method
        Code.putJump(0);
        int foundReturnJump = Code.pc - 2;
        
        Code.fixup(notEqualJump);
        
        // index++
        Code.load(index);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(index);
        
        // repeat the loop
        Code.putJump(startLoop);
        Code.fixup(exitLoop);
        
        // contains = 0
        Code.loadConst(0);
        Code.store(containsFlag);
        
        // return
        Code.fixup(foundReturnJump);
        Code.load(containsFlag);
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	private void initEqualsMethod() {
		SemanticAnalyzer.equalsMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.equalsMeth.getLevel());
        Code.put(SemanticAnalyzer.equalsMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.equalsMeth.getLocalSymbols().iterator();

        Obj setA = localSymbolsIterator.next();
        Obj setB = localSymbolsIterator.next();
        
        Obj indexA = localSymbolsIterator.next();
        Obj indexB = localSymbolsIterator.next();
        Obj equalsFlag = localSymbolsIterator.next();
        
        // sizeA = setA[0]
        Obj tempSizeA = new Obj(Obj.Var, "tempSizeA", Tab.intType, 10, 1);
        Code.load(setA);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSizeA);
        
        // sizeB = setB[0]
        Obj tempSizeB = new Obj(Obj.Var, "tempSizeB", Tab.intType, 11, 1);
        Code.load(setB);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSizeB);
        
        // compare sizeA and sizeB
        Code.load(tempSizeA);
        Code.load(tempSizeB);
        Code.putFalseJump(Code.eq, 0);
        int differentSizesJump = Code.pc - 2;
        
        // indexA = 1;
        Code.loadConst(1);
        Code.store(indexA);
        
        int outerLoopStart = Code.pc;
        
        // if (indexA > sizeA) -> exit outer loop
        Code.load(indexA);
        Code.load(tempSizeA);
        Code.putFalseJump(Code.le, 0);
        int exitOuterLoop = Code.pc - 2;
        
        // equalsFlag = 0 - false
        Code.loadConst(0);
        Code.store(equalsFlag);
        
        // indexB = 1
        Code.loadConst(1);
        Code.store(indexB);
        
        int innerLoopStart = Code.pc;
        
        // if (indexB > sizeB) -> exit outer loop
        Code.load(indexB);
        Code.load(tempSizeB);
        Code.putFalseJump(Code.le, 0);
        int exitInnerLoop = Code.pc - 2;
        
        // load setA[indexA]
        Code.load(setA);
        Code.load(indexA);
        Code.put(Code.aload);
        
        // load setB[indexB]
        Code.load(setB);
        Code.load(indexB);
        Code.put(Code.aload);
        
        // compare
        Code.putFalseJump(Code.eq, 0);
        int notEqual = Code.pc - 2;
        
        // equalsFlag = 1 - true
        Code.loadConst(1);
        Code.store(equalsFlag);
        Code.putJump(0);
        int breakJump = Code.pc - 2;
        
        Code.fixup(notEqual);
        
        // indexB++
        Code.load(indexB);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(indexB);
        
        // repeat inner loop
        Code.putJump(innerLoopStart);
        Code.fixup(breakJump);
        Code.fixup(exitInnerLoop);
        
        // if (equalsFlag == false) return false
        Code.load(equalsFlag);
        Code.loadConst(0);
        Code.putFalseJump(Code.eq, 0);
        int equalsTrue = Code.pc - 2;
        
        // jump to the end of the method
        Code.putJump(0);
        int notEqualJump = Code.pc - 2;
        
        Code.fixup(equalsTrue);
        
        // indexA++
        Code.load(indexA);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(indexA);
        
        // repeat outer loop
        Code.putJump(outerLoopStart);
        Code.fixup(exitOuterLoop);
        
        // equalsFlag = 1 - true
        Code.loadConst(1);
        Code.store(equalsFlag);
        
        // return
        Code.fixup(notEqualJump);
        Code.fixup(differentSizesJump);
        Code.load(equalsFlag);
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	private void initCopyMethod() {
		SemanticAnalyzer.copyMeth.setAdr(Code.pc);

		Code.put(Code.enter);
        Code.put(SemanticAnalyzer.copyMeth.getLevel());
        Code.put(SemanticAnalyzer.copyMeth.getLocalSymbols().size());

        Iterator<Obj> localSymbolsIterator = SemanticAnalyzer.copyMeth.getLocalSymbols().iterator();

        Obj dest = localSymbolsIterator.next();
        Obj src = localSymbolsIterator.next();
        
        Obj index = localSymbolsIterator.next();
        
        // size = src[0]
        Obj tempSize = new Obj(Obj.Var, "tempSize", Tab.intType, 10, 1);
        Code.load(src);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.store(tempSize);
        
        // dest[0] = src[0]
        Code.load(dest);
        Code.loadConst(0);
        Code.load(src);
        Code.loadConst(0);
        Code.put(Code.aload);
        Code.put(Code.astore);
        
        // index = 1
        Code.loadConst(1);
        Code.store(index);
        
        int startLoop = Code.pc;
        
        // if (index > size) -> exit loop
        Code.load(index);
        Code.load(tempSize);
        Code.putFalseJump(Code.le, 0);
        int exitLoop = Code.pc - 2;
        
        // dest[index] = src[index]
        Code.load(dest);
        Code.load(index);
        Code.load(src);
        Code.load(index);
        Code.put(Code.aload);
        Code.put(Code.astore);
        
        // index++
        Code.load(index);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(index);
        
        // repeat the loop
        Code.putJump(startLoop);
        Code.fixup(exitLoop);
        
        // return
        Code.put(Code.exit);
        Code.put(Code.return_);
	}
	
	@Override
	public void visit(ProgName progName) { }
	
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

	    if (stmt.getDesignatorStatementTail() instanceof AssignopExprDSTail) {	    	
	    	 AssignopExprDSTail tail = (AssignopExprDSTail) stmt.getDesignatorStatementTail();
	         tail.getExpr().accept(this);

	         Code.store(obj);
	    }
	    else if (stmt.getDesignatorStatementTail() instanceof ActParsDSTail) {
	        Code.put(Code.call);
	        Code.put2(obj.getAdr() - Code.pc + 1);
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
        Obj dest = stmt.getDesignator().obj;   // s3
        Obj left = stmt.getDesignator1().obj;  // s1
        Obj right = stmt.getDesignator2().obj; // s2
        
        Code.load(dest);
        Code.load(left);
        Code.load(right);
        Code.put(Code.call);
        Code.put2(getPredefinedMethodAddress("union") - Code.pc + 1);

//        Code.load(left);
//        Code.put(Code.arraylength);
//        Code.load(right);
//        Code.put(Code.arraylength);
//        Code.put(Code.add);
//        Code.loadConst(1);
//        Code.put(Code.add);
//        Code.put(Code.newarray);
//        Code.put(1);
//        Code.store(dest);
//
//        Code.load(dest);
//        Code.loadConst(0);
//        Code.loadConst(0);
//        Code.put(Code.astore);
//
//        Code.load(dest);
//        Code.load(left);
//        Code.put(Code.call);
//        Code.put2(getPredefinedMethodAddress("addAll") - Code.pc + 1);
//
//        Code.load(dest);
//        Code.load(right);
//        Code.put(Code.call);
//        Code.put2(getPredefinedMethodAddress("addAll") - Code.pc + 1);
    }
	
//	@Override
//	public void visit(FixedDesignatorStatement stmt) { 
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
//	}
	
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
		// stmt.getDesignator().accept(this);
		
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
		
	    if (stmt.getExpr().struct.equals(SemanticAnalyzer.boolType)) {
	        int printFalseAddr, printEndAddr;

	        Code.loadConst(1);
	        Code.putFalseJump(Code.eq, 0);
	        printFalseAddr = Code.pc - 2;

	        for (char c : "True".toCharArray()) {
	            Code.loadConst(c);
	            Code.loadConst(1);
	            Code.put(Code.bprint);
	        }
	        Code.putJump(0);
	        printEndAddr = Code.pc - 2;

	        Code.fixup(printFalseAddr);
	        for (char c : "False".toCharArray()) {
	            Code.loadConst(c);
	            Code.loadConst(1);
	            Code.put(Code.bprint);
	        }
	        Code.fixup(printEndAddr);

	        return;
	    }
		
		Code.loadConst(width);
		
		if (stmt.getExpr().struct.equals(SemanticAnalyzer.setType)) {
			Code.put(Code.call);
            Code.put2(SemanticAnalyzer.printSetMeth.getAdr() - Code.pc + 1);
        }
		else if (stmt.getExpr().struct == Tab.charType) {
            Code.put(Code.bprint);
        }
        else {
            Code.put(Code.print);
        }
    }
    
	@Override
    public void visit(NoSignExpr expr) { }
	
	@Override
    public void visit(NegativeSignExpr expr) {
		expr.getTerm().traverseBottomUp(this);
		Code.put(Code.neg);
		expr.getAddopTermList().traverseBottomUp(this);
	}
	
	@Override
    public void visit(AddopTerms list) { }
	
	@Override
    public void visit(AddopTerm addopTerm) {
	    if (addopTerm.getAddop() instanceof Plus) {
	        Code.put(Code.add);
	    }
	    else if (addopTerm.getAddop() instanceof Minus) {
	        Code.put(Code.sub);
	    }
	}
	
	@Override
    public void visit(MulopFactors list) { }
	
	@Override
    public void visit(MulopFactor mulopFactor) {		
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
    public void visit(Term term) { }
	
    
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
	    Obj obj = factor.getDesignator().obj;
	    
	    if (obj.getKind() == Obj.Meth) {
	        Code.put(Code.call);
	        Code.put2(obj.getAdr() - Code.pc + 1);
	    }
	    else if (obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem || obj.getKind() == Obj.Con) {
	        Code.load(obj);
	    }
	}
	
	@Override
	public void visit(ActParamsOption option) {	}
	
	@Override
	public void visit(NoActParamsOption option) { }
	
	@Override
	public void visit(ActParamsInner inner) { }
	
	@Override
	public void visit(NoActParamsInner inner) { }
	
	@Override
	public void visit(ActParams pars) {	}
	
	@Override
	public void visit(ExprsExtendedList extended) {	}
	
	@Override
	public void visit(NoExprsExtendedList extended) { }
	
	@Override
    public void visit(NewFactor factor) {		
		Struct type = factor.getType().struct;
		
		if (type.equals(SemanticAnalyzer.setType)) {
			Code.loadConst(1);
            Code.put(Code.add);
            Code.put(Code.newarray);
            Code.put(1);
            Code.put(Code.dup);
            Code.loadConst(0);
            Code.loadConst(0);
            Code.put(Code.astore);
		}
		else {	    
			Code.put(Code.newarray);
			
			if (type == Tab.charType) {
				Code.put(0);
			}
			else {
				Code.put(1);
			}
		}
	}
	
	@Override
    public void visit(ExprFactor factor) { }
    
	@Override
    public void visit(Designator designator) { 
		Obj obj = designator.obj;

		if (designator.getDesignatorTail() instanceof ExprDesignatorTail) {
			if (obj.getLevel() == 0) {
				 Code.put(Code.getstatic); 
			     Code.put2(obj.getAdr());
			}
			else {
				Code.load(obj);
			}
			
	        Code.put(Code.dup_x1);
	        Code.put(Code.pop);
		}
	}
	
	@Override
    public void visit(DotDesignatorTail tail) { }
	
	@Override
    public void visit(ExprDesignatorTail tail) { }
}
