package it.polimi.ingsw.PSP47.Visitor;

public class VisitableInt implements Visitable
{
    private static final long serialVersionUID = -1988276850952162862L;
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
