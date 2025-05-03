package be.howest.ti.game.logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameLobby {

    private int gameId; // TODO make final + find a way to genereate a unique gameId per instance
    private final List<Player> players;
    private final int maxPlayers;
    private String gameName;

    public GameLobby(String gameName, int maxPlayers){
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        players = new ArrayList<>();
    }

    public GameLobby(int maxPlayers){
        this(null, maxPlayers);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameLobby gameLobby = (GameLobby) o;
        return gameId == gameLobby.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameId);
    }

}