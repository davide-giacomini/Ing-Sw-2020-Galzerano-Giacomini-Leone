package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.CurrentScene;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.Network.Server.Server;
import it.polimi.ingsw.PSP47.View.GameView;
import it.polimi.ingsw.PSP47.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    private MainController mainController;

    private boolean start = false;

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
        //startController= fxmlLoader.getController();

        startController = fxmlLoader.getController();
        startController.setNetworkHandler(networkHandler);

        primaryStage.setOnCloseRequest(new EventHandler<>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                networkHandler.endConnection();
            }
        });

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
        Platform.runLater(() -> {
            currentScene = CurrentScene.START;
            startController = setLayout(scene, "/FXML/startPane.fxml");
            startController.setNetworkHandler(networkHandler);
        });
    }

    @Override
    public void askNumberOfPlayers() {
        Platform.runLater(() -> {
        currentScene = CurrentScene.CHOOSE_PLAYERS;
        ChoosePlayersController choosePlayersController = setLayout(scene,"/FXML/choosePlayers.fxml");
        choosePlayersController.setNetworkHandler(networkHandler);
        });
    }

    @Override
    public void challengerWillChooseThreeGods() {
        Platform.runLater(() -> {
            currentScene = CurrentScene.CHOOSE_CARDS;
            primaryStage.setWidth(1100);
            primaryStage.setHeight(800);
            ChooseCardsController chooseCardsController = setLayout(scene, "/FXML/chooseCards.fxml");
            chooseCardsController.setNetworkHandler(networkHandler);
            chooseCardsController.setNumberOfPlayers(gameView.getNumberOfPlayers());
        });
    }

    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen) {
        Platform.runLater(() -> {
            currentScene = CurrentScene.CHOOSE_CARD;
            primaryStage.setWidth(1100);
            primaryStage.setHeight(800);
            ChooseCardController chooseCardController = setLayout(scene, "/FXML/chooseCard.fxml");
            chooseCardController.setNetworkHandler(networkHandler);
            chooseCardController.setAvailableGods(godsChosen);

        });

    }

    @Override
    public GameView getGameView() {
        return gameView;
    }

    @Override
    public void showPublicInformation() {
        Platform.runLater(() -> {
        mainController.setUsernames(gameView.getUsernames());
        mainController.setColors(gameView.getColors());
        mainController.setGods(gameView.getGods());
        mainController.setPublicInformation();
        });
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

    /**
     * method imoplemented in order to display the same board but with the different slot that has just been updated from the model
     * @param slot is the slot that has been changed
     */
    @Override
    public void showGuiSlot(Slot slot) {
    Platform.runLater(()-> {
        mainController.changeSlot(slot);
    });
    }

    /**
     * method used to set the main scene, when the scene with the board is setted, the default moment is wait
     * the public information are displayed
     */
    @Override
    public void showGame() {
        Platform.runLater(()-> {
        mainController = setLayout(scene, "/FXML/mainView.fxml");
        primaryStage.setHeight(700);
        mainController.setMoment(Action.WAIT);
        mainController.addViewListener(networkHandler);
        mainController.initialize();
        start = true;
        });
    }

    /**
     * method that sets a the moment in which the gui expects the worker to use, in order to allow the main controller to handle the click differently
     */
    @Override
    public void askWhichWorkerToUse() {
        Platform.runLater(() -> {
            mainController.setMoment(Action.ASK_WHICH_WORKER);
            mainController.initialize();
        });
    }

    /**
     * method that sets method a the moment in which the gui expects two positions for the initial workers, in order to allow the main controller to handle the click differently
     */
    @Override
    public void askWhereToPositionWorkers() {
        Platform.runLater(() -> {
            mainController.setMoment(Action.ASK_INITIAL_POSITION);
            mainController.setUsernames(gameView.getUsernames());
            mainController.setColors(gameView.getColors());
            mainController.setGods(gameView.getGods());
            mainController.initialize();
        });
    }

    /**
     * method that if the game hasn't started informs the user with an alert to wait, otherwise it sets the wait moment in main scene
     */
    @Override
    public void othersTurn(String usernameOnTurn) {
        Platform.runLater(() -> {
            if(!start) {
                 Alert a = new Alert(Alert.AlertType.WARNING);
                 a.setContentText("It's " + usernameOnTurn + "'s turn. You can't do anything until it's your turn.");
                 a.show();
            }else {
                currentScene = CurrentScene.SET_WORKERS;
                mainController.setMoment(Action.WAIT);
                mainController.initialize();
            }
        });
    }

    /**
     * method that indicates to the controller with the new moment set that it has to wait for action + slot
     */
    @Override
    public void askAction() {
        Platform.runLater(() -> {
        mainController.setMoment(Action.CHOOSEACT);
        mainController.initialize();
        });
    }

}
