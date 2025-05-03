package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.Player;

public interface SplendorService {

    GameLobby createLobby(int maxPlayers, String creatorName);
    GameLobby createLobby(int maxPlayers, String creatorName, String gameName);
}
