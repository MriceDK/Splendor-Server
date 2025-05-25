package be.howest.ti.game.web.views.response.manager;

import be.howest.ti.game.web.views.response.AbstractResponseWithHiddenStatus;

public class JoinSpectateGameResponse extends AbstractResponseWithHiddenStatus {
    private final int gameId;

    public JoinSpectateGameResponse(int gameId) {
        super(200);
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }
}
