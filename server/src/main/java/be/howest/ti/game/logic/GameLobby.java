package be.howest.ti.game.logic;


import java.util.List;

public class GameLobby extends GameSuperclass {

    public GameLobby(int gameId, String gameName, int maxPlayers){
        super(gameId, gameName, maxPlayers);
    }

    public GameLobby(int gameId, int maxPlayers){
        this(gameId, null, maxPlayers);
    }

    public SplendorGame startGame(){
        return new SplendorGame(this);
    }

    public boolean isFull() {
        return super.getMaxPlayers() == super.getTotalPlayers();
    }

}