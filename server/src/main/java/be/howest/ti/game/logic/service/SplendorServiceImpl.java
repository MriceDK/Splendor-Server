package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.Player;

import java.util.List;
import java.util.Map;

public class SplendorServiceImpl implements SplendorService {
    private List<GameLobby> unstartedGames;
    private List<GameLobby> startedGames;
    private Map<Integer, GameLobby> games;

    public void deleteGame(int gameId){
        //TODO
    }

    public void deleteAllGames(){
        //TODO
    }

    @Override
    public void createGameLobby(int maxPlayers) {

    }

    @Override
    public void startGameLobby() {

    }

    @Override
    public void addPlayerToGameLobby(Player player) {

    }
}
