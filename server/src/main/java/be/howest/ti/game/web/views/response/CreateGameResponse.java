package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.GameLobby;

public class CreateGameResponse extends AbstractResponseWithHiddenStatus {

    private final GameLobby game;
    private final String playerName;
    private final String token;

    public CreateGameResponse(GameLobby game, String playerName, String token) {
        super(200);
        this.game = game;
        this.playerName = playerName;
        this.token = token;
    }

    public int getGameId() {
        return game.getGameId();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return token;
    }
}