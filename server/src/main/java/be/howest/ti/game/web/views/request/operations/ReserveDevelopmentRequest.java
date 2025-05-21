package be.howest.ti.game.web.views.request.operations;

import be.howest.ti.game.web.views.request.BaseSplendorRequest;
import io.vertx.ext.web.RoutingContext;

public class ReserveDevelopmentRequest extends BaseSplendorRequest {
    public ReserveDevelopmentRequest(RoutingContext ctx) {
        super(ctx);
    }

    public int getGameId() {
        return params.pathParameter("gameId").getInteger();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }

    public String getDevelopmentName() {
        if (params.body().getJsonObject().getJsonObject("development").containsKey("name")) {
            return params.body().getJsonObject().getJsonObject("development").getString("name");
        }
        throw new IllegalArgumentException("Please provide a valid development name");
    }

    public int getDevelopmentLevel() {
        if (params.body().getJsonObject().getJsonObject("development").containsKey("level")) {
            return params.body().getJsonObject().getJsonObject("development").getInteger("level");
        } else throw new IllegalArgumentException("Please provide a valid level (1-3)");
    }
}
