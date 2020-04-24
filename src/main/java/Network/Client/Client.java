package Network.Client;

import Network.Server.Server;
import View.View;
import View.CLI;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private View view;
    
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
    
    @Override
    public void run() {
        //initial request of choice between GUI or CLI
        Scanner scanner = new Scanner(System.in);
        System.out.println("CLI or GUI ?");
        String viewChoice = scanner.nextLine();
        
        if ("CLI".equals(viewChoice.toUpperCase())) {
            view = new CLI(this);
        } else if ("GUI".equals(viewChoice.toUpperCase())) {
            //TODO istanzia la gui come view
        }
        String serverIpAddress = view.askServerIpAddress();
    
        // open a connection with the server
        Socket serverSocket;
        try {
            serverSocket = new Socket(serverIpAddress, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
    
        NetworkHandler networkHandler = new NetworkHandler(this, serverSocket);
        Thread thread = new Thread(networkHandler);
        thread.start();
    }
    
    public View getView() {
        return view;
    }
}
