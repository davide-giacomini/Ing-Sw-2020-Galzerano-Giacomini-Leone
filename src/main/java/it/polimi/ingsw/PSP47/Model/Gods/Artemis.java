package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * {@link Player}'s {@link Worker} may move one additional time, but not back to the space it started on.
 */
public class Artemis extends God {

    private Direction firstDirection;

    public Artemis(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 2;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
    }

    /**
     * This method checks the numberOfMovements of the actual turn: if it's zero, it just calls the standard move of a
     * worker and save the chosen direction; if it's one, it control if the chosen direction is opposed to the first,
     * then it calls the standard move of a worker.
     * @param direction where the worker wants to move to.
     * @param worker the {@link Player}'s {@link Worker} to be moved.
     * @return true if the winning condition has been verified, false otherwise
     * @throws IndexOutOfBoundsException if the worker try to move in a direction that is out out the board
     * @throws InvalidMoveException if the move is invalid.
     */
    @Override
    public boolean move(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidDirectionException, InvalidMoveException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();

        if (numberOfMovements == 1 && player.getTurn().getNumberOfBuildings() == 1) {
            throw new InvalidMoveException("Order of movements incorrect");
        }

        if (numberOfMovements == 0) {
            firstDirection = direction;
            try {
                return worker.move(direction);
            } catch (SlotOccupiedException e) {
                throw new InvalidMoveException("Slot occupied");
            }
        }
        else {
            if (checkOppositeDirection(firstDirection, direction)) {
                throw new InvalidMoveException("You cannot move back to your first slot");
            }
            try {
                return worker.move(direction);
            } catch (SlotOccupiedException e) {
                throw new InvalidMoveException("Slot occupied");
            }
        }
    }

    /**
     * It resets the firstDirection.
     */
    @Override
    public void resetParameters() {
        firstDirection = null;
    }

    /**
     * If the player has already moved, this method checks if the worker is paralyzed controlling all the directions
     * except the opponent at the first he chose, as he cannot move here.
     * @param worker {@link Player}'s {@link Worker} selected to be checked.
     * @return true if the worker can move, false otherwise
     */
    @Override
    public boolean checkIfCanMove(Worker worker) {
        if (player.getTurn().getNumberOfMovements() == 0)
            return checkIfCanMoveInNormalConditions(worker);
        else {
            for (Direction direction : Direction.values()) {
                try {
                    if (checkOppositeDirection(firstDirection, direction))
                        continue;
                    worker.checkDirection(direction);
                    Slot destinationSlot = player.getTurn().getBoard().getNearbySlot(direction, worker.getSlot());
                    if (!destinationSlot.isOccupied()) {
                        if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal() + 1)
                            return true;
                        else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal())
                            return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //continue
                } catch (InvalidDirectionException e) {
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * This method checks if the worker is paralyzed or not.
     * @param worker the worker chosen to be checked.
     * @return true if the worker can go on, false otherwise.
     */
    @Override
    public boolean checkIfCanGoOn(Worker worker) {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfMovements==0)
            return checkIfCanMove(worker);
        else if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanMove(worker) || checkIfCanBuild(worker);
        else if (numberOfMovements==2 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        return false;
    }
    
    /**
     * This method check if two directions are opposite: this must be checked as Artemis
     * cannot return in her first position moving the second time
     * @param oldDirection the first direction
     * @param newDirection the second direction
     * @return true if the two directions are opposed
     * @throws InvalidDirectionException if the input isn't correct
     */
    private boolean checkOppositeDirection(Direction oldDirection, Direction newDirection) throws InvalidDirectionException {
        switch (oldDirection) {
            case LEFT:
                return (newDirection == Direction.RIGHT);
            case LEFTUP:
                return (newDirection == Direction.RIGHTDOWN);
            case UP:
                return (newDirection == Direction.DOWN);
            case RIGHTUP:
                return (newDirection == Direction.LEFTDOWN);
            case RIGHT:
                return (newDirection == Direction.LEFT);
            case RIGHTDOWN:
                return (newDirection == Direction.LEFTUP);
            case DOWN:
                return (newDirection == Direction.UP);
            case LEFTDOWN:
                return (newDirection == Direction.RIGHTUP);
            default:
                throw new InvalidDirectionException();
        }
    }
}
