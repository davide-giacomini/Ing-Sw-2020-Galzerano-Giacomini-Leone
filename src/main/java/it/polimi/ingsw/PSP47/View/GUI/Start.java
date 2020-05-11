package it.polimi.ingsw.PSP47.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Start {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField ip;

    @FXML
    void OkClick(ActionEvent event) throws IOException {
        String ipAddress = ip.getText();
        if(!ipAddress.equals("")) {
            GUI.setLayout(mainPane.getScene(), "/FXML/esempio.fxml");
        }
    }

}
