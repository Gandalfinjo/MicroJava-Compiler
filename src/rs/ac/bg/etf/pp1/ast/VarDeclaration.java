// generated with ast extension for cup
// version 0.8
// 13/8/2025 11:7:26


package rs.ac.bg.etf.pp1.ast;

public class VarDeclaration extends VarDecl {

    private Type Type;
    private String I2;
    private VarDeclExtendedList VarDeclExtendedList;

    public VarDeclaration (Type Type, String I2, VarDeclExtendedList VarDeclExtendedList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.VarDeclExtendedList=VarDeclExtendedList;
        if(VarDeclExtendedList!=null) VarDeclExtendedList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public VarDeclExtendedList getVarDeclExtendedList() {
        return VarDeclExtendedList;
    }

    public void setVarDeclExtendedList(VarDeclExtendedList VarDeclExtendedList) {
        this.VarDeclExtendedList=VarDeclExtendedList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclExtendedList!=null) VarDeclExtendedList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclExtendedList!=null) VarDeclExtendedList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclExtendedList!=null) VarDeclExtendedList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(VarDeclExtendedList!=null)
            buffer.append(VarDeclExtendedList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclaration]");
        return buffer.toString();
    }
}
