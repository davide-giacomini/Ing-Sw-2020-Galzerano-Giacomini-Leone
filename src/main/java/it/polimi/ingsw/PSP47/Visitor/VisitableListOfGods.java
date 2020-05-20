package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;

/**
 * This class implements the Visitable interface of the Visitor pattern.
 * It represents the list of god that can be used during the game and the
 * name of the first player chosen by the Challenger. In fact, all of this information
 * is decided by the Challenger during the setup of the game.
 */
public class VisitableListOfGods implements Visitable  {
    private static final long serialVersionUID = 2052847251326893541L;
    private ArrayList<GodName> godNames ;
    private String chosenPlayer;

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

    public String getChosenPlayer() {
        return chosenPlayer;
    }

    public void setChosenPlayer(String chosenPlayer) {
        this.chosenPlayer = chosenPlayer;
    }
}
