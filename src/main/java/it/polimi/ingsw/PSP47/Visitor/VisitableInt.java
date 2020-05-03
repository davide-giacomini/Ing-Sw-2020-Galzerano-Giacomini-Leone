package it.polimi.ingsw.PSP47.Visitor;

/**
 * This class implements the Visitable interface of the Visitor pattern.
 * It represents the number of players of the game.
 */
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
