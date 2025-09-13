// generated with ast extension for cup
// version 0.8
// 13/8/2025 12:25:14


package rs.ac.bg.etf.pp1.ast;

public class ExtendsTypeError extends ExtendsType {

    public ExtendsTypeError () {
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
        buffer.append("ExtendsTypeError(\n");

        buffer.append(tab);
        buffer.append(") [ExtendsTypeError]");
        return buffer.toString();
    }
}
