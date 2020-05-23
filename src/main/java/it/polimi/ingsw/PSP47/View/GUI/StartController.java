package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * This class is the controller of the startPane.fxml scene.
 * In this scene the player can choose his username and color.
 */
public class StartController extends ViewObservable {

    private ObservableList<Color> colors = FXCollections.observableArrayList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.PURPLE, Color.CYAN, Color.WHITE);

    @FXML
    private Text joinText;

    @FXML
    private TextField player_name;

    @FXML
    private ChoiceBox<Color> colorBox;

    @FXML
    private ImageView joinButton;

    /**
     * This method sets the available colors that can be chosen.
     */
    @FXML
    private void initialize() {
        colorBox.setItems(colors);
    }

    /**
     * This method notify the networkHandler about username and color chosen by the player,
     * only if both of them have been set.
     * @param event the click on the Join button.
     */
    @FXML
    void OnJoinClick(MouseEvent event) {
        String username;

        username = player_name.getText();

        if(!username.equals("") && colorBox.getValue() != null) {
            VisitableInformation visitableInformation = new VisitableInformation();
            visitableInformation.setUsername(username);
            visitableInformation.setColor(colorBox.getValue());
            notifyViewListener(visitableInformation);
            joinButton.setDisable(true);
            joinText.setDisable(true);
        }
    }

}
