package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If a {@link Player} has Hera, the opponents' {@link Worker} cannot win by moving into a perimeter slot.
 */
public class Hera extends God  {

    public Hera(Player player, String name) {

        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = true;
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

        if (numberOfMovements==0 && numberOfBuildings==0)
            return checkIfCanMove(worker);
        if (numberOfMovements==1 && numberOfBuildings==0)
            return checkIfCanBuild(worker);

        return false;
    }

    /**
     * This method checks if the player has completed a turn or if he still have to do some actions.
     * @return true if he can end his turn, false otherwise.
     */
    @Override
    public boolean validateEndTurn() {
        int numberOfMovements = player.getTurn().getNumberOfMovements();
        int numberOfBuildings = player.getTurn().getNumberOfBuildings();

        return numberOfMovements==1 && numberOfBuildings==1 || player.isWinning();
    }
}
