package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Network.Server.*;
import it.polimi.ingsw.PSP47.View.View;
import it.polimi.ingsw.PSP47.View.CLI;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class instantiates a thread for each client and handles the choice of the graphical interface and the
 * {@link Server} the user wants to connect to.
 */
public class Client {
    private View view;
    
    public static void main(String[] args) {
        Client client = new Client();
        client.init();
    }
    
    /**
     * This method asks the user what graphical interface they desire and which {@link Server} they want to connect to.
     * If everything goes well, a new thread is instantiated: the {@link NetworkHandler}. It will handle the
     * connection with the server.
     */
    public void init() {
        //initial request of choice between GUI or CLI
        Scanner scanner = new Scanner(System.in);
        System.out.println("CLI or GUI ?");
        String viewChoice = scanner.nextLine();
        
        if ("CLI".equals(viewChoice.toUpperCase())) {
            view = new CLI(this);
            view.showTitle();
        } else if ("GUI".equals(viewChoice.toUpperCase())) {
            //TODO istanzia la gui come view
        }
        String serverIpAddress = view.askServerIpAddress();
    
        // open a connection with the server
        Socket serverSocket;
        try {
            serverSocket = new Socket(serverIpAddress, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("Server unreachable.");
            return;
        }
        System.out.println("Connected to the address " + serverSocket.getInetAddress());
    
        NetworkHandler networkHandler = new NetworkHandler(view , serverSocket);
        Thread thread = new Thread(networkHandler);
        thread.start();
    }
    
    public View getView() {
        return view;
    }
}
