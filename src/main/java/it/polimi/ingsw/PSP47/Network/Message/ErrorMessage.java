package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import it.polimi.ingsw.PSP47.Visitor.Visitable;

import java.io.ObjectOutputStream;

public class ErrorMessage extends Message {
    private static final long serialVersionUID = 9177839806408998663L;
    private String errorText;

    public ErrorMessage(String errorText) {
        this.errorText=errorText;
        this.messageType=MessageType.ERROR;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    @Override
    public Visitable getContent() {
        return null;
    }
}
