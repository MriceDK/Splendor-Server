package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.*;

import java.util.List;

public class SplendorServiceImpl implements SplendorService {

    private final GameLobbyManager gameLobbyManager;
    private final GameActionManager gameActionManager;

    public SplendorServiceImpl() {
        this.gameLobbyManager = new GameLobbyManager();
        this.gameActionManager = new GameActionManager(gameLobbyManager);
    }

    @Override
    public GameLobby findLobby(int gameId) {
        return gameLobbyManager.findLobby(gameId);
    }

    @Override
    public GameSuperclass findGame(int gameId) {
        return gameLobbyManager.findGame(gameId);
    }

    @Override
    public void joinLobby(int gameId, String playerName) {
        gameLobbyManager.joinLobby(findLobby(gameId), playerName);
    }

    @Override
    public void spectateGame(int gameId, String spectatorName) {
        gameLobbyManager.spectateLobby(findGame(gameId), spectatorName);
    }

    @Override
    public void removeSpectator(int gameId, String spectatorName) {
        gameLobbyManager.removeSpectator(findGame(gameId), spectatorName);
    }

    @Override
    public void removePlayer(int gameId, String playerName) {
        gameLobbyManager.removePlayer(findGame(gameId), playerName);
    }

    @Override
    public GameLobby createLobby(int maxPlayers, String creatorName) {
        return gameLobbyManager.createLobby(maxPlayers, creatorName);
    }

    @Override
    public GameLobby createLobby(int maxPlayers, String creatorName, String gameName) {
        return gameLobbyManager.createLobby(maxPlayers, creatorName, gameName);
    }

    @Override
    public GameLobby createPrivateLobby(int maxPlayers, String creatorName, String password) {
        return gameLobbyManager.createPrivateLobby(maxPlayers, creatorName, password);
    }

    @Override
    public GameLobby createPrivateLobby(int maxPlayers, String creatorName, String gameName, String password) {
        return gameLobbyManager.createPrivateLobby(maxPlayers, creatorName, gameName, password);
    }

    @Override
    public List<GameSuperclass> getGames() {
        return gameLobbyManager.getGames();
    }

    @Override
    public List<GameSuperclass> getGames(boolean hasStarted) {
        return gameLobbyManager.getGames(hasStarted);
    }

    @Override
    public void removeGame(int gameId) {
        gameLobbyManager.removeGame(gameId);
    }

    @Override
    public List<GameSuperclass> removeGames() {
        return gameLobbyManager.removeGames();
    }

    @Override
    public SplendorGame findStartedGame(int gameId) {
        return gameLobbyManager.findStartedGame(gameId);
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
