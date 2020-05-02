package it.polimi.ingsw.PSP47.Visitor;

public class VisitableRowsAndColumns implements Visitable  {
    int [] rowsAndColumns;

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
