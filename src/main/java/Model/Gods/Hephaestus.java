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
 * {@link Player}'s {@link Worker} may build one additional block (not dome) on top of your first block.
 */
public class Hephaestus extends God {

    // the slot where Hephaestus builds the first time
    // and where it can build for a second time
    private Slot doubleBuildSlot;

    public Hephaestus(Player player, String name){
        super (player, name);
        MAX_BUILDINGS = 2;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MIN_MOVEMENTS = 1;
        canBuildDome = false;
        canUseBothWorkers = false;
    }

    @Override
    public boolean move(Direction direction, Worker worker)  throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException, WrongBuildOrMoveException {

        return worker.move(direction);
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {

        if (player.getTurn().getNumberOfMovements() == 0) throw new WrongBuildOrMoveException();

        if (player.getTurn().getNumberOfBuildings() == 0)
            doubleBuildSlot = Board.getNearbySlot(direction, worker.getSlot());
        else if (!Board.getNearbySlot(direction, worker.getSlot()).equals(doubleBuildSlot) || (worker.getSlot().getLevel().ordinal()== 3) )
            throw new WrongBuildOrMoveException();

        worker.build(direction);

    }

    @Override
    public void resetParameters() {
        doubleBuildSlot = null;
    }
    
    @Override
    protected boolean checkIfCanMove(Worker worker) throws InvalidDirectionException {
        return checkIfCanMoveInNormalConditions(worker);
    }
    
    @Override
    protected boolean checkIfCanBuild(Worker worker) throws InvalidDirectionException {
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfBuildings==0)
            return checkIfCanBuildInNormalConditions(worker);
        ///THIS PART HERE IS TO CHECK AGAIN AND MAYBE DELETE
        if (numberOfBuildings==1) {
            for (Direction direction: Direction.values()){
                Slot destinationSlot = Board.getBoard().getNearbySlot(direction, worker.getSlot());
                try {

                    worker.checkDirection(direction);
                    // else, check if the worker can build on the destinationSlot
                    if (destinationSlot.equals(doubleBuildSlot) && !destinationSlot.isOccupied())
                        return true;
                }
                catch (IndexOutOfBoundsException e) {
                    // just let the for continue
                }
            }
        }

        return false;
    }
    
    @Override
    public boolean checkIfCanGoOn(Worker worker) throws InvalidDirectionException {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker);
        if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);
        if (numberOfMovements==1 && numberOfBuildings==1)
            return checkIfCanBuild(worker);

        return false;
    }
    
    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfBuildings==1 && numberOfMovements==1 || numberOfBuildings==2 && numberOfMovements==1;
    }

    
}
