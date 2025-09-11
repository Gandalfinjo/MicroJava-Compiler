// generated with ast extension for cup
// version 0.8
// 11/8/2025 23:42:33


package rs.ac.bg.etf.pp1.ast;

public class FormParamExtendedArary extends FormParamExtended {

    private String I1;

    public FormParamExtendedArary (String I1) {
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
        buffer.append("FormParamExtendedArary(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParamExtendedArary]");
        return buffer.toString();
    }
}
