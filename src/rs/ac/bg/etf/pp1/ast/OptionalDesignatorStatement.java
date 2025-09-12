// generated with ast extension for cup
// version 0.8
// 13/8/2025 0:32:44


package rs.ac.bg.etf.pp1.ast;

public class OptionalDesignatorStatement extends DesignatorStatement {

    private Designator Designator;
    private DesignatorStatementTail DesignatorStatementTail;

    public OptionalDesignatorStatement (Designator Designator, DesignatorStatementTail DesignatorStatementTail) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorStatementTail=DesignatorStatementTail;
        if(DesignatorStatementTail!=null) DesignatorStatementTail.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorStatementTail getDesignatorStatementTail() {
        return DesignatorStatementTail;
    }

    public void setDesignatorStatementTail(DesignatorStatementTail DesignatorStatementTail) {
        this.DesignatorStatementTail=DesignatorStatementTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorStatementTail!=null) DesignatorStatementTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorStatementTail!=null) DesignatorStatementTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorStatementTail!=null) DesignatorStatementTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptionalDesignatorStatement(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementTail!=null)
            buffer.append(DesignatorStatementTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptionalDesignatorStatement]");
        return buffer.toString();
    }
}
