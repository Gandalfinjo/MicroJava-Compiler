// generated with ast extension for cup
// version 0.8
// 11/8/2025 23:42:33


package rs.ac.bg.etf.pp1.ast;

public class NoVarDeclarationsExtended extends VarDeclExtendedList {

    public NoVarDeclarationsExtended () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoVarDeclarationsExtended(\n");

        buffer.append(tab);
        buffer.append(") [NoVarDeclarationsExtended]");
        return buffer.toString();
    }
}
