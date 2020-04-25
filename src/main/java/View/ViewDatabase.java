package View;

import Enumerations.Color;
import Enumerations.GodName;
import Model.Board;

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
    public Board boadView ;

    public ArrayList<String> usernames = new ArrayList<String>();
    public ArrayList<Color> colors = new ArrayList<Color>();
    public ArrayList <GodName> gods = new ArrayList<GodName>();




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

    public Board getBoadView() {
        return boadView;
    }

    public void setBoadView(Board boadView) {
        this.boadView = boadView;
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
