package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Gods.God;

/**
 * This class implements all the element of a turn game.
 * His main method are executeMove and executeBuild which update the model the correspondent action.
 */
public class Turn {

    private int numberOfMovements;
    private int numberOfBuildings;
    private final Player player;
    private Gender workerGender;
    private Board board;
    private boolean wantsToBuildDome;
    private final boolean canAlwaysBuildDome;
    
    public Turn(Player player, Board board) {
        this.numberOfMovements = 0;
        this.numberOfBuildings = 0;
        this.player = player;
        this.board = board;
        player.setTurn(this);
        player.getGod().resetParameters();
        this.wantsToBuildDome = false;
        this.canAlwaysBuildDome = player.getGod().canAlwaysBuildDome();
        checkIfWorkersAreParalyzed();
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
     */
    public void setWorkerGender(Gender workerGender) {
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
            throw new InvalidBuildException("You cannot build a dome");
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

        player.build(direction, player.getWorker(workerGender));
        numberOfBuildings++;
    }

    /**
     * This method check if both the player's workers are paralyzed in every conditions.
     * In that case, the player loses the game.
     */
    private void checkIfWorkersAreParalyzed() {
        Worker maleWorker = player.getWorker(Gender.MALE);
        Worker femaleWorker = player.getWorker(Gender.FEMALE);
        God playerGod = player.getGod();

        if (!playerGod.checkIfCanGoOn(maleWorker) && !playerGod.checkIfCanGoOn(femaleWorker))
            player.setLoosing(true);
    }

    public Board getBoard() {
        return board;
    }
}
