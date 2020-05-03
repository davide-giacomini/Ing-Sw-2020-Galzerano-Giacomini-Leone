package it.polimi.ingsw.PSP47.Visitor;

import it.polimi.ingsw.PSP47.Enumerations.GodName;

public class VisitableGod implements Visitable {
    private static final long serialVersionUID = 8423564983096890839L;
    private GodName godName;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public GodName getGodName() {
        return godName;
    }

    public void setGodName(GodName godName) {
        this.godName = godName;
    }
}
