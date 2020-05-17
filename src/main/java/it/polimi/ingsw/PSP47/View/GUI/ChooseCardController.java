package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableGod;
import it.polimi.ingsw.PSP47.Visitor.VisitableListOfGods;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class ChooseCardController extends ViewObservable {

    ArrayList<GodName> availableGods = new ArrayList<>();
    GodName chosenGod;

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
        if (availableGods.contains(GodName.APOLLO)) {
            Apollo.setEffect(new DropShadow());
            godPower.setText("Apollo – You may move your worker onto a space occupied by an opponent’s builder. Their builder will be moved to the space your builder was just on.");
        }
        else
            Apollo.setEffect(new Shadow());
    }

    @FXML
    void onArtemis(MouseEvent event) {
        if (availableGods.contains(GodName.ARTEMIS)) {
            Artemis.setEffect(new DropShadow());
            godPower.setText("Artemis – You may move your builders two spaces but you may not move back to the space you started your turn on.");
        }
        else
            Artemis.setEffect(new Shadow());
    }

    @FXML
    void onAthena(MouseEvent event) {
        if (availableGods.contains(GodName.ATHENA)) {
            Athena.setEffect(new DropShadow());
            godPower.setText("Athena – If you moved one of your workers up one level on your previous turn, your opponent may not move up a level on their turn.");
        }
        else
            Athena.setEffect(new Shadow());
    }

    @FXML
    void onAtlas(MouseEvent event) {
        if (availableGods.contains(GodName.ATLAS)) {
            Atlas.setEffect(new DropShadow());
            godPower.setText("Atlas – Your builders can build a dome on any level including the ground.");
        }
        else
            Atlas.setEffect(new Shadow());
    }

    @FXML
    void onChronus(MouseEvent event) {
        if (availableGods.contains(GodName.CHRONUS)) {
            Chronus.setEffect(new DropShadow());
            godPower.setText("Chronus - You also win when there are at least five complete Towers on the board.");
        }
        else
            Chronus.setEffect(new Shadow());
    }

    @FXML
    void onDemeter(MouseEvent event) {
        if (availableGods.contains(GodName.DEMETER)) {
            Demeter.setEffect(new DropShadow());
            godPower.setText("Demeter – Your builders can build twice on your turn, but may not build twice on the same space.");
        }
        else
            Demeter.setEffect(new Shadow());
    }

    @FXML
    void onHephaestus(MouseEvent event) {
        if (availableGods.contains(GodName.HEPHAESTUS)) {
            Hephaestus.setEffect(new DropShadow());
            godPower.setText("Hephaestus – Your builders can build twice on the same space. They may not use this ability to place a dome though.");
        }
        else
            Hephaestus.setEffect(new Shadow());
    }

    @FXML
    void onHera(MouseEvent event) {
        if (availableGods.contains(GodName.HERA)) {
            Hera.setEffect(new DropShadow());
            godPower.setText("Hera - An opponent cannot win by moving on to a perimeter space.");
        }
        else
            Hera.setEffect(new Shadow());
    }

    @FXML
    void onHestia(MouseEvent event) {
        if (availableGods.contains(GodName.HESTIA)) {
            Hestia.setEffect(new DropShadow());
            godPower.setText("Hestia - Your worker may build one additional time. The additional build cannot be on a perimeter space.");
        }
        else
            Hestia.setEffect(new Shadow());
    }

    @FXML
    void onMinotaur(MouseEvent event) {
        if (availableGods.contains(GodName.MINOTAUR)) {
            Minotaur.setEffect(new DropShadow());
            godPower.setText("Minotaur – You may move your builder onto a space occupied by an opponent’s builder if the next space in the same direction is unoccupied. You will push the other player’s builder into the next space in the same direction.");
        }
        else
            Minotaur.setEffect(new Shadow());
    }

    @FXML
    void onPan(MouseEvent event) {
        if (availableGods.contains(GodName.PAN)) {
            Pan.setEffect(new DropShadow());
            godPower.setText("Pan – If one of your builders moves down two spaces in one movement you will automatically win the game.");
        }
        else
            Pan.setEffect(new Shadow());
    }

    @FXML
    void onPrometheus(MouseEvent event) {
        if (availableGods.contains(GodName.PROMETHEUS)) {
            Prometheus.setEffect(new DropShadow());
            godPower.setText("Prometheus – If you don’t move up a level during your turn you may build before and after you move.");
        }
        else
            Prometheus.setEffect(new Shadow());
    }

    @FXML
    void onTriton(MouseEvent event) {
        if (availableGods.contains(GodName.TRITON)) {
            Triton.setEffect(new DropShadow());
            godPower.setText("Triton - Each time your Worker moves onto a perimeter space (ground or block), it may immediately move again.");
        }
        else
            Triton.setEffect(new Shadow());
    }

    @FXML
    void onZeus(MouseEvent event) {
        if (availableGods.contains(GodName.ZEUS)) {
            Zeus.setEffect(new DropShadow());
            godPower.setText("Zeus - your Worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up the 3rd level.");
        }
        else
            Zeus.setEffect(new Shadow());
    }

    @FXML
    void selectApollo(MouseEvent event) {
        if (availableGods.contains(GodName.APOLLO)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.APOLLO);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectArtemis(MouseEvent event) {
        if (availableGods.contains(GodName.ARTEMIS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ARTEMIS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectAthena(MouseEvent event) {
        if (availableGods.contains(GodName.ATHENA)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ATHENA);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectAtlas(MouseEvent event) {
        if (availableGods.contains(GodName.ATLAS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.ATLAS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectChronus(MouseEvent event) {
        if (availableGods.contains(GodName.CHRONUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.CHRONUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectDemeter(MouseEvent event) {
        if (availableGods.contains(GodName.DEMETER)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.DEMETER);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectHephaestus(MouseEvent event) {
        if (availableGods.contains(GodName.HEPHAESTUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.HEPHAESTUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectHera(MouseEvent event) {
        if (availableGods.contains(GodName.HERA)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.HERA);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectHestia(MouseEvent event) {
        if (availableGods.contains(GodName.HESTIA)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.HESTIA);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectMinotaur(MouseEvent event) {
        if (availableGods.contains(GodName.MINOTAUR)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.MINOTAUR);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectPan(MouseEvent event) {
        if (availableGods.contains(GodName.PAN)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.PAN);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectPrometheus(MouseEvent event) {
        if (availableGods.contains(GodName.PROMETHEUS)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.PROMETHEUS);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

    @FXML
    void selectTriton(MouseEvent event) {
        if (availableGods.contains(GodName.TRITON)) {
            VisitableGod visitableGod = new VisitableGod();
            visitableGod.setGodName(GodName.TRITON);
            notifyViewListener(visitableGod);
            disableAll();
        }
    }

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

    void disableAll() {
        Apollo.setDisable(true);
        Artemis.setDisable(true);
        Athena.setDisable(true);
        Atlas.setDisable(true);
        Chronus.setDisable(true);
        Demeter.setDisable(true);
        Hephaestus.setDisable(true);
        Hera.setDisable(true);
        Hestia.setDisable(true);
        Minotaur.setDisable(true);
        Pan.setDisable(true);
        Prometheus.setDisable(true);
        Triton.setDisable(true);
        Zeus.setDisable(true);
    }

}
