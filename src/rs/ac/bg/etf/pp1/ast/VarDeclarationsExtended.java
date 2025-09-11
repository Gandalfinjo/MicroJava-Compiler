// generated with ast extension for cup
// version 0.8
// 11/8/2025 23:42:33


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationsExtended extends VarDeclExtendedList {

    private VarDeclExtendedList VarDeclExtendedList;
    private VarDeclExtended VarDeclExtended;

    public VarDeclarationsExtended (VarDeclExtendedList VarDeclExtendedList, VarDeclExtended VarDeclExtended) {
        this.VarDeclExtendedList=VarDeclExtendedList;
        if(VarDeclExtendedList!=null) VarDeclExtendedList.setParent(this);
        this.VarDeclExtended=VarDeclExtended;
        if(VarDeclExtended!=null) VarDeclExtended.setParent(this);
    }

    public VarDeclExtendedList getVarDeclExtendedList() {
        return VarDeclExtendedList;
    }

    public void setVarDeclExtendedList(VarDeclExtendedList VarDeclExtendedList) {
        this.VarDeclExtendedList=VarDeclExtendedList;
    }

    public VarDeclExtended getVarDeclExtended() {
        return VarDeclExtended;
    }

    public void setVarDeclExtended(VarDeclExtended VarDeclExtended) {
        this.VarDeclExtended=VarDeclExtended;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclExtendedList!=null) VarDeclExtendedList.accept(visitor);
        if(VarDeclExtended!=null) VarDeclExtended.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclExtendedList!=null) VarDeclExtendedList.traverseTopDown(visitor);
        if(VarDeclExtended!=null) VarDeclExtended.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclExtendedList!=null) VarDeclExtendedList.traverseBottomUp(visitor);
        if(VarDeclExtended!=null) VarDeclExtended.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationsExtended(\n");

        if(VarDeclExtendedList!=null)
            buffer.append(VarDeclExtendedList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclExtended!=null)
            buffer.append(VarDeclExtended.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationsExtended]");
        return buffer.toString();
    }
}
