package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;

public class VisitableListOfGods implements Visitable  {
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
