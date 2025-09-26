// generated with ast extension for cup
// version 0.8
// 26/8/2025 12:58:24


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclaration extends ConstDecl {

    private Type Type;
    private String name;
    private ConstDeclValue ConstDeclValue;
    private ConstDeclExtendedList ConstDeclExtendedList;

    public ConstDeclaration (Type Type, String name, ConstDeclValue ConstDeclValue, ConstDeclExtendedList ConstDeclExtendedList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.name=name;
        this.ConstDeclValue=ConstDeclValue;
        if(ConstDeclValue!=null) ConstDeclValue.setParent(this);
        this.ConstDeclExtendedList=ConstDeclExtendedList;
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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

    public ConstDeclExtendedList getConstDeclExtendedList() {
        return ConstDeclExtendedList;
    }

    public void setConstDeclExtendedList(ConstDeclExtendedList ConstDeclExtendedList) {
        this.ConstDeclExtendedList=ConstDeclExtendedList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstDeclValue!=null) ConstDeclValue.accept(visitor);
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstDeclValue!=null) ConstDeclValue.traverseTopDown(visitor);
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstDeclValue!=null) ConstDeclValue.traverseBottomUp(visitor);
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(ConstDeclValue!=null)
            buffer.append(ConstDeclValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclExtendedList!=null)
            buffer.append(ConstDeclExtendedList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclaration]");
        return buffer.toString();
    }
}
