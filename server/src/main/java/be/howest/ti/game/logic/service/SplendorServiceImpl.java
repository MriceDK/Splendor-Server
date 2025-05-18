package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.util.reader.DevelopmentReader;
import be.howest.ti.game.util.reader.NobleReader;
import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import be.howest.ti.game.web.views.request.BuyReservedDevelopmentRequest;

import java.util.ArrayList;
import java.util.List;

public class SplendorServiceImpl implements SplendorService {

    private int incrementalIdentifier = 0;

    private final List<GameSuperclass> games;

    public SplendorServiceImpl() {
        games = new ArrayList<>();
    }

    public GameLobby createLobby(int maxPlayers, String creatorName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, maxPlayers);
        newLobby.addPlayer(creatorName);
        games.add(newLobby);

        return newLobby;
    }

    public GameLobby createLobby(int maxPlayers, String creatorName, String gameName) {
        int gameId = generateGameId();
        GameLobby newLobby = new GameLobby(gameId, gameName, maxPlayers);
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
        throw new IllegalArgumentException("Lobby not found");
    }

    public SplendorGame findStartedGame(int gameId) {
        for (SplendorGame game : getStartedGames()) {
            if (gameId == game.getGameId()) {
                return game;
            }
        }
        throw new IllegalArgumentException("Game not found or it has not started yet");
    }

    public GameSuperclass findGame(int gameId) {
        for (GameSuperclass game : getGames()) {
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

    @Override
    public ArrayList<GameSuperclass> removeGames() {
        if (getGames().isEmpty()) {
            throw new IllegalArgumentException("No games to delete");
        }
        ArrayList<GameSuperclass> gamesThatWereDeleted = new ArrayList<>(games);
        games.clear();
        return gamesThatWereDeleted;
    }

    public int generateGameId() {
        int gameId = incrementalIdentifier;

        incrementalIdentifier++;

        return gameId;
    }

    public List<Development> getAllDevelopments() {
        DevelopmentReader reader = new DevelopmentReader();
        return reader.getAllDevelopments();
    }

    @Override
    public Player buyReservedDevelopment(BuyReservedDevelopmentRequest request) {
        SplendorGame game = findStartedGame(request.getGameId());
        Player player = game.findPlayer(request.getPlayerName());
        game.buyReservedDevelopment(request.getPayment(), request.getDevelopmentName(), player);

        return player;
    }

    public List<Noble> getAllNobles() {
        NobleReader reader = new NobleReader();
        return reader.getAllNobles();
    }

}
