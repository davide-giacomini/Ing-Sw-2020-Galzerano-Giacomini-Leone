package it.polimi.ingsw.PSP47.View.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class JavaFXTest extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Hello world");
        label.setAlignment(Pos.CENTER);
    
        AnchorPane.setBottomAnchor(label, 30.0);
        AnchorPane.setTopAnchor(label, 30.0);
        AnchorPane.setLeftAnchor(label, 30.0);
        AnchorPane.setRightAnchor(label, 30.0);
        AnchorPane anchorPane = new AnchorPane(label);
    
        Scene scene = new Scene(anchorPane);
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
