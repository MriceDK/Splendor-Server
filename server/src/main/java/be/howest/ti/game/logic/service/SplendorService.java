package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.*;
import be.howest.ti.game.util.customization.CountryCode;

import java.util.List;

public interface SplendorService {


    GameLobby findLobby(int gameid);

    GameSuperclass findGame(int gameId);

    void joinLobby(int gameId, String playerName, CountryCode avatar);

    void joinLobby(int gameId, String playerName, CountryCode avatar, String password);

    void spectateGame(int gameId, String spectatorName);

    void spectateGame(int gameId, String spectatorName, String password);

    void removeSpectator(int gameId, String spectatorName);

    void removePlayer(int gameId, String playerName);

    GameLobby createPublicLobby(int maxPlayers, String creatorName, CountryCode avatar);

    GameLobby createPublicLobby(int maxPlayers, String creatorName, CountryCode avatar, String gameName);

    GameLobby createPrivateLobby(int maxPlayers, String creatorName, CountryCode avatar, String password);

    GameLobby createPrivateLobby(int maxPlayers, String creatorName, CountryCode avatar, String gameName, String password);

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
