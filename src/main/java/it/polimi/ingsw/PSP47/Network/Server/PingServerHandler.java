package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Message.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PingServerHandler implements Runnable{
    private final int TIME_EXPIRED_MILLIS = 15000;
    private int timeMillis = 0;
    private final ClientHandler clientHandler;
    private ObjectOutputStream outputPing;
    private ObjectInputStream inputPing;
    private boolean isConnected = true;
    
    public PingServerHandler(Socket pingSocket, ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    
        try {
            outputPing = new ObjectOutputStream(pingSocket.getOutputStream());
            inputPing = new ObjectInputStream(pingSocket.getInputStream());
        }
        catch (IOException e){
            System.out.println("Connection failed.");
            this.isConnected = false;
            clientHandler.endConnection();
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        new Thread(() -> {
            try {
                while (isConnected){
                    if (timeMillis > TIME_EXPIRED_MILLIS)
                        isConnected = false;
                
                    Thread.sleep(1);
                    timeMillis++;
                }
            } catch (InterruptedException e) {
                isConnected = false;
                e.printStackTrace();
            }
            finally {
                clientHandler.endConnection();
            }
        
        }).start();
    
        listen();
    }
    
    private void listen() {
        System.out.println("Started listening to the server.");
        
        while (isConnected && !Thread.currentThread().isInterrupted()){
            try {
                Ping ping = (Ping) inputPing.readObject();
//                System.out.println("Ping listened.");
                timeMillis = 0;
            }
            catch (IOException | ClassNotFoundException e){
                isConnected = false;
                e.printStackTrace();
            }
        }
    }
}
