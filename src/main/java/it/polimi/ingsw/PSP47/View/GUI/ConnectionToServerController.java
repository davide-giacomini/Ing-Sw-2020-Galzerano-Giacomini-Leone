package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Network.Client.NetworkConnectionUtil;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ConnectionToServerController {
    GUI gui;
    
    public void setGui(GUI gui) {
        this.gui = gui;
    }
    
    @FXML
    TextField ipAddress;
    
    @FXML
    BorderPane internalBorderPane;
    
    @FXML
    void computeAddress(KeyEvent event) {
        NetworkHandler networkHandler;
        
        if (event.getCode() == KeyCode.ENTER){
            try {
                networkHandler = NetworkConnectionUtil.setConnection(gui, ipAddress.getText());
                gui.setNetworkHandler(networkHandler);
            } catch (IOException e) {
                Label label = new Label("Server unreachable.");
                label.setTextFill(Color.RED);
                BorderPane.setAlignment(label, Pos.CENTER);
                internalBorderPane.setBottom(label);
            }
        }
    }
}
