package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * {@link Player}'s {@link Worker} may also choose to build under himself and so add another level on his/her
 * current Slot. However,he/she cannot win if the level 3 is reached in this way.
 */
public class Zeus extends God  {

    public Zeus(Player player, String name) {
        super(player, name);
    }

    @Override
    public boolean move(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidMoveException, InvalidDirectionException {
        return false;
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {

    }

    @Override
    public void resetParameters() {

    }

    @Override
    public boolean checkIfCanMove(Worker worker) {
        return false;
    }

    @Override
    public boolean checkIfCanBuild(Worker worker) {
        return false;
    }

    @Override
    public boolean checkIfCanGoOn(Worker worker) {
        return false;
    }

    @Override
    public boolean validateEndTurn() {
        return false;
    }
}
