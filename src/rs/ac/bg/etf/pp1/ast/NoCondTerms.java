// generated with ast extension for cup
// version 0.8
// 12/9/2025 12:27:36


package rs.ac.bg.etf.pp1.ast;

public class NoCondTerms extends CondTermList {

    public NoCondTerms () {
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
        buffer.append("NoCondTerms(\n");

        buffer.append(tab);
        buffer.append(") [NoCondTerms]");
        return buffer.toString();
    }
}
