package it.polimi.ingsw.PSP47.Network.Client;

import it.polimi.ingsw.PSP47.Network.Server.*;
import it.polimi.ingsw.PSP47.View.CLI.CLI;
import it.polimi.ingsw.PSP47.View.GUI.GUI;
import it.polimi.ingsw.PSP47.View.View;
import javafx.application.Application;

import java.util.Scanner;

/**
 * This class instantiates a thread for each client and handles the choice of the graphical interface and the
 * {@link Server} the user wants to connect to.
 */
public class Client {
    
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
    
        String viewChoice = askView(scanner);

        if ("CLI".equals(viewChoice.toUpperCase()))
            new CLI();
        else if ("GUI".equals(viewChoice.toUpperCase()))
            Application.launch(GUI.class);

    }
    
    private String askView(Scanner scanner) {
        System.out.println("You want to play with CLI or GUI?");
        String viewChoice = scanner.nextLine();
        
        
        while (viewChoice==null || !viewChoice.toUpperCase().equals("CLI") && !viewChoice.toUpperCase().equals("GUI")) {
            System.out.println("You have to write \"CLI\" or \"GUI\"!\nWhich one do you want?.");
            viewChoice = scanner.nextLine();
        }
        
        
        return viewChoice;
    }
}
