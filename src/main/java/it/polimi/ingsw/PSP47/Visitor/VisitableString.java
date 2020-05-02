package it.polimi.ingsw.PSP47.Visitor;

public class VisitableString implements Visitable  {
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
