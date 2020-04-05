package Model.Turns;

import Model.Enumerations.Direction;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Player;
import Model.Turn;

/**
 * This class implements a Demeter or Hephaestus turn, as they can build twice:
 * the only difference between them is that the first one must build in two different slots,
 * and the second one must do that in the same.
 */
// PER IL MOMENTO E' UN COMMENTO
public class BuildTwiceTurn extends Turn {
    private Direction firstBuildDirection;
    private final static int MAX_BUILDING = 2;


    public BuildTwiceTurn(Player player) {
        super(player);
    }

    /**
     * This method allows the player to build twice if he wants
     * @param direction the direction player wants to build to
     * @throws WrongBuildOrMoveException if the direction of the second build isn't allowed by the rules
     */
  /*
    @Override
    public void executeBuild(Direction direction) throws Exception {
        if (numberOfMovements < MIN_MOVEMENTS || numberOfBuildings == MAX_BUILDING) {
            throw new InvalidDirectionException();
        }
        else if (numberOfBuildings == 0) {
            player.getWorker(indexOfWorker).build(direction);
            numberOfBuildings++;
            firstBuildDirection = direction;
        }
        else if (numberOfBuildings == 1) { //va aggiunta la condizione player.getState() == DEMETRA
            if (firstBuildDirection == direction) {
                throw new WrongBuildOrMoveException();
            }
            player.getWorker(indexOfWorker).build(direction);
            numberOfBuildings++;
        }
        else if (numberOfBuildings == 1 ) { //va aggiunta la condizione player.getState() == EFESTO
            if (firstBuildDirection != direction) {     // NB la condizione che non sia una cupola va gestita
                throw new WrongBuildOrMoveException();      // dal pattern state nel player!!
            }
            player.getWorker(indexOfWorker).build(direction);
            numberOfBuildings++;
        }
    }
    
            */
}
