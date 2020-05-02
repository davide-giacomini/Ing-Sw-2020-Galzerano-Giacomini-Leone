package it.polimi.ingsw.PSP47.Visitor;

public interface Visitor {

    void visit(VisitableActionAndDirection actionAndDirection);

    void visit(VisitableGod god);

    void visit(VisitableInformation information);

    void visit(VisitableListOfGods listOfGods);

    void visit(VisitableRowsAndColumns rowsAndColumns);

    void visit(VisitableString string);

    void visit(VisitableInt number);

    void visit(VisitableInitialPositions visitableInitialPositions);
}
