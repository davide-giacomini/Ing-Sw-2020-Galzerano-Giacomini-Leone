package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.View.CLI.GameView;
import it.polimi.ingsw.PSP47.View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class GUI extends Application implements View {

    public NetworkHandler networkHandler;

    private static GUI instance = null;

    private Scene scene;
    private Parent root;

    private StartController startController;

    public static GUI getInstance() {
        if (instance == null)
            instance = new GUI();
        return instance;
    }


    @Override
    public void start(Stage stage) throws Exception {

        instance = getInstance();

        List<String> args = getParameters().getRaw();

        setConnection(args.get(0));


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/startPane.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Santorini");
        stage.setResizable(false);
        stage.show();

        startController = fxmlLoader.getController();
        startController.setNetworkHandler(networkHandler);

    }

    private <T> T setLayout(Scene scene, String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GUI.class.getResource(path));

        Parent root;

        try {
            root = loader.load();
            scene.setRoot(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader.getController();
    }

    //TODO vedere se va bene inserirlo nella gui
    @Override
    public void setConnection(String address) {

        // open a connection with the server
        Socket serverSocket;
        try {
            serverSocket = new Socket(address, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("Server unreachable.");
            return;
        }
        System.out.println("Connected to the address " + serverSocket.getInetAddress());

        networkHandler = new NetworkHandler(this, serverSocket);

        Thread thread = new Thread(networkHandler);
        thread.start();

    }


    @Override
    public void showTitle() {

    }

    @Override
    public void askFirstConnection() {
        startController.ableButton();
    }

    @Override
    public String askUsername() {
        return null;
    }

    @Override
    public Color askColorWorkers() {
        return null;
    }

    @Override
    public void askNumberOfPlayers() {
        setLayout(scene,"/FXML/choosePlayers.fxml");
    }


    @Override
    public void askWhichWorkerToUse() {

    }

    @Override
    public void askWhereToPositionWorkers() {

    }

    @Override
    public void challengerWillChooseThreeGods() {

    }

    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen) {

    }

    @Override
    public void askAction() {

    }

    @Override
    public GameView getGameView() {
        return null;
    }

    @Override
    public void showPublicInformation() {

    }

    @Override
    public void showCurrentBoard() {

    }

    @Override
    public void showErrorMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(text);
        alert.show();
    }

    @Override
    public void showImportantMessage(String text) {

    }

    @Override
    public void theWinnerIs(String usernameWinner) {

    }

    @Override
    public void theLoserIs(String usernameLoser) {

    }


}
