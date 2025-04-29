package be.howest.ti.game.logic.service;


import be.howest.ti.game.logic.Player;

import java.util.List;

public class GameLobby implements SplendorService {

    private final int gameId;
    private List<Player> players;
    private final int maxPlayers;
    private String gameName;

    public GameLobby(String gameName, int maxPlayers){
        //TODO
    }

    public GameLobby(int maxPlayers){
        //TODO
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public void createGameLobby(int maxPlayers) {
        //TODO
    }

    @Override
    public void startGameLobby() {
        //TODO

    }

    @Override
    public void addPlayerToGameLobby(Player player) {
        //TODO

    }
}