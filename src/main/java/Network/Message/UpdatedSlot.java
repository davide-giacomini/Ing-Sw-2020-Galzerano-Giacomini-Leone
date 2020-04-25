package Network.Message;

import Enumerations.MessageType;
import Model.Slot;

/**
 * This message contains an update from the model (a modified slot).
 * It's a S->C message.
 */
public class UpdatedSlot extends Message{

    private static final long serialVersionUID = 709094366320013507L;
    private Slot updatedSlot ;

    public UpdatedSlot(MessageType messageType) {
        super(messageType);

    }

    public Slot getUpdatedSlot() {
        return updatedSlot;
    }

    public void setUpdatedSlot(Slot updatedSlot) {
        this.updatedSlot = updatedSlot;
    }


}
