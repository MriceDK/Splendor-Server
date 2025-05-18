package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.*;
import be.howest.ti.game.web.views.request.BuyReservedDevelopmentRequest;

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
    ArrayList<GameSuperclass> removeGames();

    SplendorGame findStartedGame(int gameId);

    List<Development> getAllDevelopments();

    Player buyReservedDevelopment(BuyReservedDevelopmentRequest request);

    List<Noble> getAllNobles();
}
