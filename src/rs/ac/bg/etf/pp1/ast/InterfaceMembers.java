// generated with ast extension for cup
// version 0.8
// 14/8/2025 17:37:35


package rs.ac.bg.etf.pp1.ast;

public class InterfaceMembers extends InterfaceMemberList {

    private InterfaceMemberList InterfaceMemberList;
    private InterfaceMember InterfaceMember;

    public InterfaceMembers (InterfaceMemberList InterfaceMemberList, InterfaceMember InterfaceMember) {
        this.InterfaceMemberList=InterfaceMemberList;
        if(InterfaceMemberList!=null) InterfaceMemberList.setParent(this);
        this.InterfaceMember=InterfaceMember;
        if(InterfaceMember!=null) InterfaceMember.setParent(this);
    }

    public InterfaceMemberList getInterfaceMemberList() {
        return InterfaceMemberList;
    }

    public void setInterfaceMemberList(InterfaceMemberList InterfaceMemberList) {
        this.InterfaceMemberList=InterfaceMemberList;
    }

    public InterfaceMember getInterfaceMember() {
        return InterfaceMember;
    }

    public void setInterfaceMember(InterfaceMember InterfaceMember) {
        this.InterfaceMember=InterfaceMember;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceMemberList!=null) InterfaceMemberList.accept(visitor);
        if(InterfaceMember!=null) InterfaceMember.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceMemberList!=null) InterfaceMemberList.traverseTopDown(visitor);
        if(InterfaceMember!=null) InterfaceMember.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceMemberList!=null) InterfaceMemberList.traverseBottomUp(visitor);
        if(InterfaceMember!=null) InterfaceMember.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceMembers(\n");

        if(InterfaceMemberList!=null)
            buffer.append(InterfaceMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMember!=null)
            buffer.append(InterfaceMember.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceMembers]");
        return buffer.toString();
    }
}
