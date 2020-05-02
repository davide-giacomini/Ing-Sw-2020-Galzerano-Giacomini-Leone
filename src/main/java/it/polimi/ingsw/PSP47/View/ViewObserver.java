package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Model.Slot;

/**
 * Observer interface of the Pattern observer.
 */
public interface ViewObserver {

    void update(int[] rowsAndColumns);
}
