// generated with ast extension for cup
// version 0.8
// 13/8/2025 0:32:44


package rs.ac.bg.etf.pp1.ast;

public class InterfaceDeclaration extends InterfaceDecl {

    private String I1;
    private InterfaceMemberList InterfaceMemberList;

    public InterfaceDeclaration (String I1, InterfaceMemberList InterfaceMemberList) {
        this.I1=I1;
        this.InterfaceMemberList=InterfaceMemberList;
        if(InterfaceMemberList!=null) InterfaceMemberList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public InterfaceMemberList getInterfaceMemberList() {
        return InterfaceMemberList;
    }

    public void setInterfaceMemberList(InterfaceMemberList InterfaceMemberList) {
        this.InterfaceMemberList=InterfaceMemberList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceMemberList!=null) InterfaceMemberList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceMemberList!=null) InterfaceMemberList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceMemberList!=null) InterfaceMemberList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceDeclaration(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(InterfaceMemberList!=null)
            buffer.append(InterfaceMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceDeclaration]");
        return buffer.toString();
    }
}
