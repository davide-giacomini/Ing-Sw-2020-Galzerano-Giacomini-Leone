package Network.Message.ErrorMessages;

import Enumerations.MessageType;
import Network.Message.Message;

public class ConnectionFailed extends Message {
    String errorMessage;
    
    public ConnectionFailed(){
        super(MessageType.CONNECTION_FAILED);
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
