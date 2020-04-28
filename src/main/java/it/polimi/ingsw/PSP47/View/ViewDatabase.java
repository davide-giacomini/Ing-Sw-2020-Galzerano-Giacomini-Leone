package it.polimi.ingsw.PSP47.View;

import it.polimi.ingsw.PSP47.Enumerations.Color;
import it.polimi.ingsw.PSP47.Enumerations.GodName;

import java.util.ArrayList;

/*This class stores in the client some basic information of the model that the view needs in order to
interact with the user
 */
public class ViewDatabase {

    public String myUsername;
    public Color myColor;
    public int myIndex;
    public GodName myGod;
    public int numberOfPlayers ;
    public BoardView boardView ;

    public ArrayList<String> usernames = new ArrayList<String>();
    public ArrayList<Color> colors = new ArrayList<Color>();
    public ArrayList <GodName> gods = new ArrayList<GodName>();

    ViewDatabase(){
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
    
    public int getMyIndex() {
        return myIndex;
    }

    public void setMyIndex(int myIndex) {
        this.myIndex = myIndex;
    }

    public GodName getMyGod() {
        return myGod;
    }

    public void setMyGod(GodName godChosen){
        this.myGod  = godChosen;
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




    /*for now what I need is:  - board
                                -slots
                               - lista colori
                               - lista username
                               -ordine players
                               -challenger?
                               -lista gods
                               -number of players
                               -which number are you in the turn


    */
}
