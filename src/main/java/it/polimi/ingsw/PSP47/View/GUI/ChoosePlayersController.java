package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.View.CLI.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class ChoosePlayersController extends ViewObservable {

    @FXML
    private CheckBox two_players;

    @FXML
    private CheckBox three_players;

    @FXML
    void ClickOnDone(MouseEvent event) {
        VisitableInt visitableInt;
        if (three_players.isSelected()) {
            visitableInt = new VisitableInt(3);
            notifyViewListener(visitableInt);
        }
        else if (two_players.isSelected()) {
            visitableInt = new VisitableInt(2);
            notifyViewListener(visitableInt);
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


