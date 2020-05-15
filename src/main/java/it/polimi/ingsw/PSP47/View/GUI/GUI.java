package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.CurrentScene;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.View.GameView;
import it.polimi.ingsw.PSP47.View.View;
import javafx.application.Application;
import javafx.application.Platform;
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

    private NetworkHandler networkHandler;
    private GameView gameView;

    private CurrentScene currentScene;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private StartController startController;

    @Override
    public void start(Stage primaryStage) throws Exception {


        List<String> args = getParameters().getRaw();
        setConnection(args.get(0));

        gameView = new GameView();

        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/startPane.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santorini");
        primaryStage.show();

        currentScene = CurrentScene.START;

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
    public void askFirstConnection() {
        currentScene = CurrentScene.START;
        startController = setLayout(scene, "/FXML/startPane.fxml");
        startController.setNetworkHandler(networkHandler);
    }

    @Override
    public void askNumberOfPlayers() {
        currentScene = CurrentScene.CHOOSE_PLAYERS;
        ChoosePlayersController choosePlayersController = setLayout(scene,"/FXML/choosePlayers.fxml");
        choosePlayersController.setNetworkHandler(networkHandler);
    }


    @Override
    public void askWhichWorkerToUse() {

    }

    @Override
    public void askWhereToPositionWorkers() {
        currentScene = CurrentScene.SET_WORKERS;
        primaryStage.setHeight(700);
        SetWorkersController controller = setLayout(scene, "/FXML/setWorkers.fxml");
        controller.setNetworkHandler(networkHandler);
        controller.setUsernames(gameView.getUsernames());
        controller.setColors(gameView.getColors());
        controller.setGods(gameView.getGods());
    }

    @Override
    public void challengerWillChooseThreeGods() {
        currentScene = CurrentScene.CHOOSE_CARDS;
        primaryStage.setWidth(1100);
        primaryStage.setHeight(800);
        ChooseCardsController chooseCardsController = setLayout(scene, "/FXML/chooseCards.fxml");
        chooseCardsController.setNetworkHandler(networkHandler);
        chooseCardsController.setNumberOfPlayers(gameView.getNumberOfPlayers());
    }

    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen) {
        currentScene = CurrentScene.CHOOSE_CARD;
        primaryStage.setWidth(1100);
        primaryStage.setHeight(800);
        ChooseCardController chooseCardController = setLayout(scene, "/FXML/chooseCard.fxml");
        chooseCardController.setNetworkHandler(networkHandler);
        chooseCardController.setAvailableGods(godsChosen);
    }

    @Override
    public void askAction() {

    }

    @Override
    public GameView getGameView() {
        return gameView;
    }

    @Override
    public void showPublicInformation() {

    }

    @Override
    public void showCurrentBoard() {

    }

    @Override
    public void showErrorMessage(String text) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(text);
            a.show();
        });
    }

    @Override
    public void showImportantMessage(String text) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(text);
            a.show();
        });
    }

    @Override
    public void theWinnerIs(String usernameWinner) {

    }

    @Override
    public void theLoserIs() {

    }

    @Override
    public void othersTurn(String usernameOnTurn) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("It's " + usernameOnTurn + "'s turn. You can't do anything until it's your turn.");
            a.show();
        });
    }

    @Override
    public void showEnd() {

    }

    private String choosePath() {
        String path = "";
        switch (currentScene) {
            case START:
                path = "/FXML/startPane.fxml";
                break;
            case CHOOSE_PLAYERS:
                path = "/FXML/choosePlayers.fxml";
                break;
            case CHOOSE_CARDS:
                path = "/FXML/chooseCards.fxml";
                break;
            case CHOOSE_CARD:
                path = "/FXML/chooseCard.fxml";
                break;
            case SET_WORKERS:
                path = "/FXML/setWorkers.fxml";
                break;
        }
        return path;
    }

}
