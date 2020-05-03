package it.polimi.ingsw.PSP47.Visitor;

public class VisitableString implements Visitable {
    private static final long serialVersionUID = -733387011246104008L;
    String string;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
