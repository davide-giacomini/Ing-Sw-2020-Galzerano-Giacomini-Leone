package Model.Turns;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidActionException;
import Model.Player;

/**
 * This class implements a default turn, which is shared by Gods as Apollo, Athena,
 * Atlas, Minotaur, Pan and Prometheus, as their effects don't change which are the actions
 * that can be done in a turn as others do.
 *
 */
public class Turn {
    // DA MODIFICARE // da mettere private quando ho finito
    final int MIN_MOVEMENTS;
    final int MIN_BUILDINGS;
    final int MAX_MOVEMENTS;
    final int MAX_BUILDINGS;
    int numberOfMovements;
    int numberOfBuildings;
    protected Player player;
    int indexOfWorker;

    
    public Turn(Player player) {
        this.numberOfMovements = 0;
        this.numberOfBuildings = 0;
        this.player = player;
        MIN_MOVEMENTS = player.getGod().getMIN_MOVEMENTS();
        MIN_BUILDINGS = player.getGod().getMIN_BUILDINGS();
        MAX_MOVEMENTS = player.getGod().getMAX_MOVEMENTS();
        MAX_BUILDINGS = player.getGod().getMAX_BUILDINGS();
    }

    public void setNumberOfMovements(int newNumber) {
        this.numberOfMovements = newNumber;
    }

    public void setNumberOfBuildings(int newNumber) {
        this.numberOfBuildings = newNumber;
    }
    
    public void setIndexOfWorker (int indexOfWorker){
        this.indexOfWorker = indexOfWorker;
    }

    /**
     * This method implements a player's move
     * @throws InvalidActionException if he cannot do this action
     */
    public void executeMove(Direction direction) throws Exception {
        if (numberOfMovements == MAX_MOVEMENTS) {
            throw new InvalidActionException();
        }
        player.move(direction, player.getWorker(indexOfWorker));
        numberOfMovements++;
    }

    /**
     * This method implements a player's build
     * @throws InvalidActionException if he cannot do this action (for example he still has not moved)
     */
    public void executeBuild(Direction direction) throws Exception {
        if (numberOfMovements < MIN_MOVEMENTS || numberOfBuildings == MAX_BUILDINGS) {
            throw new InvalidActionException();
        }
        player.build(direction, player.getWorker(indexOfWorker));
        numberOfBuildings++;
    }

    /**
     * This method control if the player can end his turn: he must move and build to do that,
     * or he has to do the winning move (in this case he doesn't have to build).
     * Before ending turn the CantMoveUp boolean become false as God's effect ends with the end of the turn.
     */
    public boolean validateEndTurn() {
        player.setCantMoveUp(false);
        return numberOfBuildings >= MIN_BUILDINGS && (numberOfMovements >= MIN_MOVEMENTS || player.isWinning());
    }

}
