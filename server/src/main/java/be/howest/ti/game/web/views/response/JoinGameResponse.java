package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameLobby;

public class JoinGameResponse extends AbstractResponseWithHiddenStatus {
    private final int gameId;
    private final String playerName;
    private final String token;

    public JoinGameResponse(int gameId, String playerName, String token) {
        super(200);
        this.gameId = gameId;
        this.playerName = playerName;
        this.token = token;
    }

    public int getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return token;
    }
}
