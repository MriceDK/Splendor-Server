package be.howest.ti.game.logic;


import java.util.ArrayList;
import java.util.List;

public class GameLobby extends GameSuperclass {

    private final int maxPlayers;

    public GameLobby(int gameId, String gameName, int maxPlayers){
        super(gameId, gameName);
        this.maxPlayers = maxPlayers;
    }

    public GameLobby(int gameId, int maxPlayers){
        this(gameId, null, maxPlayers);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void startGame(){
        //TODO
    }

}