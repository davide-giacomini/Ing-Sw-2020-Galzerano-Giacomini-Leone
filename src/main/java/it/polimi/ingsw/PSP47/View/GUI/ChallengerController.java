package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableListOfGods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChallengerController extends ViewObservable {

    private ArrayList<GodName> godNames = new ArrayList<>();
    private int numberOfPlayers;
    private String chosenPlayer = null;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView Apollo;

    @FXML
    private ImageView Athena;

    @FXML
    private ImageView Artemis;

    @FXML
    private ImageView Atlas;

    @FXML
    private ImageView Chronus;

    @FXML
    private ImageView Demeter;

    @FXML
    private ImageView Hephaestus;

    @FXML
    private ImageView Hera;

    @FXML
    private ImageView Hestia;

    @FXML
    private ImageView Minotaur;

    @FXML
    private ImageView Pan;

    @FXML
    private ImageView Prometheus;

    @FXML
    private ImageView Triton;

    @FXML
    private ImageView Zeus;

    @FXML
    private Text godPower;

    @FXML
    private CheckBox first;

    @FXML
    private CheckBox second;

    @FXML
    private CheckBox third;

    @FXML
    private void initialize() {

    }
    @FXML
    void exit(MouseEvent event) {
        godPower.setText("");
        Apollo.setEffect(null);
        Artemis.setEffect(null);
        Athena.setEffect(null);
        Atlas.setEffect(null);
        Chronus.setEffect(null);
        Demeter.setEffect(null);
        Hephaestus.setEffect(null);
        Hera.setEffect(null);
        Hestia.setEffect(null);
        Minotaur.setEffect(null);
        Pan.setEffect(null);
        Prometheus.setEffect(null);
        Triton.setEffect(null);
        Zeus.setEffect(null);
    }


    @FXML
    void onApollo(MouseEvent event) {
        Apollo.setEffect(new DropShadow());
        godPower.setText("Apollo – You may move your worker onto a space occupied by an opponent’s builder. Their builder will be moved to the space your builder was just on.");
    }

    @FXML
    void onArtemis(MouseEvent event) {
        Artemis.setEffect(new DropShadow());
        godPower.setText("Artemis – You may move your builders two spaces but you may not move back to the space you started your turn on.");
    }

    @FXML
    void onAthena(MouseEvent event) {
        Athena.setEffect(new DropShadow());
        godPower.setText("Athena – If you moved one of your workers up one level on your previous turn, your opponent may not move up a level on their turn.");
    }

    @FXML
    void onAtlas(MouseEvent event) {
        Atlas.setEffect(new DropShadow());
        godPower.setText("Atlas – Your builders can build a dome on any level including the ground.");
    }

    @FXML
    void onChronus(MouseEvent event) {
        Chronus.setEffect(new DropShadow());
        godPower.setText("Chronus - You also win when there are at least five complete Towers on the board.");
    }

    @FXML
    void onDemeter(MouseEvent event) {
        Demeter.setEffect(new DropShadow());
        godPower.setText("Demeter – Your builders can build twice on your turn, but may not build twice on the same space.");
    }

    @FXML
    void onHephaestus(MouseEvent event) {
        Hephaestus.setEffect(new DropShadow());
        godPower.setText("Hephaestus – Your builders can build twice on the same space. They may not use this ability to place a dome though.");

    }

    @FXML
    void onHera(MouseEvent event) {
        Hera.setEffect(new DropShadow());
        godPower.setText("Hera - An opponent cannot win by moving on to a perimeter space.");
    }

    @FXML
    void onHestia(MouseEvent event) {
        Hestia.setEffect(new DropShadow());
        godPower.setText("Hestia - Your worker may build one additional time. The additional build cannot be on a perimeter space.");
    }

    @FXML
    void onMinotaur(MouseEvent event) {
        Minotaur.setEffect(new DropShadow());
        godPower.setText("Minotaur – You may move your builder onto a space occupied by an opponent’s builder if the next space in the same direction is unoccupied. You will push the other player’s builder into the next space in the same direction.");
    }

    @FXML
    void onPan(MouseEvent event) {
        Pan.setEffect(new DropShadow());
        godPower.setText("Pan – If one of your builders moves down two spaces in one movement you will automatically win the game.");
    }

    @FXML
    void onPrometheus(MouseEvent event) {
        Prometheus.setEffect(new DropShadow());
        godPower.setText("Prometheus – If you don’t move up a level during your turn you may build before and after you move.");
    }

    @FXML
    void onTriton(MouseEvent event) {
        Triton.setEffect(new DropShadow());
        godPower.setText("Triton - Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again.");
    }

    @FXML
    void onZeus(MouseEvent event) {
        Zeus.setEffect(new DropShadow());
        godPower.setText("Zeus - your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up the 3rd level.");
    }

    @FXML
    void selectApollo(MouseEvent event) {
        if (!godNames.contains(GodName.APOLLO)) {
            godNames.add(GodName.APOLLO);
            Apollo.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectArtemis(MouseEvent event) {
        if (!godNames.contains(GodName.ARTEMIS)) {
            godNames.add(GodName.ARTEMIS);
            Artemis.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectAthena(MouseEvent event) {
        if (!godNames.contains(GodName.ATHENA)) {
            godNames.add(GodName.ATHENA);
            Athena.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectAtlas(MouseEvent event) {
        if (!godNames.contains(GodName.ATLAS)) {
            godNames.add(GodName.ATLAS);
            Atlas.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectChronus(MouseEvent event) {
        if (!godNames.contains(GodName.CHRONUS)) {
            godNames.add(GodName.CHRONUS);
            Chronus.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectDemeter(MouseEvent event) {
        if (!godNames.contains(GodName.DEMETER)) {
            godNames.add(GodName.DEMETER);
            Demeter.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectHephaestus(MouseEvent event) {
        if (!godNames.contains(GodName.HEPHAESTUS)) {
            godNames.add(GodName.HEPHAESTUS);
            Hephaestus.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectHera(MouseEvent event) {
        if (!godNames.contains(GodName.HERA)) {
            godNames.add(GodName.HERA);
            Hera.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectHestia(MouseEvent event) {
        if (!godNames.contains(GodName.HESTIA)) {
            godNames.add(GodName.HESTIA);
            Hestia.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectMinotaur(MouseEvent event) {
        if (!godNames.contains(GodName.MINOTAUR)) {
            godNames.add(GodName.MINOTAUR);
            Minotaur.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectPan(MouseEvent event) {
        if (!godNames.contains(GodName.PAN)) {
            godNames.add(GodName.PAN);
            Pan.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectPrometheus(MouseEvent event) {
        if (!godNames.contains(GodName.PROMETHEUS)) {
            godNames.add(GodName.PROMETHEUS);
            Prometheus.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectTriton(MouseEvent event) {
        if (!godNames.contains(GodName.TRITON)) {
            godNames.add(GodName.TRITON);
            Triton.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void selectZeus(MouseEvent event) {
        if (!godNames.contains(GodName.ZEUS)) {
            godNames.add(GodName.ZEUS);
            Zeus.setDisable(true);
            notifyAndWait();
        }
    }

    @FXML
    void OnTheFirst(ActionEvent event) {
        if (first.isSelected()) {
            second.setSelected(false);
            third.setSelected(false);
            chosenPlayer = first.getText();
        }
        notifyAndWait();
    }

    @FXML
    void OnTheSecond(ActionEvent event) {
        if (second.isSelected()) {
            first.setSelected(false);
            third.setSelected(false);
            chosenPlayer = second.getText();
        }
        notifyAndWait();
    }

    @FXML
    void OnTheThird(ActionEvent event) {
        if (third.isSelected()) {
            second.setSelected(false);
            first.setSelected(false);
            chosenPlayer = third.getText();
        }
        notifyAndWait();
    }

    void setFirstPlayer(String firstPlayer) {
        first.setText(firstPlayer);
    }

    void setSecondPlayer(String secondPlayer) {
        second.setText(secondPlayer);
    }

    void setThirdPlayer(String thirdPlayer) {
        if (thirdPlayer != null)
            third.setText(thirdPlayer);
        else
            third.setVisible(false);
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    private void notifyAndWait() {
        if (godNames.size() == numberOfPlayers && chosenPlayer != null) {
            VisitableListOfGods visitableListOfGods = new VisitableListOfGods();
            visitableListOfGods.setGodNames(godNames);
            visitableListOfGods.setChosenPlayer(chosenPlayer);
            notifyViewListener(visitableListOfGods);
            mainPane.setDisable(true);
        }
    }
}
