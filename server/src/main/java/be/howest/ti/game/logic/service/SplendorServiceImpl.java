package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.*;

import java.util.List;

public class SplendorServiceImpl implements SplendorService {

    private final GameLobbyManager lobbyManager;
    private final GameActionManager gameActionManager;

    public SplendorServiceImpl() {
        this.lobbyManager = new GameLobbyManager();
        this.gameActionManager = new GameActionManager(lobbyManager);
    }

    @Override
    public GameLobby findLobby(int gameId) {
        return lobbyManager.findLobby(gameId);
    }

    @Override
    public GameSuperclass findGame(int gameId) {
        return lobbyManager.findGame(gameId);
    }

    @Override
    public void joinLobby(GameLobby lobby, String playerName) {
        lobbyManager.joinLobby(lobby, playerName);
    }

    @Override
    public void spectateLobby(GameSuperclass game, String spectatorName) {
        lobbyManager.spectateLobby(game, spectatorName);
    }

    @Override
    public void leaveSpectate(GameSuperclass game, String spectatorName) {
        lobbyManager.leaveSpectate(game, spectatorName);
    }

    @Override
    public void leaveGame(GameSuperclass game, String playerName) {
        lobbyManager.leaveGame(game, playerName);
    }

    @Override
    public GameLobby createLobby(int maxPlayers, String creatorName) {
        return lobbyManager.createLobby(maxPlayers, creatorName);
    }

    @Override
    public GameLobby createLobby(int maxPlayers, String creatorName, String gameName) {
        return lobbyManager.createLobby(maxPlayers, creatorName, gameName);
    }

    @Override
    public List<GameSuperclass> getGames() {
        return lobbyManager.getGames();
    }

    @Override
    public List<GameSuperclass> getGames(boolean hasStarted) {
        return lobbyManager.getGames(hasStarted);
    }

    @Override
    public void removeGame(int gameId) {
        lobbyManager.removeGame(gameId);
    }

    @Override
    public List<GameSuperclass> removeGames() {
        return lobbyManager.removeGames();
    }

    @Override
    public SplendorGame findStartedGame(int gameId) {
        return lobbyManager.findStartedGame(gameId);
    }

    @Override
    public Player buyReservedDevelopment(int gameId, String playerName, String developmentName, Purse payment) {
        return gameActionManager.buyReservedDevelopment(gameId, playerName, developmentName, payment);
    }

    @Override
    public Player updateTokens(boolean takeOrReturn, int gameId, String playerName, Purse purse) {
        return gameActionManager.updateTokens(takeOrReturn, gameId, playerName, purse);
    }

    @Override
    public Player buyDevelopment(int gameId, String playerName, String developmentName, Purse payment) {
        return gameActionManager.buyDevelopment(gameId, playerName, developmentName, payment);
    }

    @Override
    public Player reserveDevelopment(int gameId, String playerName, String name) {
        return gameActionManager.reserveDevelopment(gameId, playerName, name);
    }

    @Override
    public Player reserveDevelopmentFromLevel(int gameId, String playerName, int level) {
        return gameActionManager.reserveDevelopmentFromLevel(gameId, playerName, level);
    }

    @Override
    public Noble chooseNoble(int gameId, String playerName, Noble noble) {
        return gameActionManager.chooseNoble(gameId, playerName, noble);
    }
}
