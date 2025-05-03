package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.Player;

public interface SplendorService {

    public GameLobby findLobby(int gameid);

    void joinLobby(GameLobby lobby, String playerName);
}
