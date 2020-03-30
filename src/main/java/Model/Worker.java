package Model;

import Model.Enumerations.Gender;
import Model.Enumerations.Level;
import Model.Exceptions.InvalidActionException;

import java.awt.*;

public class Worker {
    private Color color;
    private Gender gender;
    private Level workerLevel;
    private Slot workerSlot;
    private boolean winnerMoveUp;

    public Worker(Color c) {
        this.color = c;
        this.workerLevel = Level.GROUND;

    }

        //bisogna implementare l'eccezione per cui la casella è già piena
        public void setWorkerSlot(Slot slot) {
                if (!slot.isThereAWorker()) {
                    this.workerSlot = slot;
                }
        }

        public Slot getWorkerSlot() {
                return this.workerSlot;
        }

        public Level getWorkerLevel() { return this.workerLevel; }

        public Color getWorkerColor() { return this.color; }

    public Gender getWorkerGender() { return this.gender; }

        /*public void moveUp() throws WorkerCannotGoUpException {
                if (this.workerLevel == Level.GROUND) {
                    this.workerLevel = Level.LEVEL1;
                }
                else if (this.workerLevel == Level.LEVEL1) {
                    this.workerLevel = Level.LEVEL2;
                }

                else if (this.workerLevel == Level.LEVEL2) {
                    this.workerLevel = Level.LEVEL3;
                    this.winnerMoveUp = true;
                }
                else throw new WorkerCannotGoUpException();


        }

        public void moveDown(Slot newSlot) {
                this.workerLevel = newSlot.getSlotLevel();

        } */ //move up e down are not useful



        public void updateWorkerLevel () {
            this.workerLevel = workerSlot.getSlotLevel();
        }

        public void goLeft() throws InvalidActionException {
         //ordinal() method to cast enum to int

          if ( (workerSlot.getLeftSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getLeftSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
              && (workerSlot.getColumn() > 0)) {
              workerSlot.becomeUnoccupied();
              workerSlot = workerSlot.getLeftSlot(workerSlot);
              workerSlot.becomeOccupied(this);
              updateWorkerLevel();
          }
            else throw new InvalidActionException();
        }

        public void goRight() throws InvalidActionException{
            if ( (workerSlot.getRightSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getRightSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    && (workerSlot.getColumn() < 4)) {
                workerSlot.becomeUnoccupied();
                workerSlot = workerSlot.getRightSlot(workerSlot);
                workerSlot.becomeOccupied(this);
                updateWorkerLevel();
            }
            else throw new InvalidActionException();
        }

        public void goUp() throws InvalidActionException{
            if ( (workerSlot.getUpSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getUpSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    && (workerSlot.getRow() > 0)) {
                workerSlot.becomeUnoccupied();
                workerSlot = workerSlot.getUpSlot(workerSlot);
                workerSlot.becomeOccupied(this);
                updateWorkerLevel();
            }
            else throw new InvalidActionException();
        }

        public void goDown() throws InvalidActionException{
            if ( (workerSlot.getDownSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getDownSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    && (workerSlot.getRow() < 4)) {
                workerSlot.becomeUnoccupied();
                workerSlot = workerSlot.getDownSlot(workerSlot);
                workerSlot.becomeOccupied(this);
                updateWorkerLevel();
            }
            else throw new InvalidActionException();
        }

        public void goUpLeft() throws InvalidActionException{
            if ( (workerSlot.getUpLeftSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getUpLeftSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    && (workerSlot.getRow() > 0)) {
                workerSlot.becomeUnoccupied();
                workerSlot = workerSlot.getUpLeftSlot(workerSlot);
                workerSlot.becomeOccupied(this);
            }
            else throw new InvalidActionException();
        }

        public void goUpRight() throws InvalidActionException{
            if ( (workerSlot.getUpRightSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getUpRightSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    || (workerSlot.getRow() > 0)) {
                workerSlot.becomeUnoccupied();
                workerSlot = workerSlot.getUpRightSlot(workerSlot);
                workerSlot.becomeOccupied(this);
                updateWorkerLevel();
            }
            else throw new InvalidActionException();

        }

        public void goDownLeft() throws InvalidActionException{
            if ( (workerSlot.getDownLeftSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getDownLeftSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    && (workerSlot.getRow() > 0)){
            workerSlot.becomeUnoccupied();
            workerSlot = workerSlot.getDownLeftSlot(workerSlot);
            workerSlot.becomeOccupied(this);
            updateWorkerLevel();
            }
            else throw new InvalidActionException();
        }

        public void goDownRight() throws InvalidActionException{
            if ( (workerSlot.getDownRightSlot(workerSlot).isThereAWorker() ==false) && (workerSlot.getDownRightSlot(workerSlot).getSlotLevel().ordinal() - workerLevel.ordinal() < 2)
                    && (workerSlot.getRow() > 0)) {
                workerSlot.becomeUnoccupied();
                workerSlot = workerSlot.getDownRightSlot(workerSlot);
                workerSlot.becomeOccupied(this);
                updateWorkerLevel();
            }
            else throw new InvalidActionException();

        }


        //build here or in player

        }
