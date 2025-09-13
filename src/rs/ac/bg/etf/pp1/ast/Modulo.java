// generated with ast extension for cup
// version 0.8
// 13/8/2025 19:10:57


package rs.ac.bg.etf.pp1.ast;

public class Modulo extends Mulop {

    public Modulo () {
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
        buffer.append("Modulo(\n");

        buffer.append(tab);
        buffer.append(") [Modulo]");
        return buffer.toString();
    }
}
