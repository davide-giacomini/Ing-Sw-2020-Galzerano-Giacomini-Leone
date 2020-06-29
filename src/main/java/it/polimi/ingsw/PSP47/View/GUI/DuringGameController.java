package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.*;
import it.polimi.ingsw.PSP47.Model.Slot;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * This class is used to separate from the Gui class the logic of the game, based on which moment we are in the game, it
 * displays different texts to give instructions and reacts to the user inputs differently
 * from the Gui it receives the GameView, in which all information about the game and current moment are stored
 */
public class DuringGameController extends ViewObservable{

    private GameView gameView; // gives info about the game saved in the client
    private Action action; //used to save the action chosen by the player

    private int[] newRowAndColumn = new int[4]; //used for the positions of the workers that will be sent in messages
    private int[] workerRowAndColumn = new int[2];

    @FXML
    private GridPane gridPane;

    @FXML
    private Text commandText;

    @FXML
    private StackPane buildDomePane;

    @FXML
    private Text first_username;

    @FXML
    private ImageView first_god;

    @FXML
    private ImageView first_color;

    @FXML
    private Text second_username;

    @FXML
    private ImageView second_god;

    @FXML
    private ImageView second_color;

    @FXML
    private StackPane third_name;

    @FXML
    private Text third_username;

    @FXML
    private StackPane third_information;

    @FXML
    private ImageView third_god;

    @FXML
    private ImageView third_color;

    @FXML
    private ImageView moveIndicatorView;

    @FXML
    private StackPane first_name;

    @FXML
    private StackPane first_information;

    @FXML
    private StackPane second_name;

    @FXML
    private StackPane second_information;

    /**
     * This method is used to display on  the right of the scene the information of the players in the game
     */
    void setPublicInformation() {
        first_username.setText(gameView.getUsernames().get(0));
        Image godFirst = new Image(getImageGodFromGodName(gameView.getGods().get(0)));
        first_god.setImage(godFirst);
        first_god.setPreserveRatio(true);
        Image colorFirst = new Image(getImageWorkerFromColor(gameView.getColors().get(0)));
        first_color.setImage(colorFirst);
        first_color.setPreserveRatio(true);

        second_username.setText(gameView.getUsernames().get(1));
        Image godSecond = new Image(getImageGodFromGodName(gameView.getGods().get(1)));
        second_god.setImage(godSecond);
        second_god.setPreserveRatio(true);
        Image colorSecond = new Image(getImageWorkerFromColor(gameView.getColors().get(1)));
        second_color.setImage(colorSecond);
        second_color.setPreserveRatio(true);

        if (gameView.getNumberOfPlayers() == 2) {
            third_information.setVisible(false);
            third_name.setVisible(false);
        }
        else {
            third_username.setText(gameView.getUsernames().get(2));
            Image godThird = new Image(getImageGodFromGodName(gameView.getGods().get(2)));
            third_god.setImage(godThird);
            third_god.setPreserveRatio(true);
            Image colorThird = new Image(getImageWorkerFromColor(gameView.getColors().get(2)));
            third_color.setImage(colorThird);
            third_color.setPreserveRatio(true);
        }
    }

    /**
     * This method is used to display the special button for building a dome only for the player who chose atlas
     */
    void displayBuildDome(){
        if (!gameView.getMyGod().equals(GodName.ATLAS)){
            buildDomePane.setVisible(false);
            buildDomePane.getChildren().removeAll();
        }
    }

    /**
     * based on the moment in which we are in the game the text to display
     */
    void changeText() {
        if (gameView.getCurrentMoment() == CurrentMoment.ASK_INITIAL_POSITION){
            commandText.setText("Choose where to position your workers by clicking on the slot :");
        }else if(gameView.getCurrentMoment() == CurrentMoment.ASK_WHICH_WORKER){
            commandText.setText("Choose the worker you want to use by clicking on it :");
        }else if (gameView.getCurrentMoment() == CurrentMoment.CHOOSE_ACTION){
            commandText.setText("Choose an action between the buttons :");
        }else if (gameView.getCurrentMoment() == CurrentMoment.WAIT){
            commandText.setText("WAIT");
        }
    }

    void changeText(String displayedText){
        if (gameView.getCurrentMoment() == CurrentMoment.WAIT )
            commandText.setText(displayedText);
        else if (gameView.getCurrentMoment() == CurrentMoment.END){
            commandText.setText(displayedText);
            disableEverything();
        }

    }

    /**
     * method used only when the gui has received the request to ask which worker to use or where to position it
     * the parameters are resetted in order to be then filled with the correct values of the clicked slot
     */
    void resetRowsAndColumns(){
        for (int i = 0; i < 4; i++) {
            newRowAndColumn[i] = -1; //for the row+column+row+column of the two initial positions of the workers
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
    void onMoveClick(MouseEvent event) {
        if (gameView.getCurrentMoment() == CurrentMoment.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentMoment() == CurrentMoment.CHOOSE_ACTION){
            action = Action.MOVE;
            gameView.updateMoment(CurrentMoment.ACTION_CHOSEN);
            commandText.setText("Now click on the slot where you want to move.");
        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the moment has to change since now the user has to click a slot
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void onBuildClick(MouseEvent event) {
        if (gameView.getCurrentMoment() == CurrentMoment.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentMoment() == CurrentMoment.CHOOSE_ACTION) {
            action = Action.BUILD;
            gameView.updateMoment(CurrentMoment.ACTION_CHOSEN);
            commandText.setText("Now click on the slot where you want to build:");
        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the moment has to change since now the user has to click a slot
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void onBuildDomeClick(MouseEvent event) {
        if (gameView.getCurrentMoment() == CurrentMoment.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentMoment() == CurrentMoment.CHOOSE_ACTION) {
            action = Action.BUILDDOME;
            gameView.updateMoment(CurrentMoment.ACTION_CHOSEN);
            commandText.setText("Now click on the slot where you want to build the dome:");
        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the message is sent since I dont have to click on
     * the gridPane
     * 2. you are not allowed to click so I show you an alert
     * @param event is the input click of the user
     */
    @FXML
    void onEndClick(MouseEvent event) {
        if (gameView.getCurrentMoment() == CurrentMoment.WAIT) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }else if (gameView.getCurrentMoment() == CurrentMoment.CHOOSE_ACTION) {
            action = Action.END;
            VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
            visitableActionAndDirection.setAction(action);
            notifyViewListener(visitableActionAndDirection);
            commandText.setText("You asked to end your turn");
            gameView.updateMoment(CurrentMoment.WAIT);
        }
    }

    /**
     * This method notifies the Network Handler that it has to close itself
     * @param event the click on the Quit button
     */
    @FXML
    void onQuitClick(MouseEvent event) {
        notifyEndConnection();
        System.exit(0); //added because the GUI , as application with its own thread, has to be shut too
    }

    /**
     * method that depending on the moment(current scene) handles the click on the grid differently
     */
    @FXML
    void gridClick(MouseEvent event) {

        Node source = (Node)event.getSource() ; // gets the pane clicked
        Integer colIndex = GridPane.getColumnIndex(source); //column adn row of the pane clicked
        Integer rowIndex = GridPane.getRowIndex(source);

        if (gameView.getCurrentMoment() == CurrentMoment.ASK_INITIAL_POSITION) //the click is accepted twice before of sending the initial positions of the 2 workers
            selectSlotAndNotify(rowIndex, colIndex);
        else if (gameView.getCurrentMoment() == CurrentMoment.ASK_WHICH_WORKER){ //the click is accepted once when I choose the worker
            chooseWorkerToUse(rowIndex,colIndex);
        }else if(gameView.getCurrentMoment() == CurrentMoment.ACTION_CHOSEN){ //the action button was clicked and after clicking on the grid the message can be created and sent
            VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
            visitableActionAndDirection.setAction(action);
            visitableActionAndDirection.setDirection(Direction.getDirectionGivenSlots(workerRowAndColumn[0],workerRowAndColumn[1], rowIndex,colIndex));
            notifyViewListener(visitableActionAndDirection);
            //commandText.setText("WAIT"); // now the user cannot keep on clicking but has to wait, both if its his turn or not, until the request is accepted by the server
            gameView.updateMoment(CurrentMoment.WAIT);
            changeText(); // now the user cannot keep on clicking but has to wait, both if its his turn or not, until the request is accepted by the server
        }else if (gameView.getCurrentMoment() == CurrentMoment.WAIT ){ //if I am in this moment I cannot click
            //TODO davide -> io questo lo cambierei in un non alert ma poi vediamo
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please wait for your Turn!");
            a.show();
        }

    }

    /**
     * method used to send the initial positions of the two workers
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
            gameView.updateMoment(CurrentMoment.WAIT);
            changeText();
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
        gameView.updateMoment(CurrentMoment.WAIT);
        changeText();
    }

    /**
     * method used when an updated slot arrives; It displays only the change in the specific slot, it adds worker and also all levels with addLevels function
     * by using images
     * @param slot is the clot that has been changed
     */
    public void changeSlot(Slot slot){
        Pane pane = (Pane) getNodeByRowColumnIndex(slot.getRow(), slot.getColumn(), gridPane); //I get the pane that corresponds to the row and column  received by the server
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
        grid.setAlignment(Pos.CENTER); //not sure if necessary for alignment

        int levels = addLevels(grid,pane, slot.getLevel()); //add images for levels

        if (slot.isWorkerOnSlot()){ //add worker image

            Image worker = new Image(getImageWorkerFromColor(slot.getWorkerColor()));
            ImageView workerView = new ImageView(worker);
            workerView.setPreserveRatio(true); //to keep the ratio and make it look nice
            workerView.fitWidthProperty().bind(pane.widthProperty());  //also the sizes of the image have to be dimensioned based on the pane otherwise they will grow as big as they want
            workerView.fitHeightProperty().bind(pane.widthProperty().divide(4));
            grid.add(workerView,0,levels);
            if(slot.getWorkerColor() == gameView.getMyColor()) {
                workerRowAndColumn[0] = slot.getRow(); //since a worker has been moved here, this is the worker used in the turn, I reset this parameters used for the next action
                workerRowAndColumn[1] = slot.getColumn();
            }
        }
    }

    /**
     * method that gets th worker image based on the color of the worker that is in the slot
     * @param workerColor is the color of the worker
     * @return image of the worker
     */
    private String getImageWorkerFromColor(Color workerColor) {
        switch (workerColor){
            case BLUE: return "/Images/female_blue.png";
            case RED: return "/Images/female_red.png";
            case YELLOW: return "/Images/female_yellow.png";
            case GREEN: return "/Images/female_green.png";
            case PURPLE: return "/Images/female_purple.png";
            case CYAN: return "/Images/female_cyan.png";
            default: return "/Images/female_white.png"; //WHITE
        }
    }

    private String getImageGodFromGodName(GodName godName) {
        switch (godName){
            case APOLLO: return "/Images/Podium/podiumApollo.png";
            case ARTEMIS: return "/Images/Podium/podiumArtemis.png";
            case ATHENA: return "/Images/Podium/podiumAthena.png";
            case ATLAS: return "/Images/Podium/podiumAtlas.png";
            case CHRONUS: return "/Images/Podium/podiumChronus.png";
            case DEMETER: return "/Images/Podium/podiumDemeter.png";
            case HEPHAESTUS: return "/Images/Podium/podiumHephaestus.png";
            case HERA: return "/Images/Podium/podiumHera.png";
            case HESTIA: return "/Images/Podium/podiumHestia.png";
            case MINOTAUR: return "/Images/Podium/podiumMinotaur.png";
            case PAN: return "/Images/Podium/podiumPan.png";
            case PROMETHEUS: return "/Images/Podium/podiumPrometheus.png";
            case TRITON: return "/Images/Podium/podiumTriton.png";
            default: return "/Images/Podium/podiumZeus.png";   // ZEUS
        }
    }

    /**
     * method used to get the pane from the gridPane from its row and column
     * @param row of the slot that I need
     * @param column of the slot that I need
     * @param gridPane for the graphic of the board
     * @return node in the gridpane that represents the board
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
     * Adds images which represent the levels in the little GridPane of the slot correctly
     * @param gridPane which has the images for levels and workers
     * @param pane which represents the slot
     * @param level level of the slot that has been updated
     * @return which level has just been added in the given pane
     */
    public int addLevels(GridPane gridPane, Pane pane, Level level){
        int levels = 2;

        if (level == Level.ATLAS_DOME){ //for now the dome of atlas is added at the buttom
            Image levelAtlas = new Image("/Images/dome.png");
            ImageView levelAtlasView = new ImageView(levelAtlas);
            levelAtlasView.setPreserveRatio(true);
            levelAtlasView.fitWidthProperty().bind(pane.widthProperty());
            levelAtlasView.fitHeightProperty().bind(pane.heightProperty().divide(4));
            gridPane.add(levelAtlasView, 0, 3);
            return levels;
        }

        if(level.ordinal() >= Level.LEVEL1.ordinal()) { // I put >= since the level1 has to be added also when the level is 2 and same for the other levels
            levels=3;
            Image levelOne = new Image("/Images/level1_1_light.png");
            ImageView levelOneView = new ImageView(levelOne);
            levelOneView.setPreserveRatio(true);
            levelOneView.fitWidthProperty().bind(pane.widthProperty());
            levelOneView.fitHeightProperty().bind(pane.heightProperty().divide(4));
            gridPane.add(levelOneView, 0, 3);
            levels--;
        }

        if(level.ordinal() >= Level.LEVEL2.ordinal()) {
            Image levelTwo = new Image("/Images/level2_1_light.png");
            ImageView levelTwoView = new ImageView(levelTwo);
            levelTwoView.setPreserveRatio(true);
            levelTwoView.fitWidthProperty().bind(pane.widthProperty());
            levelTwoView.fitHeightProperty().bind(pane.heightProperty().divide(4));
            gridPane.add(levelTwoView, 0, 2);
            levels--;
        }

        if(level.ordinal() >= Level.LEVEL3.ordinal()){
            Image levelThree = new Image("/Images/level3_light.png");
            ImageView levelThreeView = new ImageView(levelThree);
            levelThreeView.setPreserveRatio(true);
            levelThreeView.fitWidthProperty().bind(pane.widthProperty());
            levelThreeView.fitHeightProperty().bind(pane.heightProperty().divide(4));
            gridPane.add(levelThreeView, 0, 1);
            levels--;


        }

        if ( level.ordinal() >= Level.DOME.ordinal()){
            Image levelDome = new Image("/Images/dome_light.png");
            ImageView levelDomeView = new ImageView(levelDome);
            levelDomeView.setPreserveRatio(true);
            levelDomeView.fitWidthProperty().bind(pane.widthProperty());
            levelDomeView.fitHeightProperty().bind(pane.heightProperty().divide(4));
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

    /**
     * This method is used to insert the image of the move indicater when the mouse goes on a pane in order to light it up
     * @param event indicates when the mouse goes into the pane that will be lighten up
     */
    @FXML
   void lightUp(MouseEvent event){
        moveIndicatorView = new ImageView();
        moveIndicatorView.setImage(new Image("Images/playermoveindicator_red.png"));
        Node source = (Node)event.getSource() ; // gets the pane clicked
        Integer colIndex = GridPane.getColumnIndex(source); //column and row of the pane clicked
        Integer rowIndex = GridPane.getRowIndex(source);
        Pane pane = (Pane) getNodeByRowColumnIndex(rowIndex, colIndex, gridPane);
        moveIndicatorView.fitWidthProperty().bind(pane.widthProperty());
        moveIndicatorView.fitHeightProperty().bind(pane.widthProperty());
        pane.getChildren().add(moveIndicatorView);
    }

    /**
     * This method is used to delete the indicator of a movement of the mouse on one of the panes (slots of the board)
     * @param event indicates when the mouse goes out of the pane that was light up
     */
    @FXML
   void lightDown(MouseEvent event){
        Node source = (Node)event.getSource() ; // gets the pane clicked
        Integer colIndex = GridPane.getColumnIndex(source); //column and row of the pane clicked
        Integer rowIndex = GridPane.getRowIndex(source);
        Pane pane = (Pane) getNodeByRowColumnIndex(rowIndex, colIndex, gridPane);
        pane.getChildren().remove(moveIndicatorView);
    }

    /**
     * This method disables all the buttons in the scene when the game is finished
     */
    private void disableEverything() {
        gridPane.setDisable(true);
        //add others cases
    }

    /**
     * Method used to display with more opacity the information about players who are not in the game anymore
     * @param usernameLoser is the username of the player who has lost
     */
    void deleteLoserInformation(String usernameLoser){

        if (usernameLoser.equals(first_username.getText())) {
            first_name.setOpacity(0.5);
            first_information.setOpacity(0.5);
        }
        else if (usernameLoser.equals(second_username.getText())){
            second_name.setOpacity(0.5);
            second_information.setOpacity(0.5);

        }else if (third_username.getText()!=null && usernameLoser.equals(third_username.getText())){
           third_name.setOpacity(0.5);
           third_information.setOpacity(0.5);
        }
    }

}
