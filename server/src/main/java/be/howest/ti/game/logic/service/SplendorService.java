package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Player;

public interface SplendorService {

    void createGameLobby(int maxPlayers);
    void startGameLobby();
    void addPlayerToGameLobby(Player player);




}
