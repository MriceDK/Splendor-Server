package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplendorServiceImpl implements SplendorService {

    private static int incrementalIdentifier = 0;

    private final List<GameLobby> lobbies; // TODO make it so that both unstarted and started games can be collected in this one list. (so Objects of GameLobby class and SplendorGame class)

    public SplendorServiceImpl() {
        lobbies = new ArrayList<>();
    }

    public GameLobby createLobby(int maxPlayers, String creatorName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, maxPlayers);
        newLobby.addPlayer(creatorName);
        lobbies.add(newLobby);

        return newLobby;
    }

    public GameLobby createLobby(int maxPlayers, String creatorName, String gameName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, gameName, maxPlayers);
        newLobby.addPlayer(creatorName);
        lobbies.add(newLobby);

        return newLobby;
    }

    public GameLobby findLobby(int gameId) {
        for (GameLobby lobby : lobbies) {
            if (gameId == lobby.getGameId()) {
                return lobby;
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public void joinLobby(GameLobby lobby, String playerName) {
        lobby.addPlayer(playerName);
    }

    public List<GameLobby> getGames() {
        return lobbies;
    }

    public int generateGameId() {
        int gameId = incrementalIdentifier;

        incrementalIdentifier++;

        return gameId;
    }

}
