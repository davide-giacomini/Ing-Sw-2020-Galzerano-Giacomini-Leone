package View;

import Network.Client.Client;

import java.io.PrintStream;
import java.util.Scanner;

public abstract class View {
    public Client currentClient;
    public final static int SOCKET_PORT = 7777;

    private Scanner in;
    private PrintStream out;

    public View (Client client){
        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out);
        currentClient = client;
    }

    public void startConnection(){}


}
