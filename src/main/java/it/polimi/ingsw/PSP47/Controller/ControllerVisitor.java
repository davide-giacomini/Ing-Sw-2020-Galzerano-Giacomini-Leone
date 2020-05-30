package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Controller.GameController;
import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Visitor.*;

import java.util.ArrayList;

/**
 *  This class implements the Visitor interface referred to the Visitor pattern.
 *  In this way, every time the GameController calls an update, he knows exactly
 *  what to do thanks to the Visitable class which is passed in.
 */
public class ControllerVisitor implements Visitor {
    private GameController controller;

    public ControllerVisitor(GameController controller) {
        this.controller = controller;
    }

    /**
     * This method executes an action in a turn.
     * @param actionAndDirection a Visitable class that represents the action chosen by the player
     */
    @Override
    public void visit(VisitableActionAndDirection actionAndDirection) {
        Action action = actionAndDirection.getAction();
        Direction direction = actionAndDirection.getDirection();
        controller.getTurn().executeAction(action, direction);
    }

    /**
     * This method sets the god chosen by the player in the model.
     * @param god a Visitable class that represents the chosen god.
     */
    @Override
    public void visit(VisitableGod god) {
        GodName godName = god.getGodName();
        controller.setGod(godName);
    }

    @Override
    public void visit(VisitableInformation information) {
        // do nothing
    }

    /**
     * This method sets the list of gods which can be used during the game.
     * @param listOfGods a Visitable class that represents the list of chosen gods.
     */
    @Override
    public void visit(VisitableListOfGods listOfGods) {
        ArrayList<GodName> list = listOfGods.getGodNames();
        String firstPlayer = listOfGods.getChosenPlayer();
        controller.setGods(list, firstPlayer);
    }

    /**
     * This method sets the worker that has been chosen in a turn through its position.
     * @param rowsAndColumns a Visitable class that represents the position of the worker.
     */
    @Override
    public void visit(VisitableRowsAndColumns rowsAndColumns) {
        int[] coordinates = rowsAndColumns.getRowsAndColumns();
        controller.getTurn().setWorkerGender(coordinates);
    }

    @Override
    public void visit(VisitableInt number) {
        //do nothing
    }

    /**
     * This method sets the initial position of the workers during the setup of the game.
     * @param visitableInitialPositions a Visitable class that represents the positions of the two workers.
     */
    @Override
    public void visit(VisitableInitialPositions visitableInitialPositions) {
        int[] coordinates = visitableInitialPositions.getRowsAndColumns();
        controller.setWorkers(coordinates);
    }
}

