package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class JoinGameRequest extends BaseSplendorRequest {
    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public boolean getIsSpectator() {
        // ctx is used here because it ignores the spec
        // this is a bonus functionality
        return ctx.body().asJsonObject().getBoolean("isSpectator", false);
    }
}
