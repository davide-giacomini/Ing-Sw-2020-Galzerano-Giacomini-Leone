package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;
import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * {@link Player}'s {@link Worker} may build a dome instead of the "following rules" level.
 */
public class Atlas extends God {

    
    public Atlas(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = true;
        threePlayersGame = true;
    }

    /**
     * This method calls two different worker's methods, depending on the setting of WantsToBuildDome.
     * @param direction specifies the slot where to build
     * @param worker one of the player's workers
     * @throws IndexOutOfBoundsException if the worker try to build in a direction that is out out the board
     * @throws InvalidDirectionException if there are troubles with I/O
     * @throws InvalidBuildException if building is not permitted.
     */
    @Override
    public void build(Direction direction, Worker worker)
            throws IndexOutOfBoundsException, InvalidBuildException, InvalidDirectionException {
        if (player.getTurn().getNumberOfMovements() == 0) {
            throw new InvalidBuildException("Order of movements incorrect");
        }

        if (player.getTurn().wantsToBuildDome()) {
            try {
                worker.buildDome(direction);
            } catch (SlotOccupiedException e) {
                throw new InvalidBuildException("Slot occupied");
            }
        }
        else if (!player.getTurn().wantsToBuildDome()) {
            try {
                worker.build(direction);
            } catch (SlotOccupiedException e) {
                throw new InvalidBuildException("Slot occupied");
            }
        }

    }
}
