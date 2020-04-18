package Network.Client;

import View.CLI;
import View.View;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client implements Runnable{


    public Client (){

    }

    public static void main( String[] args )
    {


       Client client = new Client();
        client.run();



    }

    public void setConnection(String ipServer, int port, String username, String color) {
       boolean connectionNotSetted = true;

       while (connectionNotSetted){

               NetworkHandler clientSocket = new NetworkHandler(ipServer, port,username, color);
               clientSocket.run();
               connectionNotSetted = false ;
       }

    }

    @Override
    public void run() {
        View view;
        Client client = this;
        //initial request of choice between GUI or CLI
        Scanner scanner = new Scanner(System.in);
        System.out.println("CLI or GUI ?");
        String viewChoice = scanner.nextLine();

        if (viewChoice.equals("CLI")){
            view = new CLI(client);
            view.startConnection();

        }
    }
}
