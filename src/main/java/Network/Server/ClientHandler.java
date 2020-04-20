package Network.Server;

import Network.Message.MessageContainer;
import Network.Message.MessageVC;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ClientHandler implements Runnable{
    private boolean connected;
    private VirtualView virtualView;
    private Socket client;
    private ServerSocket serverSocket;
    private SantoriniServer server;
    private int numberOfThread;
    private String username;
    private String color;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Thread listener;

    ClientHandler(Socket client, ServerSocket serverSocket, int numberOfThread, String username, String color, ObjectInputStream in, ObjectOutputStream out, SantoriniServer server)
    {
        this.client = client;
        this.serverSocket = serverSocket;
        this.numberOfThread = numberOfThread;
        this.username = username;
        this.color = color;
        this.in = in;
        this.out = out;
        this.server = server;
        listener = new Thread();
        listener.start();
        connected = true;
    }

    private void AskToThe1stThreadNumberOfPlayers(){
        if(numberOfThread == 0){}
            //tell the controller to ask to the user from the view
            //the number of players, a message with an int as parameter
            //will be sent to the server
    }


    public void run()
    {
        System.out.println("Connected to " + client.getInetAddress());

        try {
            while (!Thread.currentThread().isInterrupted()) {
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                MessageVC message = (MessageVC) input.readObject();
                update(message);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("invalid stream from client");
        } catch (IOException e) {
            System.out.println("Connection dropped");
        }

    }

    public void update(MessageVC message) {
        // message.accept(vView.getController());
    }

    public String getUsername() {
        return username;
    }

    public String getColor() {
        return color;
    }

    public void send(MessageContainer message) {
        try {
            out.writeObject(message);
            out.flush();
            out.reset();
        } catch (IOException e) {
            // ??
        }
    }

    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                //what to do?
            }

            listener.interrupt();
            connected = false;
            server.onDisconnect(this);
        }
    }
}

