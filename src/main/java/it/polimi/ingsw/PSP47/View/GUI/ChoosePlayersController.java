package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChoosePlayersController extends ViewObservable {

    private NetworkHandler networkHandler;

    @FXML
    private Pane mainPane;

    @FXML
    private Text doneText;

    @FXML
    private CheckBox two_players;

    @FXML
    private CheckBox three_players;

    @FXML
    private ImageView doneButton;

    @FXML
    void ClickOnDone(MouseEvent event) {
        VisitableInt visitableInt;
        if (three_players.isSelected()) {
            visitableInt = new VisitableInt(3);
            notifyAndWait(visitableInt);
        }
        else if (two_players.isSelected()) {
            visitableInt = new VisitableInt(2);
            notifyAndWait(visitableInt);
        }
    }

    private void notifyAndWait(VisitableInt visitableInt) {
        notifyViewListener(visitableInt);
        doneText.setDisable(true);
        doneButton.setDisable(true);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource("/FXML/waitingPane.fxml"));
        Parent root;

        try {
            root = loader.load();
            mainPane.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleThreePlayers(ActionEvent event) {
        if (three_players.isSelected())
            two_players.setSelected(false);
    }

    @FXML
    void handleTwoPlayers(ActionEvent event) {
        if (two_players.isSelected())
            three_players.setSelected(false);
    }


}


