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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * This class is the controller of the challenger.fxml scene.
 * In this scene are chosen the gods that will be used during the game and the player
 * who will play first during the turn.
 */
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

    /**
     * This method makes empty the text on the bottom of the scene and deletes the shadow effect on every card.
     * @param event the exit of the mouse from a card area.
     */
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

    /**
     * This method sets a shadow Effect on the Apollo Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Apollo card.
     */
    @FXML
    void onApollo(MouseEvent event) {
        Apollo.setEffect(new DropShadow());
        godPower.setText("Apollo – You may move your worker onto a space occupied by an opponent’s builder. Their builder will be moved to the space your builder was just on.");
    }

    /**
     * This method sets a shadow Effect on the Artemis Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Artemis card.
     */
    @FXML
    void onArtemis(MouseEvent event) {
        Artemis.setEffect(new DropShadow());
        godPower.setText("Artemis – You may move your builders two spaces but you may not move back to the space you started your turn on.");
    }

    /**
     * This method sets a shadow Effect on the Athena Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Athena card.
     */
    @FXML
    void onAthena(MouseEvent event) {
        Athena.setEffect(new DropShadow());
        godPower.setText("Athena – If you moved one of your workers up one level on your previous turn, your opponent may not move up a level on their turn.");
    }

    /**
     * This method sets a shadow Effect on the Atlas Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Atlas card.
     */
    @FXML
    void onAtlas(MouseEvent event) {
        Atlas.setEffect(new DropShadow());
        godPower.setText("Atlas – Your builders can build a dome on any level including the ground.");
    }

    /**
     * This method sets a shadow Effect on the Chronus Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Chronus card.
     */
    @FXML
    void onChronus(MouseEvent event) {
        Chronus.setEffect(new DropShadow());
        godPower.setText("Chronus - You also win when there are at least five complete Towers on the board.");
    }

    /**
     * This method sets a shadow Effect on the Demeter Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Demeter card.
     */
    @FXML
    void onDemeter(MouseEvent event) {
        Demeter.setEffect(new DropShadow());
        godPower.setText("Demeter – Your builders can build twice on your turn, but may not build twice on the same space.");
    }

    /**
     * This method sets a shadow Effect on the Hephaestus Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Hephaestus card.
     */
    @FXML
    void onHephaestus(MouseEvent event) {
        Hephaestus.setEffect(new DropShadow());
        godPower.setText("Hephaestus – Your builders can build twice on the same space. They may not use this ability to place a dome though.");

    }

    /**
     * This method sets a shadow Effect on the Hera Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Hera card.
     */
    @FXML
    void onHera(MouseEvent event) {
        Hera.setEffect(new DropShadow());
        godPower.setText("Hera - An opponent cannot win by moving on to a perimeter space.");
    }

    /**
     * This method sets a shadow Effect on the Hestia Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Hestia card.
     */
    @FXML
    void onHestia(MouseEvent event) {
        Hestia.setEffect(new DropShadow());
        godPower.setText("Hestia - Your worker may build one additional time. The additional build cannot be on a perimeter space.");
    }

    /**
     * This method sets a shadow Effect on the Minotaur Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Minotaur card.
     */
    @FXML
    void onMinotaur(MouseEvent event) {
        Minotaur.setEffect(new DropShadow());
        godPower.setText("Minotaur – You may move your builder onto a space occupied by an opponent’s builder if the next space in the same direction is unoccupied. You will push the other player’s builder into the next space in the same direction.");
    }

    /**
     * This method sets a shadow Effect on the Pan Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Pan card.
     */
    @FXML
    void onPan(MouseEvent event) {
        Pan.setEffect(new DropShadow());
        godPower.setText("Pan – If one of your builders moves down two spaces in one movement you will automatically win the game.");
    }

    /**
     * This method sets a shadow Effect on the Prometheus Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Prometheus card.
     */
    @FXML
    void onPrometheus(MouseEvent event) {
        Prometheus.setEffect(new DropShadow());
        godPower.setText("Prometheus – If you don’t move up a level during your turn you may build before and after you move.");
    }

    /**
     * This method sets a shadow Effect on the Triton Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Triton card.
     */
    @FXML
    void onTriton(MouseEvent event) {
        Triton.setEffect(new DropShadow());
        godPower.setText("Triton - Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again.");
    }

    /**
     * This method sets a shadow Effect on the Zeus Card and print his power on the bottom of the scene.
     * @param event the enter of the mouse in the Zeus card.
     */
    @FXML
    void onZeus(MouseEvent event) {
        Zeus.setEffect(new DropShadow());
        godPower.setText("Zeus - your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up the 3rd level.");
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Apollo on the list.
     * @param event the click of the mouse on the Apollo card.
     */
    @FXML
    void selectApollo(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.APOLLO)) {
            godNames.add(GodName.APOLLO);
            Apollo.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Artemis on the list.
     * @param event the click of the mouse on the Artemis card.
     */
    @FXML
    void selectArtemis(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.ARTEMIS)) {
            godNames.add(GodName.ARTEMIS);
            Artemis.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Athena on the list.
     * @param event the click of the mouse on the Athena card.
     */
    @FXML
    void selectAthena(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.ATHENA)) {
            godNames.add(GodName.ATHENA);
            Athena.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Atlas on the list.
     * @param event the click of the mouse on the Atlas card.
     */
    @FXML
    void selectAtlas(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.ATLAS)) {
            godNames.add(GodName.ATLAS);
            Atlas.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Chronus on the list.
     * @param event the click of the mouse on the Chronus card.
     */
    @FXML
    void selectChronus(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.CHRONUS)) {
            godNames.add(GodName.CHRONUS);
            Chronus.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Demeter on the list.
     * @param event the click of the mouse on the Demeter card.
     */
    @FXML
    void selectDemeter(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.DEMETER)) {
            godNames.add(GodName.DEMETER);
            Demeter.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Hephaestus on the list.
     * @param event the click of the mouse on the Hephaestus card.
     */
    @FXML
    void selectHephaestus(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.HEPHAESTUS)) {
            godNames.add(GodName.HEPHAESTUS);
            Hephaestus.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Hera on the list.
     * @param event the click of the mouse on the Hera card.
     */
    @FXML
    void selectHera(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.HERA)) {
            godNames.add(GodName.HERA);
            Hera.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Hestia on the list.
     * @param event the click of the mouse on the Hestia card.
     */
    @FXML
    void selectHestia(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.HESTIA)) {
            godNames.add(GodName.HESTIA);
            Hestia.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Minotaur on the list.
     * @param event the click of the mouse on the Minotaur card.
     */
    @FXML
    void selectMinotaur(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.MINOTAUR)) {
            godNames.add(GodName.MINOTAUR);
            Minotaur.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Pan on the list.
     * @param event the click of the mouse on the Pan card.
     */
    @FXML
    void selectPan(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.PAN)) {
            godNames.add(GodName.PAN);
            Pan.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Prometheus on the list.
     * @param event the click of the mouse on the Prometheus card.
     */
    @FXML
    void selectPrometheus(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.PROMETHEUS)) {
            godNames.add(GodName.PROMETHEUS);
            Prometheus.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Triton on the list.
     * @param event the click of the mouse on the Triton card.
     */
    @FXML
    void selectTriton(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.TRITON)) {
            godNames.add(GodName.TRITON);
            Triton.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method controls if the gods has already been chosen.
     * If they are not, it adds Zeus on the list.
     * @param event the click of the mouse on the Zeus card.
     */
    @FXML
    void selectZeus(MouseEvent event) {
        if (checkIfYetChose())
            return;
        if (!godNames.contains(GodName.ZEUS)) {
            godNames.add(GodName.ZEUS);
            Zeus.setDisable(true);
            notifyAndWait();
        }
    }

    /**
     * This method handles the click on the first player in the checkbox.
     * @param event the click of the mouse in the checkbox.
     */
    @FXML
    void OnTheFirst(ActionEvent event) {
        if (first.isSelected()) {
            second.setSelected(false);
            third.setSelected(false);
            chosenPlayer = first.getText();
        }
        notifyAndWait();
    }

    /**
     * This method handles the click on the second player in the checkbox.
     * @param event the click of the mouse in the checkbox.
     */
    @FXML
    void OnTheSecond(ActionEvent event) {
        if (second.isSelected()) {
            first.setSelected(false);
            third.setSelected(false);
            chosenPlayer = second.getText();
        }
        notifyAndWait();
    }

    /**
     * This method handles the click on the third player in the checkbox.
     * @param event the click of the mouse in the checkbox.
     */
    @FXML
    void OnTheThird(ActionEvent event) {
        if (third.isSelected()) {
            second.setSelected(false);
            first.setSelected(false);
            chosenPlayer = third.getText();
        }
        notifyAndWait();
    }

    /**
     * This method sets the name of the first player.
     * @param firstPlayer the username of the player.
     */
    void setFirstPlayer(String firstPlayer) {
        first.setText(firstPlayer);
    }

    /**
     * This method sets the name of the second player.
     * @param secondPlayer the username of the player.
     */
    void setSecondPlayer(String secondPlayer) {
        second.setText(secondPlayer);
    }

    /**
     * This method sets the name of the first player.
     * If it's null, then the game has only two players, and the checkBox
     * became no more visible.
     * @param thirdPlayer the username of the player.
     */
    void setThirdPlayer(String thirdPlayer) {
        if (thirdPlayer != null)
            third.setText(thirdPlayer);
        else {
            third.setVisible(false);
            // This is graphically useful to eliminate the hole given by the absence of the third player.
            AnchorPane.setRightAnchor(mainPane.getChildren().get(5), 220.0);
            AnchorPane.setLeftAnchor(mainPane.getChildren().get(5), 220.0);
        }
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * This method checks if all of the parameters have been chosen.
     * If they have been, it notifies the networkHandler; otherwise, it does nothing.
     */
    private void notifyAndWait() {
        if (godNames.size() == numberOfPlayers && chosenPlayer != null) {
            VisitableListOfGods visitableListOfGods = new VisitableListOfGods();
            visitableListOfGods.setGodNames(godNames);
            visitableListOfGods.setChosenPlayer(chosenPlayer);
            notifyViewListener(visitableListOfGods);
            mainPane.setDisable(true);
        }
    }

    /**
     * This method checks if have already been chosen enough cards.
     * If they are, the text on the bottom of the scene is set with an advice.
     * @return true if the gods have already been chosen, false otherwise.
     */
    private boolean checkIfYetChose() {
        if (godNames.size() == numberOfPlayers) {
            godPower.setText("You have already chose enough cards, you can't select another one");
            return true;
        }
        return false;
    }
}
