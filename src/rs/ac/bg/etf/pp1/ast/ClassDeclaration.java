// generated with ast extension for cup
// version 0.8
// 12/9/2025 12:27:36


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclaration extends ClassDecl {

    private String name;
    private ExtendsType ExtendsType;
    private VarDeclList VarDeclList;
    private ClassMemberList ClassMemberList;

    public ClassDeclaration (String name, ExtendsType ExtendsType, VarDeclList VarDeclList, ClassMemberList ClassMemberList) {
        this.name=name;
        this.ExtendsType=ExtendsType;
        if(ExtendsType!=null) ExtendsType.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.ClassMemberList=ClassMemberList;
        if(ClassMemberList!=null) ClassMemberList.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public ExtendsType getExtendsType() {
        return ExtendsType;
    }

    public void setExtendsType(ExtendsType ExtendsType) {
        this.ExtendsType=ExtendsType;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public ClassMemberList getClassMemberList() {
        return ClassMemberList;
    }

    public void setClassMemberList(ClassMemberList ClassMemberList) {
        this.ClassMemberList=ClassMemberList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsType!=null) ExtendsType.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(ClassMemberList!=null) ClassMemberList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsType!=null) ExtendsType.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(ClassMemberList!=null) ClassMemberList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsType!=null) ExtendsType.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(ClassMemberList!=null) ClassMemberList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclaration(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(ExtendsType!=null)
            buffer.append(ExtendsType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMemberList!=null)
            buffer.append(ClassMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclaration]");
        return buffer.toString();
    }
}
