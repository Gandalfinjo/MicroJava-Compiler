// generated with ast extension for cup
// version 0.8
// 14/8/2025 17:37:35


package rs.ac.bg.etf.pp1.ast;

public class InterfaceDeclaration extends InterfaceDecl {

    private String name;
    private InterfaceMemberList InterfaceMemberList;

    public InterfaceDeclaration (String name, InterfaceMemberList InterfaceMemberList) {
        this.name=name;
        this.InterfaceMemberList=InterfaceMemberList;
        if(InterfaceMemberList!=null) InterfaceMemberList.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
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

        buffer.append(" "+tab+name);
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
