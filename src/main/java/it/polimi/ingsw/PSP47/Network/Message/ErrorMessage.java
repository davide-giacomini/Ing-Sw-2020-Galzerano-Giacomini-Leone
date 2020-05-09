package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;

/**
 * This
 */
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

}
