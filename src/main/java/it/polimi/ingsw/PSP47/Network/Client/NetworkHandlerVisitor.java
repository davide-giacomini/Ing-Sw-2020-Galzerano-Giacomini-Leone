package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Message.*;
import it.polimi.ingsw.PSP47.Visitor.*;

/**
 *  This class implements the Visitor interface referred to the Visitor pattern.
 *  In this way, every time the NetworkHandler calls an update, he knows exactly
 *  what to do thanks to the Visitable class which is passed in.
 */
public class NetworkHandlerVisitor implements Visitor {
    private NetworkHandler networkHandler;

    public NetworkHandlerVisitor(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    /**
     * This method sends server side the action chosen by the player.
     * @param actionAndDirection a Visitable class that represents the action chosen.
     */
    @Override
    public void visit(VisitableActionAndDirection actionAndDirection) {

        ChooseAction newMessage = new ChooseAction(actionAndDirection);
        networkHandler.send(newMessage);
    }

    /**
     * This method sends server side the god chosen by the player.
     * @param god a Visitable class that represents the god chosen by the player.
     */
    @Override
    public void visit(VisitableGod god) {
        GodChosen newMessage = new GodChosen(god);

        networkHandler.send(newMessage);

    }

    /**
     * This method sends server side the username and color chosen by a player.
     * @param information a Visitable class that represents username and color that have been chosen.
     */
    @Override
    public void visit(VisitableInformation information) {
        FirstConnection newMessage = new FirstConnection(information);

        networkHandler.send(newMessage);
    }

    /**
     * This method sends server side the list of gods chosen by the challenger.
     * @param listOfGods a Visitable class that represents the list of god chosen.
     */
    @Override
    public void visit(VisitableListOfGods listOfGods) {
        ListOfGods newMessage = new ListOfGods(listOfGods);

        networkHandler.send(newMessage);

    }

    /**
     * This method sends server side the position of the worker that the player chose to use during the turn.
     * @param rowsAndColumns a Visitable class that represents the position of the worker.
     */
    @Override
    public void visit(VisitableRowsAndColumns rowsAndColumns) {
        ChooseWorkerByPosition newMessage = new ChooseWorkerByPosition(rowsAndColumns);

        networkHandler.send(newMessage);

    }

    /**
     * This method sends server side the number of players of the current game.
     * @param number a Visitable class that represents the number of players.
     */
    @Override
    public void visit(VisitableInt number) {
        networkHandler.send(new RequestPlayersNumber(number));
    }

    /**
     * This method sends server side the position of both workers during the setup of the game.
     * @param visitableInitialPositions a Visitable class that represents the positions of the workers.
     */
    @Override
    public void visit(VisitableInitialPositions visitableInitialPositions) {
        networkHandler.send(new AskWorkersPosition(visitableInitialPositions));
    }


}
