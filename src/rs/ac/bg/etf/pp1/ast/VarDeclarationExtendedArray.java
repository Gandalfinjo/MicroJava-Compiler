// generated with ast extension for cup
// version 0.8
// 12/8/2025 16:33:1


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationExtendedArray extends VarDeclExtended {

    private String I1;

    public VarDeclarationExtendedArray (String I1) {
        this.I1=I1;
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
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
        buffer.append("VarDeclarationExtendedArray(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationExtendedArray]");
        return buffer.toString();
    }
}
