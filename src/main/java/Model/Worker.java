package Model;

import Enumerations.Gender;
import Enumerations.Level;
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

        public void moveUp() {
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
        }

        public void moveDown(Slot newSlot) {
                this.workerLevel = newSlot.getSlotLevel();
        }

        public void goLeft() {
            workerSlot.becomeUnoccupied();
            workerSlot = workerSlot.getLeftSlot(workerSlot);
            workerSlot.becomeOccupied(this);
        }

        }
