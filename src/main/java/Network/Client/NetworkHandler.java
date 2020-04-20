package Network.Client;

import Network.Message.*;
import View.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class NetworkHandler implements Runnable, Serializable {
    private String ipServer;
    private int port;
    private String username;
    private String color;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private View view;
    private Thread listener;



    NetworkHandler(String ipServer, int port, String username, String color, View view){
        this.ipServer = ipServer;
        this.port = port;
        this.username = username;
        this.color = color;
        this.view = view;
    }

    /**
     * This method creates the ClientSocket and sends a RequestConnection message to start the connection with the server.
     * Then it starts the thread which will be waiting for server messages.
     */
    public void openConnection() {
        Socket server;
        try {
            server = new Socket(ipServer, port);
            out = new ObjectOutputStream(server.getOutputStream());
            in = new ObjectInputStream(server.getInputStream());
            sendMessage(new RequestConnection(getUsername(),getColor()));

            listener = new Thread(this);
            listener.start();

        } catch (IOException e) {
            System.out.println("server unreachable");
        }
    }

    /**
     * This method send a message to the server
     * @param message the message you want to send to the server
     * @throws IOException if there are some I/O problems
     */
    private void sendMessage(MessageVC message) throws IOException {
        if (out != null) {
            out.writeObject(message);
            out.reset();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getColor() {
        return color;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * The networkHandler keeps waiting for messages from the server.
     * They do different things if they come from the model or from the controller.
     */
    @Override
    public synchronized void run() {

        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Ci seiii");
                MessageContainer message = (MessageContainer) in.readObject();
                if (message.getType() == MessageContainer.MV_EVENT) {
                    //do something
                }
                else if (message.getType() == MessageContainer.CV_EVENT) {
                    MessageCV messageCV = (MessageCV) message.getContent();
                    messageCV.accept(this);
                }
                }
            } catch (IOException e) {
                view.print("Server has dropped the connection");
            } catch (ClassNotFoundException e) {
                //what to do?
            }
        }

    public void print(String text) {
        view.print(text);
    }
}
