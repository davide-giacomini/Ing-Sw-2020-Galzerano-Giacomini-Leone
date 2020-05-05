package it.polimi.ingsw.PSP47.Network.Client;

/**
 * This class controls if the server is still connected to the network.
 */
public class ClientTimer implements Runnable{
    public final static int TIME_EXPIRED_MILLIS = 15000;
    private int timeMillis = 0;
    private final NetworkHandler networkHandler;
    private boolean isConnected = true;
    
    public ClientTimer(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }
    
    /**
     * This method controls, each milliseconds, that the client is still connected to the server.
     * If not, it calls the {@link NetworkHandler#endConnection()} and terminates.
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
        networkHandler.endConnection();
    }
    
    void setIsConnectedFalse(){
        isConnected = false;
    }
    
    /**
     * When a ping is listened by the {@link NetworkHandler}, the timer is reset.
     */
    void resetTime(){
        timeMillis = 0;
    }
}
