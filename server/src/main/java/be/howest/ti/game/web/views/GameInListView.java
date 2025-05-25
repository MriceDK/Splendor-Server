package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.GameSuperclass;
import be.howest.ti.game.logic.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameInListView implements Comparable<GameInListView> {

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

    public boolean getStarted() {
        return game.hasStarted();
    }

    public boolean getPrivate() {
        return game.isPrivate();
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

    public List<String> getSpectators() {
        return game.getSpectators();
    }

    @Override
    public int compareTo(GameInListView o) {
        return this.game.getGameId() - o.game.getGameId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameInListView that = (GameInListView) o;
        return Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(game);
    }
}
