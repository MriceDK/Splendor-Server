package be.howest.ti.game.logic;


import java.util.ArrayList;
import java.util.List;

public class GameLobby extends GameSuperclass {

    private final List<Player> players;

    public GameLobby(int gameId, String gameName, int maxPlayers){
        super(gameId, gameName, maxPlayers);
        players = getPlayers();
    }

    public GameLobby(int gameId, int maxPlayers){
        this(gameId, null, maxPlayers);
    }

    public void startGame(){
        //TODO
    }

    public void addPlayer(String name){
        Player newPlayer = new Player(name);

        if (players.contains(newPlayer)) {
            throw new IllegalStateException("There already exists a player with the same name in this game.");
        }

        players.add(newPlayer);
    }

}