package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

import java.util.Map;

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
        if (ctx.body().asJsonObject().containsKey("isSpectator")) {
            return ctx.body().asJsonObject().getBoolean("isSpectator");
        }
        throw new IllegalArgumentException("isSpectator is missing");
    }

    public boolean getWantsToLeave() {
        // ctx is used here because it ignores the spec
        // this is a bonus functionality
        if (ctx.body().asJsonObject().containsKey("wantsToLeave")) {
            return ctx.body().asJsonObject().getBoolean("wantsToLeave");
        }
        throw new IllegalArgumentException("wantsToLeave is missing");
    }

    public String getToken() {
        if (!ctx.request().getHeader("Authorization").isEmpty()) {
            return ctx.request().getHeader("Authorization");
        }
        throw new IllegalArgumentException("Authorization token is missing");
    }
}
