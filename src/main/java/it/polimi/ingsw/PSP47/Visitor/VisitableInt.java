package it.polimi.ingsw.PSP47.Visitor;

public class VisitableInt implements Visitable
{


    int number;

    public VisitableInt(int number) {
        this.number = number;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
