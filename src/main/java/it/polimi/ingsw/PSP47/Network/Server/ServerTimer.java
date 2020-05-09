package it.polimi.ingsw.PSP47.Network.Server;

/**
 * This class controls if the client is still connected to the network.
 */
public class ServerTimer implements Runnable{
    public final static int TIME_EXPIRED_MILLIS = 15000;
    private int timeMillis = 0;
    private final ClientHandler clientHandler;
    private boolean isConnected = true;
    
    public ServerTimer(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }
    
    /**
     * This method controls, each milliseconds, that the client is still connected to the server.
     * If not, it calls the {@link ClientHandler#endConnection()} and terminates.
     */
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
    
    /**
     * When a ping is listened by the {@link ClientHandler}, the timer is reset.
     */
    void resetTime(){
        timeMillis = 0;
    }
}
