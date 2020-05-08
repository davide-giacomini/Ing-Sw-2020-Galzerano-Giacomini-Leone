package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidBuildException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidDirectionException;
import it.polimi.ingsw.PSP47.Model.Exceptions.InvalidMoveException;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * With Chronus, {@link Player} can win also if there are 5 COMPLETE towers on the Board.
 */
public class Chronus extends God  {

    public Chronus(Player player, String name) {
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
