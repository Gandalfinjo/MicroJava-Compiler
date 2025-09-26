// generated with ast extension for cup
// version 0.8
// 26/8/2025 12:58:24


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationsExtended extends ConstDeclExtendedList {

    private ConstDeclExtendedList ConstDeclExtendedList;
    private ConstDeclExtended ConstDeclExtended;

    public ConstDeclarationsExtended (ConstDeclExtendedList ConstDeclExtendedList, ConstDeclExtended ConstDeclExtended) {
        this.ConstDeclExtendedList=ConstDeclExtendedList;
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.setParent(this);
        this.ConstDeclExtended=ConstDeclExtended;
        if(ConstDeclExtended!=null) ConstDeclExtended.setParent(this);
    }

    public ConstDeclExtendedList getConstDeclExtendedList() {
        return ConstDeclExtendedList;
    }

    public void setConstDeclExtendedList(ConstDeclExtendedList ConstDeclExtendedList) {
        this.ConstDeclExtendedList=ConstDeclExtendedList;
    }

    public ConstDeclExtended getConstDeclExtended() {
        return ConstDeclExtended;
    }

    public void setConstDeclExtended(ConstDeclExtended ConstDeclExtended) {
        this.ConstDeclExtended=ConstDeclExtended;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.accept(visitor);
        if(ConstDeclExtended!=null) ConstDeclExtended.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.traverseTopDown(visitor);
        if(ConstDeclExtended!=null) ConstDeclExtended.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclExtendedList!=null) ConstDeclExtendedList.traverseBottomUp(visitor);
        if(ConstDeclExtended!=null) ConstDeclExtended.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationsExtended(\n");

        if(ConstDeclExtendedList!=null)
            buffer.append(ConstDeclExtendedList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclExtended!=null)
            buffer.append(ConstDeclExtended.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationsExtended]");
        return buffer.toString();
    }
}
