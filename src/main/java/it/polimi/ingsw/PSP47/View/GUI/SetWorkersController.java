package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInitialPositions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;


public class SetWorkersController extends ViewObservable {

    private int[] newRowAndColumn = new int[4];
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList <GodName> gods = new ArrayList<>();

    @FXML
    private Label publicInformation;

    @FXML
    private void initialize() {
        for (int i = 0; i < 4; i++) {
            newRowAndColumn[i] = -1;
        }
    }

    @FXML
    private void showPublicInformation() {
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i<usernames.size(); i++) {
            textBuilder.append(usernames.get(i)).append(" ").append(colors.get(i)).append(" ").append(gods.get(i)).append("\n");
        }
        String text = textBuilder.toString();
        text = "About players: \n" + text.toLowerCase();
        publicInformation.setText(text);

    }

    @FXML
    void ClickOn11(ActionEvent event) {
        selectSlotAndNotify(1,1);
    }

    @FXML
    void ClickOn12(ActionEvent event) {
        selectSlotAndNotify(1,2);
    }

    @FXML
    void ClickOn13(ActionEvent event) {
        selectSlotAndNotify(1,3);
    }

    @FXML
    void ClickOn14(ActionEvent event) {
        selectSlotAndNotify(1,4);
    }

    @FXML
    void ClickOn15(ActionEvent event) {
        selectSlotAndNotify(1,5);
    }

    @FXML
    void ClickOn21(ActionEvent event) {
        selectSlotAndNotify(2,1);
    }

    @FXML
    void ClickOn22(ActionEvent event) {
        selectSlotAndNotify(2,2);
    }

    @FXML
    void ClickOn23(ActionEvent event) {
        selectSlotAndNotify(2,3);
    }

    @FXML
    void ClickOn24(ActionEvent event) {
        selectSlotAndNotify(2,4);
    }

    @FXML
    void ClickOn25(ActionEvent event) {
        selectSlotAndNotify(2,5);
    }

    @FXML
    void ClickOn31(ActionEvent event) {
        selectSlotAndNotify(3,1);
    }

    @FXML
    void ClickOn32(ActionEvent event) {
        selectSlotAndNotify(3,2);
    }

    @FXML
    void ClickOn33(ActionEvent event) {
        selectSlotAndNotify(3,3);
    }

    @FXML
    void ClickOn34(ActionEvent event) {
        selectSlotAndNotify(3,4);
    }

    @FXML
    void ClickOn35(ActionEvent event) {
        selectSlotAndNotify(3,5);
    }

    @FXML
    void ClickOn41(ActionEvent event) {
        selectSlotAndNotify(4,1);
    }

    @FXML
    void ClickOn42(ActionEvent event) {
        selectSlotAndNotify(4,2);
    }

    @FXML
    void ClickOn43(ActionEvent event) {
        selectSlotAndNotify(4,3);
    }

    @FXML
    void ClickOn44(ActionEvent event) {
        selectSlotAndNotify(4,4);
    }

    @FXML
    void ClickOn45(ActionEvent event) {
        selectSlotAndNotify(4,5);
    }

    @FXML
    void ClickOn51(ActionEvent event) {
        selectSlotAndNotify(5,1);
    }

    @FXML
    void ClickOn52(ActionEvent event) {
        selectSlotAndNotify(5,2);
    }

    @FXML
    void ClickOn53(ActionEvent event) {
        selectSlotAndNotify(5,3);
    }

    @FXML
    void ClickOn54(ActionEvent event) {
        selectSlotAndNotify(5,5);
    }

    @FXML
    void ClickOn55(ActionEvent event) {
        selectSlotAndNotify(5,5);
    }

    private void selectSlotAndNotify(int row, int column) {
        int i;
        for (i = 0; i < 4; i++) {
            if ( newRowAndColumn[i] == -1 ) {
                newRowAndColumn[i] = row - 1;
                newRowAndColumn[i + 1] = column - 1;
                break;
            }
        }
        if (i == 2) {
            VisitableInitialPositions visitableInitialPositions = new VisitableInitialPositions();
            visitableInitialPositions.setRowsAndColumns(newRowAndColumn);
            notifyViewListener(visitableInitialPositions);
            //disabilitare i 25 slots ? (oppure trovare alternativa)
        }
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public void setGods(ArrayList<GodName> gods) {
        this.gods = gods;
    }

}
