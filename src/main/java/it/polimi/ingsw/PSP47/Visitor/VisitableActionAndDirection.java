package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Direction;

/**
 * This class implements the Visitable interface of the Visitor pattern.
 * It represents a turn action.
 */
public class VisitableActionAndDirection implements Visitable  {
    private static final long serialVersionUID = -9154274020524036911L;
    private Action action;
    private Direction direction;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Action getAction() {
        return action;
    }

    public Direction getDirection() {
        return direction;
    }
}
