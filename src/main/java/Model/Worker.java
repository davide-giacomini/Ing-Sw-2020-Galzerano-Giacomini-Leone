package Model;

import Model.Enumerations.Gender;
import Model.Enumerations.Level;
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
     * @param slotDestination the slot where the worker is going to move to
     */
    private boolean updatePosition(Slot slotDestination) {
        this.slot = slotDestination;
        this.level = this.slot.getLevel();
        
        return this.level == Level.LEVEL3;
    }
    
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot in the left
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goLeft() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getColumn()<1) throw new IndexOutOfBoundsException();
        
        Slot leftSlot = Board.getBoard().getLeftSlot(slot);
        if (leftSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(leftSlot);
        }
    }
    /**
     * @return true if the worker reached the third right
     * @throws IndexOutOfBoundsException if there isn't a slot in the left
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goRight() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getColumn()>Board.COLUMNSNUMBER -2) throw new IndexOutOfBoundsException();
    
        Slot rightSlot = Board.getBoard().getRightSlot(slot);
        if (rightSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(rightSlot);
        }
    }
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot up
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goUp() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getRow()>Board.ROWSNUMBER -2) throw new IndexOutOfBoundsException();
        
        Slot upSlot = Board.getBoard().getUpSlot(slot);
        if (upSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(upSlot);
        }
    }
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot down
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goDown() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getRow()<1) throw new IndexOutOfBoundsException();
        
        Slot downSlot = Board.getBoard().getDownSlot(slot);
        if (downSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(downSlot);
        }
    }
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot up-left
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goUpLeft() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getRow()>Board.ROWSNUMBER-2 || slot.getColumn()<1) throw new IndexOutOfBoundsException();
        
        Slot upLeftSlot = Board.getBoard().getUpLeftSlot(slot);
        if (upLeftSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(upLeftSlot);
        }
    }
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot up-right
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goUpRight() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getRow()>Board.ROWSNUMBER-2 || slot.getColumn()>Board.COLUMNSNUMBER-2) throw new IndexOutOfBoundsException();
        
        Slot upRightSlot = Board.getBoard().getUpRightSlot(slot);
        if (upRightSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(upRightSlot);
        }
    }
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot down-left
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goDownLeft() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getRow()<1|| slot.getColumn()<1) throw new IndexOutOfBoundsException();
        
        Slot downLeftSlot = Board.getBoard().getDownLeftSlot(slot);
        if (downLeftSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(downLeftSlot);
        }
    }
    /**
     * @return true if the worker reached the third level
     * @throws IndexOutOfBoundsException if there isn't a slot down-right
     * @throws SlotOccupiedException if the slot is occupied
     */
    public boolean goDownRight() throws IndexOutOfBoundsException, SlotOccupiedException {
        if (slot.getRow()<1 || slot.getColumn()>Board.COLUMNSNUMBER-2) throw new IndexOutOfBoundsException();
        
        Slot downRightSlot = Board.getBoard().getDownRightSlot(slot);
        if (downRightSlot.isOccupied()) throw new SlotOccupiedException();
        else {
            return updatePosition(downRightSlot);
        }
    }
    
}
