package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Message.*;

public class NetworkHandlerVisitor implements Visitor {
    private NetworkHandler networkHandler;

    public NetworkHandlerVisitor(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    @Override
    public void visit(VisitableActionAndDirection actionAndDirection) {

        ChooseAction newMessage = new ChooseAction(actionAndDirection);
        networkHandler.send(newMessage);
    }

    @Override
    public void visit(VisitableGod god) {
        GodChosen newMessage = new GodChosen(god);

        networkHandler.send(newMessage);

    }

    @Override
    public void visit(VisitableInformation information) {
        FirstConnection newMessage = new FirstConnection(information);

        networkHandler.send(newMessage);
    }

    @Override
    public void visit(VisitableListOfGods listOfGods) {

        ListOfGods newMessage = new ListOfGods(listOfGods);

        networkHandler.send(newMessage);

    }

    @Override
    public void visit(VisitableRowsAndColumns rowsAndColumns) {
        ChooseWorkerByPosition newMessage = new ChooseWorkerByPosition(rowsAndColumns);

        networkHandler.send(newMessage);

    }

    @Override
    public void visit(VisitableString string) {

    }

    @Override
    public void visit(VisitableInt number) {
        networkHandler.send(new RequestPlayersNumber(number));
    }

    @Override
    public void visit(VisitableInitialPositions visitableInitialPositions) {
        networkHandler.send(new AskWorkersPosition(visitableInitialPositions));
    }


}
