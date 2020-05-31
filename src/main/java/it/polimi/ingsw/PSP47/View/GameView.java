package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.CurrentScene;
import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;

/**
 * This class stores in the client some basic information of the model that the view needs in order to
 *interact with the user
 */
public class GameView{

    private String myUsername;
    private Color myColor;
    private GodName myGod;
    private int numberOfPlayers ;
    private BoardView boardView ;

    private ArrayList<String> usernames = new ArrayList<String>();
    private ArrayList<Color> colors = new ArrayList<Color>();
    private ArrayList <GodName> gods = new ArrayList<GodName>();

    private CurrentScene currentScene;
    private boolean turn;

    public GameView(){
        this.boardView = new BoardView();
    }


    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getMyUsername() {
        return myUsername;
    }
    
    public void setMyColor(Color myColor) {
        this.myColor = myColor;
    }

    public Color getMyColor() {
        return myColor;
    }

    public void setMyGod(GodName godChosen){
        this.myGod  = godChosen;
    }

    public GodName getMyGod() {
        return myGod;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setColors(ArrayList<Color> color) {
        this.colors = color;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setGods(ArrayList<GodName> gods) {
        this.gods = gods;
    }

    public ArrayList<GodName> getGods() {
        return gods;
    }

    public CurrentScene getCurrentScene() {
        return currentScene;
    }

    public void updateMoment(CurrentScene currentScene) {
        this.currentScene = currentScene;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
