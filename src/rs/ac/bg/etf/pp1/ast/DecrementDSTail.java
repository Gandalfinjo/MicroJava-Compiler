// generated with ast extension for cup
// version 0.8
// 14/8/2025 17:37:35


package rs.ac.bg.etf.pp1.ast;

public class DecrementDSTail extends DesignatorStatementTail {

    public DecrementDSTail () {
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
        buffer.append("DecrementDSTail(\n");

        buffer.append(tab);
        buffer.append(") [DecrementDSTail]");
        return buffer.toString();
    }
}
