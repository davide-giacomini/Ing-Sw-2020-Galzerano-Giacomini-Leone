package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.CurrentMoment;
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

    private boolean start = false;

    private ArrayList<String> usernames = new ArrayList<String>();
    private ArrayList<Color> colors = new ArrayList<Color>();
    private ArrayList <GodName> gods = new ArrayList<GodName>();

    private CurrentMoment currentMoment;
    private boolean myTurn;

    public GameView(){
    }

    public String getGodByUsername(String username){
        int index = usernames.indexOf(username);
        
        return gods.get(index).toString().toLowerCase();
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

    public CurrentMoment getCurrentMoment() {
        return currentMoment;
    }

    public void updateMoment(CurrentMoment currentMoment) {
        this.currentMoment = currentMoment;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
