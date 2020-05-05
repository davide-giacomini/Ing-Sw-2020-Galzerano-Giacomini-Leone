package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Network.Message.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PingHandler implements Runnable{
    private final NetworkHandler networkHandler;
    private final Socket pingSocket;
    private ObjectOutputStream outputPing;
    private ObjectInputStream inputPing;
    private boolean isConnected = true;
    
    public PingHandler(Socket pingSocket, NetworkHandler networkHandler){
        this.pingSocket = pingSocket;
        this.networkHandler = networkHandler;
    
        try {
            outputPing = new ObjectOutputStream(this.pingSocket.getOutputStream());
            inputPing = new ObjectInputStream(this.pingSocket.getInputStream());
        }
        catch (IOException e){
            System.out.println("Connection failed.");
            this.isConnected = false;
            networkHandler.endConnection();
//            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        // send ping
        try {
            while (isConnected){
                Thread.sleep(5000);
                outputPing.writeObject(new Ping());
//                System.out.println("Sent a ping to the server.");
            }
        } catch (InterruptedException | IOException e) {
            isConnected = false;
            networkHandler.endConnection();
            try {
                pingSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
//            e.printStackTrace();
        }
    }
}
