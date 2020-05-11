package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Client.Client;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.CLI.AnsiCode;
import it.polimi.ingsw.PSP47.View.CLI.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class StartController extends ViewObservable {

    private NetworkHandler networkHandler;

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

        if(!username.equals("") && !color.equals("")) {
            addViewListener(networkHandler);
            VisitableInformation visitableInformation = new VisitableInformation();
            visitableInformation.setUsername(username);
            visitableInformation.setColor(Color.getColorByName(color));
            notifyViewListener(visitableInformation);

        }
    }

    @FXML
    void onSubmit(MouseEvent event) {
        System.out.println("Cliccando qui parteciperai alla partita");
    }

    public void setNetworkHandler(NetworkHandler networkHandler) {
            this.networkHandler= networkHandler;
    }
}
