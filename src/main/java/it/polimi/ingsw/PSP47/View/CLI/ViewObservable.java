package it.polimi.ingsw.PSP47.View.CLI;

import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.util.ArrayList;

/**
 * Observable interface of pattern observer.
 */
public abstract class ViewObservable {
    private final ArrayList<ViewListener> viewListeners = new ArrayList<>();

    public void notifyViewListener(Visitable visitableObject){
        for (ViewListener slotListener : viewListeners) {
            slotListener.update(visitableObject);
        }
    }

    public void addViewListener(ViewListener viewListener){
        viewListeners.add(viewListener);
    }
}
