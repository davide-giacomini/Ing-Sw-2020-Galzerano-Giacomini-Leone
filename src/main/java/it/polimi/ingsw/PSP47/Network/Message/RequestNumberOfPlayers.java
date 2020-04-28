package it.polimi.ingsw.PSP47.Network.Message;

import it.polimi.ingsw.PSP47.Enumerations.MessageType;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.Network.Server.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RequestNumberOfPlayers extends Message {
    private static final long serialVersionUID = -2808360409198774148L;
    private int numberOfPlayers;
    
    public RequestNumberOfPlayers(MessageType messageType) {
        super(messageType);
    }
    
    /**
     * The client is asked the number of players they want in the game, then a message with that number is sent to
     * the server.
     *
     * @param client the client to be handled.
     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
     */
    @Override
    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
        int numberOfPlayers = 0;
        while (numberOfPlayers<2 || numberOfPlayers>3)
            numberOfPlayers = client.getView().askNumberOfPlayers();
        RequestNumberOfPlayers requestNumberOfPlayers = new RequestNumberOfPlayers(MessageType.REQUEST_NUMBER_OF_PLAYERS);
        requestNumberOfPlayers.setNumberOfPlayers(numberOfPlayers);
        try {
            outputServer.writeObject(requestNumberOfPlayers);
        } catch (IOException e) {
            client.getView().print("Error in the serialization of " +this.toString()+" message.");
            //e.printStackTrace();
        }
        //TODO provare ad utilizzare lo stesso messaggio per tutti i messaggi che chiedono di rimandare indietro
    }
    
    /**
     * After the client connected chose the number of the players of the game, this method sets it.
     *
     * @param server the server, which has got the parameters in common with all the clients.
     * @param virtualView the {@link VirtualView} of the client connected.
     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
     */
    @Override
    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
        server.setMaxNumberOfPlayers(numberOfPlayers);
    }
    
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
