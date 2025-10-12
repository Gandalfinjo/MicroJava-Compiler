// generated with ast extension for cup
// version 0.8
// 11/9/2025 11:30:24


package rs.ac.bg.etf.pp1.ast;

public class HiglerEqual extends Relop {

    public HiglerEqual () {
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
        buffer.append("HiglerEqual(\n");

        buffer.append(tab);
        buffer.append(") [HiglerEqual]");
        return buffer.toString();
    }
}
