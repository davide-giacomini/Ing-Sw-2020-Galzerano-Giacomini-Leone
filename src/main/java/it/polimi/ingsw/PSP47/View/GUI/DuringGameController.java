package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.GameView;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableActionAndDirection;
import it.polimi.ingsw.PSP47.Visitor.VisitableInitialPositions;
import it.polimi.ingsw.PSP47.Visitor.VisitableRowsAndColumns;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is used to separate from the Gui class the logic of the game, based on which moment we are in the game, it
 * dislays different texts to give instructions and reacts to the user inputs differently
 * from the Gui it receives the GameView, in which all information about the game and current moment are stored
 */
public class DuringGameController extends ViewObservable{

    private ArrayList<String> usernames= new ArrayList<>(3); // 3 arrays used for the dislay of publi info
    private ArrayList<Color> colors = new ArrayList<>(3);
    private ArrayList <GodName> gods = new ArrayList<>(3);

    private GameView gameView; // gives info about the game saved in the client
    private Action action; //used to save the action chosen by the player

    private int[] newRowAndColumn = new int[4]; //used for the positions of the workers that will be sent in messages
    private int[] workerRowAndColumn = new int[2];

    @FXML
    private GridPane gridPane;

    @FXML
    private ArrayList<Pane> slots;

    @FXML
    private Button moveButton;

    @FXML
    private Button buildButton;

    @FXML
    private Button buildDomeButton;

    @FXML
    private Button endButton;

    @FXML
    private Button quitButton;

    @FXML
    private Text commandText;

    @FXML
    private TextArea firstPlayerInfo;

    @FXML
    private TextArea secondPlayerInfo;

    @FXML
    private TextArea thirdPlayerInfo;


    /**
     * in the initialize method the based on the moment in which we are in the game the text to display
     */
    void changeText() {
        if (gameView.getCurrentScene() == CurrentScene.ASK_INITIAL_POSITION){
            commandText.setText("Choose where to position worker :");
        }else if(gameView.getCurrentScene() == CurrentScene.ASK_WHICH_WORKER){
            commandText.setText("Choose the worker you want to use :");
        }else if (gameView.getCurrentScene() == CurrentScene.CHOOSE_ACTION){
            commandText.setText("Choose the action you want between the buttons :");
        }else if (gameView.getCurrentScene() == CurrentScene.WAIT){
            commandText.setText("WAIT");
        }
    }

    /**
     * method used only when the gui has received the request to ask which worker to use or where to position it
     * the parameters are resetted in order to be then filled with the correct values of the clicked slot
     */
    void resetRowsAndColumns(){
        for (int i = 0; i < 4; i++) {
            newRowAndColumn[i] = -1; //for the row+column+row+column of the two inital positions of the workers
        }

        workerRowAndColumn[0]= -1; //row and column of the position of the worker the user wants to use
        workerRowAndColumn[1]= -1;
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the moment has to change since now the user has to click a slot
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void OnMoveClick(MouseEvent event) {
        if (gameView.getCurrentScene() == CurrentScene.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentScene() == CurrentScene.CHOOSE_ACTION){
            action = Action.MOVE;
            gameView.update(CurrentScene.ACTION_CHOSEN);
            commandText.setText("Now click on the slot where you want to move:");
        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the moment has to change since now the user has to click a slot
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void OnBuildClick(MouseEvent event) {
        if (gameView.getCurrentScene() == CurrentScene.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentScene() == CurrentScene.CHOOSE_ACTION) {
            action = Action.BUILD;
            gameView.update(CurrentScene.ACTION_CHOSEN);
            commandText.setText("Now click on the slot where you want to build:");
        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the moment has to change since now the user has to click a slot
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void OnBuildDomeClick(MouseEvent event) {
        if (gameView.getCurrentScene() == CurrentScene.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentScene() == CurrentScene.CHOOSE_ACTION) {
            action = Action.BUILDDOME;
            gameView.update(CurrentScene.ACTION_CHOSEN);
            commandText.setText("Now click on the slot where you want to build the Dome:");
        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the message is sent since I dont have to click on
     * the gridPane
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void OnEndClick(MouseEvent event) {
        if (gameView.getCurrentScene() == CurrentScene.WAIT) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentScene() == CurrentScene.CHOOSE_ACTION) {
            action = Action.END;
            VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
            visitableActionAndDirection.setAction(action);
            notifyViewListener(visitableActionAndDirection);
            commandText.setText("You asked to end your turn");
            gameView.update(CurrentScene.WAIT);
        }
    }

    /**
     * This method notifies the Network Handler that it has to close itself
     * @param event
     */
    @FXML
    void OnQuitClick(MouseEvent event) {
        notifyEndConnection();
        System.exit(0); //added because the GUI , as application with its own thread, has to be shut too
    }

    /**
     * method that depending on the moment(current scene) handles the click on the grid differently
     */
    @FXML
    void GridClick(MouseEvent event) {

        Node source = (Node)event.getSource() ; // gets the pane clicked
        Integer colIndex = GridPane.getColumnIndex(source); //column adn row of the pane clicked
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);

        if (gameView.getCurrentScene() == CurrentScene.ASK_INITIAL_POSITION) //the click is accepted twice before of sending the initial positions of the 2 workers
            selectSlotAndNotify(rowIndex, colIndex);
        else if (gameView.getCurrentScene() == CurrentScene.ASK_WHICH_WORKER){ //the click is accepted once when I choose the worker
            chooseWorkerToUse(rowIndex,colIndex);
        }else if(gameView.getCurrentScene() == CurrentScene.ACTION_CHOSEN){ //the action button was clicked and after clicking on the grid the message can be created and sent
            VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
            visitableActionAndDirection.setAction(action);
            visitableActionAndDirection.setDirection(Direction.getDirectionGivenSlots(workerRowAndColumn[0],workerRowAndColumn[1], rowIndex,colIndex));
            notifyViewListener(visitableActionAndDirection);
            commandText.setText("WAIT"); // now the user cannot keep on clicking but has to wait, both if its his turn or not, until the request is accepted by the server
            gameView.update(CurrentScene.WAIT);
        }else if (gameView.getCurrentScene() == CurrentScene.WAIT ){ //if I am in this moment I cannot click
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }

    }

    /**
     * method used to send the inital positions of the two workers
     * @param row of the pane clicked in the gridPane
     * @param column of the pane clicked in the gridPane
     */
    private void selectSlotAndNotify(int row, int column) {
        int i;
        for (i = 0; i < 4; i++) {
            if ( newRowAndColumn[i] == -1 ) {
                newRowAndColumn[i] = row ;
                newRowAndColumn[i +1] = column;
                break;
            }
        }
        if (i == 2) {
            VisitableInitialPositions visitableInitialPositions = new VisitableInitialPositions(); //create message at the second click
            visitableInitialPositions.setRowsAndColumns(newRowAndColumn);
            notifyViewListener(visitableInitialPositions);
            commandText.setText("WAIT");
            gameView.update(CurrentScene.WAIT);
        }
    }

    /**
     *This method is used to set row and column of the pane clicked that will be sent in an array to the sever
     * @param row of the pane
     * @param column of the pane
     */
    private void chooseWorkerToUse(int row, int column) {

        workerRowAndColumn[0] = row ;
        workerRowAndColumn[1] = column;

        VisitableRowsAndColumns visitableRowsAndColumns = new VisitableRowsAndColumns();
        visitableRowsAndColumns.setRowsAndColumns(workerRowAndColumn);
        notifyViewListener(visitableRowsAndColumns);
        commandText.setText("WAIT");
        gameView.update(CurrentScene.WAIT);

    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public void setGods(ArrayList<GodName> gods) {
        this.gods = gods;
    }

    /**
     * method used when an updated slot arrives; It displays only the change in the specific slot, it adds worker and alsa all levels with addLevels function
     * by using images
     * @param slot is the clot that has been changed
     */
    public void changeSlot(Slot slot){
        Pane pane = (Pane) getNodeByRowColumnIndex(slot.getRow(), slot.getColumn(), gridPane); //I get the pane that corresponds to the row and column  received by the server
        //pane.setEffect(new DropShadow());
        pane.getChildren().clear(); //clean what was in the pane before and recreate everything

        GridPane grid = new GridPane(); //gridPane created to always divide the pane into 4 parts
        //grid.setGridLinesVisible(true);
        final int numCols = 1 ;
        final int numRows = 4 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols); //sets the width of the columns all equal to 1/4 of the total width of the gridpane
            colConst.setHalignment(HPos.CENTER); //centers the insertions of images in the columns
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);//sets the height of the columns all equal to 1/4 of the total height of the gridpane
            rowConst.setValignment(VPos.CENTER);  //centers the insertions of images in the row
            grid.getRowConstraints().add(rowConst);
        }
        grid.prefWidthProperty().bind(pane.widthProperty()); //associates the grid width to the one of the bigger pane (slot)
        grid.prefHeightProperty().bind(pane.heightProperty());//associates the grid height to the one of the bigger pane (slot)
        pane.getChildren().add(grid); //adds grid as son of the pane
        grid.setAlignment(Pos.CENTER); //not sure if necessarey for allignment


        int levels = addLevels(grid,pane, slot.getLevel()); //add images for levels

        if (slot.isWorkerOnSlot()){ //add worker image

            Image worker = new Image(getImageWorkerFromColor(slot.getWorkerColor()));
            ImageView workerView = new ImageView(worker);
            workerView.setPreserveRatio(true); //to keep the ratio and matìke it look nice
            workerView.fitWidthProperty().bind(pane.widthProperty());  //also the dimensions of the image have to be dimensioned based on the pane otherwhise they will grow as big as they want
            workerView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            grid.add(workerView,0,levels);

            workerRowAndColumn[0] = slot.getRow() ; //since a worker has been moved here, this is the worker used in the turn, I reset this parameters used for the next action //TODO CHECK IF IT WORKS WITH ALL GODS
            workerRowAndColumn[1] = slot.getColumn();
        }
    }

    /**
     * method that gets th worker image based on the color of the worker that is in the slot
     * @param workerColor is the color of the worker
     * @return image of the worker
     */
    private String getImageWorkerFromColor(Color workerColor) {
        if(workerColor== Color.BLUE)
            return "/Images/female_blue.png";
        else if (workerColor== Color.RED)
           return "/Images/female_red.png";
        else if (workerColor== Color.YELLOW)
           return "/Images/female_yellow.png";
        else if (workerColor== Color.GREEN)
           return "/Images/female_green.png";
        else if (workerColor== Color.PURPLE)
           return "/Images/female_purple.png";
        else if (workerColor== Color.CYAN)
           return "/Images/female_cyan.png";
        else //(workerColor== Color.WHITE)
           return "/Images/female_white.png";
    }

    /**
     * method used to get the pane from the gridPane from its row and column
     * @param row of the slot that I need
     * @param column of the slot that I need
     * @param gridPane for the graphic of the board
     * @return
     */
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    /**
     * method used to add the public information in the scene ( they arrive after the scene has been displayed)
     */
    public void setPublicInformation(){
        firstPlayerInfo.appendText(usernames.get(0)+"\n"+ colors.get(0)+"\n"+ gods.get(0));
        secondPlayerInfo.appendText(usernames.get(1)+"\n"+ colors.get(1)+"\n"+ gods.get(1));
        if (usernames.size()==3)
            thirdPlayerInfo.appendText(usernames.get(2)+"\n"+ colors.get(2)+"\n"+ gods.get(2));
    }

    /**
     * Adds images which represent the levels in the little GridPane of the slot correctly
     * @param gridPane which has the images for levels and workers
     * @param pane which represents the slot
     * @param level level of the slot that has been updated
     * @return
     */
    public int addLevels(GridPane gridPane, Pane pane, Level level){
        int levels = 3;

        if (level == Level.ATLAS_DOME){ //for now the dome of atlas is added at the buttom
            Image levelAtlas = new Image("/Images/dome.png");
            ImageView levelAtlasView = new ImageView(levelAtlas);
            levelAtlasView.setPreserveRatio(true);
            levelAtlasView.fitWidthProperty().bind(pane.widthProperty());
            levelAtlasView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            gridPane.add(levelAtlasView, 0, 3);
            return levels;
        }

        if(level.ordinal() >= Level.LEVEL1.ordinal()) { // I put >= since the level1 has to be added also when the level is 2 and same for the other levels
            Image levelOne = new Image("/Images/level1_1_light.png");
            ImageView levelOneView = new ImageView(levelOne);
            levelOneView.setPreserveRatio(true);
            levelOneView.fitWidthProperty().bind(pane.widthProperty());
            levelOneView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            gridPane.add(levelOneView, 0, 3);
            levels--;
        }

        if(level.ordinal() >= Level.LEVEL2.ordinal()) {
            Image levelTwo = new Image("/Images/level2_1_light.png");
            ImageView levelTwoView = new ImageView(levelTwo);
            levelTwoView.setPreserveRatio(true);
            levelTwoView.fitWidthProperty().bind(pane.widthProperty());
            levelTwoView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            gridPane.add(levelTwoView, 0, 2);
            levels--;
        }

        if(level.ordinal() >= Level.LEVEL3.ordinal()){
            Image levelThree = new Image("/Images/level3_light.png");
            ImageView levelThreeView = new ImageView(levelThree);
            levelThreeView.setPreserveRatio(true);
            levelThreeView.fitWidthProperty().bind(pane.widthProperty());
            levelThreeView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            gridPane.add(levelThreeView, 0, 1);
            levels--;


        }

        if ( level.ordinal() >= Level.DOME.ordinal()){
            Image levelDome = new Image("/Images/dome_light.png");
            ImageView levelDomeView = new ImageView(levelDome);
            levelDomeView.setPreserveRatio(true);
            levelDomeView.fitWidthProperty().bind(pane.widthProperty());
            levelDomeView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            gridPane.add(levelDomeView, 0, 0);
            levels--;

        }

        return levels;

    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
