package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameLobby;

public class JoinGameResponse extends AbstractResponseWithHiddenStatus {
    private final GameLobby game;
    private final String playerName;

    public JoinGameResponse(GameLobby game, String playerName) {
        super(200);
        this.game = game;
        this.playerName = playerName;
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return getGameId() + "_" + getPlayerName();
    }
}
