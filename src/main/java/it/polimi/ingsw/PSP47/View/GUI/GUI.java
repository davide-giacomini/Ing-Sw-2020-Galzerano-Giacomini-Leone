package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.CurrentScene;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.GameView;
import it.polimi.ingsw.PSP47.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application implements View {

    private NetworkHandler networkHandler;
    private GameView gameView;

    private CurrentScene currentScene;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private ConnectionToServerController connectionToServerController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameView = new GameView();

        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/connectionToServer.fxml"));
        Font.loadFont(getClass().getResourceAsStream("Fonts/savoye.ttf"), 14);
        root = fxmlLoader.load();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/background.css").toExternalForm());
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santorini");
        primaryStage.show();

        currentScene = CurrentScene.START;

        connectionToServerController = fxmlLoader.getController();
        connectionToServerController.setGui(this);
    }
    
    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
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


    @Override
    public void askFirstConnection() {
        currentScene = CurrentScene.START;
        StartController startController = setLayout(scene, "/FXML/startPane.fxml");
        startController.addViewListener(networkHandler);
    }

    @Override
    public void askNumberOfPlayers() {
        currentScene = CurrentScene.CHOOSE_PLAYERS;
        ChoosePlayersController choosePlayersController = setLayout(scene,"/FXML/choosePlayers.fxml");
        choosePlayersController.addViewListener(networkHandler);
    }


    @Override
    public void askWhichWorkerToUse() {

    }

    @Override
    public void askWhereToPositionWorkers() {
        currentScene = CurrentScene.SET_WORKERS;
        primaryStage.setHeight(700);
        SetWorkersController controller = setLayout(scene, "/FXML/setWorkers.fxml");
        controller.addViewListener(networkHandler);
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
        chooseCardsController.addViewListener(networkHandler);
        chooseCardsController.setNumberOfPlayers(gameView.getNumberOfPlayers());
    }

    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen) {
        currentScene = CurrentScene.CHOOSE_CARD;
        primaryStage.setWidth(1100);
        primaryStage.setHeight(800);
        ChooseCardController chooseCardController = setLayout(scene, "/FXML/chooseCard.fxml");
        chooseCardController.addViewListener(networkHandler);
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
        currentScene = CurrentScene.WIN;
        primaryStage.setWidth(1100);
        primaryStage.setHeight(800);
        WinningAdviceController winningAdviceController = setLayout(scene, "/FXML/winningAdvice.fxml");
        winningAdviceController.addViewListener(networkHandler);
    }

    @Override
    public void theLoserIs() {
        currentScene = CurrentScene.LOSE;
        primaryStage.setWidth(1100);
        primaryStage.setHeight(800);
        LosingAdviceController losingAdviceController = setLayout(scene, "/FXML/losingAdvice.fxml");
        losingAdviceController.addViewListener(networkHandler);
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
}
