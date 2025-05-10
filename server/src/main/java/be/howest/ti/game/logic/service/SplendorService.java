package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.Token;

import java.util.List;
import java.util.Map;

public interface SplendorService {


    public GameLobby findLobby(int gameid);
    public GameSuperclass findGame(int gameId);

    void joinLobby(GameLobby lobby, String playerName);

    GameLobby createLobby(int maxPlayers, String creatorName);
    GameLobby createLobby(int maxPlayers, String creatorName, String gameName);

    List<GameSuperclass> getGames();
    List<GameSuperclass> getGames(boolean hasStarted);
}
