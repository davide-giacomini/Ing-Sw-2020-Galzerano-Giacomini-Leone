package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.View.CLI.ViewObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Start extends ViewObservable {

    private Client client;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField ip;

    @FXML
    void OkClick(ActionEvent event) throws IOException {

        String ipAddress = ip.getText();
       // if(!ipAddress.equals("")) {
        //addViewListener(client.getNetworkHandler());

            //notifyViewListener();
            //GUI.setLayout(mainPane.getScene(), "/FXML/esempio.fxml");

    }

    public void setClient(Client client) {
        this.client = client;
    }

}
