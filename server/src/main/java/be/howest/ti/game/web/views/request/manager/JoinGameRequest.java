package be.howest.ti.game.web.views.request.manager;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
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
        // ctx is used because it allows arguments to be passed on which are not in the spec
        // this is a bonus functionality
        if (ctx.body().asJsonObject().containsKey("isSpectator")) {
            return ctx.body().asJsonObject().getBoolean("isSpectator");
        }
        throw new IllegalArgumentException("isSpectator is missing");
    }

    public boolean getWantsToLeave() {
        // ctx is used because it allows arguments to be passed on which are not in the spec
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
