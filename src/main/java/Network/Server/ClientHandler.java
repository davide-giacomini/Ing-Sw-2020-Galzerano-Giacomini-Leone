package Network.Server;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class ClientHandler implements Runnable{
    private Socket client;
    private int numberOfThread;

    ClientHandler(Socket client, int numberOfThread)
    {
        this.client = client;
        this.numberOfThread = numberOfThread;
    }

    private void AskToThe1stThreadNumberOfPlayers(){
        if(numberOfThread == 0){}
            //tell the controller to ask to the user from the view
            //the number of players, a message with an int as parameter
            //will be sent to the server
    }


    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }



    private void handleClientConnection() throws IOException
    {
        System.out.println("Connected to " + client.getInetAddress());

        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());

        try {
            while (true) {
                /* read a String from the stream and write an uppercase string in response */
                Object next = input.readObject();
                String str = (String)next;

                try {
                    /* simulate a complex computation */
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) { }

                output.writeObject(str.toUpperCase());
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

        client.close();
    }
}

