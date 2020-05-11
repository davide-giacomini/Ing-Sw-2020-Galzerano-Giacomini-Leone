package it.polimi.ingsw.PSP47.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class StartController {

    @FXML
    private Pane mainPane;

    @FXML
    private TextField player_name;

    @FXML
    private TextField player_color;

    @FXML
    void onSubmitClick(ActionEvent event) {
        String username;
        String color;

        username = player_name.getText();
        color = player_color.getText();

        System.out.println(username + " " + color);
    }

    @FXML
    void onSubmit(MouseEvent event) {
        System.out.println("Cliccando qui parteciperai alla partita");
    }
}
