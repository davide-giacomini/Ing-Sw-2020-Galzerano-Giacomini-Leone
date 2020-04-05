package Model.Gods;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Worker;

/**
 * {@link Player}'s {@link Worker} may build a dome.
 */
public class Atlas extends God {

    public Atlas(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canBuildDome = true;
        canUseBothWorkers = false;
    }

    @Override
    public boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException {
        return worker.move(direction);
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        if (player.getTurn().getNumberOfMovements() == 0) {
            throw new WrongBuildOrMoveException();
        }

        if (player.getTurn().WantsToBuildDome() && player.CanBuildDome()) {
            worker.buildDome(direction);
        }
        else if (!player.getTurn().WantsToBuildDome()) {
            worker.build(direction);
        } else if (player.getTurn().WantsToBuildDome() && !player.CanBuildDome()) {
            throw new WrongBuildOrMoveException();
        }
        
    }

    @Override
    public void resetParameters() {
    }
}
