package it.polimi.ingsw.PSP47.Model;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.Gender;
import it.polimi.ingsw.PSP47.Enumerations.Level;
import it.polimi.ingsw.PSP47.Model.Exceptions.*;


public class Worker {
    /**
     * Variable which represents a male. It's useful to call the male worker through the player's array.
     */
    public final static int MALE = 0;
    /**
     * Variable which represents a female. It's useful to call the female worker through the player's array.
     */
    public final static int FEMALE = 1;
    private final Color color;
    private final Gender gender;
    private Slot slot;
    private final Game game;

    protected Worker(Color color, Gender gender, Game game) {
        this.color = color;
        this.gender = gender;
        this.game = game;
    }
    
    /**
     * This methods automatically updates the {@link Slot}'s worker.
     * The method {@link Slot#setWorker(Worker)} mustn't be called.
     *
     * @param slot slot where {@link Worker} is going to be placed
     */
    public boolean setSlot(Slot slot) {
        this.slot = slot;
        if (slot!=null && !slot.isOccupied()) {
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
     * @throws IndexOutOfBoundsException if the direction is out of the board.
     * @throws InvalidMoveException if the move is not permitted.
     * @throws SlotOccupiedException if the destination {@link Slot} is occupied.
     */
    public boolean move (Direction direction)
            throws InvalidMoveException, IndexOutOfBoundsException, SlotOccupiedException {

        checkDirection(direction);

        Slot destinationSlot;
        try {
            destinationSlot = game.getBoard().getNearbySlot(direction, slot);
        }
        catch (InvalidDirectionException e){
            throw new InvalidMoveException("Invalid direction of the getNearBySlot.");
        }
        if (destinationSlot.isOccupied()) throw new SlotOccupiedException();
        if (destinationSlot.getLevel().ordinal() - slot.getLevel().ordinal()>1)
            throw new InvalidMoveException("Level unreachable");
        
        return updatePosition(destinationSlot);
    }

    /**
     * This method builds in the specified direction.
     * @param direction where the worker wants to build to.
     * @throws SlotOccupiedException if the destination {@link Slot} is occupied.
     * @throws IndexOutOfBoundsException if the destination {@link Slot} is outside the {@link Board}
     * @throws InvalidBuildException if the build is not permitted.
     */
    public void build (Direction direction)
            throws IndexOutOfBoundsException, SlotOccupiedException, InvalidBuildException {

        checkDirection(direction);
        
        Slot destinationSlot;
        try {
            destinationSlot = game.getBoard().getNearbySlot(direction, slot);
        }
        catch (InvalidDirectionException e){
            throw new InvalidBuildException("Invalid direction for the destination slot");
        }
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

        Slot destinationSlot = game.getBoard().getNearbySlot(direction, slot);
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
                if (slot.getRow()> Board.ROWS_NUMBER -2) throw new IndexOutOfBoundsException();
                break;
            case UP:
                if (slot.getRow()<1) throw new IndexOutOfBoundsException();
                break;
            case RIGHT:
                if (slot.getColumn()>Board.COLUMNS_NUMBER -2) throw new IndexOutOfBoundsException();
                break;
            case LEFTDOWN:
                if (slot.getRow()>Board.ROWS_NUMBER -2 || slot.getColumn()<1) throw new IndexOutOfBoundsException();
                break;
            case RIGHTDOWN:
                if (slot.getRow()>Board.ROWS_NUMBER -2 || slot.getColumn()>Board.COLUMNS_NUMBER -2) throw new IndexOutOfBoundsException();
                break;
            case LEFTUP:
                if (slot.getRow()<1|| slot.getColumn()<1) throw new IndexOutOfBoundsException();
                break;
            case RIGHTUP:
                if (slot.getRow()<1 || slot.getColumn()>Board.COLUMNS_NUMBER -2) throw new IndexOutOfBoundsException();
                break;
        }
    }


}
