package it.polimi.ingsw.PSP47.Model.Gods;

import it.polimi.ingsw.PSP47.Model.Player;
import it.polimi.ingsw.PSP47.Model.Worker;

/**
 * If a {@link Player} has Hera, the opponents' {@link Worker} cannot win by moving into a perimeter slot.
 * However, this check is done in the Game class, as is must be controlled for each player.
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
}
