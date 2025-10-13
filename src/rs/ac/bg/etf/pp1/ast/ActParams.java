// generated with ast extension for cup
// version 0.8
// 12/9/2025 12:27:36


package rs.ac.bg.etf.pp1.ast;

public class ActParams extends ActPars {

    private Expr Expr;
    private ExprsExtended ExprsExtended;

    public ActParams (Expr Expr, ExprsExtended ExprsExtended) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.ExprsExtended=ExprsExtended;
        if(ExprsExtended!=null) ExprsExtended.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public ExprsExtended getExprsExtended() {
        return ExprsExtended;
    }

    public void setExprsExtended(ExprsExtended ExprsExtended) {
        this.ExprsExtended=ExprsExtended;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(ExprsExtended!=null) ExprsExtended.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(ExprsExtended!=null) ExprsExtended.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(ExprsExtended!=null) ExprsExtended.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParams(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprsExtended!=null)
            buffer.append(ExprsExtended.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParams]");
        return buffer.toString();
    }
}
