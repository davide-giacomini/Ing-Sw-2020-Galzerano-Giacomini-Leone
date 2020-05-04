package it.polimi.ingsw.PSP47.Network.Client;

import java.util.TimerTask;

public class CheckServerNetDown extends TimerTask {
    private final static long EXPIRATION_TIME_MILLIS = 15000;
    private boolean pingSuccessful = false;
    NetworkHandler networkHandler;
    
    public CheckServerNetDown(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }
    
    @Override
    public void run() {
        while (!pingSuccessful) {
            if (System.currentTimeMillis()-scheduledExecutionTime() > EXPIRATION_TIME_MILLIS){
                networkHandler.endConnection();
            }
            try {
                Thread.sleep(1000);
                System.out.println("STA CONTROLLANDO IL CheckServerNetDown" + this.toString());
                System.out.println(System.currentTimeMillis()-scheduledExecutionTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void setPingSuccessfulTrue(){
        pingSuccessful = true;
    }
}
