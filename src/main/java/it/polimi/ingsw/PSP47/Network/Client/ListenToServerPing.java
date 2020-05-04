//package it.polimi.ingsw.PSP47.Network.Client;
//
//import it.polimi.ingsw.PSP47.Enumerations.MessageType;
//import it.polimi.ingsw.PSP47.Network.Message.Message;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//
//public class ListenToServerPing extends Thread{
//    private boolean isConnected = true;
//    private final ObjectInputStream inputClient;
//    NetworkHandler networkHandler;
//
//    public ListenToServerPing(ObjectInputStream inputClient, NetworkHandler networkHandler){
//        this.inputClient = inputClient;
//        this.networkHandler = networkHandler;
//    }
//
//    @Override
//    public void run() {
//        while (isConnected){
//            try {
//                Message message = (Message) inputClient.readObject();
//                if (message.getMessageType() == MessageType.PING) {
//                    networkHandler.pingReceived();
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                isConnected = false;
//                e.printStackTrace();
//            }
//        }
//    }
//}
