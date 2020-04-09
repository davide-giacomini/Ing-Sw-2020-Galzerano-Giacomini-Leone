package Model.Gods;

import Model.Board;
import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Slot;
import Model.Worker;

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
        canBuildDome = false;
        canUseBothWorkers = false;
    }

    @Override
    public boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();

        if (numberOfMovements == 1 && player.getTurn().getNumberOfBuildings() == 1) {
            throw new WrongBuildOrMoveException();
        }

        if (numberOfMovements == 0) {
            firstDirection = direction;
            return worker.move(direction);
        }
        else if (numberOfMovements == 1) {
            if (checkOppositeDirection(firstDirection, direction)) {
                throw new WrongBuildOrMoveException();
            }
            return worker.move(direction);
        }
        else throw new WrongBuildOrMoveException();
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        if (player.getTurn().getNumberOfMovements() == 0) throw new WrongBuildOrMoveException();

        worker.build(direction);
    }

    @Override
    public void resetParameters() {
        firstDirection = null;
    }

    @Override
    protected boolean checkIfCanMove(Worker worker) throws InvalidDirectionException {
        if (player.getTurn().getNumberOfMovements() == 0)
            return checkIfCanMoveInNormalConditions(worker);
        else {
            for (Direction direction : Direction.values()) {
                try {
                    if (checkOppositeDirection(firstDirection, direction))
                        continue;
                    worker.checkDirection(direction);
                    Board.getBoard();
                    Slot destinationSlot = Board.getNearbySlot(direction, worker.getSlot());
                    if (!destinationSlot.isOccupied()) {
                        if (!player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal() + 1)
                            return true;
                        else if (player.cannotMoveUp() && destinationSlot.getLevel().ordinal() <= worker.getSlot().getLevel().ordinal())
                            return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //continue
                }
            }
            return false;
        }
    }

    @Override
    protected boolean checkIfCanBuild(Worker worker) throws InvalidDirectionException {
        return checkIfCanBuildInNormalConditions(worker);
    }

    @Override
    public boolean checkIfCanGoOn(Worker worker) throws InvalidDirectionException {
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

    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings >= MIN_BUILDINGS && numberOfMovements >= MIN_MOVEMENTS
                || numberOfMovements >= MIN_MOVEMENTS && player.isWinning();
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
