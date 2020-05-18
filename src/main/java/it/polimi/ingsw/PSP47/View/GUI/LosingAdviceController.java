package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.View.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LosingAdviceController extends ViewObservable {
    private final double BUTTON_OPACITY = 1.0;
    
    @FXML
    private ImageView imageButtonPlayAgain;
    
    @FXML
    private ImageView imageButtonQuit;
    
    @FXML
    void mouseEntered(MouseEvent event) {
        ImageView target =(ImageView) event.getTarget();
        target.setOpacity(BUTTON_OPACITY/2);
    }
    
    @FXML
    void mouseExited(MouseEvent event){
        ImageView target = (ImageView) event.getTarget();
        target.setOpacity(BUTTON_OPACITY);
    }
    
    @FXML
    void playAgain(MouseEvent event) {
        //TODO implementare il ricominciare del gioco
    }
    
    @FXML
    void quit(MouseEvent event) {
        notifyEndConnection();
    }
}
