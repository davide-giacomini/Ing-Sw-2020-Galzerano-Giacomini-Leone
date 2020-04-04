package Model.Turns;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Player;

/**
 *  PER IL MOMENTO SETTATA AI COMMENTI
 */
public class BuildBeforeMoveTurn extends Turn{
    private final static int MAX_BUILDING = 2;

    public BuildBeforeMoveTurn(Player player) {
        super(player);
    }

    /**
     * This method allows the player to build before move: in this case he wont move up on this turn.
     * @param direction the direction player wants to build to
     */
  /*
    @Override
    public void executeBuild(Direction direction) throws Exception {
        if (numberOfMovements < MIN_MOVEMENTS && numberOfBuildings == 0) {
            player.getWorker(indexOfWorker).build(direction);
            player.setCantMoveUp(true);
            numberOfBuildings++;
        }
        else if (numberOfMovements < MIN_MOVEMENTS && numberOfBuildings == 1) {
            throw new InvalidDirectionException();
        }
        else if (numberOfMovements == MAX_MOVEMENTS && numberOfBuildings < MAX_BUILDING) {
            player.getWorker(indexOfWorker).build(direction);
            numberOfBuildings++;
        }
        else if (numberOfBuildings == MAX_BUILDING) {
            throw new InvalidDirectionException();
        }
    }
           */
}
