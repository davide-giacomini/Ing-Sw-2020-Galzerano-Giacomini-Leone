package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;

/**
 *  This class implements the Visitor interface referred to the Visitor pattern.
 *  In this way, everytime the GameController calls an update, he knows exactly
 *  what to do thanks to the Visitable class which is passed in.
 */
public class ControllerVisitor implements Visitor {
    private GameController controller;

    public ControllerVisitor(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void visit(VisitableActionAndDirection actionAndDirection) {
        Action action = actionAndDirection.getAction();
        Direction direction = actionAndDirection.getDirection();
        controller.getTurn().executeAction(action, direction);
    }

    @Override
    public void visit(VisitableGod god) {
        GodName godName = god.getGodName();
        controller.setGod(godName);
    }

    @Override
    public void visit(VisitableInformation information) {
        // do nothing
    }

    @Override
    public void visit(VisitableListOfGods listOfGods) {
        ArrayList<GodName> list = listOfGods.getGodNames();
        controller.setGods(list);
    }

    @Override
    public void visit(VisitableRowsAndColumns rowsAndColumns) {
        int[] coordinates = rowsAndColumns.getRowsAndColumns();
        controller.getTurn().setWorkerGender(coordinates);
    }

    @Override
    public void visit(VisitableString string) {
        //do nothing
    }

    @Override
    public void visit(VisitableInitialPositions visitableInitialPositions) {
        int[] coordinates = visitableInitialPositions.getRowsAndColumns();
        controller.setWorkers(coordinates);
    }
}
