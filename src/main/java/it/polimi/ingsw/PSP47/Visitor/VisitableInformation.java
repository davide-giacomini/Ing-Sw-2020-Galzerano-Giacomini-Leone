package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.Color;

/**
 * This class implements the Visitable interface of the Visitor pattern.
 * It represents the username and the color chosen by a player.
 */
public class VisitableInformation implements Visitable  {
    private static final long serialVersionUID = 7691614604691227681L;
    private String username;
    private Color color;

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
