package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.Color;

public class VisitableInformation implements Visitable  {
    private static final long serialVersionUID = 7691614604691227681L;
    String username;
    Color color;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
