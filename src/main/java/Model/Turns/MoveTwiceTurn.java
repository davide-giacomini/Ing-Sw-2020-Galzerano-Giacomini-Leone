package Model.Turns;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidActionException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;

/**
 * This class implements an Artemis' turn, who is enable to move twice.
 */
public class MoveTwiceTurn extends Turn{
    private Direction firstMovementDirection;
    private final static int MAX_MOVEMENT = 2;

    public MoveTwiceTurn(Player player, int indexOfWorker) {
        super(player, indexOfWorker);
    }

    /**
     * This method check if two direction are opposite: this must be checked as Artemis
     * cannot return in her first position moving the second time
     * @param oldDirection the first direction
     * @param newDirection the second direction
     * @return true if the two directions are opposed
     * @throws Exception in the default case
     */
    private boolean checkOppositeDirection(Direction oldDirection, Direction newDirection) throws Exception {
        switch (oldDirection) {
            case LEFT: return (newDirection == Direction.RIGHT);
            case LEFTUP: return (newDirection == Direction.RIGHTDOWN);
            case UP: return (newDirection == Direction.DOWN);
            case RIGHTUP: return (newDirection == Direction.LEFTDOWN);
            case RIGHT: return (newDirection == Direction.LEFT);
            case RIGHTDOWN: return (newDirection == Direction.LEFTUP);
            case DOWN: return (newDirection == Direction.UP);
            case LEFTDOWN: return (newDirection == Direction.RIGHTUP);
            default: throw new Exception();
        }
    }

    /**
     * This method allows the player to move twice if he wants
     * @param direction the direction player wants to move to
     * @throws WrongBuildOrMoveException if the direction of the second move isn't allowed by the rules
     */
    @Override
    public void executeMove(Direction direction) throws Exception {
        if (numberOfMovement == MAX_MOVEMENT) {
            throw new InvalidActionException();
        }
        else if (numberOfMovement == 0) {
            player.getWorker(indexOfWorker).move(direction);
            numberOfMovement++;
            firstMovementDirection = direction;
        }
        else if (numberOfMovement == 1) {
            if(checkOppositeDirection(firstMovementDirection, direction)) {
                throw new WrongBuildOrMoveException();
            }
            player.getWorker(indexOfWorker).move(direction);
            numberOfMovement++;
        }
    }

}
