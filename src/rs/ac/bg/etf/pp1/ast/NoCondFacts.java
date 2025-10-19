// generated with ast extension for cup
// version 0.8
// 16/9/2025 14:30:9


package rs.ac.bg.etf.pp1.ast;

public class NoCondFacts extends CondFactList {

    public NoCondFacts () {
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
        buffer.append("NoCondFacts(\n");

        buffer.append(tab);
        buffer.append(") [NoCondFacts]");
        return buffer.toString();
    }
}
