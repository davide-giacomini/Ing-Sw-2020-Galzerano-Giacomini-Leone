package it.polimi.ingsw.PSP47.View.GUI;

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

/**
 * This class is the controller of the choosePlayers.fxml scene.
 * In this scene the player can choose how many players there will be in the game.
 */
public class ChoosePlayersController extends ViewObservable {

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

    /**
     * This method notifies the controller with the number of player chosen by the user.
     * @param event the click of the mouse on the Done button.
     */
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

    /**
     * This method loads a new scene that will be visible until the other players connect.
     * @param visitableInt the number of players chosen by the user.
     */
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

    /**
     * This method handles with the choose of a three players game.
     * @param event the click on the three players checkBox.
     */
    @FXML
    void handleThreePlayers(ActionEvent event) {
        if (three_players.isSelected())
            two_players.setSelected(false);
    }

    /**
     * This method handles with the choose of a two players game.
     * @param event the click on the two players checkBox.
     */
    @FXML
    void handleTwoPlayers(ActionEvent event) {
        if (two_players.isSelected())
            three_players.setSelected(false);
    }

}


