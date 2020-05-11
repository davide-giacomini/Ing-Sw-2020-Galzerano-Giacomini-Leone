package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.CLI.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class StartController extends ViewObservable {

    private NetworkHandler networkHandler;

    private ObservableList<Color> colors = FXCollections.observableArrayList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.PURPLE, Color.CYAN, Color.WHITE);

    @FXML
    private Pane mainPane;

    @FXML
    private TextField player_name;

    @FXML
    private ChoiceBox<Color> colorBox;

    @FXML
    private ImageView joinButton;

    @FXML
    private void initialize() {
        colorBox.setItems(colors);
    }


    public void setNetworkHandler(NetworkHandler networkHandler) {
            this.networkHandler= networkHandler;
    }

    @FXML
    void OnSubmitClick(MouseEvent event) {
        String username;

        username = player_name.getText();

        if(!username.equals("") && colorBox.getValue() != null) {
            addViewListener(networkHandler);
            VisitableInformation visitableInformation = new VisitableInformation();
            visitableInformation.setUsername(username);
            visitableInformation.setColor(colorBox.getValue());
            notifyViewListener(visitableInformation);
            joinButton.isDisabled();
        }
    }

    void ableButton() {
        joinButton.setDisable(false);
    }

}
