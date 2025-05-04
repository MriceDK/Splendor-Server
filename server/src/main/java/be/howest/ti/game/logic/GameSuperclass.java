package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.List;

public class GameSuperclass {

    private final int gameId; // TODO Find another way to generate gameId
    private final List<Player> players;
    private final String gameName;

    public GameSuperclass(int gameId, String gameName){
        this.gameId = gameId;
        this.gameName = gameName;
        players = new ArrayList<>();
    }

    public GameSuperclass(int gameId){
        this(gameId, null);
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
}
