package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.*;
import java.util.List;

public interface SplendorService {


    GameLobby findLobby(int gameid);

    GameSuperclass findGame(int gameId);

    void joinLobby(int gameId, String playerName);
    void spectateGame(int gameId, String spectatorName);

    void removeSpectator(int gameId, String spectatorName);
    void removePlayer(int gameId, String playerName);

    GameLobby createPublicLobby(int maxPlayers, String creatorName);

    GameLobby createPublicLobby(int maxPlayers, String creatorName, String gameName);

    GameLobby createPrivateLobby(int maxPlayers, String creatorName, String password);

    GameLobby createPrivateLobby(int maxPlayers, String creatorName, String gameName, String password);

    List<GameSuperclass> getGames();

    List<GameSuperclass> getGames(boolean hasStarted);

    void removeGame(int gameId);

    List<GameSuperclass> removeGames();

    SplendorGame findStartedGame(int gameId);

    Player buyReservedDevelopment(int gameId, String playerName, String developmentName, Purse payment);

    Player updateTokens(boolean takeOrReturn, int game, String playerName, Purse purse);

    Player buyDevelopment(int gameId, String playerName, String developmentName, Purse payment);

    Player reserveDevelopment(int gameId, String playerName, String name);

    Player reserveDevelopmentFromLevel(int gameId, String playerName, int level);

    Noble chooseNoble(int gameId, String playerName, Noble noble);
}
