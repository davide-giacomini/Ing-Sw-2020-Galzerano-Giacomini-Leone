package Model.Gods;

import Model.Enumerations.Direction;
import Model.Exceptions.InvalidDirectionException;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;
import Model.Exceptions.WrongBuildOrMoveException;
import Model.Game;
import Model.Player;
import Model.Worker;

public class Athena extends God{
    public Athena(Player player, String name) {
        super(player, name);
        MIN_MOVEMENTS = 1;
        MIN_BUILDINGS = 1;
        MAX_MOVEMENTS = 1;
        MAX_BUILDINGS = 1;
        canBuildDome = false;
        canUseBothWorkers = false;
    }

    @Override
    public boolean move(Direction direction, Worker worker) throws SlotOccupiedException, NotReachableLevelException, IndexOutOfBoundsException, InvalidDirectionException {
        int initialLevel = worker.getSlot().getLevel().ordinal();
        boolean winCondition = worker.move(direction);
        int actualLevel = worker.getSlot().getLevel().ordinal();
        if (actualLevel>initialLevel) {
            for (int i = 0; i<3; i++) {
                if (Game.getPlayer(i) != null && Game.getPlayer(i) != player) {
                    Game.getPlayer(i).setCantMoveUp(true);
                }
            }
        }
        else {
            for (int i = 0; i<3; i++) {
                if (Game.getPlayer(i) != null && Game.getPlayer(i) != player) {
                    Game.getPlayer(i).setCantMoveUp(false);
                }
            }
        }
        return winCondition;
    }

    @Override
    public void build(Direction direction, Worker worker) throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException, WrongBuildOrMoveException {
        if (player.getTurn().getNumberOfMovements() == 0) throw new WrongBuildOrMoveException();

        worker.build(direction);
    }

    @Override
    public void resetParameters() {

    }

}
