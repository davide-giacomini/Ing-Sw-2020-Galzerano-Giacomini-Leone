package it.polimi.ingsw.PSP47.View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class GUI extends Application {

    private static GUI instance = null;

    public static GUI getInstance() {
        if (instance == null)
            instance = new GUI();
        return instance;
    }


    @Override
    public void start(Stage stage) throws Exception {

        instance = getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/startPane.fxml"));

        Parent root;
        Scene scene;

        root = fxmlLoader.load();
        scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Santorini");
        stage.setResizable(false);

        stage.show();
    }

    public static <T> T setLayout(Scene scene, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource(path));

        Parent root;

        root = loader.load();
        scene.setRoot(root);

        return loader.getController();
    }
}
