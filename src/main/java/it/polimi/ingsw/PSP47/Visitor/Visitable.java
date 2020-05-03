package it.polimi.ingsw.PSP47.Visitor;

import java.io.Serializable;

public interface Visitable extends Serializable {

    void accept(Visitor visitor) ;
}
