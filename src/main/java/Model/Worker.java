package Model;

import Model.Enumerations.Direction;
import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.InvalidDirectionException;
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
    private Slot slot;

    protected Worker(Color color, Gender gender) {
        this.color = color;
        this.gender = gender;
    }
    
    /**
     * @param slot slot where {@link Worker} is going to be placed
     * @return true if the slot was set, false otherwise.
     */
    public boolean setSlot(Slot slot) {
        if (!slot.isOccupied()) {
            this.slot = slot;
            this.slot.setWorker(this);
            return true;
        }
        else return false;
    }

    public Slot getSlot() {
            return this.slot;
    }
    
    public Color getColor() { return this.color; }

    public Gender getGender() { return this.gender; }

    
    /**
     * This method update some parameters that are modified with a worker's movement
     * Who calls the method has to keep in mind that this method set the worker of the previous slot null and THEN put
     * the worker in the new slot.
     * @return true if the worker voluntarily moved up to the level 3, false otherwise
     * @param destinationSlot the slot where the worker is going to move to
     */
    public boolean updatePosition(Slot destinationSlot) {
        int previousLevel = getSlot().getLevel().ordinal();
        this.slot.setWorker(null);
        this.slot = destinationSlot;
        this.slot.setWorker(this);
        int nextLevel = getSlot().getLevel().ordinal();
        
        return nextLevel-previousLevel>0 && getSlot().getLevel()==Level.LEVEL3;
    }
    
    /**
     * This method moves a worker from a slot to another, towards the destination specified.
     * @param direction where the worker wants to move to.
     * @return true if the worker voluntarily moved up to the level 3, false otherwise
     * @throws SlotOccupiedException if the destination slot is occupied by a dome or another worker
     * @throws NotReachableLevelException if the level of the destination has at least 2 blocks more than the current
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     */
    public boolean move (Direction direction)
            throws IndexOutOfBoundsException, SlotOccupiedException, NotReachableLevelException, InvalidDirectionException {

        checkDirection(direction);

        Slot destinationSlot = Board.getBoard().getNearbySlot(direction, slot);
        if (destinationSlot.isOccupied()) throw new SlotOccupiedException();
        if (destinationSlot.getLevel().ordinal() - slot.getLevel().ordinal()>1) throw new NotReachableLevelException();
        return updatePosition(destinationSlot);
    }

    /**
     * This method builds in the specified direction.
     * @param direction where the worker wants to build to.
     * @throws SlotOccupiedException if the destination {@link Slot} is occupied.
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     * @throws InvalidDirectionException if the switch-else of getNearbySlot enters the default case. It shouldn't happen.
     */
    public void build (Direction direction)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidDirectionException {

        checkDirection(direction);

        Slot destinationSlot = Board.getBoard().getNearbySlot(direction, slot);
        if(destinationSlot.isOccupied()) throw new SlotOccupiedException();
        Level levelToUpdate;
        levelToUpdate = destinationSlot.getLevel();
        switch (levelToUpdate) {
            case LEVEL3: destinationSlot.setLevel(Level.DOME);
            break;
            case LEVEL2: destinationSlot.setLevel(Level.LEVEL3);
            break;
            case LEVEL1: destinationSlot.setLevel(Level.LEVEL2);
            break;
            case GROUND: destinationSlot.setLevel(Level.LEVEL1);
        }
    }

    public void buildDome (Direction direction) throws InvalidDirectionException, SlotOccupiedException {
        checkDirection(direction);

        Slot destinationSlot = Board.getBoard().getNearbySlot(direction, slot);
        if (destinationSlot.isOccupied()) throw new SlotOccupiedException();
        destinationSlot.setLevel(Level.DOME);
    }

    /**
     * This method check if in the direction chosen by the {@link Player} exists a slot.
     * @param direction the chosen direction
     * @throws IndexOutOfBoundsException if the slot in the direction doesn't exist.
     */
    public void checkDirection(Direction direction) throws IndexOutOfBoundsException {
        switch (direction){
            case LEFT:
                if (slot.getColumn()<1) throw new IndexOutOfBoundsException();
                break;
            case DOWN:
                if (slot.getRow()> Board.ROWSNUMBER -2) throw new IndexOutOfBoundsException();
                break;
            case UP:
                if (slot.getRow()<1) throw new IndexOutOfBoundsException();
                break;
            case RIGHT:
                if (slot.getColumn()>Board.COLUMNSNUMBER -2) throw new IndexOutOfBoundsException();
                break;
            case LEFTDOWN:
                if (slot.getRow()>Board.ROWSNUMBER-2 || slot.getColumn()<1) throw new IndexOutOfBoundsException();
                break;
            case RIGHTDOWN:
                if (slot.getRow()>Board.ROWSNUMBER-2 || slot.getColumn()>Board.COLUMNSNUMBER-2) throw new IndexOutOfBoundsException();
                break;
            case LEFTUP:
                if (slot.getRow()<1|| slot.getColumn()<1) throw new IndexOutOfBoundsException();
                break;
            case RIGHTUP:
                if (slot.getRow()<1 || slot.getColumn()>Board.COLUMNSNUMBER-2) throw new IndexOutOfBoundsException();
                break;
        }
    }

}
