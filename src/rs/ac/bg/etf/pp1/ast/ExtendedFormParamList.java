// generated with ast extension for cup
// version 0.8
// 12/8/2025 15:51:33


package rs.ac.bg.etf.pp1.ast;

public class ExtendedFormParamList extends FormParamExtendedList {

    private FormParamExtendedList FormParamExtendedList;
    private FormParamExtended FormParamExtended;

    public ExtendedFormParamList (FormParamExtendedList FormParamExtendedList, FormParamExtended FormParamExtended) {
        this.FormParamExtendedList=FormParamExtendedList;
        if(FormParamExtendedList!=null) FormParamExtendedList.setParent(this);
        this.FormParamExtended=FormParamExtended;
        if(FormParamExtended!=null) FormParamExtended.setParent(this);
    }

    public FormParamExtendedList getFormParamExtendedList() {
        return FormParamExtendedList;
    }

    public void setFormParamExtendedList(FormParamExtendedList FormParamExtendedList) {
        this.FormParamExtendedList=FormParamExtendedList;
    }

    public FormParamExtended getFormParamExtended() {
        return FormParamExtended;
    }

    public void setFormParamExtended(FormParamExtended FormParamExtended) {
        this.FormParamExtended=FormParamExtended;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParamExtendedList!=null) FormParamExtendedList.accept(visitor);
        if(FormParamExtended!=null) FormParamExtended.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParamExtendedList!=null) FormParamExtendedList.traverseTopDown(visitor);
        if(FormParamExtended!=null) FormParamExtended.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParamExtendedList!=null) FormParamExtendedList.traverseBottomUp(visitor);
        if(FormParamExtended!=null) FormParamExtended.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExtendedFormParamList(\n");

        if(FormParamExtendedList!=null)
            buffer.append(FormParamExtendedList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParamExtended!=null)
            buffer.append(FormParamExtended.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExtendedFormParamList]");
        return buffer.toString();
    }
}
