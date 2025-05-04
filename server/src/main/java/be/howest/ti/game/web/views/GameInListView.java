package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.List;

public class GameInListView {

    private final GameSuperclass game;

    public GameInListView(GameSuperclass game) {
        this.game = game;
    }

    public List<String> getPlayers() {
        List<String> res = new ArrayList<>();

        for (Player player : game.getPlayers()) {

            res.add(player.getName());

        }

        return res;
    }

    public boolean getHasStarted() {
        return game.hasStarted();
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getGameName() {
        return game.getGameName();
    }

    public int getNumberOfPlayers() {
        return game.getMaxPlayers();
    }

}
