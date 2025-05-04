package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.SplendorGame;

import java.util.ArrayList;
import java.util.List;

public class SplendorServiceImpl implements SplendorService {


    private final List<GameLobby> lobbies; // TODO make it so that both unstarted and started games can be collected in this one list. (so Objects of GameLobby class and SplendorGame class)
    private final List<SplendorGame> games;

    public SplendorServiceImpl() {
        lobbies = new ArrayList<>();
        games = new ArrayList<>();
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

    public void startGame(GameLobby lobby) {
        SplendorGame game = lobby.startGame();

        games.add(game);
        lobbies.remove(lobby);
    }

    public GameLobby findLobby(int gameId) {
        for (GameLobby lobby : lobbies) {
            if (gameId == lobby.getGameId()) {
                return lobby;
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    public SplendorGame findStartedGame(int gameId) {
        for (SplendorGame game : games) {
            if (gameId == game.getGameId()) {
                return game;
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    public GameSuperclass findGame(int gameId) {
        for (GameSuperclass game : getAllGames()) {
            if (gameId == game.getGameId()) {
                return game;
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public void joinLobby(GameLobby lobby, String playerName) {
        lobby.addPlayer(playerName);

        if (lobby.isFull()) {
            startGame(lobby);
        }
    }

    public List<GameSuperclass> getAllGames() {
        List<GameSuperclass> list = new ArrayList<>();

        list.addAll(lobbies);
        list.addAll(games);

        return list;
    }

    public int generateGameId() {
        return lobbies.size();
    }

}
