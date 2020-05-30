package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.CurrentScene;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.GameView;
import it.polimi.ingsw.PSP47.View.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to change what the user will see and updates the current scene (//TODO maybe move it in the messageHandler)
 * It is only used for the graphic part, which means display and change of scene layout //TODO Check where to delete platform.runLater (ari tried and everything broke)
 */
public class GUI extends Application implements View {

    private NetworkHandler networkHandler;
    private GameView gameView;

    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private DuringGameController duringGameController;

    private boolean start = false;

    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    @Override
    public GameView getGameView() {
        return gameView;
    }

    /**
     * This method makes the GUI starts running.
     * It load the first scene and creates the gameView.
     * @param primaryStage the Stage that will be used during the game.
     * @throws Exception if there are some I/O troubles.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        gameView = new GameView();
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXML/connectionToServer.fxml"));
        Font.loadFont(getClass().getResourceAsStream("Fonts/savoye.ttf"), 30);
        root = fxmlLoader.load();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/background.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Santorini");
        primaryStage.show();

        gameView.updateMoment(CurrentScene.START);

        ConnectionToServerController connectionToServerController = fxmlLoader.getController();
        connectionToServerController.setGui(this);

        primaryStage.setOnCloseRequest(new EventHandler<>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if(networkHandler!=null)
                    networkHandler.endConnection();
                System.exit(0);
            }
        });
    }

    /**
     * This method is used to change the layout of the current scene, loading another FXML file.
     * @param scene the scene that must be changed.
     * @param path the path of the file that has to be loaded.
     * @param <T> the controller of the new scene.
     * @return the controller of the new scene.
     */
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

    /**
     * This method loads the scene where username and color are asked and add the
     * networkHandler as a listener.
     */
    @Override
    public void askFirstConnection() {
        Platform.runLater(() -> {
            StartController startController = setLayout(scene, "/FXML/startPane.fxml");
            startController.addViewListener(networkHandler);
        });
    }

    /**
     * This method loads the scene where the number of players is asked and add the
     * networkHandler as a listener.
     */
    @Override
    public void askNumberOfPlayers() {
        Platform.runLater(() -> {
            ChoosePlayersController choosePlayersController = setLayout(scene,"/FXML/choosePlayers.fxml");
            choosePlayersController.addViewListener(networkHandler);
        });
    }

    /**
     * This method loads the scene where the Challenger is asked to choose the gods and the first player of the game.
     * @param usernames the list of players in the game.
     */
    @Override
    public void challengerWillChooseThreeGods(ArrayList<String> usernames) { //TODO Ari voleva cambiarlo
        Platform.runLater(() -> {
            // TODO modifico la grandezza perché è troppo grande
            primaryStage.setWidth(1100);
            primaryStage.setHeight(800);

            ChallengerController chooseCardsController = setLayout(scene, "/FXML/challenger.fxml");
            chooseCardsController.addViewListener(networkHandler);
            chooseCardsController.setNumberOfPlayers(gameView.getNumberOfPlayers());
            chooseCardsController.setFirstPlayer(usernames.get(0));
            chooseCardsController.setSecondPlayer(usernames.get(1));
            if (usernames.size() == 3)
                chooseCardsController.setThirdPlayer(usernames.get(2));
            else
                chooseCardsController.setThirdPlayer(null);
        });
    }

    /**
     * This method loads the scene where the player has to choose the god's power that will use
     * during the game.
     * @param godsChosen is the array of the available gods (chosen by the challenger)
     */
    @Override
    public void chooseYourGod(ArrayList<GodName> godsChosen) {
        Platform.runLater(() -> {
            // TODO modifico la grandezza perché è troppo grande
            primaryStage.setWidth(1100);
            primaryStage.setHeight(800);

            ChooseCardController chooseCardController = setLayout(scene, "/FXML/chooseCard.fxml");
            chooseCardController.addViewListener(networkHandler);
            chooseCardController.setAvailableGods(godsChosen);

        });

    }

    /**
     * This method loads an Alert pane with an error message.
     * @param text the content of the error message.
     */
    @Override
    public void showErrorMessage(String text) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(text);
            a.show();
        });
    }

    /**
     * This method loads an Alert pane with an information about the game.
     * @param text the content of the information.
     */
    @Override
    public void showImportantMessage(String text) {
        //TODO modificare stammerda
        if (text.split("\n")[0].equals("Wait for the other players to connect.")){
            Platform.runLater(() -> {
                setLayout(scene, "/FXML/waitingPane.fxml");
            });
        }
        else {
            Platform.runLater(() -> {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(text);
                a.show();
            });
        }
    }

    /**
     * This method loads the scene where the winner is declared and the game ends.
     * @param usernameWinner is the username of the winner.
     */
    @Override
    public void theWinnerIs(String usernameWinner) {
        if (gameView.getMyUsername().equals(usernameWinner))
            showIAmTheWinner();
        else
            showWhoIsTheWinner(usernameWinner);
    }
    
    private void showIAmTheWinner(){
        Platform.runLater(()->{
            try {
                Stage winnerStage = new Stage();
                winnerStage.setHeight(400);
                winnerStage.setWidth(600);
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXML/winningAdvice.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                
                winnerStage.setScene(scene);
                winnerStage.setResizable(false);
            
                winnerStage.setTitle("You won!");
                winnerStage.initModality(Modality.APPLICATION_MODAL);
                winnerStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    private void showWhoIsTheWinner(String usernameWinner){
        Platform.runLater(()->{
            try {
                Stage winnerStage = new Stage();
                winnerStage.setHeight(400);
                winnerStage.setWidth(600);
    
                winnerStage.setX(Screen.getPrimary().getBounds().getMinX());
                winnerStage.setY(Screen.getPrimary().getBounds().getMinY());
            
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXML/losingWithWinner.fxml"));
                AnchorPane rootAnchorPane = fxmlLoader.load();
                Scene scene = new Scene(rootAnchorPane);
                
                BorderPane borderPane = (BorderPane) rootAnchorPane.getChildren().get(2);
                Text text = (Text) ((BorderPane)((BorderPane)borderPane.getCenter()).getTop()).getCenter();
                text.setText("I am sorry.\n" +usernameWinner+ " won.");
                
                winnerStage.setScene(scene);
                winnerStage.setResizable(false);
            
                winnerStage.setTitle("I am sorry...");
                winnerStage.initModality(Modality.APPLICATION_MODAL);
                winnerStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    /**
     * This method loads the scene where a loser is declared.
     */
    @Override
    public void theLoserIs() {
        Platform.runLater(()-> {
            try {
                Stage loserStage = new Stage();
                loserStage.setHeight(400);
                loserStage.setWidth(600);
                
                loserStage.setX(Screen.getPrimary().getBounds().getMinX());
                loserStage.setY(Screen.getPrimary().getBounds().getMinY());
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXML/losingWithoutWinner.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                
                loserStage.setScene(scene);
                loserStage.setResizable(false);
                
                loserStage.setTitle("I am sorry...");
                loserStage.initModality(Modality.APPLICATION_MODAL);
                loserStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void showEnd() {
       // System.exit(0); decide how to handle the final part
    }

    /**
     * method implemented in order to display the same board but with the different slot that has just been updated from the model
     * @param slot is the slot that has been changed
     */
    @Override
    public void showNewBoard(Slot slot) {
        Platform.runLater(()-> {
            gameView.updateMoment(CurrentScene.WAIT); //new current scene
            duringGameController.changeSlot(slot);
        });
    }

    /**
     * method used to set the main scene, when the scene with the board is set, the default moment is wait
     * the public information are displayed
     */
    @Override
    public void showGame() {
        Platform.runLater(()-> {
            primaryStage.setWidth(1100);
            primaryStage.setHeight(800);

            duringGameController = setLayout(scene, "/FXML/boardDuringGame.fxml");
            gameView.updateMoment(CurrentScene.WAIT); //new current scene
            duringGameController.setGameView(gameView);
            duringGameController.addViewListener(networkHandler);
            duringGameController.changeText();
            primaryStage.setResizable(true);
            start = true;
        });
    }

    /**
     * method that sets a the moment in which the gui expects the worker to use, in order to allow the main controller to handle the click differently
     */
    @Override
    public void askWhichWorkerToUse() {
        Platform.runLater(() -> {
            gameView.updateMoment(CurrentScene.ASK_WHICH_WORKER); //new current scene
            duringGameController.resetRowsAndColumns();
            duringGameController.changeText();
        });
    }

    /**
     * method that sets method at the moment (current scene) in which the gui expects two positions for the initial workers, in order to allow the main controller to handle the click differently
     */
    @Override
    public void askWhereToPositionWorkers() {
        Platform.runLater(() -> {
            gameView.updateMoment(CurrentScene.ASK_INITIAL_POSITION);//new current scene
            duringGameController.resetRowsAndColumns();
            duringGameController.setUsernames(gameView.getUsernames());
            duringGameController.setColors(gameView.getColors());
            duringGameController.setGods(gameView.getGods());
            duringGameController.changeText();
        });
    }

    /**
     * method that if the game hasn't started informs the user with an alert to wait, otherwise it sets the wait moment in main scene
     */
    @Override
    public void othersTurn(String usernameOnTurn) {
        Platform.runLater(() -> {
            if(!start) {
                primaryStage.setWidth(600);
                primaryStage.setHeight(450);

                setLayout(scene, "/FXML/waitingPane.fxml"); //now instead of alert I show waiting Pane made by Moni :)
            }else {
                gameView.updateMoment(CurrentScene.WAIT);//new current scene
                duringGameController.changeText(usernameOnTurn);
            }
        });
    }

    /**
     * method that indicates to the DuringGameController with the new moment set that it has to wait for action + slot
     */
    @Override
    public void askAction() {
        Platform.runLater(() -> {
            gameView.updateMoment(CurrentScene.CHOOSE_ACTION); //new current scene
            duringGameController.changeText();
        });
    }

    /**
     * gives the informations to the duringGameController to set them in the lateral part of the scene
     */
    @Override
    public void showPublicInformation() {
        Platform.runLater(() -> {
            duringGameController.setUsernames(gameView.getUsernames());
            duringGameController.setColors(gameView.getColors());
            duringGameController.setGods(gameView.getGods());
            duringGameController.setPublicInformation();
        });
    }

    @Override
    public void whileOthersTurn(String changes) {

    }
}
