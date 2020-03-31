package Model;

import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.NotReachableLevelException;
import Model.Exceptions.SlotOccupiedException;

import java.awt.*;

public class Worker {
    /**
     * Variable which represents a male. It's useful to call the male worker through the player's array.
     */
    public final static int MALE = 0;
    /**
     * Variable which represents a female. It's useful to call the female worker through the player's array.
     */
    public final static int FEMALE = 1;
    private Color color;
    private Gender gender;
    private Level level;
    private Slot slot;
    private boolean winnerMoveUp;

    public Worker(Color color, Gender gender) {
        this.color = color;
        this.gender = gender;
        this.level = Level.GROUND;
    }
    
    /**
     * @param slot slot where {@link Worker} is going to be placed
     * @return true if the slot was set, false otherwise.
     */
    public boolean setSlot(Slot slot) {
        if (!slot.isOccupied()) {
            this.slot = slot;
            return true;
        }
        else return false;
    }

    public Slot getSlot() {
            return this.slot;
    }

    public Level getLevel() { return this.level; }

    public Color getColor() { return this.color; }

    public Gender getGender() { return this.gender; }
    
    
    /**
     * @return true if the worker reached the level 3, false otherwise
     * @param destinationSlot the slot where the worker is going to move to
     */
    private boolean updatePosition(Slot destinationSlot) {
        this.slot = destinationSlot;
        this.level = this.slot.getLevel();
    
        return this.level == Level.LEVEL3;
    }
    
    /**
     * This method moves a worker from a slot to another, towards the destination specified.
     * @param direction where the worker want to move to.
     * @return true if the level three is reached, false otherwise.
     * @throws SlotOccupiedException if the destination slot is occupied by a dome or another worker
     * @throws NotReachableLevelException if the level of the destination has at least 2 blocks more than the current
     * slot
     */
    public boolean move (Direction direction) throws SlotOccupiedException, NotReachableLevelException {
        
        switch (direction){
            case LEFT:
                if (slot.getColumn()<1) throw new IndexOutOfBoundsException();
            case DOWN:
                if (slot.getRow()>Board.ROWSNUMBER -2) throw new IndexOutOfBoundsException();
            case UP:
                if (slot.getRow()<1) throw new IndexOutOfBoundsException();
            case RIGHT:
                if (slot.getColumn()>Board.COLUMNSNUMBER -2) throw new IndexOutOfBoundsException();
            case LEFTDOWN:
                if (slot.getRow()>Board.ROWSNUMBER-2 || slot.getColumn()<1) throw new IndexOutOfBoundsException();
            case RIGHTDOWN:
                if (slot.getRow()>Board.ROWSNUMBER-2 || slot.getColumn()>Board.COLUMNSNUMBER-2) throw new IndexOutOfBoundsException();
            case LEFTUP:
                if (slot.getRow()<1|| slot.getColumn()<1) throw new IndexOutOfBoundsException();
            case RIGHTUP:
                if (slot.getRow()<1 || slot.getColumn()>Board.COLUMNSNUMBER-2) throw new IndexOutOfBoundsException();
        }
        
        try {
            Slot destinationSlot = Board.getBoard().getNearbySlot(direction, slot);
            if (destinationSlot.isOccupied()) throw new SlotOccupiedException();
            if (destinationSlot.getLevel().ordinal() - slot.getLevel().ordinal()>1) throw new NotReachableLevelException();
            return updatePosition(destinationSlot);
        }
        catch (Exception e) {System.out.println("Entered getNearBySlot exception... so strange");}
        
        return false;
    }
    
}
