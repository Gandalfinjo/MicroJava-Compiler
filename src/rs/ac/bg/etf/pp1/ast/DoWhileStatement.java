// generated with ast extension for cup
// version 0.8
// 13/8/2025 11:7:26


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStatement extends Statement {

    private Statement Statement;
    private DoWhileOption DoWhileOption;

    public DoWhileStatement (Statement Statement, DoWhileOption DoWhileOption) {
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.DoWhileOption=DoWhileOption;
        if(DoWhileOption!=null) DoWhileOption.setParent(this);
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public DoWhileOption getDoWhileOption() {
        return DoWhileOption;
    }

    public void setDoWhileOption(DoWhileOption DoWhileOption) {
        this.DoWhileOption=DoWhileOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Statement!=null) Statement.accept(visitor);
        if(DoWhileOption!=null) DoWhileOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(DoWhileOption!=null) DoWhileOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(DoWhileOption!=null) DoWhileOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStatement(\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoWhileOption!=null)
            buffer.append(DoWhileOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStatement]");
        return buffer.toString();
    }
}
