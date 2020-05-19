package it.polimi.ingsw.PSP47.View.GUI;

import it.polimi.ingsw.PSP47.Enumerations.Action;
import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.Direction;
import it.polimi.ingsw.PSP47.Enumerations.GodName;
import it.polimi.ingsw.PSP47.Model.Slot;
import it.polimi.ingsw.PSP47.Network.Client.NetworkHandler;
import it.polimi.ingsw.PSP47.View.ViewObservable;
import it.polimi.ingsw.PSP47.Visitor.VisitableActionAndDirection;
import it.polimi.ingsw.PSP47.Visitor.VisitableInitialPositions;
import it.polimi.ingsw.PSP47.Visitor.VisitableRowsAndColumns;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController extends ViewObservable {

    private NetworkHandler networkHandler;

    private ArrayList<String> usernames= new ArrayList<>(3);
    private ArrayList<Color> colors = new ArrayList<>(3);
    private ArrayList <GodName> gods = new ArrayList<>(3);

    private Action moment;


    private int[] newRowAndColumn = new int[4];
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
     * in the initialize method the based on the moment in which we are in the game the text to display and col and row are resetted
     */
    void initialize() {

        //setto la grid pane
        if (moment == Action.ASK_INITIAL_POSITION){
            for (int i = 0; i < 4; i++) {
                newRowAndColumn[i] = -1;
            }
            commandText.setText("Choose where to position worker :");
           //buildButton.setEffect(new DropShadow());
            //buildButton.setDisable(true);
            //moveButton.setEffect(new DropShadow());
            //moveButton.setDisable(true);
            //buildDomeButton.setEffect(new DropShadow());
            //buildDomeButton.setDisable(true);
            //endButton.setEffect(new DropShadow());
            //endButton.setDisable(true);
        }else if(moment == Action.ASK_WHICH_WORKER){
            commandText.setText("Choose the worker you want to use :");
            workerRowAndColumn[0]= -1;
            workerRowAndColumn[1]= -1;
        }else if (moment == Action.CHOOSEACT){
            commandText.setText("Choose the action you want between the buttons :");

        }else if (moment == Action.WAIT){
            commandText.setText("WAIT");

        }
    }

    /**
     * method that handles the click in two ways : 1. you are allowed to click the button and in this case the moment has to change since now the user has to click a slot
     * 2. you are not allowed to click so I show you an alert
     * @param event
     */
    @FXML
    void OnMoveClick(MouseEvent event) {
        if (moment == Action.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Ooooo aspetta");
            a.show();
        }else if (moment == Action.CHOOSEACT){
            moment = Action.MOVE;
            commandText.setText("Now click on the slot where you want to move:");
        }
    }

    @FXML
    void OnBuildClick(MouseEvent event) {
        if (moment == Action.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Ooooo aspetta");
            a.show();
        }else if (moment == Action.CHOOSEACT) {
            moment = Action.BUILD;
            commandText.setText("Now click on the slot where you want to build:");
        }
    }

    @FXML
    void OnBuildDomeClick(MouseEvent event) {
        if (moment == Action.WAIT){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Ooooo aspetta");
            a.show();
        }else if (moment == Action.CHOOSEACT) {
            moment = Action.BUILDDOME;
            commandText.setText("Now click on the slot where you want to build the Dome:");
        }
    }

    @FXML
    void OnEndClick(MouseEvent event) {
        if (moment == Action.WAIT) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Ooooo aspetta");
            a.show();
        }else if (moment == Action.CHOOSEACT) {
            moment = Action.END;
            commandText.setText("You asked to end your turn");
            VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
            visitableActionAndDirection.setAction(moment);
            notifyViewListener(visitableActionAndDirection);
        }
    }
    //TODO CHANGE WITH NOTIFY IMPLEMENTED BY DAVID
    @FXML
    void OnQuitClick(MouseEvent event) {
        notifyEndConnection();
    }

    /**
     * method that depending on the moment handles the click on the grid differently
     */
    @FXML
    void GridClick(MouseEvent event) {

        Node source = (Node)event.getSource() ;
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);

        if (moment == Action.ASK_INITIAL_POSITION)
            selectSlotAndNotify(rowIndex, colIndex);
        else if (moment == Action.ASK_WHICH_WORKER){
            chooseWorkerToUse(rowIndex,colIndex);
        }else if(moment == Action.MOVE || moment == Action.BUILD || moment == Action.BUILDDOME){
            VisitableActionAndDirection visitableActionAndDirection = new VisitableActionAndDirection();
            visitableActionAndDirection.setAction(moment);
             visitableActionAndDirection.setDirection(Direction.getDirectionGivenSlots(workerRowAndColumn[0],workerRowAndColumn[1], rowIndex,colIndex));
            notifyViewListener(visitableActionAndDirection);
            commandText.setText("WAIT");
            moment = Action.WAIT;
        }else if (moment == Action.WAIT ){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Oooo Aspetta");
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
            //addViewListener(networkHandler);
            VisitableInitialPositions visitableInitialPositions = new VisitableInitialPositions();
            visitableInitialPositions.setRowsAndColumns(newRowAndColumn);
            notifyViewListener(visitableInitialPositions);
            commandText.setText("WAIT");
            moment = Action.WAIT;
        }
    }

    /**
     *
     * @param row
     * @param column
     */
    private void chooseWorkerToUse(int row, int column) {
        int i=0;
            if ( workerRowAndColumn[i] == -1 ) {
                workerRowAndColumn[i] = row ;
                workerRowAndColumn[i +1] = column;
                i=1;
            }
        if (i == 1) {
            //addViewListener(networkHandler);
            VisitableRowsAndColumns visitableRowsAndColumns = new VisitableRowsAndColumns();
            visitableRowsAndColumns.setRowsAndColumns(newRowAndColumn);
            notifyViewListener(visitableRowsAndColumns);
            commandText.setText("WAIT");
            moment = Action.WAIT;
        }
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
    //TODO ERASE
    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    public void setMoment(Action action){ this.moment = action;}

    /**
     * method used when an updated slot arrives to display only the change in the specific slot, it adds for now the worker in the pane of the gridpane
     * @param slot is the clot that has been changed
     */
    public void changeSlot(Slot slot){
        Pane pane = (Pane) getNodeByRowColumnIndex(slot.getRow(), slot.getColumn(), gridPane);
        pane.getChildren().removeAll();

        if (slot.isWorkerOnSlot()){
            //aggiunta livelli poi worker
            //FileInputStream input = null;
            //input = getImageWorkerFromColor(slot.getWorkerColor());
            Image image = new Image(getImageWorkerFromColor(slot.getWorkerColor()));
            ImageView im1 = new ImageView(image);
            im1.fitWidthProperty().bind(pane.widthProperty());
            im1.fitHeightProperty().bind(pane.heightProperty());
            pane.getChildren().add(im1);
        }else{
            // aggiunta livelli
        }
    }

    /**
     * method that gets th worker immage based on the color of the worker that is in the slot
     * @param workerColor is the color of the worker
     * @return image of the worker
     */
    private String getImageWorkerFromColor(Color workerColor) {
        if(workerColor== Color.BLUE)
            return "/Images/pedinablu.png";
        else if (workerColor== Color.RED)
           return "/Images/pedinarossa.png";
        else if (workerColor== Color.YELLOW)
           return "/Images/pedinagialla.png";
        else if (workerColor== Color.GREEN)
           return "/Images/pedinaverde.png";
        else if (workerColor== Color.PURPLE)
           return "/Images/pedinaviola.png";
        else if (workerColor== Color.WHITE)
           return "/Images/pedinabianca.png";
        else if (workerColor== Color.CYAN)
           return "/Images/pedinaazzurra.png";
       else
        return null;
    }

    /**
     * method used to get th pane from the gridPane from its row and column
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
        if (usernames.get(2)!=null)
            thirdPlayerInfo.appendText(usernames.get(2)+"\n"+ colors.get(2)+"\n"+ gods.get(2));
    }

}
