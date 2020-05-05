package it.polimi.ingsw.PSP47.Network.Server;

public class ServerTimer implements Runnable{
    private final int TIME_EXPIRED_MILLIS = 15000;
    private int timeMillis = 0;
    private final ClientHandler clientHandler;
    private boolean isConnected = true;
    
    public ServerTimer(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }
    
    @Override
    public void run() {
        while (isConnected) {
            try {
                if (timeMillis > TIME_EXPIRED_MILLIS)
                    isConnected = false;
    
                Thread.sleep(1);
                timeMillis++;
            } catch (InterruptedException e) {
                isConnected = false;
                e.printStackTrace();
            }
        }
        clientHandler.endConnection();
    }
    
    void setIsConnectedFalse(){
        isConnected = false;
    }
    
    void resetTime(){
        timeMillis = 0;
    }
}
