package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Message.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PingServerHandler implements Runnable{
    private final int TIME_EXPIRED_MILLIS = 15000;
    private final Socket pingSocket;
    private int timeMillis = 0;
    private final ClientHandler clientHandler;
    private ObjectOutputStream outputPing;
    private ObjectInputStream inputPing;
    private boolean isConnected = true;
    
    public PingServerHandler(Socket pingSocket, ClientHandler clientHandler){
        this.pingSocket = pingSocket;
        this.clientHandler = clientHandler;
    
        try {
            outputPing = new ObjectOutputStream(this.pingSocket.getOutputStream());
            inputPing = new ObjectInputStream(this.pingSocket.getInputStream());
        }
        catch (IOException e){
            System.out.println("Connection failed.");
            this.isConnected = false;
            clientHandler.endConnection();
            e.printStackTrace();
            try {
                this.pingSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    
    @Override
    public void run() {
        new Thread(() -> {
            while (isConnected){
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
    
            try {
                pingSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            
            clientHandler.endConnection();
        
        }).start();
    
        listen();
    }
    
    private void listen() {
        System.out.println("Started listening to the server.");
        
        while (isConnected){
            try {
                Ping ping = (Ping) inputPing.readObject();
//                System.out.println("Ping listened.");
                timeMillis = 0;
            }
            catch (IOException | ClassNotFoundException e){
                isConnected = false;
//                e.printStackTrace();
            }
        }
    
        try {
            pingSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
