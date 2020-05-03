package it.polimi.ingsw.PSP47.Visitor;

public class VisitableRowsAndColumns implements Visitable  {
    private static final long serialVersionUID = -4512574648515099233L;
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
