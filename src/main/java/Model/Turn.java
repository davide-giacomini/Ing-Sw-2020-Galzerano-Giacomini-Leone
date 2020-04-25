package Model;

import Enumerations.Direction;
import Enumerations.Gender;
import Model.Exceptions.*;
import Model.Gods.God;

/**
 * This class implements all the element of a turn game.
 * His main method are executeMove and executeBuild which update the model the correspondent action.
 */
public class Turn {
    private final int MIN_MOVEMENTS;
    private final int MIN_BUILDINGS;
    private final int MAX_MOVEMENTS;
    private final int MAX_BUILDINGS;
    private int numberOfMovements;
    private int numberOfBuildings;
    private Player player;
    private Gender workerGender;
    private boolean wantsToBuildDome;
    private boolean canUseBothWorkers;
    private boolean alreadySetWorker;
    private boolean canAlwaysBuildDome;
    
    public Turn(Player player) throws InvalidDirectionException, GodNotSetException {
        this.numberOfMovements = 0;
        this.numberOfBuildings = 0;
        this.player = player;
        player.setTurn(this);
        if (player.getGod() == null)
            throw new GodNotSetException();
        player.getGod().resetParameters();
        MIN_MOVEMENTS = player.getGod().getMIN_MOVEMENTS();
        MIN_BUILDINGS = player.getGod().getMIN_BUILDINGS();
        MAX_MOVEMENTS = player.getGod().getMAX_MOVEMENTS();
        MAX_BUILDINGS = player.getGod().getMAX_BUILDINGS();
        this.wantsToBuildDome = false;
        this.canUseBothWorkers = player.getGod().canUseBothWorkers();
        this.canAlwaysBuildDome = player.getGod().canAlwaysBuildDome();
        this.alreadySetWorker = false;
        deleteWorkersIfParalyzed();
    }
    
    /**
     * This function has to be used for now because there isn't the god that let the player change his
     * worker DURING the turn. Hence, it shouldn't be used to set canUseBothWorkers run-time.
     *
     * @param canUseBothWorkers it's true if the player can choose both workers, false otherwise.
     */
    @Deprecated
    public void setCanUseBothWorkers(boolean canUseBothWorkers){
        this.canUseBothWorkers = canUseBothWorkers;
    }
    
    public Gender getWorkerGender () {
        return workerGender;
    }
    
    public int getNumberOfMovements() {
        return numberOfMovements;
    }
    
    public int getNumberOfBuildings() {
        return numberOfBuildings;
    }

    /**
     * This method set the worker that will be used during the turn.
     * @param workerGender the gender of the chosen worker
     * @throws InvalidMoveException if the player has already chosen its worker and he cannot change
     * it during the turn.
     */
    public void setWorkerGender(Gender workerGender) throws InvalidMoveException {
        if (!alreadySetWorker)
            this.alreadySetWorker = true;
        else if (!canUseBothWorkers)
            throw new InvalidMoveException("You cannot choose an other worker in the middle of the turn");
        this.workerGender = workerGender;
    }

    public boolean wantsToBuildDome() {
        return wantsToBuildDome;
    }

    /**
     * This method set if the player wants to build a dome instead of the rules' level.
     * @param wantsToBuildDome true if he wants to build a dome, false otherwise
     * @throws InvalidBuildException if the player wants to build a dome but he isn't allowed to.
     */
    public void setWantsToBuildDome(boolean wantsToBuildDome) throws InvalidBuildException {
        if (!canAlwaysBuildDome && wantsToBuildDome)
            throw new InvalidBuildException("You cannot build multiple times");
        this.wantsToBuildDome = wantsToBuildDome;
    }

    /**
     * This method implements a {@link Player}'s move
     * @param direction where the player's {@link Worker} is going to move
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidDirectionException if there are some troubles of I/O.
     * @throws InvalidMoveException if the move is invalid.
     */
    public void executeMove(Direction direction)
            throws IndexOutOfBoundsException, InvalidDirectionException, InvalidMoveException {
        
        if (numberOfMovements == MAX_MOVEMENTS) throw new InvalidMoveException("Max number of movements reached");
        
        // player.move returns a boolean, but the method can throw all the exceptions above.
        // Hence, numberOfMovements has to be incremented only after the method.
        boolean thirdLevelReached = player.move(direction, player.getWorker(workerGender));
        numberOfMovements++;
        player.setWinning(thirdLevelReached);
    }
    
    /**
     * This method builds a construction on the {@link Slot} adjacent to the {@link Worker}, in the direction chosen.
     * @param direction specifies the slot where to build
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are some troubles of I/O.
     * @throws InvalidBuildException if building is not permitted.
     */
    public void executeBuild(Direction direction)
            throws IndexOutOfBoundsException, InvalidDirectionException, InvalidBuildException {
        if (numberOfBuildings == MAX_BUILDINGS) throw new InvalidBuildException("Max number of buildings reached");
        
        player.build(direction, player.getWorker(workerGender));
        numberOfBuildings++;
    }
    
    /**
     * This method check if both the player's workers are paralyzed in every conditions.
     * In that case, the player loses the game.
     */
    private void deleteWorkersIfParalyzed() throws InvalidDirectionException {
        Worker maleWorker = player.getWorker(Gender.MALE);
        Worker femaleWorker = player.getWorker(Gender.FEMALE);
        God playerGod = player.getGod();
        
        if (femaleWorker!=null && maleWorker!=null) {
            if (femaleWorker.getSlot() != null && maleWorker.getSlot() != null && !playerGod.checkIfCanGoOn(femaleWorker) && !playerGod.checkIfCanGoOn(maleWorker)
                    || femaleWorker.getSlot() == null && maleWorker.getSlot()!=null && !playerGod.checkIfCanGoOn(maleWorker)
                    || maleWorker.getSlot() == null && femaleWorker.getSlot()!=null && !playerGod.checkIfCanGoOn(femaleWorker)) {
                player.setLoosing(true);    // it also deletes the workers.
            }
        }
        else if (femaleWorker!=null) {
            if (femaleWorker.getSlot() != null && !playerGod.checkIfCanGoOn(femaleWorker))
                player.setLoosing(true);
        }
        else {
            if (maleWorker.getSlot()!=null && !playerGod.checkIfCanGoOn(maleWorker))
                player.setLoosing(true);
        }
    }

}
