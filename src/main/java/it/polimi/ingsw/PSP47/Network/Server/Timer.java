package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Message.TimerUpdater;

public class Timer implements Runnable {
    private int secondsWaited;
    private boolean timeExpired = false;
    private final ClientHandler clientHandler;
    public static final int MAX_MILLISECONDS_PER_PLAYER = 60000;
    
    public Timer (ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }
    
    @Override
    public void run() {
        while (!timeExpired){
            try {
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsWaited+=10;
    
            if (secondsWaited<=MAX_MILLISECONDS_PER_PLAYER) {
                TimerUpdater message = new TimerUpdater();
                message.setSecondsWaited(secondsWaited);
                clientHandler.send(message);
            }
            else
                notifyTimeIsExpired();
        }
    }
    
    void notifyTimeIsExpired(){
        timeExpired = true;
        TimerUpdater message = new TimerUpdater();
        message.setExpired();
        clientHandler.handleDisconnection();
    }
}
