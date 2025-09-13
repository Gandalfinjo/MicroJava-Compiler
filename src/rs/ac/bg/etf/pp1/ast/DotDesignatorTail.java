// generated with ast extension for cup
// version 0.8
// 13/8/2025 11:7:26


package rs.ac.bg.etf.pp1.ast;

public class DotDesignatorTail extends DesignatorTail {

    private String I1;
    private DesignatorTail DesignatorTail;

    public DotDesignatorTail (String I1, DesignatorTail DesignatorTail) {
        this.I1=I1;
        this.DesignatorTail=DesignatorTail;
        if(DesignatorTail!=null) DesignatorTail.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public DesignatorTail getDesignatorTail() {
        return DesignatorTail;
    }

    public void setDesignatorTail(DesignatorTail DesignatorTail) {
        this.DesignatorTail=DesignatorTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorTail!=null) DesignatorTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorTail!=null) DesignatorTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorTail!=null) DesignatorTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DotDesignatorTail(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(DesignatorTail!=null)
            buffer.append(DesignatorTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DotDesignatorTail]");
        return buffer.toString();
    }
}
