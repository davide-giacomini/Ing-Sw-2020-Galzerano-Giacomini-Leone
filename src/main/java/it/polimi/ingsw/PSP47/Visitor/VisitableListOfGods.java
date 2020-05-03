package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;

/**
 * This class implements the Visitable interface of the Visitor pattern.
 * It represents the list of god that can be used during the game.
 */
public class VisitableListOfGods implements Visitable  {
    private static final long serialVersionUID = 2052847251326893541L;
    ArrayList<GodName> godNames ;
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public ArrayList<GodName> getGodNames() {
        return godNames;
    }

    public void setGodNames(ArrayList<GodName> godNames) {
        this.godNames = godNames;
    }
}
