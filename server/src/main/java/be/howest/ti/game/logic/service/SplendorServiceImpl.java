package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.SplendorGame;

import java.util.ArrayList;
import java.util.List;

public class SplendorServiceImpl implements SplendorService {

    private final List<GameSuperclass> allGames;

    public SplendorServiceImpl() {
        allGames = new ArrayList<>();
    }

    public GameLobby createLobby(int maxPlayers, String creatorName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, maxPlayers);
        newLobby.addPlayer(creatorName);
        allGames.add(newLobby);

        return newLobby;
    }

    public GameLobby createLobby(int maxPlayers, String creatorName, String gameName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, gameName, maxPlayers);
        newLobby.addPlayer(creatorName);
        allGames.add(newLobby);

        return newLobby;
    }

    public void startGame(GameLobby lobby) {
        SplendorGame game = lobby.startGame();

        allGames.remove(lobby);
        allGames.add(game);
    }

    public GameLobby findLobby(int gameId) {
        for (GameLobby lobby : getLobbies()) {
            if (gameId == lobby.getGameId()) {
                return lobby;
            }
        }
        throw new IllegalArgumentException("Lobby not found");
    }

    public SplendorGame findStartedGame(int gameId) {
        for (SplendorGame game : getStartedGames()) {
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
        return allGames;
    }

    public List<GameLobby> getLobbies() {
        List<GameLobby> lobbies = new ArrayList<>();

        for (GameSuperclass game : allGames) {

            if (!game.hasStarted()) {
                lobbies.add((GameLobby) game);
            }

        }

        return lobbies;
    }

    public List<SplendorGame> getStartedGames() {
        List<SplendorGame> startedGames = new ArrayList<>();

        for (GameSuperclass game : allGames) {

            if (game.hasStarted()) {
                startedGames.add((SplendorGame) game);
            }

        }

        return startedGames;
    }

    public int generateGameId() {
        return allGames.size();
    }

}
