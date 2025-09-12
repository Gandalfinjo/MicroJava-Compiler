// generated with ast extension for cup
// version 0.8
// 12/8/2025 21:54:39


package rs.ac.bg.etf.pp1.ast;

public class FormParamList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private String I2;
    private FormParamArray FormParamArray;
    private FormParamExtendedList FormParamExtendedList;

    public FormParamList (Type Type, String I2, FormParamArray FormParamArray, FormParamExtendedList FormParamExtendedList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.I2=I2;
        this.FormParamArray=FormParamArray;
        if(FormParamArray!=null) FormParamArray.setParent(this);
        this.FormParamExtendedList=FormParamExtendedList;
        if(FormParamExtendedList!=null) FormParamExtendedList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public FormParamArray getFormParamArray() {
        return FormParamArray;
    }

    public void setFormParamArray(FormParamArray FormParamArray) {
        this.FormParamArray=FormParamArray;
    }

    public FormParamExtendedList getFormParamExtendedList() {
        return FormParamExtendedList;
    }

    public void setFormParamExtendedList(FormParamExtendedList FormParamExtendedList) {
        this.FormParamExtendedList=FormParamExtendedList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(FormParamArray!=null) FormParamArray.accept(visitor);
        if(FormParamExtendedList!=null) FormParamExtendedList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(FormParamArray!=null) FormParamArray.traverseTopDown(visitor);
        if(FormParamExtendedList!=null) FormParamExtendedList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(FormParamArray!=null) FormParamArray.traverseBottomUp(visitor);
        if(FormParamExtendedList!=null) FormParamExtendedList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParamList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(FormParamArray!=null)
            buffer.append(FormParamArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParamExtendedList!=null)
            buffer.append(FormParamExtendedList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParamList]");
        return buffer.toString();
    }
}
