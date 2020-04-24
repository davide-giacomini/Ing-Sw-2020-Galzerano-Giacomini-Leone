package View;

import Enumerations.GodName;
import Model.Board;

/*This class stores in the client some basic information of the model that the view needs in order to
interact with the user
 */
public class ViewDatabase {

    public String myUsername;
    public int myIndex;
    public GodName myGod;
    public int numberOfPlayers ;
    public Board boadView ;


    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public String getMyUsername() {
        return myUsername;
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
