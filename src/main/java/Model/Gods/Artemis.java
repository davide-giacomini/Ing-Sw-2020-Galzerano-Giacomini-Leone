package Model.Gods;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Worker;

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
