package Model;

public abstract class PlayerBehaviour {
    Player player;
    String name;
    
    public PlayerBehaviour(Player player, PlayerBehaviour god) {
        this.player = player;
        this.name = god.getClass().toString();
    }
    

}
