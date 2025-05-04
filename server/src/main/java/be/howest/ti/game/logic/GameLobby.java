package be.howest.ti.game.logic;


import java.util.ArrayList;
import java.util.List;

public class GameLobby {

    private final int gameId; // TODO Find another way to generate gameId
    private final List<Player> players;
    private final int maxPlayers;
    private String gameName;

    public GameLobby(int gameId, String gameName, int maxPlayers){
        this.gameId = gameId;
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        players = new ArrayList<>();
    }

    public GameLobby(int gameId, int maxPlayers){
        this(gameId, null, maxPlayers);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalPlayers() { return players.size(); }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    private void setGameName(String gameName) {
        this.gameName = gameName;
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


    public int getGameId() {
        return gameId;
    }
}