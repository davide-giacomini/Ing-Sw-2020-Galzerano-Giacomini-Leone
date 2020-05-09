package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;

/**
 * This message contains an update from the model (a modified slot).
 */
public class UpdatedSlot extends Message{
    private static final long serialVersionUID = 709094366320013507L;

    private Slot updatedSlot ;

    public UpdatedSlot(Slot updatedSlot) {
        this.updatedSlot = updatedSlot;
        this.messageType=MessageType.UPDATE_SLOT;
    }

    public Slot getUpdatedSlot() {
        return updatedSlot;
    }

}
