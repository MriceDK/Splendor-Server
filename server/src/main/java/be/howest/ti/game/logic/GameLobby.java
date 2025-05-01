package be.howest.ti.game.logic;


import java.util.List;

public class GameLobby {

    private final int gameId;
    private List<Player> players;
    private final int maxPlayers;
    private String gameName;

    public GameLobby(String gameName, int maxPlayers){
        //TODO
    }

    public GameLobby(int maxPlayers){
        //TODO
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    private void setGameName(String gameName) {
        this.gameName = gameName;
    }

}