// generated with ast extension for cup
// version 0.8
// 23/8/2025 17:38:49


package rs.ac.bg.etf.pp1.ast;

public class Lower extends Relop {

    public Lower () {
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
        buffer.append("Lower(\n");

        buffer.append(tab);
        buffer.append(") [Lower]");
        return buffer.toString();
    }
}
