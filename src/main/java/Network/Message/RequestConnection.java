package Network.Message;

import Controller.GameController;

public class RequestConnection implements MessageVC {

    private String username;
    private String color;

    public RequestConnection(String username, String color){
        this.username=username;
        this.color=color;
    }

    public String getUsername(){
        return username;
    }

    public String getColor(){
        return color;
    }

    @Override
    public void accept(GameController controller) {

    }
}
