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
    static final int MIN_MOVEMENT = 1;
    static final int MIN_BUILDING = 1;
    static final int MAX_MOVEMENT = 1;
    static final int MAX_BUILDING = 1;
    int numberOfMovement;
    int numberOfBuilding;
    protected Player player;
    int indexOfWorker;

    public Turn(Player player, int indexOfWorker) {
        this.numberOfMovement = 0;
        this.numberOfBuilding = 0;
        this.player = player;
        this.indexOfWorker = indexOfWorker;
    }

    public void setNumberOfMovement(int newNumber) {
        this.numberOfMovement = newNumber;
    }

    public void setNumberOfBuilding(int newNumber) {
        this.numberOfBuilding = newNumber;
    }

    /**
     * This method implements a player's move
     * @throws InvalidActionException if he cannot do this action
     */
    public void executeMove(Direction direction) throws Exception {
        if (numberOfMovement == MAX_MOVEMENT) {
            throw new InvalidActionException();
        }
        player.getWorker(indexOfWorker).move(direction);
        numberOfMovement++;
    }

    /**
     * This method implements a player's build
     * @throws InvalidActionException if he cannot do this action (for example he still has not moved)
     */
    public void executeBuild(Direction direction) throws Exception {
        if (numberOfMovement < MIN_MOVEMENT || numberOfBuilding == MAX_BUILDING) {
            throw new InvalidActionException();
        }
        player.getWorker(indexOfWorker).build(direction);
        numberOfBuilding++;
    }

    /**
     * This method control if the player can end his turn: he must move and build to do that,
     * or he has to do the winning move (in this case he doesn't have to build).
     * Before ending turn the CantMoveUp boolean become false as God's effect ends with the end of the turn.
     */
    public boolean validateEndTurn() {
        player.setCantMoveUp(false);
        return numberOfBuilding >= MIN_MOVEMENT && (numberOfMovement >= MIN_BUILDING || player.isWinning());
    }

}
