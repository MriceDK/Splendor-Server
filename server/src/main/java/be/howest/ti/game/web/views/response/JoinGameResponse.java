package be.howest.ti.game.web.views.response;

public class JoinGameResponse extends AbstractResponseWithHiddenStatus {
    public JoinGameResponse(int status) {
        super(status);
    }

    public int getGameId() {
        return params.body().getJsonObject().getInteger("gameId");
    }

    public String getPlayerName() {
        return params.body().getJsonObject().getString("playerName");
    }

    public String getPlayerToken() {
        return params.body().getJsonObject().getString("playerToken");
    }
}
