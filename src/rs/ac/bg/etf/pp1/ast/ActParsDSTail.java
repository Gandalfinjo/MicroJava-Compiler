// generated with ast extension for cup
// version 0.8
// 11/9/2025 11:30:24


package rs.ac.bg.etf.pp1.ast;

public class ActParsDSTail extends DesignatorStatementTail {

    private ActParsInner ActParsInner;

    public ActParsDSTail (ActParsInner ActParsInner) {
        this.ActParsInner=ActParsInner;
        if(ActParsInner!=null) ActParsInner.setParent(this);
    }

    public ActParsInner getActParsInner() {
        return ActParsInner;
    }

    public void setActParsInner(ActParsInner ActParsInner) {
        this.ActParsInner=ActParsInner;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsInner!=null) ActParsInner.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsInner!=null) ActParsInner.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsInner!=null) ActParsInner.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsDSTail(\n");

        if(ActParsInner!=null)
            buffer.append(ActParsInner.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsDSTail]");
        return buffer.toString();
    }
}
