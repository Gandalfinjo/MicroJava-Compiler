// generated with ast extension for cup
// version 0.8
// 12/8/2025 16:52:38


package rs.ac.bg.etf.pp1.ast;

public class InterfaceDeclarations extends InterfaceDeclList {

    private InterfaceDeclList InterfaceDeclList;
    private InterfaceDecl InterfaceDecl;

    public InterfaceDeclarations (InterfaceDeclList InterfaceDeclList, InterfaceDecl InterfaceDecl) {
        this.InterfaceDeclList=InterfaceDeclList;
        if(InterfaceDeclList!=null) InterfaceDeclList.setParent(this);
        this.InterfaceDecl=InterfaceDecl;
        if(InterfaceDecl!=null) InterfaceDecl.setParent(this);
    }

    public InterfaceDeclList getInterfaceDeclList() {
        return InterfaceDeclList;
    }

    public void setInterfaceDeclList(InterfaceDeclList InterfaceDeclList) {
        this.InterfaceDeclList=InterfaceDeclList;
    }

    public InterfaceDecl getInterfaceDecl() {
        return InterfaceDecl;
    }

    public void setInterfaceDecl(InterfaceDecl InterfaceDecl) {
        this.InterfaceDecl=InterfaceDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceDeclList!=null) InterfaceDeclList.accept(visitor);
        if(InterfaceDecl!=null) InterfaceDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceDeclList!=null) InterfaceDeclList.traverseTopDown(visitor);
        if(InterfaceDecl!=null) InterfaceDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceDeclList!=null) InterfaceDeclList.traverseBottomUp(visitor);
        if(InterfaceDecl!=null) InterfaceDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceDeclarations(\n");

        if(InterfaceDeclList!=null)
            buffer.append(InterfaceDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceDecl!=null)
            buffer.append(InterfaceDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceDeclarations]");
        return buffer.toString();
    }
}
