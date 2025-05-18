package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.*;
import java.util.ArrayList;
import java.util.List;

public interface SplendorService {


    GameLobby findLobby(int gameid);

    GameSuperclass findGame(int gameId);

    void joinLobby(GameLobby lobby, String playerName);

    GameLobby createLobby(int maxPlayers, String creatorName);

    GameLobby createLobby(int maxPlayers, String creatorName, String gameName);

    List<GameSuperclass> getGames();

    List<GameSuperclass> getGames(boolean hasStarted);

    void removeGame(int gameId);

    List<GameSuperclass> removeGames();

    SplendorGame findStartedGame(int gameId);

    List<Development> getAllDevelopments();

    Player buyReservedDevelopment(int gameId, String playerName, String DevelopmentName, Purse payment);

}
