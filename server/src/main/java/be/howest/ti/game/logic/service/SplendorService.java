package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Development;
import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.SplendorGame;

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
    void removeGames();

    SplendorGame findStartedGame(int gameId);

    List<Development> getAllDevelopments();
    }
