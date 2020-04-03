package Model.Turns;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Exceptions.*;
import Model.Player;
import Model.Slot;
import Model.Worker;

/**
 * This class implements a default turn, which is shared by Gods as Apollo, Athena,
 * Atlas, Minotaur, Pan and Prometheus, as their effects don't change which are the actions
 * that can be done in a turn as others do.
 *
 */
public class Turn {
    private final int MIN_MOVEMENTS;
    private final int MIN_BUILDINGS;
    private final int MAX_MOVEMENTS;
    private final int MAX_BUILDINGS;
    private int numberOfMovements;
    private int numberOfBuildings;
    private Player player;
    private int indexOfWorker;
    
    
    public Turn(Player player) {
        this.numberOfMovements = 0;
        this.numberOfBuildings = 0;
        this.player = player;
        player.setTurn(this);
        player.getGod().resetParameters();
        MIN_MOVEMENTS = player.getGod().getMIN_MOVEMENTS();
        MIN_BUILDINGS = player.getGod().getMIN_BUILDINGS();
        MAX_MOVEMENTS = player.getGod().getMAX_MOVEMENTS();
        MAX_BUILDINGS = player.getGod().getMAX_BUILDINGS();
    }
    
    public int getNumberOfMovements() {
        return numberOfMovements;
    }
    
    public void setNumberOfMovements(int newNumber) {
        this.numberOfMovements = newNumber;
    }
    
    public int getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public void setNumberOfBuildings(int newNumber) {
        this.numberOfBuildings = newNumber;
    }
    
    public void setIndexOfWorker (int indexOfWorker){
        this.indexOfWorker = indexOfWorker;
    }

    /**
     * This method implements a {@link Player}'s move
     * @param direction where the player's {@link Worker} is going to move
     * @return true if the worker moved voluntarily up on the third level, false otherwise
     * @throws IndexOutOfBoundsException if the destination {@link Slot} doesn't exist in the {@link Board}.
     * @throws NotReachableLevelException if the level of the destination has at least 2 blocks more than the current.
     * @throws SlotOccupiedException if the destination {@link Slot} is occupied
     * @throws InvalidDirectionException if the switch-case of getNearbySlot of {@link Board} entered the default case. It
     * shouldn't happen.
     * @throws NoAvailableMovementsException if the worker has been already moved enough times.
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    public boolean executeMove(Direction direction)
            throws IndexOutOfBoundsException, NotReachableLevelException, SlotOccupiedException, InvalidDirectionException, NoAvailableMovementsException, WrongBuildOrMoveException {
        
        if (numberOfMovements == MAX_MOVEMENTS) throw new NoAvailableMovementsException();
        
        // player.move returns a boolean, but the method can throw all the exceptions above.
        // Hence, numberOfMovements has to be incremented only after the method.
        boolean thirdLevelReached = player.move(direction, player.getWorker(indexOfWorker));
        numberOfMovements++;
        
        return thirdLevelReached;
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker}, in the direction chosen.
     * @param direction specifies the slot where to build
     * @throws IndexOutOfBoundsException if the {@link Slot} where to build is outside the {@link Board}
     * @throws SlotOccupiedException if the slot where to build is occupied by a dome or another worker
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     * @throws NoAvailableBuildingsException if the worker has already built enough times.
     * @throws WrongBuildOrMoveException if the order of the moves is not ok.
     */
    public void executeBuild(Direction direction)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, NoAvailableBuildingsException, WrongBuildOrMoveException {
        if (numberOfBuildings == MAX_BUILDINGS) throw new NoAvailableBuildingsException();
        
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
