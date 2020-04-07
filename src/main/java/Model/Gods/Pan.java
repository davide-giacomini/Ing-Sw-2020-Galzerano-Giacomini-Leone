package Model.Gods;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Worker;

public class Pan extends God{
    public Pan(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canBuildDome = false;
        canUseBothWorkers = false;
    }

    @Override
    public boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException {
        int previousLevel = worker.getSlot().getLevel().ordinal();
        boolean winCondition = worker.move(direction);
        int actualLevel = worker.getSlot().getLevel().ordinal();
        return winCondition || (actualLevel - previousLevel < -1);
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        if (player.getTurn().getNumberOfMovements() == 0) throw new WrongBuildOrMoveException();

        worker.build(direction);
    }

    @Override
    public void resetParameters() {

    }
}
