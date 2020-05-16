package it.polimi.ingsw.PSP47.Controller;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Server.ClientHandler;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

import java.net.Socket;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {
    private GameController controller;
    private HashMap<String, Color> mapUserColor = new HashMap<>();
    private HashMap<String, VirtualView> mapUserVirtualView = new HashMap<>();


    @Mock
    ClientHandler ariannaClientMock;

    @Mock
    ClientHandler monicaClientMock ;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        int numberOfPlayers = 2;

        mapUserColor.put("Arianna", Color.BLUE);
        VirtualView v1 = new VirtualView("Arianna", Color.BLUE, ariannaClientMock);
        mapUserVirtualView.put("Arianna",  v1);
        mapUserColor.put("Monica", Color.YELLOW);
        mapUserVirtualView.put("Monica", new VirtualView("Monica", Color.YELLOW, monicaClientMock));
        controller = new GameController(numberOfPlayers, mapUserColor, mapUserVirtualView);
    }

    @Test
    public void startController(){
        assertEquals(controller.getNumberOfPlayers(),2);
    }



}
