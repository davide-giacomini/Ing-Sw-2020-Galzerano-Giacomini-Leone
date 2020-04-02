package Model.Turns;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidActionException;
import Model.Player;

/**
 *
 */
public class BuildBeforeMoveTurn extends Turn{
    private final static int MAX_BUILDING = 2;

    public BuildBeforeMoveTurn(Player player, int indexOfWorker) {
        super(player, indexOfWorker);
    }

    /**
     * This method allows the player to build before move: in this case he wont move up on this turn.
     * @param direction the direction player wants to build to
     */
    @Override
    public void executeBuild(Direction direction) throws Exception {
        if (numberOfMovement < MIN_MOVEMENT && numberOfBuilding == 0) {
            player.getWorker(indexOfWorker).build(direction);
            player.setCantMoveUp(true);
            numberOfBuilding++;
        }
        else if (numberOfMovement < MIN_MOVEMENT && numberOfBuilding == 1) {
            throw new InvalidActionException();
        }
        else if (numberOfMovement == MAX_MOVEMENT && numberOfBuilding < MAX_BUILDING) {
            player.getWorker(indexOfWorker).build(direction);
            numberOfBuilding++;
        }
        else if (numberOfBuilding == MAX_BUILDING) {
            throw new InvalidActionException();
        }
    }
}
