package it.polimi.ingsw.PSP47.Visitor;

public class VisitableInitialPositions implements Visitable {
    private static final long serialVersionUID = 1376954572600301507L;
    private int[] rowsAndColumns;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int[] getRowsAndColumns() {
        return rowsAndColumns;
    }

    public void setRowsAndColumns(int[] rowsAndColumns) {
        this.rowsAndColumns = rowsAndColumns;
    }
}
