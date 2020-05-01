//package it.polimi.ingsw.PSP47.Network.Message;
//
//import it.polimi.ingsw.PSP47.Enumerations.MessageType;
//import it.polimi.ingsw.PSP47.Network.Client.Client;
//import it.polimi.ingsw.PSP47.Network.Server.Server;
//import it.polimi.ingsw.PSP47.Network.Server.VirtualView;
//
//import java.io.ObjectOutputStream;
//
//public class ErrorMessage extends Message {
//    private static final long serialVersionUID = 9177839806408998663L;
//    private String errorText;
//
//    public ErrorMessage(MessageType messageType) {
//        super(messageType);
//    }
//
////    /**
////     * This method print the errorString to the client screen.
////     * @param client the client to be handled.
////     * @param outputServer the {@link ObjectOutputStream} of the server. It can be used to send other messages.
////     */
////    @Override
////    public void handleClientSide(Client client, ObjectOutputStream outputServer) {
////        client.getView().showMessage(getErrorText());
////    }
////
////    /**
////     * This method is empty as is is never send to the server.
////     * @param server the server, which has got the parameters in common with all the clients.
////     * @param virtualView the {@link VirtualView} of the client connected.
////     * @param outputClient the {@link ObjectOutputStream} of the client. It can be used to send other messages.
////     */
////    @Override
////    public void handleServerSide(Server server, VirtualView virtualView, ObjectOutputStream outputClient) {
////    }
//
//    public String getErrorText() {
//        return errorText;
//    }
//
//    public void setErrorText(String errorText) {
//        this.errorText = errorText;
//    }
//}
