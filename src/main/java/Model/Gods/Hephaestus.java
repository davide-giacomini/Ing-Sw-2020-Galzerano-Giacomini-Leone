package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Slot;
import Model.Worker;


/**
 * {@link Player}'s {@link Worker} may build one additional time, but only on the same space.
 */
public class Hephaestus extends God {

    // the slot where Hephaestus builds the first time
    // and where it can build for a second time
    private Slot doubleBuildSlot;

    public Hephaestus(Player player, String name){
        super (player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canBuildDome = false;
        canUseBothWorkers = false;
}

    @Override
    public boolean move(Direction direction, Worker worker)  throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException {

        return worker.move(direction);
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {

        if (player.getTurn().getNumberOfMovements() == 0) throw new WrongBuildOrMoveException();

        if (player.getTurn().getNumberOfBuildings() == 0)
            doubleBuildSlot = Board.getNearbySlot(direction, worker.getSlot());
        else if (!Board.getNearbySlot(direction, worker.getSlot()).equals(doubleBuildSlot))
            throw new WrongBuildOrMoveException();

        worker.build(direction);

    }

    @Override
    public void resetParameters() {
        doubleBuildSlot = null;
    }

}
