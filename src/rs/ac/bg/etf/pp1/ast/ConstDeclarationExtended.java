// generated with ast extension for cup
// version 0.8
// 14/8/2025 17:37:35


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationExtended extends ConstDeclExtended {

    private String name;
    private ConstDeclValue ConstDeclValue;

    public ConstDeclarationExtended (String name, ConstDeclValue ConstDeclValue) {
        this.name=name;
        this.ConstDeclValue=ConstDeclValue;
        if(ConstDeclValue!=null) ConstDeclValue.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public ConstDeclValue getConstDeclValue() {
        return ConstDeclValue;
    }

    public void setConstDeclValue(ConstDeclValue ConstDeclValue) {
        this.ConstDeclValue=ConstDeclValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclValue!=null) ConstDeclValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclValue!=null) ConstDeclValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclValue!=null) ConstDeclValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationExtended(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(ConstDeclValue!=null)
            buffer.append(ConstDeclValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationExtended]");
        return buffer.toString();
    }
}
