package be.howest.ti.game.web.views;

import be.howest.ti.game.logic.GameSuperclass;

import java.util.List;

public class GameInListView {

    private final GameSuperclass game;

    public GameInListView(GameSuperclass game) {
        this.game = game;
    }

    public int getGameId() {
        return game.getGameId();
    }

}
