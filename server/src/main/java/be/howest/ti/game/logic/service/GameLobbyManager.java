package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.logic.exceptions.SplendorGameResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class GameLobbyManager {

    private int incrementalIdentifier = 0;

    private final List<GameSuperclass> games;

    public GameLobbyManager() {
        games = new ArrayList<>();
    }

    public GameLobby createPublicLobby(int maxPlayers, String creatorName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, maxPlayers);
        newLobby.addPlayer(creatorName);
        games.add(newLobby);

        return newLobby;
    }

    public GameLobby createPublicLobby(int maxPlayers, String creatorName, String gameName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, gameName, maxPlayers);
        newLobby.addPlayer(creatorName);
        games.add(newLobby);

        return newLobby;
    }

    public PrivateGameLobby createPrivateLobby(int maxPlayers, String creatorName, String password) {
        int gameId = generateGameId();
        PrivateGameLobby newLobby = new PrivateGameLobby(gameId, maxPlayers, password);
        newLobby.addPlayer(creatorName);
        games.add(newLobby);

        return newLobby;
    }

    public PrivateGameLobby createPrivateLobby(int maxPlayers, String creatorName, String gameName, String password) {
        int gameId = generateGameId();
        PrivateGameLobby newLobby = new PrivateGameLobby(gameId, gameName, maxPlayers, password);
        newLobby.addPlayer(creatorName);
        games.add(newLobby);

        return newLobby;
    }

    public void startGame(GameLobby lobby) {
        SplendorGame game = lobby.startGame();

        games.remove(lobby);
        games.add(game);
    }

    public GameLobby findLobby(int gameId) {
        for (GameLobby lobby : getLobbies()) {
            if (gameId == lobby.getGameId()) {
                return lobby;
            }
        }
        throw new SplendorGameResourceNotFoundException("Lobby not found");
    }

    public SplendorGame findStartedGame(int gameId) {
        for (SplendorGame game : getStartedGames()) {
            if (gameId == game.getGameId()) {
                return game;
            }
        }
        throw new SplendorGameResourceNotFoundException("Game not found or it has not started yet");
    }

    public GameSuperclass findGame(int gameId) {
        for (GameSuperclass game : getGames()) {
            if (gameId == game.getGameId()) {
                return game;
            }
        }
        throw new SplendorGameResourceNotFoundException("Game not found");
    }


    public void joinLobby(GameLobby lobby, String playerName) {
        lobby.addPlayer(playerName);

        if (lobby.isFull()) {
            startGame(lobby);
        }
    }

    public void joinLobby(PrivateGameLobby lobby, String playerName, String password) {
        if (!lobby.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }
        lobby.addPlayer(playerName);
        if (lobby.isFull()) {
            startGame(lobby);
        }
    }

    public void removePlayer(GameSuperclass game, String playerName) {
        game.removePlayer(playerName);
        if (game.hasStarted()) {
            removeGame(game.getGameId());
            return;
        }
        if (game.isEmpty()){
            removeGame(game.getGameId());

        }
    }

    public void spectateLobby(GameSuperclass game, String spectatorName, String password) {
        if (game.isPrivate()) {
            if (game.hasStarted()) {
                PrivateSplendorGame privateGame = (PrivateSplendorGame) game;
                if (!privateGame.getPassword().equals(password)) {
                    throw new IllegalArgumentException("Wrong password");
                }
                game.addSpectator(spectatorName);
            } else {
                PrivateGameLobby privateLobby = (PrivateGameLobby) game;
                if (!privateLobby.getPassword().equals(password)) {
                    throw new IllegalArgumentException("Wrong password");
                }
                game.addSpectator(spectatorName);
            }
        } else throw new IllegalArgumentException("Game isn't private");
    }

    public void spectateLobby(GameSuperclass game, String spectatorName) {
        game.addSpectator(spectatorName);
    }

    public void removeSpectator(GameSuperclass game, String spectatorName) {
        game.removeSpectator(spectatorName);
    }

    public List<GameSuperclass> getGames() {
        return games;
    }

    public List<GameSuperclass> getGames(boolean hasStarted) {
        List<GameSuperclass> res = new ArrayList<>();

        if (hasStarted) {
            res.addAll(getStartedGames());
        } else {
            res.addAll(getLobbies());
        }

        return res;
    }

    public List<GameLobby> getLobbies() {
        List<GameLobby> lobbies = new ArrayList<>();

        for (GameSuperclass game : games) {

            if (!game.hasStarted()) {
                lobbies.add((GameLobby) game);
            }

        }

        return lobbies;
    }

    public List<SplendorGame> getStartedGames() {
        List<SplendorGame> startedGames = new ArrayList<>();

        for (GameSuperclass game : games) {

            if (game.hasStarted()) {
                startedGames.add((SplendorGame) game);
            }

        }

        return startedGames;
    }

    public void removeGame(int gameId) {
        GameSuperclass game = findGame(gameId);
        games.remove(game);
    }


    public List<GameSuperclass> removeGames() {
        if (getGames().isEmpty()) {
            throw new SplendorGameResourceNotFoundException("No games to delete");
        }
        List<GameSuperclass> gamesThatWereDeleted = new ArrayList<>(games);
        games.clear();
        return gamesThatWereDeleted;
    }

    public int generateGameId() {
        int gameId = incrementalIdentifier;

        incrementalIdentifier++;

        return gameId;
    }
}
