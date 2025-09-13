// generated with ast extension for cup
// version 0.8
// 13/8/2025 12:25:14


package rs.ac.bg.etf.pp1.ast;

public class ExprDesignatorTail extends DesignatorTail {

    private Expr Expr;
    private DesignatorTail DesignatorTail;

    public ExprDesignatorTail (Expr Expr, DesignatorTail DesignatorTail) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.DesignatorTail=DesignatorTail;
        if(DesignatorTail!=null) DesignatorTail.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
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
        if(Expr!=null) Expr.accept(visitor);
        if(DesignatorTail!=null) DesignatorTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(DesignatorTail!=null) DesignatorTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(DesignatorTail!=null) DesignatorTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprDesignatorTail(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorTail!=null)
            buffer.append(DesignatorTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprDesignatorTail]");
        return buffer.toString();
    }
}
