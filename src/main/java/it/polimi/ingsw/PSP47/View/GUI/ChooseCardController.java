package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableGod;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * This class is the controller of the chooseCard.fxml scene.
 * In this scene the player can choose the god's power that will use during the game.
 */
public class ChooseCardController extends ViewObservable {

    private ArrayList<GodName> availableGods = new ArrayList<>();

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
     * This method sets a shadow Effect on the Apollo Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Apollo card.
     */
    @FXML
    void onApollo(MouseEvent event) {
        if (availableGods.contains(GodName.APOLLO)) {
            Apollo.setEffect(new DropShadow());
            godPower.setText("Apollo – You may move your worker onto a space occupied by an opponent’s builder. Their builder will be moved to the space your builder was just on.");
        }
        else
            Apollo.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Artemis Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Artemis card.
     */
    @FXML
    void onArtemis(MouseEvent event) {
        if (availableGods.contains(GodName.ARTEMIS)) {
            Artemis.setEffect(new DropShadow());
            godPower.setText("Artemis – You may move your builders two spaces but you may not move back to the space you started your turn on.");
        }
        else
            Artemis.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Athena Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Athena card.
     */
    @FXML
    void onAthena(MouseEvent event) {
        if (availableGods.contains(GodName.ATHENA)) {
            Athena.setEffect(new DropShadow());
            godPower.setText("Athena – If you moved one of your workers up one level on your previous turn, your opponent may not move up a level on their turn.");
        }
        else
            Athena.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Athena Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Athena card.
     */
    @FXML
    void onAtlas(MouseEvent event) {
        if (availableGods.contains(GodName.ATLAS)) {
            Atlas.setEffect(new DropShadow());
            godPower.setText("Atlas – Your builders can build a dome on any level including the ground.");
        }
        else
            Atlas.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Chronus Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Chronus card.
     */
    @FXML
    void onChronus(MouseEvent event) {
        if (availableGods.contains(GodName.CHRONUS)) {
            Chronus.setEffect(new DropShadow());
            godPower.setText("Chronus - You also win when there are at least five complete Towers on the board.");
        }
        else
            Chronus.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Demeter Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Demeter card.
     */
    @FXML
    void onDemeter(MouseEvent event) {
        if (availableGods.contains(GodName.DEMETER)) {
            Demeter.setEffect(new DropShadow());
            godPower.setText("Demeter – Your builders can build twice on your turn, but may not build twice on the same space.");
        }
        else
            Demeter.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Hephaestus Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Hephaestus card.
     */
    @FXML
    void onHephaestus(MouseEvent event) {
        if (availableGods.contains(GodName.HEPHAESTUS)) {
            Hephaestus.setEffect(new DropShadow());
            godPower.setText("Hephaestus – Your builders can build twice on the same space. They may not use this ability to place a dome though.");
        }
        else
            Hephaestus.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Hera Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Hera card.
     */
    @FXML
    void onHera(MouseEvent event) {
        if (availableGods.contains(GodName.HERA)) {
            Hera.setEffect(new DropShadow());
            godPower.setText("Hera - An opponent cannot win by moving on to a perimeter space.");
        }
        else
            Hera.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Hestia Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Hestia card.
     */
    @FXML
    void onHestia(MouseEvent event) {
        if (availableGods.contains(GodName.HESTIA)) {
            Hestia.setEffect(new DropShadow());
            godPower.setText("Hestia - Your worker may build one additional time. The additional build cannot be on a perimeter space.");
        }
        else
            Hestia.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Minotaur Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Minotaur card.
     */
    @FXML
    void onMinotaur(MouseEvent event) {
        if (availableGods.contains(GodName.MINOTAUR)) {
            Minotaur.setEffect(new DropShadow());
            godPower.setText("Minotaur – You may move your builder onto a space occupied by an opponent’s builder if the next space in the same direction is unoccupied. You will push the other player’s builder into the next space in the same direction.");
        }
        else
            Minotaur.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Pan Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Pan card.
     */
    @FXML
    void onPan(MouseEvent event) {
        if (availableGods.contains(GodName.PAN)) {
            Pan.setEffect(new DropShadow());
            godPower.setText("Pan – If one of your builders moves down two spaces in one movement you will automatically win the game.");
        }
        else
            Pan.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Prometheus Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Prometheus card.
     */
    @FXML
    void onPrometheus(MouseEvent event) {
        if (availableGods.contains(GodName.PROMETHEUS)) {
            Prometheus.setEffect(new DropShadow());
            godPower.setText("Prometheus – If you don’t move up a level during your turn you may build before and after you move.");
        }
        else
            Prometheus.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Triton Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Triton card.
     */
    @FXML
    void onTriton(MouseEvent event) {
        if (availableGods.contains(GodName.TRITON)) {
            Triton.setEffect(new DropShadow());
            godPower.setText("Triton - Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again.");
        }
        else
            Triton.setEffect(new Shadow());
    }

    /**
     * This method sets a shadow Effect on the Zeus Card and print his power on the bottom of the scene if this card is
     * available during the game. If it's not, the card is obscured.
     * @param event the enter of the mouse in the Zeus card.
     */
    @FXML
    void onZeus(MouseEvent event) {
        if (availableGods.contains(GodName.ZEUS)) {
            Zeus.setEffect(new DropShadow());
            godPower.setText("Zeus - your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up the 3rd level.");
        }
        else
            Zeus.setEffect(new Shadow());
    }

    /**
     * This method notifies the networkHandler that Apollo is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Apollo card.
     */
    @FXML
    void selectApollo(MouseEvent event) {
        if (availableGods.contains(GodName.APOLLO)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.APOLLO);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Artemis is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Artemis card.
     */
    @FXML
    void selectArtemis(MouseEvent event) {
        if (availableGods.contains(GodName.ARTEMIS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ARTEMIS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Athena is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Athena card.
     */
    @FXML
    void selectAthena(MouseEvent event) {
        if (availableGods.contains(GodName.ATHENA)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ATHENA);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Atlas is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Atlas card.
     */
    @FXML
    void selectAtlas(MouseEvent event) {
        if (availableGods.contains(GodName.ATLAS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ATLAS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Chronus is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Chronus card.
     */
    @FXML
    void selectChronus(MouseEvent event) {
        if (availableGods.contains(GodName.CHRONUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.CHRONUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Demeter is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Demeter card.
     */
    @FXML
    void selectDemeter(MouseEvent event) {
        if (availableGods.contains(GodName.DEMETER)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.DEMETER);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Hephaestus is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Hephaestus card.
     */
    @FXML
    void selectHephaestus(MouseEvent event) {
        if (availableGods.contains(GodName.HEPHAESTUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.HEPHAESTUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Hera is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Hera card.
     */
    @FXML
    void selectHera(MouseEvent event) {
        if (availableGods.contains(GodName.HERA)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.HERA);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Hestia is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Hestia card.
     */
    @FXML
    void selectHestia(MouseEvent event) {
        if (availableGods.contains(GodName.HESTIA)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.HESTIA);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Minotaur is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Minotaur card.
     */
    @FXML
    void selectMinotaur(MouseEvent event) {
        if (availableGods.contains(GodName.MINOTAUR)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.MINOTAUR);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Pan is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Pan card.
     */
    @FXML
    void selectPan(MouseEvent event) {
        if (availableGods.contains(GodName.PAN)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.PAN);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Prometheus is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Prometheus card.
     */
    @FXML
    void selectPrometheus(MouseEvent event) {
        if (availableGods.contains(GodName.PROMETHEUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.PROMETHEUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Triton is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Triton card.
     */
    @FXML
    void selectTriton(MouseEvent event) {
        if (availableGods.contains(GodName.TRITON)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.TRITON);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    /**
     * This method notifies the networkHandler that Zeus is the card chosen by the player.
     * Then the whole scene is disabled.
     * @param event the click of the mouse on the Zeus card.
     */
    @FXML
    void selectZeus(MouseEvent event) {
        if (availableGods.contains(GodName.ZEUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ZEUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    void setAvailableGods(ArrayList<GodName> availableGods) {
        this.availableGods = availableGods;
    }

    /**
     * This method disables the whole scene.
     */
    private void disableAll() {
        mainPane.setDisable(true);
    }

}
