package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Server.ClientHandler;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {
    private GameController controller;
    private HashMap<String, Color> mapUserColor;
    private HashMap<String, VirtualView> mapUserVirtualView;



    @Before
    public void setUp() {
        int numberOfPlayers = 2;

        ClientHandler ariannaClient = new ClientHandler(new Socket(), false);
        ClientHandler monicaClient = new ClientHandler(new Socket(), false);
        mapUserColor.put("Arianna", Color.BLUE);
        mapUserVirtualView.put("Arianna", new VirtualView("Arianna", Color.BLUE, ariannaClient));
        mapUserColor.put("Monica", Color.YELLOW);
        mapUserVirtualView.put("Monica", new VirtualView("Monica", Color.YELLOW, monicaClient));
        controller = new GameController(numberOfPlayers, mapUserColor, mapUserVirtualView);
    }

}
