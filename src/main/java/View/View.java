package View;

import Network.Client.Client;

import java.io.PrintStream;
import java.util.Scanner;

public abstract class View {
    public Client currentClient;
    public final static int SOCKET_PORT = 7777;

    public View (Client client){
        currentClient = client;
    }

    public void startConnection(){}

    public abstract String askUsername();

    public abstract String askColorWorkers();

    public abstract void print(String text);

}
