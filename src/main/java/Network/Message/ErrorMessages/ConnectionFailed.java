package Network.Message.ErrorMessages;

import Enumerations.MessageType;
import Network.Message.Message;

public class ConnectionFailed extends Message {
    String errorMessage;
    
    public ConnectionFailed(MessageType messageType) {
        super(messageType);
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
