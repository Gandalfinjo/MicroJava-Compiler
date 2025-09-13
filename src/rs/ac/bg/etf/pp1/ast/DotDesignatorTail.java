// generated with ast extension for cup
// version 0.8
// 13/8/2025 19:10:57


package rs.ac.bg.etf.pp1.ast;

public class DotDesignatorTail extends DesignatorTail {

    private String field;
    private DesignatorTail DesignatorTail;

    public DotDesignatorTail (String field, DesignatorTail DesignatorTail) {
        this.field=field;
        this.DesignatorTail=DesignatorTail;
        if(DesignatorTail!=null) DesignatorTail.setParent(this);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field=field;
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

        buffer.append(" "+tab+field);
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
