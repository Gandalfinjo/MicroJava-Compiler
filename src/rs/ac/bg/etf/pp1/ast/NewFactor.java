// generated with ast extension for cup
// version 0.8
// 13/8/2025 19:10:57


package rs.ac.bg.etf.pp1.ast;

public class NewFactor extends Factor {

    private Type Type;
    private NewFactorTail NewFactorTail;

    public NewFactor (Type Type, NewFactorTail NewFactorTail) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.NewFactorTail=NewFactorTail;
        if(NewFactorTail!=null) NewFactorTail.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public NewFactorTail getNewFactorTail() {
        return NewFactorTail;
    }

    public void setNewFactorTail(NewFactorTail NewFactorTail) {
        this.NewFactorTail=NewFactorTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(NewFactorTail!=null) NewFactorTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(NewFactorTail!=null) NewFactorTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(NewFactorTail!=null) NewFactorTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewFactor(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NewFactorTail!=null)
            buffer.append(NewFactorTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewFactor]");
        return buffer.toString();
    }
}
