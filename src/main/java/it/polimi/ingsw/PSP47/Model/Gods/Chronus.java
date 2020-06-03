package it.polimi.ingsw.PSP47.Model.Gods;
import it.polimi.ingsw.PSP47.Model.Player;

/**
 * With Chronus, {@link Player} can win also if there are 5 COMPLETE towers on the Board.
 * However, this check is done in the Game class, as it must be controlled after the build action of each player,
 * and not only if the one who has built is Chronus.
 */
public class Chronus extends God  {

    public Chronus(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canAlwaysBuildDome = false;
        threePlayersGame = false;
    }

}
