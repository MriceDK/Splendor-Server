package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class JoinGameRequest extends BaseSplendorRequest{
    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.body().getJsonObject().getInteger("gameId");
    }

    public String getPlayerName() {
        return params.body().getJsonObject().getString("playerName");
    }
}
