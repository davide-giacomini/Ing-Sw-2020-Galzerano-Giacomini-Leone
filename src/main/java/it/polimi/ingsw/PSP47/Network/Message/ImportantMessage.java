package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

public class ImportantMessage extends Message {
    private static final long serialVersionUID = -6704283313352329038L;
    private String text;

    public ImportantMessage(String text) {
        this.text=text;
        this.messageType= MessageType.IMPORTANT;
    }

    public String getText() {
        return text;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
}
