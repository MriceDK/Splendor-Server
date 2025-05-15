package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.*;

import java.util.List;

public interface SplendorService {


    GameLobby findLobby(int gameid);
    GameSuperclass findGame(int gameId);

    void joinLobby(GameLobby lobby, String playerName);

    GameLobby createLobby(int maxPlayers, String creatorName);
    GameLobby createLobby(int maxPlayers, String creatorName, String gameName);

    List<GameSuperclass> getGames();
    List<GameSuperclass> getGames(boolean hasStarted);

    SplendorGame findStartedGame(int gameId);

    List<Development> getAllDevelopments();
    List<Noble> getAllNobles();
    }
