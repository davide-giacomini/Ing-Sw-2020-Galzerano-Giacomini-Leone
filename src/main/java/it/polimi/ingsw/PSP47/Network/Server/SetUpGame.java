package it.polimi.ingsw.PSP47.Network.Server;

import it.polimi.ingsw.PSP47.Network.Message.FirstConnection;
import it.polimi.ingsw.PSP47.Network.Message.FirstPlayerConnection;
import it.polimi.ingsw.PSP47.Network.Message.Message;
import it.polimi.ingsw.PSP47.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SetUpGame extends Observable implements Runnable{
    private final Socket clientSocket;
    private final boolean isTheFirstPlayer;
    private ObjectInputStream inputClient;
    private ObjectOutputStream outputClient;
    private boolean connectionOver = false;
    
    public SetUpGame (Socket clientSocket, boolean isTheFirstPlayer){
        this.clientSocket = clientSocket;
        this.isTheFirstPlayer = isTheFirstPlayer;
    }
    
    
    @Override
    public void run() {
        try {
            inputClient = new ObjectInputStream(clientSocket.getInputStream());
            outputClient = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    
        if (isTheFirstPlayer)
                askParametersToTheFirstPlayer();
        else
            askParametersToPlayer();
        
        try {
            while (!connectionOver){
                Message message = (Message) inputClient.readObject();
                switch (message.getMessageType()){
                    case FIRST_PLAYER_CONNECTION:
                        notifyFirstPlayerParameters((FirstPlayerConnection) message, clientSocket);
                        connectionOver = true;
                        break;
                    case FIRST_CONNECTION:
                        notifyFirstConnection((FirstConnection) message, clientSocket);
                        connectionOver = true;
                        break;
                    default:
                        connectionOver = true;
                        //TODO è arrivato un messaggio sbagliato, faccio cadere la connessione
                }
            }
        } catch (IOException e) {
            connectionOver = true;
            //TODO catchata se la socket si chiude
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            connectionOver = true;
            //TODO catchata se la readObject funziona male
            e.printStackTrace();
        }
    }
    
    private void askParametersToTheFirstPlayer(){
        try {
            outputClient.writeObject(new FirstPlayerConnection(null, null, null));
        } catch (IOException e) {
            //TODO notifica il server che c'è un'eccezione
            e.printStackTrace();
        }
    }
    
    private void askParametersToPlayer(){
        try {
            outputClient.writeObject(new FirstConnection(null, null));
        } catch (IOException e) {
            //TODO notifica il server che c'è un'eccezione
            e.printStackTrace();
        }
    }
}
