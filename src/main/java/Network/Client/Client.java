package Network.Client;

import View.CLI;
import View.View;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client implements Runnable{
    View view;

    public static void main( String[] args )
    {
        Client client = new Client();
        client.run();
    }

    public void setConnection(String ipServer, int port, String username, String color) {
       boolean connectionNotSet = true;

       while (connectionNotSet)
       {
           NetworkHandler clientSocket = new NetworkHandler(ipServer, port, username, color, view);
           clientSocket.openConnection();
           connectionNotSet = false ;
       }
    }

    @Override
    public void run() {
        Client client = this;
        //initial request of choice between GUI or CLI
        Scanner scanner = new Scanner(System.in);
        System.out.println("CLI or GUI ?");
        String viewChoice = scanner.nextLine();

        if ("CLI".equals(viewChoice.toUpperCase())){
            view = new CLI(client);
            view.startConnection();
        }
    }

    public View getView() {
        return view;
    }
}
