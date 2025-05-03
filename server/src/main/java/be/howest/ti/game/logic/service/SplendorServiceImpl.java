package be.howest.ti.game.logic.service;

import be.howest.ti.game.logic.GameLobby;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplendorServiceImpl implements SplendorService {

    private List<GameLobby> lobbies; // TODO make it so that both unstarted and started games can be collected in this one list. (so Objects of GameLobby class and SplendorGame class)

    public SplendorServiceImpl() {
        lobbies = new ArrayList<>();
    }

    public void createLobby(int maxPlayers, String creatorName) {
        GameLobby newLobby = new GameLobby(maxPlayers);
        newLobby.addPlayer(creatorName);
        lobbies.add(newLobby);
    }

    public void createLobby(int maxPlayers, String creatorName, String gameName) {
        GameLobby newLobby = new GameLobby(gameName, maxPlayers);
        newLobby.addPlayer(creatorName);
        lobbies.add(newLobby);
    }

    public List<GameLobby> getGames() {
        return lobbies;
    }

}
