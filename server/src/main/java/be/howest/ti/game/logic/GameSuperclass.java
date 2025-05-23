package be.howest.ti.game.logic;

import be.howest.ti.game.logic.exceptions.SplendorGameRuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameSuperclass implements Comparable<GameSuperclass> {

    private final int gameId;
    private final List<Player> players;
    private final List<String> spectators;
    private final String gameName;
    private final int maxPlayers;

    protected GameSuperclass(int gameId, String gameName, int maxPlayers) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();
    }

    protected GameSuperclass(GameSuperclass game) {
        this.gameId = game.getGameId();
        this.gameName = game.getGameName();
        this.players = game.getPlayers();
        this.maxPlayers = game.getMaxPlayers();
        this.spectators = game.getSpectators();
    }

    public int getGameId() {
        return gameId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalPlayers() {
        return players.size();
    }

    public String getGameName() {
        return gameName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<String> getSpectators() {
        return spectators;
    }

    public int getTotalSpectators() {
        return spectators.size();
    }

    public void addPlayer(String name) {
        Player newPlayer = new Player(name);

        validateNewPlayer(newPlayer);

        players.add(newPlayer);
    }

    public void removePlayer(String playerName) {
        Player playerToRemove = new Player(playerName);
        if (!players.contains(playerToRemove)) {
            throw new IllegalStateException("The player is not in the game.");
        }
        players.remove(playerToRemove);
    }

    private void validateNewPlayer(Player newPlayer) {
        if (getTotalPlayers() >= maxPlayers) {
            throw new SplendorGameRuleException("There are already " + maxPlayers + " in the game!");

        }

        if (players.contains(newPlayer)) {
            throw new IllegalStateException("There already exists a player with the same name in this game.");
        }
    }

    public void addSpectator(String name) {
        validateNewSpectator(name);
        spectators.add(name);
    }

    private void validateNewSpectator(String name) {
        if (players.contains(new Player(name))) {
            throw new SplendorGameRuleException("There already exists a player with this name in this game.");
        }
        if (spectators.contains(name)) {
            throw new IllegalStateException("There already exists a spectator with this name in this game.");
        }
    }

    public void removeSpectator(String spectatorName) {
        if (!spectators.contains(spectatorName)) {
            throw new IllegalStateException("The spectator is not in the game.");
        }
        spectators.remove(spectatorName);
    }

    public boolean hasStarted() {

        if (getClass() == GameLobby.class) {
            return false;
        } else return getClass() == SplendorGame.class;
    }

    @Override
    public int compareTo(GameSuperclass o) {
        return this.gameId - o.gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameSuperclass that = (GameSuperclass) o;
        return gameId == that.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameId);
    }
}
