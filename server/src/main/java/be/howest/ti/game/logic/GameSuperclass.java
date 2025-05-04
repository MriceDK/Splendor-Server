package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.List;

public abstract class GameSuperclass {

    private final int gameId; // TODO Find another way to generate gameId
    private final List<Player> players;
    private final String gameName;
    private final int maxPlayers;

    public GameSuperclass(int gameId, String gameName, int maxPlayers){
        this.gameId = gameId;
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        players = new ArrayList<>();
    }

    public GameSuperclass(GameSuperclass game) {
        this.gameId = game.getGameId();
        this.gameName = game.getGameName();
        this.players = game.getPlayers();
        this.maxPlayers = game.getMaxPlayers();
    }

    public int getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalPlayers() { return players.size(); }

    public String getGameName() {
        return gameName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void addPlayer(String name){
        Player newPlayer = new Player(name);

        validateNewPlayer(newPlayer);

        players.add(newPlayer);

    }

    private void validateNewPlayer(Player newPlayer) {
        if (getTotalPlayers() >= maxPlayers) {
            throw new IllegalStateException("There are already " + maxPlayers + " in the game!");

        }

        if (players.contains(newPlayer)) {
            throw new IllegalStateException("There already exists a player with the same name in this game.");
        }
    }
}
